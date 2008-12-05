package gov.nih.nci.caxchange.servicemix.bean.routing;

import java.util.Map;

import gov.nih.nci.caXchange.CaxchangeConstants;
import gov.nih.nci.caXchange.messaging.CaXchangeResponseMessageDocument;
import gov.nih.nci.caXchange.messaging.MessageStatuses;
import gov.nih.nci.caXchange.messaging.Response;
import gov.nih.nci.caXchange.messaging.ResponseMessage;
import gov.nih.nci.caXchange.messaging.ResponseMetadata;
import gov.nih.nci.caXchange.messaging.Statuses;
import gov.nih.nci.caxchange.jdbc.CaxchangeMessage;
import gov.nih.nci.caxchange.persistence.CaxchangeMessageDAO;
import gov.nih.nci.caxchange.persistence.DAOFactory;
import gov.nih.nci.caxchange.servicemix.bean.CaXchangeMessagingBean;
import gov.nih.nci.caxchange.servicemix.bean.util.CaXchangeDataUtil;

import javax.annotation.Resource;
import javax.jbi.messaging.DeliveryChannel;
import javax.jbi.messaging.ExchangeStatus;
import javax.jbi.messaging.MessageExchange;
import javax.jbi.messaging.MessagingException;
import javax.jbi.messaging.NormalizedMessage;
import javax.jbi.messaging.RobustInOnly;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.servicemix.MessageExchangeListener;
import org.apache.servicemix.jbi.jaxp.SourceTransformer;
import org.apache.servicemix.jbi.util.MessageUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
	/**
	 * This class generates the caXchange response forwards it to the JMS queue. 
	 * for the client to pick up the response
	 *  
	 * @author Ekagra Software Technologies
	 *
	 */
public class GenerateResponseBean extends CaXchangeMessagingBean { 
    @Resource
    private DeliveryChannel channel;
    
    static Logger logger = LogManager.getLogger(GenerateResponseBean.class);
    /**
     * Default constructor
     */
    public GenerateResponseBean() {
    }
    
    /**
	 * When the POJO Implements the MessageExchangeListener interface of servicemix-bean component
	 * all the exchange will be dispatched to the onMessageExchange() method
	 * @param exchange
	 * @return
	 * @throws MessagingException
	 */
    
    public void processMessageExchange(MessageExchange exchange) throws MessagingException {
        logger.debug("Received exchange: " + exchange);
        try {
        	NormalizedMessage in = exchange.getMessage("in");
        	Source inSource = in.getContent();
        	Document document = new SourceTransformer().toDOMDocument(inSource);
        	if (isTargetResponse(document)) {
        	   Map<String, String> metadata = (Map<String,String>)in.getProperty(CaxchangeConstants.REQUEST_METADATA);
        	   Node caXchangeResponse = generateResponseFromTargetResponse(metadata,document);
        	   Source response = new DOMSource(caXchangeResponse);
        	   NormalizedMessage out = exchange.createMessage();
     	       MessageUtil.transfer(in,out);
     	      copyPropertiesAndAttachments(in,out);
        	   out.setContent(response);
        	   exchange.setMessage(out, "out");
        	}else {
         	   NormalizedMessage out = exchange.createMessage();
     	       MessageUtil.transfer(in,out);  
       	       copyPropertiesAndAttachments(in,out);
     	       exchange.setMessage(out, "out");
        	}
        	channel.send(exchange);
        }catch(Exception e){
            logger.error("An error occurred generating caxchange response.", e);
            throw new MessagingException("An error occurred generating caxchange response.", e);
        }
    }
    
    public boolean isCaXchangeResponse(Document document) throws Exception {
    	String localName = document.getDocumentElement().getNodeName();
    	if (localName.equals("caXchangeResponseMessage")) {
    		return true;
    	}
    	return false;
    }
    
    public boolean isTargetResponse(Document document) throws Exception {
    	String localName = document.getDocumentElement().getNodeName();
    	logger.debug(localName);
    	if (localName.equals("targetResponse")) {
    		return true;
    	}
    	return false;
    }
        
    
    public Node generateResponseFromTargetResponse(Map<String, String> metadata, Document document) throws Exception {
        CaXchangeResponseMessageDocument responseDocument = CaXchangeResponseMessageDocument.Factory.newInstance();
        ResponseMessage responseMessage = responseDocument.addNewCaXchangeResponseMessage();
        ResponseMetadata responseMetaData= responseMessage.addNewResponseMetadata();
        responseMetaData.setCaXchangeIdentifier(metadata.get(CaxchangeConstants.CAXCHANGE_IDENTIFIER));
        responseMetaData.setExternalIdentifier(metadata.get(CaxchangeConstants.EXTERNAL_IDENTIFIER));
        Response response = responseMessage.addNewResponse();
        response.setResponseStatus(Statuses.SUCCESS);
        XPath xpath = XPathFactory.newInstance().newXPath();
        XPathExpression msExp = xpath.compile("/targetResponse/targetMessageStatus");        
        String targetMessageStatus = (String)msExp.evaluate(document, XPathConstants.STRING);
        if ((MessageStatuses.FAULT.toString().equals(targetMessageStatus))||
        	(MessageStatuses.ERROR.toString().equals(targetMessageStatus))) {
        	response.setResponseStatus(Statuses.FAILURE);
        }
        Document responseOwnerDocument = response.getDomNode().getOwnerDocument();
        Node importedDocument  = responseOwnerDocument.importNode(document.getFirstChild(), true);
        response.getDomNode().appendChild(importedDocument);
        
        return responseDocument.getDomNode();
    }    

    
    
}
