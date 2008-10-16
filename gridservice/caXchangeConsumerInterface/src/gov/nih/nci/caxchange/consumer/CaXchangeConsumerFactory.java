package gov.nih.nci.caxchange.consumer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * A factor for caXchang consumers.
 * 
 * @author Ekagra Software Technologies
 *
 */
public class CaXchangeConsumerFactory {
	
	static Logger logger=Logger.getLogger(CaXchangeConsumerFactory.class);
	static final String BEAN_ID_SUFFIX = "_Consumer"; 
	static ApplicationContext context = new ClassPathXmlApplicationContext("CaXchangeRequestConsumers.xml");
	static BeanFactory factory=context;
	/**
	 * Returns the consumer configured for a message Type.
	 * 
	 * @param messageType
	 * @return
	 * @throws CaXchangeConsumerException
	 */
	public static CaXchangeMessageConsumer getConsumer(String messageType) throws CaXchangeConsumerException 
	{
        String beanId = messageType+BEAN_ID_SUFFIX;
        CaXchangeMessageConsumer consumer = null;
        try {
		   consumer=(CaXchangeMessageConsumer)factory.getBean(beanId);
        }catch(Exception e) {
        	logger.error("Error looking up the consumer for message type:"+messageType,e);
        	throw new CaXchangeConsumerException("Error looking up the consumer for message type:"+messageType,e);
        }
		if (consumer == null) {
			throw new CaXchangeConsumerException("Error looking up the consumer for message type:"+messageType);
		}
		return consumer;
		
	}
		

}
