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
