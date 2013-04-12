/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */
package gov.nih.nci.caxchange.ctom.viewer.actions;

import gov.nih.nci.caxchange.ctom.viewer.constants.DisplayConstants;
import gov.nih.nci.caxchange.ctom.viewer.constants.ForwardConstants;
import gov.nih.nci.caxchange.ctom.viewer.forms.AdministrationForm;
import gov.nih.nci.caxchange.ctom.viewer.forms.LoginForm;
import gov.nih.nci.caxchange.ctom.viewer.util.CommonUtil;
import gov.nih.nci.logging.api.user.UserInfoHelper;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

/**
 * This class Saves the Lab data Search results to a CSV file. It checks if
 * valid login information is in session; if not it redirects the user to login
 * page.
 * 
 * @author asharma
 */
public class SaveAdminConfgAction extends Action
{
	private static final Logger logDB =
			Logger.getLogger(SaveAdminConfgAction.class);
	private static final String CONFIG_FILE = "baseURL.properties";

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.struts.action.Action#execute(org.apache.struts.action.ActionMapping,
	 *      org.apache.struts.action.ActionForm,
	 *      javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		
		HttpSession session = request.getSession();
		AdministrationForm aForm = (AdministrationForm) form;

		// if the session is new or the login object is null; redirects the user
		// to login page
		if (session.isNew()
				|| (session.getAttribute(DisplayConstants.LOGIN_OBJECT) == null))
		{
			logDB
					.error("No Session or User Object Forwarding to the Login Page");
			return mapping.findForward(ForwardConstants.LOGIN_PAGE);
		}

		String username =
				((LoginForm) session
						.getAttribute(DisplayConstants.LOGIN_OBJECT))
						.getLoginId();

		UserInfoHelper.setUserInfo(username, session.getId());

		try
		{	
			//save the user set properties for the urls to a properties file
			saveToPropertiesFile(aForm,session);
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					DisplayConstants.MESSAGE_ID,
					"Configuration Saved Successfully"));
			saveMessages(request, messages);
			logDB.info(new ActionMessage(DisplayConstants.MESSAGE_ID,
					"Configuration Saved Successfully"));

		}
		catch (Exception cse)
		{
			String msg = cse.getMessage();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					DisplayConstants.ERROR_ID,
					"Error in Saving Configuration: " + msg));
			saveErrors(request, errors);
			logDB.error("Error saving lConfiguration", cse);
		}
		session.setAttribute(DisplayConstants.CURRENT_FORM, aForm);

		// if the login is valid and the selected form data is successfully
		// saved to a csv file;
		// it returns to the search results page and displays the save
		// successful message
		return (mapping.findForward(ForwardConstants.UPDATE_SUCCESS));
	}


	/**
	 * Saves the Properties updated/entered by the user
	 * @param aForm
	 * @param session
	 */
	private void saveToPropertiesFile(AdministrationForm aForm,HttpSession session)
	{
		try
		{
			//get the path of the BaseURL properties file from the session
			String path = (String) session.getAttribute("propertyFilePath");
			                    
			File file = new File(session.getServletContext().getRealPath(path));
			Properties propsToUpdate = new Properties();
			propsToUpdate.put("BaseURLcaAERS", aForm.getCaaersUrl());
			propsToUpdate.put("BaseURLC3D", aForm.getC3dUrl());
			propsToUpdate.put("BaseURLC3PR", aForm.getC3prUrl());
			propsToUpdate.put("tissueURL",aForm.getTissueUrl());
			propsToUpdate.put("url", aForm.getCaxUrl());
			OutputStream oStream =
					new BufferedOutputStream(new FileOutputStream(file));
			propsToUpdate.store(oStream, "");
			oStream.close();

            /* also update cache */
            CommonUtil.props1.put("BaseURLcaAERS", aForm.getCaaersUrl());
			CommonUtil.props1.put("BaseURLC3D", aForm.getC3dUrl());
			CommonUtil.props1.put("BaseURLC3PR", aForm.getC3prUrl());
			CommonUtil.props1.put("tissueURL",aForm.getTissueUrl());
			CommonUtil.props1.put("url", aForm.getCaxUrl());
             
        }
		catch (FileNotFoundException e1)
		{
			logDB.error("The config file not found: " + CONFIG_FILE);
		}
		catch (IOException e1)
		{
			logDB.error("Error writing to the config file: " + CONFIG_FILE);
		}
		//after saving the properties to the file,load it into session.
		session.setAttribute("BaseURLcaAERS", aForm.getCaaersUrl());
		session.setAttribute("BaseURLC3PR", aForm.getC3prUrl());
		session.setAttribute("caXchangeURL", aForm.getCaxUrl());
		session.setAttribute("BaseURLC3D", aForm.getC3dUrl());
		session.setAttribute("tissueURL", aForm.getTissueUrl());
	}

}
