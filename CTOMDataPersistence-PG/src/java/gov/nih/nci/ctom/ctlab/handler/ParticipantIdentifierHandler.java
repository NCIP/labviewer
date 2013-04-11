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

import org.apache.log4j.Logger;

/**
 * Persists the identifier object into the database. The identifier is
 * associated with the participant object.If the identifier object is already
 * present, the method updates the identifier else insert the identifier.
 * 
 * @author asharma
 */
public class ParticipantIdentifierHandler extends CTLabDAO implements HL7V3MessageHandlerInterface
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

		PreparedStatement ps = null;
		ResultSet rs = null;
		Long id = null;
		Long identifierId = null;

		logger.debug("Saving the Participant Identifier");
		// retrieve the participant data from Protocol
		Participant participant =
				protocol.getHealthCareSite().getStudyParticipantAssignment().getParticipant();

		// check if the participant is associated with identifier
		try
		{
			if (participant.getIdentifier() != null)
			{
				ps =
						con
								.prepareStatement("select ID,PARTICIPANT_ID from IDENTIFIER where EXTENSION = ? AND PARTICIPANT_ID IS NOT NULL");
				ps.setString(1, participant.getIdentifier().getExtension());
				rs = ps.executeQuery();

				// check if identifier is in DB
				if (rs.next() && !rs.isBeforeFirst() && rs.getLong("PARTICIPANT_ID") != 0)
				{
					// already present;update the identifier table
					id = rs.getLong("PARTICIPANT_ID");
					participant.setId(id);
					participant.getIdentifier().setId(rs.getLong("ID"));
				}
				else
				{
					//clean up
					ps = SQLHelper.closePreparedStatement(ps);
					
					// get the identifier id
					identifierId = getNextVal(con, "IDENTIFIER_SEQ");
					// insert new into identifier get the identifierid and
					// insert
					// into participant table
					ps =
							con
									.prepareStatement("insert into IDENTIFIER (Id,EXTENSION, SOURCE,ROOT,assigning_authority_name) values (?,?,?,?,?)");
					// need to set the participantid into the identifier table
					ps.setLong(1, identifierId);
					ps.setString(2, participant.getIdentifier().getExtension());
					ps.setString(3, participant.getIdentifier().getSource());
					ps.setString(4, participant.getIdentifier().getRoot());
					ps.setString(5, participant.getIdentifier().getAssigningAuthorityName());
					ps.executeUpdate();
					con.commit();

					participant.setId(id);
					participant.getIdentifier().setId(identifierId);
				}
			}
		}
		catch (SQLException se)
		{
			logger.error("Error saving the Participant Identifier",se);
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
