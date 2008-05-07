/**
 * Copyright Notice.  Copyright 2008  Scenpro, Inc (“caBIG™ Participant”).caXchange
 * was created with NCI funding and is part of the caBIG™ initiative. 
 * The software subject to this notice and license includes both human readable source code form and 
 * machine readable, binary, object code form (the “caBIG™ Software”).
 * This caBIG™ Software License (the “License”) is between caBIG™ Participant and You.  
 * “You (or “Your”) shall mean a person or an entity, and all other entities that control, 
 * are controlled by, or are under common control with the entity.  “Control” for purposes of this 
 * definition means (i) the direct or indirect power to cause the direction or management of such entity, 
 * whether by contract or otherwise, or (ii) ownership of fifty percent (50%) or more of the outstanding shares, 
 * or (iii) beneficial ownership of such entity.  
 * License.  Provided that You agree to the conditions described below, caBIG™ Participant grants 
 * You a non-exclusive, worldwide, perpetual, fully-paid-up, no-charge, irrevocable, transferable and 
 * royalty-free right and license in its rights in the caBIG™ Software, including any copyright or patent rights therein, to 
 * (i) use, install, disclose, access, operate, execute, reproduce, copy, modify, translate, market, publicly display, 
 * publicly perform, and prepare derivative works of the caBIG™ Software in any manner and for any purpose, and to have 
 * or permit others to do so; (ii) make, have made, use, practice, sell, and offer for sale, import, and/or otherwise 
 * dispose of caBIG™ Software (or portions thereof); (iii) distribute and have distributed to and by third parties the 
 * caBIG™ Software and any modifications and derivative works thereof; and (iv) sublicense the foregoing rights 
 * set out in (i), (ii) and (iii) to third parties, including the right to license such rights to further third parties.  
 * For sake of clarity, and not by way of limitation, caBIG™ Participant shall have no right of accounting or right of payment
 *  from You or Your sublicensees for the rights granted under this License.  This License is granted at no charge to You.  
 *  Your downloading, copying, modifying, displaying, distributing or use of caBIG™ Software constitutes acceptance of all 
 *  of the terms and conditions of this Agreement.  If you do not agree to such terms and conditions, you have no right to 
 *  download, copy, modify, display, distribute or use the caBIG™ Software.  
 * 1.	Your redistributions of the source code for the caBIG™ Software must retain the above copyright notice, 
 * 		this list of conditions and the disclaimer and limitation of liability of Article 6 below.  
 * 		Your redistributions in object code form must reproduce the above copyright notice, this list of conditions and 
 * 		the disclaimer of Article 6 in the documentation and/or other materials provided with the distribution, if any.
 * 2.	Your end-user documentation included with the redistribution, if any, must include the following acknowledgment: 
 * 		“This product includes software developed by Scenpro, Inc.”  
 * 		If You do not include such end-user documentation, You shall include this acknowledgment in the caBIG™ Software itself, 
 * 		wherever such third-party acknowledgments normally appear.
 * 3.	You may not use the names  “Scenpro, Inc”, 
 * 		“The National Cancer Institute”, “NCI”, “Cancer Bioinformatics Grid” or “caBIG™” to endorse or promote products 
 * 		derived from this caBIG™ Software.  This License does not authorize You to use any trademarks, service marks, trade names,
 * 		logos or product names of either caBIG™ Participant, NCI or caBIG™, except as required to comply with the terms of this 
 * 		License.
 * 4.	For sake of clarity, and not by way of limitation, You may incorporate this caBIG™ Software into Your proprietary 
 * 		programs and into any third party proprietary programs.  However, if You incorporate the caBIG™ Software into third party 
 * 		proprietary programs, You agree that You are solely responsible for obtaining any permission from such third parties 
 * 		required to incorporate the caBIG™ Software into such third party proprietary programs and for informing Your sublicensees, 
 * 		including without limitation Your end-users, of their obligation to secure any required permissions from such third parties 
 * 		before incorporating the caBIG™ Software into such third party proprietary software programs.  In the event that You fail to 
 * 		obtain such permissions, You agree to indemnify caBIG™ Participant for any claims against caBIG™ Participant by such third 
 * 		parties, except to the extent prohibited by law, resulting from Your failure to obtain such permissions.
 * 5.	For sake of clarity, and not by way of limitation, You may add Your own copyright statement to Your modifications and 
 * 		to the derivative works, and You may provide additional or different license terms and conditions in Your sublicenses of 
 * 		modifications of the caBIG™ Software, or any derivative works of the caBIG™ Software as a whole, provided Your use, reproduction, 
 * 		and distribution of the Work otherwise complies with the conditions stated in this License.
 * 6.	THIS caBIG™ SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED WARRANTIES (INCLUDING, BUT NOT LIMITED TO, 
 * 		THE IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED.  
 * 		IN NO EVENT SHALL THE Scenpro, Inc OR ITS AFFILIATES 
 * 		BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * 		PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON 
 * 		ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY 
 * 		OUT OF THE USE OF THIS caBIG™ SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * 
 */

