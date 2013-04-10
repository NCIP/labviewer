/*
* Copyright ScenPro, Inc and SemanticBits, LLC
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/labviewer/LICENSE.txt for details.
*/

package gov.nih.nci.lv.util;


import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.ConnectionReleaseMode;
import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.Interceptor;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.NamingStrategy;
import org.hibernate.context.ManagedSessionContext;
import org.hibernate.engine.SessionFactoryImplementor;
/**
 * Hibernate Helper class.
 * @author Naveen Amiruddin
 *
 */

public class HibernateHelper implements LVHibernateHelper {
    /**
     * The maximum number of elements that can be in a single in clause. This is due to bug
     * http://opensource.atlassian.com/projects/hibernate/browse/HHH-2166
     */
    public static final int MAX_IN_CLAUSE_LENGTH = 500;

    private static final Logger LOG = Logger.getLogger(HibernateHelper.class);


    private Configuration configuration;
    private SessionFactory sessionFactory;

    /**
     * Default constructor.
     */
    public HibernateHelper() {
        this(null, null);
    }

    /**
     * Constructor to build the hibernate helper.
     * @param namingStrategy the name strategy, if one is needed, null otherwise.
     * @param interceptor the interceptor, if one is needed, null otherwise.
     */
    public HibernateHelper(NamingStrategy namingStrategy, Interceptor interceptor) {
        try {
            configuration = new AnnotationConfiguration();
            initializeConfig(namingStrategy, interceptor);
            configuration = configuration.configure();
            // We call buildSessionFactory twice, because it appears that the annotated classes are
            // not 'activated' in the config until we build. The filters required the classes to
            // be present, so we throw away the first factory and use the second. If this is
            // removed, you'll likely see a NoClassDefFoundError in the unit tests
            configuration.buildSessionFactory();
            sessionFactory = configuration.buildSessionFactory();
        } catch (HibernateException e) {
//            LOG.error(e.getMessage(), e);
//            throw new ExceptionInInitializerError(e);
            LOG.warn("Failed to initialize HibernateHelper using hibernate.cfg.xml.  "
                    + "This is expected behavior during unit testing." , e);
            e.printStackTrace();
        }
    }


    private void initializeConfig(NamingStrategy namingStrategy, Interceptor interceptor) {
        if (namingStrategy != null) {
            configuration.setNamingStrategy(namingStrategy);
        }

        if (interceptor != null) {
            configuration.setInterceptor(interceptor);
        }
    }

    /**
     * @return the configuration
     */
    public Configuration getConfiguration() {
        return this.configuration;
    }

    /**
     * @return the sessionFactory
     */
    public SessionFactory getSessionFactory() {
        return this.sessionFactory;
    }

    /**
     * Get the session that is bound to the current context.
     * @return the current session
     */
    public Session getCurrentSession() {
         Session s =  getSessionFactory().getCurrentSession();
         beginTransaction();
         return s;
    }

    /**
     * Starts a transaction on the current Hibernate session. Intended for use in
     * unit tests - DAO / Service layer logic should rely on container-managed transactions
     *
     * @return a Hibernate session.
     */
    public Transaction beginTransaction() {
        return getSessionFactory().getCurrentSession().beginTransaction();
    }

    /**
     * Checks if the transaction is active and then rolls it back.
     *
     * @param tx the Transaction to roll back.
     */
    public void rollbackTransaction(Transaction tx) {
        if ((tx != null) && (tx.isActive())) {
            tx.rollback();
        }
    }

    /**
     * Open a hibernate session and bind it as the current session via
     * {@link ManagedSessionContext#bind(org.hibernate.classic.Session)}. The hibernate property
     * "hibernate.current_session_context_class" must be set to "managed" for this to have effect This method should be
     * called from within an Interceptor or Filter type class that is setting up the scope of the Session. This method
     * should then call {@link HibernateUtil#unbindAndCleanupSession()} when the scope of the Session is expired.
     *
     * @see ManagedSessionContext#bind(org.hibernate.classic.Session)
     */
    public void openAndBindSession() {
        SessionFactoryImplementor sessionFactoryImplementor = (SessionFactoryImplementor) getSessionFactory();
        org.hibernate.classic.Session currentSession = sessionFactoryImplementor.openSession(null, true, false,
                ConnectionReleaseMode.AFTER_STATEMENT);
        currentSession.setFlushMode(FlushMode.COMMIT);
        ManagedSessionContext.bind(currentSession);
    }

    /**
     * Close the current session and unbind it via {@link ManagedSessionContext#unbind(SessionFactory)}. The hibernate
     * property "hibernate.current_session_context_class" must be set to "managed" for this to have effect. This method
     * should be called from within an Interceptor or Filter type class that is setting up the scope of the Session,
     * when this scope is about to expire.
     */
    public void unbindAndCleanupSession() {
        org.hibernate.classic.Session currentSession = ManagedSessionContext.unbind(getSessionFactory());
        if (currentSession != null) {
            currentSession.close();
        }
    }

    /**
     * Break up a list of items into separate in clauses, to avoid limits imposed by databases or by Hibernate bug
     * http://opensource.atlassian.com/projects/hibernate/browse/HHH-2166.
     * @param items list of items to include in the in clause
     * @param columnName name of column to match against the list
     * @param blocks empty Map of HQL param name to param list of values to be set in the HQL query - it will be
     *               populated by the method
     * @return full HQL "in" clause, of the form: " columnName in (:block1) or ... or columnName in (:blockN)"
     */
    public static String buildInClause(List<? extends Serializable> items, String columnName,
            Map<String, List<? extends Serializable>> blocks) {
        StringBuffer inClause = new StringBuffer();
        for (int i = 0; i < items.size(); i += MAX_IN_CLAUSE_LENGTH) {
            List<? extends Serializable> block = items.subList(i, Math.min(items.size(), i + MAX_IN_CLAUSE_LENGTH));
            String paramName = "block" + (i / MAX_IN_CLAUSE_LENGTH);
            if (inClause.length() > 0) {
                inClause.append(" or");
            }
            inClause.append(" " + columnName + " in (:" + paramName + ")");
            blocks.put(paramName, block);
        }
        return inClause.toString();
    }

    /**
     * Bind the parameters returned by {@link #buildInClause(List, String, Map)} to a hibernate Query.
     * @param query hibernate query to bind to
     * @param blocks blocks to be bound to query
     */
    public static void bindInClauseParameters(Query query, Map<String, List<? extends Serializable>> blocks) {
        for (Map.Entry<String, List<? extends Serializable>> block : blocks.entrySet()) {
            query.setParameterList(block.getKey(), block.getValue());
        }
    }

}


