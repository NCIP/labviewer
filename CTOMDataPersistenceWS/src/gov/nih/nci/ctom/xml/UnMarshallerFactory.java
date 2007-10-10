package gov.nih.nci.ctom.xml;

import gov.nih.nci.ctom.ctlab.domain.Protocol;
import gov.nih.nci.ctom.exceptions.UnSupportedMessageFormatException;

import java.io.ByteArrayInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class UnMarshallerFactory {

	public UnMarshaller getUnMarshaller(String xml) throws Exception {

		XPathFactory factory = XPathFactory.newInstance();
		XPath xPath = factory.newXPath();

		DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();

		DocumentBuilder db = fact.newDocumentBuilder();
		ByteArrayInputStream inputXMLStr = new ByteArrayInputStream(xml
				.getBytes());

		Document doc = db.parse(inputXMLStr);
		doc.getDocumentElement().normalize();
		// Test the Xpath API
		XPath xpath = XPathFactory.newInstance().newXPath();

		String expression = null;

		String id = new String();
		expression = "count(/ClinicalTrial) = 1";
		Boolean bl = (Boolean) xpath.evaluate(expression, doc,
				XPathConstants.BOOLEAN);
		if (bl)
			return new HL7v3CtLabUnMarshaller();

		expression = "count(/registration) = 1";
		bl = (Boolean) xpath.evaluate(expression, doc, XPathConstants.BOOLEAN);
		if (bl)
			return new C3PRRegistrationUnMarshaller();

		throw new UnSupportedMessageFormatException(
				"XML was sent in an format that is not supported.");

	}
}
