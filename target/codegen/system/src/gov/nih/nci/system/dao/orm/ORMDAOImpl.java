package gov.nih.nci.system.dao.orm;

import gov.nih.nci.system.dao.DAO;
import gov.nih.nci.system.dao.DAOException;
import gov.nih.nci.system.dao.Request;
import gov.nih.nci.system.dao.Response;
import gov.nih.nci.system.dao.orm.translator.CQL2HQL;
import gov.nih.nci.system.dao.orm.translator.NestedCriteria2HQL;
import gov.nih.nci.system.dao.orm.translator.Path2NestedCriteria;
import gov.nih.nci.system.query.cql.CQLQuery;
import gov.nih.nci.system.query.hibernate.HQLCriteria;
import gov.nih.nci.system.query.nestedcriteria.NestedCriteria;
import gov.nih.nci.system.query.nestedcriteria.NestedCriteriaPath;
import gov.nih.nci.system.security.helper.SecurityInitializationHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.JDBCException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * @author Satish Patel, Dan Dumitru
 *
 */
public class ORMDAOImpl extends HibernateDaoSupport implements DAO 
{
	protected static Logger log = Logger.getLogger(DAO.class.getName());

	private Configuration config;
	private SecurityInitializationHelper securityHelper;

	private boolean caseSensitive;
	private int resultCountPerQuery;

	
	protected HibernateTemplate createHibernateTemplate(SessionFactory sessionFactory)
	{
		if(securityHelper!=null && securityHelper.isSecurityEnabled() && securityHelper.getAuthorizationManager()!=null && (securityHelper.isInstanceLevelSecurityEnabled() || securityHelper.isAttributeLevelSecurityEnabled()))
			return new FilterableHibernateTemplate(sessionFactory,securityHelper);
		else
			return super.createHibernateTemplate(sessionFactory);
	}

	public Response query(Request request) throws DAOException 
	{
		Object obj = request.getRequest();

		try
		{
			log.debug("****** obj: " + obj.getClass());
			if (obj instanceof DetachedCriteria) 				
				return query(request, (DetachedCriteria) obj); 	
			else if (obj instanceof NestedCriteriaPath)
				return query(request, (NestedCriteriaPath) obj); 	
			else if (obj instanceof HQLCriteria)
				return query(request, (HQLCriteria) obj);
			else if (obj instanceof CQLQuery)
				return query(request, (CQLQuery) obj);
			else
				throw new DAOException("Can not determine type of the query");
		} catch (JDBCException ex){
			log.error("JDBC Exception in ORMDAOImpl ", ex);
			throw new DAOException("JDBC Exception in ORMDAOImpl ", ex);
		} catch(org.hibernate.HibernateException hbmEx)	{
			log.error(hbmEx.getMessage());
			throw new DAOException("Hibernate problem ", hbmEx);
		} catch(Exception e) {
			log.error("Exception ", e);
			throw new DAOException("Exception in ORMDAOImpl ", e);
		}
	}
	
    public List<String> getAllClassNames(){
    	
    	List<String> allClassNames = new ArrayList<String>();
    	Map allClassMetadata = getSessionFactory().getAllClassMetadata();
    	
    	for (Iterator iter = allClassMetadata.keySet().iterator() ; iter.hasNext(); ){		
    		allClassNames.add((String)iter.next());
    	}
    	
    	return allClassNames;
    }
	
	protected Response query(Request request, DetachedCriteria obj) throws Exception
	{
		Response rsp = new Response();
		log.info("Detached Criteria Query :"+obj.toString());
		
	    if(request.getIsCount() != null && request.getIsCount())
	    {
	    	HibernateCallback callBack = getExecuteCountCriteriaHibernateCallback(obj);
	        Integer rowCount = (Integer)getHibernateTemplate().execute(callBack);
			log.debug("DetachedCriteria ORMDAOImpl ===== count = " + rowCount);
			rsp.setRowCount(rowCount);
	    }
	    else 
	    {
	    	List rs = getHibernateTemplate().findByCriteria(obj, request.getFirstRow() == null?-1:request.getFirstRow(), resultCountPerQuery);
	    	rsp.setRowCount(rs.size());
	        rsp.setResponse(rs);
	    }
	    
		return rsp;
	}	
	
