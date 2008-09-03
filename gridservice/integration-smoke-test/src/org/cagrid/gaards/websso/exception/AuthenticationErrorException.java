package org.cagrid.gaards.websso.exception;

import org.jasig.cas.authentication.handler.BadCredentialsAuthenticationException;

public class AuthenticationErrorException extends BadCredentialsAuthenticationException
{

	private static final long serialVersionUID = 1L;
	
	public AuthenticationErrorException(String message)
	{
		super(message);
	}

}
