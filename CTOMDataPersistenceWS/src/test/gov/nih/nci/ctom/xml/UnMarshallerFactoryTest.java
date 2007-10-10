package test.gov.nih.nci.ctom.xml;

import gov.nih.nci.ctom.xml.C3PRRegistrationUnMarshaller;
import gov.nih.nci.ctom.xml.HL7v3CtLabUnMarshaller;
import gov.nih.nci.ctom.xml.UnMarshaller;
import gov.nih.nci.ctom.xml.UnMarshallerFactory;
import junit.framework.TestCase;

public class UnMarshallerFactoryTest extends TestCase {

	public void testGetUnMarshaller() {
	
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <registration healthCareSiteGridId=\"String\" studyGridId=\"String\"> <informedConsentFormSignedDate>1967-08-13</informedConsentFormSignedDate> <enrollmentDate>1967-08-13</enrollmentDate> <studyParticipantIdentifier>String</studyParticipantIdentifier> <eligibilityIndicator>true</eligibilityIndicator> <participant participantGridId=\"String\"> <administrativeGenderCode>String</administrativeGenderCode> <birthDate>1967-08-13</birthDate> <ethnicGroupCode>String</ethnicGroupCode> <firstName>String</firstName> <lastName>String</lastName> <maritalStatusCode>String</maritalStatusCode> <raceCode>String</raceCode> <identifier> <source>String</source> <type>String</type> <value>String</value> <isprimary>true</isprimary> </identifier> <address> <city>String</city> <countryCode>String</countryCode> <postalCode>String</postalCode> <stateCode>String</stateCode> <streetAddress>String</streetAddress> </address> </participant> <study> <identifier> <source>String</source> <type>String</type> <value>String</value> <isprimary>true</isprimary> </identifier> </study> <identifier> <source>String</source> <type>String</type> <value>String</value> <isprimary>true</isprimary> </identifier> </registration>";;
		
		
		UnMarshallerFactory fact =  new UnMarshallerFactory();
		try {
			UnMarshaller um = fact.getUnMarshaller(xml);
			if(um instanceof C3PRRegistrationUnMarshaller)
				assertTrue(true);
			else 
				fail();
			
			xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <ClinicalTrial xmlns=\"urn:hl7-org:v3\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" classCode=\"CLNTRL\" moodCode=\"EVN\"> <id root=\"2.16.840.1.113883.19\" extension=\"6230\" assigningAuthorityName=\"NCI\"/> <title representation=\"TXT\" mediaType=\"text/plain\">RANDOMIZED PHASE I/II STUDY OF VACCINIA-CEA(6D)-TRICOM AND FOWLPOX-(6D)-TRICOM WITH GM-CSF IN COMBINATION WITH DOCETAXEL IN PATIENTS WITH CEA-BEARING CANCERS</title> <component typeCode=\"COMP\" contextConductionInd=\"true\"> <trialAtSite classCode=\"CLNTRL\" moodCode=\"EVN\"> <location typeCode=\"LOC\" contextControlCode=\"OP\"> <trialSite classCode=\"SDLOC\"> <id root=\"2.16.840.1.113883.19\" extension=\"DC005\"/> </trialSite> </location> <component typeCode=\"COMP\" contextConductionInd=\"true\"> <investigatorAtSite classCode=\"CLNTRL\" moodCode=\"EVN\"> <responsibleParty typeCode=\"RESP\" contextControlCode=\"OP\"> <trialInvestigator classCode=\"CRINV\"> <id root=\"2.16.840.1.113883.19\" extension=\"13\"/> <investigatorNamedPerson classCode=\"PSN\" determinerCode=\"INSTANCE\"> <name>MARSHALL, JOHN, MD</name> </investigatorNamedPerson> </trialInvestigator> </responsibleParty> <component typeCode=\"COMP\" contextConductionInd=\"true\"> <subjectAssignment classCode=\"CLNTRL\" moodCode=\"EVN\"> <recordTarget typeCode=\"RCT\" contextControlCode=\"OP\"> <enrolledSubject classCode=\"RESBJ\"> <id root=\"2.16.840.1.113883.19\" extension=\"SMITHJANE1\"/> </enrolledSubject> </recordTarget> <component typeCode=\"COMP\" contextConductionInd=\"true\"> <studyEvent classCode=\"CTTEVENT\" moodCode=\"EVN\"> <code code=\"dummy\" codeSystem=\"2.16.840.1.113883.19\"/> <component typeCode=\"COMP\" contextConductionInd=\"true\"> <accession classCode=\"ACSN\" moodCode=\"EVN\"> <author typeCode=\"AUT\" contextControlCode=\"OP\"> <agent classCode=\"AGNT\"> <representedLaboratory classCode=\"ORG\" determinerCode=\"INSTANCE\"> <id root=\"2.16.840.1.113883.19\" extension=\"1\"/> </representedLaboratory> </agent> </author> <component typeCode=\"COMP\" contextConductionInd=\"true\"> <baseSpecimenDefinition classCode=\"ACT\" moodCode=\"EVN\"> <subject typeCode=\"SBJ\" contextControlCode=\"OP\"> <baseSpecimen classCode=\"SPEC\"> <productOf typeCode=\"PRD\" contextControlCode=\"OP\"> <specimenCollectionProcedure classCode=\"PROC\" moodCode=\"EVN\"> <effectiveTime xsi:type=\"IVL_TS\"> <low value=\"200603311155\"/> </effectiveTime> </specimenCollectionProcedure> </productOf> </baseSpecimen> </subject> <component typeCode=\"COMP\" contextConductionInd=\"true\"> <baseBattery classCode=\"OBS\" moodCode=\"EVN\"> <code code=\"1\" codeSystem=\"2.16.840.1.113883.19\"/> <component typeCode=\"COMP\" contextConductionInd=\"true\"> <baseUnitaryResult classCode=\"OBS\" moodCode=\"EVN\"> <code code=\"BASO_PCT_BLD\" displayName=\"BASO %\" codeSystem=\"2.16.840.1.113883.19\" codeSystemName=\"C3D\"> <originalText representation=\"TXT\" mediaType=\"text/plain\">BASO %</originalText> </code> <statusCode code=\"completed\"/> <value value=\"0.0\" unit=\"%\"/> <performer typeCode=\"PRF\" contextControlCode=\"OP\"> <agent classCode=\"AGNT\"> <representedLaboratory classCode=\"ORG\" determinerCode=\"INSTANCE\"> <id root=\"2.16.840.1.113883.19\" extension=\"1\"/> </representedLaboratory> </agent> </performer> </baseUnitaryResult> </component> </baseBattery> </component> </baseSpecimenDefinition> </component> </accession> </component> </studyEvent> </component> </subjectAssignment> </component> </investigatorAtSite> </component> </trialAtSite> </component> </ClinicalTrial>";
			um = fact.getUnMarshaller(xml);
			if(um instanceof HL7v3CtLabUnMarshaller)
				assertTrue(true);
			else 
				fail();
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			this.assertTrue(false);
		}
		
	}

}
