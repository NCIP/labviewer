/**
 * 
 */
package gov.nih.nci.caXchange.outbound;

/**
 * This exception is raised during execution of the grid invocation strategy.
 * @author steve
 */
public class GridInvocationException extends Exception {

    private static final long serialVersionUID = -1562208594511382570L;
    
    private boolean canRetry;

    /**
     * Constructor
     */
    public GridInvocationException() {
	super();
    }

    /**
     * Constructor
     * @param message
     * @param cause
     */
    public GridInvocationException(String message, Throwable cause) {
	super(message, cause);
    }

    /**
     * Constructor
     * @param message
     */
    public GridInvocationException(String message) {
	super(message);
    }

    /**
     * Constructor
     * @param cause
     */
    public GridInvocationException(Throwable cause) {
	super(cause);
    }
    
    /**
     * Returns true if a grid invocation can be retried because of this
     * exception.
     * 
     * @return
     */
    public boolean getCanRetry() {
	return canRetry;
    }
    
    /**
     * Sets if a grid invocation can be retried because of this
     * exception.
     * 
     * @return
     */
    public void setCanRetry(boolean canRetry) {
	this.canRetry = canRetry;
    }

}
