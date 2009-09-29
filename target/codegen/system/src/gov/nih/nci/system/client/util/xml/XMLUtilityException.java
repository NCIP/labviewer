package gov.nih.nci.system.client.util.xml;

public class XMLUtilityException extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Exception rootException;

    public XMLUtilityException(String message, Throwable cause) {
        super(message);
        rootException = (Exception)cause;
    }

    /**
     * Returns the root exception that caused the exception
     * in the xml serialization/deserialization routing.
     * Can also return the Exception from the underlying xml
     * serialization library.
     * @return root exception
     */
    public Exception getRootException() {
        return rootException;
    }

}
