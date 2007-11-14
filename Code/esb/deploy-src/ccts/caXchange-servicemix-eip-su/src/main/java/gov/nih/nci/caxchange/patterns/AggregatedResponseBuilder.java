package gov.nih.nci.caxchange.patterns;

import java.util.Iterator;
import java.util.List;

import javax.jbi.messaging.NormalizedMessage;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import javax.xml.transform.Source;

import org.apache.servicemix.jbi.jaxp.SourceTransformer;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class AggregatedResponseBuilder {
    static DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
    
    String timeOutResponse ="            <businessResponse>\n" + 
    "                <targetServiceIdentifier>targetServiceIdentifier0</targetServiceIdentifier>\n" + 
    "                <targetServiceOperation>targetServiceOperation0</targetServiceOperation>\n" + 
    "                <messageStatus>RESPONSE</messageStatus>\n" + 
    "                <messagePayload>\n" + 
    "                    <xmlSchemaDefinition>http://www.oxygenxml.com/</xmlSchemaDefinition>\n" + 
    "                </messagePayload>\n" + 
    "            </businessResponse>";
    
    String faultResponse = "            <businessResponse>\n" + 
    "                <targetServiceIdentifier>targetServiceIdentifier0</targetServiceIdentifier>\n" + 
    "                <targetServiceOperation>targetServiceOperation0</targetServiceOperation>\n" + 
    "                <messageStatus>RESPONSE</messageStatus>\n" + 
    "                <messagePayload>\n" + 
    "                    <xmlSchemaDefinition>http://www.oxygenxml.com/</xmlSchemaDefinition>\n" + 
    "                </messagePayload>\n" + 
    "            </businessResponse>";
    
    public AggregatedResponseBuilder() {
    }
    
    
    static Document buildAggregatedDocument(List messages, boolean timeOut) throws Exception {
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document =documentBuilder.newDocument();
        Element aggregateResponse = document.createElement("aggregateResponse");
        document.appendChild(aggregateResponse);
        SourceTransformer st = new SourceTransformer();
        if (messages != null) {
            Iterator iterator = messages.iterator();
            while(iterator.hasNext()) {
                NormalizedMessage message=(NormalizedMessage)iterator.next();
                Source content = message.getContent();
                Document doc = st.toDOMDocument(content);
                Node importedNode= document.importNode(doc.getDocumentElement(), true);
                aggregateResponse.appendChild(importedNode);
            }
        }
        return document;
    }
}
