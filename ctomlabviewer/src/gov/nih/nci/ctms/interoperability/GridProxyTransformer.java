package gov.nih.nci.ctms.interoperability;

import gov.nih.nci.cagrid.authentication.bean.BasicAuthenticationCredential;
import gov.nih.nci.cagrid.authentication.stubs.types.AuthenticationProviderFault;
import gov.nih.nci.cagrid.authentication.stubs.types.InsufficientAttributeFault;
import gov.nih.nci.cagrid.authentication.stubs.types.InvalidCredentialFault;

import org.globus.gsi.GlobusCredential;

public interface GridProxyTransformer {

	public abstract int getDelegationPathLength();

	public abstract String getIfsUrl();
	
	public abstract String getIdpUrl();
	
	public abstract void setIdpUrl(String idpUrl);
	

	public abstract int getProxyLifetimeHours();

	public abstract int getProxyLifetimeMinutes();

	public abstract int getProxyLifetimeSeconds();

	public abstract void setDelegationPathLength(int delegationPathLength);

	public abstract void setIfsUrl(String ifsUrl);

	public abstract void setProxyLifetimeHours(int proxyLifetimeHours);

	public abstract void setProxyLifetimeMinutes(int proxyLifetimeMinutes);

	public abstract void setProxyLifetimeSeconds(int proxyLifetimeSeconds);

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nih.nci.ctms.demo.GridProxyTransformer#transform(java.lang.String,
	 *      java.lang.String)
	 */
	public abstract GlobusCredential transform(BasicAuthenticationCredential bac, String idpUrl)
			throws InvalidCredentialFault, InsufficientAttributeFault,
			AuthenticationProviderFault;

	
	public abstract GlobusCredential transform(BasicAuthenticationCredential bac)
	throws InvalidCredentialFault, InsufficientAttributeFault,
	AuthenticationProviderFault;

}