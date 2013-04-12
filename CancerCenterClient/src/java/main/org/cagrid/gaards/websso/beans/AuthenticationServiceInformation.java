/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */
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
