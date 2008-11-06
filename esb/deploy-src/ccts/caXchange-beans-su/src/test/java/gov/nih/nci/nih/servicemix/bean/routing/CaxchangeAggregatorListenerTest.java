package gov.nih.nci.nih.servicemix.bean.routing;

import java.io.InputStream;

import javax.jbi.messaging.ExchangeStatus;
import javax.jbi.messaging.InOut;
import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamSource;

import org.apache.servicemix.client.DefaultServiceMixClient;
import org.apache.servicemix.tck.SpringTestSupport;
import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.context.support.AbstractXmlApplicationContext;

public class CaxchangeAggregatorListenerTest extends SpringTestSupport {

	public void testAggregatorListener() throws Exception{
    	DefaultServiceMixClient client = new DefaultServiceMixClient(jbi);
    	InputStream fis = getClass().getClassLoader().getResourceAsStream("aggregatemessage.xml");
    	InOut me = client.createInOutExchange();
    	me.getInMessage().setContent(new StreamSource(fis));
        me.setService(new QName("http://nci.nih.gov/caXchange",
		"aggregatorListener"));
        me.setOperation(new QName("http://nci.nih.gov/caXchange",
		"aggregatorListener"));
        client.sendSync(me);
		System.out.println(me);
		assertEquals(ExchangeStatus.ACTIVE, me.getStatus());
		assertTrue(me.getMessage("out") != null);
	}
	
	@Override
	protected AbstractXmlApplicationContext createBeanFactory() {
		// TODO Auto-generated method stub
		return new ClassPathXmlApplicationContext("spring.xml");
	}

}
