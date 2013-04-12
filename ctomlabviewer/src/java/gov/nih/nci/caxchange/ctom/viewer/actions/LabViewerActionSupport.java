/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */
package gov.nih.nci.caxchange.ctom.viewer.actions;

import gov.nih.nci.caxchange.ctom.viewer.constants.DisplayConstants;
import gov.nih.nci.caxchange.ctom.viewer.constants.ForwardConstants;
import gov.nih.nci.caxchange.ctom.viewer.forms.LoginForm;
import gov.nih.nci.caxchange.ctom.viewer.util.CommonUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author Lisa Kelley
 */
public class LabViewerActionSupport extends ActionSupport implements ServletRequestAware
{
	private static final Logger log = Logger.getLogger(LabViewerActionSupport.class);
	protected HttpServletRequest request;
	protected HttpSession session;
	private String tableId = null;
	
	/**
	 * set method for the servlet request
	 *
	 * @param request - servlet request
	 */
	public void setServletRequest(HttpServletRequest request)
	{
		this.request = request;
		session = request.getSession();
	}
	
	public String getLoginStatus()
	{
		if (session.isNew() || // lisa - ask about this
		    session.getAttribute(DisplayConstants.LOGIN_OBJECT) == null)
		{
			if (log.isDebugEnabled()) // lisa - ask about this
				log.debug("||||Failure|No Session or User Object Forwarding to the Login Page||");
			return ForwardConstants.LOGIN_PAGE;
		}

		// clear the session attributes 
		CommonUtil util = new CommonUtil();
		util.clearMenuSessionData(session);

		if (log.isDebugEnabled())
			log.debug(session.getId()
					+ "|"
					+ ((LoginForm) session
							.getAttribute(DisplayConstants.LOGIN_OBJECT))
							.getLoginId() + "|"
					+ tableId
					+ "|Forward|Success|Forwarding to the "
					+ tableId + " Home Page||");
		
		return SUCCESS;
	}
      
      /**
	   * get method for the boolean that returns true if the chart contains all null values or returns false if the chart
	   * contains a value that is not null
	   * 
	   * @return boolean - true if the chart contains all null values, otherwise false
	   */
	public boolean getIsWebssoCasLogout()
	{
		boolean isWebssoCasLogout = false;
		String webssoEnabled = (String)session.getAttribute("webssoEnabled");
		
	    if (session.getAttribute("CAGRID_SSO_GRID_IDENTITY") != null && 
	    	webssoEnabled != null &&
	       (webssoEnabled.equalsIgnoreCase("true") || session.getAttribute("HOT_LINK") == "true"))
	    {
	    	isWebssoCasLogout = true;
	    }
	    
	    return isWebssoCasLogout;
	}
	
	/**
	   * get method for the boolean that returns true if the chart contains all null values or returns false if the chart
	   * contains a value that is not null
	   * 
	   * @return boolean - true if the chart contains all null values, otherwise false
	   */
	public String getTableId()
	{
		if (tableId == null)
		{
			tableId = DisplayConstants.HOME_ID; // default
		}
		
		return tableId;
	}
	
	/**
	   * set method for the unit name.  Unit name is URL-encoded, decode it before saving.
	   * 
	   * @param unitName - the unit name
	   */
	public void setTableId(String tableId)
	{
	    this.tableId = tableId;
	}

}
