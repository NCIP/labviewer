/**
 * Copyright Notice.  Copyright 2008  Scenpro, Inc (�caBIG� Participant�).caXchange
 * was created with NCI funding and is part of the caBIG� initiative. 
 * The software subject to this notice and license includes both human readable source code form and 
 * machine readable, binary, object code form (the �caBIG� Software�).
 * This caBIG� Software License (the �License�) is between caBIG� Participant and You.  
 * �You (or �Your�) shall mean a person or an entity, and all other entities that control, 
 * are controlled by, or are under common control with the entity.  �Control� for purposes of this 
 * definition means (i) the direct or indirect power to cause the direction or management of such entity, 
 * whether by contract or otherwise, or (ii) ownership of fifty percent (50%) or more of the outstanding shares, 
 * or (iii) beneficial ownership of such entity.  
 * License.  Provided that You agree to the conditions described below, caBIG� Participant grants 
 * You a non-exclusive, worldwide, perpetual, fully-paid-up, no-charge, irrevocable, transferable and 
 * royalty-free right and license in its rights in the caBIG� Software, including any copyright or patent rights therein, to 
 * (i) use, install, disclose, access, operate, execute, reproduce, copy, modify, translate, market, publicly display, 
 * publicly perform, and prepare derivative works of the caBIG� Software in any manner and for any purpose, and to have 
 * or permit others to do so; (ii) make, have made, use, practice, sell, and offer for sale, import, and/or otherwise 
 * dispose of caBIG� Software (or portions thereof); (iii) distribute and have distributed to and by third parties the 
 * caBIG� Software and any modifications and derivative works thereof; and (iv) sublicense the foregoing rights 
 * set out in (i), (ii) and (iii) to third parties, including the right to license such rights to further third parties.  
 * For sake of clarity, and not by way of limitation, caBIG� Participant shall have no right of accounting or right of payment
 *  from You or Your sublicensees for the rights granted under this License.  This License is granted at no charge to You.  
 *  Your downloading, copying, modifying, displaying, distributing or use of caBIG� Software constitutes acceptance of all 
 *  of the terms and conditions of this Agreement.  If you do not agree to such terms and conditions, you have no right to 
 *  download, copy, modify, display, distribute or use the caBIG� Software.  
 * 1.	Your redistributions of the source code for the caBIG� Software must retain the above copyright notice, 
 * 		this list of conditions and the disclaimer and limitation of liability of Article 6 below.  
 * 		Your redistributions in object code form must reproduce the above copyright notice, this list of conditions and 
 * 		the disclaimer of Article 6 in the documentation and/or other materials provided with the distribution, if any.
 * 2.	Your end-user documentation included with the redistribution, if any, must include the following acknowledgment: 
 * 		�This product includes software developed by Scenpro, Inc.�  
 * 		If You do not include such end-user documentation, You shall include this acknowledgment in the caBIG� Software itself, 
 * 		wherever such third-party acknowledgments normally appear.
 * 3.	You may not use the names  �Scenpro, Inc�, 
 * 		�The National Cancer Institute�, �NCI�, �Cancer Bioinformatics Grid� or �caBIG�� to endorse or promote products 
 * 		derived from this caBIG� Software.  This License does not authorize You to use any trademarks, service marks, trade names,
 * 		logos or product names of either caBIG� Participant, NCI or caBIG�, except as required to comply with the terms of this 
 * 		License.
 * 4.	For sake of clarity, and not by way of limitation, You may incorporate this caBIG� Software into Your proprietary 
 * 		programs and into any third party proprietary programs.  However, if You incorporate the caBIG� Software into third party 
 * 		proprietary programs, You agree that You are solely responsible for obtaining any permission from such third parties 
 * 		required to incorporate the caBIG� Software into such third party proprietary programs and for informing Your sublicensees, 
 * 		including without limitation Your end-users, of their obligation to secure any required permissions from such third parties 
 * 		before incorporating the caBIG� Software into such third party proprietary software programs.  In the event that You fail to 
 * 		obtain such permissions, You agree to indemnify caBIG� Participant for any claims against caBIG� Participant by such third 
 * 		parties, except to the extent prohibited by law, resulting from Your failure to obtain such permissions.
 * 5.	For sake of clarity, and not by way of limitation, You may add Your own copyright statement to Your modifications and 
 * 		to the derivative works, and You may provide additional or different license terms and conditions in Your sublicenses of 
 * 		modifications of the caBIG� Software, or any derivative works of the caBIG� Software as a whole, provided Your use, reproduction, 
 * 		and distribution of the Work otherwise complies with the conditions stated in this License.
 * 6.	THIS caBIG� SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED WARRANTIES (INCLUDING, BUT NOT LIMITED TO, 
 * 		THE IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED.  
 * 		IN NO EVENT SHALL THE Scenpro, Inc OR ITS AFFILIATES 
 * 		BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * 		PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON 
 * 		ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY 
 * 		OUT OF THE USE OF THIS caBIG� SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * 
 */

