package gov.nih.nci.caxchange.eip;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.xml.namespace.NamespaceContext;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import gov.nih.nci.caxchange.beans.BeansConfig;
import gov.nih.nci.caxchange.servicemix.ServiceUnitConfig;


/**
 * A class to read/verify the EIP service unit configuration file.
 * 
 * @author Harsh Marwaha
 *
 */
public class EipConfig extends ServiceUnitConfig {

	private static Logger logger = LogManager.getLogger(EipConfig.class);
	private BeansConfig beansConfig;
	private int routingErrors=0;


    /**
     * Returns an EIP component for a given service name.
     * 
     * @param serviceName
     * @return
     * @throws EipConfigurationException
     */
    public Node getComponentByServiceName(String serviceName) throws EipConfigurationException {
    	try {
    		XPath xpath = xpathFactory.newXPath();
    	    XPathExpression beanExpression = xpath.compile("/beans/*[@service=\""+serviceName+"\"]");
    	    Node bean = (Node)beanExpression.evaluate(configuration, XPathConstants.NODE);
    	    return bean;
    	}catch(Exception e) {
    		logger.error("Error getting the node for service name:"+serviceName+".",e);
    		throw new EipConfigurationException("Error getting the node for service name:"+serviceName+".",e);
    	}
    }
    
    /**
     * Gets the exchange-targets within a Node. These are the target service names
     * that a given EIP element can route messages to.
     * 
     * @param node
     * @return
     * @throws EipConfigurationException
     */
    public Set getExchangeTargetsForANode(Node node) throws EipConfigurationException {
    	Set exchangeTargetServiceNames = new HashSet();
    	try {
    		XPath xpath = xpathFactory.newXPath();
    	    XPathExpression beanExpression = xpath.compile("..//exchange-target");
    	    NodeList targets = (NodeList)beanExpression.evaluate(node.getFirstChild(), XPathConstants.NODESET);
    	    int i=0;
    	    while((targets!=null)&&(i<targets.getLength())) {
    	    	Node target = targets.item(i++);
    	    	exchangeTargetServiceNames.add(target.getAttributes().getNamedItem("service").getNodeValue());
    	    }
    	    return exchangeTargetServiceNames;
    	}catch(Exception e) {
    		logger.error("Error getting the target service names for :"+configuration+".",e);
    		throw new EipConfigurationException("Error getting thethe target service names for :"+configuration+".",e);
    	}    	
    }
    
    
    /**
     * Recursively verify that the routing of an EIP node is correct. It gets
     * all the target service names of the given node. Gets the nodes for the
     * target service names and verifies routing for those nodes. If a node is 
     * not found for a given service name, a message is printed that the routing
     * is not configured for the service name.
     * 
     * @param node
     * @throws EipConfigurationException
     */
    public void verifyRouting(Node node) throws EipConfigurationException {
    	try {
    		//printANode(node,System.out);
    		Set<String> serviceNames  = getExchangeTargetsForANode(node);
    		for(String serviceName:serviceNames){
    		  if (!EipConfigConstants.ignoreServiceNames.contains(serviceName)) {
    			Node targetNode= getComponentByServiceName(serviceName);
    			boolean beanEndPoint = false;
    			if (targetNode == null){
    				//try to verify if it is a beans endpoint:
    				if (beansConfig!=null) {
    					beanEndPoint = beansConfig.beanServiceNameExists(serviceName);
    				}
    			}
    			if ((targetNode == null)&&(!beanEndPoint)){
    				routingErrors++;
    				System.out.println("\nNo routing found for "+serviceName+". This service is referred in:  ");
    				printANode(node, System.out);
    			}else if (targetNode != null) {
    				verifyRouting(targetNode);
    			}
    		  }
    		}
    	}catch(Exception e) {
    		logger.error("Error verifying routing for:"+node,e);
    		throw new EipConfigurationException("Error verifying routing for:"+node,e);
    	}
    }
    

	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			EipConfig eipConfig = new EipConfig();
			eipConfig.setFileName("caXchangeConf/eip/eip.xml");
			eipConfig.initialize();
		    Node router = eipConfig.getComponentByServiceName("caxchange:MessageTypeRouter");
			//eipConfig.printANode(router, System.out);
			Set<String> serviceNames = eipConfig.getExchangeTargetsForANode(router);
			BeansConfig beansConfig = new BeansConfig();
			beansConfig.setFileName("caXchangeConf/beans/beans.xml");
			beansConfig.initialize();
			eipConfig.setBeansConfig(beansConfig);
			eipConfig.verifyRouting(router);
			System.out.println("\n\nThere were "+eipConfig.getRoutingErrors()+" routing errors detected.");
		}catch(Exception e){
			e.printStackTrace();
		}

	}
	
	/**
	 * Initialize routing errors before calling verify routing. To get the 
	 * correct count of routing errors.
	 */
	protected void initializeRoutingErrors(){
		routingErrors=0;
	}
	

	public BeansConfig getBeansConfig() {
		return beansConfig;
	}

	public void setBeansConfig(BeansConfig beansConfig) {
		this.beansConfig = beansConfig;
	}

	public int getRoutingErrors() {
		return routingErrors;
	}


}
