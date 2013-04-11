/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */
package gov.nih.nci.ctom.ctlab.handler;

import gov.nih.nci.ctom.ctlab.domain.CentralLaboratory;
import gov.nih.nci.ctom.ctlab.domain.Protocol;
import gov.nih.nci.ctom.ctlab.persistence.CTLabDAO;
import gov.nih.nci.ctom.ctlab.persistence.SQLHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

/**
 * CentralLaboratoryHandler class persists Central Laboratory data to CTODS
 * database.
 * 
 * @author asharma
 */
public class CentralLaboratoryHandler extends CTLabDAO implements HL7V3MessageHandlerInterface
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

		logger.debug("Saving the Central Laboratory");
		PreparedStatement ps = null;
		ResultSet rs = null;
		// retrieve the central lab data from Protocol
		CentralLaboratory centralLaboratory =
				protocol.getHealthCareSite().getStudyParticipantAssignment().getActivity()
						.getProcedure().getSpecimenCollection().getCentralLaboratory();
		try
		{
			ps = con.prepareStatement("select ID from CENTRAL_LABORATORY where IDENTIFIER = ?");
			ps.setString(1, centralLaboratory.getIdentifier());
			rs = ps.executeQuery();
			if (rs.next())
			{
				centralLaboratory.setId(rs.getLong(1));
			}
			else
			{
				//clean up
				rs = SQLHelper.closeResultSet(rs);
				ps = SQLHelper.closePreparedStatement(ps);
				
				// Get Id from sequence
				Long id = getNextVal(con, "ORGANIZATION_SEQ");
				centralLaboratory.setId(id);
				
				ps =
						con
								.prepareStatement("insert into CENTRAL_LABORATORY(ID, IDENTIFIER, NAME)  values(?,?,?)");
				ps.setLong(1, centralLaboratory.getId());
				ps.setString(2, centralLaboratory.getIdentifier());
				ps.setString(3, centralLaboratory.getName());
				ps.execute();
			}
		}
		catch (SQLException se)
		{
			logger.error("Error saving the Central Laboratory",se);
			throw (new Exception(se.getLocalizedMessage()));

		}
		finally
		{
			rs = SQLHelper.closeResultSet(rs);
			ps = SQLHelper.closePreparedStatement(ps);

		}

	}

}
