package gov.nih.nci.caxchange.client.exceptions;

public class FolderDoesNotExistException extends java.lang.Exception{
	
	String ErrorMessage;

	public FolderDoesNotExistException(String ErrorMessage)
	{
		this.ErrorMessage = ErrorMessage;
	}
	
	public String  getErrorMessage()
	{
		return this.ErrorMessage;
	}
}