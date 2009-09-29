package gov.nih.nci.system.security.authentication.cagrid.executor;

import java.util.Map;

import javax.security.auth.login.CredentialNotFoundException;

import org.globus.gsi.GlobusCredential;

public class GridAuthenticationRemoteServiceImpl implements GridAuthenticationRemoteService
{
	GridAuthenticationService service;
	Map securityMap;
	
	public GridAuthenticationRemoteServiceImpl(GridAuthenticationService service, Map securityMap)
	{
		this.service = service;
		this.securityMap = securityMap;
	}

	public void authenticate(String bindKey, String username, String password, String authenticationServiceURL, String dorianServiceURL) throws CredentialNotFoundException, Exception
	{
		GlobusCredential credential = service.authenticate(username, password, authenticationServiceURL,dorianServiceURL);
		bindCredential(bindKey,credential);
	}

	public void authenticate(String bindKey, String username, String password) throws CredentialNotFoundException, Exception
	{
		GlobusCredential credential = service.authenticate(username, password);
		bindCredential(bindKey,credential);		
	}

	private void bindCredential(String bindKey, GlobusCredential credential)
	{
		securityMap.put(bindKey, credential);
	}
	
}