/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
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
