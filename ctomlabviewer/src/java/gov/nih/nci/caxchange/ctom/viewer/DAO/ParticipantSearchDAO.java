/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */
package gov.nih.nci.caxchange.ctom.viewer.DAO;

import gov.nih.nci.cabig.ctms.suite.authorization.SuiteRole;
import gov.nih.nci.cabig.ctms.suite.authorization.SuiteRoleMembership;
import gov.nih.nci.caxchange.ctom.viewer.beans.HealthcareSitePart;
import gov.nih.nci.caxchange.ctom.viewer.beans.util.HibernateUtil;
import gov.nih.nci.caxchange.ctom.viewer.forms.ParticipantSearchForm;
import gov.nih.nci.caxchange.ctom.viewer.util.CommonUtil;
import gov.nih.nci.caxchange.ctom.viewer.util.LabViewerAuthorizationHelper;
import gov.nih.nci.caxchange.ctom.viewer.viewobjects.ParticipantSearchResult;
import gov.nih.nci.caxchange.ctom.viewer.viewobjects.SearchResult;
import gov.nih.nci.labhub.domain.HealthCareSite;
import gov.nih.nci.labhub.domain.II;
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
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * @author asharma
 */
public class ParticipantSearchDAO extends HibernateDaoSupport
{

	private static final Logger logDB =
			Logger.getLogger(ParticipantSearchDAO.class);

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
		logDB.debug("Participant search called");
		HashMap map = new HashMap();
		HttpSession session = request.getSession();
		List<ParticipantSearchResult> allLarList =
				new ArrayList<ParticipantSearchResult>();

