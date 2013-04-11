/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */
package gov.nih.nci.caxchange.ctom.viewer.actions;

import gov.nih.nci.cagrid.caxchange.client.CaXchangeRequestProcessorClient;
import gov.nih.nci.cagrid.caxchange.context.client.CaXchangeResponseServiceClient;
import gov.nih.nci.cagrid.caxchange.context.stubs.types.CaXchangeResponseServiceReference;
import gov.nih.nci.cagrid.common.Utils;
import gov.nih.nci.caxchange.Credentials;
import gov.nih.nci.caxchange.Message;
import gov.nih.nci.caxchange.MessagePayload;
import gov.nih.nci.caxchange.Metadata;
import gov.nih.nci.caxchange.Request;
import gov.nih.nci.caxchange.Response;
import gov.nih.nci.caxchange.ResponseMessage;
import gov.nih.nci.caxchange.Statuses;
import gov.nih.nci.caxchange.TargetResponseMessage;
import gov.nih.nci.caxchange.ctom.viewer.beans.LabViewerStatus;
import gov.nih.nci.caxchange.ctom.viewer.beans.util.HibernateUtil;
import gov.nih.nci.caxchange.ctom.viewer.constants.DisplayConstants;
import gov.nih.nci.caxchange.ctom.viewer.constants.ForwardConstants;
import gov.nih.nci.caxchange.ctom.viewer.forms.LabActivitiesSearchResultForm;
import gov.nih.nci.caxchange.ctom.viewer.forms.LoginForm;
import gov.nih.nci.caxchange.ctom.viewer.viewobjects.LabActivityResult;
import gov.nih.nci.caxchange.ctom.viewer.viewobjects.SearchResult;
import gov.nih.nci.labhub.domain.II;
import gov.nih.nci.logging.api.user.UserInfoHelper;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.namespace.QName;

import org.apache.axis.message.MessageElement;
import org.apache.axis.types.URI;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.globus.gsi.GlobusCredential;
import org.hibernate.Session;

import webservices.Documentation;
import webservices.LabResult;
import webservices.LoadLabsRequest;
import webservices.Participant;
import webservices.PerformedActivity;
import webservices.PerformedStudy;
import webservices.StudySubject;

/**
 * This class performs the Load to caAERS action. It loads the selected form
 * data to caAERS. It checks if valid login information is in session; if not it
 * redirects the user to login page.
 * 
 * @author asharma
 */
public class LoadTocaAERSAction extends Action
{
	private static final Logger logDB =
			Logger.getLogger(LoadTocaAERSAction.class);
	private static final String CONFIG_FILE = "/baseURL.properties";

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
		String password =
				((LoginForm) session
						.getAttribute(DisplayConstants.LOGIN_OBJECT))
						.getPassword();
		UserInfoHelper.setUserInfo(username, session.getId());
		int numOfLabs = 0;

