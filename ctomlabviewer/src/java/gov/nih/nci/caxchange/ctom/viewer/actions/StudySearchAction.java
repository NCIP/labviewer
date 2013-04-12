/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */
package gov.nih.nci.caxchange.ctom.viewer.actions;

import gov.nih.nci.caxchange.ctom.viewer.DAO.StudySearchDAO;
import gov.nih.nci.caxchange.ctom.viewer.constants.DisplayConstants;
import gov.nih.nci.caxchange.ctom.viewer.constants.ForwardConstants;
import gov.nih.nci.caxchange.ctom.viewer.forms.LoginForm;
import gov.nih.nci.caxchange.ctom.viewer.forms.StudySearchForm;
import gov.nih.nci.caxchange.ctom.viewer.util.CommonUtil;
import gov.nih.nci.caxchange.ctom.viewer.viewobjects.SearchResult;
import gov.nih.nci.caxchange.ctom.viewer.viewobjects.StudySearchResult;
import gov.nih.nci.logging.api.user.UserInfoHelper;
import gov.nih.nci.security.exceptions.CSException;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

/**
 * This class performs the study search action. The search action calls the
 * searchObjects method with the user entered search criteria and queries the
 * database. If the search returns a non null result set; it forwards the user
 * to study search page and displays the results.
 * 
 * @author asharma
 */
public class StudySearchAction extends DispatchAction
{
	private static final Logger logDB =
			Logger.getLogger(StudySearchAction.class);
	private static final String CONFIG_FILE = "/baseURL.properties";

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.struts.action.Action#execute(org.apache.struts.action.ActionMapping,
	 *      org.apache.struts.action.ActionForm,
	 *      javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public ActionForward doStudySearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();

		// gets the session object from HttpRequest
		HttpSession session = request.getSession();

		// search form
		StudySearchForm sForm = (StudySearchForm) form;

