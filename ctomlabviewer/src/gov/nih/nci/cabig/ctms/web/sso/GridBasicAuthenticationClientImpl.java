/**
 * 
 */
package gov.nih.nci.cabig.ctms.web.sso;

import gov.nih.nci.cagrid.authentication.bean.BasicAuthenticationCredential;
import gov.nih.nci.cagrid.authentication.bean.Credential;
import gov.nih.nci.cagrid.authentication.client.AuthenticationServiceClient;
import gov.nih.nci.cagrid.authentication.stubs.types.InsufficientAttributeFault;
import gov.nih.nci.cagrid.authentication.stubs.types.InvalidCredentialFault;
import gov.nih.nci.cagrid.common.Utils;
import gov.nih.nci.cagrid.dorian.bean.SAMLAssertion;
import gov.nih.nci.cagrid.dorian.client.DorianClient;
import gov.nih.nci.cagrid.dorian.ifs.bean.DelegationPathLength;
import gov.nih.nci.cagrid.dorian.ifs.bean.ProxyLifetime;
import gov.nih.nci.cagrid.dorian.ifs.bean.PublicKey;
import gov.nih.nci.cagrid.dorian.stubs.types.InvalidAssertionFault;
import gov.nih.nci.cagrid.dorian.stubs.types.InvalidProxyFault;
import gov.nih.nci.cagrid.dorian.stubs.types.PermissionDeniedFault;
import gov.nih.nci.cagrid.dorian.stubs.types.UserPolicyFault;
import gov.nih.nci.cagrid.gridca.common.CertUtil;
import gov.nih.nci.cagrid.gridca.common.KeyUtil;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.security.KeyPair;
import java.security.cert.X509Certificate;

import javax.xml.namespace.QName;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.globus.gsi.GlobusCredential;

/**
 * @author <a href="mailto:joshua.phillips@semanticbits.com>Joshua Phillips</a>
 * 
 */
public class GridBasicAuthenticationClientImpl implements GridBasicAuthenticationClient {

  private static Log logger = LogFactory.getLog(GridBasicAuthenticationClientImpl.class);

  private String idpUrl;

  private String ifsUrl;

  private int proxyLifetimeHours = 12;

  private int proxyLifetimeMinutes = 0;

  private int proxyLifetimeSeconds = 0;

  private int delegationPathLength = 0;

  public String getIdpUrl() {
    return idpUrl;
  }

  public void setIdpUrl(String idpUrl) {
    this.idpUrl = idpUrl;
  }

  public String getIfsUrl() {
    return ifsUrl;
  }

  public void setIfsUrl(String ifsUrl) {
    this.ifsUrl = ifsUrl;
  }

