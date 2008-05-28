package gov.nih.nci.caxchange.patterns;

import gov.nih.nci.caXchange.CaxchangeErrors;

import java.io.StringReader;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.jbi.messaging.NormalizedMessage;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import javax.xml.transform.Source;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.servicemix.jbi.jaxp.SourceTransformer;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import org.xml.sax.InputSource;
/**
 * This class builds the aggregated response after 
 * getting responses from all the target grid services
 * @author hmarwaha
 *
 */
public class AggregatedResponseBuilder {
    static DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
    static Logger logger = LogManager.getLogger(AggregatedResponseBuilder.class);

    static String timeOutResponse ="  <targetResponse>\n" +
"                <targetServiceIdentifier>@targetServiceIdentifier@</targetServiceIdentifier>\n" +
"                <targetServiceOperation></targetServiceOperation>\n" +
"                <targetMessageStatus>ERROR</targetMessageStatus>\n" +
"                   <targetError>  "+
"                        <errorCode>"+CaxchangeErrors.TIMEOUT+"</errorCode>  "+
"                        <errorDescription>A timeout occurred waiting for response.</errorDescription> "+
"                    </targetError> "+
"            </targetResponse>";

    protected static Document timeOutDocument=null;

    String faultResponse = "            <businessResponse>\n" +
    "                <targetServiceIdentifier>targetServiceIdentifier0</targetServiceIdentifier>\n" +
    "                <targetServiceOperation>targetServiceOperation0</targetServiceOperation>\n" +
    "                <messageStatus>RESPONSE</messageStatus>\n" +
    "                <messagePayload>\n" +
    "                    <xmlSchemaDefinition>http://www.oxygenxml.com/</xmlSchemaDefinition>\n" +
    "                </messagePayload>\n" +
    "            </businessResponse>";
    
    /**
     * Default constructor
     */
    public AggregatedResponseBuilder() {
    }

    /**
     * This methods scans the list of messages form all the target 
     * grid services and builds the response document to send it to 
     * listener aggregator
     * @param messages
     * @param timeOut
     * @return document
     * @throws Exception
     */
    static Document buildAggregatedDocument(List messages, boolean timeOut, Set exchangesToReceive) throws Exception {
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
        //Create the timeout response for the services which have not yet responded.
        if (timeOut) {
        	if (exchangesToReceive != null) {
        	   Iterator exchangesToReceiveIterator = exchangesToReceive.iterator();
        	   while (exchangesToReceiveIterator.hasNext()) {
        		     String exchangeIdentifier = (String)exchangesToReceiveIterator.next();
        		     logger.debug("******"+exchangeIdentifier);
        		     String serviceTimeOutResponse = timeOutResponse.replace("@targetServiceIdentifier@", exchangeIdentifier);
        		     logger.debug("******"+serviceTimeOutResponse);
			         timeOutDocument = documentBuilder.parse(new InputSource(new StringReader(serviceTimeOutResponse)));
                     Node importedNode = document.importNode(timeOutDocument.getDocumentElement(), true);
                     aggregateResponse.appendChild(importedNode);
        	   }
        	}
     	}
        return document;
    }
}
