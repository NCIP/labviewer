package gov.nih.nci.cagrid.labviewer.service.test;

import gov.nih.nci.cagrid.authentication.bean.BasicAuthenticationCredential;
import gov.nih.nci.cagrid.authentication.bean.Credential;
import gov.nih.nci.cagrid.authentication.client.AuthenticationClient;
import gov.nih.nci.cagrid.labviewer.client.LabLoaderClient;
import gov.nih.nci.cagrid.opensaml.SAMLAssertion;

import java.io.File;
import java.io.FileInputStream;

import org.apache.log4j.Logger;
import org.cagrid.gaards.dorian.client.GridUserClient;
import org.cagrid.gaards.dorian.federation.CertificateLifetime;
import org.globus.gsi.GlobusCredential;

public class LabLoaderTest
{
	String serviceUrl =
	    "https://localhost:21443/ctom-wsrf/services/cagrid/LabLoader";
		//"https://ncias-c278-v.nci.nih.gov:21443/ctom-wsrf/services/cagrid/LabLoader"; // Dev1
        // "https://ncias-d282-v.nci.nih.gov:29543/ctom-wsrf/services/cagrid/LabLoader"; // Dev2

	String sampleFileDirectory = "D:/ccts/files/labs/SmokeTestLabs";
	String proxyFile = "D:/ccts/files/proxy/proxy";
    private Logger logger = Logger.getLogger(getClass());

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		LabLoaderTest tester = new LabLoaderTest();
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
            System.out.println("Calling SERVICE URL:" + serviceUrl);
            // Setup the credentials
			GlobusCredential gb = new GlobusCredential(proxyFile); //this.obtainCredentials();

            // Create the client
		    LabLoaderClient client = new LabLoaderClient(serviceUrl, gb);

            File[] sampleFiles = new File(sampleFileDirectory).listFiles();
            for (int i=0; i < sampleFiles.length; i++)
            {
                File sampleFile = sampleFiles[i];

                FileInputStream fileInputStream = new FileInputStream(sampleFile);
				int numBytes = fileInputStream.available();
				byte byteArray[] = new byte[numBytes];
				fileInputStream.read(byteArray);
				String lab = new String(byteArray);

                System.out.println("BEGIN loading data from file: " + sampleFile.getName());
                logger.debug("BEGIN loading data from file: " + sampleFile.getName());

                // Call the service
                client.loadLab(lab);

                System.out.println("FINISHED loading data from file: " + sampleFile.getName());
                logger.debug("FINISHED loading data from file: " + sampleFile.getName());
                logger.debug("\n\n");
            }
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
		logger.debug("Obtaining Globus Proxy");
		try
		{

			// Create credential

			Credential cred = new Credential();
			BasicAuthenticationCredential bac =
					new BasicAuthenticationCredential();
			bac.setUserId("ccts@nih.gov");
			bac.setPassword("!Ccts@nih.gov1");
			cred.setBasicAuthenticationCredential(bac);

			// Authenticate to the IdP (DorianIdP) using credential
			AuthenticationClient authClient =
					new AuthenticationClient("https://cbvapp-d1017.nci.nih.gov:38443/wsrf/services/cagrid/Dorian",
							cred);
			SAMLAssertion saml = authClient.authenticate();

			// Requested Grid Credential lifetime (12 hours)

	        CertificateLifetime lifetime = new CertificateLifetime();
	        lifetime.setHours(12);

	        // Request PKI/Grid Credential
	        String dorianURL="https://cbvapp-d1017.nci.nih.gov:38443/wsrf/services/cagrid/Dorian";
	        GridUserClient dorian = new GridUserClient(dorianURL);
	        proxy = dorian.requestUserCertificate(saml, lifetime);


		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return proxy;

	}
}
