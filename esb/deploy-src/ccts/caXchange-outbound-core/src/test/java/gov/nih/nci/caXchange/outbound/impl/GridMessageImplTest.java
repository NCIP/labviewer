package gov.nih.nci.caXchange.outbound.impl;

import gov.nih.nci.caXchange.outbound.GridSUTest;

import java.io.InputStream;

import javax.xml.transform.stream.StreamSource;

import org.apache.servicemix.jbi.jaxp.SourceTransformer;
import org.w3c.dom.Document;

import junit.framework.TestCase;

public class GridMessageImplTest extends TestCase {
    
    public void testRegistrationMessage() throws Exception {
	InputStream fis = GridSUTest.class.getResourceAsStream("registration-request.xml");
	Document document = new SourceTransformer().toDOMDocument(new StreamSource(fis));
	GridMessageImpl message = new GridMessageImpl(document);
	assertNotNull(message.getMetaData());
	Document metaData =
	    new SourceTransformer().toDOMDocument(message.getMetaData());
	
	System.out.println(new SourceTransformer().toString(metaData));
	assertNotNull(message.getPayload());
	
	Document payload =
	    new SourceTransformer().toDOMDocument(message.getPayload());
	
	System.out.println(new SourceTransformer().toString(payload));
	
	assertNotNull(message.getSchemaDefinition());
    }
    
    public void testStudyMessage() throws Exception {
    	InputStream fis = GridSUTest.class.getResourceAsStream("study-request.xml");
    	Document document = new SourceTransformer().toDOMDocument(new StreamSource(fis));
    	GridMessageImpl message = new GridMessageImpl(document);
    	assertNotNull(message.getMetaData());
    	Document metaData =
    	    new SourceTransformer().toDOMDocument(message.getMetaData());
    	
    	System.out.println(new SourceTransformer().toString(metaData));
    	assertNotNull(message.getPayload());
    	
    	Document payload =
    	    new SourceTransformer().toDOMDocument(message.getPayload());
    	
    	System.out.println(new SourceTransformer().toString(payload));
    	
    	assertNotNull(message.getSchemaDefinition());
        }

}
