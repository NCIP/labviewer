package gov.nih.nci.cagrid.caxchange.service;

import gov.nih.nci.caXchange.CaxchangeErrors;
import gov.nih.nci.cagrid.caxchange.context.service.globus.resource.CaXchangeResponseServiceResource;
import gov.nih.nci.cagrid.caxchange.context.service.globus.resource.CaXchangeResponseServiceResourceHome;
import gov.nih.nci.cagrid.caxchange.listener.CaxchangeResponseExceptionListener;
import gov.nih.nci.cagrid.caxchange.listener.CaxchangeResponseListener;
import gov.nih.nci.cagrid.caxchange.listener.ResponseHandler;
import gov.nih.nci.cagrid.caxchange.stubs.types.CaXchangeFault;
import gov.nih.nci.cagrid.common.Utils;
import gov.nih.nci.caxchange.ErrorDetails;
import gov.nih.nci.caxchange.MessageStatuses;
import gov.nih.nci.caxchange.Response;
import gov.nih.nci.caxchange.ResponseMessage;
import gov.nih.nci.caxchange.ResponseMetadata;
import gov.nih.nci.caxchange.Statuses;
import gov.nih.nci.caxchange.TargetResponseMessage;
import gov.nih.nci.caxchange.synchronous.SynchronousRequestServiceStub;
import gov.nih.nci.caxchange.synchronous.SynchronousRequestServiceStub.CaXchangeRequestMessage;
import gov.nih.nci.caxchange.synchronous.SynchronousRequestServiceStub.CaXchangeResponseMessage;
import gov.nih.nci.caxchange.synchronous.SynchronousRequestServiceStub.Credentials;
import gov.nih.nci.caxchange.synchronous.SynchronousRequestServiceStub.CredentialsChoice_type0;
import gov.nih.nci.caxchange.synchronous.SynchronousRequestServiceStub.Message;
import gov.nih.nci.caxchange.synchronous.SynchronousRequestServiceStub.MessagePayload;
import gov.nih.nci.caxchange.synchronous.SynchronousRequestServiceStub.Metadata;
import gov.nih.nci.caxchange.synchronous.SynchronousRequestServiceStub.Request;
import gov.nih.nci.caxchange.synchronous.SynchronousRequestServiceStub.TransactionControls;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMNamespace;
import org.apache.axiom.om.impl.builder.StAXOMBuilder;
import org.apache.axiom.om.impl.llom.OMNamespaceImpl;
import org.apache.axis.message.MessageElement;
import org.apache.axis2.client.Options;
import org.apache.axis2.databinding.types.URI;
import org.apache.axis2.databinding.utils.writer.MTOMAwareXMLSerializer;
import org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.globus.wsrf.ResourceKey;
import org.globus.wsrf.security.SecurityManager;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * 
 * @created by Introduce Toolkit version 1.1
 * 
 */
