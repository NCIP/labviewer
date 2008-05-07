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
import gov.nih.nci.caxchange.ctom.viewer.forms.LabActivitiesSearchForm;
import gov.nih.nci.caxchange.ctom.viewer.forms.LoginForm;
import gov.nih.nci.caxchange.ctom.viewer.util.ObjectFactory;
import gov.nih.nci.caxchange.ctom.viewer.viewobjects.LabActivityResult;
import gov.nih.nci.caxchange.ctom.viewer.viewobjects.LabActivityResultComparator;
import gov.nih.nci.caxchange.ctom.viewer.viewobjects.SearchResult;
import gov.nih.nci.labhub.domain.CD;
import gov.nih.nci.labhub.domain.II;
import gov.nih.nci.labhub.domain.LaboratoryResult;
import gov.nih.nci.labhub.domain.LaboratoryTest;
import gov.nih.nci.labhub.domain.Specimen;
import gov.nih.nci.labhub.domain.SpecimenCollection;
import gov.nih.nci.labhub.domain.Study;
import gov.nih.nci.labhub.domain.StudySite;
import gov.nih.nci.labhub.domain.SubjectAssignment;
import gov.nih.nci.logging.api.user.UserInfoHelper;
import gov.nih.nci.security.exceptions.CSException;
import gov.nih.nci.system.applicationservice.ApplicationService;
import gov.nih.nci.system.client.ApplicationServiceProvider;
import gov.nih.nci.system.query.cql.CQLAssociation;
import gov.nih.nci.system.query.cql.CQLAttribute;
import gov.nih.nci.system.query.cql.CQLGroup;
import gov.nih.nci.system.query.cql.CQLLogicalOperator;
import gov.nih.nci.system.query.cql.CQLObject;
import gov.nih.nci.system.query.cql.CQLPredicate;
import gov.nih.nci.system.query.cql.CQLQuery;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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
 * This class performs the search action. The search action calls the searchObjects method with
 * the user entered search criteria and queries the database.
 * If the search returns a non null resultset; it forwards the user to search results page and
 * displays the results.
 * @author asharma
 * 
 */
public class SearchAction extends Action
{
	private static final Logger logDB = Logger.getLogger(SearchAction.class);
	private static final String CONFIG_FILE = "/baseURL.properties";

