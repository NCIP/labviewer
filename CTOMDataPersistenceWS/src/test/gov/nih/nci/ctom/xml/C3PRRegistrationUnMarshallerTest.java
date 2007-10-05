package test.gov.nih.nci.ctom.xml;

import gov.nih.nci.ctom.xml.C3PRRegistrationUnMarshaller;
import gov.nih.nci.ctom.xml.HL7v3CtLabUnMarshaller;
import gov.nih.nci.ctom.xml.UnMarshaller;
import gov.nih.nci.ctom.xml.UnMarshallerFactory;
import junit.framework.TestCase;

public class C3PRRegistrationUnMarshallerTest extends TestCase {

	public void testParseXmlToObject() {
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <registration xmlns=\"http://semanticbits.com/registration.xsd\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" healthCareSiteGridId=\"NCI\" studyGridId=\"b13b207a-2289-4350-9883-25492fc0e8ea\"> <informedConsentFormSignedDate>2006-05-04</informedConsentFormSignedDate> <enrollmentDate>2006-05-04</enrollmentDate> <studyParticipantIdentifier>studyParticipantIdentifier0</studyParticipantIdentifier> <eligibilityIndicator>false</eligibilityIndicator> <participant participantGridId=\"b13b207a-2289-4350-9983-25492fc0e8ea\"> <administrativeGenderCode>administrativeGenderCode0</administrativeGenderCode> <birthDate>2006-05-04</birthDate> <ethnicGroupCode>ethnicGroupCode0</ethnicGroupCode> <firstName>firstName0</firstName> <lastName>lastName0</lastName> <maritalStatusCode>maritalStatusCode0</maritalStatusCode> <raceCode>raceCode0</raceCode> <identifier> <source>NCI</source> <type>MRN</type> <value>00-66-00-1</value> <isprimary>true</isprimary> </identifier> <identifier> <source>source1</source> <type>type1</type> <value>value1</value> <isprimary>false</isprimary> </identifier> <address> <city>city0</city> <countryCode>countryCode0</countryCode> <postalCode>postalCode0</postalCode> <stateCode>stateCode0</stateCode> <streetAddress>streetAddress0</streetAddress> </address> </participant> <study> <identifier> <source>National Cancer Institute</source> <type>Protocol Authority Identifier</type> <value>NCI-04-C-0121</value> <isprimary>true</isprimary> </identifier> <identifier> <source>National Cancer Institute</source> <type>C3D Identifier</type> <value>04_C_0121</value> <isprimary>false</isprimary> </identifier> <identifier> <source>National Cancer Institute</source> <type>ClinicalTrials.gov</type> <value>NCT00080821</value> <isprimary>false</isprimary> </identifier> </study> <identifier> <source>C3PR</source> <type>Grid Identifier</type> <value>b13b207a-2289-4350-8883-25492fc0e8ea</value> <isprimary>false</isprimary> </identifier> <identifier> <source>C3D</source> <type>Patient Position</type> <value>xxxx</value> <isprimary>false</isprimary> </identifier>     </registration>";;
		
		
		UnMarshallerFactory fact =  new UnMarshallerFactory();
		try {
			UnMarshaller um = fact.getUnMarshaller(xml);
			Object obj = um.parseXmlToObject(xml);
			System.out.println(obj.getClass().getCanonicalName());
		} catch (Exception e) {
			e.printStackTrace();
			this.assertTrue(false);
		}

	}

}
