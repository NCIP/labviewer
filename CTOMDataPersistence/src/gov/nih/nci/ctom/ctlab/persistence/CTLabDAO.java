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
public class CTLabDAO extends BaseJDBCDAO
{
	Logger logger = Logger.getLogger(getClass());
	
	/**
	 * Persists the Protocol object to the database
	 * @param con  Connection 
	 * @param prot Protocol
	 * @throws Exception
	 */
	public void saveProtocol(Connection con, Protocol prot) throws Exception
	{
		logger.info("Saving Protocol");
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
                ps = con.prepareStatement("select ID from IDENTIFIER where PROTOCOL_ID = ?");
                ps.setLong(1, id);
                rs = ps.executeQuery();

                if (rs.next()) {
                    prot.getIdentifier().setId(rs.getLong(1));
                }
                logger.info("Protocol existed");
            } else {
                // If not then insert it
                saveOrInsertIdentifier(con, prot);
                if (prot.getId() == null) {
                    identifierUpdInd = true;
                    Statement stmt = con.createStatement();
                    rs = stmt.executeQuery("select protocol_seq.nextval from dual");
                    rs.next();
                    id = rs.getLong(1);
                    ps = con
                                    .prepareStatement("insert into protocol (ID, NCI_IDENTIFIER, IDENTIFIER_ASSIGNING_AUTHORITY, LONG_TITLE_TEXT,CTOM_INSERT_DATE)  values(?,?,?,?,?)");
                    ps.setLong(1, id);
                    ps.setString(2, String.valueOf(prot.getNciIdentifier()));
                    ps.setString(3, String.valueOf(prot.getIdAssigningAuth()));
                    ps.setString(4, String.valueOf(prot.getLongTxtTitle()));
                    if (prot.getCtomInsertDt() == null)
                        ps.setTimestamp(5, new java.sql.Timestamp(new Date().getTime()));
                    else
                        ps.setTimestamp(5, new java.sql.Timestamp(prot.getCtomInsertDt().getTime()));
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
            if(ps != null) {
                ps.close();
            }
            if(rs != null) {
                rs.close();
            }
        }
        if (prot.getInvestigator() != null)
			saveInvestigator(con, id, prot.getInvestigator());

		if (prot.getHealthCareSite() != null)
			saveHealthCareSite(con, id, prot.getHealthCareSite());
		
	}

	/**
	 * 
	 * @param con
	 * @param protId
	 * @param hcSite
	 * @throws SQLException
	 */
	private void saveHealthCareSite(Connection con, Long protId,
			HealthCareSite hcSite) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Long hsId = null;
		Statement stmt = null;

		Long ssId;
		try {
            ps = con.prepareStatement("select id from healthcare_site where NCI_INSTITUTE_CODE = ?");
            ps.setString(1, hcSite.getNciInstituteCd());
            rs = ps.executeQuery();
            if (rs.next()) {
                hsId = rs.getLong(1);
            } else {
                // Get Id from sequence
                stmt = con.createStatement();
                rs = stmt.executeQuery("select ORGANIZATION_SEQ.nextval from dual");
                rs.next();
                hsId = rs.getLong(1);
                // insert into HealthCare_Site
                ps = con.prepareStatement("insert into HEALTHCARE_SITE (ID, NCI_INSTITUTE_CODE)  values(?,?)");

                ps.setLong(1, hsId);
                ps.setString(2, hcSite.getNciInstituteCd());
                ps.execute();
            }
            ssId = null;
            ps = con.prepareStatement("select id from study_site where HEALTHCARE_SITE_ID = ? and PROTOCOL_ID = ?");
            ps.setLong(1, hsId);
            ps.setLong(2, protId);
            rs = ps.executeQuery();
            if (rs.next()) {
                ssId = rs.getLong(1);
            } else {
                stmt = con.createStatement();
                rs = stmt.executeQuery("select STUDY_SITE_SEQ.nextval from dual");
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
        } finally{
            if(ps != null) {
                ps.close();
            }
            if(stmt != null) {
                stmt.close();
            }
            if(rs != null) {
                rs.close();
            }
        }
        if (hcSite.getStudyParticipantAssignment() != null)
		{
			//saveParticipant(con, hcSite.getStudyParticipantAssignment().getParticipant());
			saveStudyParticipantAssignment(con, ssId, hcSite.getStudyParticipantAssignment());
		}

	}

	/**
	 * @param con
	 * @param ssId
	 * @param spa
	 * @throws SQLException
	 */
	private void saveStudyParticipantAssignment(Connection con, Long ssId,
			StudyParticipantAssignment spa) throws SQLException
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Long spaId = null;
		boolean identifierUpdInd=false;
		
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
                    rs = stmt.executeQuery("select STUDY_PARTICIPANT_ASSGNMNT_SEQ.nextval from dual");
                    rs.next();
                    spaId = rs.getLong(1);

