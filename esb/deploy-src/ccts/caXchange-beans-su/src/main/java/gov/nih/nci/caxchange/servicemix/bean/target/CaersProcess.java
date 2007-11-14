package gov.nih.nci.caxchange.servicemix.bean;

import java.io.StringReader;

import javax.annotation.Resource;

import javax.jbi.messaging.DeliveryChannel;
import javax.jbi.messaging.ExchangeStatus;
import javax.jbi.messaging.MessageExchange;

import javax.jbi.messaging.MessagingException;
import javax.jbi.messaging.NormalizedMessage;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.dom.DOMSource;

import org.apache.servicemix.MessageExchangeListener;
import org.apache.servicemix.jbi.util.MessageUtil;

import org.w3c.dom.Document;

import org.xml.sax.InputSource;

public class CaersProcess implements MessageExchangeListener {

    @Resource
    private DeliveryChannel channel;
    
    String businessResponse = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + 
    "            <targetResponse>\n" + 
    "                <targetServiceIdentifier>targetServiceIdentifier1</targetServiceIdentifier>\n" + 
    "                <targetServiceOperation>targetServiceOperation1</targetServiceOperation>\n" + 
    "                <targetMessageStatus>RESPONSE</targetMessageStatus>\n" + 
    "                    <targetBusinessMessage>\n" + 
    "                        <xmlSchemaDefinition>http://www.oxygenxml.com/</xmlSchemaDefinition>\n" + 
    "                    </targetBusinessMessage>\n" + 
    "            </targetResponse>";

    public CaersProcess() {
    }

    public void onMessageExchange(MessageExchange exchange) throws MessagingException {
        System.out.println("Received exchange: " + exchange);
        if ((exchange.getStatus()== ExchangeStatus.DONE)
            ||(exchange.getStatus()== ExchangeStatus.ERROR)){
                        return;
        }

        //sendResponse(exchange);
        //exchange.setStatus(ExchangeStatus.DONE);
        NormalizedMessage message = exchange.getMessage("in");
        NormalizedMessage out = exchange.createMessage();
        MessageUtil.transfer(message, out);
        Document document= null;
        try {
           document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(businessResponse)));
        }catch(Exception e){
           e.printStackTrace();
        }
        out.setContent(new DOMSource(document));
        exchange.setMessage(out, "out");
        channel.send(exchange);    
    }
}
