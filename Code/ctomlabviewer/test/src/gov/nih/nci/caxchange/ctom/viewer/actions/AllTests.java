package gov.nih.nci.caxchange.ctom.viewer.actions;

import junit.framework.Test;
import junit.framework.TestSuite;


public class AllTests
{

	public static Test suite()
	{

		TestSuite suite =
				new TestSuite("Test for gov.nih.nci.caxchange.ctom.viewer.junits.actions");
		//$JUnit-BEGIN$
		suite.addTestSuite(LoginActionTest.class);
		suite.addTestSuite(LoadAdminActionTest.class);
		suite.addTestSuite(HappyActionTest.class);
		suite.addTestSuite(LoadHealthCareSiteActionTest.class);
		//$JUnit-END$
		return suite;
	}

}
