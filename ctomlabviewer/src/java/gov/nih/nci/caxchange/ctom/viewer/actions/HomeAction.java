/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */
package gov.nih.nci.caxchange.ctom.viewer.actions;

import gov.nih.nci.cabig.ctms.suite.authorization.SuiteAuthorizationAccessException;
import gov.nih.nci.cabig.ctms.suite.authorization.SuiteRole;
import gov.nih.nci.caxchange.ctom.viewer.constants.DisplayConstants;
import gov.nih.nci.caxchange.ctom.viewer.constants.ForwardConstants;
import gov.nih.nci.caxchange.ctom.viewer.forms.LabActivitiesSearchForm;
import gov.nih.nci.caxchange.ctom.viewer.forms.LoginForm;
import gov.nih.nci.caxchange.ctom.viewer.util.CommonUtil;
import gov.nih.nci.caxchange.ctom.viewer.util.LabViewerAuthorizationHelper;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Kunal Modi (Ekagra Software Technologies Ltd.)
 */
public class HomeAction extends Action
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

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		ActionErrors errors = new ActionErrors();
		CommonUtil util = new CommonUtil();
		ActionForward forward = null;
		HttpSession session = request.getSession();

		LoginForm loginForm =
				(LoginForm) session.getAttribute(DisplayConstants.LOGIN_OBJECT);

		// check for the user login information
		String userEmail = util.checkUserLogin(session);

		if ((loginForm == null) && (userEmail == null))
		{
			log
					.debug("||||Failure|No Session or User Object Forwarding to the Login Page||");
			return mapping.findForward(ForwardConstants.LOGIN_PAGE);
		}

		/*
		 * clear the junk in the session here
		 */
		util.clearSessionData(session);

		session.setAttribute(DisplayConstants.CURRENT_TABLE_ID,
				DisplayConstants.HOME_ID);

		// If they got here by WebSSO then check authorization and set things up
		if (userEmail != null)
		{
			boolean loggedIn = true;
			loginForm = new LoginForm();

			String username = userEmail;
			loginForm.setLoginId(username);

			// Check their authorization
			LabViewerAuthorizationHelper authHelper = new LabViewerAuthorizationHelper();
			Set<SuiteRole> userRoles = null;
			try
			{
			    userRoles = authHelper.getUserRoles(username);
			    userRoles.remove(SuiteRole.USER_ADMINISTRATOR); // this is temporary until user provisioning in standalone mode is implemented
			    
			    if (!(userRoles.contains(SuiteRole.SYSTEM_ADMINISTRATOR) || 
					  userRoles.contains(SuiteRole.USER_ADMINISTRATOR) ||
					  userRoles.contains(SuiteRole.LAB_DATA_USER)))
				{
					log.error("User not authorized for LabViewer");
					loggedIn = false;
					forward = mapping.findForward(ForwardConstants.LOGIN_FAILURE);
				}
			}
			catch (SuiteAuthorizationAccessException e)
			{
				log.error("Error authorizing user for LabViewer: ", e);
				loggedIn = false;
				forward = mapping.findForward(ForwardConstants.LOGIN_FAILURE);
			}

			if (loggedIn)
			{
				loginForm.setGridProxy("test");
				// retrieve and set properties
				util.getProperties(session);
				session.setAttribute(DisplayConstants.LOGIN_OBJECT, loginForm);
				session.setAttribute(DisplayConstants.CURRENT_TABLE_ID,
						DisplayConstants.HOME_ID);
				
				session.setAttribute(DisplayConstants.USER_ROLES, userRoles);
				
				if (session.getAttribute("HOT_LINK") == "true")
				{
					LabActivitiesSearchForm labFm =
							(LabActivitiesSearchForm) session
									.getAttribute("CURRENT_FORM");
					session.setAttribute("CURRENT_FORM", labFm);
					return (mapping
							.findForward(ForwardConstants.LOGIN_SUCCESS_HOTLINK));
				}

				forward = mapping.findForward(ForwardConstants.LOGIN_SUCCESS);
			}
		}

		return forward;
	}

}
