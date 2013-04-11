/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */

package gov.nih.nci.caxchange.client;

import static java.util.concurrent.TimeUnit.SECONDS;
import gov.nih.nci.caadapter.common.validation.ValidatorResults;
import gov.nih.nci.caadapter.hl7.transformation.TransformationService;
import gov.nih.nci.caadapter.hl7.transformation.data.XMLElement;
import gov.nih.nci.cabig.ccts.domain.OrganizationAssignedIdentifierType;
import gov.nih.nci.cabig.ccts.domain.ParticipantType;
import gov.nih.nci.cabig.ccts.domain.Registration;
import gov.nih.nci.cabig.ccts.domain.Study;
import gov.nih.nci.cagrid.labviewer.grid.client.StudyServiceClient;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import org.apache.log4j.Logger;
import org.globus.gsi.GlobusCredential;
import org.w3c.dom.Document;

/**
 * HL7V3Transformation class performs the transformation of .CSV file to a HL7V3
 * message by invoking the caAdapter API. It performs the StudyLookup for the
 * participant in the transformed HL7V3 message and populates the study details-
 * by calling into StudyLookup grid service. It further invokes the grid service
 * to persist the HL7V3 message into CTODS database.
 * 
 * @author asharma
 * 
 */
public class HL7V3Transformation {

	CancerCenterClient cancerCenterClient;
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	private List<String> HL7V3Msgs = Collections.synchronizedList(new ArrayList<String>());
	static Logger logger = Logger.getLogger("client");
	public HL7V3Transformation(CancerCenterClient client) {
		cancerCenterClient = client;
	}

	/**
	 * Starts a thread to poll the directory for .CSV files. It invokes the
	 * caAdapter API to convert a .CSV file to HL7V3. Invokes the grid service
	 * to persist the generated HL7V3 message.
	 */
	public void process(ArrayList<ScheduledExecutorService> threadList) {
		threadList.add(scheduler);
		final Runnable fileCheck = new Runnable() {
			public void run() {
				try {
					logger.debug(Messages.getString("Polling for csv files"));
					if (!cancerCenterClient.checkPreProcessedFolder()) {
						logger.error(Messages.getString("CancerCenterClient.20"));
						throw new Exception(Messages.getString("CancerCenterClient.21"));
					} else {
						File inProcessDir = new File(cancerCenterClient.getInProcessFolder());
						File[] inProcessfileList = inProcessDir.listFiles();
						for (int i = 0; i < inProcessfileList.length; i++) {
							if (inProcessfileList[i].length() == 0)
								inProcessfileList[i].delete();
						}
						File[] fileList = inProcessDir.listFiles();
						boolean fileMoved = false;
						for (int i = 0; i < fileList.length; i++) {
							if (fileList[i].getName().indexOf("V2TOV3") == -1) {
								HL7V3Msgs = invokecaAdapterAPI(fileList[i].getAbsolutePath(), 
								        cancerCenterClient.getMapFileName());
							} else {
								HL7V3Msgs = invokecaAdapterAPI(fileList[i].getAbsolutePath(), cancerCenterClient
										.getHl7v2mapFileName().replace('/','\\'));
							}
							if (HL7V3Msgs.isEmpty()) {
								fileMoved = fileList[i].renameTo(new File(
										cancerCenterClient.getErrorDir(),fileList[i].getName()));
							} else {
								String fileName = fileList[i].getName();
								if(fileList[i].getName().indexOf("V2TOV3") != -1){
								    fileMoved = fileList[i].renameTo(new File(
										cancerCenterClient.getRawFilesBackupDirectory(),fileName));
								    logger.debug("File Moved " +fileMoved+":" +fileName );
								}else {
									fileMoved=true;
								}
								setUpToInvokeGrid(fileName);
								if(fileList[i].getName().indexOf("V2TOV3") == -1){
									fileList[i].delete();
								}	
							}
					}
					if(!fileMoved && fileList.length > 0)
							 logger.error("Error moving the .CSV file InProcessfolder to backup folder");
				 }							
			  } catch (Exception e) {
				logger.fatal(Messages.getString("CancerCenterClient.22"));
			  }
			}
		};
		final ScheduledFuture<?> fileCheckHandle = scheduler
				.scheduleAtFixedRate(fileCheck, cancerCenterClient
						.getInitialDelay_long(), cancerCenterClient.getPollingInterval_long(), SECONDS);
	}

	/**
	 * @param fileName
	 */
	private void setUpToInvokeGrid(String fileName) {
		int counter = 1;
		try {
		    GlobusCredential gb = cancerCenterClient.getGlobusCredential();
		    for (String HL7V3 : HL7V3Msgs) {
				logger.debug("Processing row ... "+counter);
		        File hl7v3XML = createHL7V3File(fileName, HL7V3, counter);
				Study study = invokeStudyLookupService(new ByteArrayInputStream(HL7V3.getBytes()),gb);
				if (study == null) {
					logger.error(Messages.getString("CancerCenterClient.61"));
					if (hl7v3XML != null) {
						 hl7v3XML.renameTo(new File(cancerCenterClient.getErrorDir(), hl7v3XML.getName()));
					}
					else{
						logger.error(Messages.getString("CancerCenterClient.62"));
					}
				} 
				else {
				    String changedHL7V3 = changeXMLAttvalues(HL7V3 , study);
					FileWriter changedfw = new FileWriter(hl7v3XML, false);
					changedfw.write(changedHL7V3);
					changedfw.flush();
					changedfw.close();
				    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				    DocumentBuilder db =dbf.newDocumentBuilder();
				    ByteArrayInputStream stream = new ByteArrayInputStream(changedHL7V3.getBytes());
				    Document payload = db.parse(stream);
					InvokeGridService invokeGridService = new InvokeGridService(cancerCenterClient);    
					invokeGridService.invokeGridService(payload,hl7v3XML); 
				}
				counter++;
		    }
		}catch (Exception e) {
			logger.fatal(e);
		}catch (Throwable e) {
            logger.fatal(e);
        }
	}
	
