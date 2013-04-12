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
import gov.nih.nci.caxchange.ctom.viewer.forms.MenuForm;
import gov.nih.nci.caxchange.ctom.viewer.util.CommonUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Kunal Modi (Ekagra Software Technologies Ltd.) TODO To change the
 *         template for this generated type comment go to Window - Preferences -
 *         Java - Code Style - Code Templates
 */
public class MenuSelectionAction extends Action
{

	private static final Logger log =
			Logger.getLogger(MenuSelectionAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		/* perform login task */
		HttpSession session = request.getSession();
		MenuForm menuSelectionForm = (MenuForm) form;

		if (session.isNew()
				|| (session.getAttribute(DisplayConstants.LOGIN_OBJECT) == null))
		{
			if (log.isDebugEnabled())
				log
						.debug("||||Failure|No Session or User Object Forwarding to the Login Page||");
			return mapping.findForward(ForwardConstants.LOGIN_PAGE);
		}

		//clear the session attributes 
		CommonUtil util = new CommonUtil();
		util.clearMenuSessionData(session);

		session.setAttribute(DisplayConstants.CURRENT_TABLE_ID,
				menuSelectionForm.getTableId());

		if (log.isDebugEnabled())
			log.debug(session.getId()
					+ "|"
					+ ((LoginForm) session
							.getAttribute(DisplayConstants.LOGIN_OBJECT))
							.getLoginId() + "|"
					+ menuSelectionForm.getTableId()
					+ "|Forward|Success|Forwarding to the "
					+ menuSelectionForm.getTableId() + " Home Page||");

		if (menuSelectionForm.getTableId().equalsIgnoreCase(
				DisplayConstants.HOME_ID))
			return (mapping.findForward(ForwardConstants.HOME_PAGE));
		if (menuSelectionForm.getTableId().equalsIgnoreCase(
				DisplayConstants.ADMIN_HOME_ID))
			return (mapping.findForward(ForwardConstants.ADMIN_HOME_PAGE));
		else if (menuSelectionForm.getTableId().equalsIgnoreCase(
				DisplayConstants.LABACTIVITES_ID))
			// return
			// (mapping.findForward(ForwardConstants.LABACTIVITIES_HOME_PAGE));
			return (mapping.findForward(ForwardConstants.LOAD_SEARCH_SUCCESS));
		else if (menuSelectionForm.getTableId().equalsIgnoreCase(
				DisplayConstants.STUDYSEARCH_ID))
			return (mapping
					.findForward(ForwardConstants.LOAD_STUDY_SEARCH_SUCCESS));
		else if (menuSelectionForm.getTableId().equalsIgnoreCase(
				DisplayConstants.PARTICIPANTSEARCH_ID))
			return (mapping
					.findForward(ForwardConstants.LOAD_PART_SEARCH_SUCCESS));
		else if (menuSelectionForm.getTableId().equalsIgnoreCase(
				DisplayConstants.TEST_ID))
			return (mapping.findForward(ForwardConstants.LOAD_TEST));
		else if (menuSelectionForm.getTableId().equalsIgnoreCase(
				DisplayConstants.ADMIN_ID))
			return (mapping.findForward(ForwardConstants.LOAD_ADMIN_SUCCESS));
		else if (menuSelectionForm.getTableId().equalsIgnoreCase(
				DisplayConstants.USER_CONFG_ID))
			return (mapping.findForward(ForwardConstants.LOAD_USERS_SUCCESS));
		else if (menuSelectionForm.getTableId().equalsIgnoreCase(
				DisplayConstants.LOGOUT_ID))
			return (mapping.findForward(ForwardConstants.LOGOUT_ACTION));
		else
			return (mapping.findForward(ForwardConstants.HOME_PAGE));

	}

}
