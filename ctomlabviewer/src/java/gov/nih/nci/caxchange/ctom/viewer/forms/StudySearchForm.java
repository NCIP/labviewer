/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */
package gov.nih.nci.caxchange.ctom.viewer.forms;

import gov.nih.nci.caxchange.ctom.viewer.constants.DisplayConstants;
import gov.nih.nci.caxchange.ctom.viewer.viewobjects.StudySearchResult;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * The StudySearchForm is the class for study search form.
 * 
 * @author asharma
 */
public class StudySearchForm extends ActionForm implements BaseAssociationForm
{

	// studyPhrase is the studyPhrase the user searches with as an input
	private String studyID;
	private String studyTitle;

	// list of studies
	private List<StudySearchResult> studiesList;

	private String index;
	
	/**
	 * @return the studyPhrase
	 */
	public String getStudyID()
	{
		return studyID;
	}

	/**
	 * @param studyPhrase
	 *            the studyPhrase to set
	 */
	public void setStudyID(String studyID)
	{
		this.studyID = studyID.trim();
	}
	
	/**
	 * @return the studyPhrase
	 */
	public String getStudyTitle()
	{
		return studyTitle;
	}

	/**
	 * @param studyPhrase
	 *            the studyPhrase to set
	 */
	public void setStudyTitle(String studyTitle)
	{
		this.studyTitle = studyTitle.trim();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.struts.action.ActionForm#validate(org.apache.struts.action.ActionMapping,
	 *      javax.servlet.http.HttpServletRequest)
	 */
//	public ActionErrors validate(ActionMapping mapping,
//			HttpServletRequest request)
//	{
//		ActionErrors errors = super.validate(mapping, request);
//		if (errors == null)
//			errors = new ActionErrors();
//
//		if ((studyID == null || studyID.length() < 1) && (studyTitle == null || studyTitle.length() < 1))
//		{
//			//errors.add("studyTitle", new ActionError("error.studyPhrase.value"));
//			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.studyPhrase.value"));
//		}
//
//		return errors;
//
//	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nih.nci.caxchange.ctom.viewer.forms.BaseAssociationForm#getAssociatedIds()
	 */
	public String[] getAssociatedIds()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nih.nci.caxchange.ctom.viewer.forms.BaseDBForm#resetForm()
	 */
	public void resetForm()
	{
		studyID = "";
		studyTitle = "";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nih.nci.caxchange.ctom.viewer.forms.BaseDBForm#getFormName()
	 */
	public String getFormName()
	{
		return DisplayConstants.STUDYSEARCH_ID;
	}

	/**
	 * @return the studiesList
	 */
	public List<StudySearchResult> getStudiesList()
	{
		return studiesList;
	}

	/**
	 * @param studiesList
	 *            the studiesList to set
	 */
	public void setStudiesList(List<StudySearchResult> studiesList)
	{
		this.studiesList = studiesList;
	}

	
	/**
	 * @return the index
	 */
	public String getIndex()
	{
	
		return index;
	}

	
	/**
	 * @param index the index to set
	 */
	public void setIndex(String index)
	{
	
		this.index = index;
	}

	
}
