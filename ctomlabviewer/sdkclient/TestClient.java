import gov.nih.nci.common.util.HQLCriteria;
import gov.nih.nci.labhub.domain.ConceptDescriptorDataType;
import gov.nih.nci.labhub.domain.LabResult;
import gov.nih.nci.labhub.domain.LabTest;
import gov.nih.nci.labhub.domain.Specimen;
import gov.nih.nci.labhub.domain.SpecimenCollection;
import gov.nih.nci.labhub.domain.Study;
import gov.nih.nci.labhub.domain.StudySite;
import gov.nih.nci.labhub.domain.SubjectAssignment;
import gov.nih.nci.system.applicationservice.ApplicationService;
import gov.nih.nci.system.applicationservice.ApplicationServiceProvider;
import gov.nih.nci.system.query.cql.*;


import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class TestClient {

    public static void main(String[] args) {

		String studySubjectIdentifier = "123";
		String conceptDescCode="EOSIN";

		try{
			HashMap map = new HashMap();

			//ApplicationService appService = ApplicationServiceProvider.getRemoteInstance("http://localhost:8080/example/server/HTTPServer");
			ApplicationService appService = ApplicationServiceProvider.getApplicationService();

			CQLQuery query = new CQLQuery();
			CQLObject target = new CQLObject();
			target.setName("gov.nih.nci.labhub.domain.SubjectAssignment");
			target.setAttribute(new CQLAttribute("studySubjectIdentifier",CQLPredicate.EQUAL_TO,studySubjectIdentifier));
			
			CQLAssociation association1 = new CQLAssociation();
			association1.setName("gov.nih.nci.labhub.domain.SpecimenCollection");
			association1.setTargetRoleName("activityCollection");
			
			CQLGroup group = new CQLGroup();
			group.addAttribute(new CQLAttribute("actualStartDateTime",CQLPredicate.GREATER_THAN,"11/11/2001"));
			group.addAttribute(new CQLAttribute("actualStartDateTime",CQLPredicate.LESS_THAN,"11/11/2009"));
			group.setLogicOperator(CQLLogicalOperator.AND);
			association1.setGroup(group);

			target.setAssociation(association1);

			CQLAssociation association2 = new CQLAssociation();
			association2.setName("gov.nih.nci.labhub.domain.Specimen");
			association2.setTargetRoleName("specimenCollection");
			association1.setAssociation(association2);

			CQLAssociation association3 = new CQLAssociation();
			association3.setName("gov.nih.nci.labhub.domain.LabTest");
			association3.setSourceRoleName("specimen");
			association2.setAssociation(association3);
			
			CQLAssociation association4 = new CQLAssociation();
			association4.setName("gov.nih.nci.labhub.domain.ConceptDescriptorDataType");
			association4.setTargetRoleName("labTestId");
			association4.setAttribute(new CQLAttribute("code",CQLPredicate.EQUAL_TO,conceptDescCode));
			association3.setAssociation(association4);

			query.setTarget(target);
			
			
			String hql =" select sa from gov.nih.nci.labhub.domain.SubjectAssignment sa, "+
									" gov.nih.nci.labhub.domain.Study study,"+
									" gov.nih.nci.labhub.domain.StudySite site,"+
									" gov.nih.nci.labhub.domain.SpecimenCollection specimenCollection,"+
									" gov.nih.nci.labhub.domain.Specimen specimen,"+
									" gov.nih.nci.labhub.domain.LabTest labTest,"+
									" gov.nih.nci.labhub.domain.ConceptDescriptorDataType conceptDesc"+
									" where"+
									" sa.studySubjectIdentifier="+studySubjectIdentifier+" and"+
									" sa.studySite=site and"+
									" site.study=study and"+
									" specimenCollection in elements(sa.activityCollection) and"+
									" specimen in elements(specimenCollection.specimenCollection) and"+
									" labTest.specimen=specimen and "+
									" labTest.labTestId=conceptDesc "+
									" and conceptDesc.code='"+conceptDescCode+"' "+
									" order by study.identifier";
			
			
			List resultList = appService.query(query, "gov.nih.nci.labhub.domain.SubjectAssignment");
			//List resultList = appService.query(new HQLCriteria(hql), "gov.nih.nci.labhub.domain.SubjectAssignment");
			//List resultList = appService.search(SubjectAssignment.class, new SubjectAssignment());

			PrintWriter out = new PrintWriter(new FileWriter("out.csv"));

			out.println("Study,SubjectAssignment,StartDate,LabTest,Result,Units");
			for (Iterator resultsIterator = resultList.iterator(); resultsIterator.hasNext();) 
			{
				SubjectAssignment sa = (SubjectAssignment) resultsIterator.next();
				if(map.get(sa.getId()) == null)
					printRecord(sa,out);
				map.put(sa.getId(), sa);
			}
			out.close();
		}
		catch(Exception ex){
			//ex.printStackTrace();
			logger.error("Test client throws Exception = "+ ex);
		}
	}
    
    private static void printRecord(SubjectAssignment sa, java.io.PrintWriter out)
    {
    	if(sa==null) return;
    	
		if(sa!=null)
		{
			StudySite site = sa.getStudySite();
			if(site!=null)
			{
				Study study=site.getStudy();
				out.print(study.getIdentifier());
			}
			out.println(","+sa.getStudySubjectIdentifier());
			Collection activityCollection = sa.getActivityCollection();
			if(activityCollection !=null && activityCollection.size()>0)
			{
				for(Iterator activityIterator=activityCollection.iterator();activityIterator.hasNext();)
				{
					SpecimenCollection activity = (SpecimenCollection)activityIterator.next();
					String date = convertToString(activity.getActualStartDateTime());
					out.println(",,"+date);
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
										ConceptDescriptorDataType labTestId = labTest.getLabTestId();
										if(labTestId!=null)
											out.print(",,,"+labTestId.getCode());
										LabResult labResult = labTest.getLabResult();
										if(labResult!=null)
										{
											out.print(","+labResult.getNumericResult());
											ConceptDescriptorDataType units = labTest.getLabTestId();
											if(units!=null)
												out.print(","+units.getCode());
										}
										out.print("\n");
									}
								}
							}
						}
					}
				}
			}
		}
    }
    
    private static String convertToString(java.util.Date date)
    {
    	if(date == null) return "-";
    	SimpleDateFormat formatter = new SimpleDateFormat();
    	String str = formatter.format(date);
    	return str;
    }
}
