package gov.nih.nci.system.query.cql;

/**
 * @author Satish Patel
 *
 */
public class CQLQuery  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

    /** Defines the target data type of a CQL query */
    private CQLObject target;

    public CQLQuery() {
    }


    public CQLQuery(CQLObject target)
	{
		super();
		this.target = target;
	}


	/**
     * Gets the target value for this CQLQuery.
     * 
     * @return target Defines the target data type of a CQL query
     */
    public CQLObject getTarget() {
        return target;
    }


    /**
     * Sets the target value for this CQLQuery.
     * 
     * @param target Defines the target data type of a CQL query
     */
    public void setTarget(CQLObject target) {
        this.target = target;
    }
}
