package gov.nih.nci.cabig.labviewer.grid;

import gov.nih.nci.cabig.ccts.domain.HealthcareSiteType;
import gov.nih.nci.cabig.ccts.domain.IdentifierType;
import gov.nih.nci.cabig.ccts.domain.OrganizationAssignedIdentifierType;
import gov.nih.nci.cabig.ccts.domain.ParticipantType;
import gov.nih.nci.cabig.ccts.domain.Registration;
import gov.nih.nci.cabig.ccts.domain.StudyRefType;
import gov.nih.nci.cabig.ccts.domain.StudySiteType;
import gov.nih.nci.caxchange.ctom.viewer.util.LabViewerAuthorizationHelper;
import gov.nih.nci.ccts.grid.common.RegistrationConsumerI;
import gov.nih.nci.ccts.grid.service.globus.RegistrationConsumerAuthorization;
import gov.nih.nci.ccts.grid.stubs.types.InvalidRegistrationException;
import gov.nih.nci.ccts.grid.stubs.types.RegistrationConsumptionException;
import gov.nih.nci.ctom.ctlab.domain.HealthCareSite;
import gov.nih.nci.ctom.ctlab.domain.Identifier;
import gov.nih.nci.ctom.ctlab.domain.Participant;
import gov.nih.nci.ctom.ctlab.domain.Protocol;
import gov.nih.nci.ctom.ctlab.domain.StudyParticipantAssignment;
import gov.nih.nci.ctom.ctlab.handler.ProtocolHandler;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;

import org.apache.log4j.Logger;

/**
 * LabViewerRegistrationConsumer is the implementing class for this grid service
 * for Lab Viewer. It consumes the patient registration message that is sent
 * from the patient registry application via the hub.
 * <P>
 * 
 * @author Anupama Sharma
 */
public class LabViewerRegistrationConsumer implements RegistrationConsumerI
{
	private static final int MILLIS_PER_MINUTE = 60 * 1000;
	private static final int THRESHOLD_MINUTE = 1;
	private static final Logger logger =
			Logger.getLogger(LabViewerRegistrationConsumer.class);
	private HashMap<String, ParticipantPersistTime> map =
			new HashMap<String, ParticipantPersistTime>();
	private ProtocolHandler dao = new ProtocolHandler();
	private Connection con;

	/**
	 * commit is not currently implemented but is included in the grid service
	 * to possibly provide a double commit transaction process.
	 */
	public void commit(Registration registration) throws RemoteException,
			InvalidRegistrationException
	{
		// TODO Auto-generated method stub

	}

	public org.oasis.wsrf.properties.QueryResourcePropertiesResponse queryResourceProperties(
			org.oasis.wsrf.properties.QueryResourceProperties_Element params)
			throws RemoteException
	{
		return queryResourceProperties(params);
	}

	public org.oasis.wsrf.properties.GetMultipleResourcePropertiesResponse getMultipleResourceProperties(
			org.oasis.wsrf.properties.GetMultipleResourceProperties_Element params)
			throws RemoteException
	{
		return getMultipleResourceProperties(params);
	}

