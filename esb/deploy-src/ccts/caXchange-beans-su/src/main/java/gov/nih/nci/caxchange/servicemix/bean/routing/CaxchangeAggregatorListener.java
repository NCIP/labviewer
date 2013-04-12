/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */
package gov.nih.nci.caxchange.servicemix.bean.routing;
import gov.nih.nci.caXchange.CaxchangeConstants;
import gov.nih.nci.caXchange.messaging.CaXchangeRequestMessageDocument;
import gov.nih.nci.caXchange.messaging.CaXchangeResponseMessageDocument;
import gov.nih.nci.caXchange.messaging.Statuses;
import gov.nih.nci.caxchange.jdbc.CaxchangeMessage;
import gov.nih.nci.caxchange.persistence.CaxchangeMessageDAO;
import gov.nih.nci.caxchange.persistence.DAOFactory;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.jbi.messaging.DeliveryChannel;
import javax.jbi.messaging.ExchangeStatus;
import javax.jbi.messaging.InOnly;
import javax.jbi.messaging.MessageExchange;
import javax.jbi.messaging.MessagingException;
import javax.jbi.messaging.NormalizedMessage;
import javax.jbi.messaging.RobustInOnly;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.servicemix.MessageExchangeListener;
import org.apache.servicemix.jbi.jaxp.StringSource;

import gov.nih.nci.caxchange.servicemix.bean.util.*;
	/**
	 * Listens to the aggregated response from EIP aggregator and creates 
	 * a response message for the response queue. Optionally if one of the 
	 * requests have failed it sends a rollback if the input message type is
	 * eligible for rollback.
	 * 
	 *  
	 * @author Ekagra Software Technologies
	 *
	 */
public class CaxchangeAggregatorListener  implements MessageExchangeListener {

    @Resource
    private DeliveryChannel channel;

    static Logger logger = LogManager.getLogger(CaxchangeAggregatorListener.class);
    List<String> messageTypesEligibleForRollback = new ArrayList<String>();
    /**
     * Default constructor
     */
    public CaxchangeAggregatorListener() {
    }
	/**
	 * When the POJO Implements the MessageExchangeListener interface of servicemix-bean component
	 * all the exchange will be dispatched to the onMessageExchange() method
	 * @param messageExchange
	 * @return
	 * @throws MessagingException
	 */
    public void onMessageExchange(MessageExchange messageExchange) throws MessagingException{
        String correlationID=null;
        try {
            if (messageExchange.getStatus().equals(ExchangeStatus.DONE)) {
                return;
            }
            if (messageExchange.getStatus().equals(ExchangeStatus.ERROR)) {
                throw new MessagingException("An error occurred in the aggregator listener.",messageExchange.getError());
            }
            NormalizedMessage in = messageExchange.getMessage("in");
			logger.debug("Aggregated message :" +messageExchange);
            correlationID= (String)in.getProperty(CaxchangeConstants.ORIGINAL_EXCHANGE_CORRELATIONID);
            Source originalSource = getOriginalMessage(correlationID);
            XPathUtil originalUtil = new XPathUtil();
            NormalizedMessage original = messageExchange.createMessage();
            original.setContent(originalSource);
            originalUtil.setIn(original);
            originalUtil.initialize();
            String messageType = originalUtil.getMessageType();
            Source aggregatedResponse = in.getContent();
            CaXchangeResponseMessageDocument responseDocument = originalUtil.generateResponseFromAggregatedResponse(aggregatedResponse);
            if (isRollbackRequired(messageType, responseDocument)) {
                logger.info("Sending a rollback for "+correlationID);
                Source rollback = originalUtil.getRollbackMessage();
                sendRollback(rollback);
            }else {
                logger.info("No rollback required for "+correlationID);
            }
            Source response = new DOMSource(responseDocument.getDomNode());
            sendResponse(response);
            messageExchange.setStatus(ExchangeStatus.DONE);
            
            
            logger.debug("caXchange aggregated responseDocument: "+responseDocument);
            
            logger.debug("caXchange aggregatedResponse: "+aggregatedResponse);
    		
    		
            
            
            
            channel.send(messageExchange);
            if (!(messageExchange.getStatus().equals(ExchangeStatus.ERROR))) {
               deleteMessage(messageExchange);
            }
        }catch (Exception e) {
            logger.error("Error occurred sending response for "+correlationID, e);
            throw new MessagingException("Error occurred sending response for "+correlationID, e);
        }
    }
    /**
     * Get the original message stored in the database.
     * @param correlationID
     * @return 
     * @throws Exception
     */
    public Source getOriginalMessage(String correlationID) throws Exception {
        CaxchangeMessageDAO caxchangeMessageDAO = DAOFactory.getCaxchangeMessageDAO();
        CaxchangeMessage message =caxchangeMessageDAO.getMessage(correlationID);
        if (message == null) {
            throw new Exception("Original message not found for "+correlationID);
        }
        String originalMessage = message.getMessage();
        logger.debug("original message "+originalMessage);
        StringSource source = new StringSource(originalMessage); 
        CaXchangeRequestMessageDocument orgDoc= CaXchangeRequestMessageDocument.Factory.parse(originalMessage);

        return source;
    }
    /**
     * Verify if a rollback is required. A rollback is required if the request has
     * a FAILURE or ERROR response from one of the target services and the MESSAGE_TYPE
     * is one of REGISTER_SUBJECT or STUDY_CREATION.
     * 
     * @param messageType
     * @param responseDocument
     * @return rollbackRequired
     * @throws Exception
     */
    public boolean isRollbackRequired(String messageType, CaXchangeResponseMessageDocument responseDocument)throws Exception {
       boolean rollbackRequired = false;
       Statuses.Enum status= responseDocument.getCaXchangeResponseMessage().getResponse().getResponseStatus();
       if (Statuses.FAILURE.equals(status)) {
           logger.debug("Response has failed.");
           logger.debug("Verifying the message type "+ messageType);
           if (messageTypesEligibleForRollback.contains(messageType)) {
               return true;
           }
       }
	   logger.debug("No rollback required");
       return rollbackRequired;
    }
   /**
    * Send a roll back for the current request. set the operation to roll back and
    * route the message.
    * @param rollback
    * @return
    * @throws MessagingException
    */
   public void sendRollback(Source rollback) throws MessagingException {
       InOnly inOnly =channel.createExchangeFactory().createInOnlyExchange();
       NormalizedMessage rollbackMessage= inOnly.createMessage();
       rollbackMessage.setContent(rollback);
       inOnly.setInMessage(rollbackMessage);
       inOnly.setService(CaxchangeConstants.ROLLBACK_PIPELINE);
       channel.send(inOnly);
   }

