/**
 * Copyright Notice. Copyright 2008 ScenPro, Inc (“caBIG™
 * Participant”).caXchange was created with NCI funding and is part of the
 * caBIG™ initiative. The software subject to this notice and license includes
 * both human readable source code form and machine readable, binary, object
 * code form (the “caBIG™ Software”). This caBIG™ Software License (the
 * “License”) is between caBIG™ Participant and You. “You (or “Your”) shall mean
 * a person or an entity, and all other entities that control, are controlled
 * by, or are under common control with the entity. “Control” for purposes of
 * this definition means (i) the direct or indirect power to cause the direction
 * or management of such entity, whether by contract or otherwise, or (ii)
 * ownership of fifty percent (50%) or more of the outstanding shares, or (iii)
 * beneficial ownership of such entity. License. Provided that You agree to the
 * conditions described below, caBIG™ Participant grants You a non-exclusive,
 * worldwide, perpetual, fully-paid-up, no-charge, irrevocable, transferable and
 * royalty-free right and license in its rights in the caBIG™ Software,
 * including any copyright or patent rights therein, to (i) use, install,
 * disclose, access, operate, execute, reproduce, copy, modify, translate,
 * market, publicly display, publicly perform, and prepare derivative works of
 * the caBIG™ Software in any manner and for any purpose, and to have or permit
 * others to do so; (ii) make, have made, use, practice, sell, and offer for
 * sale, import, and/or otherwise dispose of caBIG™ Software (or portions
 * thereof); (iii) distribute and have distributed to and by third parties the
 * caBIG™ Software and any modifications and derivative works thereof; and (iv)
 * sublicense the foregoing rights set out in (i), (ii) and (iii) to third
 * parties, including the right to license such rights to further third parties.
 * For sake of clarity, and not by way of limitation, caBIG™ Participant shall
 * have no right of accounting or right of payment from You or Your sublicensees
 * for the rights granted under this License. This License is granted at no
 * charge to You. Your downloading, copying, modifying, displaying, distributing
 * or use of caBIG™ Software constitutes acceptance of all of the terms and
 * conditions of this Agreement. If you do not agree to such terms and
 * conditions, you have no right to download, copy, modify, display, distribute
 * or use the caBIG™ Software. 1. Your redistributions of the source code for
 * the caBIG™ Software must retain the above copyright notice, this list of
 * conditions and the disclaimer and limitation of liability of Article 6 below.
 * Your redistributions in object code form must reproduce the above copyright
 * notice, this list of conditions and the disclaimer of Article 6 in the
 * documentation and/or other materials provided with the distribution, if any.
 * 2. Your end-user documentation included with the redistribution, if any, must
 * include the following acknowledgment: “This product includes software
 * developed by ScenPro, Inc.” If You do not include such end-user
 * documentation, You shall include this acknowledgment in the caBIG™ Software
 * itself, wherever such third-party acknowledgments normally appear. 3. You may
 * not use the names “ScenPro, Inc”, “The National Cancer Institute”, “NCI”,
 * “Cancer Bioinformatics Grid” or “caBIG™” to endorse or promote products
 * derived from this caBIG™ Software. This License does not authorize You to use
 * any trademarks, service marks, trade names, logos or product names of either
 * caBIG™ Participant, NCI or caBIG™, except as required to comply with the
 * terms of this License. 4. For sake of clarity, and not by way of limitation,
 * You may incorporate this caBIG™ Software into Your proprietary programs and
 * into any third party proprietary programs. However, if You incorporate the
 * caBIG™ Software into third party proprietary programs, You agree that You are
 * solely responsible for obtaining any permission from such third parties
 * required to incorporate the caBIG™ Software into such third party proprietary
 * programs and for informing Your sublicensees, including without limitation
 * Your end-users, of their obligation to secure any required permissions from
 * such third parties before incorporating the caBIG™ Software into such third
 * party proprietary software programs. In the event that You fail to obtain
 * such permissions, You agree to indemnify caBIG™ Participant for any claims
 * against caBIG™ Participant by such third parties, except to the extent
 * prohibited by law, resulting from Your failure to obtain such permissions. 5.
 * For sake of clarity, and not by way of limitation, You may add Your own
 * copyright statement to Your modifications and to the derivative works, and
 * You may provide additional or different license terms and conditions in Your
 * sublicenses of modifications of the caBIG™ Software, or any derivative works
 * of the caBIG™ Software as a whole, provided Your use, reproduction, and
 * distribution of the Work otherwise complies with the conditions stated in
 * this License. 6. THIS caBIG™ SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED
 * OR IMPLIED WARRANTIES (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE)
 * ARE DISCLAIMED. IN NO EVENT SHALL THE ScenPro, Inc OR ITS AFFILIATES BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS caBIG™ SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
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
