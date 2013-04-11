/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */
package gov.nih.nci.caxchange.ctom.viewer.apitest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gov.nih.nci.caxchange.ctom.viewer.DAO.LabSearchDAO;

import java.util.List;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author sharms9
 * 
 */
public class LabSearchDAOTest extends TestCase{

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }

    /**
     * Test method for
     * {@link gov.nih.nci.caxchange.ctom.viewer.DAO.LabSearchDAO#executeQuery(java.lang.String, java.lang.String)}
     * .
     */
    @Test
    public void testExecuteQuery() throws Exception {
        LabSearchDAO lsDAO = new LabSearchDAO();
        
        String studyId= "SMOKE_TEST";
        String patientId = "00-00-00-0";
        List results = lsDAO.executeQuery(studyId, patientId);
        //assertions and checks
        assertNotNull("The filter list cannot be null", results);
        assertEquals(1,results.size());
    }

}
