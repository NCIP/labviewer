package gov.nih.nci.nih.servicemix.bean.validation;

import gov.nih.nci.nih.servicemix.bean.CaxchangeBeanServiceUnitTest;

import java.io.InputStream;

import javax.jbi.messaging.ExchangeStatus;
import javax.jbi.messaging.InOut;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;

import org.apache.servicemix.client.DefaultServiceMixClient;
import org.apache.servicemix.tck.SpringTestSupport;
import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.context.support.AbstractXmlApplicationContext;
import org.w3c.dom.Document;
/**
 * Tests the caXchange payload validation framework. This requires the GME configured
 * in the spring.xml file is  up and running.
 * 
 * @author marwahah
 *
 */
public class PayloadValidationTest extends CaxchangeBeanServiceUnitTest{

	public void testValidMessage() throws Exception{
	    	DefaultServiceMixClient client = new DefaultServiceMixClient(jbi);
	    	InOut me = client.createInOutExchange();
	    	me.getInMessage().setContent(getSource("testmessage.xml"));
            me.setService(new QName("http://nci.nih.gov/caXchange",
			"payloadValidator"));
            me.setOperation(new QName("http://nci.nih.gov/caXchange",
			"payloadValidator"));
            client.sendSync(me);
    		assertEquals(ExchangeStatus.ACTIVE, me.getStatus());
    		assertTrue(me.getFault() == null);
		}
	
	public void testInValidMessage() throws Exception{
    	DefaultServiceMixClient client = new DefaultServiceMixClient(jbi);
    	InOut me = client.createInOutExchange();
    	me.getInMessage().setContent(getSource("invalidPayloadMessage.xml"));
        me.setService(new QName("http://nci.nih.gov/caXchange",
		"payloadValidator"));
        me.setOperation(new QName("http://nci.nih.gov/caXchange",
		"payloadValidator"));
        client.sendSync(me);
		assertEquals(ExchangeStatus.ACTIVE, me.getStatus());
		assertTrue(me.getFault() != null);
	}
	


}