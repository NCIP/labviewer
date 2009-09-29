package gov.nih.nci.system.security.authentication.cagrid.executor;

import java.util.Map;
import java.util.Random;

import javax.security.auth.login.CredentialNotFoundException;

import org.globus.gsi.GlobusCredential;

public class GridAuthenticationLocalServiceImpl implements GridAuthenticationService
{
	private GridAuthenticationRemoteService service;
	private Map securityMap;
	private Random generator = new Random(); 
	
	public GridAuthenticationLocalServiceImpl(GridAuthenticationRemoteService service,Map securityMap)
	{
		this.service = service;
		this.securityMap = securityMap;
	}
	
	public GlobusCredential authenticate(String username, String password, String authenticationServiceURL, String dorianServiceURL) throws CredentialNotFoundException,Exception
	{
		String bindKey = generateBindKey();
		service.authenticate(bindKey, username, password,authenticationServiceURL,dorianServiceURL);
		GlobusCredential credential = retrieveCredential(bindKey);
		return credential;
	}

	public GlobusCredential authenticate(String username, String password) throws CredentialNotFoundException,Exception
	{
		String bindKey = generateBindKey();
		service.authenticate(bindKey, username, password);
		GlobusCredential credential = retrieveCredential(bindKey);
		return credential;
	}

	private GlobusCredential retrieveCredential(String bindKey)
	{
		GlobusCredential credential = (GlobusCredential)securityMap.get(bindKey);
		securityMap.remove(bindKey);
		return credential;
	}

	private String generateBindKey()
	{
		while(true)
		{
			String randomKey = generator.nextLong()+"";
			if(securityMap.get(randomKey)==null)
				return randomKey;
		}
	}
}