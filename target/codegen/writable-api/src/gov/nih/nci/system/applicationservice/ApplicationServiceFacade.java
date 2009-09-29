package gov.nih.nci.system.applicationservice;

import gov.nih.nci.system.query.SDKQuery;
import gov.nih.nci.system.query.SDKQueryResult;
import gov.nih.nci.system.query.cql.CQLQuery;
import gov.nih.nci.system.query.example.DeleteExampleQuery;
import gov.nih.nci.system.query.example.InsertExampleQuery;
import gov.nih.nci.system.query.example.SearchExampleQuery;
import gov.nih.nci.system.query.example.UpdateExampleQuery;
import gov.nih.nci.system.query.hibernate.HQLCriteria;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;


@Deprecated
public class ApplicationServiceFacade implements WritableApplicationService {
	
	private WritableApplicationService appService ;
	
	public ApplicationServiceFacade(WritableApplicationService appService){
		this.appService = appService;
	}
	
	protected ApplicationService getApplicationService() {
		return appService;
	}
	
	/**
	 * Retrieves the result from the data source using the CQL query. The CQL query structure is converted into the
	 * data source specific query language. For the Object Relational Mapping based persistence tier, the CQL query
	 * structure is converted in the Hibernate Query Language (HQL). Hibernate converts the HQL into SQL and executes
	 * it against the relational database. 
	 * 
	 * The retrieved results are converted into a list which may not be completely loaded. If the retrieved results 
	 * are more than the maximum number of supported records as indicated by {@link #getMaxRecordsCount()} then the
	 * result set will be partially loaded. The client framework will execute a subsequent query (transparent to the
	 * client application) against the <code>ApplicationService</code> to load the remaining results in the chunk 
	 * no greater than value specified by {@link #getMaxRecordsCount()}.
	 * 
	 * <B>Note:</B> The <code>targetClassName</code> parameter will not be interpreted by the system. This parameter
	 * will be determined from the CQLQuery. 
	 * 
	 * @param cqlQuery
	 * @param targetClassName
	 * @return
	 * @throws ApplicationException
	 * @see {@link #query(CQLQuery)}
	 */
	@Deprecated
	public <E> List<E> query(CQLQuery cqlQuery, String targetClassName) throws ApplicationException{
		return appService.query(cqlQuery, targetClassName);
	}
	
	/**
	 * Retrieves the result from the data source using the CQL query. The CQL query structure is converted into the
	 * data source specific query language. For the Object Relational Mapping based persistence tier, the CQL query
	 * structure is converted in the Hibernate Query Language (HQL). Hibernate converts the HQL into SQL and executes
	 * it against the relational database. 
	 * 
	 * The retrieved results are converted into a list which may not be completely loaded. If the retrieved results 
	 * are more than the maximum number of supported records as indicated by {@link #getMaxRecordsCount()} then the
	 * result set will be partially loaded. The client framework will execute a subsequent query (transparent to the
	 * client application) against the <code>ApplicationService</code> to load the remaining results in the chunk 
	 * no greater than value specified by {@link #getMaxRecordsCount()}.
	 * 
	 * @param cqlQuery
	 * @return
	 * @throws ApplicationException
	 */
	public <E> List<E> query(CQLQuery cqlQuery) throws ApplicationException {
		return appService.query(cqlQuery);
	}

	/**
	 * Retrieves the result from the data source using the DetachedCriteria query. The DetachedCriteria query structure 
	 * can be used only by the Object Relational Mapping based persistence tier. Hibernate executes it against the 
	 * relational database and fetches the results. 
	 * 
	 * The retrieved results are converted into a list which may not be completely loaded. If the retrieved results 
	 * are more than the maximum number of supported records as indicated by {@link #getMaxRecordsCount()} then the
	 * result set will be partially loaded. The client framework will execute a subsequent query (transparent to the
	 * client application) against the <code>ApplicationService</code> to load the remaining results in the chunk 
	 * no greater than value specified by {@link #getMaxRecordsCount()}.
	 * 
	 * <B>Note:</B> The <code>targetClassName</code> parameter will not be interpreted by the system. This parameter
	 * will be determined from the DetachedCriteria object. 
	 * 
	 * @param detachedCriteria
	 * @param targetClassName
	 * @return
	 * @throws ApplicationException
	 * @see {@link #query(DetachedCriteria)}
	 */
	@Deprecated
	public <E> List<E> query(DetachedCriteria detachedCriteria, String targetClassName) throws ApplicationException {
		return appService.query(detachedCriteria, targetClassName);
	}
	
