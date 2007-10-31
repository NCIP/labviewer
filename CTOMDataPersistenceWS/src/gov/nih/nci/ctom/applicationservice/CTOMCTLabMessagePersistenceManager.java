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
		System.out.println(message);
		
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
