package gov.nih.nci.nih.servicemix.bean.util;

import java.io.InputStream;

import javax.jbi.messaging.InOnly;
import javax.jbi.messaging.InOut;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.servicemix.client.DefaultServiceMixClient;
import org.apache.servicemix.tck.SpringTestSupport;
import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.context.support.AbstractXmlApplicationContext;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import gov.nih.nci.caXchange.messaging.CaXchangeResponseMessageDocument;
import gov.nih.nci.caxchange.servicemix.bean.util.CaXchangeDataUtil;
import gov.nih.nci.nih.servicemix.bean.CaxchangeBeanServiceUnitTest;

public class CaXchangeDataUtilTest extends CaxchangeBeanServiceUnitTest {

	public void testBuildResponse() {
	  try {
    	DefaultServiceMixClient client = new DefaultServiceMixClient(jbi);
    	InputStream fis = getClass().getClassLoader().getResourceAsStream("testmessage.xml");
    	InOnly me = client.createInOnlyExchange();
    	me.getInMessage().setContent(new StreamSource(fis));
    	CaXchangeDataUtil util = (CaXchangeDataUtil)context.getBean("xpathUtil");
    	util.setIn(me.getMessage("in"));
    	util.initialize();
    	InputStream aggregatedResponse = getClass().getClassLoader().getResourceAsStream("aggregatemessage.xml");
    	DocumentBuilderFactory dbf= DocumentBuilderFactory.newInstance();
    	DocumentBuilder db = dbf.newDocumentBuilder();
    	Document document = db.parse(aggregatedResponse);
    	DOMSource ds = new DOMSource(document);
    	CaXchangeResponseMessageDocument response = util.generateResponseFromAggregatedResponse(ds);
    	assertNotNull(response);
    	System.out.println(response.validate());
    	System.out.println(response);
	  }catch(Exception e) {
		  throw new RuntimeException(e);
	  }
	}
	
	public void testGetDelegationEPR() {
		  try {
	    	DefaultServiceMixClient client = new DefaultServiceMixClient(jbi);
	    	InputStream fis = getClass().getClassLoader().getResourceAsStream("testmessage.xml");
	    	InOnly me = client.createInOnlyExchange();
	    	me.getInMessage().setContent(new StreamSource(fis));
	    	CaXchangeDataUtil util = (CaXchangeDataUtil)context.getBean("xpathUtil");
	    	util.setIn(me.getMessage("in"));
	    	util.initialize();
	    	String delegationReference = util.getDelegatedCredentialReference();
	    	assertNotNull(delegationReference);
	    	System.out.println(delegationReference);
		  }catch(Exception e) {
			  throw new RuntimeException(e);
		  }
		}	
	
	public void testUserName() {
		  try {
	    	DefaultServiceMixClient client = new DefaultServiceMixClient(jbi);
	    	InputStream fis = getClass().getClassLoader().getResourceAsStream("testmessage.xml");
	    	InOnly me = client.createInOnlyExchange();
	    	me.getInMessage().setContent(new StreamSource(fis));
	    	CaXchangeDataUtil util = (CaXchangeDataUtil)context.getBean("xpathUtil");
	    	util.setIn(me.getMessage("in"));
	    	util.initialize();
	    	String userName = util.getUserName();
	    	assertEquals(userName,"testuser");
		  }catch(Exception e) {
			  throw new RuntimeException(e);
		  }
		}	
	
	public void testMessageType() {
		  try {
	    	DefaultServiceMixClient client = new DefaultServiceMixClient(jbi);
	    	InOnly me = client.createInOnlyExchange();
	    	me.getInMessage().setContent(getSource("testmessage.xml"));
	    	CaXchangeDataUtil util = (CaXchangeDataUtil)context.getBean("xpathUtil");
	    	util.setIn(me.getMessage("in"));
	    	util.initialize();
	    	String messageType = util.getServiceType();
	    	assertEquals(messageType,"REGISTER_SUBJECT");
		  }catch(Exception e) {
			  throw new RuntimeException(e);
		  }
		}	
	
	public void testBusinessPayload() {
		  try {
	    	DefaultServiceMixClient client = new DefaultServiceMixClient(jbi);
	    	InputStream fis = getClass().getClassLoader().getResourceAsStream("testmessage.xml");
	    	InOnly me = client.createInOnlyExchange();
	    	me.getInMessage().setContent(new StreamSource(fis));
	    	CaXchangeDataUtil util = (CaXchangeDataUtil)context.getBean("xpathUtil");
	    	util.setIn(me.getMessage("in"));
	    	util.initialize();
	    	Node node = util.getBusinessPayload();
	    	Transformer transformer = TransformerFactory.newInstance().newTransformer();
	    	transformer.transform(new DOMSource(node), new StreamResult(System.out));
	    	assertNotNull(node);
		  }catch(Exception e) {
			  throw new RuntimeException(e);
		  }
		}	

	public void testRollback() {
		  try {
	    	DefaultServiceMixClient client = new DefaultServiceMixClient(jbi);
	    	InputStream fis = getClass().getClassLoader().getResourceAsStream("testmessage.xml");
	    	InOnly me = client.createInOnlyExchange();
	    	me.getInMessage().setContent(new StreamSource(fis));
	    	CaXchangeDataUtil util = (CaXchangeDataUtil)context.getBean("xpathUtil");
	    	util.setIn(me.getMessage("in"));
	    	util.initialize();
	    	Source source = util.getRollbackMessage();
	    	Transformer transformer = TransformerFactory.newInstance().newTransformer();
	    	transformer.transform(source, new StreamResult(System.out));
	    	assertNotNull(source);
		  }catch(Exception e) {
			  throw new RuntimeException(e);
		  }
		}	
	

}
