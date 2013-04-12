/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */
package gov.nih.nci.ctom.ctlab.persistence;

import gov.nih.nci.ctom.ctlab.domain.HealthCareSite;
import gov.nih.nci.ctom.ctlab.domain.Investigator;
import gov.nih.nci.ctom.ctlab.domain.Participant;
import gov.nih.nci.ctom.ctlab.domain.Protocol;
import gov.nih.nci.ctom.ctlab.domain.StudyParticipantAssignment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * CTLabDAO has methods to persist clinical trial data to the CTODS database.
 * <P>
 * 
 * @author Anupama Sharma
 */
public class CTLabDAO extends BaseJDBCDAO
{

	// Logging File
	private static Logger logger = Logger.getLogger("client");

	/**
	 * Updates the Identifier table with the Participant Id
	 * 
	 * @param con
	 *            Connection
	 * @param participant
	 *            Participant
	 * @throws SQLException
	 */
	public void updateIdentifier(Connection con, Participant participant) throws SQLException
	{

		PreparedStatement ps = null;
		try
		{
			ps = con.prepareStatement("update IDENTIFIER set PARTICIPANT_ID = ? where ID= ?");
			// need to set the participantid into the identifier table
			ps.setLong(1, participant.getId());
			ps.setLong(2, participant.getIdentifier().getId());
			ps.executeUpdate();
			con.commit();
		}
		catch (SQLException se)
		{
			logger.error("Error updating the identifier with participant info",se);
			throw se;
		}
		finally
		{
			ps = SQLHelper.closePreparedStatement(ps);
			
		}

	}

	/**
	 * Updates Identifier table with Study Participant Assignment Id.
	 * 
	 * @param con
	 *            Connection
	 * @param participant
	 *            Participant
	 * @throws SQLException
	 */
	public void updateIdentifier(Connection con, StudyParticipantAssignment studypartAssign,
			Long identifierId) throws SQLException
	{

		PreparedStatement ps = null;
		try
		{
			ps =
					con
							.prepareStatement("update IDENTIFIER set STUDY_PARTICIPANT_ASSIGNMNT_ID = ? where ID= ?");
			// need to set the Study participant Assignemnt id into the
			// identifier table
			ps.setLong(1, studypartAssign.getId());
			ps.setLong(2, identifierId);
			ps.executeUpdate();
			con.commit();
		}
		catch (SQLException se)
		{
			logger.error("Error updating the identifier with SPA info",se);
			throw se;
		}
		finally
		{
			ps = SQLHelper.closePreparedStatement(ps);
		}

	}

	/**
	 * Updates Identifier table with Protocol Id
	 * 
	 * @param con
	 *            Connection
	 * @param protocol
	 *            Protocol
	 * @throws SQLException
	 */
	public void updateIdentifier(Connection con, Protocol protocol) throws SQLException
	{

		PreparedStatement ps = null;
		try
		{
			ps = con.prepareStatement("update IDENTIFIER set PROTOCOL_ID = ? where ID= ?");
			// need to set the protocolid into the identifier table
			ps.setLong(1, protocol.getId());
			ps.setLong(2, protocol.getIdentifier().getId());
			ps.executeUpdate();
			con.commit();
		}
		catch (SQLException se)
		{
			logger.error("Error updating the identifier with protocol info",se);
			throw se;
		}
		finally
		{
			ps = SQLHelper.closePreparedStatement(ps);
		}

	}

