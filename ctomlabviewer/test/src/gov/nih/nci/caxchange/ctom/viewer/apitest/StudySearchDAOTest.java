/**
 * 
 */
package gov.nih.nci.caxchange.ctom.viewer.apitest;

import static org.junit.Assert.assertNotNull;
import gov.nih.nci.caxchange.ctom.viewer.DAO.StudySearchDAO;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.ContextLoader;

/**
 * @author sharms9
 * 
 */
public class StudySearchDAOTest {

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
     * {@link gov.nih.nci.caxchange.ctom.viewer.DAO.StudySearchDAO#executeQuery(java.lang.String)}
     * .
     */
    @Test
    public void testExecuteQuery() throws Exception {
    	
        //Set up Spring context
    	StudySearchDAO ssDAO = new StudySearchDAO();
        String studyPhrase = "test";
        List results = ssDAO.executeQuery(studyPhrase);
        assertNotNull("The filter list cannot be null", results);
    }

}
