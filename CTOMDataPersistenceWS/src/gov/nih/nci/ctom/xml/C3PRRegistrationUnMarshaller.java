package gov.nih.nci.ctom.xml;

import gov.nih.nci.ctom.ctlab.domain.Participant;
import gov.nih.nci.ctom.ctlab.domain.Protocol;
import gov.nih.nci.ctom.ctlab.domain.StudyParticipantAssignment;

import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class C3PRRegistrationUnMarshaller implements UnMarshaller {

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
		Participant part = new Participant();
		try {
			String value = new String();
			expression = "//participant/administrativeGenderCode";
			Node xNode = (Node) xpath.evaluate(expression, doc,
					XPathConstants.NODE);
			value = xNode.getNodeValue();
			part.setAdminGenderCode(value);
			
			//These codes should be sent but must hard code for now, this needs to be refactored
			part.setAdminGenderCodeSystem("C3PR");
			part.setAdminGenderCodeSystemName("C3PR");
		} catch (NullPointerException ex) {
		}

		try {
			String value = new String();
			expression = "//participant/raceCode";
			Node xNode = (Node) xpath.evaluate(expression, doc,
					XPathConstants.NODE);
			value = xNode.getNodeValue();
			part.setRaceCode(value);
			
			//These codes should be sent but must hard code for now, this needs to be refactored
			part.setRaceCodeSystem("C3PR");
			part.setRaceCodeSystemName("C3PR");
		} catch (NullPointerException ex) {
		}

		
		try {
			String value = new String();
			expression = "//participant/birthDate";
			Node xNode = (Node) xpath.evaluate(expression, doc,
					XPathConstants.NODE);
			value = xNode.getNodeValue();
			part.setBirthDateOrig(value);
			Date date = new SimpleDateFormat("yyyy/MM/dd").parse(value);
			part.setBirthDate(date);
		} catch (Exception ex) {
		}
		
		try {
			String value = new String();
			expression = "//participant/ethnicGroupCode";
			Node xNode = (Node) xpath.evaluate(expression, doc,
					XPathConstants.NODE);
			value = xNode.getNodeValue();
			part.setEthnicGroupCode(value);
		} catch (NullPointerException ex) {
		}
		
		try {
			String value = new String();
			expression = "//participant/firstName";
			Node xNode = (Node) xpath.evaluate(expression, doc,
					XPathConstants.NODE);
			value = xNode.getNodeValue();
			part.setFirstName(value);
		} catch (NullPointerException ex) {
		}

		try {
			String value = new String();
			expression = "//participant/lastName";
			Node xNode = (Node) xpath.evaluate(expression, doc,
					XPathConstants.NODE);
			value = xNode.getNodeValue();
			part.setLastName(value);
		} catch (NullPointerException ex) {
		}

		
		try {
			String value = new String();
			expression = "//participant/maritalStatusCode";
			Node xNode = (Node) xpath.evaluate(expression, doc,
					XPathConstants.NODE);
			value = xNode.getNodeValue();
			part.setMaritalStatusCode(value);
		} catch (NullPointerException ex) {
		}

		
		
		
		try {
			part.setInitials(part.getFirstName().substring(0, 1)+part.getLastName().substring(0,1));
		} catch (NullPointerException ex){}
		
		try {
			String value = new String();
			expression = "//participant/address/postalCode";
			Node xNode = (Node) xpath.evaluate(expression, doc,
					XPathConstants.NODE);
			value = xNode.getNodeValue();
			part.setPostalCode(value);
		} catch (NullPointerException ex) {
		}

		try {
			String value = new String();
			expression = "//participant/address/countryCode";
			Node xNode = (Node) xpath.evaluate(expression, doc,
					XPathConstants.NODE);
			value = xNode.getNodeValue();
			part.setCountryCode(value);
		} catch (NullPointerException ex) {
		}

		StudyParticipantAssignment ssa = new StudyParticipantAssignment();
		ssa.setParticipant(part);
		
		try {
			String value = new String();
			expression = "//participant/identifier[type='MRN']/value";
			Node xNode = (Node) xpath.evaluate(expression, doc,
					XPathConstants.NODE);
			value = xNode.getNodeValue();
			ssa.setStudyPartIdOrig(value);
		} catch (NullPointerException ex) {
		}
		

		return ssa;
	}

}
