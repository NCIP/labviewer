/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */
package gov.nih.nci.ctom.ctlab.handler;

import gov.nih.nci.ctom.ctlab.domain.Protocol;
import gov.nih.nci.ctom.ctlab.domain.SpecimenCollection;
import gov.nih.nci.ctom.ctlab.persistence.CTLabDAO;
import gov.nih.nci.ctom.ctlab.persistence.SQLHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

/**
 * SpecimenCollectionHandler persists the Specimen Collection Data into CTODS
 * database.
 * 
 * @author asharma
 */
public class SpecimenCollectionHandler extends CTLabDAO implements HL7V3MessageHandlerInterface
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

		logger.debug("Saving the Specimen Collection");
		PreparedStatement ps = null;

		// retrieve the specimenCollection data from Protocol
		SpecimenCollection specimenCollection =
				protocol.getHealthCareSite().getStudyParticipantAssignment().getActivity()
						.getProcedure().getSpecimenCollection();
		try
		{
			if (specimenCollection.getCentralLaboratory() != null)
			{
				// save Central Laboratory data
				HL7V3MessageHandlerFactory.getInstance().getHandler("CENTRAL_LABORATORY").persist(
						con, protocol);
				ps =
						con
								.prepareStatement("insert into SPECIMEN_COLLECTION (ID,CENTRAL_LABORATORY_ID)  values(?,?)");
				ps.setLong(1, specimenCollection.getProcedureActivityId());
				ps.setLong(2, specimenCollection.getCentralLaboratory().getId());
				ps.execute();

			}
		}
		catch (SQLException se)
		{
			logger.error("Error saving the Specimen Collection",se);
			throw (new Exception(se.getLocalizedMessage()));

		}
		finally
		{
			//clean up
			ps = SQLHelper.closePreparedStatement(ps);
		}
		if (specimenCollection.getSpecimen() != null)
		{
			specimenCollection.getSpecimen().setProcedureActivityId(
					specimenCollection.getProcedureActivityId());
			// save Specimen
			HL7V3MessageHandlerFactory.getInstance().getHandler("SPECIMEN").persist(con, protocol);

		}
	}

}
