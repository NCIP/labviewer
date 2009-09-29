package gov.nih.nci.system.query.nestedcriteria;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 * This class holds the nested criteria in the linked list format
 * 
 * @author Satish Patel
 */
public class NestedCriteria implements Serializable{

	/**
	 * Default serial version ID
	 */
	private static final long serialVersionUID = 1L;
	private String targetObjectName;
	private String sourceObjectName;
	private String roleName;
	private boolean targetCollection;
	private String sourceRoleName; 
	private boolean sourceCollection;
	
	private List sourceObjectList = new ArrayList();

	private NestedCriteria internalNestedCriteria;

	public void setTargetObjectName(String targetName)
	{
		this.targetObjectName = targetName;
	}

	public String getTargetObjectName()
	{
		return this.targetObjectName;
	}

	public void setSourceObjectName(String sourceName)
	{
		this.sourceObjectName = sourceName;
	}

	public String getSourceName()
	{
		return this.sourceObjectName;
	}

	public void setRoleName(String roleName)
	{
		this.roleName = roleName;
	}

	public String getRoleName()
	{
		return this.roleName;
	}

	public void setInternalNestedCriteria(NestedCriteria nestedCriteria)
	{
		this.internalNestedCriteria = nestedCriteria;
	}

	public NestedCriteria getInternalNestedCriteria()
	{
		return this.internalNestedCriteria;
	}

	public void setSourceObjectList(List objList)
	{
		this.sourceObjectList = objList;
	}

	public List getSourceObjectList()
	{
		return this.sourceObjectList;
	}

	public void addSourceObject(Object obj)
	{
		sourceObjectList.add(obj);
	}

	public boolean isTargetCollection()
	{
		return targetCollection;
	}

	public void setTargetCollection(boolean targetCollection)
	{
		this.targetCollection = targetCollection;
	}

	public boolean isSourceCollection() {
		return sourceCollection;
	}

	public void setSourceCollection(boolean sourceCollection) {
		this.sourceCollection = sourceCollection;
	}

	public String getSourceRoleName() {
		return sourceRoleName;
	}

	public void setSourceRoleName(String sourceRoleName) {
		this.sourceRoleName = sourceRoleName;
	}

	public String getSourceObjectName() {
		return sourceObjectName;
	}

	public NestedCriteria(String sourceObjectName, String targetObjectName, String roleName, NestedCriteria internalNestedCriteria)
	{
		super();
		this.sourceObjectName = sourceObjectName;
		this.targetObjectName = targetObjectName;
		this.roleName = roleName;
		this.internalNestedCriteria = internalNestedCriteria;
	}

	public NestedCriteria() {

	}
}

