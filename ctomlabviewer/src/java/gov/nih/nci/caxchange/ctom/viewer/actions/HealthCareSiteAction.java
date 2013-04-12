/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */
package gov.nih.nci.caxchange.ctom.viewer.actions;

import gov.nih.nci.caxchange.ctom.viewer.DAO.HealthCareSiteDAO;
import gov.nih.nci.caxchange.ctom.viewer.beans.HCSite;
import gov.nih.nci.caxchange.ctom.viewer.constants.DisplayConstants;
import gov.nih.nci.caxchange.ctom.viewer.constants.ForwardConstants;
import gov.nih.nci.caxchange.ctom.viewer.forms.LoginForm;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * This class loads the HealthCare Site details page after login information is
 * authenticated. If authentication fails it redirects the user to login page to
 * enter valid login information.
 * 
 * @author Lisa Kelley
 */
public class HealthCareSiteAction extends LabViewerActionSupport
{
	private static final Logger log = Logger.getLogger(HealthCareSiteAction.class);
	private Long protocolId;
	private List<HCSite> healthCareSites;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.struts.action.Action#execute(org.apache.struts.action.ActionMapping,
	 *      org.apache.struts.action.ActionForm,
	 *      javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public String display()
	{
		// lisa - ask about this section of code
		// afterwards put in LabViewerActionSupport and access from there (PrincipalInvestigatorAction too)
		String gridId;
		String gridProxy;

		LoginForm loginForm = (LoginForm) session.getAttribute(DisplayConstants.LOGIN_OBJECT);
		if (loginForm == null)
		{
			gridId = (String) request.getParameter("gridId");
			gridProxy = (String) request.getParameter("gridProxy");
		}
		else
		{
			gridId = loginForm.getLoginId();
			gridProxy = loginForm.getGridProxy();
		}

		if (StringUtils.isNotBlank(gridId))
		{
			// validate the gridId
			loginForm = new LoginForm();				
			loginForm.setLoginId(gridId);
			loginForm.setGridProxy(StringUtils.isNotBlank(gridProxy) ? gridProxy : null);
			
			session = request.getSession(true);
			session.setAttribute(DisplayConstants.LOGIN_OBJECT, loginForm);
		}
		else
		{
			if (session.isNew() || // lisa - ask about this
				session.getAttribute(DisplayConstants.LOGIN_OBJECT) == null)
				{
					if (log.isDebugEnabled()) // lisa - ask about this
						log.debug("||||Failure|No Session or User Object Forwarding to the Login Page||");
					return ForwardConstants.LOGIN_PAGE;
				}
		}
		
		try
	    {
			HealthCareSiteDAO dao = new HealthCareSiteDAO();
		    healthCareSites = dao.getHealthCareSites(protocolId, session);
	    }
	    catch (Exception e)
	    {
		    // error message has already been logged
		    this.addActionError(e.getMessage());
	    }

		// if the login is valid; loads the search page
		if (log.isDebugEnabled())
			log.debug(session.getId()
					+ "|"
					+ ((LoginForm) session.getAttribute(DisplayConstants.LOGIN_OBJECT))
							.getLoginId() + "|" //+ baseDBForm.getFormName()
					+ "|loadHealthCareSite|Success|Loading the HealthCare Site Page||");

		return SUCCESS;
	}
	
	/**
	   * set method for the unit name.  Unit name is URL-encoded, decode it before saving.
	   * 
	   * @param unitName - the unit name
	   */
	public void setProtocolId(String protocolId)
	{
	    this.protocolId = Long.valueOf(protocolId);
	}
	
	/**
	   * get method for the list of workload objects
	   * 
	   * @return List<Workload> - list of workload objects
	   */
	public List<HCSite> getHealthCareSites()
	{
	    return healthCareSites;
	}

}
