/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */
package gov.nih.nci.caxchange.ctom.viewer.actions;

import gov.nih.nci.caxchange.ctom.viewer.DAO.ParticipantSearchDAO;
import gov.nih.nci.caxchange.ctom.viewer.constants.DisplayConstants;
import gov.nih.nci.caxchange.ctom.viewer.constants.ForwardConstants;
import gov.nih.nci.caxchange.ctom.viewer.forms.LoginForm;
import gov.nih.nci.caxchange.ctom.viewer.forms.ParticipantSearchForm;
import gov.nih.nci.caxchange.ctom.viewer.util.CommonUtil;
import gov.nih.nci.caxchange.ctom.viewer.viewobjects.ParticipantSearchResult;
import gov.nih.nci.caxchange.ctom.viewer.viewobjects.SearchResult;
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
 * This class performs the participant search action. The search action calls
 * the searchObjects method with the user entered search criteria and queries
 * the database. If the search returns a non null result set; it forwards the
 * user to participant search page and displays the results.
 * 
 * @author asharma
 */
public class ParticipantSearchAction extends DispatchAction
{
	private static final Logger logDB =
			Logger.getLogger(ParticipantSearchAction.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.struts.action.Action#execute(org.apache.struts.action.ActionMapping,
	 *      org.apache.struts.action.ActionForm,
	 *      javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public ActionForward doParticipantSearch(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
	{
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();

		// gets the session object from HttpRequest
		HttpSession session = request.getSession();

		// search form
		ParticipantSearchForm pForm = (ParticipantSearchForm) form;

		UserInfoHelper.setUserInfo(((LoginForm) session
				.getAttribute(DisplayConstants.LOGIN_OBJECT)).getLoginId(),
				session.getId());
		try
		{
			SearchResult searchResult = new SearchResult();
			ParticipantSearchDAO participantDAO = new ParticipantSearchDAO();

			// search based on the given search criteria
			searchResult =
					participantDAO.searchObjects(mapping, pForm, request,
							errors, messages);
			pForm.setParticipantsList(searchResult.getSearchResultObjects());
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
									+ pForm.getFormName()
									+ "|search|Failure|No Records found for "
									+ pForm.getFormName() + " object|"
									+ form.toString() + "|");
				return (mapping
						.findForward(ForwardConstants.LOAD_PART_SEARCH_SUCCESS));
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

//			session.setAttribute(DisplayConstants.SEARCH_RESULT_PART,
//					searchResult);
			//session.setAttribute("participantsList", pForm.getParticipantsList());
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
								.getLoginId() + "|" + pForm.getFormName()
						+ "|search|Failure|Error Searching the "
						+ pForm.getFormName() + " object|" + form.toString()
						+ "|" + cse.getMessage());
		}
		catch (Exception e)
		{
			logDB.error(e.getMessage());
		}

		// if search result is not null; forward to searchresults page
		session.setAttribute(DisplayConstants.CURRENT_FORM, pForm);
		if (logDB.isDebugEnabled())
			logDB.debug(session.getId()
					+ "|"
					+ ((LoginForm) session
							.getAttribute(DisplayConstants.LOGIN_OBJECT))
							.getLoginId() + "|" + pForm.getFormName()
					+ "|search|Success|Success in searching "
					+ pForm.getFormName() + " object|" + form.toString() + "|");

		return (mapping.findForward(ForwardConstants.LOAD_PART_SEARCH_SUCCESS));
	}

	/**
	 * Forwards the control to the Lab Search page.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward loadLabs(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		// gets the session object from HttpRequest
		HttpSession session = request.getSession();
		
		//clear the session attributes with previous lab data
		CommonUtil util = new CommonUtil();
		util.clearLabSessionData(session);

		// search form
		ParticipantSearchForm pForm = (ParticipantSearchForm) form;
		List<ParticipantSearchResult> participantsList =
				pForm.getParticipantsList();
		int index = Integer.parseInt(pForm.getIndex()) - 1;
		if (participantsList != null && !participantsList.isEmpty())
			{
			    ParticipantSearchResult psr = participantsList.get(index);
				String participantTitle = psr.getFirstName()+" " +psr.getLastName() + " [" + psr.getPatientId()+ "]";
				session.setAttribute("participantTitle",participantTitle);
				// change the title to include patient information
				String titleString =
						session.getAttribute("studyTitle") + " :: "+ participantTitle;
				session.setAttribute("participantId", psr.getGridId());
				session.setAttribute("patientId", psr.getPatientId());
				session.setAttribute("pageTitle", titleString);
				pForm.setIndex("");
				session.setAttribute(DisplayConstants.CURRENT_TABLE_ID,
						DisplayConstants.LABACTIVITES_ID);
				return (mapping
						.findForward(ForwardConstants.LOAD_SEARCH_SUCCESS));
			}
		
		return (mapping.findForward(ForwardConstants.LOAD_STUDY_SEARCH_SUCCESS));
	}

}