    /**
     * Send the caXchange response back to the response queue.
     * @param response
     * @return
     * @throws MessagingException
     */
    public void sendResponse(Source response) throws MessagingException {
       RobustInOnly robustInOnly =channel.createExchangeFactory().createRobustInOnlyExchange();
       NormalizedMessage resp= robustInOnly.createMessage();
       resp.setContent(response);
       robustInOnly.setInMessage(resp);
       robustInOnly.setService(CaxchangeConstants.RESPONSE_QUEUE);
	   logger.info("Sending response:"+robustInOnly);
       channel.sendSync(robustInOnly, 1000);
    }
    /**
     * delete the original message in the database.
     * @param exchange
     * @return
     * @throws
     */
    public void deleteMessage(MessageExchange exchange)  {
      try {
        CaxchangeMessageDAO caxchangeMessageDAO = DAOFactory.getCaxchangeMessageDAO();
        String correlationId = (String)exchange.getProperty(CaxchangeConstants.EXCHANGE_CORRELATIONID);
        CaxchangeMessage caxchangeMessage = new CaxchangeMessage();
        caxchangeMessage.setMessageId(correlationId);
        caxchangeMessageDAO.deleteMessage(caxchangeMessage);
      }catch(Exception e) {
        logger.error("Error deleting original message.",e);
      }
    }
    /**
     * Sets the message types eligible for roll back.
     * @param messageTypesEligibleForRollback
     * @return
     * @throws
     */
    public void setMessageTypesEligibleForRollback(List<String> messageTypesEligibleForRollback) {
        this.messageTypesEligibleForRollback = messageTypesEligibleForRollback;
    }
    /**
     *Gets the message types eligible for rollback
     * @param 
     * @return messageTypesEligibleForRollback
     * @throws
     */
    public List<String> getMessageTypesEligibleForRollback() {
        return messageTypesEligibleForRollback;
    }
}
