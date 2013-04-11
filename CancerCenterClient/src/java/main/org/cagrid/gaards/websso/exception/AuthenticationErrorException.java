/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */
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
