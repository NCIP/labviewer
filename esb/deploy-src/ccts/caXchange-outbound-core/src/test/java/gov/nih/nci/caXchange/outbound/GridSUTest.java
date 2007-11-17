package gov.nih.nci.caXchange.outbound;

import java.io.InputStream;

import javax.jbi.messaging.ExchangeStatus;
import javax.jbi.messaging.InOut;
import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamSource;

import org.apache.servicemix.client.DefaultServiceMixClient;
import org.apache.servicemix.tck.SpringTestSupport;
import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.context.support.AbstractXmlApplicationContext;

public class GridSUTest extends SpringTestSupport {

    public void testOkExchange() throws Exception {
	DefaultServiceMixClient client = new DefaultServiceMixClient(jbi);
	InputStream fis = getClass().getResourceAsStream("request.xml");
        InOut me = client.createInOutExchange();
        me.getInMessage().setProperty("Test", "TestProperty");
        me.getInMessage().setContent(new StreamSource(fis));
        me.setService(new QName("http://servicemix.apache.org/caXchange", "test"));
        me.setOperation(new QName("http://servicemix.apache.org/caXchange", "test"));
        client.sendSync(me);
        System.out.println(me);
        assertEquals(ExchangeStatus.ACTIVE, me.getStatus());
        assertTrue(me.getMessage("out") != null);
        assertEquals("TestProperty", me.getMessage("out").getProperty("Test"));
    }

    public void testRetryingExchange() throws Exception {

    }

    public void testFauldExchange() throws Exception {

    }

    /*
     * (non-Javadoc) *
     * 
     * @see org.apache.servicemix.tck.SpringTestSupport#createBeanFactory()
     */
    @Override
    protected AbstractXmlApplicationContext createBeanFactory() {
	return new ClassPathXmlApplicationContext("spring.xml");
    }
}
