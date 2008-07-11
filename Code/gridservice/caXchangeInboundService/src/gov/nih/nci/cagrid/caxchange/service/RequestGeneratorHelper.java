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
    }


    public void setCaxchangeRequest(Message caxchangeRequest) {
        this.caxchangeRequest = caxchangeRequest;
    }

    public Message getCaxchangeRequest() {
        return caxchangeRequest;
    }
}
