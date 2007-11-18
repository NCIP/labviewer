package gov.nih.nci.nih.servicemix.bean.routing;

import org.apache.servicemix.tck.SpringTestSupport;
import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.context.support.AbstractXmlApplicationContext;

public class CaxchangeAggregatorListenerTest extends SpringTestSupport {

	public void testAggregation() throws Exception {
		
		assertTrue(true);
	}

	
	@Override
	protected AbstractXmlApplicationContext createBeanFactory() {
		// TODO Auto-generated method stub
		return new ClassPathXmlApplicationContext("spring.xml");
	}

}
