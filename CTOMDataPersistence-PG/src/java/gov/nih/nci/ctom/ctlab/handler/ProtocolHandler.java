/*
* Copyright ScenPro, Inc and SemanticBits, LLC
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/labviewer/LICENSE.txt for details.
*/
package gov.nih.nci.ctom.ctlab.handler;

import gov.nih.nci.ctom.ctlab.domain.Protocol;
import gov.nih.nci.ctom.ctlab.persistence.CTLabDAO;
import gov.nih.nci.ctom.ctlab.persistence.SQLHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import org.apache.log4j.Logger;

/**
 * ProtocolHandler persists Protocol object to the CTODS database
 *
 * @author asharma
 */
public class ProtocolHandler extends CTLabDAO implements HL7V3MessageHandlerInterface
{

	// Logging File
	private static Logger logger = Logger.getLogger("client");

	private static final String PROTOCOL_INSERT = "INSERT INTO protocol (id, " +
																	    "nci_identifier, " +
																	    "identifier_assigning_authority, " +
																	    "long_title_text, " +
																	    "short_title_text, " +
																	    "ctom_insert_date, " +
																	    "sponsor_code, " +
																	    "precis_text, " +
																	    "description_text, " +
																	    "phase_code, " +
																	    "blinded_indicator, " +
																	    "multi_institution_indicator, " +
																	    "randomized_indicator, " +
																	    "intent_code, " +
																	    "target_accrual_number) " +
																 "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,null)";

	/*
	 * (non-Javadoc)
	 *
	 * @see gov.nih.nci.ctom.ctlab.handler.HL7V3MessageHandler#persist(java.sql.Connection,
	 *      gov.nih.nci.ctom.ctlab.domain.Protocol)
	 */
	public void persist(Connection con, Protocol protocol) throws Exception
	{

		logger.debug("Saving Protocol");
		PreparedStatement ps = null;
		ResultSet rs = null;
		Long id = null;
		boolean identifierUpdInd = false;

		if (protocol.getNciIdentifier() == null)
		{
			throw new Exception("Invalid Data, Protocol Identifier Cannot Be Null");
		}
		try
		{
			// Check if the protocol already exists using identifier
			ps =
					con
							.prepareStatement("select p.ID from protocol p, identifier ii where p.ID = ii.PROTOCOL_ID and ii.extension=?");
			ps.setString(1, protocol.getIdentifier().getExtension());
			rs = ps.executeQuery();
			// If it exists get the ID
			if (rs.next())
			{
				id = rs.getLong(1);
				protocol.setId(id);
				ps = con.prepareStatement("select ID from IDENTIFIER where PROTOCOL_ID = ?");
				ps.setLong(1, id);
				rs = ps.executeQuery();

				if (rs.next())
				{
					protocol.getIdentifier().setId(rs.getLong(1));
				}
				logger.debug("Protocol existed");
			}
			else
			{
				//clean up
				 ps = SQLHelper.closePreparedStatement(ps);

				// Save/update Identifier
				HL7V3MessageHandlerFactory.getInstance().getHandler("PROTOCOL_IDENTIFIER").persist(
						con, protocol);
				if (protocol.getId() == null)
				{
					identifierUpdInd = true;
					// get ID from sequence
					id = getNextVal(con, "protocol_seq");
					ps = con.prepareStatement(PROTOCOL_INSERT);
					ps.setLong(1, id);
					ps.setString(2, String.valueOf(protocol.getNciIdentifier()));
					ps.setString(3, String.valueOf(protocol.getIdAssigningAuth()));
					ps.setString(4, String.valueOf(protocol.getLongTxtTitle()));
					ps.setString(5, String.valueOf(protocol.getShortTxtTitle()));
					if (protocol.getCtomInsertDt() == null)
					{
						ps.setTimestamp(6, new java.sql.Timestamp(new Date().getTime()));
					}
					else
					{
						ps.setTimestamp(6, new java.sql.Timestamp(protocol.getCtomInsertDt()
								.getTime()));
					}
					ps.setString(7, String.valueOf(protocol.getSponsorCode()));

					ps.setString(8, protocol.getPrecisTxt());
					ps.setString(9, protocol.getDescTxt());
					ps.setString(10, protocol.getPhaseCode());
					ps.setString(11, protocol.getBlindedId());
					ps.setString(12, protocol.getMultiInstId());
					ps.setString(13, protocol.getRandomId());
					ps.setString(14, protocol.getIntentCode());
					//ps.setLong(15, );
					ps.execute();
					if (identifierUpdInd && protocol.getIdentifier() != null)
					{
						protocol.setId(id);
						updateIdentifier(con, protocol);
					}
				} // update into participant if there was a participant
				// associated
				// with identifier.
				else
				{
					ps =
							con
									.prepareStatement("update PROTOCOL set NCI_IDENTIFIER = ?, IDENTIFIER_ASSIGNING_AUTHORITY = ?, LONG_TITLE_TEXT = ? where ID = ?");
					ps.setString(1, String.valueOf(protocol.getNciIdentifier()));
					ps.setString(2, String.valueOf(protocol.getIdAssigningAuth()));
					ps.setString(3, String.valueOf(protocol.getLongTxtTitle()));
					ps.setLong(4, protocol.getId());
					ps.executeUpdate();
					con.commit();
				}
			}
		}
		catch (SQLException se)
		{
			logger.error("Error saving the Protocol",se);
			throw (new Exception(se.getLocalizedMessage()));

		}
		finally
		{
			//clean up
			rs = SQLHelper.closeResultSet(rs);
			ps = SQLHelper.closePreparedStatement(ps);
		}
		// save protocol status
		if (protocol.getStatus() != null)
		{
			HL7V3MessageHandlerFactory.getInstance().getHandler("PROTOCOL_STATUS").persist(con,
					protocol);
		}
		// save investigator
		if (protocol.getInvestigator() != null)
		{
			HL7V3MessageHandlerFactory.getInstance().getHandler("INVESTIGATOR").persist(con,
					protocol);
		}
		// save healthcaresite
		if (protocol.getHealthCareSite() != null)
		{
			HL7V3MessageHandlerFactory.getInstance().getHandler("HEALTH_CARE_SITE").persist(con,
					protocol);
		}
	}

