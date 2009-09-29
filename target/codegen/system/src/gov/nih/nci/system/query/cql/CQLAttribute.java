package gov.nih.nci.system.query.cql;


/**
 * CQLObject Property element used as search criteria
 * 
 * @author Satish Patel
 */
public class CQLAttribute  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

    private String name;  // attribute
    private CQLPredicate predicate;  // attribute
    private String value;  // attribute

    public CQLAttribute() {
    }

    public CQLAttribute(
           String name,
           CQLPredicate predicate,
           String value) {
           this.name = name;
           this.predicate = predicate;
           this.value = value;
    }


    /**
     * Gets the name value for this Attribute.
     * 
     * @return name
     */
    public String getName() {
        return name;
    }


    /**
     * Sets the name value for this Attribute.
     * 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * Gets the predicate value for this Attribute.
     * 
     * @return predicate
     */
    public CQLPredicate getPredicate() {
        return predicate;
    }


    /**
     * Sets the predicate value for this Attribute.
     * 
     * @param predicate
     */
    public void setPredicate(CQLPredicate predicate) {
        this.predicate = predicate;
    }


    /**
     * Gets the value value for this Attribute.
     * 
     * @return value
     */
    public String getValue() {
        return value;
    }


    /**
     * Sets the value value for this Attribute.
     * 
     * @param value
     */
    public void setValue(String value) {
        this.value = value;
    }
}
