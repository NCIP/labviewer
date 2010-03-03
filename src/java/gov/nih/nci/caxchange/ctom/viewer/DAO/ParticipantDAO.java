/**
 * Copyright Notice. Copyright 2008 ScenPro, Inc (�caBIG�
 * Participant�).caXchange was created with NCI funding and is part of the
 * caBIG� initiative. The software subject to this notice and license includes
 * both human readable source code form and machine readable, binary, object
 * code form (the �caBIG� Software�). This caBIG� Software License (the
 * �License�) is between caBIG� Participant and You. �You (or �Your�) shall mean
 * a person or an entity, and all other entities that control, are controlled
 * by, or are under common control with the entity. �Control� for purposes of
 * this definition means (i) the direct or indirect power to cause the direction
 * or management of such entity, whether by contract or otherwise, or (ii)
 * ownership of fifty percent (50%) or more of the outstanding shares, or (iii)
 * beneficial ownership of such entity. License. Provided that You agree to the
 * conditions described below, caBIG� Participant grants You a non-exclusive,
 * worldwide, perpetual, fully-paid-up, no-charge, irrevocable, transferable and
 * royalty-free right and license in its rights in the caBIG� Software,
 * including any copyright or patent rights therein, to (i) use, install,
 * disclose, access, operate, execute, reproduce, copy, modify, translate,
 * market, publicly display, publicly perform, and prepare derivative works of
 * the caBIG� Software in any manner and for any purpose, and to have or permit
 * others to do so; (ii) make, have made, use, practice, sell, and offer for
 * sale, import, and/or otherwise dispose of caBIG� Software (or portions
 * thereof); (iii) distribute and have distributed to and by third parties the
 * caBIG� Software and any modifications and derivative works thereof; and (iv)
 * sublicense the foregoing rights set out in (i), (ii) and (iii) to third
 * parties, including the right to license such rights to further third parties.
 * For sake of clarity, and not by way of limitation, caBIG� Participant shall
 * have no right of accounting or right of payment from You or Your sublicensees
 * for the rights granted under this License. This License is granted at no
 * charge to You. Your downloading, copying, modifying, displaying, distributing
 * or use of caBIG� Software constitutes acceptance of all of the terms and
 * conditions of this Agreement. If you do not agree to such terms and
 * conditions, you have no right to download, copy, modify, display, distribute
 * or use the caBIG� Software. 1. Your redistributions of the source code for
 * the caBIG� Software must retain the above copyright notice, this list of
 * conditions and the disclaimer and limitation of liability of Article 6 below.
 * Your redistributions in object code form must reproduce the above copyright
 * notice, this list of conditions and the disclaimer of Article 6 in the
 * documentation and/or other materials provided with the distribution, if any.
 * 2. Your end-user documentation included with the redistribution, if any, must
 * include the following acknowledgment: �This product includes software
 * developed by ScenPro, Inc.� If You do not include such end-user
 * documentation, You shall include this acknowledgment in the caBIG� Software
 * itself, wherever such third-party acknowledgments normally appear. 3. You may
 * not use the names �ScenPro, Inc�, �The National Cancer Institute�, �NCI�,
 * �Cancer Bioinformatics Grid� or �caBIG�� to endorse or promote products
 * derived from this caBIG� Software. This License does not authorize You to use
 * any trademarks, service marks, trade names, logos or product names of either
 * caBIG� Participant, NCI or caBIG�, except as required to comply with the
 * terms of this License. 4. For sake of clarity, and not by way of limitation,
 * You may incorporate this caBIG� Software into Your proprietary programs and
 * into any third party proprietary programs. However, if You incorporate the
 * caBIG� Software into third party proprietary programs, You agree that You are
 * solely responsible for obtaining any permission from such third parties
 * required to incorporate the caBIG� Software into such third party proprietary
 * programs and for informing Your sublicensees, including without limitation
 * Your end-users, of their obligation to secure any required permissions from
 * such third parties before incorporating the caBIG� Software into such third
 * party proprietary software programs. In the event that You fail to obtain
 * such permissions, You agree to indemnify caBIG� Participant for any claims
 * against caBIG� Participant by such third parties, except to the extent
 * prohibited by law, resulting from Your failure to obtain such permissions. 5.
 * For sake of clarity, and not by way of limitation, You may add Your own
 * copyright statement to Your modifications and to the derivative works, and
 * You may provide additional or different license terms and conditions in Your
 * sublicenses of modifications of the caBIG� Software, or any derivative works
 * of the caBIG� Software as a whole, provided Your use, reproduction, and
 * distribution of the Work otherwise complies with the conditions stated in
 * this License. 6. THIS caBIG� SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED
 * OR IMPLIED WARRANTIES (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE)
 * ARE DISCLAIMED. IN NO EVENT SHALL THE ScenPro, Inc OR ITS AFFILIATES BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS caBIG� SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
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
