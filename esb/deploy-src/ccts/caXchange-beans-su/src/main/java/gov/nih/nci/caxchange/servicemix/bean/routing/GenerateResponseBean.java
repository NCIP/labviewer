package gov.nih.nci.caxchange.servicemix.bean.routing;

import java.util.HashMap;
import java.util.Map;

import gov.nih.nci.caXchange.CaxchangeConstants;
import gov.nih.nci.caXchange.messaging.CaXchangeResponseMessageDocument;
import gov.nih.nci.caXchange.messaging.MessageStatuses;
import gov.nih.nci.caXchange.messaging.Response;
import gov.nih.nci.caXchange.messaging.ResponseMessage;
import gov.nih.nci.caXchange.messaging.ResponseMetadata;
import gov.nih.nci.caXchange.messaging.Statuses;
import gov.nih.nci.caXchange.messaging.TargetResponseMessage;
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
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.servicemix.MessageExchangeListener;
import org.apache.servicemix.jbi.jaxp.SourceTransformer;
import org.apache.servicemix.jbi.jaxp.StringSource;
import org.apache.servicemix.jbi.util.MessageUtil;
import org.apache.xmlbeans.XmlOptions;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
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
        	   out.setContent(response);
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
    	XmlOptions xmlOptions = new XmlOptions();
    	HashMap<String,String> ns = new HashMap<String,String>();
    	ns.put("", "http://caXchange.nci.nih.gov/messaging");
        CaXchangeResponseMessageDocument responseDocument = CaXchangeResponseMessageDocument.Factory.newInstance(xmlOptions);
        
        ResponseMessage responseMessage = responseDocument.addNewCaXchangeResponseMessage();
        ResponseMetadata responseMetaData= responseMessage.addNewResponseMetadata();
        responseMetaData.setCaXchangeIdentifier(metadata.get(CaxchangeConstants.CAXCHANGE_IDENTIFIER));
        responseMetaData.setExternalIdentifier(metadata.get(CaxchangeConstants.EXTERNAL_IDENTIFIER));
        Response response = responseMessage.addNewResponse();
        response.setResponseStatus(Statuses.SUCCESS);
        caXchangeDataUtil.buildTargetResponse(document.getFirstChild(), response);

        logger.debug(responseDocument.xmlText());
        return responseDocument.getDomNode();
    }   
    
   

    
    
}
