/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */
package gov.nih.nci.ctom.ctlab.handler;

import gov.nih.nci.ctom.ctlab.domain.PerformingLaboratory;
import gov.nih.nci.ctom.ctlab.domain.Protocol;
import gov.nih.nci.ctom.ctlab.persistence.CTLabDAO;
import gov.nih.nci.ctom.ctlab.persistence.SQLHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

/**
 * PerformingLaboratoryHandler persists Performing Laboratory object to the
 * CTODS database
 * 
 * @author asharma
 */
public class PerformingLaboratoryHandler extends CTLabDAO implements HL7V3MessageHandlerInterface
{

	// Logging File
	private static Logger logger = Logger.getLogger("client");

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nih.nci.ctom.ctlab.handler.HL7V3MessageHandler#persist()
	 */
	public void persist(Connection con, Protocol protocol) throws Exception
	{

		logger.debug("Saving Performing Laboratory");
		PreparedStatement ps = null;
		ResultSet rs = null;

		// retrieve Performing Lab data from Protocol
		PerformingLaboratory performingLaboratory =
				protocol.getHealthCareSite().getStudyParticipantAssignment().getActivity()
						.getObservation().getClinicalResult().getPerformingLaboratory();

		try
		{
			ps = con.prepareStatement("select ID from PERFORMING_LABORATORY where IDENTIFIER = ?");
			ps.setString(1, performingLaboratory.getPlIdentifier());
			rs = ps.executeQuery();
			if (rs.next())
			{
				performingLaboratory.setOrganizationId(rs.getLong(1));
			}
			else
			{
				//clean up
				ps = SQLHelper.closePreparedStatement(ps);
				
				// get the id from sequence
				Long id = getNextVal(con, "ORGANIZATION_SEQ");
				performingLaboratory.setOrganizationId(id);
				ps =
						con
								.prepareStatement("insert into PERFORMING_LABORATORY (ID, IDENTIFIER, NAME)  values(?,?,?)");
				ps.setLong(1, performingLaboratory.getOrganizationId());
				ps.setString(2, performingLaboratory.getPlIdentifier());
				ps.setString(3, performingLaboratory.getPlName());
				ps.execute();
			}
		}
		catch (SQLException se)
		{
			logger.error("Error saving the Performing Laboratory",se);
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
