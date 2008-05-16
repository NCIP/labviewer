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
     * Default constructor
     */
    public TransformationException() {
    }

    /**
     * Parameterized constructor
     * @param message
     * @param cause
     */
    public TransformationException(String message, Throwable cause) {
	super(message, cause);
    }
    /**
     * Parameterized constructor
     * @param message
     */
    public TransformationException(String message) {
	super(message);
    }

    /**
     * Parameterized constructor
     * @param cause
     */
    public TransformationException(Throwable cause) {
	super(cause);
    }

}
