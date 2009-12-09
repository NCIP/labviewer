/**
 * 
 */
package gov.nih.nci.ctom.ctlab.junits;


import gov.nih.nci.ctom.ctlab.domain.Activity;
import gov.nih.nci.ctom.ctlab.domain.CentralLaboratory;
import gov.nih.nci.ctom.ctlab.domain.ClinicalResult;
import gov.nih.nci.ctom.ctlab.domain.HealthCareSite;
import gov.nih.nci.ctom.ctlab.domain.Identifier;
import gov.nih.nci.ctom.ctlab.domain.Investigator;
import gov.nih.nci.ctom.ctlab.domain.Observation;
import gov.nih.nci.ctom.ctlab.domain.Participant;
import gov.nih.nci.ctom.ctlab.domain.PerformingLaboratory;
import gov.nih.nci.ctom.ctlab.domain.Procedure;
import gov.nih.nci.ctom.ctlab.domain.Protocol;
import gov.nih.nci.ctom.ctlab.domain.Specimen;
import gov.nih.nci.ctom.ctlab.domain.SpecimenCollection;
import gov.nih.nci.ctom.ctlab.domain.StudyParticipantAssignment;
import gov.nih.nci.ctom.ctlab.handler.HL7V3MessageHandlerInterface;
import gov.nih.nci.ctom.ctlab.handler.ProtocolHandler;
import gov.nih.nci.ctom.ctlab.persistence.CTLabDAO;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;


/**
 * @author asharma
 *
 */
public class HandlersTest
{
	 Protocol protocol = null;
	 Connection con =null;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		protocol = new Protocol();
		Date today = new Date();
		//set Study details
		Identifier iiProtocol = new Identifier(); 
		protocol.setNciIdentifier("nci");
		iiProtocol.setExtension("SMOKE_TEST");
		iiProtocol.setRoot("2.16.840.1.113883.19");
		protocol.setIdentifier(iiProtocol);
		protocol.setLongTxtTitle("Smoke Test Study");
		protocol.setShortTxtTitle("Smoke Test");
		protocol.setCtomInsertDt(today);
		//set Investigators
		Investigator investigator = new Investigator();
		investigator.setFirstName("Leon");
		investigator.setLastName("First");
		investigator.setCtomInsertDt(today);
		investigator.setNciId("2.16.840.1.113883.19.NCI");
		protocol.setInvestigator(investigator);
		
		//set Healthcare site details
		HealthCareSite  hcs = new HealthCareSite();
		hcs.setCity("Dallas");
		hcs.setCountryCode("USA");
		hcs.setCtomInsertDt(today);
		hcs.setNciInstituteCd("NCI");
		hcs.setPostalCode("75081");
		hcs.setName("NCI-HCS");
		protocol.setHealthCareSite(hcs);
		//Set SPA
		StudyParticipantAssignment spa = new StudyParticipantAssignment();
		spa.setCtomInsertDt(today);
		Identifier iiSPA = new Identifier(); 
		iiSPA.setExtension("00-00-00-0");
		iiSPA.setRoot("2.16.840.1.113883.19");
		spa.setIdentifier(iiSPA);
		spa.setStudyPartIdOrig(iiSPA.getRoot()+"."+iiSPA.getExtension());
		protocol.getHealthCareSite().setStudyParticipantAssignment(spa);
		//Set Activity
		Activity activity = new Activity();
		activity.setCtomInsertDate(today);
		activity.setStartDate(today);
		activity.setStartDateOrig(new SimpleDateFormat("yyyyMMddHHmmss").format(today));
		activity.setStopDate(today);
		activity.setStopDateOrig(new SimpleDateFormat("yyyyMMddHHmmss").format(today));
		protocol.getHealthCareSite().getStudyParticipantAssignment().setActivity(activity);
		
		//set Observation 
		 Observation observation = new Observation();
		 observation.setStatusCode("Completed");
		 observation.setCtomInsertDt(today);
		 protocol.getHealthCareSite().getStudyParticipantAssignment().getActivity().setObservation(observation);
		 
		 //set Performing lab 
		 PerformingLaboratory performLab = new PerformingLaboratory();
		 performLab.setPlIdentifier("1");
		 performLab.setPlName("NCI");
		 
		 //set clinicalresult
		 ClinicalResult cr = new ClinicalResult();
		 cr.setValue("11");
		 cr.setRefRangeLow(8.000000000000);
		 cr.setRefRangeHigh(22.000000000000);
		 cr.setPerformingLaboratory(performLab);
		 protocol.getHealthCareSite().getStudyParticipantAssignment().getActivity().getObservation().setClinicalResult(cr);
		 
		 //set Procedure
		 Procedure procedure = new Procedure();
		 procedure.setCtomInsertDt(today);
		 procedure.setFastingStatus("Fasting");
		 
		 //set specimen collection
		 SpecimenCollection specimenColl = new SpecimenCollection();
		 specimenColl.setCtomInsertDt(today);
		 	 
		 //set specimen
		 Specimen specimen = new Specimen();
		 specimen.setCtomInsertDt(today);
		 
		 specimenColl.setSpecimen(specimen);
		 procedure.setSpecimenCollection(specimenColl);
		 protocol.getHealthCareSite().getStudyParticipantAssignment().getActivity().setProcedure(procedure);
		 
		 //set central lab
		 CentralLaboratory centralLab =new CentralLaboratory();
		 centralLab.setIdentifier("1");
		 centralLab.setName("NCI");
		 protocol.getHealthCareSite().getStudyParticipantAssignment().getActivity().getProcedure().getSpecimenCollection().setCentralLaboratory(centralLab);
		 
		//set Participant details
		Participant part = new Participant();
		Identifier iiParticipant = new Identifier(); 
		iiParticipant.setExtension("00-00-00-0");
		iiParticipant.setRoot("2.16.840.1.113883.19");
		part.setIdentifier(iiParticipant);
		part.setFirstName("Joe");
		part.setLastName("Frost");
		part.setCtomInsertDate(new Date());
		protocol.getHealthCareSite().getStudyParticipantAssignment().setParticipant(part);
		
		//retrieve the SPA id to be persisted in the DB
		
		CTLabDAO ctLab = new CTLabDAO();
		con = ctLab.getConnection();
     
	}
   
	@Test
	public void testProperties() throws Exception{
		assertHandlerBehaviour(new ProtocolHandler() );
	}
	
	public void assertHandlerBehaviour(HL7V3MessageHandlerInterface clazz) throws Exception{
		
		clazz.persist(con, protocol);
	}
}
