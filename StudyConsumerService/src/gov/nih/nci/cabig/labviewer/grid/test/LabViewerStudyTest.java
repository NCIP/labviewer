package gov.nih.nci.cabig.labviewer.grid.test;

import java.io.InputStream;
import java.io.InputStreamReader; 

import org.globus.wsrf.encoding.DeserializationException;
import org.xml.sax.SAXException;

import gov.nih.nci.cagrid.common.Utils;
import gov.nih.nci.ccts.grid.Study;
import gov.nih.nci.ccts.grid.client.StudyConsumerClient;

/**
 * Test class to test the Study Consumer for lab viewer
 * <P>
 * @author Michael Holck
 */
public class LabViewerStudyTest
{
	//String serviceUrl = "http://localhost:8080/wsrf/services/cagrid/StudyConsumer";
	//String serviceUrl = "http://cbiovqa5010.nci.nih.gov:28080/wsrf/services/cagrid/StudyConsumer";
	String serviceUrl = "http://cbvapp-d1017.nci.nih.gov:18080/ctom-wsrf/services/cagrid/StudyConsumer";
	String sampleFile = "/SampleStudyMessage.xml";
	
	/**
	 * main method just creates this class and calls the test method which performs all
	 * the work
	 * <P>
	 * @param args not needed
	 */
	public static void main(String[] args)
	{
		LabViewerStudyTest tester = new LabViewerStudyTest();
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
			System.out.println("Calling study service at " + serviceUrl);
			
			// Create the client
			StudyConsumerClient client = new StudyConsumerClient(this.serviceUrl);
			
			// Create the Study object
			Study study = getStudy();
			
			// Call the service
			client.createStudy(study);
			//client.rollback(study);
			
			System.out.println("Returned from calling service");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * getStudy reads the XML file and creates a study object from it for testing
	 * <P>
	 * @return
	 * @throws DeserializationException
	 * @throws SAXException
	 */
	private Study getStudy() throws DeserializationException, SAXException
	{
    	InputStream sampleIs = getClass().getResourceAsStream(sampleFile);
        InputStreamReader reader = new InputStreamReader(sampleIs);
        InputStream wsddIs = getClass().getResourceAsStream("/gov/nih/nci/ccts/grid/client/client-config.wsdd");

        Study study = (gov.nih.nci.ccts.grid.Study)
        	Utils.deserializeObject(reader, gov.nih.nci.ccts.grid.Study.class, wsddIs);
        
        return study;
    }

}
