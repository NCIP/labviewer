package org.cagrid.gaards.websso.authentication;

import gov.nih.nci.cagrid.opensaml.SAMLAssertion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.cagrid.gaards.websso.authentication.helper.AuthenticationServiceHelper;
import org.cagrid.gaards.websso.authentication.helper.DorianHelper;
import org.cagrid.gaards.websso.authentication.helper.GridCredentialDelegator;
import org.cagrid.gaards.websso.authentication.helper.ProxyValidator;
import org.cagrid.gaards.websso.authentication.helper.SAMLToAttributeMapper;
import org.cagrid.gaards.websso.beans.AuthenticationServiceInformation;
import org.cagrid.gaards.websso.beans.HostInformation;
import org.cagrid.gaards.websso.exception.AuthenticationConfigurationException;
import org.cagrid.gaards.websso.utils.ObjectFactory;
import org.cagrid.gaards.websso.utils.WebSSOConstants;
import org.cagrid.gaards.websso.utils.WebSSOProperties;
import org.globus.gsi.GlobusCredential;
import org.jasig.cas.authentication.Authentication;
import org.jasig.cas.authentication.AuthenticationManager;
import org.jasig.cas.authentication.MutableAuthentication;
import org.jasig.cas.authentication.handler.AuthenticationException;
import org.jasig.cas.authentication.principal.Credentials;
import org.jasig.cas.authentication.principal.Principal;
import org.jasig.cas.authentication.principal.SimplePrincipal;
import org.jasig.cas.authentication.principal.UsernamePasswordCredentials;

/**
 * CaGridAuthenticationManager
 * 
 * @author oster
 * @created Oct 2, 2007 12:40:18 PM
 * @version $Id: multiscaleEclipseCodeTemplates.xml,v 1.1 2007/03/02 14:35:01
 *          dervin Exp $
 */
public class CaGridAuthenticationManager implements AuthenticationManager
{
	private WebSSOProperties webSSOProperties = null;
	
	private List<String> hostIdentities = null;
	
	private String authenticationServiceURL = null;
	
	private String serializedDelegationEpr=null;
	
	private GlobusCredential credentials = null;
	
	public CaGridAuthenticationManager()
	{
		ObjectFactory.initialize(WebSSOConstants.WEBSSO_BEAN_CONFIG_FILE);
		try
		{
			this.webSSOProperties = (WebSSOProperties)ObjectFactory.getObject(WebSSOConstants.WEBSSO_PROPERTIES);
		}
		catch (AuthenticationConfigurationException e)
		{
			e.printStackTrace();
		}
	}

	public Authentication authenticate(Credentials credentials) throws AuthenticationException
	{
		if (null == webSSOProperties)
		{
			throw new AuthenticationConfigurationException ("Error Initializing Authentication Manager properties");
		}
		
		// Obtain the implementation for the AuthenticationServiceHelper Interface
		AuthenticationServiceHelper authenticationServiceHelper = (AuthenticationServiceHelper)ObjectFactory.getObject(WebSSOConstants.AUTHENTICATION_SERVICE_HELPER);
		
		// Authenticate the user credentials and retrieve 
		SAMLAssertion samlAssertion = authenticationServiceHelper.authenticate(getAuthenticationServiceURL(), ((UsernamePasswordCredentials) credentials).getUsername(), ((UsernamePasswordCredentials) credentials).getPassword());

		// Obtain the implementation for the DorianHelper Interface
		DorianHelper dorianHelper = (DorianHelper) ObjectFactory.getObject(WebSSOConstants.DORIAN_HELPER);
		
		// Obtained the GlobusCredential for the Authenticated User
		GlobusCredential globusCredential = dorianHelper.obtainProxy(samlAssertion);
		this.credentials = globusCredential;
		
		// Obtain the implementation for the GridCredentialsDelegator Interface
		GridCredentialDelegator gridCredentialDelegator = (GridCredentialDelegator)ObjectFactory.getObject(WebSSOConstants.GRID_CREDENTIAL_DELEGATOR);
		
		// Delegate the Globus Credentials
		String serializedDelegatedCredentialReference = gridCredentialDelegator.delegateGridCredential(globusCredential, dorianHelper.getProxyLifetime(), this.getHostIdentities());
        this.serializedDelegationEpr = serializedDelegatedCredentialReference;		 
		// Obtain the implementation for the SAMLToAttributeMapper Interface
		SAMLToAttributeMapper samlToAttributeMapper = (SAMLToAttributeMapper)ObjectFactory.getObject(WebSSOConstants.SAML_TO_ATTRIBUTE_MAPPER);  

		HashMap<String, String> attributesMap = samlToAttributeMapper.convertSAMLtoHashMap(samlAssertion);

		// Adding the serialed Delegated Credentails Reference and Grid Identity to the 
		attributesMap.put(WebSSOConstants.CAGRID_SSO_DELEGATION_SERVICE_EPR, serializedDelegatedCredentialReference);
		attributesMap.put(WebSSOConstants.CAGRID_SSO_GRID_IDENTITY, globusCredential.getIdentity());
		
		// Creating the Principal from the grid identity
		Principal p = new SimplePrincipal(this.constructPrincipal(attributesMap));

		// Create a new Authentication Object using the Principal
		MutableAuthentication mutableAuthentication = new MutableAuthentication(p);
		
		return mutableAuthentication;
			
	}
	
	private String getAuthenticationServiceURL() throws AuthenticationConfigurationException
	{
		if (null == authenticationServiceURL)
		{
			List<AuthenticationServiceInformation> authenticationServiceInformationList = webSSOProperties.getAuthenticationServices();
			if (authenticationServiceInformationList.size() == 1)
				authenticationServiceURL = authenticationServiceInformationList.get(0).getAuthenticationServiceURL();
			else
				throw new AuthenticationConfigurationException("More than one Authentication Service configured");
		}
		return authenticationServiceURL;
	}
	
	private List<String> getHostIdentities() throws AuthenticationConfigurationException
	{
		if (null == hostIdentities)
		{
			List<HostInformation> hostInformationList = webSSOProperties.getHostInformationList();
			
			if (hostInformationList.size() == 0)
				throw new AuthenticationConfigurationException("None Host Identities configured for Delegation ");

			hostIdentities = new ArrayList<String>();
			
			for (HostInformation hostInformation : hostInformationList)
				hostIdentities.add(hostInformation.getHostIdentity());
		}
		return hostIdentities;
	}
	
	private String constructPrincipal(HashMap<String, String> attributeMap)
	{
		String principalName = new String();
		Set<String> keySet = attributeMap.keySet();
		for(String key: keySet)
		{
			String value = attributeMap.get(key);
			principalName = principalName.concat(key + WebSSOConstants.KEY_VALUE_PAIR_DELIMITER + value + WebSSOConstants.ATTRIBUTE_DELIMITER);
		}
		principalName = principalName.substring(0, principalName.lastIndexOf(WebSSOConstants.ATTRIBUTE_DELIMITER));
		return principalName;
	}
	
	public static void main(String[] args) {
		CaGridAuthenticationManager authManager = new CaGridAuthenticationManager();
		UsernamePasswordCredentials credentials = new UsernamePasswordCredentials();
		credentials.setUsername("cctsdemo1@nci.nih.gov");
		credentials.setPassword("!Ccts1");
		try {
		  authManager.authenticate(credentials);
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	public String getSerializedDelegationEpr() {
		return serializedDelegationEpr;
	}

	public GlobusCredential getCredentials() {
		return credentials;
	}


	
}
