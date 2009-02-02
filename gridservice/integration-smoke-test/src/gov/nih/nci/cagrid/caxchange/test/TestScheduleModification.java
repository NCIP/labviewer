package gov.nih.nci.cagrid.caxchange.test;
import gov.nih.nci.caxchange.MessagePayload;
import gov.nih.nci.caxchange.MessageTypes;
import gov.nih.nci.caxchange.ResponseMessage;
import gov.nih.nci.caxchange.Statuses;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;

import org.apache.axis.message.MessageElement;
import org.apache.axis.types.URI;
import org.springframework.core.io.ClassPathResource;
import org.w3c.dom.Document;

import junit.framework.Test;
import junit.framework.TestSuite;
public class TestScheduleModification extends TestCaXchangeGridService{

	public TestScheduleModification(){
		super();
	}
	public TestScheduleModification(String name){
		super(name);
	}

	  /**
     * Test messagess of type SCHEDULE_MODIFICATION .
     *
     * .
     */
    public void testScheduleModification() {
      try {
    	  InputStream testMessage = getResourceInputStream("/payloads/SCHEDULE_MODIFICATION.xml");
          message.getMetadata().setServiceType("SCHEDULE_MODIFICATION");
        MessagePayload messagePayload = new MessagePayload();
        URI uri = new URI();
        uri.setPath("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain");
        messagePayload.setXmlSchemaDefinition(uri);
        DocumentBuilder db =dbf.newDocumentBuilder();
        Document payload = db.parse(testMessage);
        MessageElement messageElement = new MessageElement(payload.getDocumentElement());
        messagePayload.set_any(new MessageElement[]{messageElement});
        message.getRequest().setBusinessMessagePayload(messagePayload);
        ResponseMessage responseMessage = invokeService();
        assertNotNull(responseMessage);
        assertEquals(responseMessage.getResponse().getResponseStatus(),Statuses.SUCCESS);
      }
      catch(Exception e) {
          System.out.println("Error sending message .");
          e.printStackTrace();
          fail();
      }

    }


    public static Test suite() {
        TestSuite suite = new TestSuite();

       suite.addTest(new TestScheduleModification("testScheduleModification"));

        return suite;
     }

     public static void main(String[] args) {
          url = System.getProperty("caxchange.url");
          junit.textui.TestRunner.run(suite());
      }
}
