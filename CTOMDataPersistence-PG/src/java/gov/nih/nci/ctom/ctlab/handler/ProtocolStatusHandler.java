/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */
package gov.nih.nci.ctom.ctlab.handler;

import gov.nih.nci.ctom.ctlab.domain.Protocol;
import gov.nih.nci.ctom.ctlab.domain.ProtocolStatus;
import gov.nih.nci.ctom.ctlab.persistence.CTLabDAO;
import gov.nih.nci.ctom.ctlab.persistence.SQLHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

/**
 * ProtocolStatusHandler persists the Protocol Status to the CTODS database
 * 
 * @author asharma
 */
public class ProtocolStatusHandler extends CTLabDAO implements HL7V3MessageHandlerInterface
{

	private static Logger logger = Logger.getLogger("client");

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nih.nci.ctom.ctlab.handler.HL7V3MessageHandler#persist(java.sql.Connection,
	 *      gov.nih.nci.ctom.ctlab.domain.Protocol)
	 */
	public void persist(Connection con, Protocol protocol) throws Exception
	{

		logger.debug("Saving the Protocol Status");
		PreparedStatement ps = null;

		// retrieve the ProtocolStatus from Protocol.
		ProtocolStatus protoStatus = protocol.getStatus();
		Long id = protocol.getId();

		try
		{
			// Get Id from sequence
			Long nextId = getNextVal(con, "PROTOCOL_STATUS_SEQ");
			// set the id
			protoStatus.setId(nextId);

			// insert into PROTOCOL STATUS
			ps =
					con
							.prepareStatement("insert into PROTOCOL_STATUS (ID, PROTOCOL_ID, STATUS_CODE,CTOM_INSERT_DATE)  values(?,?,?,?)");
			ps.setLong(1, protoStatus.getId());
			ps.setLong(2, id);
			ps.setString(3, protoStatus.getStatus_code());
			ps.setDate(4, new java.sql.Date(protoStatus.getCtom_insert_date().getTime()));
			ps.execute();
		}
		catch (SQLException se)
		{
			logger.error("Error saving the Protocol Status",se);
			throw (new Exception(se.getLocalizedMessage()));

		}
		finally
		{
			//clean up
			ps = SQLHelper.closePreparedStatement(ps);
		}

	}

}
