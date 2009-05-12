package gov.nih.nci.caxchange.beans;


import gov.nih.nci.caxchange.servicemix.ConfigurationException;
import gov.nih.nci.caxchange.servicemix.ServiceUnitConfig;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.Set;


import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Loads the bean service unit configuration file and exposes methods to verify
 * service names available in the beans service unt.
 * 
 * @author Harsh Marwaha
 *
 */
public class BeansConfig extends ServiceUnitConfig{

	private static Logger logger = LogManager.getLogger(BeansConfig.class);
    private XPathExpression beanEndpointXPath = null;
    private XPathExpression aggregatorListenerXPath = null;
	
    /**
     * Default Constructor.
     */
    public BeansConfig() {
    	
    }
    

    
    /**
     * Gets the set of service names available through this bean service unit.
     * 
     * @return
     * @throws BeansConfigurationException
     */
    public Set getBeanServiceNames() throws BeansConfigurationException {
    	Set beanServiceNames = new HashSet();
    	try {
            if (beanEndpointXPath == null) {
         	   XPath xpath = xpathFactory.newXPath();
         	   beanEndpointXPath = xpath.compile(BeansConfigConstants.BEAN_ENDPOINT_XPATH);
            }
     	}catch(Exception e){
     		logger.error("Error compiling Bean endpoint expression.",e);
     		throw new BeansConfigurationException("Error compiling Bean endpoint expression.",e);
     	}    
    	try {
     	   NodeList beanEndpoints = (NodeList)beanEndpointXPath.evaluate(configuration, XPathConstants.NODESET);
     	   if (beanEndpoints != null) {
     		   int i= 0;
     		   while((i<beanEndpoints.getLength())) {
     			   Node endpoint = beanEndpoints.item(i);
     			   Node attrService = endpoint.getAttributes().getNamedItem("service");
                   beanServiceNames.add(attrService.getNodeValue());
     			   i++;
     		   }
     	   }
     	}catch(Exception e){
     		logger.error("Error matching service endpoints.",e);
     		throw new BeansConfigurationException("Error matching service endpoints.",e);
     	}
     	return beanServiceNames;
     	

    }
    
    /**
     * Verifies that a given service name exists.
     * 
     * @param serviceName
     * @return
     * @throws BeansConfigurationException
     */
    public boolean serviceNameExists(String serviceName) throws ConfigurationException {
    	if (serviceName==null){
    		return true;
    	}
    	boolean match=false;
    	if (getBeanServiceNames().contains(serviceName)) {
    		match = true;
    	}else {
    		match = false;
    	}
    	   
    	return match;
    }
    

    /**
     * Gets the message types that are eligible for rollback.
     * 
     * @return
     * @throws BeansConfigurationException
     */
    public Set getRollbackMessageTypes() throws BeansConfigurationException {
    	Set rollbackMessageTypes = new HashSet();
     	try {
     		Node aggregatorListenerRollbacks = getBeanPropertyNode("aggregatorListener","messageTypesEligibleForRollback");
     		if (aggregatorListenerRollbacks != null) {
     			XPath xpath = xpathFactory.newXPath();
     			XPathExpression rollbackExp = xpath.compile("*/value");
     			NodeList rollbacks = (NodeList)rollbackExp.evaluate(aggregatorListenerRollbacks,XPathConstants.NODESET);
     			int i=0;
     			while((rollbacks!=null)&&(i<rollbacks.getLength())){
     				rollbackMessageTypes.add(rollbacks.item(i).getTextContent());
     				i++;
     			}
     		}
     	}catch(Exception e){
     		logger.error("Error getting rollback message types.", e);
     		throw new BeansConfigurationException("Error getting rollback message types.", e);
     	}
    	return rollbackMessageTypes;
    }
    
    /**
     * Gets the list of end points from the service unit configuration.
     * 
     * @return
     * @throws BeansConfigurationException
     */
    public NodeList getEndPoints() throws BeansConfigurationException {
    	try {
    		XPath xpath = xpathFactory.newXPath();
    	    XPathExpression beanExpression = xpath.compile("/beans/bean:endpoint");
    	    NodeList endpoints = (NodeList)beanExpression.evaluate(configuration, XPathConstants.NODESET);
    	    return endpoints;
    	}catch(Exception e) {
    		logger.error("Error getting the endpoints.",e);
    		throw new BeansConfigurationException("Error getting the endpoints.",e);
    	}	
    }
    
    
    /**
     * Returns the Node corresponding to the bean id.
     * @param beanId
     * @return Node for the given id.
     * @throws BeansConfigurationException
     */
    public Node getBeanNode(String beanId) throws BeansConfigurationException {
    	try {
    		XPath xpath = xpathFactory.newXPath();
    	    XPathExpression beanExpression = xpath.compile("/beans/bean[@id=\""+beanId+"\"]");
    	    Node bean = (Node)beanExpression.evaluate(configuration, XPathConstants.NODE);
    	    return bean;
    	}catch(Exception e) {
    		logger.error("Error getting the bean node:"+beanId+".",e);
    		throw new BeansConfigurationException("Error getting the bean node:"+beanId+".",e);
    	}
    }
    
    /**
     * Gets the property node for the given beanId and the property name.
     * @param beanId
     * @param propertyName
     * @return property Node.
     * @throws BeansConfigurationException
     */
    public Node getBeanPropertyNode(String beanId, String propertyName) throws BeansConfigurationException {
    	try {
    		XPath xpath = xpathFactory.newXPath();
    	    XPathExpression beanExpression = xpath.compile("/beans/bean[@id=\""+beanId+"\"]/property[@name=\""+propertyName+"\"]");
    	    Node property = (Node)beanExpression.evaluate(configuration, XPathConstants.NODE);
    	    return property;
    	}catch(Exception e) {
    		logger.error("Error getting the property  node for bean:"+beanId+"."+propertyName,e);
    		throw new BeansConfigurationException("Error getting the property  node for bean:"+beanId+"."+propertyName,e);
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
			beansConfig.setFileName("caXchangeConf/beans/beans.xml");
			beansConfig.initialize();
			System.out.println(beansConfig.beanServiceNameExists("caxchange:ctomRegistration"));
			System.out.println(beansConfig.getRollbackMessageTypes());
			NodeList imports = beansConfig.getImports();
			int i=0;
			while((imports!=null)&&(i<imports.getLength())) {
				beansConfig.processAnImport(imports.item(i));
				i++;
			}
		}catch(Exception e){
			e.printStackTrace();
		}

	}
	


}
