/**
 * Copyright Notice.  Copyright 2008  Scenpro, Inc (“caBIG™ Participant”).caXchange
 * was created with NCI funding and is part of the caBIG™ initiative. 
 * The software subject to this notice and license includes both human readable source code form and 
 * machine readable, binary, object code form (the “caBIG™ Software”).
 * This caBIG™ Software License (the “License”) is between caBIG™ Participant and You.  
 * “You (or “Your”) shall mean a person or an entity, and all other entities that control, 
 * are controlled by, or are under common control with the entity.  “Control” for purposes of this 
 * definition means (i) the direct or indirect power to cause the direction or management of such entity, 
 * whether by contract or otherwise, or (ii) ownership of fifty percent (50%) or more of the outstanding shares, 
 * or (iii) beneficial ownership of such entity.  
 * License.  Provided that You agree to the conditions described below, caBIG™ Participant grants 
 * You a non-exclusive, worldwide, perpetual, fully-paid-up, no-charge, irrevocable, transferable and 
 * royalty-free right and license in its rights in the caBIG™ Software, including any copyright or patent rights therein, to 
 * (i) use, install, disclose, access, operate, execute, reproduce, copy, modify, translate, market, publicly display, 
 * publicly perform, and prepare derivative works of the caBIG™ Software in any manner and for any purpose, and to have 
 * or permit others to do so; (ii) make, have made, use, practice, sell, and offer for sale, import, and/or otherwise 
 * dispose of caBIG™ Software (or portions thereof); (iii) distribute and have distributed to and by third parties the 
 * caBIG™ Software and any modifications and derivative works thereof; and (iv) sublicense the foregoing rights 
 * set out in (i), (ii) and (iii) to third parties, including the right to license such rights to further third parties.  
 * For sake of clarity, and not by way of limitation, caBIG™ Participant shall have no right of accounting or right of payment
 *  from You or Your sublicensees for the rights granted under this License.  This License is granted at no charge to You.  
 *  Your downloading, copying, modifying, displaying, distributing or use of caBIG™ Software constitutes acceptance of all 
 *  of the terms and conditions of this Agreement.  If you do not agree to such terms and conditions, you have no right to 
 *  download, copy, modify, display, distribute or use the caBIG™ Software.  
 * 1.	Your redistributions of the source code for the caBIG™ Software must retain the above copyright notice, 
 * 		this list of conditions and the disclaimer and limitation of liability of Article 6 below.  
 * 		Your redistributions in object code form must reproduce the above copyright notice, this list of conditions and 
 * 		the disclaimer of Article 6 in the documentation and/or other materials provided with the distribution, if any.
 * 2.	Your end-user documentation included with the redistribution, if any, must include the following acknowledgment: 
 * 		“This product includes software developed by Scenpro, Inc.”  
 * 		If You do not include such end-user documentation, You shall include this acknowledgment in the caBIG™ Software itself, 
 * 		wherever such third-party acknowledgments normally appear.
 * 3.	You may not use the names  “Scenpro, Inc”, 
 * 		“The National Cancer Institute”, “NCI”, “Cancer Bioinformatics Grid” or “caBIG™” to endorse or promote products 
 * 		derived from this caBIG™ Software.  This License does not authorize You to use any trademarks, service marks, trade names,
 * 		logos or product names of either caBIG™ Participant, NCI or caBIG™, except as required to comply with the terms of this 
 * 		License.
 * 4.	For sake of clarity, and not by way of limitation, You may incorporate this caBIG™ Software into Your proprietary 
 * 		programs and into any third party proprietary programs.  However, if You incorporate the caBIG™ Software into third party 
 * 		proprietary programs, You agree that You are solely responsible for obtaining any permission from such third parties 
 * 		required to incorporate the caBIG™ Software into such third party proprietary programs and for informing Your sublicensees, 
 * 		including without limitation Your end-users, of their obligation to secure any required permissions from such third parties 
 * 		before incorporating the caBIG™ Software into such third party proprietary software programs.  In the event that You fail to 
 * 		obtain such permissions, You agree to indemnify caBIG™ Participant for any claims against caBIG™ Participant by such third 
 * 		parties, except to the extent prohibited by law, resulting from Your failure to obtain such permissions.
 * 5.	For sake of clarity, and not by way of limitation, You may add Your own copyright statement to Your modifications and 
 * 		to the derivative works, and You may provide additional or different license terms and conditions in Your sublicenses of 
 * 		modifications of the caBIG™ Software, or any derivative works of the caBIG™ Software as a whole, provided Your use, reproduction, 
 * 		and distribution of the Work otherwise complies with the conditions stated in this License.
 * 6.	THIS caBIG™ SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED WARRANTIES (INCLUDING, BUT NOT LIMITED TO, 
 * 		THE IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED.  
 * 		IN NO EVENT SHALL THE Scenpro, Inc OR ITS AFFILIATES 
 * 		BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * 		PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON 
 * 		ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY 
 * 		OUT OF THE USE OF THIS caBIG™ SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * 
 */

package gov.nih.nci.caxchange.ctom.viewer.actions;

