package gov.nih.nci.system.security.acegi.authentication;

import gov.nih.nci.system.security.SecurityConstants;
import gov.nih.nci.system.security.authentication.cagrid.GridAuthenticationHelper;

import java.security.cert.X509Certificate;

import javax.security.auth.login.CredentialNotFoundException;
import javax.security.auth.login.LoginException;

import org.acegisecurity.AcegiMessageSource;
import org.acegisecurity.Authentication;
import org.acegisecurity.AuthenticationException;
import org.acegisecurity.AuthenticationServiceException;
import org.acegisecurity.BadCredentialsException;
import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.GrantedAuthorityImpl;
import org.acegisecurity.providers.AuthenticationProvider;
import org.acegisecurity.providers.UsernamePasswordAuthenticationToken;
import org.acegisecurity.providers.dao.UserCache;
import org.acegisecurity.providers.dao.cache.NullUserCache;
import org.acegisecurity.providers.x509.X509AuthenticationProvider;
import org.acegisecurity.providers.x509.X509AuthenticationToken;
import org.acegisecurity.userdetails.UserDetails;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.globus.gsi.GlobusCredential;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.util.Assert;

public class GridAuthenticationProvider implements AuthenticationProvider, InitializingBean, MessageSourceAware
{
	private static final Log logger	= LogFactory.getLog(GridAuthenticationProvider.class);

	//~ Instance fields ================================================================================================

	protected MessageSourceAccessor	messages	= AcegiMessageSource.getAccessor();
	private UserCache userCache	= new NullUserCache();
	private X509AuthenticationProvider	x509AuthenticationProvider;
	private String loginModule;
	private boolean	loginModuleDisabled;

	public Authentication authenticate(Authentication authentication) throws AuthenticationException
	{

		Assert.isInstanceOf(UsernamePasswordAuthenticationToken.class, authentication, messages.getMessage("GridAuthenticationProvider.onlySupports", "Only UsernamePasswordAuthenticationToken is supported"));

		// Determine username
		String username = (authentication.getPrincipal() == null) ? "NONE_PROVIDED" : authentication.getName();

		boolean cacheWasUsed = true;
		UserDetails user = this.userCache.getUserFromCache(username);

		if (user == null)
		{
			cacheWasUsed = false;
		}
		try
		{
			X509Certificate x509Certificate = login(username, ((UsernamePasswordAuthenticationToken) authentication).getCredentials().toString());

			GrantedAuthority dummyGrantedAuthority = new GrantedAuthorityImpl(SecurityConstants.DUMMY_ROLE);
			GrantedAuthority[] grantedAuthorities = new GrantedAuthority[1];
			grantedAuthorities[0] = dummyGrantedAuthority;

			user = new X509User(username, ((UsernamePasswordAuthenticationToken) authentication).getCredentials().toString(), true, true, true, true, grantedAuthorities);
			((X509User) user).setCertificate(x509Certificate);
		} 
		catch (CredentialNotFoundException cnfe)
		{
			throw new BadCredentialsException(messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
		} 
		catch (Exception e)
		{

			if (loginModuleDisabled)
				throw new BadCredentialsException(messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
			throw new AuthenticationServiceException("Login error :" + e.getMessage());
		}

		Assert.notNull(user, "retrieveUser returned null - a violation of the interface contract");
		Assert.notNull(((X509User) user).getCertificate(), "retrieveUser certificate returned null - a violation of the interface contract");

		if (!cacheWasUsed)
		{
			this.userCache.putUserInCache(user);
		}

		//Invoke into X509AuthenticationProvider and return the user from there 
		return x509AuthenticationProvider.authenticate(new X509AuthenticationToken(((X509User) user).getCertificate()));
	}

	public boolean supports(Class authentication)
	{
		if (loginModuleDisabled)
			return false;
		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}

	protected X509Certificate login(String username, String password) throws LoginException
	{
		try
		{
			GridAuthenticationHelper helper = new GridAuthenticationHelper(loginModule);
			GlobusCredential credential = helper.login(username, password);
			return credential.getIdentityCertificate();
		} 
		catch (Exception e)
		{
			throw new BadCredentialsException(messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
		}
	}
	
	
	public void afterPropertiesSet() throws Exception
	{
	}

	public void setMessageSource(MessageSource messageSource)
	{
		this.messages = new MessageSourceAccessor(messageSource);
	}

	public MessageSourceAccessor getMessages()
	{
		return messages;
	}

	public void setMessages(MessageSourceAccessor messages)
	{
		this.messages = messages;
	}

	public UserCache getUserCache()
	{
		return userCache;
	}

	public void setUserCache(UserCache userCache)
	{
		this.userCache = userCache;
	}

	public X509AuthenticationProvider getX509AuthenticationProvider()
	{
		return x509AuthenticationProvider;
	}

	public void setX509AuthenticationProvider(X509AuthenticationProvider authenticationProvider)
	{
		x509AuthenticationProvider = authenticationProvider;
	}

	public String getLoginModule()
	{
		return loginModule;
	}

	public void setLoginModule(String loginModule)
	{
		this.loginModule = loginModule;
	}
}