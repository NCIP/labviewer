package gov.nih.nci.caxchange.ctom.viewer.apitest;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;


import gov.nih.nci.labhub.domain.II;
import gov.nih.nci.labhub.domain.Participant;
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


import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * @author Yogaraj Khanal
 *
 * TODO Test Cases for the CTOM Lab Domain Model API
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CTODSLabDomainModelAPITest extends TestCase{
	/* public CTODSLabDomainModelAPITest() {
	    }

	    public CTODSLabDomainModelAPITest(String name) {
	        super(name);
	    }
	*/
	
	// Create the query to get SubjectAssignment object
	public void testAPIClient(){
	try {
		ApplicationService appService = ApplicationServiceProvider.getApplicationService();
		CQLQuery query = new CQLQuery();
		CQLObject target = new CQLObject();
		target.setName("gov.nih.nci.labhub.domain.SubjectAssignment");
		// Set the subject Identifier on the association to II
		CQLAssociation subjectAssignmentAssociation1 = new CQLAssociation();
		subjectAssignmentAssociation1.setName("gov.nih.nci.labhub.domain.II");
		subjectAssignmentAssociation1.setTargetRoleName("studySubjectIdentifier");
		subjectAssignmentAssociation1.setAttribute(new CQLAttribute("extension",
		CQLPredicate.EQUAL_TO, "59-60-00-3"));
		//CQLPredicate.EQUAL_TO, "MRN-4200"));
		CQLAssociation studyIds = new CQLAssociation();
		studyIds.setName("gov.nih.nci.labhub.domain.II");
		studyIds.setTargetRoleName("studyIdentifier");
		studyIds.setAttribute(new CQLAttribute("extension",	CQLPredicate.EQUAL_TO, "04_C_0121"));
		
		
		CQLAssociation studyAss = new CQLAssociation();
		studyAss.setName("gov.nih.nci.labhub.domain.Study");
		studyAss.setTargetRoleName("study");
		studyAss.setAssociation(studyIds);
		CQLAssociation studyAssociation1 = new CQLAssociation();
		studyAssociation1.setName("gov.nih.nci.labhub.domain.StudySite");
		studyAssociation1.setAssociation(studyAss);
		//sa-->ss-->s-->si-->ext
		CQLGroup group = new CQLGroup();
		group.addAttribute(new CQLAttribute("actualStartDateTime",
		CQLPredicate.GREATER_THAN, "1/1/2007"));
		group.addAttribute(new CQLAttribute("actualStartDateTime",
		CQLPredicate.LESS_THAN, "10/30/2007"));
		group.setLogicOperator(CQLLogicalOperator.AND);
		CQLAssociation subjectAssignmentAssociation3 = new CQLAssociation();
		subjectAssignmentAssociation3.setName("gov.nih.nci.labhub.domain.Activity");
		subjectAssignmentAssociation3.setTargetRoleName("activityCollection");
		subjectAssignmentAssociation3.setGroup(group);
		// Then put it all together
		CQLGroup finalgroup = new CQLGroup();
		finalgroup.addAssociation(subjectAssignmentAssociation1);
		finalgroup.addAssociation(studyAssociation1);
		finalgroup.addAssociation(subjectAssignmentAssociation3);
		finalgroup.setLogicOperator(CQLLogicalOperator.AND);
		target.setGroup(finalgroup);
		//target.setAssociation(studyAssociation1);
		query.setTarget(target);
		List resultList = appService.query(query);
		Iterator iter = resultList.iterator();
		while (iter.hasNext())
		{
			SubjectAssignment sa = (SubjectAssignment)iter.next();
			StudySite s = sa.getStudySite();
			System.out.println("\t Records from database");
			System.out.println("\t---------------------");
			System.out.println("\tID="+sa.getId());
			System.out.println("\tTYPE="+sa.getType());
			System.out.println("\t ACTIVITY="+sa.getActivityCollection());
			//System.out.println("\t STUDY SUBJECT ID="+sa.getStudySubjectIdentifier());
			System.out.println("\t PATTICIPANT="+sa.getParticipant());
			System.out.println("\t CLASS="+sa.getClass());
			System.out.println("\t STUDY SITE ="+sa.getStudySite());
			
			Collection<II> temp = sa.getStudySubjectIdentifier();
			for(II ii:temp)
				System.out.println("\t\tsubjIdentifier.extension="+ii.getExtension()+"--"+ii.getRoot());
			for(II ii:s.getStudy().getStudyIdentifier())
				
				System.out.println("\t\tsubjIdentifier.extension="+ii.getExtension()+"--"+ii.getRoot());
			//assertEquals("59-60-00-3",s.getStudy().getStudyIdentifier());
		}
		
	} catch (Exception e) {
		e.printStackTrace();
		assertTrue(false);
	}
	
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	


}
