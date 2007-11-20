package gov.nih.nci.cabig.labviewer.grid;

import gov.nih.nci.ccts.grid.HealthcareSiteType;
import gov.nih.nci.ccts.grid.IdentifierType;
import gov.nih.nci.ccts.grid.Study;
import gov.nih.nci.ccts.grid.common.StudyConsumerI;
import gov.nih.nci.ccts.grid.stubs.types.InvalidStudyException;
import gov.nih.nci.ccts.grid.stubs.types.StudyCreationException;
import gov.nih.nci.ctom.ctlab.domain.HealthCareSite;
import gov.nih.nci.ctom.ctlab.domain.Identifier;
import gov.nih.nci.ctom.ctlab.domain.Protocol;
import gov.nih.nci.ctom.ctlab.persistence.CTLabDAO;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;

public class LabViewerStudyConsumer implements StudyConsumerI
{
	Logger logger = Logger.getLogger(getClass());
	private CTLabDAO dao = new CTLabDAO();
	private Connection con;
	
	public void commit(Study study) throws RemoteException, InvalidStudyException
	{
		// TODO Auto-generated method stub
		
	}

	public void createStudy(Study study) throws RemoteException, InvalidStudyException, StudyCreationException
	{
		logger.info("Create Study message received");
			
		// save the study data
		Protocol protocol = new Protocol();
		protocol.setLongTxtTitle(study.getLongTitleText());
		protocol.setShortTxtTitle(study.getShortTitleText());
	   	IdentifierType identifiers[] = study.getIdentifier();
	   	
        // save the identifier data	
		for(IdentifierType ident: identifiers)
		{
			if (ident.getPrimaryIndicator())
			{
				Identifier id = new Identifier();
				id.setExtension(ident.getValue());
				id.setSource(ident.getSource());
				id.setRoot(study.getGridId());
				protocol.setIdentifier(id);
				protocol.setNciIdentifier(id.getRoot() + "." + id.getExtension());
			}
		}
		
		// save the study site data
		/*HealthcareSiteType hcsType = study.getStudyOrganization(0).getHealthcareSite(0);
		HealthCareSite healthCare = new HealthCareSite();
		String tmpstr = hcsType.getGridId()+"."+hcsType.getNciInstituteCode();
		healthCare.setNciInstituteCd(tmpstr);*/
		
		protocol.setHealthCareSite(null);
		protocol.setInvestigator(null);
		con = dao.getConnection();
		logger.info("Create Study message validated");
		try
		{
			dao.saveProtocol(con, protocol);
			logger.info("Persisted the study");
		}
		catch (SQLException e)
		{
			logger.error("Error creating study", e);
			StudyCreationException rce = new StudyCreationException();
			rce.setFaultString(e.getMessage());
			throw rce;
		}
		catch (Exception e)
		{
			logger.error("Error craeting study", e);
			StudyCreationException rce = new StudyCreationException();
			rce.setFaultString(e.getMessage());
			throw rce;
		}
		finally
		{
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				logger.error("Error closing connection",e);
			}
		}
		
		logger.info("Study created");
		
		//return study;
	
		
	}

	public void rollback(Study study) throws RemoteException, InvalidStudyException
	{
		// TODO Auto-generated method stub
		
	}
}
