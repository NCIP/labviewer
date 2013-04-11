/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */
package gov.nih.nci.caxchange.ctom.viewer.forms;

import gov.nih.nci.caxchange.ctom.viewer.beans.Users;
import gov.nih.nci.caxchange.ctom.viewer.constants.DisplayConstants;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * The UserConfigForm is the class for User configuration.
 * 
 * @author asharma
 */
public class UserConfigForm extends ActionForm implements BaseAssociationForm
{

	private Users userBean;

	private List<Users> usersList;
	
	private int selectedIndex;

	/**
	 * @return the userBean
	 */
	public Users getUserBean()
	{
		return userBean;
	}

	/**
	 * @param userBean
	 *            the userBean to set
	 */
	public void setUserBean(Users userBean)
	{
		this.userBean = userBean;
	}

	/**
	 * @return the usersList
	 */
	public List<Users> getUsersList()
	{
		return usersList;
	}

	/**
	 * @param usersList
	 *            the usersList to set
	 */
	public void setUsersList(List<Users> usersList)
	{
		this.usersList = usersList;
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
		return DisplayConstants.USER_CONFG_ID;
	}

	
	/**
	 * @return the selectedIndex
	 */
	public int getSelectedIndex()
	{
	
		return selectedIndex;
	}

	
	/**
	 * @param selectedIndex the selectedIndex to set
	 */
	public void setSelectedIndex(int selectedIndex)
	{
	
		this.selectedIndex = selectedIndex;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.struts.action.ActionForm#validate(org.apache.struts.action.ActionMapping,
	 *      javax.servlet.http.HttpServletRequest)
	 */
	/*
	 * public ActionErrors validate(ActionMapping mapping, HttpServletRequest
	 * request) { ActionErrors errors = super.validate(mapping, request); if
	 * (errors == null) errors = new ActionErrors(); if
	 * ((userBean.getLoginName() == null) || (userBean.getLoginName().length() <
	 * 1)) errors.add("loginName", new ActionError("error.loginName.value")); if
	 * ((userBean.getFirstName() == null) || (userBean.getFirstName().length() <
	 * 1)) errors.add("firstName", new ActionError("error.firstName.value")); if
	 * ((userBean.getLastName() == null) || (userBean.getLastName().length() <
	 * 1)) errors.add("lastName", new ActionError("error.lastName.value")); if
	 * ((userBean.getPassword() == null) || (userBean.getPassword().length() <
	 * 1)) errors.add("password", new ActionError("error.password.value"));
	 * return errors; }
	 */

}