		try
		{ // calls the loadTocaAERS method
			numOfLabs = loadTocaAERS(request, lForm, username, password);
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					DisplayConstants.MESSAGE_ID, numOfLabs
							+ " Message(s) Submitted to caAERS Successfully"));
			updateLabResult(request);
			updateLabResultForUI(request);
			saveMessages(request, messages);
		}
		catch (Exception cse)
		{
			String msg = cse.getMessage();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					DisplayConstants.ERROR_ID,
					"Error in Submitting Messages to caAERS: " + msg));
			saveErrors(request, errors);
			logDB.error("Error sending labs to caAERS", cse);
		}
		session.setAttribute(DisplayConstants.CURRENT_FORM, lForm);
		// if the login is valid and the selected form data is successfully
		// loaded to caAERS;
		// it returns to the search results page and displays the load
		// successful message
		return (mapping
				.findForward(ForwardConstants.LOAD_TO_caAERS_EVENT_SUCCESS));
	}

	/**
	 * Collects the selected form data and loads the labs to caAERS
	 * 
	 * @param request
	 * @param form
	 * @param username
	 * @return numOfLabs number of labs laoded
	 * @throws Exception
	 */
	private int loadTocaAERS(HttpServletRequest request, ActionForm form,
			String username, String password) throws Exception
	{
		HttpSession session = request.getSession();
		LabActivitiesSearchResultForm lForm =
				(LabActivitiesSearchResultForm) form;
		HashMap map = (HashMap) request.getSession().getAttribute("RESULT_SET");
		HashMap<String, LabActivityResult> labResultsMap =
				new HashMap<String, LabActivityResult>();
		HashMap<String, String> labResultIds = new HashMap<String, String>();
		String test = lForm.getRecordId();
		StringTokenizer stringTokenizer = new StringTokenizer(test, ",");
		int count = stringTokenizer.countTokens();
		int numOfLabs = 0;
		// Create the list of results to send
		if (test != null)
		{
			if (count >= 1)
			{
				while (stringTokenizer.hasMoreTokens())
				{
					String temp = stringTokenizer.nextToken();
					LabActivityResult lar = (LabActivityResult) map.get(temp);
					labResultsMap.put(temp, lar);
					labResultIds.put(temp, lar.getLabResultId());
				}
			}
			else
			{
				LabActivityResult lar =
						(LabActivityResult) map.get(lForm.getRecordId());
				labResultsMap.put(lForm.getRecordId(), lar);
				labResultIds.put(lForm.getRecordId(), lar.getLabResultId());

			}
		}
		Properties props = new Properties();
		// Get the file input stream
		try
		{
			InputStream stream = getClass().getResourceAsStream(CONFIG_FILE);
			props.load(stream);
		}
		catch (FileNotFoundException e1)
		{
			logDB.error("The config file not found: " + CONFIG_FILE);
		}
		catch (IOException e1)
		{
			logDB.error("Error reading the config file: " + CONFIG_FILE);
		}

		LoadLabsRequest labRequest = new LoadLabsRequest();
		// Then for each lab selected set the lab information
		LabResult labResults[] = new LabResult[labResultsMap.size()];
		int i = 0;

		for (String key : labResultsMap.keySet())
		{
			LabActivityResult lab = labResultsMap.get(key);
			// Populate the study information
			Documentation documentation = new Documentation();
			PerformedStudy performedStudy = new PerformedStudy();

			String studyId = lab.getStudyId();
			if (studyId != null)
			{
				// Set the study identifier on the document
				webservices.II ii = new webservices.II();
				ii.setExtension(studyId);
				ii.setAssigningAuthorityName("CTODS");
				ii.setRoot("caAERS");
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
				ii.setRoot("caAERS");
				ii.setExtension(ssII.getExtension());
				webservices.II[] iis = new webservices.II[1];
				iis[0] = ii;
				participant.setII(iis);
				webservices.II ii2 = new webservices.II();
				ii2.setAssigningAuthorityName("CTODS");
				ii2.setRoot("caAERS");
				ii2.setExtension(ssII.getExtension());
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
			java.util.Date labDate = lab.getActualDate();
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
		labRequest.setLabResult(labResults);
		numOfLabs = labResults.length;
		 PrintWriter writer = new PrintWriter("caAERSmessage.xml");
		// QName lab = new
		// QName("http://integration/caaers.nci.nih.gov/services","LoadLabsRequest");
		QName lab =
				new QName(
						"gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain.loadlabs",
						"LoadLabsRequest");
		// QName lab = new QName("LoadLabsRequest");
		Utils.serializeObject(labRequest, lab, writer);

		// Create the caxchange message
		Message requestMessage = new Message();
		Metadata metadata = new Metadata();
		metadata.setExternalIdentifier("CTODS");
		Credentials creds = new Credentials();
		creds.setUserName(username);
		creds.setPassword(password);
		String credentialEpr =
				(String) request.getSession().getAttribute(
						"CAGRID_SSO_DELEGATION_SERVICE_EPR");
		logDB.info("The credential EPR: " + credentialEpr);
		if (credentialEpr != null)
			creds.setDelegatedCredentialReference(credentialEpr);
		GlobusCredential gridCreds =
				(GlobusCredential) session
						.getAttribute("CAGRID_SSO_GRID_CREDENTIAL");
		if (gridCreds != null)
			logDB.info("The credential : " + gridCreds.getIdentity());
		metadata.setCredentials(creds);
		metadata.setServiceType("LAB_BASED_AE");
		requestMessage.setMetadata(metadata);
		Request caxchangeRequest = new Request();
		requestMessage.setRequest(caxchangeRequest);
		MessagePayload messagePayload = new MessagePayload();
		URI uri = new URI();
		uri
				.setPath("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain.loadlabs");
		messagePayload.setXmlSchemaDefinition(uri);
		MessageElement messageElement = new MessageElement(lab, labRequest);
		messagePayload.set_any(new MessageElement[]
		{ messageElement });
		requestMessage.getRequest().setBusinessMessagePayload(messagePayload);

		// Then create the request
		String url = (String) props.getProperty("url");
		CaXchangeRequestProcessorClient client =
				new CaXchangeRequestProcessorClient(url, gridCreds);

		CaXchangeResponseServiceReference crsr =
				client.processRequestAsynchronously(requestMessage);
		CaXchangeResponseServiceClient responseService =
				new CaXchangeResponseServiceClient(crsr.getEndpointReference(),
						gridCreds);

		boolean gotResponse = false;
		int responseCount = 0;
		ResponseMessage responseMessage = null;
		while (!gotResponse)
		{
			try
			{
				responseMessage = responseService.getResponse();
				gotResponse = true;
			}
			catch (Exception e)
			{
				logDB.info("No response from caxchange", e);
				responseCount++;
				if (responseCount > 25)
				{
					logDB.error("Never got a response from caxchange hub");
					throw new Exception("No response from hub");
				}
				Thread.sleep(1000);
			}
		}
		if (responseMessage != null)
		{
			Response resp = responseMessage.getResponse();
			logDB.info("caXchange response was "
					+ resp.getResponseStatus().toString());
			if (resp.getResponseStatus().equals(Statuses.SUCCESS))
			{
				if (resp.getCaXchangeError() != null)
				{
					String message =
							resp.getCaXchangeError().getErrorDescription();
					logDB.info("Received a success from caxchange hub: "
							+ message);
				}
				if (resp.getTargetResponse() != null)
				{
					String status = getTargetResponse(resp);

					if (status.equalsIgnoreCase("Processed"))
					{
						logDB
								.info("Response from Target Service was "
										+ status);
					}
					else
					{
						logDB.error("Received response from Target Service: "
								+ status);
						throw new Exception(status);
					}
				}
				else if (resp.getResponseStatus().equals(Statuses.FAILURE))
				{
					String message = "";
					if (resp.getCaXchangeError() != null)
					{
						message =
								resp.getCaXchangeError().getErrorDescription();
						logDB.error("Received a failure from caxchange hub: "
								+ message);
					}
					else
					{
						if (resp.getTargetResponse() != null)
						{
							for (TargetResponseMessage msg : resp
									.getTargetResponse())
							{
								message =
										msg.getTargetMessageStatus().getValue()
												+ ":"
												+ msg.getTargetError()
														.getErrorDescription();
							}
						}
					}
					throw new Exception(message);
				}
			}
		}
		lForm.setRecordId("");
		lForm.setRecordId(null);
		session.setAttribute("LabResultIDs", labResultIds);
		return numOfLabs;

	}

	/**
	 * @param resp
	 * @return
	 */
	private String getTargetResponse(Response resp)
	{
		String status = "";
		String errorCode = "";
		String errorDesc = "";
		for (TargetResponseMessage msg : resp.getTargetResponse())
		{
			MessageElement[] messagePay =
					msg.getTargetBusinessMessage().get_any();
			for (MessageElement mEle : messagePay)
			{
				if (mEle != null)
				{
					if (mEle.getName().equalsIgnoreCase("Acknowledgement"))
					{
						Iterator it = mEle.getChildElements();
						while (it.hasNext())
						{
							MessageElement ME = (MessageElement) it.next();
							if (ME.getName().equalsIgnoreCase("Status"))
							{
								List doc = ME.getChildren();
								if (doc != null)
								{
									status = doc.get(0).toString();
								}
							}
							if (ME.getName().equalsIgnoreCase("Errors"))
							{
								Iterator itr = ME.getChildElements();
								while (itr.hasNext())
								{
									MessageElement ME2 =
											(MessageElement) itr.next();
									if (ME2.getName().equalsIgnoreCase(
											"WsError"))
									{
										Iterator itr1 = ME2.getChildElements();
										while (itr1.hasNext())
										{
											MessageElement ME3 =
													(MessageElement) itr1
															.next();
											if (ME3.getName().equalsIgnoreCase(
													"ErrorCode"))
											{
												List doc2 = ME3.getChildren();
												if (doc2 != null)
												{
													errorCode =
															doc2.get(0)
																	.toString();
													;
												}
											}
											if (ME3.getName().equalsIgnoreCase(
													"ErrorDesc"))
											{
												List doc2 = ME3.getChildren();
												if (doc2 != null)
												{
													errorDesc =
															doc2.get(0)
																	.toString();
													;
												}
											}
										}
									}
								}

							}
						}
					}
				}
			}
		}
		logDB.debug("status" + status);
		logDB.debug("errorCode" + errorCode);
		logDB.debug("errorDesc" + errorDesc);
		if (status.equalsIgnoreCase("Processed"))
			return status;
		else
			return status = errorCode + "-" + errorDesc;
	}

	/**
	 * updateLabResult updates the database with information about the labs
	 * results that were sent to caAERS.
	 * 
	 * @param request
	 */
	private void updateLabResult(HttpServletRequest request)
	{
		Session session = null;
		Date date = new Date();
		try
		{
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			if (session != null)
			{
				HashMap<String, String> labResultIds =
						(HashMap<String, String>) request.getSession()
								.getAttribute("LabResultIDs");
				if (labResultIds != null)
				{
					for (String key : labResultIds.keySet())
					{
						int labResutId =
								Integer.parseInt(labResultIds.get(key));
						LabViewerStatus lvs = new LabViewerStatus();
						lvs.setAdverseEventIndicator("true");
						lvs.setAdverseEventSentDate(date);
						lvs.setClinicalResultId(labResutId);
						session.beginTransaction();
						session.save(lvs);
					}
					session.getTransaction().commit();
				}
			}
		}
		catch (Exception se)
		{
			logDB.error("Error updating Lab Result: ", se);
			if (session.getTransaction() != null)
			{
				session.getTransaction().rollback();
			}

		}
	}

	/**
	 * Update the LabResults for UI display
	 * 
	 * @param request
	 */
	private void updateLabResultForUI(HttpServletRequest request)
	{
		SearchResult searchResult =
				(SearchResult) request.getSession().getAttribute(
						"SEARCH_RESULT");
		List search = searchResult.getSearchResultObjects();
		HashMap<String, String> labResultIds =
				(HashMap<String, String>) request.getSession().getAttribute(
						"LabResultIDs");
		if (labResultIds != null)
		{
			for (String key : labResultIds.keySet())
			{
				int index = (Integer.parseInt(key)) - 1;
				LabActivityResult lar = (LabActivityResult) search.get(index);
				lar.setAdverseEventReported(true);
				lar
						.setAdverseEventReportedDate(new java.util.Date()
								.toString());
			}
			request.getSession().setAttribute("SEARCH_RESULT", searchResult);
		}
	}
}
