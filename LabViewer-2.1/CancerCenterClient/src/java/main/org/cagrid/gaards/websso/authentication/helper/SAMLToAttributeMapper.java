package org.cagrid.gaards.websso.authentication.helper;

import gov.nih.nci.cagrid.opensaml.SAMLAssertion;

import java.util.HashMap;

import org.cagrid.gaards.websso.exception.AuthenticationConfigurationException;

public interface SAMLToAttributeMapper
{
	
	public HashMap<String, String> convertSAMLtoHashMap(SAMLAssertion samlAssertion) throws AuthenticationConfigurationException;
	
}
