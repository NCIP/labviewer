/*
 * Created on Dec 20, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package gov.nih.nci.caxchange.ctom.viewer.actions;

import gov.nih.nci.caxchange.ctom.viewer.constants.DisplayConstants;
import gov.nih.nci.caxchange.ctom.viewer.constants.ForwardConstants;
import gov.nih.nci.caxchange.ctom.viewer.forms.LabActivitiesSearchForm;
import gov.nih.nci.caxchange.ctom.viewer.forms.LoginForm;
import gov.nih.nci.caxchange.ctom.viewer.util.LabViewerAuthorizationHelper;
import gov.nih.nci.labhub.domain.II;
import gov.nih.nci.labhub.domain.SubjectAssignment;
import gov.nih.nci.security.AuthenticationManager;
import gov.nih.nci.security.SecurityServiceProvider;
import gov.nih.nci.security.exceptions.CSConfigurationException;
import gov.nih.nci.security.exceptions.CSException;
import gov.nih.nci.system.applicationservice.ApplicationService;
import gov.nih.nci.system.client.ApplicationServiceProvider;
import gov.nih.nci.system.query.cql.CQLAssociation;
import gov.nih.nci.system.query.cql.CQLAttribute;
import gov.nih.nci.system.query.cql.CQLGroup;
import gov.nih.nci.system.query.cql.CQLLogicalOperator;
import gov.nih.nci.system.query.cql.CQLObject;
import gov.nih.nci.system.query.cql.CQLPredicate;
import gov.nih.nci.system.query.cql.CQLQuery;

import java.util.Iterator;
import java.util.List;

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
		HttpSession session = request.getSession(true);
		LoginForm loginForm = (LoginForm)form;
		
		String username = loginForm.getLoginId();
		String password = loginForm.getPassword();
        /*String studySubjectGridId = loginForm.getStudySubjectGridId();
        if (studySubjectGridId !=null)
        {
        	session.setAttribute(DisplayConstants.HOT_LINK,"true"); 
			session.setAttribute("studySubjectGridId", (String)request.getParameter("studySubjectGridId"));
			log.info("studySubjectGridId"+studySubjectGridId);
        }*/
		AuthenticationManager authenticationManager = null;
		boolean loggedIn = false;
		
		try
		{
			
			// Authenticate the user
			authenticationManager = SecurityServiceProvider.getAuthenticationManager(APPLICATION_CONTEXT);
			loggedIn = authenticationManager.login(username, password);
			
			// Then check their authorization
			LabViewerAuthorizationHelper lvaHelper = new LabViewerAuthorizationHelper();
			boolean authorized = lvaHelper.isAuthorized(username);
				
			if (!authorized)
			{
				log.error("User authenticated but not authorized");
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(DisplayConstants.ERROR_ID,
							"User does not have permissions for this application"));
				saveErrors(request, errors);
				loggedIn = false;
			}
		}
		catch (CSConfigurationException e)
		{
			log.error("Error authenticating user", e);
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(DisplayConstants.ERROR_ID, e.getMessage()));			
			saveErrors(request, errors);
			loggedIn=false;
		}
		catch (CSException e)
		{
			log.error("Error authenticating user", e);
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(DisplayConstants.ERROR_ID, e.getMessage()));			
			saveErrors(request, errors);
			loggedIn=false;
		}
		
		if (loggedIn)
		{
			loginForm.setGridProxy("test");
			session.setAttribute(DisplayConstants.LOGIN_OBJECT,form);
			session.setAttribute(DisplayConstants.CURRENT_TABLE_ID,DisplayConstants.HOME_ID);
			forward = mapping.findForward(ForwardConstants.LOGIN_SUCCESS);
		}
		else
		{
			forward = mapping.findForward(ForwardConstants.LOGIN_FAILURE);
		}
		
		return forward;
	}
}
