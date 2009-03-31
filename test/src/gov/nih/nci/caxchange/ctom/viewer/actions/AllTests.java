package gov.nih.nci.caxchange.ctom.viewer.actions;

import gov.nih.nci.caxchange.ctom.viewer.beans.DTOTest;
import junit.framework.Test;
import junit.framework.TestSuite;


public class AllTests
{

	public static Test suite()
	{

		TestSuite suite = new TestSuite("Test for gov.nih.nci.caxchange.ctom.viewer.actions");
		//$JUnit-BEGIN$
		suite.addTestSuite(LoginActionTest.class);
		suite.addTestSuite(LoadHealthCareSiteActionTest.class);
		suite.addTestSuite(HappyActionTest.class);
		suite.addTestSuite(LoadAdminActionTest.class);
		suite.addTestSuite(DTOTest.class);
		//$JUnit-END$
		return suite;
	}

}
