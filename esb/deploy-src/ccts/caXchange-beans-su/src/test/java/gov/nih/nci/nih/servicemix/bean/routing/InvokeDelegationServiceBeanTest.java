/**
 * 
 */
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

/**
 * @author griffinch
 *
 */
public class InvokeDelegationServiceBeanTest extends SpringTestSupport {

    public void testSubmitValidCredentialsMessage() throws Exception {
    	DefaultServiceMixClient client = new DefaultServiceMixClient(jbi);
    	//InputStream fis = getClass().getResourceAsStream("testmessage.xml");    								
    	InputStream fis = getClass().getClassLoader().getResourceAsStream("testmessage.xml");
            InOut me = client.createInOutExchange();
            me.getInMessage().setProperty("Test", "TestProperty");
            me.getInMessage().setContent(new StreamSource(fis));
            me.setService(new QName("http://servicemix.apache.org/caXchange", "setSubjectService"));
            me.setOperation(new QName("http://servicemix.apache.org/caXchange", "setSubjectEndPoint"));
            client.sendSync(me);
            System.out.println(me);
            assertEquals(ExchangeStatus.ACTIVE, me.getStatus());
            assertTrue(me.getMessage("out") != null);
            assertEquals("TestProperty", me.getMessage("out").getProperty("Test"));
        }

    public void testSubmitInValidCredentialsMessage() throws Exception {
    	DefaultServiceMixClient client = new DefaultServiceMixClient(jbi);
    	InputStream fis = getClass().getClassLoader().getResourceAsStream("testmessageinvalidcredentials.xml");
            InOut me = client.createInOutExchange();
            me.getInMessage().setProperty("Test", "TestProperty");
            me.getInMessage().setContent(new StreamSource(fis));
            me.setService(new QName("http://servicemix.apache.org/caXchange", "setSubjectService"));
            me.setOperation(new QName("http://servicemix.apache.org/caXchange", "setSubjectEndPoint"));
            client.sendSync(me);
            System.out.println(me);
            assertEquals(ExchangeStatus.ACTIVE, me.getStatus());
            assertTrue(me.getMessage("out") == null);
            assertEquals("TestProperty", me.getFault().getProperty("Test"));
            assertEquals("DELEGATION_SERVICE_ERROR",me.getFault().getProperty("caxchange.errorcode"));
     }

    
	
	
	/* (non-Javadoc)
	 * @see org.apache.servicemix.tck.SpringTestSupport#createBeanFactory()
	 */
	@Override
	protected AbstractXmlApplicationContext createBeanFactory() {
		// TODO Auto-generated method stub
		return new ClassPathXmlApplicationContext("spring.xml");
	}

}
