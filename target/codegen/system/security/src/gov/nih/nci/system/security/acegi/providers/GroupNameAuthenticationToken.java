package gov.nih.nci.system.security.acegi.providers;

import java.util.Collection;

import org.acegisecurity.Authentication;
import org.acegisecurity.GrantedAuthority;

public class GroupNameAuthenticationToken implements Authentication {

   private static final long serialVersionUID = 1L;
   Collection<String> groups;
   boolean authenticated;

   public GroupNameAuthenticationToken(Collection<String> groups) {
	   this.groups=groups;
	   authenticated=true;
   }

   public void setAuthenticated(boolean isAuthenticated)
       throws IllegalArgumentException {
       if (isAuthenticated) {
           throw new IllegalArgumentException(
               "Cannot set this token to trusted - use constructor containing GrantedAuthority[]s instead");
       }
      this.authenticated=isAuthenticated;
   }

	public GrantedAuthority[] getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getCredentials() {
		// TODO Auto-generated method stub
		return "dummy";
	}

	public Object getDetails() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getPrincipal() {
		// TODO Auto-generated method stub
		return "dummy";
	}

	public boolean isAuthenticated() {
		// TODO Auto-generated method stub
		return authenticated;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return "dummy";
	}
	
	public void setGroups(Collection<String> groups) {
		this.groups = groups;
	}

	public Collection<String> getGroups() {
		return groups;
	}
}

