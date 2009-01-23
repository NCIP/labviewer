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
package gov.nih.nci.ctom.ctlab.cleanTests;

import gov.nih.nci.ctom.ctlab.persistence.BaseJDBCDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

/**
 * CTTestDAO performs data clean up for Study, Patient and Lab data.
 * <P>
 * 
 * @author Lisa Kelley, asharma
 */
public class CTTestDAO extends BaseJDBCDAO
{
	// Logging File
	private static Logger logger = Logger.getLogger("client");

	/**
	 * @param con
	 * @throws Exception
	 */
	public void cleanSmokeStudy(Connection con) throws Exception
	{
		logger.info("Cleaning smoke study");

		String sql =
				"delete from STUDY_SITE where "
						+ "(PROTOCOL_ID = (select PROTOCOL_ID from IDENTIFIER where (ROOT='51bd374c-d8b5-4759-84b3-ac6259d58648' and EXTENSION='SMOKE_TEST')) "
						+ "and HEALTHCARE_SITE_ID = (select ID from HEALTHCARE_SITE where NCI_INSTITUTE_CODE='NCI'))";
		deleteFromTable(con, sql);

		sql = "delete from HEALTHCARE_SITE where NCI_INSTITUTE_CODE = 'NCI'";
		deleteFromTable(con, sql);

		sql =
				"delete from STUDY_INVESTIGATOR where "
						+ "(PROTOCOL_ID = (select PROTOCOL_ID from IDENTIFIER where (ROOT='51bd374c-d8b5-4759-84b3-ac6259d58648' and EXTENSION='SMOKE_TEST')) "
						+ "and INVESTIGATOR_ID = (select ID from INVESTIGATOR where NCI_IDENTIFIER='04_C_0133'))";
		deleteFromTable(con, sql);

		sql = "delete from INVESTIGATOR where NCI_IDENTIFIER = '04_C_0133'";
		deleteFromTable(con, sql);

		sql =
				"delete from PROTOCOL where ID = (select PROTOCOL_ID from IDENTIFIER where (ROOT='51bd374c-d8b5-4759-84b3-ac6259d58648' and EXTENSION='SMOKE_TEST'))";
		deleteFromTable(con, sql);

		sql =
				"delete from IDENTIFIER where (ROOT='51bd374c-d8b5-4759-84b3-ac6259d58648' and EXTENSION='SMOKE_TEST')"; // at
																															// this
																															// point,
																															// this
																															// row
																															// was
																															// already
																															// deleted
		deleteFromTable(con, sql);
	}

	/**
	 * @param con
	 * @throws Exception
	 */
	public void cleanSmokeStudyPatient(Connection con) throws Exception
	{
		logger.info("Cleaning smoke study patient");

		String sql =
				"delete from STUDY_PARTICIPANT_ASSIGNMENT where ID = "
						+ "(select STUDY_PARTICIPANT_ASSIGNMNT_ID from IDENTIFIER where "
						+ "ROOT='6115c43c-851e-425c-8312-fd78367aaef3a' and EXTENSION='00-00-00-0' and STUDY_PARTICIPANT_ASSIGNMNT_ID is not null)";
		deleteFromTable(con, sql);

		sql =
				"delete from PARTICIPANT where ID = "
						+ "(select PARTICIPANT_ID from IDENTIFIER where ROOT='91dd4580-801b-4874-adeb-a174361bacea' and EXTENSION='00-00-00-0' and PARTICIPANT_ID is not null)";
		deleteFromTable(con, sql);

		sql =
				"delete from IDENTIFIER where (ROOT='6115c43c-851e-425c-8312-fd78367aaef3a' and EXTENSION='00-00-00-0')"; // at
																															// this
																															// point,
																															// this
																															// row
																															// was
																															// already
																															// deleted
		deleteFromTable(con, sql);

		sql =
				"delete from IDENTIFIER where (ROOT='91dd4580-801b-4874-adeb-a174361bacea' and EXTENSION='00-00-00-0')"; // at
																															// this
																															// point,
																															// this
																															// row
																															// was
																															// already
																															// deleted
		deleteFromTable(con, sql);
	}

