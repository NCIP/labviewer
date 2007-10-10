/**
 * 
 */
package gov.nih.nci.cabig.ctms.web.sso;

import javax.servlet.http.HttpServletRequest;

/**
 * @author <a href="mailto:joshua.phillips@semanticbits.com>Joshua Phillips</a>
 *
 */
public interface GridProxyRetrievalStrategy {
    
    
    /**
     * Returns a string representation of the grid
     * proxy, given a request.
     * 
     * @param request
     * @return
     */
    String processRequest(HttpServletRequest request);

}
