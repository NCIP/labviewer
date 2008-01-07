package gov.nih.nci.caxchange.client.exceptions;

public class PropertiesFileException extends java.lang.Exception{
	
	String ErrorMessage;

	public PropertiesFileException(String ErrorMessage)
	{
		this.ErrorMessage = ErrorMessage;
	}
	
	public String  getErrorMessage()
	{
		return this.ErrorMessage;
	}
}