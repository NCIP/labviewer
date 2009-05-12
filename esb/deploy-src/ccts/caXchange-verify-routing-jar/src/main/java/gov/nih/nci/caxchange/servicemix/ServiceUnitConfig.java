package gov.nih.nci.caxchange.servicemix;


import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * A class that reads the initializes for verification service unit configuration
 * files.
 *
 * @author Harsh Marwaha
 *
 */
public abstract class ServiceUnitConfig {

	protected String fileName;
	protected URL url;
	protected Document configuration;
	protected DocumentBuilderFactory documentBuilderFactory;
	protected DocumentBuilder documentBuilder;
	protected static Logger logger = LogManager.getLogger(ServiceUnitConfig.class);
    protected XPathFactory xpathFactory = XPathFactory.newInstance();


    /**
     * Initialize the class by loading the serivcemix service unit configuration
     * file.
     *
     * @throws ConfigurationException
     */
    public void initialize() throws ConfigurationException {
    	InputStream inputStream =null;
       try {
    	  documentBuilderFactory =  DocumentBuilderFactory.newInstance();
    	  documentBuilder = documentBuilderFactory.newDocumentBuilder();
    	  if (url != null) {
			  try {
			   inputStream = url.openStream();
		      }catch(java.io.IOException e) {
				  throw new ConfigurationException("Configuration file not found from url:"+url.getPath());
		      }
	      }
	      if (inputStream == null) {
    	     inputStream = ServiceUnitConfig.class.getClassLoader().getResourceAsStream(fileName);
	  	  }
    	  if (inputStream==null) {
    		  throw new ConfigurationException("Configuration file not found:"+fileName);
    	  }
    	  configuration = documentBuilder.parse(inputStream);
    	  NodeList imports = getImports();
    	  int i=0;
    	  while((imports!=null)&&(i<imports.getLength())) {
    		  processAnImport(imports.item(i++));
    	  }
       }catch(Exception e) {
    	   logger.error("Error initializing beans config");
    	   throw new  ConfigurationException("Error initializing beans config",e);
       }
    }

    /**
     * Returns the import nodes in this document.
     *
     * @return
     * @throws ConfigurationException
     */
    public NodeList getImports() throws ConfigurationException {
    	try {
    		XPath xpath = xpathFactory.newXPath();
    	    XPathExpression beanExpression = xpath.compile("/beans/import");
    	    NodeList imports = (NodeList)beanExpression.evaluate(configuration, XPathConstants.NODESET);
    	    return imports;
    	}catch(Exception e) {
    		logger.error("Error getting the imports.",e);
    		throw new ConfigurationException("Error getting the imports.",e);
    	}
    }

    /**
     * Processes an import node to load it and import it into the current document.
     *
     * @param importDocument
     * @throws ConfigurationException
     */
    public void processAnImport(Node importDocument) throws ConfigurationException {
    	try {
            if (importDocument == null) return;
    	    Node resource = importDocument.getAttributes().getNamedItem("resource");
            String resourcePath = resource.getNodeValue().substring("classpath:/".length());
            InputStream inputStream = ServiceUnitConfig.class.getClassLoader().getResourceAsStream(resourcePath);
      	    if (inputStream==null) {
    		   throw new ConfigurationException("Configuration file not found:"+resourcePath);
    	    }
            Document importedDocument = documentBuilder.parse(inputStream);
       		XPath xpath = xpathFactory.newXPath();
    	    XPathExpression beanExpression = xpath.compile("/beans/*");
    	    NodeList children = (NodeList)beanExpression.evaluate(importedDocument, XPathConstants.NODESET);
            int i=0;
            while((children!=null)&&(i<children.getLength())) {
            	Node adoptedNode = configuration.importNode(children.item(i++),true);
            	configuration.getDocumentElement().appendChild(adoptedNode);
            }
    	}catch(Exception e) {
    		logger.error("Error processing imports.",e);
    		throw new ConfigurationException("Error processing imports.",e);
    	}
    }
    
    public boolean serviceNameExists(String serviceName) throws ConfigurationException {
    	return false;
    }


    /**
     * Prints a node to the given OutputStream.
     *
     * @param node
     * @param stream
     * @throws ConfigurationException
     */
    public void printANode(Node node, OutputStream stream) throws ConfigurationException {
    	try {
    		if (node==null) {
    			return;
    		}
    		Source source = new DOMSource(node);
    		TransformerFactory transformerFactory = TransformerFactory.newInstance();
    		Transformer transformer = transformerFactory.newTransformer();
    		Result result = new StreamResult(stream);
    		transformer.transform(source, result);
    	}catch(Exception e){
    		logger.error("Error printing node:"+node.getNodeName(),e);
    		throw new  ConfigurationException("Error printing node:"+node.getNodeName(),e);
    	}
    }

	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public URL getURL() {
		return url;
	}
	public void setURL(URL url) {
		this.url = url;
	}

}
