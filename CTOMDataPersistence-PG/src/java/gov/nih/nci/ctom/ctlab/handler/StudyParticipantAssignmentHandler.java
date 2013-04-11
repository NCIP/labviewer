/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */
package gov.nih.nci.ctom.ctlab.handler;

import gov.nih.nci.ctom.ctlab.domain.Protocol;
import gov.nih.nci.ctom.ctlab.domain.StudyParticipantAssignment;
import gov.nih.nci.ctom.ctlab.persistence.CTLabDAO;
import gov.nih.nci.ctom.ctlab.persistence.SQLHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

/**
 * StudyParticipantAssignmentHandler persists Study Participant Assignment to
 * CTODS database
 * 
 * @author asharma
 */
public class StudyParticipantAssignmentHandler extends CTLabDAO implements
		HL7V3MessageHandlerInterface
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

		logger.debug("Saving the StudyParticipantAssignment (SPA)");
		PreparedStatement ps = null;
		ResultSet rs = null;
		Long spaId = null;
		boolean identifierUpdInd = false;
		// retrieve the SPA from protocol
		StudyParticipantAssignment spa =
				protocol.getHealthCareSite().getStudyParticipantAssignment();
		Long ssId = spa.getStudySiteId();
		try
		{
			ps =
					con
							.prepareStatement("select spa.id from STUDY_PARTICIPANT_ASSIGNMENT spa, identifier ii where ii.STUDY_PARTICIPANT_ASSIGNMNT_ID = spa.id AND spa.STUDY_SITE_ID = ? AND ii.extension=?");
			ps.setLong(1, ssId);
			ps.setString(2, spa.getIdentifier().getExtension());
			rs = ps.executeQuery();
			if (rs.next())
			{
				spaId = rs.getLong(1);
				spa.setId(spaId);
			}
			else
			{
				//clean up
				ps = SQLHelper.closePreparedStatement(ps);
				
				// Check study participant identifier, if it exists, then don't
				// insert new SPA or Participant

				HL7V3MessageHandlerFactory.getInstance().getHandler("SPA_IDENTIFIER").persist(con,
						protocol);

				if (spa.getId() == null)
				{
					// Get Id from sequence
					spaId = getNextVal(con, "STUDY_PARTICIPANT_ASSGNMNT_SEQ");
					identifierUpdInd = true;

					// Save Participant
					HL7V3MessageHandlerFactory.getInstance().getHandler("PARTICIPANT").persist(con,
							protocol);

					// insert into STUDY_PARTICIPANT_ASSIGNMENT
					ps =
							con
									.prepareStatement("insert into STUDY_PARTICIPANT_ASSIGNMENT (ID, STUDY_PARTICIPANT_IDENTFR_ORIG, STUDY_SITE_ID, PARTICIPANT_ID,type)  values(?,?,?,?,?)");
					ps.setLong(1, spaId);
					ps.setString(2, spa.getStudyPartIdOrig());
					ps.setLong(3, ssId);
					ps.setLong(4, spa.getParticipant().getId());
					ps.setString(5, spa.getType());
					ps.execute();
					con.commit();
					if (identifierUpdInd && spa.getParticipant().getIdentifier() != null)
					{
						spa.setId(spaId);
						updateIdentifier(con, spa, spa.getIdentifier().getId());
					}
				}

			}
		}
		catch (SQLException se)
		{
			logger.error("Error saving the Study Participant Assignment (SPA)",se);
			throw (new Exception(se.getLocalizedMessage()));

		}
		finally
		{
			//clean up
			rs = SQLHelper.closeResultSet(rs);
			ps = SQLHelper.closePreparedStatement(ps);

		}

		// save healthCare-participant
		if (protocol.getHealthCareSite().getId() != null && spa.getParticipant().getId() != null)
		{
			HL7V3MessageHandlerFactory.getInstance().getHandler("HEALTH_CARE_SITE_PARTICIPANT")
					.persist(con, protocol);

		}
		// Save Activity
		if (spa.getActivity() != null)
		{
			HL7V3MessageHandlerFactory.getInstance().getHandler("ACTIVITY").persist(con, protocol);
		}
	}

}
