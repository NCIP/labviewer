package gov.nih.nci.cabig.labviewer.grid;

import gov.nih.nci.cabig.ccts.domain.HealthcareSiteType;
import gov.nih.nci.cabig.ccts.domain.IdentifierType;
import gov.nih.nci.cabig.ccts.domain.InvestigatorType;
import gov.nih.nci.cabig.ccts.domain.OrganizationAssignedIdentifierType;
import gov.nih.nci.cabig.ccts.domain.Study;
import gov.nih.nci.cabig.ccts.domain.StudyInvestigatorType;
import gov.nih.nci.cabig.ccts.domain.StudyOrganizationType;
import gov.nih.nci.cabig.ctms.suite.authorization.SuiteAuthorizationAccessException;
import gov.nih.nci.cabig.ctms.suite.authorization.SuiteRole;
import gov.nih.nci.cabig.ctms.suite.authorization.SuiteRoleMembership;
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
import gov.nih.nci.ctom.ctlab.handler.ProtocolHandler;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.oasis.wsrf.properties.GetMultipleResourcePropertiesResponse;
import org.oasis.wsrf.properties.GetMultipleResourceProperties_Element;
import org.oasis.wsrf.properties.GetResourcePropertyResponse;
import org.oasis.wsrf.properties.QueryResourcePropertiesResponse;
import org.oasis.wsrf.properties.QueryResourceProperties_Element;

public class LabViewerStudyConsumer implements StudyConsumerI
{
	Logger log = Logger.getLogger(getClass());
	static final int MILLIS_PER_MINUTE = 60 * 1000;
	static final int THRESHOLD_MINUTE = 1;
	private ProtocolHandler dao = new ProtocolHandler();
	private Connection con;
	private LabViewerAuthorizationHelper authorizationHelper;

	public void commit(Study study) throws RemoteException,
			InvalidStudyException
	{
		// TODO Auto-generated method stub

	}

	public GetMultipleResourcePropertiesResponse getMultipleResourceProperties(
			GetMultipleResourceProperties_Element params)
			throws RemoteException
	{
		// TODO Auto-generated method stub
		return null;
	}

	public GetResourcePropertyResponse getResourceProperty(QName params)
			throws RemoteException
	{
		// TODO Auto-generated method stub
		return null;
	}

	public QueryResourcePropertiesResponse queryResourceProperties(
			QueryResourceProperties_Element params) throws RemoteException
	{
		// TODO Auto-generated method stub
		return null;
	}

	public void createStudy(Study study) throws RemoteException,
			InvalidStudyException, StudyCreationException
	{
		log.info("Create Study message received");
		checkAuthorization(StudyConsumerAuthorization.getCallerIdentity(), study);

		// save the study data
		log.info("payload has Study information");
		Protocol protocol = new Protocol();
		// populate the Protocol object with Study message data
		populateProtocol(protocol, study);
		// obtain connection
		con = dao.getConnection();
		log.info("Create Study message validated");
		try
		{
			// save Protocol
			dao.persist(con, protocol);
			log.info("Persisted the study");
		}
		catch (SQLException e)
		{
			log.error("Error creating study", e);
			StudyCreationException rce = new StudyCreationException();
			rce.setFaultString(e.getMessage());
			throw rce;
		}
		catch (Exception e)
		{
			log.error("Error creating study", e);
			StudyCreationException rce = new StudyCreationException();
			rce.setFaultString(e.getMessage());
			throw rce;
		}
		finally
		{
			try
			{
				con.close();
			}
			catch (SQLException e)
			{
				log.error("Error closing connection", e);
			}
		}
		log.info("Study created");
	}
	
