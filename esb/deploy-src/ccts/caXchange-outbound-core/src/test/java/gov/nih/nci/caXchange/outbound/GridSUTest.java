package gov.nih.nci.caXchange.outbound;

import java.io.InputStream;

import javax.jbi.messaging.ExchangeStatus;
import javax.jbi.messaging.InOnly;
import javax.jbi.messaging.InOut;
import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamSource;

import org.apache.servicemix.client.DefaultServiceMixClient;
import org.apache.servicemix.tck.SpringTestSupport;
import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.context.support.AbstractXmlApplicationContext;

public class GridSUTest extends SpringTestSupport {

	public void testCTOMExchange() throws Exception {
		DefaultServiceMixClient client = new DefaultServiceMixClient(jbi);
		InputStream fis = getClass().getResourceAsStream("registration-request.xml");
		InOut me = client.createInOutExchange();
		me.getInMessage().setProperty("Test", "TestProperty");
		me.getInMessage().setContent(new StreamSource(fis));
		me.setService(new QName("http://nci.nih.gov/caXchange",
				"ctomRegistration"));
		me.setOperation(new QName("http://nci.nih.gov/caXchange",
				"ctomRegistration"));
		client.sendSync(me);
		System.out.println(me);
		assertEquals(ExchangeStatus.ACTIVE, me.getStatus());
		assertTrue(me.getMessage("out") != null);
		assertEquals("TestProperty", me.getMessage("out").getProperty("Test"));
	}
	
	public void testLoadLabExchange() throws Exception {
		DefaultServiceMixClient client = new DefaultServiceMixClient(jbi);
		InputStream fis = getClass().getResourceAsStream("loadlab-request.xml");
		InOut me = client.createInOutExchange();
		me.getInMessage().setProperty("Test", "TestProperty");
		me.getInMessage().setContent(new StreamSource(fis));
		me.setService(new QName("http://nci.nih.gov/caXchange",
				"loadLab"));
		me.setOperation(new QName("http://nci.nih.gov/caXchange",
				"loadLab"));
		client.sendSync(me);
		System.out.println(me);
		assertEquals(ExchangeStatus.ACTIVE, me.getStatus());
		assertTrue(me.getMessage("out") != null);
		assertEquals("TestProperty", me.getMessage("out").getProperty("Test"));
	}
	
	public void testCTOMSStudyExchange() throws Exception {
		DefaultServiceMixClient client = new DefaultServiceMixClient(jbi);
		InputStream fis = getClass().getResourceAsStream("study-request.xml");
		InOut me = client.createInOutExchange();
		me.getInMessage().setProperty("Test", "TestProperty");
		me.getInMessage().setContent(new StreamSource(fis));
		me.setService(new QName("http://nci.nih.gov/caXchange",
				"ctomStudy"));
		me.setOperation(new QName("http://nci.nih.gov/caXchange",
				"ctomStudy"));
		client.sendSync(me);
		System.out.println(me);
		assertEquals(ExchangeStatus.ACTIVE, me.getStatus());
		assertTrue(me.getMessage("out") != null);
		assertEquals("TestProperty", me.getMessage("out").getProperty("Test"));
	}
	
	public void testCTOMRollbackExchange() throws Exception {
		DefaultServiceMixClient client = new DefaultServiceMixClient(jbi);
		InputStream fis = getClass().getResourceAsStream("registration-request.xml");
		InOnly me = client.createInOnlyExchange();
		me.getInMessage().setProperty("Test", "TestProperty");
		me.getInMessage().setContent(new StreamSource(fis));
		me.setService(new QName("http://nci.nih.gov/caXchange",
				"ctomRegistrationRollback"));
		me.setOperation(new QName("http://nci.nih.gov/caXchange",
				"ctomRegistrationRollback"));
		client.sendSync(me, 5000);
		System.out.println(me);
		assertEquals(ExchangeStatus.DONE, me.getStatus());
	}
	
	public void testCTOMStudyRollbackExchange() throws Exception {
		DefaultServiceMixClient client = new DefaultServiceMixClient(jbi);
		InputStream fis = getClass().getResourceAsStream("study-request.xml");
		InOnly me = client.createInOnlyExchange();
		me.getInMessage().setProperty("Test", "TestProperty");
		me.getInMessage().setContent(new StreamSource(fis));
		me.setService(new QName("http://nci.nih.gov/caXchange",
				"ctomStudyRollback"));
		me.setOperation(new QName("http://nci.nih.gov/caXchange",
				"ctomStudyRollback"));
		client.sendSync(me, 5000);
		System.out.println(me);
		assertEquals(ExchangeStatus.DONE, me.getStatus());
	}
	
