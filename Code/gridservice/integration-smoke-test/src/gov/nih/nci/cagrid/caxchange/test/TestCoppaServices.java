package gov.nih.nci.cagrid.caxchange.test;

import gov.nih.nci.caxchange.MessagePayload;
import gov.nih.nci.caxchange.ResponseMessage;
import gov.nih.nci.caxchange.Statuses;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.axis.message.MessageElement;
import org.apache.axis.types.URI;
import org.w3c.dom.Document;

public class TestCoppaServices extends TestCaXchangeGridService {

	private String messageType=null;
	private String payloadFileName=null;
	private String operationName=null;
	
	public TestCoppaServices() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TestCoppaServices(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	
	
    public void testCoppaService() {
        try {
          messageType = System.getProperty("message.type");
          payloadFileName =  System.getProperty("payload.file.name");
          operationName = System.getProperty("operation.name");
      	  InputStream testMessage = getResourceInputStream("/payloads/"+payloadFileName);
          message.getMetadata().setServiceType(messageType);
          if (operationName != null) {
              message.getMetadata().setOperationName(operationName);
          }
          MessagePayload messagePayload = new MessagePayload();
          messagePayload.setXmlSchemaDefinition(new URI("http://po.coppa.nci.nih.gov"));
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

       suite.addTest(new TestCoppaServices("testCoppaService"));

        return suite;
     }

     public static void main(String[] args) {
          url = System.getProperty("caxchange.url");
         
          
          junit.textui.TestRunner.run(suite());
      }	

}
