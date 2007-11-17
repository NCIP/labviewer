package gov.nih.nci.caXchange.outbound.impl;

import gov.nih.nci.caXchange.outbound.GridSUTest;

import java.io.InputStream;

import javax.xml.transform.stream.StreamSource;

import org.apache.servicemix.jbi.jaxp.SourceTransformer;
import org.w3c.dom.Document;

import junit.framework.TestCase;

public class GridMessageImplTest extends TestCase {
    
    public void testBasicFunctionality() throws Exception {
	InputStream fis = GridSUTest.class.getResourceAsStream("request.xml");
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