		// search form
		ParticipantSearchForm pForm = (ParticipantSearchForm) form;
		SearchResult searchResult = new SearchResult();
		String searchTerm = "%" + pForm.getParticipantPhrase().trim() + "%";
		String studyId =
				(String) session.getAttribute("studyId") != null ? (String) session
						.getAttribute("studyId")
						: "";
		logDB.debug("Participant search called with search term" + searchTerm
				+ "or" + pForm.getParticipantPhrase());
		try
		{
			List resultList =
					executeQuery(searchTerm, studyId, pForm
							.getParticipantPhrase(), session);
			allLarList = printRecord(resultList, request);
		}
		catch (Exception ex)
		{
			logDB.error(ex.getMessage());
		}
		searchResult.setSearchResultObjects(allLarList);
		request.getSession().setAttribute("SEARCH_RESULT_PART", searchResult);
		return searchResult;
	}

	/**
	 * executeQuery queries the database with the user entered search criteria.
	 * returns the participant search results
	 * 
	 * @param searchTerm
	 * @param studyId
	 * @param phrase
	 * @return List of participants
	 * @throws Exception
	 */
	public List executeQuery(String searchTerm, String studyId, String phrase, HttpSession session)
			throws Exception
	{
		ApplicationService appService =
				ApplicationServiceProvider.getApplicationService();
		logDB.debug("Participant search query setup");
		// Create the query to get Study object
		CQLQuery query = new CQLQuery();
		CQLObject target = new CQLObject();
		target.setName("gov.nih.nci.labhub.domain.SubjectAssignment");

		// Set the subject Identifier on the association to II
		CQLAssociation subjectAssignmentAssociation1 = new CQLAssociation();
		subjectAssignmentAssociation1.setName("gov.nih.nci.labhub.domain.II");
		subjectAssignmentAssociation1
				.setTargetRoleName("participantIdentifier");
		subjectAssignmentAssociation1.setAttribute(new CQLAttribute(
				"extension", CQLPredicate.LIKE, searchTerm));

		// Set the search term conditions
		CQLGroup group = new CQLGroup();
		group.addAttribute(new CQLAttribute("firstName", CQLPredicate.LIKE,
				searchTerm));
		group.addAttribute(new CQLAttribute("lastName", CQLPredicate.LIKE,
				searchTerm));
		group.addAssociation(subjectAssignmentAssociation1);

		group.setLogicOperator(CQLLogicalOperator.OR);

		// get the participant
		CQLAssociation participantAssociation = new CQLAssociation();
		participantAssociation.setName("gov.nih.nci.labhub.domain.Participant");
		participantAssociation.setTargetRoleName("participant");
		participantAssociation.setGroup(group);
		
		// Now set the study identifier on the association to II
		CQLAssociation iiAssociation = new CQLAssociation();
		iiAssociation.setName("gov.nih.nci.labhub.domain.II");
		iiAssociation.setTargetRoleName("studyIdentifier");
		iiAssociation.setAttribute(new CQLAttribute("extension", CQLPredicate.EQUAL_TO, studyId.trim()));
		
		// Now get to Study
		CQLAssociation studyAssociation = new CQLAssociation();
		studyAssociation.setName("gov.nih.nci.labhub.domain.Study");
		studyAssociation.setTargetRoleName("study");
		studyAssociation.setAssociation(iiAssociation);
		
		// Now get to StudySite
		CQLAssociation studySiteAssociation = new CQLAssociation();
		studySiteAssociation.setName("gov.nih.nci.labhub.domain.StudySite");
		studySiteAssociation.setTargetRoleName("studySite");
		
		CommonUtil util = new CommonUtil();		
		String username = util.checkUserLogin(session);
		LabViewerAuthorizationHelper authHelper = new LabViewerAuthorizationHelper();
		SuiteRoleMembership roleMembership = authHelper.getUserRoleMembership(username, SuiteRole.LAB_DATA_USER);
		
		// if the user has permission to access specific sites (not all sites), then filter based upon these specific sites
		if (roleMembership.isAllSites())
		{
			studySiteAssociation.setAssociation(studyAssociation);
		}
		else
		{
			CQLGroup studySiteGroup = new CQLGroup();
			studySiteGroup.addAssociation(studyAssociation);
			CQLGroup healthcareSiteGroup = new CQLGroup();
			
			for (String siteId : roleMembership.getSiteIdentifiers())
			{
				CQLAssociation healthcareSiteAssociation = new CQLAssociation();
				healthcareSiteAssociation.setName("gov.nih.nci.labhub.domain.HealthCareSite"); 
				healthcareSiteAssociation.setTargetRoleName("healthCareSite");
				healthcareSiteAssociation.setAttribute(new CQLAttribute("nci_institute_code", CQLPredicate.EQUAL_TO, siteId));
				healthcareSiteGroup.addAssociation(healthcareSiteAssociation);
			}
			
			healthcareSiteGroup.setLogicOperator(CQLLogicalOperator.OR);
			studySiteGroup.addGroup(healthcareSiteGroup);
			studySiteGroup.setLogicOperator(CQLLogicalOperator.AND);
			studySiteAssociation.setGroup(studySiteGroup);
	    }

		CQLGroup finalgroup = new CQLGroup();
		finalgroup.addAssociation(participantAssociation);
		finalgroup.addAssociation(studySiteAssociation);
		finalgroup.setLogicOperator(CQLLogicalOperator.AND);
		target.setGroup(finalgroup);

		query.setTarget(target);
		// Query the database
		logDB.debug("Participant search query execute");
		List resultList = appService.query(query);
		logDB.debug("Participant search query result size" + resultList.size());
		return resultList;
	}

	/**
	 * printRecord creates the view object that will properly display the
	 * results of participant search.
	 * 
	 * @param resultList
	 * @param request
	 * @return
	 * @throws ParseException
	 */
	private ArrayList<ParticipantSearchResult> printRecord(List resultList,
			HttpServletRequest request) throws ParseException
	{
		logDB.debug("Participant search printRecord method called");
		ArrayList<ParticipantSearchResult> list =
				new ArrayList<ParticipantSearchResult>();
		String participantGridId = null;
		String studyId = "";

		if (resultList == null)
			return null;

		if (resultList != null)
		{
			for (Iterator resultsIterator = resultList.iterator(); resultsIterator
					.hasNext();)
			{
				SubjectAssignment participant =
						(SubjectAssignment) resultsIterator.next();
				ParticipantSearchResult participantSearchResult =
						new ParticipantSearchResult();
				// retrieve the study Id
				Collection<II> partidentifiers =
						participant.getStudySite().getStudy()
								.getStudyIdentifier();
				II partidentifier = retrieveIdentifier(partidentifiers);
				participantSearchResult.setStudyId(partidentifier
						.getExtension());
				studyId = partidentifier.getRoot();
				request.getSession().setAttribute("studyGridId", studyId);
				// retrieve the first name
				participantSearchResult.setFirstName(participant
						.getParticipant().getFirstName());
				// retrieve the last name
				participantSearchResult.setLastName(participant
						.getParticipant().getLastName());
				// retrieve the extension and root for the participant
				Collection<II> identifiers =
						participant.getParticipant().getParticipantIdentifier();
				II identifier = retrieveIdentifier(identifiers);
				participantSearchResult.setPatientId(identifier.getExtension());
				participantGridId = identifier.getRoot();
				participantSearchResult.setGridId(participantGridId);
				request.getSession().setAttribute("participantGridId",
						participantGridId);
				String assignedBy =
						identifier.getAssigningAuthorityName() != null ? identifier
								.getAssigningAuthorityName()
								: "";
				String type =
						participant.getType() != null ? participant.getType()
								: "";
				if (assignedBy.length() > 1 && type.length() > 1)
				{
					String orgNciId =
							getNCIId(participant.getParticipant().getId());
					// TO DO: set the details link
					String c3prURL =
							(String) request.getSession().getAttribute(
									"BaseURLC3PR");
					String details =
							c3prURL
									+ "/pages/participant/viewParticipant?assignedBy="
									+ assignedBy + "&organizationNcid="
									+ orgNciId + "&identifierType=" + type
									+ "&&identifier="
									+ identifier.getExtension();
					participantSearchResult.setDetails(details);
				}
				list.add(participantSearchResult);
			}
		}

		return list;
	}

	/**
	 * getNCIId queries the database to retrieve the NCI assigned HealthCareSite
	 * Id.
	 * 
	 * @param participantId
	 * @return String
	 */
	private String getNCIId(Integer participantId)
	{
		String nciId = "NCI";// to do
		Session session = null;
		try
		{
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			int studyId = participantId.intValue();
			List<HealthcareSitePart> result =
					session
							.createQuery(
									"from HealthcareSitePart hcsp where hcsp.participantId =?")
							.setLong(0, participantId).list();
			if (result != null)
			{
				for (HealthcareSitePart hcsp : result)
				{
					Query query =
							session
									.createQuery("from HealthCareSite hcs where hcs.id =?");
					Integer hcsId = hcsp.getHealthcareSiteId();
					query.setLong(0, hcsId);
					List<HealthCareSite> hcsresult = query.list();
					if (hcsresult != null && hcsresult.size() > 0)
						nciId = hcsresult.get(0).getName();
					break;
				}
			}
			session.getTransaction().commit();

		}
		catch (Exception se)
		{
			logDB.error("Error looking up Lab result", se);
		}
		return nciId;
	}

	/**
	 * @param identifiers
	 * @return
	 */
	private II retrieveIdentifier(Collection<II> identifiers)
	{
		// String identifier = null;

		Iterator<II> idIterator = identifiers.iterator();
		II identifier = idIterator.next();

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

}
