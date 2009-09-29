package gov.nih.nci.system.security.authentication.cagrid;

import gov.nih.nci.system.security.authentication.cagrid.executor.GridAuthenticationService;

import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.TextInputCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.CredentialNotFoundException;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

import org.acegisecurity.providers.x509.X509AuthenticationToken;
import org.globus.gsi.GlobusCredential;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class GridJAASLoginModule implements  LoginModule 
{

	private static ApplicationContext ctx = new ClassPathXmlApplicationContext("grid-login-service-config.xml");
	
	private Subject 		subject;
	private CallbackHandler callbackHandler;
	private Map 			sharedState;
	private Map 			options;
	

	
	public void initialize(Subject subject, CallbackHandler callbackHandler, Map sharedState, Map options)
	{
		this.subject 			= subject;
		this.callbackHandler	= callbackHandler;
		this.sharedState 		= sharedState;
		this.options 			= options;
	}
	
	public boolean login() throws LoginException
	{
		if (callbackHandler == null)
			throw new LoginException("Error in obtaining Callback Handler");

		String 		userID;
		char[]		password;
		String authenticationServiceURL;
		String dorianServiceURL;
		
		Callback[] callbacks = new Callback[4];
		callbacks[0] = new NameCallback("userid: ");
		callbacks[1] = new PasswordCallback("password: ", false);
		callbacks[2] = new TextInputCallback("authenticationURL: ");
		callbacks[3] = new TextInputCallback("dorianServiceURL: ");
		
		try
		{
			callbackHandler.handle(callbacks);
			userID = ((NameCallback) callbacks[0]).getName();
			char[] tmpPassword = ((PasswordCallback) callbacks[1]).getPassword();
			authenticationServiceURL = ((TextInputCallback) callbacks[2]).getText();
			dorianServiceURL = ((TextInputCallback) callbacks[3]).getText();
			
			if (tmpPassword == null)
				tmpPassword = new char[0];
			password = new char[tmpPassword.length];
			System.arraycopy(tmpPassword, 0, password, 0, tmpPassword.length);
			((PasswordCallback) callbacks[1]).clearPassword();
		}
		catch (java.io.IOException e)
		{
			throw new LoginException("Error in Creating the CallBack Handler");
		}
		catch (UnsupportedCallbackException e)
		{
			throw new LoginException("Error in Creating the CallBack Handler");
		}
	
		return performAuthentication(userID, password, authenticationServiceURL,dorianServiceURL);
	}
	
	
	public boolean commit() throws LoginException
	{
		return true;
	}

	public boolean abort() throws LoginException
	{
		return true;
	}

	public boolean logout() throws LoginException
	{
		subject.getPrincipals().clear();
		return true;
	}
	

	protected boolean performAuthentication(String userID, char[] password, String authenticationServiceURL,String dorianServiceURL) throws LoginException
	{
		try 
		{
			GlobusCredential credential = null;
			GridAuthenticationService executor = getAuthenticationService();
			if(authenticationServiceURL == null || authenticationServiceURL.length() == 0 || dorianServiceURL==null || dorianServiceURL.length() == 0)
				credential = executor.authenticate(userID, new String(password));
			else
				credential = executor.authenticate(userID, new String(password), authenticationServiceURL,dorianServiceURL);
			if (credential!=null)
			{
				X509AuthenticationToken auth = new X509AuthenticationToken(credential.getIdentityCertificate());
				auth.setDetails(credential);
				subject.getPrincipals().add(auth);
				return true;
			}
		}
		catch(CredentialNotFoundException cnfe)
		{
			throw cnfe;
		}
		catch (Exception e) 
		{
			throw new LoginException(e.getMessage());
		} 

		throw new FailedLoginException("Invalid Login Credentials");
	}
	
	protected GridAuthenticationService getAuthenticationService() throws LoginException{
		return (GridAuthenticationService)ctx.getBean("AuthenticationService");
	}
}