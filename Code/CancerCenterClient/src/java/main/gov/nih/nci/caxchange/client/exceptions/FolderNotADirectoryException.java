package gov.nih.nci.caxchange.client.exceptions;

public class FolderNotADirectoryException extends java.lang.Exception{
	String ErrorMessage;
	
	public FolderNotADirectoryException(String ErrorMessage)
	{
		this.ErrorMessage = ErrorMessage;
	}
	
	
	public String  getErrorMessage()
	{
		return this.ErrorMessage;
	}

}
