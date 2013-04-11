/*
* Copyright ScenPro, Inc and SemanticBits, LLC
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/labviewer/LICENSE.txt for details.
*/


package gov.nih.nci.lv.util;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * Hibernate bind session.
 * @author NAmiruddin
 *
 */
public class BindHibernateSession implements Filter {

    /**
     * {@inheritDoc}
     */
    public void destroy() {
        // no op
    }

    /**
     * {@inheritDoc}
     */
    public void doFilter(ServletRequest arg0, ServletResponse arg1,
            FilterChain arg2) throws IOException, ServletException {
        try {
            HibernateUtil.getHibernateHelper().openAndBindSession();
            arg2.doFilter(arg0, arg1);
            HibernateUtil.getHibernateHelper().unbindAndCleanupSession();
        } catch (Exception e) {
            System.out.println(" exception e ");
            e.printStackTrace();
        } catch (Throwable e) {
            System.out.println(" throwable e ");
            e.printStackTrace();

        }

    }

    /**
     * {@inheritDoc}
     */
    public void init(FilterConfig arg0) throws ServletException {
     // no-op
    }

}
