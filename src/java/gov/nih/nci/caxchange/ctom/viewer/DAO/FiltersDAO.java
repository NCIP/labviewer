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