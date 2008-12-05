package gov.nih.nci.nih.servicemix.bean.routing;

import gov.nih.nci.caXchange.CaxchangeConstants;
import gov.nih.nci.nih.servicemix.bean.CaxchangeBeanServiceUnitTest;

import java.io.InputStream;
import java.util.Date;

import javax.jbi.messaging.ExchangeStatus;
import javax.jbi.messaging.InOut;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.dom.DOMSource;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.servicemix.client.DefaultServiceMixClient;
import org.w3c.dom.Document;

public class StoreOriginalMessageServiceBeanTest extends CaxchangeBeanServiceUnitTest {
	Logger logger = LogManager.getLogger(StoreOriginalMessageServiceBeanTest.class);
	public void testStoreMessage() throws Exception {
    	DefaultServiceMixClient client = new DefaultServiceMixClient(jbi);
    	InOut me = client.createInOutExchange();
    	me.getInMessage().setContent(getSource("testmessage.xml"));
    	me.setProperty(CaxchangeConstants.EXCHANGE_CORRELATIONID, new Date().toString());
        me.setService(new QName("http://nci.nih.gov/caXchange",
		"storeOriginalMessage"));
        me.setOperation(new QName("http://nci.nih.gov/caXchange",
		"storeOriginalMessage"));
        boolean sent = client.sendSync(me);
        assertTrue(sent);
		System.out.println(me);
		assertEquals(ExchangeStatus.ACTIVE, me.getStatus());
		assertTrue(me.getFault() == null);

	}

	
}
