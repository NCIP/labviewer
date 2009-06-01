package gov.nih.nci.nih.servicemix.bean.routing;

import gov.nih.nci.caXchange.CaxchangeConstants;
import gov.nih.nci.nih.servicemix.bean.CaxchangeBeanServiceUnitTest;

import java.util.HashMap;
import java.util.Map;

import javax.jbi.messaging.ExchangeStatus;
import javax.jbi.messaging.InOut;
import javax.xml.namespace.QName;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.servicemix.client.DefaultServiceMixClient;

public class TestEmailNotificationBean extends CaxchangeBeanServiceUnitTest {
	
	protected static Logger logger = LogManager.getLogger(TestEmailNotificationBean.class);
	
	public void testEmailNotificationBean() throws Exception{
    	DefaultServiceMixClient client = new DefaultServiceMixClient(jbi);
    	
       	InOut originalExchange = client.createInOutExchange();
        originalExchange.getInMessage().setContent(getSource("testmessage.xml"));
        Map<String,String> metadata= new HashMap<String,String>();
        metadata.put(CaxchangeConstants.CAXCHANGE_IDENTIFIER, "cax_id_123");
        metadata.put(CaxchangeConstants.EXTERNAL_IDENTIFIER, "external_123");
        originalExchange.getInMessage().setProperty(CaxchangeConstants.REQUEST_METADATA, metadata);
        originalExchange.setService(new QName("http://nci.nih.gov/caXchange",
		"emailNotificationService"));
        boolean sent = client.sendSync(originalExchange);
        assertTrue(sent);
		assertEquals(ExchangeStatus.ACTIVE, originalExchange.getStatus());
		assertNull(originalExchange.getFault());
		logger.info(originalExchange);
	}

}
