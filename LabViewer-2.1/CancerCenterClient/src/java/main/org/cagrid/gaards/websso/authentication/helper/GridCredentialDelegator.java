package org.cagrid.gaards.websso.authentication.helper;

import gov.nih.nci.cagrid.dorian.ifs.bean.ProxyLifetime;

import java.util.List;

import org.cagrid.gaards.websso.exception.AuthenticationConfigurationException;
import org.globus.gsi.GlobusCredential;

public interface GridCredentialDelegator
{
	
	public String delegateGridCredential(GlobusCredential globusCredential, ProxyLifetime credentialslifetime, List<String> hostIdentityList) throws AuthenticationConfigurationException;
	
}
