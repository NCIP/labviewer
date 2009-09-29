package gov.nih.nci.system.security.acegi.authentication;

import java.security.cert.X509Certificate;

import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.userdetails.User;

public class X509User extends User
{

	X509Certificate certificate;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public X509User(String username, String password, boolean enabled,
			GrantedAuthority[] authorities) throws IllegalArgumentException {
		super(username, password, enabled, authorities);
	}

	public X509User(String username, String password, boolean enabled,
			boolean accountNonExpired, boolean credentialsNonExpired,
			GrantedAuthority[] authorities) throws IllegalArgumentException {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired,
				authorities);
	}

	public X509User(String username, String password, boolean enabled,
			boolean accountNonExpired, boolean credentialsNonExpired,
			boolean accountNonLocked, GrantedAuthority[] authorities)
			throws IllegalArgumentException {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired,
				accountNonLocked, authorities);
	}

	public X509Certificate getCertificate() {
		return certificate;
	}

	public void setCertificate(X509Certificate certificate) {
		this.certificate = certificate;
	}
}