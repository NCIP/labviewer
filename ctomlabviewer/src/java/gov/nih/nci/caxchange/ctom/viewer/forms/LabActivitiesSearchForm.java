/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
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
 * 
 * @author asharma
 */
public class LabActivitiesSearchForm extends ActionForm implements
		BaseAssociationForm
{

	private String patientId;

	private String studyId;

	// beginDate is the beginDate they search with as an input
	private String beginDate;
	// endDate is the endDate they search with as an input
	private String endDate;
	// labTestFilter is used to filter the labs by lab test type
	private List labTestFilter;
	// siteFilter is used to filter the labs by the healthcare site
	private List siteFilter;
	// numericResultFilter is used to filter the labs by numeric range
	private List numericResultFilter;
	// dateRangeFilter is used to filter the labs by date range
	private List dateRangeFilter;

	// user selected filters
	private String selectedLabTest;
	private String selectedSite;
	private String selectedNumericResult;
	private String selectedDateRange;

	/**
	 * @return the labTestFilter
	 */
	public List getLabTestFilter()
	{
		return labTestFilter;
	}

	/**
	 * @param labTestFilter
	 *            the labTestFilter to set
	 */
	public void setLabTestFilter(List labTestFilter)
	{
		this.labTestFilter = labTestFilter;
	}

	/**
	 * @return the siteFilter
	 */
	public List getSiteFilter()
	{
		return siteFilter;
	}

	/**
	 * @param siteFilter
	 *            the siteFilter to set
	 */
	public void setSiteFilter(List siteFilter)
	{
		this.siteFilter = siteFilter;
	}

	/**
	 * @return the numericResultFilter
	 */
	public List getNumericResultFilter()
	{
		return numericResultFilter;
	}

	/**
	 * @param numericResultFilter
	 *            the numericResultFilter to set
	 */
	public void setNumericResultFilter(List numericResultFilter)
	{
		this.numericResultFilter = numericResultFilter;
	}

	/**
	 * @return the dateRangeFilter
	 */
	public List getDateRangeFilter()
	{
		return dateRangeFilter;
	}

	/**
	 * @param dateRangeFilter
	 *            the dateRangeFilter to set
	 */
	public void setDateRangeFilter(List dateRangeFilter)
	{
		this.dateRangeFilter = dateRangeFilter;
	}

	/**
	 * @return the beginDate
	 */
	public String getBeginDate()
	{
		return beginDate;
	}

	/**
	 * @param beginDate
	 *            the beginDate to set
	 */
	public void setBeginDate(String beginDate)
	{
		this.beginDate = beginDate;
	}

	/**
	 * @return the endDate
	 */
	public String getEndDate()
	{
		return endDate;
	}

	/**
	 * @param endDate
	 *            the endDate to set
	 */
	public void setEndDate(String endDate)
	{
		this.endDate = endDate;
	}

	public String getFormName()
	{
		return DisplayConstants.LABACTIVITES_ID;
	}

	public String[] getAssociatedIds()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nih.nci.security.forms.BaseDBForm#getPrimaryId()
	 */
	public String getPrimaryId()
	{
		if (this.patientId == null)
		{
			return new String("");
		}
		else
		{
			return "";
		}
	}

	/**
	 * The reset method is called on the loading of this form and anytime the
	 * user selects the reset button. It can be used to set the default starting
	 * values for the form.
	 * <P>
	 * 
	 * @param mapping
	 *            The ActionMapping for the posted action
	 * @param request
	 *            The HttpServletRequest for this post
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request)
	{

		if (request.getSession().getAttribute("HOT_LINK") == "true")
		{
			LabActivitiesSearchForm lForm =
					(LabActivitiesSearchForm) request.getSession()
							.getAttribute("CURRENT_FORM");
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nih.nci.caxchange.ctom.viewer.forms.BaseDBForm#resetForm()
	 */
	public void resetForm()
	{

		List newList = new ArrayList();
		newList.add("All");

		this.patientId = "";
		this.beginDate = "";
		this.endDate = "";
		this.studyId = "";
		this.numericResultFilter = newList;
		this.dateRangeFilter = newList;
		this.labTestFilter = newList;
		this.labTestFilter = newList;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.struts.action.ActionForm#validate(org.apache.struts.action.ActionMapping,
	 *      javax.servlet.http.HttpServletRequest)
	 */
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request)
	{
		ActionErrors errors = super.validate(mapping, request);
		HttpSession session = request.getSession();

		String studyIdent =
				(String) session.getAttribute("studyId") != null ? (String) session
						.getAttribute("studyId")
						: "";
		String patientIdent =
				(String) session.getAttribute("patientId") != null ? (String) session
						.getAttribute("patientId")
						: "";

		if (errors == null)
			errors = new ActionErrors();
		if ((patientIdent == null) || (patientIdent.length() < 1))
			// errors.add(ActionErrors.GLOBAL_ERROR, new
			// ActionError(DisplayConstants.ERROR_ID, "PatientId is required"));
			errors.add("patientId", new ActionError("error.patientId.value"));
		if ((studyIdent == null) || (studyIdent.length() < 1))
			// errors.add(ActionErrors.GLOBAL_ERROR, new
			// ActionError(DisplayConstants.ERROR_ID, "StudyId is required"));
			errors.add("studyId", new ActionError("error.studyId.value"));
		if (getSelectedDateRange().equals(DateRangeFilter.CUSTOM_DATES_TIMES))
		{
			if ((getBeginDate() == null) || (getBeginDate().length() < 1))
				// errors.add(ActionErrors.GLOBAL_ERROR, new
				// ActionError(DisplayConstants.ERROR_ID, "PatientId is
				// required"));
				errors.add("beginDate",
						new ActionError("error.beginDate.value"));

			if ((getEndDate() == null) || (getEndDate().length() < 1))
				// errors.add(ActionErrors.GLOBAL_ERROR, new
				// ActionError(DisplayConstants.ERROR_ID, "StudyId is
				// required"));
				errors.add("endDate", new ActionError("error.endDate.value"));

			try
			{
				if (new SimpleDateFormat("MM/dd/yyyy").parse(getBeginDate())
						.after(
								new SimpleDateFormat("MM/dd/yyyy")
										.parse(getEndDate())))
				{
					// errors.add(ActionErrors.GLOBAL_ERROR, new
					// ActionError(DisplayConstants.ERROR_ID, "End Date cannot
					// be earlier than Begin Date"));
					errors.add("dateRange", new ActionError(
							"error.dateRange.value"));
				}
			}
			catch (ParseException e)
			{
				// errors.add(ActionErrors.GLOBAL_ERROR, new
				// ActionError(DisplayConstants.ERROR_ID, "Incorrect Date
				// Format"));
				errors.add("dateFormat", new ActionError(
						"error.dateFormat.value"));
			}
		}
		return errors;

	}

	/**
	 * @return the selectedLabTest
	 */
	public String getSelectedLabTest()
	{
		return selectedLabTest;
	}

	/**
	 * @param selectedLabTest
	 *            the selectedLabTest to set
	 */
	public void setSelectedLabTest(String selectedLabTest)
	{
		this.selectedLabTest = selectedLabTest;
	}

	/**
	 * @return the selectedSite
	 */
	public String getSelectedSite()
	{
		return selectedSite;
	}

	/**
	 * @param selectedSite
	 *            the selectedSite to set
	 */
	public void setSelectedSite(String selectedSite)
	{
		this.selectedSite = selectedSite;
	}

	/**
	 * @return the selectedNumericResult
	 */
	public String getSelectedNumericResult()
	{
		return selectedNumericResult;
	}

	/**
	 * @param selectedNumericResult
	 *            the selectedNumericResult to set
	 */
	public void setSelectedNumericResult(String selectedNumericResult)
	{
		this.selectedNumericResult = selectedNumericResult;
	}

	/**
	 * @return the selectedDateRange
	 */
	public String getSelectedDateRange()
	{
		return selectedDateRange;
	}

	/**
	 * @param selectedDateRange
	 *            the selectedDateRange to set
	 */
	public void setSelectedDateRange(String selectedDateRange)
	{
		this.selectedDateRange = selectedDateRange;
	}

	/**
	 * @return the patientId
	 */
	public String getPatientId()
	{
		return patientId;
	}

	/**
	 * @param patientId
	 *            the patientId to set
	 */
	public void setPatientId(String patientId)
	{
		this.patientId = patientId;
	}

	/**
	 * @return the studyId
	 */
	public String getStudyId()
	{
		return studyId;
	}

	/**
	 * @param studyId
	 *            the studyId to set
	 */
	public void setStudyId(String studyId)
	{
		this.studyId = studyId;
	}
}
