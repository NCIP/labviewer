package gov.nih.nci.cagrid.caxchange.listener;


import gov.nih.nci.caxchange.Response;
import gov.nih.nci.caxchange.ResponseMessage;
import gov.nih.nci.caxchange.Statuses;
import gov.nih.nci.cagrid.caxchange.context.service.globus.resource.CaXchangeResponseServiceResourceHome;

import gov.nih.nci.cagrid.caxchange.context.service.globus.resource.CaXchangeResponseServiceResource;

import gov.nih.nci.cagrid.caxchange.context.common.CaXchangeResponseServiceConstants;

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
	CaXchangeResponseServiceResourceHome resourceHome;
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
           logger.debug("Got the message.."+textMessage.getText());
           ResponseHandler responseHandler = new ResponseHandler();
           responseHandler.setResponseText(textMessage.getText());
           ResponseMessage responseMessage = responseHandler.getResponse();
           String caXchangeIdentifier = responseMessage.getResponseMetadata().getCaXchangeIdentifier();
           logger.info("Got response for caXchangeIdentifier."+caXchangeIdentifier);
           ResourceKey resourceKey = new SimpleResourceKey(resourceHome.getKeyTypeName(), caXchangeIdentifier);
           logger.debug("ressourceKey created "+resourceKey.getName()+" "+resourceKey.getValue());
             CaXchangeResponseServiceResource resource = resourceHome.getResource(resourceKey);
             if (resource != null) {
                 resource.setCaXchangeResponseMessage(responseMessage);
                 logger.info("Updated the Resource with the response for :"+caXchangeIdentifier);
             }
       }
       catch(Exception e) {
           logger.error("Error occurred getting response and setting to the resource ",e);
           throw new RuntimeException(e);
       }
    }

    public void setResourceHome(CaXchangeResponseServiceResourceHome resourceHome) {
        this.resourceHome = resourceHome;
    }

    public CaXchangeResponseServiceResourceHome getResourceHome() {
        return resourceHome;
    }
}
