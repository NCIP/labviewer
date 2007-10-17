/**
 * 
 */
package gov.nih.nci.caxchange.ctom.viewer.actions;

import gov.nih.nci.caxchange.ctom.viewer.constants.DisplayConstants;
import gov.nih.nci.caxchange.ctom.viewer.constants.ForwardConstants;
import gov.nih.nci.caxchange.ctom.viewer.forms.LabActivitiesSearchResultForm;
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
 * This class performs the search result page setup action. The action loads the search result page 
 * after valid search criteria has been entered in the search page. 
 * Before displaying the search results it checks if the login information is valid: 
 * if not it redirects the user to login page to enter valid login information.
 * @author asharma
 *
 */
public class LoadSearchResultSetupAction extends Action {
	
	private static final Logger logDB = Logger.getLogger(LoadSearchResultSetupAction.class);
	
	/* (non-Javadoc)
	 * @see org.apache.struts.action.Action#execute(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		//gets the session object from HttpRequest	
		HttpSession session = request.getSession();
		LabActivitiesSearchResultForm baseDBForm = (LabActivitiesSearchResultForm)form;
		//if the session is new or the login object is null; redirects the user to login page  
		if (session.isNew() || (session.getAttribute(DisplayConstants.LOGIN_OBJECT) == null)) {
			if (logDB.isDebugEnabled())
				logDB.debug("||"+baseDBForm.getFormName()+"|loadSearchResult|Failure|No Session or User Object Forwarding to the Login Page||");
			return mapping.findForward(ForwardConstants.LOGIN_PAGE);
		}
		//if the user clicks on cancel button; it loads the search page and resets the search form.
		if (isCancelled(request)){
			return (mapping.findForward(ForwardConstants.LOAD_SEARCH_SUCCESS));
		} 
		//if the login is valid and search criteria is valid; loads the search result page with search results
		if (logDB.isDebugEnabled())
			logDB.debug(session.getId()+"|"+((LoginForm)session.getAttribute(DisplayConstants.LOGIN_OBJECT)).getLoginId()+
					"|"+baseDBForm.getFormName()+"|loadSearchResult|Success|Loading the Search Result Page||");		
		
		return (mapping.findForward(ForwardConstants.LOAD_SEARCH_RESULT_SUCCESS));
	}
	
}