	public org.oasis.wsrf.properties.GetResourcePropertyResponse getResourceProperty(
			javax.xml.namespace.QName params) throws RemoteException
	{
		return getResourceProperty(params);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nih.nci.ccts.grid.common.RegistrationConsumer#rollback(gov.nih.nci.ccts.grid.Registration)
	 */
	public void rollback(Registration registration) throws RemoteException,
			InvalidRegistrationException
	{
		logger.debug("Received a rollback for participant");
		ParticipantType participant = registration.getParticipant();
		String participantGridId = participant.getGridId();
		String participantExtension = participant.getIdentifier(0).getValue();
		// obtain Connection
		con = dao.getConnection();
		// method returns the ctominsertdate if the participantGridId is found
		// in the database
		try
		{
			// check if Participant exists for rollback
			java.util.Date insertdate =
					dao.checkParticipantForRollback(con, participantGridId,
							participantExtension);
			if (insertdate != null)
			{
				java.util.Date currdate = new Date();
				long milis1 = insertdate.getTime();
				long milis2 = currdate.getTime();
				long diffInMin = (milis2 - milis1) / MILLIS_PER_MINUTE;

				if (insertdate.before(currdate) && diffInMin < THRESHOLD_MINUTE)
				{
					// issue Participant roll back
					dao.rollbackParticipant(con, participantGridId,
							participantExtension);
					logger.info("deleted participant");
				}
				else
				{
					logger
							.info("There is no participant within the threshold time for rollback");
				}
			}
			else
			{
				InvalidRegistrationException ire =
						new InvalidRegistrationException();
				ire
						.setFaultString("Lab Viewer invalid patient rollback message - no patient found with given gridid");
				logger.fatal(ire);
				throw (ire);

			}
		}
		catch (SQLException se)
		{
			logger.error("Error deleting participant", se);
			String msg =
					"Lab Viewer unable to rollback participant"
							+ se.getMessage();
			throw new RemoteException(msg);
		}
		finally
		{
			try
			{
				con.close();
			}
			catch (SQLException e)
			{
				logger.error("Error closing connection", e);
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nih.nci.ccts.grid.common.RegistrationConsumer#register(gov.nih.nci.ccts.grid.Registration)
	 */
	public Registration register(Registration registration)
			throws RemoteException, InvalidRegistrationException,
			RegistrationConsumptionException
	{
		logger.debug("Lab Viewer Registration message received");

		// Authorization code
		String username = RegistrationConsumerAuthorization.getCallerIdentity();
		if (!authorized(username))
		{
			logger.error("Error saving participant");
			RegistrationConsumptionException rce =
					new RegistrationConsumptionException();
			rce.setFaultString("User " + username
					+ " not authorized for this operation");
			throw rce;
		}
		else
		{
			// save the study data
			Protocol protocol = new Protocol();
			// populate the protocol and retrieve the MRN from the identifier
			// type
			String mrn = populateProtocol(protocol, registration);
			// obtain database Connection
			con = dao.getConnection();
			logger.info("Lab Viewer Registration message validated");

			try
			{
				// Perform the check to see if the patient exists - Demo case
				// where labs are loaded prior to registering a patient.
				Long spaid = dao.checkParticipantExists(con, protocol, mrn);
				if (spaid != null)
				{
					// protocol.getHealthCareSite().getStudyParticipantAssignment().getParticipant().getIdentifier().setRoot(root);
					logger.debug(protocol.getHealthCareSite()
							.getStudyParticipantAssignment().getParticipant()
							.getIdentifier().getRoot());
					// update Participant Grid Id
					dao.updateParticipantGridId(con, registration.getGridId(),
							spaid, mrn);
				}
				else
				{
					// Save Protocol.
					dao.persist(con, protocol);
				}
			}
			catch (SQLException e)
			{
				logger.error("Error saving participant", e);
				RegistrationConsumptionException rce =
						new RegistrationConsumptionException();
				rce.setFaultString(e.getMessage());
				throw rce;
			}
			catch (Exception e)
			{
				logger.error("Error saving participant", e);
				RegistrationConsumptionException rce =
						new RegistrationConsumptionException();
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
					logger.error("Error closing connection", e);
				}
			}
			logger.info("Lab Viewer Registration message stored");
		}
		return registration;
	}

	/**
	 * Populates the protocol object and returns the mrn from the identifierType 
	 * @param protocol
	 * @param registration
	 * @return mrn 
	 * @throws InvalidRegistrationException
	 */
	private String populateProtocol(Protocol protocol, Registration registration)
			throws InvalidRegistrationException
	{
		java.util.Date now = new Date();
		StudyRefType studyRef = registration.getStudyRef();
		protocol.setLongTxtTitle(studyRef.getLongTitleText());
		protocol.setShortTxtTitle(studyRef.getShortTitleText());
		IdentifierType identifiers[] = studyRef.getIdentifier();

		// save the identifier data
		for (IdentifierType ident : identifiers)
		{
			if (ident.getPrimaryIndicator())
			{
				Identifier id = new Identifier();
				id.setExtension(ident.getValue());
				id.setSource(ident.getSource());
				id.setRoot(studyRef.getGridId());
				protocol.setIdentifier(id);
				protocol.setNciIdentifier(id.getRoot() + "."
						+ id.getExtension());
			}
		}

		// save the study site data
		StudySiteType studySite = registration.getStudySite();
		HealthcareSiteType hcsType = studySite.getHealthcareSite(0);
		HealthCareSite healthCare = new HealthCareSite();
		healthCare.setNciInstituteCd(hcsType.getNciInstituteCode());
		healthCare.setName(hcsType.getName());

		// save participant data
		ParticipantType participant = registration.getParticipant();
		Participant part = new Participant();
		part.setFirstName(participant.getFirstName());
		part.setLastName(participant.getLastName());
		char[] initials = new char[2];
		initials[0] = participant.getFirstName().charAt(0);
		initials[1] = participant.getLastName().charAt(0);
		part.setInitials(new String(initials));
		part.setAdminGenderCode(participant.getAdministrativeGenderCode());
		part.setBirthDate(participant.getBirthDate());
		part.setEthnicGroupCode(participant.getEthnicGroupCode());
		part.setRaceCode(participant.getRaceCode());
		part.setCtomInsertDate(now);
		// Assume that only one identifier was sent and use that
		IdentifierType[] ids = participant.getIdentifier();
		IdentifierType id = null;
		Identifier partIdent = new Identifier();
		String type = "";
		String assignedBy = "";
		if (ids != null && ids.length > 0)
		{
			id = ids[0];
			if (id instanceof OrganizationAssignedIdentifierType)
				assignedBy = "organization";
			else
				assignedBy = "system";
			type = id.getType();
			partIdent.setExtension(id.getValue());
			partIdent.setSource(id.getSource());
			partIdent.setRoot(participant.getGridId());
			partIdent.setAssigningAuthorityName(assignedBy);
			part.setIdentifier(partIdent);

		}
		else
		{
			InvalidRegistrationException ire =
					new InvalidRegistrationException();
			ire
					.setFaultString("Invalid patient registration message missing patient identifier");
			throw ire;
		}

		StudyParticipantAssignment studyPartAssig =
				new StudyParticipantAssignment();
		studyPartAssig.setParticipant(part);
		String tmp = participant.getGridId() + "." + id.getValue();
		String mrn = id.getValue();
		studyPartAssig.setStudyPartIdOrig(tmp);

		// SPA identifier is same as participant but different grid id
		Identifier regIdent = new Identifier();
		regIdent.setSource(partIdent.getSource());
		regIdent.setExtension(partIdent.getExtension());
		regIdent.setRoot(registration.getGridId());
		studyPartAssig.setIdentifier(regIdent);
		studyPartAssig.setType(type);

		healthCare.setStudyParticipantAssignment(studyPartAssig);
		protocol.setHealthCareSite(healthCare);
		protocol.setCtomInsertDt(now);

		return mrn;
	}

	/**
	 * authorized checks if the user is authorized to perform the Registration
	 * 
	 * @param username
	 * @return userAuthorized
	 * @throws RegistrationConsumptionException
	 */
	private boolean authorized(String username)
			throws RegistrationConsumptionException
	{
		boolean userAuthorized = false;
		String user = "";
		if (username == null)
		{
			logger.error("Error saving participant no username provided");
			RegistrationConsumptionException rce =
					new RegistrationConsumptionException();
			rce.setFaultString("No user credentials provided");
			throw rce;
		}
		else
		{
			logger.info("User " + username);
			// call the LabViewer Authorization Helper
			LabViewerAuthorizationHelper lvaHelper =
					new LabViewerAuthorizationHelper();
			if (username != null)
			{
				int beginIndex = username.lastIndexOf("=");
				int endIndex = username.length();
				user = username.substring(beginIndex + 1, endIndex);
			}
			// check if authorized
			userAuthorized = lvaHelper.isAuthorized(user);
		}
		return userAuthorized;

	}

}