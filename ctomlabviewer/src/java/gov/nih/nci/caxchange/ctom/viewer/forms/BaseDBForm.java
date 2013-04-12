/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */
package gov.nih.nci.caxchange.ctom.viewer.forms;


import gov.nih.nci.caxchange.ctom.viewer.viewobjects.SearchResult;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessages;

/**
 * @author Kunal Modi (Ekagra Software Technologies Ltd.)
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface BaseDBForm
{
	public String getFormName();
	
	//public ArrayList getAddFormElements();

	//public ArrayList getDisplayFormElements();
	
	//public ArrayList getSearchFormElements();

	//public void buildDisplayForm(HttpServletRequest request) throws Exception;
	
	//public void submitAdverseEvent(HttpServletRequest request) throws Exception;
	
	//public void loadToCTMS(HttpServletRequest request) throws Exception;

	//public void removeDBObject(HttpServletRequest request) throws Exception;
	
	//public SearchResult searchObjects(HttpServletRequest request, ActionErrors errors, ActionMessages messages) throws Exception;

	//public String getPrimaryId();
	
	public void resetForm();

}
