package gov.nih.nci.caxchange.transform;

import java.util.HashSet;
import java.util.Set;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import gov.nih.nci.caxchange.beans.BeansConfigConstants;
import gov.nih.nci.caxchange.beans.BeansConfigurationException;
import gov.nih.nci.caxchange.servicemix.ConfigurationException;
import gov.nih.nci.caxchange.servicemix.ServiceUnitConfig;

public class TransformConfig extends ServiceUnitConfig {
	
	private static Logger logger = LogManager.getLogger(TransformConfig.class);
    private XPathExpression transformEndpointXPath = null;
    
    
    /**
     * Gets the set of service names available through this bean service unit.
     * 
     * @return
     * @throws BeansConfigurationException
     */
    public Set getTransformServiceNames() throws BeansConfigurationException {
    	Set transformServiceNames = new HashSet();
    	try {
            if (transformEndpointXPath == null) {
         	   XPath xpath = xpathFactory.newXPath();
         	   transformEndpointXPath = xpath.compile(TransformConfigConstants.TRANSFORM_ENDPOINT_XPATH);
            }
     	}catch(Exception e){
     		logger.error("Error compiling Bean endpoint expression.",e);
     		throw new BeansConfigurationException("Error compiling Bean endpoint expression.",e);
     	}    
    	try {
     	   NodeList transformEndpoints = (NodeList)transformEndpointXPath.evaluate(configuration, XPathConstants.NODESET);
     	   if (transformEndpoints != null) {
     		   int i= 0;
     		   while((i<transformEndpoints.getLength())) {
     			   Node endpoint = transformEndpoints.item(i);
     			   Node attrService = endpoint.getAttributes().getNamedItem("service");
     			   transformServiceNames.add(attrService.getNodeValue());
     			   i++;
     		   }
     	   }
     	}catch(Exception e){
     		logger.error("Error matching service endpoints.",e);
     		throw new BeansConfigurationException("Error matching service endpoints.",e);
     	}
     	return transformServiceNames;
     	

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
    	if (getTransformServiceNames().contains(serviceName)) {
    		match = true;
    	}else {
    		match = false;
    	}
    	   
    	return match;
    }    

}
