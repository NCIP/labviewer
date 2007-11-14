package gov.nih.nci.caxchange.patterns;

import javax.jbi.messaging.MessageExchange;

import javax.jbi.messaging.NormalizedMessage;

import org.apache.servicemix.eip.patterns.StaticRecipientList;

/**
 *
 * @org.apache.xbean.XBean element="caxchange-static-recipient-list"
 *                  description="A static Recipient List that adds the count of recipients as a property"
 */
public class CaxchangeStaticRecipientList extends StaticRecipientList {
    public CaxchangeStaticRecipientList() {
    }
    
    public void process(MessageExchange exchange) throws Exception {
         NormalizedMessage in = exchange.getMessage("in");
         int length= getRecipients().length;
         in.setProperty(CaxchangeEIPConstants.CAXCHANGE_RECIPIENT_COUNT, length+"");
         super.process(exchange);
    }    
}
