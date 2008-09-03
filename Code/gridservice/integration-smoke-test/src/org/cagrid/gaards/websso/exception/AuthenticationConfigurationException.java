package org.cagrid.gaards.websso.exception;

import org.jasig.cas.authentication.handler.AuthenticationException;

public class AuthenticationConfigurationException extends AuthenticationException
{
	/**
	 * Default serial id
	 */
	private static final long serialVersionUID = 1L;

	public AuthenticationConfigurationException(String message)
	{
		super(message);
	}
	
	public AuthenticationConfigurationException(String message, Exception exception)
	{
		super (message, exception);
	}
}
