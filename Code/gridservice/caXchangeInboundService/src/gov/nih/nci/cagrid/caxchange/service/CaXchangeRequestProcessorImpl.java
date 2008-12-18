package gov.nih.nci.cagrid.caxchange.service;

import gov.nih.nci.caXchange.CaxchangeConstants;
import gov.nih.nci.caXchange.CaxchangeErrors;
import gov.nih.nci.cagrid.caxchange.context.service.globus.resource.CaXchangeResponseServiceResource;
import gov.nih.nci.cagrid.caxchange.listener.CaxchangeConnectedListeners;
import gov.nih.nci.cagrid.caxchange.listener.CaxchangeResponseExceptionListener;
import gov.nih.nci.cagrid.caxchange.listener.CaxchangeResponseListener;

import gov.nih.nci.cagrid.caxchange.listener.ResponseHandler;

import gov.nih.nci.caxchange.ResponseMessage;

import java.io.StringWriter;

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

import javax.xml.namespace.QName;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.axis.encoding.SerializationContext;
import org.apache.axis.encoding.ser.BeanSerializer;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import org.globus.wsrf.ResourceKey;
import org.globus.wsrf.security.SecurityManager;

/**
 * TODO:I am the service side implementation class.  IMPLEMENT AND DOCUMENT ME
 *
 * @created by Introduce Toolkit version 1.1
 *
 */
public class CaXchangeRequestProcessorImpl extends CaXchangeRequestProcessorImplBase {
    protected static String brokerURL=null; //"tcp://localhost:61618";
    protected static String destinationName = "caxchangeInboundQueue";
    protected static String replyDestinationName = "caxchangeOutboundQueue";
    protected static Destination destination = null;
    protected static Destination replyDestination = null;
    protected static Logger logger = LogManager.getLogger(CaXchangeRequestProcessorImpl.class.getName());
    protected static ConnectionFactory connectionFactory = null;
    protected static Connection connection = null;
    public static Map<CaxchangeResponseExceptionListener,Connection> responseListeners = null;

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
	  logger.debug("request:"+caXchangeRequestMessage);
      try {
          gov.nih.nci.cagrid.caxchange.context.service.globus.resource.CaXchangeResponseServiceResourceHome ctxResourceHome = getCaXchangeResponseServiceResourceHome();
          ResourceKey resKey = ctxResourceHome.createResource();
          logger.info("Performance Request Received,"+resKey.getValue()+","+caXchangeRequestMessage.getMetadata().getMessageType()+","+new java.util.Date().getTime());
          caXchangeRequestMessage.getMetadata().setCaXchangeIdentifier(resKey.getValue().toString());
  		  String caller = SecurityManager.getManager().getCaller();
  		  if ((caller == null) || (caller.equals("<anonymous>"))) {
  			updateErrorResponse(caXchangeRequestMessage, resKey, CaxchangeErrors.PERMISSION_DENIED_FAULT, "Unable to get the identity of the caller.Caller identity:"+caller);
		  }
          caXchangeRequestMessage.getMetadata().getCredentials().setGridIdentifier(caller);
          try { //Sending the request to the caXchange inbound queue
        	 logger.info("Before sending messge "+new Date().getTime());
             sendMessage(caXchangeRequestMessage);
             logger.info("Before sending messge "+new Date().getTime());
          }catch(Exception e) {
              updateErrorResponse(caXchangeRequestMessage, resKey, CaxchangeErrors.UNKNOWN, "An error occured sending message to the caXchange hub."+e.getMessage());
          }

          if ((responseListeners==null)||(responseListeners.size()==0)) {
              registerResponseListeners();
          }
          logger.debug("Request send to ESB for key "+resKey);

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
        Session session = null;
        try  {
        	CaXchangeRequestProcessorConfiguration configuration = getConfiguration();
            CaXchangeExternalProperties properties = CaXchangeExternalProperties.getInstance();
            String inboundJmsBrokerUrl = properties.getProperty("inbound.jms.brokerURL");
            if (inboundJmsBrokerUrl==null) {
            	inboundJmsBrokerUrl = configuration.getCaXchangeInboundBrokerURL();
            }
            if (connectionFactory == null) {
                connectionFactory = new ActiveMQConnectionFactory(inboundJmsBrokerUrl);
            }
            if (destination == null) {
                destination = new ActiveMQQueue(destinationName);
            }
            if (connection == null) {
               connection = connectionFactory.createConnection();
            }
            try {
               session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            }catch(JMSException connectionFailed) {
            	//Connection may have failed try to create a new connection:
            	connection = connectionFactory.createConnection();
            	session  = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            }
            MessageProducer producer = session.createProducer(destination);
            RequestGeneratorHelper rgh = new RequestGeneratorHelper();
            rgh.setCaxchangeRequest(caXchangeRequestMessage);
            String req = rgh.getRequestForCaxchange();
            TextMessage message = session.createTextMessage();
            logger.debug("Sending message:"+req);
            message.setText(req);
            producer.send(message);
        } catch (Exception ex)  {
            logger.error("Error sending message to the ESB.", ex);
            responseListeners= null;
            throw new Exception("Error sending message to the ESB.", ex);
        } finally  {
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
  public void updateErrorResponse(gov.nih.nci.caxchange.Message caXchangeRequestMessage, ResourceKey resKey, String errorCode, String errorMessage) throws Exception {
      try {
         ResponseHandler responseHandler = new ResponseHandler();
         ResponseMessage response = responseHandler.getResponseFromError(caXchangeRequestMessage, errorCode, errorMessage);
         gov.nih.nci.cagrid.caxchange.context.service.globus.resource.CaXchangeResponseServiceResourceHome ctxResourceHome = getCaXchangeResponseServiceResourceHome();
         CaXchangeResponseServiceResource resource= (CaXchangeResponseServiceResource)ctxResourceHome.find(resKey);
         resource.setCaXchangeResponseMessage(response);
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
    public void registerResponseListeners() throws Exception {
        try {
        	CaXchangeRequestProcessorConfiguration configuration = getConfiguration();
            CaXchangeExternalProperties properties = CaXchangeExternalProperties.getInstance();
            String outboundJmsBrokerUrl = properties.getProperty("outbound.jms.brokerURL");
            if (outboundJmsBrokerUrl==null) {
            	outboundJmsBrokerUrl = configuration.getCaXchangeInboundBrokerURL();
            }
            if (connectionFactory == null) {
                connectionFactory = new ActiveMQConnectionFactory(outboundJmsBrokerUrl);
            }
            if (replyDestination == null) {
                replyDestination = new ActiveMQQueue(replyDestinationName);
            }
            Connection connection = connectionFactory.createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            if ((responseListeners == null)||(responseListeners.entrySet().size()==0)){
                logger.info("Listener initiated.");
                responseListeners = new HashMap(1);
               MessageConsumer consumer = session.createConsumer(replyDestination);
               CaxchangeResponseListener listener = new CaxchangeResponseListener();
               listener.setResourceHome(getCaXchangeResponseServiceResourceHome());
               consumer.setMessageListener(listener);
               CaxchangeResponseExceptionListener el = new CaxchangeResponseExceptionListener();
               connection.setExceptionListener(el);
               responseListeners.put(el,connection);
            }
        }catch (Exception ex)  {
            logger.error("Error registering response listeners.", ex);
            throw new Exception("Error registers response listeners.", ex);
        } finally  {
        }
    }

}

