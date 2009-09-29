package gov.nih.nci.codegen.util;


import gov.nih.nci.codegen.GenerationException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;


/**
 * A simple object factory which retrieves objects from the pre-configured factory
 * 
 * @author Satish Patel
 *
 */
public class ObjectFactory
{
	private static Logger log = Logger.getLogger(ObjectFactory.class.getName());

	private static XmlBeanFactory factory;
	
	private static Boolean initialized = false;

	private static Object lock = new Object();
	
	private ObjectFactory() {}
	
	/**
	 * Initializes the object factory from the <code>fileName</code> parameter
	 * 
	 * @param fileName Name of the file which contains the configuration for the codegen
	 */
	public static void initialize(String fileName) 
	{
		synchronized(lock)
		{
			if(!initialized)
			{
				initialized = true;
				factory = new XmlBeanFactory(new ClassPathResource(fileName));
			}
		}
	}
	

	/**
	 * Get Object instance from the classname.
	 * 
	 * @param key Key representing the class (bean) to be retrieved as configured in the configuration file
	 * @return
	 * @throws ApplicationException 
	 */

	public static Object getObject(String key) throws GenerationException{

		if(!initialized)
			throw new RuntimeException("Object factory not initialized; can not retrieve bean");
		
		try
		{
			return factory.getBean(key);
		}
		catch(Exception e)
		{
			log.info("No bean found for key = "+key +"\n");
			throw new GenerationException("No bean found for key = "+key +"\n",e);
		}
	}

}
