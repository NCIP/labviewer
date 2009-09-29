package gov.nih.nci.system.query.nestedcriteria;

import java.io.Serializable;
import java.util.List;

/**
 * @author Satish Patel
 *
 */
public class NestedCriteriaPath implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String pathString;
	private List parameters;	
	
	public NestedCriteriaPath(String pathString)
	{
		this.pathString = pathString;
	}
	
	public NestedCriteriaPath(String pathString, List parameters)
	{
		this.pathString = pathString;
		this.parameters = parameters;
	}	

	public String getpathString()
	{
		return this.pathString;
	}
	
	public void setPathString(String pathString) {
		this.pathString = pathString;
	}
	
	public List getParameters() {
		return parameters;
	}
	
	public void setParameters(List parameters) {
		this.parameters = parameters;
	}

}
