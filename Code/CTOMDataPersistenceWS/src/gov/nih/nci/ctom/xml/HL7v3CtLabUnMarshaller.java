package gov.nih.nci.ctom.xml;

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
import gov.nih.nci.ctom.util.HL7v3DateParser;

import java.io.ByteArrayInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class HL7v3CtLabUnMarshaller implements UnMarshaller {

	public Object parseXmlToObject(String xml) throws Exception {
		XPathFactory factory = XPathFactory.newInstance();
		XPath xPath = factory.newXPath();

		DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();

		DocumentBuilder db = fact.newDocumentBuilder();
		ByteArrayInputStream inputXMLStr = new ByteArrayInputStream(xml.getBytes());

		Document doc = db.parse(inputXMLStr);
		doc.getDocumentElement().normalize();
		// Test the Xpath API
		XPath xpath = XPathFactory.newInstance().newXPath();

		String expression = null;
		Protocol prot = new Protocol();
		try {
			String id = new String();
			expression = "/ClinicalTrial/id/@root";
			Node xNode = (Node) xpath.evaluate(expression, doc,
					XPathConstants.NODE);
			id = xNode.getNodeValue();

			expression = "/ClinicalTrial/id/@extension";
			xNode = (Node) xpath.evaluate(expression, doc, XPathConstants.NODE);
			id = id + "." + xNode.getNodeValue();
			prot.setNciIdentifier(id);

		} catch (NullPointerException ex) {
			try {
			String id = new String();
			expression = "/ClinicalTrial/id/@root";
			Node xNode = (Node) xpath.evaluate(expression, doc,
					XPathConstants.NODE);
			id = xNode.getNodeValue();

			expression = "/ClinicalTrial/id/@extension";
			xNode = (Node) xpath.evaluate(expression, doc, XPathConstants.NODE);
			id = id + "." + xNode.getNodeValue();
			prot.setNciIdentifier(id);
			} catch (NullPointerException e){}
		}

		try {
			expression = "/ClinicalTrial/id/@assigningAuthorityName";
			Node xNode = (Node) xpath.evaluate(expression, doc,
					XPathConstants.NODE);
			prot.setIdAssigningAuth(xNode.getNodeValue());
		} catch (NullPointerException ex) {
			try {
			expression = "/ClinicalTrial/id/@assigningAuthorityName";
			Node xNode = (Node) xpath.evaluate(expression, doc,
					XPathConstants.NODE);
			prot.setIdAssigningAuth(xNode.getNodeValue());
			} catch (NullPointerException e){}
		}

		try {
			expression = "/ClinicalTrial/title";
			Node xNode = (Node) xpath.evaluate(expression, doc,
					XPathConstants.NODE);
			prot.setLongTxtTitle(xNode.getFirstChild().getNodeValue());

		} catch (NullPointerException ex) {
			try {
			expression = "/ClinicalTrial/title";
			Node xNode = (Node) xpath.evaluate(expression, doc,
					XPathConstants.NODE);
			prot.setLongTxtTitle(xNode.getFirstChild().getNodeValue());
			} catch (NullPointerException e){}
		}

		HealthCareSite hcs = new HealthCareSite();
		try {
			String id = null;
			expression = "//trialSite/id/@root";
			Node xNode = (Node) xpath.evaluate(expression, doc,
					XPathConstants.NODE);
			id = xNode.getNodeValue();

			expression = "//trialSite/id/@extension";
			xNode = (Node) xpath.evaluate(expression, doc, XPathConstants.NODE);
			id = id + "." + xNode.getNodeValue();

			hcs.setNciInstituteCd(id);

		} catch (NullPointerException ex) {
		}

		Investigator inv = new Investigator();
		try {
			String id = null;
			expression = "//trialInvestigator/id/@root";
			Node xNode = (Node) xpath.evaluate(expression, doc,
					XPathConstants.NODE);
			id = xNode.getNodeValue();

			expression = "//trialInvestigator/id/@extension";
			xNode = (Node) xpath.evaluate(expression, doc, XPathConstants.NODE);
			id = id + "." + xNode.getNodeValue();

			inv.setNciId(id);

		} catch (NullPointerException ex) {
		}

		try {

			expression = "//investigatorNamedPerson/name";
			Node xNode = (Node) xpath.evaluate(expression, doc,
					XPathConstants.NODE);
			inv.setLastName(xNode.getFirstChild().getNodeValue());

		} catch (NullPointerException ex) {
		}

		Participant participant = new Participant();
		try {

			expression = "//enrolledSubject/subjectPerson/name";
			Node xNode = (Node) xpath.evaluate(expression, doc,
					XPathConstants.NODE);
			participant.setInitials(xNode.getFirstChild().getNodeValue());

		} catch (NullPointerException ex) {
		}

		try {

			expression = "//enrolledSubject/subjectPerson/birthTime/@value";
			Node xNode = (Node) xpath.evaluate(expression, doc,
					XPathConstants.NODE);
			participant.setBirthDateOrig(xNode.getNodeValue());

			participant.setBirthDate(HL7v3DateParser
					.convertHL7DateToDate(participant.getBirthDateOrig()));

		} catch (NullPointerException ex) {
		}

		try {

			expression = "//enrolledSubject/subjectPerson/raceCode/@code";
			Node xNode = (Node) xpath.evaluate(expression, doc,
					XPathConstants.NODE);
			participant.setRaceCode(xNode.getNodeValue());

			expression = "//enrolledSubject/subjectPerson/raceCode/@codeSystem";
			xNode = (Node) xpath.evaluate(expression, doc, XPathConstants.NODE);
			participant.setRaceCode(xNode.getNodeValue());

			expression = "//enrolledSubject/subjectPerson/raceCode/@codeSystemName";
			xNode = (Node) xpath.evaluate(expression, doc, XPathConstants.NODE);
			//participant.setRaceCodeSystemName(xNode.getNodeValue());

		} catch (NullPointerException ex) {
		}

		try {

			expression = "//enrolledSubject/subjectPerson/administrativeGenderCode/@code";
			Node xNode = (Node) xpath.evaluate(expression, doc,
					XPathConstants.NODE);
			participant.setAdminGenderCode(xNode.getNodeValue());

			expression = "//enrolledSubject/subjectPerson/administrativeGenderCode/@codeSystem";
			xNode = (Node) xpath.evaluate(expression, doc, XPathConstants.NODE);
			//participant.setAdminGenderCodeSystem(xNode.getNodeValue());

			expression = "//enrolledSubject/subjectPerson/administrativeGenderCode/@codeSystemName";
			xNode = (Node) xpath.evaluate(expression, doc, XPathConstants.NODE);
			//participant.setAdminGenderCodeSystemName(xNode.getNodeValue());

		} catch (NullPointerException ex) {
		}

		StudyParticipantAssignment spa = new StudyParticipantAssignment();
		try {

			String id = null;
			expression = "//enrolledSubject/id/@root";
			Node xNode = (Node) xpath.evaluate(expression, doc,
					XPathConstants.NODE);
			id = xNode.getNodeValue();

			expression = "//enrolledSubject/id/@extension";
			xNode = (Node) xpath.evaluate(expression, doc, XPathConstants.NODE);
			id = id + "." + xNode.getNodeValue();

			spa.setStudyPartIdOrig(id);
		} catch (NullPointerException ex) {
		}

		try {
			expression = "//enrolledSubject/code/@code";
			Node xNode = (Node) xpath.evaluate(expression, doc,
					XPathConstants.NODE);
			spa.setType(xNode.getNodeValue());

		} catch (NullPointerException ex) {
		}

		StudyTimePoint stp = new StudyTimePoint();
		try {

			String value = null;
			expression = "//studyEvent/code/@code";
			Node xNode = (Node) xpath.evaluate(expression, doc,
					XPathConstants.NODE);
			value = xNode.getNodeValue();

			stp.setVisitName(value);
		} catch (NullPointerException e1) {

		}

		Activity act = new Activity();

		try {
			String value = null;
			expression = "//studyEvent/reasonCode/@code";
			Node xNode = (Node) xpath.evaluate(expression, doc,
					XPathConstants.NODE);
			value = xNode.getNodeValue();

			act.setReasonCode(value);
		} catch (NullPointerException ex) {
		}

		Specimen specimen = new Specimen();
		try {

			String id = null;
			expression = "//studyEvent/component/accession/id/@root";
			Node xNode = (Node) xpath.evaluate(expression, doc,
					XPathConstants.NODE);
			id = xNode.getNodeValue();

			expression = "//studyEvent/component/accession/id/@extension";
			xNode = (Node) xpath.evaluate(expression, doc, XPathConstants.NODE);
			id = id + "." + xNode.getNodeValue();

			specimen.setAccessionNumber(id);

		} catch (NullPointerException e1) {
		}

		CentralLaboratory lab = new CentralLaboratory();
		try {

			String id = null;
			expression = "//studyEvent/component/accession/author/agent/representedLaboratory/id/@root";
			Node xNode = (Node) xpath.evaluate(expression, doc,
					XPathConstants.NODE);
			id = xNode.getNodeValue();

			expression = "//studyEvent/component/accession/author/agent/representedLaboratory/id/@extension";
			xNode = (Node) xpath.evaluate(expression, doc, XPathConstants.NODE);
			id = id + "." + xNode.getNodeValue();

			lab.setIdentifier(id);

		} catch (NullPointerException e1) {

		}

		try {

			String value = null;
			expression = "//studyEvent/component/accession/author/agent/representedLaboratory/name";
			Node xNode = (Node) xpath.evaluate(expression, doc,
					XPathConstants.NODE);
			value = xNode.getFirstChild().getNodeValue();

			lab.setName(value);
		} catch (NullPointerException e1) {
		}

		try {

			String id = null;
			expression = "//studyEvent/component/accession/component/baseSpecimenDefinition/subject/baseSpecimen/id/@root";
			Node xNode = (Node) xpath.evaluate(expression, doc,
					XPathConstants.NODE);
			id = xNode.getNodeValue();

			expression = "//studyEvent/component/accession/component/baseSpecimenDefinition/subject/baseSpecimen/id/@extension";
			xNode = (Node) xpath.evaluate(expression, doc, XPathConstants.NODE);
			id = id + "." + xNode.getNodeValue();

			specimen.setSampleIdentifierOrig(id);

		} catch (NullPointerException e1) {
		}

		try {

			String value = null;
			expression = "//studyEvent/component/accession/component/baseSpecimenDefinition/subject/baseSpecimen/productOf/specimenCollectionProcedure/effectiveTime/low/@value";
			Node xNode = (Node) xpath.evaluate(expression, doc,
					XPathConstants.NODE);
			value = xNode.getNodeValue();
			act.setStartDateOrig(value);
		} catch (NullPointerException ex) {

		}

		try {
			String value = null;
			expression = "//studyEvent/component/accession/component/baseSpecimenDefinition/subject/baseSpecimen/productOf/specimenCollectionProcedure/effectiveTime/high/@value";
			Node xNode = (Node) xpath.evaluate(expression, doc,
					XPathConstants.NODE);
			value = xNode.getNodeValue();
			act.setStopDateOrig(value);

		} catch (NullPointerException ex) {

		}

		try {
			String value = null;
			expression = "//studyEvent/component/accession/component/baseSpecimenDefinition/subject/baseSpecimen/productOf/specimenCollectionProcedure/pertinentInformation2/ageAtVisit/value/@value";
			Node xNode = (Node) xpath.evaluate(expression, doc,
					XPathConstants.NODE);
			value = xNode.getNodeValue();
			act.setAgeAtVisit(value);

		} catch (NullPointerException ex) {

		}

		try {
			String value = null;
			expression = "//studyEvent/component/accession/component/baseSpecimenDefinition/subject/baseSpecimen/productOf/specimenCollectionProcedure/pertinentInformation2/ageAtVisit/value/@unit";
			Node xNode = (Node) xpath.evaluate(expression, doc,
					XPathConstants.NODE);
			value = xNode.getNodeValue();
			act.setAgeAtVisitUnits(value);

		} catch (NullPointerException ex) {

		}

		Procedure procedure = new Procedure();

		try {
			String value = null;
			expression = "//studyEvent/component/accession/component/baseSpecimenDefinition/subject/baseSpecimen/productOf/specimenCollectionProcedure/pertinentInformation1/fastingStatus/value";
			Node xNode = (Node) xpath.evaluate(expression, doc,
					XPathConstants.NODE);
			value = xNode.getFirstChild().getNodeValue();
			procedure.setFastingStatus(value);

		} catch (NullPointerException ex) {

		}

		try {

			expression = "//studyEvent/component/accession/component/baseSpecimenDefinition/subject/baseSpecimen/subjectOf1/specimenComment/code/@code";
			Node xNode = (Node) xpath.evaluate(expression, doc,
					XPathConstants.NODE);
			String codeValue = xNode.getNodeValue();

			String value = null;
			expression = "//studyEvent/component/accession/component/baseSpecimenDefinition/subject/baseSpecimen/subjectOf1/specimenComment/value";
			xNode = (Node) xpath.evaluate(expression, doc, XPathConstants.NODE);
			value = xNode.getFirstChild().getNodeValue();

			if (codeValue.equalsIgnoreCase("comments_from_laboratory")) {
				specimen.setCommentsFromLaboratory(value);
			} else if (codeValue.equalsIgnoreCase("comments_from_investigator")) {
				specimen.setCommentsFromInvestigator(value);
			} else if (codeValue.equalsIgnoreCase("condition")) {
				specimen.setCondition(value);
			}

		} catch (NullPointerException ex) {

		}

		try {
			String value = null;
			expression = "//studyEvent/component/accession/component/baseSpecimenDefinition/subject/baseSpecimen/specimenNaturalMaterial/code/@code";
			Node xNode = (Node) xpath.evaluate(expression, doc,
					XPathConstants.NODE);
			value = xNode.getNodeValue();
			specimen.setSampleTypeCd(value);

			expression = "//studyEvent/component/accession/component/baseSpecimenDefinition/subject/baseSpecimen/specimenNaturalMaterial/code/@codeSystem";
			xNode = (Node) xpath.evaluate(expression, doc, XPathConstants.NODE);
			value = xNode.getNodeValue();
			specimen.setSampleTypeCdSystem(value);

			expression = "//studyEvent/component/accession/component/baseSpecimenDefinition/subject/baseSpecimen/specimenNaturalMaterial/code/@codeSystemName";
			xNode = (Node) xpath.evaluate(expression, doc, XPathConstants.NODE);
			value = xNode.getNodeValue();
			specimen.setSampleTypeCdSystemName(value);

		} catch (NullPointerException ex) {

		}

		ClinicalResult clinicalResult = new ClinicalResult();
		try {
			String value = null;
			expression = "//studyEvent/component/accession/component/baseSpecimenDefinition/component/baseBattery/code/@code";
			Node xNode = (Node) xpath.evaluate(expression, doc,
					XPathConstants.NODE);
			value = xNode.getNodeValue();
			clinicalResult.setPanelName(value);

		} catch (NullPointerException ex) {

		}

		try {
			String value = null;
			expression = "//studyEvent/component/accession/component/baseSpecimenDefinition/component/baseBattery/component/baseUnitaryResult/code/@code";
			Node xNode = (Node) xpath.evaluate(expression, doc,
					XPathConstants.NODE);
			value = xNode.getNodeValue();
			clinicalResult.setCrCode(value);

		} catch (NullPointerException ex) {

		}

		try {
			String value = null;
			expression = "//studyEvent/component/accession/component/baseSpecimenDefinition/component/baseBattery/component/baseUnitaryResult/code/@codeSystem";
			Node xNode = (Node) xpath.evaluate(expression, doc,
					XPathConstants.NODE);
			value = xNode.getNodeValue();
			clinicalResult.setCrCodeSystem(value);

		} catch (NullPointerException ex) {

		}

		try {
			String value = null;
			expression = "//studyEvent/component/accession/component/baseSpecimenDefinition/component/baseBattery/component/baseUnitaryResult/code/@codeSystemName";
			Node xNode = (Node) xpath.evaluate(expression, doc,
					XPathConstants.NODE);
			value = xNode.getNodeValue();
			clinicalResult.setCrCodeSystemName(value);

		} catch (NullPointerException ex) {

		}

		try {
			Double version = null;
			String value = null;
			expression = "//studyEvent/component/accession/component/baseSpecimenDefinition/component/baseBattery/component/baseUnitaryResult/code/@codeSystemVersion";
			Node xNode = (Node) xpath.evaluate(expression, doc,
					XPathConstants.NODE);
			value = xNode.getNodeValue();
			try {
				version = Double.valueOf(value);
				clinicalResult.setCrCodeSystemVersion(version);
			} catch (Exception ex) {

			}
		} catch (NullPointerException ex) {

		}

		try {
			String value = null;
			expression = "//studyEvent/component/accession/component/baseSpecimenDefinition/component/baseBattery/component/baseUnitaryResult/code/@displayName";
			Node xNode = (Node) xpath.evaluate(expression, doc,
					XPathConstants.NODE);
			value = xNode.getNodeValue();
			clinicalResult.setCrCodeDisplayText(value);

		} catch (NullPointerException ex) {

		}

		try {
			String value = null;
			expression = "//studyEvent/component/accession/component/baseSpecimenDefinition/component/baseBattery/component/baseUnitaryResult/text";
			Node xNode = (Node) xpath.evaluate(expression, doc,
					XPathConstants.NODE);
			value = xNode.getFirstChild().getNodeValue();
			clinicalResult.setValue(value);

		} catch (NullPointerException ex) {

		}

		Observation observation = new Observation();

		try {
			String value = null;
			expression = "//studyEvent/component/accession/component/baseSpecimenDefinition/subject/baseSpecimen/productOf/specimenCollectionProcedure/pertinentInformation1/testComment/text";
			Node xNode = (Node) xpath.evaluate(expression, doc,
					XPathConstants.NODE);
			value = xNode.getFirstChild().getNodeValue();
			observation.setComments(value);

		} catch (NullPointerException ex) {

		}

		try {
			String value = null;
			expression = "//studyEvent/component/accession/component/baseSpecimenDefinition/component/baseBattery/component/baseUnitaryResult/statusCode/@code";
			Node xNode = (Node) xpath.evaluate(expression, doc,
					XPathConstants.NODE);
			value = xNode.getNodeValue();
			observation.setStatusCode(value);

		} catch (NullPointerException ex) {

		}

		try {
			String value = null;
			expression = "//studyEvent/component/accession/component/baseSpecimenDefinition/component/baseBattery/component/baseUnitaryResult/activityTime/low/@value";
			Node xNode = (Node) xpath.evaluate(expression, doc,
					XPathConstants.NODE);
			value = xNode.getNodeValue();
			observation.setReportingDateOrig(value);

		} catch (NullPointerException ex) {

		}

		try {
			String value = null;
			expression = "//studyEvent/component/accession/component/baseSpecimenDefinition/component/baseBattery/component/baseUnitaryResult/confidentialityCode/@code";
			Node xNode = (Node) xpath.evaluate(expression, doc,
					XPathConstants.NODE);
			value = xNode.getNodeValue();
			observation.setConfidentialityCode(value);

		} catch (NullPointerException ex) {

		}

		try {
			String value = null;
			expression = "//studyEvent/component/accession/component/baseSpecimenDefinition/component/baseBattery/component/baseUnitaryResult/reasonCode/@code";
			Node xNode = (Node) xpath.evaluate(expression, doc,
					XPathConstants.NODE);
			value = xNode.getNodeValue();
			act.setReasonCode(value);

		} catch (NullPointerException ex) {

		}

		try {
			String value = null;
			expression = "//studyEvent/component/accession/component/baseSpecimenDefinition/component/baseBattery/component/baseUnitaryResult/value/@value";
			Node xNode = (Node) xpath.evaluate(expression, doc,
					XPathConstants.NODE);
			value = xNode.getNodeValue();
			clinicalResult.setValue(value);

		} catch (NullPointerException ex) {

		}

		try {
			String value = null;
			expression = "//studyEvent/component/accession/component/baseSpecimenDefinition/component/baseBattery/component/baseUnitaryResult/value/@unit";
			Node xNode = (Node) xpath.evaluate(expression, doc,
					XPathConstants.NODE);
			value = xNode.getNodeValue();
			clinicalResult.setValueUnitOfMeasureCd(value);

			// Set this because it's not available in the xml
			clinicalResult
					.setValueUnitOfMeasureCdSystem("2.16.840.1.113883.5.141");

		} catch (NullPointerException ex) {

		}

		try {
			String value = null;
			expression = "//studyEvent/component/accession/component/baseSpecimenDefinition/component/baseBattery/component/baseUnitaryResult/interpretationCode/@code";
			Node xNode = (Node) xpath.evaluate(expression, doc,
					XPathConstants.NODE);
			value = xNode.getNodeValue();
			clinicalResult.setRefFlag(value);

		} catch (NullPointerException ex) {

		}

		try {
			String value = null;
			expression = "//studyEvent/component/accession/component/baseSpecimenDefinition/component/baseBattery/component/baseUnitaryResult/methodCode/@code";
			Node xNode = (Node) xpath.evaluate(expression, doc,
					XPathConstants.NODE);
			value = xNode.getNodeValue();
			clinicalResult.setLabTechCd(value);

		} catch (NullPointerException ex) {

		}

		PerformingLaboratory performingLaboratory = new PerformingLaboratory();
		try {

			String id = null;
			expression = "//studyEvent/component/accession/component/baseSpecimenDefinition/component/baseBattery/component/baseUnitaryResult/performer/agent/representedLaboratory/id/@root";
			Node xNode = (Node) xpath.evaluate(expression, doc,
					XPathConstants.NODE);
			id = xNode.getNodeValue();

			expression = "//studyEvent/component/accession/component/baseSpecimenDefinition/component/baseBattery/component/baseUnitaryResult/performer/agent/representedLaboratory/id/@extension";
			xNode = (Node) xpath.evaluate(expression, doc, XPathConstants.NODE);
			id = id + "." + xNode.getNodeValue();

			performingLaboratory.setPlIdentifier(id);

		} catch (XPathExpressionException e1) {
		}

		try {
			String value = null;
			expression = "//studyEvent/component/accession/component/baseSpecimenDefinition/component/baseBattery/component/baseUnitaryResult/performer/agent/representedLaboratory/name";
			Node xNode = (Node) xpath.evaluate(expression, doc,
					XPathConstants.NODE);
			value = xNode.getFirstChild().getNodeValue();
			performingLaboratory.setPlName(value);

		} catch (NullPointerException ex) {

		}

		AdverseEvent adverseEvent = new AdverseEvent();
		try {
			String value = null;
			expression = "//studyEvent/component/accession/component/baseSpecimenDefinition/subject/baseSpecimen/productOf/specimenCollectionProcedure/pertinentInformation2/toxicityGrade/code/@code";
			Node xNode = (Node) xpath.evaluate(expression, doc,
					XPathConstants.NODE);
			value = xNode.getNodeValue();
			adverseEvent.setCtcTermTypeCd(value);

		} catch (NullPointerException ex) {

		}

		try {
			String value = null;
			expression = "//studyEvent/component/accession/component/baseSpecimenDefinition/subject/baseSpecimen/productOf/specimenCollectionProcedure/pertinentInformation2/toxicityGrade/code/@codeSystem";
			Node xNode = (Node) xpath.evaluate(expression, doc,
					XPathConstants.NODE);
			value = xNode.getNodeValue();
			adverseEvent.setCtcTermTypeCdSystem(value);

		} catch (NullPointerException ex) {

		}

		try {
			String value = null;
			expression = "//studyEvent/component/accession/component/baseSpecimenDefinition/subject/baseSpecimen/productOf/specimenCollectionProcedure/pertinentInformation2/toxicityGrade/value/@code";
			Node xNode = (Node) xpath.evaluate(expression, doc,
					XPathConstants.NODE);
			value = xNode.getNodeValue();
			adverseEvent.setCtcGradeCd(value);

		} catch (NullPointerException ex) {

		}

		try {
			String value = null;
			expression = "//studyEvent/component/accession/component/baseSpecimenDefinition/subject/baseSpecimen/productOf/specimenCollectionProcedure/pertinentInformation2/toxicityGrade/value/@codeSystem";
			Node xNode = (Node) xpath.evaluate(expression, doc,
					XPathConstants.NODE);
			value = xNode.getNodeValue();
			adverseEvent.setCtcGradeCdSystem(value);

		} catch (NullPointerException ex) {

		}

		SpecimenCollection specimenCollection = new SpecimenCollection();
		specimenCollection.setCentralLaboratory(lab);
		specimenCollection.setSpecimen(specimen);

		procedure.setSpecimenCollection(specimenCollection);

		clinicalResult.setPerformingLaboratory(performingLaboratory);
		observation.setClinicalResult(clinicalResult);

		act.setObservation(observation);
		act.setProcedure(procedure);
		act.setStudyTimePoint(stp);

		spa.setActivity(act);
		spa.setParticipant(participant);
		hcs.setStudyParticipantAssignment(spa);
		prot.setHealthCareSite(hcs);
		prot.setInvestigator(inv);

		return prot;
	}

}
