package org.cagrid.gaards.websso.beans;

import java.io.Serializable;

public class AuthenticationServiceInformation implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String authenticationServiceName = null;
	
	private String authenticationServiceURL = null;

	public String getAuthenticationServiceName()
	{
		return authenticationServiceName;
	}

	public void setAuthenticationServiceName(String authenticationServiceName)
	{
		this.authenticationServiceName = authenticationServiceName;
	}

	public String getAuthenticationServiceURL()
	{
		return authenticationServiceURL;
	}

	public void setAuthenticationServiceURL(String authenticationServiceURL)
	{
		this.authenticationServiceURL = authenticationServiceURL;
	}

}
