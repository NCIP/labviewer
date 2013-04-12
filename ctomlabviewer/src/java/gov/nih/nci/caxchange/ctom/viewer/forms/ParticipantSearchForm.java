/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */
package gov.nih.nci.caxchange.ctom.viewer.forms;

import gov.nih.nci.caxchange.ctom.viewer.constants.DisplayConstants;
import gov.nih.nci.caxchange.ctom.viewer.viewobjects.ParticipantSearchResult;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * The ParticipantSearchForm is the class for participant search form.
 * 
 * @author asharma
 */
public class ParticipantSearchForm extends ActionForm implements
		BaseAssociationForm
{

	// participantPhrase is the participantPhrase they search with as an input
	private String participantPhrase;

	// list of participants
	private List<ParticipantSearchResult> participantsList;

	/**
	 * @return the participantPhrase
	 */
	public String getParticipantPhrase()
	{
		return participantPhrase;
	}

	private String index;
	
	// size of the studiesList
	private int listSize;

	/**
	 * @param participantPhrase
	 *            the participantPhrase to set
	 */
	public void setParticipantPhrase(String participantPhrase)
	{
		this.participantPhrase = participantPhrase;
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
		if (errors == null)
			errors = new ActionErrors();
		if ((studyIdent == null) || (studyIdent.length() < 1))
			// errors.add(ActionErrors.GLOBAL_ERROR, new
			// ActionError(DisplayConstants.ERROR_ID, "StudyId is required"));
			errors.add("studyIdent", new ActionError("error.studyId.value"));

		return errors;

	}

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

		this.participantPhrase = "";

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nih.nci.caxchange.ctom.viewer.forms.BaseDBForm#getFormName()
	 */
	public String getFormName()
	{
		return DisplayConstants.PARTICIPANTSEARCH_ID;
	}

	/**
	 * @return the participantsList
	 */
	public List<ParticipantSearchResult> getParticipantsList()
	{
		return participantsList;
	}

	/**
	 * @param participantsList
	 *            the participantsList to set
	 */
	public void setParticipantsList(
			List<ParticipantSearchResult> participantsList)
	{
		this.participantsList = participantsList;
	}

	/**
	 * @return the listSize
	 */
	public int getListSize()
	{
		return this.getParticipantsList().size();
	}

	/**
	 * @param listSize
	 *            the listSize to set
	 */
	public void setListSize(int listSize)
	{
		this.listSize = listSize;
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
