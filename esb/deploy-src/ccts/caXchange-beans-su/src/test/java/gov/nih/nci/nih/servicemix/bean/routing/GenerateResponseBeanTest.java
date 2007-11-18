package gov.nih.nci.nih.servicemix.bean.routing;

import org.apache.servicemix.tck.SpringTestSupport;
import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.context.support.AbstractXmlApplicationContext;

public class GenerateResponseBeanTest extends SpringTestSupport {

	
	public void testSendValidErrorMessage() throws Exception {
		
		assertTrue(true);
	}

	
	public void testSendValidErrorMessageWithOutErrorCodeProperty() throws Exception {
		
		assertTrue(true);
	}

	public void testSendValidErrorMessageWithOutErrorMessageProperty() throws Exception {
		
		assertTrue(true);
	}

	
	
	@Override
	protected AbstractXmlApplicationContext createBeanFactory() {
		// TODO Auto-generated method stub
		return new ClassPathXmlApplicationContext("spring.xml");
	}

}
