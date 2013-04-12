/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */
package gov.nih.nci.caxchange.ctom.viewer.DAO;

import gov.nih.nci.caxchange.ctom.viewer.viewobjects.ParticipantSearchResult;
import gov.nih.nci.labhub.domain.Participant;
import gov.nih.nci.labhub.domain.SubjectAssignment;
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
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

/**
 * @author Lisa Kelley
 */
public class ParticipantDAO
{
	private static final Logger log = Logger.getLogger(ParticipantDAO.class);
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public List<ParticipantSearchResult> getSearchResults(String studyId) throws Exception
	{
		List<String> searchTerms = new ArrayList<String>();
	    searchTerms.add("");
	        	
		return getSearchResults(studyId, searchTerms);
	}

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
	public List<ParticipantSearchResult> getSearchResults(String studyId, List<String> searchTerms) throws Exception
	{
		CQLQuery query = new CQLQuery();
		CQLObject target = new CQLObject();
		target.setName("gov.nih.nci.labhub.domain.SubjectAssignment");
		CQLGroup group = new CQLGroup();

		if (searchTerms != null)
		{
		    // Set the search terms conditions
		    CQLGroup participantGroup = new CQLGroup();
		
		    for (String searchTerm : searchTerms)
		    {
			    participantGroup.addAttribute(new CQLAttribute("firstName", CQLPredicate.LIKE, "%" + searchTerm + "%"));
			    participantGroup.addAttribute(new CQLAttribute("lastName", CQLPredicate.LIKE, "%" + searchTerm + "%"));
		    
		        CQLAssociation association = new CQLAssociation();
		        association.setName("gov.nih.nci.labhub.domain.II");
		        association.setTargetRoleName("participantIdentifier");
		        association.setAttribute(new CQLAttribute("extension", CQLPredicate.LIKE, "%" + searchTerm + "%"));
		        participantGroup.addAssociation(association);
		    }

		    participantGroup.setLogicOperator(CQLLogicalOperator.OR);

		    CQLAssociation participantAssociation = new CQLAssociation();
		    participantAssociation.setName("gov.nih.nci.labhub.domain.Participant");
		    participantAssociation.setTargetRoleName("participant");
		    participantAssociation.setGroup(participantGroup);
		
		    group.addAssociation(participantAssociation);
		}

		if (studyId != null)
		{			
			CQLAssociation iiAssociation = new CQLAssociation();
		    iiAssociation.setName("gov.nih.nci.labhub.domain.II");
		    iiAssociation.setTargetRoleName("studyIdentifier");
		    iiAssociation.setAttribute(new CQLAttribute("extension", CQLPredicate.LIKE, "%" + studyId + "%"));		    

		    CQLAssociation studyAssociation = new CQLAssociation();
		    studyAssociation.setName("gov.nih.nci.labhub.domain.Study");
		    studyAssociation.setTargetRoleName("study");
		    studyAssociation.setAssociation(iiAssociation);
		    
		    CQLAssociation studySiteAssociation = new CQLAssociation();
		    studySiteAssociation.setName("gov.nih.nci.labhub.domain.StudySite");
		    studySiteAssociation.setTargetRoleName("studySite");
		    studySiteAssociation.setAssociation(studyAssociation);

		    group.addAssociation(studySiteAssociation);
		}
		
		if (searchTerms != null && studyId != null)
		{
		    group.setLogicOperator(CQLLogicalOperator.AND);
		}
		
		if (searchTerms != null || studyId != null)
		{
		    target.setGroup(group);
		}
		
		query.setTarget(target);
		
		List subjectAssignments = null;
		try
		{
			subjectAssignments = ApplicationServiceProvider.getApplicationService().query(query);
		}
		catch (Exception e)
		{
			log.error("getSearchResults: Exception occurred: ", e);
			throw e;
		}
		
		log.debug("getSearchResults size = " + subjectAssignments.size());
		return convertParticipants(subjectAssignments);
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
	private List<ParticipantSearchResult> convertParticipants(List<SubjectAssignment> subjectAssignments)
	{
		List<ParticipantSearchResult> participantSearchResults = new ArrayList<ParticipantSearchResult>();
		
		if (subjectAssignments.size() <= 0)
		{
			return participantSearchResults;
		}
		
		for (SubjectAssignment subjectAssignment : subjectAssignments)
		{
		    ParticipantSearchResult participantSearchResult = new ParticipantSearchResult();
			
			Participant participant = subjectAssignment.getParticipant();
			participantSearchResult.setPatientId(participant.getParticipantIdentifier().iterator().next().getExtension());
			participantSearchResult.setFirstName(participant.getFirstName());
			participantSearchResult.setLastName(participant.getLastName());
			
			participantSearchResult.setStudyId(subjectAssignment.getStudySite().getStudy().getStudyIdentifier().iterator().next().getExtension());
			
// lisa - anu - what is this?
//				String assignedBy = participantIdentifier.getAssigningAuthorityName() != null ? participantIdentifier.getAssigningAuthorityName() : "";
//				String type = subjectAssignment.getType() != null ? subjectAssignment.getType() : "";
//				if (assignedBy.length() > 1 && type.length() > 1)
//				{
//					String orgNciId = getNCIId(participant.getId());
//					// TO DO: set the details link
//					String c3prURL = (String) session.getAttribute("BaseURLC3PR");
//					String details = c3prURL
//									+ "/pages/participant/viewParticipant?assignedBy="
//									+ assignedBy + "&organizationNcid="
//									+ orgNciId + "&identifierType=" + type
//									+ "&&identifier="
//									+ participantIdentifier.getExtension();
//					participantSearchResult.setDetails(details);
//				}
				
			participantSearchResults.add(participantSearchResult);
		}

		return participantSearchResults;
	}

//	/**
//	 * getNCIId queries the database to retrieve the NCI assigned HealthCareSite
//	 * Id.
//	 * 
//	 * @param participantId
//	 * @return String
//	 */
//	private String getNCIId(Integer participantId)
//	{
//		String nciId = "NCI";// to do
//		Session session = null;
//		try
//		{
//			session = HibernateUtil.getSessionFactory().getCurrentSession();
//			session.beginTransaction();
//			int studyId = participantId.intValue();
//			List<HealthcareSitePart> result =
//					session
//							.createQuery(
//									"from HealthcareSitePart hcsp where hcsp.participantId =?")
//							.setLong(0, participantId).list();
//			if (result != null)
//			{
//				for (HealthcareSitePart hcsp : result)
//				{
//					Query query =
//							session
//									.createQuery("from HealthCareSite hcs where hcs.id =?");
//					Integer hcsId = hcsp.getHealthcareSiteId();
//					query.setLong(0, hcsId);
//					List<HealthCareSite> hcsresult = query.list();
//					if (hcsresult != null && hcsresult.size() > 0)
//						nciId = hcsresult.get(0).getName();
//					break;
//				}
//			}
//			session.getTransaction().commit();
//
//		}
//		catch (Exception se)
//		{
//			log.error("Error looking up Lab result", se);
//		}
//		return nciId;
//	}

}
