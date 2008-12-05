package gov.nih.nci.nih.servicemix.bean.routing;

import gov.nih.nci.caXchange.CaxchangeConstants;
import gov.nih.nci.nih.servicemix.bean.CaxchangeBeanServiceUnitTest;

import java.io.InputStream;
import java.util.Date;

import javax.jbi.messaging.ExchangeStatus;
import javax.jbi.messaging.InOut;
import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamSource;

import org.apache.servicemix.client.DefaultServiceMixClient;
import org.apache.servicemix.tck.SpringTestSupport;
import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.context.support.AbstractXmlApplicationContext;

public class CaxchangeAggregatorListenerTest extends CaxchangeBeanServiceUnitTest {

	public void testAggregatorListener() throws Exception{
    	DefaultServiceMixClient client = new DefaultServiceMixClient(jbi);
    	
       	InOut originalExchange = client.createInOutExchange();
       	String corrId = "testmessage"+new Date();
        originalExchange.getInMessage().setContent(getSource("testmessage.xml"));
        originalExchange.setProperty(CaxchangeConstants.EXCHANGE_CORRELATIONID, corrId);
        originalExchange.setService(new QName("http://nci.nih.gov/caXchange",
		"storeOriginalMessage"));
        originalExchange.setOperation(new QName("http://nci.nih.gov/caXchange",
		"storeOriginalMessage"));
        boolean sent = client.sendSync(originalExchange);
        assertTrue(sent);
        
    	InOut me = client.createInOutExchange();
    	me.getInMessage().setContent(getSource("aggregatemessage.xml"));
        me.setService(new QName("http://nci.nih.gov/caXchange",
		"aggregatorListener"));
        me.setOperation(new QName("http://nci.nih.gov/caXchange",
		"aggregatorListener"));
        me.getInMessage().setProperty(CaxchangeConstants.ORIGINAL_EXCHANGE_CORRELATIONID, corrId);
        sent = client.sendSync(me);
        assertTrue(sent);
		assertEquals(ExchangeStatus.ACTIVE, me.getStatus());
	}
	


}
