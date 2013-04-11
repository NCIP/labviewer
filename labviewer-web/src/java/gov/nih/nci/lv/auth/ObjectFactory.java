/*
* Copyright ScenPro, Inc and SemanticBits, LLC
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/labviewer/LICENSE.txt for details.
*/
package gov.nih.nci.lv.auth;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

/**
 *
 * @author Naveen Amiruddin
 *
 */
public final class ObjectFactory {
	static BeanFactory fact;
	private ObjectFactory()	{
	}

	/**
	 *
	 * @param name name
	 * @return Object
	 */
	public static Object getObject(String name)	{
		if (fact == null) {
			ClassPathResource res =	new ClassPathResource("applicationContext.xml");
			fact = new XmlBeanFactory(res);
		}
		return fact.getBean(name);
	}
}