	/**
	 * Updates the participant table with gender and race concept descriptor
	 * 
	 * @param con
	 *            Connection
	 * @param participant
	 *            Participant
	 * @throws SQLException
	 */
	private void updateParticipant(Connection con, Participant participant) throws SQLException
	{

		PreparedStatement ps = null;
		Long personId = null;

		Long genderCDId =
				insertOrsaveConceptDescriptor(con, participant.getAdminGenderCode(), null, null);
		Long raceCDId = insertOrsaveConceptDescriptor(con, participant.getRaceCode(), null, null);

		try
		{
			// get next Id from seq
			personId = getNextVal(con, "PERSON_SEQ");
			participant.setId(personId);
			boolean genderCode = false;
			boolean raceCode = false;
			if (genderCDId != null)
			{
				genderCode = true;
			}
			if (raceCDId != null)
			{
				raceCode = true;
			}
			if (raceCode && genderCode)
			{

				ps =
						con
								.prepareStatement("update  PARTICIPANT set BIRTH_DATE = ?, BIRTH_DATE_ORIG = ?, INITIALS = ?, LAST_NAME = ?, FIRST_NAME = ?, MARITAL_STATUS_CODE = ?, ZIP_CODE = ?, COUNTRY_CODE = ?, ADM_GNDR_CONCEPT_DESCRIPTOR_ID = ?,RACE_CONCEPT_DESCRIPTOR_ID = ?  where PERSON_ID = ?");
				ps.setLong(7, genderCDId);
				ps.setLong(8, raceCDId);
			}
			else if (!raceCode && !genderCode)
			{

				ps =
						con
								.prepareStatement("update into PARTICIPANT set BIRTH_DATE = ?, BIRTH_DATE_ORIG = ?, INITIALS = ?, LAST_NAME = ?, FIRST_NAME = ?, MARITAL_STATUS_CODE = ?, ZIP_CODE = ?, COUNTRY_CODE = ?  where PERSON_ID = ?");

			}
			else if (!raceCode)
			{
				ps =
						con
								.prepareStatement("update into PARTICIPANT set BIRTH_DATE = ?, BIRTH_DATE_ORIG = ?, INITIALS = ?, LAST_NAME = ?, FIRST_NAME = ?, MARITAL_STATUS_CODE = ?, ZIP_CODE = ?, COUNTRY_CODE = ?, ADM_GNDR_CONCEPT_DESCRIPTOR_ID = ? where PERSON_ID =?");
				ps.setLong(7, genderCDId);

			}
			else if (!genderCode)
			{
				ps =
						con
								.prepareStatement("update into PARTICIPANT set BIRTH_DATE = ?, BIRTH_DATE_ORIG = ?, INITIALS = ?, LAST_NAME = ?, FIRST_NAME = ?, MARITAL_STATUS_CODE = ?, ZIP_CODE = ?, COUNTRY_CODE = ?, RACE_CONCEPT_DESCRIPTOR_ID = ? where PERSON_ID = ?");
				ps.setLong(7, raceCDId);
			}
			ps.setLong(1, personId);
			if (participant.getBirthDate() != null)
			{
				ps.setDate(1, new java.sql.Date(participant.getBirthDate().getTime()));
			}
			else
			{
				ps.setDate(1, null);
			}
			ps.setString(2, participant.getBirthDateOrig());
			ps.setString(3, participant.getInitials());
			ps.setString(4, participant.getLastName());
			ps.setString(5, participant.getFirstName());
			ps.setString(6, participant.getMaritalStatusCode());
			ps.setString(7, participant.getZipCode());
			ps.setString(8, participant.getCountryCode());
			ps.execute();
		}
		catch (SQLException se)
		{
			logger.error("Error updating the participant info",se);
			throw se;
		}
		finally
		{
			ps = SQLHelper.closePreparedStatement(ps);
		}

	}

	/**
	 * Get the Participant Person Id of the Study Participant Assignment
	 * 
	 * @param con
	 *            Connection
	 * @param studyParticipantIdentifier
	 *            StudyParticipantIdentifier
	 * @return ParticipantId
	 * @throws SQLException
	 */
	protected Long getStudyParticipantAssignmentPersonId(Connection con,
			String studyParticipantIdentifier) throws SQLException
	{

		PreparedStatement ps = null;
		ResultSet rs = null;

		try
		{
			ps =
					con
							.prepareStatement("select PARTICIPANT_ID from STUDY_PARTICIPANT_ASSIGNMENT where STUDY_PARTICIPANT_IDENTFR_ORIG = ?");
			ps.setString(1, studyParticipantIdentifier);
			rs = ps.executeQuery();
			if (rs.next())
			{
				return rs.getLong(1);
			}
			else
			{
				return null;
			}
		}
		catch (SQLException se)
		{
			logger.error("Error retrieving person id from Study participant assignment",se);
			throw se;
		}
		finally
		{
			rs = SQLHelper.closeResultSet(rs);
			ps = SQLHelper.closePreparedStatement(ps);

		}

	}

