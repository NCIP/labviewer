/**
 * Copyright Notice. Copyright 2008 ScenPro, Inc ("caBIG(TM)
 * Participant").caXchange was created with NCI funding and is part of the
 * caBIG(TM) initiative. The software subject to this notice and license includes
 * both human readable source code form and machine readable, binary, object
 * code form (the "caBIG(TM) Software"). This caBIG(TM) Software License (the
 * "License") is between caBIG(TM) Participant and You. "You (or "Your") shall mean
 * a person or an entity, and all other entities that control, are controlled
 * by, or are under common control with the entity. "Control" for purposes of
 * this definition means (i) the direct or indirect power to cause the direction
 * or management of such entity, whether by contract or otherwise, or (ii)
 * ownership of fifty percent (50%) or more of the outstanding shares, or (iii)
 * beneficial ownership of such entity. License. Provided that You agree to the
 * conditions described below, caBIG(TM) Participant grants You a non-exclusive,
 * worldwide, perpetual, fully-paid-up, no-charge, irrevocable, transferable and
 * royalty-free right and license in its rights in the caBIG(TM) Software,
 * including any copyright or patent rights therein, to (i) use, install,
 * disclose, access, operate, execute, reproduce, copy, modify, translate,
 * market, publicly display, publicly perform, and prepare derivative works of
 * the caBIG(TM) Software in any manner and for any purpose, and to have or permit
 * others to do so; (ii) make, have made, use, practice, sell, and offer for
 * sale, import, and/or otherwise dispose of caBIG(TM) Software (or portions
 * thereof); (iii) distribute and have distributed to and by third parties the
 * caBIG(TM) Software and any modifications and derivative works thereof; and (iv)
 * sublicense the foregoing rights set out in (i), (ii) and (iii) to third
 * parties, including the right to license such rights to further third parties.
 * For sake of clarity, and not by way of limitation, caBIG(TM) Participant shall
 * have no right of accounting or right of payment from You or Your sublicensees
 * for the rights granted under this License. This License is granted at no
 * charge to You. Your downloading, copying, modifying, displaying, distributing
 * or use of caBIG(TM) Software constitutes acceptance of all of the terms and
 * conditions of this Agreement. If you do not agree to such terms and
 * conditions, you have no right to download, copy, modify, display, distribute
 * or use the caBIG(TM) Software. 1. Your redistributions of the source code for
 * the caBIG(TM) Software must retain the above copyright notice, this list of
 * conditions and the disclaimer and limitation of liability of Article 6 below.
 * Your redistributions in object code form must reproduce the above copyright
 * notice, this list of conditions and the disclaimer of Article 6 in the
 * documentation and/or other materials provided with the distribution, if any.
 * 2. Your end-user documentation included with the redistribution, if any, must
 * include the following acknowledgment: "This product includes software
 * developed by ScenPro, Inc." If You do not include such end-user
 * documentation, You shall include this acknowledgment in the caBIG(TM) Software
 * itself, wherever such third-party acknowledgments normally appear. 3. You may
 * not use the names "ScenPro, Inc", "The National Cancer Institute", "NCI",
 * "Cancer Bioinformatics Grid" or "caBIG(TM)" to endorse or promote products
 * derived from this caBIG(TM) Software. This License does not authorize You to use
 * any trademarks, service marks, trade names, logos or product names of either
 * caBIG(TM) Participant, NCI or caBIG(TM), except as required to comply with the
 * terms of this License. 4. For sake of clarity, and not by way of limitation,
 * You may incorporate this caBIG(TM) Software into Your proprietary programs and
 * into any third party proprietary programs. However, if You incorporate the
 * caBIG(TM) Software into third party proprietary programs, You agree that You are
 * solely responsible for obtaining any permission from such third parties
 * required to incorporate the caBIG(TM) Software into such third party proprietary
 * programs and for informing Your sublicensees, including without limitation
 * Your end-users, of their obligation to secure any required permissions from
 * such third parties before incorporating the caBIG(TM) Software into such third
 * party proprietary software programs. In the event that You fail to obtain
 * such permissions, You agree to indemnify caBIG(TM) Participant for any claims
 * against caBIG(TM) Participant by such third parties, except to the extent
 * prohibited by law, resulting from Your failure to obtain such permissions. 5.
 * For sake of clarity, and not by way of limitation, You may add Your own
 * copyright statement to Your modifications and to the derivative works, and
 * You may provide additional or different license terms and conditions in Your
 * sublicenses of modifications of the caBIG(TM) Software, or any derivative works
 * of the caBIG(TM) Software as a whole, provided Your use, reproduction, and
 * distribution of the Work otherwise complies with the conditions stated in
 * this License. 6. THIS caBIG(TM) SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED
 * OR IMPLIED WARRANTIES (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE)
 * ARE DISCLAIMED. IN NO EVENT SHALL THE ScenPro, Inc OR ITS AFFILIATES BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS caBIG(TM) SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

package gov.nih.nci.caxchange.ctom.viewer.actions;

import gov.nih.nci.caxchange.ctom.viewer.constants.DisplayConstants;
import gov.nih.nci.caxchange.ctom.viewer.constants.ForwardConstants;
import gov.nih.nci.caxchange.ctom.viewer.forms.AdministrationForm;
import gov.nih.nci.caxchange.ctom.viewer.forms.LoginForm;
import gov.nih.nci.caxchange.ctom.viewer.util.CommonUtil;
import gov.nih.nci.logging.api.user.UserInfoHelper;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

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
 * This class Saves the Lab data Search results to a CSV file. It checks if
 * valid login information is in session; if not it redirects the user to login
 * page.
 * 
 * @author asharma
 */
