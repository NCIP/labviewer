package gov.nih.nci.cagrid.caxchange.test;

import gov.nih.nci.caxchange.MessagePayload;
import gov.nih.nci.caxchange.ResponseMessage;
import gov.nih.nci.caxchange.Statuses;

import java.io.InputStream;
import java.net.URI;

import javax.xml.parsers.DocumentBuilder;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.axis.message.MessageElement;
import org.w3c.dom.Document;

public class TestNewMessageType extends TestCaXchangeGridService {

	private String messageType=null;
	private String payloadFileName=null;
	
	public TestNewMessageType() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TestNewMessageType(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	
    public void testNewMessageType() {
        try {
        	 messageType = System.getProperty("message.type");
        	 payloadFileName =  System.getProperty("payload.file.name");
      	  InputStream testMessage = getResourceInputStream("/payloads/"+payloadFileName);
            message.getMetadata().setMessageType(messageType);
          MessagePayload messagePayload = new MessagePayload();
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
            throw new RuntimeException(e);
        }

      }	
	
	
    public static Test suite() {
        TestSuite suite = new TestSuite();

       suite.addTest(new TestNewMessageType("testNewMessageType"));

        return suite;
     }

     public static void main(String[] args) {
          url = System.getProperty("caxchange.url");
         
          
          junit.textui.TestRunner.run(suite());
      }	

}