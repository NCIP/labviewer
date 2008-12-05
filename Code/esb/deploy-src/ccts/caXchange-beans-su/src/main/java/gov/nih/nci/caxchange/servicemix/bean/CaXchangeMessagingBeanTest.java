package gov.nih.nci.caxchange.servicemix.bean;

import javax.jbi.messaging.MessageExchange;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class CaXchangeMessagingBeanTest extends CaXchangeMessagingBean {

	Logger logger = LogManager.getLogger(CaXchangeMessagingBeanTest.class);
	@Override
	public void processMessageExchange(MessageExchange exchange)
			throws Exception {
		logger.info("Exchange received:"+exchange);
		
	}
	

}
