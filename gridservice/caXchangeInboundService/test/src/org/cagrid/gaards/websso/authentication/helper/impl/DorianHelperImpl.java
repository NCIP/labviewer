package org.cagrid.gaards.websso.authentication.helper.impl;

import java.rmi.RemoteException;

import gov.nih.nci.cagrid.dorian.client.IFSUserClient;
import gov.nih.nci.cagrid.dorian.common.DorianFault;
import gov.nih.nci.cagrid.dorian.ifs.bean.ProxyLifetime;
import gov.nih.nci.cagrid.dorian.stubs.types.DorianInternalFault;
import gov.nih.nci.cagrid.dorian.stubs.types.InvalidAssertionFault;
import gov.nih.nci.cagrid.dorian.stubs.types.InvalidProxyFault;
import gov.nih.nci.cagrid.dorian.stubs.types.PermissionDeniedFault;
import gov.nih.nci.cagrid.dorian.stubs.types.UserPolicyFault;
import gov.nih.nci.cagrid.opensaml.SAMLAssertion;

import org.apache.axis.types.URI.MalformedURIException;
import org.cagrid.gaards.websso.authentication.helper.DorianHelper;
import org.cagrid.gaards.websso.exception.AuthenticationErrorException;
import org.cagrid.gaards.websso.exception.AuthenticationConfigurationException;
import org.globus.gsi.GlobusCredential;

public class DorianHelperImpl implements DorianHelper
{

	private String serviceURL = null;
	
	private int proxyLifetimeHours = 12;

	private int proxyLifetimeMinutes = 0;

	private int proxyLifetimeSeconds = 0;
	
	private int delegationPathLength = 0;
	
	private ProxyLifetime proxyLifetime = null; 
		
	public DorianHelperImpl(String serviceURL, int proxyLifetimeHours, int proxyLifetimeMinutes, int proxyLifetimeSeconds, int delegationPathLength)
	{
		super();
		this.serviceURL = serviceURL;
		this.proxyLifetimeHours = proxyLifetimeHours;
		this.proxyLifetimeMinutes = proxyLifetimeMinutes;
		this.proxyLifetimeSeconds = proxyLifetimeSeconds;
		this.delegationPathLength = delegationPathLength;

		// Setting the lifetime object
		proxyLifetime = new ProxyLifetime();
		proxyLifetime.setHours(getProxyLifetimeHours());
		proxyLifetime.setMinutes(getProxyLifetimeMinutes());
		proxyLifetime.setSeconds(getProxyLifetimeSeconds());

	}

	
	public GlobusCredential obtainProxy(SAMLAssertion samlAssertion) throws AuthenticationConfigurationException, AuthenticationErrorException
	{
		GlobusCredential globusCredential = null;
		
		IFSUserClient ifsUserClient = null;
		try
		{
			ifsUserClient = new IFSUserClient(getServiceURL());
		} catch (MalformedURIException e)
		{
			throw new AuthenticationConfigurationException("Invalid Dorian Service URL : " + e.getMessage());
		} 
		catch (RemoteException e)
		{
			throw new AuthenticationConfigurationException("Error accessing the Dorian Service : " + e.getMessage());
		}
		try
		{
			globusCredential = ifsUserClient.createProxy(samlAssertion, proxyLifetime, getDelegationPathLength());
		} catch (DorianFault e)
		{
			throw new AuthenticationConfigurationException("Error accessing the Dorian Service : " + e.getMessage());
		} 
		catch (DorianInternalFault e)
		{
			throw new AuthenticationConfigurationException("Error accessing the Dorian Service : " + e.getMessage());
		} 
		catch (InvalidAssertionFault e)
		{
			throw new AuthenticationConfigurationException("Invalid SAML Assertion : " + e.getMessage());
		} 
		catch (InvalidProxyFault e)
		{
			throw new AuthenticationConfigurationException("Error accessing the Dorian Service : " + e.getMessage());
		} 
		catch (UserPolicyFault e)
		{
			throw new AuthenticationConfigurationException("Error accessing the Dorian Service : " + e.getMessage());
		} 
		catch (PermissionDeniedFault e)
		{
			throw new AuthenticationErrorException("Permission denied while obtaining Grid Credentials : " + e.getMessage());
		}

		return globusCredential;

	}
	

	public ProxyLifetime getProxyLifetime()
	{
		return proxyLifetime;
	}


	public void setProxyLifetime(ProxyLifetime proxyLifetime)
	{
		this.proxyLifetime = proxyLifetime;
	}


	public String getServiceURL()
	{
		return serviceURL;
	}


	public void setServiceURL(String serviceURL)
	{
		this.serviceURL = serviceURL;
	}


	public int getProxyLifetimeHours()
	{
		return proxyLifetimeHours;
	}

	public void setProxyLifetimeHours(int proxyLifetimeHours)
	{
		this.proxyLifetimeHours = proxyLifetimeHours;
	}

	public int getProxyLifetimeMinutes()
	{
		return proxyLifetimeMinutes;
	}

	public void setProxyLifetimeMinutes(int proxyLifetimeMinutes)
	{
		this.proxyLifetimeMinutes = proxyLifetimeMinutes;
	}

	public int getProxyLifetimeSeconds()
	{
		return proxyLifetimeSeconds;
	}

	public void setProxyLifetimeSeconds(int proxyLifetimeSeconds)
	{
		this.proxyLifetimeSeconds = proxyLifetimeSeconds;
	}

	public int getDelegationPathLength()
	{
		return delegationPathLength;
	}

	public void setDelegationPathLength(int delegationPathLength)
	{
		this.delegationPathLength = delegationPathLength;
	}

}
