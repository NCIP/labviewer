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
 * @author Lisa Kelley, asharma
 */
public class CTTestDAO extends BaseJDBCDAO
{
	// Logging File
	private static Logger logger = Logger
			.getLogger("client"); 

  /**
	 * @param con
	 * @throws Exception
	 */
	public void cleanSmokeStudy(Connection con) throws Exception
	{
		logger.info("Cleaning smoke study");

		String sql = "delete from STUDY_SITE where "
		           + "(PROTOCOL_ID = (select PROTOCOL_ID from IDENTIFIER where (ROOT='51bd374c-d8b5-4759-84b3-ac6259d58648' and EXTENSION='SMOKE_TEST')) "
                   + "and HEALTHCARE_SITE_ID = (select ID from HEALTHCARE_SITE where NCI_INSTITUTE_CODE='NCI'))";
        deleteFromTable(con, sql);

        sql = "delete from HEALTHCARE_SITE where NCI_INSTITUTE_CODE = 'NCI'";
        deleteFromTable(con, sql);

        sql = "delete from STUDY_INVESTIGATOR where "
            + "(PROTOCOL_ID = (select PROTOCOL_ID from IDENTIFIER where (ROOT='51bd374c-d8b5-4759-84b3-ac6259d58648' and EXTENSION='SMOKE_TEST')) "
            + "and INVESTIGATOR_ID = (select ID from INVESTIGATOR where NCI_IDENTIFIER='04_C_0133'))";
        deleteFromTable(con, sql);

        sql = "delete from INVESTIGATOR where NCI_IDENTIFIER = '04_C_0133'";
        deleteFromTable(con, sql);

        sql = "delete from PROTOCOL where ID = (select PROTOCOL_ID from IDENTIFIER where (ROOT='51bd374c-d8b5-4759-84b3-ac6259d58648' and EXTENSION='SMOKE_TEST'))";
        deleteFromTable(con, sql);

        sql = "delete from IDENTIFIER where (ROOT='51bd374c-d8b5-4759-84b3-ac6259d58648' and EXTENSION='SMOKE_TEST')"; //  at this point, this row was already deleted
        deleteFromTable(con, sql);
	}

    /**
     * @param con
     * @throws Exception
     */
    public void cleanSmokeStudyPatient(Connection con) throws Exception
	{
		logger.info("Cleaning smoke study patient");

		String sql = "delete from STUDY_PARTICIPANT_ASSIGNMENT where ID = "
                   + "(select STUDY_PARTICIPANT_ASSIGNMNT_ID from IDENTIFIER where "
                   + "ROOT='6115c43c-851e-425c-8312-fd78367aaef3a' and EXTENSION='00-00-00-0' and STUDY_PARTICIPANT_ASSIGNMNT_ID is not null)";
	    deleteFromTable(con, sql);

		sql = "delete from PARTICIPANT where ID = "
            + "(select PARTICIPANT_ID from IDENTIFIER where ROOT='91dd4580-801b-4874-adeb-a174361bacea' and EXTENSION='00-00-00-0' and PARTICIPANT_ID is not null)";
	    deleteFromTable(con, sql);

	    sql = "delete from IDENTIFIER where (ROOT='6115c43c-851e-425c-8312-fd78367aaef3a' and EXTENSION='00-00-00-0')"; // at this point, this row was already deleted
	    deleteFromTable(con, sql);

	    sql = "delete from IDENTIFIER where (ROOT='91dd4580-801b-4874-adeb-a174361bacea' and EXTENSION='00-00-00-0')"; // at this point, this row was already deleted
	    deleteFromTable(con, sql);
	}

