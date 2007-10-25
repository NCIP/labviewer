package gov.nih.nci.ctom.ctlab.persistence;

import gov.nih.nci.ctom.ctlab.domain.Activity;
import gov.nih.nci.ctom.ctlab.domain.CentralLaboratory;
import gov.nih.nci.ctom.ctlab.domain.HealthCareSite;
import gov.nih.nci.ctom.ctlab.domain.Investigator;
import gov.nih.nci.ctom.ctlab.domain.Observation;
import gov.nih.nci.ctom.ctlab.domain.Participant;
import gov.nih.nci.ctom.ctlab.domain.PerformingLaboratory;
import gov.nih.nci.ctom.ctlab.domain.Procedure;
import gov.nih.nci.ctom.ctlab.domain.Protocol;
import gov.nih.nci.ctom.ctlab.domain.SpecimenCollection;
import gov.nih.nci.ctom.ctlab.domain.StudyParticipantAssignment;
import gov.nih.nci.ctom.ctlab.domain.StudyTimePoint;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CTLabDAO extends BaseJDBCDAO {

	public void saveProtocol(Connection con, Protocol prot) throws Exception {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Long id = null;

		if (prot.getNciIdentifier() == null)
			throw new Exception(
			"Invalid Data, Protocol Identifier Cannot Be Null");
		ps = con
		.prepareStatement("select ID from protocol where NCI_IDENTIFIER = ?");
		ps.setString(1, prot.getNciIdentifier());
		rs = ps.executeQuery();

		if (rs.next()) {
			id = rs.getLong(1);
		} else {

			Statement stmt = con.createStatement();
			rs = stmt.executeQuery("select protocol_seq.nextval from dual");
			rs.next();
			id = rs.getLong(1);

			ps = con
			.prepareStatement("insert into protocol (ID, NCI_IDENTIFIER, IDENTIFIER_ASSIGNING_AUTHORITY, LONG_TITLE_TEXT)  values(?,?,?,?)");
			ps.setLong(1, id);
			ps.setString(2, String.valueOf(prot.getNciIdentifier()));
			ps.setString(3, String.valueOf(prot.getIdAssigningAuth()));
			ps.setString(4, String.valueOf(prot.getLongTxtTitle()));
			ps.execute();

		}

		if (prot.getInvestigator() != null)
			saveInvestigator(con, id, prot.getInvestigator());

		if (prot.getHealthCareSite() != null)
			saveHealthCareSite(con, id, prot.getHealthCareSite());

	}

	private void saveHealthCareSite(Connection con, Long protId,
			HealthCareSite hcSite) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Long hsId = null;
		Statement stmt = null;

		ps = con
		.prepareStatement("select id from healthcare_site where NCI_INSTITUTE_CODE = ?");
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
			ps = con
			.prepareStatement("insert into HEALTHCARE_SITE (ID, NCI_INSTITUTE_CODE)  values(?,?)");

			ps.setLong(1, hsId);
			ps.setString(2, hcSite.getNciInstituteCd());
			ps.execute();
		}

		Long ssId = null;
		ps = con
		.prepareStatement("select id from study_site where HEALTHCARE_SITE_ID = ? and PROTOCOL_ID = ?");
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

			// insert into Study_Investigator

			ps = con
			.prepareStatement("insert into study_site (ID, HEALTHCARE_SITE_ID, PROTOCOL_ID)  values(?,?,?)");

			ps.setLong(1, ssId);
			ps.setLong(2, hsId);
			ps.setLong(3, protId);
			ps.execute();

		}

		if (hcSite.getStudyParticipantAssignment() != null) {

			saveStudyParticipantAssignment(con, ssId, hcSite
					.getStudyParticipantAssignment());
		}

	}

	private void saveStudyParticipantAssignment(Connection con, Long ssId,
			StudyParticipantAssignment spa) throws SQLException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Long spaId = null;

		ps = con
		.prepareStatement("select id from STUDY_PARTICIPANT_ASSIGNMENT where STUDY_PARTICIPANT_IDENTFR_ORIG = ? AND STUDY_SITE_ID = ?");
		ps.setString(1, spa.getStudyPartIdOrig());
		ps.setLong(2, ssId);
		rs = ps.executeQuery();
		if (rs.next()) {
			spaId = rs.getLong(1);
		} else {

			// Get Id from sequence
			Statement stmt = con.createStatement();
			rs = stmt
			.executeQuery("select STUDY_PARTICIPANT_ASSGNMNT_SEQ.nextval from dual");
			rs.next();
			spaId = rs.getLong(1);

			// TODO CHeck study participant identifier, if it exists, then don't
			// insert new SPA or Participant

			saveParticipant(con, spa.getParticipant());

			// insert into STUDY_PARTICIPANT_ASSIGNMENT
			ps = con
			.prepareStatement("insert into STUDY_PARTICIPANT_ASSIGNMENT (ID, STUDY_PARTICIPANT_IDENTFR_ORIG, STUDY_SITE_ID, PARTICIPANT_ID)  values(?,?,?,?)");
			ps.setLong(1, spaId);
			ps.setString(2, spa.getStudyPartIdOrig());
			ps.setLong(3, ssId);
			ps.setLong(4, spa.getParticipant().getId());
			ps.execute();
		}

		Activity act = spa.getActivity();
		if (act != null)
			saveActivity(con, spaId, act);

	}

	/**
	 * saveParticipant is used to save a Pariticpant object to the CTOM data model
	 * <P>
	 * @param con
	 * @param participant
	 * @throws SQLException
	 */
	public void saveParticipant(Connection con, Participant participant)
	throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean identifierUpdInd = false;
		//Long personId = null;

		// Get the concept descriptor ids for the race and gender
		Long genderCDId = insertOrsaveConceptDescriptor(con, participant
				.getAdminGenderCode(), null, null);
		Long raceCDId = insertOrsaveConceptDescriptor(con, participant
				.getRaceCode(), null, null);

		// Get the next person ID
		//Statement stmt = con.createStatement();
		//rs = stmt.executeQuery("select PERSON_SEQ.nextval from dual");
		//rs.next();
		//personId = rs.getLong(1);
		//participant.setId(personId);

		//check if there exists a identifier with the same value; if yes- returns its participantID else return null
		saveOrInsertIdentifier(con, participant);
		System.out.println("The participantid=" + participant.getId());
		//insert into participant if there was no participant associated with identifier.
		if (participant.getId() == null) {
			identifierUpdInd = true;
			ps = con.prepareStatement("select Person_SEQ.nextval from dual");
			rs = ps.executeQuery();

			while (rs.next()) {
				participant.setId(rs.getLong(1));
				System.out.println("Get the participantid if null="
						+ rs.getLong(1));
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
				.prepareStatement("insert into PARTICIPANT (ID, BIRTH_DATE, INITIALS, LAST_NAME, FIRST_NAME, ETHNIC_GROUP_CODE, ADM_GNDR_CONCEPT_DESCRIPTOR_ID,RACE_CONCEPT_DESCRIPTOR_ID )  values(?,?,?,?,?,?,?,?)");
				ps.setLong(7, genderCDId);
				ps.setLong(8, raceCDId);
			} else if (!rc && !gc) {
				ps = con
				.prepareStatement("insert into PARTICIPANT (ID, BIRTH_DATE, INITIALS, LAST_NAME, FIRST_NAME, ETHNIC_GROUP_CODE)  values(?,?,?,?,?,?)");

			} else if (!rc) {
				ps = con
				.prepareStatement("insert into PARTICIPANT (ID, BIRTH_DATE, INITIALS, LAST_NAME, FIRST_NAME, ETHNIC_GROUP_CODE, ADM_GNDR_CONCEPT_DESCRIPTOR_ID )  values(?,?,?,?,?,?,?)");
				ps.setLong(7, genderCDId);
			} else if (!gc) {
				ps = con
				.prepareStatement("insert into PARTICIPANT (ID, BIRTH_DATE, INITIALS, LAST_NAME, FIRST_NAME, ETHNIC_GROUP_CODE, RACE_CONCEPT_DESCRIPTOR_ID )  values(?,?,?,?,?,?,?)");
				ps.setLong(7, raceCDId);
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

			ps.execute();
			con.commit();

			if (identifierUpdInd)
				updateIdentifier(con, participant);
		} //update into participant if there was a participant associated with identifier.
		else {
			ps = con
			.prepareStatement("update PARTICIPANT set BIRTH_DATE = ?, INITIALS = ?, LAST_NAME = ?, FIRST_NAME = ?, ETHNIC_GROUP_CODE=?, ADM_GNDR_CONCEPT_DESCRIPTOR_ID=?,RACE_CONCEPT_DESCRIPTOR_ID=? where ID = ?");
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
			ps.setLong(8, participant.getId());
			ps.executeUpdate();
			con.commit();
		}
	}

	/**
	 * Persists the identifier object into the database. The identifier is associated with the 
	 * participant object.If the identifier object is already present, the method updates the identifier
	 * else insert the identifier.
	 * @param con
	 * @param participant
	 * @throws SQLException
	 */
	private void saveOrInsertIdentifier(Connection con, Participant participant)
	throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Long id = null;
		Long identifierId = null;
		//check if the participant is associated with identifier

		if (participant.getIdentifier() != null) {
			ps = con
			.prepareStatement("select PARTICIPANT_ID from IDENTIFIER where EXTENSION = ?");
			ps.setString(1, participant.getIdentifier().getExtension());
			rs = ps.executeQuery();

			//check if identifier is in DB
			if (rs.next() && !rs.isBeforeFirst()) {
				System.out.println("Inside if participantID " + rs.getLong(1));
				//already present;update the identifier table
				id = rs.getLong(1);
				participant.setId(id);
			} else {
				//get the identifier id
				ps = con
				.prepareStatement("select IDENTIFIER_SEQ.nextval from dual");
				rs = ps.executeQuery();
				while (rs.next()) {
					identifierId = rs.getLong(1);
					System.out.println("Inside esle identifierID "
							+ rs.getLong(1));
					break;
				}
				//insert new into identifier get the identifierid and insert into participant table
				ps = con
				.prepareStatement("insert into IDENTIFIER (Id,EXTENSION, SOURCE) values (?,?,?)");
				//need to set the participantid into the identifier table
				ps.setLong(1, identifierId);
				ps.setString(2, participant.getIdentifier().getExtension());
				ps.setString(3, participant.getIdentifier().getSource());
				ps.executeUpdate();
				con.commit();

				participant.setId(id);
				participant.getIdentifier().setId(identifierId);
			}
		}

	}

	private void updateIdentifier(Connection con, Participant participant)
	throws SQLException {

		PreparedStatement ps = null;

		ps = con
		.prepareStatement("update IDENTIFIER set PARTICIPANT_ID = ? where ID= ?");
		//need to set the participantid into the identifier table
		ps.setLong(1, participant.getId());
		ps.setLong(2, participant.getIdentifier().getId());
		ps.executeUpdate();
		con.commit();

	}

	private void updateParticipant(Connection con, Participant participant)
	throws SQLException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Long personId = null;

		Long genderCDId = insertOrsaveConceptDescriptor(con, participant
				.getAdminGenderCode(), null, null);
		Long raceCDId = insertOrsaveConceptDescriptor(con, participant
				.getRaceCode(), null, null);

		Statement stmt = con.createStatement();
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

	}

	private void saveActivity(Connection con, Long spaId, Activity act)
	throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Long actId = null;

		// Get Id from sequence
		Statement stmt = con.createStatement();
		rs = stmt.executeQuery("select ACTIVITY_SEQ.nextval from dual");
		rs.next();
		actId = rs.getLong(1);
		act.setId(actId);

		// insert into Activity
		ps = con
		.prepareStatement("insert into ACTIVITY (ID, STUDY_PARTICIPANT_ASSIGNMNT_ID, START_DATE_ORIG, STOP_DATE_ORIG)  values(?,?,?,?)");
		ps.setLong(1, actId);
		ps.setLong(2, spaId);
		ps.setString(3, String.valueOf(act.getStartDateOrig()));
		ps.setString(4, String.valueOf(act.getStopDateOrig()));
		ps.execute();

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

	}

	private void saveStudyTimePoint(Connection con,
			StudyTimePoint studyTimePoint) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;

		// Get Id from sequence
		Statement stmt = con.createStatement();
		rs = stmt.executeQuery("select STUDY_TIME_POINT_SEQ.nextval from dual");
		rs.next();
		studyTimePoint.setId(rs.getLong(1));

		// insert into Activity
		ps = con
		.prepareStatement("insert into STUDY_TIME_POINT (ID, ACTIVITY_ID, VISIT_NAME)  values(?,?,?)");
		ps.setLong(1, studyTimePoint.getId());
		ps.setLong(2, studyTimePoint.getActivityId());
		ps.setString(3, studyTimePoint.getVisitName());
		ps.execute();

	}

	private void saveProcedure(Connection con, Procedure procedure)
	throws SQLException {

		PreparedStatement ps = null;

		ps = con
		.prepareStatement("insert into PROCEDURE (ACTIVITY_ID, FASTING_STATUS)  values(?,?)");
		ps.setLong(1, procedure.getId());
		ps.setString(2, procedure.getFastingStatus());
		ps.execute();

		if (procedure.getSpecimenCollection() != null) {
			procedure.getSpecimenCollection().setProcedureActivityId(
					procedure.getId());
			saveSpecimenCollection(con, procedure.getSpecimenCollection());
		}

	}

	private void saveSpecimenCollection(Connection con,
			SpecimenCollection specimenCollection) throws SQLException {
		PreparedStatement ps = null;

		if (specimenCollection.getCentralLaboratory() != null) {
			saveCentralLaboratory(con, specimenCollection
					.getCentralLaboratory());

			ps = con
			.prepareStatement("insert into SPECIMEN_COLLECTION (PROCEDURE_ACTIVITY_ID, CENTRAL_LABORATORY_ORG_ID)  values(?,?)");
			ps.setLong(1, specimenCollection.getProcedureActivityId());
			ps.setLong(2, specimenCollection.getCentralLaboratory().getId());
			ps.execute();

		}

		if (specimenCollection.getSpecimen() != null) {
			specimenCollection.getSpecimen().setProcedureActivityId(
					specimenCollection.getProcedureActivityId());
			//saveSpecimen(con, specimenCollection.getSpecimen());

		}
	}

	/*
	 private void saveSpecimen(Connection con, Specimen specimen)
	 throws SQLException {
	 PreparedStatement ps = null;
	 ResultSet rs = null;

	 Long sampleTypeCDId = insertOrsaveConceptDescriptor(con, specimen
	 .getSampleTypeCd(), specimen.getSampleTypeCdSystem(), specimen
	 .getSampleTypeCdSystemName(), null, null);

	 // Get Id from sequence
	 Statement stmt = con.createStatement();
	 rs = stmt.executeQuery("select SPECIMEN_SEQ.nextval from dual");
	 rs.next();
	 specimen.setId(rs.getLong(1));

	 boolean sc = false;
	 if (sampleTypeCDId != null)
	 sc = true;

	 if (sc) {
	 ps = con
	 .prepareStatement("insert into SPECIMEN(ID, ACCESSION_NUMBER, COMMENTS_FROM_LABORATORY, COMMENTS_FROM_INVESTIGATOR, CONDITION, SPECIMN_CLLCTN_PRCDR_ACTVTY_ID, SAMPLE_IDENTIFIER_ORIG, SMPLTYPE_CONCEPT_DESCRIPTOR_ID)  values(?,?,?,?,?,?,?,?)");
	 ps.setLong(8, sampleTypeCDId);
	 } else {
	 ps = con
	 .prepareStatement("insert into SPECIMEN(ID, ACCESSION_NUMBER, COMMENTS_FROM_LABORATORY, COMMENTS_FROM_INVESTIGATOR, CONDITION, SPECIMN_CLLCTN_PRCDR_ACTVTY_ID, SAMPLE_IDENTIFIER_ORIG)  values(?,?,?,?,?,?,?)");
	 }

	 ps.setLong(1, specimen.getId());
	 ps.setString(2, specimen.getAccessionNumber());
	 ps.setString(3, specimen.getCommentsFromLaboratory());
	 ps.setString(4, specimen.getCommentsFromInvestigator());
	 ps.setString(5, specimen.getCondition());
	 ps.setLong(6, specimen.getProcedureActivityId());
	 ps.setString(7, specimen.getSampleIdentifierOrig());

	 ps.execute();

	 }*/

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
		Long personId = null;

		ps = con
		.prepareStatement("select PARTICIPANT_PERSON_ID from STUDY_PARTICIPANT_ASSIGNMENT where STUDY_PARTICIPANT_IDENTFR_ORIG = ?");
		ps.setString(1, studyParticipantIdentifier);
		rs = ps.executeQuery();
		if (rs.next()) {
			return rs.getLong(1);
		} else {
			return null;
		}

	}

	private void saveCentralLaboratory(Connection con,
			CentralLaboratory centralLaboratory) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;

		ps = con
		.prepareStatement("select ORGANIZATION_ID from CENTRAL_LABORATORY where IDENTIFIER = ?");
		ps.setString(1, centralLaboratory.getIdentifier());
		rs = ps.executeQuery();
		if (rs.next()) {
			centralLaboratory.setId(rs.getLong(1));
		} else {

			// Get Id from sequence
			Statement stmt = con.createStatement();
			rs = stmt.executeQuery("select ORGANIZATION_SEQ.nextval from dual");
			rs.next();
			centralLaboratory.setId(rs.getLong(1));

			ps = con
			.prepareStatement("insert into CENTRAL_LABORATORY(ORGANIZATION_ID, IDENTIFIER, NAME)  values(?,?,?)");
			ps.setLong(1, centralLaboratory.getId());
			ps.setString(2, centralLaboratory.getIdentifier());
			ps.setString(3, centralLaboratory.getName());
			ps.execute();
		}
	}

	private void saveObservation(Connection con, Observation observation)
	throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;

		// Get Id from sequence
		Statement stmt = con.createStatement();
		rs = stmt.executeQuery("select OBSERVATION_SEQ.nextval from dual");
		rs.next();
		observation.setId(rs.getLong(1));

		// insert into Activity
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

		if (observation.getClinicalResult() != null) {
			observation.getClinicalResult().setObservationId(
					observation.getId());
			//	saveClinicalResult(con, observation.getClinicalResult());
		}

	}

	/*private void saveClinicalResult(Connection con,
			ClinicalResult clinicalResult) throws SQLException {

		Long valUOMCdId = null;
		Long labTestCdId = null;

		if (clinicalResult.getPerformingLaboratory() != null) {
			savePerformingLaboratory(con, clinicalResult
					.getPerformingLaboratory());
			valUOMCdId = insertOrsaveConceptDescriptor(con, clinicalResult
					.getValueUnitOfMeasureCd(), clinicalResult
					.getValueUnitOfMeasureCdSystem(), clinicalResult
					.getValueUnitOfMeasureCdSystemName(), null, null);
			labTestCdId = insertOrsaveConceptDescriptor(con, clinicalResult
					.getCrCode(), clinicalResult.getCrCodeSystem(),
					clinicalResult.getCrCodeSystemName(), clinicalResult
							.getCrCodeSystemVersion(), clinicalResult
							.getCrCodeDisplayText());
		}

		if (labTestCdId == null) // This is a required field in the database
			return;
		PreparedStatement ps = null;

		boolean val = false;
		boolean lt = false;
		if (valUOMCdId != null)
			val = true;
		if (labTestCdId != null)
			lt = true;

		if (lt && val) {
			ps = con
					.prepareStatement("insert into CLINICAL_RESULT (OBSERVATION_ID, LAB_TECHNIQUE_CODE, PANEL_NAME, PERFORMING_LABORATORY_ORG_ID, REFERENCE_FLAG, VALUE, VALUEUOM_CONCEPT_DESCRIPTOR_ID, LAB_TEST_CONCEPT_DESCRIPTOR_ID)  values(?,?,?,?,?,?,?,?)");
			ps.setLong(7, valUOMCdId);
			ps.setLong(8, labTestCdId);
		} else if (!lt && !val) {
			ps = con
					.prepareStatement("insert into CLINICAL_RESULT (OBSERVATION_ID, LAB_TECHNIQUE_CODE, PANEL_NAME, PERFORMING_LABORATORY_ORG_ID, REFERENCE_FLAG, VALUE)  values(?,?,?,?,?,?)");
		} else if (!lt) {
			ps = con
					.prepareStatement("insert into CLINICAL_RESULT (OBSERVATION_ID, LAB_TECHNIQUE_CODE, PANEL_NAME, PERFORMING_LABORATORY_ORG_ID, REFERENCE_FLAG, VALUE, VALUEUOM_CONCEPT_DESCRIPTOR_ID)  values(?,?,?,?,?,?,?)");
			ps.setLong(7, valUOMCdId);
		} else if (!val) {
			ps = con
					.prepareStatement("insert into CLINICAL_RESULT (OBSERVATION_ID, LAB_TECHNIQUE_CODE, PANEL_NAME, PERFORMING_LABORATORY_ORG_ID, REFERENCE_FLAG, VALUE, LAB_TEST_CONCEPT_DESCRIPTOR_ID)  values(?,?,?,?,?,?,?)");
			ps.setLong(7, labTestCdId);
		}

		ps.setLong(1, clinicalResult.getObservationId());
		ps.setString(2, clinicalResult.getLabTechCd());
		ps.setString(3, clinicalResult.getPanelName());
		ps.setLong(4, clinicalResult.getPerformingLaboratory()
				.getOrganizationId());
		ps.setString(5, clinicalResult.getRefFlag());
		ps.setString(6, clinicalResult.getValue());
		ps.execute();

	}*/

	private Long insertOrsaveConceptDescriptor(Connection con, String code,
			Double codeSystemVersion, String displayText) throws SQLException {

		if (code == null)
			return null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Long id = null;
		System.out.println("The code is --" + code);
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
			System.out.println("The concept descriptor id is --" + id);
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

	private void savePerformingLaboratory(Connection con,
			PerformingLaboratory performingLaboratory) throws SQLException {

		PreparedStatement ps = null;
		ResultSet rs = null;

		// Get Id from sequence

		ps = con
		.prepareStatement("select ORGANIZATION_ID from PERFORMING_LABORATORY where IDENTIFIER = ?");
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
			.prepareStatement("insert into PERFORMING_LABORATORY (ORGANIZATION_ID, IDENTIFIER, NAME)  values(?,?,?)");
			ps.setLong(1, performingLaboratory.getOrganizationId());
			ps.setString(2, performingLaboratory.getPlIdentifier());
			ps.setString(3, performingLaboratory.getPlName());
			ps.execute();
		}
	}

	private void saveInvestigator(Connection con, Long protId, Investigator inv)
	throws SQLException {
		// TODO Auto-generated method stub

		PreparedStatement ps = null;
		ResultSet rs = null;
		Long id = null;

		ps = con
		.prepareStatement("select PERSON_ID from INVESTIGATOR  where NCI_IDENTIFIER = ?");
		ps.setString(1, inv.getNciId());
		rs = ps.executeQuery();

		if (rs.next()) {
			id = rs.getLong(1);
		} else {

			// Get Id from sequence
			Statement stmt = con.createStatement();
			rs = stmt.executeQuery("select PERSON_SEQ.nextval from dual");
			rs.next();
			id = rs.getLong(1);

			// insert into Investigator
			ps = con
			.prepareStatement("insert into investigator (ID, NCI_IDENTIFIER, LAST_NAME)  values(?,?,?)");

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
			Statement stmt = con.createStatement();
			rs = stmt
			.executeQuery("select STUDY_INVESTIGATOR_SEQ.nextval from dual");
			rs.next();
			siId = rs.getLong(1);

			ps = con
			.prepareStatement("insert into study_investigator (ID, PROTOCOL_ID, INVESTIGATOR_ID)  values(?,?,?)");

			ps.setLong(1, siId);
			ps.setLong(2, protId);
			ps.setLong(3, id);
			ps.execute();
		}
	}

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
}
