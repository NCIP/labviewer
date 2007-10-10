package gov.nih.nci.caxchange.ctom.viewer.util;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

public class ObjectFactory {

	static BeanFactory fact;
	
	private ObjectFactory(){};
	
	public static Object getObject(String name){
		
		
		
		if (fact == null){
			ClassPathResource res = new ClassPathResource("applicationContext.xml");
			fact = new XmlBeanFactory(res);
		}
		return fact.getBean(name);

	}
}
