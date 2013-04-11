/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */
package gov.nih.nci.caxchange.ctom.viewer.apitest;

import gov.nih.nci.caxchange.ctom.viewer.DAO.FiltersDAO;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import junit.framework.TestCase;

import org.junit.Before;

/**
 * 
 */

/**
 * @author sharms9
 * 
 */
public class FiltersDAOTest extends TestCase {

	public FiltersDAOTest(String name)
	{
		super(name);
	}
   
	@Before
	public void setUp() throws Exception
	{
		super.setUp();
		
     }

    /*
     * (non-Javadoc)
     * 
     * @see junit.framework.TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test method for {@link FiltersDAO#getSiteFilterList(HttpSession)}.
     */
    public void testGetSiteFilterList() throws Exception {
        try {
            FiltersDAO fDao = new FiltersDAO();
            List siteList = new ArrayList();
            siteList.add("All");
            String patientId = "00-00-00-0";
            String gridId = "2.16.840.1.113883.19";
            fDao.executeSiteFilterQuery(patientId, gridId, siteList);
            assertNotNull("The filter list cannot be null", siteList);
            assertEquals(1,siteList.size());
        } finally {

        }
    }

    /**
     * Test method for {@link FiltersDAO#getLabTestFilterList(HttpSession)}.
     */
    public void testGetLabTestFilterList() throws Exception {
        try {
            List labTestList = new ArrayList();
            labTestList.add("All");
            FiltersDAO fDao = new FiltersDAO();
            String studyId = "SMOKE_TEST";
            String patientId = "00-00-00-0";
            fDao.executeLabTestFilter(studyId, patientId, labTestList);
            assertNotNull("The filter list cannot be null", labTestList);
            assertEquals(2,labTestList.size());
        } finally {
        }
    }

}
