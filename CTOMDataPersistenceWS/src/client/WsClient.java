package client;

import gov.nih.nci.ctom.ws.CTOMDataPersistenceService;
import gov.nih.nci.ctom.ws.datapersistence.Acknowledgement;
import gov.nih.nci.ctom.ws.datapersistence.Credentials;
import gov.nih.nci.ctom.ws.datapersistence.Message;
import gov.nih.nci.ctom.ws.datapersistence.PersistDataRequest;

import java.io.FileInputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.Logger;
import org.codehaus.xfire.XFire;
import org.codehaus.xfire.XFireFactory;
import org.codehaus.xfire.client.XFireProxyFactory;
import org.codehaus.xfire.service.Service;
import org.codehaus.xfire.service.binding.ObjectServiceFactory;

/** Servlet working as a Web services client. 
 * 
 */
public class WsClient {	
	private static Logger log = Logger.getLogger(WsClient.class);	


	public static void main(String[] args)
	{
		WsClient client = new WsClient();
		client.test();
	}
	
	public void test()
	{
		try
		{
			System.out.println("Beginning test");
			
			URL fileLoc = getClass().getResource("/SampleHL7v3.xml");
			
			// Read the file from disk
			FileInputStream fis = new FileInputStream(fileLoc.getFile());
			int x= fis.available();
			byte b[]= new byte[x];
			fis.read(b);
			String content = new String(b);
			
			Credentials c = new Credentials();
			c.setUserName("Vijay");
			c.setPassword("Vijay");
			Message m = new Message();
			m.setData(content);
			//m.setData("<?xml version=\"1.0\" encoding=\"UTF-8\"?> <ClinicalTrial xmlns=\"urn:hl7-org:v3\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" classCode=\"CLNTRL\" moodCode=\"EVN\"> <id root=\"2.16.840.1.113883.19\" extension=\"6230\" assigningAuthorityName=\"NCI\"/> <title representation=\"TXT\" mediaType=\"text/plain\">RANDOMIZED PHASE I/II STUDY OF VACCINIA-CEA(6D)-TRICOM AND FOWLPOX-(6D)-TRICOM WITH GM-CSF IN COMBINATION WITH DOCETAXEL IN PATIENTS WITH CEA-BEARING CANCERS</title> <component typeCode=\"COMP\" contextConductionInd=\"true\"> <trialAtSite classCode=\"CLNTRL\" moodCode=\"EVN\"> <location typeCode=\"LOC\" contextControlCode=\"OP\"> <trialSite classCode=\"SDLOC\"> <id root=\"2.16.840.1.113883.19\" extension=\"DC005\"/> </trialSite> </location> <component typeCode=\"COMP\" contextConductionInd=\"true\"> <investigatorAtSite classCode=\"CLNTRL\" moodCode=\"EVN\"> <responsibleParty typeCode=\"RESP\" contextControlCode=\"OP\"> <trialInvestigator classCode=\"CRINV\"> <id root=\"2.16.840.1.113883.19\" extension=\"13\"/> <investigatorNamedPerson classCode=\"PSN\" determinerCode=\"INSTANCE\"> <name>MARSHALL, JOHN, MD</name> </investigatorNamedPerson> </trialInvestigator> </responsibleParty> <component typeCode=\"COMP\" contextConductionInd=\"true\"> <subjectAssignment classCode=\"CLNTRL\" moodCode=\"EVN\"> <recordTarget typeCode=\"RCT\" contextControlCode=\"OP\"> <enrolledSubject classCode=\"RESBJ\"> <id root=\"2.16.840.1.113883.19\" extension=\"SMITHJANE1\"/> </enrolledSubject> </recordTarget> <component typeCode=\"COMP\" contextConductionInd=\"true\"> <studyEvent classCode=\"CTTEVENT\" moodCode=\"EVN\"> <code code=\"dummy\" codeSystem=\"2.16.840.1.113883.19\"/> <component typeCode=\"COMP\" contextConductionInd=\"true\"> <accession classCode=\"ACSN\" moodCode=\"EVN\"> <author typeCode=\"AUT\" contextControlCode=\"OP\"> <agent classCode=\"AGNT\"> <representedLaboratory classCode=\"ORG\" determinerCode=\"INSTANCE\"> <id root=\"2.16.840.1.113883.19\" extension=\"1\"/> </representedLaboratory> </agent> </author> <component typeCode=\"COMP\" contextConductionInd=\"true\"> <baseSpecimenDefinition classCode=\"ACT\" moodCode=\"EVN\"> <subject typeCode=\"SBJ\" contextControlCode=\"OP\"> <baseSpecimen classCode=\"SPEC\"> <productOf typeCode=\"PRD\" contextControlCode=\"OP\"> <specimenCollectionProcedure classCode=\"PROC\" moodCode=\"EVN\"> <effectiveTime xsi:type=\"IVL_TS\"> <low value=\"200603311155\"/> </effectiveTime> </specimenCollectionProcedure> </productOf> </baseSpecimen> </subject> <component typeCode=\"COMP\" contextConductionInd=\"true\"> <baseBattery classCode=\"OBS\" moodCode=\"EVN\"> <code code=\"1\" codeSystem=\"2.16.840.1.113883.19\"/> <component typeCode=\"COMP\" contextConductionInd=\"true\"> <baseUnitaryResult classCode=\"OBS\" moodCode=\"EVN\"> <code code=\"BASO_PCT_BLD\" displayName=\"BASO %\" codeSystem=\"2.16.840.1.113883.19\" codeSystemName=\"C3D\"> <originalText representation=\"TXT\" mediaType=\"text/plain\">BASO %</originalText> </code> <statusCode code=\"completed\"/> <value value=\"0.0\" unit=\"%\"/> <performer typeCode=\"PRF\" contextControlCode=\"OP\"> <agent classCode=\"AGNT\"> <representedLaboratory classCode=\"ORG\" determinerCode=\"INSTANCE\"> <id root=\"2.16.840.1.113883.19\" extension=\"1\"/> </representedLaboratory> </agent> </performer> </baseUnitaryResult> </component> </baseBattery> </component> </baseSpecimenDefinition> </component> </accession> </component> </studyEvent> </component> </subjectAssignment> </component> </investigatorAtSite> </component> </trialAtSite> </component> </ClinicalTrial>");
			m.setFormatDefinition("StringDataFormat");
			
			PersistDataRequest pdr = new PersistDataRequest();
			pdr.setCredentials(c);
			pdr.setMessage(m);
			callWebService(pdr);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/* call the web service
	*
	*/
	public static String callWebService(PersistDataRequest persistDataRequest) 
		throws MalformedURLException, Exception{
		
		//create a metadata of the service		
        Service serviceModel = new ObjectServiceFactory().create(CTOMDataPersistenceService.class);        
        System.out.println("callSoapServiceLocal(): got service model." );
   
        //create a proxy for the deployed service
		XFire xfire = XFireFactory.newInstance().getXFire();
        XFireProxyFactory factory = new XFireProxyFactory(xfire);      
        //String serviceUrl = "xfire.local://Banking" ;         
        String serviceUrl = "http://localhost:8080/CTOMDataPersistenceWebService/services/CTOMDataPersistenceService";
        //String serviceUrl = "http://localhost:9080/CTOMDataPersistenceWebService/services/CTOMDataPersistenceService";
       //String serviceUrl =  "http://cbiodev104.nci.nih.gov:59080/CTOMDataPersistenceWebService/services/CTOMDataPersistenceService";
        CTOMDataPersistenceService client = null;
        try {
			client = (CTOMDataPersistenceService) factory.create(serviceModel, serviceUrl);
        } catch (MalformedURLException e) {
        	log.error("WsClient.callWebService(): EXCEPTION: " + e.toString());
        }    
               
        //invoke the service
        Acknowledgement ack = null;
        try { 
        	ack= client.persistData(persistDataRequest);
       } catch (Exception e){
			log.error("WsClient.callWebService(): EXCEPTION: " + e.toString());	               	
			e.printStackTrace();
        }        
		log.debug("WsClient.callWebService(): status=" + ack.getStatus());        		

		System.out.println(ack.getStatus());
		System.out.println(ack.getDescription());
		
		return "";
	}	

	  
}