package gov.nih.nci.caxchange.ctom.viewer.actions;

import java.util.Iterator;
import java.util.List;

import gov.nih.nci.caxchange.ctom.viewer.constants.DisplayConstants;
import gov.nih.nci.caxchange.ctom.viewer.constants.ForwardConstants;
import gov.nih.nci.caxchange.ctom.viewer.forms.LabActivitiesSearchForm;
import gov.nih.nci.caxchange.ctom.viewer.forms.LoginForm;
import gov.nih.nci.caxchange.ctom.viewer.util.LabViewerAuthorizationHelper;
import gov.nih.nci.labhub.domain.II;
import gov.nih.nci.labhub.domain.SubjectAssignment;
import gov.nih.nci.system.applicationservice.ApplicationService;
import gov.nih.nci.system.client.ApplicationServiceProvider;
import gov.nih.nci.system.query.cql.CQLAssociation;
import gov.nih.nci.system.query.cql.CQLAttribute;
import gov.nih.nci.system.query.cql.CQLGroup;
import gov.nih.nci.system.query.cql.CQLLogicalOperator;
import gov.nih.nci.system.query.cql.CQLObject;
import gov.nih.nci.system.query.cql.CQLPredicate;
import gov.nih.nci.system.query.cql.CQLQuery;

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
 * @author asharma
 *
 */
public class StudySubjectAction extends Action
{

	private static final Logger log = Logger.getLogger(StudySubjectAction.class);
	
	/* (non-Javadoc)
	 * @see org.apache.struts.action.Action#execute(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception 
	{
		HttpSession session = request.getSession();
		LabActivitiesSearchForm baseDBForm = new LabActivitiesSearchForm();
		ActionErrors errors = new ActionErrors();
		ActionForward forward = null;
		String studySubjectGridId = (String)request.getParameter("studySubjectGridId");
		
		log.debug("studySubjectGridId is " + studySubjectGridId);
		
		if(studySubjectGridId != null)
		{
			// Since they may have come from another app but logged in through webSSO let's check auth
			String userEmail = (String)session.getAttribute("CAGRID_SSO_EMAIL_ID");
			
			if (userEmail != null)
			{
				boolean loggedIn = true;
				LoginForm loginForm = new LoginForm();
				
				String username = userEmail;
				loginForm.setLoginId(username);
				
				// Check their authorization
				LabViewerAuthorizationHelper lvaHelper = new LabViewerAuthorizationHelper();
				boolean authorized = lvaHelper.isAuthorized(username);
						
				if (!authorized)
				{
					log.error("User authenticated but not authorized");
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(DisplayConstants.ERROR_ID,
								"User does not have permissions for this application"));
					saveErrors(request, errors);
					loggedIn = false;
					forward = mapping.findForward(ForwardConstants.LOGIN_FAILURE);
				}
				
				if (loggedIn)
				{
					loginForm.setGridProxy("test");
					session.setAttribute(DisplayConstants.LOGIN_OBJECT,loginForm);
					session.setAttribute(DisplayConstants.CURRENT_TABLE_ID,DisplayConstants.HOME_ID);
				}
			}
			
			// Then proceed on
			session.setAttribute(DisplayConstants.HOT_LINK,"true"); 
			session.setAttribute("studySubjectGridId", studySubjectGridId);
			session.setAttribute(DisplayConstants.CURRENT_ACTION, DisplayConstants.SEARCH);
			session.setAttribute(DisplayConstants.CURRENT_FORM, baseDBForm);
			session.setAttribute(DisplayConstants.CURRENT_TABLE_ID,DisplayConstants.HOME_ID);
			LabActivitiesSearchForm labFm = (LabActivitiesSearchForm)session.getAttribute("CURRENT_FORM");
			
			try
			{
				log.info("Get Patient info");
				getPatientIdRoot((String)session.getAttribute("studySubjectGridId"),labFm);
				
			}
			catch (Exception e)
			{
				log.error("Error retreiving patient information",e);
			}
			
			if(labFm.getStudyId()==null)
			{
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
						DisplayConstants.ERROR_ID,
						"No Patient Information associated with that study"));
				saveErrors(request, errors);
				log.debug("No Patient Information associated with that study");
				
				forward = mapping.findForward(ForwardConstants.SEARCH_FAILURE);
			}
			
			session.setAttribute("CURRENT_FORM", labFm);
			forward = mapping.findForward(ForwardConstants.LOGIN_SUCCESS_HOTLINK);
		}
		else
		{
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					DisplayConstants.ERROR_ID,
					"No Patient Information associated with that study"));
			saveErrors(request, errors);
			log.debug("No Patient Information associated with that study");
			
			forward = mapping.findForward(ForwardConstants.SEARCH_FAILURE);
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
					//System.out.println("Study gridid"+studyGridId);
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