	/**
	 * @param con
	 * @throws Exception
	 */
	public void cleanSmokeStudyLab(Connection con) throws Exception
	{
		logger.info("Cleaning smoke study lab");

		/*
		 * String sql = "delete from STUDY_TIME_POINT where ACTIVITY_ID =
		 * (select ID from ACTIVITY where STUDY_PARTICIPANT_ASSIGNMNT_ID = " +
		 * "(select STUDY_PARTICIPANT_ASSIGNMNT_ID from IDENTIFIER where
		 * ROOT='2.16.840.1.113883.19' and EXTENSION='32-71-13-38' " + "and
		 * STUDY_PARTICIPANT_ASSIGNMNT_ID is not null))"; deleteFromTable(con,
		 * sql); sql = "delete from SPECIMEN where SPECIMEN_COLLECTION_ID =
		 * (select ID from ACTIVITY where STUDY_PARTICIPANT_ASSIGNMNT_ID = " +
		 * "(select STUDY_PARTICIPANT_ASSIGNMNT_ID from IDENTIFIER where
		 * ROOT='2.16.840.1.113883.19' and EXTENSION='32-71-13-38' " + "and
		 * STUDY_PARTICIPANT_ASSIGNMNT_ID is not null))"; deleteFromTable(con,
		 * sql); sql = "delete from SPECIMEN_COLLECTION where ID = (select ID
		 * from ACTIVITY where STUDY_PARTICIPANT_ASSIGNMNT_ID = " + "(select
		 * STUDY_PARTICIPANT_ASSIGNMNT_ID from IDENTIFIER where
		 * ROOT='2.16.840.1.113883.19' and EXTENSION='32-71-13-38' " + "and
		 * STUDY_PARTICIPANT_ASSIGNMNT_ID is not null))"; deleteFromTable(con,
		 * sql); sql = "delete from PROCEDURE where ID = (select ID from
		 * ACTIVITY where STUDY_PARTICIPANT_ASSIGNMNT_ID = " + "(select
		 * STUDY_PARTICIPANT_ASSIGNMNT_ID from IDENTIFIER where
		 * ROOT='2.16.840.1.113883.19' and EXTENSION='32-71-13-38' " + "and
		 * STUDY_PARTICIPANT_ASSIGNMNT_ID is not null))"; deleteFromTable(con,
		 * sql); sql = "delete from CLINICAL_RESULT where ID = (select ID from
		 * OBSERVATION where ACTIVITY_ID = (select ID from ACTIVITY where
		 * STUDY_PARTICIPANT_ASSIGNMNT_ID = " + "(select
		 * STUDY_PARTICIPANT_ASSIGNMNT_ID from IDENTIFIER where
		 * ROOT='2.16.840.1.113883.19' and EXTENSION='32-71-13-38' " + "and
		 * STUDY_PARTICIPANT_ASSIGNMNT_ID is not null)))"; deleteFromTable(con,
		 * sql); sql = "delete from OBSERVATION where ACTIVITY_ID = (select ID
		 * from ACTIVITY where STUDY_PARTICIPANT_ASSIGNMNT_ID = " + "(select
		 * STUDY_PARTICIPANT_ASSIGNMNT_ID from IDENTIFIER where
		 * ROOT='2.16.840.1.113883.19' and EXTENSION='32-71-13-38' " + "and
		 * STUDY_PARTICIPANT_ASSIGNMNT_ID is not null))"; deleteFromTable(con,
		 * sql);
		 */

		String sql =
				"delete from ACTIVITY where STUDY_PARTICIPANT_ASSIGNMNT_ID = "
						+ "(select STUDY_PARTICIPANT_ASSIGNMNT_ID from IDENTIFIER where ROOT='6115c43c-851e-425c-8312-fd78367aaef3a' and EXTENSION='00-00-00-0' "
						+ "and STUDY_PARTICIPANT_ASSIGNMNT_ID is not null)";
		deleteFromTable(con, sql);
	}

	/**
	 * @param con
	 * @throws Exception
	 */
	public void verifySmokeStudyData(Connection con) throws Exception
	{
		logger.info("Verifying smoke study data");
		String sql =
				"select ID from PROTOCOL where ID = (select PROTOCOL_ID from IDENTIFIER where (ROOT='51bd374c-d8b5-4759-84b3-ac6259d58648' and EXTENSION='SMOKE_TEST'))";
		verifyData(con, sql, "Study");

	}

	/**
	 * @param con
	 * @throws Exception
	 */
	public void verifySmokePatientData(Connection con) throws Exception
	{
		logger.info("Verifying smoke study patient data");
		String sql =
				"select ID from STUDY_PARTICIPANT_ASSIGNMNT where ID = (select STUDY_PARTICIPANT_ASSIGNMNT_ID from IDENTIFIER where (EXTENSION='00-00-00-0' and STUDY_PARTICIPANT_ASSIGNMNT_ID is not null))";
		verifyData(con, sql, "Patient");
	}

	/**
	 * @param con
	 * @throws Exception
	 */
	public void verifySmokeLabData(Connection con) throws Exception
	{
		logger.info("Verifying smoke study lab data");
		String sql =
				"select distinct STUDY_PARTICIPANT_ASSIGNMNT_ID from ACTIVITY where STUDY_PARTICIPANT_ASSIGNMNT_ID = "
						+ "(select STUDY_PARTICIPANT_ASSIGNMNT_ID from IDENTIFIER where ROOT='6115c43c-851e-425c-8312-fd78367aaef3' and EXTENSION='00-00-00-0' and STUDY_PARTICIPANT_ASSIGNMNT_ID is not null)";

		verifyData(con, sql, "Lab");
	}

	/**
	 * @param con
	 * @param sql
	 * @param type
	 * @throws SQLException
	 */
	private void verifyData(Connection con, String sql, String type)
			throws SQLException
	{

		Statement stmt = null;
		ResultSet rs = null;
		try
		{
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				logger.info("Data Exists for " + type);
				System.out.println(rs.getInt(1));
			}
			else
			{
				logger.info("Data does not exist for " + type);
				System.out.println("Data does not exist for " + type);
			}
		}
		finally
		{
			if (stmt != null)
			{
				stmt.close();
			}
			if (rs != null)
			{
				rs.close();
			}
		}

	}

	/**
	 * @param con
	 * @param sql
	 * @throws SQLException
	 */
	private void deleteFromTable(Connection con, String sql)
			throws SQLException
	{
		Statement stmt = null;

		try
		{
			stmt = con.createStatement();
			stmt.executeUpdate(sql);
			con.commit();
		}
		finally
		{
			if (stmt != null)
			{
				stmt.close();
			}
		}
	}

}
