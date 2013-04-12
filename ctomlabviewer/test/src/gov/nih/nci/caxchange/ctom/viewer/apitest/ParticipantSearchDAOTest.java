/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */
package gov.nih.nci.caxchange.ctom.viewer.apitest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gov.nih.nci.caxchange.ctom.viewer.DAO.ParticipantSearchDAO;
import gov.nih.nci.caxchange.ctom.viewer.constants.ForwardConstants;
import gov.nih.nci.caxchange.ctom.viewer.forms.ParticipantSearchForm;

import java.io.File;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import servletunit.struts.MockStrutsTestCase;

/**
 * @author sharms9
 *
 */
public class ParticipantSearchDAOTest extends MockStrutsTestCase {
	 ParticipantSearchDAO psDAO;
	/**
	 * Instantiates a new participant search dao test.
	 * 
	 * @param name the name
	 */
	public ParticipantSearchDAOTest(String name)
	{
		super(name);
	}
    @Before
	public void setUp() throws Exception
	{
		super.setUp();
	    
	}
      
    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }

    /**
     * Test method for {@link gov.nih.nci.caxchange.ctom.viewer.DAO.ParticipantSearchDAO#executeQuery(java.lang.String, java.lang.String, java.lang.String)}.
     */
    @Test
    public void testExecuteQuery() throws Exception {
        psDAO = new ParticipantSearchDAO();
        String studyId = "SMOKE_TEST";
        String phrase = "test";
        String searchTerm = "joe";
        List results = psDAO.executeQuery(searchTerm, studyId, phrase);
        assertNotNull("The filter list cannot be null", results);
        assertEquals(1,results.size());
    }

}
