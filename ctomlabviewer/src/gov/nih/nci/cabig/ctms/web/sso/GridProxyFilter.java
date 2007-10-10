/**
 * 
 */
package gov.nih.nci.cabig.ctms.web.sso;

import gov.nih.nci.caxchange.ctom.viewer.util.ObjectFactory;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * @author <a href="mailto:joshua.phillips@semanticbits.com>Joshua Phillips</a>
 * 
 */
public class GridProxyFilter implements Filter {

    private static final Log logger = LogFactory.getLog(GridProxyFilter.class);

    public static final String FAILUREURL_PARAM = "failureUrl";

    public static final String IGNOREURLPATTERN_PARAM = "ignoreUrlPattern";

    private static final String ISSTRICT_PARAM = "isStrict";

    private static final String PROXYRETRIEVALSTRGYBEANNAME_PARAM = "gridProxyRetrievalStrategyBeanName";
    
    private String failureUrl;

    private String ignoreUrlPattern;

    private boolean isStrict;

    private String gridProxyRetrievalStrategyBeanName;

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.Filter#destroy()
     */
    public void destroy() {
        // nothing to do
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
     *      javax.servlet.ServletResponse, javax.servlet.FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
                    throws IOException, ServletException {

        HttpServletRequest hReq = (HttpServletRequest) request;


        // Check if should short-circuit
       String reqUrl = hReq.getRequestURL().toString();
        logger.debug("Checking short-circuit for \"" + reqUrl + "\"");
        if (this.ignoreUrlPattern != null && reqUrl.matches(this.ignoreUrlPattern)) {
            chain.doFilter(request, response);
            return;
        }

        GridProxyRetrievalStrategy processor = null;
        try {
            ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(hReq
                            .getSession().getServletContext());
            

			
            processor = (GridProxyRetrievalStrategy) ObjectFactory.getObject(this.gridProxyRetrievalStrategyBeanName);
        } catch (Exception ex) {
            throw new ServletException("Error getting application context: " + ex.getMessage(), ex);
        }

        String proxy = processor.processRequest(hReq);

        
        RequestDispatcher disp = hReq.getRequestDispatcher(this.failureUrl);
        if (proxy == null && this.isStrict) {

            // Then the user needs to authenticate.
            logger.debug("No proxy found. Redirecting to " + this.failureUrl);
            disp.forward(request, response);
        } else {
    		
    		request.setAttribute("gridProxy", proxy);
    		chain.doFilter(request, response);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    public void init(FilterConfig conf) throws ServletException {
        if (conf != null) {
            if (conf.getInitParameter(FAILUREURL_PARAM) != null) {
                this.failureUrl = conf.getInitParameter(FAILUREURL_PARAM);
            }
            if (conf.getInitParameter(IGNOREURLPATTERN_PARAM) != null) {
                this.ignoreUrlPattern = conf.getInitParameter(IGNOREURLPATTERN_PARAM);
            }
            if (conf.getInitParameter(ISSTRICT_PARAM) != null) {
                try {
                    this.isStrict = Boolean.valueOf(conf.getInitParameter(ISSTRICT_PARAM));
                } catch (Exception ex) {
                    throw new ServletException("Error getting isStrict parameter: "
                                    + ex.getMessage(), ex);
                }
            }
            if (conf.getInitParameter(PROXYRETRIEVALSTRGYBEANNAME_PARAM) != null) {
                this.gridProxyRetrievalStrategyBeanName = conf
                                .getInitParameter(PROXYRETRIEVALSTRGYBEANNAME_PARAM);
            }

        }
    }

}