	/**
	 * Retrieves the result from the data source using the DetachedCriteria query. The DetachedCriteria query structure 
	 * can be used only by the Object Relational Mapping based persistence tier. Hibernate executes it against the 
	 * relational database and fetches the results. 
	 * 
	 * The retrieved results are converted into a list which may not be completely loaded. If the retrieved results 
	 * are more than the maximum number of supported records as indicated by {@link #getMaxRecordsCount()} then the
	 * result set will be partially loaded. The client framework will execute a subsequent query (transparent to the
	 * client application) against the <code>ApplicationService</code> to load the remaining results in the chunk 
	 * no greater than value specified by {@link #getMaxRecordsCount()}.
	 * 
	 * @param detachedCriteria
	 * @return
	 * @throws ApplicationException
	 */
	public <E> List<E> query(DetachedCriteria detachedCriteria) throws ApplicationException {
		return appService.query(detachedCriteria);
	}
	
	/**
	 * Retrieves the result from the data source using the HQL query. The HQL query structure can be used only by 
	 * the Object Relational Mapping based persistence tier. Hibernate executes hql query against the relational 
	 * database and fetches the results. 
	 * 
	 * The retrieved results are converted into a list which may not be completely loaded. If the retrieved results 
	 * are more than the maximum number of supported records as indicated by {@link #getMaxRecordsCount()} then the
	 * result set will be partially loaded. The client framework will execute a subsequent query (transparent to the
	 * client application) against the <code>ApplicationService</code> to load the remaining results in the chunk 
	 * no greater than value specified by {@link #getMaxRecordsCount()}.
	 * 
	 * <B>Note:</B> The <code>targetClassName</code> parameter will not be interpreted by the system. This parameter
	 * will be determined from the hql query. 
	 * 
	 * @param hqlCriteria
	 * @param targetClassName
	 * @return
	 * @throws ApplicationException
	 * @see {@link #query(HQLCriteria)}
	 */
	@Deprecated
	public <E> List<E> query(HQLCriteria hqlCriteria, String targetClassName) throws ApplicationException {
		return appService.query(hqlCriteria, targetClassName);
	}
	
	/**
	 * Retrieves the result from the data source using the HQL query. The HQL query structure can be used only by 
	 * the Object Relational Mapping based persistence tier. Hibernate executes hql query against the relational 
	 * database and fetches the results. 
	 * 
	 * The retrieved results are converted into a list which may not be completely loaded. If the retrieved results 
	 * are more than the maximum number of supported records as indicated by {@link #getMaxRecordsCount()} then the
	 * result set will be partially loaded. The client framework will execute a subsequent query (transparent to the
	 * client application) against the <code>ApplicationService</code> to load the remaining results in the chunk 
	 * no greater than value specified by {@link #getMaxRecordsCount()}.
	 * 
	 * @param hqlCriteria
	 * @return
	 * @throws ApplicationException
	 */
	public <E> List<E> query(HQLCriteria hqlCriteria) throws ApplicationException {
		return appService.query(hqlCriteria);
	}

	/**
	 * Retrieves the result from the data source using the Query by Example. The <code>targetClass</code> specifies 
	 * the object that the user intends to fetch after executing the query. The <code>targetClass</code> should be 
	 * same as the object specified in the objList or associated object for the example object. Also, all the objects 
	 * in the <code>objList</code> has to be of the same type. The example query is converted into the data source 
	 * specific query language. For the Object Relational Mapping based persistence tier, the example query structure 
	 * is converted in the Hibernate Query Language (HQL). Hibernate converts the HQL into SQL and executes it against
	 * the relational database.
	 *  
	 * The retrieved results are converted into a list which may not be completely loaded. If the retrieved results 
	 * are more than the maximum number of supported records as indicated by {@link #getMaxRecordsCount()} then the
	 * result set will be partially loaded. The client framework will execute a subsequent query (transparent to the
	 * client application) against the <code>ApplicationService</code> to load the remaining results in the chunk 
	 * no greater than value specified by {@link #getMaxRecordsCount()}.
	 * 
	 * @param targetClass
	 * @param objList
	 * @return
	 * @throws ApplicationException
	 */
	public <E> List<E> search(Class targetClass, List objList) throws ApplicationException{
		return appService.search(targetClass, objList);
	}
	
