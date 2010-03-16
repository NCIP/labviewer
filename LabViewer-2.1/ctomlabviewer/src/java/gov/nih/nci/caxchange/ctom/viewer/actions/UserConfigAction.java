/**
 * Copyright Notice. Copyright 2008 ScenPro, Inc ("caBIG(TM)
 * Participant").caXchange was created with NCI funding and is part of the
 * caBIG(TM) initiative. The software subject to this notice and license includes
 * both human readable source code form and machine readable, binary, object
 * code form (the "caBIG(TM) Software"). This caBIG(TM) Software License (the
 * "License") is between caBIG(TM) Participant and You. "You (or "Your") shall mean
 * a person or an entity, and all other entities that control, are controlled
 * by, or are under common control with the entity. "Control" for purposes of
 * this definition means (i) the direct or indirect power to cause the direction
 * or management of such entity, whether by contract or otherwise, or (ii)
 * ownership of fifty percent (50%) or more of the outstanding shares, or (iii)
 * beneficial ownership of such entity. License. Provided that You agree to the
 * conditions described below, caBIG(TM) Participant grants You a non-exclusive,
 * worldwide, perpetual, fully-paid-up, no-charge, irrevocable, transferable and
 * royalty-free right and license in its rights in the caBIG(TM) Software,
 * including any copyright or patent rights therein, to (i) use, install,
 * disclose, access, operate, execute, reproduce, copy, modify, translate,
 * market, publicly display, publicly perform, and prepare derivative works of
 * the caBIG(TM) Software in any manner and for any purpose, and to have or permit
 * others to do so; (ii) make, have made, use, practice, sell, and offer for
 * sale, import, and/or otherwise dispose of caBIG(TM) Software (or portions
 * thereof); (iii) distribute and have distributed to and by third parties the
 * caBIG(TM) Software and any modifications and derivative works thereof; and (iv)
 * sublicense the foregoing rights set out in (i), (ii) and (iii) to third
 * parties, including the right to license such rights to further third parties.
 * For sake of clarity, and not by way of limitation, caBIG(TM) Participant shall
 * have no right of accounting or right of payment from You or Your sublicensees
 * for the rights granted under this License. This License is granted at no
 * charge to You. Your downloading, copying, modifying, displaying, distributing
 * or use of caBIG(TM) Software constitutes acceptance of all of the terms and
 * conditions of this Agreement. If you do not agree to such terms and
 * conditions, you have no right to download, copy, modify, display, distribute
 * or use the caBIG(TM) Software. 1. Your redistributions of the source code for
 * the caBIG(TM) Software must retain the above copyright notice, this list of
 * conditions and the disclaimer and limitation of liability of Article 6 below.
 * Your redistributions in object code form must reproduce the above copyright
 * notice, this list of conditions and the disclaimer of Article 6 in the
 * documentation and/or other materials provided with the distribution, if any.
 * 2. Your end-user documentation included with the redistribution, if any, must
 * include the following acknowledgment: "This product includes software
 * developed by ScenPro, Inc." If You do not include such end-user
 * documentation, You shall include this acknowledgment in the caBIG(TM) Software
 * itself, wherever such third-party acknowledgments normally appear. 3. You may
 * not use the names "ScenPro, Inc", "The National Cancer Institute", "NCI",
 * "Cancer Bioinformatics Grid" or "caBIG(TM)" to endorse or promote products
 * derived from this caBIG(TM) Software. This License does not authorize You to use
 * any trademarks, service marks, trade names, logos or product names of either
 * caBIG(TM) Participant, NCI or caBIG(TM), except as required to comply with the
 * terms of this License. 4. For sake of clarity, and not by way of limitation,
 * You may incorporate this caBIG(TM) Software into Your proprietary programs and
 * into any third party proprietary programs. However, if You incorporate the
 * caBIG(TM) Software into third party proprietary programs, You agree that You are
 * solely responsible for obtaining any permission from such third parties
 * required to incorporate the caBIG(TM) Software into such third party proprietary
 * programs and for informing Your sublicensees, including without limitation
 * Your end-users, of their obligation to secure any required permissions from
 * such third parties before incorporating the caBIG(TM) Software into such third
 * party proprietary software programs. In the event that You fail to obtain
 * such permissions, You agree to indemnify caBIG(TM) Participant for any claims
 * against caBIG(TM) Participant by such third parties, except to the extent
 * prohibited by law, resulting from Your failure to obtain such permissions. 5.
 * For sake of clarity, and not by way of limitation, You may add Your own
 * copyright statement to Your modifications and to the derivative works, and
 * You may provide additional or different license terms and conditions in Your
 * sublicenses of modifications of the caBIG(TM) Software, or any derivative works
 * of the caBIG(TM) Software as a whole, provided Your use, reproduction, and
 * distribution of the Work otherwise complies with the conditions stated in
 * this License. 6. THIS caBIG(TM) SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED
 * OR IMPLIED WARRANTIES (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE)
 * ARE DISCLAIMED. IN NO EVENT SHALL THE ScenPro, Inc OR ITS AFFILIATES BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS caBIG(TM) SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
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
