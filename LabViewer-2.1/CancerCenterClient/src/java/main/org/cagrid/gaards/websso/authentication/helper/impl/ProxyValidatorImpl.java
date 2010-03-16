/**
 *
 */
package org.cagrid.gaards.websso.authentication.helper.impl;

import java.security.cert.X509Certificate;

import org.cagrid.gaards.websso.authentication.helper.ProxyValidator;
import org.cagrid.gaards.websso.exception.AuthenticationConfigurationException;
import org.globus.gsi.CertificateRevocationLists;
import org.globus.gsi.GlobusCredential;
import org.globus.gsi.GlobusCredentialException;
import org.globus.gsi.TrustedCertificates;
import org.globus.gsi.proxy.ProxyPathValidator;
import org.globus.gsi.proxy.ProxyPathValidatorException;

/**
 * @author MODI
 *
 */
public class ProxyValidatorImpl implements ProxyValidator {

	private String trustStorePath = null;

	private String certificateRevocationListPath = null;

	public ProxyValidatorImpl()
	{
		super();
	}

	public ProxyValidatorImpl(String trustStorePath, String certificateRevocationListPath)
	{
		super();
		this.trustStorePath = trustStorePath.trim();
		this.certificateRevocationListPath = certificateRevocationListPath.trim();
	}

	/* (non-Javadoc)
	 * @see org.cagrid.gaards.websso.authentication.helper.CaGridProxyValidator#validate(org.globus.gsi.GlobusCredential)
	 */
	public boolean validate(GlobusCredential globusCredential) throws AuthenticationConfigurationException
	{

		if (null == globusCredential)
		{
			throw new AuthenticationConfigurationException("No proxy certificate found");
		}
    	try
		{
			globusCredential.verify();
		} catch (GlobusCredentialException e)
		{
			throw new AuthenticationConfigurationException("Error verifying the proxy certificate : " + e.getMessage(), e);
		}

        X509Certificate[] proxyChain = globusCredential.getCertificateChain();
        X509Certificate[] trustedCerts = null;
        CertificateRevocationLists crls = null;

        String trustStoreLocation = getTrustStorePath();
        if (trustStoreLocation != null && trustStoreLocation.length() != 0 )
        {
            trustedCerts = TrustedCertificates.loadCertificates(trustStoreLocation);
        }
        else
        {
            trustedCerts = TrustedCertificates.getDefaultTrustedCertificates().getCertificates();
        }

        String certificateRevocationListLocation = getCertificateRevocationListPath();
        if (certificateRevocationListLocation != null && certificateRevocationListLocation.length() != 0 )
        {
            crls = CertificateRevocationLists.getCertificateRevocationLists(certificateRevocationListLocation);
        }
        else
        {
            crls = CertificateRevocationLists.getDefaultCertificateRevocationLists();
        }

        ProxyPathValidator proxyPathValidator = new ProxyPathValidator();
        try
		{
			proxyPathValidator.validate(proxyChain, trustedCerts, crls);
		} catch (ProxyPathValidatorException e)
		{
			System.out.println(trustStoreLocation);
			e.printStackTrace();
			throw new AuthenticationConfigurationException("Error validating the Proxy Certificate : " + e.getMessage(), e);
		}

        return true;
	}

	public String getTrustStorePath()
	{
		return trustStorePath;
	}

	public void setTrustStorePath(String trustStorePath)
	{
		this.trustStorePath = trustStorePath;
	}

	public String getCertificateRevocationListPath()
	{
		return certificateRevocationListPath;
	}

	public void setCertificateRevocationListPath(String certificateRevocationListPath)
	{
		this.certificateRevocationListPath = certificateRevocationListPath;
	}

}
