package gov.nih.nci.cagrid.caxchange.test;

import gov.nih.nci.caxchange.MessagePayload;
import gov.nih.nci.caxchange.ResponseMessage;
import gov.nih.nci.caxchange.Statuses;

import java.io.InputStream;
import java.util.StringTokenizer;

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
	
	public MessagePayload getMessagePayloadForGetByIds(String payloadFiles) throws Exception{
	   try {
		MessagePayload messagePayload = new MessagePayload();
        messagePayload.setXmlSchemaDefinition(new URI("http://po.coppa.nci.nih.gov"));
        StringTokenizer st = new StringTokenizer(payloadFiles,",");
        MessageElement[] payloads = new MessageElement[st.countTokens()];
        int i=0;
        System.out.println("Payloads:"+payloadFiles);
        while (st.hasMoreTokens()){
        	String nextFile = st.nextToken();
        	System.out.println("Adding file:"+nextFile);
        	InputStream testMessage = getResourceInputStream("/payloads/"+nextFile);
            DocumentBuilder db =dbf.newDocumentBuilder();
            Document payload = db.parse(testMessage);
            MessageElement messageElement = new MessageElement(payload.getDocumentElement());
            payloads[i++] = messageElement;
        }
        messagePayload.set_any(payloads);
        return messagePayload;
	   } catch(Exception e) {
       	 throw e;       
       }
	}
	
	
    public void testCoppaService() {
        try {
          messageType = System.getProperty("message.type");
          payloadFileName =  System.getProperty("payload.file.name");
          operationName = System.getProperty("operation.name");
          message.getMetadata().setServiceType(messageType);
          if ((operationName != null)&&(!(operationName.equals("${operation.name}")))) {
              message.getMetadata().setOperationName(operationName);
          }
          MessagePayload messagePayload = null;
          if (operationName.equals("getByIds")) {
        	  messagePayload = getMessagePayloadForGetByIds(payloadFileName);
          }else {
        	  messagePayload = new MessagePayload();
              messagePayload.setXmlSchemaDefinition(new URI("http://po.coppa.nci.nih.gov"));
          	  InputStream testMessage = getResourceInputStream("/payloads/"+payloadFileName);
              DocumentBuilder db =dbf.newDocumentBuilder();
              Document payload = db.parse(testMessage);
              MessageElement messageElement = new MessageElement(payload.getDocumentElement());
              messagePayload.set_any(new MessageElement[]{messageElement});
          }
          
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
