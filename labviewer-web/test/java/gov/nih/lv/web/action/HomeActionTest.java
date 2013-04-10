/*
* Copyright ScenPro, Inc and SemanticBits, LLC
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/labviewer/LICENSE.txt for details.
*/

package gov.nih.lv.web.action;

import gov.nih.lv.web.AbstractActionTest;
import gov.nih.nci.lv.web.action.HomeAction;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.opensymphony.xwork2.ActionSupport;
/**
 * 
 * @author Naveen Amiruddin
 * Test class for home Action
 *
 */
public class HomeActionTest extends AbstractActionTest {
    
    HomeAction act = new HomeAction();
    
    /**
     * {@inheritDoc}
     */    
    @Test
    public void executeTest() throws Exception {
        act.prepare();
        assertEquals(ActionSupport.SUCCESS, act.execute());
    }

}
