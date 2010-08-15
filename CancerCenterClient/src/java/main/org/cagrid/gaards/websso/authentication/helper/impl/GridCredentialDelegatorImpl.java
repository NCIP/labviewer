package org.cagrid.gaards.websso.authentication.helper.impl;

import gov.nih.nci.cagrid.common.Utils;

import java.io.StringWriter;
import java.rmi.RemoteException;
import java.util.List;

import javax.xml.namespace.QName;

import org.apache.axis.message.addressing.EndpointReferenceType;
import org.apache.axis.types.URI.MalformedURIException;
import org.cagrid.gaards.cds.client.DelegationUserClient;
import org.cagrid.gaards.cds.common.AllowedParties;
import org.cagrid.gaards.cds.common.IdentityDelegationPolicy;
import org.cagrid.gaards.cds.common.ProxyLifetime;
import org.cagrid.gaards.cds.delegated.stubs.types.DelegatedCredentialReference;
import org.cagrid.gaards.cds.stubs.types.CDSInternalFault;
import org.cagrid.gaards.cds.stubs.types.DelegationFault;
import org.cagrid.gaards.cds.stubs.types.PermissionDeniedFault;
import org.cagrid.gaards.websso.authentication.helper.GridCredentialDelegator;
import org.cagrid.gaards.websso.exception.AuthenticationConfigurationException;
import org.globus.gsi.GlobusCredential;
import org.globus.wsrf.encoding.ObjectSerializer;
import org.globus.wsrf.encoding.SerializationException;

public class GridCredentialDelegatorImpl implements GridCredentialDelegator
{

	String serviceURL = null;
	
	
	private int delegationLifetimeHours = 12;

	private int delegationLifetimeMinutes = 0;

	private int delegationLifetimeSeconds = 0;
	
	
	public GridCredentialDelegatorImpl(String serviceURL,  int delegationLifetimeHours, int delegationLifetimeMinutes, int delegationLifetimeSeconds)
	{
		super();
		this.serviceURL = serviceURL;
		this.delegationLifetimeHours = delegationLifetimeHours;
		this.delegationLifetimeMinutes = delegationLifetimeMinutes;
		this.delegationLifetimeSeconds = delegationLifetimeSeconds;
	}

	public String delegateGridCredential(GlobusCredential globusCredential, gov.nih.nci.cagrid.dorian.ifs.bean.ProxyLifetime credentialslifetime, List<String> hostIdentityList ) throws AuthenticationConfigurationException
	{


		ProxyLifetime credentialsCDSLifeTime = convertToCDSLifeTime(credentialslifetime);
		
		String[] hostIdentities = new String[hostIdentityList.size()];
		hostIdentityList.toArray(hostIdentities);
		
		AllowedParties allowedParties = new AllowedParties();
		allowedParties.setGridIdentity(hostIdentities);
		
		IdentityDelegationPolicy identityDelegationPolicy = new IdentityDelegationPolicy();
		identityDelegationPolicy.setAllowedParties(allowedParties);
		identityDelegationPolicy.setAllowedParties(allowedParties);
		ProxyLifetime delegationProxyLifetime = new ProxyLifetime();
		
		DelegationUserClient client = null;
		try
		{
			client = new DelegationUserClient(this.serviceURL, globusCredential);
		} 
		catch (Exception e)
		{
			throw new AuthenticationConfigurationException("Error accessing the Delegation Service : " + e.getMessage());
		}

		DelegatedCredentialReference delegatedCredentialReference = null;
		try
		{
			delegatedCredentialReference = client.delegateCredential( delegationProxyLifetime, identityDelegationPolicy, credentialsCDSLifeTime);
		}
		catch (CDSInternalFault e)
		{
			throw new AuthenticationConfigurationException("Error accessing the Delegation Service : " + e.getMessage());
		}
		catch (DelegationFault e)
		{
			throw new AuthenticationConfigurationException("Error accessing the Delegation Service : " + e.getMessage());
		}
		catch (PermissionDeniedFault e)
		{
			throw new AuthenticationConfigurationException("Error accessing the Delegation Service : " + e.getMessage());
		}
		catch (RemoteException e)
		{
			throw new AuthenticationConfigurationException("Error accessing the Delegation Service : " + e.getMessage());
		}
		catch (MalformedURIException e)
		{
			throw new AuthenticationConfigurationException("Error accessing the Delegation Service : " + e.getMessage());
		}
		
        String serializedDelegatedCredentialReference = null;
        try {
            StringWriter stringWriter = new StringWriter();
            Utils
                    .serializeObject(
                            delegatedCredentialReference,
                            new QName(
                                    "http://cds.gaards.cagrid.org/CredentialDelegationService/DelegatedCredential/types",
                                    "DelegatedCredentialReference"),
                            stringWriter, DelegationUserClient.class.getResourceAsStream("client-config.wsdd"));
            serializedDelegatedCredentialReference = stringWriter.toString();
        } catch (Exception e) {
            throw new AuthenticationConfigurationException(
                    "Unable to serialize the message Delegated Credentials : "
                            + e.getMessage(), e);
        }
        return serializedDelegatedCredentialReference;
	}

	private ProxyLifetime convertToCDSLifeTime(gov.nih.nci.cagrid.dorian.ifs.bean.ProxyLifetime credentialslifetime)
	{
		return new ProxyLifetime(credentialslifetime.getHours(), credentialslifetime.getMinutes(), credentialslifetime.getSeconds());
	}

	public String getServiceURL()
	{
		return serviceURL;
	}

	public void setServiceURL(String serviceURL)
	{
		this.serviceURL = serviceURL;
	}


	public int getDelegationLifetimeHours()
	{
		return delegationLifetimeHours;
	}

	public void setDelegationLifetimeHours(int delegationLifetimeHours)
	{
		this.delegationLifetimeHours = delegationLifetimeHours;
	}

	public int getDelegationLifetimeMinutes()
	{
		return delegationLifetimeMinutes;
	}

	public void setDelegationLifetimeMinutes(int delegationLifetimeMinutes)
	{
		this.delegationLifetimeMinutes = delegationLifetimeMinutes;
	}

	public int getDelegationLifetimeSeconds()
	{
		return delegationLifetimeSeconds;
	}

	public void setDelegationLifetimeSeconds(int delegationLifetimeSeconds)
	{
		this.delegationLifetimeSeconds = delegationLifetimeSeconds;
	}

}
