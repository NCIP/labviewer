package gov.nih.nci.ctom.webservice;



import gov.nih.nci.ctom.ws.ErrorMessage;
import gov.nih.nci.ctom.ws.datapersistence.ErrorDetails;



/**
 * Interface to that defines describe classes that will handle web service 
 * operations.
 * 
 * @author griffinch
 * @version 1.0
 * @created 07-Jul-2005 8:35:59 AM
 */
public interface WSRequestHandler {

	/**
	 * Method to process the web service request and build and return 
	 * the reply.
	 * 
	 * @param request
	 * 
	 */
    public java.lang.Object handleWSRequest(Object request) throws ErrorMessage;
    
}

