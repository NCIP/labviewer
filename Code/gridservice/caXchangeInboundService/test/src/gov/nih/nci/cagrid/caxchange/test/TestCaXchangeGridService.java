package gov.nih.nci.cagrid.caxchange.test;


import gov.nih.nci.cagrid.caxchange.client.CaXchangeRequestProcessorClient;

import gov.nih.nci.cagrid.caxchange.context.stubs.CaXchangeResponseServicePortType;
import gov.nih.nci.cagrid.caxchange.context.stubs.GetResponseRequest;
import gov.nih.nci.cagrid.caxchange.context.stubs.GetResponseResponse;
import gov.nih.nci.cagrid.caxchange.context.stubs.service.CaXchangeResponseServiceAddressingLocator;
import gov.nih.nci.cagrid.caxchange.context.stubs.types.CaXchangeResponseServiceReference;
import gov.nih.nci.cagrid.caxchange.stubs.service.CaXchangeRequestProcessorServiceAddressingLocator;
import gov.nih.nci.cagrid.common.Utils;

import gov.nih.nci.caxchange.Credentials;
import gov.nih.nci.caxchange.Message;

import gov.nih.nci.caxchange.MessagePayload;
import gov.nih.nci.caxchange.MessageTypes;
import gov.nih.nci.caxchange.Metadata;
import gov.nih.nci.caxchange.Request;
import gov.nih.nci.caxchange.Response;

import gov.nih.nci.caxchange.ResponseMessage;

import java.io.File;
import java.io.FileReader;
import java.io.InputStream;

import java.io.Reader;

import java.io.StringWriter;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import junit.framework.Test;
import junit.framework.TestCase;

import junit.framework.TestSuite;

import org.apache.axis.message.MessageElement;
import org.apache.axis.message.addressing.EndpointReferenceType;

import org.apache.axis.types.URI;

import org.w3c.dom.Document;

/**
 * Test the caXchange Grid service.
 *
 */
public class TestCaXchangeGridService extends TestCase {

    CaXchangeRequestProcessorClient client;
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    String externalIdentifier = "myExternalIdentifier";
    String userName="hmarwaha";
    String password="password";
    Message message = null;

    static String url=System.getProperty("caxchange.url");

    public TestCaXchangeGridService() {
    }

    public TestCaXchangeGridService(String name) {
        super(name);
    }

