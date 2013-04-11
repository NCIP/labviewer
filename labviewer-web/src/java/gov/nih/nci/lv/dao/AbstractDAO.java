/*
* Copyright ScenPro, Inc and SemanticBits, LLC
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/labviewer/LICENSE.txt for details.
*/

package gov.nih.nci.lv.dao;

import gov.nih.nci.lv.util.HibernateUtil;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * An abstract Base DAO class.
 * @author NAmiruddin

 */
public class AbstractDAO { 
    
    private static final Logger LOG  = Logger.getLogger(AbstractDAO.class);

    
    /**
     * open a current session with a transaction.
     * @return Session 
     */
    Session getSession() {
        Session session = HibernateUtil.getCurrentSession();
        session.beginTransaction();
        return session;
    }
    
    /**
     * 
     * @param sql sql for execution
     * @return list of domain objs
     */
    List<Object> executeObjSql(String sql) {
        LOG.info("sql " + sql);
        Query query = getSession().createQuery(sql);
        List<Object> bos = query.list();
        LOG.debug("total retrieved " + bos.size());
        return bos;
    }
    
    
}
