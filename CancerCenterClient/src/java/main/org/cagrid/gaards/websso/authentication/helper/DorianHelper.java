/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */
package org.cagrid.gaards.websso.authentication.helper;

import org.cagrid.gaards.websso.exception.AuthenticationErrorException;
import org.cagrid.gaards.websso.exception.AuthenticationConfigurationException;
import org.globus.gsi.GlobusCredential;

import gov.nih.nci.cagrid.dorian.ifs.bean.ProxyLifetime;
import gov.nih.nci.cagrid.opensaml.SAMLAssertion;

public interface DorianHelper
{
	
	public GlobusCredential obtainProxy(SAMLAssertion samlAssertion) throws AuthenticationConfigurationException, AuthenticationErrorException;
	
	public ProxyLifetime getProxyLifetime();

}
