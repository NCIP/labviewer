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
public class StudyTest extends TestCase
{
	Logger logger = Logger.getLogger(getClass());
	private CTTestDAO dao = new CTTestDAO();
	private Connection con;
	
    public StudyTest(String name)
    {
        super(name);
    }

    // Clean 'smoke study' from CTODS database
	public void testCleanSmokeStudy()
	{
	   try
	    {
		    con = dao.getConnection();
		    dao.cleanSmokeStudy(con);
		    logger.info("Cleaned the smoke study");

	    }
	    catch (Exception e)
		{
		    logger.error("Error cleaning smoke study", e);
		}
		finally
		{
		    try
		    {
		    	if(con!=null){
		    		con.close();	
		    	}
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
        suite.addTest(new StudyTest("testCleanSmokeStudy"));
        return suite;
    }

}