		UserInfoHelper.setUserInfo(((LoginForm) session
				.getAttribute(DisplayConstants.LOGIN_OBJECT)).getLoginId(),
				session.getId());
		try
		{
			StudySearchDAO studySearchDAO = new StudySearchDAO();
			// search based on the given search criteria
			SearchResult searchResult = studySearchDAO.getStudySearchResults(sForm.getStudyID(), sForm.getStudyTitle(), session);
			sForm.setStudiesList(searchResult.getSearchResultObjects());
			// if the search returned nothing/null; display message
			if (searchResult.getSearchResultObjects() == null
					|| searchResult.getSearchResultObjects().isEmpty())
			{
//				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
//						DisplayConstants.ERROR_ID,
//						"The search criteria returned zero results"));
//				saveErrors(request, errors);
				if (logDB.isDebugEnabled())
					logDB
							.debug(session.getId()
									+ "|"
									+ ((LoginForm) session
											.getAttribute(DisplayConstants.LOGIN_OBJECT))
											.getLoginId() + "|"
									+ sForm.getFormName()
									+ "|search|Failure|No Records found for "
									+ sForm.getFormName() + " object|"
									+ form.toString() + "|");
				return (mapping
						.findForward(ForwardConstants.LOAD_STUDY_SEARCH_SUCCESS));
			}

			// if search result is not null; forward to searchresults page
			if (searchResult.getSearchResultMessage() != null
					&& !(searchResult.getSearchResultMessage().trim()
							.equalsIgnoreCase("")))
			{
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						DisplayConstants.MESSAGE_ID, searchResult
								.getSearchResultMessage()));
				saveMessages(request, messages);
			}

			//session.setAttribute(DisplayConstants.SEARCH_RESULT_STUDY,
				//	searchResult);
			//session.setAttribute("studiesList", sForm.getStudiesList());
			
		}
		catch (CSException cse)
		{
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					DisplayConstants.ERROR_ID, cse.getMessage()));
			saveErrors(request, errors);
			if (logDB.isDebugEnabled())
				logDB.debug(session.getId()
						+ "|"
						+ ((LoginForm) session
								.getAttribute(DisplayConstants.LOGIN_OBJECT))
								.getLoginId() + "|" + sForm.getFormName()
						+ "|search|Failure|Error Searching the "
						+ sForm.getFormName() + " object|" + form.toString()
						+ "|" + cse.getMessage());
		}
		catch (Exception e)
		{
			logDB.error(e.getMessage());
		}

		// if search result is not null; forward to searchresults page
		session.setAttribute(DisplayConstants.CURRENT_FORM, sForm);
		if (logDB.isDebugEnabled())
			logDB.debug(session.getId()
					+ "|"
					+ ((LoginForm) session
							.getAttribute(DisplayConstants.LOGIN_OBJECT))
							.getLoginId() + "|" + sForm.getFormName()
					+ "|search|Success|Success in searching "
					+ sForm.getFormName() + " object|" + form.toString() + "|");

		return (mapping.findForward(ForwardConstants.LOAD_STUDY_SEARCH_SUCCESS));
	}

	/**
	 * Forwards the control to the Participant Search page
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward loadParticipant(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
	{
		// gets the session object from HttpRequest
		HttpSession session = request.getSession();
		
		//clear the session attributes with previous participant data
		CommonUtil util = new CommonUtil();
		util.clearParticipantSessionData(session);

		// search form
		StudySearchForm sForm = (StudySearchForm) form;
		List<StudySearchResult> studiesList = sForm.getStudiesList();
		int index = Integer.parseInt(sForm.getIndex()) - 1;
		if (studiesList != null && !studiesList.isEmpty())
			{
			StudySearchResult ssr =  studiesList.get(index);
				// change the title to include patient information
				String titleString =
						"Study: " + ssr.getStudyId() + " [" + ssr.getStudyId()
								+ "]";
				session.setAttribute("studyId", ssr.getStudyId());
				session.setAttribute("studyTitle", titleString);
				session.setAttribute("pageTitle", titleString);
				sForm.setIndex("");
				session.setAttribute(DisplayConstants.CURRENT_TABLE_ID,
						DisplayConstants.PARTICIPANTSEARCH_ID);
				return (mapping
						.findForward(ForwardConstants.LOAD_PART_SEARCH_SUCCESS));
			}
	
		return (mapping.findForward(ForwardConstants.LOAD_STUDY_SEARCH_SUCCESS));
	}
	/**
	 * Forwards the control to the Healthcare site details page
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward showHealthCareSite(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
	{
		// gets the session object from HttpRequest
		HttpSession session = request.getSession();

		// search form
		StudySearchForm sForm = (StudySearchForm) form;
		List<StudySearchResult> studiesList = sForm.getStudiesList();
		int index = Integer.parseInt(sForm.getIndex()) - 1;
		if (studiesList != null && !studiesList.isEmpty())
			{
			   StudySearchResult ssr =  studiesList.get(index);
				// change the title to include study information
				String titleString =
						"Study: " + ssr.getStudyId() + " [" + ssr.getStudyId()
								+ "]";
				session.setAttribute("studyId", ssr.getStudyId());
				session.setAttribute("ID", ssr.getId());
				session.setAttribute("pageTitle", titleString);
				sForm.setIndex("");
				session.setAttribute(DisplayConstants.CURRENT_TABLE_ID,
						DisplayConstants.STUDYSEARCH_ID);
				return (mapping
						.findForward(ForwardConstants.LOAD_HEALTHCARESITE_SUCCESS));
			}
		

		return (mapping.findForward(ForwardConstants.LOAD_STUDY_SEARCH_SUCCESS));
	}
	/**
	 * Forwards the control to the PI details page
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward showPI(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
	{
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();

		// gets the session object from HttpRequest
		HttpSession session = request.getSession();

		// search form
		StudySearchForm sForm = (StudySearchForm) form;
		List<StudySearchResult> studiesList = sForm.getStudiesList();
		int index = Integer.parseInt(sForm.getIndex())- 1;
		if (studiesList != null && !studiesList.isEmpty())
			{
			 StudySearchResult ssr =  studiesList.get(index);
				// change the title to include study information
				String titleString =
						"Study: " + ssr.getStudyId() + " [" + ssr.getStudyId()
								+ "]";
				session.setAttribute("studyId", ssr.getStudyId());
				session.setAttribute("ID", ssr.getId());
				session.setAttribute("pageTitle", titleString);
				sForm.setIndex("");
				session.setAttribute(DisplayConstants.CURRENT_TABLE_ID,
						DisplayConstants.STUDYSEARCH_ID);
				return (mapping
						.findForward(ForwardConstants.LOAD_PI_SUCCESS));
			}
		

		return (mapping.findForward(ForwardConstants.LOAD_STUDY_SEARCH_SUCCESS));
	}

}
