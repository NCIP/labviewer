package gov.nih.nci.caxchange.ctom.viewer.util;



import gov.nih.nci.caxchange.ctom.viewer.viewobjects.LabActivityResult;
import gov.nih.nci.ctom.messaging.loadlabactivities.LoadLabActivities;
import gov.nih.nci.ctom.messaging.loadlabactivities.Study;
import gov.nih.nci.ctom.messaging.loadlabactivities.StudySubjectAssignment;
import gov.nih.nci.labhub.domain.ConceptDescriptorDataType;
import gov.nih.nci.labhub.domain.LabResult;
import gov.nih.nci.labhub.domain.LabTest;
import gov.nih.nci.labhub.domain.Specimen;
import gov.nih.nci.labhub.domain.SpecimenCollection;
import gov.nih.nci.labhub.domain.SubjectAssignment;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.exolab.castor.types.Date;

public class LoadLabActivitiesMapper {

	
	public LoadLabActivities mapLabActivities(List<LabActivityResult>  results){
		
		if (results == null)
			return null;
		
		
		
		LoadLabActivities las = new LoadLabActivities();
		
		for (LabActivityResult rslt: results){
			
		
		
		//for(SubjectAssignment sa : assignments) {
			SubjectAssignment sa = rslt.getSubjectAssignment();
			StudySubjectAssignment xmlSA = new StudySubjectAssignment();
			xmlSA.setStudySubjectIdentifier(sa.getStudySubjectIdentifier());
			las.addStudySubjectAssignment(xmlSA);
			
			
			Study study = new Study();
			xmlSA.setStudy(study);
			study.setAssigningAuthority(sa.getStudySite().getStudy().getAssigningAuthority());
			study.setIdentifier(sa.getStudySite().getStudy().getIdentifier());
			study.setName(sa.getStudySite().getStudy().getName());


			Collection activityCollection = sa.getActivityCollection();
			
			int ct = 0;
			if(activityCollection !=null && activityCollection.size()>0)
			{
				for(Iterator activityIterator=activityCollection.iterator();activityIterator.hasNext();)
				{
					SpecimenCollection activity = (SpecimenCollection)activityIterator.next();
					
					gov.nih.nci.ctom.messaging.loadlabactivities.Activity xmlAct = new gov.nih.nci.ctom.messaging.loadlabactivities.Activity();
					xmlAct.setStartDateTime(activity.getActualStartDateTime());
					xmlAct.setEndDateTime(activity.getActualEndDateTime());
					xmlAct.setIdentifier(activity.getIdentifier());
			
					
					Collection specimenCollection = activity.getSpecimenCollection();
					if(specimenCollection!=null && specimenCollection.size()>0)
					{
						for(Iterator specimenIterator = specimenCollection.iterator();specimenIterator.hasNext();)
						{
							Specimen specimen = (Specimen)specimenIterator.next();
							Collection labTestCollection = specimen.getLabTestCollection();
					
							if(labTestCollection!=null && labTestCollection.size()>0)
							{
								for(Iterator labTestIterator = labTestCollection.iterator();labTestIterator.hasNext();)
								{
									LabTest labTest = (LabTest)labTestIterator.next();
									if(labTest!=null)
									{
										gov.nih.nci.ctom.messaging.loadlabactivities.LabTest xmlLabTest = new gov.nih.nci.ctom.messaging.loadlabactivities.LabTest();
										ConceptDescriptorDataType labTestIde = labTest.getLabTestId();
										if(labTestIde!=null){
											xmlLabTest.setCode(labTestIde.getCode());
										}
										
										gov.nih.nci.ctom.messaging.loadlabactivities.LabResult xmlLabResult = new gov.nih.nci.ctom.messaging.loadlabactivities.LabResult();

										
										LabResult labResult = labTest.getLabResult();
										if(labResult!=null)
										{
											System.out.println("Before Lab Result comparison "+labResult.getId().intValue()+" to "+rslt.getLabResult().getId().intValue());
											if(labResult.getId().intValue() == rslt.getLabResult().getId().intValue()){
												//setting the activity
												xmlSA.addActivity(xmlAct);
												xmlAct.addLabTest(xmlLabTest);
												xmlLabTest.setLabResult(xmlLabResult);												
											
										
												
											}
											
											try {
												xmlLabResult.setNumericResult(new Float(labResult.getNumericResult()));
											} catch (Exception ex){
												xmlLabResult.setNumericResult(0);
												
											} //do nothing if can't parse to a float.
											
											if (labResult.getTextResult() != null)
												xmlLabResult.setTextResult(labResult.getTextResult());
											else 
												xmlLabResult.setTextResult(String.valueOf(labResult.getNumericResult()));
											
										
											ConceptDescriptorDataType units = labResult.getUnits();
										
											if(units!=null) {
												xmlLabResult.setUnitOfMeasureCode(units.getCode());
											}else {
												xmlLabResult.setUnitOfMeasureCode(" ");
											}
												
											if(labResult.getReferenceRangeComments() != null)
												xmlLabResult.setReferenceRangeComments(labResult.getReferenceRangeComments());
											else
												xmlLabResult.setReferenceRangeComments(" ");
											
											try{
												xmlLabResult.setReferenceRangeLow(new Double(labResult.getReferenceRangeLow()));
											} catch(Exception ex) {
												
											}
											
											try {
												
												xmlLabResult.setReferenceRangeHigh(new Double(labResult.getReferenceRangeHigh()));
											} catch (Exception ex){}
	
											try {
											xmlLabResult.setTestPerformedDate(new Date(activity.getActualStartDateTime().getTime()));
											} catch (Exception ex){
												xmlLabResult.setTestPerformedDate(new Date(new java.util.Date()));
											}
										}
		
									}
								}
							}
						}
					}
				}
			}

			
			
			//las.addLaStudySubjectAssignment(LaStudySubjectAssignment)
			
			
		}
		
		return las;
	}
	
	
	
}
