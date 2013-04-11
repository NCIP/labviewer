/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */
package gov.nih.nci.caxchange.ctom.viewer.actions;

import gov.nih.nci.caxchange.ctom.viewer.DAO.UsersDAO;
import gov.nih.nci.caxchange.ctom.viewer.beans.Users;
import gov.nih.nci.caxchange.ctom.viewer.constants.DisplayConstants;
import gov.nih.nci.caxchange.ctom.viewer.constants.ForwardConstants;
import gov.nih.nci.caxchange.ctom.viewer.forms.LoginForm;
import gov.nih.nci.caxchange.ctom.viewer.forms.UserConfigForm;
import gov.nih.nci.security.util.StringEncrypter;
import gov.nih.nci.security.util.StringEncrypter.EncryptionException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

/**
 * This class performs the User Config page setup action. The action loads the
 * User Config page after login information is authenticated. if authentication
 * fails it redirects the user to login page to enter valid login information.
 * 
 * @author asharma
 */
public class UserConfigAction extends DispatchAction
{

	private static final Logger logDB =
			Logger.getLogger(UserConfigAction.class);

	private UsersDAO userDAO = new UsersDAO();

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.struts.action.Action#execute(org.apache.struts.action.ActionMapping,
	 *      org.apache.struts.action.ActionForm,
	 *      javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public ActionForward loadUsers(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		HttpSession session = request.getSession();
		UserConfigForm baseDBForm = (UserConfigForm) form;
		// validate the login against the grid id and proxy
		if (!validLogin(request, session))
		{ // if the session is new or the login object is null; redirects the
			// user to login page
			if (session.isNew()
					|| (session.getAttribute(DisplayConstants.LOGIN_OBJECT) == null))
			{
				if (logDB.isDebugEnabled())
					logDB
							.debug("||"
									+ baseDBForm.getFormName()
									+ "|loadAdmin|Failure|No Session or User Object Forwarding to the Login Page||");
				return mapping.findForward(ForwardConstants.LOGIN_PAGE);
			}
		}
		// retrieve the users - make a DAO call
		List<Users> users = new ArrayList<Users>();
		users = userDAO.retrieveUsers();
		for (Users user : users)
		{
			String password = user.getPassword();
			user.setPassword(getDecryptedPassword(password));
		}

		// set the users list in the form
		baseDBForm.setUsersList(users);

		session.setAttribute(DisplayConstants.CURRENT_FORM, baseDBForm);

		// if the login is valid; loads the search page
		if (logDB.isDebugEnabled())
			logDB.debug(session.getId()
					+ "|"
					+ ((LoginForm) session
							.getAttribute(DisplayConstants.LOGIN_OBJECT))
							.getLoginId() + "|" + baseDBForm.getFormName()
					+ "|loadAdmin|Success|Loading the Users Page||");

		return (mapping.findForward(ForwardConstants.LOAD_USERS_SUCCESS));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.struts.action.Action#execute(org.apache.struts.action.ActionMapping,
	 *      org.apache.struts.action.ActionForm,
	 *      javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public ActionForward deleteUsers(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{

		// Errors and messages
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();

		// Http Session
		HttpSession session = request.getSession();

		// User Configuration Form
		UserConfigForm baseDBForm = (UserConfigForm) form;

		// validate the login against the grid id and proxy
		if (!validLogin(request, session))
		{ // if the session is new or the login object is null; redirects the
			// user to login page
			if (session.isNew()
					|| (session.getAttribute(DisplayConstants.LOGIN_OBJECT) == null))
			{
				if (logDB.isDebugEnabled())
					logDB
							.debug("||"
									+ baseDBForm.getFormName()
									+ "|loadAdmin|Failure|No Session or User Object Forwarding to the Login Page||");
				return mapping.findForward(ForwardConstants.LOGIN_PAGE);
			}
		}
		
		// retrieve the users from the form
		List<Users> users = baseDBForm.getUsersList();
		int selectedIndex = baseDBForm.getSelectedIndex()>0?baseDBForm.getSelectedIndex()-1:0;
				
		boolean deleted = false;
		
		//encrypt passwords for all users before submitting for delete
		 encryptAllUserPasswords(users);
		
		//check if user marked for delete
		 if(users != null && !users.isEmpty() && selectedIndex >= 0)
		 {
		   Users user = users.get(selectedIndex);
		   user.setDeleteFlag("T");
		   // call the DAO method to perform delete
	      deleted = userDAO.deleteUser(user);
			
		}	
				
		if (deleted)
		{
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					DisplayConstants.MESSAGE_ID,"The user was deleted"));
			saveMessages(request, messages);
			if (logDB.isDebugEnabled())
				logDB.debug(session.getId()
						+ "|"
						+ ((LoginForm) session
								.getAttribute(DisplayConstants.LOGIN_OBJECT))
								.getLoginId() + "|" + baseDBForm.getFormName()
						+ "|User delete|Success|User was deleted successfully"
						+ baseDBForm.getFormName() + " object|"
						+ form.toString() + "|");
		}
		else
		{
			errors
					.add(ActionErrors.GLOBAL_ERROR, new ActionError(
							DisplayConstants.ERROR_ID,
							"The user could not be deleted"));
			saveErrors(request, errors);
			if (logDB.isDebugEnabled())
				logDB.debug(session.getId()
						+ "|"
						+ ((LoginForm) session
								.getAttribute(DisplayConstants.LOGIN_OBJECT))
								.getLoginId() + "|" + baseDBForm.getFormName()
						+ "|User delete|Failure|The user could not be deleted"
						+ baseDBForm.getFormName() + " object|"
						+ form.toString() + "|");
		}

		return (mapping.findForward(ForwardConstants.RESET));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.struts.action.Action#execute(org.apache.struts.action.ActionMapping,
	 *      org.apache.struts.action.ActionForm,
	 *      javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public ActionForward addUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{

		// Errors and messages
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();

		// Http Session
		HttpSession session = request.getSession();

		// User Configuration Form
		UserConfigForm baseDBForm = (UserConfigForm) form;

		// validate the login against the grid id and proxy
		if (!validLogin(request, session))
		{ // if the session is new or the login object is null; redirects the
			// user to login page
			if (session.isNew()
					|| (session.getAttribute(DisplayConstants.LOGIN_OBJECT) == null))
			{
				if (logDB.isDebugEnabled())
					logDB
							.debug("||"
									+ baseDBForm.getFormName()
									+ "|loadAdmin|Failure|No Session or User Object Forwarding to the Login Page||");
				return mapping.findForward(ForwardConstants.LOGIN_PAGE);
			}
		}
		encryptAllUserPasswords(baseDBForm.getUsersList());
		// retrieve the user from the form
		Users user = baseDBForm.getUserBean();
		boolean userAdded = false;

		String password = user.getPassword();
		user.setPassword(getEncryptedPassword(password));

		// call the DAO method to perform add
		userAdded = userDAO.addUser(user);
		if (userAdded)
		{
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					DisplayConstants.MESSAGE_ID,"The user was successfully added"));
			saveMessages(request, messages);
			if (logDB.isDebugEnabled())
				logDB
						.debug(session.getId()
								+ "|"
								+ ((LoginForm) session
										.getAttribute(DisplayConstants.LOGIN_OBJECT))
										.getLoginId()
								+ "|"
								+ baseDBForm.getFormName()
								+ "The user {"+ user.getLoginName() +"} was successfully added"
								+ baseDBForm.getFormName() + " object |"
								+ form.toString() + "|");
		}
		else
		{
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					DisplayConstants.ERROR_ID, "The user could not be added"));
			saveErrors(request, errors);
			if (logDB.isDebugEnabled())
				logDB.debug(session.getId()
						+ "|"
						+ ((LoginForm) session
								.getAttribute(DisplayConstants.LOGIN_OBJECT))
								.getLoginId() + "|" + baseDBForm.getFormName()
						+ "The user {"+ user.getLoginName() + "} could not be added"
						+ baseDBForm.getFormName() + " object |"
						+ form.toString() + "|");
		}

		return (mapping.findForward(ForwardConstants.RESET));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.struts.action.Action#execute(org.apache.struts.action.ActionMapping,
	 *      org.apache.struts.action.ActionForm,
	 *      javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public ActionForward loadAddUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{

		// Errors and messages
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();

		// Http Session
		HttpSession session = request.getSession();

		// User Configuration Form
		UserConfigForm baseDBForm = (UserConfigForm) form;
		Users user = new Users();
		user.setUpdateDate(new Date());
		baseDBForm.setUserBean(user);

		return (mapping.findForward(ForwardConstants.LOAD_ADD_USER));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.struts.action.Action#execute(org.apache.struts.action.ActionMapping,
	 *      org.apache.struts.action.ActionForm,
	 *      javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public ActionForward loadModifyUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{

		// Errors and messages
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();

		// User Configuration Form
		UserConfigForm baseDBForm = (UserConfigForm) form;
		int selectedIndex = baseDBForm.getSelectedIndex()>0?baseDBForm.getSelectedIndex()-1:0;
		List<Users> users = baseDBForm.getUsersList();
		if(users != null && !users.isEmpty() && selectedIndex >= 0)
		{
			    Users user = users.get(selectedIndex);
			    user.setModifyFlag("T");
				user.setUpdateDate(new Date());
				baseDBForm.setUserBean(user);
						
		}

		return (mapping.findForward(ForwardConstants.LOAD_MODIFY_USER));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.struts.action.Action#execute(org.apache.struts.action.ActionMapping,
	 *      org.apache.struts.action.ActionForm,
	 *      javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public ActionForward modifyUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{

		// Errors and messages
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();

		// Http Session
		HttpSession session = request.getSession();

		// User Configuration Form
		UserConfigForm baseDBForm = (UserConfigForm) form;

		// validate the login against the grid id and proxy
		if (!validLogin(request, session))
		{ // if the session is new or the login object is null; redirects the
			// user to login page
			if (session.isNew()
					|| (session.getAttribute(DisplayConstants.LOGIN_OBJECT) == null))
			{
				if (logDB.isDebugEnabled())
					logDB
							.debug("||"
									+ baseDBForm.getFormName()
									+ "|loadAdmin|Failure|No Session or User Object Forwarding to the Login Page||");
				return mapping.findForward(ForwardConstants.LOGIN_PAGE);
			}
		}
		// retrieve the user from the form
		List<Users> users = baseDBForm.getUsersList();
		int selectedIndex = baseDBForm.getSelectedIndex()>0?baseDBForm.getSelectedIndex()-1:0;
		boolean userToModify = false;
		
		//encrypt passwords for all users before submitting for modify
		 encryptAllUserPasswords(users);
		
		//check if user marked for modify
		 if(users != null && !users.isEmpty() && selectedIndex >= 0)
		 {
			 	Users user = users.get(selectedIndex);
			 	user.setModifyFlag("T");
				userToModify = userDAO.modifyUser(user);
			
		}
		if (userToModify)
		{
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					DisplayConstants.MESSAGE_ID,"The user details were successfully modified"));
			saveMessages(request, messages);
			if (logDB.isDebugEnabled())
				logDB
						.debug(session.getId()
								+ "|"
								+ ((LoginForm) session
										.getAttribute(DisplayConstants.LOGIN_OBJECT))
										.getLoginId()
								+ "|"
								+ baseDBForm.getFormName()
								+ "|User delete|Success|User details were modified successfully"
								+ baseDBForm.getFormName() + " object|"
								+ form.toString() + "|");
		}
		else
		{
			errors
					.add(ActionErrors.GLOBAL_ERROR, new ActionError(
							DisplayConstants.ERROR_ID,
							"The user could not be deleted"));
			saveErrors(request, errors);
			if (logDB.isDebugEnabled())
				logDB
						.debug(session.getId()
								+ "|"
								+ ((LoginForm) session
										.getAttribute(DisplayConstants.LOGIN_OBJECT))
										.getLoginId()
								+ "|"
								+ baseDBForm.getFormName()
								+ "|User delete|Failure|The user details could not be modified"
								+ baseDBForm.getFormName() + " object|"
								+ form.toString() + "|");
		}

		return (mapping.findForward(ForwardConstants.RESET));
	}

	/**
	 * @param request
	 * @param session
	 * @return
	 */
	private boolean validLogin(HttpServletRequest request, HttpSession session)
	{
		boolean validated = false;
		String gridId = null;
		String gridProxy = null;

		// gets the login object from the session
		LoginForm fm =
				(LoginForm) request.getSession().getAttribute(
						DisplayConstants.LOGIN_OBJECT);
		if (fm == null)
		{
			gridId = (String) request.getParameter("gridId");
			gridProxy = (String) request.getParameter("gridProxy");
		}
		else
		{
			gridId = fm.getLoginId();
			gridProxy = fm.getGridProxy();
		}

		if (gridId != null && gridId.trim().length() > 0)
		{
			// validate the gridId
			LoginForm loginForm = new LoginForm();
			if (fm != null)
			{
				loginForm.setGridProxy(fm.getGridProxy());
				loginForm.setLoginId(fm.getLoginId());

			}
			else
			{
				loginForm = new LoginForm();
				loginForm.setLoginId(gridId);

				if (gridProxy != null && gridProxy.length() > 0)
				{
					loginForm.setGridProxy(gridProxy);
				}
			}
			session = request.getSession(true);
			session.setAttribute(DisplayConstants.LOGIN_OBJECT, loginForm);
			session.setAttribute(DisplayConstants.CURRENT_TABLE_ID,
					DisplayConstants.ADMIN_ID);
			validated = true;
		}

		return validated;
	}

	/**
	 * @param password
	 * @return
	 */
	private String getEncryptedPassword(String password) throws EncryptionException
	{
		String userPassword = null;
		StringEncrypter stringEncrypter = new StringEncrypter();
		userPassword = stringEncrypter.encrypt(password);
		
		return userPassword;

	}

	/**
	 * @param password
	 * @return
	 */
	private String getDecryptedPassword(String password) throws EncryptionException
	{
		String userPassword = null;
		StringEncrypter stringEncrypter = new StringEncrypter();
		userPassword = stringEncrypter.decrypt(password);
		
		return userPassword;

	}
	
	/**
	 * @param users
	 */
	private void encryptAllUserPasswords(List<Users> users) throws EncryptionException
	{ //encrypt all user passwords
		for (Users user : users)
		{
			String password = user.getPassword();
			user.setPassword(getEncryptedPassword(password));
		}	
	}

}
