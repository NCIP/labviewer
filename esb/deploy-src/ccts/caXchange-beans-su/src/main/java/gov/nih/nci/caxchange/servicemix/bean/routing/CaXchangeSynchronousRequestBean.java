package gov.nih.nci.caxchange.servicemix.bean.routing;

import gov.nih.nci.caxchange.servicemix.bean.CaXchangeMessagingBean;

import java.io.StringReader;

import javax.jbi.messaging.MessageExchange;
import javax.jbi.messaging.MessagingException;
import javax.jbi.messaging.NormalizedMessage;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.servicemix.jbi.jaxp.SourceTransformer;

public class CaXchangeSynchronousRequestBean extends CaXchangeMessagingBean {

	private Logger logger = LogManager
			.getLogger(CaXchangeSynchronousRequestBean.class);

	@Override
	public void processMessageExchange(MessageExchange exchange)
			throws MessagingException {
		logger.debug("Received exchange: " + exchange);
		NormalizedMessage message = exchange.getMessage("in");
		message.setContent(caXchangeDataUtil.getDOMSource());
		Source content = message.getContent();
		try {
			logger.debug("Request Messagee: "
					+ new SourceTransformer().toString(content));

			NormalizedMessage out = exchange.createMessage();
			String responseMessage = buildResponseMessage();
			logger.debug("RESPONSE MESSAGE: "+responseMessage);
			Source source = new StreamSource(new StringReader(responseMessage));
			out.setContent(source);
			exchange.setMessage(out, "out");
			
			out.setProperty("content-type", "application/soap+xml");
			channel.send(exchange);
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String buildResponseMessage() {
		return "<ns1:caXchangeResponseMessage xmlns:ns1=\"http://caXchange.nci.nih.gov/messaging\"> <ns1:responseMetadata>  <ns1:externalIdentifier>myExternalIdentifier</ns1:externalIdentifier>  <ns1:caXchangeIdentifier>6204c650-eb3f-11dd-8e1f-e11452ebd4eb</ns1:caXchangeIdentifier> </ns1:responseMetadata> <ns1:response>  <ns1:responseStatus>SUCCESS</ns1:responseStatus>  <ns1:targetResponse>   <ns1:targetServiceIdentifier>psc</ns1:targetServiceIdentifier>   <ns1:targetServiceOperation>SCHEDULE_MODIFICATION</ns1:targetServiceOperation>   <ns1:targetMessageStatus>RESPONSE</ns1:targetMessageStatus>   <ns1:targetBusinessMessage>    <ns1:xmlSchemaDefinition>gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain</ns1:xmlSchemaDefinition>    <result xmlns=\"\">success</result>   </ns1:targetBusinessMessage>  </ns1:targetResponse> </ns1:response> </ns1:caXchangeResponseMessage>";
	}

}