public class CaXchangeRequestProcessorImpl extends
		CaXchangeRequestProcessorImplBase {
	protected static Pattern emptyNamespacePattern = Pattern.compile("xmlns:.*=\"\"|xmlns=\"\"");
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

				if (caXchangeSynchronousServiceURL == null) {
					caXchangeSynchronousServiceURL = configuration
							.getCaXchangeSynchronousServiceURL();
				}

				CaXchangeRequestMessage caXchangeRequestMessageToESB = new CaXchangeRequestMessage();
				Message requestMessageToESB = buildRequestMessageToESB(requestMessageFromClient);

				caXchangeRequestMessageToESB
						.setCaXchangeRequestMessage(requestMessageToESB);

				logger.info("Before sending messge " + new Date().getTime());
				SynchronousRequestServiceStub synchronousRequestServiceStub = new SynchronousRequestServiceStub(
						caXchangeSynchronousServiceURL);
				CaXchangeResponseMessage caXchangeResponseMessageFromESB = synchronousRequestServiceStub
						.processRequestSynchronously(caXchangeRequestMessageToESB);
				logger.info("After sending messge " + new Date().getTime());

				ResponseMessage responseMessageToClient = buildResponseMessageToClient(caXchangeResponseMessageFromESB
						.getCaXchangeResponseMessage());
				StringWriter stringWriter = new StringWriter();
				if (logger.isDebugEnabled()) {
				   Utils.serializeObject(responseMessageToClient, new QName(
					    	"http://caXchange.nci.nih.gov/messaging",
						    "caXchangeResponseMessage"), stringWriter);
				   logger.debug(stringWriter);
				}
				return responseMessageToClient;

			} catch (Exception e) {
				logger
						.error(
								"An error occured sending message to the caXchange hub.",
								e);
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
	private Message buildRequestMessageToESB(
			gov.nih.nci.caxchange.Message reqMsgFromClient) {
		logger.debug("In - buildRequestMessageToESB method");

		Message requestMessageToESB = new Message();
		try {
			// Create and set the metadata
			Metadata metadata = new Metadata();
			metadata.setTransactionControl(TransactionControls.PROCESS);

			// build the credentials object. Only one of the credential choices
			// can be set the last choice values will reset the other choices in
			// the group
			Credentials credentials = new SynchronousRequestServiceStub.Credentials();
			CredentialsChoice_type0 credentialsChoice_type0 = new SynchronousRequestServiceStub.CredentialsChoice_type0();
			if (reqMsgFromClient.getMetadata().getCredentials()
					.getGridIdentifier() != null) {
				if (credentialsChoice_type0 == null) {
				}
				credentialsChoice_type0.setGridIdentifier(reqMsgFromClient
						.getMetadata().getCredentials().getGridIdentifier());
			} else if (reqMsgFromClient.getMetadata().getCredentials()
					.getGroupName() != null) {
				credentialsChoice_type0.setGroupName(reqMsgFromClient
						.getMetadata().getCredentials().getGroupName());
			} else {
				credentialsChoice_type0.setUserName(reqMsgFromClient
						.getMetadata().getCredentials().getUserName());
			}
			credentials.setCredentialsChoice_type0(credentialsChoice_type0);
			credentials.setDelegatedCredentialReference(reqMsgFromClient
					.getMetadata().getCredentials()
					.getDelegatedCredentialReference());

			credentials.setPassword(reqMsgFromClient.getMetadata()
					.getCredentials().getPassword());

			metadata.setCredentials(credentials);
			metadata.setCaXchangeIdentifier(reqMsgFromClient.getMetadata()
					.getCaXchangeIdentifier());
			metadata.setExternalIdentifier(reqMsgFromClient.getMetadata()
					.getExternalIdentifier());
			metadata.setOperationName(reqMsgFromClient.getMetadata()
					.getOperationName());
			metadata.setServiceType(reqMsgFromClient.getMetadata()
					.getServiceType());
			requestMessageToESB.setMetadata(metadata);

			// Create and set the request
			Request request = new Request();
			MessagePayload messagePayload = new MessagePayload();

			URI uri = new URI(reqMsgFromClient.getRequest()
					.getBusinessMessagePayload().getXmlSchemaDefinition().toString()
					);
			messagePayload.setXmlSchemaDefinition(uri);

			MessageElement[] messageElement = reqMsgFromClient.getRequest()
					.getBusinessMessagePayload().get_any();
			if (messageElement != null && messageElement.length == 1) {
				String msgElementString = ((MessageElement) messageElement[0])
						.getAsString();
				XMLStreamReader parser = XMLInputFactory.newInstance()
						.createXMLStreamReader(
								new ByteArrayInputStream(msgElementString
										.getBytes()));
				StAXOMBuilder builder = new StAXOMBuilder(parser);
				OMElement documentElement = builder.getDocumentElement();
				messagePayload.setExtraElement(documentElement);
			}

			request.setBusinessMessagePayload(messagePayload);

			requestMessageToESB.setRequest(request);
			logger.debug("Out - buildRequestMessageToESB method");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return requestMessageToESB;
	}
	
	protected String replaceEmptyNamespaces(String documentAsString) {
		Matcher matcher = emptyNamespacePattern.matcher(documentAsString);
		return matcher.replaceAll("");
	}

	/**
	 * Builds the ResponseMessage object to be sent to the client, from the
	 * Message object returned by the ESB
	 * 
	 * @param respMsgFromESB
	 * @return ResponseMessage
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 */
	/*
	private ResponseMessage buildResponseMessageToClient(
			gov.nih.nci.caxchange.synchronous.SynchronousRequestServiceStub.ResponseMessage respMsgFromESB)
			throws Exception {
		XMLStreamReader xmlReader = respMsgFromESB.getPullParser(null);
		StAXOMBuilder builder = new StAXOMBuilder(xmlReader);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		XMLStreamWriter writer = XMLOutputFactory.newInstance().createXMLStreamWriter(baos);
		MTOMAwareXMLStreamWriter mtomWriter = new MTOMAwareXMLSerializer(writer);
		respMsgFromESB.serialize(gov.nih.nci.caxchange.synchronous.SynchronousRequestServiceStub.CaXchangeResponseMessage.MY_QNAME,
				                     OMAbstractFactory.getOMFactory(), mtomWriter, false);
		String responseAsString = replaceEmptyNamespaces(baos.toString());
		logger.debug("The response from ESB is:"+responseAsString);
		StringReader reader = new StringReader(responseAsString);
		ResponseMessage response = (ResponseMessage)Utils.deserializeObject(reader, ResponseMessage.class);
		return response;
		
	}
	*/
	private ResponseMessage buildResponseMessageToClient(
			gov.nih.nci.caxchange.synchronous.SynchronousRequestServiceStub.ResponseMessage respMsgFromESB)
			throws IllegalAccessException, InvocationTargetException,
			ParserConfigurationException, SAXException, IOException {
		logger.debug("In - buildResponseMessageToClient method");
		ResponseMessage responseMessageToClientL1 = new ResponseMessage();

		// create and set the response metadata
		responseMessageToClientL1.setResponseMetadata(new ResponseMetadata(
				respMsgFromESB.getResponseMetadata().getCaXchangeIdentifier(),
				respMsgFromESB.getResponseMetadata().getExternalIdentifier()));

		// create and set the response
		Response responseToClientL2 = new Response();

		Statuses statuses = Statuses.fromString(respMsgFromESB.getResponse()
				.getResponseStatus().toString());
		responseToClientL2.setResponseStatus(statuses);

		// create a response or error response based on the choice
		if (respMsgFromESB.getResponse().getResponseChoice_type0()
				.getTargetResponse() != null) {
			gov.nih.nci.caxchange.synchronous.SynchronousRequestServiceStub.TargetResponseMessage[] targetRespMsgFromESBArray = respMsgFromESB
					.getResponse().getResponseChoice_type0()
					.getTargetResponse();
			  TargetResponseMessage[] targetResponses = new TargetResponseMessage[targetRespMsgFromESBArray.length];
			  int i=0;
			  for (gov.nih.nci.caxchange.synchronous.SynchronousRequestServiceStub.TargetResponseMessage targetRespMsgFromESB:targetRespMsgFromESBArray) {
				TargetResponseMessage targetRespMsgToClientL2L1 = new TargetResponseMessage();
				targetRespMsgToClientL2L1
						.setTargetServiceIdentifier(targetRespMsgFromESB
								.getTargetServiceIdentifier());
				targetRespMsgToClientL2L1
						.setTargetServiceOperation(targetRespMsgFromESB
								.getTargetServiceOperation());

				targetRespMsgToClientL2L1
						.setTargetMessageStatus(MessageStatuses
								.fromValue(targetRespMsgFromESB
										.getTargetMessageStatus().getValue()));

				if (targetRespMsgFromESB.getTargetResponseMessageChoice_type0()
						.getTargetBusinessMessage() != null) {
					gov.nih.nci.caxchange.MessagePayload targetBusinessMsg = new gov.nih.nci.caxchange.MessagePayload();
					if (targetRespMsgFromESB.getTargetResponseMessageChoice_type0().getTargetBusinessMessage().getXmlSchemaDefinition()!= null){
					   targetBusinessMsg.setXmlSchemaDefinition(new org.apache.axis.types.URI(targetRespMsgFromESB.getTargetResponseMessageChoice_type0().getTargetBusinessMessage().getXmlSchemaDefinition().toString()));
					}

					OMElement documentElement = targetRespMsgFromESB
							.getTargetResponseMessageChoice_type0()
							.getTargetBusinessMessage().getExtraElement();
					if (documentElement != null){
					   DocumentBuilderFactory dbf = DocumentBuilderFactory
								.newInstance();
					   dbf.setNamespaceAware(true);
					   DocumentBuilder db = dbf.newDocumentBuilder();
					       logger.debug(documentElement.getNamespace().getNamespaceURI()+":"+documentElement.getNamespace().getPrefix());
                           String documentElementAsString = replaceEmptyNamespaces(documentElement.toString());
                           Document payload = db.parse(new ByteArrayInputStream(
							documentElementAsString.getBytes()));
					       logger.debug("Document as string:"+documentElementAsString);
					       MessageElement messageElement = new MessageElement(payload
							.getDocumentElement());

					   targetBusinessMsg
							.set_any( new MessageElement[]{messageElement});
					}
					/*
					 * //un- comment the block below if the schema is present in
					 * the URI if (targetRespMsgFromESB
					 * .getTargetResponseMessageChoice_type0()
					 * .getTargetBusinessMessage()
					 * .getXmlSchemaDefinition().getPath() != null) {
					 * logger.debug("IN TARGET SCHEMA NOT NULL IF LOOP");
					 * targetBusinessMsg .setXmlSchemaDefinition(new
					 * org.apache.axis.types.URI( targetRespMsgFromESB
					 * .getTargetResponseMessageChoice_type0()
					 * .getTargetBusinessMessage() .getXmlSchemaDefinition()
					 * .getPath())); }
					 */

					targetRespMsgToClientL2L1
							.setTargetBusinessMessage(targetBusinessMsg);
				} else {
					targetRespMsgToClientL2L1.setTargetError(new ErrorDetails(
							targetRespMsgFromESB
									.getTargetResponseMessageChoice_type0()
									.getTargetError().getErrorCode()
									.getErrorCodes(), targetRespMsgFromESB
									.getTargetResponseMessageChoice_type0()
									.getTargetError().getErrorDescription()));
				}
				targetResponses[i++] =   targetRespMsgToClientL2L1;
			}

			responseToClientL2
					.setTargetResponse(targetResponses);

		} else {
			ErrorDetails errorDetails = new ErrorDetails(respMsgFromESB
					.getResponse().getResponseChoice_type0()
					.getCaXchangeError().getErrorCode().getErrorCodes(),
					respMsgFromESB.getResponse().getResponseChoice_type0()
							.getCaXchangeError().getErrorDescription());
			responseToClientL2.setCaXchangeError(errorDetails);
		}
		responseMessageToClientL1.setResponse(responseToClientL2);
		logger.debug("Out - buildResponseMessageToClient method");
		return responseMessageToClientL1;
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
