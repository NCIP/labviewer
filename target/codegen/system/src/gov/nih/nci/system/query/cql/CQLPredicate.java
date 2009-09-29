package gov.nih.nci.system.query.cql;

/**
 * @author Satish Patel
 *
 */
public class CQLPredicate implements java.io.Serializable {

	private static final long serialVersionUID = 1L;


	private String value;
	
    // Constructor
    private CQLPredicate(String value) {
        this.value = value;
    }

    
    public static final CQLPredicate EQUAL_TO = new CQLPredicate("EQUAL_TO");
    public static final CQLPredicate NOT_EQUAL_TO = new CQLPredicate("NOT_EQUAL_TO");
    public static final CQLPredicate LIKE = new CQLPredicate("LIKE");
    public static final CQLPredicate IS_NULL = new CQLPredicate("IS_NULL");
    public static final CQLPredicate IS_NOT_NULL = new CQLPredicate("IS_NOT_NULL");
    public static final CQLPredicate LESS_THAN = new CQLPredicate("LESS_THAN");
    public static final CQLPredicate LESS_THAN_EQUAL_TO = new CQLPredicate("LESS_THAN_EQUAL_TO");
    public static final CQLPredicate GREATER_THAN = new CQLPredicate("GREATER_THAN");
    public static final CQLPredicate GREATER_THAN_EQUAL_TO = new CQLPredicate("GREATER_THAN_EQUAL_TO");
    public String getValue() { return value;}

    public boolean equals(Object obj) {
    	if(obj!=null && obj instanceof CQLPredicate)
    		return ((CQLPredicate)obj).getValue().equals(value);
    	else
    		return false;
    }
    public int hashCode() { return toString().hashCode();}
    public String toString() { return value;}

}
