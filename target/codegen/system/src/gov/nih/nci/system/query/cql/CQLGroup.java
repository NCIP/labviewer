package gov.nih.nci.system.query.cql;

import java.util.ArrayList;
import java.util.Collection;


/**
 * Binary joint
 * @author Satish Patel
 */
public class CQLGroup  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

    private Collection<CQLAssociation> associationCollection;
    private Collection<CQLAttribute> attributeCollection;
    private Collection<CQLGroup> groupCollection;
    private CQLLogicalOperator logicOperator;  // attribute

    public CQLGroup() {
    }

    public CQLGroup(Collection<CQLAssociation> associationCollection, Collection<CQLAttribute> attributeCollection, Collection<CQLGroup> groupCollection, CQLLogicalOperator logicOperator)
	{
		super();
		this.associationCollection = associationCollection;
		this.attributeCollection = attributeCollection;
		this.groupCollection = groupCollection;
		this.logicOperator = logicOperator;
	}


	public Collection<CQLAssociation> getAssociationCollection() {
		return associationCollection;
	}

	public void setAssociationCollection(
			Collection<CQLAssociation> associationCollection) {
		this.associationCollection = associationCollection;
	}

	public Collection<CQLAttribute> getAttributeCollection() {
		return attributeCollection;
	}

	public void setAttributeCollection(Collection<CQLAttribute> attributeCollection) {
		this.attributeCollection = attributeCollection;
	}

	public Collection<CQLGroup> getGroupCollection() {
		return groupCollection;
	}

	public void setGroupCollection(Collection<CQLGroup> groupCollection) {
		this.groupCollection = groupCollection;
	}

	public CQLLogicalOperator getLogicOperator() {
		return logicOperator;
	}

	public void setLogicOperator(CQLLogicalOperator logicOperator) {
		this.logicOperator = logicOperator;
	}

	public void addAssociation(CQLAssociation association)
	{
		if(association == null) return;
		if(this.associationCollection == null)
			associationCollection = new ArrayList<CQLAssociation>();
		associationCollection.add(association);
	}

	public void addGroup(CQLGroup group)
	{
		if(group == null) return;
		if(this.groupCollection == null)
			groupCollection = new ArrayList<CQLGroup>();
		groupCollection.add(group);
	}
	
	public void addAttribute(CQLAttribute attribute)
	{
		if(attribute == null) return;
		if(this.attributeCollection == null)
			attributeCollection = new ArrayList<CQLAttribute>();
		attributeCollection.add(attribute);	
	}
}
