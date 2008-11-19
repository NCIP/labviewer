/**
 * Copyright Notice.  Copyright 2008  Scenpro, Inc (“caBIG™ Participant”).  caXchange 
 * [insert name of caBIG™ software program] was created with NCI funding and is part of the caBIG™ initiative. 
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

import gov.nih.nci.caxchange.ctom.viewer.DAO.FiltersDAO;
import gov.nih.nci.caxchange.ctom.viewer.constants.DisplayConstants;
import gov.nih.nci.caxchange.ctom.viewer.constants.ForwardConstants;
import gov.nih.nci.caxchange.ctom.viewer.forms.LabActivitiesSearchForm;
import gov.nih.nci.caxchange.ctom.viewer.forms.LoginForm;
import gov.nih.nci.caxchange.ctom.viewer.viewobjects.DateRangeFilter;
import gov.nih.nci.caxchange.ctom.viewer.viewobjects.NumericResultFilter;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * This class performs the search page setup action. The action loads the search page 
 * after login information is authenticated. 
 * if authentication fails it redirects the user to login page to enter valid login information.
 * @author asharma
 *
 */
public class LoadSearchSetupAction extends Action{
	
	private static final Logger logDB = Logger.getLogger(LoadSearchSetupAction.class);
	
	/* (non-Javadoc)
	 * @see org.apache.struts.action.Action#execute(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		HttpSession session = request.getSession();
		LabActivitiesSearchForm baseDBForm = (LabActivitiesSearchForm)form;
		
		String gridId = null;
		String gridProxy = null;
		
		//gets the login object from the session
		LoginForm fm = (LoginForm)request.getSession().getAttribute(DisplayConstants.LOGIN_OBJECT);
		if(fm == null){
			gridId = (String)request.getParameter("gridId");
			gridProxy = (String)request.getParameter("gridProxy");
		} else {
			gridId = fm.getLoginId();
			gridProxy = fm.getGridProxy();
		}
			
			
		if (gridId != null && gridId.trim().length() > 0)
		{
			// validate the gridId
			LoginForm loginForm = new LoginForm();
			if(fm != null){
				loginForm.setGridProxy(fm.getGridProxy());
				loginForm.setLoginId(fm.getLoginId());
				
			} else {
				loginForm = new LoginForm();
				loginForm.setLoginId(gridId);
			
				if(gridProxy != null && gridProxy.length() > 0)
					loginForm.setGridProxy(gridProxy);
			}
			session = request.getSession(true);
			session.setAttribute(DisplayConstants.LOGIN_OBJECT,loginForm);
			session.setAttribute(DisplayConstants.CURRENT_TABLE_ID,DisplayConstants.LABACTIVITES_ID);
		}
		else
		{   //if the session is new or the login object is null; redirects the user to login page  
			if (session.isNew() || (session.getAttribute(DisplayConstants.LOGIN_OBJECT) == null)) {
				if (logDB.isDebugEnabled())
					logDB.debug("||"+baseDBForm.getFormName()+"|loadSearch|Failure|No Session or User Object Forwarding to the Login Page||");
				return mapping.findForward(ForwardConstants.LOGIN_PAGE);
			}
			//resets the search page form
			baseDBForm.resetForm();
		}
		
		populateFilters(baseDBForm,session);
		session.setAttribute(DisplayConstants.CURRENT_ACTION, DisplayConstants.SEARCH);
		session.setAttribute(DisplayConstants.CURRENT_FORM, baseDBForm);
		 
		//if the login is valid; loads the search page
		if (logDB.isDebugEnabled())
			logDB.debug(session.getId()+"|"+((LoginForm)session.getAttribute(DisplayConstants.LOGIN_OBJECT)).getLoginId()+
					"|"+baseDBForm.getFormName()+"|loadSearch|Success|Loading the Search Page||");
		//populate the filters
		return (mapping.findForward(ForwardConstants.LOAD_SEARCH_SUCCESS));
	}
   
	
	/**
	 * @param form
	 */
	public void populateFilters(LabActivitiesSearchForm form,HttpSession session)
	{
		populateLabFilter(form,session);
		populateSiteFilter(form,session);
		populateNumericResult(form,session);
		populateDateRange(form,session);
		
	}
	
	/**
	 * Populate the Lab Filter drop down list
	 * @param form
	 */
	private void populateLabFilter(LabActivitiesSearchForm form,HttpSession session){
		List<String> labTestFilter = new ArrayList<String>(); 
		FiltersDAO filterDAO = new FiltersDAO();
		try {
			labTestFilter =(ArrayList)filterDAO.getLabTestFilterList(session);
		} catch (Exception e) {
			logDB.error(e.getLocalizedMessage());
		}
		form.setLabTestFilter(labTestFilter);
	}
	
	/**
	 * Populate the Site Filter drop down list
	 * @param form
	 */
	private void populateSiteFilter(LabActivitiesSearchForm form,HttpSession session){
		List<String> siteFilter = new ArrayList<String>(); 
		FiltersDAO filterDAO = new FiltersDAO();
		try {
			siteFilter =(ArrayList)filterDAO.getSiteFilterList(session);
		} catch (Exception e) {
			logDB.error(e.getLocalizedMessage());
		}
		form.setSiteFilter(siteFilter);
	}
	/**
	 * Populate the Numeric Result drop down list
	 * @param form
	 */
	private void populateNumericResult(LabActivitiesSearchForm form,HttpSession session){
		List<String> numericResultFilter = new ArrayList<String>(); 
		for (String name: NumericResultFilter.getDisplayNames()){
			numericResultFilter.add(name);
		}
		form.setNumericResultFilter(numericResultFilter);
	}
	/**
	 * Populate the Date Range drop down list
	 * @param form
	 */
	private void populateDateRange(LabActivitiesSearchForm form,HttpSession session){
		List<String> dateRangeFilter = new ArrayList<String>(); 
		for (String name:DateRangeFilter.getDisplayNames()){
			dateRangeFilter.add(name);
		}
		form.setDateRangeFilter(dateRangeFilter);
	}
	
}
