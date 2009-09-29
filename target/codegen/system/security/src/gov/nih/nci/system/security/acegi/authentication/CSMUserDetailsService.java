package gov.nih.nci.system.security.acegi.authentication;

import gov.nih.nci.security.AuthenticationManager;
import gov.nih.nci.security.AuthorizationManager;
import gov.nih.nci.security.SecurityServiceProvider;
import gov.nih.nci.security.authorization.domainobjects.Privilege;
import gov.nih.nci.security.authorization.domainobjects.ProtectionElementPrivilegeContext;
import gov.nih.nci.security.exceptions.CSConfigurationException;
import gov.nih.nci.security.exceptions.CSException;
import gov.nih.nci.security.exceptions.CSObjectNotFoundException;
import gov.nih.nci.system.security.SecurityConstants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.GrantedAuthorityImpl;
import org.acegisecurity.userdetails.User;
import org.acegisecurity.userdetails.UserDetails;
import org.acegisecurity.userdetails.UserDetailsService;
import org.acegisecurity.userdetails.UsernameNotFoundException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DataRetrievalFailureException;

/**
 * CSM Implementation of UserDetailsService uses 
 * 
 */
public class CSMUserDetailsService implements UserDetailsService {
	
	private String csmApplicationContext;
	private Boolean cacheProtectionElementsFlag;
	
	
	private AuthorizationManager authorizationManager;
	private AuthenticationManager authenticationManager;
	
	public AuthorizationManager authorizationManagerInstance(){
		if(this.authorizationManager!=null){
			return this.authorizationManager;
		}else{
			try {
				this.authorizationManager = SecurityServiceProvider
						.getAuthorizationManager(this.csmApplicationContext);
			} catch (CSConfigurationException e) {
				throw new DataRetrievalFailureException(e.getMessage());
			} catch (CSException e) {
				throw new DataAccessResourceFailureException(e.getMessage());
			}
			return this.authorizationManager;
		}
	}
	
	public AuthenticationManager authenticationManagerInstance(){
		if(this.authenticationManager!=null){
			return this.authenticationManager;
		}else{
			try {
				this.authenticationManager = SecurityServiceProvider
						.getAuthenticationManager(this.csmApplicationContext);
			} catch (CSConfigurationException e) {
				throw new DataRetrievalFailureException(e.getMessage());
			} catch (CSException e) {
				throw new DataAccessResourceFailureException(e.getMessage());
			}
			return this.authenticationManager;
		}
	}

	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException {
		gov.nih.nci.security.authorization.domainobjects.User csmUser = loadCSMUserDetails(username);
		
		// Get All GrantedAuthorities from CSM.		
		GrantedAuthority[] grantedAuthorities;
		if (cacheProtectionElementsFlag) {
			grantedAuthorities = getGrantedAuthorityCollection(csmUser.getUserId().toString());
		} else {
			GrantedAuthority dummyGrantedAuthority = new GrantedAuthorityImpl(SecurityConstants.DUMMY_ROLE);
			grantedAuthorities = new GrantedAuthority[1];
			grantedAuthorities[0] = dummyGrantedAuthority;			
		}		
		UserDetails userDetails = new User(csmUser.getLoginName(), csmUser.getPassword()==null?"":csmUser.getPassword(),true, true, true, true, grantedAuthorities);
		return userDetails;
	}

	public gov.nih.nci.security.authorization.domainobjects.User loadCSMUserDetails(String username) {
		//	Make sure AuthenticationManager Instance is available.
		authenticationManagerInstance();
		gov.nih.nci.security.authorization.domainobjects.User csmUser = authorizationManagerInstance().getUser(username);
		if (csmUser == null)
			throw new UsernameNotFoundException("Unable to find user by the given user name");
		return csmUser;
	}

	@SuppressWarnings("unchecked")
	public GrantedAuthority[] getGrantedAuthorityCollection(String csmUserId) {
		Set pePrivContextSet = null;
		try {
			pePrivContextSet = authorizationManagerInstance().getProtectionElementPrivilegeContextForUser(csmUserId);
		} catch (CSObjectNotFoundException e) {
			throw new DataRetrievalFailureException("Could not retrieve Granted Authorities for user.");			
		}
		Collection<GrantedAuthority> authorityCollection = new ArrayList<GrantedAuthority>();
		Iterator iter = pePrivContextSet.iterator();
		while(iter.hasNext()){
			ProtectionElementPrivilegeContext pepc = (ProtectionElementPrivilegeContext)iter.next();
			String peName = pepc.getProtectionElement().getProtectionElementName();
			Set privSet = pepc.getPrivileges();
			Iterator ite = privSet.iterator();
			while(ite.hasNext()){
				Privilege priv = (Privilege)ite.next();
				authorityCollection.add(new GrantedAuthorityImpl(peName+"_"+priv.getName().toUpperCase()));
			}
		}
		GrantedAuthority[] grantedAuthorities = new GrantedAuthorityImpl[authorityCollection.size()];
		System.arraycopy(authorityCollection.toArray(), 0, grantedAuthorities, 0, authorityCollection.size());
		return grantedAuthorities;
	}
	
	public AuthorizationManager getAuthorizationManager() {
		return authorizationManager;
	}

	public void setAuthorizationManager(AuthorizationManager authorizationManager) {
		this.authorizationManager = authorizationManager;
	}

	public String getCsmApplicationContext() {
		return csmApplicationContext;
	}

	public void setCsmApplicationContext(String csmApplicationContext) {
		this.csmApplicationContext = csmApplicationContext;
	}
	
	public void setCacheProtectionElementsFlag(Boolean cacheProtectionElementsFlag) {
		this.cacheProtectionElementsFlag = cacheProtectionElementsFlag;
	}

	public Boolean getCacheProtectionElementsFlag() {
		return cacheProtectionElementsFlag;
	}
}