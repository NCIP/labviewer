/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */
package gov.nih.nci.caxchange.ctom.viewer.forms;

import gov.nih.nci.caxchange.ctom.viewer.beans.PrincipalInvestigator;
import gov.nih.nci.caxchange.ctom.viewer.constants.DisplayConstants;

import java.util.List;

import org.apache.struts.action.ActionForm;

/**
 * The StudySearchForm is the class for study search form.
 * 
 * @author asharma
 */
public class PIForm extends ActionForm implements BaseAssociationForm
{
	
	private List<PrincipalInvestigator> piList;
	
	private PrincipalInvestigator pi;
	
	/**
	 * @return the pi
	 */
	public PrincipalInvestigator getPi()
	{
	
		return pi;
	}

	/**
	 * @param pi the pi to set
	 */
	public void setPi(PrincipalInvestigator pi)
	{
	
		this.pi = pi;
	}


	/**
	 * @return the piList
	 */
	public List<PrincipalInvestigator> getPiList()
	{
	
		return piList;
	}

	
	/**
	 * @param piList the piList to set
	 */
	public void setPiList(List<PrincipalInvestigator> piList)
	{
	
		this.piList = piList;
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

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nih.nci.caxchange.ctom.viewer.forms.BaseDBForm#getFormName()
	 */
	public String getFormName()
	{
		return DisplayConstants.PI_ID;
	}

	
	

	
	
	
}
