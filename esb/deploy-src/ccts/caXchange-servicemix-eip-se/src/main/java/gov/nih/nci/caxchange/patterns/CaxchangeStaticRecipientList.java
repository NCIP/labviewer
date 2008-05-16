package gov.nih.nci.caxchange.patterns;

import javax.jbi.messaging.MessageExchange;

import javax.jbi.messaging.NormalizedMessage;

import org.apache.servicemix.eip.patterns.StaticRecipientList;

/**
 * This class contains static Recipient List that adds the count of recipients as a property
 * @author hmarwaha
 * @org.apache.xbean.XBean element="caxchange-static-recipient-list"
 *                  description="A static Recipient List that adds the count of recipients as a property"
 */
public class CaxchangeStaticRecipientList extends StaticRecipientList {
    
	/**
     * Default constructor
     */
	public CaxchangeStaticRecipientList() {
    }
    /**
     * This methods counts the number of static receipients 
     * @param exchange
     * @return
     * @throws Exception
     */
    public void process(MessageExchange exchange) throws Exception {
         NormalizedMessage in = exchange.getMessage("in");
         int length= getRecipients().length;
         in.setProperty(CaxchangeEIPConstants.CAXCHANGE_RECIPIENT_COUNT, length+"");
         in.setProperty(CaxchangeEIPConstants.ORIGINAL_EXCHANGE_CORRELATIONID, exchange.getProperty("org.apache.servicemix.correlationId"));
         super.process(exchange);
    }    
}