	/* (non-Javadoc)
	 * @see org.apache.struts.action.Action#execute(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		
        //gets the session object from HttpRequest 
		HttpSession session = request.getSession();
		
        //search form
		LabActivitiesSearchForm lForm =(LabActivitiesSearchForm) form;
       
		UserInfoHelper.setUserInfo(((LoginForm) session
				.getAttribute(DisplayConstants.LOGIN_OBJECT)).getLoginId(),
				session.getId());
		try
		{
			SearchResult searchResult = new SearchResult();
			
			//search based on the given search criteria
			searchResult = searchObjects(mapping, lForm, request, errors, messages);
			
		    //if the search returned nothing/null; display message  	
			if (searchResult.getSearchResultObjects() == null
					|| searchResult.getSearchResultObjects().isEmpty())
			{
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
						DisplayConstants.ERROR_ID,
						"The search criteria returned zero results"));
				saveErrors(request, errors);
				if (logDB.isDebugEnabled())
					logDB
							.debug(session.getId()
									+ "|"
									+ ((LoginForm) session
											.getAttribute(DisplayConstants.LOGIN_OBJECT))
											.getLoginId() + "|"
									+ lForm.getFormName()
									+ "|search|Failure|No Records found for "
									+ lForm.getFormName() + " object|"
									+ form.toString() + "|");
				return (mapping.findForward(ForwardConstants.SEARCH_FAILURE));
			}
			
			//if search result is not null; forward to searchresults page
			if (searchResult.getSearchResultMessage() != null
					&& !(searchResult.getSearchResultMessage().trim()
							.equalsIgnoreCase("")))
			{
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						DisplayConstants.MESSAGE_ID, searchResult
								.getSearchResultMessage()));
				saveMessages(request, messages);
			}
			
			session.setAttribute(DisplayConstants.SEARCH_RESULT, searchResult);
		}  
		catch (CSException cse)
		{
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					DisplayConstants.ERROR_ID, cse.getMessage()));
			saveErrors(request, errors);
			if (logDB.isDebugEnabled())
				logDB.debug(session.getId()
						+ "|"
						+ ((LoginForm) session
								.getAttribute(DisplayConstants.LOGIN_OBJECT))
								.getLoginId() + "|" + lForm.getFormName()
						+ "|search|Failure|Error Searching the "
						+ lForm.getFormName() + " object|" + form.toString()
						+ "|" + cse.getMessage());
		}
		catch (Exception e)
		{
			logDB.error(e.getMessage());
		}
		
		//if search result is not null; forward to searchresults page
		//get the base url for caAERS and C3D from the properties file/
		try
		 {   Properties props = new Properties();
			 InputStream stream = getClass().getResourceAsStream(CONFIG_FILE);
			 props.load(stream);
			 String caAERSurl = (String)props.getProperty("BaseURLcaAERS");
		     //String C3Durl = (String)props.getProperty("BaseURLC3D");
		     session.setAttribute("BaseURLcaAERS", caAERSurl);
		     //session.setAttribute("BaseURLC3D", C3Durl);
		 } 
		 catch (FileNotFoundException e1) 
		 {
		     logDB.error("The config file not found: " + CONFIG_FILE);
		 } 
		 catch (IOException e1) 
		 {
			 logDB.error("Error reading the config file: " + CONFIG_FILE);
		 }
		//if search result is not null; forward to searchresults page
		session.setAttribute(DisplayConstants.CURRENT_FORM, lForm);
		if (logDB.isDebugEnabled())
			logDB.debug(session.getId()
					+ "|"
					+ ((LoginForm) session
							.getAttribute(DisplayConstants.LOGIN_OBJECT))
							.getLoginId() + "|" + lForm.getFormName()
					+ "|search|Success|Success in searching "
					+ lForm.getFormName() + " object|" + form.toString() + "|");
		
		return (mapping.findForward(ForwardConstants.SEARCH_SUCCESS));
	}

	/**
	 * SearchObjects queries the database with the user entered search criteria.
	 * returns the search results
	 * @param mapping
	 * @param form
	 * @param request
	 * @param errors
	 * @param messages
	 * @return searchResult
	 * @throws Exception
	 */
	private SearchResult searchObjects(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			ActionErrors errors, ActionMessages messages) throws Exception
	{
		HashMap map = new HashMap();
		List allLarList = new ArrayList();
		
		//search form 
		LabActivitiesSearchForm lForm = (LabActivitiesSearchForm) form;
		try
		{
			ApplicationService appService = ApplicationServiceProvider.getApplicationService();

			// Create the query to get SubjectAssignment object
			CQLQuery query = new CQLQuery();
			CQLObject target = new CQLObject();
			target.setName("gov.nih.nci.labhub.domain.SubjectAssignment");
			
			// Set the subject Identifier on the association to II
			CQLAssociation subjectAssignmentAssociation1 = new CQLAssociation();
			subjectAssignmentAssociation1.setName("gov.nih.nci.labhub.domain.II");
			subjectAssignmentAssociation1.setTargetRoleName("studySubjectIdentifier");
			subjectAssignmentAssociation1.setAttribute(new CQLAttribute("extension",
					CQLPredicate.EQUAL_TO, lForm.getPatientId().trim()));
			
			// Now get to StudySite
			CQLAssociation subjectAssignmentAssociation2 = new CQLAssociation();
			subjectAssignmentAssociation2.setName("gov.nih.nci.labhub.domain.StudySite");
			subjectAssignmentAssociation2.setTargetRoleName("studySite");

			// Now get to Study
			CQLAssociation studySiteAssociation1 = new CQLAssociation();
			studySiteAssociation1.setName("gov.nih.nci.labhub.domain.Study");
			studySiteAssociation1.setTargetRoleName("study");
			subjectAssignmentAssociation2.setAssociation(studySiteAssociation1);
			
			// Now set the study identifier on the association to II
			CQLAssociation studyAssociation1 = new CQLAssociation();
			studyAssociation1.setName("gov.nih.nci.labhub.domain.II");
			studyAssociation1.setTargetRoleName("studyIdentifier");
			studyAssociation1.setAttribute(new CQLAttribute("extension",
					CQLPredicate.EQUAL_TO, lForm.getStudyId().trim()));
			studySiteAssociation1.setAssociation(studyAssociation1);

			// Now get the SpecimenCollection
			CQLAssociation subjectAssignmentAssociation3 = new CQLAssociation();
			subjectAssignmentAssociation3.setName("gov.nih.nci.labhub.domain.SpecimenCollection");
			subjectAssignmentAssociation3.setTargetRoleName("activityCollection");

			// Set the date conditions
			CQLGroup group = new CQLGroup();
			group.addAttribute(new CQLAttribute("actualStartDateTime",
					CQLPredicate.GREATER_THAN, lForm.getBeginDate()));
			group.addAttribute(new CQLAttribute("actualStartDateTime",
					CQLPredicate.LESS_THAN, lForm.getEndDate()));

			group.setLogicOperator(CQLLogicalOperator.AND);
			subjectAssignmentAssociation3.setGroup(group);

			// Then put it all together
			CQLGroup finalgroup = new CQLGroup();
			finalgroup.addAssociation(subjectAssignmentAssociation1);
			finalgroup.addAssociation(subjectAssignmentAssociation2);
			finalgroup.addAssociation(subjectAssignmentAssociation3);
			finalgroup.setLogicOperator(CQLLogicalOperator.AND);
			target.setGroup(finalgroup);

			query.setTarget(target);
			List resultList = appService.query(query);
			
			// Get a connection to the database using the hibernate configuration
			/*
			XmlBeanFactory factory = new XmlBeanFactory(new ClassPathResource("DAOConfig.xml"));
			LabViewerDAOSupport dao = (LabViewerDAOSupport)factory.getBean("DAO");
			Session session = dao.getSessionFactory().openSession();

			Connection connection = session.connection();*/
			
			List larlist = null;

			int i = 0;
			for (Iterator resultsIterator = resultList.iterator(); resultsIterator.hasNext();)
			{
				SubjectAssignment sa = (SubjectAssignment) resultsIterator.next();

				LabActivityResult labActivityResult = null;

				larlist = printRecord(sa, lForm.getBeginDate(), lForm.getEndDate(),request);

				for (int j = 0; j < larlist.size(); j++)
				{
					i++;
					labActivityResult = (LabActivityResult) larlist.get(j);
					labActivityResult.setAdverseEventReported("0");
					/*
					ResultSet rs = connection
							.createStatement()
							.executeQuery(
									"select ADVERSE_EVENT_INDICATOR, UPDATE_DATE from LV_ADVERSE_EVENTS where CLINICAL_RESULT_ID = "
											+ labActivityResult
													.getLabResultId());
					if (rs.next()) {
						labActivityResult.setAdverseEventReported(rs
								.getString(1));
						labActivityResult
								.setAdverseEventReportedDate(new SimpleDateFormat(
										"MM-dd-yyyy").format(rs.getDate(2)));
					} else {
						labActivityResult.setAdverseEventReported("0");
					}
					// labActivityResult.setRecordId(Integer.toString(i));

					// map.put(Integer.toString(i), labActivityResult);
					 */
					allLarList.add(labActivityResult);
				}
			}
			if (allLarList != null && allLarList.size() > 0)
			{
				LabActivityResultComparator comp = (LabActivityResultComparator) ObjectFactory
						.getObject("labActivityResultComparator");
				Collections.sort(allLarList, comp);
				int k = 0;
				for (int j = 0; j < allLarList.size(); j++)
				{
					k++;
					LabActivityResult labActivityResult = null;
					labActivityResult = (LabActivityResult) allLarList
							.get(j);
					labActivityResult.setRecordId(Integer.toString(k));
					map.put(Integer.toString(k), labActivityResult);
				}
			}
		}
		catch (Exception ex)
		{
			logDB.error(ex.getMessage());
		}
		
		SearchResult searchResult = new SearchResult();
		searchResult.setSearchResultObjects(allLarList);
		request.getSession().setAttribute("RESULT_SET", map);
		request.getSession().setAttribute("SEARCH_RESULT", searchResult);
				 
		return searchResult;
	}

