package gov.nih.nci.cagrid.caxchange.service;

import gov.nih.nci.cagrid.introduce.servicetools.ServiceConfiguration;

import org.globus.wsrf.config.ContainerConfig;
import java.io.File;
import javax.naming.InitialContext;

import org.apache.axis.MessageContext;
import org.globus.wsrf.Constants;


/** 
 * DO NOT EDIT:  This class is autogenerated!
 * 
 * This class holds all service properties which were defined for the service to have
 * access to.
 * 
 * @created by Introduce Toolkit version 1.2
 * 
 */
public class CaXchangeRequestProcessorConfiguration implements ServiceConfiguration {

	public static CaXchangeRequestProcessorConfiguration  configuration = null;

	public static CaXchangeRequestProcessorConfiguration getConfiguration() throws Exception {
		if (CaXchangeRequestProcessorConfiguration.configuration != null) {
			return CaXchangeRequestProcessorConfiguration.configuration;
		}
		MessageContext ctx = MessageContext.getCurrentContext();

		String servicePath = ctx.getTargetService();

		String jndiName = Constants.JNDI_SERVICES_BASE_NAME + servicePath + "/serviceconfiguration";
		try {
			javax.naming.Context initialContext = new InitialContext();
			CaXchangeRequestProcessorConfiguration.configuration = (CaXchangeRequestProcessorConfiguration) initialContext.lookup(jndiName);
		} catch (Exception e) {
			throw new Exception("Unable to instantiate service configuration.", e);
		}

		return CaXchangeRequestProcessorConfiguration.configuration;
	}
	
	private String etcDirectoryPath;
	
	
	private String caXchangeInboundBrokerURL;
	
	private String caXchangeSynchronousServiceURL;
	
	private String caXchangeInboundDestination;
	
	private String caXchangeResponseDestination;
	
	
	public String getEtcDirectoryPath() {
		return ContainerConfig.getBaseDirectory() + File.separator + etcDirectoryPath;
	}
	
	public void setEtcDirectoryPath(String etcDirectoryPath) {
		this.etcDirectoryPath = etcDirectoryPath;
	}

	
	public String getCaXchangeInboundBrokerURL() {
		return caXchangeInboundBrokerURL;
	}
	
	
	public void setCaXchangeInboundBrokerURL(String caXchangeInboundBrokerURL) {
		this.caXchangeInboundBrokerURL = caXchangeInboundBrokerURL;
	}

	
	public String getCaXchangeInboundDestination() {
		return caXchangeInboundDestination;
	}
	
	
	public void setCaXchangeInboundDestination(String caXchangeInboundDestination) {
		this.caXchangeInboundDestination = caXchangeInboundDestination;
	}

	
	public String getCaXchangeResponseDestination() {
		return caXchangeResponseDestination;
	}
	
	
	public void setCaXchangeResponseDestination(String caXchangeResponseDestination) {
		this.caXchangeResponseDestination = caXchangeResponseDestination;
	}

	public String getCaXchangeSynchronousServiceURL() {
		return caXchangeSynchronousServiceURL;
	}

	public void setCaXchangeSynchronousServiceURL(
			String caXchangeSynchronousServiceURL) {
		this.caXchangeSynchronousServiceURL = caXchangeSynchronousServiceURL;
	}

	
}
