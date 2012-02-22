package gov.nih.nci.cabig.labviewer.grid.test;

import gov.nih.nci.cabig.ccts.domain.Registration;
import gov.nih.nci.cagrid.authentication.bean.BasicAuthenticationCredential;
import gov.nih.nci.cagrid.authentication.bean.Credential;
import gov.nih.nci.cagrid.authentication.client.AuthenticationClient;
import gov.nih.nci.cagrid.common.Utils;
import gov.nih.nci.cagrid.opensaml.SAMLAssertion;
import gov.nih.nci.ccts.grid.client.RegistrationConsumerClient;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;
import org.cagrid.gaards.dorian.client.GridUserClient;
import org.cagrid.gaards.dorian.federation.CertificateLifetime;
import org.globus.gsi.GlobusCredential;
import org.globus.wsrf.encoding.DeserializationException;
import org.xml.sax.SAXException;

/**
 * Test class to test the Registration Consumer for lab viewer
 * <P>
 * 
 * @author Michael Holck
 */                                                                
public class LabViewerRegistrationTest                       
{
	String serviceUrl =
	    "https://localhost:21443/ctom-wsrf/services/cagrid/RegistrationConsumer";
		//"https://ncias-c278-v.nci.nih.gov:21443/ctom-wsrf/services/cagrid/RegistrationConsumer"; // Dev1
        // "https://ncias-d282-v.nci.nih.gov:29543/ctom-wsrf/services/cagrid/RegistrationConsumer"; // Dev2
	
	String sampleFile = "D:/CCTS/FILES/XML/REGISTER_SUBJECT.xml";
	String proxyFile = "D:/CCTS/FILES/PROXY/proxy";
	private Logger logger = Logger.getLogger(getClass());

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
		LabViewerRegistrationTest tester = new LabViewerRegistrationTest();
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
			System.out.println("Calling patient registration service at "
					+ serviceUrl);
			logger.debug("Calling patient registration service at "
					+ serviceUrl);

			// Setup the credentials
			GlobusCredential gb = new GlobusCredential(proxyFile); //this.obtainCredentials();//new
																	//

			// Create the client
			RegistrationConsumerClient client =
					new RegistrationConsumerClient(this.serviceUrl, gb);

			// Create the Registration object
			Registration reg = getRegistration();

			// Call the service
			client.register(reg);
			// client.rollback(reg);

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
			logger.debug("Obtaining globus Proxy");
			// Create credential

			Credential cred = new Credential();
			BasicAuthenticationCredential bac =
					new BasicAuthenticationCredential();
			bac.setUserId("dev1@nci");
			bac.setPassword("D3v1@NC1.gov");
			cred.setBasicAuthenticationCredential(bac);

			// Authenticate to the IdP (DorianIdP) using credential
			AuthenticationClient authClient =
					new AuthenticationClient(
							"https://ncias-c278-v.nci.nih.gov:28443/webssoserver",
							cred);
			SAMLAssertion saml = authClient.authenticate();

			// Requested Grid Credential lifetime (12 hours)

	        CertificateLifetime lifetime = new CertificateLifetime();
	        lifetime.setHours(12);

	        // Request PKI/Grid Credential
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
	 * getRegistration reads the XML file and creates a registration object from
	 * it for testing
	 * <P>
	 * 
	 * @return
	 * @throws DeserializationException
	 * @throws SAXException
	 */
	private Registration getRegistration() throws DeserializationException,
			SAXException, IOException {
		//InputStream sampleIs = getClass().getResourceAsStream(sampleFile);
         InputStream sampleIs = new FileInputStream(sampleFile);
        InputStreamReader reader = new InputStreamReader(sampleIs);
		InputStream wsddIs =
				getClass().getResourceAsStream(
						"/gov/nih/nci/ccts/grid/client/client-config.wsdd");

		Registration reg =
				(gov.nih.nci.cabig.ccts.domain.Registration) Utils
						.deserializeObject(
								reader,
								gov.nih.nci.cabig.ccts.domain.Registration.class,
								wsddIs);

		return reg;
	}

}
