package gov.nih.nci.cagrid.labviewer.service.test;

import gov.nih.nci.cagrid.authentication.bean.BasicAuthenticationCredential;
import gov.nih.nci.cagrid.authentication.bean.Credential;
import gov.nih.nci.cagrid.authentication.client.AuthenticationClient;
import gov.nih.nci.cagrid.dorian.client.IFSUserClient;
import gov.nih.nci.cagrid.labviewer.client.LabLoaderClient;
import gov.nih.nci.cagrid.opensaml.SAMLAssertion;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.File;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.cagrid.gaards.dorian.client.GridUserClient;
import org.cagrid.gaards.dorian.federation.CertificateLifetime;
import org.cagrid.gaards.dorian.federation.ProxyLifetime;
import org.globus.gsi.GlobusCredential;

public class LabLoaderTest
{
    private static final String CONFIG_FILE_1 = "C:/LabViewer/TestClient/service-url.properties";
    public static Properties props1 = new Properties();
    private static final Logger log = Logger.getLogger(LabLoaderTest.class);
    static {
        try {
            props1.load(new FileInputStream(CONFIG_FILE_1));
        } catch(IOException ioe) {
            System.out.println ("Error reading the config file: " + CONFIG_FILE_1);
            ioe.printStackTrace();
        }
    }

    String serviceUrl = (String) props1.get("serviceURL");
			//"https://ncias-c278-v.nci.nih.gov:21443/ctom-wsrf/services/cagrid/LabLoader";

    String proxyFile = "C:/LabViewer/TestClient/proxy";

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
			GlobusCredential gb = new GlobusCredential(proxyFile);//this.obtainCredentials();//

            // Create the client
		    LabLoaderClient client = new LabLoaderClient(this.serviceUrl,gb);


           File[] dataFiles = new File("C:/LabViewer/TestData").listFiles();
            for (int i = 0; i < dataFiles.length; i++) {
                File dataFile = dataFiles[i];

                if (! dataFile.getName().endsWith(".xml")) continue;

                FileInputStream fis = new FileInputStream(dataFile);
				int x= fis.available();
				byte b[]= new byte[x];
				fis.read(b);
				String content = new String(b);

                System.out.println("BEGIN loading data from file: " + dataFile.getName());
                log.debug("BEGIN loading data from file: " + dataFile.getName());

                // Call the service
                client.loadLab(content);

                System.out.println("FINISHED loading data from file: " + dataFile.getName());
                log.debug("FINISHED loading data from file: " + dataFile.getName());
                log.debug("\n\n");

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
