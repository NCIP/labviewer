package gov.nih.nci.caxchange.ctom.viewer.apitest;
import gov.nih.nci.cagrid.caxchange.test.TestCaXchangeGridService;
//import gov.nih.nci.caxchange.ctom.viewer.viewobjects.LabActivityResult;
import gov.nih.nci.labhub.domain.Activity;
import gov.nih.nci.labhub.domain.CD;
import gov.nih.nci.labhub.domain.II;
import gov.nih.nci.labhub.domain.LaboratoryResult;
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

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

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
	 public CTODSLabDomainModelAPITest() {
	    }

	    public CTODSLabDomainModelAPITest(String name) {
	        super(name);
	    }
	
	
	// Create the query to get SubjectAssignment object
	public void testAPIClient(){
	try {
		ApplicationService appService = ApplicationServiceProvider.getApplicationService();

		// Create the query to get SubjectAssignment object
		CQLQuery query = new CQLQuery();
		CQLObject target = new CQLObject();
		target.setName("gov.nih.nci.labhub.domain.SubjectAssignment");
		
		// Set the subject Identifier on the association to II
		CQLAssociation subjectAssignmentAssociation1 = new CQLAssociation();
		subjectAssignmentAssociation1.setName("gov.nih.nci.labhub.domain.II");
		subjectAssignmentAssociation1.setTargetRoleName("studySubjectIdentifier");
		subjectAssignmentAssociation1.setAttribute(new CQLAttribute("extension",
				CQLPredicate.EQUAL_TO, "59-60-00-3"));
		
		// Now get to StudySite
		CQLAssociation subjectAssignmentAssociation2 = new CQLAssociation();
		subjectAssignmentAssociation2.setName("gov.nih.nci.labhub.domain.StudySite");
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
				CQLPredicate.EQUAL_TO, "04_C_0121"));
		studySiteAssociation1.setAssociation(studyAssociation1);

		// Now get the SpecimenCollection
		CQLAssociation subjectAssignmentAssociation3 = new CQLAssociation();
		subjectAssignmentAssociation3.setName("gov.nih.nci.labhub.domain.SpecimenCollection");
		subjectAssignmentAssociation3.setTargetRoleName("activityCollection");

		// Set the date conditions
		CQLGroup group = new CQLGroup();
		group.addAttribute(new CQLAttribute("actualStartDateTime",
				CQLPredicate.GREATER_THAN, "1/1/2007"));
		group.addAttribute(new CQLAttribute("actualStartDateTime",
				CQLPredicate.LESS_THAN, "10/30/2007"));

		group.setLogicOperator(CQLLogicalOperator.AND);
		subjectAssignmentAssociation3.setGroup(group);

		// Then put it all together
		CQLGroup finalgroup = new CQLGroup();
		finalgroup.addAssociation(subjectAssignmentAssociation1);
		finalgroup.addAssociation(subjectAssignmentAssociation2);
		finalgroup.addAssociation(subjectAssignmentAssociation3);
		finalgroup.setLogicOperator(CQLLogicalOperator.AND);
		target.setGroup(finalgroup);

		query.setTarget(target);
		List resultList = appService.query(query);
		Iterator iter = resultList.iterator();
		while (iter.hasNext()){
			SubjectAssignment sa = (SubjectAssignment)iter.next();
			//LabActivityResult labActivityResult=null;
			
			StudySite s = sa.getStudySite();
			//System.out.println("\t Records from database");
			System.out.println(iter.getClass());
			System.out.println("\tID="+sa.getId());
			//System.out.println("\tTYPE="+sa.getType());
			//System.out.println("\t ACTIVITY="+sa.getActivityCollection());
			//Collection<Activity> activities = sa.getActivityCollection();
			/*for (Activity activity : activities) {
				System.out.println("\t Start Date:"+activity.getPlannedTimeElapsedDescription());
				System.out.println("\t End Date: "+activity.getActualEndDateTime());
			}*/
			
			Collection activityCollection = sa.getActivityCollection();
			for (Iterator activityIterator = activityCollection.iterator(); activityIterator.hasNext();)
			{
				
				SpecimenCollection activity = (SpecimenCollection) activityIterator.next();
				Collection specimenCollection = activity.getSpecimenCollection();
				//System.out.println("specimen collection"+specimenCollection);
				Iterator specimenIterator = specimenCollection.iterator();
				Specimen specimen = (Specimen) specimenIterator.next();
				Collection labTestCollection = specimen.getLaboratoryTestCollection();
				

				for (Iterator labTestIterator = labTestCollection
						.iterator(); labTestIterator.hasNext();)
				{
					LaboratoryTest labTest = (LaboratoryTest) labTestIterator.next();
					CD labTestIde = labTest.getLaboratoryTestId();
					LaboratoryResult labResult = labTest.getLaboratoryResult();
					CD units = labResult.getUnits();
					String labname=specimen.getCommentFromLaboratory();
					Float numericResult = labResult.getNumericResult();
					String unitOfMeasure = units.getCode();
					Float lowRange = new Float ( labResult.getReferenceRangeLow());
					Float highRange = new Float (labResult.getReferenceRangeHigh());
					
					System.out.println("\t LAB NAME="+labname+"\t VALUE="+numericResult+"\t UNIT OF MEASURE="+unitOfMeasure+ "\t LOW RANGE="+lowRange+"\t HIGH RANGE"+highRange);
				}
		
			}
			
			
			//System.out.println("\t STUDY SUBJECT ID="+sa.getStudySubjectIdentifier());
			//System.out.println("\t PATTICIPANT="+sa.getParticipant());
			//System.out.println("\t CLASS="+sa.getClass());
			System.out.println("\t STUDY SITE ="+sa.getStudySite());
			
			Collection<II> temp = sa.getStudySubjectIdentifier();
			for(II ii:temp)
				System.out.println("\t\tsubjIdentifier.extension="+ii.getExtension()+"--"+ii.getRoot());
			for(II ii:s.getStudy().getStudyIdentifier())
				
				System.out.println("\t\tsubjIdentifier.extension="+ii.getExtension()+"--"+ii.getRoot());
			
		}		
		
	} catch (Exception e) {
		e.printStackTrace();
		assertTrue(false);
	}
	
	}
	
	
	
	
	
	
	
	
    public static Test suite() {
    	System.out.println("Add more test to the suits here");
        TestSuite suite = new TestSuite();

        suite.addTest(new CTODSLabDomainModelAPITest("testAPIClient"));
        

        return suite;
     }

}
