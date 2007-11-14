package gov.nih.nci.cagrid.caxchange.listener;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageConsumer;

import javax.jms.Session;

import org.apache.activemq.command.ActiveMQQueue;

public class CaxchangeConnectedListeners {
    protected static Map<CaxchangeResponseExceptionListener,Connection> connections=null;
    protected static ConnectionFactory connectionFactory = null;
    protected static String replyQueueName=null;
    protected static Destination replyDestination=null;
    protected static gov.nih.nci.cagrid.caxchange.context.service.globus.resource.BaseResourceHome ctxResourceHome=null;
    

    
    public CaxchangeConnectedListeners() {
    }
    
    public static void setConnectionFactory(ConnectionFactory cf) {
        connectionFactory= cf;
    }
    
    public static void setReplyQueueName(String rqn) {
        replyQueueName = rqn;
    }
    
    public static void setCtxResourceHome( gov.nih.nci.cagrid.caxchange.context.service.globus.resource.BaseResourceHome crh) {
        ctxResourceHome = crh;
    }
    
    public static void startListening( ) throws Exception {
        if (connectionFactory==null) {
            throw new Exception("Connection factory to the jms not specified.");
        }
        if (replyQueueName==null) {
            throw new Exception("Response queue name not specified.");
        }
        if (ctxResourceHome==null) {
            throw new Exception("Response queue name not specified.");
        }        
        System.out.println("Starting to listen.");
        replyDestination = new ActiveMQQueue(replyQueueName);
        connections = new HashMap<CaxchangeResponseExceptionListener, Connection>();
        addListener();

    }
    
    protected static void addListener() throws Exception {
        System.out.println("Adding a listener,");
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageConsumer consumer = session.createConsumer(replyDestination);
        CaxchangeResponseListener listener = new CaxchangeResponseListener();
        listener.setResourceHome(ctxResourceHome);
        consumer.setMessageListener(listener);
        CaxchangeResponseExceptionListener exceptionListener =new CaxchangeResponseExceptionListener();
        connection.setExceptionListener(exceptionListener);
        connections.put(exceptionListener,connection);
    }
    
    public static void stopListening( ) throws Exception {

        if (connections != null) {
           Iterator iterator = connections.keySet().iterator();
            while(iterator.hasNext()) {
                CaxchangeResponseExceptionListener el = (CaxchangeResponseExceptionListener)iterator.next();
                Connection connection = connections.get(el);
                if (connection != null) {
                    connection.stop();
                    connection.close();
                }
                connections.remove(el);
            }
            connections=null;
        }

    }    
}