package gov.nih.nci.caxchange.ctom.viewer.actions;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import gov.nih.nci.caxchange.ctom.viewer.constants.DisplayConstants;
import gov.nih.nci.caxchange.ctom.viewer.constants.ForwardConstants;
import gov.nih.nci.caxchange.ctom.viewer.forms.LabActivitiesSearchForm;
import gov.nih.nci.caxchange.ctom.viewer.forms.LoginForm;
import gov.nih.nci.caxchange.ctom.viewer.util.LabViewerAuthorizationHelper;
import gov.nih.nci.security.exceptions.CSConfigurationException;
import gov.nih.nci.security.exceptions.CSException;

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
 * @author Kunal Modi (Ekagra Software Technologies Ltd.)
 * 
 */
public class HappyAction extends Action {

	private static final Logger log = Logger.getLogger(HappyAction.class);
	private static final String CONFIG_FILE_1 = "/baseURL.properties";
	private static final String CONFIG_FILE_2 = "/loadLabURLS.properties";

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.struts.action.Action#execute(org.apache.struts.action.ActionMapping,
	 *      org.apache.struts.action.ActionForm,
	 *      javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionErrors errors = new ActionErrors();
		ActionForward forward = null;
		HttpSession session = request.getSession();
		String userEmail = (String) session.getAttribute("CAGRID_SSO_EMAIL_ID");
		LoginForm loginForm = (LoginForm) session
				.getAttribute(DisplayConstants.LOGIN_OBJECT);

		if ((loginForm == null) && (userEmail == null)) {
			log
					.debug("||||Failure|No Session or User Object Forwarding to the Login Page||");
			return mapping.findForward(ForwardConstants.LOGIN_PAGE);
		}

		/*
		 * clear the junk in the session here
		 */
		session.removeAttribute(DisplayConstants.CURRENT_ACTION);
		session.removeAttribute(DisplayConstants.CURRENT_FORM);
		session.removeAttribute(DisplayConstants.SEARCH_RESULT);

		session.setAttribute(DisplayConstants.CURRENT_TABLE_ID,
				DisplayConstants.TEST_ID);

		// If they got here by WebSSO then check authorization and set things up
		if (userEmail != null) {
			boolean loggedIn = true;
			loginForm = new LoginForm();

			String username = userEmail;
			loginForm.setLoginId(username);

			// Check their authorization
			LabViewerAuthorizationHelper lvaHelper = new LabViewerAuthorizationHelper();
			boolean authorized = lvaHelper.isAuthorized(username);

			if (!authorized) {
				log.error("User authenticated but not authorized");
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
						DisplayConstants.ERROR_ID,
						"User does not have permissions for this application"));
				saveErrors(request, errors);
				loggedIn = false;
				forward = mapping.findForward(ForwardConstants.LOGIN_FAILURE);
			}

			if (loggedIn) {
				loginForm.setGridProxy("test");

				session.setAttribute(DisplayConstants.LOGIN_OBJECT, loginForm);
				session.setAttribute(DisplayConstants.CURRENT_TABLE_ID,
						DisplayConstants.TEST_ID);
				if (session.getAttribute("HOT_LINK") == "true") {
					LabActivitiesSearchForm labFm = (LabActivitiesSearchForm) session
							.getAttribute("CURRENT_FORM");
					session.setAttribute("CURRENT_FORM", labFm);
					return (mapping
							.findForward(ForwardConstants.LOGIN_SUCCESS_HOTLINK));
				}
				try
				 {   Properties props = new Properties();
					 InputStream stream = getClass().getResourceAsStream(CONFIG_FILE_1);
					 props.load(stream);
					 String caAERSurl = (String)props.getProperty("BaseURLcaAERS");
					 String hotLinkType = (String)props.getProperty("hotLink_Type");
				     //String C3Durl = (String)props.getProperty("BaseURLC3D");
				     session.setAttribute("BaseURLcaAERS", caAERSurl);
				     //hotlink type can be _blank,_self,${hotLink_NAME}:value set in properties file
				     session.setAttribute("hotLinkType", hotLinkType);
				       
				     
				 } catch (FileNotFoundException e1) 
				 {
				     log.error("The config file not found: " + CONFIG_FILE_1);
				 } 
				 catch (IOException e1) 
				 {
					 log.error("Error reading the config file: " + CONFIG_FILE_1);
				 }	   
				 
				 try{
					 Properties props2 = new Properties();
					 InputStream stream2 = getClass().getResourceAsStream(CONFIG_FILE_2);
					 props2.load(stream2);
					 String url = (String)props2.getProperty("url");
					 session.setAttribute("caXchangeURL", url);
				 }catch (FileNotFoundException e1) 
				 {
				     log.error("The config file not found: " + CONFIG_FILE_2);
				 } 
				 catch (IOException e1) 
				 {
					 log.error("Error reading the config file: " + CONFIG_FILE_2);
				 }	   
			}
			 	forward = mapping.findForward(ForwardConstants.LOAD_TEST);
			}
		return forward;
	}

}