	/**
	 * Retrieves the result from the data source using the Query by Example. The <code>targetClass</code> specifies 
	 * the object that the user intends to fetch after executing the query. The <code>targetClass</code> should be 
	 * same as the example object or associated object for the example object. The example query is converted into 
	 * the data source specific query language. For the Object Relational Mapping based persistence tier, the example 
	 * query structure is converted in the Hibernate Query Language (HQL). Hibernate converts the HQL into SQL and 
	 * executes it against the relational database.
	 *  
	 * The retrieved results are converted into a list which may not be completely loaded. If the retrieved results 
	 * are more than the maximum number of supported records as indicated by {@link #getMaxRecordsCount()} then the
	 * result set will be partially loaded. The client framework will execute a subsequent query (transparent to the
	 * client application) against the <code>ApplicationService</code> to load the remaining results in the chunk 
	 * no greater than value specified by {@link #getMaxRecordsCount()}.
	 * 
	 * @param targetClass
	 * @param obj
	 * @return
	 * @throws ApplicationException
	 */
	public <E> List<E> search(Class targetClass, Object obj) throws ApplicationException {
		return appService.search(targetClass, obj);
	}
	
	/**
	 * Retrieves the result from the data source using a Nested Search Criteria. The <code>path</code> specifies 
	 * the list of objects (separated by commas), which should be used to reach the target object from the example objects 
	 * passed in the <code>objList</code> or associated object for the example object. The Nested Search Criteria 
	 * is converted into the data source specific query language. For the Object Relational Mapping based persistence 
	 * tier, the query structure is first converted into the Hibernate Query Language (HQL). Hibernate then converts the HQL into 
	 * SQL and executes it against the relational database.
	 *  
	 * The retrieved results are converted into a list which may not be completely loaded. If the retrieved results 
	 * are more than the maximum number of supported records as indicated by {@link #getMaxRecordsCount()} then the
	 * result set will be partially loaded. The client framework will execute a subsequent query (transparent to the
	 * client application) against the <code>ApplicationService</code> to load the remaining results in the chunk 
	 * no greater than value specified by {@link #getMaxRecordsCount()}.
	 * 
	 * @param path
	 * @param objList
	 * @return
	 * @throws ApplicationException
	 */
	public <E> List<E> search(String path, List objList) throws ApplicationException{
		return appService.search(path, objList);
	}
	
	/**
	 * Retrieves the result from the data source using the Nested Search Criteria. The <code>path</code> specifies 
	 * the list of objects (separated by commas) which should be used to reach the target object from the example object 
	 * passed as <code>obj</code>, or the associated object for the example object. Internally, the Nested Search Criteria 
	 * is converted into the data source specific query language. For the Object Relational Mapping based persistence 
	 * tier, the query structure is first converted into the Hibernate Query Language (HQL). Hibernate then converts the HQL into 
	 * SQL and executes it against the relational database.
	 *  
	 * The retrieved results are converted into a list which may not be completely loaded. If the retrieved results 
	 * are more than the maximum number of supported records as indicated by {@link #getMaxRecordsCount()} then the
	 * result set will be partially loaded. The client framework will execute a subsequent query (transparent to the
	 * client application) against the <code>ApplicationService</code> to load the remaining results in the chunk 
	 * no greater than value specified by {@link #getMaxRecordsCount()}.
	 * 
	 * @param path
	 * @param obj
	 * @return
	 * @throws ApplicationException
	 */
	public <E> List<E> search(String path, Object obj) throws ApplicationException {
		return appService.search(path, obj);
	}

