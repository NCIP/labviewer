/*
* Copyright ScenPro, Inc and SemanticBits, LLC
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/labviewer/LICENSE.txt for details.
*/
package gov.nih.nci.ctom.ctlab.cleanTests;

import gov.nih.nci.ctom.ctlab.persistence.CTLabDAO;
import gov.nih.nci.ctom.ctlab.persistence.SQLHelper;

import java.sql.Connection;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.apache.log4j.Logger;

/**
 * @author Yogaraj Khanal
 */
public class CTTest extends TestCase
{
	// Logging File
	private static Logger logger = Logger.getLogger("client");

	private CTLabDAO dao = new CTLabDAO();
	private Connection con;

	public CTTest(String name)
	{
		super(name);
	}

	//Test retrieveCTEPIDForHCS
	public void testRetrieveCTEPIDForHCS()
	{
		try
		{
			con = dao.getConnection();
			List<String> ids = dao.retrieveCTEPIDForHCS(con, Long.valueOf(107));
			assertNotNull(ids);
			logger.info("Cleaned the smoke study lab");

		}
		catch (Exception e)
		{
			logger.error("Error testing retrieveCTEPIDForHCS", e);
		}
		finally
		{
			con = SQLHelper.closeConnection(con);
		}

	}

	public static Test suite()
	{
		TestSuite suite = new TestSuite();
		suite.addTest(new CTTest("testCleanSmokeStudyLab"));
		return suite;
	}

}
