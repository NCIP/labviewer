package gov.nih.nci.ctom.ctlab.persistence;

import gov.nih.nci.ctom.ctlab.domain.Activity;
import gov.nih.nci.ctom.ctlab.domain.CentralLaboratory;
import gov.nih.nci.ctom.ctlab.domain.ClinicalResult;
import gov.nih.nci.ctom.ctlab.domain.HealthCareSite;
import gov.nih.nci.ctom.ctlab.domain.Investigator;
import gov.nih.nci.ctom.ctlab.domain.Observation;
import gov.nih.nci.ctom.ctlab.domain.Participant;
import gov.nih.nci.ctom.ctlab.domain.PerformingLaboratory;
import gov.nih.nci.ctom.ctlab.domain.Procedure;
import gov.nih.nci.ctom.ctlab.domain.Protocol;
import gov.nih.nci.ctom.ctlab.domain.ProtocolStatus;
import gov.nih.nci.ctom.ctlab.domain.Specimen;
import gov.nih.nci.ctom.ctlab.domain.SpecimenCollection;
import gov.nih.nci.ctom.ctlab.domain.StudyParticipantAssignment;
import gov.nih.nci.ctom.ctlab.domain.StudyTimePoint;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

/**
 * CTLabDAO persist all clinical trial data to the CTODS database for labviewer
 * <P>
 * @author Anupama Sharma
 */
public class CTLabDAO extends BaseJDBCDAO {
	// Logging File
	private static Logger logger = Logger.getLogger("client");

	/**
	 * saveProtocol persists the Protocol object to the CTODS database
	 * @param con  Connection 
	 * @param prot Protocol
	 * @throws Exception
	 */
	public void saveProtocol(Connection con, Protocol prot) throws Exception {
		logger.debug("Saving Protocol");
		PreparedStatement ps = null;
		ResultSet rs = null;
		Long id = null;
		boolean identifierUpdInd = false;

		if (prot.getNciIdentifier() == null)
			throw new Exception(
					"Invalid Data, Protocol Identifier Cannot Be Null");

		try {
			// Check if the protocol already exists using identifier
			ps = con
					.prepareStatement("select p.ID from protocol p, identifier ii where p.ID = ii.PROTOCOL_ID and ii.extension=?");
			ps.setString(1, prot.getIdentifier().getExtension());
			rs = ps.executeQuery();
			// If it exists get the ID
			if (rs.next()) {
				id = rs.getLong(1);
				prot.setId(id);
				ps = con
						.prepareStatement("select ID from IDENTIFIER where PROTOCOL_ID = ?");
				ps.setLong(1, id);
				rs = ps.executeQuery();

				if (rs.next()) {
					prot.getIdentifier().setId(rs.getLong(1));
				}
				logger.debug("Protocol existed");
			} else {
				// If not then insert it
				saveOrInsertIdentifier(con, prot);
				if (prot.getId() == null) {
					identifierUpdInd = true;
					Statement stmt = con.createStatement();
					rs = stmt.executeQuery("select nextval('protocol_seq')");
					rs.next();
					id = rs.getLong(1);
					ps = con
							.prepareStatement("insert into protocol (ID, NCI_IDENTIFIER, IDENTIFIER_ASSIGNING_AUTHORITY, LONG_TITLE_TEXT, SHORT_TITLE_TEXT, CTOM_INSERT_DATE, SPONSOR_CODE)  values(?,?,?,?,?,?,?)");
					ps.setLong(1, id);
					ps.setString(2, String.valueOf(prot.getNciIdentifier()));
					ps.setString(3, String.valueOf(prot.getIdAssigningAuth()));
					ps.setString(4, String.valueOf(prot.getLongTxtTitle()));
					ps.setString(5, String.valueOf(prot.getShortTxtTitle()));
					if (prot.getCtomInsertDt() == null) {
						ps.setTimestamp(6, new java.sql.Timestamp(new Date()
								.getTime()));
					} else {
						ps.setTimestamp(6, new java.sql.Timestamp(prot
								.getCtomInsertDt().getTime()));
					}
					ps.setString(7, String.valueOf(prot.getSponsorCode()));
					ps.execute();
					if (identifierUpdInd && prot.getIdentifier() != null) {
						prot.setId(id);
						updateIdentifier(con, prot);
					}
				} // update into participant if there was a participant associated
				// with identifier.
				else {
					ps = con
							.prepareStatement("update PROTOCOL set NCI_IDENTIFIER = ?, IDENTIFIER_ASSIGNING_AUTHORITY = ?, LONG_TITLE_TEXT = ? where ID = ?");
					ps.setString(1, String.valueOf(prot.getNciIdentifier()));
					ps.setString(2, String.valueOf(prot.getIdAssigningAuth()));
					ps.setString(3, String.valueOf(prot.getLongTxtTitle()));
					ps.setLong(4, prot.getId());
					ps.executeUpdate();
					con.commit();
				}
			}
		} finally {
			if (ps != null) {
				ps.close();
			}
			if (rs != null) {
				rs.close();
			}
		}
		//save protocol status
		if (prot.getStatus() != null)
			saveProtocolStatus(con, id, prot.getStatus());
		//save investigator
		if (prot.getInvestigator() != null)
			saveInvestigator(con, id, prot.getInvestigator());
		//save healthcaresite
		if (prot.getHealthCareSite() != null)
			saveHealthCareSite(con, id, prot.getHealthCareSite());

	}

