/**
 * Copyright Notice.  Copyright 2008  Scenpro, Inc (“caBIG™ Participant”).caXchange
 * was created with NCI funding and is part of the caBIG™ initiative. 
 * The software subject to this notice and license includes both human readable source code form and 
 * machine readable, binary, object code form (the “caBIG™ Software”).
 * This caBIG™ Software License (the “License”) is between caBIG™ Participant and You.  
 * “You (or “Your”) shall mean a person or an entity, and all other entities that control, 
 * are controlled by, or are under common control with the entity.  “Control” for purposes of this 
 * definition means (i) the direct or indirect power to cause the direction or management of such entity, 
 * whether by contract or otherwise, or (ii) ownership of fifty percent (50%) or more of the outstanding shares, 
 * or (iii) beneficial ownership of such entity.  
 * License.  Provided that You agree to the conditions described below, caBIG™ Participant grants 
 * You a non-exclusive, worldwide, perpetual, fully-paid-up, no-charge, irrevocable, transferable and 
 * royalty-free right and license in its rights in the caBIG™ Software, including any copyright or patent rights therein, to 
 * (i) use, install, disclose, access, operate, execute, reproduce, copy, modify, translate, market, publicly display, 
 * publicly perform, and prepare derivative works of the caBIG™ Software in any manner and for any purpose, and to have 
 * or permit others to do so; (ii) make, have made, use, practice, sell, and offer for sale, import, and/or otherwise 
 * dispose of caBIG™ Software (or portions thereof); (iii) distribute and have distributed to and by third parties the 
 * caBIG™ Software and any modifications and derivative works thereof; and (iv) sublicense the foregoing rights 
 * set out in (i), (ii) and (iii) to third parties, including the right to license such rights to further third parties.  
 * For sake of clarity, and not by way of limitation, caBIG™ Participant shall have no right of accounting or right of payment
 *  from You or Your sublicensees for the rights granted under this License.  This License is granted at no charge to You.  
 *  Your downloading, copying, modifying, displaying, distributing or use of caBIG™ Software constitutes acceptance of all 
 *  of the terms and conditions of this Agreement.  If you do not agree to such terms and conditions, you have no right to 
 *  download, copy, modify, display, distribute or use the caBIG™ Software.  
 * 1.	Your redistributions of the source code for the caBIG™ Software must retain the above copyright notice, 
 * 		this list of conditions and the disclaimer and limitation of liability of Article 6 below.  
 * 		Your redistributions in object code form must reproduce the above copyright notice, this list of conditions and 
 * 		the disclaimer of Article 6 in the documentation and/or other materials provided with the distribution, if any.
 * 2.	Your end-user documentation included with the redistribution, if any, must include the following acknowledgment: 
 * 		“This product includes software developed by Scenpro, Inc.”  
 * 		If You do not include such end-user documentation, You shall include this acknowledgment in the caBIG™ Software itself, 
 * 		wherever such third-party acknowledgments normally appear.
 * 3.	You may not use the names  “Scenpro, Inc”, 
 * 		“The National Cancer Institute”, “NCI”, “Cancer Bioinformatics Grid” or “caBIG™” to endorse or promote products 
 * 		derived from this caBIG™ Software.  This License does not authorize You to use any trademarks, service marks, trade names,
 * 		logos or product names of either caBIG™ Participant, NCI or caBIG™, except as required to comply with the terms of this 
 * 		License.
 * 4.	For sake of clarity, and not by way of limitation, You may incorporate this caBIG™ Software into Your proprietary 
 * 		programs and into any third party proprietary programs.  However, if You incorporate the caBIG™ Software into third party 
 * 		proprietary programs, You agree that You are solely responsible for obtaining any permission from such third parties 
 * 		required to incorporate the caBIG™ Software into such third party proprietary programs and for informing Your sublicensees, 
 * 		including without limitation Your end-users, of their obligation to secure any required permissions from such third parties 
 * 		before incorporating the caBIG™ Software into such third party proprietary software programs.  In the event that You fail to 
 * 		obtain such permissions, You agree to indemnify caBIG™ Participant for any claims against caBIG™ Participant by such third 
 * 		parties, except to the extent prohibited by law, resulting from Your failure to obtain such permissions.
 * 5.	For sake of clarity, and not by way of limitation, You may add Your own copyright statement to Your modifications and 
 * 		to the derivative works, and You may provide additional or different license terms and conditions in Your sublicenses of 
 * 		modifications of the caBIG™ Software, or any derivative works of the caBIG™ Software as a whole, provided Your use, reproduction, 
 * 		and distribution of the Work otherwise complies with the conditions stated in this License.
 * 6.	THIS caBIG™ SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED WARRANTIES (INCLUDING, BUT NOT LIMITED TO, 
 * 		THE IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED.  
 * 		IN NO EVENT SHALL THE Scenpro, Inc OR ITS AFFILIATES 
 * 		BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * 		PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON 
 * 		ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY 
 * 		OUT OF THE USE OF THIS caBIG™ SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * 
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
 * This class Saves the Lab data Search results to a CSV file. 
 * It checks if valid login information is in session; if not it redirects the user to login page.
 * 
 * @author asharma
 *
 */
