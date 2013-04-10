/*
* Copyright ScenPro, Inc and SemanticBits, LLC
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/labviewer/LICENSE.txt for details.
*/
package gov.nih.nci.ctom.ctlab.handler;

import gov.nih.nci.ctom.ctlab.domain.HealthCareSite;
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
 * HealthCareSiteHandler class persists the HealthCasre Site data to CTODS
 * database
 * 
 * @author asharma
 */
public class HealthCareSiteHandler extends CTLabDAO implements HL7V3MessageHandlerInterface
{

	// Logging File
	private static Logger logger = Logger.getLogger("client");
	
	private static final String HEALTHCARE_SITE_INSERT = "INSERT INTO healthcare_site (id, " +
																				      "nci_institute_code, " +
																				      "name, " +
																				      "street_address, " +
																				      "city, " +
																				      "state_code, " +
																				      "postal_code, " +
																				      "country_code, " +
																				      "telecom_address, " +
																				      "source, " +
																				      "source_extract_date, " +
																				      "ctom_update_date, " +
																				      "description_text) " +
																			   "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";	

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nih.nci.ctom.ctlab.handler.HL7V3MessageHandler#persist(java.sql.Connection,
	 *      gov.nih.nci.ctom.ctlab.domain.Protocol)
	 */
	public void persist(Connection con, Protocol protocol) throws Exception
	{

		logger.debug("Saving the HealthCareSite");
		PreparedStatement ps = null;
		ResultSet rs = null;
		Long hsId = null;
		Long ssId = null;

		// retrieve HealthCareSite from Protocol
		HealthCareSite hcSite = protocol.getHealthCareSite();
		Long protId = protocol.getId();

		try
		{
			// check if there exists a healthcare site with the NCI Institute
			// code
			ps =
					con
							.prepareStatement("select id from healthcare_site where NCI_INSTITUTE_CODE = ?");
			ps.setString(1, hcSite.getNciInstituteCd());
			rs = ps.executeQuery();
			if (rs.next())
			{
				// use the existing Id
				hsId = rs.getLong(1);
			}
			else
			{

				// new HealthCare Site - Get Id from sequence
				hsId = getNextVal(con, "ORGANIZATION_SEQ");
				
				//clean up
				ps = SQLHelper.closePreparedStatement(ps);
				
				// insert into HealthCare_Site
				ps =
						con
								.prepareStatement(HEALTHCARE_SITE_INSERT);

				ps.setLong(1, hsId);
				ps.setString(2, hcSite.getNciInstituteCd() != null ? hcSite.getNciInstituteCd()
						: "");
				ps.setString(3, String.valueOf(hcSite.getName() != null ? hcSite.getName() : ""));
				ps.setString(4, hcSite.getStreetAddr());
				ps.setString(5, hcSite.getCity());
				ps.setString(6, hcSite.getStateCode());
				ps.setString(7, hcSite.getPostalCode());
				ps.setString(8, hcSite.getCountryCode());
				ps.setString(9, String.valueOf(hcSite.getTelecomAddr() != null ? hcSite.getTelecomAddr() : ""));
				ps.setString(10, String.valueOf(hcSite.getSource() != null ? hcSite.getSource() : ""));
				Date srcExtractDt =
					hcSite.getSrcExtractDt() != null ? hcSite.getSrcExtractDt() : new Date();
			    
					ps.setDate(11, new java.sql.Date(srcExtractDt.getTime()));
				
			    Date ctomUpdateDt =
			    	hcSite.getCtomUpdateDt() != null ? hcSite.getCtomUpdateDt() : new Date();
			    
					ps.setDate(12, new java.sql.Date(ctomUpdateDt.getTime()));
					
				ps.setString(13, hcSite.getDescpTxt());
								
				ps.execute();
			}
			// set the id
			hcSite.setId(hsId);

			// Check if the Study Site exits for that healthcaresite Id and
			// Protocol Id
			
			//clean up
			rs = SQLHelper.closeResultSet(rs);
			ps = SQLHelper.closePreparedStatement(ps);
			
			ps =
					con
							.prepareStatement("select id from study_site where HEALTHCARE_SITE_ID = ? and PROTOCOL_ID = ?");
			ps.setLong(1, hsId);
			ps.setLong(2, protId);
			rs = ps.executeQuery();
			if (rs.next())
			{
				// If exists - use that Study site Id
				ssId = rs.getLong(1);
			}
			else
			{
				// create a new Study Site id

				// get the next Id value
				ssId = getNextVal(con, "STUDY_SITE_SEQ");
				
				//clean up
				ps = SQLHelper.closePreparedStatement(ps);
				
				// insert into Study_Site
				ps =
						con
								.prepareStatement("insert into study_site (ID, HEALTHCARE_SITE_ID, PROTOCOL_ID)  values(?,?,?)");

				ps.setLong(1, ssId);
				ps.setLong(2, hsId);
				ps.setLong(3, protId);
				ps.execute();

			}

		}
		catch (SQLException se)
		{
			logger.error("Error saving the HealthCareSite",se);
			throw (new Exception(se.getLocalizedMessage()));

		}
		finally
		{
			//clean up
			rs = SQLHelper.closeResultSet(rs);
			ps = SQLHelper.closePreparedStatement(ps);

		}
		if (hcSite.getStudyParticipantAssignment() != null)
		{
			protocol.getHealthCareSite().getStudyParticipantAssignment().setStudySiteId(ssId);
			// saves the StudyParticipant Assignment
			HL7V3MessageHandlerFactory.getInstance().getHandler("STUDY_PARTICIPANT_ASSIGNMENT")
					.persist(con, protocol);
		}
	}
	
	
	/**
	 * Update.
	 * 
	 * @param con the con
	 * @param protocol the protocol
	 */
	public void update(Connection con, Protocol protocol)throws Exception
	{
		logger.debug("Updating the HealthCareSite");
		PreparedStatement ps = null;
		
		// retrieve HealthCareSite from Protocol
		HealthCareSite hcSite = protocol.getHealthCareSite();
		
		// update  HealthCare_Site
		ps =
				con
						.prepareStatement("Update HEALTHCARE_SITE set NAME = ?, STREET_ADDRESS = ? , CITY = ?, STATE_CODE = ?, " +
								"POSTAL_CODE = ?, COUNTRY_CODE = ? , TELECOM_ADDRESS = ?, SOURCE = ?, SOURCE_EXTRACT_DATE = ? , CTOM_UPDATE_DATE = ? where NCI_INSTITUTE_CODE=?");

		ps.setString(1, String.valueOf(hcSite.getName() != null ? hcSite.getName() : ""));
		ps.setString(2, String.valueOf(hcSite.getStreetAddr() != null ? hcSite.getStreetAddr() : ""));
		ps.setString(3, String.valueOf(hcSite.getCity() != null ? hcSite.getCity() : ""));
		ps.setString(4, String.valueOf(hcSite.getStateCode() != null ? hcSite.getStateCode() : ""));
		ps.setString(5, String.valueOf(hcSite.getPostalCode() != null ? hcSite.getPostalCode() : ""));
		ps.setString(6, String.valueOf(hcSite.getCountryCode() != null ? hcSite.getCountryCode() : ""));
		ps.setString(7, String.valueOf(hcSite.getTelecomAddr() != null ? hcSite.getTelecomAddr() : ""));
		ps.setString(8, String.valueOf(hcSite.getSource() != null ? hcSite.getSource() : ""));
		Date srcExtractDt =
			hcSite.getSrcExtractDt() != null ? hcSite.getSrcExtractDt() : new Date();
	    
			ps.setDate(9, new java.sql.Date(srcExtractDt.getTime()));
		
	    Date ctomUpdateDt =
	    	hcSite.getCtomUpdateDt() != null ? hcSite.getCtomUpdateDt() : new Date();
	    
			ps.setDate(10, new java.sql.Date(ctomUpdateDt.getTime()));
			ps.setString(11, hcSite.getNciInstituteCd());
						
		ps.execute();
		con.commit();
	}
		


	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nih.nci.ctom.ctlab.handler.HL7V3MessageHandler#persist(java.sql.Connection,
	 *      gov.nih.nci.ctom.ctlab.domain.Protocol)
	 */
	/*public void persist1(Connection con, Protocol protocol) throws Exception
	{

		logger.debug("Saving the HealthCareSite");
		PreparedStatement ps = null;
		ResultSet rs = null;
		Long hsId = null;
		Long ssId = null;

		// retrieve HealthCareSite from Protocol
		HealthCareSite hcSite = protocol.getHealthCareSite();
		Long protId = protocol.getId();

		try
		{
			// check if there exists a healthcare site with the NCI Institute
			// code
			ps =
					con
							.prepareStatement("select id from healthcare_site where NCI_INSTITUTE_CODE = ?");
			ps.setString(1, hcSite.getNciInstituteCd());
			rs = ps.executeQuery();
			if (rs.next())
			{
				// use the existing Id
				hsId = rs.getLong(1);
			}
			else
			{

				// new HealthCare Site - Get Id from sequence
				hsId = getNextVal(con, "ORGANIZATION_SEQ");
				
				//clean up
				ps = SQLHelper.closePreparedStatement(ps);
				
				// insert into HealthCare_Site - source - CTEP ID :source_extract_date - the date it was updated by invoking COPPA service
				ps =
						con
								.prepareStatement("insert into HEALTHCARE_SITE (ID, SOURCE, CTOM_INSERT_DATE)  values(?,?,?)");

				ps.setLong(1, hsId);
				ps.setString(2, hcSite.getSource()!= null ? hcSite.getSource(): "");
				ps.setString(3, hcSite.getNciInstituteCd() != null ? hcSite.getNciInstituteCd()
						: "");
				Date insertDt =
						hcSite.getCtomInsertDt() != null ? hcSite.getCtomInsertDt() : new Date();
				ps.setDate(3, new java.sql.Date(insertDt.getTime()));
				ps.execute();
			}
			// set the id
			hcSite.setId(hsId);

			// Check if the Study Site exits for that healthcaresite Id and
			// Protocol Id
			
			//clean up
			rs = SQLHelper.closeResultSet(rs);
			ps = SQLHelper.closePreparedStatement(ps);
			
			ps =
					con
							.prepareStatement("select id from study_site where HEALTHCARE_SITE_ID = ? and PROTOCOL_ID = ?");
			ps.setLong(1, hsId);
			ps.setLong(2, protId);
			rs = ps.executeQuery();
			if (rs.next())
			{
				// If exists - use that Study site Id
				ssId = rs.getLong(1);
			}
			else
			{
				// create a new Study Site id

				// get the next Id value
				ssId = getNextVal(con, "STUDY_SITE_SEQ");
				
				//clean up
				ps = SQLHelper.closePreparedStatement(ps);
				
				// insert into Study_Site
				ps =
						con
								.prepareStatement("insert into study_site (ID, HEALTHCARE_SITE_ID, PROTOCOL_ID)  values(?,?,?)");

				ps.setLong(1, ssId);
				ps.setLong(2, hsId);
				ps.setLong(3, protId);
				ps.execute();

			}

		}
		catch (SQLException se)
		{
			logger.error("Error saving the HealthCareSite",se);
			throw (new Exception(se.getLocalizedMessage()));

		}
		finally
		{
			//clean up
			rs = SQLHelper.closeResultSet(rs);
			ps = SQLHelper.closePreparedStatement(ps);

		}
		if (hcSite.getStudyParticipantAssignment() != null)
		{
			protocol.getHealthCareSite().getStudyParticipantAssignment().setStudySiteId(ssId);
			// saves the StudyParticipant Assignment
			HL7V3MessageHandlerFactory.getInstance().getHandler("STUDY_PARTICIPANT_ASSIGNMENT")
					.persist(con, protocol);
		}
	}*/
}