	/**
	 * saveProtocolStatus persists the Protocol Status to the CTODS database
	 * @param con Connection  
	 * @param id Protocol Id
	 * @param protoStatus ProtocolStatus 
	 * @throws SQLException
	 */
	private void saveProtocolStatus(Connection con, Long id,
			ProtocolStatus protoStatus) throws SQLException {
		logger.debug("Saving the Protocol Status");
		PreparedStatement ps = null;
		ResultSet rs = null;
		Statement stmt = null;

		try {
			// Get Id from sequence
			stmt = con.createStatement();
			rs = stmt.executeQuery("select nextval('PROTOCOL_STATUS_SEQ')");
			rs.next();
			protoStatus.setId(rs.getLong(1));
			// insert into PROTOCOL STATUS
			ps = con
					.prepareStatement("insert into PROTOCOL_STATUS (ID, PROTOCOL_ID, STATUS_CODE,CTOM_INSERT_DATE)  values(?,?,?,?)");
			ps.setLong(1, protoStatus.getId());
			ps.setLong(2, id);
			ps.setString(3, protoStatus.getStatus_code());
			ps.setDate(4, new java.sql.Date(protoStatus.getCtom_insert_date()
					.getTime()));
			ps.execute();
		} finally {
			if (ps != null) {
				ps.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			if (rs != null) {
				rs.close();
			}
		}

	}

	/**
	 * saveHealthCareSite persists the HealthCasre Site
	 * @param con Connection 
	 * @param protId Protocol Id
	 * @param hcSite HealthCare Site
	 * @throws SQLException
	 */
	private void saveHealthCareSite(Connection con, Long protId,
			HealthCareSite hcSite) throws SQLException {
		logger.debug("Saving the HealthCareSite");
		PreparedStatement ps = null;
		ResultSet rs = null;
		Long hsId = null;
		Statement stmt = null;

		Long ssId;
		try {
			//check if there exists a healthcare site with the NCI Institue code
			ps = con
					.prepareStatement("select id from healthcare_site where NCI_INSTITUTE_CODE = ?");
			ps.setString(1, hcSite.getNciInstituteCd());
			rs = ps.executeQuery();
			if (rs.next()) {
				//use the existing Id
				hsId = rs.getLong(1);
			} else {
				// new HealthCare Site - Get Id from sequence
				stmt = con.createStatement();
				rs = stmt.executeQuery("select nextval('ORGANIZATION_SEQ')");
				rs.next();
				hsId = rs.getLong(1);
				// insert into HealthCare_Site
				ps = con
						.prepareStatement("insert into HEALTHCARE_SITE (ID, NCI_INSTITUTE_CODE, NAME, CTOM_INSERT_DATE)  values(?,?,?,?)");

				ps.setLong(1, hsId);
				ps.setString(2, hcSite.getNciInstituteCd() != null ? hcSite
						.getNciInstituteCd() : "");
				ps.setString(3, hcSite.getNciInstituteCd() != null ? hcSite
						.getNciInstituteCd() : "");
				Date insertDt = hcSite.getCtomInsertDt() != null ? hcSite
						.getCtomInsertDt() : new Date();
				ps.setDate(4, new java.sql.Date(insertDt.getTime()));
				ps.execute();
			}
			hcSite.setId(hsId);
			ssId = null;
			//Check if the Study Site exits with for that healthcaresite Id and Protocol Id
			ps = con
					.prepareStatement("select id from study_site where HEALTHCARE_SITE_ID = ? and PROTOCOL_ID = ?");
			ps.setLong(1, hsId);
			ps.setLong(2, protId);
			rs = ps.executeQuery();
			if (rs.next()) {
				//If exists - use that Study site Id 
				ssId = rs.getLong(1);
			} else {
				//create a new Study Site id
				stmt = con.createStatement();
				rs = stmt.executeQuery("select nextval('STUDY_SITE_SEQ')");
				rs.next();
				ssId = rs.getLong(1);

				// insert into Study_Site
				ps = con
						.prepareStatement("insert into study_site (ID, HEALTHCARE_SITE_ID, PROTOCOL_ID)  values(?,?,?)");

				ps.setLong(1, ssId);
				ps.setLong(2, hsId);
				ps.setLong(3, protId);
				ps.execute();

			}
		} finally {
			if (ps != null) {
				ps.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			if (rs != null) {
				rs.close();
			}
		}
		if (hcSite.getStudyParticipantAssignment() != null) {
			//saves the StudyParticipant Assignment
			saveStudyParticipantAssignment(con, ssId, hcSite);
		}

	}

	/**
	 * saveStudyParticipantAssignment persists Study PArticipant Assignment to CTODS database
	 * @param con Connection
	 * @param ssId Study Site Id
	 * @param hcs HealthCareSite 
	 * @throws SQLException
	 */
	private void saveStudyParticipantAssignment(Connection con, Long ssId,
			HealthCareSite hcs) throws SQLException {
		logger.debug("Saving the StudyParticipantAssignment");
		PreparedStatement ps = null;
		ResultSet rs = null;
		Long spaId = null;
		boolean identifierUpdInd = false;
		StudyParticipantAssignment spa = hcs.getStudyParticipantAssignment();

		try {
			ps = con
					.prepareStatement("select spa.id from STUDY_PARTICIPANT_ASSIGNMENT spa, identifier ii where ii.STUDY_PARTICIPANT_ASSIGNMNT_ID = spa.id AND spa.STUDY_SITE_ID = ? AND ii.extension=?");
			ps.setLong(1, ssId);
			ps.setString(2, spa.getIdentifier().getExtension());
			rs = ps.executeQuery();
			if (rs.next()) {
				spaId = rs.getLong(1);
			} else {
				// Check study participant identifier, if it exists, then don't
				// insert new SPA or Participant
				Long identifierID = saveOrInsertIdentifier(con, spa);
				if (spa.getId() == null) {
					// Get Id from sequence
					Statement stmt = con.createStatement();
					rs = stmt
							.executeQuery("select nextval('STUDY_PARTICIPANT_ASSGNMNT_SEQ')");
					rs.next();
					spaId = rs.getLong(1);

					identifierUpdInd = true;
					saveParticipant(con, spa.getParticipant());
					// insert into STUDY_PARTICIPANT_ASSIGNMENT
					ps = con
							.prepareStatement("insert into STUDY_PARTICIPANT_ASSIGNMENT (ID, STUDY_PARTICIPANT_IDENTFR_ORIG, STUDY_SITE_ID, PARTICIPANT_ID,type)  values(?,?,?,?,?)");
					ps.setLong(1, spaId);
					ps.setString(2, spa.getStudyPartIdOrig());
					ps.setLong(3, ssId);
					ps.setLong(4, spa.getParticipant().getId());
					ps.setString(5, spa.getType());
					ps.execute();
					con.commit();
					if (identifierUpdInd
							&& spa.getParticipant().getIdentifier() != null) {
						spa.setId(spaId);
						updateIdentifier(con, spa, identifierID);
					}
				} else {
					spaId = spa.getId();
				}
			}
		} finally {
			if (ps != null) {
				ps.close();
			}
			if (rs != null) {
				rs.close();
			}
		}

		//save healthCare-participant
		if (hcs.getId() != null && spa.getParticipant().getId() != null) {
			saveHealthCareSiteParticipant(con, hcs.getId(), spa
					.getParticipant().getId());
		}
		//Save Activity
		Activity act = spa.getActivity();
		if (act != null) {
			saveActivity(con, spaId, act);
		}
	}

	/**
	 *  saveHealthCareSiteParticipant persists the HealthCareSite Participant.
	 * @param con Connection
	 * @param hcsId HealthCareSite Id
	 * @param participantId Participant Id
	 */
	public void saveHealthCareSiteParticipant(Connection con, Long hcsId,
			Long participantId) throws SQLException {
		logger.debug("Saving the HealthCareSite - participant join ");
		PreparedStatement ps = null;
		ResultSet rs = null;
		Statement stmt = null;

		try {
			// Get Id from sequence
			stmt = con.createStatement();
			rs = stmt
					.executeQuery("select nextval('healthcare_site_prtcpnt_seq')");
			rs.next();
			// insert into healthcare_site_prtcpnt
			ps = con
					.prepareStatement("insert into healthcare_site_prtcpnt (ID, healthcare_site_id, participant_id,ctom_insert_date)  values(?,?,?,?)");
			ps.setLong(1, rs.getLong(1));
			ps.setLong(2, hcsId);
			ps.setLong(3, participantId);
			Date insertDt = new Date();
			ps.setDate(4, new java.sql.Date(insertDt.getTime()));
			ps.execute();
		} finally {
			if (ps != null) {
				ps.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			if (rs != null) {
				rs.close();
			}
		}
	}

	/**
	 * saveParticipant persists Participant object to the CTODS database
	 * <P>
	 * @param con Connection
	 * @param participant participant
	 * @throws SQLException
	 */
	public void saveParticipant(Connection con, Participant participant)
			throws SQLException {
		logger.debug("Saving the Participant");
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean identifierUpdInd = false;

		// Get the concept descriptor ids for the race and gender
		Long genderCDId = insertOrsaveConceptDescriptor(con, participant
				.getAdminGenderCode(), participant.getAdminGenderCodeSystem(),
				participant.getAdminGenderCodeSystemName(), null, null);
		Long raceCDId = insertOrsaveConceptDescriptor(con, participant
				.getRaceCode(), participant.getRaceCodeSystem(), participant
				.getRaceCodeSystemName(), null, null);

		//updates or inserts the IDentifier
		saveOrInsertIdentifier(con, participant);

		try {
			// insert into participant if there was no participant associated with
			// identifier.
			if (participant.getId() == null) {
				identifierUpdInd = true;
				ps = con.prepareStatement("select nextval('Person_SEQ')");
				rs = ps.executeQuery();

				while (rs.next()) {
					participant.setId(rs.getLong(1));
					break;
				}

				boolean gc = false;
				boolean rc = false;
				if (genderCDId != null)
					gc = true;
				if (raceCDId != null)
					rc = true;

				if (rc && gc) {
					ps = con
							.prepareStatement("insert into PARTICIPANT (ID, BIRTH_DATE, INITIALS, LAST_NAME, FIRST_NAME, ETHNIC_GROUP_CODE, CTOM_INSERT_DATE, ADM_GNDR_CONCEPT_DESCRIPTOR_ID,RACE_CONCEPT_DESCRIPTOR_ID )  values(?,?,?,?,?,?,?,?,?)");
					ps.setLong(8, genderCDId);
					ps.setLong(9, raceCDId);
				} else if (!rc && !gc) {
					ps = con
							.prepareStatement("insert into PARTICIPANT (ID, BIRTH_DATE, INITIALS, LAST_NAME, FIRST_NAME, ETHNIC_GROUP_CODE, CTOM_INSERT_DATE)  values(?,?,?,?,?,?,?)");

				} else if (!rc) {
					ps = con
							.prepareStatement("insert into PARTICIPANT (ID, BIRTH_DATE, INITIALS, LAST_NAME, FIRST_NAME, ETHNIC_GROUP_CODE, CTOM_INSERT_DATE, ADM_GNDR_CONCEPT_DESCRIPTOR_ID )  values(?,?,?,?,?,?,?,?)");
					ps.setLong(8, genderCDId);
				} else if (!gc) {
					ps = con
							.prepareStatement("insert into PARTICIPANT (ID, BIRTH_DATE, INITIALS, LAST_NAME, FIRST_NAME, ETHNIC_GROUP_CODE, CTOM_INSERT_DATE, RACE_CONCEPT_DESCRIPTOR_ID )  values(?,?,?,?,?,?,?,?)");
					ps.setLong(8, raceCDId);
				}

				ps.setLong(1, participant.getId());
				if (participant.getBirthDate() != null)
					ps.setDate(2, new java.sql.Date(participant.getBirthDate()
							.getTime()));
				else
					ps.setDate(2, null);

				ps.setString(3, participant.getInitials());
				ps.setString(4, participant.getLastName());
				ps.setString(5, participant.getFirstName());
				ps.setString(6, participant.getEthnicGroupCode());
				if (participant.getCtomInsertDate() == null)
					ps.setTimestamp(7, new java.sql.Timestamp(new Date()
							.getTime()));
				else
					ps.setTimestamp(7, new java.sql.Timestamp(participant
							.getCtomInsertDate().getTime()));
				ps.execute();
				con.commit();

				if (identifierUpdInd && participant.getIdentifier() != null) {
					updateIdentifier(con, participant);
				}
			} // update into participant if there was a participant associated
			// with identifier.
			else {
				ps = con
						.prepareStatement("update PARTICIPANT set BIRTH_DATE = ?, INITIALS = ?, LAST_NAME = ?, FIRST_NAME = ?, ETHNIC_GROUP_CODE=?, ADM_GNDR_CONCEPT_DESCRIPTOR_ID=?,RACE_CONCEPT_DESCRIPTOR_ID=?,CTOM_UPDATE_DATE=? where ID = ?");
				if (participant.getBirthDate() != null)
					ps.setDate(1, new java.sql.Date(participant.getBirthDate()
							.getTime()));
				else
					ps.setDate(1, null);
				ps.setString(2, participant.getInitials());
				ps.setString(3, participant.getLastName());
				ps.setString(4, participant.getFirstName());
				ps.setString(5, participant.getEthnicGroupCode());
				ps.setLong(6, genderCDId);
				ps.setLong(7, raceCDId);
				if (participant.getCtomInsertDate() == null)
					ps.setTimestamp(8, new java.sql.Timestamp(new Date()
							.getTime()));
				else
					ps.setTimestamp(8, new java.sql.Timestamp(participant
							.getCtomInsertDate().getTime()));
				ps.executeUpdate();
				con.commit();
			}
		} finally {
			if (ps != null) {
				ps.close();
			}
			if (rs != null) {
				rs.close();
			}
		}
	}

	/**
	 * Persists the identifier object into the database. The identifier is
	 * associated with the participant object.If the identifier object is
	 * already present, the method updates the identifier else insert the
	 * identifier.
	 * 
	 * @param con Connection
	 * @param participant Particiapant
	 * @throws SQLException
	 */
	private void saveOrInsertIdentifier(Connection con, Participant participant)
			throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Long id = null;
		Long identifierId = null;
		// check if the participant is associated with identifier
		try {
			if (participant.getIdentifier() != null) {
				ps = con
						.prepareStatement("select ID,PARTICIPANT_ID from IDENTIFIER where EXTENSION = ? AND PARTICIPANT_ID IS NOT NULL");
				ps.setString(1, participant.getIdentifier().getExtension());
				rs = ps.executeQuery();

				// check if identifier is in DB
				if (rs.next() && !rs.isBeforeFirst()
						&& rs.getLong("PARTICIPANT_ID") != 0) {
					// already present;update the identifier table
					id = rs.getLong("PARTICIPANT_ID");
					participant.setId(id);
					participant.getIdentifier().setId(rs.getLong("ID"));
				} else {
					// get the identifier id
					ps = con
							.prepareStatement("select nextval('IDENTIFIER_SEQ')");
					rs = ps.executeQuery();
					while (rs.next()) {
						identifierId = rs.getLong(1);
						break;
					}
					// insert new into identifier get the identifierid and insert
					// into participant table
					ps = con
							.prepareStatement("insert into IDENTIFIER (Id,EXTENSION, SOURCE,ROOT,assigning_authority_name) values (?,?,?,?,?)");
					// need to set the participantid into the identifier table
					ps.setLong(1, identifierId);
					ps.setString(2, participant.getIdentifier().getExtension());
					ps.setString(3, participant.getIdentifier().getSource());
					ps.setString(4, participant.getIdentifier().getRoot());
					ps.setString(5, participant.getIdentifier()
							.getAssigningAuthorityName());
					ps.executeUpdate();
					con.commit();

					participant.setId(id);
					participant.getIdentifier().setId(identifierId);
				}
			}
		} finally {
			if (ps != null) {
				ps.close();
			}
			if (rs != null) {
				rs.close();
			}
		}

	}

	/**
	 * Persists the identifier object into the database. The identifier is
	 * associated with the protocol object.If the identifier object is
	 * already present, the method updates the identifier else insert the
	 * identifier.
	 * 
	 * @param con Connection
	 * @param protocol Protocol
	 * @throws SQLException
	 */
	private void saveOrInsertIdentifier(Connection con, Protocol protocol)
			throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Long id = null;
		Long identifierId = null;
		// check if the protocol is associated with identifier
		try {
			if (protocol.getIdentifier() != null) {
				ps = con
						.prepareStatement("select ID,PROTOCOL_ID from IDENTIFIER where EXTENSION = ? AND PROTOCOL_ID IS NOT NULL");
				ps.setString(1, protocol.getIdentifier().getExtension());
				rs = ps.executeQuery();

				// check if identifier is in DB
				if (rs.next() && !rs.isBeforeFirst()
						&& rs.getLong("PROTOCOL_ID") != 0) {
					// already present;update the identifier table
					id = rs.getLong("PROTOCOL_ID");
					protocol.setId(id);
					protocol.getIdentifier().setId(rs.getLong("ID"));
				} else {
					// get the identifier id
					ps = con
							.prepareStatement("select nextval('IDENTIFIER_SEQ')");
					rs = ps.executeQuery();
					while (rs.next()) {
						identifierId = rs.getLong(1);
						break;
					}
					// insert new into identifier get the identifierid and insert
					// into participant table
					ps = con
							.prepareStatement("insert into IDENTIFIER (Id,EXTENSION, SOURCE,ROOT,assigning_authority_name) values (?,?,?,?,?)");
					// need to set the participantid into the identifier table
					ps.setLong(1, identifierId);
					ps.setString(2, protocol.getIdentifier().getExtension());
					ps.setString(3, protocol.getIdentifier().getSource());
					ps.setString(4, protocol.getIdentifier().getRoot());
					ps.setString(5, protocol.getIdentifier()
							.getAssigningAuthorityName());
					ps.executeUpdate();
					con.commit();

					protocol.setId(id);
					protocol.getIdentifier().setId(identifierId);
				}
			}
		} finally {
			if (ps != null) {
				ps.close();
			}
			if (rs != null) {
				rs.close();
			}
		}

	}

	/**
	 * Updates the Identifier table with the Participant Id
	 * @param con Connection 
	 * @param participant Participant
	 * @throws SQLException
	 */
	private void updateIdentifier(Connection con, Participant participant)
			throws SQLException {
		PreparedStatement ps = null;
		try {
			ps = con
					.prepareStatement("update IDENTIFIER set PARTICIPANT_ID = ? where ID= ?");
			// need to set the participantid into the identifier table
			ps.setLong(1, participant.getId());
			ps.setLong(2, participant.getIdentifier().getId());
			ps.executeUpdate();
			con.commit();
		} finally {
			if (ps != null) {
				ps.close();
			}
		}

	}

	/**
	 * Persists the identifier object into the database. The identifier is
	 * associated with the Study Participant Assignment object.If the identifier object is
	 * already present, the method updates the identifier else insert the
	 * identifier.
	 * 
	 * @param con
	 * @param protocol
	 * @throws SQLException
	 */
	private Long saveOrInsertIdentifier(Connection con,
			StudyParticipantAssignment spa) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Long id = null;
		Long identifierId = null;
		// check if the StudyParticipantAssignment is associated with identifier

		try {
			if (spa.getIdentifier() != null) {
				ps = con
						.prepareStatement("select ID,STUDY_PARTICIPANT_ASSIGNMNT_ID from IDENTIFIER where EXTENSION = ? AND STUDY_PARTICIPANT_ASSIGNMNT_ID IS NOT NULL");
				ps.setString(1, spa.getIdentifier().getExtension());
				rs = ps.executeQuery();

				// check if identifier is in DB
				if (rs.next() && !rs.isBeforeFirst()
						&& rs.getLong("STUDY_PARTICIPANT_ASSIGNMNT_ID") != 0) {
					// already present;update the identifier table
					id = rs.getLong("STUDY_PARTICIPANT_ASSIGNMNT_ID");
					spa.setId(id);
					spa.getIdentifier().setId(rs.getLong("ID"));
					identifierId = rs.getLong("ID");
				} else {
					// get the identifier id
					ps = con
							.prepareStatement("select nextval('IDENTIFIER_SEQ')");
					rs = ps.executeQuery();
					while (rs.next()) {
						identifierId = rs.getLong(1);
						break;
					}
					// insert new into identifier get the identifierid and insert
					// into participant table
					ps = con
							.prepareStatement("insert into IDENTIFIER (Id,EXTENSION, SOURCE,ROOT) values (?,?,?,?)");
					// need to set the participantid into the identifier table
					ps.setLong(1, identifierId);
					ps.setString(2, spa.getIdentifier().getExtension());
					ps.setString(3, spa.getIdentifier().getSource());
					ps.setString(4, spa.getIdentifier().getRoot());
					ps.executeUpdate();
					con.commit();

					spa.setId(id);
					spa.getIdentifier().setId(identifierId);

				}

			}
		} finally {
			if (ps != null) {
				ps.close();
			}
			if (rs != null) {
				rs.close();
			}
		}
		return identifierId;

	}