public class SaveToCSVAction extends Action
{
	private static final Logger logDB = Logger.getLogger(SaveToCSVAction.class);
	
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
		
		String username = ((LoginForm)session.getAttribute(DisplayConstants.LOGIN_OBJECT)).getLoginId();
		
		UserInfoHelper.setUserInfo(username, session.getId());		
		
		try
		{  
			//calls the saveToCSV method
			saveToCSV(request, lForm, username,response);
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(DisplayConstants.MESSAGE_ID, "Messages Saved to CSV file Successfully"));
			saveMessages( request, messages );
			logDB.info(new ActionMessage(DisplayConstants.MESSAGE_ID, "Messages Saved to CSV file Successfully"));
			
		}
		catch (Exception cse)
		{
			String msg = cse.getMessage();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(DisplayConstants.ERROR_ID, "Error in Saving labs to CSV: " + msg));
			saveErrors( request,errors );
			logDB.error("Error saving labs to CSV File", cse);
		}
		session.setAttribute(DisplayConstants.CURRENT_FORM, lForm);
		
		//if the login is valid and the selected form data is successfully saved to a csv file; 
		//it returns to the search results page and displays the save successful message	
		return (mapping.findForward(ForwardConstants.SAVE_TO_CSV_EVENT_SUCCESS));
	}
	
	
	/**
	 * Gets the CSV file name and type
	 * @param response
	 * @return
	 */
	private ActionForward getFileName(HttpServletResponse response)
	{
		//get the filename
		response.setContentType("text/csv");
        response.setHeader("Content-disposition", "attachment;filename=LabResults.csv");
		return null;
		
	}
	
	/**
	 * Collects the selected form data and saves the labs to CSV file
	 * @param request
	 * @param form
	 * @param username
	 * @param response
	 * @throws Exception
	 */
	private void saveToCSV(HttpServletRequest request,ActionForm form, String username, HttpServletResponse response) throws Exception
	{
		logDB.info("In Save to CSV");
		getFileName(response);
	    LabActivitiesSearchResultForm lForm = (LabActivitiesSearchResultForm)form;
	    HashMap map = (HashMap) request.getSession().getAttribute("RESULT_SET");
		ArrayList list = new ArrayList();
		String[] test = lForm.getRecordIds();
		//StringTokenizer stringTokenizer = new StringTokenizer(test, ",");
		int count=0;
		if(test!=null)
		{
			count = test.length;
			for(int i=0;i<count;i++)
			{
				if(map.get(test[i]) != null){
					list.add(map.get(test[i]));	
				}
			}
		}
		int numOfLabs=0;
		// Create the list of results to send
		/*if (count >= 1)
		{
			while (stringTokenizer.hasMoreTokens())
			{
				list.add(map.get(stringTokenizer.nextToken()));
			}
		}
		else*/
		
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
		String heading=" Patient Id,Date / Time,Lab Test,Text Result,Numeric Result,Unit Of Measure,Lower Limit,Upper Limit";
		out.write(heading);
		out.write("\n");
		logDB.info("Selected Lab results:"+labResults.length);
		for(int ii=0; ii<labResults.length;ii++)
		{
			LabResult result = labResults[ii];
			data = result.getStudySubject().getParticipant().getII(0).getExtension() +","+ convertToString(new Date (result.getReportedDateTime().getTimeInMillis()))+","+result.getStudySubject().getPerformedActivity(0).getName()+","+result.getTextResult()+","+result.getNumericResult()+","+result.getNumericUnit()+","+result.getReferenceRangeLow()+","+result.getReferenceRangeHigh()+"\n";
			//System.out.println("Data: "+data);
			out.write(data);
			data="";
		}
		out.flush();
		out.close();	
	}	
	
	/**
	 * @param date
	 * @return str formatted date.
	 */
	private static String convertToString(java.util.Date date) {
		if (date == null)
			return "-";
		SimpleDateFormat formatter = new SimpleDateFormat();
		String str = formatter.format(date);
		return str;
	}	

}
