/**
 * Copyright Notice.  Copyright 2008  ScenPro, Inc (“caBIG™ Participant”).caXchange
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
 * 		“This product includes software developed by ScenPro, Inc.”  
 * 		If You do not include such end-user documentation, You shall include this acknowledgment in the caBIG™ Software itself, 
 * 		wherever such third-party acknowledgments normally appear.
 * 3.	You may not use the names  “ScenPro, Inc”, 
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
 * 		IN NO EVENT SHALL THE ScenPro, Inc OR ITS AFFILIATES 
 * 		BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * 		PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON 
 * 		ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY 
 * 		OUT OF THE USE OF THIS caBIG™ SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * 
 */

package gov.nih.nci.caxchange.ctom.viewer.DAO;

import gov.nih.nci.caxchange.ctom.viewer.beans.ProtocolStatus;
import gov.nih.nci.caxchange.ctom.viewer.beans.util.HibernateUtil;
import gov.nih.nci.caxchange.ctom.viewer.forms.StudySearchForm;
import gov.nih.nci.caxchange.ctom.viewer.viewobjects.SearchResult;
import gov.nih.nci.caxchange.ctom.viewer.viewobjects.StudySearchResult;
import gov.nih.nci.labhub.domain.II;
import gov.nih.nci.labhub.domain.Study;
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

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * @author asharma
 * 
 */
public class StudySearchDAO extends HibernateDaoSupport {

	private static final Logger logDB = Logger.getLogger(StudySearchDAO.class);

	/**
	 * SearchObjects retrieves the user entered search criteria and
	 * returns the study search results
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
			ActionMessages messages) throws Exception {
		List<StudySearchResult> allLarList = new ArrayList<StudySearchResult>();
		logDB.debug("Study search called");
		// search form
		StudySearchForm sForm = (StudySearchForm) form;
		try {
			List resultList = executeQuery(sForm.getStudyPhrase());
			allLarList = printRecord(resultList, request);
		} catch (Exception ex) {
			logDB.error(ex.getMessage());
		}
		logDB.debug("Study search called with search term" +sForm.getStudyPhrase());
		SearchResult searchResult = new SearchResult();
		searchResult.setSearchResultObjects(allLarList);
		request.getSession().setAttribute("SEARCH_RESULT_STUDY", searchResult);

		return searchResult;
	}

	/** executeQuery queries the database with the user entered search criteria.
	 * returns the study search results
	 * @param studyPhrase
	 * @return
	 * @throws Exception
	 */
	public List executeQuery(String studyPhrase) throws Exception {
		ApplicationService appService = ApplicationServiceProvider
				.getApplicationService();
		logDB.debug("Study search query setup");
		String searchTerm = "%" + studyPhrase.trim() + "%";
		// Create the query to get Study object
		CQLQuery query = new CQLQuery();
		CQLObject target = new CQLObject();
		target.setName("gov.nih.nci.labhub.domain.Study");

		// Now set the study identifier on the association to II
		CQLAssociation iiAssociation = new CQLAssociation();
		iiAssociation.setName("gov.nih.nci.labhub.domain.II");
		iiAssociation.setTargetRoleName("studyIdentifier");
		iiAssociation.setAttribute(new CQLAttribute("extension",
				CQLPredicate.EQUAL_TO, studyPhrase.trim()));

		// set the or condition
		CQLGroup group = new CQLGroup();
		group.addAttribute(new CQLAttribute("name", CQLPredicate.LIKE,
				searchTerm));
		group.addAssociation(iiAssociation);
		group.setLogicOperator(CQLLogicalOperator.OR);
		target.setGroup(group);

		query.setTarget(target);
		// Query the database
		 logDB.debug("Study search query execute");
		List resultList = appService.query(query);
		logDB.debug("Study search query result size"+resultList.size()); 
		return resultList;
	}

	/**
	 * printRecord creates the view object that will properly display the
	 * results
	 * @param resultList
	 * @param request
	 * @return
	 * @throws ParseException
	 */
	private ArrayList<StudySearchResult> printRecord(List resultList,
			HttpServletRequest request) throws ParseException 
	{
		logDB.debug("Study search printRecord method called");
		ArrayList<StudySearchResult> list = new ArrayList<StudySearchResult>();
		String studyTitle = null;
		String studyGridId = null;

		if (resultList == null)
			return null;

		if (resultList != null) {
			for (Iterator resultsIterator = resultList.iterator(); resultsIterator
					.hasNext();) {
				Study study = (Study) resultsIterator.next();

				StudySearchResult studySearchResult = new StudySearchResult();
				studySearchResult.setId(study.getId());
				// retrieve the short title
				studySearchResult.setShortTitle(study.getShortTitle());
				// retrieve the extension and root for the study
				Collection<II> identifiers = study.getStudyIdentifier();
				II identifier = retrieveIdentifier(identifiers);
				studySearchResult.setStudyId(identifier.getExtension());
				studyGridId = identifier.getRoot();
				studySearchResult.setGridId(studyGridId);
				request.getSession().setAttribute("studySubjectGridId",
						studyGridId);
				// retrieve the phase code
				studySearchResult
						.setPhaseCode(study.getPhaseCode() != null ? study
								.getPhaseCode() : "");
				// retrieve the sponsor code
				String sponsorCd = "";
				if (study.getSponsorCode() != null
						&& !study.getSponsorCode().equals("null"))
					sponsorCd = study.getSponsorCode();
				studySearchResult.setSponsorCode(sponsorCd);
				// TO DO: set the details
				// link:https://cbvapp-d1017.nci.nih.gov:28443/c3pr/pages/study/viewStudy?studyId=14
				String details = "";
				studySearchResult.setDetails(details);
				list.add(studySearchResult);
			}
		}
		// updates the list with the protocol status
		checkProtocolStatus(list);
		return list;
	}

	/**
	 * @param identifiers
	 * @return
	 */
	private II retrieveIdentifier(Collection<II> identifiers) {
		// String identifier = null;

		Iterator<II> idIterator = identifiers.iterator();
		II identifier = idIterator.next();

		return identifier;
	}

	/**
	 * Checks and sets the Protocol/Study Status
	 * @param studySearchResultList
	 */
	private void checkProtocolStatus(List studySearchResultList) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			for (int j = 0; j < studySearchResultList.size(); j++) {
				StudySearchResult studyResult = null;
				studyResult = (StudySearchResult) studySearchResultList.get(j);
				int studyId = studyResult.getId().intValue();
				List<ProtocolStatus> result = session
						.createQuery(
								"from ProtocolStatus where protocol_Id=? order by ctom_insert_date desc")
						.setLong(0, studyId).list();
				if (result != null) {
					for (ProtocolStatus lvs : result) {
						studyResult.setStatus(lvs.getStatus_code());
						break;
					}
				}

			}
			session.getTransaction().commit();
		} catch (Exception se) {
			logDB.error("Error looking up Lab result", se);

		}
	}

}
