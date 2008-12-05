package gov.nih.nci.nih.servicemix.bean.routing;

import java.util.Date;

import javax.jbi.messaging.ExchangeStatus;
import javax.jbi.messaging.InOut;
import javax.xml.namespace.QName;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.servicemix.client.DefaultServiceMixClient;

import gov.nih.nci.caXchange.CaxchangeConstants;
import gov.nih.nci.nih.servicemix.bean.CaxchangeBeanServiceUnitTest;

public class StoreOriginalMessageDatabaseDownTest extends
		CaxchangeBeanServiceUnitTest {
	
	Logger logger = LogManager.getLogger(StoreOriginalMessageDatabaseDownTest.class);
	
	/**
	 * Tests for a fault response if the database is down. Verify that the database is 
	 * down for this test.
	 * 
	 */
	public void testStoreMessageDatabaseDown() throws Exception {
    	DefaultServiceMixClient client = new DefaultServiceMixClient(jbi);
    	InOut me = client.createInOutExchange();
    	me.getInMessage().setContent(getSource("testmessage.xml"));
    	me.setProperty(CaxchangeConstants.EXCHANGE_CORRELATIONID, new Date().toString());
        me.setService(new QName("http://nci.nih.gov/caXchange",
		"storeOriginalMessage"));
        me.setOperation(new QName("http://nci.nih.gov/caXchange",
		"storeOriginalMessage"));
        boolean sent = client.sendSync(me);
        assertTrue(sent);
		assertEquals(ExchangeStatus.ACTIVE, me.getStatus());
		assertTrue(me.getFault()!= null);
		assertNotNull(me.getFault().getProperty(CaxchangeConstants.ERROR_CODE));
		assertNotNull(me.getFault().getProperty(CaxchangeConstants.ERROR_MESSAGE));
		logger.debug(me.getFault().getProperty(CaxchangeConstants.ERROR_CODE)+":"+me.getFault().getProperty(CaxchangeConstants.ERROR_MESSAGE));

	}		

}
