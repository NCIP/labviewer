package gov.nih.nci.nih.servicemix.bean.routing;

import gov.nih.nci.nih.servicemix.bean.CaxchangeBeanServiceUnitTest;

import java.io.InputStream;

import javax.jbi.messaging.ExchangeStatus;
import javax.jbi.messaging.InOut;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.servicemix.client.DefaultServiceMixClient;
import org.apache.servicemix.tck.SpringTestSupport;
import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.context.support.AbstractXmlApplicationContext;
import org.w3c.dom.Document;

public class InvokeDelegationServiceBeanTest extends CaxchangeBeanServiceUnitTest {

	Logger logger = LogManager.getLogger(InvokeDelegationServiceBeanTest.class);
	
	/**
	 * Make sure a valid delegation EPR is added to the message before this test is run
	 * otherwise it will fail with the expired credentials.
	 * @throws Exception
     */
	public void testInvokeDelegationServiceValidCredentials() throws Exception{
    	DefaultServiceMixClient client = new DefaultServiceMixClient(jbi);
    	InputStream fis = getClass().getClassLoader().getResourceAsStream("testMessageValidCredentials.xml");
    	InOut me = client.createInOutExchange();
    	me.getInMessage().setContent(getSource("testMessageValidCredentials.xml"));
        me.setService(new QName("http://nci.nih.gov/caXchange",
		"setSubjectService"));
        me.setOperation(new QName("http://nci.nih.gov/caXchange",
		"setSubjectService"));
        client.sendSync(me);
		assertEquals(ExchangeStatus.ACTIVE, me.getStatus());
		assertTrue(me.getFault() == null);
	}		
	


}
