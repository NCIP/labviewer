package gov.nih.nci.caxchange.eip;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import javax.xml.namespace.NamespaceContext;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import gov.nih.nci.caxchange.beans.BeansConfig;
import gov.nih.nci.caxchange.servicemix.ServiceUnitConfig;
import gov.nih.nci.caxchange.transform.TransformConfig;


/**
 * A class to read/verify the EIP service unit configuration file.
 * 
 * @author Harsh Marwaha
 *
 */
public class EipConfig extends ServiceUnitConfig {

	private static Logger logger = LogManager.getLogger(EipConfig.class);
	private List<ServiceUnitConfig> configs = new ArrayList<ServiceUnitConfig>();
	private int routingErrors=0;
	protected Stack<String> serviceNamesStack = new Stack<String>();


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
    		if (serviceNamesStack.contains(getServiceNameForNode(node))) {
    			return;
    		}
    		serviceNamesStack.push(getServiceNameForNode(node));
    		//printANode(node,System.out);
    		Set<String> serviceNames  = getExchangeTargetsForANode(node);
    		for(String serviceName:serviceNames){
    		  if (!EipConfigConstants.ignoreServiceNames.contains(serviceName)) {
    			Node targetNode= getComponentByServiceName(serviceName);
    			boolean beanEndPoint = false;
    			if (targetNode == null){
    				//try to verify if it is a  endpoint in other service units
    				Iterator<ServiceUnitConfig> configI = configs.iterator();
    				while(configI.hasNext() && !beanEndPoint) {
    					ServiceUnitConfig config = configI.next();
    					beanEndPoint = config.serviceNameExists(serviceName);
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
    		serviceNamesStack.pop();
    	}catch(Exception e) {
    		logger.error("Error verifying routing for:"+node,e);
    		throw new EipConfigurationException("Error verifying routing for:"+node,e);
    	}
    }
    
    public String getServiceNameForNode(Node node) throws EipConfigurationException {
        if (node == null) return null;
        NamedNodeMap attrs = node.getAttributes();
        if (attrs == null) return null;
        Node attr = attrs.getNamedItem("service");
        if (attr == null) return null;
        return attr.getNodeValue();
    }
    /**
     * This method returns a String[] of message types configured within caXchange.
     * @return
     * @throws EipConfigurationException
     */
    public String[] getConfiguredMessageTypes()throws EipConfigurationException  {
    	try {
    		Node messageTypeRouter = getComponentByServiceName("caxchange:MessageTypeRouter");
    		XPath xpath = xpathFactory.newXPath();
    	    XPathExpression xpathExpression = xpath.compile("..//@xpath");
    	    NodeList xpaths = (NodeList)xpathExpression.evaluate(messageTypeRouter.getFirstChild(), XPathConstants.NODESET);
    	    String[] messageTypes = new String[xpaths.getLength()];
    	    int i=0;
    	    while((xpaths!=null)&&(i<xpaths.getLength())) {
    	    	Node xpathNode = xpaths.item(i);
    	    	String xpathMessageType = xpathNode.getNodeValue();
    	    	String messageType = xpathMessageType.substring(xpathMessageType.indexOf("caxchange:messageType='")+23, 
    	    			                                           xpathMessageType.indexOf("'",xpathMessageType.indexOf("caxchange:messageType='")+23));
    	    	System.out.println(messageType);		                                           
    	    	messageTypes[i] = messageType;
    	    	i++;
    	    	
    	    }
    	    return messageTypes;
    	}catch(Exception e){
    		logger.error("Error getting message types.",e);
    		throw new EipConfigurationException("Error getting message types.",e);
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
		    Node router = eipConfig.getComponentByServiceName("caxchange:serviceTypeRouter");
			//eipConfig.printANode(router, System.out);
			Set<String> serviceNames = eipConfig.getExchangeTargetsForANode(router);
			BeansConfig beansConfig = new BeansConfig();
			beansConfig.setFileName("caXchangeConf/beans/beans.xml");
			beansConfig.initialize();
			TransformConfig transformConfig = new TransformConfig();
			transformConfig.setFileName("caXchangeConf/transform/transforms.xml");
			transformConfig.initialize();
			eipConfig.addConfig(beansConfig);
			eipConfig.addConfig(transformConfig);
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
	

	public List<ServiceUnitConfig> getConfigs() {
		return configs;
	}

	public void setConfigs(List<ServiceUnitConfig> configs) {
		this.configs = configs;
	}
	
	public void addConfig(ServiceUnitConfig config) {
		this.configs.add(config);
	}


	public int getRoutingErrors() {
		return routingErrors;
	}


}
