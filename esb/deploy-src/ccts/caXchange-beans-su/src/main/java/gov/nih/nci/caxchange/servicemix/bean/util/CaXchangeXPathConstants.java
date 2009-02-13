package gov.nih.nci.caxchange.servicemix.bean.util;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

public class CaXchangeXPathConstants {
	private static XPath xpath = null;
    public static XPathExpression targetServiceIdentifierExp = null;
    public static XPathExpression targetServiceOperationExp = null;
    public static XPathExpression targetMessageStatusExp = null;
    public static XPathExpression targetBusinessMessageExp = null;
    public static XPathExpression targetErrorExp = null;
    public static XPathExpression xmlSchemaDefinitionExp = null;
    public static XPathExpression aggregateResponseTargetResponseExp = null;
    static {
    	CaXchangeXPathConstants xpathConstants = new CaXchangeXPathConstants();
    	xpathConstants.init();
    }

    public  XPath init() throws IllegalStateException {
    	try {
    	  XPath xpath = XPathFactory.newInstance().newXPath();
    	  targetServiceIdentifierExp = xpath.compile("/targetResponse/targetServiceIdentifier");
    	  targetServiceOperationExp = xpath.compile("/targetResponse/targetServiceOperation");
    	  targetMessageStatusExp = xpath.compile("/targetResponse/targetMessageStatus");
    	  targetBusinessMessageExp = xpath.compile("/targetResponse/targetBusinessMessage");
    	  targetErrorExp = xpath.compile("/targetResponse/targetError");
    	  xmlSchemaDefinitionExp = xpath.compile("xmlSchemaDefinition");
    	  aggregateResponseTargetResponseExp = xpath.compile("/aggregateResponse/targetResponse");
    	  return xpath;
    	}catch(Exception e) {
    		throw new IllegalStateException("Error compile xpaths.",e);
    	}   	
    }
    
}
