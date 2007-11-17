package gov.nih.nci.caXchange.outbound;

import gov.nih.nci.caXchange.outbound.impl.GridMessageImpl;

import java.util.Iterator;

import javax.annotation.Resource;
import javax.jbi.messaging.DeliveryChannel;
import javax.jbi.messaging.ExchangeStatus;
import javax.jbi.messaging.MessageExchange;
import javax.jbi.messaging.MessagingException;
import javax.jbi.messaging.NormalizedMessage;
import javax.xml.transform.dom.DOMSource;

import org.apache.log4j.Category;
import org.apache.servicemix.MessageExchangeListener;
import org.apache.servicemix.jbi.jaxp.SourceTransformer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Hello world!
 * 
 */
public class GridSU implements MessageExchangeListener {

	private static final Category log = Category.getInstance(GridSU.class);

	public static final String NS = "http://caXchange.nci.nih.gov/messaging";

	public static final String REQUEST_ELEMENT = "request";

	public static final String REQUEST_PAYLOAD_ELEMENT = "businessMessagePayload";

	public static final String META_DATA_ELEMENT = "metadata";

	public static final String RESPONSE_ELEMENT = "targetResponseMessage";

	public static final String TARGET_SERVICE_ID_ELEMENT = "targetServiceIdentifier";

	public static final String TARGET_SERVICE_OP_ELEMENT = "targetServiceOperation";

	public static final String RESPONSE_PAYLOAD_ELEMENT = "messagePayload";

	public static final String SCHEMA_DEFINITION_ELEMENT = "xmlSchemaDefinition";

	@Resource
	private DeliveryChannel channel;

	private GridInvocationStrategy strategy;
	private boolean rollback;
	private String targetOperation;
	private String targetId;
	private boolean copyProperties = true;
	private boolean copyAttachments = true;
	private boolean copySubject = true;
	private int retries;
	private int waitBetweenRetries;

	public void onMessageExchange(MessageExchange exchange)
			throws MessagingException {

		log.debug("Grid service unit received exchange: " + exchange);
		if (ExchangeStatus.ACTIVE != exchange.getStatus()) {
			failInactiveExchange(exchange);
		}

		try {
			executeStrategy(exchange);
		} catch (Exception e) {
			log.error("Failed to process exchange.", e);
			exchange.setError(e);
		}
		channel.send(exchange);
	}

	/**
	 * Number of times to retry grid invocation in case of failure.
	 * 
	 * @return int number of retries
	 */
	public int getRetries() {
		return retries;
	}

	/**
	 * Number of times to retry grid invocation in case of failure.
	 * 
	 * @param int
	 *            number of retries
	 */
	public void setRetries(int retries) {
		this.retries = retries;
	}

	/**
	 * Seconds to wait between retries.
	 * 
	 * @return int seconds
	 */
	public int getWaitBetweenRetries() {
		return waitBetweenRetries;
	}

	/**
	 * Seconds to wait between retries.
	 * 
	 * @param int
	 *            seconds
	 */
	public void setWaitBetweenRetries(int waitBetweenRetries) {
		this.waitBetweenRetries = waitBetweenRetries;
	}

	public GridInvocationStrategy getStrategy() {
		return strategy;
	}

	public void setStrategy(GridInvocationStrategy strategy) {
		this.strategy = strategy;
	}

	public DeliveryChannel getChannel() {
		return channel;
	}

	public void executeStrategy(MessageExchange exchange) throws Exception {

		for (int i = 0; i <= retries; i++) {
			try {
				NormalizedMessage in = exchange.getMessage("in");
				Document input = new SourceTransformer().toDOMDocument(in);
				GridMessage gridMessage = new GridMessageImpl(input);
				GridInvocationResult result = strategy.invokeGridService(
						channel, exchange, gridMessage);

				if (!isRollback()) {
					NormalizedMessage out = exchange.createMessage();
					Document output = createOutputDocument(gridMessage, result);
					out.setContent(new DOMSource(output));
					exchange.setMessage(out, "out");
				} else {
					exchange.setStatus(ExchangeStatus.DONE);
				}
			} catch (GridInvocationException gie) {
				log.error("Failed to invoke grid service.", gie);
				if (gie.getCanRetry()) {
					log.debug("Retrying invocation");
				} else {
					log.debug("Exhausted retries. Returning error.");
					exchange.setError(gie);
					break;
				}
			}
		}
		if (exchange.getMessage("out") != null) {
			copyPropertiesAndAttachments(exchange.getMessage("in"), exchange
					.getMessage("out"));
		}
	}

