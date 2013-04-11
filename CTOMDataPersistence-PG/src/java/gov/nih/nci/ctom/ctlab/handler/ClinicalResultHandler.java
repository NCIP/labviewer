/*
* Copyright ScenPro, Inc and SemanticBits, LLC
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/labviewer/LICENSE.txt for details.
*/
package gov.nih.nci.ctom.ctlab.handler;

import gov.nih.nci.ctom.ctlab.domain.ClinicalResult;
import gov.nih.nci.ctom.ctlab.domain.Protocol;
import gov.nih.nci.ctom.ctlab.persistence.CTLabDAO;
import gov.nih.nci.ctom.ctlab.persistence.SQLHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

/**
 * ClinicalResultHandler persists the Clinical Result data to CTODS database.
 * 
 * @author asharma
 */
public class ClinicalResultHandler extends CTLabDAO implements HL7V3MessageHandlerInterface
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

		Long valUOMCdId = null;
		Long labTestCdId = null;
		logger.debug("Saving the clinical Result");

		// retrieve the clinical result data from Protocol
		ClinicalResult clinicalResult =
				protocol.getHealthCareSite().getStudyParticipantAssignment().getActivity()
						.getObservation().getClinicalResult();

		if (clinicalResult.getPerformingLaboratory() != null)
		{
			// Save Performing Laboratory
			HL7V3MessageHandlerFactory.getInstance().getHandler("PERFORMING_LABORATORY").persist(
					con, protocol);

			// update/insert ConceptDescriptor with UOM
			valUOMCdId =
					insertOrsaveConceptDescriptor(con, clinicalResult.getValueUnitOfMeasureCd(),
							clinicalResult.getValueUnitOfMeasureCdSystem(), clinicalResult
									.getValueUnitOfMeasureCdSystemName(), null, null);

			// update/insert ConceptDescriptor with Clinical result codes
			labTestCdId =
					insertOrsaveConceptDescriptor(con, clinicalResult.getCrCode(), clinicalResult
							.getCrCodeSystem(), clinicalResult.getCrCodeSystemName(),
							clinicalResult.getCrCodeSystemVersion(), clinicalResult
									.getCrCodeDisplayText());

		}

		if (labTestCdId == null) // This is a required field in the database
		{
			return;
		}
		PreparedStatement ps = null;

		try
		{
			boolean val = false;
			boolean lt = false;
			if (valUOMCdId != null)
			{
				val = true;
			}
			if (labTestCdId != null)
			{
				lt = true;
			}
			if (lt && val)
			{
				ps =
						con
								.prepareStatement("insert into CLINICAL_RESULT (ID, LAB_TECHNIQUE_CODE, PANEL_NAME, PERFORMING_LABORATORY_ID, REFERENCE_FLAG, VALUE, REFERENCE_RANGE_LOW, REFERENCE_RANGE_HIGH, VALUEUOM_CONCEPT_DESCRIPTOR_ID, LAB_TEST_CONCEPT_DESCRIPTOR_ID)  values(?,?,?,?,?,?,?,?,?,?)");
				ps.setLong(9, valUOMCdId);
				ps.setLong(10, labTestCdId);
			}
			else if (!lt && !val)
			{
				ps =
						con
								.prepareStatement("insert into CLINICAL_RESULT (ID, LAB_TECHNIQUE_CODE, PANEL_NAME, PERFORMING_LABORATORY_ID, REFERENCE_FLAG, VALUE, REFERENCE_RANGE_LOW, REFERENCE_RANGE_HIGH)  values(?,?,?,?,?,?,?,?)");
			}
			else if (!lt)
			{
				ps =
						con
								.prepareStatement("insert into CLINICAL_RESULT (ID, LAB_TECHNIQUE_CODE, PANEL_NAME, PERFORMING_LABORATORY_ID, REFERENCE_FLAG, VALUE, REFERENCE_RANGE_LOW, REFERENCE_RANGE_HIGH, VALUEUOM_CONCEPT_DESCRIPTOR_ID)  values(?,?,?,?,?,?,?,?,?)");
				ps.setLong(9, valUOMCdId);
			}
			else if (!val)
			{
				ps =
						con
								.prepareStatement("insert into CLINICAL_RESULT (ID, LAB_TECHNIQUE_CODE, PANEL_NAME, PERFORMING_LABORATORY_ID, REFERENCE_FLAG, VALUE, REFERENCE_RANGE_LOW, REFERENCE_RANGE_HIGH, LAB_TEST_CONCEPT_DESCRIPTOR_ID)  values(?,?,?,?,?,?,?,?,?)");
				ps.setLong(9, labTestCdId);
			}
			ps.setLong(1, clinicalResult.getObservationId());
			ps.setString(2, clinicalResult.getLabTechCd());
			ps.setString(3, clinicalResult.getPanelName());
			ps.setLong(4, clinicalResult.getPerformingLaboratory().getOrganizationId());
			ps.setString(5, clinicalResult.getRefFlag());
			ps.setString(6, clinicalResult.getValue());
			ps.setDouble(7, clinicalResult.getRefRangeLow());
			ps.setDouble(8, clinicalResult.getRefRangeHigh());
			ps.execute();
		}
		catch (SQLException se)
		{
			logger.error("Error saving the clinical Result",se);
			throw (new Exception(se.getLocalizedMessage()));

		}
		finally
		{
			ps = SQLHelper.closePreparedStatement(ps);
		}

	}

}