	/**
	 * Updates Identifier with Study Participant Assignment Id.
	 * @param con Connection
	 * @param participant Participant
	 * @throws SQLException
	 */
	private void updateIdentifier(Connection con,
			StudyParticipantAssignment studypartAssign, Long identifierId)
			throws SQLException {

		PreparedStatement ps = null;
		try {
			ps = con
					.prepareStatement("update IDENTIFIER set STUDY_PARTICIPANT_ASSIGNMNT_ID = ? where ID= ?");
			// need to set the Study participant Assignemnt id into the identifier table
			ps.setLong(1, studypartAssign.getId());
			ps.setLong(2, identifierId);
			ps.executeUpdate();
			con.commit();
		} finally {
			if (ps != null) {
				ps.close();
			}
		}

	}

	/**
	 * Updates Identifier with Protocol Id
	 * @param con Connection
	 * @param protocol Protocol
	 * @throws SQLException
	 */
	private void updateIdentifier(Connection con, Protocol protocol)
			throws SQLException {

		PreparedStatement ps = null;
		try {
			ps = con
					.prepareStatement("update IDENTIFIER set PROTOCOL_ID = ? where ID= ?");
			// need to set the protocolid into the identifier table
			ps.setLong(1, protocol.getId());
			ps.setLong(2, protocol.getIdentifier().getId());
			ps.executeUpdate();
			con.commit();
		} finally {
			if (ps != null) {
				ps.close();
			}
		}

	}