	/**
	 * Update.
	 *
	 * @param conn the connection
	 * @param protocol the protocol
	 */
	public void update(Connection conn, Protocol protocol) throws Exception
	{
		logger.debug("Updating the Protocol");
		String sql = "update PROTOCOL set LONG_TITLE_TEXT = ?, " +
				                         "SHORT_TITLE_TEXT = ?, " +
				                         "PHASE_CODE = ?, " +
				                         "SPONSOR_CODE = ? " +
				                         //"IDENTIFIER_ASSIGNING_AUTHORITY = ? " + // this is not available in PA StudyProtocol object
				                   "WHERE ID = ?";
		try
		{
		    PreparedStatement pstmt = conn.prepareStatement(sql);
		    pstmt.setString(1, protocol.getLongTxtTitle());
		    pstmt.setString(2, protocol.getShortTxtTitle());
		    pstmt.setString(3, protocol.getPhaseCode());
		    pstmt.setString(4, protocol.getSponsorCode());
		    //pstmt.setString(5, protocol.getIdAssigningAuth()); // this is not available in PA StudyProtocol object
		    pstmt.setLong(5, protocol.getId());
		    pstmt.execute();
		    conn.commit();
		}
		catch (SQLException se)
		{
			logger.error("Error updating the Protocol",se);
			throw (new Exception(se.getLocalizedMessage()));

		}

		// save protocol status
		if (protocol.getStatus() != null)
		{
			HL7V3MessageHandlerFactory.getInstance().getHandler("PROTOCOL_STATUS").persist(conn, protocol);
		}
	}

}
