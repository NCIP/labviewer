/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */
package gov.nih.nci.caxchange.ctom.viewer.forms;

import org.apache.struts.action.ActionForm;

/**
 * @author Kunal Modi (Ekagra Software Technologies Ltd.)
 */
public class LoginForm extends ActionForm
{
	private String loginId;
	private String password;
	private String gridProxy;

	public String getGridProxy()
	{
		return gridProxy;
	}

	public void setGridProxy(String gridProxy)
	{
		this.gridProxy = gridProxy;
	}

	/**
	 * @return Returns the loginId.
	 */
	public String getLoginId()
	{
		return loginId;
	}

	/**
	 * @param loginId
	 *            The loginId to set.
	 */
	public void setLoginId(String loginId)
	{
		this.loginId = loginId;
	}

	/**
	 * @return Returns the password.
	 */
	public String getPassword()
	{
		return password;
	}

	/**
	 * @param password
	 *            The password to set.
	 */
	public void setPassword(String password)
	{
		this.password = password;
	}
}
