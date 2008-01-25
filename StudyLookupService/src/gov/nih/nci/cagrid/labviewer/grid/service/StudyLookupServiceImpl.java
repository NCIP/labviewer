package gov.nih.nci.cagrid.labviewer.grid.service;

import gov.nih.nci.cabig.ccts.domain.IdentifierType;
import gov.nih.nci.cabig.ccts.domain.OrganizationAssignedIdentifierType;
import gov.nih.nci.cabig.ccts.domain.ParticipantType;
import gov.nih.nci.cabig.ccts.domain.Study;
import gov.nih.nci.ctom.ctlab.domain.Identifier;
import gov.nih.nci.ctom.ctlab.domain.Participant;
import gov.nih.nci.ctom.ctlab.domain.Protocol;
import gov.nih.nci.ctom.ctlab.persistence.CTLabDAO;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;

/** 
 * 
 * The StudyLookupServiceImpl is the implementation of the StudyLookup grid service.
 * It accepts participant information and gets the study associated with the participant. 
 *    
 * @created by asharma
 * 
 */
public class StudyLookupServiceImpl extends StudyLookupServiceImplBase {
	
	Logger logger = Logger.getLogger(getClass());
	private CTLabDAO dao = new CTLabDAO();
	private Connection con;
	
	
	public StudyLookupServiceImpl() throws RemoteException
	{
		super();
		
	}
	
	
	/* (non-Javadoc)
	 * @see gov.nih.nci.cagrid.labviewer.grid.common.StudyLookupServiceI#getStudy(gov.nih.nci.cabig.ccts.domain.Registration)
	 */
	public gov.nih.nci.cabig.ccts.domain.Study getStudy(gov.nih.nci.cabig.ccts.domain.Registration registration)
  	throws RemoteException
  {
		logger.info("StudyLookupService getStudy method called");
		logger.info("Received the registration message from Cancer Center Client");
		
		// Retrieve participant data from registration object
		ParticipantType participant = registration.getParticipant();
		Participant part = new Participant();
		// Assume that only one identifier was sent and use that
		IdentifierType[] ids = participant.getIdentifier();
		IdentifierType id = null;
		Identifier partIdent = new Identifier();
		if (ids != null && ids.length > 0)
		{
			id = ids[0];
			partIdent.setExtension(id.getValue());
			partIdent.setRoot(participant.getGridId());
			part.setIdentifier(partIdent);
		}
		//Get the connection object and call the DAO getStudy method
		con = dao.getConnection();
		Protocol protocol= new Protocol();
		Study study = null;
				try {
					protocol=dao.getStudy(con,part);
				} catch (SQLException e) {
					
					logger.error("SQLException"+ e.getLocalizedMessage());
				}
		if(protocol!=null)
		{
		 logger.info("Found a study associated with the participant");	
		 study = new Study();
		 //Use the Protocol object returned by getStudy to populate the Study object.		
		 study.setLongTitleText(protocol.getLongTxtTitle());
		 OrganizationAssignedIdentifierType ident = new OrganizationAssignedIdentifierType();
		 //Assuming there is only one identifier
		 ident.setValue(protocol.getIdentifier().getExtension());
		 ident.setSource(protocol.getIdentifier().getSource());
		 ident.setGridId(protocol.getIdentifier().getRoot());
		 OrganizationAssignedIdentifierType [] identifier = {ident};
		 study.setIdentifier(identifier);
		}
		return study;
	}
}

