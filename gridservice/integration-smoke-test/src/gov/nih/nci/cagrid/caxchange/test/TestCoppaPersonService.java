package gov.nih.nci.cagrid.caxchange.test;

import gov.nih.nci.caxchange.MessagePayload;
import gov.nih.nci.caxchange.ResponseMessage;
import gov.nih.nci.caxchange.Statuses;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import org.apache.axis.message.MessageElement;
import org.apache.axis.types.URI;

import org.w3c.dom.Document;

public class TestCoppaPersonService extends TestCoppaServices {

	
	public void testGetPerson() {
	      try {
	    	InputStream testMessage = getResourceInputStream("/payloads/PERSON_II.xml");
	        message.getMetadata().setServiceType("PERSON");
	        MessagePayload messagePayload = new MessagePayload();
	        URI uri = new URI();
	        uri.setPath("http://coppa.nci.nih.gov");
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
	    	  fail();
	    	  e.printStackTrace();
	      }		
	}
	
	public TestCoppaPersonService() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TestCoppaPersonService(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	
	
	

}
