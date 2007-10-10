/**
 * 
 */
package gov.nih.nci.cabig.ctms.web.sso;

/**
 * @author <a href="mailto:joshua.phillips@semanticbits.com>Joshua Phillips</a>
 * 
 */
public interface GridBasicAuthenticationClient {

    /**
     * 
     * @param username
     * @param password
     * @return String representation of grid proxy
     * @throws AuthenticationErrorException if an error is encountered during authentication
     */
    String authenticate(String username, String password) throws AuthenticationErrorException;

}