	/**
	 * Shamelessly copied from saxon endpoint :) No developer on file.
	 * 
	 * If enabled the properties and attachments are copied to the destination
	 * message
	 * 
	 * @param NormalizedMessage
	 *            in the in message of the exchange
	 * @param NormalizedMessage
	 *            out the out message of the exchange
	 */
	protected void copyPropertiesAndAttachments(NormalizedMessage in,
			NormalizedMessage out) throws Exception {
		if (isCopyProperties()) {
			for (Iterator it = in.getPropertyNames().iterator(); it.hasNext();) {
				String name = (String) it.next();
				out.setProperty(name, in.getProperty(name));
			}
		}
		if (isCopyAttachments()) {
			for (Iterator it = in.getAttachmentNames().iterator(); it.hasNext();) {
				String name = (String) it.next();
				out.addAttachment(name, in.getAttachment(name));
			}
		}
		if (isCopySubject()) {
			out.setSecuritySubject(in.getSecuritySubject());
		}
	}

	public String getTargetOperation() {
		return targetOperation;
	}

	public void setTargetOperation(String targetOperation) {
		this.targetOperation = targetOperation;
	}

	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	/**
	 * @return the copyAttachments
	 */
	public boolean isCopyAttachments() {
		return copyAttachments;
	}

	/**
	 * @param copyAttachments
	 *            the copyAttachments to set
	 */
	public void setCopyAttachments(boolean copyAttachments) {
		this.copyAttachments = copyAttachments;
	}

	/**
	 * @return the copyProperties
	 */
	public boolean isCopyProperties() {
		return copyProperties;
	}

	/**
	 * @param copyProperties
	 *            the copyProperties to set
	 */
	public void setCopyProperties(boolean copyProperties) {
		this.copyProperties = copyProperties;
	}

	/**
	 * @return the copySubject
	 */
	public boolean isCopySubject() {
		return copySubject;
	}

	/**
	 * @param copySubject
	 *            the copySubject to set
	 */
	public void setCopySubject(boolean copySubject) {
		this.copySubject = copySubject;
	}

	public boolean isRollback() {
		return rollback;
	}

	public void setRollback(boolean rollback) {
		this.rollback = rollback;
	}

	protected Document createOutputDocument(GridMessage gridMessage,
			GridInvocationResult result) throws Exception {
		Document output = new SourceTransformer().createDocument();
		Element root = output.createElementNS(NS, RESPONSE_ELEMENT);
		Element targetServiceId = output.createElementNS(NS,
				TARGET_SERVICE_ID_ELEMENT);
		targetServiceId.setTextContent(getTargetId());
		Element targetServiceOp = output.createElementNS(NS,
				TARGET_SERVICE_OP_ELEMENT);
		targetServiceOp.setTextContent(getTargetOperation());
		root.appendChild(targetServiceId);
		root.appendChild(targetServiceOp);
		Element payloadElement = output.createElementNS(NS,
				RESPONSE_PAYLOAD_ELEMENT);
		payloadElement.appendChild(output.importNode(gridMessage
				.getSchemaDefinition(), true));
		payloadElement.appendChild(output.importNode(result.getResult(), true));
		root.appendChild(payloadElement);
		output.appendChild(root);
		return output;
	}

	private void failInactiveExchange(MessageExchange exchange)
			throws MessagingException {

		log.error("Inactive exchange received. Actual status ("
				+ exchange.getStatus() + ").");

		throw new MessagingException("Inactive exchange received. "
				+ "Actual status (" + exchange.getStatus() + ").");
	}

}
