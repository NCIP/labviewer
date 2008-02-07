package gov.nih.nci.nih.servicemix.bean.routing;

import gov.nih.nci.caXchange.CaxchangeConstants;
import gov.nih.nci.caXchange.messaging.CaXchangeResponseMessageDocument;
import gov.nih.nci.caXchange.messaging.ErrorDetails;
import gov.nih.nci.caXchange.messaging.Response;
import gov.nih.nci.caXchange.messaging.ResponseMessage;
import gov.nih.nci.caXchange.messaging.Statuses;
import gov.nih.nci.caxchange.servicemix.bean.routing.HandleErrorResponseBean;

import java.io.InputStream;

import javax.jbi.messaging.ExchangeStatus;
import javax.jbi.messaging.InOut;
import javax.xml.namespace.QName;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;

import org.apache.servicemix.client.DefaultServiceMixClient;
import org.apache.servicemix.tck.SpringTestSupport;
import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.context.support.AbstractXmlApplicationContext;

public class StoreOriginalMessageServiceBeanDatabaseDownTest extends SpringTestSupport {

    /**
     * Send a test message to the bean when the database is down.
     * Verify that a fault is returned.
     * 
     * @throws Exception
     */	
	public void testSendMessage() throws Exception {
    	DefaultServiceMixClient client = new DefaultServiceMixClient(jbi);  								
    	InputStream fis = getClass().getClassLoader().getResourceAsStream("testmessage.xml");
        InOut me = client.createInOutExchange();
        me.getInMessage().setContent(new StreamSource(fis));
        me.setService(new QName("http://servicemix.apache.org/caXchange", "storeOriginalMessage"));
        me.setOperation(new QName("http://servicemix.apache.org/caXchange", "endPoint"));
        me.setProperty(CaxchangeConstants.EXCHANGE_CORRELATIONID, "testCorrelationId"+System.currentTimeMillis());
        client.sendSync(me);
        System.out.println(me);
        assertTrue(me.getFault()!=null);
        assertEquals("ERROR_STORING_MESSAGE",me.getFault().getProperty(CaxchangeConstants.ERROR_CODE));

	}
    
	
	@Override
	protected AbstractXmlApplicationContext createBeanFactory() {
		// TODO Auto-generated method stub
		return new ClassPathXmlApplicationContext("spring.xml");
	}

}
