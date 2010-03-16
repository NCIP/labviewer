package org.cagrid.gaards.websso.authentication.helper.impl;

import gov.nih.nci.cagrid.authentication.bean.BasicAuthenticationCredential;
import gov.nih.nci.cagrid.authentication.bean.Credential;
import gov.nih.nci.cagrid.authentication.client.AuthenticationClient;
import gov.nih.nci.cagrid.authentication.stubs.types.AuthenticationProviderFault;
import gov.nih.nci.cagrid.authentication.stubs.types.InsufficientAttributeFault;
import gov.nih.nci.cagrid.authentication.stubs.types.InvalidCredentialFault;
import gov.nih.nci.cagrid.opensaml.SAMLAssertion;

import java.rmi.RemoteException;

import org.apache.axis.types.URI.MalformedURIException;
import org.cagrid.gaards.websso.authentication.helper.AuthenticationServiceHelper;
import org.cagrid.gaards.websso.exception.AuthenticationErrorException;
import org.cagrid.gaards.websso.exception.AuthenticationConfigurationException;

public class AuthenticationServiceHelperImpl implements AuthenticationServiceHelper
{

	public AuthenticationServiceHelperImpl()
	{
		super();
	}

	public SAMLAssertion authenticate(String authenticationServiceURL, String userName, String password) throws AuthenticationErrorException, AuthenticationConfigurationException
	{
		SAMLAssertion samlAssertion = null;
		BasicAuthenticationCredential basicAuthenticationCredential = new BasicAuthenticationCredential();
		basicAuthenticationCredential.setUserId(userName);
		basicAuthenticationCredential.setPassword(password);
		Credential credential = new Credential();
		credential.setBasicAuthenticationCredential(basicAuthenticationCredential);

		AuthenticationClient authenticationClient;
		try
		{
			authenticationClient = new AuthenticationClient(authenticationServiceURL, credential);
		} 
		catch (MalformedURIException e)
		{
			throw new AuthenticationConfigurationException("Invalid Authentication Service URL : " + e.getMessage());
		} 
		catch (RemoteException e)
		{
			throw new AuthenticationConfigurationException("Error accessing the Authentication Service : " + e.getMessage());
		}
		try
		{
			samlAssertion = authenticationClient.authenticate();
		} 
		catch (InvalidCredentialFault e)
		{
			throw new AuthenticationErrorException("Invalid Credentials : " + e.getMessage());
		} 
		catch (InsufficientAttributeFault e)
		{
			throw new AuthenticationConfigurationException("Insufficient Attribute configured for the Authentication Service : " + e.getMessage());
		} 
		catch (AuthenticationProviderFault e)
		{
			throw new AuthenticationConfigurationException("Error accessing the Authentication Service : " + e.getMessage());
		} 
		catch (RemoteException e)
		{
			throw new AuthenticationConfigurationException("Error accessing the Authentication Service : " + e.getMessage());
		}
		return samlAssertion;
	}

}
