package gov.nih.nci.caxchange.jms.inbound;

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
		
		try {
		
		
			
			
		this.assertTrue(true);
		} catch (Exception ex) {
			assertTrue(false);
		}
	}

	@Override
	public TestResult run() {
		// TODO Auto-generated method stub
		return super.run();
	}

}
