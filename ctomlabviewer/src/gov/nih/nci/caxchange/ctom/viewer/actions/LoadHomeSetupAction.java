/**
 * 
 */
package gov.nih.nci.caxchange.ctom.viewer.actions;

import gov.nih.nci.caxchange.ctom.viewer.constants.DisplayConstants;
import gov.nih.nci.caxchange.ctom.viewer.constants.ForwardConstants;
import gov.nih.nci.caxchange.ctom.viewer.forms.BaseDBForm;
import gov.nih.nci.caxchange.ctom.viewer.forms.LoginForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

/**
 * @author asharma
 *
 */
public class LoadHomeSetupAction extends Action{

	private static final Logger logDB = Logger.getLogger(LoadHomeSetupAction.class);
	
	/* (non-Javadoc)
	 * @see org.apache.struts.action.Action#execute(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		
		HttpSession session = request.getSession();
		BaseDBForm baseDBForm = (BaseDBForm)form;
		
		if (session.isNew() || (session.getAttribute(DisplayConstants.LOGIN_OBJECT) == null)) {
			if (logDB.isDebugEnabled())
				logDB.debug("||"+baseDBForm.getFormName()+"|loadHome|Failure|No Session or User Object Forwarding to the Login Page||");
			return mapping.findForward(ForwardConstants.LOGIN_PAGE);
		}
		
		session.removeAttribute(DisplayConstants.CURRENT_ACTION);
		session.removeAttribute(DisplayConstants.CURRENT_FORM);
		session.removeAttribute(DisplayConstants.SEARCH_RESULT);

		if (logDB.isDebugEnabled())
			logDB.debug(session.getId()+"|"+((LoginForm)session.getAttribute(DisplayConstants.LOGIN_OBJECT)).getLoginId()+
					"|"+baseDBForm.getFormName()+"|loadHome|Success|Load the Home Page||");
		
		return (mapping.findForward(ForwardConstants.LOAD_HOME_SUCCESS));	
	}

}
