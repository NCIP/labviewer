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
public class VerifyPatientData extends TestCase
{
	// Logging File
	private static Logger logger = Logger
			.getLogger("client"); 

	private CTTestDAO dao = new CTTestDAO();
	private Connection con;
	
    public VerifyPatientData(String name)
    {
        super(name);
    }


    
	/**
	 * Verify 'smoke study' Patient data from CTODS database
	 */
	public void testVerifyPatientData()
	{
	   try
	    {
		    con = dao.getConnection();
		    dao.verifySmokePatientData(con);
		    logger.info("Verified the smoke study patient data");

	    }
	    catch (Exception e)
		{
		    logger.error("Error verifying smoke study patient data", e);
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
        suite.addTest(new VerifyPatientData("testVerifyPatientData"));
        return suite;
    }
}
