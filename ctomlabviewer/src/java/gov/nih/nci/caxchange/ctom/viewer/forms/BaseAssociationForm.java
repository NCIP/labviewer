/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */
package gov.nih.nci.caxchange.ctom.viewer.forms;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Kunal Modi (Ekagra Software Technologies Ltd.)
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface BaseAssociationForm extends BaseDBForm
{
	public String[] getAssociatedIds();
	
	//public void buildAssociationObject(HttpServletRequest request)throws Exception;

	//public void setAssociationObject(HttpServletRequest request)throws Exception;

}
