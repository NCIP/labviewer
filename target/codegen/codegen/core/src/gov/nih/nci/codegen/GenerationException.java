package gov.nih.nci.codegen;

public class GenerationException extends Exception
{

	/**
	 * Default serial version ID
	 */
	private static final long serialVersionUID = 1L;

	
	public GenerationException(String message, Exception e)
	{
		super(message,e);
	}


	public GenerationException(String message) {
		super(message);
	}
	
}