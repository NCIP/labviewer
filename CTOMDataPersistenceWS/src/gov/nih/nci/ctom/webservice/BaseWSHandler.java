/*
 * BaseWSHandler.java
 * Created on Jul 8, 2005
 */
package gov.nih.nci.ctom.webservice;



import gov.nih.nci.ctom.ws.ErrorMessage;
import gov.nih.nci.ctom.ws.datapersistence.Acknowledgement;
import gov.nih.nci.ctom.ws.datapersistence.AcknowledgementStatuses;




/**
 * TODO Add comments.
 * @author griffinch
 */
public abstract class BaseWSHandler {

    
    protected Acknowledgement createAcknowledgementResponse(String ackStatus) {
        
     	Acknowledgement ack = new Acknowledgement();
    	ack.setStatus(AcknowledgementStatuses.fromValue(ackStatus));
    	
    	return ack;
        
    }

    protected Acknowledgement createApplicationErrorAcknowledgement(Throwable error){
    	
    	
    	return WSErrorHandler.createErrorAcknowledgment(error);
    }
    
    protected ErrorMessage createWsErrors(Throwable error) {
        return WSErrorHandler.createWsErrors(error);  
    }


}
