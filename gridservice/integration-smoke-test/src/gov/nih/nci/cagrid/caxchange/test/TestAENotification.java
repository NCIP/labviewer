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
public class TestAENotification extends TestCaXchangeGridService{

	public TestAENotification(){
		super();
	}

	public TestAENotification(String name){
		super(name);
	}


	/**
     * Test messagess of type LAB_BASED_AE.
     *
     * .
     */
    public void testAENotification() {
      try {
    	  InputStream testMessage = getResourceInputStream("/payloads/LAB_BASED_AE.xml");
          message.getMetadata().setServiceType("LAB_BASED_AE");
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
        //Since lab based AE is currently an unhandled message type it should return a response of failure
        /*
        assertEquals(responseMessage.getResponse().getResponseStatus(),Statuses.FAILURE);
        assertEquals(responseMessage.getResponse().getCaXchangeError().getErrorCode(),"401");
        */
        assertNotNull(responseMessage);
        assertEquals(responseMessage.getResponse().getResponseStatus(),Statuses.SUCCESS);
      }
      catch(Exception e) {
          System.out.println("Error sending message .");
          throw new RuntimeException(e);
      }

    }

    public static Test suite() {
        TestSuite suite = new TestSuite();

       suite.addTest(new TestAENotification("testAENotification"));

        return suite;
     }

     public static void main(String[] args) {
          url = System.getProperty("caxchange.url");
          junit.textui.TestRunner.run(suite());
      }


}
