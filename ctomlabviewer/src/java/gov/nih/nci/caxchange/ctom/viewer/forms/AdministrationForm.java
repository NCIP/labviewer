/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */
package gov.nih.nci.caxchange.ctom.viewer.forms;

import javax.servlet.http.HttpServletRequest;

import gov.nih.nci.caxchange.ctom.viewer.constants.DisplayConstants;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * The AdmininstrationForm is the class for Admin configuration.
 * 
 * @author asharma
 */
/**
 * @author asharma
 */
public class AdministrationForm extends ActionForm implements
		BaseAssociationForm
{

	private String caxUrl;

	private String caaersUrl;

	private String c3dUrl;

	private String tissueUrl;
	
	private String c3prUrl;

	/**
	 * @return the c3prUrl
	 */
	public String getC3prUrl()
	{
		return c3prUrl;
	}

	/**
	 * @param url the c3prUrl to set
	 */
	public void setC3prUrl(String url)
	{
		c3prUrl = url;
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
	 * @see gov.nih.nci.caxchange.ctom.viewer.forms.BaseDBForm#getFormName()
	 */
	public String getFormName()
	{
		return DisplayConstants.ADMIN_ID;
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
		if (errors == null)
			errors = new ActionErrors();

		if ((getCaxUrl() == null) || (getCaxUrl().length() < 1))
			errors.add("caXchangeURL", new ActionError(
					"error.caXchangeURL.value"));
		if ((getCaaersUrl() == null) || (getCaaersUrl().length() < 1))
			errors.add("caaersURL", new ActionError("error.caaersUrl.value"));

		return errors;

	}

	/**
	 * @return the caxUrl
	 */
	public String getCaxUrl()
	{
		return caxUrl;
	}

	/**
	 * @param caxUrl
	 *            the caxUrl to set
	 */
	public void setCaxUrl(String caxUrl)
	{
		this.caxUrl = caxUrl;
	}

	/**
	 * @return the caaersUrl
	 */
	public String getCaaersUrl()
	{
		return caaersUrl;
	}

	/**
	 * @param caaersUrl
	 *            the caaersUrl to set
	 */
	public void setCaaersUrl(String caaersUrl)
	{
		this.caaersUrl = caaersUrl;
	}

	/**
	 * @return the c3dUrl
	 */
	public String getC3dUrl()
	{
		return c3dUrl;
	}

	/**
	 * @param url
	 *            the c3dUrl to set
	 */
	public void setC3dUrl(String url)
	{
		c3dUrl = url;
	}

	/**
	 * @return the tissueUrl
	 */
	public String getTissueUrl()
	{
		return tissueUrl;
	}

	/**
	 * @param tissueUrl
	 *            the tissueUrl to set
	 */
	public void setTissueUrl(String tissueUrl)
	{
		this.tissueUrl = tissueUrl;
	}

}
