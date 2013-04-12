/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */
package gov.nih.nci.caxchange.ctom.viewer.actions;

import gov.nih.nci.caxchange.ctom.viewer.constants.DisplayConstants;
import gov.nih.nci.caxchange.ctom.viewer.constants.ForwardConstants;
import gov.nih.nci.caxchange.ctom.viewer.forms.LabActivitiesSearchForm;
import gov.nih.nci.caxchange.ctom.viewer.forms.LoginForm;
import gov.nih.nci.caxchange.ctom.viewer.util.CommonUtil;
import gov.nih.nci.caxchange.ctom.viewer.util.LabViewerAuthorizationHelper;

import org.apache.log4j.Logger;

/**
 * @author Lisa Kelley
 */
public class HomeAction extends LabViewerActionSupport
{
	private static final Logger log = Logger.getLogger(HomeAction.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.struts.action.Action#execute(org.apache.struts.action.ActionMapping,
	 *      org.apache.struts.action.ActionForm,
	 *      javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */

	public String execute()
	{
		LoginForm loginForm = (LoginForm) session.getAttribute(DisplayConstants.LOGIN_OBJECT);

		// check for the user login information
		CommonUtil util = new CommonUtil();
		String loginID = util.checkUserLogin(session);

		if (loginForm == null && loginID == null)
		{
			log.debug("||||Failure|No Session or User Object Forwarding to the Login Page||");
			return ForwardConstants.LOGIN_FAILURE;
		}

		util.clearSessionData(session);
		session.setAttribute(DisplayConstants.CURRENT_TABLE_ID, DisplayConstants.HOME_ID);

		// If they got here by WebSSO then check authorization and set things up
		if (loginID != null)
		{
			// Check their authorization
			LabViewerAuthorizationHelper lvaHelper = new LabViewerAuthorizationHelper();
			boolean authorized = lvaHelper.isAuthorized(loginID);

			if (!authorized)
			{
				log.error("User authenticated but not authorized");
				this.addActionError("User does not have permissions for this application");
				return ForwardConstants.LOGIN_FAILURE; // lisa - test this!
			}
			
			loginForm = new LoginForm();			
			loginForm.setLoginId(loginID);
			loginForm.setGridProxy("test");
			
			// retrieve and set properties
			util.getProperties(session);
			session.setAttribute(DisplayConstants.LOGIN_OBJECT, loginForm);
			
			if (session.getAttribute("HOT_LINK") == "true")
			{
				LabActivitiesSearchForm labFm = (LabActivitiesSearchForm) session.getAttribute("CURRENT_FORM"); // lisa - test if this line and the next line are really necessary?
				session.setAttribute("CURRENT_FORM", labFm);
				return ForwardConstants.LOGIN_SUCCESS_HOTLINK;
			}

			return ForwardConstants.LOGIN_SUCCESS;
		}

		return null; // lisa - figure out if this is correct?
	}

}