	/**
	 * insertOrsaveConceptDescriptor persists the Concept Descriptor data into
	 * CTODS database
	 * 
	 * @param con
	 *            Connection
	 * @param code
	 *            Clinical Result Code
	 * @param codeSystemVersion
	 *            Clinical Result Code system version
	 * @param displayText
	 *            Clinical Result display text
	 * @return Concept Descriptor Id
	 * @throws SQLException
	 */
	public Long insertOrsaveConceptDescriptor(Connection con, String code, String codeSystem,
			String codeSystemName, Double codeSystemVersion, String displayText)
			throws SQLException
	{

		if (code == null)
		{
			return null;
		}
		PreparedStatement ps = null;
		ResultSet rs = null;
		Long id = null;
		try
		{
			ps = con.prepareStatement("select id from concept_descriptor where code = ?");
			ps.setString(1, code);
			rs = ps.executeQuery();
			if (rs.next())
			{
				return rs.getLong(1);
			}
			else
			{
				rs = SQLHelper.closeResultSet(rs);
				ps = SQLHelper.closePreparedStatement(ps);
				
				// get next id from sequence
				id = getNextVal(con, "CONCEPT_DESCRIPTOR_SEQ");
				ps =
						con
								.prepareStatement("insert into CONCEPT_DESCRIPTOR (ID,CODE,CODE_SYSTEM,CODE_SYSTEM_NAME,CODE_SYSTEM_VERSION, DISPLAY_TEXT)  values(?,?,?,?,?,?)");
				ps.setLong(1, id);
				ps.setString(2, code);
				ps.setString(3, codeSystem);
				ps.setString(4, codeSystemName);
				if (codeSystemVersion != null)
				{
					ps.setDouble(5, codeSystemVersion);
				}
				else
				{
					ps.setDouble(5, 0);
				}
				ps.setString(6, displayText);
				ps.execute();
				con.commit();

				return id;
			}
		}
		catch (SQLException se)
		{
			logger.error("Error inserting/updating the Concept descriptor");
			throw se;
		}
		finally
		{
			rs = SQLHelper.closeResultSet(rs);
			ps = SQLHelper.closePreparedStatement(ps);

		}

	}

	/**
	 * insertOrsaveConceptDescriptor persists the Concept Descriptor data into
	 * CTODS database
	 * 
	 * @param con
	 *            Connection
	 * @param code
	 *            Clinical Result Code
	 * @param codeSystemVersion
	 *            Clinical Result Code system version
	 * @param displayText
	 *            Clinical Result display text
	 * @return Concept Descriptor Id
	 * @throws SQLException
	 */
	public Long insertOrsaveConceptDescriptor(Connection con, String code,
			Double codeSystemVersion, String displayText) throws SQLException
	{

		if (code == null)
			return null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Long id = null;
		try
		{
			ps = con.prepareStatement("select id from concept_descriptor where code = ?");
			ps.setString(1, code);
			rs = ps.executeQuery();
			if (rs.next())
			{
				return rs.getLong(1);
			}
			else
			{	
				
				// get next id from sequence
				id = getNextVal(con, "CONCEPT_DESCRIPTOR_SEQ");
			
				//Clean up
				rs = SQLHelper.closeResultSet(rs);
				ps = SQLHelper.closePreparedStatement(ps);
							
				ps =
						con
								.prepareStatement("insert into CONCEPT_DESCRIPTOR (ID,CODE, CODE_SYSTEM_VERSION, DISPLAY_TEXT)  values(?,?,?,?)");
				ps.setLong(1, id);
				ps.setString(2, code);
				if (codeSystemVersion != null)
				{
					ps.setDouble(3, codeSystemVersion);
				}
				else
				{
					ps.setDouble(3, 0);
				}
				ps.setString(4, displayText);
				ps.execute();
				con.commit();

				return id;
			}
		}
		catch (SQLException se)
		{
			logger.error("Error inserting/updating the Concept descriptor",se);
			throw se;
		}
		finally
		{
			rs = SQLHelper.closeResultSet(rs);
			ps = SQLHelper.closePreparedStatement(ps);
		}
	}

