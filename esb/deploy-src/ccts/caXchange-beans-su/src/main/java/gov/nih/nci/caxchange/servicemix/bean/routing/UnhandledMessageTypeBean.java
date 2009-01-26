package gov.nih.nci.caxchange.servicemix.bean.routing;

import gov.nih.nci.caXchange.CaxchangeConstants;
import gov.nih.nci.caXchange.CaxchangeErrors;
import gov.nih.nci.caxchange.servicemix.bean.CaXchangeMessagingBean;

import javax.jbi.messaging.MessageExchange;
import javax.jbi.messaging.MessagingException;
import javax.jbi.messaging.NormalizedMessage;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.servicemix.jbi.util.MessageUtil;
/**
 * CaXchange received a message for a message type for which routing 
 * has not yet been defined. This bean handles such messages. It sets 
 * appropriate error properties and sends the exchange back for the 
 * error response to be generated.
 * 
 * @author Ekagra Software Technologies
 *
 */
public class UnhandledMessageTypeBean  extends CaXchangeMessagingBean {
    
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
    public void processMessageExchange(MessageExchange exchange) throws MessagingException {
        logger.debug("Received exchange: " + exchange); 
        NormalizedMessage out = exchange.createMessage();
        MessageUtil.transfer(exchange.getMessage("in"), out);
        try {
           String messageType = caXchangeDataUtil.getServiceType();
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