public class SaveAdminConfgAction extends Action
{
	private static final Logger logDB =
			Logger.getLogger(SaveAdminConfgAction.class);
	private static final String CONFIG_FILE = "baseURL.properties";

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
			throws Exception
	{
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		
		HttpSession session = request.getSession();
		AdministrationForm aForm = (AdministrationForm) form;

		// if the session is new or the login object is null; redirects the user
		// to login page
		if (session.isNew()
				|| (session.getAttribute(DisplayConstants.LOGIN_OBJECT) == null))
		{
			logDB
					.error("No Session or User Object Forwarding to the Login Page");
			return mapping.findForward(ForwardConstants.LOGIN_PAGE);
		}

		String username =
				((LoginForm) session
						.getAttribute(DisplayConstants.LOGIN_OBJECT))
						.getLoginId();

		UserInfoHelper.setUserInfo(username, session.getId());

		try
		{	
			//save the user set properties for the urls to a properties file
			saveToPropertiesFile(aForm,session);
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					DisplayConstants.MESSAGE_ID,
					"Configuration Saved Successfully"));
			saveMessages(request, messages);
			logDB.info(new ActionMessage(DisplayConstants.MESSAGE_ID,
					"Configuration Saved Successfully"));

		}
		catch (Exception cse)
		{
			String msg = cse.getMessage();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					DisplayConstants.ERROR_ID,
					"Error in Saving Configuration: " + msg));
			saveErrors(request, errors);
			logDB.error("Error saving lConfiguration", cse);
		}
		session.setAttribute(DisplayConstants.CURRENT_FORM, aForm);

		// if the login is valid and the selected form data is successfully
		// saved to a csv file;
		// it returns to the search results page and displays the save
		// successful message
		return (mapping.findForward(ForwardConstants.UPDATE_SUCCESS));
	}


	/**
	 * Saves the Properties updated/entered by the user
	 * @param aForm
	 * @param session
	 */
	private void saveToPropertiesFile(AdministrationForm aForm,HttpSession session)
	{
		try
		{
			//get the path of the BaseURL properties file from the session
			String path = (String) session.getAttribute("propertyFilePath");
			                    
			File file = new File(session.getServletContext().getRealPath(path));
			Properties propsToUpdate = new Properties();
			propsToUpdate.put("BaseURLcaAERS", aForm.getCaaersUrl());
			propsToUpdate.put("BaseURLC3D", aForm.getC3dUrl());
			propsToUpdate.put("BaseURLC3PR", aForm.getC3prUrl());
			propsToUpdate.put("tissueURL",aForm.getTissueUrl());
			propsToUpdate.put("url", aForm.getCaxUrl());
			OutputStream oStream =
					new BufferedOutputStream(new FileOutputStream(file));
			propsToUpdate.store(oStream, "");
			oStream.close();

            /* also update cache */
            CommonUtil.props1.put("BaseURLcaAERS", aForm.getCaaersUrl());
			CommonUtil.props1.put("BaseURLC3D", aForm.getC3dUrl());
			CommonUtil.props1.put("BaseURLC3PR", aForm.getC3prUrl());
			CommonUtil.props1.put("tissueURL",aForm.getTissueUrl());
			CommonUtil.props1.put("url", aForm.getCaxUrl());
             
        }
		catch (FileNotFoundException e1)
		{
			logDB.error("The config file not found: " + CONFIG_FILE);
		}
		catch (IOException e1)
		{
			logDB.error("Error writing to the config file: " + CONFIG_FILE);
		}
		//after saving the properties to the file,load it into session.
		session.setAttribute("BaseURLcaAERS", aForm.getCaaersUrl());
		session.setAttribute("BaseURLC3PR", aForm.getC3prUrl());
		session.setAttribute("caXchangeURL", aForm.getCaxUrl());
		session.setAttribute("BaseURLC3D", aForm.getC3dUrl());
		session.setAttribute("tissueURL", aForm.getTissueUrl());
	}

}