	/**
	 * printRecord creates the view object that will properly display the results
	 * <P>
	 * @param sa
	 * @param beginDate2
	 * @param endDate2
	 * @return
	 * @throws ParseException
	 */
	private ArrayList printRecord(SubjectAssignment sa, String beginDate2, String endDate2,HttpServletRequest request)
		throws ParseException
	{
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

		ArrayList list = new ArrayList();

		Date beginActDate = null;
		Date endActDate = null;

		beginActDate = formatter.parse(beginDate2);
		endActDate = formatter.parse(endDate2);

		String patientId = null;
		String studyId = null;
		String date = null;
		Date actualDate = null;
		String labTestId = null;
		String textResult = null;
		Float numericResult = null;
		String unitOfMeasure = null;
		Float lowRange;
		Float highRange;
		Collection<II> identifiers = null;
		String studyGridId=null;

		if (sa == null)
			return null;

		if (sa != null)
		{
			LabActivityResult labActivityResult = new LabActivityResult();

			labActivityResult.setSubjectAssignment(sa);
			
			// Set the patient ID
			identifiers = sa.getStudySubjectIdentifier();
			patientId = retrieveIdentifier(identifiers);
			labActivityResult.setPatientId(patientId);
			identifiers = sa.getStudySubjectIdentifier();
			studyGridId=retriveGridId(identifiers);
			request.getSession().setAttribute("studySubjectGridId", studyGridId);

			// Set the study ID
			StudySite site = sa.getStudySite();
			if (site != null)
			{
				Study study = site.getStudy();
				identifiers = study.getStudyIdentifier();
				studyId = retrieveIdentifier(identifiers);
				labActivityResult.setStudyId(studyId);
			}
			
			// Now set the lab information
			Collection activityCollection = sa.getActivityCollection();
			if (activityCollection != null && activityCollection.size() > 0)
			{
				for (Iterator activityIterator = activityCollection.iterator(); activityIterator.hasNext();)
				{
					SpecimenCollection activity = (SpecimenCollection) activityIterator.next();

					actualDate = activity.getActualStartDateTime();

					if (actualDate.before(beginActDate) || actualDate.after(endActDate))
						continue;

					date = convertToString(activity.getActualStartDateTime());
					labActivityResult.setDate(date);
					labActivityResult.setActualDate(actualDate);
					Collection specimenCollection = activity.getSpecimenCollection();
					
					if (specimenCollection != null && specimenCollection.size() > 0)
					{
						for (Iterator specimenIterator = specimenCollection
								.iterator(); specimenIterator.hasNext();)
						{
							Specimen specimen = (Specimen) specimenIterator.next();
							Collection labTestCollection = specimen.getLaboratoryTestCollection();

							if (labTestCollection != null && labTestCollection.size() > 0)
							{
								for (Iterator labTestIterator = labTestCollection
										.iterator(); labTestIterator.hasNext();)
								{
									LaboratoryTest labTest = (LaboratoryTest) labTestIterator.next();
									if (labTest != null)
									{
										CD labTestIde = labTest.getLaboratoryTestId();
										if (labTestIde != null)
										{
											labTestId = labTestIde.getCode();
											labActivityResult.setLabTestId(labTestId);
										}
										LaboratoryResult labResult = labTest.getLaboratoryResult();

										if (labResult != null)
										{
											numericResult = labResult.getNumericResult();
											labActivityResult.setLabResultId(labResult.getId().toString());

											if (numericResult != null)
												labActivityResult.setNumericResult(numericResult.toString());
											else
												labActivityResult.setNumericResult("");

											textResult = labResult.getTextResult();
											labActivityResult.setTextResult(textResult);
											
											CD units = labResult.getUnits();
											if (units != null)
											{
												unitOfMeasure = units.getCode();
												labActivityResult.setUnitOfMeasure(unitOfMeasure);
											}

											lowRange = new Float ( labResult.getReferenceRangeLow());
											labActivityResult.setLowRange(String.valueOf(lowRange));
											highRange = new Float (labResult.getReferenceRangeHigh());

											if ((numericResult != null) && (lowRange != null) && (highRange != null))
											{
												if (numericResult < lowRange || numericResult > highRange)
												{
													labActivityResult.setIsAdverseEvent(DisplayConstants.YES);
												}
												else
												{
													labActivityResult.setIsAdverseEvent(DisplayConstants.NO);
												}
											}
											else
											{
												labActivityResult.setIsAdverseEvent(DisplayConstants.NO);
											}

											labActivityResult.setHighRange(String.valueOf(highRange));
											labActivityResult.setLabResult(labResult);
											list.add(labActivityResult);
											labActivityResult = new LabActivityResult();
											
											// Re set the values
											labActivityResult.setSubjectAssignment(sa);
											labActivityResult.setPatientId(patientId);
											labActivityResult.setStudyId(studyId);
											labActivityResult.setDate(date);
											labActivityResult.setActualDate(actualDate);

										}
									}
								}
							}
						}
					}
				}
			}
		}
		
		return list;
	}
	
	private String retrieveIdentifier(Collection<II> identifiers)
	{
		String identifier = null;
		
		Iterator<II> idIterator = identifiers.iterator();
		identifier = idIterator.next().getExtension();
		
		return identifier;
	}

	private static String convertToString(java.util.Date date) {
		if (date == null)
			return "-";
		SimpleDateFormat formatter = new SimpleDateFormat();
		String str = formatter.format(date);
		return str;
	}
	private String retriveGridId(Collection<II> identifiers)
	{
		String identifier = null;
		
		Iterator<II> idIterator = identifiers.iterator();
		identifier = idIterator.next().getRoot();
		
		return identifier;
	}
}

