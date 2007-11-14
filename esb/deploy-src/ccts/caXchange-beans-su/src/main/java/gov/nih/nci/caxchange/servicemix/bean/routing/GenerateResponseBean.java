package gov.nih.nci.caxchange.servicemix.bean;

import gov.nih.nci.caxchange.jdbc.CaxchangeMessage;
import gov.nih.nci.caxchange.persistence.CaxchangeMessageDAO;
import gov.nih.nci.caxchange.persistence.DAOFactory;

import javax.annotation.Resource;
import javax.jbi.messaging.DeliveryChannel;
import javax.jbi.messaging.ExchangeStatus;
import javax.jbi.messaging.InOnly;
import javax.jbi.messaging.MessageExchange;
import javax.jbi.messaging.MessagingException;
import javax.jbi.messaging.NormalizedMessage;

import javax.jbi.messaging.RobustInOnly;

import javax.xml.transform.Source;

import javax.xml.transform.Transformer;

import javax.xml.transform.TransformerFactory;

import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.servicemix.MessageExchangeListener;

public class GenerateResponseBean implements MessageExchangeListener{ 
    @Resource
    private DeliveryChannel channel;
    
    static Logger logger = LogManager.getLogger(GenerateResponseBean.class);
    
    public GenerateResponseBean() {
    }
    
    
    
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
