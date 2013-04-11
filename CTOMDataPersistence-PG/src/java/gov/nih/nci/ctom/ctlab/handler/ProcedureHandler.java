/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */
package gov.nih.nci.ctom.ctlab.handler;

import gov.nih.nci.ctom.ctlab.domain.Procedure;
import gov.nih.nci.ctom.ctlab.domain.Protocol;
import gov.nih.nci.ctom.ctlab.persistence.CTLabDAO;
import gov.nih.nci.ctom.ctlab.persistence.SQLHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

/**
 * ProcedureHandler persists Procedure object to the CTODS database
 * 
 * @author asharma
 */
public class ProcedureHandler extends CTLabDAO implements HL7V3MessageHandlerInterface
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

		logger.debug("Saving the Procedure");
		PreparedStatement ps = null;
		// retrieve the Procedure from Protocol
		Procedure procedure =
				protocol.getHealthCareSite().getStudyParticipantAssignment().getActivity()
						.getProcedure();
		try
		{
			ps = con.prepareStatement("insert into PROCEDURE (ID, FASTING_STATUS)  values(?,?)");
			ps.setLong(1, procedure.getId());
			ps.setString(2, procedure.getFastingStatus());
			ps.execute();

		}
		catch (SQLException se)
		{
			logger.error("Error saving the Procedure",se);
			throw (new Exception(se.getLocalizedMessage()));

		}
		finally
		{
			//clean up
			ps = SQLHelper.closePreparedStatement(ps);
		}
		if (procedure.getSpecimenCollection() != null)
		{
			procedure.getSpecimenCollection().setProcedureActivityId(procedure.getId());
			// save SpecimenCollection
			HL7V3MessageHandlerFactory.getInstance().getHandler("SPECIMEN_COLLECTION").persist(con,
					protocol);
		}

	}

}
