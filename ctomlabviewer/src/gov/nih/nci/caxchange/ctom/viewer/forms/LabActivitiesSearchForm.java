/**
 * Copyright Notice.  Copyright 2008  ScenPro, Inc (“caBIG™ Participant”).caXchange
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
 * 		“This product includes software developed by ScenPro, Inc.”  
 * 		If You do not include such end-user documentation, You shall include this acknowledgment in the caBIG™ Software itself, 
 * 		wherever such third-party acknowledgments normally appear.
 * 3.	You may not use the names  “ScenPro, Inc”, 
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
 * 		IN NO EVENT SHALL THE ScenPro, Inc OR ITS AFFILIATES 
 * 		BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * 		PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON 
 * 		ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY 
 * 		OUT OF THE USE OF THIS caBIG™ SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * 
 */


package gov.nih.nci.caxchange.ctom.viewer.forms;

import gov.nih.nci.caxchange.ctom.viewer.constants.DisplayConstants;
import gov.nih.nci.caxchange.ctom.viewer.viewobjects.DateRangeFilter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * The LabActivitiesSearchForm is the class for lab search form. It performs
 * validations on user entered form data.
 * @author asharma
 *
 */
public class LabActivitiesSearchForm extends ActionForm implements
		BaseAssociationForm {
	

	private String patientId;
	
	private String studyId;
	
	//beginDate is the beginDate they search with as an input 
	private String beginDate;
	//endDate is the endDate they search with as an input
	private String endDate;
	//labTestFilter is used to filter the labs by lab test type 
	private List labTestFilter;
	//siteFilter is used to filter the labs by the healthcare site
	private List siteFilter;
	//numericResultFilter is used to filter the labs by numeric range
	private List numericResultFilter;
	//dateRangeFilter is used to filter the labs by date range
	private List dateRangeFilter;
	
	//user selected filters
	private String selectedLabTest;
	private String selectedSite;
	private String selectedNumericResult;
	private String selectedDateRange;
	
	/**
	 * @return the labTestFilter
	 */
	public List getLabTestFilter() {
		return labTestFilter;
	}

	/**
	 * @param labTestFilter the labTestFilter to set
	 */
	public void setLabTestFilter(List labTestFilter) {
		this.labTestFilter = labTestFilter;
	}

	/**
	 * @return the siteFilter
	 */
	public List getSiteFilter() {
		return siteFilter;
	}

	/**
	 * @param siteFilter the siteFilter to set
	 */
	public void setSiteFilter(List siteFilter) {
		this.siteFilter = siteFilter;
	}

	/**
	 * @return the numericResultFilter
	 */
	public List getNumericResultFilter() {
		return numericResultFilter;
	}

	/**
	 * @param numericResultFilter the numericResultFilter to set
	 */
	public void setNumericResultFilter(List numericResultFilter) {
		this.numericResultFilter = numericResultFilter;
	}

	/**
	 * @return the dateRangeFilter
	 */
	public List getDateRangeFilter() {
		return dateRangeFilter;
	}

	/**
	 * @param dateRangeFilter the dateRangeFilter to set
	 */
	public void setDateRangeFilter(List dateRangeFilter) {
		this.dateRangeFilter = dateRangeFilter;
	}

	/**
	 * @return the beginDate
	 */
	public String getBeginDate() {
		return beginDate;
	}

	/**
	 * @param beginDate the beginDate to set
	 */
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getFormName() {
		return DisplayConstants.LABACTIVITES_ID;
	}

	public String[] getAssociatedIds() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see gov.nih.nci.security.forms.BaseDBForm#getPrimaryId()
	 */
	public String getPrimaryId() {
		if (this.patientId == null) {
			return new String("");
		} else {
			return "";
		}
	}

	/**
	 * The reset method is called on the loading of this form and anytime the
	 * user selects the reset button.  It can be used to set the default starting
	 * values for the form.
	 * <P>
	 * @param mapping The ActionMapping for the posted action
	 * @param request The HttpServletRequest for this post
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		
		if(request.getSession().getAttribute("HOT_LINK")=="true")
		{
			LabActivitiesSearchForm lForm = (LabActivitiesSearchForm)request.getSession().getAttribute("CURRENT_FORM"); 
			this.patientId = lForm.patientId;
			this.beginDate = "";
			this.endDate = "";
			this.studyId = lForm.studyId;
			request.getSession().setAttribute("HOT_LINK", "false");
		}
		else
		{
			this.patientId = "";
			this.beginDate = "";
			this.endDate = "";
			this.studyId = "";
			}
	}

	/* (non-Javadoc)
	 * @see gov.nih.nci.caxchange.ctom.viewer.forms.BaseDBForm#resetForm()
	 */
	public void resetForm() {
		
		List newList =new ArrayList();
		newList.add("All");
		
		this.patientId = "";
		this.beginDate = "";
		this.endDate = "";
		this.studyId = "";
		this.numericResultFilter = newList;
		this.dateRangeFilter =newList;
		this.labTestFilter = newList;
		this.labTestFilter=newList;
		
	}

	/* (non-Javadoc)
	 * @see org.apache.struts.action.ActionForm#validate(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		ActionErrors errors = super.validate(mapping, request);
		HttpSession session = request.getSession();

		String studyIdent = (String)session.getAttribute("studyId")!=null?(String)session.getAttribute("studyId"):"";
		String patientIdent = (String)session.getAttribute("patientId")!=null?(String)session.getAttribute("patientId"):"";
 
		if(errors == null) errors = new ActionErrors();
			if ((patientIdent == null) || (patientIdent.length() < 1))
				//errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(DisplayConstants.ERROR_ID, "PatientId is required"));
				errors.add("patientId", new ActionError(
						"error.patientId.value"));
			if ((studyIdent == null) || (studyIdent.length() < 1))
				//errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(DisplayConstants.ERROR_ID, "StudyId is required"));
				errors.add("studyId", new ActionError("error.studyId.value"));
		    if(getSelectedDateRange().equals(DateRangeFilter.CUSTOM_DATES_TIMES))
		    {
				if ((getBeginDate() == null) || (getBeginDate().length() < 1))
					//errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(DisplayConstants.ERROR_ID, "PatientId is required"));
					errors.add("beginDate", new ActionError(
							"error.beginDate.value"));
			
				if ((getEndDate() == null) || (getEndDate().length() < 1))
					//errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(DisplayConstants.ERROR_ID, "StudyId is required"));
					errors.add("endDate", new ActionError("error.endDate.value"));
				
				try {
					 if (new SimpleDateFormat("MM/dd/yyyy").parse(getBeginDate()).after(
						new SimpleDateFormat("MM/dd/yyyy").parse(getEndDate()))) 
					 {
					//	errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(DisplayConstants.ERROR_ID, "End Date cannot be earlier than Begin Date"));
					errors.add("dateRange", new ActionError(
							"error.dateRange.value"));
	    			 }
				  } catch (ParseException e) {
				//	errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(DisplayConstants.ERROR_ID, "Incorrect Date Format"));
				errors.add("dateFormat",
						new ActionError("error.dateFormat.value"));
			}
	  }	
		return errors;
	  
	}

	/**
	 * @return the selectedLabTest
	 */
	public String getSelectedLabTest() {
		return selectedLabTest;
	}

	/**
	 * @param selectedLabTest the selectedLabTest to set
	 */
	public void setSelectedLabTest(String selectedLabTest) {
		this.selectedLabTest = selectedLabTest;
	}

	/**
	 * @return the selectedSite
	 */
	public String getSelectedSite() {
		return selectedSite;
	}

	/**
	 * @param selectedSite the selectedSite to set
	 */
	public void setSelectedSite(String selectedSite) {
		this.selectedSite = selectedSite;
	}

	/**
	 * @return the selectedNumericResult
	 */
	public String getSelectedNumericResult() {
		return selectedNumericResult;
	}

	/**
	 * @param selectedNumericResult the selectedNumericResult to set
	 */
	public void setSelectedNumericResult(String selectedNumericResult) {
		this.selectedNumericResult = selectedNumericResult;
	}

	/**
	 * @return the selectedDateRange
	 */
	public String getSelectedDateRange() {
		return selectedDateRange;
	}

	/**
	 * @param selectedDateRange the selectedDateRange to set
	 */
	public void setSelectedDateRange(String selectedDateRange) {
		this.selectedDateRange = selectedDateRange;
	}

	/**
	 * @return the patientId
	 */
	public String getPatientId() {
		return patientId;
	}

	/**
	 * @param patientId the patientId to set
	 */
	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	/**
	 * @return the studyId
	 */
	public String getStudyId() {
		return studyId;
	}

	/**
	 * @param studyId the studyId to set
	 */
	public void setStudyId(String studyId) {
		this.studyId = studyId;
	}
}
