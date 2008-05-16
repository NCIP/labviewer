package gov.nih.nci.caXchange.outbound;

import gov.nih.nci.caXchange.CaxchangeConstants;
import gov.nih.nci.caXchange.CaxchangeErrors;
import gov.nih.nci.caXchange.outbound.impl.GridMessageImpl;

import java.io.IOException;
import java.net.ConnectException;
import java.rmi.RemoteException;
import java.util.Iterator;

import javax.annotation.Resource;
import javax.jbi.messaging.DeliveryChannel;
import javax.jbi.messaging.ExchangeStatus;
import javax.jbi.messaging.MessageExchange;
import javax.jbi.messaging.MessagingException;
import javax.jbi.messaging.NormalizedMessage;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;

import org.apache.axis.AxisFault;
import org.apache.axis.types.URI.MalformedURIException;
import org.apache.log4j.Category;
import org.apache.servicemix.MessageExchangeListener;
import org.apache.servicemix.jbi.jaxp.SourceTransformer;
import org.globus.wsrf.encoding.DeserializationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;


/**
 * This class contains the methods for processing caxchange message and send to 
 * target grid services and create the response
 * @author steve
 * 
 */
public class GridSU implements MessageExchangeListener {

	private static final Category log = Category.getInstance(GridSU.class);

	public static final String NS = "http://caXchange.nci.nih.gov/messaging";

	public static final String REQUEST_ELEMENT = "request";

	public static final String REQUEST_PAYLOAD_ELEMENT = "businessMessagePayload";

	public static final String META_DATA_ELEMENT = "metadata";

	public static final String RESPONSE_ELEMENT = "targetResponse";

	public static final String ERROR_RESPONSE_ELEMENT = "targetError";

	public static final String ERROR_CODE_ELEMENT = "errorCode";

	public static final String ERROR_DESCRIPTION_ELEMENT = "errorDescription";

	public static final String TARGET_SERVICE_ID_ELEMENT = "targetServiceIdentifier";

	public static final String TARGET_SERVICE_OP_ELEMENT = "targetServiceOperation";

	public static final String TARGET_STATUS_ELEMENT = "targetMessageStatus";

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
	private int retries = 3;
	private int waitBetweenRetries = 3000;
	
