/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */
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
/**
 * This class consists of the different test cases for the target grid service
 * @author steve
 *
 */
public class GridSUTest extends SpringTestSupport {
	/**
	 * This method test the CTOM grid service
	 * @param
	 * @return
	 * @throws Exception
	 */
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
	
	/**
	 * This method test Load Lab grid service 
	 * @param
	 * @return
	 * @throws Exception
	 */
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
	
	/**
	 * This method tests the CTOM study grid service 
	 * @param
	 * @return
	 * @throws Exception
	 */
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
	
	/**
	 * This mehtod tests CTOM grid service for the rollback action
	 * @param
	 * @return
	 * @throws Exception
	 */
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
	
	/**
	 * This method test CTOM study grid service for the rollback action
	 * @param
	 * @return
	 * @throws Exception
	 */
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
	
	/**
	 * This method test C3D grid service registration
	 * @param
	 * @return
	 * @throws Exception
	 */
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
	
	/**
	 * This method test CAAERS grid service
	 * @param
	 * @return
	 * @throws Exception
	 */
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
	
	/**
	 * This  method test CAAERS study grid service
	 * @param
	 * @return 
	 * @throws Exception
	 */
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
	/**
	 * This method test CAARES grid service for the rollback action
	 * @param
	 * @return
	 * @throws Exception
	 */
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
	
	/**
	 * This method test CAAERS grid service for the study rollback action
	 * @param
	 * @return
	 * @throws Exception
	 */
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

	/**
	 * This method test PSC grid service 
	 * @param
	 * @return
	 * @throws Exception
	 */
	public void testPSCExchange() throws Exception {
		DefaultServiceMixClient client = new DefaultServiceMixClient(jbi);
		InputStream fis = getClass().getResourceAsStream("registration-request.xml");
		InOut me = client.createInOutExchange();
		me.getInMessage().setProperty("Test", "TestProperty");
		me.getInMessage().setContent(new StreamSource(fis));
		me.setService(new QName("http://nci.nih.gov/caXchange",
				"pscRegistration"));
		me.setOperation(new QName("http://nci.nih.gov/caXchange",
				"pscRegistration"));
		client.sendSync(me);
		System.out.println(me);
		assertEquals(ExchangeStatus.ACTIVE, me.getStatus());
		assertTrue(me.getMessage("out") != null);
		assertEquals("TestProperty", me.getMessage("out").getProperty("Test"));
	}
	
	/**
	 * This method test PSC grid service for the commit action
	 * @param
	 * @return
	 * @throws Exception
	 */
	public void testPSCStudyExchange() throws Exception {
		DefaultServiceMixClient client = new DefaultServiceMixClient(jbi);
		InputStream fis = getClass().getResourceAsStream("study-request.xml");
		InOut me = client.createInOutExchange();
		me.getInMessage().setProperty("Test", "TestProperty");
		me.getInMessage().setContent(new StreamSource(fis));
		me.setService(new QName("http://nci.nih.gov/caXchange",
				"pscStudy"));
		me.setOperation(new QName("http://nci.nih.gov/caXchange",
				"pscStudy"));
		client.sendSync(me);
		System.out.println(me);
		assertEquals(ExchangeStatus.ACTIVE, me.getStatus());
		assertTrue(me.getMessage("out") != null);
		assertEquals("TestProperty", me.getMessage("out").getProperty("Test"));
	}
	/**
	 * This method test PSC grid service for the rollback action
	 * @param
	 * @return
	 * @throws Exception
	 */
	public void testPSCRollbackExchange() throws Exception {
		DefaultServiceMixClient client = new DefaultServiceMixClient(jbi);
		InputStream fis = getClass().getResourceAsStream("registration-request.xml");
		InOnly me = client.createInOnlyExchange();
		me.getInMessage().setProperty("Test", "TestProperty");
		me.getInMessage().setContent(new StreamSource(fis));
		me.setService(new QName("http://nci.nih.gov/caXchange",
				"pscRegistrationRollback"));
		me.setOperation(new QName("http://nci.nih.gov/caXchange",
				"pscRegistrationRollback"));
		client.sendSync(me, 5000);
		System.out.println(me);
		assertEquals(ExchangeStatus.DONE, me.getStatus());
	}
	
	/**
	 * This method test PSC grid service for the study rollback action
	 * @param
	 * @return
	 * @throws Exception
	 */
	public void testPSCStudyRollbackExchange() throws Exception {
		DefaultServiceMixClient client = new DefaultServiceMixClient(jbi);
		InputStream fis = getClass().getResourceAsStream("registration-request.xml");
		InOnly me = client.createInOnlyExchange();
		me.getInMessage().setProperty("Test", "TestProperty");
		me.getInMessage().setContent(new StreamSource(fis));
		me.setService(new QName("http://nci.nih.gov/caXchange",
				"pscStudyRollback"));
		me.setOperation(new QName("http://nci.nih.gov/caXchange",
				"pscStudyRollback"));
		client.sendSync(me, 5000);
		System.out.println(me);
		assertEquals(ExchangeStatus.DONE, me.getStatus());
	}
	
	/**
	 * This method tests PSC grid service for the adverse event
	 * @param
	 * @return
	 * @throws Exception
	 */
	public void testPSCAdverseEvent() throws Exception {
		DefaultServiceMixClient client = new DefaultServiceMixClient(jbi);
		InputStream fis = getClass().getResourceAsStream("adverse-event-request.xml");
		InOnly me = client.createInOnlyExchange();
		me.getInMessage().setProperty("Test", "TestProperty");
		me.getInMessage().setContent(new StreamSource(fis));
		me.setService(new QName("http://nci.nih.gov/caXchange",
				"pscAdverseEvent"));
		me.setOperation(new QName("http://nci.nih.gov/caXchange",
				"pscAdverseEvent"));
		client.sendSync(me, 5000);
		System.out.println(me);
		assertEquals(ExchangeStatus.DONE, me.getStatus());
	}
	
	/**
	 * This method test retrying exchange
	 * @param
	 * @return
	 * @throws Exception
	 */ /*No references*/
	public void testRetryingExchange() throws Exception {

	}
	/**
	 * This method tests the fault exchange
	 * @param
	 * @return
	 * @throws Exception
	 */ /*No references*/
	public void testFaultExchange() throws Exception {

	}

	/**
	 * This method returns application context form the given xml file
	 * @param
	 * @return application context
	 * @throws
	 */ /*No references*/
	@Override
	protected AbstractXmlApplicationContext createBeanFactory() {
		return new ClassPathXmlApplicationContext("spring.xml");
	}
}
