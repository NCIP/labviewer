/**
 * Copyright Notice. Copyright 2008 ScenPro, Inc ("caBIG(TM)
 * Participant").caXchange was created with NCI funding and is part of the
 * caBIG(TM) initiative. The software subject to this notice and license includes
 * both human readable source code form and machine readable, binary, object
 * code form (the "caBIG(TM) Software"). This caBIG(TM) Software License (the
 * "License") is between caBIG(TM) Participant and You. "You (or "Your") shall mean
 * a person or an entity, and all other entities that control, are controlled
 * by, or are under common control with the entity. "Control" for purposes of
 * this definition means (i) the direct or indirect power to cause the direction
 * or management of such entity, whether by contract or otherwise, or (ii)
 * ownership of fifty percent (50%) or more of the outstanding shares, or (iii)
 * beneficial ownership of such entity. License. Provided that You agree to the
 * conditions described below, caBIG(TM) Participant grants You a non-exclusive,
 * worldwide, perpetual, fully-paid-up, no-charge, irrevocable, transferable and
 * royalty-free right and license in its rights in the caBIG(TM) Software,
 * including any copyright or patent rights therein, to (i) use, install,
 * disclose, access, operate, execute, reproduce, copy, modify, translate,
 * market, publicly display, publicly perform, and prepare derivative works of
 * the caBIG(TM) Software in any manner and for any purpose, and to have or permit
 * others to do so; (ii) make, have made, use, practice, sell, and offer for
 * sale, import, and/or otherwise dispose of caBIG(TM) Software (or portions
 * thereof); (iii) distribute and have distributed to and by third parties the
 * caBIG(TM) Software and any modifications and derivative works thereof; and (iv)
 * sublicense the foregoing rights set out in (i), (ii) and (iii) to third
 * parties, including the right to license such rights to further third parties.
 * For sake of clarity, and not by way of limitation, caBIG(TM) Participant shall
 * have no right of accounting or right of payment from You or Your sublicensees
 * for the rights granted under this License. This License is granted at no
 * charge to You. Your downloading, copying, modifying, displaying, distributing
 * or use of caBIG(TM) Software constitutes acceptance of all of the terms and
 * conditions of this Agreement. If you do not agree to such terms and
 * conditions, you have no right to download, copy, modify, display, distribute
 * or use the caBIG(TM) Software. 1. Your redistributions of the source code for
 * the caBIG(TM) Software must retain the above copyright notice, this list of
 * conditions and the disclaimer and limitation of liability of Article 6 below.
 * Your redistributions in object code form must reproduce the above copyright
 * notice, this list of conditions and the disclaimer of Article 6 in the
 * documentation and/or other materials provided with the distribution, if any.
 * 2. Your end-user documentation included with the redistribution, if any, must
 * include the following acknowledgment: "This product includes software
 * developed by ScenPro, Inc." If You do not include such end-user
 * documentation, You shall include this acknowledgment in the caBIG(TM) Software
 * itself, wherever such third-party acknowledgments normally appear. 3. You may
 * not use the names "ScenPro, Inc", "The National Cancer Institute", "NCI",
 * "Cancer Bioinformatics Grid" or "caBIG(TM)" to endorse or promote products
 * derived from this caBIG(TM) Software. This License does not authorize You to use
 * any trademarks, service marks, trade names, logos or product names of either
 * caBIG(TM) Participant, NCI or caBIG(TM), except as required to comply with the
 * terms of this License. 4. For sake of clarity, and not by way of limitation,
 * You may incorporate this caBIG(TM) Software into Your proprietary programs and
 * into any third party proprietary programs. However, if You incorporate the
 * caBIG(TM) Software into third party proprietary programs, You agree that You are
 * solely responsible for obtaining any permission from such third parties
 * required to incorporate the caBIG(TM) Software into such third party proprietary
 * programs and for informing Your sublicensees, including without limitation
 * Your end-users, of their obligation to secure any required permissions from
 * such third parties before incorporating the caBIG(TM) Software into such third
 * party proprietary software programs. In the event that You fail to obtain
 * such permissions, You agree to indemnify caBIG(TM) Participant for any claims
 * against caBIG(TM) Participant by such third parties, except to the extent
 * prohibited by law, resulting from Your failure to obtain such permissions. 5.
 * For sake of clarity, and not by way of limitation, You may add Your own
 * copyright statement to Your modifications and to the derivative works, and
 * You may provide additional or different license terms and conditions in Your
 * sublicenses of modifications of the caBIG(TM) Software, or any derivative works
 * of the caBIG(TM) Software as a whole, provided Your use, reproduction, and
 * distribution of the Work otherwise complies with the conditions stated in
 * this License. 6. THIS caBIG(TM) SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED
 * OR IMPLIED WARRANTIES (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE)
 * ARE DISCLAIMED. IN NO EVENT SHALL THE ScenPro, Inc OR ITS AFFILIATES BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS caBIG(TM) SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
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
