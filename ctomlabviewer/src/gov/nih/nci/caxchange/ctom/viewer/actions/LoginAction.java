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
		
		LoginForm loginForm = (LoginForm)form;
		
		String username = loginForm.getLoginId();
		String password = loginForm.getPassword();

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
		
			HttpSession session = request.getSession(true);
			session.setAttribute(DisplayConstants.LOGIN_OBJECT,form);
			session.setAttribute(DisplayConstants.CURRENT_TABLE_ID,DisplayConstants.HOME_ID);
			if(session.getAttribute("HOT_LINK")=="true")
			{  
				LabActivitiesSearchForm labFm=(LabActivitiesSearchForm)session.getAttribute("CURRENT_FORM");
				try {
					
					getPatientIdRoot((String)session.getAttribute("studySubjectGridId"),labFm);
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				session.setAttribute("CURRENT_FORM", labFm);
			  	String operation= "execute";
				return (mapping.findForward(ForwardConstants.LOGIN_SUCCESS_HOTLINK));
			}	
			
			forward = mapping.findForward(ForwardConstants.LOGIN_SUCCESS);
		}
		else
		{
			forward = mapping.findForward(ForwardConstants.LOGIN_FAILURE);
		}
		
		return forward;
	}
	/**
	 * 
	 * Select i.extension from identifier i where i.protocol_id in (
	 * select ss.protocol_id from study_site ss, study_participant_assignment spa, identifier ii where ((ii.root='50113a1a-4c3e-4418-8c37-7383cba1aaf8' and ii.study_participant_assignmnt_id=spa.id 
	 * and spa.study_site_id=ss.id))) UNION
	 * select i.extension from identifier i where i.root='50113a1a-4c3e-4418-8c37-7383cba1aaf8' and i.study_participant_assignmnt_id is not null
	 * @param studyGridId
	 * @param labFm
	 * @throws Exception
	 */
	private void getPatientIdRoot(String studyGridId,LabActivitiesSearchForm labFm) throws Exception
	{
		
		try
		{
			ApplicationService appService = ApplicationServiceProvider.getApplicationService();
			
			// Create the query to get SubjectAssignment object
			CQLQuery query = new CQLQuery();
			CQLObject target = new CQLObject();
			target.setName("gov.nih.nci.labhub.domain.SubjectAssignment");
			//target.setAttribute(new CQLAttribute("studySubjectIdentifier",
					//CQLPredicate.IS_NOT_NULL,""));
			
			// Set the subject Identifier on the association to II
			CQLAssociation subjectAssignmentAssociation1 = new CQLAssociation();
			subjectAssignmentAssociation1.setName("gov.nih.nci.labhub.domain.II");
			subjectAssignmentAssociation1.setTargetRoleName("studySubjectIdentifier");
			subjectAssignmentAssociation1.setAttribute(new CQLAttribute("root",
					CQLPredicate.EQUAL_TO, studyGridId.trim()));
			
			// Now get to StudySite
			CQLAssociation subjectAssignmentAssociation2 = new CQLAssociation();
			subjectAssignmentAssociation2.setName("gov.nih.nci.labhub.domain.StudySite");
			subjectAssignmentAssociation2.setTargetRoleName("studySite");

			// Now get to Study
			CQLAssociation studySiteAssociation1 = new CQLAssociation();
			studySiteAssociation1.setName("gov.nih.nci.labhub.domain.Study");
			studySiteAssociation1.setTargetRoleName("study");
			subjectAssignmentAssociation2.setAssociation(studySiteAssociation1);
			
			
			CQLGroup finalgroup = new CQLGroup();
			finalgroup.addAssociation(subjectAssignmentAssociation1);
			finalgroup.addAssociation(subjectAssignmentAssociation2);
			finalgroup.setLogicOperator(CQLLogicalOperator.AND);
			target.setGroup(finalgroup);
			query.setTarget(target);
			
			List resultList = appService.query(query);
			for (Iterator resultsIterator = resultList.iterator(); resultsIterator.hasNext();)
			{
				SubjectAssignment ii = (SubjectAssignment) resultsIterator.next();
				for(Iterator i = ii.getStudySubjectIdentifier().iterator();i.hasNext();)
				{	
					II ident = (II)i.next();
					labFm.setPatientId(ident.getExtension());
					//System.out.println(ident.getExtension());
				}
				for(Iterator i = ii.getStudySite().getStudy().getStudyIdentifier().iterator();i.hasNext();)
				{	
					II ident = (II)i.next();
					labFm.setStudyId(ident.getExtension());
					//System.out.println(ident.getExtension());
				}
			}		
		}
		catch(Exception e){}
		}	
}
