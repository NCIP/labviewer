package gov.nih.nci.caxchange.servicemix.bean.routing;

import gov.nih.nci.caXchange.CaxchangeConstants;
import gov.nih.nci.caxchange.servicemix.bean.CaXchangeMessagingBean;

import java.io.StringReader;
import java.util.HashMap;

import javax.jbi.messaging.ExchangeStatus;
import javax.jbi.messaging.InOnly;
import javax.jbi.messaging.InOut;
import javax.jbi.messaging.MessageExchange;
import javax.jbi.messaging.MessagingException;
import javax.jbi.messaging.NormalizedMessage;
import javax.xml.namespace.QName;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.servicemix.jbi.jaxp.SourceTransformer;
import org.apache.servicemix.mail.marshaler.AbstractMailMarshaler;

import org.w3c.dom.Document;

public class NotificationCapableBean extends CaXchangeMessagingBean {

	public static final String DEFAULT_ERROR_CODE = "CAXCHANGE_ERROR";
	public static final String MAIL_MSG_HEADER = "<ns1:mailBody xmlns:ns1=\"http://caXchange.nci.nih.gov/messaging\">An error occurred processing request.";
	public static final String MAIL_MSG_FOOTER = "</ns1:mailBody>";
	public static HashMap<String, String> caXchangeIDAndRetryCountMap = new HashMap<String, String>();

	private Logger logger = LogManager.getLogger(NotificationCapableBean.class);

	/**
	 * 
	 * @param exchange
	 * @throws MessagingException
	 */
	private void sendMessageToAsyncPipeline(MessageExchange exchange)
			throws MessagingException {
		NormalizedMessage in = exchange
				.getMessage(CaxchangeConstants.IN_MESSAGE);
		InOut inOut = channel.createExchangeFactory().createInOutExchange();
		NormalizedMessage messageToAsyncPipeline = inOut.createMessage();
		in.setContent(caXchangeDataUtil.getDOMSource());
		Source sourceToPipeline = in.getContent();
		messageToAsyncPipeline.setContent(sourceToPipeline);
		inOut.setInMessage(messageToAsyncPipeline);
		inOut.setService(new QName("http://nci.nih.gov/caXchange",
				"synchronousAsyncBridge"));
		inOut.setStatus(ExchangeStatus.ACTIVE);
		channel.send(inOut);
	}

	/**
	 * 
	 * @param exchange
	 * @throws Exception
	 */
	private void sendFailureEmailNotification(MessageExchange exchange)
			throws Exception {
		NormalizedMessage in = exchange
				.getMessage(CaxchangeConstants.IN_MESSAGE);
		InOnly inOnly = channel.createExchangeFactory().createInOnlyExchange();
		NormalizedMessage emailNormalizedMessage = inOnly.createMessage();
		in.setContent(caXchangeDataUtil.getDOMSource());
		
		String emailMessageDetails = "    SERVICE_TYPE: "
				+ caXchangeDataUtil.getServiceType()
				+ ".    CAXCHANGE_IDENTIFIER: "
				+ caXchangeDataUtil.getCaXchangeIdentifier();

		String emailMessage = MAIL_MSG_HEADER + emailMessageDetails + MAIL_MSG_FOOTER;
		logger.debug("EMAIL_MESSAGE: "+emailMessage);
		Source streamSource = new StreamSource(new StringReader(emailMessage));
		Source sourceToEmail = new SourceTransformer().toDOMSource(streamSource);

		emailNormalizedMessage.setProperty(AbstractMailMarshaler.MAIL_TAG_SUBJECT, "CAXCHANGE ERROR: " + caXchangeDataUtil.getServiceType() + ": "
			+ caXchangeDataUtil.getCaXchangeIdentifier());
//		emailNormalizedMessage.setProperty("org.apache.servicemix.email.text", emailMessage);

		emailNormalizedMessage.setContent(sourceToEmail);
		inOnly.setInMessage(emailNormalizedMessage);
		inOnly.setService(CaxchangeConstants.EMAIL_SERVICE);
		inOnly.setStatus(ExchangeStatus.ACTIVE);
		channel.send(inOnly);
	}

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
			logger.debug("REQUEST COMING FROM: "
					+ exchange.getService().toString());
			if (exchange.getService().equals(
					new QName("http://nci.nih.gov/caXchange",
							"notificationCapableService"))) {
				logger
						.debug("REQUEST COMING FROM NOTIFICAITON CAPABLE SERVICE");
				caXchangeIDAndRetryCountMap.put(caXchangeDataUtil
						.getCaXchangeIdentifier(), "0");
				sendMessageToAsyncPipeline(exchange);

			} else {
				if (!(exchange.toString().lastIndexOf(
						"<responseStatus>SUCCESS</responseStatus>") > -1)) {

					logger.debug("RESPONSE STATUS FAILURE");
					try {
						int retryCount = new Integer(
								caXchangeIDAndRetryCountMap
										.get(caXchangeDataUtil
												.getCaXchangeIdentifier()))
								.intValue();
						if (retryCount < 2) {
							retryCount += 1;
							logger.debug("****RETRY ATTEMPT****** "+retryCount);
							caXchangeIDAndRetryCountMap.put(caXchangeDataUtil
									.getCaXchangeIdentifier(), new Integer(
									retryCount).toString());
							try {
								Thread.sleep(5000);
							} catch (InterruptedException ie) {
								logger.error(
										"An error occurred sending message.",
										ie);
								throw new MessagingException(
										"An error occurred sending message.",
										ie);
							}
							sendMessageToAsyncPipeline(exchange);
						} else {
							caXchangeIDAndRetryCountMap
									.remove(caXchangeDataUtil
											.getCaXchangeIdentifier());
							sendFailureEmailNotification(exchange);
						}
						// deleteMessage(exchange);
					} catch (Exception e) {
						logger
								.error(
										"An error occurred sending email notification.",
										e);
						throw new MessagingException(
								"An error occurred sending email notification.",
								e);
					}
				} else {
					logger.debug("Message Delivered Successfully");
					// DO NOTHING. EVERYTHING IS SUCCESSFUL
				}
			}
		} catch (Exception e) {
			logger.error("An error occurred sending message.", e);
			throw new MessagingException("An error occurred sending message.",
					e);
		}

	}

}
