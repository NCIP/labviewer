package gov.nih.nci.nih.servicemix.bean;

import java.io.InputStream;

import javax.jbi.messaging.ExchangeStatus;
import javax.jbi.messaging.InOut;
import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamSource;

import org.apache.servicemix.client.DefaultServiceMixClient;
import org.apache.servicemix.tck.SpringTestSupport;
import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.context.support.AbstractXmlApplicationContext;

public class CaxchangeConsumerInvocationTest extends CaxchangeBeanServiceUnitTest {

	
	public void testCaXchangeConsumer() throws Exception{
    	DefaultServiceMixClient client = new DefaultServiceMixClient(jbi);
    	InOut me = client.createInOutExchange();
    	me.getInMessage().setContent(getSource("testmessage.xml"));
        me.setService(new QName("http://nci.nih.gov/caXchange",
		"caXchangeConsumer"));
        me.setOperation(new QName("http://nci.nih.gov/caXchange",
		"caXchangeConsumer"));
        client.sendSync(me);
		assertEquals(ExchangeStatus.ACTIVE, me.getStatus());
		assertTrue(me.getMessage("out") != null);
	}	
	

}
