/*
* Copyright ScenPro, Inc and SemanticBits, LLC
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/labviewer/LICENSE.txt for details.
*/
package gov.nih.nci.lv.web.action;

import gov.nih.nci.cabig.ctms.suite.authorization.SuiteAuthorizationAccessException;
import gov.nih.nci.lv.util.LVConstants;

/**
 * This initial class, gets the value from the LabViewer System table and puts the value in the session.
 * @author NAmiruddin
 *
 */
public class HomeAction extends LabViewerAction {
    private static final long serialVersionUID = 1234573645L;
    /**
     * {@inheritDoc}
     */
    public String execute() throws Exception {

        try {
            labViewerSetup();
        } catch (SuiteAuthorizationAccessException sae) {
            setAttribute(LVConstants.FAILURE_MESSAGE, sae.getMessage());
            return "no_access";
        } catch (Exception sae) {
            setAttribute(LVConstants.FAILURE_MESSAGE, sae.getMessage());
            return "no_access";
        }
        String access = (String) getSessionAttr(LVConstants.ALLOW_ACCESS);
        if (access != null && access.equals("no")) {
            return "no_access";
        }
        return SUCCESS;
    }

}