import gov.nih.nci.caxchange.ctom.viewer.DAO.LabSearchDAO;
import gov.nih.nci.caxchange.ctom.viewer.constants.DisplayConstants;
import gov.nih.nci.caxchange.ctom.viewer.constants.ForwardConstants;
import gov.nih.nci.caxchange.ctom.viewer.forms.LabActivitiesSearchForm;
import gov.nih.nci.caxchange.ctom.viewer.forms.LoginForm;
import gov.nih.nci.caxchange.ctom.viewer.util.DateUtility;
import gov.nih.nci.caxchange.ctom.viewer.viewobjects.DateRangeFilter;
import gov.nih.nci.caxchange.ctom.viewer.viewobjects.LabActivityResult;
import gov.nih.nci.caxchange.ctom.viewer.viewobjects.NumericResultFilter;
import gov.nih.nci.caxchange.ctom.viewer.viewobjects.SearchResult;
import gov.nih.nci.logging.api.user.UserInfoHelper;
import gov.nih.nci.security.exceptions.CSException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

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
 * This class performs the search action. The search action calls the searchObjects method with
 * the user entered search criteria and queries the database.
 * If the search returns a non null result set; it forwards the user to search results page and
 * displays the results.
 * @author asharma
 * 
 */
public class SearchAction extends DispatchAction
{
	private static final Logger logDB = Logger.getLogger(SearchAction.class);
	private static final String CONFIG_FILE = "/baseURL.properties";

	/* (non-Javadoc)
	 * @see org.apache.struts.action.Action#execute(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		
        //gets the session object from HttpRequest 
		HttpSession session = request.getSession();
		
        //search form
		LabActivitiesSearchForm lForm =(LabActivitiesSearchForm) form;
        UserInfoHelper.setUserInfo(((LoginForm) session
				.getAttribute(DisplayConstants.LOGIN_OBJECT)).getLoginId(),
				session.getId());
		try
		{
			SearchResult searchResult = new SearchResult();
			List filteredSearchResult = new ArrayList();
			//search based on the given search criteria
			LabSearchDAO labSearch = new LabSearchDAO();
			if(request.getSession().getAttribute("ALL_SEARCH_RESULT")==null)
			{	
			  searchResult = labSearch.searchObjects(mapping, lForm, request, errors, messages);
			  session.setAttribute(DisplayConstants.ALL_SEARCH_RESULT, searchResult);
			}
			else{
				SearchResult retreivedResults = (SearchResult)request.getSession().getAttribute("ALL_SEARCH_RESULT");
				searchResult.setSearchResultObjects(retreivedResults.getSearchResultObjects());
			}
			//filter the results based on the user selected criteria
			filteredSearchResult = queryResult(lForm,searchResult.getSearchResultObjects());
			if(!filteredSearchResult.isEmpty())
				//set the filtered results back into search result.
				searchResult.setSearchResultObjects(filteredSearchResult);
			else
				//if the filter returns no results set null into search result.
				searchResult.setSearchResultObjects(null);
					
		    //if the search returned nothing/null; display message  	
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
			
			//if search result is not null; forward to searchresults page
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
		
		//if search result is not null; forward to searchresults page
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
	 * @param lForm
	 * @param searchResult
	 * @return
	 */
	private List queryResult(LabActivitiesSearchForm lForm,List<LabActivityResult>searchResult)
	{
	 List queryRes = new ArrayList();
	 logDB.debug("the selected date range is " +lForm.getSelectedDateRange());
	 logDB.debug("the selected lab test is " +lForm.getSelectedLabTest());
	 logDB.debug("the selected numeric result is " +lForm.getSelectedNumericResult());
	 logDB.debug("the selected site is " +lForm.getSelectedSite());
	 //get the filters selected.
	 String labTest =lForm.getSelectedLabTest();
	 String site=lForm.getSelectedSite();
	 String dateRange=lForm.getSelectedDateRange();
	 String numericResult = lForm.getSelectedNumericResult();
	 
	 boolean cond1 = true;
	 boolean cond2 = true;
	 boolean cond3 = true;
	 boolean cond4 = true;
	//serach in the results list for the selected filters 
	 for(LabActivityResult lar: searchResult)
	 {   
		 //filters set to defaults return the results
		 if(labTest.equals("All") && site.equals("All") && dateRange.equals("All dates/times") && numericResult.equals("All"))
		 {
			 return searchResult;
		 }
		 else
		 {
		  if(!labTest.equals("All"))
			   cond1 = lar.getLabTestId().equals(labTest);
		  if(!site.equals("All"))
			  cond2  = lar.getSiteId().equals(site);
		  if(!numericResult.equals("All"))
		     cond3 = buildNumericResultQuery(numericResult,lar);
		  if(!dateRange.equals("All dates/times"))
		  {
			  DateUtility dateUtil = new DateUtility(); 
			  cond4= dateUtil.buildDateRangeQuery(dateRange,lar,lForm);
		  }
		  if(cond1 && cond2 && cond3 && cond4)
		  {
			  queryRes.add(lar);
		  }
		 }  
		 
	 }
	 
	 return queryRes;
	}
	
	/** 
	 * Builds the numeric range query
	 * @param numericResult
	 * @param session
	 */
	private boolean buildNumericResultQuery(String numericResult,LabActivityResult lar){
		boolean cond=false;
		if(numericResult.equals(NumericResultFilter.IN_RANGE.getCode()))
				cond = lar.getIsAdverseEvent().equals(DisplayConstants.NO);
			else
				cond = lar.getIsAdverseEvent().equals(DisplayConstants.YES);
		return cond;
	}
	
}

