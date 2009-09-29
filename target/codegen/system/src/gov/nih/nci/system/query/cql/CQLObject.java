package gov.nih.nci.system.query.cql;


/**
 * Associated CQLObject used as search criteria
 * 
 * @author Satish Patel
 */
public class CQLObject  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

    private CQLAttribute attribute;
    private CQLAssociation association;
    private CQLGroup group;
    private java.lang.String name;  // attribute

    public CQLObject() {
    }

    public CQLObject(
           CQLAssociation association,
           CQLAttribute attribute,
           CQLGroup group,
           java.lang.String name) {
           this.attribute = attribute;
           this.association = association;
           this.group = group;
           this.name = name;
    }


    /**
     * Gets the attribute value for this CQLObject.
     * 
     * @return attribute
     */
    public CQLAttribute getAttribute() {
        return attribute;
    }


    /**
     * Sets the attribute value for this CQLObject.
     * 
     * @param attribute
     */
    public void setAttribute(CQLAttribute attribute) {
        this.attribute = attribute;
    }


    /**
     * Gets the association value for this CQLObject.
     * 
     * @return association
     */
    public CQLAssociation getAssociation() {
        return association;
    }


    /**
     * Sets the association value for this CQLObject.
     * 
     * @param association
     */
    public void setAssociation(CQLAssociation association) {
        this.association = association;
    }


    /**
     * Gets the group value for this CQLObject.
     * 
     * @return group
     */
    public CQLGroup getGroup() {
        return group;
    }


    /**
     * Sets the group value for this CQLObject.
     * 
     * @param group
     */
    public void setGroup(CQLGroup group) {
        this.group = group;
    }


    /**
     * Gets the name value for this CQLObject.
     * 
     * @return name
     */
    public java.lang.String getName() {
        return name;
    }


    /**
     * Sets the name value for this CQLObject.
     * 
     * @param name
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }
}
