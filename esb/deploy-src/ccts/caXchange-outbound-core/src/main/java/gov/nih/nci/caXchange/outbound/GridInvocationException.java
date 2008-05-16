/**
 * 
 */
package gov.nih.nci.caXchange.outbound;

/**
 * This class contains exception that are  raised during execution of the grid invocation strategy.
 * @author steve
 */
public class GridInvocationException extends Exception {

    private static final long serialVersionUID = -1562208594511382570L;
    
    private boolean canRetry;

    /**
     * Default Constructor
     */
    public GridInvocationException() {
	super();
    }

    /**
     * Constructor
     * @param message
     * @param cause
     * @return
     * @throws
     */
    public GridInvocationException(String message, Throwable cause) {
	super(message, cause);
    }

    /**
     * Constructor
     * @param message
     * @return
     * @throws
     */
    public GridInvocationException(String message) {
	super(message);
    }

    /**
     * Constructor
     * @param cause
     * @return
     * @throws
     */
    public GridInvocationException(Throwable cause) {
	super(cause);
    }
    
    /**
     * Returns true if a grid invocation can be retried because of this
     * exception.
     * @param
     * @return canRetry
     * @throws
     */
    public boolean getCanRetry() {
	return canRetry;
    }
    
    /**
     * This method sets if a grid invocation can be 
     * retried because of this
     * exception.
     * @param canRetry
     * @return
     * @throws
     */
    public void setCanRetry(boolean canRetry) {
	this.canRetry = canRetry;
    }

}
