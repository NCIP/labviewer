/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */
package gov.nih.nci.ctom.ctlab.handler;

import gov.nih.nci.ctom.ctlab.domain.Activity;
import gov.nih.nci.ctom.ctlab.domain.Protocol;
import gov.nih.nci.ctom.ctlab.persistence.CTLabDAO;
import gov.nih.nci.ctom.ctlab.persistence.SQLHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;

/**
 * ActivityHandler class persists the Activity data into CTODS Database
 * 
 * @author asharma
 */
public class ActivityHandler extends CTLabDAO implements
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
		PreparedStatement ps = null;
		ResultSet rs = null;
		Long actId = null;
		// retrieve Activity data from Protocol
		Activity act =
				protocol.getHealthCareSite().getStudyParticipantAssignment()
						.getActivity();
		Long spaId =
				protocol.getHealthCareSite().getStudyParticipantAssignment().getId();
		try
		{
			logger.debug("Saving Activity");
			// Get Id from sequence
			actId = getNextVal(con, "ACTIVITY_SEQ");
			act.setId(actId);
			// insert into Activity
			ps =
					con
							.prepareStatement("insert into ACTIVITY (ID, STUDY_PARTICIPANT_ASSIGNMNT_ID, START_DATE_ORIG, STOP_DATE_ORIG,START_DATE,STOP_DATE)  values(?,?,?,?,?,?)");
			ps.setLong(1, actId);
			ps.setLong(2, spaId);
			ps.setString(3, String.valueOf(act.getStartDateOrig()));
			ps.setString(4, String.valueOf(act.getStartDateOrig()));
			try
			{
				java.util.Date startTmp =
						new SimpleDateFormat("yyyyMMddHHmmss").parse(act
								.getStartDateOrig());
				java.util.Date stopTmp =
						new SimpleDateFormat("yyyyMMddHHmmss").parse(act
								.getStartDateOrig());
				ps.setTimestamp(5, new java.sql.Timestamp(startTmp.getTime()));
				ps.setTimestamp(6, new java.sql.Timestamp(stopTmp.getTime()));
				ps.execute();
			}
			catch (ParseException e)
			{
				logger.error("error creating activity", e);
			}
			if (act.getObservation() != null)
			{
				act.getObservation().setActivityId(act.getId());
				// Saves Observation
				HL7V3MessageHandlerFactory.getInstance().getHandler("OBSERVATION")
						.persist(con, protocol);

			}
			if (act.getProcedure() != null)
			{
				act.getProcedure().setId(act.getId());
				// Saves Procedure
				HL7V3MessageHandlerFactory.getInstance().getHandler("PROCEDURE")
						.persist(con, protocol);
			}
			if (act.getStudyTimePoint() != null)
			{
				act.getStudyTimePoint().setActivityId(act.getId());
				// Saves Study time Point
				HL7V3MessageHandlerFactory.getInstance().getHandler(
						"STUDY_TIME_POINT").persist(con, protocol);
			}
		}
		catch (SQLException se)
		{
			logger.error("Error creating activity",se);
			throw new Exception(se.getMessage());
		}
		finally
		{
			rs = SQLHelper.closeResultSet(rs);
			ps = SQLHelper.closePreparedStatement(ps);
		}

	}

}
