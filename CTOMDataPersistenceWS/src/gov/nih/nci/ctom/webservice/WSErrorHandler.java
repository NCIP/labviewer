package gov.nih.nci.ctom.webservice;

import gov.nih.nci.ctom.exceptions.UnSupportedMessageFormatException;
import gov.nih.nci.ctom.ws.ErrorMessage;
import gov.nih.nci.ctom.ws.datapersistence.Acknowledgement;
import gov.nih.nci.ctom.ws.datapersistence.AcknowledgementStatuses;
import gov.nih.nci.ctom.ws.datapersistence.ErrorDetails;
import gov.nih.nci.ctom.ws.datapersistence.ErrorCodes;

/**
 * Class to handle exceptions and erros and transform them into WsErrors
 * 
 * @version 1.0
 * @created 07-Jul-2005 8:35:59 AM
 */
public class WSErrorHandler {

    /**
     * 
     * Throws an ErrorDetails exception for the appropriate exception.
     * 
     * @param error
     * @throws ErrorDetails
     */
    public static void handleError(Throwable error) throws ErrorMessage {

    	
    	ErrorDetails eds = new ErrorDetails();
    	eds.setErrorDescription(error.getMessage());
    	ErrorMessage em = new ErrorMessage("An Application Error Occurred While Processing", eds);
    	
    	
        
        throw em;

    }
    
    public static Acknowledgement createErrorAcknowledgment(Throwable error) {
		
    	Acknowledgement ack = new Acknowledgement();
    	ack.setStatus(AcknowledgementStatuses.APPLICATION_ERROR);
    	ack.setDescription(error.getMessage());
    	return ack;
    	
    	
    	
    	
    }
    

    public static ErrorMessage createWsErrors(Throwable error) {

        ErrorDetails wsErrors = new ErrorDetails();
        wsErrors.setErrorDescription(error.getMessage());
    	
    	ErrorMessage em = new ErrorMessage("An Application Error Occurred While Processing", wsErrors);
    	
    	
    	return em;

    }

}