/**
 * Copyright Notice. Copyright 2008 ScenPro, Inc (“caBIG™
 * Participant”).caXchange was created with NCI funding and is part of the
 * caBIG™ initiative. The software subject to this notice and license includes
 * both human readable source code form and machine readable, binary, object
 * code form (the “caBIG™ Software”). This caBIG™ Software License (the
 * “License”) is between caBIG™ Participant and You. “You (or “Your”) shall mean
 * a person or an entity, and all other entities that control, are controlled
 * by, or are under common control with the entity. “Control” for purposes of
 * this definition means (i) the direct or indirect power to cause the direction
 * or management of such entity, whether by contract or otherwise, or (ii)
 * ownership of fifty percent (50%) or more of the outstanding shares, or (iii)
 * beneficial ownership of such entity. License. Provided that You agree to the
 * conditions described below, caBIG™ Participant grants You a non-exclusive,
 * worldwide, perpetual, fully-paid-up, no-charge, irrevocable, transferable and
 * royalty-free right and license in its rights in the caBIG™ Software,
 * including any copyright or patent rights therein, to (i) use, install,
 * disclose, access, operate, execute, reproduce, copy, modify, translate,
 * market, publicly display, publicly perform, and prepare derivative works of
 * the caBIG™ Software in any manner and for any purpose, and to have or permit
 * others to do so; (ii) make, have made, use, practice, sell, and offer for
 * sale, import, and/or otherwise dispose of caBIG™ Software (or portions
 * thereof); (iii) distribute and have distributed to and by third parties the
 * caBIG™ Software and any modifications and derivative works thereof; and (iv)
 * sublicense the foregoing rights set out in (i), (ii) and (iii) to third
 * parties, including the right to license such rights to further third parties.
 * For sake of clarity, and not by way of limitation, caBIG™ Participant shall
 * have no right of accounting or right of payment from You or Your sublicensees
 * for the rights granted under this License. This License is granted at no
 * charge to You. Your downloading, copying, modifying, displaying, distributing
 * or use of caBIG™ Software constitutes acceptance of all of the terms and
 * conditions of this Agreement. If you do not agree to such terms and
 * conditions, you have no right to download, copy, modify, display, distribute
 * or use the caBIG™ Software. 1. Your redistributions of the source code for
 * the caBIG™ Software must retain the above copyright notice, this list of
 * conditions and the disclaimer and limitation of liability of Article 6 below.
 * Your redistributions in object code form must reproduce the above copyright
 * notice, this list of conditions and the disclaimer of Article 6 in the
 * documentation and/or other materials provided with the distribution, if any.
 * 2. Your end-user documentation included with the redistribution, if any, must
 * include the following acknowledgment: “This product includes software
 * developed by ScenPro, Inc.” If You do not include such end-user
 * documentation, You shall include this acknowledgment in the caBIG™ Software
 * itself, wherever such third-party acknowledgments normally appear. 3. You may
 * not use the names “ScenPro, Inc”, “The National Cancer Institute”, “NCI”,
 * “Cancer Bioinformatics Grid” or “caBIG™” to endorse or promote products
 * derived from this caBIG™ Software. This License does not authorize You to use
 * any trademarks, service marks, trade names, logos or product names of either
 * caBIG™ Participant, NCI or caBIG™, except as required to comply with the
 * terms of this License. 4. For sake of clarity, and not by way of limitation,
 * You may incorporate this caBIG™ Software into Your proprietary programs and
 * into any third party proprietary programs. However, if You incorporate the
 * caBIG™ Software into third party proprietary programs, You agree that You are
 * solely responsible for obtaining any permission from such third parties
 * required to incorporate the caBIG™ Software into such third party proprietary
 * programs and for informing Your sublicensees, including without limitation
 * Your end-users, of their obligation to secure any required permissions from
 * such third parties before incorporating the caBIG™ Software into such third
 * party proprietary software programs. In the event that You fail to obtain
 * such permissions, You agree to indemnify caBIG™ Participant for any claims
 * against caBIG™ Participant by such third parties, except to the extent
 * prohibited by law, resulting from Your failure to obtain such permissions. 5.
 * For sake of clarity, and not by way of limitation, You may add Your own
 * copyright statement to Your modifications and to the derivative works, and
 * You may provide additional or different license terms and conditions in Your
 * sublicenses of modifications of the caBIG™ Software, or any derivative works
 * of the caBIG™ Software as a whole, provided Your use, reproduction, and
 * distribution of the Work otherwise complies with the conditions stated in
 * this License. 6. THIS caBIG™ SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED
 * OR IMPLIED WARRANTIES (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE)
 * ARE DISCLAIMED. IN NO EVENT SHALL THE ScenPro, Inc OR ITS AFFILIATES BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS caBIG™ SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */
