/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */
package gov.nih.nci.ctom.ctlab.handler;

import gov.nih.nci.ctom.ctlab.domain.Observation;
import gov.nih.nci.ctom.ctlab.domain.Protocol;
import gov.nih.nci.ctom.ctlab.persistence.CTLabDAO;
import gov.nih.nci.ctom.ctlab.persistence.SQLHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

/**
 * ObservationHandler persists Observation data to CTODS database
 * 
 * @author asharma
 */
public class ObservationHandler extends CTLabDAO implements HL7V3MessageHandlerInterface
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

		logger.debug("Saving Observation");
		PreparedStatement ps = null;
		Long id = null;
		// retrieve Observation data from Protocol
		Observation observation =
				protocol.getHealthCareSite().getStudyParticipantAssignment().getActivity()
						.getObservation();
		try
		{
			// Get Id from sequence
			id = getNextVal(con, "OBSERVATION_SEQ");
			observation.setId(id);

			// insert into Observation
			ps =
					con
							.prepareStatement("insert into OBSERVATION (ID, REPORTING_DATE, REPORTING_DATE_ORIG, CONFIDENTIALITY_CODE, STATUS_CODE, COMMENTS, ACTIVITY_ID)  values(?,?,?,?,?,?,?)");
			ps.setLong(1, observation.getId());
			if (observation.getReportingDate() != null)
			{
				ps.setDate(2, new java.sql.Date(observation.getReportingDate().getTime()));
			}
			else
			{
				ps.setDate(2, null);
			}
			ps.setString(3, observation.getReportingDateOrig());
			ps.setString(4, observation.getConfidentialityCode());
			ps.setString(5, observation.getStatusCode());
			ps.setString(6, observation.getComments());
			ps.setLong(7, observation.getActivityId());
			ps.execute();
		}
		catch (SQLException se)
		{
			logger.error("Error saving the observation",se);
			throw (new Exception(se.getLocalizedMessage()));

		}
		finally
		{
			//clean up
			ps = SQLHelper.closePreparedStatement(ps);
		}
		if (observation.getClinicalResult() != null)
		{
			observation.getClinicalResult().setObservationId(observation.getId());
			// saves Clinical Results
			HL7V3MessageHandlerFactory.getInstance().getHandler("CLINICAL_RESULT").persist(con,
					protocol);
		}

	}

}
