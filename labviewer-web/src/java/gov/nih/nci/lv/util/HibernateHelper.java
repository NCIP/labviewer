/*
* caBIG Open Source Software License
*
* Copyright Notice.  Copyright 2008, ScenPro, Inc,  (caBIG Participant).   The Protocol  Abstraction (PA) Application
* was created with NCI funding and is part of  the caBIG initiative. The  software subject to  this notice  and license
* includes both  human readable source code form and machine readable, binary, object code form (the caBIG Software).
*
* This caBIG Software License (the License) is between caBIG  Participant  and  You.  You (or Your) shall  mean a
* person or an entity, and all other entities that control, are  controlled by,  or  are under common  control  with the
* entity.  Control for purposes of this definition means
*
* (i) the direct or indirect power to cause the direction or management of such entity,whether by contract
* or otherwise,or
*
* (ii) ownership of fifty percent (50%) or more of the outstanding shares, or
*
* (iii) beneficial ownership of such entity.
* License.  Provided that You agree to the conditions described below, caBIG Participant  grants  You a  non-exclusive,
* worldwide, perpetual, fully-paid-up, no-charge, irrevocable,  transferable  and royalty-free  right and license in its
* rights in the caBIG Software, including any copyright or patent rights therein, to
*
* (i) use,install, disclose, access, operate,  execute, reproduce,  copy, modify, translate,  market,  publicly display,
* publicly perform, and prepare derivative works of the caBIG Software in any manner and for any  purpose,  and to have
* or permit others to do so;
*
* (ii) make, have made, use, practice, sell, and offer  for sale,  import, and/or  otherwise  dispose of caBIG Software
* (or portions thereof);
*
* (iii) distribute and have distributed  to  and by third   parties the   caBIG  Software  and any   modifications  and
* derivative works thereof; and (iv) sublicense the  foregoing rights  set  out in (i), (ii) and (iii) to third parties,
* including the right to license such rights to further third parties. For sake of clarity,and not by way of limitation,
* caBIG Participant shall have no right of accounting or right of payment from You or Your sub licensees for the rights
* granted under this License.   This  License  is  granted  at no  charge  to You. Your downloading, copying, modifying,
* displaying, distributing or use of caBIG Software constitutes acceptance  of  all of the terms and conditions of this
* Agreement.  If You do not agree to such terms and conditions,  You have no right to download,  copy,  modify, display,
* distribute or use the caBIG Software.
*
* 1.  Your redistributions of the source code for the caBIG Software must retain the above copyright notice, this  list
* of conditions and the disclaimer and limitation of liability of Article 6 below.   Your redistributions in object code
* form must reproduce the above copyright notice,  this list of  conditions  and the  disclaimer  of  Article  6  in the
* documentation and/or other materials provided with the distribution, if any.
*
* 2.  Your end-user documentation included with the redistribution, if any,  must include the  following acknowledgment:
* This product includes software developed by ScenPro, Inc.   If  You  do not include such end-user documentation, You
* shall include this acknowledgment in the caBIG Software itself, wherever such third-party acknowledgments normally
* appear.
*
* 3.  You may not use the names ScenPro, Inc., The National Cancer Institute, NCI, Cancer Bioinformatics Grid or
* caBIG to endorse or promote products derived from this caBIG Software.  This License does not authorize You to use
* any trademarks, service marks, trade names, logos or product names of either caBIG Participant, NCI or caBIG, except
* as required to comply with the terms of this License.
*
* 4.  For sake of clarity, and not by way of limitation, You  may incorporate this caBIG Software into Your proprietary
* programs and into any third party proprietary programs.  However, if You incorporate the  caBIG Software  into  third
* party proprietary programs,  You agree  that You are  solely responsible  for obtaining any permission from such third
* parties required to incorporate the caBIG Software  into such third party proprietary programs and for informing Your
* sub licensees, including without limitation Your end-users, of their obligation  to  secure  any  required permissions
* from such third parties before incorporating the caBIG Software into such third party proprietary  software programs.
* In the event that You fail to obtain such permissions,  You  agree  to  indemnify  caBIG  Participant  for any claims
* against caBIG Participant by such third parties, except to the extent prohibited by law,  resulting from Your failure
* to obtain such permissions.
*
* 5.  For sake of clarity, and not by way of limitation, You may add Your own copyright statement  to Your modifications
* and to the derivative works, and You may provide  additional  or  different  license  terms  and  conditions  in  Your
* sublicenses of modifications of the caBIG  Software,  or  any  derivative  works  of  the caBIG Software as a whole,
* provided Your use, reproduction,  and  distribution  of the Work otherwise complies with the conditions stated in this
* License.
*
* 6.  THIS caBIG SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED WARRANTIES  ( INCLUDING, BUT NOT LIMITED TO,
* THE IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED.  IN
* NO EVENT SHALL THE ScenPro, Inc. OR ITS AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
* OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT  LIMITED  TO,  PROCUREMENT OF SUBSTITUTE GOODS  OR SERVICES; LOSS OF USE,
* DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
* LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS caBIG SOFTWARE, EVEN
* IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
*
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
                    + "This is expected behavior during unit testing.");
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
    

