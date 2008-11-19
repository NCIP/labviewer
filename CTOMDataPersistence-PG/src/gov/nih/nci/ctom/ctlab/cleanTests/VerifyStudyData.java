/**
 * 
 */
package gov.nih.nci.ctom.ctlab.cleanTests;

import java.sql.Connection;
import java.sql.SQLException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.apache.log4j.Logger;

/**
 * @author asharma
 *
 */
public class VerifyStudyData extends TestCase
{
	// Logging File
	private static Logger logger = Logger
			.getLogger("client"); 

	private CTTestDAO dao = new CTTestDAO();
	private Connection con;
	
    public VerifyStudyData(String name)
    {
        super(name);
    }

   	/**
	 *  Verify 'smoke study' from CTODS database
	 */
	public void testVerifyStudyData()
	{
	   try
	    {
		    System.out.println("Test");
		    con = dao.getConnection();
		    dao.verifySmokeStudyData(con);
		    logger.info("Verified smoke study data");

	    }
	    catch (Exception e)
		{
		    logger.error("Error verifying smoke study data", e);
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
        suite.addTest(new VerifyStudyData("testVerifyStudyData"));
        return suite;
    }

}
