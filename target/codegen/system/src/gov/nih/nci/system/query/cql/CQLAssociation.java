package gov.nih.nci.system.query.cql;

/**
 * @author Satish Patel
 *
 */
public class CQLAssociation  extends CQLObject  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String sourceRoleName;  // attribute
    private String targetRoleName;  // attribute
    
    public CQLAssociation() {
    }

	public CQLAssociation(CQLAssociation association, CQLAttribute attribute, CQLGroup group, String name, String sourceRoleName, String targetRoleName)
	{
		super(association, attribute, group, name);
		this.sourceRoleName = sourceRoleName;
		this.targetRoleName = targetRoleName;
	}

	public String getSourceRoleName()
	{
		return sourceRoleName;
	}

	public void setSourceRoleName(String sourceRoleName)
	{
		this.sourceRoleName = sourceRoleName;
	}

	public String getTargetRoleName()
	{
		return targetRoleName;
	}

	public void setTargetRoleName(String targetRoleName)
	{
		this.targetRoleName = targetRoleName;
	}
}
