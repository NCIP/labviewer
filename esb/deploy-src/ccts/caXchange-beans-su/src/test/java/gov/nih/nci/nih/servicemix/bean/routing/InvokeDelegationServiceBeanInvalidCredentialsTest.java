package gov.nih.nci.nih.servicemix.bean.routing;

import gov.nih.nci.caXchange.CaxchangeConstants;
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

public class InvokeDelegationServiceBeanInvalidCredentialsTest extends CaxchangeBeanServiceUnitTest {

    Logger logger = LogManager.getLogger(InvokeDelegationServiceBeanInvalidCredentialsTest.class);
    
	public void testInvokeDelegationServiceInvalidCredentials() throws Exception{
    	DefaultServiceMixClient client = new DefaultServiceMixClient(jbi);
    	InputStream fis = getClass().getClassLoader().getResourceAsStream("testmessageinvalidcredentials.xml");
    	InOut me = client.createInOutExchange();
    	me.getInMessage().setContent(getSource("testmessageinvalidcredentials.xml"));
        me.setService(new QName("http://nci.nih.gov/caXchange",
		"setSubjectService"));
        me.setOperation(new QName("http://nci.nih.gov/caXchange",
		"setSubjectService"));
        client.sendSync(me);
		assertEquals(ExchangeStatus.ACTIVE, me.getStatus());
		assertTrue(me.getFault() != null);
		assertNotNull(me.getFault().getProperty(CaxchangeConstants.ERROR_CODE));
		assertNotNull(me.getFault().getProperty(CaxchangeConstants.ERROR_MESSAGE));
		logger.error(me.getFault().getProperty(CaxchangeConstants.ERROR_CODE)+":"+me.getFault().getProperty(CaxchangeConstants.ERROR_MESSAGE));
	}



}
