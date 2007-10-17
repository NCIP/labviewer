/**
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
import gov.nih.nci.labhub.domain.ConceptDescriptorDataType;
import gov.nih.nci.labhub.domain.LabResult;
import gov.nih.nci.labhub.domain.LabTest;
import gov.nih.nci.labhub.domain.Specimen;
import gov.nih.nci.labhub.domain.SpecimenCollection;
import gov.nih.nci.labhub.domain.Study;
import gov.nih.nci.labhub.domain.StudySite;
import gov.nih.nci.labhub.domain.SubjectAssignment;
import gov.nih.nci.logging.api.user.UserInfoHelper;
import gov.nih.nci.security.exceptions.CSException;
import gov.nih.nci.system.applicationservice.ApplicationService;
import gov.nih.nci.system.applicationservice.ApplicationServiceProvider;
import gov.nih.nci.system.dao.impl.orm.ORMConnection;
import gov.nih.nci.system.query.cql.CQLAssociation;
import gov.nih.nci.system.query.cql.CQLAttribute;
import gov.nih.nci.system.query.cql.CQLGroup;
import gov.nih.nci.system.query.cql.CQLLogicalOperator;
import gov.nih.nci.system.query.cql.CQLObject;
import gov.nih.nci.system.query.cql.CQLPredicate;
import gov.nih.nci.system.query.cql.CQLQuery;

import java.sql.Connection;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

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
import org.hibernate.Session;

/**
 * This class performs the search action. The search action calls the searchObjects method with
 * the user entered search criteria and queries the database.
 * If the search returns a non null resultset; it forwards the user to search results page and
 * displays the results.
 * @author asharma
 * 
 */
public class SearchAction extends Action {

	private static final Logger logDB = Logger.getLogger(SearchAction.class);

