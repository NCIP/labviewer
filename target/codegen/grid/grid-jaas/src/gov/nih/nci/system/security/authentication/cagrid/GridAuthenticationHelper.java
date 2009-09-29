package gov.nih.nci.system.security.authentication.cagrid;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;

import org.acegisecurity.providers.x509.X509AuthenticationToken;
import org.globus.gsi.GlobusCredential;

public class GridAuthenticationHelper
{
	private String loginModule = "sdk"; 
	
	public GridAuthenticationHelper(){}
	
	public GridAuthenticationHelper(String loginModule){
		this.loginModule = loginModule;
	}
	
	public GlobusCredential login(String username, String password) throws Exception
	{
		Subject subject = new Subject();
		LoginContext loginContext = new LoginContext(loginModule, subject, new GridJAASCallbackHandler(username, password));
		loginContext.login();
		
		for(Object o: subject.getPrincipals())
		{
			if(o instanceof X509AuthenticationToken)
			{
				X509AuthenticationToken token = (X509AuthenticationToken)o;
				return (GlobusCredential) token.getDetails();
			}
		}
		return null;
	}

	public GlobusCredential login(String username, String password,String authenticationServiceURL, String dorianServiceURL) throws Exception
	{
		Subject subject = new Subject();
		LoginContext loginContext = new LoginContext(loginModule, subject, new GridJAASCallbackHandler(username, password,authenticationServiceURL, dorianServiceURL));
		loginContext.login();
		
		for(Object o: subject.getPrincipals())
		{
			if(o instanceof X509AuthenticationToken)
			{
				X509AuthenticationToken token = (X509AuthenticationToken)o;
				return (GlobusCredential) token.getDetails();
			}
		}
		return null;
	}
}