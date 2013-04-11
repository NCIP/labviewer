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
 * Persists the identifier object into the database. The identifier is
 * associated with the Study Participant Assignment object.If the identifier
 * object is already present, the method updates the identifier else insert the
 * identifier.
 * 
 * @author asharma
 */
public class SPAIdentifierHandler extends CTLabDAO implements HL7V3MessageHandlerInterface
{

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

		logger.debug("Saving the SPA Identifier");

		// retrieve the Study Participant assignment data from the Protocol
		StudyParticipantAssignment spa =
				protocol.getHealthCareSite().getStudyParticipantAssignment();

		// check if the StudyParticipantAssignment is associated with identifier
		try
		{
			if (spa.getIdentifier() != null)
			{
				//ps = con.prepareStatement("select ID,STUDY_PARTICIPANT_ASSIGNMNT_ID from IDENTIFIER where EXTENSION = ? AND STUDY_PARTICIPANT_ASSIGNMNT_ID IS NOT NULL");
				String query = "SELECT i.id, "
                             +        "i.study_participant_assignmnt_id "
                             +   "FROM identifier i "
                             +   "JOIN study_participant_assignment spa ON(i.study_participant_assignmnt_id = spa.id) "
                             +  "WHERE i.extension = ? "
                             +    "AND spa.study_site_id = ?";
				ps = con.prepareStatement(query);
				ps.setString(1, spa.getIdentifier().getExtension());
				ps.setLong(2, spa.getStudySiteId());
				rs = ps.executeQuery();

				// check if identifier is in DB
//				if (rs.next() && !rs.isBeforeFirst()
//						&& rs.getLong("STUDY_PARTICIPANT_ASSIGNMNT_ID") != 0)
				if (rs.next())
				{
					// already present;update the identifier table
					id = rs.getLong("STUDY_PARTICIPANT_ASSIGNMNT_ID");
					identifierId = rs.getLong("ID");
										
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
									.prepareStatement("insert into IDENTIFIER (Id,EXTENSION, SOURCE,ROOT) values (?,?,?,?)");
					// need to set the participantid into the identifier table
					ps.setLong(1, identifierId);
					ps.setString(2, spa.getIdentifier().getExtension());
					ps.setString(3, spa.getIdentifier().getSource());
					ps.setString(4, spa.getIdentifier().getRoot());
					ps.executeUpdate();
					con.commit();
				}
				
				spa.setId(id);
				spa.getIdentifier().setId(identifierId);

			}
		}
		catch (SQLException se)
		{
			logger.error("Error saving the SPA Identifier",se);
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
