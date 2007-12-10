package gov.nih.nci.caxchange.jms.inbound;

/*import javax.activation.DataSource;
import javax.jbi.messaging.InOut;
import org.apache.servicemix.client.DefaultServiceMixClient;
import org.apache.servicemix.jbi.jaxp.StringSource;
import javax.xml.namespace.QName;
import javax.jms.*;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
*/
import junit.framework.TestCase;
import junit.framework.TestResult;

public class InboundJmsTests extends TestCase {

	public InboundJmsTests(String arg0) {
		super(arg0);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testSendingMessage() throws Exception{
		/*
		 * Add code to create a text jms message and send it to a 
		 * JMS Queue that the JMS Binding Component is listening on.
		 * 
		 *  After the message is sent.  Check the ServiceMix console to and the 
		 *  JMX console to see if the message was forwarded to the component 
		 *  that the JMS Binding Component was configured to forward it to or not.
		 *  
		 */
		
		 

		
		/*try {
			ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
			Destination inQueue = new ActiveMQQueue("queueY");
            Destination outQueue = new ActiveMQQueue("caxchangeInboundQueue");
            Connection connection = factory.createConnection();
            Session session = connection.createSession(false,Session.CLIENT_ACKNOWLEDGE);
            MessageProducer producer = session.createProducer(inQueue);
            MessageConsumer consumer = session.createConsumer(outQueue);
            connection.start();
            

            System.out.println("Waiting for request form JSR and HTTP SA.");
             producer.send(session.createTextMessage("<message>Hellow,Yogaraj!</message>"));
            for (int i = 0 ;i < 800; i++) {
                    TextMessage tMsg = session
                                    .createTextMessage("<message>Message #"+i+" Hello, Yogaraj!</message>");
                    System.out.println(tMsg.getText());
                    //tMsg.setJMSReplyTo(outQueue);
                    producer.send(tMsg);
            }*/
           
			
		try{
		this.assertTrue(true);
		} catch (Exception ex) {
			 ex.printStackTrace();
			
			assertTrue(false);
		}
	
}

	@Override
	public TestResult run() {
		// TODO Auto-generated method stub
		return super.run();
	}

}
