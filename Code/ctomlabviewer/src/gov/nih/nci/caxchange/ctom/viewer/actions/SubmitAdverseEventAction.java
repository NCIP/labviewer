/**
 * 
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
import gov.nih.nci.system.dao.impl.orm.ORMConnection;

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
 * @author asharma
 * 
 */
public class SubmitAdverseEventAction extends Action {

	private static final Logger logDB = Logger
			.getLogger(SubmitAdverseEventAction.class);

	/* (non-Javadoc)
	 * @see org.apache.struts.action.Action#execute(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();

		HttpSession session = request.getSession();
		LabActivitiesSearchResultForm lForm = (LabActivitiesSearchResultForm) form;

		if (session.isNew()
				|| (session.getAttribute(DisplayConstants.LOGIN_OBJECT) == null)) {
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
		try {
			submitAdverseEvent(request, lForm);
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					DisplayConstants.MESSAGE_ID,
					"Adverse Event Submitted Successfully"));
			saveMessages(request, messages);
		} catch (CSException cse) {
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
	 * @param request
	 * @throws Exception
	 */
	private void submitAdverseEvent(HttpServletRequest request, ActionForm form)
			throws Exception {

		LabActivitiesSearchResultForm lForm = (LabActivitiesSearchResultForm) form;
		org.hibernate.Session session = null;
		Connection connection = null;
		try {
			session = ORMConnection
					.openSession("gov.nih.nci.labhub.domain.SubjectAssignment");
			connection = session.connection();
			connection.setAutoCommit(false);
			HashMap map = (HashMap) request.getSession().getAttribute(
					"RESULT_SET");
			LabActivityResult labActivityResult = (LabActivityResult) map
					.get(lForm.getRecordId());
			/*String sql = "insert into LV_ADVERSE_EVENTS (CLINICAL_RESULT_ID, ADVERSE_EVENT_INDICATOR, UPDATE_DATE) values ( ? , '1' , sysdate )";
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, labActivityResult.getLabResultId());
			stmt.executeUpdate();
			try {
				EventsManager mgr = (EventsManager) ObjectFactory
						.getObject("eventsManager");
				mgr.sendAdverseEvent(labActivityResult, "TESTUSER");
				connection.commit();
				stmt.close();
			} catch (Exception ex) {
				try {
					connection.rollback();
				} catch (Exception e) {
				}
				throw ex;
			}*/

			// connection.createStatement().execute("insert into
			// LV_ADVERSE_EVENTS (CLINICAL_RESULT_ID, ADVERSE_EVENT_INDICATOR,
			// UPDATE_DATE) values (" + labActivityResult.getLabResultId() + " ,
			// '1' , sysdate )");
			// connection.commit();
			labActivityResult.setAdverseEventReported("1");
			labActivityResult.setAdverseEventReportedDate(new SimpleDateFormat(
					"MM/dd/yyyy").format(new Date()));
			map.put(lForm.getRecordId(), labActivityResult);
			request.getSession().setAttribute("RESULT_SET", map);
			lForm.setRecordId("");
			lForm.setRecordIds(null);

		} finally {
			try {
				connection.setAutoCommit(true);
			} catch (Exception ex) {
			}
			try {
				session.close();
			} catch (Exception ex) {
			}
		}

	}

}
