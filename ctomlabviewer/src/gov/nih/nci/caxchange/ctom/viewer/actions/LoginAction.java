/*
 * Created on Dec 20, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package gov.nih.nci.caxchange.ctom.viewer.actions;

/**
 *
 *<!-- LICENSE_TEXT_START -->
 *
 *The NCICB Common Security Module's User Provisioning Tool (CTOM LAB Viewer) Software License,
 *Version 3.0 Copyright 2004-2005 Ekagra Software Technologies Limited ('Ekagra')
 *
 *Copyright Notice.  The software subject to this notice and license includes both
 *human readable source code form and machine readable, binary, object code form
 *(the 'CTOM LAB Viewer Software').  The CTOM LAB Viewer Software was developed in conjunction with the
 *National Cancer Institute ('NCI') by NCI employees and employees of Ekagra.  To
 *the extent government employees are authors, any rights in such works shall be
 *subject to Title 17 of the United States Code, section 105.    
 *
 *This CTOM LAB Viewer Software License (the 'License') is between NCI and You.  'You (or
 *'Your') shall mean a person or an entity, and all other entities that control,
 *are controlled by, or are under common control with the entity.  'Control' for
 *purposes of this definition means (i) the direct or indirect power to cause the
 *direction or management of such entity, whether by contract or otherwise, or
 *(ii) ownership of fifty percent (50%) or more of the outstanding shares, or
 *(iii) beneficial ownership of such entity.  
 *
 *This License is granted provided that You agree to the conditions described
 *below.  NCI grants You a non-exclusive, worldwide, perpetual, fully-paid-up,
 *no-charge, irrevocable, transferable and royalty-free right and license in its
 *rights in the CTOM LAB Viewer Software to (i) use, install, access, operate, execute, copy,
 *modify, translate, market, publicly display, publicly perform, and prepare
 *derivative works of the CTOM LAB Viewer Software; (ii) distribute and have distributed to
 *and by third parties the CTOM LAB Viewer Software and any modifications and derivative works
 *thereof; and (iii) sublicense the foregoing rights set out in (i) and (ii) to
 *third parties, including the right to license such rights to further third
 *parties.  For sake of clarity, and not by way of limitation, NCI shall have no
 *right of accounting or right of payment from You or Your sublicensees for the
 *rights granted under this License.  This License is granted at no charge to You.
 *
 *1.	Your redistributions of the source code for the Software must retain the
 *above copyright notice, this list of conditions and the disclaimer and
 *limitation of liability of Article 6 below.  Your redistributions in object code
 *form must reproduce the above copyright notice, this list of conditions and the
 *disclaimer of Article 6 in the documentation and/or other materials provided
 *with the distribution, if any.
 *2.	Your end-user documentation included with the redistribution, if any, must
 *include the following acknowledgment: 'This product includes software developed
 *by Ekagra and the National Cancer Institute.'  If You do not include such
 *end-user documentation, You shall include this acknowledgment in the Software
 *itself, wherever such third-party acknowledgments normally appear.
 *
 *3.	You may not use the names 'The National Cancer Institute', 'NCI' 'Ekagra
 *Software Technologies Limited' and 'Ekagra' to endorse or promote products
 *derived from this Software.  This License does not authorize You to use any
 *trademarks, service marks, trade names, logos or product names of either NCI or
 *Ekagra, except as required to comply with the terms of this License.
 *
 *4.	For sake of clarity, and not by way of limitation, You may incorporate this
 *Software into Your proprietary programs and into any third party proprietary
 *programs.  However, if You incorporate the Software into third party proprietary
 *programs, You agree that You are solely responsible for obtaining any permission
 *from such third parties required to incorporate the Software into such third
 *party proprietary programs and for informing Your sublicensees, including
 *without limitation Your end-users, of their obligation to secure any required
 *permissions from such third parties before incorporating the Software into such
 *third party proprietary software programs.  In the event that You fail to obtain
 *such permissions, You agree to indemnify NCI for any claims against NCI by such
 *third parties, except to the extent prohibited by law, resulting from Your
 *failure to obtain such permissions.
 *
 *5.	For sake of clarity, and not by way of limitation, You may add Your own
 *copyright statement to Your modifications and to the derivative works, and You
 *may provide additional or different license terms and conditions in Your
 *sublicenses of modifications of the Software, or any derivative works of the
 *Software as a whole, provided Your use, reproduction, and distribution of the
 *Work otherwise complies with the conditions stated in this License.
 *
 *6.	THIS SOFTWARE IS PROVIDED 'AS IS,' AND ANY EXPRESSED OR IMPLIED WARRANTIES,
 *(INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 *NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED.  IN NO
 *EVENT SHALL THE NATIONAL CANCER INSTITUTE, EKAGRA, OR THEIR AFFILIATES BE LIABLE
 *FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 *DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRCTOM LAB ViewerION) HOWEVER
 *CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
 *TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 *THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *<!-- LICENSE_TEXT_END -->
 *
 */


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
import gov.nih.nci.security.exceptions.CSException;

