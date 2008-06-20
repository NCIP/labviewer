package gov.nih.nci.cagrid.caxchange.service;

import gov.nih.nci.cagrid.caxchange.context.service.globus.resource.CaXchangeResponseServiceResource;
import gov.nih.nci.cagrid.caxchange.listener.CaxchangeResponseExceptionListener;
import gov.nih.nci.cagrid.caxchange.listener.CaxchangeResponseListener;

import gov.nih.nci.cagrid.caxchange.listener.ResponseHandler;

import gov.nih.nci.caxchange.ResponseMessage;

import java.io.StringWriter;

import java.rmi.RemoteException;

import java.util.HashMap;
import java.util.Map;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;

import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;

import javax.jms.Session;

import javax.jms.TextMessage;

import javax.xml.namespace.QName;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.axis.encoding.SerializationContext;
import org.apache.axis.encoding.ser.BeanSerializer;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import org.globus.wsrf.ResourceKey;

/**
 * TODO:I am the service side implementation class.  IMPLEMENT AND DOCUMENT ME
 *
 * @created by Introduce Toolkit version 1.1
 *
 */
public class CaXchangeRequestProcessorImpl extends CaXchangeRequestProcessorImplBase {
	protected static String brokerURL=null; //"tcp://localhost:61618";
	protected static String destinationName = null; //"caxchangeInboundQueue";
	protected static String replyDestinationName = null; //"caxchangeOutboundQueue";
	protected static Destination destination = null;
	protected static Destination replyDestination = null;
	protected static Logger logger = LogManager.getLogger(CaXchangeRequestProcessorImpl.class.getName());
	protected static ConnectionFactory connectionFactory = null;
	//public static Map<CaxchangeResponseExceptionListener,Connection> responseListeners = null;

	public static Connection connection=null;
	public static Session session=null;
	public static MessageConsumer consumer=null;
	public static CaxchangeResponseListener listener=null;

	public static int count=0;

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
	public gov.nih.nci.cagrid.caxchange.context.stubs.types.CaXchangeResponseServiceReference processRequestAsynchronously(gov.nih.nci.caxchange.Message caXchangeRequestMessage) throws RemoteException {
		try {
			gov.nih.nci.cagrid.caxchange.context.service.globus.resource.BaseResourceHome ctxResourceHome = getCaXchangeResponseServiceResourceHome();
			ResourceKey resKey = ctxResourceHome.createResource();
			caXchangeRequestMessage.getMetadata().setCaXchangeIdentifier(resKey.getValue().toString());
			registerResponseListeners();
			try { //Sending the request to the caXchange inbound queue
				sendMessage(caXchangeRequestMessage);
			}catch(Exception e) {
				updateErrorResponse(caXchangeRequestMessage, resKey);
			}


			logger.info("Request send to ESB for key "+resKey);

			return ctxResourceHome.getResourceReference(resKey);
		}
		catch(Exception e) {
			logger.error("Error processing message request.",e);
			throw new RemoteException("Error processing message request.",e);
		}
	}
	/**
	 * Send the message to the caXchange inbound JMS component.
	 *
	 * @param caXchangeRequestMessage
	 * @throws Exception
	 */
	protected void sendMessage(gov.nih.nci.caxchange.Message caXchangeRequestMessage) throws Exception {
		Connection conn = null;
		Session session = null;
		try  {
			ServiceConfiguration configuration = getConfiguration();
			if (connectionFactory == null) {
				connectionFactory = new ActiveMQConnectionFactory(configuration.getCaXchangeInboundBrokerURL());
			}
			if (destination == null) {
				destination = new ActiveMQQueue(configuration.getCaXchangeInboundDestination());
			}

			conn = connectionFactory.createConnection("system", "manager");
			session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
			MessageProducer producer = session.createProducer(destination);
			RequestGeneratorHelper rgh = new RequestGeneratorHelper();
			rgh.setCaxchangeRequest(caXchangeRequestMessage);
			String req = rgh.getRequestForCaxchange();
			/*
            BeanSerializer beanSerializer = (BeanSerializer)caXchangeRequestMessage.getSerializer("",gov.nih.nci.caxchange.Message.class,new QName("http://caXchange.nci.nih.gov/messaging", "caXchangeRequestMessage"));
            StringWriter stringWriter = new StringWriter();
            SerializationContext sc = new SerializationContext(stringWriter);

            beanSerializer.serialize(new QName("http://caXchange.nci.nih.gov/messaging", "caXchangeRequestMessage"), null, caXchangeRequestMessage, sc);
			 */
			TextMessage message = session.createTextMessage();
			logger.debug("Sending message:"+req);
			message.setText(req);
			producer.send(message);
		} catch (Exception ex)  {
			logger.error("Error sending message to the ESB.", ex);
			ex.printStackTrace();
			throw new Exception("Error sending message to the ESB.", ex);
		} finally  {
			if (conn != null) {
				conn.close();
			}
			if (session != null){
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
	public void updateErrorResponse(gov.nih.nci.caxchange.Message caXchangeRequestMessage, ResourceKey resKey) throws Exception {
		try {
			ResponseHandler responseHandler = new ResponseHandler();
			ResponseMessage response = responseHandler.getResponseFromError(caXchangeRequestMessage, "ERROR_SENDING_REQUEST", "An error happened sending the request to the inbound queue.");
			gov.nih.nci.cagrid.caxchange.context.service.globus.resource.BaseResourceHome ctxResourceHome = getCaXchangeResponseServiceResourceHome();
			CaXchangeResponseServiceResource resource= (CaXchangeResponseServiceResource)ctxResourceHome.find(resKey);
			resource.setCaXchangeResponseMessageValue(response);
		}catch(Exception e) {
			logger.error("Error updating resource with the error response.", e);
			throw e;
		}
	}

	/**
	 * Register listeners for the response queue to update the Resources.
	 *
	 * @throws Exception
	 */
	public synchronized void registerResponseListeners() throws Exception {
		count++;
		logger.debug("in registerresponseListeners: count="+count);
		try {
			if(connection==null || session==null || consumer==null || listener==null){
				ServiceConfiguration configuration = getConfiguration();
				if (connectionFactory == null) {
					connectionFactory = new ActiveMQConnectionFactory(configuration.getCaXchangeInboundBrokerURL());
				}
				if (replyDestination == null) {
					replyDestination = new ActiveMQQueue(configuration.getCaXchangeResponseDestination());
				}
				connection = connectionFactory.createConnection("system","manager");
				CaxchangeResponseExceptionListener el = new CaxchangeResponseExceptionListener();
				connection.setExceptionListener(el);

				session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
				consumer = session.createConsumer(replyDestination);
				listener = new CaxchangeResponseListener();
				listener.setResourceHome(getCaXchangeResponseServiceResourceHome());
				consumer.setMessageListener(listener);
				connection.start();
				logger.info("2.Listener initiated.");
			}
		}catch (Exception ex)  {
			logger.error("Error registering response listeners.", ex);
			ex.printStackTrace();
			throw new Exception("Error registers response listeners.", ex);
		} finally  {
		}
	}

}


