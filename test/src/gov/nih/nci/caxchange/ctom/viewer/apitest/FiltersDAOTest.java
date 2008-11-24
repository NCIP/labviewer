package gov.nih.nci.caxchange.ctom.viewer.apitest;

import gov.nih.nci.caxchange.ctom.viewer.DAO.FiltersDAO;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import junit.framework.TestCase;

/**
 * 
 */

/**
 * @author sharms9
 * 
 */
public class FiltersDAOTest extends TestCase {

    /*
     * (non-Javadoc)
     * 
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
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
            String gridId = "91dd4580-801b-4874-adeb-a174361bacea";
            fDao.executeSiteFilterQuery(patientId, gridId, siteList);
            assertNotNull("The filter list cannot be null", siteList);
            // assert for expected number of records...

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

        } finally {
        }
    }

}
