package gov.nih.nci.ctom.ctlab.cleanTests;

import java.sql.Connection;
import java.sql.SQLException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.apache.log4j.Logger;

/**
 * @author Yogaraj Khanal
 */
public class PatientTest extends TestCase
{
	Logger logger = Logger.getLogger(getClass());
	private CTTestDAO dao = new CTTestDAO();
	private Connection con;
	
	/**
	 * @param name
	 */
	public PatientTest(String name)
    {
        super(name);
    }

    
	/**
	 * Clean 'smoke study' patient from CTODS database
	 */
	public void testCleanSmokeStudyPatient()
	{
	   try
	    {
		    con = dao.getConnection();
		    dao.cleanSmokeStudyPatient(con);
		    logger.info("Cleaned the smoke study patient");

	    }
	    catch (Exception e)
		{
		    logger.error("Error cleaning smoke study patient", e);
		}
		finally
		{
		    try
		    {
				con.close();
			}
			catch (SQLException e)
			{
				logger.error("Error closing connection",e);
			}
	    }

    }

    public static Test suite()
    {
        TestSuite suite = new TestSuite();
        suite.addTest(new PatientTest("testCleanSmokeStudyPatient"));
        return suite;
    }

}