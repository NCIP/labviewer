/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */
package gov.nih.nci.caxchange.ctom.viewer.actions;

import gov.nih.nci.caxchange.ctom.viewer.DAO.HealthCareSiteDAO;
import gov.nih.nci.caxchange.ctom.viewer.constants.DisplayConstants;
import gov.nih.nci.caxchange.ctom.viewer.constants.ForwardConstants;
import gov.nih.nci.caxchange.ctom.viewer.forms.HealthCareSiteForm;
import gov.nih.nci.caxchange.ctom.viewer.forms.LoginForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * This class loads the HealthCare Site details page after login information is
 * authenticated. If authentication fails it redirects the user to login page to
 * enter valid login information.
 * 
 * @author asharma
 */
public class LoadHealthCareSiteAction extends Action
{

	private static final Logger logDB = Logger.getLogger(LoadHealthCareSiteAction.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.struts.action.Action#execute(org.apache.struts.action.ActionMapping,
	 *      org.apache.struts.action.ActionForm,
	 *      javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{

		HttpSession session = request.getSession();
		HealthCareSiteForm baseDBForm = (HealthCareSiteForm) form;

		String gridId = null;
		String gridProxy = null;

		// gets the login object from the session
		LoginForm fm = (LoginForm) request.getSession().getAttribute(DisplayConstants.LOGIN_OBJECT);
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
			session
					.setAttribute(DisplayConstants.CURRENT_TABLE_ID,
							DisplayConstants.STUDYSEARCH_ID);
		}
		else
		{ // if the session is new or the login object is null; redirects the
			// user to login page
			if (session.isNew() || (session.getAttribute(DisplayConstants.LOGIN_OBJECT) == null))
			{
				if (logDB.isDebugEnabled())
					logDB
							.debug("||"
									+ baseDBForm.getFormName()
									+ "|loadSearch|Failure|No Session or User Object Forwarding to the Login Page||");
				return mapping.findForward(ForwardConstants.LOGIN_PAGE);
			}
		}
		// retrieve the Healthcaresite details
		retrieveHealthCareSiteDetails(request, baseDBForm);

		// Set the details in the session
		session.setAttribute(DisplayConstants.CURRENT_ACTION, DisplayConstants.STUDYSEARCH_ID);
		session.setAttribute(DisplayConstants.CURRENT_FORM, baseDBForm);
		//session.setAttribute("hcsList",baseDBForm.getHcsList());

		// if the login is valid; loads the search page
		if (logDB.isDebugEnabled())
			logDB.debug(session.getId()
					+ "|"
					+ ((LoginForm) session.getAttribute(DisplayConstants.LOGIN_OBJECT))
							.getLoginId() + "|" + baseDBForm.getFormName()
					+ "|loadHealthCareSite|Success|Loading the HealthCare Site Page||");

		return (mapping.findForward(ForwardConstants.LOAD_HEALTHCARESITE_SUCCESS));
	}

	/**
	 * @param session
	 * @param baseDBForm
	 */
	private void retrieveHealthCareSiteDetails(HttpServletRequest request,
			HealthCareSiteForm baseDBForm) throws Exception
	{

		// perform a call to the COPPA Service to retrieve the latest
		// organization information
		// if the call is successful and returns results - persist them in the
		// database and display the details to the user
		// if the call fails - retrieve the details currently stored in the
		// database and display the details to the user
		HealthCareSiteDAO hcsDAO = new HealthCareSiteDAO();
		//hcsDAO.retrieveHealthCareSite(request, baseDBForm); // lisa - commented out because of code change!
		/*
		 * baseDBForm.setName("NCI"); baseDBForm.setEmail("email@nci.nih.gov");
		 * baseDBForm.setPhone("111-222-3333"); baseDBForm.setAddress("101,
		 * Renner rd, Richardson");
		 */}

}