	/* (non-Javadoc)
	 * @see org.apache.struts.action.Action#execute(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
        //gets the session object from HttpRequest 
		HttpSession session = request.getSession();
        //search form
		LabActivitiesSearchForm lForm = (LabActivitiesSearchForm) form;
       
		UserInfoHelper.setUserInfo(((LoginForm) session
				.getAttribute(DisplayConstants.LOGIN_OBJECT)).getLoginId(),
				session.getId());
		try {
			   SearchResult searchResult=new SearchResult();
			   //search based on the given search criteria
				searchResult = searchObjects(mapping, lForm, request, errors,
						messages);
		      //if the search returned nothing/null; display message  	
			if (searchResult.getSearchResultObjects() == null
					|| searchResult.getSearchResultObjects().isEmpty()) {
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
							.equalsIgnoreCase(""))) {
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						DisplayConstants.MESSAGE_ID, searchResult
								.getSearchResultMessage()));
				saveMessages(request, messages);
			}
			session.setAttribute(DisplayConstants.SEARCH_RESULT, searchResult);
		}  
		catch (CSException cse) {
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
		catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			logDB.error(e.getMessage());
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
			ActionErrors errors, ActionMessages messages) throws Exception {
		
		HashMap map = new HashMap();
		List allLarList = new ArrayList();
		//search form 
		LabActivitiesSearchForm lForm = (LabActivitiesSearchForm) form;
		try {

			ApplicationService appService = ApplicationServiceProvider
					.getApplicationService();

			CQLQuery query = new CQLQuery();
			CQLObject target = new CQLObject();
			target.setName("gov.nih.nci.labhub.domain.SubjectAssignment");
			target.setAttribute(new CQLAttribute("studySubjectIdentifier",
					CQLPredicate.EQUAL_TO, lForm.getPatientId().trim()));

			CQLAssociation association3 = new CQLAssociation();
			association3.setName("gov.nih.nci.labhub.domain.StudySite");
			association3.setTargetRoleName("studySite");

			CQLAssociation association4 = new CQLAssociation();
			association4.setName("gov.nih.nci.labhub.domain.Study");
			association4.setTargetRoleName("study");
			// association4.setAttribute(new
			// CQLAttribute("identifier",CQLPredicate.EQUAL_TO,"04_C_0121"));
			association4.setAttribute(new CQLAttribute("identifier",
					CQLPredicate.EQUAL_TO, lForm.getStudyId().trim()));
			association3.setAssociation(association4);

			CQLAssociation association1 = new CQLAssociation();
			association1
					.setName("gov.nih.nci.labhub.domain.SpecimenCollection");
			association1.setTargetRoleName("activityCollection");

			CQLGroup group = new CQLGroup();
			// group.addAttribute(new
			// CQLAttribute("actualStartDateTime",CQLPredicate.GREATER_THAN,"11/11/2005"));
			// group.addAttribute(new
			// CQLAttribute("actualStartDateTime",CQLPredicate.LESS_THAN,"11/11/2009"));
			group.addAttribute(new CQLAttribute("actualStartDateTime",
					CQLPredicate.GREATER_THAN, lForm.getBeginDate()));
			group.addAttribute(new CQLAttribute("actualStartDateTime",
					CQLPredicate.LESS_THAN, lForm.getEndDate()));

			group.setLogicOperator(CQLLogicalOperator.AND);
			association1.setGroup(group);

			CQLGroup finalgroup = new CQLGroup();
			finalgroup.addAssociation(association1);
			finalgroup.addAssociation(association3);
			finalgroup.setLogicOperator(CQLLogicalOperator.AND);
			target.setGroup(finalgroup);

			query.setTarget(target);

			List resultList = appService.query(query,
					"gov.nih.nci.labhub.domain.SubjectAssignment");
			Connection connection = null;
			Session session = null;

			try {

				session = ORMConnection
						.openSession("gov.nih.nci.labhub.domain.SubjectAssignment");
				connection = session.connection();
				List larlist = null;

				int i = 0;
				for (Iterator resultsIterator = resultList.iterator(); resultsIterator
						.hasNext();) {
					SubjectAssignment sa = (SubjectAssignment) resultsIterator
							.next();

					sa.getStudySite().getStudy().getIdentifier();

					LabActivityResult labActivityResult = null;

					larlist = printRecord(sa, lForm.getBeginDate(), lForm
							.getEndDate());

					for (int j = 0; j < larlist.size(); j++) {
						i++;
						labActivityResult = (LabActivityResult) larlist.get(j);
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
						allLarList.add(labActivityResult);
					}
				}
				if (allLarList != null && allLarList.size() > 0) {
					LabActivityResultComparator comp = (LabActivityResultComparator) ObjectFactory
							.getObject("labActivityResultComparator");
					Collections.sort(allLarList, comp);
					int k = 0;
					for (int j = 0; j < allLarList.size(); j++) {
						k++;
						LabActivityResult labActivityResult = null;
						labActivityResult = (LabActivityResult) allLarList
								.get(j);
						labActivityResult.setRecordId(Integer.toString(k));
						map.put(Integer.toString(k), labActivityResult);
					}
				}

			} finally {
				try {
					session.close();
				} catch (Exception ex) {
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		SearchResult searchResult = new SearchResult();
		// searchResult.setSearchResultObjects(new ArrayList(map.values()));
		searchResult.setSearchResultObjects(allLarList);
		request.getSession().setAttribute("RESULT_SET", map);
		request.getSession().setAttribute("SEARCH_RESULT", searchResult);
		return searchResult;
	}

	/**
	 * @param sa
	 * @param beginDate2
	 * @param endDate2
	 * @return
	 * @throws ParseException
	 */
	private static ArrayList printRecord(SubjectAssignment sa,
			String beginDate2, String endDate2) throws ParseException {

		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

		ArrayList list = new ArrayList();

		Date beginActDate = null;
		Date endActDate = null;

		beginActDate = formatter.parse(beginDate2);
		endActDate = formatter.parse(endDate2);

		String patientId = null;
		String date = null;
		Date actualDate = null;
		String labTestId = null;
		String textResult = null;
		String numericResult = null;
		String unitOfMeasure = null;
		Double lowRange;
		Double highRange;

		if (sa == null)
			return null;

		if (sa != null) {
			LabActivityResult labActivityResult = new LabActivityResult();

			labActivityResult.setSubjectAssignment(sa);
			patientId = sa.getStudySubjectIdentifier();

			StudySite site = sa.getStudySite();
			if (site != null) {
				Study study = site.getStudy();
				labActivityResult.setStudyId(study.getIdentifier());
			}
			labActivityResult.setPatientId(sa.getStudySubjectIdentifier());
			Collection activityCollection = sa.getActivityCollection();
			if (activityCollection != null && activityCollection.size() > 0) {
				for (Iterator activityIterator = activityCollection.iterator(); activityIterator
						.hasNext();) {
					SpecimenCollection activity = (SpecimenCollection) activityIterator
							.next();

					actualDate = activity.getActualStartDateTime();

					if (actualDate.before(beginActDate)
							|| actualDate.after(endActDate))
						continue;

					date = convertToString(activity.getActualStartDateTime());
					labActivityResult.setDate(date);
					labActivityResult.setActualDate(actualDate);
					Collection specimenCollection = activity
							.getSpecimenCollection();
					if (specimenCollection != null
							&& specimenCollection.size() > 0) {
						for (Iterator specimenIterator = specimenCollection
								.iterator(); specimenIterator.hasNext();) {
							Specimen specimen = (Specimen) specimenIterator
									.next();
							Collection labTestCollection = specimen
									.getLabTestCollection();

							if (labTestCollection != null
									&& labTestCollection.size() > 0) {
								for (Iterator labTestIterator = labTestCollection
										.iterator(); labTestIterator.hasNext();) {
									LabTest labTest = (LabTest) labTestIterator
											.next();
									if (labTest != null) {
										ConceptDescriptorDataType labTestIde = labTest
												.getLabTestId();
										if (labTestIde != null) {
											labTestId = labTestIde.getCode();
											labActivityResult
													.setLabTestId(labTestId);
										}
										LabResult labResult = labTest
												.getLabResult();

										if (labResult != null) {
											Double numResult = null;
											numericResult = labResult.getNumericResult();
											labActivityResult
													.setLabResultId(labResult
															.getId().toString());

											try {
												numResult = Double
														.parseDouble(numericResult);
												labActivityResult
														.setNumericResult(numericResult);
											} catch (Exception ex) {
												labActivityResult
														.setNumericResult("");
											}

											textResult = labResult
													.getTextResult();
											labActivityResult
													.setTextResult(textResult);
											ConceptDescriptorDataType units = labResult
													.getUnits();
											if (units != null) {
												unitOfMeasure = units.getCode();
												labActivityResult
														.setUnitOfMeasure(unitOfMeasure);
											}

											lowRange = labResult
													.getReferenceRangeLow();
											labActivityResult
													.setLowRange(String
															.valueOf(lowRange));
											highRange = labResult
													.getReferenceRangeHigh();

											if (numResult != null) {
												if (numResult < lowRange
														|| numResult > highRange) {
													labActivityResult
															.setIsAdverseEvent(DisplayConstants.YES);
													System.out
															.println(DisplayConstants.YES);
												} else {
													labActivityResult
															.setIsAdverseEvent(DisplayConstants.NO);
												}
											} else {
												labActivityResult
														.setIsAdverseEvent(DisplayConstants.NO);
											}

											labActivityResult
													.setHighRange(String
															.valueOf(highRange));
											labActivityResult
													.setLabResult(labResult);
											list.add(labActivityResult);
											labActivityResult = new LabActivityResult();
											// Re set the values

											/*
											 * SubjectAssignment sac = new
											 * SubjectAssignment();
											 * sac.setId(sa.getId());
											 * sac.setStudySite(sa.getStudySite());
											 * sac.setStudySubjectIdentifier(sa.getStudySubjectIdentifier());
											 * sac.setType(sa.getType());
											 * Collection actCol = new
											 * ArrayList(); SpecimenCollection
											 * speccollClo = new
											 * SpecimenCollection();
											 * 
											 * 
											 * Specimen specClo = new Specimen;
											 * actCol.add(activity);
											 * sac.setActivityCollection(actCol);
											 */
											labActivityResult
													.setSubjectAssignment(sa);
											labActivityResult
													.setPatientId(sa
															.getStudySubjectIdentifier());
											labActivityResult.setStudyId(sa
													.getStudySite().getStudy()
													.getIdentifier());
											labActivityResult.setDate(date);
											labActivityResult
													.setActualDate(actualDate);

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

	private static String convertToString(java.util.Date date) {
		if (date == null)
			return "-";
		SimpleDateFormat formatter = new SimpleDateFormat();
		String str = formatter.format(date);
		return str;
	}
}