	/**
	 * When the POJO Implements the MessageExchangeListener interface of servicemix-bean component
	 * all the exchange will be dispatched to the onMessageExchange() method
	 * Here is tries to execute strategy depending on the message exchange sent
	 * @param messageExchange
	 * @return
	 * @throws MessagingException
	 */
	public void onMessageExchange(MessageExchange exchange)
			throws MessagingException {

		if (ExchangeStatus.DONE == exchange.getStatus() || ExchangeStatus.ERROR == exchange.getStatus()) {
			log.debug("returning done status recvd:"+exchange.getStatus());
			exchange.setStatus(ExchangeStatus.DONE);
			return;
		}
		
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
	 * This method defines number of times to retry grid invocation in case of failure.
	 * @param
	 * @return retries
	 * @throws
	 */
	public int getRetries() {
		return retries;
	}

	/**
	 * This method sets number of times to retry grid invocation in case of failure.
	 * 
	 * @param retries
	 * @return
	 * @throws
	 *           
	 */
	public void setRetries(int retries) {
		this.retries = retries;
	}

	/**
	 * This method gets seconds to wait between retries.
	 * @param
	 * @return waitBetweenRetries
	 * @throws
	 */
	public int getWaitBetweenRetries() {
		return waitBetweenRetries;
	}

	/**
	 * This mehods sets time in seconds to wait between retries.
	 * 
	 * @param waitBetweenRetries
	 *            
	 */
	public void setWaitBetweenRetries(int waitBetweenRetries) {
		this.waitBetweenRetries = waitBetweenRetries;
	}

	/**
	 * This methods gets the grid invocaton strategy
	 * @param 
	 * @return strategy
	 * @throws
	 */
	public GridInvocationStrategy getStrategy() {
		return strategy;
	}

	/**
	 * This methods sets the grid invocation strategy
	 * @param strategy
	 * @return
	 * @throws
	 */
	public void setStrategy(GridInvocationStrategy strategy) {
		this.strategy = strategy;
	}

	/**
	 * This method gets delivery channel for the message exchange
	 * @param
	 * @return channel
	 * @throws
	 */
	public DeliveryChannel getChannel() {
		return channel;
	}
	/**
	 * This methods invokes the grid service with the exchanges form the ESB 
	 * depending upon the grid invocation strategy
	 * @param exchange
	 * @return
	 * @throws Exception
	 */
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
					Document output = createOutputDocument(exchange, gridMessage, result);
					out.setContent(new DOMSource(output));
					exchange.setMessage(out, "out");
				} else {
					exchange.setStatus(ExchangeStatus.DONE);
				}
				break;
			} catch (GridInvocationException gie) {
				log.error("Failed to invoke grid service.", gie);
				if (gie.getCanRetry() && i<retries) {
					log.debug("Retrying invocation");
				} else {
					log.debug("Exhausted retries. Returning error.");
					if(!isRollback()) {
						NormalizedMessage out = exchange.createMessage();
						Document output = createErrorDocument(exchange, gie);
						out.setContent(new DOMSource(output));
						exchange.setMessage(out, "out");
					} else {
						exchange.setError(gie);
					}
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
	 * @param in
	 * @param out
	 * @return targetOperation
	 * @throws Exception
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
	/**
	 * Gets the target operation
	 * @param
	 * @return targetOperation
	 * @throws
	 */
	public String getTargetOperation() {
		return targetOperation;
	}
	/**
	 * Sets the target operation
	 * @param targetOperation
	 * @return
	 * @throws
	 */
	public void setTargetOperation(String targetOperation) {
		this.targetOperation = targetOperation;
	}

	/**
	 * Gets the target id
	 * @param exchange
	 * @return
	 * @throws
	 */
	public String getTargetId(MessageExchange exchange) {
		if(strategy.isItineraryBased())
			return (String) exchange.getMessage("in").getProperty(CaxchangeConstants.TARGET_ID);
		return targetId;
	}

	/**
	 * Sets the target id
	 * @param targetId
	 * @return
	 * @throws
	 */
	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	/**
	 * Returns if the message have attachments or not
	 * @param
	 * @return the copyAttachments
	 * @throws
	 */
	public boolean isCopyAttachments() {
		return copyAttachments;
	}

	/**
	 * Sets the attachments to the message
	 * @param copyAttachments
	 * @return
	 * @throws           
	 */
	public void setCopyAttachments(boolean copyAttachments) {
		this.copyAttachments = copyAttachments;
	}

	/**
	 * Return if there is properties copy or not in message exchange
	 * @param
	 * @return the copyProperties
	 * @throws
	 */
	public boolean isCopyProperties() {
		return copyProperties;
	}

	/**
	 * Sets copy properties to the exchange
	 * @param copyProperties
	 * @return
	 * @throws          
	 */
	public void setCopyProperties(boolean copyProperties) {
		this.copyProperties = copyProperties;
	}

	/**
	 * Returns if it had to copy subject part of message
	 * @param
	 * @return the copySubject
	 * @throws
	 */
	public boolean isCopySubject() {
		return copySubject;
	}

	/**
	 * Sets the copy subject
	 * @param copySubject
	 * @return
	 * @throws   
	 */
	public void setCopySubject(boolean copySubject) {
		this.copySubject = copySubject;
	}

	/**
	 * Checks if rollback is required
	 * @param
	 * @return rollback
	 * @throws
	 */
	public boolean isRollback() {
		return rollback;
	}

	/**
	 * Sets the rollback
	 * @param rollback
	 * @return
	 * @throws
	 *
	 */
	public void setRollback(boolean rollback) {
		this.rollback = rollback;
	}
	
	/**
	 * Creates the output document for target grid service
	 * @param exchange
	 * @return output
	 * @throws Exception
	 */
	protected Document createBaseOutputDocument(MessageExchange exchange) throws Exception {
		Document output = new SourceTransformer().createDocument();
		Element root = output.createElement(RESPONSE_ELEMENT);
		Element targetServiceId = output.createElement(TARGET_SERVICE_ID_ELEMENT);
		targetServiceId.setTextContent(getTargetId(exchange));
		Element targetServiceOp = output.createElement(TARGET_SERVICE_OP_ELEMENT);
		targetServiceOp.setTextContent(getTargetOperation());
		root.appendChild(targetServiceId);
		root.appendChild(targetServiceOp);
		output.appendChild(root);
		return output;
	}
	
	/**
	 * This methods creates output document with grid message
	 * @param exchange
	 * @param gridMessage
	 * @param result
	 * @return output
	 * @throws Exception
	 */
	protected Document createOutputDocument(MessageExchange exchange, GridMessage gridMessage,
			GridInvocationResult result) throws Exception {

		Document output = createBaseOutputDocument(exchange);
		Element root = output.getDocumentElement();
		Element targetStatus = output.createElement(TARGET_STATUS_ELEMENT);
		if (result.isFault()) {
			targetStatus.setTextContent("FAULT");
		} else {
			targetStatus.setTextContent("RESPONSE");
		}
		root.appendChild(targetStatus);

		Element payloadElement = output.createElement(RESPONSE_PAYLOAD_ELEMENT);
		payloadElement.appendChild(output.importNode(gridMessage
				.getSchemaDefinition(), true));
		payloadElement.appendChild(output.importNode(result.getResult(), true));
		root.appendChild(payloadElement);
		return output;
	}
	/**
	 * This method creates the error code document with the description of 
	 * the causes of error
	 * @param exchange
	 * @param e
	 * @return output
	 * @throws Exception
	 */
	public Document createErrorDocument(MessageExchange exchange, Exception e) throws Exception {
		Document output = createBaseOutputDocument(exchange);
		Element root = output.getDocumentElement();
		Element targetStatus = output.createElement(TARGET_STATUS_ELEMENT);
		targetStatus.setTextContent("ERROR");
		root.appendChild(targetStatus);
		Element payloadElement = output.createElement(ERROR_RESPONSE_ELEMENT);
		Element errorCode = output.createElement(ERROR_CODE_ELEMENT);
		Element errorDescription = output.createElement(ERROR_DESCRIPTION_ELEMENT);
		errorCode.setTextContent(findErrorCodeForException(e));
		
		String errorDesc=e.getMessage();
		if(e instanceof AxisFault){
			errorDesc=((AxisFault)e).getFaultString();
		}
		errorDescription.setTextContent(errorDesc);
		payloadElement.appendChild(errorCode);
		payloadElement.appendChild(errorDescription);
		root.appendChild(payloadElement);
		return output;
	}
/**
 * This methods gives the error code for the exception
 * @param e
 * @return errorCode
 * @throws
 */
	public static String findErrorCodeForException(Throwable e) {

		String errorCode="";
		if(e instanceof GridInvocationException){
			errorCode= findErrorCodeForException(e.getCause());
		}
		if(e instanceof AxisFault){
			errorCode= findErrorCodeForException(e.getCause());
		}
		if("".equals(errorCode) || CaxchangeErrors.UNKNOWN.equals(errorCode)){
			if (e instanceof AxisFault) {
				errorCode=CaxchangeErrors.AXIS_FAULT;
			} else if (e instanceof MalformedURIException) {
				errorCode=CaxchangeErrors.MALFORMED_URI;
			} else if (e instanceof RemoteException) {
				errorCode=CaxchangeErrors.REMOTE_EXCEPTION;
			} else if (e instanceof TransformationException) {
				errorCode=CaxchangeErrors.TRANSFORMER_EXCEPTION;
			} else if (e instanceof DeserializationException) {
				errorCode=CaxchangeErrors.DESERIALIZATION_EXCEPTION;
			} else if (e instanceof SAXException) {
				errorCode=CaxchangeErrors.SAX_EXCEPTION;
			} else if (e instanceof ParserConfigurationException) {
				errorCode=CaxchangeErrors.PARSER_CONFIGURATION_EXCEPTION;
			} else if (e instanceof IOException) {
				errorCode=CaxchangeErrors.IO_EXCEPTION;
			} else if (e instanceof ConnectException) {
				errorCode=CaxchangeErrors.CONNECT_EXCEPTION;
			}
		}
			
		return errorCode;
	}
	/**
	 * This method checks the shows the status of the inactive exchanges
	 * @param exchange
	 * @return
	 * @throws MessagingException
	 */
	private void failInactiveExchange(MessageExchange exchange)
			throws MessagingException {

		log.error("Inactive exchange received. Actual status ("
				+ exchange.getStatus() + ").");

		throw new MessagingException("Inactive exchange received. "
				+ "Actual status (" + exchange.getStatus() + ").");
	}

}
