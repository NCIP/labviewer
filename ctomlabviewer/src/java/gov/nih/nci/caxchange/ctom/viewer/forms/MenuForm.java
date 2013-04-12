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

public class MenuForm extends ActionForm
{
	private String tableId;

	/**
	 * @return Returns the tableId.
	 */
	public String getTableId()
	{
		return tableId;
	}

	/**
	 * @param tableId
	 *            The tableId to set.
	 */
	public void setTableId(String tableId)
	{
		this.tableId = tableId;
	}
}
