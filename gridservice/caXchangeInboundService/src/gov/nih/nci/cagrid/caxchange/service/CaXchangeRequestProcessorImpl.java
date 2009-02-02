package gov.nih.nci.cagrid.caxchange.service;

import gov.nih.nci.caXchange.CaxchangeErrors;
import gov.nih.nci.cagrid.caxchange.context.service.globus.resource.CaXchangeResponseServiceResource;
import gov.nih.nci.cagrid.caxchange.context.service.globus.resource.CaXchangeResponseServiceResourceHome;
import gov.nih.nci.cagrid.caxchange.listener.CaxchangeResponseExceptionListener;
import gov.nih.nci.cagrid.caxchange.listener.CaxchangeResponseListener;
import gov.nih.nci.cagrid.caxchange.listener.ResponseHandler;
import gov.nih.nci.cagrid.caxchange.stubs.types.CaXchangeFault;
import gov.nih.nci.caxchange.ErrorDetails;
import gov.nih.nci.caxchange.ResponseMessage;
import gov.nih.nci.caxchange.ResponseMetadata;
import gov.nih.nci.caxchange.Statuses;
import gov.nih.nci.caxchange.TargetResponseMessage;
import gov.nih.nci.caxchange.synchronous.Credentials;
import gov.nih.nci.caxchange.synchronous.Message;
import gov.nih.nci.caxchange.synchronous.MessagePayload;
import gov.nih.nci.caxchange.synchronous.ProcessRequestSynchronouslyResponse;
import gov.nih.nci.caxchange.synchronous.Request;
import gov.nih.nci.caxchange.synchronous.Response;
import gov.nih.nci.caxchange.synchronous.TransactionControls;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.axis.message.MessageElement;
import org.apache.axis.types.URI;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.globus.wsrf.ResourceKey;
import org.globus.wsrf.security.SecurityManager;
import org.w3c.dom.Document;

/**
 * TODO:I am the service side implementation class. IMPLEMENT AND DOCUMENT ME
 * 
 * @created by Introduce Toolkit version 1.1
 * 
 */
