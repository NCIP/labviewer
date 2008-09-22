package gov.nih.nci.caxchange.servicemix.bean.validation;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import gov.nih.nci.caXchange.CaxchangeConstants;
import gov.nih.nci.caxchange.jdbc.CaxchangeMetadata;
import gov.nih.nci.caxchange.persistence.CaxchangeMetadataDAO;
import gov.nih.nci.caxchange.persistence.DAOFactory;
import gov.nih.nci.caxchange.servicemix.bean.metadata.CaXchangeRoutingMetadataBean;
import gov.nih.nci.caxchange.servicemix.bean.util.XPathUtil;

import javax.jbi.messaging.DeliveryChannel;
import javax.jbi.messaging.ExchangeStatus;
import javax.jbi.messaging.Fault;
import javax.jbi.messaging.MessageExchange;
import javax.jbi.messaging.MessagingException;
import javax.jbi.messaging.NormalizedMessage;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.servicemix.MessageExchangeListener;
import org.apache.servicemix.jbi.util.MessageUtil;
import org.w3c.dom.Node;

public class PayloadValidatorBean implements MessageExchangeListener {

	@javax.annotation.Resource
    private DeliveryChannel channel;
	
    private Logger logger = LogManager.getLogger(PayloadValidatorBean.class);
    private PayloadValidator payloadValidator = null;
    private GMESchemaFactory gmeSchemaFactory = null;
    private List<String> messageTypesEligibleForValidation = new ArrayList<String>();
    private String messageTypesForValidation;
    static private java.util.Map<String, CaxchangeMetadata> metadataCache = new java.util.HashMap<String, CaxchangeMetadata>(6);
    
	public String getMessageTypesForValidation() {
		return messageTypesForValidation;
	}

	public void setMessageTypesForValidation(String messageTypesForValidation) {
		this.messageTypesForValidation = messageTypesForValidation;
		StringTokenizer stringTokenizer = new StringTokenizer(messageTypesForValidation,",");
		while (stringTokenizer.hasMoreTokens()) {
			messageTypesEligibleForValidation.add(stringTokenizer.nextToken());
		}
	}

	public PayloadValidator getPayloadValidator() {
		return payloadValidator;
	}

	public void setPayloadValidator(PayloadValidator payloadValidator) {
		this.payloadValidator = payloadValidator;
	}

	public void onMessageExchange(MessageExchange exchange)
			throws MessagingException {
		if (exchange.getStatus().equals(ExchangeStatus.DONE)) {
			return;
		}
		if (exchange.getStatus().equals(ExchangeStatus.ERROR)) {
			return;
		}
        logger.info("Received exchange: " + exchange);
        NormalizedMessage in = exchange.getMessage("in");
        NormalizedMessage out = exchange.createMessage();
        MessageUtil.transfer(in,out);
        try {
           XPathUtil util = new XPathUtil();
           util.setIn(in);
           util.initialize();
           String messageType = util.getMessageType();
           if (messageTypesEligibleForValidation.contains(messageType)) {
              String namespace = getPayloadNamespace(messageType);
              Schema schema = null;
              if (gmeSchemaFactory == null) {
            	  throw new PayloadValidationException("GME schema factory not initialized for payload validation.");
              }
              if (namespace != null){
        	     schema = gmeSchemaFactory.getSchema(namespace);
              } else {
            	  throw new PayloadValidationException("Namespace not configured for this message type. Please configure the namespace to get the validating schema from GME.");
              }
              if (schema != null){
                 Node payload = util.getBusinessPayload();
        	     payloadValidator.validatePayload(payload, schema);
              }else {
            	  throw new PayloadValidationException("Schema not foung for namespace:"+namespace+"  and message type:"+messageType+" GME url:"+gmeSchemaFactory.getGMEGridServiceLocation());
              }
           }
        }catch(PayloadValidationException pve) {
            logger.error("Payload validation error.", pve);
			Fault fault = getFault("600","Invalid payload error: "+pve.getMessage(), exchange);
			exchange.setFault(fault);
			channel.send(exchange);
			return;       	
		}catch(Exception e){
            logger.error("An error occurred getting metadata.", e);
			Fault fault = getFault("601","Error occurred validating payload."+e.getMessage(), exchange);
			exchange.setFault(fault);
			channel.send(exchange);
			return;
        }
        exchange.setMessage(out, "out");
        channel.send(exchange);		

	}
	
	public String getPayloadNamespace(String messageType) throws Exception {
		CaxchangeMetadata metadata = metadataCache.get(messageType);
		if (metadata == null){
		    CaxchangeMetadataDAO metadataDAO = DAOFactory.getCaxchangeMetadataDAO();
		    metadata =metadataDAO.getMetadata(messageType);
		    metadataCache.put(messageType, metadata);
		}
		if (metadata != null) {
			return metadata.getPayloadNamespace();
		}
		return null;
	}
	
	
	public Fault getFault(String errorCode, String errorMessage, MessageExchange exchange) throws MessagingException {
		NormalizedMessage in = exchange.getMessage("in");
		Fault fault = exchange.createFault();
		MessageUtil.transfer(in, fault);
		fault.setProperty(CaxchangeConstants.ERROR_CODE,
		errorCode);
		fault.setProperty(CaxchangeConstants.ERROR_MESSAGE,
		errorMessage);
        return fault;
	}	

	public GMESchemaFactory getGmeSchemaFactory() {
		return gmeSchemaFactory;
	}

	public void setGmeSchemaFactory(GMESchemaFactory gmeSchemaFactory) {
		this.gmeSchemaFactory = gmeSchemaFactory;
	}

	public List<String> getMessageTypesEligibleForValidation() {
		return messageTypesEligibleForValidation;
	}

	public void setMessageTypesEligibleForValidation(
			List<String> messageTypesEligibleForValidation) {
		this.messageTypesEligibleForValidation = messageTypesEligibleForValidation;
	}

}
