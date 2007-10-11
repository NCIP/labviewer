/**
 * 
 */
package gov.nih.nci.caxchange.ctom.viewer.actions;

import gov.nih.nci.caxchange.ctom.viewer.constants.DisplayConstants;
import gov.nih.nci.caxchange.ctom.viewer.constants.ForwardConstants;
import gov.nih.nci.caxchange.ctom.viewer.forms.LabActivitiesSearchForm;
import gov.nih.nci.caxchange.ctom.viewer.forms.LabActivitiesSearchResultForm;
import gov.nih.nci.caxchange.ctom.viewer.forms.LoginForm;
import gov.nih.nci.logging.api.user.UserInfoHelper;
import gov.nih.nci.security.exceptions.CSException;

import java.util.ArrayList;
import java.util.HashMap;

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
 * @author asharma
 *
 */
public class LoadToCTMSAction extends Action{
	
private static final Logger logDB = Logger.getLogger(LoadToCTMSAction.class);

/* (non-Javadoc)
 * @see org.apache.struts.action.Action#execute(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
 */
public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
{
	ActionErrors errors = new ActionErrors();
	ActionMessages messages = new ActionMessages();
	
	HttpSession session = request.getSession();
	LabActivitiesSearchResultForm lForm = (LabActivitiesSearchResultForm) form;
	
	if (session.isNew() || (session.getAttribute(DisplayConstants.LOGIN_OBJECT) == null)) {
		if (logDB.isDebugEnabled())
			logDB.debug("||"+lForm.getFormName()+"|create|Failure|No Session or User Object Forwarding to the Login Page||");
		return mapping.findForward(ForwardConstants.LOGIN_PAGE);
	}
	UserInfoHelper.setUserInfo(((LoginForm)session.getAttribute(DisplayConstants.LOGIN_OBJECT)).getLoginId(), session.getId());		
	try
	{
		loadToCTMS(request, lForm);
		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(DisplayConstants.MESSAGE_ID, "Messages Submitted to CTMS Successfully"));
		saveMessages( request, messages );
	}
	catch (CSException cse)
	{
		errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(DisplayConstants.ERROR_ID, "Error in Submitting Messages to CTMS"));
		saveErrors( request,errors );
		if (logDB.isDebugEnabled())
			logDB.debug(session.getId()+"|"+((LoginForm)session.getAttribute(DisplayConstants.LOGIN_OBJECT)).getLoginId()+
				"|"+lForm.getFormName()+"|create|Failure|Error Adding the "+lForm.getFormName()+" object|"
				+form.toString()+"|"+ cse.getMessage());
	}
	session.setAttribute(DisplayConstants.CURRENT_FORM, lForm);
	if (logDB.isDebugEnabled())
		logDB.debug(session.getId()+"|"+((LoginForm)session.getAttribute(DisplayConstants.LOGIN_OBJECT)).getLoginId()+
			"|"+lForm.getFormName()+"|create|Success|Adding a  new "+lForm.getFormName()+" object|"
			+form.toString()+"|");	
	return (mapping.findForward(ForwardConstants.LOAD_TO_CTMS_EVENT_SUCCESS));
}

  /**
 * @param request
 * @param form
 * @throws Exception
 */
private void loadToCTMS(HttpServletRequest request,ActionForm form) throws Exception
  {
	   LabActivitiesSearchResultForm lForm = (LabActivitiesSearchResultForm)form;
		HashMap map = (HashMap) request.getSession().getAttribute("RESULT_SET");
		ArrayList list = new ArrayList();
		String test = lForm.getRecordId();
		/*StringTokenizer stringTokenizer = new StringTokenizer(test, ",");
		int count = stringTokenizer.countTokens();
		if (count >= 1)
		{
			while (stringTokenizer.hasMoreTokens())
			{
				list.add(map.get(stringTokenizer.nextToken()));
			}
		}
		else
		{
			list.add(map.get(lForm.getRecordId()));
		}

		EventsManager mgr =  (EventsManager) ObjectFactory.getObject("eventsManager");
		
		//TODO send the user login object instead of the username
		mgr.sendLabActivities(list, "TESTUSER");*/
		
	
		lForm.setRecordId("");
		lForm.setRecordId(null);
	}	

}
