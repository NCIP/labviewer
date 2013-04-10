/*
* Copyright ScenPro, Inc and SemanticBits, LLC
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/labviewer/LICENSE.txt for details.
*/

package gov.nih.nci.lv.util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
/**
 * Hibernate helper interface.
 * @author NAmiruddin
 *
 */
public interface LVHibernateHelper {
    /**
     * @return the sessionFactory
     */
    SessionFactory getSessionFactory();

    /**
     * Get the session that is bound to the current context.
     * @return the current session
     */
    Session getCurrentSession();

    /**
     * @return the configuration
     */
    Configuration getConfiguration();

    /**
     * Open a hibernate session and bind it as the current session.
     */
    void openAndBindSession();

    /**
     * Close the current session and unbind it.
     */
    void unbindAndCleanupSession();
}
