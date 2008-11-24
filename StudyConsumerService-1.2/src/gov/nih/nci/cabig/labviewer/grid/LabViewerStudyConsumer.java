package gov.nih.nci.cabig.labviewer.grid;

import gov.nih.nci.cabig.ccts.domain.HealthcareSiteType;
import gov.nih.nci.cabig.ccts.domain.IdentifierType;
import gov.nih.nci.cabig.ccts.domain.OrganizationAssignedIdentifierType;
import gov.nih.nci.cabig.ccts.domain.Study;
import gov.nih.nci.cabig.ccts.domain.StudyInvestigatorType;
import gov.nih.nci.caxchange.ctom.viewer.util.LabViewerAuthorizationHelper;
import gov.nih.nci.ccts.grid.studyconsumer.common.StudyConsumerI;
import gov.nih.nci.ccts.grid.studyconsumer.service.globus.StudyConsumerAuthorization;
import gov.nih.nci.ccts.grid.studyconsumer.stubs.types.InvalidStudyException;
import gov.nih.nci.ccts.grid.studyconsumer.stubs.types.StudyCreationException;
import gov.nih.nci.ctom.ctlab.domain.HealthCareSite;
import gov.nih.nci.ctom.ctlab.domain.Identifier;
import gov.nih.nci.ctom.ctlab.domain.Investigator;
import gov.nih.nci.ctom.ctlab.domain.Protocol;
import gov.nih.nci.ctom.ctlab.domain.ProtocolStatus;
import gov.nih.nci.ctom.ctlab.persistence.CTLabDAO;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import javax.xml.namespace.QName;

import org.apache.log4j.Logger;
import org.oasis.wsrf.properties.GetMultipleResourcePropertiesResponse;
import org.oasis.wsrf.properties.GetMultipleResourceProperties_Element;
import org.oasis.wsrf.properties.GetResourcePropertyResponse;
import org.oasis.wsrf.properties.QueryResourcePropertiesResponse;
import org.oasis.wsrf.properties.QueryResourceProperties_Element;

public class LabViewerStudyConsumer implements StudyConsumerI {
	Logger logger = Logger.getLogger(getClass());
	static final int MILLIS_PER_MINUTE = 60 * 1000;
	static final int THRESHOLD_MINUTE = 1;
	//private HashMap<String,StudyPersistTime> map = new HashMap<String,StudyPersistTime>();
	private CTLabDAO dao = new CTLabDAO();
	private Connection con;

	public void commit(Study study) throws RemoteException,
			InvalidStudyException {
		// TODO Auto-generated method stub

	}

