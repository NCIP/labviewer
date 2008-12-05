package gov.nih.nci.caxchange.servicemix.bean.routing;

import java.util.Map;

import gov.nih.nci.caXchange.CaxchangeConstants;
import gov.nih.nci.caxchange.jdbc.CaxchangeMessage;
import gov.nih.nci.caxchange.persistence.CaxchangeMessageDAO;
import gov.nih.nci.caxchange.persistence.DAOFactory;
import gov.nih.nci.caxchange.servicemix.bean.CaXchangeMessagingBean;

import javax.jbi.messaging.MessageExchange;
import javax.jbi.messaging.MessagingException;
import javax.jbi.messaging.NormalizedMessage;
import javax.xml.transform.Source;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.servicemix.jbi.util.MessageUtil;

/**
 * This class generates the error response if any while processing client request
 *  
 * @author Ekagra Software Technologies
 *
 */
public class HandleErrorResponseBean extends CaXchangeMessagingBean{ 
    
    public static final String DEFAULT_ERROR_CODE= "CAXCHANGE_ERROR";
    public static final String DEFAULT_ERROR_MESSAGE="An error occurred processing request.";
    
    static Logger logger = LogManager.getLogger(HandleErrorResponseBean.class);
    /**
     * Default constructor
     */
    public HandleErrorResponseBean() {
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
        NormalizedMessage in = exchange.getMessage("in");
        NormalizedMessage out = exchange.createMessage();
        MessageUtil.transfer(in,out);
        try {
          String errorMessage = (String)in.getProperty(CaxchangeConstants.ERROR_MESSAGE);
          String errorCode = (String)in.getProperty(CaxchangeConstants.ERROR_CODE);
          if ((errorMessage != null)||(errorCode != null)) {
             Source response = getErrorResponse(exchange);
             out.setContent(response);            
          }
        }catch(Exception e){
            logger.error("An error occurred sending error response to the queue.", e);
            throw new MessagingException("An error occurred sending error response to the queue.", e);
        }
        exchange.setMessage(out, "out");
        channel.send(exchange);
        deleteMessage(exchange);
    }
    /**
	 * This method gives the error response with the corresponding error message and error code
	 * in case of errors
	 * @param exchange
	 * @return response
	 * @throws Exception
	 */	
    
    public Source getErrorResponse(MessageExchange exchange) throws Exception {
        NormalizedMessage in = exchange.getMessage("in");
        String errCode =  (String)in.getProperty(CaxchangeConstants.ERROR_CODE);
        String errMessage =  (String)in.getProperty(CaxchangeConstants.ERROR_MESSAGE);
        Map<String,String> metadata = (Map<String,String>)in.getProperty(CaxchangeConstants.REQUEST_METADATA);
        errCode = (errCode==null)?DEFAULT_ERROR_CODE:errCode;
        errMessage = (errMessage==null)?DEFAULT_ERROR_MESSAGE:errMessage;
        Source response = caXchangeDataUtil.generateResponseFromErroredRequest(errCode, errMessage, metadata);
        return response;        
    }
    /**
	 * This method gives deletes the original message after the response is sent form MySql database
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
    
    
}