	/**
	 * Invokes the caAdapter API to convert the .csv file to HL7V3 message.
	 * @param filePath
	 * @param mapFile
	 * @return hl7MessageXml
	 * @throws Exception
	 */
	public ArrayList<String> invokecaAdapterAPI(String filePath, String mapFile) throws Exception {
		// Transformation Service
		TransformationService transformationService = new TransformationService(mapFile, filePath);
		List<XMLElement> xmlElements = null;
		ArrayList<String> hl7MessageXml = new ArrayList<String>();
		try {
			logger.debug(Messages.getString("CancerCenterClient.45"));
			xmlElements = transformationService.process();
			if (xmlElements == null) {
				ValidatorResults rs = transformationService.getValidatorResults();
				String errorMsg = rs.getAllMessages().toString();
			} else {
				// return a list of generated messages
				for (XMLElement rootElement : xmlElements) {
					hl7MessageXml.add(rootElement.toXML().toString());
				}
				logger.debug(Messages.getString("CancerCenterClient.46")+ hl7MessageXml);
			}
		} catch (Exception e) {
			logger.error(Messages.getString("CancerCenterClient.47"), e.fillInStackTrace());
		}
		return hl7MessageXml;
	}

	/**
	 * Creates a file for the HL7V3 message.
	 * @param fileName
	 * @param HL7V3
	 * @return hl7v3XML
	 */
	private File createHL7V3File(String fileName, String HL7V3, int index) {
		File hl7v3XML = null;
		try {
			String[] strFile = fileName.split("\\.");
			String strFileName = strFile[0] + "-" + index + "-hl7v3.xml";
			hl7v3XML = new File(cancerCenterClient.getRawFilesBackupFolder() + strFileName);
			FileWriter fw = new FileWriter(hl7v3XML, false);
			fw.write(HL7V3);
			fw.flush();
			fw.close();
		} catch (IOException e) {
			logger.error("IOException" + e);
		}
		return hl7v3XML;
	}


	/**
	 * Invokes the StudyLookup Service to lookup study information for a
	 * participant in the HL7V3 message.
	 * 
	 * @param HL7V3
	 * @return study
	 */
	private Study invokeStudyLookupService(ByteArrayInputStream HL7V3, GlobusCredential gb) {
        Study study = null;         
        try {
            Registration registration = new Registration();
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = builder.parse(HL7V3);
    		XPath xpath = XPathFactory.newInstance().newXPath();
    		String expression1 = "//enrolledSubject/id/@root";
    		String expression2 = "//enrolledSubject/id/@extension";
    		String root = (String) xpath.evaluate(expression1, document,XPathConstants.STRING);
    		String extension = (String) xpath.evaluate(expression2, document,XPathConstants.STRING);
    		logger.debug("Root" + root);
    		logger.debug("Extension" + extension);
    		ParticipantType participant = new ParticipantType();
    		OrganizationAssignedIdentifierType identifier = new OrganizationAssignedIdentifierType();
    		participant.setGridId(root);
    		identifier.setValue(extension);
    		OrganizationAssignedIdentifierType[] identifiers = { identifier };
    		participant.setIdentifier(identifiers);
    		registration.setParticipant(participant);
    		StudyServiceClient client =  new StudyServiceClient(cancerCenterClient.getStudyLookupServiceURL(), gb);
   		    study = client.getStudy(registration);
		} catch (Exception e) {
		    logger.error(" Error while retrieving study ", e);
		}
		return study;

	}

	/**
	 * Replaces the values of the attributes in the XML with the values in the
	 * Study received from Grid Service call.
	 * @param xml
	 * @param study
	 * @return xlm8 modified XML as String
	 */
	private String changeXMLAttvalues(String xml, Study study) {
		String xml1 = xml.replace("PROTOCOL ID ASSIGN AUTH", "NCI");
		xml1 = xml1.replace("PROTOCOL ID ROOT", study.getIdentifier(0).getGridId());
		xml1 = xml1.replace("PROTOCOL ID EXT", study.getIdentifier(0).getValue());
		xml1 = xml1.replace("PROTOCOL TITLE", study.getLongTitleText());
		xml1 = xml1.replace("PI ID ASSIGN AUTH", "PlaceHolder");
		xml1 = xml1.replace("PI ID ROOT", "2.16.840.1.113883.19");
		xml1 = xml1.replace("PI ID EXT", "PlaceHolder");
		xml1 = xml1.replace("PI NAME", "PlaceHolder");
		return xml1;
	}
}
