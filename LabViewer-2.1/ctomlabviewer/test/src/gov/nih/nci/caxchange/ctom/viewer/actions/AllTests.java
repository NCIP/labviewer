package gov.nih.nci.caxchange.ctom.viewer.actions;

import gov.nih.nci.caxchange.ctom.viewer.apitest.FiltersDAOTest;
import gov.nih.nci.caxchange.ctom.viewer.apitest.HealthCareSiteDAOTest;
import gov.nih.nci.caxchange.ctom.viewer.apitest.LabSearchDAOTest;
import gov.nih.nci.caxchange.ctom.viewer.apitest.PIDAOTest;
import gov.nih.nci.caxchange.ctom.viewer.apitest.ParticipantSearchDAOTest;
import gov.nih.nci.caxchange.ctom.viewer.apitest.StudySearchDAOTest;
import gov.nih.nci.caxchange.ctom.viewer.beans.DTOTest;
import gov.nih.nci.caxchange.ctom.viewer.beans.EnumTest;
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
		suite.addTestSuite(FiltersDAOTest.class);
		suite.addTestSuite(LabSearchDAOTest.class);
		suite.addTestSuite(ParticipantSearchDAOTest.class);
		//suite.addTestSuite(HealthCareSiteDAOTest.class);
		//suite.addTestSuite(PIDAOTest.class);
		suite.addTestSuite(StudySearchDAOTest.class);
		
		suite.addTestSuite(DTOTest.class);
		suite.addTestSuite(EnumTest.class);		
		//$JUnit-END$
		return suite;
	}

}
