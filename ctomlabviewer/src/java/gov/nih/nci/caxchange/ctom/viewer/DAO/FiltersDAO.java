/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */
package gov.nih.nci.caxchange.ctom.viewer.DAO;

import gov.nih.nci.labhub.domain.CD;
import gov.nih.nci.labhub.domain.HealthCareSite;
import gov.nih.nci.labhub.domain.LaboratoryTest;
import gov.nih.nci.labhub.domain.Specimen;
import gov.nih.nci.labhub.domain.SpecimenCollection;
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
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * @author asharma
 */
public class FiltersDAO extends HibernateDaoSupport
{

	private static final Logger logDB = Logger.getLogger(FiltersDAO.class);
	private static final String CONFIG_FILE = "/baseURL.properties";

	/**
	 * Retrieves the Site Filters
	 * 
	 * @param session
	 * @return
	 * @throws Exception
	 */
	public List getSiteFilterList(HttpSession session) throws Exception
	{

		List siteList = new ArrayList();
		siteList.add("All");
		try
		{
			String patientId =
					(String) session.getAttribute("patientId") != null ? (String) session
							.getAttribute("patientId")
							: "";
			String gridId =
					(String) session.getAttribute("participantGridId") != null ? (String) session
							.getAttribute("participantGridId")
							: "";
			executeSiteFilterQuery(patientId, gridId, siteList);
		}
		catch (Exception ex)
		{
			logDB.error(ex.getMessage());
		}

		return siteList;
	}

	/**
	 * executeSiteFilterQuery method queries the database to retrieve the sites.
	 * 
	 * @param patientId
	 * @param gridId
	 * @param siteList
	 * @throws Exception
	 */
	public void executeSiteFilterQuery(String patientId, String gridId,
			List siteList) throws Exception
	{
		ApplicationService appService =
				ApplicationServiceProvider.getApplicationService();
		// Create the query to get Study object
		CQLQuery query = new CQLQuery();
		CQLObject target = new CQLObject();
		target.setName("gov.nih.nci.labhub.domain.HealthCareSite");

		// Now get to StudySite
		CQLAssociation studySiteAssociation1 = new CQLAssociation();
		studySiteAssociation1.setName("gov.nih.nci.labhub.domain.StudySite");
		studySiteAssociation1.setTargetRoleName("studySiteCollection");

		// Now get to Study
		CQLAssociation subjectAssignmentAssociation2 = new CQLAssociation();
		subjectAssignmentAssociation2
				.setName("gov.nih.nci.labhub.domain.SubjectAssignment");
		subjectAssignmentAssociation2
				.setTargetRoleName("subjectAssignmentCollection");

		CQLGroup group = new CQLGroup();
		group.addAttribute(new CQLAttribute("extension", CQLPredicate.EQUAL_TO,
				patientId.trim()));
		group.addAttribute(new CQLAttribute("root", CQLPredicate.NOT_EQUAL_TO,
				gridId.trim()));
		group.setLogicOperator(CQLLogicalOperator.AND);

		// Now set the study identifier on the association to II
		CQLAssociation iiAssociation = new CQLAssociation();
		iiAssociation.setName("gov.nih.nci.labhub.domain.II");
		iiAssociation.setTargetRoleName("studySubjectIdentifier");
		iiAssociation.setGroup(group);

		subjectAssignmentAssociation2.setAssociation(iiAssociation);

		studySiteAssociation1.setAssociation(subjectAssignmentAssociation2);

		// Then put it all together
		target.setAssociation(studySiteAssociation1);

		query.setTarget(target);
		List resultList = appService.query(query);

		// Get a connection to the database using the hibernate configuration
		int i = 0;
		for (Iterator resultsIterator = resultList.iterator(); resultsIterator
				.hasNext();)
		{
			HealthCareSite hcs = (HealthCareSite) resultsIterator.next();
			siteList.addAll(printRecordHealthCareSite(hcs));

		}

	}

	/**
	 * printRecordHealthCareSite creates the view object that will properly
	 * display the results for the site filter.
	 * 
	 * @param hcs
	 * @return
	 * @throws ParseException
	 */
	private ArrayList printRecordHealthCareSite(HealthCareSite hcs)
			throws ParseException
	{
		ArrayList list = new ArrayList();

		if (hcs == null)
			return null;

		if (hcs != null)
		{

			// Set the study ID
			Collection siteCollection = hcs.getStudySiteCollection();

			if (siteCollection != null && siteCollection.size() > 0)
			{
				for (Iterator ssIterator = siteCollection.iterator(); ssIterator
						.hasNext();)
				{
					StudySite ss = (StudySite) ssIterator.next();
					list.add(ss.getHealthCareSite().getName());

				}
			}
		}
		return list;
	}

	/**
	 * Retrieves the Lab Test Filters
	 * 
	 * @param session
	 * @return
	 * @throws Exception
	 */
	public List getLabTestFilterList(HttpSession session) throws Exception
	{

		List labTestList = new ArrayList();
		labTestList.add("All");
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
			executeLabTestFilter(studyId, patientId, labTestList);
		}
		catch (Exception ex)
		{
			logDB.error(ex.getMessage());
		}

		return labTestList;
	}

	/**
	 * executeLabTestFilter method queries the database to retrieve the lab
	 * tests.
	 * 
	 * @param studyId
	 * @param patientId
	 * @param labTestList
	 * @return
	 * @throws Exception
	 */
	public List executeLabTestFilter(String studyId, String patientId,
			List labTestList) throws Exception
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

		// Get a connection to the database using the hibernate
		// configuration
		int i = 0;
		for (Iterator resultsIterator = resultList.iterator(); resultsIterator
				.hasNext();)
		{
			SubjectAssignment sa = (SubjectAssignment) resultsIterator.next();
			labTestList.addAll(printRecord(sa));

		}
		return labTestList;
	}

	/**
	 * printRecord creates the view object that will properly display the
	 * results for the Lab test filter
	 * 
	 * @param sa
	 * @param beginDate2
	 * @param endDate2
	 * @param request
	 * @return
	 * @throws ParseException
	 */
	private ArrayList printRecord(SubjectAssignment sa) throws ParseException
	{
		ArrayList list = new ArrayList();

		SortedSet labSet = new TreeSet();
		if (sa == null)
			return null;

		if (sa != null)
		{

			// Now set the lab information
			Collection activityCollection = sa.getActivityCollection();
			if (activityCollection != null && activityCollection.size() > 0)
			{
				for (Iterator activityIterator = activityCollection.iterator(); activityIterator
						.hasNext();)
				{
					SpecimenCollection activity =
							(SpecimenCollection) activityIterator.next();

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
										labTestCollection.iterator(); labTestIterator
										.hasNext();)
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
											String labTestId =
													labTestIde.getCode();
											labSet.add(labTestId);
										}
									}
								}
							}
						}
					}
				}
			}

		}
		if (labSet != null)
			list.addAll(labSet);

		return list;
	}

}
