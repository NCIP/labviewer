/**
 * 
 */
package gov.nih.nci.caxchange.ctom.viewer.forms;

import gov.nih.nci.caxchange.ctom.viewer.constants.DisplayConstants;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 * The LabActivitiesSearchForm is the class for lab search form. It performs
 * validations on user entered form data.
 * @author asharma
 *
 */
public class LabActivitiesSearchForm extends ActionForm implements
		BaseAssociationForm {
	

	//studyId is the studyId they search with as an input
	private String studyId;
	//patientId is the patientId they search with as an input
	private String patientId;
	//beginDate is the beginDate they search with as an input 
	private String beginDate;
	//endDate is the endDate they search with as an input
	private String endDate;
	
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
		else{
		this.patientId = "";
		this.beginDate = "";
		this.endDate = "";
		this.studyId = "";
		}
	}

	public void resetForm() {

		this.patientId = "";
		this.beginDate = "";
		this.endDate = "";
		this.studyId = "";
	}

	/* (non-Javadoc)
	 * @see org.apache.struts.action.ActionForm#validate(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		ActionErrors errors = super.validate(mapping, request);
		if(errors == null) errors = new ActionErrors();
			if ((getPatientId() == null) || (getPatientId().length() < 1))
				//errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(DisplayConstants.ERROR_ID, "PatientId is required"));
				errors.add("patientId", new ActionError(
						"error.patientId.value"));
		
			if ((getStudyId() == null) || (getStudyId().length() < 1))
				//errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(DisplayConstants.ERROR_ID, "StudyId is required"));
				errors.add("studyId", new ActionError("error.studyId.value"));
		
			if ((getBeginDate() == null) || (getBeginDate().length() < 1))
				//errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(DisplayConstants.ERROR_ID, "PatientId is required"));
				errors.add("beginDate", new ActionError(
						"error.beginDate.value"));
		
			if ((getEndDate() == null) || (getEndDate().length() < 1))
				//errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(DisplayConstants.ERROR_ID, "StudyId is required"));
				errors.add("endDate", new ActionError("error.endDate.value"));
			try {

			if (new SimpleDateFormat("MM/dd/yyyy").parse(getBeginDate()).after(
					new SimpleDateFormat("MM/dd/yyyy").parse(getEndDate()))) {
				//	errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(DisplayConstants.ERROR_ID, "End Date cannot be earlier than Begin Date"));
				errors.add("dateRange", new ActionError(
						"error.dateRange.value"));
			}
		} catch (ParseException e) {
			//	errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(DisplayConstants.ERROR_ID, "Incorrect Date Format"));
			errors.add("dateFormat",
					new ActionError("error.dateFormat.value"));
		}
		return errors;
	  
	}
}
