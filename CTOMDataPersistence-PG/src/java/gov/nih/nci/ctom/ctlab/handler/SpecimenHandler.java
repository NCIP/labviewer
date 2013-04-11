/*
* Copyright ScenPro, Inc and SemanticBits, LLC
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/labviewer/LICENSE.txt for details.
*/
package gov.nih.nci.ctom.ctlab.handler;

import gov.nih.nci.ctom.ctlab.domain.Protocol;
import gov.nih.nci.ctom.ctlab.domain.Specimen;
import gov.nih.nci.ctom.ctlab.persistence.CTLabDAO;
import gov.nih.nci.ctom.ctlab.persistence.SQLHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

/**
 * SpecimenHandler persists the Specimen Data into CTODS database.
 * 
 * @author asharma
 */
public class SpecimenHandler extends CTLabDAO implements HL7V3MessageHandlerInterface
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

		logger.debug("Saving the Specimen");
		PreparedStatement ps = null;

		// retrieve the specimen data from Protocol
		Specimen specimen =
				protocol.getHealthCareSite().getStudyParticipantAssignment().getActivity()
						.getProcedure().getSpecimenCollection().getSpecimen();

		Long sampleTypeCDId =
				insertOrsaveConceptDescriptor(con, specimen.getVolumeUOMCd(), null, null);

		try
		{
			// Get Id from sequence
			Long id = getNextVal(con, "SPECIMEN_SEQ");
			specimen.setId(id);

			boolean sc = false;
			if (sampleTypeCDId != null)
			{
				sc = true;
			}
			if (sc)
			{
				ps =
						con
								.prepareStatement("insert into SPECIMEN(ID, ACCESSION_NUMBER, COMMENTS_FROM_LABORATORY, COMMENTS_FROM_INVESTIGATOR, CONDITION,SPECIMEN_COLLECTION_ID, SAMPLE_IDENTIFIER_ORIG,SMPLTYPE_CONCEPT_DESCRIPTOR_ID) values(?,?,?,?,?,?,?,?)");
				ps.setLong(8, sampleTypeCDId);
			}
			else
			{
				ps =
						con
								.prepareStatement("insert into SPECIMEN(ID, ACCESSION_NUMBER, COMMENTS_FROM_LABORATORY, COMMENTS_FROM_INVESTIGATOR, CONDITION,SPECIMEN_COLLECTION_ID,SAMPLE_IDENTIFIER_ORIG) values(?,?,?,?,?,?,?)");
			}
			ps.setLong(1, specimen.getId());
			ps.setString(2, specimen.getAccessionNumber());
			ps.setString(3, specimen.getCommentsFromLaboratory());
			ps.setString(4, specimen.getCommentsFromInvestigator());
			ps.setString(5, specimen.getCondition());
			ps.setLong(6, specimen.getProcedureActivityId());
			ps.setString(7, specimen.getSampleIdentifierOrig());
			ps.execute();
		}
		catch (SQLException se)
		{
			logger.error("Error saving the Specimen",se);
			throw (new Exception(se.getLocalizedMessage()));

		}
		finally
		{
			//clean up
			ps = SQLHelper.closePreparedStatement(ps);
		}

	}

}
