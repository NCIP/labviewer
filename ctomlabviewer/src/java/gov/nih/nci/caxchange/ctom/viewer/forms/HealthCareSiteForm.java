/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */
package gov.nih.nci.caxchange.ctom.viewer.forms;

import gov.nih.nci.caxchange.ctom.viewer.beans.HCSite;
import gov.nih.nci.caxchange.ctom.viewer.constants.DisplayConstants;

import java.util.List;

import org.apache.struts.action.ActionForm;

/**
 * The StudySearchForm is the class for study search form.
 * 
 * @author asharma
 */
public class HealthCareSiteForm extends ActionForm implements BaseAssociationForm
{
	private List<HCSite> hcsList;
	
	private HCSite hcs;

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
		return DisplayConstants.HEALTH_CARE_SITE_ID;
	}

	
	/**
	 * @return the hcsList
	 */
	public List<HCSite> getHcsList()
	{
	
		return hcsList;
	}

	
	/**
	 * @param hcsList the hcsList to set
	 */
	public void setHcsList(List<HCSite> hcsList)
	{
	
		this.hcsList = hcsList;
	}

	
	/**
	 * @return the hcs
	 */
	public HCSite getHcs()
	{
	
		return hcs;
	}

	
	/**
	 * @param hcs the hcs to set
	 */
	public void setHcs(HCSite hcs)
	{
	
		this.hcs = hcs;
	}

	
}