	/**
	 * rollbackParticipant rolls back participant information
	 * 
	 * @param con
	 *            Connection
	 * @param participant
	 *            Participant
	 * @throws SQLException
	 */
	public void rollbackParticipant(Connection con, String participantGridId,
			String participantExtension) throws SQLException
	{

		logger.debug("Received a participant Rollback message");
		PreparedStatement ps = null;
		ResultSet rs = null;
		Long id = null;
		if (participantGridId == null || participantExtension == null)
		{
			return;
		}
		try
		{
			// String participantGridId = participant.getIdentifier().getRoot();
			ps =
					con
							.prepareStatement("select ID, PARTICIPANT_ID from IDENTIFIER where ROOT = ? AND EXTENSION =? AND PARTICIPANT_ID IS NOT NULL");
			ps.setString(1, participantGridId);
			ps.setString(2, participantExtension);
			rs = ps.executeQuery();
			// check if identifier is in DB
			if (rs.next() && !rs.isBeforeFirst() && rs.getLong("PARTICIPANT_ID") != 0)
			{
				// already present;update the identifier table
				id = rs.getLong("PARTICIPANT_ID");

			}
			//Clean up
			rs = SQLHelper.closeResultSet(rs);
			ps = SQLHelper.closePreparedStatement(ps);
			
			if (id != null)
			{	
				logger.debug("Performing rollback of the participant " + id);
				// delete from the identifier table
				ps =
						con
								.prepareStatement("delete from identifier where extension = (select extension from identifier where"
										+ " root = ? and extension = ?)");

				ps.setString(1, participantGridId);
				ps.setString(2, participantExtension);
				ps.executeUpdate();
				
				//clean up
				ps = SQLHelper.closePreparedStatement(ps);
				
				// delete from the participant
				ps = con.prepareStatement("delete from PARTICIPANT where ID=?");
				ps.setLong(1, id);
				ps.executeUpdate();
				con.commit();
			}
		}
		catch (SQLException se)
		{
			logger.error("Error performing participant rollback",se);
			throw se;
		}
		finally
		{
			rs = SQLHelper.closeResultSet(rs);
			ps = SQLHelper.closePreparedStatement(ps);

		}
	}

	/**
	 * rollbackStudy performs the study roll back
	 * 
	 * @param con
	 *            Connection
	 * @param protocol
	 *            Protocol
	 * @throws SQLException
	 */
	public void rollbackStudy(Connection con, String studyGridId) throws SQLException
	{

		logger.debug("Received a Study Rollback message");
		PreparedStatement ps = null;
		ResultSet rs = null;
		Long id = null;
		if (studyGridId == null)
			return;
		try
		{
			// String protocolGridId = protocol.getIdentifier().getRoot();
			ps =
					con
							.prepareStatement("select ID,PROTOCOL_ID from IDENTIFIER where ROOT = ? AND PROTOCOL_ID IS NOT NULL");
			ps.setString(1, studyGridId);
			rs = ps.executeQuery();
			// check if identifier is in DB
			if (rs.next() && !rs.isBeforeFirst() && rs.getLong("PROTOCOL_ID") != 0)
			{
				logger.debug("protocolID " + rs.getLong("PROTOCOL_ID"));
				// already present;update the identifier table
				id = rs.getLong("PROTOCOL_ID");
			}
			//Clean up
			rs = SQLHelper.closeResultSet(rs);
			ps = SQLHelper.closePreparedStatement(ps);
			
			if (id != null)
			{
				logger.debug("Performing rollback of the study " + id);
				// delete from the identifier table
				ps = con.prepareStatement("delete from IDENTIFIER where PROTOCOL_ID=?");
				ps.setLong(1, id);
				ps.executeUpdate();

				//clean up
				ps = SQLHelper.closePreparedStatement(ps);
				
				// delete from the protocol table
				ps = con.prepareStatement("delete from PROTOCOL where ID=?");
				ps.setLong(1, id);
				ps.executeUpdate();
				con.commit();
			}
		}
		catch (SQLException se)
		{
			logger.error("Error performing study rollback",se);
			throw se;
		}
		finally
		{
			rs = SQLHelper.closeResultSet(rs);
			ps = SQLHelper.closePreparedStatement(ps);

		}
	}