    public void setUp() {
      try {
       client = new CaXchangeRequestProcessorClient(url);
       message = new Message();
       Metadata metadata = new Metadata();
       metadata.setExternalIdentifier(externalIdentifier);
       Credentials creds = new Credentials();
       creds.setUserName(userName);
       creds.setPassword(password);
       metadata.setCredentials(creds);
       message.setMetadata(metadata);
       Request request = new Request();
       message.setRequest(request);
      }
      catch(Exception e) {
          System.out.println("Error creating the client");
          throw new RuntimeException(e);
      }
    }
    /**
     * Invoke the service with the input request message
     *
     * @throws Exception
     */
    public ResponseMessage invokeService() throws Exception {
        CaXchangeResponseServiceReference crsr = client.processRequestAsynchronously(message);
        System.out.println("Message has been send.");
        EndpointReferenceType endPointReference =crsr.getEndpointReference();
        CaXchangeResponseServiceAddressingLocator locator = new CaXchangeResponseServiceAddressingLocator();
        CaXchangeResponseServicePortType responsePort = locator.getCaXchangeResponseServicePortTypePort(endPointReference);
        boolean gotResponse=false;
        GetResponseResponse getResponse=null;
        while(!gotResponse) {
        try {
            getResponse = responsePort.getResponse(new GetResponseRequest());
            gotResponse= true;
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        }
        ResponseMessage responseMessage = getResponse.getCaXchangeResponseMessage();
        StringWriter stringWriter = new StringWriter();
        Utils.serializeObject(responseMessage, new QName("http://caXchange.nci.nih.gov/messaging", "caXchangeResponseMessage"), stringWriter);
        System.out.println(stringWriter.toString());
        return responseMessage;
    }
    /**
     * test messages of type STUDY_CREATION.
     *
     */
    public void testStudyCreation() {
      try {
        InputStream testMessage = TestCaXchangeGridService.class.getClassLoader().getResourceAsStream("studycreation.xml");
        //File testMessage = new File("studycreation.xml");
        if (testMessage == null) {
            throw new RuntimeException("Test message does not exist.");
        }
        message.getMetadata().setMessageType(MessageTypes.STUDY_CREATION);
        MessagePayload messagePayload = new MessagePayload();
        URI uri = new URI();
        uri.setPath("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain");
        messagePayload.setXmlSchemaDefinition(uri);
        DocumentBuilder db =dbf.newDocumentBuilder();
        Document payload = db.parse(testMessage);
        String namespaceURI=payload.getFirstChild().getAttributes().getNamedItem("xmlns").getNodeValue();
        MessageElement messageElement = new MessageElement(payload.getDocumentElement());
        messagePayload.set_any(new MessageElement[]{messageElement});
        message.getRequest().setBusinessMessagePayload(messagePayload);
        ResponseMessage responseMessage = invokeService();
        assertNotNull(responseMessage);
      }
      catch(Exception e) {
          System.out.println("Error sending message .");
          throw new RuntimeException(e);
      }

    }
    /**
     * Test messagess of type REGISTER_SUBJECT.
     *
     * .
     */
    public void testRegisterSubject() {
      try {
        InputStream testMessage = TestCaXchangeGridService.class.getClassLoader().getResourceAsStream("registersubject.xml");
        if (testMessage == null) {
            throw new RuntimeException("Test message does not exist.");
        }
        message.getMetadata().setMessageType(MessageTypes.REGISTER_SUBJECT);
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
      }
      catch(Exception e) {
          System.out.println("Error sending message .");
          throw new RuntimeException(e);
      }

    }

    /**
     * Test messagess of type LAB_BASED_AE.
     *
     * .
     */
    public void testAENotification() {
      try {
        InputStream testMessage = TestCaXchangeGridService.class.getClassLoader().getResourceAsStream("labbasedae.xml");
        if (testMessage == null) {
            throw new RuntimeException("Test message does not exist.");
        }
        message.getMetadata().setMessageType(MessageTypes.LAB_BASED_AE);
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
      }
      catch(Exception e) {
          System.out.println("Error sending message .");
          throw new RuntimeException(e);
      }

    }


    /**
     * Test messagess of type LOAD_LAB_TO_CDMS.
     *
     * .
     */
    public void testLoadLab() {
      try {
        InputStream testMessage = TestCaXchangeGridService.class.getClassLoader().getResourceAsStream("loadlabcdms.xml");
        if (testMessage == null) {
            throw new RuntimeException("Test message does not exist.");
        }
        message.getMetadata().setMessageType(MessageTypes.LOAD_LAB_TO_CDMS);
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
      }
      catch(Exception e) {
          System.out.println("Error sending message .");
          throw new RuntimeException(e);
      }

    }

    /**
     * Test messagess of type CT_LAB_DATA .
     *
     * .
     */
    public void testCtLabData() {
      try {
        InputStream testMessage = TestCaXchangeGridService.class.getClassLoader().getResourceAsStream("ctlabdata.xml");
        if (testMessage == null) {
            throw new RuntimeException("Test message does not exist.");
        }
        message.getMetadata().setMessageType(MessageTypes.CT_LAB_DATA);
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
      }
      catch(Exception e) {
          System.out.println("Error sending message .");
          throw new RuntimeException(e);
      }

    }

    /**
     * Test messagess of type SCHEDULE_MODIFICATION .
     *
     * .
     */
    public void testScheduleModification() {
      try {
        InputStream testMessage = TestCaXchangeGridService.class.getClassLoader().getResourceAsStream("schedulemodification.xml");
        if (testMessage == null) {
            throw new RuntimeException("Test message does not exist.");
        }
        message.getMetadata().setMessageType(MessageTypes.SCHEDULE_MODIFICATION);
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
      }
      catch(Exception e) {
          System.out.println("Error sending message .");
          throw new RuntimeException(e);
      }

    }

    public static Test suite() {
       TestSuite suite = new TestSuite();

       suite.addTest(new TestCaXchangeGridService("testStudyCreation"));
       suite.addTest(new TestCaXchangeGridService("testRegisterSubject"));
       suite.addTest(new TestCaXchangeGridService("testScheduleModification"));
       suite.addTest(new TestCaXchangeGridService("testCtLabData"));
       suite.addTest(new TestCaXchangeGridService("testLoadLab"));
       suite.addTest(new TestCaXchangeGridService("testAENotification"));
       return suite;
    }

    public static void main(String[] args) {
         url = System.getProperty("caxchange.url");
         junit.textui.TestRunner.run(suite());
     }


}
