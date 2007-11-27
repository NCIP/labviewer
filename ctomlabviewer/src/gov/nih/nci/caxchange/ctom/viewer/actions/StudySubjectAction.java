/**
 * 
 */
package gov.nih.nci.caxchange.ctom.viewer.actions;

import gov.nih.nci.caxchange.ctom.viewer.constants.DisplayConstants;
import gov.nih.nci.caxchange.ctom.viewer.constants.ForwardConstants;
import gov.nih.nci.caxchange.ctom.viewer.forms.LabActivitiesSearchForm;
import gov.nih.nci.caxchange.ctom.viewer.forms.LoginForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
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
		
		if (session.isNew() || (session.getAttribute(DisplayConstants.LOGIN_OBJECT) == null)) {
			
		if((request.getParameter("studySubjectGridId"))!=null){
			session.setAttribute(DisplayConstants.HOT_LINK,"true"); 
			session.setAttribute("studySubjectGridId", (String)request.getParameter("studySubjectGridId"));
			session.setAttribute(DisplayConstants.CURRENT_ACTION, DisplayConstants.SEARCH);
			session.setAttribute(DisplayConstants.CURRENT_FORM, baseDBForm);
			session.setAttribute(DisplayConstants.CURRENT_TABLE_ID,DisplayConstants.HOME_ID);
			}
			if (log.isDebugEnabled())
				log.debug("||||Failure|No Session or User Object Forwarding to the Login Page||");
			return mapping.findForward(ForwardConstants.LOGIN_PAGE);
		}
		/*
		 * clear the junk in the session here
		 */
		session.removeAttribute(DisplayConstants.CURRENT_ACTION);
		session.removeAttribute(DisplayConstants.CURRENT_FORM);
		session.removeAttribute(DisplayConstants.SEARCH_RESULT);
		session.setAttribute(DisplayConstants.HOT_LINK,"false");
		session.setAttribute(DisplayConstants.CURRENT_TABLE_ID,DisplayConstants.HOME_ID);

		if (log.isDebugEnabled())
			log.debug(session.getId()+"|"+((LoginForm)session.getAttribute(DisplayConstants.LOGIN_OBJECT)).getLoginId()+
					"|Home|Redirect|Success|Already Logged In and Forwarding to the Home Page||");
		
		return mapping.findForward(ForwardConstants.HOME_PAGE);
	}

}
