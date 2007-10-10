package gov.nih.nci.ctom.webservice;


import gov.nih.nci.ctom.applicationservice.CTOMCTLabMessagePersistenceManager;
import gov.nih.nci.ctom.applicationservice.CTOMPersistenceManager;
import gov.nih.nci.ctom.ws.ErrorMessage;
import gov.nih.nci.ctom.ws.datapersistence.AcknowledgementStatuses;

public class PersistDataWSHandler extends BaseWSHandler implements WSRequestHandler {

	public Object handleWSRequest(Object request)  throws ErrorMessage {


		try { 
			CTOMPersistenceManager mgr = new CTOMCTLabMessagePersistenceManager();
			mgr.saveData(request);
			
		
		} catch (Exception ex){
			ex.printStackTrace();
			return createApplicationErrorAcknowledgement(ex); 
		}
		 
		return createAcknowledgementResponse(AcknowledgementStatuses.MESSAGE_PERSISTED.value());
				
		
	}

}
