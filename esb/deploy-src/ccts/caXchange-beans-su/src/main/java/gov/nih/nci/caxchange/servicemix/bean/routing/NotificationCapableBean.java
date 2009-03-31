package gov.nih.nci.caxchange.servicemix.bean.routing;

import gov.nih.nci.caXchange.CaxchangeConstants;

import java.io.StringReader;
import java.util.HashMap;

import javax.jbi.messaging.ExchangeStatus;
import javax.jbi.messaging.InOnly;
import javax.jbi.messaging.InOut;
import javax.jbi.messaging.MessageExchange;
import javax.jbi.messaging.MessagingException;
import javax.jbi.messaging.NormalizedMessage;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class NotificationCapableBean extends HandleErrorResponseBean {

	public static final String DEFAULT_ERROR_CODE = "CAXCHANGE_ERROR";
	public static final String DEFAULT_ERROR_MESSAGE = "An error occurred processing request.";
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
		inOut.setService(CaxchangeConstants.ASYNC_PIPELINE);
		inOut.setStatus(ExchangeStatus.ACTIVE);
		channel.send(inOut);
	}

	/**
	 * 
	 * @param exchange
	 * @throws MessagingException
	 */
	private void sendFailureEmailNotification(MessageExchange exchange)
			throws MessagingException {
		NormalizedMessage in = exchange
				.getMessage(CaxchangeConstants.IN_MESSAGE);
		InOnly inOnly = channel.createExchangeFactory().createInOnlyExchange();
		NormalizedMessage emailNormalizedMessage = inOnly.createMessage();
		in.setContent(caXchangeDataUtil.getDOMSource());
		Source sourceToEmail = in.getContent();

		String emailMessage = DEFAULT_ERROR_MESSAGE;
		Source source = new StreamSource(new StringReader(emailMessage));

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
			if (exchange.getService().equals(
					CaxchangeConstants.NC_REQUEST_SERVICE)) {
				caXchangeIDAndRetryCountMap.put(caXchangeDataUtil
						.getCaXchangeIdentifier(), "0");
				sendMessageToAsyncPipeline(exchange);

			} else {
				NormalizedMessage in = exchange
						.getMessage(CaxchangeConstants.IN_MESSAGE);
				String errorMessage = (String) in
						.getProperty(CaxchangeConstants.ERROR_MESSAGE);
				String errorCode = (String) in
						.getProperty(CaxchangeConstants.ERROR_CODE);
				logger.debug("ErrorMessage: " + errorMessage + " ErrorCode: "
						+ errorCode);
				if ((errorMessage != null) || (errorCode != null)) {
					try {
						int retryCount = new Integer(
								caXchangeIDAndRetryCountMap
										.get(caXchangeDataUtil
												.getCaXchangeIdentifier()))
								.intValue();
						if (retryCount < 5) {
							retryCount += 1;
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
						deleteMessage(exchange);
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
