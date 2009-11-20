package gov.nih.nci.cabig.labviewer.grid.test;

import gov.nih.nci.cabig.ccts.domain.Study;
import gov.nih.nci.cagrid.authentication.bean.BasicAuthenticationCredential;
import gov.nih.nci.cagrid.authentication.bean.Credential;
import gov.nih.nci.cagrid.authentication.client.AuthenticationClient;
import gov.nih.nci.cagrid.common.Utils;
import gov.nih.nci.cagrid.opensaml.SAMLAssertion;
import gov.nih.nci.ccts.grid.studyconsumer.client.StudyConsumerClient;

import java.io.*;

import org.apache.log4j.Logger;
import org.cagrid.gaards.dorian.client.GridUserClient;
import org.cagrid.gaards.dorian.federation.CertificateLifetime;
import org.globus.gsi.GlobusCredential;
import org.globus.wsrf.encoding.DeserializationException;
import org.xml.sax.SAXException;



/**
 * Test class to test the Study Consumer for lab viewer
 * <P>
 * 
 * @author Michael Holck
 */                                                       
public class LabViewerStudyTest                  
{
	String serviceUrl =
			"https://localhost:21443/ctom-wsrf/services/cagrid/StudyConsumer";
            //"https://ncias-c278-v.nci.nih.gov:21443/ctom-wsrf/services/cagrid/StudyConsumer";   // DEV1 URL
           // "https://ncias-d282-v.nci.nih.gov:29543/ctom-wsrf/services/cagrid/StudyConsumer";  // new DEV2 URL




    // ="https://cbvapp-d1029.nci.nih.gov:8443/ctom-wsrf/services/cagrid/StudyConsumer";
	// String serviceUrl=
	// "https://cbvapp-d1017.nci.nih.gov:28445/ctom-wsrf/services/cagrid/StudyConsumer";
	// String serviceUrl =
	// "http://cbvapp-d1017.nci.nih.gov:18080/ctom-wsrf/services/cagrid/StudyConsumer";
	// String serviceUrl=
	// "http://cbvapp-t1017.nci.nih.gov:8080/ctom-wsrf/services/cagrid/StudyConsumer";
	//String sampleFile = "/SampleStudyMessage.xml";

    String sampleFile = "C:/Development/sampleMessages/ccts_2.8_sample_study.xml";

    //String sampleFile = "/StudyMessage.xml";
	String proxyFile = "C:/Development/proxy";
	private Logger log = Logger.getLogger(getClass());

	/**
	 * main method just creates this class and calls the test method which
	 * performs all the work
	 * <P>
	 * 
	 * @param args
	 *            not needed
	 */
	public static void main(String[] args)
	{
		LabViewerStudyTest tester = new LabViewerStudyTest();
		tester.test();
	}

	/**
	 * test method performs the test by creating the registration object and
	 * calling the service
	 */
	public void test()
	{
		try
		{
			System.out.println("Calling study service at " + serviceUrl);
			log.debug("Calling study service at " + serviceUrl);

			// Setup the credentials
			GlobusCredential gb = new GlobusCredential(proxyFile);// this.obtainCredentials();//

			// Create the client
			StudyConsumerClient client =
					new StudyConsumerClient(this.serviceUrl, gb);

			// Create the Study object
			Study study = getStudy();

			// Call the service
			client.createStudy(study);
			// client.rollback(study);

			System.out.println("Returned from calling service");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Obtains grid credentials directly from Dorian.
	 * 
	 * @return GlobusCredential
	 */
	private GlobusCredential obtainCredentials()
	{

		GlobusCredential proxy = null;
		try
		{
			log.debug("Obtaining globus Proxy");
			// Create credential

			Credential cred = new Credential();
			BasicAuthenticationCredential bac =
					new BasicAuthenticationCredential();
			bac.setUserId("dev1@nci");
			bac.setPassword("D3v1@NC1.gov");
			cred.setBasicAuthenticationCredential(bac);

			// Authenticate to the IdP (DorianIdP) using credential

            /* RAM: on 10/26/2009 changed to go to current server */
            AuthenticationClient authClient =
					new AuthenticationClient(
							//"https://cbvapp-d1017.nci.nih.gov:38443/wsrf/services/cagrid/Dorian",
							"https://ncias-c278-v.nci.nih.gov:28443/webssoserver",
                            cred);



            SAMLAssertion saml = authClient.authenticate();

			// Requested Grid Credential lifetime (12 hours)

	        CertificateLifetime lifetime = new CertificateLifetime();
	        lifetime.setHours(12);

	        // Request PKI/Grid Credential
            /* RAM: on 10/26/2009 changed to go to current server */
            //String dorianURL="https://cbvapp-d1017.nci.nih.gov:38443/wsrf/services/cagrid/Dorian";
            String dorianURL="https://ncias-c278-v.nci.nih.gov:28443/webssoserver";

            GridUserClient dorian = new GridUserClient(dorianURL);
	        proxy = dorian.requestUserCertificate(saml, lifetime);


		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return proxy;

	}
	
	

	/**
	 * getStudy reads the XML file and creates a study object from it for
	 * testing
	 * <P>
	 * 
	 * @return
	 * @throws DeserializationException
	 * @throws SAXException
	 */
	private Study getStudy() throws DeserializationException, SAXException, IOException {
		//InputStream sampleIs = getClass().getResourceAsStream(sampleFile);
        InputStream sampleIs = new FileInputStream(sampleFile);
        InputStreamReader reader = new InputStreamReader(sampleIs);
		InputStream wsddIs =
				getClass()
						.getResourceAsStream(
								"/gov/nih/nci/ccts/grid/studyconsumer/client/client-config.wsdd");

		Study study =
				(gov.nih.nci.cabig.ccts.domain.Study) Utils.deserializeObject(
						reader, gov.nih.nci.cabig.ccts.domain.Study.class,
						wsddIs);

		return study;
	}

}