	//	if (obj instanceof NestedCriteriaPath)
	protected Response query(Request request, NestedCriteriaPath obj) throws Exception	
	{
		NestedCriteria nc = Path2NestedCriteria.createNestedCriteria(obj.getpathString(), obj.getParameters(), request.getClassCache());
		NestedCriteria2HQL converter = new NestedCriteria2HQL(nc, config, caseSensitive);
		HQLCriteria hqlCriteria = converter.translate();
		return query(request, hqlCriteria);
	}
	
	//if (obj instanceof CQLQuery)
	protected Response query(Request request, CQLQuery obj) throws Exception	
	{
		CQL2HQL converter = new CQL2HQL(request.getClassCache());
		HQLCriteria hqlCriteria = converter.translate((CQLQuery)obj, false, caseSensitive);
		return query(request, hqlCriteria);		
	}

	//if (obj instanceof HQLCriteria)
	protected Response query(Request request, HQLCriteria hqlCriteria) throws Exception
	{
		if(request.getIsCount() != null && request.getIsCount())
		{
			String countQ = hqlCriteria.getCountHqlString();
			if(countQ == null)
				countQ = getCountQuery(hqlCriteria.getHqlString());
			log.info("HQL Query :"+countQ);
			Response rsp = new Response();
	    	HibernateCallback callBack = getExecuteCountQueryHibernateCallback(countQ,hqlCriteria.getParameters());
			Integer rowCount = Integer.parseInt(getHibernateTemplate().execute(callBack)+"");
			log.debug("HQL Query : count = " + rowCount);		
			rsp.setRowCount(rowCount);
			return rsp;
		}
		else 
		{
			log.info("HQL Query :"+hqlCriteria.getHqlString());
			Response rsp = new Response();
	    	HibernateCallback callBack = getExecuteFindQueryHibernateCallback(hqlCriteria.getHqlString(),hqlCriteria.getParameters(), request.getFirstRow() == null?-1:request.getFirstRow(),resultCountPerQuery);
	    	List rs = (List)getHibernateTemplate().execute(callBack);
	    	rsp.setRowCount(rs.size());
	    	rsp.setResponse(rs);
			return rsp;
		}
	}
	
	protected HibernateCallback getExecuteFindQueryHibernateCallback(final String hql, final List params, final int firstResult, final int maxResult)
	{
		HibernateCallback callBack = new HibernateCallback(){

			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				query.setFirstResult(firstResult);				    		
				query.setMaxResults(maxResult);
				
				int count = 0;
				if(params!=null)
					for(Object param:params)
						query.setParameter(count++, param);
				return query.list();
			}
		};
		return callBack;
	}

	protected HibernateCallback getExecuteCountQueryHibernateCallback(final String hql, final List params)
	{
		HibernateCallback callBack = new HibernateCallback(){

			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				query.setMaxResults(1);
				
				int count = 0;
				if(params!=null)
					for(Object param:params)
						query.setParameter(count++, param);
				return query.uniqueResult();
			}
		};
		return callBack;
	}	

	protected HibernateCallback getExecuteCountCriteriaHibernateCallback(final DetachedCriteria criteria)
	{
		HibernateCallback callBack = new HibernateCallback(){

			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria exeCriteria = criteria.getExecutableCriteria(session);
				return exeCriteria.setProjection(Projections.rowCount()).uniqueResult();
			}
		};
		return callBack;
	}	
	
	private String getCountQuery(String hql)
	{
		return NestedCriteria2HQL.getCountQuery(hql);
	}

	public boolean isCaseSensitive() {
		return caseSensitive;
	}

	public void setCaseSensitive(boolean caseSensitive) {
		this.caseSensitive = caseSensitive;
	}

	public Configuration getConfig() {
		return config;
	}

	public void setConfig(Configuration config) {
		this.config = config;
	}

	public int getResultCountPerQuery() {
		return resultCountPerQuery;
	}

	public void setResultCountPerQuery(int resultCountPerQuery) {
		this.resultCountPerQuery = resultCountPerQuery;
	}

	public SecurityInitializationHelper getSecurityHelper() {
		return securityHelper;
	}

	public void setSecurityHelper(SecurityInitializationHelper securityHelper) {
		this.securityHelper = securityHelper;
	}
}