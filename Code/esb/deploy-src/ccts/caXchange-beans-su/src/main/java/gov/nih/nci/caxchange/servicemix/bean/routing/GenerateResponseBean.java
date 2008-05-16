package gov.nih.nci.caxchange.servicemix.bean.routing;

import gov.nih.nci.caXchange.CaxchangeConstants;
import gov.nih.nci.caxchange.servicemix.bean.util.*;
import gov.nih.nci.caxchange.jdbc.CaxchangeMessage;
import gov.nih.nci.caxchange.persistence.CaxchangeMessageDAO;
import gov.nih.nci.caxchange.persistence.DAOFactory;
import javax.annotation.Resource;
import javax.jbi.messaging.DeliveryChannel;
import javax.jbi.messaging.ExchangeStatus;
import javax.jbi.messaging.MessageExchange;
import javax.jbi.messaging.MessagingException;
import javax.jbi.messaging.NormalizedMessage;
import javax.jbi.messaging.RobustInOnly;
import javax.xml.transform.Source;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.servicemix.MessageExchangeListener;
	/**
	 * This class generates the response and put in response jms queue to the GRID node 
	 * for the client to pick up the response
	 *  
	 * @author hmarwaha
	 *
	 */
public class GenerateResponseBean implements MessageExchangeListener{ 
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
    
    public void onMessageExchange(MessageExchange exchange) throws MessagingException {
        if (exchange.getStatus().equals(ExchangeStatus.DONE)) {
            return;
        }
        if (exchange.getStatus().equals(ExchangeStatus.ERROR)) {
           throw new MessagingException("An error occurred sending error response to the queue.", exchange.getError());
        }
        logger.debug("Received exchange: " + exchange);
        try {
          NormalizedMessage in= exchange.getMessage("in");
          String errorMessage = (String)in.getProperty(CaxchangeConstants.ERROR_MESSAGE);
          if (errorMessage != null) {
             handleErrorResponse(exchange);
          }
        }catch(Exception e){
            logger.error("An error occurred sending error response to the queue.", e);
            throw new MessagingException("An error occurred sending error response to the queue.", e);
        }
        exchange.setStatus(ExchangeStatus.DONE);
        channel.send(exchange);
        deleteMessage(exchange);
    }
    /**
	 * This method create the error response in case of any error to send to jms queue to GRID Node
	 * @param exchange
	 * @return
	 * @throws Exception
	 */
    public void handleErrorResponse(MessageExchange exchange) throws Exception {
        NormalizedMessage in = exchange.getMessage("in");
        XPathUtil util = new XPathUtil();
        util.setIn(in);
        util.initialize();
        String errCode =  (String)in.getProperty(CaxchangeConstants.ERROR_CODE);
        String errMessage =  (String)in.getProperty(CaxchangeConstants.ERROR_MESSAGE);
        Source response = util.generateResponseFromErroredRequest(errCode, errMessage);
        RobustInOnly robustInOnly = channel.createExchangeFactory().createRobustInOnlyExchange();
        NormalizedMessage resp= robustInOnly.createMessage();
        resp.setContent(response);
        robustInOnly.setInMessage(resp);
        robustInOnly.setService(CaxchangeConstants.RESPONSE_QUEUE);
        channel.sendSync(robustInOnly, 1000);
        
    }
    /**
	 * This method deletes the original message once the response is sent to jms queue of GRID node
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
