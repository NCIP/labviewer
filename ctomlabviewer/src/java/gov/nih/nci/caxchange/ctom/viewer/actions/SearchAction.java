/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */
package gov.nih.nci.caxchange.ctom.viewer.actions;

import gov.nih.nci.caxchange.ctom.viewer.DAO.LabSearchDAO;
import gov.nih.nci.caxchange.ctom.viewer.constants.DisplayConstants;
import gov.nih.nci.caxchange.ctom.viewer.constants.ForwardConstants;
import gov.nih.nci.caxchange.ctom.viewer.forms.LabActivitiesSearchForm;
import gov.nih.nci.caxchange.ctom.viewer.forms.LoginForm;
import gov.nih.nci.caxchange.ctom.viewer.util.DateUtility;
import gov.nih.nci.caxchange.ctom.viewer.viewobjects.LabActivityResult;
import gov.nih.nci.caxchange.ctom.viewer.viewobjects.NumericResultFilter;
import gov.nih.nci.caxchange.ctom.viewer.viewobjects.SearchResult;
import gov.nih.nci.logging.api.user.UserInfoHelper;
import gov.nih.nci.security.exceptions.CSException;

import java.util.ArrayList;
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
 * This class performs the search action. The search action calls the
 * searchObjects method with the user entered search criteria and queries the
 * database. If the search returns a non null result set; it forwards the user
 * to search results page and displays the results.
 * 
 * @author asharma
 */
public class SearchAction extends DispatchAction
{
	private static final Logger logDB = Logger.getLogger(SearchAction.class);
	private static final String CONFIG_FILE = "/baseURL.properties";

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
	{
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();

		// gets the session object from HttpRequest
		HttpSession session = request.getSession();

		// search form
		LabActivitiesSearchForm lForm = (LabActivitiesSearchForm) form;
		UserInfoHelper.setUserInfo(((LoginForm) session
				.getAttribute(DisplayConstants.LOGIN_OBJECT)).getLoginId(),
				session.getId());
		try
		{
			SearchResult searchResult = new SearchResult();
			List filteredSearchResult = new ArrayList();
			// search based on the given search criteria
			LabSearchDAO labSearch = new LabSearchDAO();
			if (request.getSession().getAttribute("ALL_SEARCH_RESULT") == null)
			{
				SearchResult allSearchResult = new SearchResult();
				allSearchResult =
						labSearch.searchObjects(mapping, lForm, request,
								errors, messages);
				session.setAttribute(DisplayConstants.ALL_SEARCH_RESULT,
						allSearchResult.getSearchResultObjects());
				searchResult = allSearchResult;
			}
			else
			{
				SearchResult retreivedResults = new SearchResult();
				retreivedResults.setSearchResultObjects((List) request.getSession().getAttribute(
								"ALL_SEARCH_RESULT"));
				searchResult.setSearchResultObjects(retreivedResults
						.getSearchResultObjects());
			}
			// filter the results based on the user selected criteria
			filteredSearchResult =
					queryResult(lForm, searchResult.getSearchResultObjects());
			if (!filteredSearchResult.isEmpty())
				// set the filtered results back into search result.
				searchResult.setSearchResultObjects(filteredSearchResult);
			else
				// if the filter returns no results set null into search result.
				searchResult.setSearchResultObjects(null);

			// if the search returned nothing/null; display message
			if (searchResult.getSearchResultObjects() == null
					|| searchResult.getSearchResultObjects().isEmpty())
			{
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
						DisplayConstants.ERROR_ID,
						"The search criteria returned zero results"));
				saveErrors(request, errors);
				if (logDB.isDebugEnabled())
					logDB
							.debug(session.getId()
									+ "|"
									+ ((LoginForm) session
											.getAttribute(DisplayConstants.LOGIN_OBJECT))
											.getLoginId() + "|"
									+ lForm.getFormName()
									+ "|search|Failure|No Records found for "
									+ lForm.getFormName() + " object|"
									+ form.toString() + "|");
				session.setAttribute(DisplayConstants.SEARCH_RESULT, null);
				return (mapping.findForward(ForwardConstants.SEARCH_FAILURE));
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

			session.setAttribute(DisplayConstants.SEARCH_RESULT, searchResult);
			session.setAttribute(DisplayConstants.SEARCH_RESULT_COUNT, searchResult.getSearchResultObjects().size());
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
								.getLoginId() + "|" + lForm.getFormName()
						+ "|search|Failure|Error Searching the "
						+ lForm.getFormName() + " object|" + form.toString()
						+ "|" + cse.getMessage());
		}
		catch (Exception e)
		{
			logDB.error(e.getMessage());
		}

		// if search result is not null; forward to searchresults page
		session.setAttribute(DisplayConstants.CURRENT_FORM, lForm);
		if (logDB.isDebugEnabled())
			logDB.debug(session.getId()
					+ "|"
					+ ((LoginForm) session
							.getAttribute(DisplayConstants.LOGIN_OBJECT))
							.getLoginId() + "|" + lForm.getFormName()
					+ "|search|Success|Success in searching "
					+ lForm.getFormName() + " object|" + form.toString() + "|");

		return (mapping.findForward(ForwardConstants.SEARCH_SUCCESS));
	}

	/**
	 * Queries the search results with the user selected filters.
	 * 
	 * @param lForm
	 * @param searchResult
	 * @return
	 */
	private List queryResult(LabActivitiesSearchForm lForm,
			List<LabActivityResult> searchResult)
	{
		List queryRes = new ArrayList();
		logDB.debug("the selected date range is "
				+ lForm.getSelectedDateRange());
		logDB.debug("the selected lab test is " + lForm.getSelectedLabTest());
		logDB.debug("the selected numeric result is "
				+ lForm.getSelectedNumericResult());
		logDB.debug("the selected site is " + lForm.getSelectedSite());
		// get the filters selected.
		String labTest = lForm.getSelectedLabTest();
		String site = lForm.getSelectedSite();
		String dateRange = lForm.getSelectedDateRange();
		String numericResult = lForm.getSelectedNumericResult();

		boolean cond1 = true;
		boolean cond2 = true;
		boolean cond3 = true;
		boolean cond4 = true;
		// serach in the results list for the selected filters
		for (LabActivityResult lar : searchResult)
		{
			// filters set to defaults return the results
			if (labTest.equals("All") && site.equals("All")
					&& dateRange.equals("All dates/times")
					&& numericResult.equals("All"))
			{
				return searchResult;
			}
			else
			{
				if (!labTest.equals("All"))
					cond1 = lar.getLabTestId().equals(labTest);
				if (!site.equals("All"))
					cond2 = lar.getSiteId().equals(site);
				if (!numericResult.equals("All"))
					cond3 = buildNumericResultQuery(numericResult, lar);
				if (!dateRange.equals("All dates/times"))
				{
					DateUtility dateUtil = new DateUtility();
					cond4 = dateUtil.buildDateRangeQuery(dateRange, lar, lForm);
				}
				if (cond1 && cond2 && cond3 && cond4)
				{
					queryRes.add(lar);
				}
			}

		}

		return queryRes;
	}

	/**
	 * Builds the numeric range query
	 * 
	 * @param numericResult
	 * @param session
	 */
	private boolean buildNumericResultQuery(String numericResult,
			LabActivityResult lar)
	{
		boolean cond = false;
		if (numericResult.equals(NumericResultFilter.IN_RANGE.getCode()))
			cond = lar.getIsAdverseEvent().equals(DisplayConstants.NO);
		else
			cond = lar.getIsAdverseEvent().equals(DisplayConstants.YES);
		return cond;
	}

}
