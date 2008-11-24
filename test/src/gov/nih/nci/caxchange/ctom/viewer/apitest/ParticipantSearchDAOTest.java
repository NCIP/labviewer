/**
 * 
 */
package gov.nih.nci.caxchange.ctom.viewer.apitest;

import static org.junit.Assert.assertNotNull;
import gov.nih.nci.caxchange.ctom.viewer.DAO.ParticipantSearchDAO;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author sharms9
 *
 */
public class ParticipantSearchDAOTest {

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
     * Test method for {@link gov.nih.nci.caxchange.ctom.viewer.DAO.ParticipantSearchDAO#executeQuery(java.lang.String, java.lang.String, java.lang.String)}.
     */
    @Test
    public void testExecuteQuery() throws Exception {
        ParticipantSearchDAO psDAO = new ParticipantSearchDAO();
        String studyId = "SMOKE_TEST";
        String phrase = "test";
        String searchTerm = "hello";
        List results = psDAO.executeQuery(searchTerm, studyId, phrase);
        assertNotNull("The filter list cannot be null", results);
    }

}