	/**
	 * Checks if the participant is persisted in the database and returns the
	 * insert date.
	 * 
	 * @param con
	 *            Connection
	 * @param participantGridId
	 * @return Insert Date
	 * @throws SQLException
	 */
	public Date checkParticipantForRollback(Connection con, String participantGridId,
			String participantExtension) throws SQLException
	{

		logger.debug("Checking if participant exists for Rollback");
		Date insertDate = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Long id = null;
		if (participantGridId == null || participantExtension == null)
		{
			return insertDate;
		}
		try
		{
			ps =
					con
							.prepareStatement("select ID,PARTICIPANT_ID from IDENTIFIER where ROOT = ? AND EXTENSION= ? AND PARTICIPANT_ID IS NOT NULL");
			ps.setString(1, participantGridId);
			ps.setString(2, participantExtension);
			rs = ps.executeQuery();
			// check if identifier is in DB
			if (rs.next() && !rs.isBeforeFirst() && rs.getLong("PARTICIPANT_ID") != 0)
			{
				logger.debug("participantid " + rs.getLong("PARTICIPANT_ID"));
				// already present;update the identifier table
				id = rs.getLong("PARTICIPANT_ID");
			}
			//Clean up
			rs = SQLHelper.closeResultSet(rs);
			ps = SQLHelper.closePreparedStatement(ps);
			
			if (id != null)
			{
				ps = con.prepareStatement("select CTOM_INSERT_DATE from PARTICIPANT where ID=?");
				ps.setLong(1, id);
				rs = ps.executeQuery();
				if (rs.next() && !rs.isBeforeFirst())
				{
					insertDate = new java.util.Date(rs.getTimestamp(1).getTime());
					logger.debug("insert date " + insertDate);
				}
			}
		}
		catch (SQLException se)
		{
			logger.error("Error checking if participant exists for rollback",se);
			throw se;
		}
		finally
		{
			rs = SQLHelper.closeResultSet(rs);
			ps = SQLHelper.closePreparedStatement(ps);

		}
		return insertDate;
	}

	/**
	 * Checks if the study is persisted in the database and returns the insert
	 * date.
	 * 
	 * @param con
	 *            Connection
	 * @param studyGridId
	 *            Study Grid Id
	 * @return Insert Date
	 * @throws SQLException
	 */
	public Date checkStudyForRollback(Connection con, String studyGridId) throws SQLException
	{

		logger.debug("Checking if study exists for rollback");
		Date insertDate = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Long id = null;
		if (studyGridId == null)
			return insertDate;
		try
		{
			ps =
					con
							.prepareStatement("select ID,PROTOCOL_ID from IDENTIFIER where ROOT = ? AND PROTOCOL_ID IS NOT NULL");
			ps.setString(1, studyGridId);
			rs = ps.executeQuery();
			// check if identifier is in DB
			if (rs.next() && !rs.isBeforeFirst() && rs.getLong("PROTOCOL_ID") != 0)
			{
				logger.debug("protocolID " + rs.getLong("PROTOCOL_ID"));
				// already present;update the identifier table
				id = rs.getLong("PROTOCOL_ID");
			}
			
			//Clean up
			rs = SQLHelper.closeResultSet(rs);
			ps = SQLHelper.closePreparedStatement(ps);
			
			// insert into Protocol the insert date
			if (id != null)
			{
				ps = con.prepareStatement("select CTOM_INSERT_DATE from PROTOCOL where ID=?");
				ps.setLong(1, id);
				rs = ps.executeQuery();
				if (rs.next() && !rs.isBeforeFirst())
				{
					insertDate = new java.util.Date(rs.getTimestamp(1).getTime());
					logger.debug("insert date " + insertDate);
				}
			}
		}
		catch (SQLException se)
		{
			logger.error("Error performing check if study exists rollback",se);
			throw se;
		}
		finally
		{
			rs = SQLHelper.closeResultSet(rs);
			ps = SQLHelper.closePreparedStatement(ps);

		}
		return insertDate;
	}

