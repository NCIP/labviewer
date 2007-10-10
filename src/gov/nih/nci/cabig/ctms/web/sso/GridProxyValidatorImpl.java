/**
 * 
 */
package gov.nih.nci.cabig.ctms.web.sso;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.security.cert.X509Certificate;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.globus.gsi.GlobusCredential;
import org.globus.gsi.TrustedCertificates;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author <a href="mailto:joshua.phillips@semanticbits.com>Joshua Phillips</a>
 * 
 */
public class GridProxyValidatorImpl implements GridProxyValidator {

  private static final Log logger = LogFactory.getLog(GridProxyValidatorImpl.class);

  /*
   * (non-Javadoc)
   * 
   * @see sso.GridProxyValidator#validate(java.lang.String)
   */
  public boolean validate(String proxy) throws GridProxyValidationException {
    boolean valid = false;

    GlobusCredential cred = null;
    try {
      cred = new GlobusCredential(new ByteArrayInputStream(proxy.getBytes()));
    } catch (Exception ex) {
      throw new GridProxyValidationException("Error instantiating GlobusCredentia: "
              + ex.getMessage(), ex);
    }

    try {
      cred.verify();
      
      X509Certificate[] chain = cred.getCertificateChain();
      String dir = System.getProperty("user.home") + "/.globus/certificates";
      X509Certificate[] trustedCerts = TrustedCertificates.loadCertificates(dir);
      
      if(trustedCerts == null){
          logger.debug("trustedCerts is null");
      }else{
          logger.debug("Found " + trustedCerts.length + " trusted certs");
          search: for(int i = 0; i < chain.length; i++){
              X509Certificate cert = chain[i];
              String issuerDN = cert.getIssuerDN().getName();
              for(int j = 0; j < trustedCerts.length; j++){
                  X509Certificate trustedCert = trustedCerts[j];
                  String subjectDN  = trustedCert.getSubjectDN().getName();
                  if(issuerDN.equals(subjectDN)){
                      cert.verify(trustedCert.getPublicKey());
                      logger.debug(cert.getSubjectDN() + " was signed by " + trustedCert.getSubjectDN());
                      valid = true;
                      break search;
                  }
              }
          }
      }

    } catch (Exception ex) {
      logger.debug("Proxy validation failed: " + ex.getMessage());
    }

    return valid;
  }

}
