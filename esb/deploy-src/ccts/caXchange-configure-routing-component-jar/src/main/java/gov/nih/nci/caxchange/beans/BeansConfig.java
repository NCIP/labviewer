package gov.nih.nci.caxchange.beans;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class BeansConfig {
	
	private String fileName;
    private Document beansConfiguration;
    private static Logger logger = LogManager.getLogger(BeansConfig.class);
    private static XPathFactory xpathFactory = XPathFactory.newInstance();
    private XPathExpression beanEndpointXPath = null;
	
    public BeansConfig() {
    	
    }
    
    public void initialize() throws BeansConfigurationException {
       try {
    	  DocumentBuilderFactory documentBuilderFactory =  DocumentBuilderFactory.newInstance();
    	  DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
    	  InputStream inputStream = BeansConfig.class.getClassLoader().getResourceAsStream(fileName);
    	  beansConfiguration = documentBuilder.parse(inputStream);
       }catch(Exception e) {
    	   logger.error("Error initializing beans config");
    	   throw new BeansConfigurationException("Error initializing beans config",e);
       }
    }
    
    public boolean beanServiceNameExists(String serviceName) throws BeansConfigurationException {
    	if (serviceName==null){
    		return true;
    	}
    	try {
           if (	beanEndpointXPath == null) {
        	   XPath xpath = xpathFactory.newXPath();
        	   beanEndpointXPath = xpath.compile(BeansConfigConstants.BEAN_ENDPOINT_XPATH);
           }
    	}catch(Exception e){
    		logger.error("Error compiling Bean endpoint expression.",e);
    		throw new BeansConfigurationException("Error compiling Bean endpoint expression.",e);
    	}
    	boolean match=false;
    	try {
    	   NodeList beanEndpoints = (NodeList)beanEndpointXPath.evaluate(beansConfiguration, XPathConstants.NODESET);
    	   if (beanEndpoints != null) {
    		   int i= 0;
    		   while((i<beanEndpoints.getLength())&&(!match)) {
    			   Node endpoint = beanEndpoints.item(i);
    			   Node attrService = endpoint.getAttributes().getNamedItem("service");
    			   if (serviceName.equals(attrService.getNodeValue())) {
    				   match=true;
    			   }
    			   i++;
    		   }
    	   }
    	}catch(Exception e){
    		logger.error("Error matching service endpoints.",e);
    		throw new BeansConfigurationException("Error matching service endpoints.",e);
    	}
    	   
    	return match;
    }
    
    
    public void addGridOutboundComponent(OutboundComponentConfig ocConfig) throws BeansConfigurationException {
    	try {
    		
    	}catch(Exception e){
     	}
    }

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			BeansConfig beansConfig = new BeansConfig();
			beansConfig.setFileName("beansconfig.xml");
			beansConfig.initialize();
			System.out.println(beansConfig.beanServiceNameExists("caxchange:ctomRegistration"));
		}catch(Exception e){
			e.printStackTrace();
		}

	}
	


}
