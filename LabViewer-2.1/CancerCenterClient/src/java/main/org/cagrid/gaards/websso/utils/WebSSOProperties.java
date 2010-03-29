package org.cagrid.gaards.websso.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.cagrid.gaards.websso.beans.AuthenticationServiceInformation;
import org.cagrid.gaards.websso.beans.HostInformation;
import org.cagrid.gaards.websso.exception.AuthenticationConfigurationException;
import org.jdom.Document;
import org.jdom.Element;

public class WebSSOProperties
{
	Document propertiesFile = null;
	
	public WebSSOProperties(final FileHelper fileHelper, final String propertiesFileName, final String schemaFileName) throws AuthenticationConfigurationException
	{
		this.propertiesFile = fileHelper.validateXMLwithSchema(propertiesFileName, schemaFileName);
	}
	
	public List<AuthenticationServiceInformation> getAuthenticationServices()
	{
		List<AuthenticationServiceInformation> authenticationServiceInformationList = new ArrayList<AuthenticationServiceInformation>();
		Element webssoProperties = propertiesFile.getRootElement();
		Element authenticationServiceList = webssoProperties.getChild("authentication-service-list");
		List<?> authenticationServices = authenticationServiceList.getChildren("authentication-service");
		Iterator<?> authenticationServicesIterator  = authenticationServices.iterator();
		while(authenticationServicesIterator.hasNext())
		{
		 	Element authenticationService = (Element)authenticationServicesIterator.next();
		 	Element serviceName = authenticationService.getChild("service-name");
		 	Element serviceURL = authenticationService.getChild("service-url");
		 	AuthenticationServiceInformation authenticationServiceInformation = new AuthenticationServiceInformation();
		 	authenticationServiceInformation.setAuthenticationServiceName(serviceName.getText().trim());
		 	authenticationServiceInformation.setAuthenticationServiceURL(serviceURL.getText().trim());
		 	authenticationServiceInformationList.add(authenticationServiceInformation);
		}
		return authenticationServiceInformationList;
	}
	
	public List<HostInformation> getHostInformationList()
	{
		List<HostInformation> hostInformationList = new ArrayList<HostInformation>();
		Element webssoProperties = propertiesFile.getRootElement();
		Element delegatedApplicationList = webssoProperties.getChild("delegated-application-list");
		List<?> delegatedApplications = delegatedApplicationList.getChildren("delegated-application");
		Iterator<?> delegatedApplicationsIterator  = delegatedApplications.iterator();
		while(delegatedApplicationsIterator.hasNext())
		{
		 	Element delegatedApplication = (Element)delegatedApplicationsIterator.next();
		 	Element applicationName = delegatedApplication.getChild("application-name");
		 	Element hostIdentity = delegatedApplication.getChild("host-identity");
		 	HostInformation hostInformation = new HostInformation();
		 	hostInformation.setHostName(applicationName.getText().trim());
		 	hostInformation.setHostIdentity(hostIdentity.getText().trim());
		 	hostInformationList.add(hostInformation);
		}
		return hostInformationList;
	}

	public String getDorianServiceURL()
	{
		Element webssoProperties = propertiesFile.getRootElement();
		Element dorianInformation = webssoProperties.getChild("dorian-information");
		Element serviceURL = dorianInformation.getChild("service-url");
		return serviceURL.getText().trim();
	}
	
	public String getProxyLifeTimeHours()
	{
		Element webssoProperties = propertiesFile.getRootElement();
		Element dorianInformation = webssoProperties.getChild("dorian-information");
		Element proxyLifetimeHours = dorianInformation.getChild("proxy-lifetime-hours");
		return proxyLifetimeHours.getText().trim();
	}
	
	public String getProxyLifeTimeMinutes()
	{
		Element webssoProperties = propertiesFile.getRootElement();
		Element dorianInformation = webssoProperties.getChild("dorian-information");
		Element proxyLifetimeMinutes = dorianInformation.getChild("proxy-lifetime-minutes");
		return proxyLifetimeMinutes.getText().trim();
	}

	public String getProxyLifeTimeSeconds()
	{
		Element webssoProperties = propertiesFile.getRootElement();
		Element dorianInformation = webssoProperties.getChild("dorian-information");
		Element proxyLifetimeSeconds = dorianInformation.getChild("proxy-lifetime-seconds");
		return proxyLifetimeSeconds.getText().trim();
	}

	public String getProxyDelegationPathLength()
	{
		Element webssoProperties = propertiesFile.getRootElement();
		Element dorianInformation = webssoProperties.getChild("dorian-information");
		Element proxyDelegationPathLength = dorianInformation.getChild("proxy-delegation-path-length");
		return proxyDelegationPathLength.getText().trim();
	}
	
	public String getDelegationServiceURL()
	{
		Element webssoProperties = propertiesFile.getRootElement();
		Element delegationServiceInformation = webssoProperties.getChild("delegation-service-information");
		Element serviceURL = delegationServiceInformation.getChild("service-url");
		return serviceURL.getText().trim();
	}
	
	public String getDelegationLifeTimeHours()
	{
		Element webssoProperties = propertiesFile.getRootElement();
		Element dorianInformation = webssoProperties.getChild("delegation-service-information");
		Element delegationLifetimeHours = dorianInformation.getChild("delegation-lifetime-hours");
		return delegationLifetimeHours.getText().trim();
	}
	
	public String getDelegationLifeTimeMinutes()
	{
		Element webssoProperties = propertiesFile.getRootElement();
		Element dorianInformation = webssoProperties.getChild("delegation-service-information");
		Element delegationLifetimeMinutes = dorianInformation.getChild("delegation-lifetime-minutes");
		return delegationLifetimeMinutes.getText().trim();
	}

	public String getDelegationLifeTimeSeconds()
	{
		Element webssoProperties = propertiesFile.getRootElement();
		Element dorianInformation = webssoProperties.getChild("delegation-service-information");
		Element delegationLifetimeSeconds = dorianInformation.getChild("delegation-lifetime-seconds");
		return delegationLifetimeSeconds.getText().trim();
	}

	public String getWebSSOServerHostIdentity()
	{
		Element webssoProperties = propertiesFile.getRootElement();
		Element webSSOServerHostIdentity = webssoProperties.getChild("websso-server-host-identity");
		return webSSOServerHostIdentity.getText().trim();
	}	
	
}

