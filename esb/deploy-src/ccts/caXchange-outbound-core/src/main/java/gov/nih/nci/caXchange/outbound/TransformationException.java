/**
 * 
 */
package gov.nih.nci.caXchange.outbound;

/**
 * @author steve
 *
 */
public class TransformationException extends GridInvocationException {

    private static final long serialVersionUID = -715846006735137847L;

    /**
     * 
     */
    public TransformationException() {
    }

    /**
     * @param message
     * @param cause
     */
    public TransformationException(String message, Throwable cause) {
	super(message, cause);
    }
    /**
     * @param message
     */
    public TransformationException(String message) {
	super(message);
    }

    /**
     * @param cause
     */
    public TransformationException(Throwable cause) {
	super(cause);
    }

}
