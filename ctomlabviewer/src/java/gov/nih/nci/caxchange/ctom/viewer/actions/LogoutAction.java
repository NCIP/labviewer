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
import gov.nih.nci.caxchange.ctom.viewer.forms.LoginForm;
import gov.nih.nci.logging.api.user.UserInfoHelper;
import gov.nih.nci.security.AuthenticationManager;
import gov.nih.nci.security.SecurityServiceProvider;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

/**
 * @author Kunal Modi (Ekagra Software Technologies Ltd.) TODO To change the
 *         template for this generated type comment go to Window - Preferences -
 *         Java - Code Style - Code Templates
 */
public class LogoutAction extends Action
{

	private static final Logger log = Logger.getLogger(LogoutAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		/* perform login task */
		HttpSession session = request.getSession();

		UserInfoHelper.setUserInfo(((LoginForm) session
				.getAttribute(DisplayConstants.LOGIN_OBJECT)).getLoginId(),
				session.getId());
		boolean loggedInthroWEBSSO = false;
		try
		{
			// Since they may have come from another app but logged in through
			// webSSO let's check auth
			// String userEmail =
			// (String)session.getAttribute("CAGRID_SSO_EMAIL_ID");
			String userEmail = null;
			String gridIDentity =
					(String) session.getAttribute("CAGRID_SSO_GRID_IDENTITY");
			if (gridIDentity != null)
			{
				int beginIndex = gridIDentity.lastIndexOf("=");
				int endIndex = gridIDentity.length();
				userEmail = gridIDentity.substring(beginIndex + 1, endIndex);
			}
			if (userEmail != null)
			{
				loggedInthroWEBSSO = true;
			}
			else
			{
				AuthenticationManager authenticationManager =
						SecurityServiceProvider
								.getAuthenticationManager(DisplayConstants.APPLICATION_CONTEXT_NAME);
				authenticationManager.logout(((LoginForm) session
						.getAttribute(DisplayConstants.LOGIN_OBJECT))
						.getLoginId());
			}
		}
		catch (Exception e)
		{
			if (log.isDebugEnabled())
				log.debug(session.getId()
						+ "|"
						+ ((LoginForm) session
								.getAttribute(DisplayConstants.LOGIN_OBJECT))
								.getLoginId()
						+ "||Logout|Failure|Logout Called and Error occured||"
						+ e.getMessage());
			e.printStackTrace();
		}
		if (log.isDebugEnabled())
			log
					.debug(session.getId()
							+ "|"
							+ ((LoginForm) session
									.getAttribute(DisplayConstants.LOGIN_OBJECT))
									.getLoginId()
							+ "||Logout|Success|Logout Called and Forwarding to the Login Page||");
		session.removeAttribute(DisplayConstants.USER_PROVISIONING_MANAGER);
		session.removeAttribute(DisplayConstants.LOGIN_OBJECT);
		session.removeAttribute(DisplayConstants.CURRENT_TABLE_ID);
		session.removeAttribute(DisplayConstants.CURRENT_ACTION);
		session.removeAttribute(DisplayConstants.CURRENT_FORM);
		session.removeAttribute(DisplayConstants.SEARCH_RESULT);
		session.removeAttribute(DisplayConstants.SEARCH_RESULT_STUDY);
		session.removeAttribute(DisplayConstants.SEARCH_RESULT_PART);

		session.invalidate();
		if (loggedInthroWEBSSO)
		{
			return (mapping.findForward(ForwardConstants.WEBSSO_LOGOUT_SUCCESS));
		}
		else
		{
			return (mapping.findForward(ForwardConstants.LOGOUT_SUCCESS));
		}
	}

	/**
	 * Retrieves the UPT Context name.
	 * 
	 * @return
	 * @throws Exception
	 */
	private static String getUPTContextName() throws Exception
	{
		Document configDocument = null;
		String uptContextNameValue = null;
		String configFilePath =
				System
						.getProperty(DisplayConstants.CONFIG_FILE_PATH_PROPERTY_NAME);
		SAXBuilder builder = new SAXBuilder();
		configDocument = builder.build(new File(configFilePath));
		if (configDocument != null)
		{
			Element securityConfig = configDocument.getRootElement();
			Element uptContextName =
					securityConfig.getChild("upt-context-name");
			uptContextNameValue = uptContextName.getText().trim();
		}
		return uptContextNameValue;
	}

}