package gov.nih.nci.ctom.ctlab.persistence;

import gov.nih.nci.ctom.ctlab.domain.Participant;
import gov.nih.nci.ctom.ctlab.domain.Protocol;
import gov.nih.nci.ctom.ctlab.domain.StudyParticipantAssignment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

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
			if (ps != null)
			{
				ps.close();
			}
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
			if (ps != null)
			{
				ps.close();
			}
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
			if (ps != null)
			{
				ps.close();
			}
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
			if (ps != null)
			{
				ps.close();
			}
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
			if (rs != null)
			{
				rs.close();
			}
			if (ps != null)
			{
				ps.close();
			}
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
			if (rs != null)
			{
				rs.close();
			}
			if (ps != null)
			{
				ps.close();
			}
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
			if (rs != null)
			{
				rs.close();
			}
			if (ps != null)
			{
				ps.close();
			}
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
			if (rs != null)
			{
				rs.close();
			}
			if (ps != null)
			{
				ps.close();
			}
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
			if (id != null)
			{
				logger.debug("Performing rollback of the study " + id);
				// delete from the identifier table
				ps = con.prepareStatement("delete from IDENTIFIER where PROTOCOL_ID=?");
				ps.setLong(1, id);
				ps.executeUpdate();

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
			if (rs != null)
			{
				rs.close();
			}
			if (ps != null)
			{
				ps.close();
			}
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
		ResultSet rs1 = null;
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
			rs.close();
			if (id != null)
			{
				ps = con.prepareStatement("select CTOM_INSERT_DATE from PARTICIPANT where ID=?");
				ps.setLong(1, id);
				rs1 = ps.executeQuery();
				if (rs1.next() && !rs1.isBeforeFirst())
				{
					insertDate = new java.util.Date(rs1.getTimestamp(1).getTime());
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
			if (rs != null)
			{
				rs.close();
			}
			if (rs1 != null)
			{
				rs1.close();
			}
			if (ps != null)
			{
				ps.close();
			}

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
		ResultSet rs1 = null;
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
			rs.close();
			// insert into Protocol the insert date
			if (id != null)
			{
				ps = con.prepareStatement("select CTOM_INSERT_DATE from PROTOCOL where ID=?");
				ps.setLong(1, id);
				rs1 = ps.executeQuery();
				if (rs1.next() && !rs1.isBeforeFirst())
				{
					insertDate = new java.util.Date(rs1.getTimestamp(1).getTime());
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

			if (rs1 != null)
			{
				rs1.close();
			}
			if (rs != null)
			{
				rs.close();
			}
			if (ps != null)
			{
				ps.close();
			}

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
		ResultSet rs1 = null;
		Protocol study = null;
		Long protocolId = null;
		Long healthCareSiteId = null;

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
				healthCareSiteId = rs.getLong(2);

			}
			if (protocolId != null)
			{
				ps =
						con
								.prepareStatement("select p.long_title_text,i.root,i.extension,i.assigning_authority_name from identifier i, protocol p where i.protocol_id=p.id and p.id=?");
				ps.setLong(1, protocolId);
				rs1 = ps.executeQuery();
				if (rs1.next())
				{
					study = new Protocol();
					study.setLongTxtTitle(rs1.getString("LONG_TITLE_TEXT"));
					study.getIdentifier().setRoot(rs1.getString("ROOT"));
					study.getIdentifier().setExtension(rs1.getString("EXTENSION"));
					study.getIdentifier().setAssigningAuthorityName(
							rs1.getString("ASSIGNING_AUTHORITY_NAME"));
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
			if (rs != null)
			{
				rs.close();
			}
			if (rs1 != null)
			{
				rs1.close();
			}
			if (ps != null)
			{
				ps.close();
			}

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
		ResultSet rs1 = null;
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
			if (spa_id != null)
			{
				String identOrg = root + "." + mrn;
				ps =
						con
								.prepareStatement("select count(*) from identifier where protocol_id in (select protocol_id from study_site where id in( select study_site_id from study_participant_assignment where id = ? and STUDY_PARTICIPANT_IDENTFR_ORIG=? ))");
				ps.setLong(1, spa_id);
				ps.setString(2, identOrg);// get the participant root+mrn
				rs1 = ps.executeQuery();
				if (rs1.next())
				{
					count = rs1.getInt(1);
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
			if (rs != null)
			{
				rs.close();
			}
			if (rs1 != null)
			{
				rs1.close();
			}
			if (ps != null)
			{
				ps.close();
			}

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
			if (ps != null)
			{
				ps.close();
			}
		}
	}

}
