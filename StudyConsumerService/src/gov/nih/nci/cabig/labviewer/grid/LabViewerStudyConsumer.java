package gov.nih.nci.cabig.labviewer.grid;

import gov.nih.nci.ccts.grid.IdentifierType;
import gov.nih.nci.ccts.grid.Study;
import gov.nih.nci.ccts.grid.common.StudyConsumerI;
import gov.nih.nci.ccts.grid.stubs.types.InvalidStudyException;
import gov.nih.nci.ccts.grid.stubs.types.StudyCreationException;
import gov.nih.nci.ctom.ctlab.domain.Identifier;
import gov.nih.nci.ctom.ctlab.domain.Protocol;
import gov.nih.nci.ctom.ctlab.persistence.CTLabDAO;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;

import org.apache.log4j.Logger;

public class LabViewerStudyConsumer implements StudyConsumerI
{
	Logger logger = Logger.getLogger(getClass());
	static final int MILLIS_PER_MINUTE = 60 * 1000;
	static final int THRESHOLD_MINUTE =2;
	private HashMap<String,StudyPersistTime> map = new HashMap<String,StudyPersistTime>();
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
			//After you persist the protocol, put the participant associated with the
			//protocol into a HashMap. 
			//In case of roll back, check if the participant was just persisted
			//then call roll back on that object.
			Calendar persistTime = Calendar.getInstance();
			StudyPersistTime studyPersistTime = new StudyPersistTime();
			studyPersistTime.setProtocol(protocol);
			studyPersistTime.setPersistTime(persistTime);
		    map.put(protocol.getIdentifier().getRoot(),studyPersistTime);
			//need to store the map in the application context		
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

	/* (non-Javadoc)
	 * @see gov.nih.nci.ccts.grid.common.StudyConsumerI#rollback(gov.nih.nci.ccts.grid.Study)
	 */
	public void rollback(Study study) throws RemoteException, InvalidStudyException
	{
		String studyGridId = study.getGridId();
		long epochPersistTime=0;
		long epochcurrentTime=0;
		//need to get the hashmap from the application context
		con = dao.getConnection();
		if(map.containsKey(studyGridId))
		{
		try{ 
			StudyPersistTime ppt = map.get(studyGridId);
			Calendar persistTime = ppt.getPersistTime();
			epochPersistTime = persistTime.getTime().getTime();
			Calendar currentTime = Calendar.getInstance();
			epochcurrentTime=currentTime.getTime().getTime();
			double minutes = (double)(epochcurrentTime-epochPersistTime)/MILLIS_PER_MINUTE;
			if(minutes < THRESHOLD_MINUTE)
			{	
			dao.rollbackStudy(con, ppt.getProtocol());
			}
			}
			catch(SQLException se)
			{
				logger.error("Error deleting participant", se);
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
		}
		else{
			StudyCreationException ire = new StudyCreationException();
			ire.setFaultString("Invalid patient rollback message- no patient found with given gridid");
			throw ire;
		}
		logger.info("deleted participant");
		cleanupHashMap(epochcurrentTime);
		
	}
	/**
	 * Cleans up the hash map - looks for the time stamp value in all the ParticipantPersistTime
	 * if it is difference between the current time and persist time is greater than the threshold
	 * value -then the ParticipantPersistTime is removed from the hash map.
	 */
	private void cleanupHashMap(long currentTime)
	{
		for(StudyPersistTime ppt: map.values())
		{
			
			long persistTime = ppt.getPersistTime().getTime().getTime();
			double diffTime = (double)(currentTime-persistTime)/MILLIS_PER_MINUTE;
			if(diffTime > THRESHOLD_MINUTE )
			{
				map.remove(ppt.getProtocol().getIdentifier().getRoot());
			}
		}
	}
}
