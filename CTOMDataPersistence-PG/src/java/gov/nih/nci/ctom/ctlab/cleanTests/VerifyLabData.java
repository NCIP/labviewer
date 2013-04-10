/*
* Copyright ScenPro, Inc and SemanticBits, LLC
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/labviewer/LICENSE.txt for details.
*/
package gov.nih.nci.ctom.ctlab.cleanTests;

import gov.nih.nci.ctom.ctlab.persistence.SQLHelper;

import java.sql.Connection;
import java.sql.SQLException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.apache.log4j.Logger;

/**
 * @author asharma
 */
public class VerifyLabData extends TestCase
{
	// Logging File
	private static Logger logger = Logger.getLogger("client");

	private CTTestDAO dao = new CTTestDAO();
	private Connection con;

	public VerifyLabData(String name)
	{
		super(name);
	}

	/**
	 * Verify 'smoke study' Lab data from CTODS database
	 */
	public void testVerifyLabData()
	{
		try
		{
			con = dao.getConnection();
			dao.verifySmokeLabData(con);
			logger.info("Verified the smoke study lab data");

		}
		catch (Exception e)
		{
			logger.error("Error verifying smoke study lab data", e);
		}
		finally
		{
			con = SQLHelper.closeConnection(con);
		}

	}

	public static Test suite()
	{
		TestSuite suite = new TestSuite();
		suite.addTest(new VerifyLabData("testVerifyLabData"));
		return suite;
	}

}
