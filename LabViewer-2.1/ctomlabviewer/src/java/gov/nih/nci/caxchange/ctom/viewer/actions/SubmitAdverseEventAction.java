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
import gov.nih.nci.caxchange.ctom.viewer.forms.LabActivitiesSearchForm;
import gov.nih.nci.caxchange.ctom.viewer.forms.LabActivitiesSearchResultForm;
import gov.nih.nci.caxchange.ctom.viewer.forms.LoginForm;
import gov.nih.nci.caxchange.ctom.viewer.viewobjects.LabActivityResult;
import gov.nih.nci.logging.api.user.UserInfoHelper;
import gov.nih.nci.security.exceptions.CSException;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
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
 * This class performs the submitAdverseEvent action. If the session is not null
 * and the user login information is valid, it submits the selected search
 * result record to EventManager's sendAdverseEvent method. If the session is
 * null;it redirects the user to the login page to enter valid login
 * information. After successful submit of an adverse event, it returns to the
 * searchresults page and displays appropriate messages.
 * 
 * @author asharma
 */
public class SubmitAdverseEventAction extends Action
{

	private static final Logger logDB =
			Logger.getLogger(SubmitAdverseEventAction.class);

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
		// gets the session object from HttpRequest
		HttpSession session = request.getSession();
		// Search results form
		LabActivitiesSearchResultForm lForm =
				(LabActivitiesSearchResultForm) form;
		// if the session is new or login object is null; it redirects the user
		// to login page
		if (session.isNew()
				|| (session.getAttribute(DisplayConstants.LOGIN_OBJECT) == null))
		{
			if (logDB.isDebugEnabled())
				logDB
						.debug("||"
								+ lForm.getFormName()
								+ "|create|Failure|No Session or User Object Forwarding to the Login Page||");
			return mapping.findForward(ForwardConstants.LOGIN_PAGE);
		}
		UserInfoHelper.setUserInfo(((LoginForm) session
				.getAttribute(DisplayConstants.LOGIN_OBJECT)).getLoginId(),
				session.getId());
		try
		{
			// calls the submitAdverseEvent method
			submitAdverseEvent(request, lForm);
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					DisplayConstants.MESSAGE_ID,
					"Adverse Event Submitted Successfully"));
			saveMessages(request, messages);
		}
		catch (CSException cse)
		{
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					DisplayConstants.ERROR_ID,
					"Error in Submitting the Adverse Event"));
			saveErrors(request, errors);
			if (logDB.isDebugEnabled())
				logDB.debug(session.getId()
						+ "|"
						+ ((LoginForm) session
								.getAttribute(DisplayConstants.LOGIN_OBJECT))
								.getLoginId() + "|" + lForm.getFormName()
						+ "|create|Failure|Error Adding the "
						+ lForm.getFormName() + " object|" + form.toString()
						+ "|" + cse.getMessage());
		}
		session.setAttribute(DisplayConstants.CURRENT_FORM, lForm);
		if (logDB.isDebugEnabled())
			logDB.debug(session.getId()
					+ "|"
					+ ((LoginForm) session
							.getAttribute(DisplayConstants.LOGIN_OBJECT))
							.getLoginId() + "|" + lForm.getFormName()
					+ "|create|Success|Adding a  new " + lForm.getFormName()
					+ " object|" + form.toString() + "|");
		return (mapping
				.findForward(ForwardConstants.SUBMIT_ADVERSE_EVENT_SUCCESS));
	}

	/**
	 * submitAdverseEvent submits the selected form record to the Eventmanager's
	 * sendAdverseEvent method.
	 * 
	 * @param request
	 * @throws Exception
	 */
	private void submitAdverseEvent(HttpServletRequest request, ActionForm form)
			throws Exception
	{

		LabActivitiesSearchResultForm lForm =
				(LabActivitiesSearchResultForm) form;
		org.hibernate.Session session = null;
		Connection connection = null;
		/*
		 * try { session = ORMConnection
		 * .openSession("gov.nih.nci.labhub.domain.SubjectAssignment");
		 * connection = session.connection(); connection.setAutoCommit(false);
		 * HashMap map = (HashMap) request.getSession().getAttribute(
		 * "RESULT_SET"); LabActivityResult labActivityResult =
		 * (LabActivityResult) map .get(lForm.getRecordId());
		 */
		/*
		 * String sql = "insert into LV_ADVERSE_EVENTS (CLINICAL_RESULT_ID,
		 * ADVERSE_EVENT_INDICATOR, UPDATE_DATE) values ( ? , '1' , sysdate )";
		 * PreparedStatement stmt = connection.prepareStatement(sql);
		 * stmt.setString(1, labActivityResult.getLabResultId());
		 * stmt.executeUpdate(); try { EventsManager mgr = (EventsManager)
		 * ObjectFactory .getObject("eventsManager");
		 * mgr.sendAdverseEvent(labActivityResult, "TESTUSER");
		 * connection.commit(); stmt.close(); } catch (Exception ex) { try {
		 * connection.rollback(); } catch (Exception e) { } throw ex; }
		 */

		// connection.createStatement().execute("insert into
		// LV_ADVERSE_EVENTS (CLINICAL_RESULT_ID, ADVERSE_EVENT_INDICATOR,
		// UPDATE_DATE) values (" + labActivityResult.getLabResultId() + " ,
		// '1' , sysdate )");
		// connection.commit();
		/*
		 * labActivityResult.setAdverseEventReported("1");
		 * labActivityResult.setAdverseEventReportedDate(new SimpleDateFormat(
		 * "MM/dd/yyyy").format(new Date())); map.put(lForm.getRecordId(),
		 * labActivityResult); request.getSession().setAttribute("RESULT_SET",
		 * map); lForm.setRecordId(""); lForm.setRecordIds(null); } finally {
		 * try { connection.setAutoCommit(true); } catch (Exception ex) { } try {
		 * session.close(); } catch (Exception ex) { } }
		 */

	}

}
