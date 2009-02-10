package gov.nih.nci.caxchange.servicemix.bean.routing;

import gov.nih.nci.caXchange.CaxchangeConstants;
import gov.nih.nci.caXchange.CaxchangeErrors;
import gov.nih.nci.caxchange.jdbc.CaxchangeMessage;
import gov.nih.nci.caxchange.persistence.CaxchangeMessageDAO;
import gov.nih.nci.caxchange.persistence.DAOFactory;
import gov.nih.nci.caxchange.servicemix.bean.CaXchangeMessagingBean;

import java.io.StringWriter;

import javax.jbi.messaging.Fault;
import javax.jbi.messaging.MessageExchange;
import javax.jbi.messaging.MessagingException;
import javax.jbi.messaging.NormalizedMessage;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.servicemix.jbi.util.MessageUtil;
/**
 * This class stores original message in the database to use it later 
 * for generating response and or sending roll back
 *  
 * @author Ekagra Software Technologies 
 *
 */
public class StoreOriginalMessageServiceBean  extends CaXchangeMessagingBean {

    public static Logger logger= LogManager.getLogger(StoreOriginalMessageServiceBean.class);

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
        NormalizedMessage out= exchange.createMessage();
        MessageUtil.transfer(in, out);
        try {
            logger.debug("Storing original message");
            if (!(caXchangeDataUtil.isRollback())) {
               storeOriginalMessage(exchange);
            }
        }catch(Exception e) {
            logger.error("Error occurred storing original message. Sending fault.", e);
            Fault fault = getFault(CaxchangeErrors.ERROR_STORING_MESSAGE, "Error occurred storing original message.", exchange );
            exchange.setFault(fault);
            channel.send(exchange);
            logger.debug("Sending fault.");
            return;
        }catch(Throwable t) {
            logger.error("Runtime error occurred storing original message.", t);
            Fault fault = getFault(CaxchangeErrors.ERROR_STORING_MESSAGE, "Error occurred storing original message.", exchange );
            exchange.setFault(fault);
            channel.send(exchange);
            return;
        }
        exchange.setMessage(out, "out");
        channel.send(exchange);
    }



   /**
     * This method store the original message in the database to add with the exchange
     * correlationID as the message id.
     * @param exchange
     * @return
     * @throws Exception
     */
    public void storeOriginalMessage(MessageExchange exchange) throws Exception {
        NormalizedMessage in = exchange.getMessage("in");
        Source source = in.getContent();
        StringWriter sw = new StringWriter();
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        StreamResult stringResult= new StreamResult(sw);
        transformer.transform(source, stringResult);
        String correlationId = (String)exchange.getProperty(CaxchangeConstants.EXCHANGE_CORRELATIONID);
        logger.debug("Correlation id is:"+correlationId);
        String message = sw.getBuffer().toString();
        CaxchangeMessage caxchangeMessage = new CaxchangeMessage();
        caxchangeMessage.setMessageId(correlationId);
        caxchangeMessage.setMessage(message);
        CaxchangeMessageDAO caxchangeMessageDAO = DAOFactory.getCaxchangeMessageDAO();
        caxchangeMessageDAO.storeMessage(caxchangeMessage);
    }

}
