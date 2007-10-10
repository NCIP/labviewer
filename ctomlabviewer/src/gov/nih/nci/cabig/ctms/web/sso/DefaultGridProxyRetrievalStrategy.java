/**
 * 
 */
package gov.nih.nci.cabig.ctms.web.sso;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.globus.gsi.GlobusCredential;

/**
 * @author <a href="mailto:joshua.phillips@semanticbits.com>Joshua Phillips</a>
 * 
 */
public class DefaultGridProxyRetrievalStrategy implements GridProxyRetrievalStrategy {
    
    private static final Log logger = LogFactory.getLog(DefaultGridProxyRetrievalStrategy.class);

    private String usernameParam;

    private String passwordParam;

    private String gridProxyParam;

    private GridBasicAuthenticationClient authenticationClient;
    
    private GridProxyValidator validator;

    public GridProxyValidator getValidator() {
        return validator;
    }

    public void setValidator(GridProxyValidator validator) {
        this.validator = validator;
    }

    public GridBasicAuthenticationClient getAuthenticationClient() {
        return authenticationClient;
    }

    public void setAuthenticationClient(GridBasicAuthenticationClient authenticationClient) {
        this.authenticationClient = authenticationClient;
    }

    /*
     * (non-Javadoc)
     * 
     * @see gov.nih.nci.cabig.ctms.web.sso.GridProxyRequestProcessor#processRequest(javax.servlet.http.HttpServletRequest)
     */
    public String processRequest(HttpServletRequest request) {

        String proxyStr = null;

        // Look for the proxy as an attribute
        Object att = Utils.findAttribute(request,
                        getGridProxyParam());
        
       
        if (att != null) {
            if(att instanceof GlobusCredential){
                proxyStr = Utils.toString((GlobusCredential)att);    
            }else if(att instanceof String){
                proxyStr = (String)att;
            }
        }

        // Look for the proxy as a paramter
        if (proxyStr == null) {
            proxyStr = Utils.findParameter(request, getGridProxyParam());
        }

        // Try to use username and password to obtain a proxy
        if (proxyStr == null) {
            String username = Utils.findParameter(request, getUsernameParam());
            String password = Utils.findParameter(request, getPasswordParam());
            if (username != null && password != null) {
                try {
                    proxyStr = getAuthenticationClient().authenticate(username, password);
                } catch (Exception ex) {
                    logger.warn("Couldn't authenticate '" + username + "': " + ex.getMessage(), ex);
                }
            }
        }
        boolean valid = false;
        if(proxyStr != null){
            try{
                logger.debug("Validating proxy....");
                valid = getValidator().validate(proxyStr);
                logger.debug("Proxy valid?: " + valid);
            }catch(Exception ex){
                logger.error("Error validating proxy: " + ex.getMessage(), ex);
            }
        }
        
        if(!valid){
            proxyStr = null;
        }else{
            logger.debug("Putting proxy into session under '" + getGridProxyParam() + "'.");
            request.getSession().setAttribute(getGridProxyParam(), proxyStr);
        }

        return proxyStr;
    }


    public String getPasswordParam() {
        return passwordParam;
    }

    public void setPasswordParam(String passwordParam) {
        this.passwordParam = passwordParam;
    }

    public String getUsernameParam() {
        return usernameParam;
    }

    public void setUsernameParam(String usernameParam) {
        this.usernameParam = usernameParam;
    }

    public String getGridProxyParam() {
        return gridProxyParam;
    }

    public void setGridProxyParam(String gridProxyParam) {
        this.gridProxyParam = gridProxyParam;
    }

}
