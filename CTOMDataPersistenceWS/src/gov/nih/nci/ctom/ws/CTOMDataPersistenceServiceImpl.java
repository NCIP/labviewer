
package gov.nih.nci.ctom.ws;

import javax.jws.WebService;

import org.apache.log4j.Logger;

import gov.nih.nci.ctom.webservice.WSErrorHandler;
import gov.nih.nci.ctom.webservice.WSRequestProcessor;
import gov.nih.nci.ctom.webservice.WsRequestConstants;
import gov.nih.nci.ctom.ws.datapersistence.Acknowledgement;
import gov.nih.nci.ctom.ws.datapersistence.AcknowledgementStatuses;
import gov.nih.nci.ctom.ws.datapersistence.ErrorCodes;
import gov.nih.nci.ctom.ws.datapersistence.ErrorDetails;
import gov.nih.nci.ctom.ws.datapersistence.PersistDataRequest;

@WebService(serviceName = "CTOMDataPersistenceService", targetNamespace = "http://ctom.nci.nih.gov/ws", endpointInterface = "aa.CTOMDataPersistenceService")
public class CTOMDataPersistenceServiceImpl
    implements CTOMDataPersistenceService
{

    Logger logger = Logger.getLogger(getClass());
    
    public Acknowledgement persistData(PersistDataRequest CTOMPersistDataRequest)
        throws ErrorMessage
    {
    	
    	logger.debug("Received persist data request");
    	System.out.println("***** Right below debug");
    	return (Acknowledgement) handleRequest(CTOMPersistDataRequest, WsRequestConstants.PersistDataRequest);	
		
	}

	public Object handleRequest(Object request, String key) throws ErrorMessage {

		try {

			if (isAuthorised(request)) {

				return WSRequestProcessor.processWS(key, request);
			} else {
				logger.debug("Unauthorized User");
				return  createUnauthorizedError("User is not authorized to execute the operation.");		
			}
		}catch (ErrorMessage e) {
			throw e;
		} catch (Throwable error) {
			//log.fatal(error.getMessage(), error);
			WSErrorHandler.handleError(error);
		}
		return null;
	}

	private Acknowledgement createUnauthorizedError(String message) {

		Acknowledgement ack = new Acknowledgement();
		ack.setStatus(AcknowledgementStatuses.AUTHORIZATION_FAILURE);
		ack.setDescription(message);
		return ack;
	}

	private boolean isAuthorised(Object request) {
		// TODO Auto-generated method stub
		return true;
	}

}
