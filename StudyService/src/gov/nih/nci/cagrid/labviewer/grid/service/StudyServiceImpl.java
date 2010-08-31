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
 * TODO:I am the service side implementation class.  IMPLEMENT AND DOCUMENT ME
 * 
 * @created by Introduce Toolkit version 1.3
 * 
 */
public class StudyServiceImpl extends StudyServiceImplBase {
    private CTLabDAO dao = new CTLabDAO();
    private Connection con;

	
	public StudyServiceImpl() throws RemoteException {
		super();
	}
	
  public gov.nih.nci.cabig.ccts.domain.Study getStudy(gov.nih.nci.cabig.ccts.domain.Registration registration) throws RemoteException {

      Logger logger = Logger.getLogger(getClass());
      logger.debug("Entering get Study Method...");
      Protocol protocol= new Protocol();
      Study study = null;
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

      try {
          con = dao.getConnection();

          protocol=dao.getStudy(con,part);
          if(protocol!=null) {
              
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
               logger.info("Found a study associated with the participant, Study Id = "+ident.getValue());  
           }
           logger.debug("Leaving get Study Method...");
      } catch (SQLException e) {
          logger.error("SQLException"+ e.getLocalizedMessage());
      }
      finally
      {
          try
          {
              logger.debug("Closing connections");
              con.close();
          }
          catch (SQLException e)
          {
              logger.error("Error closing connection", e);
          }
      }

      return study;
  }

}