	/**
	 * Updates the participant with gender and race concept descriptor 
	 * @param con Connection
	 * @param participant Participant 
	 * @throws SQLException
	 */
	private void updateParticipant(Connection con, Participant participant)
			throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Long personId = null;
		Statement stmt = null;

		Long genderCDId = insertOrsaveConceptDescriptor(con, participant
				.getAdminGenderCode(), null, null);
		Long raceCDId = insertOrsaveConceptDescriptor(con, participant
				.getRaceCode(), null, null);

		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("select nextval('PERSON_SEQ')");
			rs.next();
			personId = rs.getLong(1);
			participant.setId(personId);
			boolean gc = false;
			boolean rc = false;
			if (genderCDId != null)
				gc = true;
			if (raceCDId != null)
				rc = true;
			if (rc && gc) {

				ps = con
						.prepareStatement("update  PARTICIPANT set BIRTH_DATE = ?, BIRTH_DATE_ORIG = ?, INITIALS = ?, LAST_NAME = ?, FIRST_NAME = ?, MARITAL_STATUS_CODE = ?, ZIP_CODE = ?, COUNTRY_CODE = ?, ADM_GNDR_CONCEPT_DESCRIPTOR_ID = ?,RACE_CONCEPT_DESCRIPTOR_ID = ?  where PERSON_ID = ?");
				ps.setLong(7, genderCDId);
				ps.setLong(8, raceCDId);
			} else if (!rc && !gc) {

				ps = con
						.prepareStatement("update into PARTICIPANT set BIRTH_DATE = ?, BIRTH_DATE_ORIG = ?, INITIALS = ?, LAST_NAME = ?, FIRST_NAME = ?, MARITAL_STATUS_CODE = ?, ZIP_CODE = ?, COUNTRY_CODE = ?  where PERSON_ID = ?");

			} else if (!rc) {
				ps = con
						.prepareStatement("update into PARTICIPANT set BIRTH_DATE = ?, BIRTH_DATE_ORIG = ?, INITIALS = ?, LAST_NAME = ?, FIRST_NAME = ?, MARITAL_STATUS_CODE = ?, ZIP_CODE = ?, COUNTRY_CODE = ?, ADM_GNDR_CONCEPT_DESCRIPTOR_ID = ? where PERSON_ID =?");
				ps.setLong(7, genderCDId);

			} else if (!gc) {
				ps = con
						.prepareStatement("update into PARTICIPANT set BIRTH_DATE = ?, BIRTH_DATE_ORIG = ?, INITIALS = ?, LAST_NAME = ?, FIRST_NAME = ?, MARITAL_STATUS_CODE = ?, ZIP_CODE = ?, COUNTRY_CODE = ?, RACE_CONCEPT_DESCRIPTOR_ID = ? where PERSON_ID = ?");
				ps.setLong(7, raceCDId);
			}
			ps.setLong(1, personId);
			if (participant.getBirthDate() != null)
				ps.setDate(1, new java.sql.Date(participant.getBirthDate()
						.getTime()));
			else
				ps.setDate(1, null);
			ps.setString(2, participant.getBirthDateOrig());
			ps.setString(3, participant.getInitials());
			ps.setString(4, participant.getLastName());
			ps.setString(5, participant.getFirstName());
			ps.setString(6, participant.getMaritalStatusCode());
			ps.setString(7, participant.getZipCode());
			ps.setString(8, participant.getCountryCode());
			ps.execute();
		} finally {
			if (ps != null) {
				ps.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			if (rs != null) {
				rs.close();
			}
		}

	}

	/**
	 * saveActivity persists the Activity into CTODS Database
	 * @param con Connection
	 * @param spaId StudyParticipantAssignment Id
	 * @param act Activity
	 * @throws SQLException
	 */
	private void saveActivity(Connection con, Long spaId, Activity act)
			throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Long actId = null;
		Statement stmt = null;

		try {
			logger.debug("Saving Activity");
			// Get Id from sequence
			stmt = con.createStatement();
			rs = stmt.executeQuery("select nextval('ACTIVITY_SEQ')");
			rs.next();
			actId = rs.getLong(1);
			act.setId(actId);
			// insert into Activity
			ps = con
					.prepareStatement("insert into ACTIVITY (ID, STUDY_PARTICIPANT_ASSIGNMNT_ID, START_DATE_ORIG, STOP_DATE_ORIG,START_DATE,STOP_DATE)  values(?,?,?,?,?,?)");
			ps.setLong(1, actId);
			ps.setLong(2, spaId);
			ps.setString(3, String.valueOf(act.getStartDateOrig()));
			ps.setString(4, String.valueOf(act.getStartDateOrig()));
			try {
				java.util.Date startTmp = new SimpleDateFormat("yyyyMMddHHmmss")
						.parse(act.getStartDateOrig());
				java.util.Date stopTmp = new SimpleDateFormat("yyyyMMddHHmmss")
						.parse(act.getStartDateOrig());
				ps.setTimestamp(5, new java.sql.Timestamp(startTmp.getTime()));
				ps.setTimestamp(6, new java.sql.Timestamp(stopTmp.getTime()));
				ps.execute();
			} catch (ParseException e) {
				logger.error("error creating activity", e);
			}
			if (act.getObservation() != null) {
				act.getObservation().setActivityId(act.getId());
				//Saves Observation
				saveObservation(con, act.getObservation());
			}
			if (act.getProcedure() != null) {
				act.getProcedure().setId(act.getId());
				//Saves Procedure
				saveProcedure(con, act.getProcedure());
			}
			if (act.getStudyTimePoint() != null) {
				act.getStudyTimePoint().setActivityId(act.getId());
				//Saves Study time Point
				saveStudyTimePoint(con, act.getStudyTimePoint());
			}
		} finally {
			if (ps != null) {
				ps.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			if (rs != null) {
				rs.close();
			}
		}
	}

	/**
	 * saveStudyTimePoint persists Study Time Point into CTODS database
	 * @param con Connection
	 * @param studyTimePoint StudyTimePoint
	 * @throws SQLException
	 */
	private void saveStudyTimePoint(Connection con,
			StudyTimePoint studyTimePoint) throws SQLException {
		logger.debug("Saving the Study Time Point");
		PreparedStatement ps = null;
		ResultSet rs = null;
		Statement stmt = null;

		try {
			// Get Id from sequence
			stmt = con.createStatement();
			rs = stmt.executeQuery("select nextval('STUDY_TIME_POINT_SEQ')");
			rs.next();
			studyTimePoint.setId(rs.getLong(1));
			// insert into STUDY_TIME_POINT
			ps = con
					.prepareStatement("insert into STUDY_TIME_POINT (ID, ACTIVITY_ID, VISIT_NAME)  values(?,?,?)");
			ps.setLong(1, studyTimePoint.getId());
			ps.setLong(2, studyTimePoint.getActivityId());
			ps.setString(3, studyTimePoint.getVisitName());
			ps.execute();
		} finally {
			if (ps != null) {
				ps.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			if (rs != null) {
				rs.close();
			}
		}

	}

	/**
	 * saveProcedure persists the Procedure data into CTODS database
	 * @param con Connection
	 * @param procedure Procedure
	 * @throws SQLException
	 */
	private void saveProcedure(Connection con, Procedure procedure)
			throws SQLException {
		logger.debug("Saving the Procedure");
		PreparedStatement ps = null;
		try {
			ps = con
					.prepareStatement("insert into PROCEDURE (ID, FASTING_STATUS)  values(?,?)");
			ps.setLong(1, procedure.getId());
			ps.setString(2, procedure.getFastingStatus());
			ps.execute();
			if (procedure.getSpecimenCollection() != null) {
				procedure.getSpecimenCollection().setProcedureActivityId(
						procedure.getId());
				//save SpecimenCollection
				saveSpecimenCollection(con, procedure.getSpecimenCollection());
			}
		} finally {
			if (ps != null) {
				ps.close();
			}
		}

	}

	/**
	 * saveSpecimenCollection persists the Specimen Collection Data into CTODS database.
	 * @param con Connection
	 * @param specimenCollection SpecimenCollection
	 * @throws SQLException
	 */
	private void saveSpecimenCollection(Connection con,
			SpecimenCollection specimenCollection) throws SQLException {
		logger.debug("Saving the Specimen Collection");
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			if (specimenCollection.getCentralLaboratory() != null) {
				//save Central Laboratory data
				saveCentralLaboratory(con, specimenCollection
						.getCentralLaboratory());
				// Get Id from sequence
				/* Statement stmt = con.createStatement();
				 rs = stmt.executeQuery("select nextval('ACTIVITY_SEQ')");
				 rs.next();
				 Long id = rs.getLong(1);*/
				//ps = con.prepareStatement("insert into SPECIMEN_COLLECTION (PROCEDURE_ACTIVITY_ID, CENTRAL_LABORATORY_ORG_ID)  values(?,?)");
				ps = con
						.prepareStatement("insert into SPECIMEN_COLLECTION (ID,CENTRAL_LABORATORY_ID)  values(?,?)");
				ps.setLong(1, specimenCollection.getProcedureActivityId());
				ps
						.setLong(2, specimenCollection.getCentralLaboratory()
								.getId());
				ps.execute();

			}
		} finally {
			if (ps != null) {
				ps.close();
			}
			if (rs != null) {
				rs.close();
			}
		}
		if (specimenCollection.getSpecimen() != null) {
			specimenCollection.getSpecimen().setProcedureActivityId(
					specimenCollection.getProcedureActivityId());
			//save Specimen
			saveSpecimen(con, specimenCollection.getSpecimen());

		}
	}

	/**
	 * saveSpecimen persists the Specimen data to CTODS database.
	 * @param con Connection
	 * @param specimen Specimen
	 * @throws SQLException
	 */
	private void saveSpecimen(Connection con, Specimen specimen)
			throws SQLException {
		logger.debug("Saving the Specimen");
		PreparedStatement ps = null;
		ResultSet rs = null;
		Statement stmt = null;
		Long sampleTypeCDId = insertOrsaveConceptDescriptor(con, specimen
				.getVolumeUOMCd(), null, null);

		try {
			// Get Id from sequence 
			stmt = con.createStatement();
			rs = stmt.executeQuery("select nextval('SPECIMEN_SEQ')");
			rs.next();
			specimen.setId(rs.getLong(1));
			boolean sc = false;
			if (sampleTypeCDId != null)
				sc = true;
			if (sc) {
				ps = con
						.prepareStatement("insert into SPECIMEN(ID, ACCESSION_NUMBER, COMMENTS_FROM_LABORATORY, COMMENTS_FROM_INVESTIGATOR, CONDITION,SPECIMEN_COLLECTION_ID, SAMPLE_IDENTIFIER_ORIG,SMPLTYPE_CONCEPT_DESCRIPTOR_ID) values(?,?,?,?,?,?,?,?)");
				ps.setLong(8, sampleTypeCDId);
			} else {
				ps = con
						.prepareStatement("insert into SPECIMEN(ID, ACCESSION_NUMBER, COMMENTS_FROM_LABORATORY, COMMENTS_FROM_INVESTIGATOR, CONDITION,SPECIMEN_COLLECTION_ID,SAMPLE_IDENTIFIER_ORIG) values(?,?,?,?,?,?,?)");
			}
			ps.setLong(1, specimen.getId());
			ps.setString(2, specimen.getAccessionNumber());
			ps.setString(3, specimen.getCommentsFromLaboratory());
			ps.setString(4, specimen.getCommentsFromInvestigator());
			ps.setString(5, specimen.getCondition());
			ps.setLong(6, specimen.getProcedureActivityId());
			ps.setString(7, specimen.getSampleIdentifierOrig());
			ps.execute();
		} finally {
			if (ps != null) {
				ps.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			if (rs != null) {
				rs.close();
			}
		}
	}

	/**
	 * Get the Participant Person Id of the Study Participant Assignment
	 * @param con Connection
	 * @param studyParticipantIdentifier StudyParticipantIdentifier
	 * @return ParticipantId
	 * @throws SQLException
	 */
	protected Long getStudyParticipantAssignmentPersonId(Connection con,
			String studyParticipantIdentifier) throws SQLException {

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = con
					.prepareStatement("select PARTICIPANT_ID from STUDY_PARTICIPANT_ASSIGNMENT where STUDY_PARTICIPANT_IDENTFR_ORIG = ?");
			ps.setString(1, studyParticipantIdentifier);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getLong(1);
			} else {
				return null;
			}
		} finally {
			if (ps != null) {
				ps.close();
			}
			if (rs != null) {
				rs.close();
			}
		}

	}

	/**
	 * saveCentralLaboratory persists Central Laboratory data to CTODS database.
	 * @param con connection
	 * @param centralLaboratory CentralLaboratory 
	 * @throws SQLException
	 */
	private void saveCentralLaboratory(Connection con,
			CentralLaboratory centralLaboratory) throws SQLException {
		logger.debug("Saving the Central Laboratory");
		PreparedStatement ps = null;
		ResultSet rs = null;
		Statement stmt = null;
		try {
			ps = con
					.prepareStatement("select ID from CENTRAL_LABORATORY where IDENTIFIER = ?");
			ps.setString(1, centralLaboratory.getIdentifier());
			rs = ps.executeQuery();
			if (rs.next()) {
				centralLaboratory.setId(rs.getLong(1));
			} else {

				// Get Id from sequence
				stmt = con.createStatement();
				rs = stmt.executeQuery("select nextval('ORGANIZATION_SEQ')");
				rs.next();
				centralLaboratory.setId(rs.getLong(1));

				ps = con
						.prepareStatement("insert into CENTRAL_LABORATORY(ID, IDENTIFIER, NAME)  values(?,?,?)");
				ps.setLong(1, centralLaboratory.getId());
				ps.setString(2, centralLaboratory.getIdentifier());
				ps.setString(3, centralLaboratory.getName());
				ps.execute();
			}
		} finally {
			if (ps != null) {
				ps.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			if (rs != null) {
				rs.close();
			}
		}
	}

	/**
	 * saveObservation persists  Observation data to CTODS databse
	 * @param con Connection
	 * @param observation Observation
	 * @throws SQLException
	 */
	private void saveObservation(Connection con, Observation observation)
			throws SQLException {
		logger.debug("Saving Observation");
		PreparedStatement ps = null;
		ResultSet rs = null;
		Statement stmt = null;
		try {
			// Get Id from sequence
			stmt = con.createStatement();
			rs = stmt.executeQuery("select nextval('OBSERVATION_SEQ')");
			rs.next();
			observation.setId(rs.getLong(1));
			// insert into Observation
			ps = con
					.prepareStatement("insert into OBSERVATION (ID, REPORTING_DATE, REPORTING_DATE_ORIG, CONFIDENTIALITY_CODE, STATUS_CODE, COMMENTS, ACTIVITY_ID)  values(?,?,?,?,?,?,?)");
			ps.setLong(1, observation.getId());
			if (observation.getReportingDate() != null)
				ps.setDate(2, new java.sql.Date(observation.getReportingDate()
						.getTime()));
			else
				ps.setDate(2, null);
			ps.setString(3, observation.getReportingDateOrig());
			ps.setString(4, observation.getConfidentialityCode());
			ps.setString(5, observation.getStatusCode());
			ps.setString(6, observation.getComments());
			ps.setLong(7, observation.getActivityId());
			ps.execute();
		} finally {
			if (ps != null) {
				ps.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			if (rs != null) {
				rs.close();
			}
		}
		if (observation.getClinicalResult() != null) {
			observation.getClinicalResult().setObservationId(
					observation.getId());
			//saves Clinical Results
			saveClinicalResult(con, observation.getClinicalResult());
		}

	}

	/**
	 * saveClinicalResult persists the Clinical Result to CTODS database.
	 * @param con Connection
	 * @param clinicalResult Clinical Result
	 * @throws SQLException
	 */
	private void saveClinicalResult(Connection con,
			ClinicalResult clinicalResult) throws SQLException {
		Long valUOMCdId = null;
		Long labTestCdId = null;

		if (clinicalResult.getPerformingLaboratory() != null) {
			//Save Performing Laboratory
			savePerformingLaboratory(con, clinicalResult
					.getPerformingLaboratory());
			//update/insert ConceptDescriptor with UOM
			valUOMCdId = insertOrsaveConceptDescriptor(con, clinicalResult
					.getValueUnitOfMeasureCd(), clinicalResult
					.getValueUnitOfMeasureCdSystem(), clinicalResult
					.getValueUnitOfMeasureCdSystemName(), null, null);
			//update/insert ConceptDescriptor with Clinical result codes
			labTestCdId = insertOrsaveConceptDescriptor(con, clinicalResult
					.getCrCode(), clinicalResult.getCrCodeSystem(),
					clinicalResult.getCrCodeSystemName(), clinicalResult
							.getCrCodeSystemVersion(), clinicalResult
							.getCrCodeDisplayText());

		}

		if (labTestCdId == null) // This is a required field in the database
			return;
		PreparedStatement ps = null;

		try {
			boolean val = false;
			boolean lt = false;
			if (valUOMCdId != null)
				val = true;
			if (labTestCdId != null)
				lt = true;
			if (lt && val) {
				ps = con
						.prepareStatement("insert into CLINICAL_RESULT (ID, LAB_TECHNIQUE_CODE, PANEL_NAME, PERFORMING_LABORATORY_ID, REFERENCE_FLAG, VALUE, REFERENCE_RANGE_LOW, REFERENCE_RANGE_HIGH, VALUEUOM_CONCEPT_DESCRIPTOR_ID, LAB_TEST_CONCEPT_DESCRIPTOR_ID)  values(?,?,?,?,?,?,?,?,?,?)");
				ps.setLong(9, valUOMCdId);
				ps.setLong(10, labTestCdId);
			} else if (!lt && !val) {
				ps = con
						.prepareStatement("insert into CLINICAL_RESULT (ID, LAB_TECHNIQUE_CODE, PANEL_NAME, PERFORMING_LABORATORY_ID, REFERENCE_FLAG, VALUE, REFERENCE_RANGE_LOW, REFERENCE_RANGE_HIGH)  values(?,?,?,?,?,?,?,?)");
			} else if (!lt) {
				ps = con
						.prepareStatement("insert into CLINICAL_RESULT (ID, LAB_TECHNIQUE_CODE, PANEL_NAME, PERFORMING_LABORATORY_ID, REFERENCE_FLAG, VALUE, REFERENCE_RANGE_LOW, REFERENCE_RANGE_HIGH, VALUEUOM_CONCEPT_DESCRIPTOR_ID)  values(?,?,?,?,?,?,?,?,?)");
				ps.setLong(9, valUOMCdId);
			} else if (!val) {
				ps = con
						.prepareStatement("insert into CLINICAL_RESULT (ID, LAB_TECHNIQUE_CODE, PANEL_NAME, PERFORMING_LABORATORY_ID, REFERENCE_FLAG, VALUE, REFERENCE_RANGE_LOW, REFERENCE_RANGE_HIGH, LAB_TEST_CONCEPT_DESCRIPTOR_ID)  values(?,?,?,?,?,?,?,?,?)");
				ps.setLong(9, labTestCdId);
			}
			ps.setLong(1, clinicalResult.getObservationId());
			ps.setString(2, clinicalResult.getLabTechCd());
			ps.setString(3, clinicalResult.getPanelName());
			ps.setLong(4, clinicalResult.getPerformingLaboratory()
					.getOrganizationId());
			ps.setString(5, clinicalResult.getRefFlag());
			ps.setString(6, clinicalResult.getValue());
			ps.setDouble(7, clinicalResult.getRefRangeLow());
			ps.setDouble(8, clinicalResult.getRefRangeHigh());
			ps.execute();
		} finally {
			if (ps != null) {
				ps.close();
			}
		}

	}

	/**
	 * insertOrsaveConceptDescriptor persists the Concept Descriptor data into CTODS database
	 * @param con Connection
	 * @param code Clinical Result Code
	 * @param codeSystemVersion Clinical Result Code system version
	 * @param displayText Clinical Result display text
	 * @return Concept Descriptor Id
	 * @throws SQLException
	 */
	private Long insertOrsaveConceptDescriptor(Connection con, String code,
			String codeSystem, String codeSystemName, Double codeSystemVersion,
			String displayText) throws SQLException {

		if (code == null)
			return null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Long id = null;
		try {
			ps = con
					.prepareStatement("select id from concept_descriptor where code = ?");
			ps.setString(1, code);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getLong(1);
			} else {
				Statement stmt = con.createStatement();

				rs = stmt
						.executeQuery("select nextval('CONCEPT_DESCRIPTOR_SEQ')");
				rs.next();
				id = rs.getLong(1);
				ps = con
						.prepareStatement("insert into CONCEPT_DESCRIPTOR (ID,CODE,CODE_SYSTEM,CODE_SYSTEM_NAME,CODE_SYSTEM_VERSION, DISPLAY_TEXT)  values(?,?,?,?,?,?)");
				ps.setLong(1, id);
				ps.setString(2, code);
				ps.setString(3, codeSystem);
				ps.setString(4, codeSystemName);
				if (codeSystemVersion != null)
					ps.setDouble(5, codeSystemVersion);
				else
					ps.setDouble(5, 0);
				ps.setString(6, displayText);
				ps.execute();
				con.commit();

				return id;
			}
		} finally {
			if (ps != null) {
				ps.close();
			}
			if (rs != null) {
				rs.close();
			}
		}

	}

	/**
	 * insertOrsaveConceptDescriptor persists the Concept Descriptor data into CTODS database
	 * @param con Connection
	 * @param code Clinical Result Code
	 * @param codeSystemVersion Clinical Result Code system version
	 * @param displayText Clinical Result display text
	 * @return Concept Descriptor Id
	 * @throws SQLException
	 * 
	 */
	private Long insertOrsaveConceptDescriptor(Connection con, String code,
			Double codeSystemVersion, String displayText) throws SQLException {

		if (code == null)
			return null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Long id = null;
		ps = con
				.prepareStatement("select id from concept_descriptor where code = ?");
		ps.setString(1, code);
		rs = ps.executeQuery();
		if (rs.next()) {
			return rs.getLong(1);
		} else {
			Statement stmt = con.createStatement();

			rs = stmt.executeQuery("select nextval('CONCEPT_DESCRIPTOR_SEQ')");
			rs.next();
			id = rs.getLong(1);
			ps = con
					.prepareStatement("insert into CONCEPT_DESCRIPTOR (ID,CODE, CODE_SYSTEM_VERSION, DISPLAY_TEXT)  values(?,?,?,?)");
			ps.setLong(1, id);
			ps.setString(2, code);
			if (codeSystemVersion != null)
				ps.setDouble(3, codeSystemVersion);
			else
				ps.setDouble(3, 0);
			ps.setString(4, displayText);
			ps.execute();
			con.commit();

			return id;
		}

	}

	/**
	 * @param con
	 * @param performingLaboratory
	 * @throws SQLException
	 */
	private void savePerformingLaboratory(Connection con,
			PerformingLaboratory performingLaboratory) throws SQLException {
		logger.debug("Saving Performing Laboratory");
		PreparedStatement ps = null;
		ResultSet rs = null;

		// Get Id from sequence

		ps = con
				.prepareStatement("select ID from PERFORMING_LABORATORY where IDENTIFIER = ?");
		ps.setString(1, performingLaboratory.getPlIdentifier());
		rs = ps.executeQuery();
		if (rs.next()) {
			performingLaboratory.setOrganizationId(rs.getLong(1));
		} else {

			Statement stmt = con.createStatement();
			rs = stmt.executeQuery("select nextval('ORGANIZATION_SEQ')");
			rs.next();
			performingLaboratory.setOrganizationId(rs.getLong(1));

			ps = con
					.prepareStatement("insert into PERFORMING_LABORATORY (ID, IDENTIFIER, NAME)  values(?,?,?)");
			ps.setLong(1, performingLaboratory.getOrganizationId());
			ps.setString(2, performingLaboratory.getPlIdentifier());
			ps.setString(3, performingLaboratory.getPlName());
			ps.execute();
		}
	}

	/**
	 * saveInvestigator  persists Investigator data into CTODS database
	 * @param con Connection
	 * @param protId Protocol Id
	 * @param inv Investigator 
	 * @throws SQLException
	 */
	private void saveInvestigator(Connection con, Long protId, Investigator inv)
			throws SQLException {
		logger.debug("Saving the Investigator");
		PreparedStatement ps = null;
		ResultSet rs = null;
		Long id = null;
		Statement stmt = null;

		try {
			//Check if the investigator exists in the db -use the Id.
			ps = con
					.prepareStatement("select ID from INVESTIGATOR  where NCI_IDENTIFIER = ?");
			ps.setString(1, inv.getNciId());
			rs = ps.executeQuery();
			if (rs.next()) {
				id = rs.getLong(1);
			} else {

				// Get Id from sequence
				stmt = con.createStatement();
				rs = stmt.executeQuery("select nextval('PERSON_SEQ')");
				rs.next();
				id = rs.getLong(1);
				// insert into Investigator
				ps = con
						.prepareStatement("insert into investigator (ID, NCI_IDENTIFIER, LAST_NAME, FIRST_NAME)  values(?,?,?,?)");

				ps.setLong(1, id);
				ps.setString(2, String.valueOf(inv.getNciId() != null ? inv
						.getNciId() : ""));
				ps.setString(3, String.valueOf(inv.getLastName() != null ? inv
						.getLastName() : ""));
				ps.setString(4, String.valueOf(inv.getFirstName() != null ? inv
						.getFirstName() : ""));
				ps.execute();

			}
			// insert into Study_Investigator
			ps = con
					.prepareStatement("select id from study_investigator where PROTOCOL_ID = ? AND INVESTIGATOR_ID = ?");
			ps.setLong(1, protId);
			ps.setLong(2, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				return;
			} else {

				Long siId = null;
				stmt = con.createStatement();
				rs = stmt
						.executeQuery("select nextval('STUDY_INVESTIGATOR_SEQ')");
				rs.next();
				siId = rs.getLong(1);
				logger.debug("The study_Invest id is " + siId);
				ps = con
						.prepareStatement("insert into study_investigator (ID, PROTOCOL_ID, INVESTIGATOR_ID)  values(?,?,?)");

				ps.setLong(1, siId);
				ps.setLong(2, protId);
				ps.setLong(3, id);
				ps.execute();
			}
		} finally {
			if (ps != null) {
				ps.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			if (rs != null) {
				rs.close();
			}
		}
	}

	/**
	 * updateStudyParticipantAssignment updates the Study Participant Assignment
	 * @param con Connection
	 * @param assignment Study Participant Assignment
	 * @throws SQLException
	 */
	public void updateStudyParticipantAssignment(Connection con,
			StudyParticipantAssignment assignment) throws SQLException {

		if (assignment == null)
			return;
		//retrieves the person Id
		Long personId = getStudyParticipantAssignmentPersonId(con, assignment
				.getStudyPartIdOrig());
		Participant part = assignment.getParticipant();
		part.setId(personId);
		//Saves the participant.
		saveParticipant(con, part);
	}

	/**
	 * rollbackParticipant rolls back participant information 
	 * @param con Connection
	 * @param participant Participant
	 * @throws SQLException
	 */
	public void rollbackParticipant(Connection con, String participantGridId,
			String participantExtension) throws SQLException {
		logger.debug("Received a participant Rollback message");
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		ResultSet rs = null;
		Long id = null;
		if (participantGridId == null || participantExtension == null)
			return;
		try {
			//String participantGridId = participant.getIdentifier().getRoot();
			ps = con
					.prepareStatement("select ID, PARTICIPANT_ID from IDENTIFIER where ROOT = ? AND EXTENSION =? AND PARTICIPANT_ID IS NOT NULL");
			ps.setString(1, participantGridId);
			ps.setString(2, participantExtension);
			rs = ps.executeQuery();
			// check if identifier is in DB
			if (rs.next() && !rs.isBeforeFirst()
					&& rs.getLong("PARTICIPANT_ID") != 0) {
				// already present;update the identifier table
				id = rs.getLong("PARTICIPANT_ID");

			}
			if (id != null) {
				logger.debug("Performing rollback of the participant " + id);

				ps1 = con
						.prepareStatement("delete from identifier where extension = (select extension from identifier where"
								+ " root = ? and extension = ?)");

				ps1.setString(1, participantGridId);
				ps1.setString(2, participantExtension);
				ps1.executeUpdate();

				ps2 = con
						.prepareStatement("delete from PARTICIPANT where ID=?");
				ps2.setLong(1, id);
				ps2.executeUpdate();
				con.commit();
			}
		} finally {
			if (ps != null) {
				ps.close();
			}
			if (ps1 != null) {
				ps1.close();
			}
			if (ps2 != null) {
				ps2.close();
			}
			if (rs != null) {
				rs.close();
			}
		}
	}

	/**
	 * rollbackStudy performs the study roll back
	 * @param con Connection
	 * @param protocol Protocol
	 * @throws SQLException
	 */
	public void rollbackStudy(Connection con, String studyGridId)
			throws SQLException {
		logger.debug("Received a Study Rollback message");
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		ResultSet rs = null;
		Long id = null;
		if (studyGridId == null)
			return;
		try {
			//String protocolGridId = protocol.getIdentifier().getRoot();
			ps = con
					.prepareStatement("select ID,PROTOCOL_ID from IDENTIFIER where ROOT = ? AND PROTOCOL_ID IS NOT NULL");
			ps.setString(1, studyGridId);
			rs = ps.executeQuery();
			// check if identifier is in DB
			if (rs.next() && !rs.isBeforeFirst()
					&& rs.getLong("PROTOCOL_ID") != 0) {
				logger.debug("protocolID " + rs.getLong("PROTOCOL_ID"));
				// already present;update the identifier table
				id = rs.getLong("PROTOCOL_ID");
			}
			if (id != null) {
				logger.debug("Performing rollback of the study " + id);
				ps = con
						.prepareStatement("delete from IDENTIFIER where PROTOCOL_ID=?");
				ps.setLong(1, id);
				ps.executeUpdate();
				ps1 = con.prepareStatement("delete from PROTOCOL where ID=?");
				ps1.setLong(1, id);
				ps1.executeUpdate();

				con.commit();
			}
		} finally {
			if (ps != null) {
				ps.close();
			}
			if (ps1 != null) {
				ps1.close();
			}
			if (rs != null) {
				rs.close();
			}
		}
	}

	/**
	 * Checks if the participant is persisted in the database and returns the insert date.
	 * @param con Connection
	 * @param participantGridId
	 * @return Insert Date
	 * @throws SQLException
	 */
	public Date checkParticipantForRollback(Connection con,
			String participantGridId, String participantExtension)
			throws SQLException {
		logger.debug("Checking if participant exists for Rollback");
		Date insertDate = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		Long id = null;
		if (participantGridId == null || participantExtension == null)
			return insertDate;
		try {
			ps = con
					.prepareStatement("select ID,PARTICIPANT_ID from IDENTIFIER where ROOT = ? AND EXTENSION= ? AND PARTICIPANT_ID IS NOT NULL");
			ps.setString(1, participantGridId);
			ps.setString(2, participantExtension);
			rs = ps.executeQuery();
			// check if identifier is in DB
			if (rs.next() && !rs.isBeforeFirst()
					&& rs.getLong("PARTICIPANT_ID") != 0) {
				logger.debug("participantid " + rs.getLong("PARTICIPANT_ID"));
				// already present;update the identifier table
				id = rs.getLong("PARTICIPANT_ID");
			}
			if (id != null) {
				ps = con
						.prepareStatement("select CTOM_INSERT_DATE from PARTICIPANT where ID=?");
				ps.setLong(1, id);
				rs1 = ps.executeQuery();
				if (rs1.next() && !rs1.isBeforeFirst()) {
					insertDate = new java.util.Date(rs1.getTimestamp(1)
							.getTime());
					logger.debug("insert date " + insertDate);
				}
			}
		} finally {
			if (ps != null) {
				ps.close();
			}
			if (rs1 != null) {
				rs1.close();
			}
			if (rs != null) {
				rs.close();
			}
		}
		return insertDate;
	}

	/**
	 * Checks if the study is persisted in the database and returns the insert date.
	 * @param con Connection
	 * @param studyGridId Study Grid Id
	 * @return Insert Date
	 * @throws SQLException
	 */
	public Date checkStudyForRollback(Connection con, String studyGridId)
			throws SQLException {
		logger.debug("Checking if study exists for rollback");
		Date insertDate = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		Long id = null;
		if (studyGridId == null)
			return insertDate;
		try {
			ps = con
					.prepareStatement("select ID,PROTOCOL_ID from IDENTIFIER where ROOT = ? AND PROTOCOL_ID IS NOT NULL");
			ps.setString(1, studyGridId);
			rs = ps.executeQuery();
			// check if identifier is in DB
			if (rs.next() && !rs.isBeforeFirst()
					&& rs.getLong("PROTOCOL_ID") != 0) {
				logger.debug("protocolID " + rs.getLong("PROTOCOL_ID"));
				// already present;update the identifier table
				id = rs.getLong("PROTOCOL_ID");
			}
			//insert into Protocol the insert date
			if (id != null) {
				ps = con
						.prepareStatement("select CTOM_INSERT_DATE from PROTOCOL where ID=?");
				ps.setLong(1, id);
				rs1 = ps.executeQuery();
				if (rs1.next() && !rs1.isBeforeFirst()) {
					insertDate = new java.util.Date(rs1.getTimestamp(1)
							.getTime());
					logger.debug("insert date " + insertDate);
				}
			}
		} finally {
			if (ps != null) {
				ps.close();
			}
			if (rs1 != null) {
				rs1.close();
			}
			if (rs != null) {
				rs.close();
			}
		}
		return insertDate;
	}

	/**
	 * getStudy retrieves the Study the Participant is associated with.
	 * @param con Connection
	 * @param part Participant
	 * @return study Protocol
	 * @throws SQLException
	 */
	public Protocol getStudy(Connection con, Participant part)
			throws SQLException {
		logger.debug("Inside getStudy method");
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		Protocol study = null;
		Long protocolId = null;
		Long healthCareSiteId = null;

		try {
			// ps = con.prepareStatement("select protocol_id,healthcare_site_id from
			// study_site where id in (select study_site_id from
			// study_Participant_assignment where id in (select
			// study_Participant_assignmnt_id from identifier where root=? and
			// extension=? and study_participant_assignmnt_id is not null)) ");
			ps = con
					.prepareStatement("select protocol_id,healthcare_site_id from study_site where id in (select study_site_id from study_Participant_assignment where id in (select study_Participant_assignmnt_id from identifier where extension=? and study_participant_assignmnt_id is not null)) ");
			// ps.setString(1, part.getIdentifier().getRoot());
			ps.setString(1, part.getIdentifier().getExtension());
			rs = ps.executeQuery();
			if (rs.next()) {
				protocolId = rs.getLong(1);
				healthCareSiteId = rs.getLong(2);

			}
			if (protocolId != null) {
				ps1 = con
						.prepareStatement("select p.long_title_text,i.root,i.extension,i.assigning_authority_name from identifier i, protocol p where i.protocol_id=p.id and p.id=?");
				ps1.setLong(1, protocolId);
				rs1 = ps1.executeQuery();
				if (rs1.next()) {
					study = new Protocol();
					study.setLongTxtTitle(rs1.getString("LONG_TITLE_TEXT"));
					study.getIdentifier().setRoot(rs1.getString("ROOT"));
					study.getIdentifier().setExtension(
							rs1.getString("EXTENSION"));
					study.getIdentifier().setAssigningAuthorityName(
							rs1.getString("ASSIGNING_AUTHORITY_NAME"));
				}
			}
			/*
			 * if(healthCareSiteId!=null){ ps2= con.prepareStatement("select
			 * root,extension,assigning_authority_name from identifier where
			 * protocol_id=?"); ps2.setLong(1, healthCareSiteId); }
			 */
		} finally {
			if (ps != null) {
				ps.close();
			}
			if (rs1 != null) {
				rs1.close();
			}
			if (rs != null) {
				rs.close();
			}
		}
		return study;
	}

	/**
	 * Used for Demo purpose. checks to see if the participant exists.
	 * @param con Connection
	 * @param protocol Protocol
	 * @param mrn patient mrn 
	 * @return StudyPArticipantAssignment Id
	 * @throws SQLException
	 */
	public Long checkParticipantExists(Connection con, Protocol protocol,
			String mrn) throws SQLException {
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		Long spa_id = null;
		String root = null;
		int count = 0;

		try {
			ps = con
					.prepareStatement("select study_participant_assignmnt_id, root from identifier where extension=? and study_participant_assignmnt_id is not null");
			ps.setString(1, mrn);
			rs = ps.executeQuery();
			if (rs.next()) {
				spa_id = rs.getLong(1);
				root = rs.getString(2);

			}
			if (spa_id != null) {
				String identOrg = root + "." + mrn;
				ps1 = con
						.prepareStatement("select count(*) from identifier where protocol_id in (select protocol_id from study_site where id in( select study_site_id from study_participant_assignment where id = ? and STUDY_PARTICIPANT_IDENTFR_ORIG=? ))");
				ps1.setLong(1, spa_id);
				ps1.setString(2, identOrg);// get the participant root+mrn
				rs1 = ps1.executeQuery();
				if (rs1.next()) {
					count = rs1.getInt(1);
				}
			}
			if (count == 1 && root != null)
				return spa_id;

		} finally {
			if (ps != null) {
				ps.close();
			}
			if (ps1 != null) {
				ps1.close();
			}
			if (rs1 != null) {
				rs1.close();
			}
			if (rs != null) {
				rs.close();
			}
		}
		return spa_id;
	}

	/**
	 * Updates the Participant Grid id --just for Demo 
	 * @param con Connection
	 * @param participantGridId Participant Grid Id
	 * @param spaId Study Participant Assignment Id
	 * @param mrn Medical Resource Number 
	 * @throws SQLException
	 */
	public void updateParticipantGridId(Connection con,
			String participantGridId, Long spaId, String mrn)
			throws SQLException {
		PreparedStatement ps = null;
		logger.debug(participantGridId + " " + spaId + " " + mrn);
		try {
			ps = con
					.prepareStatement("update identifier set root=? where extension=? and study_participant_assignmnt_id=?");
			ps.setString(1, participantGridId);
			ps.setString(2, mrn);
			ps.setLong(3, spaId);
			ps.executeUpdate();
			con.commit();
		} finally {
			if (ps != null) {
				ps.close();
			}
		}
	}

}
