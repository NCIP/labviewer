/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */
package gov.nih.nci.caxchange.servicemix.bean.validation;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
/**
 * Accumulates the validation errors occurring during payload validation.
 * 
 * @author marwahah
 *
 */
public class ValidatingErrorHandler implements ErrorHandler {
	
	private java.util.Set<String> errorMessages = new java.util.HashSet<String>();

	public void error(SAXParseException exception) throws SAXException {
		errorMessages.add("Line:"+exception.getLineNumber()+"Error:"+exception.getMessage());

	}

	public void fatalError(SAXParseException exception) throws SAXException {
		// TODO Auto-generated method stub
        throw new SAXException("Fatal Error.", exception); 
	}

	public void warning(SAXParseException exception) throws SAXException {
		// TODO Auto-generated method stub
		errorMessages.add("Line:"+exception.getLineNumber()+"Warning:"+exception.getMessage());
	}

	public java.util.Set<String> getErrorMessages() {
		return errorMessages;
	}

	public void setErrorMessages(java.util.Set<String> errorMessages) {
		this.errorMessages = errorMessages;
	}
	
	public PayloadValidationException getPayloadValidationException() {
		if (errorMessages.size()==0) {
			return null;
		}
        StringBuffer errorMessage = new StringBuffer();
        for(String message:errorMessages) {
        	errorMessage.append(message+"\n");
        }
        
        return new PayloadValidationException(errorMessage.toString());
	}

}
