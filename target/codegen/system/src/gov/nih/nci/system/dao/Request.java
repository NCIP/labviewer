package gov.nih.nci.system.dao;

import gov.nih.nci.system.util.ClassCache;

import java.util.Hashtable;
/**
 * Provides various methods to submit a query request to a specified datasource
 * @author Satish Patel 
 */
public class Request implements java.io.Serializable
{
	
	private static final long serialVersionUID = 1234567890L;
	private Object request;
	private String domainObjectName;
	private Hashtable config;
	private Boolean isCount;
//	private Boolean caseSensitivity;
	private Integer firstRow;
//	private Integer recordsCount;
	private ClassCache classCache;
	
	/**
	 * Creates a Request instance
	 */
	public Request()
	{
	}	
	
	/**
	 * Creates a Request instance for a given object
	 * @param request
	 */
	public Request(Object request)
	{
		this.request=request;
	}

	/**
	 * Sets the value for this request object
	 * @param request 	Specifies the request object	 
	 */
	public void setRequest(Object request)
	{
		this.request=request;
	}
	
	/**
	 * Returns a value for this request object
	 * @return 	returns a request object
	 */
	public Object getRequest()
	{
		return request;
	}
	
	/**
	 * Sets the name for this domain object
	 * @param domainObjectName 	Specifies the domain object name
	 */
	public void setDomainObjectName(String domainObjectName)
	{
		this.domainObjectName=domainObjectName;
	}
	
	/**
	 * Returns the domain object name of this request
	 * @return returns the name of this domain object
	 */
	public String getDomainObjectName()
	{
		return domainObjectName;
	}
	
	/**
	 * Sets the configuration value
	 * @param config	Specifies the configuration values
	 */
	
	public void setConfig(Hashtable config)
	{
		this.config = config;
		
	}
	
	/**
	 * Returns the configuration values for this request
	 * @return Returns the configuration values
	 */
	public Hashtable getConfig()
	{
		return config;
	}
	/**
	 * Set firstRow value
	 * @param firstRow
	 */
	public void setFirstRow(Integer firstRow)
	{
	    this.firstRow = firstRow;
	}
	/**
	 * Return the firstRow value
	 * @return firstRow
	 */
	public Integer getFirstRow()
	{
	    return this.firstRow;
	}
//	/**
//	 * Set recordsCount value
//	 * @param rc
//	 */
//	public void setRecordsCount(Integer rc)
//	{
//	    this.recordsCount = rc;
//	}
//	/**
//	 * 
//	 * @return recordsCount value
//	 */
//	public Integer getRecordsCount()
//	{
//	    return this.recordsCount;
//	}

	/**
	 * Return isCount value
	 * @return isCount
	 */
	public Boolean getIsCount()
	{
	    return this.isCount;
	}
	/**
	 * Set isCount value
	 * @param b
	 */
	public void setIsCount(Boolean b)
	{
	    this.isCount = b;
	}

	public ClassCache getClassCache() {
		return classCache;
	}

	public void setClassCache(ClassCache classCache) {
		this.classCache = classCache;
	}

//	public Boolean getCaseSensitivity()
//	{
//		return caseSensitivity;
//	}
//
//	public void setCaseSensitivity(Boolean caseSensitivity)
//	{
//		this.caseSensitivity = caseSensitivity;
//	}
	
}