                    identifierUpdInd = true;
                    saveParticipant(con, spa.getParticipant());
                    // insert into STUDY_PARTICIPANT_ASSIGNMENT
                    ps = con
                                    .prepareStatement("insert into STUDY_PARTICIPANT_ASSIGNMENT (ID, STUDY_PARTICIPANT_IDENTFR_ORIG, STUDY_SITE_ID, PARTICIPANT_ID)  values(?,?,?,?)");
                    ps.setLong(1, spaId);
                    ps.setString(2, spa.getStudyPartIdOrig());
                    ps.setLong(3, ssId);
                    ps.setLong(4, spa.getParticipant().getId());
                    ps.execute();
                    con.commit();
                    if (identifierUpdInd && spa.getParticipant().getIdentifier() != null) {
                        spa.setId(spaId);
                        updateIdentifier(con, spa, identifierID);
                    }
                } else {
                    spaId = spa.getId();
                }
            }
        } finally {
            if(ps != null) {
                ps.close();
            }
            if(rs != null) {
                rs.close();
            }
        }
        Activity act = spa.getActivity();
		if (act != null)
			saveActivity(con, spaId, act);

	}

	/**
	 * saveParticipant is used to save a Pariticpant object to the CTOM data
	 * model
	 * <P>
	 * 
	 * @param con
	 * @param participant
	 * @throws SQLException
	 */
	public void saveParticipant(Connection con, Participant participant)
			throws SQLException
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean identifierUpdInd = false;
		// Long personId = null;

		// Get the concept descriptor ids for the race and gender
		Long genderCDId = insertOrsaveConceptDescriptor(con, participant
				.getAdminGenderCode(),participant.getAdminGenderCodeSystem(),participant.getAdminGenderCodeSystemName(), null, null);
		Long raceCDId = insertOrsaveConceptDescriptor(con, participant
				.getRaceCode(),participant.getRaceCodeSystem(),participant.getRaceCodeSystemName(), null, null);

		// Get the next person ID
		// Statement stmt = con.createStatement();
		// rs = stmt.executeQuery("select PERSON_SEQ.nextval from dual");
		// rs.next();
		// personId = rs.getLong(1);
		// participant.setId(personId);

		// check if there exists a identifier with the same value; if yes-
		// returns its participantID else return null
		saveOrInsertIdentifier(con, participant);
		
		try {
            // insert into participant if there was no participant associated with
            // identifier.
            if (participant.getId() == null) {
                identifierUpdInd = true;
                ps = con.prepareStatement("select Person_SEQ.nextval from dual");
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
                    ps.setDate(2, new java.sql.Date(participant.getBirthDate().getTime()));
                else
                    ps.setDate(2, null);

                ps.setString(3, participant.getInitials());
                ps.setString(4, participant.getLastName());
                ps.setString(5, participant.getFirstName());
                ps.setString(6, participant.getEthnicGroupCode());
                if (participant.getCtomInsertDate() == null)
                    ps.setTimestamp(7, new java.sql.Timestamp(new Date().getTime()));
                else
                    ps.setTimestamp(7, new java.sql.Timestamp(participant.getCtomInsertDate().getTime()));
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
                    ps.setDate(1, new java.sql.Date(participant.getBirthDate().getTime()));
                else
                    ps.setDate(1, null);
                ps.setString(2, participant.getInitials());
                ps.setString(3, participant.getLastName());
                ps.setString(4, participant.getFirstName());
                ps.setString(5, participant.getEthnicGroupCode());
                ps.setLong(6, genderCDId);
                ps.setLong(7, raceCDId);
                if (participant.getCtomInsertDate() == null)
                    ps.setTimestamp(8, new java.sql.Timestamp(new Date().getTime()));
                else
                    ps.setTimestamp(8, new java.sql.Timestamp(participant.getCtomInsertDate().getTime()));
                ps.executeUpdate();
                con.commit();
            }
        } finally {
            if(ps != null) {
                ps.close();
            }
            if(rs != null) {
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
	 * @param con
	 * @param participant
	 * @throws SQLException
	 */
	private void saveOrInsertIdentifier(Connection con, Participant participant)
			throws SQLException
	{
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
                if (rs.next() && !rs.isBeforeFirst() && rs.getLong("PARTICIPANT_ID") != 0) {
                    // already present;update the identifier table
                    id = rs.getLong("PARTICIPANT_ID");
                    participant.setId(id);
                    participant.getIdentifier().setId(rs.getLong("ID"));
                } else {
                    // get the identifier id
                    ps = con.prepareStatement("select IDENTIFIER_SEQ.nextval from dual");
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        identifierId = rs.getLong(1);
                        break;
                    }
                    // insert new into identifier get the identifierid and insert
                    // into participant table
                    ps = con.prepareStatement("insert into IDENTIFIER (Id,EXTENSION, SOURCE,ROOT) values (?,?,?,?)");
                    // need to set the participantid into the identifier table
                    ps.setLong(1, identifierId);
                    ps.setString(2, participant.getIdentifier().getExtension());
                    ps.setString(3, participant.getIdentifier().getSource());
                    ps.setString(4, participant.getIdentifier().getRoot());
                    ps.executeUpdate();
                    con.commit();

                    participant.setId(id);
                    participant.getIdentifier().setId(identifierId);
                }
            }
        } finally {
            if(ps != null) {
                ps.close();
            }
            if(rs != null) {
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
	 * @param con
	 * @param protocol
	 * @throws SQLException
	 */
	private void saveOrInsertIdentifier(Connection con, Protocol protocol)
			throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Long id = null;
		Long identifierId = null;
		// check if the participant is associated with identifier

		try {
            if (protocol.getIdentifier() != null) {
                ps = con
                                .prepareStatement("select ID,PROTOCOL_ID from IDENTIFIER where EXTENSION = ? AND PROTOCOL_ID IS NOT NULL");
                ps.setString(1, protocol.getIdentifier().getExtension());
                rs = ps.executeQuery();

                // check if identifier is in DB
                if (rs.next() && !rs.isBeforeFirst() && rs.getLong("PROTOCOL_ID") != 0) {
                    // already present;update the identifier table
                    id = rs.getLong("PROTOCOL_ID");
                    protocol.setId(id);
                    protocol.getIdentifier().setId(rs.getLong("ID"));
                } else {
                    // get the identifier id
                    ps = con.prepareStatement("select IDENTIFIER_SEQ.nextval from dual");
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        identifierId = rs.getLong(1);
                        break;
                    }
                    // insert new into identifier get the identifierid and insert
                    // into participant table
                    ps = con.prepareStatement("insert into IDENTIFIER (Id,EXTENSION, SOURCE,ROOT) values (?,?,?,?)");
                    // need to set the participantid into the identifier table
                    ps.setLong(1, identifierId);
                    ps.setString(2, protocol.getIdentifier().getExtension());
                    ps.setString(3, protocol.getIdentifier().getSource());
                    ps.setString(4, protocol.getIdentifier().getRoot());
                    ps.executeUpdate();
                    con.commit();

                    protocol.setId(id);
                    protocol.getIdentifier().setId(identifierId);
                }
            }
        }  finally {
            if(ps != null) {
                ps.close();
            }
            if(rs != null) {
                rs.close();
            }
        }

	}
	/**
	 * @param con
	 * @param participant
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
        }  finally {
            if(ps != null) {
                ps.close();
            }
        }

	}
	/**
	 * Persists the identifier object into the database. The identifier is
	 * associated with the protocol object.If the identifier object is
	 * already present, the method updates the identifier else insert the
	 * identifier.
	 * 
	 * @param con
	 * @param protocol
	 * @throws SQLException
	 */
	private Long saveOrInsertIdentifier(Connection con, StudyParticipantAssignment spa)
			throws SQLException {
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
                if (rs.next() && !rs.isBeforeFirst() && rs.getLong("STUDY_PARTICIPANT_ASSIGNMNT_ID") != 0) {
                    // already present;update the identifier table
                    id = rs.getLong("STUDY_PARTICIPANT_ASSIGNMNT_ID");
                    spa.setId(id);
                    spa.getIdentifier().setId(rs.getLong("ID"));
                    identifierId = rs.getLong("ID");
                } else {
                    // get the identifier id
                    ps = con.prepareStatement("select IDENTIFIER_SEQ.nextval from dual");
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        identifierId = rs.getLong(1);
                        break;
                    }
                    // insert new into identifier get the identifierid and insert
                    // into participant table
                    ps = con.prepareStatement("insert into IDENTIFIER (Id,EXTENSION, SOURCE,ROOT) values (?,?,?,?)");
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
            if(ps != null) {
                ps.close();
            }
            if(rs != null) {
                rs.close();
            }
        }
        return identifierId;

	}
	/**
	 * @param con
	 * @param participant
	 * @throws SQLException
	 */
	private void updateIdentifier(Connection con, StudyParticipantAssignment studypartAssign,Long identifierId)
			throws SQLException {

		PreparedStatement ps = null;
		try {
            ps = con.prepareStatement("update IDENTIFIER set STUDY_PARTICIPANT_ASSIGNMNT_ID = ? where ID= ?");
            // need to set the participantid into the identifier table
            ps.setLong(1, studypartAssign.getId());
            ps.setLong(2, identifierId);
            ps.executeUpdate();
            con.commit();
        } finally {
            if(ps != null) {
                ps.close();
            }
        }

	}
	
	/**
	 * @param con
	 * @param protocol
	 * @throws SQLException
	 */
	private void updateIdentifier(Connection con, Protocol protocol)
			throws SQLException {

		PreparedStatement ps = null;
		try {
            ps = con.prepareStatement("update IDENTIFIER set PROTOCOL_ID = ? where ID= ?");
            // need to set the protocolid into the identifier table
            ps.setLong(1, protocol.getId());
            ps.setLong(2, protocol.getIdentifier().getId());
            ps.executeUpdate();
            con.commit();
        } finally {
            if(ps != null) {
                ps.close();
            }
        }

	}
	
	/**
	 * @param con
	 * @param participant
	 * @throws SQLException
	 */
	private void updateParticipant(Connection con, Participant participant)
			throws SQLException
	{
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
            rs = stmt.executeQuery("select PERSON_SEQ.nextval from dual");
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
                ps.setDate(1, new java.sql.Date(participant.getBirthDate().getTime()));
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
            if(ps != null) {
                ps.close();
            }
            if(stmt != null) {
                stmt.close();
            }            
            if(rs != null) {
                rs.close();
            }
        }

	}

	/**
	 * @param con
	 * @param spaId
	 * @param act
	 * @throws SQLException
	 */
	private void saveActivity(Connection con, Long spaId, Activity act)
			throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Long actId = null;
		Statement stmt = null;

		try {
            // Get Id from sequence
            stmt = con.createStatement();
            rs = stmt.executeQuery("select ACTIVITY_SEQ.nextval from dual");
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
                java.util.Date startTmp = new SimpleDateFormat("yyyyMMddHHmmss").parse(act.getStartDateOrig());
                java.util.Date stopTmp = new SimpleDateFormat("yyyyMMddHHmmss").parse(act.getStartDateOrig());
                ps.setTimestamp(5, new java.sql.Timestamp(startTmp.getTime()));
                ps.setTimestamp(6, new java.sql.Timestamp(stopTmp.getTime()));
                ps.execute();
            } catch (ParseException e) {
                logger.error("error creating activity", e);
            }
            if (act.getObservation() != null) {
                act.getObservation().setActivityId(act.getId());
                saveObservation(con, act.getObservation());
            }
            if (act.getProcedure() != null) {
                act.getProcedure().setId(act.getId());
                saveProcedure(con, act.getProcedure());
            }
            if (act.getStudyTimePoint() != null) {
                act.getStudyTimePoint().setActivityId(act.getId());
                saveStudyTimePoint(con, act.getStudyTimePoint());
            }
        } finally {
            if(ps != null) {
                ps.close();
            }
            if(stmt != null) {
                stmt.close();
            }            
            if(rs != null) {
                rs.close();
            }
        }
	}

	/**
	 * @param con
	 * @param studyTimePoint
	 * @throws SQLException
	 */
	private void saveStudyTimePoint(Connection con,
			StudyTimePoint studyTimePoint) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Statement stmt = null;

		try {
            // Get Id from sequence
            stmt = con.createStatement();
            //rs = stmt.executeQuery("select STUDY_TIME_POINT_SEQ.nextval from dual");
            rs = stmt.executeQuery("select ACTIVITY_SEQ.nextval from dual");
            rs.next();
            studyTimePoint.setId(rs.getLong(1));
            // insert into STUDY_TIME_POINT
            ps = con.prepareStatement("insert into STUDY_TIME_POINT (ID, ACTIVITY_ID, VISIT_NAME)  values(?,?,?)");
            ps.setLong(1, studyTimePoint.getId());
            ps.setLong(2, studyTimePoint.getActivityId());
            ps.setString(3, studyTimePoint.getVisitName());
            ps.execute();
        } finally {
            if(ps != null) {
                ps.close();
            }
            if(stmt != null) {
                stmt.close();
            }            
            if(rs != null) {
                rs.close();
            }
        }

	}

	/**
	 * @param con
	 * @param procedure
	 * @throws SQLException
	 */
	private void saveProcedure(Connection con, Procedure procedure)
			throws SQLException {

		PreparedStatement ps = null;
		try {
            ps = con.prepareStatement("insert into PROCEDURE (ID, FASTING_STATUS)  values(?,?)");
            ps.setLong(1, procedure.getId());
            ps.setString(2, procedure.getFastingStatus());
            ps.execute();
            if (procedure.getSpecimenCollection() != null) {
                procedure.getSpecimenCollection().setProcedureActivityId(procedure.getId());
                saveSpecimenCollection(con, procedure.getSpecimenCollection());
            }
        } finally {
            if(ps != null) {
                ps.close();
            }
        }

	}

	/**
	 * @param con
	 * @param specimenCollection
	 * @throws SQLException
	 */
	private void saveSpecimenCollection(Connection con,
			SpecimenCollection specimenCollection) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs=null;

		try {
            if (specimenCollection.getCentralLaboratory() != null) {
                saveCentralLaboratory(con, specimenCollection.getCentralLaboratory());
                // Get Id from sequence
                Statement stmt = con.createStatement();
                rs = stmt.executeQuery("select ACTIVITY_SEQ.nextval from dual");
                rs.next();
                Long id = rs.getLong(1);
                //ps = con.prepareStatement("insert into SPECIMEN_COLLECTION (PROCEDURE_ACTIVITY_ID, CENTRAL_LABORATORY_ORG_ID)  values(?,?)");
                ps = con.prepareStatement("insert into SPECIMEN_COLLECTION (ID,CENTRAL_LABORATORY_ID)  values(?,?)");
                ps.setLong(1, specimenCollection.getProcedureActivityId());
                ps.setLong(2, specimenCollection.getCentralLaboratory().getId());
                ps.execute();

            }
        } finally {
            if(ps != null) {
                ps.close();
            }   
            if(rs != null) {
                rs.close();
            }
        }
        if (specimenCollection.getSpecimen() != null) {
			specimenCollection.getSpecimen().setProcedureActivityId(
       		specimenCollection.getProcedureActivityId());
			 saveSpecimen(con, specimenCollection.getSpecimen());

		}
	}

	
	 /**
	 * @param con
	 * @param specimen
	 * @throws SQLException
	 */
	private void saveSpecimen(Connection con, Specimen specimen) throws
	  SQLException { 
		  PreparedStatement ps = null; 
		  ResultSet rs = null;
		  Statement stmt = null;
	    Long sampleTypeCDId = insertOrsaveConceptDescriptor(con, specimen.getVolumeUOMCd(), null, null);
		  
	     try {
            // Get Id from sequence 
            stmt = con.createStatement();
            rs = stmt.executeQuery("select ACTIVITY_SEQ.nextval from dual");
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
            if(ps != null) {
                ps.close();
            }
            if(stmt != null) {
                stmt.close();
            }            
            if(rs != null) {
                rs.close();
            }
        }
	   }
	

	/**
	 * Get the Participant Person Id of the Study Participant Assignment
	 * 
	 * @param con
	 * @param studyParticipantIdentifier
	 * @return
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
            if(ps != null) {
                ps.close();
            }       
            if(rs != null) {
                rs.close();
            }
        }

	}

	/**
	 * @param con
	 * @param centralLaboratory
	 * @throws SQLException
	 */
	private void saveCentralLaboratory(Connection con,
			CentralLaboratory centralLaboratory) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Statement stmt = null;
		try {
            ps = con.prepareStatement("select ID from CENTRAL_LABORATORY where IDENTIFIER = ?");
            ps.setString(1, centralLaboratory.getIdentifier());
            rs = ps.executeQuery();
            if (rs.next()) {
                centralLaboratory.setId(rs.getLong(1));
            } else {

                // Get Id from sequence
                stmt = con.createStatement();
                rs = stmt.executeQuery("select ORGANIZATION_SEQ.nextval from dual");
                rs.next();
                centralLaboratory.setId(rs.getLong(1));

                ps = con.prepareStatement("insert into CENTRAL_LABORATORY(ID, IDENTIFIER, NAME)  values(?,?,?)");
                ps.setLong(1, centralLaboratory.getId());
                ps.setString(2, centralLaboratory.getIdentifier());
                ps.setString(3, centralLaboratory.getName());
                ps.execute();
            }
        } finally {
            if(ps != null) {
                ps.close();
            }
            if(stmt != null) {
                stmt.close();
            }            
            if(rs != null) {
                rs.close();
            }
        }
	}

	/**
	 * @param con
	 * @param observation
	 * @throws SQLException
	 */
	private void saveObservation(Connection con, Observation observation)
			throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Statement stmt = null;
		try {
            // Get Id from sequence
            stmt = con.createStatement();
            rs = stmt.executeQuery("select OBSERVATION_SEQ.nextval from dual");
            rs.next();
            observation.setId(rs.getLong(1));
            // insert into Observation
            ps = con
                            .prepareStatement("insert into OBSERVATION (ID, REPORTING_DATE, REPORTING_DATE_ORIG, CONFIDENTIALITY_CODE, STATUS_CODE, COMMENTS, ACTIVITY_ID)  values(?,?,?,?,?,?,?)");
            ps.setLong(1, observation.getId());
            if (observation.getReportingDate() != null)
                ps.setDate(2, new java.sql.Date(observation.getReportingDate().getTime()));
            else
                ps.setDate(2, null);
            ps.setString(3, observation.getReportingDateOrig());
            ps.setString(4, observation.getConfidentialityCode());
            ps.setString(5, observation.getStatusCode());
            ps.setString(6, observation.getComments());
            ps.setLong(7, observation.getActivityId());
            ps.execute();
        } finally {
            if(ps != null) {
                ps.close();
            }
            if(stmt != null) {
                stmt.close();
            }            
            if(rs != null) {
                rs.close();
            }
        }
        if (observation.getClinicalResult() != null) {
			observation.getClinicalResult().setObservationId(
					observation.getId());
			saveClinicalResult(con, observation.getClinicalResult());
		}

	}

	/**
	 * @param con
	 * @param clinicalResult
	 * @throws SQLException
	 */
	private void saveClinicalResult(Connection con,
			ClinicalResult clinicalResult) throws SQLException
	{
		Long valUOMCdId = null;
		Long labTestCdId = null;
	
		if (clinicalResult.getPerformingLaboratory() != null)
		{
			savePerformingLaboratory(con, clinicalResult
					.getPerformingLaboratory());
			 valUOMCdId = insertOrsaveConceptDescriptor(con,
			 clinicalResult.getValueUnitOfMeasureCd(),
			 clinicalResult.getValueUnitOfMeasureCdSystem(),
			 clinicalResult.getValueUnitOfMeasureCdSystemName(), null, null);
			 labTestCdId = insertOrsaveConceptDescriptor(con,
			 clinicalResult.getCrCode(),		 
			 clinicalResult.getCrCodeSystem(),clinicalResult.getCrCodeSystemName(),
			 clinicalResult.getCrCodeSystemVersion(),
			 clinicalResult.getCrCodeDisplayText());
			
			//valUOMCdId = insertOrsaveConceptDescriptor(con, clinicalResult
				//	.getValueUnitOfMeasureCd(), null, null);
			
			//labTestCdId = insertOrsaveConceptDescriptor(con, clinicalResult
				//	.getMeansVitStatObtCd(), null, null);
			
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
            ps.setLong(4, clinicalResult.getPerformingLaboratory().getOrganizationId());
            ps.setString(5, clinicalResult.getRefFlag());
            ps.setString(6, clinicalResult.getValue());
            ps.setDouble(7, clinicalResult.getRefRangeLow());
            ps.setDouble(8, clinicalResult.getRefRangeHigh());
            ps.execute();
        } finally {
            if(ps != null) {
                ps.close();
            }
        }

	}
	/**
	 * @param con
	 * @param code
	 * @param codeSystemVersion
	 * @param displayText
	 * @return
	 * @throws SQLException
	 */
	private Long insertOrsaveConceptDescriptor(Connection con, String code,String codeSystem,String codeSystemName,
			Double codeSystemVersion, String displayText) throws SQLException {

		if (code == null)
			return null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Long id = null;
		try {
            ps = con.prepareStatement("select id from concept_descriptor where code = ?");
            ps.setString(1, code);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getLong(1);
            } else {
                Statement stmt = con.createStatement();

                rs = stmt.executeQuery("select CONCEPT_DESCRIPTOR_SEQ.nextval from dual");
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
            if(ps != null) {
                ps.close();
            }         
            if(rs != null) {
                rs.close();
            }
        }

	}
	/**
	 * @param con
	 * @param code
	 * @param codeSystemVersion
	 * @param displayText
	 * @return
	 * @throws SQLException
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

			rs = stmt
					.executeQuery("select CONCEPT_DESCRIPTOR_SEQ.nextval from dual");
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
			rs = stmt.executeQuery("select ORGANIZATION_SEQ.nextval from dual");
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
	 * @param con
	 * @param protId
	 * @param inv
	 * @throws SQLException
	 */
	private void saveInvestigator(Connection con, Long protId, Investigator inv)
			throws SQLException
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Long id = null;
		Statement stmt = null;
		
		try {
            ps = con.prepareStatement("select ID from INVESTIGATOR  where NCI_IDENTIFIER = ?");
            ps.setString(1, inv.getNciId());
            rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getLong(1);
            } else {

                // Get Id from sequence
                stmt = con.createStatement();
                rs = stmt.executeQuery("select PERSON_SEQ.nextval from dual");
                rs.next();
                id = rs.getLong(1);
                // insert into Investigator
                ps = con.prepareStatement("insert into investigator (ID, NCI_IDENTIFIER, LAST_NAME)  values(?,?,?)");

                ps.setLong(1, id);
                ps.setString(2, String.valueOf(inv.getNciId()));
                ps.setString(3, String.valueOf(inv.getLastName()));
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
                rs = stmt.executeQuery("select STUDY_INVESTIGATOR_SEQ.nextval from dual");
                rs.next();
                siId = rs.getLong(1);
                System.out.println("The study_Invest id is " + siId);
                ps = con
                                .prepareStatement("insert into study_investigator (ID, PROTOCOL_ID, INVESTIGATOR_ID)  values(?,?,?)");

                ps.setLong(1, siId);
                ps.setLong(2, protId);
                ps.setLong(3, id);
                ps.execute();
            }
        } finally {
            if(ps != null) {
                ps.close();
            }
            if(stmt != null) {
                stmt.close();
            }            
            if(rs != null) {
                rs.close();
            }
        }
	}

	/**
	 * @param con
	 * @param assignment
	 * @throws SQLException
	 */
	public void updateStudyParticipantAssignment(Connection con,
			StudyParticipantAssignment assignment) throws SQLException {

		if (assignment == null)
			return;

		Long personId = getStudyParticipantAssignmentPersonId(con, assignment
				.getStudyPartIdOrig());
		Participant part = assignment.getParticipant();
		part.setId(personId);
		saveParticipant(con, part);
	}
	
	/**
	 * @param con
	 * @param participant
	 * @throws SQLException
	 */
	public void rollbackParticipant(Connection con,String participantGridId)throws SQLException
	{
	   PreparedStatement ps=null;
	   ResultSet rs=null;
	   Long id=null;
		if(participantGridId==null)
			return;
		try {
            //String participantGridId = participant.getIdentifier().getRoot();
            ps = con
                            .prepareStatement("select ID,PARTICIPANT_ID from IDENTIFIER where ROOT = ? AND PARTICIPANT_ID IS NOT NULL");
            ps.setString(1, participantGridId);
            rs = ps.executeQuery();
            // check if identifier is in DB
            if (rs.next() && !rs.isBeforeFirst() && rs.getLong("PARTICIPANT_ID") != 0) {
                // already present;update the identifier table
                id = rs.getLong("PARTICIPANT_ID");
            }
            if (id != null) {
                ps = con.prepareStatement("delete from PARTICIPANT where ID=?");
                ps.setLong(1, id);
                ps.executeUpdate();
                con.commit();
            }
        } finally {
            if(ps != null) {
                ps.close();
            }         
            if(rs != null) {
                rs.close();
            }
        }
	}	
	
	/**
	 * @param con
	 * @param protocol
	 * @throws SQLException
	 */
	public void rollbackStudy(Connection con,String studyGridId)throws SQLException
	{
	   PreparedStatement ps=null;
	   ResultSet rs=null;
	   Long id=null;
		if(studyGridId==null)
			return;
		try {
            //String protocolGridId = protocol.getIdentifier().getRoot();
            ps = con
                            .prepareStatement("select ID,PROTOCOL_ID from IDENTIFIER where ROOT = ? AND PROTOCOL_ID IS NOT NULL");
            ps.setString(1, studyGridId);
            rs = ps.executeQuery();
            // check if identifier is in DB
            if (rs.next() && !rs.isBeforeFirst() && rs.getLong("PROTOCOL_ID") != 0) {
                System.out.println("protocolID " + rs.getLong("PROTOCOL_ID"));
                // already present;update the identifier table
                id = rs.getLong("PROTOCOL_ID");
            }
            if (id != null) {
                ps = con.prepareStatement("delete from PROTOCOL where ID=?");
                ps.setLong(1, id);
                ps.executeUpdate();
                con.commit();
            }
        } finally {
            if(ps != null) {
                ps.close();
            }       
            if(rs != null) {
                rs.close();
            }
        }
	}	
	
	/**
	 * Checks if the participant is persisted in the database and returns the insert date.
	 * @param con
	 * @param participantGridId
	 * @return
	 * @throws SQLException
	 */
	public Date checkParticipantForRollback(Connection con, String participantGridId) throws SQLException {
        Date insertDate = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ResultSet rs1 = null;
        Long id = null;
        if (participantGridId == null)
            return insertDate;
        try {
            ps = con
                            .prepareStatement("select ID,PARTICIPANT_ID from IDENTIFIER where ROOT = ? AND PARTICIPANT_ID IS NOT NULL");
            ps.setString(1, participantGridId);
            rs = ps.executeQuery();
            // check if identifier is in DB
            if (rs.next() && !rs.isBeforeFirst() && rs.getLong("PARTICIPANT_ID") != 0) {
                System.out.println("participantid " + rs.getLong("PARTICIPANT_ID"));
                // already present;update the identifier table
                id = rs.getLong("PARTICIPANT_ID");
            }
            if (id != null) {
                ps = con.prepareStatement("select CTOM_INSERT_DATE from PARTICIPANT where ID=?");
                ps.setLong(1, id);
                rs1 = ps.executeQuery();
                if (rs1.next() && !rs1.isBeforeFirst()) {
                    insertDate = new java.util.Date(rs1.getTimestamp(1).getTime());
                    System.out.println("insert date " + insertDate);
                }
            }
        } finally {
            if(ps != null) {
                ps.close();
            }
            if(rs1 != null) {
                rs1.close();
            }            
            if(rs != null) {
                rs.close();
            }
        }
        return insertDate;
    }
	/**
	 * Checks if the study is persisted in the database and returns the insert date.
	 * @param con
	 * @param studyGridId
	 * @return
	 * @throws SQLException
	 */
	public Date checkStudyForRollback(Connection con, String studyGridId) throws SQLException {
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
            if (rs.next() && !rs.isBeforeFirst() && rs.getLong("PROTOCOL_ID") != 0) {
                System.out.println("protocolID " + rs.getLong("PROTOCOL_ID"));
                // already present;update the identifier table
                id = rs.getLong("PROTOCOL_ID");
            }
            if (id != null) {
                ps = con.prepareStatement("select CTOM_INSERT_DATE from PROTOCOL where ID=?");
                ps.setLong(1, id);
                rs1 = ps.executeQuery();
                if (rs1.next() && !rs1.isBeforeFirst()) {
                    insertDate = new java.util.Date(rs1.getTimestamp(1).getTime());
                    System.out.println("insert date " + insertDate);
                }
            }
        }  finally {
            if(ps != null) {
                ps.close();
            }
            if(rs1 != null) {
                rs1.close();
            }            
            if(rs != null) {
                rs.close();
            }
        }
        return insertDate;
    }
	
	 /**
	 * @param con Connection
	 * @param part Participant
	 * @return study Protocol
	 * @throws SQLException
	 */
	public Protocol getStudy(Connection con, Participant part) throws SQLException {
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
                    study.getIdentifier().setExtension(rs1.getString("EXTENSION"));
                    study.getIdentifier().setAssigningAuthorityName(rs1.getString("ASSIGNING_AUTHORITY_NAME"));
                }
            }
            /*
             * if(healthCareSiteId!=null){ ps2= con.prepareStatement("select
             * root,extension,assigning_authority_name from identifier where
             * protocol_id=?"); ps2.setLong(1, healthCareSiteId); }
             */
        }  finally {
            if(ps != null) {
                ps.close();
            }
            if(rs1 != null) {
                rs1.close();
            }            
            if(rs != null) {
                rs.close();
            }
        }
        return study;
    }
	/**
	 * Used for Demo purpose. checks to see if the participant exists.
	 * @param con
	 * @param protocol
	 * @param mrn patient mrn 
	 * @return
	 * @throws SQLException
	 */
	public Long checkParticipantExists(Connection con, Protocol protocol,String mrn) throws SQLException {
        PreparedStatement ps = null;
        PreparedStatement ps1 = null;
        ResultSet rs = null;
        ResultSet rs1 = null;
        Long spa_id=null;
        String root=null;
        int count=0;
        
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
            	String identOrg = root+"."+mrn;
                ps1 = con
                  .prepareStatement("select count(*) from identifier where protocol_id in (select protocol_id from study_site where id in( select study_site_id from study_participant_assignment where id = ? and STUDY_PARTICIPANT_IDENTFR_ORIG=? ))"); 
                ps1.setLong(1, spa_id);
                ps1.setString(2,identOrg);// get the participant root+mrn
                rs1 = ps1.executeQuery();
                if (rs1.next()) {
                	 count= rs1.getInt(1);
                  }
            }
            if(count==1 && root!=null)
              return spa_id;	
            
        }  finally {
            if(ps != null) {
                ps.close();
            }
            if(ps1 != null) {
                ps1.close();
            }
            if(rs1 != null) {
                rs1.close();
            }            
            if(rs != null) {
                rs.close();
            }
        }
        return spa_id;
    }
	
	/**
	 * Updates the Participant Grid id --just for Demo 
	 * @param participantGridId
	 * @param con
	 */
	public void updateParticipantGridId(Connection con, String participantGridId,Long spaId,String mrn)throws SQLException
	{
		PreparedStatement ps=null;
		System.out.println(participantGridId +" "+spaId + " "+mrn);
		try{
			ps = con
            .prepareStatement("update identifier set root=? where extension=? and study_participant_assignmnt_id=?");
        ps.setString(1, participantGridId);
        ps.setString(2, mrn);
        ps.setLong(3, spaId);
        ps.executeUpdate();
        con.commit();
		}finally{
			if(ps != null) {
                ps.close();
            }
		}
	}
	
}
