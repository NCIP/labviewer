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
public class LoadSearchSetupAction extends Action{
	
	private static final Logger logDB = Logger.getLogger(LoadSearchSetupAction.class);
	
	/* (non-Javadoc)
	 * @see org.apache.struts.action.Action#execute(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		
		HttpSession session = request.getSession();
		BaseDBForm baseDBForm = (BaseDBForm)form;
		
		String gridId = null;
		String gridProxy = null;
		
		
		LoginForm fm = (LoginForm)request.getSession().getAttribute(DisplayConstants.LOGIN_OBJECT);
		if(fm == null){
			gridId = (String)request.getParameter("gridId");
			gridProxy = (String)request.getParameter("gridProxy");
		} else {
			gridId = fm.getLoginId();
			gridProxy = fm.getGridProxy();
		}
			
			
		if (gridId != null && gridId.trim().length() > 0)
		{
			// validate the gridId
			LoginForm loginForm = new LoginForm();
			if(fm != null){
				loginForm.setGridProxy(fm.getGridProxy());
				loginForm.setLoginId(fm.getLoginId());
				
			} else {
				loginForm = new LoginForm();
				loginForm.setLoginId(gridId);
			
				if(gridProxy != null && gridProxy.length() > 0)
					loginForm.setGridProxy(gridProxy);
			}
			session = request.getSession(true);
			session.setAttribute(DisplayConstants.LOGIN_OBJECT,loginForm);
			session.setAttribute(DisplayConstants.CURRENT_TABLE_ID,DisplayConstants.HOME_ID);
		}
		else
		{
			if (session.isNew() || (session.getAttribute(DisplayConstants.LOGIN_OBJECT) == null)) {
				if (logDB.isDebugEnabled())
					logDB.debug("||"+baseDBForm.getFormName()+"|loadSearch|Failure|No Session or User Object Forwarding to the Login Page||");
				return mapping.findForward(ForwardConstants.LOGIN_PAGE);
			}
			
			baseDBForm.resetForm();
		}
		session.setAttribute(DisplayConstants.CURRENT_ACTION, DisplayConstants.SEARCH);
		session.setAttribute(DisplayConstants.CURRENT_FORM, baseDBForm);

		if (logDB.isDebugEnabled())
			logDB.debug(session.getId()+"|"+((LoginForm)session.getAttribute(DisplayConstants.LOGIN_OBJECT)).getLoginId()+
					"|"+baseDBForm.getFormName()+"|loadSearch|Success|Loading the Search Page||");
		
		return (mapping.findForward(ForwardConstants.LOAD_SEARCH_SUCCESS));
	}

}
