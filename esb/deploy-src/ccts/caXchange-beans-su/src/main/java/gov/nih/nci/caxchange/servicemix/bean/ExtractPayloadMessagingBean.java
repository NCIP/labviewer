package gov.nih.nci.caxchange.servicemix.bean;

import gov.nih.nci.caXchange.CaxchangeConstants;

import java.util.HashMap;
import java.util.Map;

import javax.jbi.messaging.MessageExchange;
import javax.jbi.messaging.NormalizedMessage;

import org.apache.servicemix.common.util.MessageUtil;

public class ExtractPayloadMessagingBean extends CaXchangeMessagingBean {

	public void processMessageExchange(MessageExchange exchange)
			throws Exception {
		NormalizedMessage out = exchange.createMessage();
		org.w3c.dom.Node payload = caXchangeDataUtil.getBusinessPayload();
		MessageUtil.transfer(exchange.getMessage("in"), out);
		out.setContent(new javax.xml.transform.dom.DOMSource(payload));
		String externalIdentifier = caXchangeDataUtil.getExternalIdentifier();
		String caXchangeIdentifier = caXchangeDataUtil.getCaXchangeIdentifier();
		Map<String, String> metadata = new HashMap<String, String>();
		metadata.put(CaxchangeConstants.EXTERNAL_IDENTIFIER, externalIdentifier);
		metadata.put(CaxchangeConstants.CAXCHANGE_IDENTIFIER, caXchangeIdentifier);
		out.setProperty(CaxchangeConstants.REQUEST_METADATA, metadata);
		exchange.setMessage(out, "out");
        channel.send(exchange);
	}

}
