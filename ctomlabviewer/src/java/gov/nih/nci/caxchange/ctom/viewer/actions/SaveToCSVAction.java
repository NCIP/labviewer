/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */
package gov.nih.nci.caxchange.ctom.viewer.actions;

import gov.nih.nci.caxchange.ctom.viewer.constants.DisplayConstants;
import gov.nih.nci.caxchange.ctom.viewer.constants.ForwardConstants;
import gov.nih.nci.caxchange.ctom.viewer.forms.LabActivitiesSearchResultForm;
import gov.nih.nci.caxchange.ctom.viewer.forms.LoginForm;
import gov.nih.nci.caxchange.ctom.viewer.viewobjects.LabActivityResult;
import gov.nih.nci.labhub.domain.II;
import gov.nih.nci.logging.api.user.UserInfoHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;

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

import webservices.Documentation;
import webservices.LabResult;
import webservices.Participant;
import webservices.PerformedActivity;
import webservices.PerformedStudy;
import webservices.StudySubject;

/**
 * This class Saves the Lab data Search results to a CSV file. It checks if
 * valid login information is in session; if not it redirects the user to login
 * page.
 * 
 * @author asharma
 */
public class SaveToCSVAction extends Action
{
	private static final Logger logDB = Logger.getLogger(SaveToCSVAction.class);

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
		LabActivitiesSearchResultForm lForm =
				(LabActivitiesSearchResultForm) form;

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
			// calls the saveToCSV method
			saveToCSV(request, lForm, username, response);
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					DisplayConstants.MESSAGE_ID,
					"Messages Saved to CSV file Successfully"));
			saveMessages(request, messages);
			logDB.info(new ActionMessage(DisplayConstants.MESSAGE_ID,
					"Messages Saved to CSV file Successfully"));

		}
		catch (Exception cse)
		{
			String msg = cse.getMessage();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					DisplayConstants.ERROR_ID, "Error in Saving labs to CSV: "
							+ msg));
			saveErrors(request, errors);
			logDB.error("Error saving labs to CSV File", cse);
		}
		session.setAttribute(DisplayConstants.CURRENT_FORM, lForm);

		// if the login is valid and the selected form data is successfully
		// saved to a csv file;
		// it returns to the search results page and displays the save
		// successful message
		return (mapping.findForward(ForwardConstants.SAVE_TO_CSV_EVENT_SUCCESS));
	}

	/**
	 * Gets the CSV file name and type
	 * 
	 * @param response
	 * @return
	 */
	private ActionForward getFileName(HttpServletResponse response)
	{
		// get the filename
		response.setContentType("text/csv");
		response.setHeader("Content-disposition",
				"attachment;filename=LabResults.csv");
		return null;

	}

	/**
	 * Collects the selected form data and saves the labs to CSV file
	 * 
	 * @param request
	 * @param form
	 * @param username
	 * @param response
	 * @throws Exception
	 */
	private void saveToCSV(HttpServletRequest request, ActionForm form,
			String username, HttpServletResponse response) throws Exception
	{
		logDB.info("In Save to CSV");
		getFileName(response);
		LabActivitiesSearchResultForm lForm =
				(LabActivitiesSearchResultForm) form;
		HashMap map = (HashMap) request.getSession().getAttribute("RESULT_SET");
		ArrayList list = new ArrayList();
		String test = lForm.getRecordId();
		StringTokenizer stringTokenizer = new StringTokenizer(test, ",");
		int count = stringTokenizer.countTokens();

		// Create the list of results to send
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
		int numOfLabs = 0;
		// Create the list of results to send
		/*
		 * if (count >= 1) { while (stringTokenizer.hasMoreTokens()) {
		 * list.add(map.get(stringTokenizer.nextToken())); } } else
		 */

		// Then for each lab selected set the lab information
		LabResult labResults[] = new LabResult[list.size()];
		int i = 0;

		for (Iterator labs = list.iterator(); labs.hasNext();)
		{
			LabActivityResult lab = (LabActivityResult) labs.next();

			// Populate the study information
			Documentation documentation = new Documentation();
			PerformedStudy performedStudy = new PerformedStudy();

			String studyId = lab.getStudyId();
			if (studyId != null)
			{
				// Set the study identifier on the document
				webservices.II ii = new webservices.II();
				ii.setExtension("STUDY:" + studyId);
				ii.setAssigningAuthorityName("CTODS");
				ii.setRoot("C3D");
				webservices.II[] iis = new webservices.II[1];
				iis[0] = ii;
				documentation.setII(iis);
			}
			Documentation[] docs = new Documentation[1];
			docs[0] = documentation;
			performedStudy.setDocumentation(docs);

			// Then set the participant and study subject assignment identifiers
			Participant participant = new Participant();
			StudySubject studySubject = new StudySubject();

			Collection<II> studySubjectIds =
					lab.getSubjectAssignment().getStudySubjectIdentifier();
			if (studySubjectIds != null && studySubjectIds.size() > 0)
			{
				Iterator<II> idIterator = studySubjectIds.iterator();
				II ssII = idIterator.next();
				webservices.II ii = new webservices.II();
				ii.setAssigningAuthorityName("CTODS");
				ii.setRoot("C3D");
				ii.setExtension("MRN:" + ssII.getExtension());
				webservices.II[] iis = new webservices.II[1];
				iis[0] = ii;
				participant.setII(iis);
				webservices.II ii2 = new webservices.II();
				ii2.setAssigningAuthorityName("CTODS");
				ii2.setRoot("C3D");
				ii2.setExtension("PATIENTPOSITION:" + ssII.getExtension());
				webservices.II[] iis2 = new webservices.II[1];
				iis2[0] = ii2;
				studySubject.setII(iis2);
			}
			studySubject.setParticipant(participant);
			studySubject.setPerformedStudy(performedStudy);

			// Set the activity name
			PerformedActivity performedActivity = new PerformedActivity();
			String testName = lab.getLabTestId();
			performedActivity.setName(testName);
			PerformedActivity[] performedActivitys = new PerformedActivity[1];
			performedActivitys[0] = performedActivity;
			studySubject.setPerformedActivity(performedActivitys);

			// Then set the lab result
			LabResult labResult = new LabResult();
			labResult.setStudySubject(studySubject);

			// Set the reported date
			Date labDate = lab.getActualDate();
			if (labDate != null)
			{
				Calendar cal = Calendar.getInstance();
				cal.setTime(labDate);
				labResult.setReportedDateTime(cal);
			}

			// Set the lab result details
			String numResult = lab.getNumericResult();
			if ((numResult != null) && (!numResult.equals("")))
				labResult.setNumericResult(Float.parseFloat(numResult));
			String txtResult = lab.getTextResult();
			if ((txtResult != null) && (!txtResult.equals("")))
				labResult.setTextResult(txtResult);
			String labUom = lab.getUnitOfMeasure();
			if (labUom != null)
				labResult.setNumericUnit(labUom);
			String lowRange = lab.getLowRange();
			if (lowRange != null)
				labResult.setReferenceRangeLow(Float.parseFloat(lowRange));
			String highRange = lab.getHighRange();
			if (highRange != null)
				labResult.setReferenceRangeHigh(Float.parseFloat(highRange));

			labResults[i] = labResult;
			i++;
		}
		java.io.PrintWriter out = response.getWriter();
		String data = "";
		String heading =
				" Patient Id,Date / Time,Lab Test,Text Result,Numeric Result,Unit Of Measure,Lower Limit,Upper Limit";
		out.write(heading);
		out.write("\n");
		logDB.info("Selected Lab results:" + labResults.length);
		for (int ii = 0; ii < labResults.length; ii++)
		{
			LabResult result = labResults[ii];
			data =
					result.getStudySubject().getParticipant().getII(0)
							.getExtension()
							+ ","
							+ convertToString(new Date(result
									.getReportedDateTime().getTimeInMillis()))
							+ ","
							+ result.getStudySubject().getPerformedActivity(0)
									.getName()
							+ ","
							+ result.getTextResult()
							+ ","
							+ result.getNumericResult()
							+ ","
							+ result.getNumericUnit()
							+ ","
							+ result.getReferenceRangeLow()
							+ ","
							+ result.getReferenceRangeHigh() + "\n";
			// System.out.println("Data: "+data);
			out.write(data);
			data = "";
		}
		out.flush();
		out.close();
	}

	/**
	 * @param date
	 * @return str formatted date.
	 */
	private static String convertToString(java.util.Date date)
	{
		if (date == null)
			return "-";
		SimpleDateFormat formatter = new SimpleDateFormat();
		String str = formatter.format(date);
		return str;
	}

}
