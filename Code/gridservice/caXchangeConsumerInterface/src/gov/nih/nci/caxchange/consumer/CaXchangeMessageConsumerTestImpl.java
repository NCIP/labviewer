package gov.nih.nci.caxchange.consumer;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
/**
 * Implements a default implementation of the caXchange Consumer Service. Each of the method logs
 * the payload received and returns a success response.
 * 
 * @author Ekagra Software Technologies
 *
 */
public class CaXchangeMessageConsumerTestImpl implements CaXchangeMessageConsumer {
	Logger logger = LogManager.getLogger(CaXchangeMessageConsumerTestImpl.class);
    String returnMessage = "<result>Success from CaXchangeMessageConsumerTestImpl</result>";
    /**
     * Implements the commit method.
     */
	public void commit(Node payload) throws CaXchangeConsumerException {
       	logger.info("Commit method invoked.");	
       	logger.info("Payload:" + getNodeAsString(payload));
	}
     /**
      * Implements the process method. Returns a success response.
      */
	public Node process(Node payload) throws CaXchangeConsumerException {
       	logger.info("Process method invoked.");	
       	logger.info("Payload:" + getNodeAsString(payload));
       	logger.info("Returning response");
       	try {
          DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
          DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
          ByteArrayInputStream bais = new ByteArrayInputStream(returnMessage.getBytes());
          Document document= documentBuilder.parse(bais);
          return document;
       	}catch(Exception e) {
       		logger.error("Error parsing return document.",e);
       		throw new CaXchangeConsumerException("Error parsing return document.",e);
       	}
	}

	public void rollback(Node payload) throws CaXchangeConsumerException {
       	logger.info("Rollback method invoked.");	
       	logger.info("Payload:" + getNodeAsString(payload));
	}
	
	public String getNodeAsString(Node node) {
		
		if (node==null) {
			return null;
		}
		try {
		  Transformer transformer = TransformerFactory.newInstance().newTransformer();
		  Source source = new DOMSource(node);
		  StringWriter stringWriter = new StringWriter();
		  Result result = new StreamResult(stringWriter);
		  transformer.transform(source, result);
		  return stringWriter.toString();
		}catch(Exception e) {
			logger.error("Error transforming the node to string.",e);
			return "Error transforming the node to string.";
		}
	}
	
	

}
