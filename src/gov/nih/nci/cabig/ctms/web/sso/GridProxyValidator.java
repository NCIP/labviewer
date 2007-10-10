/**
 * 
 */
package gov.nih.nci.cabig.ctms.web.sso;

/**
 * @author <a href="mailto:joshua.phillips@semanticbits.com>Joshua Phillips</a>
 * 
 */
public interface GridProxyValidator {

  boolean validate(String proxy) throws GridProxyValidationException;

}
