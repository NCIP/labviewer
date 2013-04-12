/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */
package gov.nih.nci.caxchange.ctom.viewer.actions;

import gov.nih.nci.caxchange.ctom.viewer.constants.DisplayConstants;
import gov.nih.nci.caxchange.ctom.viewer.constants.ForwardConstants;
import gov.nih.nci.caxchange.ctom.viewer.forms.LoginForm;
import gov.nih.nci.caxchange.ctom.viewer.forms.StudySearchForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * This class performs the study search page setup action. The action loads the
 * study search page after login information is authenticated. if authentication
 * fails it redirects the user to login page to enter valid login information.
 * 
 * @author asharma
 */
public class LoadStudySearchAction extends Action
{

	private static final Logger logDB =
			Logger.getLogger(LoadStudySearchAction.class);

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
		HttpSession session = request.getSession();
		StudySearchForm baseDBForm = (StudySearchForm) form;

		String gridId = null;
		String gridProxy = null;

		// gets the login object from the session
		LoginForm fm =
				(LoginForm) request.getSession().getAttribute(
						DisplayConstants.LOGIN_OBJECT);
		if (fm == null)
		{
			gridId = (String) request.getParameter("gridId");
			gridProxy = (String) request.getParameter("gridProxy");
		}
		else
		{
			gridId = fm.getLoginId();
			gridProxy = fm.getGridProxy();
		}

		if (gridId != null && gridId.trim().length() > 0)
		{
			// validate the gridId
			LoginForm loginForm = new LoginForm();
			if (fm != null)
			{
				loginForm.setGridProxy(fm.getGridProxy());
				loginForm.setLoginId(fm.getLoginId());

			}
			else
			{
				loginForm = new LoginForm();
				loginForm.setLoginId(gridId);

				if (gridProxy != null && gridProxy.length() > 0)
					loginForm.setGridProxy(gridProxy);
			}
			session = request.getSession(true);
			session.setAttribute(DisplayConstants.LOGIN_OBJECT, loginForm);
			session.setAttribute(DisplayConstants.CURRENT_TABLE_ID,
					DisplayConstants.STUDYSEARCH_ID);
		}
		else
		{ // if the session is new or the login object is null; redirects the
			// user to login page
			if (session.isNew()
					|| (session.getAttribute(DisplayConstants.LOGIN_OBJECT) == null))
			{
				if (logDB.isDebugEnabled())
					logDB
							.debug("||"
									+ baseDBForm.getFormName()
									+ "|loadSearch|Failure|No Session or User Object Forwarding to the Login Page||");
				return mapping.findForward(ForwardConstants.LOGIN_PAGE);
			}
		}
		session.setAttribute(DisplayConstants.CURRENT_ACTION,
				DisplayConstants.STUDYSEARCH_ID);
		session.setAttribute(DisplayConstants.CURRENT_FORM, baseDBForm);

		// if the login is valid; loads the search page
		if (logDB.isDebugEnabled())
			logDB
					.debug(session.getId()
							+ "|"
							+ ((LoginForm) session
									.getAttribute(DisplayConstants.LOGIN_OBJECT))
									.getLoginId()
							+ "|"
							+ baseDBForm.getFormName()
							+ "|loadStudySearch|Success|Loading the Study Search Page||");

		return (mapping.findForward(ForwardConstants.LOAD_STUDY_SEARCH_SUCCESS));
	}

}
