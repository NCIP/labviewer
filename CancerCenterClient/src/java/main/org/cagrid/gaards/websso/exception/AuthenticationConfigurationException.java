/*
* Copyright ScenPro, Inc and SemanticBits, LLC
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/labviewer/LICENSE.txt for details.
*/
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
