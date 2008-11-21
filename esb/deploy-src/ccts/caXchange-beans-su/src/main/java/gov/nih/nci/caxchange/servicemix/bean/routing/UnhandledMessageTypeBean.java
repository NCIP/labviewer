package gov.nih.nci.caxchange.servicemix.bean.routing;

import gov.nih.nci.caXchange.CaxchangeConstants;
import gov.nih.nci.caXchange.CaxchangeErrors;
import javax.annotation.Resource;
import javax.jbi.messaging.DeliveryChannel;
import javax.jbi.messaging.ExchangeStatus;
import javax.jbi.messaging.MessageExchange;
import javax.jbi.messaging.MessagingException;
import javax.jbi.messaging.NormalizedMessage;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.servicemix.MessageExchangeListener;
import org.apache.servicemix.jbi.util.MessageUtil;
import gov.nih.nci.caxchange.servicemix.bean.util.*;
/**
 * CaXchange received a message for a message type for which routing 
 * has not yet been defined. This bean handles such messages. It sets 
 * appropriate error properties and sends the exchange back for the 
 * error response to be generated.
 * 
 * @author Ekagra Software Technologies
 *
 */
public class UnhandledMessageTypeBean implements MessageExchangeListener {
    @Resource
    private DeliveryChannel channel;
    
    static Logger logger = LogManager.getLogger(UnhandledMessageTypeBean.class);
    /**
     * Default constructor
     */
    public UnhandledMessageTypeBean() {
    }
    
    /**
	 * When the POJO Implements the MessageExchangeListener interface of servicemix-bean component
	 * all the exchange will be dispatched to the onMessageExchange() method
	 * Here it handles the exchange with the message type for which routing has 
	 * not been defined
	 * @param exchange
	 * @return
	 * @throws MessagingException
	 */
    public void onMessageExchange(MessageExchange exchange) throws MessagingException {
        if (exchange.getStatus().equals(ExchangeStatus.DONE)) {
            return;
        }
        if (exchange.getStatus().equals(ExchangeStatus.ERROR)) {
            throw new MessagingException("Error sending unhandled message type error.", exchange.getError());
        }
        logger.debug("Received exchange: " + exchange); 
        NormalizedMessage out = exchange.createMessage();
        MessageUtil.transfer(exchange.getMessage("in"), out);
        try {
           XPathUtil xpathUtil = new XPathUtil();
           xpathUtil.setIn(exchange.getMessage("in"));
           xpathUtil.initialize();
           String messageType = xpathUtil.getMessageType();
           out.setProperty(CaxchangeConstants.ERROR_CODE, CaxchangeErrors.UNHANDLED_MESSAGE_TYPE);
           out.setProperty(CaxchangeConstants.ERROR_MESSAGE, "Caxchange does not handle this message type :"+messageType);
        }catch(Exception e) {
            logger.error("An error occurred sending unhandled message type error response to the queue.", e);
            throw new MessagingException("An error occurred sending  unhandled message type error response to the queue.", e);
        }
        exchange.setMessage(out,"out");
        channel.send(exchange);
    }
    

    
}