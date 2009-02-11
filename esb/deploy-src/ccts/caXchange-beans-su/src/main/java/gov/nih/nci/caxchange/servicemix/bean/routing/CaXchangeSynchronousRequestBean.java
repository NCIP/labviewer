package gov.nih.nci.caxchange.servicemix.bean.routing;

import java.util.HashMap;

import gov.nih.nci.caXchange.CaxchangeConstants;
import gov.nih.nci.caxchange.servicemix.bean.util.CaXchangeDataUtil;

import javax.jbi.messaging.ExchangeStatus;
import javax.jbi.messaging.InOnly;
import javax.jbi.messaging.InOptionalOut;
import javax.jbi.messaging.InOut;
import javax.jbi.messaging.MessageExchange;
import javax.jbi.messaging.MessagingException;
import javax.jbi.messaging.NormalizedMessage;
import javax.xml.transform.Source;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.servicemix.jbi.util.MessageUtil;

public class CaXchangeSynchronousRequestBean extends HandleErrorResponseBean {

	public static final String DEFAULT_ERROR_CODE = "CAXCHANGE_ERROR";
	public static final String DEFAULT_ERROR_MESSAGE = "An error occurred processing request.";
	public static HashMap<String, String> caXchangeIDAndCorrelationIDMap = new HashMap<String, String>();

