/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */
package gov.nih.nci.caxchange.ctom.viewer.smokeTests;

import gov.nih.nci.ccts.grid.smoketest.client.SmokeTestServiceClient;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.globus.gsi.GlobusCredential;

/**
 * @author asharma
 */
public class TestPing
{

	private static final Logger log = Logger.getLogger(TestPing.class);
    
    //private static final String CONFIG_FILE = "/baseURL.properties";
    private static final String CONFIG_FILE = "/labviewer.properties";

    String serviceUrl = "";

	public static void main(String[] args)
	{
		GlobusCredential gc = null;
		TestPing tester = new TestPing();
		tester.test(gc);
	}

	/**
	 * test method performs the test by creating the registration object and
	 * calling the service
	 */
	public String test(GlobusCredential gc)
	{
		System.out.println("Beginning test to call Smoke Test Grid Service");
		log.debug("Beginning test to call Smoke Test Grid Service");
		String status = "";
		try
		{
			Properties props = new Properties();
			InputStream stream = getClass().getResourceAsStream(CONFIG_FILE);
			props.load(stream);
			serviceUrl = (String) props.getProperty("SmokeTestGridURL");

		}
		catch (FileNotFoundException e1)
		{
			log.error("The config file not found: " + CONFIG_FILE);
		}
		catch (IOException e1)
		{
			log.error("Error reading the config file: " + CONFIG_FILE);
		}
		System.out.println("Beginning test to call Smoke Test Grid Service"
				+ serviceUrl);
		log
				.debug("Beginning test to call Smoke Test Grid Service"
						+ serviceUrl);
		try
		{
			SmokeTestServiceClient sclient = null;
			if (gc != null)
			{
				sclient =
					new SmokeTestServiceClient(serviceUrl,gc);//sclient.setProxy(gc);
			}
			if (sclient != null) {
			 sclient.ping();
			 status = "Ping returned with no exceptions";
			}
		}
		catch (Exception e)
		{
			log.error(e);
			status = e.getLocalizedMessage();
		}
		log.debug("Smoke test status" + status);
		return status;
	}

    public TestPing(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }
}
