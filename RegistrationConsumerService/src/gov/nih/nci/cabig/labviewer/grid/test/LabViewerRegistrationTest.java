package gov.nih.nci.cabig.labviewer.grid.test;

import gov.nih.nci.cagrid.common.Utils;
import gov.nih.nci.ccts.grid.Registration;
import gov.nih.nci.ccts.grid.client.RegistrationConsumerClient;
import gov.nih.nci.ccts.grid.service.globus.RegistrationConsumerAuthorization;

import java.io.InputStream;
import java.io.InputStreamReader;

import org.globus.wsrf.encoding.DeserializationException;
import org.xml.sax.SAXException;

/**
 * Test class to test the Registration Consumer for lab viewer
 * <P>
 * @author Michael Holck
 */
public class LabViewerRegistrationTest
{
	String serviceUrl = "http://localhost:8080/wsrf/services/cagrid/RegistrationConsumer";
	//String serviceUrl = "http://cbiovqa5010.nci.nih.gov:28080/wsrf/services/cagrid/RegistrationConsumer";
	String sampleFile = "/SampleRegistrationMessage.xml";
	
	/**
	 * main method just creates this class and calls the test method which performs all
	 * the work
	 * <P>
	 * @param args not needed
	 */
	public static void main(String[] args)
	{
		LabViewerRegistrationTest tester = new LabViewerRegistrationTest();
		tester.test();
	}
	
	/**
	 * test method performs the test by creating the registration object and calling
	 * the service
	 */
	public void test()
	{
		try
		{
			// Create the client
			RegistrationConsumerClient client = new RegistrationConsumerClient(this.serviceUrl);
			
			// Create the Registration object
			Registration reg = getRegistration();
			
			// Call the service
			client.register(reg);
			client.rollback(reg);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * getRegistration reads the XML file and creates a registration object from it for testing
	 * <P>
	 * @return
	 * @throws DeserializationException
	 * @throws SAXException
	 */
	private Registration getRegistration() throws DeserializationException, SAXException
	{
    	InputStream sampleIs = getClass().getResourceAsStream(sampleFile);
        InputStreamReader reader = new InputStreamReader(sampleIs);
        InputStream wsddIs = getClass().getResourceAsStream("/gov/nih/nci/ccts/grid/client/client-config.wsdd");

        Registration reg = (gov.nih.nci.ccts.grid.Registration)
        	Utils.deserializeObject(reader, gov.nih.nci.ccts.grid.Registration.class, wsddIs);
        
        return reg;
    }

}
