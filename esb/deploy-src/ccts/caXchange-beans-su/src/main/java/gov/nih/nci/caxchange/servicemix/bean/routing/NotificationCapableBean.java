package gov.nih.nci.caxchange.servicemix.bean.routing;

import gov.nih.nci.caXchange.CaxchangeConstants;
import gov.nih.nci.caxchange.servicemix.bean.CaXchangeMessagingBean;

import java.io.StringReader;
import java.util.HashMap;

import javax.jbi.messaging.ExchangeStatus;
import javax.jbi.messaging.Fault;
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
	public static final String NOTIFICATION_BEAN_CORRELATION_ID = "NOTIFICATION_BEAN_CORRELATION_ID";
	public static final String MAIL_MSG_HEADER = "<ns1:mailBody xmlns:ns1=\"http://caXchange.nci.nih.gov/messaging\">An error occurred processing request.";
	public static final String MAIL_MSG_FOOTER = "</ns1:mailBody>";
	public static HashMap<String, ExchangeInProcess> caXchangeIDAndRetryCountMap = new HashMap<String, ExchangeInProcess>();

	private Logger logger = LogManager.getLogger(NotificationCapableBean.class);
	private int retryCount = 3;
	private QName targetService = null;
	private QName notificationService = null;

	public QName getNotificationService() {
		return notificationService;
	}

	public void setNotificationService(QName notificationService) {
		this.notificationService = notificationService;
	}

	public QName getTargetService() {
		return targetService;
	}

	public void setTargetService(QName targetService) {
		this.targetService = targetService;
	}

	public int getRetryCount() {
		return retryCount;
	}

	public void setRetryCount(int retryCount) {
		this.retryCount = retryCount;
	}

	/**
	 * 
	 * @param exchange
	 * @throws MessagingException
	 */
	protected void sendMessageToTargetService(MessageExchange exchange)
			throws Exception {
		NormalizedMessage in = exchange.getMessage("in");
		InOut inOut = channel.createExchangeFactory().createInOutExchange();
		NormalizedMessage messageToTargetService = inOut.createMessage();
		Source sourceToPipeline = in.getContent();
		messageToTargetService.setContent(in.getContent());
		inOut.setInMessage(messageToTargetService);
		inOut.setService(targetService);
		inOut.setStatus(ExchangeStatus.ACTIVE);
		channel.send(inOut);
	}
	protected void sendNotification(MessageExchange exchange) throws Exception{
		NormalizedMessage in = exchange
		.getMessage("in");
		InOnly inOnly = channel.createExchangeFactory().createInOnlyExchange();
		NormalizedMessage notificationMessage = inOnly.createMessage();
		notificationMessage.setContent(caXchangeDataUtil.getDOMSource());
		inOnly.setInMessage(notificationMessage);
		inOnly.setService(notificationService);
		inOnly.setStatus(ExchangeStatus.ACTIVE);
		channel.send(inOnly);
	}
	/**
	 * 
	 * @param exchange
	 * @throws Exception
	 
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
		inOnly.setService(notificationService);
		inOnly.setStatus(ExchangeStatus.ACTIVE);
		channel.send(inOnly);
	}*/

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
			ExchangeInProcess exchangeInProcess = caXchangeIDAndRetryCountMap.get(caXchangeDataUtil
						.getCaXchangeIdentifier());
			if ((exchangeInProcess == null)){
				logger.debug(" A new exchange is received.");
				exchangeInProcess = new ExchangeInProcess();
				exchangeInProcess.setExchange(exchange);
				caXchangeIDAndRetryCountMap.put(caXchangeDataUtil
						.getCaXchangeIdentifier(), exchangeInProcess);
				sendMessageToTargetService(exchange);
			} 
			else  {
				//This is  a response from the target service
				logger.debug("This is  a response from the target service");
				exchangeInProcess = caXchangeIDAndRetryCountMap.get(caXchangeDataUtil
						.getCaXchangeIdentifier());
				caXchangeDataUtil.setOut(exchange.getMessage("out"));
				caXchangeDataUtil.initialize();
				if ((exchange.getFault()!=null)||(!caXchangeDataUtil.isResponseSuccess())) {
					logger.debug("RESPONSE STATUS FAILURE");
						int currentRetryCount = exchangeInProcess.getRetryCount();
						if (currentRetryCount < retryCount) {
							currentRetryCount += 1;
							logger.debug("****RETRY ATTEMPT****** "+currentRetryCount);
							exchangeInProcess.setRetryCount(currentRetryCount);
							sendMessageToTargetService(exchangeInProcess.getExchange());
							exchange.setStatus(ExchangeStatus.DONE);
							channel.send(exchange);
						} else {
							logger.debug("Retry count achieved..");
							caXchangeIDAndRetryCountMap
									.remove(caXchangeDataUtil
											.getCaXchangeIdentifier());
							try {
							   sendNotification(exchange);
							}catch(Exception e){
								logger.error("Error sending email notification.+ for identifier:"+caXchangeDataUtil
										.getCaXchangeIdentifier());
							}
							MessageExchange originalExchange = exchangeInProcess.getExchange();
							Fault fault = originalExchange.createFault();
							if (exchange.getMessage("out")!=null){
							   fault.setContent(exchange.getMessage("out").getContent());
							}else {
								fault.setContent(caXchangeDataUtil.getDOMSource());
							}
							originalExchange.setFault(fault);
							channel.send(originalExchange);
							exchange.setStatus(ExchangeStatus.DONE);
							channel.send(exchange);
						}
				} else {
					logger.debug("Success from target service..");
					// Return response
					MessageExchange originalExchange = exchangeInProcess.getExchange();
					NormalizedMessage out = originalExchange.createMessage();
					if (exchange.getMessage("out")!=null) {
						out.setContent(exchange.getMessage("out").getContent());
					}else {
						out.setContent(caXchangeDataUtil.getDOMSource());
					}
					channel.send(originalExchange);	
					exchange.setStatus(ExchangeStatus.DONE);
					channel.send(exchange);
				}
			}
		} catch (Exception e) {
			logger.error("An error occurred sending message.", e);
			throw new MessagingException("An error occurred sending message.",
					e);
		}

	}
	
	protected class ExchangeInProcess {
		private int retryCount = 0;
		private MessageExchange exchange = null;
		public int getRetryCount() {
			return retryCount;
		}
		public void setRetryCount(int retryCount) {
			this.retryCount = retryCount;
		}
		public MessageExchange getExchange() {
			return exchange;
		}
		public void setExchange(MessageExchange exchange) {
			this.exchange = exchange;
		}
		
	}

}
