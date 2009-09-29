package gov.nih.nci.system.security.authentication.cagrid.executor;

import javax.security.auth.login.CredentialNotFoundException;

public interface GridAuthenticationRemoteService
{
	public void authenticate(String bindKey, String username, String password, String authenticationServiceURL, String dorianServiceURL) throws CredentialNotFoundException,Exception;

	public void authenticate(String bindKey, String username, String password) throws CredentialNotFoundException,Exception;
	
}