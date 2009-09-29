package gov.nih.nci.system.security.authentication.cagrid.executor;

import javax.security.auth.login.CredentialNotFoundException;

import org.globus.gsi.GlobusCredential;

public interface GridAuthenticationService
{
	public GlobusCredential authenticate(String username, String password, String authenticationServiceURL, String dorianServiceURL) throws CredentialNotFoundException,Exception;

	public GlobusCredential authenticate(String username, String password) throws CredentialNotFoundException,Exception;

}