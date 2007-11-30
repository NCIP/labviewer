/**
 * 
 */
package gov.nih.nci.caxchange.ctom.viewer.actions;

import gov.nih.nci.c3d.webservices.client.C3DGridServiceClient;
import gov.nih.nci.cagrid.caxchange.client.CaXchangeRequestProcessorClient;
import gov.nih.nci.cagrid.common.Utils;
import gov.nih.nci.caxchange.Credentials;
import gov.nih.nci.caxchange.Message;
import gov.nih.nci.caxchange.MessagePayload;
import gov.nih.nci.caxchange.MessageTypes;
import gov.nih.nci.caxchange.Metadata;
import gov.nih.nci.caxchange.Request;
import gov.nih.nci.caxchange.ctom.viewer.constants.DisplayConstants;
import gov.nih.nci.caxchange.ctom.viewer.constants.ForwardConstants;
import gov.nih.nci.caxchange.ctom.viewer.forms.LabActivitiesSearchResultForm;
import gov.nih.nci.caxchange.ctom.viewer.forms.LoginForm;
import gov.nih.nci.caxchange.ctom.viewer.viewobjects.LabActivityResult;
import gov.nih.nci.labhub.domain.II;
import gov.nih.nci.logging.api.user.UserInfoHelper;

import java.io.InputStream;
import java.io.PrintWriter;
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
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

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
import org.w3c.dom.Document;

import webservices.Documentation;
import webservices.LabResult;
import webservices.LoadLabsRequest;
import webservices.Participant;
import webservices.PerformedActivity;
import webservices.PerformedStudy;
import webservices.StudySubject;

/**
 * This class performs the Load to CTMS action. It loads the selected form data to CTMS.
 * It checks if valid login information is in session; if not it redirects the user to login page.
 * 
 * @author asharma
 *
 */
public class LoadToCTMSAction extends Action
{
	
	private static final Logger logDB = Logger.getLogger(LoadToCTMSAction.class);

	/* (non-Javadoc)
	 * @see org.apache.struts.action.Action#execute(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		
		HttpSession session = request.getSession();
		LabActivitiesSearchResultForm lForm = (LabActivitiesSearchResultForm) form;
		
		//if the session is new or the login object is null; redirects the user to login page  
		if (session.isNew() || (session.getAttribute(DisplayConstants.LOGIN_OBJECT) == null))
		{
			logDB.error("No Session or User Object Forwarding to the Login Page");
			return mapping.findForward(ForwardConstants.LOGIN_PAGE);
		}
		
		UserInfoHelper.setUserInfo(((LoginForm)session.getAttribute(DisplayConstants.LOGIN_OBJECT)).getLoginId(), session.getId());		
		
		try
		{  //calls the loadToCTMS method
			loadToCTMS(request, lForm);
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(DisplayConstants.MESSAGE_ID, "Messages Submitted to CTMS Successfully"));
			saveMessages( request, messages );
		}
		catch (Exception cse)
		{
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(DisplayConstants.ERROR_ID, "Error in Submitting Messages to CTMS"));
			saveErrors( request,errors );
			logDB.error("Error sending labs to CTMS", cse);
		}
		session.setAttribute(DisplayConstants.CURRENT_FORM, lForm);
		
		//if the login is valid and the selected form data is successfully loaded to CTMS; 
		//it returns to the search results page and displays the load successful message	
		return (mapping.findForward(ForwardConstants.LOAD_TO_CTMS_EVENT_SUCCESS));
	}
	
	 /**
	 * Collects the selectd form data and calls the EvenManager sendLabActivitiesmethod to
	 * load the data to CTMS
	 * @param request
	 * @param form
	 * @throws Exception
	 */
	private void loadToCTMS(HttpServletRequest request,ActionForm form) throws Exception
	{
	    LabActivitiesSearchResultForm lForm = (LabActivitiesSearchResultForm)form;
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

		// Then create the request
		String url = "http://NT-CBIOC3PRJB-1.nci.nih.gov:8080/wsrf/services/cagrid/C3DGridService";
		C3DGridServiceClient client = new C3DGridServiceClient(url);
		//CaXchangeRequestProcessorClient client = new CaXchangeRequestProcessorClient(url);
		
		LoadLabsRequest labRequest = new LoadLabsRequest();
		
		// Then for each lab selected set the lab information
		LabResult labResults[]= new LabResult[list.size()];
		int i = 0;
		
		for (Iterator labs = list.iterator(); labs.hasNext();)
		{
			LabActivityResult lab = (LabActivityResult)labs.next();
			
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
			Participant participant= new Participant();
			StudySubject studySubject= new StudySubject();
			
			Collection<II> studySubjectIds = lab.getSubjectAssignment().getStudySubjectIdentifier();
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
			PerformedActivity performedActivity= new PerformedActivity();
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
			String result = lab.getNumericResult();
			if (result != null)
				labResult.setNumericResult(Float.parseFloat(result));
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
		
		
		PrintWriter writer = new PrintWriter("c3dmessage.xml");
		QName lab = new QName("LoadLabsRequest");
        Utils.serializeObject(labRequest, lab, writer);
        
        /*
		// Create the caxchange message
		Message requestMessage = new Message();
		requestMessage.getMetadata().setMessageType(MessageTypes.LOAD_LAB_TO_CDMS);
        MessagePayload messagePayload = new MessagePayload();
        URI uri = new URI();
        uri.setPath("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain");
        messagePayload.setXmlSchemaDefinition(uri);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        
        //Document payload = db.parse(testMessage);
        MessageElement messageElement = new MessageElement(lab, labRequest);
        messagePayload.set_any(new MessageElement[]{messageElement});
        requestMessage.getRequest().setBusinessMessagePayload(messagePayload);
        */
		
		// Now send the load labs request
		webservices.Acknowledgement acknowledgement = client.loadLabs(labRequest);
		logDB.info("Load acknowledgement was " + acknowledgement);
	
		lForm.setRecordId("");
		lForm.setRecordId(null);
	}	

}