	public void testC3DRegistrationExchange() throws Exception {
		DefaultServiceMixClient client = new DefaultServiceMixClient(jbi);
		InputStream fis = getClass().getResourceAsStream("registration-request.xml");
		InOut me = client.createInOutExchange();
		me.getInMessage().setProperty("Test", "TestProperty");
		me.getInMessage().setContent(new StreamSource(fis));
		me.setService(new QName("http://nci.nih.gov/caXchange",
				"c3dRegistration"));
		me.setOperation(new QName("http://nci.nih.gov/caXchange",
				"c3dRegistration"));
		client.sendSync(me);
		System.out.println(me);
		assertEquals(ExchangeStatus.ACTIVE, me.getStatus());
		assertTrue(me.getMessage("out") != null);
		assertEquals("TestProperty", me.getMessage("out").getProperty("Test"));
	}
	
	public void testCAAERSExchange() throws Exception {
		DefaultServiceMixClient client = new DefaultServiceMixClient(jbi);
		InputStream fis = getClass().getResourceAsStream("registration-request.xml");
		InOut me = client.createInOutExchange();
		me.getInMessage().setProperty("Test", "TestProperty");
		me.getInMessage().setContent(new StreamSource(fis));
		me.setService(new QName("http://nci.nih.gov/caXchange",
				"caaersRegistration"));
		me.setOperation(new QName("http://nci.nih.gov/caXchange",
				"caaersRegistration"));
		client.sendSync(me);
		System.out.println(me);
		assertEquals(ExchangeStatus.ACTIVE, me.getStatus());
		assertTrue(me.getMessage("out") != null);
		assertEquals("TestProperty", me.getMessage("out").getProperty("Test"));
	}
	
	public void testCAAERSStudyExchange() throws Exception {
		DefaultServiceMixClient client = new DefaultServiceMixClient(jbi);
		InputStream fis = getClass().getResourceAsStream("study-request.xml");
		InOut me = client.createInOutExchange();
		me.getInMessage().setProperty("Test", "TestProperty");
		me.getInMessage().setContent(new StreamSource(fis));
		me.setService(new QName("http://nci.nih.gov/caXchange",
				"caaersStudy"));
		me.setOperation(new QName("http://nci.nih.gov/caXchange",
				"caaersStudy"));
		client.sendSync(me);
		System.out.println(me);
		assertEquals(ExchangeStatus.ACTIVE, me.getStatus());
		assertTrue(me.getMessage("out") != null);
		assertEquals("TestProperty", me.getMessage("out").getProperty("Test"));
	}
	
	public void testCAAERSRollbackExchange() throws Exception {
		DefaultServiceMixClient client = new DefaultServiceMixClient(jbi);
		InputStream fis = getClass().getResourceAsStream("registration-request.xml");
		InOnly me = client.createInOnlyExchange();
		me.getInMessage().setProperty("Test", "TestProperty");
		me.getInMessage().setContent(new StreamSource(fis));
		me.setService(new QName("http://nci.nih.gov/caXchange",
				"caaersRegistrationRollback"));
		me.setOperation(new QName("http://nci.nih.gov/caXchange",
				"caaersRegistrationRollback"));
		client.sendSync(me, 5000);
		System.out.println(me);
		assertEquals(ExchangeStatus.DONE, me.getStatus());
	}
	
	public void testCAAERSStudyRollbackExchange() throws Exception {
		DefaultServiceMixClient client = new DefaultServiceMixClient(jbi);
		InputStream fis = getClass().getResourceAsStream("registration-request.xml");
		InOnly me = client.createInOnlyExchange();
		me.getInMessage().setProperty("Test", "TestProperty");
		me.getInMessage().setContent(new StreamSource(fis));
		me.setService(new QName("http://nci.nih.gov/caXchange",
				"caaersStudyRollback"));
		me.setOperation(new QName("http://nci.nih.gov/caXchange",
				"caaersStudyRollback"));
		client.sendSync(me, 5000);
		System.out.println(me);
		assertEquals(ExchangeStatus.DONE, me.getStatus());
	}

	public void testRetryingExchange() throws Exception {

	}

	public void testFaultExchange() throws Exception {

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