	private Logger logger = LogManager
			.getLogger(CaXchangeSynchronousRequestBean.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.nih.nci.caxchange.servicemix.bean.routing.HandleErrorResponseBean
	 * #processMessageExchange(javax.jbi.messaging.MessageExchange)
	 */
	@Override
	public void processMessageExchange(MessageExchange exchange)
			throws MessagingException {
		logger.debug("Received exchange: " + exchange);

		try {
			logger.debug("CAXCHANGE IDENTIFIER: "
					+ caXchangeDataUtil.getCaXchangeIdentifier());
			if (exchange.getService().equals(
					CaxchangeConstants.SYNCHRONOUS_REQUEST_SERVICE)) {
				caXchangeIDAndCorrelationIDMap
						.put(
								caXchangeDataUtil.getCaXchangeIdentifier(),
								(String) exchange
										.getProperty(CaxchangeConstants.EXCHANGE_CORRELATIONID));
				NormalizedMessage in = exchange
						.getMessage(CaxchangeConstants.IN_MESSAGE);
				InOut inOut = channel.createExchangeFactory()
						.createInOutExchange();
				NormalizedMessage messageToRouter = inOut.createMessage();
				in.setContent(caXchangeDataUtil.getDOMSource());
				Source sourceToRouter = in.getContent();
				messageToRouter.setContent(sourceToRouter);
				inOut.setInMessage(messageToRouter);
				inOut.setService(CaxchangeConstants.SERVICE_TYPE_ROUTER);
				inOut.setStatus(ExchangeStatus.ACTIVE);
				channel.send(inOut);
			} else {
				NormalizedMessage in = exchange
						.getMessage(CaxchangeConstants.IN_MESSAGE);
				String errorMessage = (String) in
						.getProperty(CaxchangeConstants.ERROR_MESSAGE);
				String errorCode = (String) in
						.getProperty(CaxchangeConstants.ERROR_CODE);
				if ((errorMessage != null) || (errorCode != null)) {
					try {
						logger.debug("ErrorMessage: " + errorMessage
								+ " ErrorCode: " + errorCode);
						NormalizedMessage errorMsgToBC = exchange
								.createMessage();
						MessageUtil.transfer(in, errorMsgToBC);
						Source response = getErrorResponse(exchange);
						errorMsgToBC.setContent(response);
						exchange.setMessage(errorMsgToBC,
								CaxchangeConstants.OUT_MESSAGE);
						exchange
								.setService(CaxchangeConstants.SYNCHRONOUS_REQUEST_SERVICE);
						channel.send(exchange);
						deleteMessage(exchange);
					} catch (Exception e) {
						logger
								.error(
										"An error occurred sending error response to the queue.",
										e);
						throw new MessagingException(
								"An error occurred sending error response to the queue.",
								e);
					}

				} else {
					try {
						logger
								.debug("Sending normal response to HTTP binding component");
						// send a message to the synchronous http binding
						// component
						InOnly inToBC = channel.createExchangeFactory()
								.createInOnlyExchange();
						
						NormalizedMessage normalMsgToBC = inToBC
								.createMessage();
						in.setContent(caXchangeDataUtil.getDOMSource());
						Source sourceToBC = in.getContent();
						normalMsgToBC.setContent(sourceToBC);

						inToBC.setInMessage(normalMsgToBC);
						//inToBC.setMessage(normalMsgToBC, CaxchangeConstants.OUT_MESSAGE);
						String xChangeCorrelationID = caXchangeIDAndCorrelationIDMap
								.get(caXchangeDataUtil.getCaXchangeIdentifier());
						if (xChangeCorrelationID != null) {
							logger.debug("CORRELATION ID IN MAP: "+xChangeCorrelationID);
							inToBC.setProperty(
									CaxchangeConstants.EXCHANGE_CORRELATIONID,
									xChangeCorrelationID);
							caXchangeIDAndCorrelationIDMap
									.remove(caXchangeDataUtil
											.getCaXchangeIdentifier());
						}
						inToBC
								.setService(CaxchangeConstants.SYNCHRONOUS_REQUEST_SERVICE);
						//inToBC.setStatus(ExchangeStatus.DONE);
						channel.send(inToBC);

						// send DONE message to the router
						exchange.setStatus(ExchangeStatus.DONE);
						channel.send(exchange);
					} catch (Exception e) {
						logger
								.error(
										"An error occurred sending normal response to the queue.",
										e);
						throw new MessagingException(
								"An error occurred sending normal response to the queue.",
								e);
					}

				}
			}
		} catch (Exception e) {
			logger.error("An error occurred sending response to the queue.", e);
			throw new MessagingException(
					"An error occurred sending response to the queue.", e);
		}

		// NormalizedMessage message = exchange.getMessage("in");
		// message.setContent(caXchangeDataUtil.getDOMSource());
		// Source content = message.getContent();
		// try {
		// logger.debug("Request Messagee: "
		// + new SourceTransformer().toString(content));
		//
		// NormalizedMessage out = exchange.createMessage();
		// String responseMessage = buildResponseMessage();
		// logger.debug("RESPONSE MESSAGE: "+responseMessage);
		// Source source = new StreamSource(new StringReader(responseMessage));
		// out.setContent(source);
		// exchange.setMessage(out, "out");
		//			
		// out.setProperty("content-type", "application/soap+xml");
		// channel.send(exchange);
		// } catch (TransformerException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}

	private String buildResponseMessage() {
		// return
		// "<ns1:caXchangeResponseMessage xmlns:ns1=\"http://caXchange.nci.nih.gov/messaging\"> <ns1:responseMetadata>  <ns1:externalIdentifier>myExternalIdentifier</ns1:externalIdentifier>  <ns1:caXchangeIdentifier>6204c650-eb3f-11dd-8e1f-e11452ebd4eb</ns1:caXchangeIdentifier> </ns1:responseMetadata> <ns1:response>  <ns1:responseStatus>SUCCESS</ns1:responseStatus>  <ns1:targetResponse xmlns=\"\">   <ns1:targetServiceIdentifier>psc</ns1:targetServiceIdentifier>   <ns1:targetServiceOperation>SCHEDULE_MODIFICATION</ns1:targetServiceOperation>   <ns1:targetMessageStatus>RESPONSE</ns1:targetMessageStatus>   <ns1:targetBusinessMessage>    <ns1:xmlSchemaDefinition>gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain</ns1:xmlSchemaDefinition>    <result xmlns=\"\">success</result>   </ns1:targetBusinessMessage>  </ns1:targetResponse> </ns1:response> </ns1:caXchangeResponseMessage>";
		return "<caXchangeResponseMessage xmlns=\"http://caXchange.nci.nih.gov/messaging\"><responseMetadata><externalIdentifier>myExternalIdentifier</externalIdentifier><caXchangeIdentifier>7c5755a0-f304-11dd-9c85-e19f60f31477</caXchangeIdentifier></responseMetadata><response><responseStatus>SUCCESS</responseStatus><targetResponse><targetServiceIdentifier>psc</targetServiceIdentifier><targetServiceOperation>SCHEDULE_MODIFICATION</targetServiceOperation><targetMessageStatus>RESPONSE</targetMessageStatus><targetBusinessMessage><ns1:xmlSchemaDefinition xmlns:ns1=\"http://caXchange.nci.nih.gov/messaging\">/1.0/gov.nih.nci.cabig.ccts.domain</ns1:xmlSchemaDefinition><result>success</result></targetBusinessMessage></targetResponse></response></caXchangeResponseMessage>";
	}

}
