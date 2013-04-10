/*
* Copyright ScenPro, Inc and SemanticBits, LLC
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/labviewer/LICENSE.txt for details.
*/
package org.cagrid.gaards.websso.authentication.helper;

import gov.nih.nci.cagrid.dorian.ifs.bean.ProxyLifetime;

import java.util.List;

import org.cagrid.gaards.websso.exception.AuthenticationConfigurationException;
import org.globus.gsi.GlobusCredential;

public interface GridCredentialDelegator
{
	
	public String delegateGridCredential(GlobusCredential globusCredential, ProxyLifetime credentialslifetime, List<String> hostIdentityList) throws AuthenticationConfigurationException;
	
}