	/**
	 * getStudy retrieves the Study the Participant is associated with.
	 * 
	 * @param con
	 *            Connection
	 * @param part
	 *            Participant
	 * @return study Protocol
	 * @throws SQLException
	 */
	public Protocol getStudy(Connection con, Participant part) throws SQLException
	{

		logger.debug("Inside getStudy method");
		PreparedStatement ps = null;
		ResultSet rs = null;
		Protocol study = null;
		Long protocolId = null;
		//Long healthCareSiteId = null;

		try
		{
			ps =
					con
							.prepareStatement("select protocol_id,healthcare_site_id from study_site where id in (select study_site_id from study_Participant_assignment where id in (select study_Participant_assignmnt_id from identifier where extension=? and study_participant_assignmnt_id is not null)) ");
			ps.setString(1, part.getIdentifier().getExtension());
			rs = ps.executeQuery();
			if (rs.next())
			{
				protocolId = rs.getLong(1);
				//healthCareSiteId = rs.getLong(2);

			}
			//Clean up
			rs = SQLHelper.closeResultSet(rs);
			ps = SQLHelper.closePreparedStatement(ps);
			
			if (protocolId != null)
			{
				ps =
						con
								.prepareStatement("select p.long_title_text,i.root,i.extension,i.assigning_authority_name from identifier i, protocol p where i.protocol_id=p.id and p.id=?");
				ps.setLong(1, protocolId);
				rs = ps.executeQuery();
				if (rs.next())
				{
					study = new Protocol();
					study.setLongTxtTitle(rs.getString("LONG_TITLE_TEXT"));
					study.getIdentifier().setRoot(rs.getString("ROOT"));
					study.getIdentifier().setExtension(rs.getString("EXTENSION"));
					study.getIdentifier().setAssigningAuthorityName(
							rs.getString("ASSIGNING_AUTHORITY_NAME"));
				}
			}
		}
		catch (SQLException se)
		{
			logger.error("Error retrieving the study the participant is associated with",se);
			throw se;
		}
		finally
		{
			rs = SQLHelper.closeResultSet(rs);
			ps = SQLHelper.closePreparedStatement(ps);


		}
		return study;
	}

