package gov.nih.nci.system.query.hibernate;

import java.io.Serializable;
import java.util.List;

/**
 * @author Satish Patel
 *
 */
public class HQLCriteria implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String hqlString;
	private String countHqlString;
	private List parameters;	
	
	public HQLCriteria(String hqlString)
	{
		this.hqlString = hqlString;
	}
	
	public HQLCriteria(String hqlString, List parameters)
	{
		this.hqlString = hqlString;
		this.parameters = parameters;
	}	

	public HQLCriteria(String hqlString, String countHqlString, List parameters)
	{
		this.hqlString = hqlString;
		this.parameters = parameters;
		this.countHqlString = countHqlString;
	}	

	public String getHqlString()
	{
		return this.hqlString;
	}

	public List getParameters() {
		return parameters;
	}

	public void setHqlString(String hqlString)
	{
		this.hqlString = hqlString;
	}

	public void setParameters(List parameters) {
		this.parameters = parameters;
	}

	public String getCountHqlString() {
		return countHqlString;
	}

	public void setCountHqlString(String countHqlString) {
		this.countHqlString = countHqlString;
	}
	
}