  /*
   * (non-Javadoc)
   * 
   * @see sso.GridAuthenticationClient#(String,String)
   */
  public String authenticate(String usr, String pwd) throws AuthenticationErrorException {

    String proxyStr = null;

    AuthenticationServiceClient client = null;
    try {
      client = new AuthenticationServiceClient(getIdpUrl());
    } catch (Exception ex) {
      throw new AuthenticationErrorException("Error instantiating AuthenticationServiceClient: "
              + ex.getMessage(), ex);
    }

    BasicAuthenticationCredential bac = new BasicAuthenticationCredential();
    bac.setUserId(usr);
    bac.setPassword(pwd);
    Credential cred = new Credential();
    cred.setBasicAuthenticationCredential(bac);

    gov.nih.nci.cagrid.authentication.bean.SAMLAssertion saml = null;
    try {
      saml = client.authenticate(cred);
    } catch (InvalidCredentialFault ex) {
      logger.error(ex);
    } catch (InsufficientAttributeFault ex) {
      logger.error(ex);
    } catch (Exception ex) {
      throw new AuthenticationErrorException("Error getting SAMLAssertion: " + ex.getMessage());
    }

    if (saml != null) {
      String xml = saml.getXml();

      ProxyLifetime lifetime = new ProxyLifetime();
      lifetime.setHours(getProxyLifetimeHours());
      lifetime.setMinutes(getProxyLifetimeMinutes());
      lifetime.setSeconds(getProxyLifetimeSeconds());

      DorianClient dClient = null;
      try {
        dClient = new DorianClient(getIfsUrl());
      } catch (Exception ex) {
        throw new AuthenticationErrorException("Error instantiating DorianClient: "
                + ex.getMessage(), ex);
      }
      KeyPair pair = null;
      PublicKey key = null;
      try {
        pair = KeyUtil.generateRSAKeyPair512();
        key = new PublicKey(KeyUtil.writePublicKey(pair.getPublic()));
      } catch (Exception ex) {
        throw new AuthenticationErrorException("Error generating key pair: " + ex.getMessage(), ex);
      }

      SAMLAssertion saml2 = null;
      try {
        saml2 = new SAMLAssertion(xml);
      } catch (Exception ex) {
        throw new AuthenticationErrorException("Error parsing SAMLAssertion: " + ex.getMessage(),
                ex);
      }

      gov.nih.nci.cagrid.dorian.bean.X509Certificate list[] = null;
      try {
        list = dClient.createProxy(saml2, key, lifetime, new DelegationPathLength(
                getDelegationPathLength()));
      } catch (InvalidAssertionFault ex) {
        logger.error(ex);
      } catch (InvalidProxyFault ex) {
        logger.error(ex);
      } catch (UserPolicyFault ex) {
        logger.error(ex);
      } catch (PermissionDeniedFault ex) {
        logger.error(ex);
      } catch (Exception ex) {
        throw new AuthenticationErrorException("Error getting proxy: " + ex.getMessage(), ex);
      }
      if (list != null) {
        X509Certificate[] certs = new X509Certificate[list.length];
        for (int i = 0; i < list.length; i++) {
          try {
            certs[i] = CertUtil.loadCertificate(list[i].getCertificateAsString());
          } catch (Exception ex) {
            throw new AuthenticationErrorException("Error loading certificate: " + ex.getMessage(),
                    ex);
          }
        }
        GlobusCredential proxy = null;
        try {
          proxy = new GlobusCredential(pair.getPrivate(), certs);
        } catch (Exception ex) {
          throw new AuthenticationErrorException("Error instantiating GlobusCredential: "
                  + ex.getMessage(), ex);
        }

        try {
          ByteArrayOutputStream buf = new ByteArrayOutputStream();
          proxy.save(buf);
          proxyStr = buf.toString();
        } catch (Exception ex) {
          throw new AuthenticationErrorException("Error writing proxy to String: "
                  + ex.getMessage(), ex);
        }

      }
    }
    return proxyStr;
  }

  public int getDelegationPathLength() {
    return delegationPathLength;
  }

  public void setDelegationPathLength(int delegationPathLength) {
    this.delegationPathLength = delegationPathLength;
  }

  public int getProxyLifetimeHours() {
    return proxyLifetimeHours;
  }

  public void setProxyLifetimeHours(int proxyLifetimeHours) {
    this.proxyLifetimeHours = proxyLifetimeHours;
  }

  public int getProxyLifetimeMinutes() {
    return proxyLifetimeMinutes;
  }

  public void setProxyLifetimeMinutes(int proxyLifetimeMinutes) {
    this.proxyLifetimeMinutes = proxyLifetimeMinutes;
  }

  public int getProxyLifetimeSeconds() {
    return proxyLifetimeSeconds;
  }

  public void setProxyLifetimeSeconds(int proxyLifetimeSeconds) {
    this.proxyLifetimeSeconds = proxyLifetimeSeconds;
  }

  public static void main(String[] args) throws Exception {
      String idpUrl = System.getProperty("idpUrl", "https://cagrid-auth.nci.nih.gov:8443/wsrf/services/cagrid/AuthenticationService");
      String ifsUrl = System.getProperty("ifsUrl", "https://cagrid-dorian.nci.nih.gov:8443/wsrf/services/cagrid/Dorian");
      String proxyFile = System.getProperty("proxyFile", "proxy.txt");
      String username = getInput("Username: ");
      String password = getInput("Password: ");
      
      GridBasicAuthenticationClientImpl client = new GridBasicAuthenticationClientImpl();
      client.setIdpUrl(idpUrl);
      client.setIfsUrl(ifsUrl);
      String proxy = client.authenticate(username, password);
      
      FileWriter w = new FileWriter(proxyFile);
      w.write(proxy);
      w.flush();
      w.close();
      
      
  }
  
  public static String getInput(String prompt) throws IOException{
      String response = null;
      
      System.out.println(prompt);
      BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
      response = r.readLine();
      
      return response;
  }

}