	/**
	 * Used for Demo purpose. checks to see if the participant exists.
	 * 
	 * @param con
	 *            Connection
	 * @param protocol
	 *            Protocol
	 * @param mrn
	 *            patient mrn
	 * @return StudyPArticipantAssignment Id
	 * @throws SQLException
	 */
	public Long checkParticipantExists(Connection con, Protocol protocol, String mrn)
			throws SQLException
	{

		PreparedStatement ps = null;
		ResultSet rs = null;
		Long spa_id = null;
		String root = null;
		int count = 0;

		try
		{
			ps =
					con
							.prepareStatement("select study_participant_assignmnt_id, root from identifier where extension=? and study_participant_assignmnt_id is not null");
			ps.setString(1, mrn);
			rs = ps.executeQuery();
			if (rs.next())
			{
				spa_id = rs.getLong(1);
				root = rs.getString(2);

			}
			//Clean up
			rs = SQLHelper.closeResultSet(rs);
			ps = SQLHelper.closePreparedStatement(ps);
			
			if (spa_id != null)
			{
				String identOrg = root + "." + mrn;
				ps =
						con
								.prepareStatement("select count(*) from identifier where protocol_id in (select protocol_id from study_site where id in( select study_site_id from study_participant_assignment where id = ? and STUDY_PARTICIPANT_IDENTFR_ORIG=? ))");
				ps.setLong(1, spa_id);
				ps.setString(2, identOrg);// get the participant root+mrn
				rs = ps.executeQuery();
				if (rs.next())
				{
					count = rs.getInt(1);
				}
			}
			if (count == 1 && root != null)
			{
				return spa_id;
			}

		}
		catch (SQLException se)
		{
			logger.error("Error performing if participant exists",se);
			throw se;
		}
		finally
		{
			rs = SQLHelper.closeResultSet(rs);
			ps = SQLHelper.closePreparedStatement(ps);

		}
		return spa_id;
	}

	/**
	 * Updates the Participant Grid id --just for Demo
	 * 
	 * @param con
	 *            Connection
	 * @param participantGridId
	 *            Participant Grid Id
	 * @param spaId
	 *            Study Participant Assignment Id
	 * @param mrn
	 *            Medical Resource Number
	 * @throws SQLException
	 */
	public void updateParticipantGridId(Connection con, String participantGridId, Long spaId,
			String mrn) throws SQLException
	{

		PreparedStatement ps = null;
		logger.debug(participantGridId + " " + spaId + " " + mrn);
		try
		{
			ps =
					con
							.prepareStatement("update identifier set root=? where extension=? and study_participant_assignmnt_id=?");
			ps.setString(1, participantGridId);
			ps.setString(2, mrn);
			ps.setLong(3, spaId);
			ps.executeUpdate();
			con.commit();
		}
		catch (SQLException se)
		{
			logger.error("Error updating the participant grid id",se);
			throw se;
		}
		finally
		{
			ps = SQLHelper.closePreparedStatement(ps);
		}
	}
	
	
	/**
	 * Retrieving Investigator information using the CTEPID
	 * @param con
	 * @param ctepId
	 * @return
	 * @throws SQLException
	 */
	public Investigator retrieveInvestigator(Connection con, String ctepId) throws SQLException
	{
		PreparedStatement ps = null;
		ResultSet rs =null;
		Investigator pi = new Investigator();
		logger.debug("CTEP Identifier or nci_identifier"+ctepId);
		try
		{
			ps =
					con
							.prepareStatement("select last_name,first_name,middle_name,telecom_address,street_address,city,state,zip_code,country_code,phone,source,source_extract_date,ctom_insert_date,ctom_update_date from investigator where nci_identifier=?");
			ps.setString(1, ctepId);
			rs = ps.executeQuery();
			if (rs.next())
			{
				pi.setLastName(rs.getString("last_name"));
				pi.setFirstName(rs.getString("first_name"));
				pi.setMiddleNAle(rs.getString("middle_name"));
				pi.setTelecomAddr(rs.getString("telecom_address"));
				pi.setStreetAddr(rs.getString("street_address"));
				pi.setCity(rs.getString("city"));
				pi.setState(rs.getString("state"));
				pi.setZipCode(rs.getString("zip_code"));
				pi.setCountryCode(rs.getString("country_code"));
				pi.setPhone(rs.getString("phone"));
				pi.setSource(rs.getString("source"));
				pi.setSrcExtractDt(rs.getDate("source_extract_date"));
				pi.setCtomInsertDt(rs.getDate("ctom_insert_date"));
				pi.setCtomUpdateDt(rs.getDate("ctom_update_date"));
			}
		
			return pi;
		}
		catch (SQLException se)
		{
			logger.error("Error retrieving Investigator information uisng the ctepid",se);
			throw se;
		}
		finally
		{
			ps = SQLHelper.closePreparedStatement(ps);
		}
	}
	/**
	 * Retrieving HealthCareSite information using the CTEPID
	 * @param con
	 * @param ctepId
	 * @return
	 * @throws SQLException
	 */
	public HealthCareSite retrieveHealCareSite(Connection con, String ctepId) throws SQLException
	{
		PreparedStatement ps = null;
		ResultSet rs =null;
		HealthCareSite hcs = new HealthCareSite();
		logger.debug("CTEP Identifier/nci_institute_code"+ctepId);
		try
		{
			ps =
					con
							.prepareStatement("select name,telecom_address,street_address,city,state_code,postal_code,country_code,source,source_extract_date,ctom_insert_date,ctom_update_date from healthcare_site where nci_institute_code=?");
			ps.setString(1, ctepId);
			rs = ps.executeQuery();
			if (rs.next())
			{
				hcs.setName(rs.getString("name"));
				hcs.setTelecomAddr(rs.getString("telecom_address"));
				hcs.setStreetAddr(rs.getString("street_address"));
				hcs.setCity(rs.getString("city"));
				hcs.setStateCode(rs.getString("state_code"));
				hcs.setPostalCode(rs.getString("postal_code"));
				hcs.setCountryCode(rs.getString("country_code"));
				hcs.setSource(rs.getString("source"));
				hcs.setSrcExtractDt(rs.getDate("source_extract_date"));
				hcs.setCtomInsertDt(rs.getDate("ctom_insert_date"));
				hcs.setCtomUpdateDt(rs.getDate("ctom_update_date"));
			}
		
			return hcs;
		}
		catch (SQLException se)
		{
			logger.error("Error retrieving HealthCareSite information uisng the ctepid",se);
			throw se;
		}
		finally
		{
			ps = SQLHelper.closePreparedStatement(ps);
		}
	}
	
