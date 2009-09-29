package gov.nih.nci.system.security.acegi.authentication;

import org.acegisecurity.Authentication;
import org.acegisecurity.AuthenticationException;
import org.acegisecurity.providers.AuthenticationProvider;
import org.acegisecurity.providers.x509.X509AuthenticationToken;

public class NullX509AuthenticationProvider implements AuthenticationProvider
{

	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		return authentication;
	}

	public boolean supports(Class authentication) {
		
		return X509AuthenticationToken.class.isAssignableFrom(authentication);
	}

}