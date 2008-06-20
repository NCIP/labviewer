package gov.nih.nci.cagrid.caxchange.service;

import gov.nih.nci.caXchange.messaging.CaXchangeRequestMessageDocument;
import gov.nih.nci.caXchange.messaging.Credentials;
import gov.nih.nci.caXchange.messaging.MessagePayload;
import gov.nih.nci.caXchange.messaging.Metadata;
import gov.nih.nci.caXchange.messaging.Request;
import gov.nih.nci.cagrid.common.Utils;
import gov.nih.nci.caxchange.Message;
import gov.nih.nci.caxchange.MessageTypes;

import java.io.StringWriter;

import javax.xml.namespace.QName;

import org.apache.axis.message.MessageElement;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class RequestGeneratorHelper {
    private gov.nih.nci.caxchange.Message caxchangeRequest;
    public RequestGeneratorHelper() {
    }
    
    public String getRequestForCaxchange() throws Exception {
        StringWriter stringWriter = new StringWriter();
        QName requestQName =new QName("http://caXchange.nci.nih.gov/messaging", "caXchangeRequestMessage");
        Utils.serializeObject(caxchangeRequest,requestQName, stringWriter);
        return stringWriter.toString();
        /*
        CaXchangeRequestMessageDocument document = CaXchangeRequestMessageDocument.Factory.newInstance();
        gov.nih.nci.caXchange.messaging.Message message = document.addNewCaXchangeRequestMessage();
        addMetaData(message);
        addRequest(message);
        return document.toString();
        */
    }
    
    public Metadata addMetaData(gov.nih.nci.caXchange.messaging.Message message) {
        gov.nih.nci.caxchange.Metadata sourceMetaData = caxchangeRequest.getMetadata();
        Metadata metaData=null;
        if (sourceMetaData != null) {
            metaData = message.addNewMetadata();
            metaData.setCaXchangeIdentifier(sourceMetaData.getCaXchangeIdentifier());
            metaData.setExternalIdentifier(sourceMetaData.getExternalIdentifier());
            if (sourceMetaData.getMessageType().equals(MessageTypes.REGISTER_SUBJECT)) {
               metaData.setMessageType(gov.nih.nci.caXchange.messaging.MessageTypes.REGISTER_SUBJECT);
            }else if (sourceMetaData.getMessageType().equals(MessageTypes.LAB_BASED_AE)) {
                metaData.setMessageType(gov.nih.nci.caXchange.messaging.MessageTypes.LAB_BASED_AE);
            }else if (sourceMetaData.getMessageType().equals(MessageTypes.SCHEDULE_MODIFICATION)) {
                metaData.setMessageType(gov.nih.nci.caXchange.messaging.MessageTypes.SCHEDULE_MODIFICATION);
            }else if (sourceMetaData.getMessageType().equals(MessageTypes.STUDY_CREATION)) {
                metaData.setMessageType(gov.nih.nci.caXchange.messaging.MessageTypes.STUDY_CREATION);
            }else if (sourceMetaData.getMessageType().equals(MessageTypes.CT_LAB_DATA)) {
                metaData.setMessageType(gov.nih.nci.caXchange.messaging.MessageTypes.CT_LAB_DATA);
            }else if (sourceMetaData.getMessageType().equals(MessageTypes.LOAD_LAB_TO_CDMS)) {
                metaData.setMessageType(gov.nih.nci.caXchange.messaging.MessageTypes.LOAD_LAB_TO_CDMS);
            }
            addCredentials(metaData);
        }
        return metaData;
        
    }
    
    public Credentials addCredentials(Metadata metaData) {
            gov.nih.nci.caxchange.Credentials sourceCredentials = caxchangeRequest.getMetadata().getCredentials();
            Credentials credentials=null;
            if (sourceCredentials != null) {
                credentials = metaData.addNewCredentials();
                credentials.setGridIdentifier(sourceCredentials.getGridIdentifier());
                credentials.setGroupName(sourceCredentials.getGroupName());
                credentials.setPassword(sourceCredentials.getPassword());
                credentials.setUserName(sourceCredentials.getUserName());
            }
            return credentials;
    }
    
    public Request addRequest(gov.nih.nci.caXchange.messaging.Message message) {
        gov.nih.nci.caxchange.Request sourceRequest = caxchangeRequest.getRequest();
        Request request = null;
        if (sourceRequest != null) {
            request = message.addNewRequest();
            addBusinessMessagePayload(request);
        }
        return request;
    }
    
    public MessagePayload addBusinessMessagePayload(Request request) {
        gov.nih.nci.caxchange.MessagePayload sourceMessagePayload = caxchangeRequest.getRequest().getBusinessMessagePayload();
        MessagePayload messagePayload=null;
        if (sourceMessagePayload != null) {
            messagePayload = request.addNewBusinessMessagePayload();
            messagePayload.setXmlSchemaDefinition(sourceMessagePayload.getXmlSchemaDefinition().toString());
            org.apache.axis.message.MessageElement[] mes = sourceMessagePayload.get_any();
            Node node =messagePayload.getDomNode();
            Document doc = node.getOwnerDocument();
            for(org.apache.axis.message.MessageElement me:mes) {
                Node importedNode = doc.importNode(me, true);
                node.appendChild(importedNode);
            }
        }
        return messagePayload;
    }
    

    public void setCaxchangeRequest(Message caxchangeRequest) {
        this.caxchangeRequest = caxchangeRequest;
    }

    public Message getCaxchangeRequest() {
        return caxchangeRequest;
    }
}
