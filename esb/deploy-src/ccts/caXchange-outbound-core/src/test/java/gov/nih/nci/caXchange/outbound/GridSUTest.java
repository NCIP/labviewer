package gov.nih.nci.caXchange.outbound;

import gov.nih.nci.cagrid.authentication.bean.BasicAuthenticationCredential;
import gov.nih.nci.cagrid.authentication.bean.Credential;
import gov.nih.nci.cagrid.authentication.client.AuthenticationClient;
import gov.nih.nci.cagrid.common.security.ProxyUtil;
import gov.nih.nci.cagrid.dorian.client.IFSUserClient;
import gov.nih.nci.cagrid.dorian.ifs.bean.ProxyLifetime;

import java.io.InputStream;
import java.security.Principal;
import java.util.Properties;

import javax.jbi.messaging.ExchangeStatus;
import javax.jbi.messaging.InOnly;
import javax.jbi.messaging.InOut;
import javax.security.auth.Subject;
import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.servicemix.client.DefaultServiceMixClient;
import org.apache.servicemix.tck.SpringTestSupport;
import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.globus.gsi.GlobusCredential;
import gov.nih.nci.cagrid.opensaml.SAMLAssertion;
import gov.nih.nci.caxchange.security.CaXchangePrincipal;

import org.springframework.context.support.AbstractXmlApplicationContext;


/**
 * This class consists of the different test cases for the target grid service
 * @author steve 
 *
 */
public class GridSUTest extends SpringTestSupport {
	
	Logger logger = LogManager.getLogger(GridSUTest.class);
	Properties authenticationProperties;
	
	public void loadAuthenticationProperties() throws Exception{
		InputStream props = getClass().getClassLoader().getResourceAsStream("authentication.properties");
		authenticationProperties = new Properties();
		authenticationProperties.load(props);
	}

	public GlobusCredential getGlobusCredential() throws Exception {
		String authUrl = authenticationProperties.getProperty("authentication.url");
		String dorianUrl = authenticationProperties.getProperty("dorian.url");
		String userId = authenticationProperties.getProperty("ccts.grid.userName");
		String password = authenticationProperties.getProperty("ccts.grid.password");
		
		Credential localCredential = new Credential();
		BasicAuthenticationCredential userPass = new BasicAuthenticationCredential(); 
		userPass.setUserId(userId); 
		userPass.setPassword(password);
		localCredential.setBasicAuthenticationCredential(userPass);
		AuthenticationClient auth = new AuthenticationClient(authUrl, localCredential);
	    SAMLAssertion saml = auth.authenticate();
		IFSUserClient client = new IFSUserClient(dorianUrl);
		ProxyLifetime lifetime = new ProxyLifetime(); 
		lifetime.setHours(12); 
		lifetime.setMinutes(0); 
		lifetime.setSeconds(0); 
		int delegationPathLength = 0;
		GlobusCredential proxy = client.createProxy(saml, lifetime, delegationPathLength);
		ProxyUtil.saveProxyAsDefault(proxy);
		return proxy;
	} 
	
	public void setSecuritySubject(InOut me) throws Exception {
		Subject subject = new Subject();

		CaXchangePrincipal principal = new CaXchangePrincipal();
		GlobusCredential userCredential = getGlobusCredential();
		principal.setName(userCredential.getIdentity());
		subject.getPrincipals().add((Principal) principal);

		subject.getPrivateCredentials().add(userCredential);
		me.getInMessage().setSecuritySubject(subject);
	}
	
	
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
		logger.info(me);
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
		logger.info(me);
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
		logger.info(me);
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
