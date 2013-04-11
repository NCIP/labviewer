/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */
package gov.nih.nci.ctom.ctlab.handler;

import gov.nih.nci.ctom.ctlab.domain.Investigator;
import gov.nih.nci.ctom.ctlab.domain.Protocol;
import gov.nih.nci.ctom.ctlab.persistence.CTLabDAO;
import gov.nih.nci.ctom.ctlab.persistence.SQLHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.apache.log4j.Logger;

// TODO: Auto-generated Javadoc
/**
 * InvestigatorHandler persists Investigator data into CTODS database.
 * 
 * @author asharma
 */
public class InvestigatorHandler extends CTLabDAO implements HL7V3MessageHandlerInterface
{

	// Logging File
	/** The logger. */
	private static Logger logger = Logger.getLogger("client");

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nih.nci.ctom.ctlab.handler.HL7V3MessageHandler#persist(java.sql.Connection,
	 *      gov.nih.nci.ctom.ctlab.domain.Protocol)
	 */
	public void persist(Connection con, Protocol protocol) throws Exception
	{

		logger.debug("Saving the Investigator");
		PreparedStatement ps = null;
		ResultSet rs = null;
		Long investigatorId = null;

		// retrieve the investigator from Protocol
		Investigator inv = protocol.getInvestigator();
		Long protId = protocol.getId();

		try
		{
			// Check if the investigator exists in the db -use the Id.
			ps = con.prepareStatement("select ID from INVESTIGATOR  where NCI_IDENTIFIER = ?");
			ps.setString(1, inv.getNciId());
			rs = ps.executeQuery();
			if (rs.next())
			{
				investigatorId = rs.getLong(1);
			}
			else
			{
				// clean up
				ps = SQLHelper.closePreparedStatement(ps);
	
				// Get Id from sequence
				investigatorId = getNextVal(con, "PERSON_SEQ");

				// insert into Investigator
				ps =
						con
								.prepareStatement("insert into investigator (ID, NCI_IDENTIFIER, LAST_NAME, FIRST_NAME, MIDDLE_NAME, TELECOM_ADDRESS, STREET_ADDRESS, CITY, STATE, ZIP_CODE, COUNTRY_CODE, PHONE, SOURCE, SOURCE_EXTRACT_DATE, CTOM_UPDATE_DATE)  values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

				ps.setLong(1, investigatorId);
				ps.setString(2, String.valueOf(inv.getNciId() != null ? inv.getNciId() : ""));
				ps.setString(3, String.valueOf(inv.getLastName() != null ? inv.getLastName() : ""));
				ps.setString(4, String.valueOf(inv.getFirstName() != null ? inv.getFirstName() : ""));
				ps.setString(5, String.valueOf(inv.getMiddleNAle() != null ? inv.getMiddleNAle() : ""));
				ps.setString(6, String.valueOf(inv.getTelecomAddr() != null ? inv.getTelecomAddr() : ""));
				ps.setString(7, String.valueOf(inv.getStreetAddr() != null ? inv.getStreetAddr() : ""));
				ps.setString(8, String.valueOf(inv.getCity() != null ? inv.getCity() : ""));
				ps.setString(9, String.valueOf(inv.getState() != null ? inv.getState() : ""));
				ps.setString(10, String.valueOf(inv.getZipCode() != null ? inv.getZipCode() : ""));
				ps.setString(11, String.valueOf(inv.getCountryCode() != null ? inv.getCountryCode() : ""));
				ps.setString(12, String.valueOf(inv.getPhone() != null ? inv.getPhone() : ""));
				ps.setString(13, String.valueOf(inv.getSource() != null ? inv.getSource() : ""));
				Date srcExtractDt =
					inv.getSrcExtractDt() != null ? inv.getSrcExtractDt() : new Date();
			    
					ps.setDate(14, new java.sql.Date(srcExtractDt.getTime()));
				
			    Date ctomUpdateDt =
					inv.getCtomUpdateDt() != null ? inv.getCtomUpdateDt() : new Date();
			    
					ps.setDate(15, new java.sql.Date(ctomUpdateDt.getTime()));
				
				ps.execute();

			}
			
			//clean up
			rs = SQLHelper.closeResultSet(rs);
			ps = SQLHelper.closePreparedStatement(ps);
			
			// insert into Study_Investigator
			ps =
					con
							.prepareStatement("select id from study_investigator where PROTOCOL_ID = ? AND INVESTIGATOR_ID = ?");
			ps.setLong(1, protId);
			ps.setLong(2, investigatorId);
			rs = ps.executeQuery();
			if (rs.next())
			{
				return;
			}
			else
			{
				//clean up
				 ps = SQLHelper.closePreparedStatement(ps);
				
				Long siId = getNextVal(con, "STUDY_INVESTIGATOR_SEQ");
				logger.debug("The study_Invest id is " + siId);
				ps =
						con
								.prepareStatement("insert into study_investigator (ID, PROTOCOL_ID, INVESTIGATOR_ID)  values(?,?,?)");

				ps.setLong(1, siId);
				ps.setLong(2, protId);
				ps.setLong(3, investigatorId);
				ps.execute();
			}
		}
		catch (SQLException se)
		{
			logger.error("Error saving the investigator",se);
			throw (new Exception(se.getLocalizedMessage()));

		}
		finally
		{
			//clean up
			rs = SQLHelper.closeResultSet(rs);
			ps = SQLHelper.closePreparedStatement(ps);

		}
	}
	
	/**
	 * Update.
	 * 
	 * @param con the con
	 * @param protocol the protocol
	 * 
	 * @throws Exception the exception
	 */
	public void update(Connection con, Protocol protocol) throws Exception
	{

		logger.debug("Updating the Investigator");
		PreparedStatement ps = null;
		

		// retrieve the investigator from Protocol
		Investigator inv = protocol.getInvestigator();
		
		try
		{
			// insert into Investigator
				ps =
				con
				 .prepareStatement("update investigator set LAST_NAME=?, FIRST_NAME=?, MIDDLE_NAME=?, TELECOM_ADDRESS=?, STREET_ADDRESS=?," +
				 		"CITY=?, STATE=?, ZIP_CODE=?, COUNTRY_CODE=?, PHONE=?, SOURCE=?, SOURCE_EXTRACT_DATE=?, CTOM_UPDATE_DATE=? where NCI_IDENTIFIER=?");
				
				ps.setString(1, String.valueOf(inv.getLastName() != null ? inv.getLastName() : ""));
				ps.setString(2, String.valueOf(inv.getFirstName() != null ? inv.getFirstName() : ""));
				ps.setString(3, String.valueOf(inv.getMiddleNAle() != null ? inv.getMiddleNAle() : ""));
				ps.setString(4, String.valueOf(inv.getTelecomAddr() != null ? inv.getTelecomAddr() : ""));
				ps.setString(5, String.valueOf(inv.getStreetAddr() != null ? inv.getStreetAddr() : ""));
				ps.setString(6, String.valueOf(inv.getCity() != null ? inv.getCity() : ""));
				ps.setString(7, String.valueOf(inv.getState() != null ? inv.getState() : ""));
				ps.setString(8, String.valueOf(inv.getZipCode() != null ? inv.getZipCode() : ""));
				ps.setString(9, String.valueOf(inv.getCountryCode() != null ? inv.getCountryCode() : ""));
				ps.setString(10, String.valueOf(inv.getPhone() != null ? inv.getPhone() : ""));
				ps.setString(11, String.valueOf(inv.getSource() != null ? inv.getSource() : ""));
				Date srcExtractDt =
					inv.getSrcExtractDt() != null ? inv.getSrcExtractDt() : new Date();
			    
					ps.setDate(12, new java.sql.Date(srcExtractDt.getTime()));
				
			    Date ctomUpdateDt =
					inv.getCtomUpdateDt() != null ? inv.getCtomUpdateDt() : new Date();
			    
					ps.setDate(13, new java.sql.Date(ctomUpdateDt.getTime()));
					ps.setString(14,inv.getNciId());
				ps.execute();
				con.commit();

			}
		catch (SQLException se)
		{
			logger.error("Error updating the investigator",se);
			throw (new Exception(se.getLocalizedMessage()));

		}
		finally
		{
			//clean up
			ps = SQLHelper.closePreparedStatement(ps);

		}
		}		
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nih.nci.ctom.ctlab.handler.HL7V3MessageHandler#persist(java.sql.Connection,
	 *      gov.nih.nci.ctom.ctlab.domain.Protocol)
	 */
	/*public void persist1(Connection con, Protocol protocol) throws Exception
	{

		logger.debug("Saving the Investigator");
		PreparedStatement ps = null;
		ResultSet rs = null;
		Long investigatorId = null;

		// retrieve the investigator from Protocol
		Investigator inv = protocol.getInvestigator();
		Long protId = protocol.getId();

		try
		{
			// Check if the investigator exists in the db -use the Id.
			ps = con.prepareStatement("select ID from INVESTIGATOR  where NCI_IDENTIFIER = ?");
			ps.setString(1, inv.getNciId());
			rs = ps.executeQuery();
			if (rs.next())
			{
				investigatorId = rs.getLong(1);
			}
			else
			{
				// clean up
				ps = SQLHelper.closePreparedStatement(ps);
	
				// Get Id from sequence
				investigatorId = getNextVal(con, "PERSON_SEQ");

				// insert into Investigator -source - CTEP ID :source_extract_date - the date it was updated by invoking COPPA service
				ps =
						con
								.prepareStatement("insert into investigator (ID, SOURCE, CTOM_INSERT_DATE)  values(?,?,?)");

				ps.setLong(1, investigatorId);
				ps.setString(2, inv.getSource() != null ? inv.getSource() : "");
				Date insertDt =
					inv.getCtomInsertDt() != null ? inv.getCtomInsertDt() : new Date();
			    ps.setDate(3, new java.sql.Date(insertDt.getTime()));
				ps.execute();

			}
			
			//clean up
			rs = SQLHelper.closeResultSet(rs);
			ps = SQLHelper.closePreparedStatement(ps);
			
			// insert into Study_Investigator
			ps =
					con
							.prepareStatement("select id from study_investigator where PROTOCOL_ID = ? AND INVESTIGATOR_ID = ?");
			ps.setLong(1, protId);
			ps.setLong(2, investigatorId);
			rs = ps.executeQuery();
			if (rs.next())
			{
				return;
			}
			else
			{
				//clean up
				 ps = SQLHelper.closePreparedStatement(ps);
				
				Long siId = getNextVal(con, "STUDY_INVESTIGATOR_SEQ");
				logger.debug("The study_Invest id is " + siId);
				ps =
						con
								.prepareStatement("insert into study_investigator (ID, PROTOCOL_ID, INVESTIGATOR_ID)  values(?,?,?)");

				ps.setLong(1, siId);
				ps.setLong(2, protId);
				ps.setLong(3, investigatorId);
				ps.execute();
			}
		}
		catch (SQLException se)
		{
			logger.error("Error saving the investigator",se);
			throw (new Exception(se.getLocalizedMessage()));

		}
		finally
		{
			//clean up
			rs = SQLHelper.closeResultSet(rs);
			ps = SQLHelper.closePreparedStatement(ps);

		}
	}*/

}
