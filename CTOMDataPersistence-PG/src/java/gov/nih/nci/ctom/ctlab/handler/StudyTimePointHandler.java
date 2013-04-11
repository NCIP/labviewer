/*
* Copyright ScenPro, Inc and SemanticBits, LLC
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/labviewer/LICENSE.txt for details.
*/
package gov.nih.nci.ctom.ctlab.handler;

import gov.nih.nci.ctom.ctlab.domain.Protocol;
import gov.nih.nci.ctom.ctlab.domain.StudyTimePoint;
import gov.nih.nci.ctom.ctlab.persistence.CTLabDAO;
import gov.nih.nci.ctom.ctlab.persistence.SQLHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

/**
 * StudyTimePointHandler persists Study Time Point into CTODS database
 * 
 * @author asharma
 */
public class StudyTimePointHandler extends CTLabDAO implements HL7V3MessageHandlerInterface
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

		logger.debug("Saving the Study Time Point");
		PreparedStatement ps = null;

		// retrieve Study time Point from protocol
		StudyTimePoint studyTimePoint =
				protocol.getHealthCareSite().getStudyParticipantAssignment().getActivity()
						.getStudyTimePoint();
		try
		{
			// Get Id from sequence
			Long id = getNextVal(con, "STUDY_TIME_POINT_SEQ");

			// set the id
			studyTimePoint.setId(id);

			// insert into STUDY_TIME_POINT
			ps =
					con
							.prepareStatement("insert into STUDY_TIME_POINT (ID, ACTIVITY_ID, VISIT_NAME)  values(?,?,?)");
			ps.setLong(1, studyTimePoint.getId());
			ps.setLong(2, studyTimePoint.getActivityId());
			ps.setString(3, studyTimePoint.getVisitName());
			ps.execute();
		}
		catch (SQLException se)
		{
			logger.error("Error saving the Study Time Point",se);
			throw (new Exception(se.getLocalizedMessage()));

		}
		finally
		{
			//clean up
			ps = SQLHelper.closePreparedStatement(ps);
		}

	}

}