	/**
	 * @param callerId
	 * @throws StudyCreationException
	 */
	private void checkAuthorization(String callerId, Study study) throws StudyCreationException
	{	
		if (callerId == null)
		{
			log.error("Error saving study: no user credentials provided");
			throw createStudyCreationException("No user credentials provided");
		}

		log.debug("Service called by: " + callerId);		
		int beginIndex = callerId.lastIndexOf("=") + 1;
		int endIndex = callerId.length();
		String username = callerId.substring(beginIndex, endIndex);
		log.debug("Username = " + username);

		List<String> siteNciInstituteCodes = getSiteNciInstituteCodes(study);
		log.debug("Site NCI institute codes = " + siteNciInstituteCodes.toString());
		if (siteNciInstituteCodes == null)
		{
			log.error("Error saving study: site NCI institute code is null");
			throw createStudyCreationException("Site NCI institute code is null");
		}

		List<SuiteRole> studyConsumerRoles = new ArrayList<SuiteRole>();
		studyConsumerRoles.add(SuiteRole.STUDY_CREATOR);
		studyConsumerRoles.add(SuiteRole.STUDY_QA_MANAGER);
		
		try
		{
			Map<SuiteRole, SuiteRoleMembership> userRoleMemberships = getAuthorizationHelper().getUserRoleMemberships(username);
			boolean userAuthorizedForStudyConsumerRole = false;
			boolean userAuthorizedForSite = false;

			for (SuiteRole studyConsumerRole : studyConsumerRoles)
			{
				log.debug("Checking study consumer role: " + studyConsumerRole.toString());
				if (userRoleMemberships.containsKey(studyConsumerRole))
				{
					log.debug("User role memberships contains role: " + studyConsumerRole.toString());
					userAuthorizedForStudyConsumerRole = true;										
					
					SuiteRoleMembership userRoleMembership = userRoleMemberships.get(studyConsumerRole);
					// if the user has permission to access specific sites (not all sites), then verify the sites
					if (userRoleMembership.isAllSites())
					{
						log.debug("User is authorized for all sites");
						userAuthorizedForSite = true;
					}
					else
				    {
						for (String siteNciInstituteCode : siteNciInstituteCodes)
						{
							log.debug("Checking site NCI institute code: " + siteNciInstituteCode);
							if (userRoleMembership.getSiteIdentifiers().contains(siteNciInstituteCode))
							{
								log.debug("User is authorized for site NCI institute code: " + siteNciInstituteCode);
								userAuthorizedForSite = true;
							}
						}
				    }
				}
			}
			
			log.info("userAuthorizedForStudyConsumerRole = " + userAuthorizedForStudyConsumerRole);
			if (!userAuthorizedForStudyConsumerRole)
	    	{
				throw new SuiteAuthorizationAccessException("Username %s is not authorized for roles %s", username, studyConsumerRoles.toString());
	    	}
			
			log.info("userAuthorizedForSite = " + userAuthorizedForSite);
			if (!userAuthorizedForSite)
	    	{
				throw new SuiteAuthorizationAccessException("Username %s is not authorized for sites %s", username, siteNciInstituteCodes.toString());
	    	}
		}
		catch (SuiteAuthorizationAccessException e)
		{
			log.error("Error saving study: ", e);
			throw createStudyCreationException(e.getMessage());
		}
		catch (Exception e)
        {
            log.error("Error saving study: ", e);
            throw createStudyCreationException(e.getMessage());
        }		
	}
	
	private StudyCreationException createStudyCreationException(String message)
	{
		StudyCreationException exception = new StudyCreationException();
		exception.setFaultString(message);
		return exception;
	}
	
	private synchronized LabViewerAuthorizationHelper getAuthorizationHelper()
	{
        if (authorizationHelper == null)
        {
            authorizationHelper = new LabViewerAuthorizationHelper();
        }
        
        return authorizationHelper;
    }
	
