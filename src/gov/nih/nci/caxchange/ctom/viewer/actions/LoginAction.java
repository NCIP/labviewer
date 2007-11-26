/*
 * Created on Dec 20, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package gov.nih.nci.caxchange.ctom.viewer.actions;

import gov.nih.nci.cabig.ctms.web.sso.Utils;
import gov.nih.nci.cagrid.authentication.bean.BasicAuthenticationCredential;
import gov.nih.nci.cagrid.authentication.stubs.types.AuthenticationProviderFault;
import gov.nih.nci.cagrid.authentication.stubs.types.InsufficientAttributeFault;
import gov.nih.nci.cagrid.authentication.stubs.types.InvalidCredentialFault;
import gov.nih.nci.caxchange.ctom.viewer.constants.DisplayConstants;
import gov.nih.nci.caxchange.ctom.viewer.constants.ForwardConstants;
import gov.nih.nci.caxchange.ctom.viewer.forms.LoginForm;
import gov.nih.nci.caxchange.ctom.viewer.util.ObjectFactory;
import gov.nih.nci.ctms.interoperability.GridProxyTransformer;
import gov.nih.nci.logging.api.user.UserInfoHelper;
import gov.nih.nci.security.AuthenticationManager;
import gov.nih.nci.security.AuthorizationManager;
import gov.nih.nci.security.SecurityServiceProvider;
import gov.nih.nci.security.UserProvisioningManager;
import gov.nih.nci.security.authorization.domainobjects.Group;
import gov.nih.nci.security.authorization.domainobjects.ProtectionElement;
import gov.nih.nci.security.authorization.domainobjects.ProtectionElementPrivilegeContext;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.security.exceptions.CSConfigurationException;
import gov.nih.nci.security.exceptions.CSException;

import java.io.File;
import java.util.Set;

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
import org.globus.gsi.GlobusCredential;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

/**
 * LoginAction handles the authentication for Lab Viewer
 *
 */
public class LoginAction extends Action 
{	
	private static final Logger log = Logger.getLogger(LoginAction.class);
	private final String APPLICATION_CONTEXT = "labviewer";
	
	/* (non-Javadoc)
	 * @see org.apache.struts.action.Action#execute(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
	{
		ActionErrors errors = new ActionErrors();
		ActionForward forward = null;
		
		LoginForm loginForm = (LoginForm)form;
		
		String username = loginForm.getLoginId();
		String password = loginForm.getPassword();

		AuthenticationManager authenticationManager = null;
		AuthorizationManager authorizationManager = null;
		UserProvisioningManager userProvisioningManager = null;
		Set<ProtectionElementPrivilegeContext> protectionElementPrivilegeContextSet = null;
		Set<ProtectionElement> pes;
		boolean loggedIn = false;
		
		try
		{
			// Authenticate the user
			authenticationManager = SecurityServiceProvider.getAuthenticationManager(APPLICATION_CONTEXT);
			loggedIn = authenticationManager.login(username, password);
			
			// Then get their authorizations
			authorizationManager = SecurityServiceProvider.getAuthorizationManager(APPLICATION_CONTEXT);
			User user = authorizationManager.getUser(username);
			
			userProvisioningManager = SecurityServiceProvider.getUserProvisioningManager(APPLICATION_CONTEXT);
			
			// If we could not get the user object then force an error
			if (user == null)
			{
				log.error("Error finding user");
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(DisplayConstants.ERROR_ID, "No such user"));
				saveErrors(request, errors);
				loggedIn = false;
			}
			else
			{
				log.debug("Got user");
				
				// Now check if they have the proper protection element
				if (userProvisioningManager != null)
					protectionElementPrivilegeContextSet =
						userProvisioningManager.getProtectionElementPrivilegeContextForUser(user.getUserId().toString());
				
				boolean authorized = false;
				
				if(protectionElementPrivilegeContextSet != null)
				{
					for(ProtectionElementPrivilegeContext pepc: protectionElementPrivilegeContextSet)
					{
						ProtectionElement pe = pepc.getProtectionElement();
						log.debug("Protection Element: " + pe.getProtectionElementName());
						if (pe.getProtectionElementName().equalsIgnoreCase("labviewer"))
							authorized = true;
					}
				}
				
				if (!authorized)
				{
					log.error("User authenticated but not authorized");
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(DisplayConstants.ERROR_ID,
							"User does not have permissions for this application"));
					saveErrors(request, errors);
					loggedIn = false;
				}
				
			}
		}
		catch (CSConfigurationException e)
		{
			log.error("Error authenticating user", e);
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(DisplayConstants.ERROR_ID, e.getMessage()));			
			saveErrors(request, errors);
		}
		catch (CSException e)
		{
			log.error("Error authenticating user", e);
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(DisplayConstants.ERROR_ID, e.getMessage()));			
			saveErrors(request, errors);
		}
		
		if (loggedIn)
		{
			loginForm.setGridProxy("test");
		
			HttpSession session = request.getSession(true);
			session.setAttribute(DisplayConstants.LOGIN_OBJECT,form);
			session.setAttribute(DisplayConstants.CURRENT_TABLE_ID,DisplayConstants.HOME_ID);
			
			forward = mapping.findForward(ForwardConstants.LOGIN_SUCCESS);
		}
		else
		{
			forward = new ActionForward(mapping.getInput());
		}
		
		/*
		BasicAuthenticationCredential bac = new BasicAuthenticationCredential();
		bac.setUserId(loginForm.getLoginId());
		bac.setPassword(loginForm.getPassword());
		
		GridProxyTransformer transformer = (GridProxyTransformer) ObjectFactory.getObject("gridProxyTransformerImpl");
		

			GlobusCredential cred;
			try {
				cred = transformer.transform(bac);
			} catch (Exception ex) {
				Throwable e = ex.getCause();
				String message = null;
				if(e instanceof InvalidCredentialFault){
					message = "Invalid Login Id or Password";
				}else {
					message = "Login Application Error";
				}
					
				
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(DisplayConstants.ERROR_ID, message));			
				saveErrors( request,errors );
				if (log.isDebugEnabled())
					log.debug("|"+loginForm.getLoginId()+
							"||Login|Failure|Login Failed for user name "+loginForm.getLoginId()+" "+loginForm.toString()+"|"+ex.getMessage());
				
				return mapping.findForward(ForwardConstants.LOGIN_FAILURE);
			} 
			
			
			String  pr = Utils.toString((GlobusCredential)cred);
			
			loginForm.setGridProxy(pr);
			*/
		
		return forward;
	}
		
}
