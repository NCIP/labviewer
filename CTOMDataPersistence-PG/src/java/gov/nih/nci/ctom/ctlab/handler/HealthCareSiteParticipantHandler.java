/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */
package gov.nih.nci.ctom.ctlab.handler;

import gov.nih.nci.ctom.ctlab.domain.Protocol;
import gov.nih.nci.ctom.ctlab.persistence.CTLabDAO;
import gov.nih.nci.ctom.ctlab.persistence.SQLHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

import org.apache.log4j.Logger;

/**
 * HealthCareSiteParticipantHandler persists the HealthCareSite Participant join
 * data to CTODS database.
 * 
 * @author asharma
 */
public class HealthCareSiteParticipantHandler extends CTLabDAO implements
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

		logger.debug("Saving the HealthCareSite - participant join ");
		PreparedStatement ps = null;
		Long id = null;

		try
		{
			// Get Id from sequence
			id = getNextVal(con, "healthcare_site_prtcpnt_seq");

			// insert into healthcare_site_prtcpnt
			ps =
					con
							.prepareStatement("insert into healthcare_site_prtcpnt (ID, healthcare_site_id, participant_id,ctom_insert_date)  values(?,?,?,?)");
			ps.setLong(1, id);
			ps.setLong(2, protocol.getHealthCareSite().getId());
			ps.setLong(3, protocol.getHealthCareSite().getStudyParticipantAssignment()
					.getParticipant().getId());
			Date insertDt = new Date();
			ps.setDate(4, new java.sql.Date(insertDt.getTime()));
			ps.execute();
		}
		catch (SQLException se)
		{
			logger.error("Error creating the HealthCareSite participant",se);
			throw (new Exception(se.getLocalizedMessage()));

		}
		finally
		{
			//clean up
			ps = SQLHelper.closePreparedStatement(ps);
		}

	}

}
