package gov.nih.nci.system.security.acegi.rcp.providers;


import gov.nih.nci.system.security.SecurityConstants;

import org.acegisecurity.AuthenticationException;
import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.GrantedAuthorityImpl;
import org.acegisecurity.providers.UsernamePasswordAuthenticationToken;
import org.acegisecurity.providers.rcp.RemoteAuthenticationException;
import org.acegisecurity.providers.rcp.RemoteAuthenticationManagerImpl;

public class SDKRemoteAuthenticationManagerImpl extends RemoteAuthenticationManagerImpl {
	
	/**
	 * overridden method to return dummy grantedAuthorities to remote
	 * client
	 */
    public GrantedAuthority[] attemptAuthentication(String username, String password)
        throws RemoteAuthenticationException {
        UsernamePasswordAuthenticationToken request = new UsernamePasswordAuthenticationToken(username, password);

        try {
            getAuthenticationManager().authenticate(request);
			return getDummyGrantedAuthorities();
        } catch (AuthenticationException authEx) {
            throw new RemoteAuthenticationException(authEx.getMessage());
        }
    }
    
	private GrantedAuthority[] getDummyGrantedAuthorities() {
		GrantedAuthority[] grantedAuthorities = new GrantedAuthority[1];
		GrantedAuthority dummyGrantedAuthority = new GrantedAuthorityImpl(SecurityConstants.DUMMY_ROLE);
		grantedAuthorities[0] = dummyGrantedAuthority;
		return grantedAuthorities;
	}
}