package gov.nih.nci.caxchange.ctom.viewer.util;

import gov.nih.nci.ctom.messaging.loadlabactivities.LoadLabActivities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.StringReader;
import java.io.StringWriter;

import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;

import junit.framework.TestCase;

public class LoadLabActivitiesMapperTest extends TestCase {

	public void testMapLabActivities() {
		String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><LoadLabActivities xmlns=\"http://ctom.nci.nih.gov/labactivities\"><studySubjectAssignment><ns1:study xmlns:ns1=\"http://ctom.nci.nih.gov/common\"><ns1:identifier>String</ns1:identifier><ns1:name>String</ns1:name><ns1:assigningAuthority>String</ns1:assigningAuthority></ns1:study><ns2:studySubjectIdentifier xmlns:ns2=\"http://ctom.nci.nih.gov/common\">String</ns2:studySubjectIdentifier><ns3:activity xmlns:ns3=\"http://ctom.nci.nih.gov/common\"><ns3:identifier>String</ns3:identifier><ns3:startDateTime>2001-12-17T04:30:47.000-05:00</ns3:startDateTime><ns3:endDateTime>2001-12-17T04:30:47.000-05:00</ns3:endDateTime><ns3:labTest><ns3:code>String</ns3:code><ns3:labResult><ns3:textResult>String</ns3:textResult><ns3:numericResult>3.14159</ns3:numericResult><ns3:unitOfMeasureCode>String</ns3:unitOfMeasureCode><ns3:referenceRangeComments>String</ns3:referenceRangeComments><ns3:referenceRangeHigh>3.141593</ns3:referenceRangeHigh><ns3:referenceRangeLow>3.141593</ns3:referenceRangeLow><ns3:testPerformedDate>1967-08-13</ns3:testPerformedDate></ns3:labResult></ns3:labTest></ns3:activity></studySubjectAssignment></LoadLabActivities>";
		
		BufferedReader reader = new BufferedReader(new StringReader(xmlString));
//
//		 Marshal the object
		try {
			LoadLabActivities las = (LoadLabActivities)Unmarshaller.unmarshal(LoadLabActivities.class, reader);
			
			// Marshal the object
			StringWriter strw =  new StringWriter();	
			BufferedWriter writer = new BufferedWriter(strw);
			Marshaller.marshal(las, writer);
			System.out.println(strw.toString());
			
		
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

	}

}
