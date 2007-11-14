package gov.nih.nci.caxchange.servicemix.bean.routing;

import gov.nih.nci.caxchange.jdbc.CaxchangeMessage;
import gov.nih.nci.caxchange.persistence.CaxchangeMessageDAO;
import gov.nih.nci.caxchange.persistence.DAOFactory;

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

public class UnhandledMessageTypeBean implements MessageExchangeListener {
    @Resource
    private DeliveryChannel channel;
    
    static Logger logger = LogManager.getLogger(UnhandledMessageTypeBean.class);
    
    public UnhandledMessageTypeBean() {
    }
    
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
        out.setProperty(CaxchangeConstants.ERROR_CODE, "UNHANDLED_MESSAGE_TYPE");
        out.setProperty(CaxchangeConstants.ERROR_MESSAGE, "Caxhange does not handle this message type.");
        exchange.setMessage(out,"out");
        channel.send(exchange);
    }
    

    
}