public class CaXchangeRequestProcessorImpl extends
		CaXchangeRequestProcessorImplBase {
	protected static String brokerURL = null; // "tcp://localhost:61618";
	protected static String destinationName = "caxchangeInboundQueue";
	protected static String replyDestinationName = "caxchangeOutboundQueue";
	protected static Destination destination = null;
	protected static Destination replyDestination = null;
	protected static Logger logger = LogManager
			.getLogger(CaXchangeRequestProcessorImpl.class.getName());
	protected static ConnectionFactory connectionFactory = null;
	protected static Connection connection = null;
	public static Map<CaxchangeResponseExceptionListener, Connection> responseListeners = null;

	public CaXchangeRequestProcessorImpl() throws RemoteException {
		super();
	}

	/**
	 * Processes a request by sending it to the caXchange inbound queue.
	 * 
	 * @param caXchangeRequestMessage
	 * @return
	 * @throws RemoteException
	 */
	public gov.nih.nci.cagrid.caxchange.context.stubs.types.CaXchangeResponseServiceReference processRequestAsynchronously(
			gov.nih.nci.caxchange.Message caXchangeRequestMessage)
			throws RemoteException,
			gov.nih.nci.cagrid.caxchange.stubs.types.CaXchangeFault {
		logger.debug("request:" + caXchangeRequestMessage);
		try {
			gov.nih.nci.cagrid.caxchange.context.service.globus.resource.CaXchangeResponseServiceResourceHome ctxResourceHome = getCaXchangeResponseServiceResourceHome();
			ResourceKey resKey = ctxResourceHome.createResource();
			logger.info("Performance Request Received," + resKey.getValue()
					+ ","
					+ caXchangeRequestMessage.getMetadata().getServiceType()
					+ "," + new java.util.Date().getTime());
			caXchangeRequestMessage.getMetadata().setCaXchangeIdentifier(
					resKey.getValue().toString());
			String caller = SecurityManager.getManager().getCaller();
			if ((caller == null) || (caller.equals("<anonymous>"))) {
				updateErrorResponse(caXchangeRequestMessage, resKey,
						CaxchangeErrors.PERMISSION_DENIED_FAULT,
						"Unable to get the identity of the caller.Caller identity:"
								+ caller);
			}
			logger.debug("Sending message for the caller:" + caller);
			caXchangeRequestMessage.getMetadata().getCredentials()
					.setGridIdentifier(caller);
			try { // Sending the request to the caXchange inbound queue
				logger.info("Before sending messge " + new Date().getTime());
				sendMessage(caXchangeRequestMessage);
				logger.info("Before sending messge " + new Date().getTime());
			} catch (Exception e) {
				updateErrorResponse(caXchangeRequestMessage, resKey,
						CaxchangeErrors.UNKNOWN,
						"An error occured sending message to the caXchange hub."
								+ e.getMessage());
			}

			if ((responseListeners == null) || (responseListeners.size() == 0)) {
				registerResponseListeners();
			}
			logger.debug("Request send to ESB for key " + resKey);

			return ctxResourceHome.getResourceReference(resKey);
		} catch (Exception e) {
			logger.error("Error processing message request.", e);
			CaXchangeFault caXchangeFault = new CaXchangeFault();
			caXchangeFault
					.setFaultDetailString("Error processing message request."
							+ e.getMessage());
			caXchangeFault.setStackTrace(e.getStackTrace());
			throw caXchangeFault;
		}
	}

	/**
	 * Send the message to the caXchange inbound JMS component.
	 * 
	 * @param caXchangeRequestMessage
	 * @throws Exception
	 */
	protected void sendMessage(
			gov.nih.nci.caxchange.Message caXchangeRequestMessage)
			throws Exception {
		Session session = null;
		try {
			CaXchangeRequestProcessorConfiguration configuration = getConfiguration();
			CaXchangeExternalProperties properties = CaXchangeExternalProperties
					.getInstance();
			String inboundJmsBrokerUrl = properties
					.getProperty("inbound.jms.brokerURL");
			if (inboundJmsBrokerUrl == null) {
				inboundJmsBrokerUrl = configuration
						.getCaXchangeInboundBrokerURL();
			}
			if (connectionFactory == null) {
				connectionFactory = new ActiveMQConnectionFactory(
						inboundJmsBrokerUrl);
			}
			if (destination == null) {
				destination = new ActiveMQQueue(destinationName);
			}
			if (connection == null) {
				connection = connectionFactory.createConnection();
			}
			try {
				session = connection.createSession(false,
						Session.AUTO_ACKNOWLEDGE);
			} catch (JMSException connectionFailed) {
				// Connection may have failed try to create a new connection:
				connection = connectionFactory.createConnection();
				session = connection.createSession(false,
						Session.AUTO_ACKNOWLEDGE);
			}
			MessageProducer producer = session.createProducer(destination);
			RequestGeneratorHelper rgh = new RequestGeneratorHelper();
			rgh.setCaxchangeRequest(caXchangeRequestMessage);
			String req = rgh.getRequestForCaxchange();
			TextMessage message = session.createTextMessage();
			logger.debug("Sending message:" + req);
			message.setText(req);

			// commented lines below can be used for simulating synchronous
			// response to the client
			// TemporaryQueue outDest = session.createTemporaryQueue();
			// message.setJMSReplyTo(outDest);
			producer.send(message);

			// MessageConsumer consumer = session.createConsumer(outDest);
			// Message msgBack = null;
			// msgBack = consumer.receive(12000);
		} catch (Exception ex) {
			logger.error("Error sending message to the ESB.", ex);
			responseListeners = null;
			throw new Exception("Error sending message to the ESB.", ex);
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	/**
	 * Updating the Resource with the error Response.
	 * 
	 * @param caXchangeRequestMessage
	 * @throws Exception
	 */
	public void updateErrorResponse(
			gov.nih.nci.caxchange.Message caXchangeRequestMessage,
			ResourceKey resKey, String errorCode, String errorMessage)
			throws Exception {
		try {
			ResponseHandler responseHandler = new ResponseHandler();
			ResponseMessage response = responseHandler.getResponseFromError(
					caXchangeRequestMessage, errorCode, errorMessage);
			gov.nih.nci.cagrid.caxchange.context.service.globus.resource.CaXchangeResponseServiceResourceHome ctxResourceHome = getCaXchangeResponseServiceResourceHome();
			CaXchangeResponseServiceResource resource = (CaXchangeResponseServiceResource) ctxResourceHome
					.find(resKey);
			resource.setCaXchangeResponseMessage(response);
		} catch (Exception e) {
			logger.error("Error updating resource with the error response.", e);
			throw e;
		}
	}

	/**
	 * Register listeners for the response queue to update the Resources.
	 * 
	 * @throws Exception
	 */
	public void registerResponseListeners() throws Exception {
		try {
			CaXchangeRequestProcessorConfiguration configuration = getConfiguration();
			CaXchangeExternalProperties properties = CaXchangeExternalProperties
					.getInstance();
			String outboundJmsBrokerUrl = properties
					.getProperty("outbound.jms.brokerURL");
			if (outboundJmsBrokerUrl == null) {
				outboundJmsBrokerUrl = configuration
						.getCaXchangeInboundBrokerURL();
			}
			if (connectionFactory == null) {
				connectionFactory = new ActiveMQConnectionFactory(
						outboundJmsBrokerUrl);
			}
			if (replyDestination == null) {
				replyDestination = new ActiveMQQueue(replyDestinationName);
			}
			Connection connection = connectionFactory.createConnection();
			connection.start();
			Session session = connection.createSession(false,
					Session.AUTO_ACKNOWLEDGE);
			if ((responseListeners == null)
					|| (responseListeners.entrySet().size() == 0)) {
				logger.info("Listener initiated.");
				responseListeners = new HashMap(1);
				MessageConsumer consumer = session
						.createConsumer(replyDestination);
				CaxchangeResponseListener listener = new CaxchangeResponseListener();
				listener
						.setResourceHome(getCaXchangeResponseServiceResourceHome());
				consumer.setMessageListener(listener);
				CaxchangeResponseExceptionListener el = new CaxchangeResponseExceptionListener();
				connection.setExceptionListener(el);
				responseListeners.put(el, connection);
			}
		} catch (Exception ex) {
			logger.error("Error registering response listeners.", ex);
			throw new Exception("Error registers response listeners.", ex);
		} finally {
		}
	}

	/**
	 * 
	 * @param caXchangeRequestMessage
	 * @return
	 * @throws RemoteException
	 * @throws gov.nih.nci.cagrid.caxchange.stubs.types.CaXchangeFault
	 */
	public gov.nih.nci.caxchange.ResponseMessage processRequestSynchronously(
			gov.nih.nci.caxchange.Message requestMessageFromClient)
			throws RemoteException,
			gov.nih.nci.cagrid.caxchange.stubs.types.CaXchangeFault {
		logger.debug("Request in processRequestSynchronously: "
				+ requestMessageFromClient);
		try {
			CaXchangeResponseServiceResourceHome ctxResourceHome = getCaXchangeResponseServiceResourceHome();
			ResourceKey resKey = ctxResourceHome.createResource();

			requestMessageFromClient.getMetadata().setCaXchangeIdentifier(
					resKey.getValue().toString());

			String caller = SecurityManager.getManager().getCaller();
			if ((caller == null) || (caller.equals("<anonymous>"))) {
				return buildErrorResponse(requestMessageFromClient,
						CaxchangeErrors.PERMISSION_DENIED_FAULT,
						"Unable to get the identity of the caller.Caller identity:"
								+ caller);
			}
			logger.debug("Sending message for the caller:" + caller);
			requestMessageFromClient.getMetadata().getCredentials()
					.setGridIdentifier(caller);

			try {
				CaXchangeRequestProcessorConfiguration configuration = getConfiguration();
				CaXchangeExternalProperties properties = CaXchangeExternalProperties
						.getInstance();
				String caXchangeSynchronousServiceURL = properties
						.getProperty("synchronous.caxchange.serviceURL");
				logger.debug("CAXCHANGE SERVICE URL: "+caXchangeSynchronousServiceURL);
				if (caXchangeSynchronousServiceURL == null) {
					caXchangeSynchronousServiceURL = configuration
							.getCaXchangeSynchronousServiceURL();
				}
				logger.debug("CAXCHANGE SERVICE URL: "+caXchangeSynchronousServiceURL);

				gov.nih.nci.caxchange.synchronous.SynchronousRequestServiceLocator locator = new gov.nih.nci.caxchange.synchronous.SynchronousRequestServiceLocator();

				gov.nih.nci.caxchange.synchronous.CaXchangeRequestProcessorPortType caXchangeRequestProcessorPortType = locator
						.getsoap(new URL(caXchangeSynchronousServiceURL));
				gov.nih.nci.caxchange.synchronous.ProcessRequestSynchronouslyRequest processRequestSynchronouslyRequest = new gov.nih.nci.caxchange.synchronous.ProcessRequestSynchronouslyRequest();
				gov.nih.nci.caxchange.synchronous.Message requestMessageToESB = buildRequestMessageToESB(requestMessageFromClient);
				//gov.nih.nci.caxchange.synchronous.Message requestMessageToESB = new Message();
				//logger.debug("BEFORE BEAN UTILS COPY");
				//BeanUtils.copyProperties(requestMessageToESB, requestMessageFromClient);
				
				logger.debug("URI: "
						+ requestMessageToESB.getRequest().getBusinessMessagePayload()
								.getXmlSchemaDefinition().getPath());

				processRequestSynchronouslyRequest
						.setCaXchangeRequestMessage(requestMessageToESB);

				logger.info("Before sending messge " + new Date().getTime());
				ProcessRequestSynchronouslyResponse processRequestSynchronouslyResponse = caXchangeRequestProcessorPortType
						.processRequestSynchronously(processRequestSynchronouslyRequest);
				logger.info("After sending messge " + new Date().getTime());
				gov.nih.nci.caxchange.synchronous.ResponseMessage responseMessageFromESB = processRequestSynchronouslyResponse
						.getCaXchangeResponseMessage();

				ResponseMessage responseMessageToClient = buildResponseMessageToClient(responseMessageFromESB);

				return responseMessageToClient;

			} catch (Exception e) {
				return buildErrorResponse(requestMessageFromClient,
						CaxchangeErrors.UNKNOWN,
						"An error occured sending message to the caXchange hub."
								+ e.getMessage());
			}

		} catch (Exception e) {
			logger.error("Error processing message request.", e);
			CaXchangeFault caXchangeFault = new CaXchangeFault();
			caXchangeFault
					.setFaultDetailString("Error processing message request."
							+ e.getMessage());
			caXchangeFault.setStackTrace(e.getStackTrace());
			throw caXchangeFault;
		}
	}

	/**
	 * Builds the Message object to be sent to the ESB, from the Message object
	 * sent by the client
	 * 
	 * @param reqMsgFromClient
	 * @return gov.nih.nci.caxchange.synchronous.Message
	 */
	private gov.nih.nci.caxchange.synchronous.Message buildRequestMessageToESB(
			gov.nih.nci.caxchange.Message reqMsgFromClient) {
		logger.debug("In - buildRequestMessageToESB method");

		gov.nih.nci.caxchange.synchronous.Message requestMessageToESB = new gov.nih.nci.caxchange.synchronous.Message();
		try {
			// Create and set the metadata
			gov.nih.nci.caxchange.synchronous.Metadata metadata = new gov.nih.nci.caxchange.synchronous.Metadata();
			logger.debug("1");
			if (reqMsgFromClient.getMetadata().getTransactionControl() != null) {
				logger.debug("REQUEST TRANSACTION CONTROL FROM CLIENT IS NULL");
				TransactionControls transactionControls = TransactionControls
						.fromString(reqMsgFromClient.getMetadata()
								.getTransactionControl().toString());
				logger.debug("1.1");
				metadata.setTransactionControl(transactionControls);
			} else {
				metadata.setTransactionControl(TransactionControls.PROCESS);
			}

			logger.debug("2");
			metadata.setCredentials(new Credentials(reqMsgFromClient
					.getMetadata().getCredentials()
					.getDelegatedCredentialReference(), reqMsgFromClient
					.getMetadata().getCredentials().getGridIdentifier(),
					reqMsgFromClient.getMetadata().getCredentials()
							.getGroupName(), reqMsgFromClient.getMetadata()
							.getCredentials().getPassword(), reqMsgFromClient
							.getMetadata().getCredentials().getUserName()));
			logger.debug("3");
			metadata.setCaXchangeIdentifier(reqMsgFromClient.getMetadata()
					.getCaXchangeIdentifier());
			logger.debug("4");
			metadata.setExternalIdentifier(reqMsgFromClient.getMetadata()
					.getExternalIdentifier());
			logger.debug("5");
			metadata.setOperationName(reqMsgFromClient.getMetadata()
					.getOperationName());
			logger.debug("6");
			metadata.setServiceType(reqMsgFromClient.getMetadata()
					.getServiceType());
			requestMessageToESB.setMetadata(metadata);
			logger.debug("7");

			// Create and set the request
			Request request = new Request();
			logger.debug("URI: "
					+ reqMsgFromClient.getRequest().getBusinessMessagePayload()
							.getXmlSchemaDefinition().getPath());
			//URI uri = new URI();
			//uri.setPath("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain");
			request.setBusinessMessagePayload(new MessagePayload(
					reqMsgFromClient.getRequest().getBusinessMessagePayload()
							.get_any(), reqMsgFromClient.getRequest().getBusinessMessagePayload()
							.getXmlSchemaDefinition()));
			logger.debug("URI: "
					+ request.getBusinessMessagePayload()
							.getXmlSchemaDefinition().getPath());
			logger.debug("8");

			requestMessageToESB.setRequest(request);
			logger.debug("Out - buildRequestMessageToESB method");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return requestMessageToESB;
	}

	/**
	 * Builds the ResponseMessage object to be sent to the client, from the
	 * Message object returned by the ESB
	 * 
	 * @param respMsgFromESB
	 * @return ResponseMessage
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private ResponseMessage buildResponseMessageToClient(
			gov.nih.nci.caxchange.synchronous.ResponseMessage respMsgFromESB)
			throws IllegalAccessException, InvocationTargetException {
		logger.debug("In - buildResponseMessageToClient method");
		ResponseMessage responseMessageToClient = new ResponseMessage();

		// create and set the response metadata
		responseMessageToClient.setResponseMetadata(new ResponseMetadata(
				respMsgFromESB.getResponseMetadata().getCaXchangeIdentifier(),
				respMsgFromESB.getResponseMetadata().getExternalIdentifier()));

		// create and set the response
		ErrorDetails errorDetails = new ErrorDetails(respMsgFromESB
				.getResponse().getCaXchangeError().getErrorCode(),
				respMsgFromESB.getResponse().getCaXchangeError()
						.getErrorDescription());
		Statuses statuses = Statuses.fromString(respMsgFromESB.getResponse()
				.getResponseStatus().toString());
		TargetResponseMessage[] targetResponseMessage = new TargetResponseMessage[1];
		BeanUtils.copyProperties(targetResponseMessage, respMsgFromESB
				.getResponse().getTargetResponse());
		logger
				.debug("AFTER BEANUTILS CALL. TargetResponseMessage object copied successfully");
		gov.nih.nci.caxchange.Response response = new gov.nih.nci.caxchange.Response(
				errorDetails, statuses, targetResponseMessage);
		responseMessageToClient.setResponse(response);
		logger.debug("Out - buildResponseMessageToClient method");
		return responseMessageToClient;
	}

	/**
	 * If there is an error in the processing, this method gets called which
	 * builds the ResponseMessage object using caXchangeRequestmessage,
	 * errorCode, and errormessage
	 * 
	 * @param caXchangeRequestMessage
	 * @param errorCode
	 * @param errorMessage
	 * @return ResponseMessage
	 * @throws Exception
	 */
	private ResponseMessage buildErrorResponse(
			gov.nih.nci.caxchange.Message caXchangeRequestMessage,
			String errorCode, String errorMessage) throws Exception {
		try {
			ResponseHandler responseHandler = new ResponseHandler();
			ResponseMessage response = responseHandler.getResponseFromError(
					caXchangeRequestMessage, errorCode, errorMessage);
			return response;
		} catch (Exception e) {
			logger.error("Error updating resource with the error response.", e);
			throw e;
		}
	}

}
