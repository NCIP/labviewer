package gov.nih.nci.nih.servicemix.bean.routing;

import gov.nih.nci.caXchange.CaxchangeConstants;
import gov.nih.nci.caXchange.messaging.CaXchangeResponseMessageDocument;
import gov.nih.nci.caXchange.messaging.ErrorDetails;
import gov.nih.nci.caXchange.messaging.Response;
import gov.nih.nci.caXchange.messaging.ResponseMessage;
import gov.nih.nci.caXchange.messaging.Statuses;
import gov.nih.nci.caxchange.servicemix.bean.routing.HandleErrorResponseBean;

import java.io.InputStream;

import javax.jbi.messaging.ExchangeStatus;
import javax.jbi.messaging.InOut;
import javax.xml.namespace.QName;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;

import org.apache.servicemix.client.DefaultServiceMixClient;
import org.apache.servicemix.tck.SpringTestSupport;
import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.context.support.AbstractXmlApplicationContext;

public class HandleErrorResponseBeanTest extends SpringTestSupport {

	
	public void testSendValidErrorMessage() throws Exception {
    	DefaultServiceMixClient client = new DefaultServiceMixClient(jbi);  								
    	InputStream fis = getClass().getClassLoader().getResourceAsStream("testmessage.xml");
        InOut me = client.createInOutExchange();
        me.getInMessage().setProperty(CaxchangeConstants.ERROR_CODE, "TEST_ERROR_CODE");
        me.getInMessage().setProperty(CaxchangeConstants.ERROR_MESSAGE,"Test error message.");
        me.getInMessage().setContent(new StreamSource(fis));
        me.setService(new QName("http://servicemix.apache.org/caXchange", "handleErrorResponse"));
        me.setOperation(new QName("http://servicemix.apache.org/caXchange", "endPoint"));
        client.sendSync(me);
        System.out.println(me);
        assertEquals(ExchangeStatus.ACTIVE, me.getStatus());
        assertTrue(me.getMessage("out") != null);
        DOMSource domSource = (DOMSource)me.getMessage("out").getContent();
        CaXchangeResponseMessageDocument responseD = CaXchangeResponseMessageDocument.Factory.parse(domSource.getNode());
        ResponseMessage responseMessage = responseD.getCaXchangeResponseMessage();
        Response response =  responseMessage.getResponse();
        ErrorDetails errorDetails = response.getCaXchangeError();
        assertEquals("TEST_ERROR_CODE",errorDetails.getErrorCode() );
        assertEquals(Statuses.FAILURE, response.getResponseStatus());
	}

	
	public void testSendValidErrorMessageWithOutErrorCodeProperty() throws Exception {
    	DefaultServiceMixClient client = new DefaultServiceMixClient(jbi);  								
    	InputStream fis = getClass().getClassLoader().getResourceAsStream("testmessage.xml");
        InOut me = client.createInOutExchange();
        me.getInMessage().setProperty(CaxchangeConstants.ERROR_MESSAGE,"Test error message.");
        me.getInMessage().setContent(new StreamSource(fis));
        me.setService(new QName("http://servicemix.apache.org/caXchange", "handleErrorResponse"));
        me.setOperation(new QName("http://servicemix.apache.org/caXchange", "endPoint"));
        client.sendSync(me);
        System.out.println(me);
        assertEquals(ExchangeStatus.ACTIVE, me.getStatus());
        assertTrue(me.getMessage("out") != null);
        DOMSource domSource = (DOMSource)me.getMessage("out").getContent();
        CaXchangeResponseMessageDocument responseD = CaXchangeResponseMessageDocument.Factory.parse(domSource.getNode());
        ResponseMessage responseMessage = responseD.getCaXchangeResponseMessage();
        Response response =  responseMessage.getResponse();
        ErrorDetails errorDetails = response.getCaXchangeError();
        assertEquals(HandleErrorResponseBean.DEFAULT_ERROR_CODE,errorDetails.getErrorCode() );
        assertEquals(Statuses.FAILURE, response.getResponseStatus());
	}

	public void testSendValidErrorMessageWithOutErrorMessageProperty() throws Exception {
    	DefaultServiceMixClient client = new DefaultServiceMixClient(jbi);  								
    	InputStream fis = getClass().getClassLoader().getResourceAsStream("testmessage.xml");
        InOut me = client.createInOutExchange();
        me.getInMessage().setProperty(CaxchangeConstants.ERROR_CODE, "TEST_ERROR_CODE");
        me.getInMessage().setContent(new StreamSource(fis));
        me.setService(new QName("http://servicemix.apache.org/caXchange", "handleErrorResponse"));
        me.setOperation(new QName("http://servicemix.apache.org/caXchange", "endPoint"));
        client.sendSync(me);
        System.out.println(me);
        assertEquals(ExchangeStatus.ACTIVE, me.getStatus());
        assertTrue(me.getMessage("out") != null);
        DOMSource domSource = (DOMSource)me.getMessage("out").getContent();
        CaXchangeResponseMessageDocument responseD = CaXchangeResponseMessageDocument.Factory.parse(domSource.getNode());
        ResponseMessage responseMessage = responseD.getCaXchangeResponseMessage();
        Response response =  responseMessage.getResponse();
        ErrorDetails errorDetails = response.getCaXchangeError();
        assertEquals(HandleErrorResponseBean.DEFAULT_ERROR_MESSAGE,errorDetails.getErrorDescription() );
        assertEquals(Statuses.FAILURE, response.getResponseStatus());
	}

	
	
	@Override
	protected AbstractXmlApplicationContext createBeanFactory() {
		// TODO Auto-generated method stub
		return new ClassPathXmlApplicationContext("spring.xml");
	}

}