import java.io.File;

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
import org.globus.gsi.GlobusCredential;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

/**
 * @author Kunal Modi (Ekagra Software Technologies Ltd.)
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class LoginAction extends Action 
{	
	private static final Logger log = Logger.getLogger(LoginAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	{
		ActionErrors errors = new ActionErrors();
		
		LoginForm loginForm = (LoginForm)form;

//		AuthenticationManager authenticationManager = null;
//		boolean loginSuccessful = false;
//		
//		LoginForm loginForm = (LoginForm)form;
//		UserInfoHelper.setUserInfo(loginForm.getLoginId(), request.getSession().getId());
//		errors.clear();
//		try
//		{
//			authenticationManager = SecurityServiceProvider.getAuthenticationManager(DisplayConstants.APPLICATION_CONTEXT_NAME);
//			if (null == authenticationManager)
//			{
//				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(DisplayConstants.ERROR_ID, "Unable to initialize Authentication Manager for the given application context"));			
//				saveErrors( request,errors );
//				if (log.isDebugEnabled())
//					log.debug("|"+loginForm.getLoginId()+
//							"||Login|Failure|Unable to instantiate AuthenticationManager for CTOM LAB Viewer application||");
//				return mapping.findForward(ForwardConstants.LOGIN_FAILURE);
//			}
//		}
//		catch (CSException cse)
//		{
//			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(DisplayConstants.ERROR_ID, cse.getMessage()));			
//			saveErrors( request,errors );
//			if (log.isDebugEnabled())
//				log.debug("|"+loginForm.getLoginId()+
//						"||Login|Failure|Unable to instantiate AuthenticationManager for CTOM LAB Viewer application|"+loginForm.toString()+"|"+cse.getMessage());
//			return mapping.findForward(ForwardConstants.LOGIN_FAILURE);
//		}
//		try
//		{
//			loginSuccessful = authenticationManager.login(loginForm.getLoginId(),loginForm.getPassword());
//		}
//		catch (CSException cse)
//		{
//			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(DisplayConstants.ERROR_ID, cse.getMessage()));			
//			saveErrors( request,errors );
//			if (log.isDebugEnabled())
//				log.debug("|"+loginForm.getLoginId()+
//						"||Login|Failure|Login Failed for user name "+loginForm.getLoginId()+" "+loginForm.toString()+"|"+cse.getMessage());
//			return mapping.findForward(ForwardConstants.LOGIN_FAILURE);
//		}
//				
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
		loginForm.setGridProxy("test");
			
		HttpSession session = request.getSession(true);
		session.setAttribute(DisplayConstants.LOGIN_OBJECT,form);
		session.setAttribute(DisplayConstants.CURRENT_TABLE_ID,DisplayConstants.HOME_ID);
		
//		if (log.isDebugEnabled())
//			log.debug(session.getId()+"|"+((LoginForm)session.getAttribute(DisplayConstants.LOGIN_OBJECT)).getLoginId()+
//			"||Login|Success|Login Successful for user "+loginForm.getLoginId()+" application, Forwarding to the Home Page||");
		return (mapping.findForward(ForwardConstants.LOGIN_SUCCESS));
	}
		
}
