package gov.nih.nci.nih.servicemix.bean.routing;

import gov.nih.nci.caXchange.CaxchangeConstants;
import gov.nih.nci.caXchange.CaxchangeErrors;

import java.io.InputStream;

import javax.jbi.messaging.ExchangeStatus;
import javax.jbi.messaging.InOut;
import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.servicemix.client.DefaultServiceMixClient;

public class HandleErrorResponseBeanTest extends gov.nih.nci.nih.servicemix.bean.CaxchangeBeanServiceUnitTest{
	
	Logger logger = LogManager.getLogger(HandleErrorResponseBeanTest.class);
	
	public void testErrorResponse() throws Exception{
    	DefaultServiceMixClient client = new DefaultServiceMixClient(jbi);
    	InOut me = client.createInOutExchange();
    	me.getInMessage().setContent(getSource("testmessage.xml"));
    	me.getInMessage().setProperty(CaxchangeConstants.ERROR_CODE, CaxchangeErrors.CONNECT_EXCEPTION);
    	me.getInMessage().setProperty(CaxchangeConstants.ERROR_MESSAGE, "Error connecting to the database.");
    	me.setService(new QName("http://nci.nih.gov/caXchange",
		"handleErrorResponse"));
        me.setOperation(new QName("http://nci.nih.gov/caXchange",
		"handleErrorResponse"));
        client.sendSync(me);
		assertEquals(ExchangeStatus.ACTIVE, me.getStatus());
		assertTrue(me.getMessage("out") != null);
		logger.info(me);
	}

}
