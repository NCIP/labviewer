/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */
package gov.nih.nci.caxchange.ctom.viewer.forms;

import gov.nih.nci.caxchange.ctom.viewer.constants.DisplayConstants;
import gov.nih.nci.caxchange.ctom.viewer.viewobjects.FormElement;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

/**
 * @author Kunal Modi (Ekagra Software Technologies Ltd.)
 */
public class LabActivitiesForm extends ValidatorForm implements
		BaseAssociationForm
{
	private String recordId;
	private String[] recordIds;
	private String studyId;
	private String patientId;
	private String beginDate;
	private String endDate;
	private String gridProxy;
	private String mrn;
	private String nciIdentifier;

	public String getNciIdentifier()
	{
		return nciIdentifier;
	}

	public void setNciIdentifier(String nciIdentifier)
	{
		this.nciIdentifier = nciIdentifier;
	}

	public String getMrn()
	{
		return mrn;
	}

	public void setMrn(String mrn)
	{
		this.mrn = mrn;
	}

	public String getGridProxy()
	{
		return gridProxy;
	}

	public void setGridProxy(String gridProxy)
	{
		this.gridProxy = gridProxy;
	}

	public String getRecordId()
	{
		return recordId;
	}

	public void setRecordId(String recordId)
	{
		this.recordId = recordId;
	}

	public String[] getRecordIds()
	{
		return recordIds;
	}

	public void setRecordIds(String[] recordIds)
	{
		this.recordIds = recordIds;
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

	public ArrayList getAddFormElements()
	{
		return null;
	}

	public ArrayList getDisplayFormElements()
	{
		return null;
	}

	public ArrayList getSearchFormElements()
	{
		ArrayList formElementList = new ArrayList();

		formElementList.add(new FormElement("Study Identifier", "studyId",
				getStudyId(), DisplayConstants.INPUT_BOX,
				DisplayConstants.REQUIRED, DisplayConstants.NOT_DISABLED));
		formElementList.add(new FormElement("Patient Identifier", "patientId",
				getPatientId(), DisplayConstants.INPUT_BOX,
				DisplayConstants.REQUIRED, DisplayConstants.NOT_DISABLED));
		formElementList.add(new FormElement("Begin Date (MM/DD/YYYY)",
				"beginDate", getBeginDate(), DisplayConstants.INPUT_BOX,
				DisplayConstants.REQUIRED, DisplayConstants.NOT_DISABLED));
		formElementList.add(new FormElement("End Date (MM/DD/YYYY)", "endDate",
				getEndDate(), DisplayConstants.INPUT_BOX,
				DisplayConstants.REQUIRED, DisplayConstants.NOT_DISABLED));

		return formElementList;
	}

	public void resetForm()
	{
		this.recordId = "";
		this.recordIds = null;
		this.patientId = "";
		this.beginDate = "";
		this.endDate = "";
		this.studyId = "";
	}

	public void reset(ActionMapping mapping, HttpServletRequest request)
	{
		this.recordId = "";
		this.recordIds = null;
		this.patientId = "";
		this.beginDate = "";
		this.endDate = "";
		this.studyId = "";
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
		ActionErrors errors = new ActionErrors();
		errors = super.validate(mapping, request);
		try
		{
			if (new SimpleDateFormat("MM/dd/yyyy").parse(this.beginDate).after(
					new SimpleDateFormat("MM/dd/yyyy").parse(this.endDate)))
			{
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
						DisplayConstants.ERROR_ID,
						"End Date cannot be earlier than Begin Date"));
			}
		}
		catch (ParseException e)
		{
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					DisplayConstants.ERROR_ID, "Incorrect Date Format"));
		}
		return errors;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nih.nci.security.forms.BaseDBForm#buildDisplayForm(javax.servlet.http.HttpServletRequest)
	 */
	public void buildDisplayForm(HttpServletRequest request) throws Exception
	{
		// Will Never Be Called
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nih.nci.security.forms.BaseDBForm#removeDBObject(javax.servlet.http.HttpServletRequest)
	 */
	public void removeDBObject(HttpServletRequest request) throws Exception
	{
		// Will Never Be Called
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nih.nci.security.upt.forms.BaseAssociationForm#buildAssociationObject(javax.servlet.http.HttpServletRequest)
	 */
	public void buildAssociationObject(HttpServletRequest request)
			throws Exception
	{
		// Will Never Be Called
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nih.nci.security.upt.forms.BaseAssociationForm#setAssociationObject(javax.servlet.http.HttpServletRequest)
	 */
	public void setAssociationObject(HttpServletRequest request)
			throws Exception
	{
		// Will Never Be Called
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nih.nci.security.upt.forms.BaseDBForm#getFormName()
	 */
	public String getFormName()
	{
		return DisplayConstants.LABACTIVITES_ID;
	}

	public String[] getAssociatedIds()
	{
		return null;
	}

	private static String convertToString(java.util.Date date)
	{
		if (date == null)
			return "-";
		SimpleDateFormat formatter = new SimpleDateFormat();
		String str = formatter.format(date);
		return str;
	}

	public String getStudyId()
	{
		return studyId;
	}

	public void setStudyId(String studyId)
	{
		this.studyId = studyId;
	}

}
