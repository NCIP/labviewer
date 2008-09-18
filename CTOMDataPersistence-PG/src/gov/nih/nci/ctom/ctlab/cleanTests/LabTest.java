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
public class LabTest extends TestCase
{
	Logger logger = Logger.getLogger(getClass());
	private  CTTestDAO dao = new CTTestDAO();
	private  Connection con;
	
    public LabTest(String name)
    {
        super(name);
    }

    // Clean 'smoke study' lab from CTODS database
	public void testCleanSmokeStudyLab()
	{
		try
	    {
		    con = dao.getConnection();
		    dao.cleanSmokeStudyLab(con);
		    logger.info("Cleaned the smoke study lab");

	    }
	    catch (Exception e)
		{
		    logger.error("Error cleaning smoke study lab", e);
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
        suite.addTest(new LabTest("testCleanSmokeStudyLab"));
        return suite;
    }

}