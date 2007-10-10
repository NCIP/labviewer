package gov.nih.nci.ctom.applicationservice;

import gov.nih.nci.ctom.ctlab.domain.Activity;
import gov.nih.nci.ctom.ctlab.domain.AdverseEvent;
import gov.nih.nci.ctom.ctlab.domain.CentralLaboratory;
import gov.nih.nci.ctom.ctlab.domain.ClinicalResult;
import gov.nih.nci.ctom.ctlab.domain.HealthCareSite;
import gov.nih.nci.ctom.ctlab.domain.Investigator;
import gov.nih.nci.ctom.ctlab.domain.Observation;
import gov.nih.nci.ctom.ctlab.domain.Participant;
import gov.nih.nci.ctom.ctlab.domain.PerformingLaboratory;
import gov.nih.nci.ctom.ctlab.domain.Procedure;
import gov.nih.nci.ctom.ctlab.domain.Protocol;
import gov.nih.nci.ctom.ctlab.domain.Specimen;
import gov.nih.nci.ctom.ctlab.domain.SpecimenCollection;
import gov.nih.nci.ctom.ctlab.domain.StudyParticipantAssignment;
import gov.nih.nci.ctom.ctlab.domain.StudyTimePoint;
import gov.nih.nci.ctom.ctlab.persistence.CTLabDAO;
import gov.nih.nci.ctom.util.HL7v3DateParser;
import gov.nih.nci.ctom.ws.datapersistence.PersistDataRequest;
import gov.nih.nci.ctom.xml.UnMarshaller;
import gov.nih.nci.ctom.xml.UnMarshallerFactory;

