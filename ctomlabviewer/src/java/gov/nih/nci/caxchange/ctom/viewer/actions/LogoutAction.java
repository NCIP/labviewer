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
import gov.nih.nci.logging.api.user.UserInfoHelper;
import gov.nih.nci.security.AuthenticationManager;
import gov.nih.nci.security.SecurityServiceProvider;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

/**
 * @author Kunal Modi (Ekagra Software Technologies Ltd.) TODO To change the
 *         template for this generated type comment go to Window - Preferences -
 *         Java - Code Style - Code Templates
 */
public class LogoutAction extends Action
{

	private static final Logger log = Logger.getLogger(LogoutAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		/* perform login task */
		HttpSession session = request.getSession();

		UserInfoHelper.setUserInfo(((LoginForm) session
				.getAttribute(DisplayConstants.LOGIN_OBJECT)).getLoginId(),
				session.getId());
		boolean loggedInthroWEBSSO = false;
		try
		{
			// Since they may have come from another app but logged in through
			// webSSO let's check auth
			// String userEmail =
			// (String)session.getAttribute("CAGRID_SSO_EMAIL_ID");
			String userEmail = null;
			String gridIDentity =
					(String) session.getAttribute("CAGRID_SSO_GRID_IDENTITY");
			if (gridIDentity != null)
			{
				int beginIndex = gridIDentity.lastIndexOf("=");
				int endIndex = gridIDentity.length();
				userEmail = gridIDentity.substring(beginIndex + 1, endIndex);
			}
			if (userEmail != null)
			{
				loggedInthroWEBSSO = true;
			}
			else
			{
				AuthenticationManager authenticationManager =
						SecurityServiceProvider
								.getAuthenticationManager(DisplayConstants.APPLICATION_CONTEXT_NAME);
				authenticationManager.logout(((LoginForm) session
						.getAttribute(DisplayConstants.LOGIN_OBJECT))
						.getLoginId());
			}
		}
		catch (Exception e)
		{
			if (log.isDebugEnabled())
				log.debug(session.getId()
						+ "|"
						+ ((LoginForm) session
								.getAttribute(DisplayConstants.LOGIN_OBJECT))
								.getLoginId()
						+ "||Logout|Failure|Logout Called and Error occured||"
						+ e.getMessage());
			e.printStackTrace();
		}
		if (log.isDebugEnabled())
			log
					.debug(session.getId()
							+ "|"
							+ ((LoginForm) session
									.getAttribute(DisplayConstants.LOGIN_OBJECT))
									.getLoginId()
							+ "||Logout|Success|Logout Called and Forwarding to the Login Page||");
		session.removeAttribute(DisplayConstants.USER_PROVISIONING_MANAGER);
		session.removeAttribute(DisplayConstants.LOGIN_OBJECT);
		session.removeAttribute(DisplayConstants.CURRENT_TABLE_ID);
		session.removeAttribute(DisplayConstants.CURRENT_ACTION);
		session.removeAttribute(DisplayConstants.CURRENT_FORM);
		session.removeAttribute(DisplayConstants.SEARCH_RESULT);
		session.removeAttribute(DisplayConstants.SEARCH_RESULT_STUDY);
		session.removeAttribute(DisplayConstants.SEARCH_RESULT_PART);
		
		String webssoCasServer = (String)session.getAttribute("webssoCasServer");
		String webssoCasServerPort = (String)session.getAttribute("webssoCasServerPort");

		session.invalidate();
		
		if (loggedInthroWEBSSO)
		{
			ActionForward fwd = new ActionForward();
			fwd.setPath("https://" + webssoCasServer + ":" + webssoCasServerPort + "/webssoserver/logout");
			fwd.setContextRelative(false);
			fwd.setRedirect(true);
			
			return fwd;
		}
		else
		{
			return (mapping.findForward(ForwardConstants.LOGOUT_SUCCESS));
		}
	}

	/**
	 * Retrieves the UPT Context name.
	 * 
	 * @return
	 * @throws Exception
	 */
	private static String getUPTContextName() throws Exception
	{
		Document configDocument = null;
		String uptContextNameValue = null;
		String configFilePath =
				System
						.getProperty(DisplayConstants.CONFIG_FILE_PATH_PROPERTY_NAME);
		SAXBuilder builder = new SAXBuilder();
		configDocument = builder.build(new File(configFilePath));
		if (configDocument != null)
		{
			Element securityConfig = configDocument.getRootElement();
			Element uptContextName =
					securityConfig.getChild("upt-context-name");
			uptContextNameValue = uptContextName.getText().trim();
		}
		return uptContextNameValue;
	}

}
