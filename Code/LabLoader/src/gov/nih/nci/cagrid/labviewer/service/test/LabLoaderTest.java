package gov.nih.nci.cagrid.labviewer.service.test;

import java.io.FileInputStream;
import java.net.URL;

import gov.nih.nci.cagrid.labviewer.client.LabLoaderClient;

public class LabLoaderTest
{
	String serviceUrl = "http://localhost:8080/wsrf/services/cagrid/LabLoader";
	//String serviceUrl = "http://cbiovqa5010.nci.nih.gov:28080/wsrf/services/cagrid/LabLoader";
	String sampleFile = "/SampleHL7v3.xml";
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		LabLoaderTest tester = new LabLoaderTest();
		tester.test();
	}
	
	/**
	 * test method performs the test by creating the registration object and calling
	 * the service
	 */
	public void test()
	{
		System.out.println("Beginning test to call Lab Loader");
		
		try
		{
			// Create the client
			LabLoaderClient client = new LabLoaderClient(this.serviceUrl);
			
			// Read the sample HL7v3 message to send
			URL fileLoc = getClass().getResource("/SampleHL7v3.xml");
			
			// Read the file from disk
			FileInputStream fis = new FileInputStream(fileLoc.getFile());
			int x= fis.available();
			byte b[]= new byte[x];
			fis.read(b);
			String content = new String(b);
			
			// Call the service
			client.loadLab(content);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		System.out.println("Lab Loader test complete");
	}
}