import java.io.ByteArrayInputStream;
import java.sql.Connection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class CTOMCTLabMessagePersistenceManager implements
		CTOMPersistenceManager {

	Logger logger = Logger.getLogger(getClass());

	public void saveData(Object obj) throws Exception {

		PersistDataRequest pdr = (PersistDataRequest) obj;

		String message = pdr.getMessage().getData();

//		String message = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <ClinicalTrial xmlns=\"urn:hl7-org:v3\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" classCode=\"CLNTRL\" moodCode=\"EVN\"> <id root=\"2.16.840.1.113883.19\" extension=\"6230\" assigningAuthorityName=\"NCI\"/> <title representation=\"TXT\" mediaType=\"text/plain\">RANDOMIZED PHASE I/II STUDY OF VACCINIA-CEA(6D)-TRICOM AND FOWLPOX-(6D)-TRICOM WITH GM-CSF IN COMBINATION WITH DOCETAXEL IN PATIENTS WITH CEA-BEARING CANCERS</title> <component typeCode=\"COMP\" contextConductionInd=\"true\"> <trialAtSite classCode=\"CLNTRL\" moodCode=\"EVN\"> <location typeCode=\"LOC\" contextControlCode=\"OP\"> <trialSite classCode=\"SDLOC\"> <id root=\"2.16.840.1.113883.19\" extension=\"DC005\"/> </trialSite> </location> <component typeCode=\"COMP\" contextConductionInd=\"true\"> <investigatorAtSite classCode=\"CLNTRL\" moodCode=\"EVN\"> <responsibleParty typeCode=\"RESP\" contextControlCode=\"OP\"> <trialInvestigator classCode=\"CRINV\"> <id root=\"2.16.840.1.113883.19\" extension=\"13\"/> <investigatorNamedPerson classCode=\"PSN\" determinerCode=\"INSTANCE\"> <name>MARSHALL, JOHN, MD</name> </investigatorNamedPerson> </trialInvestigator> </responsibleParty> <component typeCode=\"COMP\" contextConductionInd=\"true\"> <subjectAssignment classCode=\"CLNTRL\" moodCode=\"EVN\"> <recordTarget typeCode=\"RCT\" contextControlCode=\"OP\"> <enrolledSubject classCode=\"RESBJ\"> <id root=\"2.16.840.1.113883.19\" extension=\"SMITHJANE1\"/> </enrolledSubject> </recordTarget> <component typeCode=\"COMP\" contextConductionInd=\"true\"> <studyEvent classCode=\"CTTEVENT\" moodCode=\"EVN\"> <code code=\"dummy\" codeSystem=\"2.16.840.1.113883.19\"/> <component typeCode=\"COMP\" contextConductionInd=\"true\"> <accession classCode=\"ACSN\" moodCode=\"EVN\"> <author typeCode=\"AUT\" contextControlCode=\"OP\"> <agent classCode=\"AGNT\"> <representedLaboratory classCode=\"ORG\" determinerCode=\"INSTANCE\"> <id root=\"2.16.840.1.113883.19\" extension=\"1\"/> </representedLaboratory> </agent> </author> <component typeCode=\"COMP\" contextConductionInd=\"true\"> <baseSpecimenDefinition classCode=\"ACT\" moodCode=\"EVN\"> <subject typeCode=\"SBJ\" contextControlCode=\"OP\"> <baseSpecimen classCode=\"SPEC\"> <productOf typeCode=\"PRD\" contextControlCode=\"OP\"> <specimenCollectionProcedure classCode=\"PROC\" moodCode=\"EVN\"> <effectiveTime xsi:type=\"IVL_TS\"> <low value=\"200603311155\"/> </effectiveTime> </specimenCollectionProcedure> </productOf> </baseSpecimen> </subject> <component typeCode=\"COMP\" contextConductionInd=\"true\"> <baseBattery classCode=\"OBS\" moodCode=\"EVN\"> <code code=\"1\" codeSystem=\"2.16.840.1.113883.19\"/> <component typeCode=\"COMP\" contextConductionInd=\"true\"> <baseUnitaryResult classCode=\"OBS\" moodCode=\"EVN\"> <code code=\"BASO_PCT_BLD\" displayName=\"BASO %\" codeSystem=\"2.16.840.1.113883.19\" codeSystemName=\"C3D\"> <originalText representation=\"TXT\" mediaType=\"text/plain\">BASO %</originalText> </code> <statusCode code=\"completed\"/> <value value=\"0.0\" unit=\"%\"/> <performer typeCode=\"PRF\" contextControlCode=\"OP\"> <agent classCode=\"AGNT\"> <representedLaboratory classCode=\"ORG\" determinerCode=\"INSTANCE\"> <id root=\"2.16.840.1.113883.19\" extension=\"1\"/> </representedLaboratory> </agent> </performer> </baseUnitaryResult> </component> </baseBattery> </component> </baseSpecimenDefinition> </component> </accession> </component> </studyEvent> </component> </subjectAssignment> </component> </investigatorAtSite> </component> </trialAtSite> </component> </ClinicalTrial>";
		
		
		Object dObj = parseXMLToObject(message);
		
		CTLabDAO dao = new CTLabDAO();
		Connection con = null;
		try {

			con = dao.getConnection();
			con.setAutoCommit(false);
			if (dObj instanceof gov.nih.nci.ctom.ctlab.domain.Protocol){
				dao.saveProtocol(con, (gov.nih.nci.ctom.ctlab.domain.Protocol)dObj);
			} else if (dObj instanceof gov.nih.nci.ctom.ctlab.domain.StudyParticipantAssignment){
				dao.updateStudyParticipantAssignment(con, (gov.nih.nci.ctom.ctlab.domain.StudyParticipantAssignment) dObj);
			}
			con.commit();
			logger.debug("Message succussfully saved to the CTOM Database");
		} catch (Exception ex) {
			logger
					.debug(
							"Error Occurred While Writing the CTOM Database, A Rollback Was Excecuted",
							ex);
			try {
				con.rollback();
			} catch (Exception e) {
			}
			try {
				con.close();
			} catch (Exception e) {
			}
			throw new Exception(
					"Error Occurred While Writing the CTOM Database, A Rollback Was Excecuted",
					ex);
		} finally {
			try {con.close();} catch(Exception ex){}
		}
		

	}

	protected Object parseXMLToObject(String xml) throws Exception {

		UnMarshallerFactory fact =  new UnMarshallerFactory();
		
		UnMarshaller um = fact.getUnMarshaller(xml);
		return um.parseXmlToObject(xml);
		
	
	}
}
