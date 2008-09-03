/**
 * 
 */
package org.cagrid.gaards.websso.authentication.helper;

import org.cagrid.gaards.websso.exception.AuthenticationConfigurationException;
import org.globus.gsi.GlobusCredential;

/**
 * @author modik [Kunal Modi - Ekagra Software Technologies Ltd.]
 *
 */
public interface ProxyValidator {
	
	public boolean validate(GlobusCredential proxy) throws AuthenticationConfigurationException;

}
