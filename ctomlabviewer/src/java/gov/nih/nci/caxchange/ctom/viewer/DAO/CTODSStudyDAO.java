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

import gov.nih.nci.caxchange.ctom.viewer.beans.util.HibernateUtil;
import gov.nih.nci.caxchange.ctom.viewer.viewobjects.StudySearchResult;
import gov.nih.nci.labhub.domain.Study;
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
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * @author Lisa Kelley
 */
public class CTODSStudyDAO extends HibernateDaoSupport
{
	private static final Logger log = Logger.getLogger(CTODSStudyDAO.class);
	private static final String EMPTY_STRING = "";
	private static final String NBSP = "&nbsp;";

	/**
	 * getCTODSSearchResults queries the database with the user entered search criteria.
	 * returns the study search results
	 *
	 * @param studyPhrase
	 * @return
	 * @throws Exception
	 */
	public List<StudySearchResult> getCTODSSearchResults(String studyID, List<String> studyTitleTerms, HttpSession session) throws Exception
	{
		CQLQuery query = new CQLQuery();
		CQLObject target = new CQLObject();
		target.setName("gov.nih.nci.labhub.domain.Study"); // Study correlates to protocol table
		CQLGroup group = new CQLGroup();

		for (String studyTitleTerm : studyTitleTerms)
		{
		    // long title is not available in Study class in ctomlabapi-beans.jar - need to add to ORM
			//group.addAttribute(new CQLAttribute("name", CQLPredicate.LIKE, "%" + searchTerm + "%")); // name correlates to long_title_text
			group.addAttribute(new CQLAttribute("shortTitle", CQLPredicate.LIKE, "%" + studyTitleTerm + "%"));
		}

		if (StringUtils.isNotBlank(studyID))
		{
		    CQLAssociation association = new CQLAssociation();
		    association.setName("gov.nih.nci.labhub.domain.II");
		    association.setTargetRoleName("studyIdentifier"); // studyIdentifier correlates to nci_identifier
		    association.setAttribute(new CQLAttribute("extension", CQLPredicate.LIKE, "%" + studyID + "%"));
		    group.addAssociation(association);
		}

		group.setLogicOperator(CQLLogicalOperator.AND);
		target.setGroup(group);
		query.setTarget(target);

		List studies = null;
		try
		{
		    studies = ApplicationServiceProvider.getApplicationService().query(query);
		}
		catch (Exception e)
		{
			log.error("getCTODSSearchResults: Exception occurred: ", e);
			throw e;
		}

		log.debug("getCTODSSearchResults size = " + studies.size());
		return convertCTODSStudies(studies, session);
	}

	/**
	 * printRecord creates the view object that will properly display the
	 * results
	 *
	 * @param resultList
	 * @param request
	 * @return
	 * @throws ParseException
	 */
	private List<StudySearchResult> convertCTODSStudies(List<Study> studies, HttpSession session) throws HibernateException
	{
		List<StudySearchResult> ctodsSearchResults = new ArrayList<StudySearchResult>();

		if (studies.size() <= 0)
		{
			return ctodsSearchResults;
		}

		for (Study study : studies)
		{
	        StudySearchResult ctodsStudy = new StudySearchResult();
	        ctodsStudy.setId(study.getId());

	        gov.nih.nci.labhub.domain.II identifier = study.getStudyIdentifier().iterator().next();
			ctodsStudy.setStudyId(identifier.getExtension() == null ? NBSP : identifier.getExtension());

			// long title is not available in Study class in ctomlabapi-beans.jar - need to add to ORM
			ctodsStudy.setLongTitle(study.getShortTitle() == null || study.getShortTitle().equals("null") ? NBSP : study.getShortTitle()); // lisa - figure out how the string "null" is getting stored in the database!
			ctodsStudy.setSponsorCode(study.getSponsorCode() == null || study.getSponsorCode().equals("null") ? NBSP : study.getSponsorCode()); // lisa - figure out how the string "null" is getting stored in the database!
			ctodsStudy.setPhaseCode(study.getPhaseCode() == null ? NBSP : study.getPhaseCode());

			String gridId = identifier.getRoot();
			ctodsStudy.setGridId(gridId == null ? EMPTY_STRING : gridId);

			// TO DO: set the details
			// link: https://cbvapp-d1017.nci.nih.gov:28443/c3pr/pages/study/viewStudy?studyId=14
			ctodsStudy.setDetails(EMPTY_STRING); // lisa - anu - what is this?

			ctodsSearchResults.add(ctodsStudy);
		}

		return setStatus(ctodsSearchResults);
	}

	private static final String QUERY = "FROM ProtocolStatus ps " +
                                       "WHERE ps.protocol_id = ? " +
                                         "AND ps.ctom_insert_date = (SELECT MAX(ctom_insert_date) FROM ProtocolStatus WHERE protocol_id = ps.protocol_id)";

	/**
	 * Checks and sets the Protocol/Study Status
	 *
	 * @param studySearchResultList
	 */
	private List<StudySearchResult> setStatus(List<StudySearchResult> ctodsSearchResults) throws HibernateException
	{
		try
		{
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();

			for (StudySearchResult ctodsSearchResult : ctodsSearchResults)
			{
				Query query = session.createQuery(QUERY);
				query.setInteger(0, ctodsSearchResult.getId().intValue());
				List<gov.nih.nci.caxchange.ctom.viewer.beans.ProtocolStatus> statusCodes = query.list();

				ctodsSearchResult.setStatus(statusCodes.isEmpty() ? NBSP : statusCodes.get(0).getStatus_code());
			}

			session.getTransaction().commit();
		}
		catch (HibernateException e)
		{
			log.error("setStatus: HibernateException occurred: ", e);
			throw e;
		}

		return ctodsSearchResults;
	}

}
