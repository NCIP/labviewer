/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */
package gov.nih.nci.caxchange.ctom.viewer.DAO;

import gov.nih.nci.caxchange.ctom.viewer.beans.LabViewerStatus;
import gov.nih.nci.caxchange.ctom.viewer.beans.util.HibernateUtil;
import gov.nih.nci.caxchange.ctom.viewer.constants.DisplayConstants;
import gov.nih.nci.caxchange.ctom.viewer.forms.LabActivitiesSearchForm;
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
import gov.nih.nci.system.applicationservice.ApplicationService;
import gov.nih.nci.system.client.ApplicationServiceProvider;
import gov.nih.nci.system.query.cql.CQLAssociation;
import gov.nih.nci.system.query.cql.CQLAttribute;
import gov.nih.nci.system.query.cql.CQLGroup;
import gov.nih.nci.system.query.cql.CQLLogicalOperator;
import gov.nih.nci.system.query.cql.CQLObject;
import gov.nih.nci.system.query.cql.CQLPredicate;
import gov.nih.nci.system.query.cql.CQLQuery;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * @author asharma
 */
public class LabSearchDAO extends HibernateDaoSupport
{

	private static final Logger logDB = Logger.getLogger(LabSearchDAO.class);

	/**
	 * SearchObjects retrieves the user entered search criteria and returns the
	 * search results
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param errors
	 * @param messages
	 * @return searchResult
	 * @throws Exception
	 */
	public SearchResult searchObjects(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, ActionErrors errors,
			ActionMessages messages) throws Exception
	{
		HashMap map = new HashMap();
		List allLarList = new ArrayList();
		HttpSession session = request.getSession();

		// search form
		LabActivitiesSearchForm lForm = (LabActivitiesSearchForm) form;
		try
		{
			String studyId =
					(String) session.getAttribute("studyId") != null ? (String) session
							.getAttribute("studyId")
							: "";
			String patientId =
					(String) session.getAttribute("patientId") != null ? (String) session
							.getAttribute("patientId")
							: "";

			// Get a connection to the database using the hibernate
			// configuration
			List larlist = null;
			int i = 0;
			List resultList = executeQuery(studyId, patientId);
			for (Iterator resultsIterator = resultList.iterator(); resultsIterator
					.hasNext();)
			{
				SubjectAssignment sa =
						(SubjectAssignment) resultsIterator.next();

				LabActivityResult labActivityResult = null;

				// larlist = printRecord(sa, lForm.getBeginDate(),
				// lForm.getEndDate(),request);
				larlist = printRecord(sa, request);

				for (int j = 0; j < larlist.size(); j++)
				{
					i++;
					labActivityResult = (LabActivityResult) larlist.get(j);
					labActivityResult.setAdverseEventReported(false);
					allLarList.add(labActivityResult);
				}
			}

			if (allLarList != null && allLarList.size() > 0)
			{
				// check if labs were loaded to CDMS or caAERS
				checkForLoadedLabs(allLarList);

				LabActivityResultComparator comp =
						(LabActivityResultComparator) ObjectFactory
								.getObject("labActivityResultComparator");
				Collections.sort(allLarList, comp);
				int k = 0;
				for (int j = 0; j < allLarList.size(); j++)
				{
					k++;
					LabActivityResult labActivityResult = null;
					labActivityResult = (LabActivityResult) allLarList.get(j);
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
	 * executeQuery queries the database with the user entered search criteria.
	 * returns the lab search results
	 * 
	 * @param studyId
	 * @param patientId
	 * @return
	 * @throws Exception
	 */
	public List executeQuery(String studyId, String patientId) throws Exception
	{
		ApplicationService appService =
				ApplicationServiceProvider.getApplicationService();
		// Create the query to get SubjectAssignment object
		CQLQuery query = new CQLQuery();
		CQLObject target = new CQLObject();
		target.setName("gov.nih.nci.labhub.domain.SubjectAssignment");

		// Set the subject Identifier on the association to II
		CQLAssociation subjectAssignmentAssociation1 = new CQLAssociation();
		subjectAssignmentAssociation1.setName("gov.nih.nci.labhub.domain.II");
		subjectAssignmentAssociation1
				.setTargetRoleName("studySubjectIdentifier");
		subjectAssignmentAssociation1.setAttribute(new CQLAttribute(
				"extension", CQLPredicate.EQUAL_TO, patientId.trim()));

		// Now get to StudySite
		CQLAssociation subjectAssignmentAssociation2 = new CQLAssociation();
		subjectAssignmentAssociation2
				.setName("gov.nih.nci.labhub.domain.StudySite");
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
				CQLPredicate.EQUAL_TO, studyId.trim()));
		studySiteAssociation1.setAssociation(studyAssociation1);

		// Now get the SpecimenCollection
		CQLAssociation subjectAssignmentAssociation3 = new CQLAssociation();
		subjectAssignmentAssociation3
				.setName("gov.nih.nci.labhub.domain.SpecimenCollection");
		subjectAssignmentAssociation3.setTargetRoleName("activityCollection");

		// Then put it all together
		CQLGroup finalgroup = new CQLGroup();
		finalgroup.addAssociation(subjectAssignmentAssociation1);
		finalgroup.addAssociation(subjectAssignmentAssociation2);
		finalgroup.addAssociation(subjectAssignmentAssociation3);
		finalgroup.setLogicOperator(CQLLogicalOperator.AND);
		target.setGroup(finalgroup);

		query.setTarget(target);
		List resultList = appService.query(query);
		return resultList;
	}

	/**
	 * printRecord creates the view object that will properly display the
	 * results
	 * 
	 * @param sa
	 * @param beginDate2
	 * @param endDate2
	 * @param request
	 * @return
	 * @throws ParseException
	 */
	private ArrayList printRecord(SubjectAssignment sa,
			HttpServletRequest request) throws ParseException
	{

		ArrayList list = new ArrayList();
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
		String studyGridId = null;

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
			studyGridId = retriveGridId(identifiers);
			request.getSession()
					.setAttribute("studySubjectGridId", studyGridId);

			// Set the study ID
			StudySite site = sa.getStudySite();
			if (site != null)
			{
				Study study = site.getStudy();
				String studyTitle = study.getName();
				request.getSession().setAttribute("studyName", studyTitle);
				identifiers = study.getStudyIdentifier();
				studyId = retrieveIdentifier(identifiers);
				labActivityResult.setStudyId(studyId);
				// labActivityResult.setSiteId(siteId)
				// labActivityResult.setSiteId(site.getHealthCareSite().getIdentifier());
			}

			// get the participant Name
			String patientName = "Participant name";
			if (sa.getParticipant() != null)
			{
				String firstName =
						sa.getParticipant().getFirstName() != null ? sa
								.getParticipant().getFirstName() : "";
				String lastName =
						sa.getParticipant().getLastName() != null ? sa
								.getParticipant().getLastName() : "";
				patientName = firstName + "  " + lastName;
				request.getSession().setAttribute("patientName", patientName);
			}
			// Now set the lab information
			Collection activityCollection = sa.getActivityCollection();
			if (activityCollection != null && activityCollection.size() > 0)
			{
				for (Iterator activityIterator = activityCollection.iterator(); activityIterator
						.hasNext();)
				{
					SpecimenCollection activity =
							(SpecimenCollection) activityIterator.next();

					actualDate = activity.getActualStartDateTime();
					labActivityResult.setActualDate(actualDate);
					Collection specimenCollection =
							activity.getSpecimenCollection();

					if (specimenCollection != null
							&& specimenCollection.size() > 0)
					{
						for (Iterator specimenIterator =
								specimenCollection.iterator(); specimenIterator
								.hasNext();)
						{
							Specimen specimen =
									(Specimen) specimenIterator.next();
							Collection labTestCollection =
									specimen.getLaboratoryTestCollection();

							if (labTestCollection != null
									&& labTestCollection.size() > 0)
							{
								for (Iterator labTestIterator =
										labTestCollection.iterator();

								labTestIterator.hasNext();)
								{
									LaboratoryTest labTest =
											(LaboratoryTest) labTestIterator
													.next();
									if (labTest != null)
									{
										CD labTestIde =
												labTest.getLaboratoryTestId();
										if (labTestIde != null)
										{
											labTestId = labTestIde.getCode();
											labActivityResult
													.setLabTestId(labTestId);
										}
										LaboratoryResult labResult =
												labTest.getLaboratoryResult();

										if (labResult != null)
										{
											numericResult =
													labResult
															.getNumericResult();
											labActivityResult
													.setLabResultId(labResult
															.getId().toString());
											if (numericResult != null)
												labActivityResult
														.setNumericResult(numericResult
																.toString());
											else
												labActivityResult
														.setNumericResult("");

											textResult =
													labResult.getTextResult();
											labActivityResult
													.setTextResult(textResult);

											CD units = labResult.getUnits();
											if (units != null)
											{
												unitOfMeasure = units.getCode();
												labActivityResult
														.setUnitOfMeasure(unitOfMeasure);
											}

											lowRange =
													new Float(
															labResult
																	.getReferenceRangeLow());
											labActivityResult
													.setLowRange(String
															.valueOf(lowRange));
											highRange =
													new Float(
															labResult
																	.getReferenceRangeHigh());

											if ((numericResult != null)
													&& (lowRange != null)
													&& (highRange != null))

											{
												if (numericResult < lowRange
														|| numericResult > highRange)
												{
													labActivityResult
															.setIsAdverseEvent(DisplayConstants.YES);
												}
												else
												{
													labActivityResult
															.setIsAdverseEvent(DisplayConstants.NO);
												}
											}
											else
											{
												labActivityResult
														.setIsAdverseEvent(DisplayConstants.NO);
											}

											labActivityResult
													.setHighRange(String
															.valueOf(highRange));
											labActivityResult
													.setLabResult(labResult);
											list.add(labActivityResult);
											labActivityResult =
													new LabActivityResult();

											// Re set the values
											labActivityResult
													.setSubjectAssignment(sa);
											labActivityResult
													.setPatientId(patientId);
											labActivityResult
													.setStudyId(studyId);
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

	/**
	 * @param identifiers
	 * @return
	 */
	private String retrieveIdentifier(Collection<II> identifiers)
	{
		String identifier = null;

		Iterator<II> idIterator = identifiers.iterator();
		identifier = idIterator.next().getExtension();

		return identifier;
	}

	/**
	 * @param identifiers
	 * @return
	 */
	private String retriveGridId(Collection<II> identifiers)
	{
		String identifier = null;

		Iterator<II> idIterator = identifiers.iterator();
		identifier = idIterator.next().getRoot();

		return identifier;
	}

	/**
	 * Checks if the labs were loaded to CDMS or caAERS and if yes; then sets
	 * their corresponding indicators to true.
	 * 
	 * @param allLarList
	 */
	private void checkForLoadedLabs(List allLarList)
	{
		Session session = null;
		try
		{
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			for (int j = 0; j < allLarList.size(); j++)
			{
				LabActivityResult labActivityResult = null;
				labActivityResult = (LabActivityResult) allLarList.get(j);
				int labResultId =
						Integer.parseInt(labActivityResult.getLabResultId());
				List<LabViewerStatus> result =
						session
								.createQuery(
										"from LabViewerStatus where clinicalResultId=?")
								.setInteger(0, labResultId).list();
				if (result != null)
				{
					for (LabViewerStatus lvs : result)
					{
						if (lvs.isCdmsIndicator().equals("true"))
						{
							labActivityResult.setLabLoadedToCDMS(true);
							labActivityResult.setLabsToCDMSDate(lvs.getCdmsSentDate());
						}
						if (lvs.isAdverseEventIndicator().equals("true"))
						{
							labActivityResult.setAdverseEventReported(true);
							labActivityResult.setLabsToAEDate(lvs.getAdverseEventSentDate());
						}
					}
				}

			}
			session.getTransaction().commit();
		}
		catch (Exception se)
		{
			logDB.error("Error looking up Lab result", se);

		}
	}

}
