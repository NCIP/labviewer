/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */
package gov.nih.nci.ctom.ctlab.handler;

import gov.nih.nci.ctom.ctlab.domain.Participant;
import gov.nih.nci.ctom.ctlab.domain.Protocol;
import gov.nih.nci.ctom.ctlab.persistence.CTLabDAO;
import gov.nih.nci.ctom.ctlab.persistence.SQLHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.apache.log4j.Logger;

/**
 * ParticipantHandler persists Participant object to the CTODS database
 * <P>
 * 
 * @author asharma
 */
public class ParticipantHandler extends CTLabDAO implements HL7V3MessageHandlerInterface
{

	// Logging File
	private static Logger logger = Logger.getLogger("client");

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nih.nci.ctom.ctlab.handler.HL7V3MessageHandler#persist(java.sql.Connection,
	 *      gov.nih.nci.ctom.ctlab.domain.Protocol)
	 */
	public void persist(Connection con, Protocol protocol) throws Exception
	{

		logger.debug("Saving the Participant");
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean identifierUpdInd = false;

		// retrieve Participant details from Protocol
		Participant participant =
				protocol.getHealthCareSite().getStudyParticipantAssignment().getParticipant();

		// Get the concept descriptor ids for the race and gender
		Long genderCDId =
				insertOrsaveConceptDescriptor(con, participant.getAdminGenderCode(), participant
						.getAdminGenderCodeSystem(), participant.getAdminGenderCodeSystemName(),
						null, null);
		Long raceCDId =
				insertOrsaveConceptDescriptor(con, participant.getRaceCode(), participant
						.getRaceCodeSystem(), participant.getRaceCodeSystemName(), null, null);

		// updates or inserts the IDentifier
		HL7V3MessageHandlerFactory.getInstance().getHandler("PARTICIPANT_IDENTIFIER").persist(con,
				protocol);

		try
		{
			// insert into participant if there was no participant associated
			// with
			// identifier.
			if (participant.getId() == null)
			{
				identifierUpdInd = true;

				// get the next value of id from sequence
				Long id = getNextVal(con, "Person_SEQ");

				// set the id
				participant.setId(id);

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
									.prepareStatement("insert into PARTICIPANT (ID, BIRTH_DATE, INITIALS, LAST_NAME, FIRST_NAME, ETHNIC_GROUP_CODE, CTOM_INSERT_DATE, ADM_GNDR_CONCEPT_DESCRIPTOR_ID,RACE_CONCEPT_DESCRIPTOR_ID )  values(?,?,?,?,?,?,?,?,?)");
					ps.setLong(8, genderCDId);
					ps.setLong(9, raceCDId);
				}
				else if (!raceCode && !genderCode)
				{
					ps =
							con
									.prepareStatement("insert into PARTICIPANT (ID, BIRTH_DATE, INITIALS, LAST_NAME, FIRST_NAME, ETHNIC_GROUP_CODE, CTOM_INSERT_DATE)  values(?,?,?,?,?,?,?)");

				}
				else if (!raceCode)
				{
					ps =
							con
									.prepareStatement("insert into PARTICIPANT (ID, BIRTH_DATE, INITIALS, LAST_NAME, FIRST_NAME, ETHNIC_GROUP_CODE, CTOM_INSERT_DATE, ADM_GNDR_CONCEPT_DESCRIPTOR_ID )  values(?,?,?,?,?,?,?,?)");
					ps.setLong(8, genderCDId);
				}
				else if (!genderCode)
				{
					ps =
							con
									.prepareStatement("insert into PARTICIPANT (ID, BIRTH_DATE, INITIALS, LAST_NAME, FIRST_NAME, ETHNIC_GROUP_CODE, CTOM_INSERT_DATE, RACE_CONCEPT_DESCRIPTOR_ID )  values(?,?,?,?,?,?,?,?)");
					ps.setLong(8, raceCDId);
				}

				ps.setLong(1, participant.getId());
				if (participant.getBirthDate() != null)
				{
					ps.setDate(2, new java.sql.Date(participant.getBirthDate().getTime()));
				}
				else
				{
					ps.setDate(2, null);
				}

				ps.setString(3, participant.getInitials());
				ps.setString(4, participant.getLastName());
				ps.setString(5, participant.getFirstName());
				ps.setString(6, participant.getEthnicGroupCode());
				if (participant.getCtomInsertDate() == null)
				{
					ps.setTimestamp(7, new java.sql.Timestamp(new Date().getTime()));
				}
				else
				{
					ps.setTimestamp(7, new java.sql.Timestamp(participant.getCtomInsertDate()
							.getTime()));
				}
				ps.execute();
				con.commit();

				if (identifierUpdInd && participant.getIdentifier() != null)
				{
					updateIdentifier(con, participant);
				}
			} // update into participant if there was a participant associated
			// with identifier.
			else
			{
				ps =
						con
								.prepareStatement("update PARTICIPANT set BIRTH_DATE = ?, INITIALS = ?, LAST_NAME = ?, FIRST_NAME = ?, ETHNIC_GROUP_CODE=?, ADM_GNDR_CONCEPT_DESCRIPTOR_ID=?,RACE_CONCEPT_DESCRIPTOR_ID=?,CTOM_UPDATE_DATE=? where ID = ?");
				if (participant.getBirthDate() != null)
				{
					ps.setDate(1, new java.sql.Date(participant.getBirthDate().getTime()));
				}
				else
				{
					ps.setDate(1, null);
				}
				ps.setString(2, participant.getInitials());
				ps.setString(3, participant.getLastName());
				ps.setString(4, participant.getFirstName());
				ps.setString(5, participant.getEthnicGroupCode());
				ps.setLong(6, genderCDId);
				ps.setLong(7, raceCDId);
				if (participant.getCtomInsertDate() == null)
				{
					ps.setTimestamp(8, new java.sql.Timestamp(new Date().getTime()));
				}
				else
				{
					ps.setTimestamp(8, new java.sql.Timestamp(participant.getCtomInsertDate()
							.getTime()));
				}
				ps.setLong(9, participant.getId());
				ps.executeUpdate();
				con.commit();
			}
		}
		catch (SQLException se)
		{
			logger.error("Error saving the Participant",se);
			throw (new Exception(se.getLocalizedMessage()));

		}
		finally
		{
			//clean up
			rs = SQLHelper.closeResultSet(rs);
			ps = SQLHelper.closePreparedStatement(ps);

		}

	}

}
