package gov.nih.nci.nih.servicemix.bean;

import java.io.InputStream;

import javax.jbi.messaging.InOut;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;

import org.apache.servicemix.jbi.jaxp.SourceTransformer;
import org.apache.servicemix.tck.SpringTestSupport;
import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.context.support.AbstractXmlApplicationContext;
import org.w3c.dom.Document;

public abstract class CaxchangeBeanServiceUnitTest extends SpringTestSupport {
	
	protected Source getSource(String fileName)throws Exception {
	   	InputStream fis = getClass().getClassLoader().getResourceAsStream(fileName);
        SourceTransformer sourceTransformer = new SourceTransformer();
        DOMSource domSource = sourceTransformer.toDOMSource(new StreamSource(fis));	
    	return domSource;
	}

	@Override
	protected AbstractXmlApplicationContext createBeanFactory() {
		// TODO Auto-generated method stub
		return new ClassPathXmlApplicationContext("spring.xml");
	}

}
