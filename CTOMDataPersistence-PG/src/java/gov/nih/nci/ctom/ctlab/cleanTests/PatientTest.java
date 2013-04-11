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
 * @author Yogaraj Khanal
 */
public class PatientTest extends TestCase
{
	// Logging File
	private static Logger logger = Logger.getLogger("client");

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
			con = SQLHelper.closeConnection(con);
		}

	}

	public static Test suite()
	{
		TestSuite suite = new TestSuite();
		suite.addTest(new PatientTest("testCleanSmokeStudyPatient"));
		return suite;
	}

}