	private List<String> getSiteNciInstituteCodes(Study study)
	{
		List<String> siteNciInstituteCodes = new ArrayList<String>();
		log.info("entering get getSiteNciInstituteCodes" );
		StudyOrganizationType studyOrganizationTypes[] = study.getStudyOrganization();
		if (studyOrganizationTypes != null)
		{
			for (StudyOrganizationType studyOrganizationType : studyOrganizationTypes)
		    {
			    HealthcareSiteType healthCareSiteTypes[] = studyOrganizationType.getHealthcareSite();
			    if (healthCareSiteTypes != null)
			    {
				for (HealthcareSiteType healthCareSiteType : healthCareSiteTypes)
				{
					if (StringUtils.isNotBlank(healthCareSiteType.getNciInstituteCode()))
					{
						if (!siteNciInstituteCodes.contains(healthCareSiteType.getNciInstituteCode()))
						{
						    siteNciInstituteCodes.add(healthCareSiteType.getNciInstituteCode());
						}
					}
				}
			    }
			}
		}
		log.info("leaving  getSiteNciInstituteCodes" );
		return siteNciInstituteCodes;
	}

	/**
	 * @param protocol
	 * @param study
	 */
	private void populateProtocol(Protocol protocol, Study study)
	{
		try
		{
		java.util.Date now = new Date();
		protocol.setLongTxtTitle(study.getLongTitleText());
		protocol.setShortTxtTitle(study.getShortTitleText());
		protocol.setPhaseCode(study.getPhaseCode());
		protocol.setBlindedId(StringUtils.isBlank(study.getBlindedIndicator()) ? null : study.getBlindedIndicator());
		protocol.setMultiInstId(StringUtils.isBlank(study.getMultiInstitutionIndicator()) ? null : study.getMultiInstitutionIndicator());
		protocol.setRandomId(StringUtils.isBlank(study.getRandomizedIndicator()) ? null : study.getRandomizedIndicator());
		protocol.setDescTxt(StringUtils.isBlank(study.getDescriptionText()) ? null : study.getDescriptionText());
		protocol.setPrecisTxt(StringUtils.isBlank(study.getPrecisText()) ? null : study.getPrecisText());
		protocol.setIntentCode(StringUtils.isBlank(study.getType()) ? null : study.getType());
		protocol.setTargetAccNum(study.getTargetAccrualNumber() == null ? null : study.getTargetAccrualNumber().longValue());
		if (study.getCoordinatingCenterStudyStatus() != null)
		{
			ProtocolStatus protocolStatus = new ProtocolStatus();
			protocolStatus.setCtom_insert_date(now);
			protocolStatus.setStatus_code(camelCase(study.getCoordinatingCenterStudyStatus().getValue()));
			protocol.setStatus(protocolStatus);
		}
		else
		{
			protocol.setStatus(null);
		}
		protocol.setCtomInsertDt(now);
		IdentifierType identifiers[] = study.getIdentifier();

		// save the identifier data
		// IdentifierType ident: identifiers
		String assignedBy = "";
		for (int i = 0; i < identifiers.length; i++)
		{
			IdentifierType ident = identifiers[i];
			if (ident.getPrimaryIndicator() != null && ident.getPrimaryIndicator().booleanValue())
			{
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
		}// end of for

		// save the study site data
		if (study.getStudyOrganization() != null)
		{
			//replace code: Perform a search by CTEP Identifier to retrieve the ii for the Organization
			// and using this ii perform a COPPA service call to retrieve the Health care site details
			Long ctepIdentifier=1L;
			//retrieveOrgIi(ctepIdentifier);

			if (study.getStudyOrganization(0).getHealthcareSite() != null)
			{
				log.info("payload has HealthcareSite information");
				HealthcareSiteType hcsType =
						study.getStudyOrganization(0).getHealthcareSite(0);
				HealthCareSite healthCare = new HealthCareSite();
				healthCare.setNciInstituteCd(hcsType.getNciInstituteCode());
				healthCare.setName(hcsType.getName());
				healthCare.setDescpTxt(StringUtils.isBlank(hcsType.getDescriptionText()) ? null : hcsType.getDescriptionText());
				if (hcsType.getAddress() != null)
	            {
					healthCare.setStreetAddr(StringUtils.isBlank(hcsType.getAddress().getStreetAddress()) ? null : hcsType.getAddress().getStreetAddress());
					healthCare.setCity(StringUtils.isBlank(hcsType.getAddress().getCity()) ? null : hcsType.getAddress().getCity());
					healthCare.setStateCode(StringUtils.isBlank(hcsType.getAddress().getStateCode()) ? null : hcsType.getAddress().getStateCode());
					healthCare.setPostalCode(StringUtils.isBlank(hcsType.getAddress().getPostalCode()) ? null : hcsType.getAddress().getPostalCode());
					healthCare.setCountryCode(StringUtils.isBlank(hcsType.getAddress().getCountryCode()) ? null : hcsType.getAddress().getCountryCode());
	            }
				healthCare.setCtomInsertDt(now);
				protocol.setHealthCareSite(healthCare);
			}
			else
			{
				log.info("payload has no HealthcareSite information");
				protocol.setHealthCareSite(null);
			}
			if (study.getStudyOrganization(0).getStudyInvestigator() != null)
			{
				StudyInvestigatorType investigator =
						study.getStudyOrganization(0).getStudyInvestigator(0);
				Investigator studyInvestigator = new Investigator();
				if (investigator.getHealthcareSiteInvestigator() != null)
				{
					if (investigator.getHealthcareSiteInvestigator()
							.getInvestigator() != null)
					{
						// save the investigator data
						log.info("payload has Investigator information");
						InvestigatorType healthCareSiteInvestigator = investigator.getHealthcareSiteInvestigator().getInvestigator(0);
						studyInvestigator.setNciId(healthCareSiteInvestigator.getNciIdentifier());
						studyInvestigator.setFirstName(healthCareSiteInvestigator.getFirstName());
						studyInvestigator.setLastName(healthCareSiteInvestigator.getLastName());
						studyInvestigator.setPhone(healthCareSiteInvestigator.getPhoneNumber());
						studyInvestigator.setTelecomAddr(StringUtils.isBlank(healthCareSiteInvestigator.getEmail()) ? null : healthCareSiteInvestigator.getEmail());
						protocol.setInvestigator(studyInvestigator);
					}
					else
					{
						log.info("payload has no Investigator information");
						protocol.setInvestigator(null);
					}
				}// end if
			}// end if
		}
		}
		catch (Exception e)
		{
			log.error("populateProtocol: Exception occurred: ", e);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see gov.nih.nci.ccts.grid.common.StudyConsumerI#rollback(gov.nih.nci.ccts.grid.Study)
	 */
	public void rollback(Study study) throws RemoteException,
			InvalidStudyException
	{
		String studyGridId = study.getGridId();
		log.debug("Received a Study Rollback StudyGridId" + studyGridId);
		// Obtain Connection
		con = dao.getConnection();
		try
		{

			java.util.Date insertdate =
					dao.checkStudyForRollback(con, studyGridId);
			if (insertdate != null)
			{
				java.util.Date currdate = new Date();
				long milis1 = insertdate.getTime();
				long milis2 = currdate.getTime();
				long diffInMin = (milis2 - milis1) / MILLIS_PER_MINUTE;

				if (insertdate.before(currdate) && diffInMin < THRESHOLD_MINUTE)
				{
					// Issue Study rollback
					dao.rollbackStudy(con, studyGridId);
				}
				else
				{
					log
							.info("There is no study with in the threshold time for rollback");
				}
			}
			else
			{
				// throw remote exception
				StudyCreationException ire = new StudyCreationException();
				ire
						.setFaultString("Invalid study rollback message- no study found with given gridid");
				log.fatal(ire);
				throw (ire);
			}
		}
		catch (SQLException se)
		{
			log.error("Error deleting study", se);
		}
		finally
		{
			try
			{
				con.close();
			}
			catch (SQLException e)
			{
				log.error("Error closing connection", e);
				String msg =
						"Lab Viewer unable to rollback study" + e.getMessage();
				throw new RemoteException(msg);
			}
		}
		log.info("deleted study");
	}
	
	private static String camelCase(String string)
	{
        switch (string.length())
        {
            case 0:  return string;
            case 1:  return string.toUpperCase();
            default: return string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();
        }
	}
	
}