	/**
	 * @param con
	 * @param protocolId
	 * @return List of CTEP Ids
	 * @throws SQLException
	 */
	public List<String> retrieveCTEPIDForHCS(Connection con, Long protocolId) throws SQLException
	{
		PreparedStatement ps = null;
		ResultSet rs =null;
		List<String> ctepList = null;
		logger.debug("Protocol ID to retrieve HealthCareSite "+protocolId);
		try
		{
			ps =
				con
						.prepareStatement("select nci_institute_code from healthcare_site where id in (select healthcare_site_id from study_site where protocol_id=?)");
		ps.setLong(1, protocolId);
		rs = ps.executeQuery();
		int i=0;
		ctepList = new ArrayList<String>();
		while (rs.next())
		{
			ctepList.add(i,rs.getString(1));
			i++;
		}
		}
		catch (SQLException se)
		{
			logger.error("Error retrieving ctepid/nci_institute_code for HCS for the given protocol id",se);
			throw se;
		}
		finally
		{
			ps = SQLHelper.closePreparedStatement(ps);
		}
		return ctepList;
	}
	
	/**
	 * @param con
	 * @param protocolId
	 * @return List of CTEP Ids
	 * @throws SQLException
	 */
	public List<String> retrieveCTEPIDForPI(Connection con, Long protocolId) throws SQLException
	{
		PreparedStatement ps = null;
		ResultSet rs =null;
		List<String> ctepList = null;
		logger.debug("Protocol ID to retrieve PI "+protocolId);
		try
		{
			ps =
				con
						.prepareStatement("select nci_identifier from investigator where id in (select investigator_id from study_investigator where protocol_id=?)");
		ps.setLong(1, protocolId);
		rs = ps.executeQuery();
		int i=0;
		ctepList = new ArrayList<String>();
		while (rs.next())
		{
			ctepList.add(i,rs.getString(1));
			i++;
		}
		}
		catch (SQLException se)
		{
			logger.error("Error retrieving ctepid/nci_identifier for PI for the given protocol id",se);
			throw se;
		}
		finally
		{
			ps = SQLHelper.closePreparedStatement(ps);
		}
		return ctepList;
	}
}
