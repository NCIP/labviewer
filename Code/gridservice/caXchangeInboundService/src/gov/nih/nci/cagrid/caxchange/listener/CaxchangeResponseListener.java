package gov.nih.nci.cagrid.caxchange.listener;


import gov.nih.nci.caxchange.Response;
import gov.nih.nci.caxchange.ResponseMessage;
import gov.nih.nci.caxchange.Statuses;
import gov.nih.nci.cagrid.caxchange.context.service.globus.resource.BaseResourceHome;

import gov.nih.nci.cagrid.caxchange.context.service.globus.resource.CaXchangeResponseServiceResource;

import gov.nih.nci.cagrid.caxchange.context.service.globus.resource.ResourceConstants;

import java.io.StringReader;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import javax.xml.namespace.QName;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Transformer;

import javax.xml.transform.TransformerFactory;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.axis.encoding.DeserializationContext;
import org.apache.axis.encoding.Deserializer;

import org.apache.axis.encoding.ser.BeanDeserializer;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import org.globus.wsrf.ResourceKey;
import org.globus.wsrf.impl.SimpleResourceKey;

import org.xml.sax.InputSource;

public class CaxchangeResponseListener implements MessageListener {
    BaseResourceHome resourceHome;
    Logger logger = LogManager.getLogger(CaxchangeResponseListener.class);

    String testResponse="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
    "<ns1:caXchangeResponseMessage xmlns:ns1=\"http://caXchange.nci.nih.gov/messaging\">\n" +
    "  <ns1:responseMetaData >\n" +
    "    <externalIdentifier>REGISTER_SUBJECTmyExternalIdentifiercafb12d0-8ae8-11dc-9ed7-c7f4bfd99e68hmarwahapassword</externalIdentifier>\n" +
    "    <caXchangeIdentifier>REGISTER_SUBJECTmyExternalIdentifiercafb12d0-8ae8-11dc-9ed7-c7f4bfd99e68hmarwahapassword</caXchangeIdentifier>\n" +
    "  </ns1:responseMetaData>\n" +
    "  <ns1:response>\n" +
    "    <ns1:responseStatus>FAILURE</ns1:responseStatus>\n" +
    "    <ns1:caXchangeError>\n" +
    "      <errorCode>DELEGATION_SERVICE_ERROR</errorCode>\n" +
    "      <errorDescription>Error occurred calling the delegation service.</errorDescription>\n" +
    "    </ns1:caXchangeError>\n" +
    "  </ns1:response>\n" +
    "</ns1:caXchangeResponseMessage>";

    public CaxchangeResponseListener() {
    }

    public void onMessage(Message message) {
       try {
           TextMessage textMessage = (TextMessage)message;
           Transformer tr = TransformerFactory.newInstance().newTransformer();
           StreamSource ss = new StreamSource(new StringReader(textMessage.getText()));
           tr.transform(ss, new StreamResult(System.out));
           System.out.println("Got the message.."+textMessage.getText());
           try {
             ResponseHandler responseHandler = new ResponseHandler();
             responseHandler.setResponseText(textMessage.getText());
             ResponseMessage responseMessage = responseHandler.getResponse();
             String caXchangeIdentifier = responseMessage.getResponseMetadata().getCaXchangeIdentifier();
             logger.info("Got response for caXchangeIdentifier."+caXchangeIdentifier);
             ResourceKey resourceKey = new SimpleResourceKey(resourceHome.getKeyTypeName(), caXchangeIdentifier);
             System.out.println("ressourceKey created "+resourceKey.getName()+" "+resourceKey.getValue());
             CaXchangeResponseServiceResource resource = resourceHome.getResource(resourceKey);
             if (resource != null) {
                 resource.setCaXchangeResponseMessageValue(responseMessage);
                 System.out.println("Updated the Resource with the response.");
             }
           }
           catch (Exception e) {
               logger.error("Error parsing response ",e);

           }
        /*
           ResponseMessage responseMessage = new ResponseMessage();
           Response response = new Response();
           response.setResponseStatus(Statuses.SUCCESS);
           responseMessage.setResponse(response);
           String id=textMessage.getText();
           ResourceKey resourceKey = new SimpleResourceKey(resourceKeyQName, id);
           CaxchangeContextResource currentResource = resourceHome.getResource(resourceKey);
           currentResource.setCaXchangeResponseMessageValue(responseMessage);
           System.out.println("Resource has been updated."); */
       }
       catch(Exception e) {
           e.printStackTrace();
       }
    }

    public void setResourceHome(BaseResourceHome resourceHome) {
        this.resourceHome = resourceHome;
    }

    public BaseResourceHome getResourceHome() {
        return resourceHome;
    }
}