	/**
	 * @param con
	 * @throws Exception
	 */
	public void cleanSmokeStudyLab(Connection con) throws Exception
	{
		logger.info("Cleaning smoke study lab");

     /*String sql = "delete from STUDY_TIME_POINT where ACTIVITY_ID = (select ID from ACTIVITY where STUDY_PARTICIPANT_ASSIGNMNT_ID = "
                   + "(select STUDY_PARTICIPANT_ASSIGNMNT_ID from IDENTIFIER where ROOT='2.16.840.1.113883.19' and EXTENSION='32-71-13-38' "
                   +  "and STUDY_PARTICIPANT_ASSIGNMNT_ID is not null))";
		deleteFromTable(con, sql);

		sql = "delete from SPECIMEN where SPECIMEN_COLLECTION_ID = (select ID from ACTIVITY where STUDY_PARTICIPANT_ASSIGNMNT_ID = "
            + "(select STUDY_PARTICIPANT_ASSIGNMNT_ID from IDENTIFIER where ROOT='2.16.840.1.113883.19' and EXTENSION='32-71-13-38' "
            + "and STUDY_PARTICIPANT_ASSIGNMNT_ID is not null))";
		deleteFromTable(con, sql);

		sql = "delete from SPECIMEN_COLLECTION where ID = (select ID from ACTIVITY where STUDY_PARTICIPANT_ASSIGNMNT_ID = "
            + "(select STUDY_PARTICIPANT_ASSIGNMNT_ID from IDENTIFIER where ROOT='2.16.840.1.113883.19' and EXTENSION='32-71-13-38' "
            + "and STUDY_PARTICIPANT_ASSIGNMNT_ID is not null))";
		deleteFromTable(con, sql);

		sql = "delete from PROCEDURE where ID = (select ID from ACTIVITY where STUDY_PARTICIPANT_ASSIGNMNT_ID = "
            + "(select STUDY_PARTICIPANT_ASSIGNMNT_ID from IDENTIFIER where ROOT='2.16.840.1.113883.19' and EXTENSION='32-71-13-38' "
            + "and STUDY_PARTICIPANT_ASSIGNMNT_ID is not null))";
		deleteFromTable(con, sql);

		sql = "delete from CLINICAL_RESULT where ID = (select ID from OBSERVATION where ACTIVITY_ID = (select ID from ACTIVITY where STUDY_PARTICIPANT_ASSIGNMNT_ID = "
            + "(select STUDY_PARTICIPANT_ASSIGNMNT_ID from IDENTIFIER where ROOT='2.16.840.1.113883.19' and EXTENSION='32-71-13-38' "
            + "and STUDY_PARTICIPANT_ASSIGNMNT_ID is not null)))";
		deleteFromTable(con, sql);

		sql = "delete from OBSERVATION where ACTIVITY_ID = (select ID from ACTIVITY where STUDY_PARTICIPANT_ASSIGNMNT_ID = "
            + "(select STUDY_PARTICIPANT_ASSIGNMNT_ID from IDENTIFIER where ROOT='2.16.840.1.113883.19' and EXTENSION='32-71-13-38' "
            + "and STUDY_PARTICIPANT_ASSIGNMNT_ID is not null))";
		deleteFromTable(con, sql);*/

		String sql = "delete from ACTIVITY where STUDY_PARTICIPANT_ASSIGNMNT_ID = "
            + "(select STUDY_PARTICIPANT_ASSIGNMNT_ID from IDENTIFIER where ROOT='6115c43c-851e-425c-8312-fd78367aaef3a' and EXTENSION='00-00-00-0' "
            + "and STUDY_PARTICIPANT_ASSIGNMNT_ID is not null)";
		deleteFromTable(con, sql);
	}

	
	/**
	 * @param con
	 * @throws Exception
	 */
	public void  verifySmokeStudyData(Connection con) throws Exception
	{
		logger.info("Verifying smoke study data");
		String sql="select ID from PROTOCOL where ID = (select PROTOCOL_ID from IDENTIFIER where (ROOT='51bd374c-d8b5-4759-84b3-ac6259d58648' and EXTENSION='SMOKE_TEST'))";
		verifyData(con,sql,"Study");
		
	}
	/**
	 * @param con
	 * @throws Exception
	 */
	public void  verifySmokePatientData(Connection con) throws Exception
	{
		logger.info("Verifying smoke study patient data");
		String sql="select ID from STUDY_PARTICIPANT_ASSIGNMNT where ID = (select STUDY_PARTICIPANT_ASSIGNMNT_ID from IDENTIFIER where (EXTENSION='00-00-00-0' and STUDY_PARTICIPANT_ASSIGNMNT_ID is not null))";
		verifyData(con,sql,"Patient");
	}
	/**
	 * @param con
	 * @throws Exception
	 */
	public void  verifySmokeLabData(Connection con) throws Exception
	{
		logger.info("Verifying smoke study lab data");
		String sql="select distinct STUDY_PARTICIPANT_ASSIGNMNT_ID from ACTIVITY where STUDY_PARTICIPANT_ASSIGNMNT_ID = "
			+ "(select STUDY_PARTICIPANT_ASSIGNMNT_ID from IDENTIFIER where ROOT='6115c43c-851e-425c-8312-fd78367aaef3' and EXTENSION='00-00-00-0' and STUDY_PARTICIPANT_ASSIGNMNT_ID is not null)";

		verifyData(con,sql,"Lab");
	}

	
	/**
	 * @param con
	 * @param sql
	 * @param type
	 * @throws SQLException
	 */
	private void verifyData(Connection con, String sql, String type) throws SQLException{
		
		Statement stmt = null;
		ResultSet rs=null;
		try
		{
			stmt = con.createStatement();
		    rs = stmt.executeQuery(sql);
		    if(rs.next())
		    { 
		     logger.info("Data Exists for "+ type);
		     System.out.println(rs.getInt(1));
		    }
		    else
		    {
		    	logger.info("Data does not exist for "+ type);
		    	System.out.println("Data does not exist for "+ type);
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
	private void deleteFromTable(Connection con, String sql)throws SQLException
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