	public GetMultipleResourcePropertiesResponse getMultipleResourceProperties(
			GetMultipleResourceProperties_Element params)
			throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	public GetResourcePropertyResponse getResourceProperty(QName params)
			throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	public QueryResourcePropertiesResponse queryResourceProperties(
			QueryResourceProperties_Element params) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	public void createStudy(Study study) throws RemoteException,
			InvalidStudyException, StudyCreationException {
		logger.info("Create Study message received");

		// Authorization code 
		String username = StudyConsumerAuthorization.getCallerIdentity();
		String user="";
		if (username == null)
		{
			logger.error("Error saving Study no username provided");
			StudyCreationException rce = new StudyCreationException();
			rce.setFaultString("No user credentials provided");
			throw rce;
		}
		else
		{
			System.out.println("User who called was " + username);
			
			LabViewerAuthorizationHelper lvaHelper = new LabViewerAuthorizationHelper();
			if(username!=null){
				int beginIndex = username.lastIndexOf("=");
				int endIndex = username.length();
				user = username.substring(beginIndex+1, endIndex);
				}
			boolean authorized = lvaHelper.isAuthorized(user);
			
			if (!authorized)
			{
				logger.error("Error saving Study");
				StudyCreationException rce = new StudyCreationException();
				rce.setFaultString("User "+ username +" not authorized for this operation");
				throw rce;
			}
			else
			{
		 java.util.Date now = new Date();
		// save the study data
		 logger.info("payload has Study information");
		Protocol protocol = new Protocol();
		protocol.setLongTxtTitle(study.getLongTitleText());
		protocol.setShortTxtTitle(study.getShortTitleText());
		protocol.setPhaseCode(study.getPhaseCode());
		if(study.getCoordinatingCenterStudyStatus()!=null){
		ProtocolStatus protocolStatus = new ProtocolStatus();
		protocolStatus.setCtom_insert_date(now);
		protocolStatus.setStatus_code(study.getCoordinatingCenterStudyStatus().getValue());
		protocol.setStatus(protocolStatus);
		}else{
			protocol.setStatus(null);
		}
		protocol.setCtomInsertDt(now);
		IdentifierType identifiers[] = study.getIdentifier();

		// save the identifier data	
		//IdentifierType ident: identifiers
		String assignedBy="";
		for (int i = 0; i < identifiers.length; i++) {
			IdentifierType ident = identifiers[i];

			if (ident.getPrimaryIndicator().booleanValue()) {
				Identifier id = new Identifier();
				if (ident instanceof OrganizationAssignedIdentifierType)
					 assignedBy = "organization";
				else
					 assignedBy = "system";
				id.setExtension(ident.getValue());
				id.setSource(ident.getSource());
				id.setRoot(study.getGridId());
				id.setAssigningAuthorityName(assignedBy);
				protocol.setIdentifier(id);
				protocol.setNciIdentifier(id.getRoot() + "."
						+ id.getExtension());
			}
		}

		// save the study site data
		if(study.getStudyOrganization()!=null)
		{
		  if(study.getStudyOrganization(0).getHealthcareSite()!=null)
			{
			  	logger.info("payload has HealthcareSite information");
				HealthcareSiteType hcsType = study.getStudyOrganization(0).getHealthcareSite(0);
				HealthCareSite healthCare = new HealthCareSite();
				healthCare.setNciInstituteCd(hcsType.getNciInstituteCode());
				healthCare.setName(hcsType.getName());
				healthCare.setCtomInsertDt(now);
				protocol.setHealthCareSite(healthCare);
			 }else{
				 logger.info("payload has no HealthcareSite information");
				  protocol.setHealthCareSite(null);
			 }
		  if(study.getStudyOrganization(0).getStudyInvestigator()!=null)
		  {
			  StudyInvestigatorType investigator = study.getStudyOrganization(0).getStudyInvestigator(0);
			  Investigator studyInvestigator = new Investigator();
			  if(investigator.getHealthcareSiteInvestigator()!=null)
			  { 
				   if (investigator.getHealthcareSiteInvestigator().getInvestigator()!=null){
					   logger.info("payload has Investigator information");
					   studyInvestigator.setNciId(investigator.getHealthcareSiteInvestigator().getInvestigator(0).getNciIdentifier());
					   String fullName =investigator.getHealthcareSiteInvestigator().getInvestigator(0).getFullName();
					   String[] result = fullName.split("\\s");
					   if(result.length==3){
					       	 studyInvestigator.setFirstName(result[0]);
					    	 studyInvestigator.setInitials(result[1]);
					    	 studyInvestigator.setLastName(result[2]);
					   }else if (result.length==2){
							 studyInvestigator.setFirstName(result[0]);
					       	 studyInvestigator.setLastName(result[1]);
					   }else if (result.length==1){
							 studyInvestigator.setFirstName(result[0]);
					   }
					   protocol.setInvestigator(studyInvestigator);
					   
					  }else{
						  logger.info("payload has no Investigator information");
					   protocol.setInvestigator(null);
				   }
				   
			  }
		  }
		}		
		con = dao.getConnection();
		logger.info("Create Study message validated");
		try {
			dao.saveProtocol(con, protocol);
			logger.info("Persisted the study");
		} catch (SQLException e) {
			logger.error("Error creating study", e);
			StudyCreationException rce = new StudyCreationException();
			rce.setFaultString(e.getMessage());
			throw rce;
		} catch (Exception e) {
			logger.error("Error creating study", e);
			StudyCreationException rce = new StudyCreationException();
			rce.setFaultString(e.getMessage());
			throw rce;
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				logger.error("Error closing connection", e);
			}
		}
		logger.info("Study created");
		    }//end of else
		 }//end of else
		}

	/* (non-Javadoc)
	 * @see gov.nih.nci.ccts.grid.common.StudyConsumerI#rollback(gov.nih.nci.ccts.grid.Study)
	 */
	public void rollback(Study study) throws RemoteException,
			InvalidStudyException {
		String studyGridId = study.getGridId();
		logger.debug("Received a Study Rollback StudyGridId" + studyGridId);
		// long epochPersistTime=0;
		// long epochcurrentTime=0;
		// need to get the hashmap from the application context
		con = dao.getConnection();
		// if(map.containsKey(studyGridId))
		// {
		try {
			/*
			 * StudyPersistTime ppt = map.get(studyGridId); Calendar persistTime =
			 * ppt.getPersistTime(); epochPersistTime =
			 * persistTime.getTime().getTime(); Calendar currentTime =
			 * Calendar.getInstance();
			 * epochcurrentTime=currentTime.getTime().getTime(); double minutes =
			 * (double)(epochcurrentTime-epochPersistTime)/MILLIS_PER_MINUTE;
			 * if(minutes < THRESHOLD_MINUTE)
			 */
			java.util.Date insertdate = dao.checkStudyForRollback(con,
					studyGridId);
			if (insertdate != null) {
				java.util.Date currdate = new Date();
				long milis1 = insertdate.getTime();
				long milis2 = currdate.getTime();
				long diffInMin = (milis2 - milis1) / MILLIS_PER_MINUTE;

				if (insertdate.before(currdate) && diffInMin < THRESHOLD_MINUTE) {
					dao.rollbackStudy(con, studyGridId);
				} else {
					logger
							.info("There is no study with in the threshold time for rollback");
				}
			} else {
				StudyCreationException ire = new StudyCreationException();
				ire
						.setFaultString("Invalid study rollback message- no study found with given gridid");
				logger.fatal(ire);
			}
		} catch (SQLException se) {
			logger.error("Error deleting study", se);
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				logger.error("Error closing connection", e);
			}
		}
		logger.info("deleted study");
	}

	//cleanupHashMap(epochcurrentTime);

	/**
	 * Cleans up the hash map - looks for the time stamp value in all the ParticipantPersistTime
	 * if it is difference between the current time and persist time is greater than the threshold
	 * value -then the ParticipantPersistTime is removed from the hash map.
	 */
	/*	private void cleanupHashMap(long currentTime)
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
	 }*/
}
