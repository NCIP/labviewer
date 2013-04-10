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
import java.sql.SQLException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.apache.log4j.Logger;

/**
 * @author asharma
 */
public class VerifyNextValueData extends TestCase
{
	// Logging File
	private static Logger logger = Logger.getLogger("client");

	private CTLabDAO dao = new CTLabDAO();
	private Connection con;

	public VerifyNextValueData(String name)
	{
		super(name);
	}

	/**
	 * Verify next value from sequence
	 */
	public void testVerifyNextValData()
	{
		try
		{
			System.out.println("Test Next Value");
			con = dao.getConnection();
			Long id = dao.getNextVal(con, "protocol_seq");
			System.out.println(id);
			logger.info("Verified smoke study data");

		}
		catch (Exception e)
		{
			logger.error("Error verifying smoke study data", e);
		}
		finally
		{
			con = SQLHelper.closeConnection(con);
		}

	}

	public static Test suite()
	{
		TestSuite suite = new TestSuite();
		suite.addTest(new VerifyNextValueData("testVerifyStudyData"));
		return suite;
	}

}