	/**
	 * Used by the infrastructure to get next chunk of records in the result set. Use this method in conjunction with the 
	 * {@link #getMaxRecordsCount()} to determine what should be the start of next chunk.
	 *  
	 * @param criteria
	 * @param firstRow
	 * @param targetClassName
	 * @return
	 * @throws ApplicationException
	 */
	public <E> List<E> query(Object criteria, Integer firstRow, String targetClassName) throws ApplicationException {
		return appService.query(criteria, firstRow, targetClassName);
	}
	
	/**
	 * Returns the number of records that meet the search criteria. The method is used by the client framework to determine 
	 * the number of chunk of results. Use this method in conjunction with the {@link #getMaxRecordsCount()}
	 * 
	 * @param criteria
	 * @param targetClassName
	 * @return
	 * @throws ApplicationException
	 */
	public Integer getQueryRowCount(Object criteria, String targetClassName) throws ApplicationException {
		return appService.getQueryRowCount(criteria, targetClassName);
	}
	
	/**
	 * Returns the maximum number of records the <code>ApplicationService</code> has been configured to return at one time. 
	 * 
	 * @return
	 * 
	 */
	public Integer getMaxRecordsCount() {
		return appService.getMaxRecordsCount();
	}
	
	/**
	 * Retrieves an associated object for the example object specified by the <code>source</code> parameter.
	 * 
	 * @param source
	 * @param associationName
	 * @return
	 * @throws ApplicationException
	 */
	public <E> List<E> getAssociation(Object source, String associationName) throws ApplicationException {
		return appService.getAssociation(source, associationName);
	}


	/**
	 * Retrieves the result from the data source using the caGrid's CQL query. The CQL query structure is converted into the
	 * data source specific query language. For the Object Relational Mapping based persistence tier, the CQL query
	 * structure is converted in the Hibernate Query Language (HQL). Hibernate converts the HQL into SQL and executes
	 * it against the relational database. 
	 * 
	 * The retrieved results are converted into a list which may not be completely loaded. If the retrieved results 
	 * are more than the maximum number of supported records as indicated by {@link #getMaxRecordsCount()} then the
	 * result set will be partially loaded. The client framework will execute a subsequent query (transparent to the
	 * client application) against the <code>ApplicationService</code> to load the remaining results in the chunk 
	 * no greater than value specified by {@link #getMaxRecordsCount()}.
	 * 
	 * @param cqlQuery
	 * @return
	 * @throws ApplicationException
	 */
	public <E> List<E> query(gov.nih.nci.cagrid.cqlquery.CQLQuery cqlQuery) throws ApplicationException {
		return appService.query(cqlQuery);
	}	

	public SDKQueryResult executeQuery(final SDKQuery sdkQuery) throws ApplicationException {
		return appService.executeQuery(sdkQuery);
	}
	
	public List<SDKQueryResult> executeBatchQuery(final List<SDKQuery> batchOperation) throws ApplicationException {
		return appService.executeBatchQuery(batchOperation);
	}	

	public Object createObject(final Object obj) throws ApplicationException {
		final InsertExampleQuery sdkQuery = new InsertExampleQuery(obj);
		
		Object result;
		SDKQueryResult queryResult = appService.executeQuery(sdkQuery);
		result=queryResult.getObjectResult();
		return result;
	}

	public Object updateObject(Object obj) throws ApplicationException {
		final UpdateExampleQuery sdkQuery = new UpdateExampleQuery(obj);
		
		Object result;
		SDKQueryResult queryResult =  appService.executeQuery(sdkQuery);
		result=queryResult.getObjectResult();
		return result;
	}

	public void removeObject(Object obj) throws ApplicationException {
		final DeleteExampleQuery sdkQuery = new DeleteExampleQuery(obj);
		appService.executeQuery(sdkQuery);
	}
	

	public List getObjects(Object obj) throws ApplicationException {
		final SearchExampleQuery sdkQuery = new SearchExampleQuery(obj);
		final List list = new ArrayList();
		
		SDKQueryResult queryResult = appService.executeQuery(sdkQuery);
		
		if (queryResult.isObjectResult()){
			Object result = queryResult.getObjectResult();
			list.add(result);
			return list;
		}
		
		Collection resultCollection = queryResult.getCollectionResult();
		list.addAll(resultCollection);
		return list;

	}
}
