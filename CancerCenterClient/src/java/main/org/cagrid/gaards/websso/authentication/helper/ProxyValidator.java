/*
* Copyright ScenPro, Inc and SemanticBits, LLC
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/labviewer/LICENSE.txt for details.
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
