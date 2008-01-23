package gov.nih.nci.caxchange.client;

import static java.util.concurrent.TimeUnit.SECONDS;
import edu.knu.medinfo.hl7.v2tree.HL7MessageTreeException;
import gov.nih.nci.caadapter.common.util.FileUtil;
import gov.nih.nci.caadapter.common.validation.ValidatorResults;
import gov.nih.nci.caadapter.hl7.transformation.TransformationService;
import gov.nih.nci.caadapter.hl7.transformation.data.XMLElement;
import gov.nih.nci.caadapter.ui.mapping.V2V3.V2Converter;
import gov.nih.nci.cabig.ccts.domain.OrganizationAssignedIdentifierType;
import gov.nih.nci.cabig.ccts.domain.ParticipantType;
import gov.nih.nci.cabig.ccts.domain.Registration;
import gov.nih.nci.cabig.ccts.domain.Study;
import gov.nih.nci.cagrid.caxchange.client.CaXchangeRequestProcessorClient;
import gov.nih.nci.cagrid.caxchange.context.client.CaXchangeResponseServiceClient;
import gov.nih.nci.cagrid.caxchange.context.stubs.GetResponseResponse;
import gov.nih.nci.cagrid.caxchange.context.stubs.types.CaXchangeResponseServiceReference;
import gov.nih.nci.cagrid.labviewer.grid.client.StudyLookupServiceClient;
import gov.nih.nci.caxchange.Message;
import gov.nih.nci.caxchange.MessagePayload;
import gov.nih.nci.caxchange.MessageTypes;
import gov.nih.nci.caxchange.Metadata;
import gov.nih.nci.caxchange.Request;
import gov.nih.nci.caxchange.ResponseMessage;
import gov.nih.nci.caxchange.client.exceptions.FolderDoesNotExistException;
import gov.nih.nci.caxchange.client.exceptions.FolderNotADirectoryException;
import gov.nih.nci.caxchange.client.exceptions.PropertiesFileException;
import gov.nih.nci.caxchange.client.preprocess.CSVP;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.axis.message.MessageElement;
import org.apache.axis.types.URI;
import org.apache.axis.types.URI.MalformedURIException;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class TestCancerCenterClient {

	private final ScheduledExecutorService scheduler = Executors
			.newScheduledThreadPool(1);

	// Property Files

	private static String preProcessorPropertiesFile_str;
	private static String programPropertiesFile = "D:/Development/CancerCenterClient/src/java/main/properties/DefaultProperties.properties";

	// To be set in a property file

	private static String location;

	//private static String WebServiceURL;
	//private static String WorkFlowId;
	//private static String EndPointListSeparatorChar = ",";
	//private static String EndPointsCommaDelimitedString;

	private static String userName;
	private static String userPasswd;

	private static String inProcessFolder;
	private static String processedFolder;
	private static String errorFolder;
	private static String rawFilesFolder;
	private static String rawFilesBackupFolder;

	private static String initialDelay_str;
	private static String pollingInterval_str;
	//private static String pollingTimeUnit_str;

	private static long initialDelay_long;
	private static long pollingInterval_long;
	//private static TimeUnit pollingTimeUnit;

	private static File processedDir;
	private static File inProcessDir;
	private static File errorDir;

	private static File rawFilesDir;
	private static File rawFilesBackupDirectory;
	private static String hubURL;

	// Map File used for mapping CSV file to HL7V3 format by caAdaptor.
	private static String mapFileName;

	// Logging File

	private static Logger logger = Logger
			.getLogger("gov.nih.nci.caxchange.client.CancerCenterClient");

	public static void main(String[] args) {

		String configFilePath = "D:/Development/CancerCenterClient/src/java/main/properties/DefaultProperties.properties";//System.getProperty("cancer.center.client.properties");
		if (configFilePath == null) {
			throw new RuntimeException(
					"Cancer Center Client property is not set as an argument on the java command line.");
		}

		File f = new File(configFilePath);
		if (f == null) {
			throw new RuntimeException(
					"Cancer Center Client Properties File is not available.");
		} else {
			programPropertiesFile = configFilePath;
			f = null;
		}

		logger.debug("Staring Cancer Center Client -- Main Method");
		TestCancerCenterClient client = new TestCancerCenterClient();
		boolean success = client.checkSetup();
		if (success) {
			client.process();
		} else {
			logger
					.fatal("Fatal: Required Resources not found to run Client Application");
			logger.fatal("Aborting Client");
			logger.fatal("Please contact System Admin");
		}

	}

	public void test() {

		String configFilePath = "D:/Development/CancerCenterClient/src/java/main/properties/DefaultProperties.properties";//System.getProperty("cancer.center.client.properties");
		if (configFilePath == null) {
			throw new RuntimeException(
					"Cancer Center Client property is not set as an argument on the java command line.");
		}

		File f = new File(configFilePath);
		if (f == null) {
			throw new RuntimeException(
					"Cancer Center Client Properties File is not available.");
		} else {
			programPropertiesFile = configFilePath;
			f = null;
		}

		logger.debug("Staring Cancer Center Client -- Main Method");
		TestCancerCenterClient client = new TestCancerCenterClient();
		boolean success = client.checkSetup();
		if (success) {
			client.process();
		} else {
			logger
					.fatal("Fatal: Required Resources not found to run Client Application");
			logger.fatal("Aborting Client");
			logger.fatal("Please contact System Admin");
		}

	}

	/**
	 * Checks the setup of all the directories.
	 * @return success
	 */
	public boolean checkSetup() {
		boolean success = false;
		try {
			//Load Properties File
			if (!loadProperties()) {
				throw new PropertiesFileException(
						"Exception processing CancerCenterClient.properties file");
			}

			rawFilesDir = new File(rawFilesFolder);
			rawFilesBackupDirectory = new File(rawFilesBackupFolder);

			inProcessDir = new File(inProcessFolder);
			processedDir = new File(processedFolder);
			errorDir = new File(errorFolder);

			File preProcessorPropertiesFile_file = new File(
					preProcessorPropertiesFile_str);

			if (!preProcessorPropertiesFile_file.exists()) {
				throw new PropertiesFileException(
						"preProcessorPropertiesFile as defined in CancerCenterClient.properties Does not Exist");
			}
			if (!rawFilesDir.exists()) {
				throw new FolderDoesNotExistException(
						"rawFilesFolder as defined in CancerCenterClient.properties  Does Not Exist");
			}
			if (!rawFilesDir.isDirectory()) {
				throw new FolderNotADirectoryException(
						"rawFilesFolder as defined in CancerCenterClient.properties  is not a Folder. It must be a folder");
			}
			if (!rawFilesBackupDirectory.exists()) {
				throw new FolderDoesNotExistException(
						"rawFilesBackupFolder as defined in CancerCenterClient.properties  Does Not Exist");
			}
			if (!rawFilesBackupDirectory.isDirectory()) {
				throw new FolderNotADirectoryException(
						"rawFilesBackupFolder as defined in CancerCenterClient.properties  is not a Folder. It must be a folder");
			}
			if (!inProcessDir.exists()) {
				throw new FolderDoesNotExistException(
						"inProcessFolder as defined in CancerCenterClient.properties  Does Not Exist");
			}
			if (!inProcessDir.isDirectory()) {
				throw new FolderNotADirectoryException(
						"inProcessFolder as defined in CancerCenterClient.properties  is not a Folder. It must be a folder");
			}
			if (!processedDir.exists()) {
				throw new FolderDoesNotExistException(
						"processedFolder as defined in CancerCenterClient.properties  Does Not Exist");
			}
			if (!processedDir.isDirectory()) {
				throw new FolderNotADirectoryException(
						"processedFolder as defined in CancerCenterClient.properties  is not a Folder. It must be a folder");
			}
			if (!errorDir.exists()) {
				throw new FolderDoesNotExistException(
						"errorFolder as defined in CancerCenterClient.properties  Does Not Exist");
			}
			if (!errorDir.isDirectory()) {
				throw new FolderNotADirectoryException(
						"errorFolder as defined in CancerCenterClient.properties is not a Folder. It must be a folder");
			}
			success = true;
		} catch (FolderDoesNotExistException e) {
			logger.error(e.getErrorMessage());
			success = false;
		} catch (FolderNotADirectoryException e) {
			logger.error(e.getErrorMessage());
			success = false;
		} catch (PropertiesFileException e) {
			logger.fatal(e.getErrorMessage());
			success = false;
		}
		return success;
	}

	
	/**
	 * 
	 */
	public void process() {

		final Runnable fileCheck = new Runnable() {

			public void run() {
				try {
					logger.debug("Poll the File");
					if (!checkPreProcessedFolder()) {
						logger.error("Error Preprocessing Files.. Aborting");
						throw new Exception(
								"Error Preprocessing Files.. Aborting");
					} else {
						//RoutingAndWorkFlowServiceClient();
						File inProcessDir = new File(inProcessFolder);
						// Delete Zero Byte Files Generated by Preprocessor in case of exceptions.
						File[] inProcessfileList = inProcessDir.listFiles();
						for (int i = 0; i < inProcessfileList.length; i++) {
							if (inProcessfileList[i].length() == 0)
								inProcessfileList[i].delete();
						}
						File[] fileList = inProcessDir.listFiles();
						GetBytesfromFile gbf = new GetBytesfromFile();
						for (int i = 0; i < fileList.length; i++) {
							String HL7V3 = callTocaAdapterAPI(fileList[i]
									.getAbsolutePath());
							callTheGridService(fileList, i, HL7V3);
						}
					}
				} catch (Exception e) {
					logger.fatal("Fatal Error in Client.. Aborting");
				}
			}

		};
		final ScheduledFuture<?> fileCheckHandle = scheduler
				.scheduleAtFixedRate(fileCheck, initialDelay_long,
						pollingInterval_long, SECONDS);
	}

	/**
	 * Converts the raw csv file to caAdapter compatible csv file and moves it
	 * to the InProcess folder
	 * @return retResult
	 */
	public boolean checkPreProcessedFolder() {
		logger.debug("In Check Pre Processed Folder");
		boolean retResult = true;
		try {
			File[] fileList = rawFilesDir.listFiles();
			// Get Current Date and Time for Stamping the file
			Date dt = new Date();
			long currentTime = dt.getTime();
			StringBuffer sbuf = new StringBuffer();
			StringBuffer outbuf = new StringBuffer();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");

			sbuf = sdf.format(dt, outbuf, new FieldPosition(0));

			logger.debug("Current Date Time : " + sbuf);

			for (int i = 0; i < fileList.length; i++) {
				//logger.debug("Pre Processing File : " + fileList[i].toString() );
				String fileName = fileList[i].getName().toString();
				//logger.info("File Name : " + fileName );

				String fileNameLocationDateTimeStamp = location + "_" + sbuf
						+ "_" + fileName;
				String outFile = inProcessFolder
						+ fileNameLocationDateTimeStamp;
				//logger.info("Out File : " + outFile);

				//logger.info("Valid File Name : " + fileName);
				try {
					logger.debug("Starting CSV Parser");
					CSVP fileOut = new CSVP(fileList[i].toString(), outFile,
							preProcessorPropertiesFile_str);

					//logger.info("Backup Dir : " + rawFilesBackupDirectory);
					//logger.info("Backup folder : " + rawFilesBackupFolder);
					//logger.info("fileNameLocationDateTimeStamp : " + fileNameLocationDateTimeStamp);
					//logger.info("*****");

					boolean success = fileList[i].renameTo(new File(
							rawFilesBackupDirectory,
							fileNameLocationDateTimeStamp));

					//logger.info("File Moved to Backup folder");
					//logger.info("***********");
					if (!success) {
						logger.error(fileList[i].toString() + "Renamed to : "
								+ fileNameLocationDateTimeStamp
								+ " Was not moved to the backup folder");
						retResult = false;
					} else {
						logger.debug("File Moved to Backup folder");
						retResult = true;
					}

				} catch (Exception e) {
					retResult = false;
					logger.error("Error PreProcessing File "
							+ fileList[i].toString());
					logger.error(e.getMessage());
					logger.info("Erro Dir : " + errorDir);
					logger.info("Error folder : " + errorFolder);
					logger.info("fileNameLocationDateTimeStamp : "
							+ fileNameLocationDateTimeStamp);
					logger.info("File : " + fileList[i]);
					logger.info("*****");
					boolean movesuccess = fileList[i].renameTo(new File(
							errorDir, fileNameLocationDateTimeStamp));
					//logger.info("****");

					if (!movesuccess) {
						logger.error(fileList[i].toString() + "Renamed to : "
								+ errorDir + fileNameLocationDateTimeStamp
								+ " Was not moved to the error folder");
						logger.info("*****");
					} else {
						logger.error(fileList[i].toString() + "Renamed to : "
								+ errorDir + fileNameLocationDateTimeStamp
								+ " Was moved to the error folder");
					}

				} // End of Catch

			} // End of For

		} catch (Exception e) {
			logger.fatal("Error Accesing Files in file System");
			retResult = false;
		}
		return retResult;

	}

	/**
	 * Calls the caAdapter API to convert the .csv file to HL7V3 message.
	 */
	public String callTocaAdapterAPI(String filePath) throws Exception {
		//Transformation Service
		TransformationService transformationService = new TransformationService(
				mapFileName, filePath);
		List<XMLElement> xmlElements;
		String hl7MessageXml = null;
		try {
			xmlElements = transformationService.process();
			if (xmlElements == null) {
				//if failed in processing the source data
				//file,it returns error messages  
				ValidatorResults rs = transformationService
						.getValidatorResults();
				String errorMsg = rs.getAllMessages().toString();
			} else {
				//return a  list of generated messages 

				for (XMLElement rootElement : xmlElements) {
					hl7MessageXml = rootElement.toXML().toString();
				}
				//System.out.println(hl7MessageXml);
				logger.info("The HL7V3 Message" + hl7MessageXml);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger
					.error("Error converting .csv to HL7V3", e
							.fillInStackTrace());
		}
		return hl7MessageXml;
	}

	/**
	 * Calls the caAdapter API to convert the HL7V2 message file to HL7V3 message.
	 * This is a 2 step process
	 * 1.Convert HL7V2 to .csv file
	 * 2.Convert .csv file to HL7V3 message - call the method 
	 * 	 callTocaAdapterAPI() to perform the same.
	 * 
	 */
	public void callTocaAdapterAPIV2toV3() throws Exception {
		String hl7FileName = "D:/Development/CancerCenterClient/src/java/main/gov/nih/nci/caxchange/client/HL7V2.xml";
		String csvFileName = "D:/Development/CancerCenterClient/src/java/main/gov/nih/nci/caxchange/client/HL7V2.cvs";
		String scsFileName = "D:/Development/CancerCenterClient/src/java/main/gov/nih/nci/caxchange/client/HL7V2.scs";
		try {
			V2Converter con = new V2Converter(FileUtil.getV2DataDirPath());
			con.convertV2ToCSV(hl7FileName, csvFileName, scsFileName);
			if (!con.isCSVValid()) {
				List<String> errList = con.getValidationMessages();
			}
		} catch (HL7MessageTreeException e) {
			// TODO Auto-generated catch block
			logger.error("Error converting HL7V2 to HL7V3", e
					.fillInStackTrace());
		}
		//callTocaAdapterAPI();

	}

	/**
	 * Calls the Grid Service hub to load the HL7V3 message. 
	 */
	public final void callTheGridService(File[] fileList, int i, String HL7V3)
			throws Exception {
		try {
			boolean filemoved = false;

			//String url = "http://cbvapp-d1017.nci.nih.gov:18080/wsrf/services/cagrid/CaXchangeRequestProcessor";//(String)props.getProperty("url");
			//String HL7V3XMLFile="<?xml version=\"1.0\" encoding=\"UTF-8\"?> <ClinicalTrial xmlns=\"urn:hl7-org:v3\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" classCode=\"CLNTRL\" moodCode=\"EVN\"> <id root=\"2.16.840.1.113883.19\" extension=\"6230\" assigningAuthorityName=\"NCI\"/> <title representation=\"TXT\" mediaType=\"text/plain\">RANDOMIZED PHASE I/II STUDY OF VACCINIA-CEA(6D)-TRICOM AND FOWLPOX-(6D)-TRICOM WITH GM-CSF IN COMBINATION WITH DOCETAXEL IN PATIENTS WITH CEA-BEARING CANCERS</title> <component typeCode=\"COMP\" contextConductionInd=\"true\"> <trialAtSite classCode=\"CLNTRL\" moodCode=\"EVN\"> <location typeCode=\"LOC\" contextControlCode=\"OP\"> <trialSite classCode=\"SDLOC\"> <id root=\"2.16.840.1.113883.19\" extension=\"DC005\"/> </trialSite> </location> <component typeCode=\"COMP\" contextConductionInd=\"true\"> <investigatorAtSite classCode=\"CLNTRL\" moodCode=\"EVN\"> <responsibleParty typeCode=\"RESP\" contextControlCode=\"OP\"> <trialInvestigator classCode=\"CRINV\"> <id root=\"2.16.840.1.113883.19\" extension=\"13\"/> <investigatorNamedPerson classCode=\"PSN\" determinerCode=\"INSTANCE\"> <name>MARSHALL, JOHN, MD</name> </investigatorNamedPerson> </trialInvestigator> </responsibleParty> <component typeCode=\"COMP\" contextConductionInd=\"true\"> <subjectAssignment classCode=\"CLNTRL\" moodCode=\"EVN\"> <recordTarget typeCode=\"RCT\" contextControlCode=\"OP\"> <enrolledSubject classCode=\"RESBJ\"> <id root=\"2.16.840.1.113883.19\" extension=\"SMITHJANE1\"/> </enrolledSubject> </recordTarget> <component typeCode=\"COMP\" contextConductionInd=\"true\"> <studyEvent classCode=\"CTTEVENT\" moodCode=\"EVN\"> <code code=\"dummy\" codeSystem=\"2.16.840.1.113883.19\"/> <component typeCode=\"COMP\" contextConductionInd=\"true\"> <accession classCode=\"ACSN\" moodCode=\"EVN\"> <author typeCode=\"AUT\" contextControlCode=\"OP\"> <agent classCode=\"AGNT\"> <representedLaboratory classCode=\"ORG\" determinerCode=\"INSTANCE\"> <id root=\"2.16.840.1.113883.19\" extension=\"1\"/> </representedLaboratory> </agent> </author> <component typeCode=\"COMP\" contextConductionInd=\"true\"> <baseSpecimenDefinition classCode=\"ACT\" moodCode=\"EVN\"> <subject typeCode=\"SBJ\" contextControlCode=\"OP\"> <baseSpecimen classCode=\"SPEC\"> <productOf typeCode=\"PRD\" contextControlCode=\"OP\"> <specimenCollectionProcedure classCode=\"PROC\" moodCode=\"EVN\"> <effectiveTime xsi:type=\"IVL_TS\"> <low value=\"200603311155\"/> </effectiveTime> </specimenCollectionProcedure> </productOf> </baseSpecimen> </subject> <component typeCode=\"COMP\" contextConductionInd=\"true\"> <baseBattery classCode=\"OBS\" moodCode=\"EVN\"> <code code=\"1\" codeSystem=\"2.16.840.1.113883.19\"/> <component typeCode=\"COMP\" contextConductionInd=\"true\"> <baseUnitaryResult classCode=\"OBS\" moodCode=\"EVN\"> <code code=\"BASO_PCT_BLD\" displayName=\"BASO %\" codeSystem=\"2.16.840.1.113883.19\" codeSystemName=\"C3D\"> <originalText representation=\"TXT\" mediaType=\"text/plain\">BASO %</originalText> </code> <statusCode code=\"completed\"/> <value value=\"0.0\" unit=\"%\"/> <performer typeCode=\"PRF\" contextControlCode=\"OP\"> <agent classCode=\"AGNT\"> <representedLaboratory classCode=\"ORG\" determinerCode=\"INSTANCE\"> <id root=\"2.16.840.1.113883.19\" extension=\"1\"/> </representedLaboratory> </agent> </performer> </baseUnitaryResult> </component> </baseBattery> </component> </baseSpecimenDefinition> </component> </accession> </component> </studyEvent> </component> </subjectAssignment> </component> </investigatorAtSite> </component> </trialAtSite> </component> </ClinicalTrial>";
			CaXchangeRequestProcessorClient client = new CaXchangeRequestProcessorClient(
					hubURL);
			//QName lab = new QName("LoadLabsRequest");

			// Create the caxchange message
			Message requestMessage = new Message();
			Metadata metadata = new Metadata();
			metadata.setExternalIdentifier("CTODS");
			metadata.setMessageType(MessageTypes.CT_LAB_DATA);

			//Credentials creds = new Credentials();// Optional Credentials - for testing purposes comment out the creds.
			//creds.setUserName(userName);
			//creds.setPassword(userPasswd);
			// metadata.setCredentials(creds);

			requestMessage.setMetadata(metadata);
			Request caxchangeRequest = new Request();
			requestMessage.setRequest(caxchangeRequest);
			MessagePayload messagePayload = new MessagePayload();
			URI uri = new URI();
			uri.setPath("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain");
			messagePayload.setXmlSchemaDefinition(uri);

			//Create the HL7V3 file
			 String[] strFile = fileList[i].getName().split("\\.");
			 String strFileName=strFile[0]+"-hl7v3.xml";
			 File hl7v3XML = new File(rawFilesBackupFolder+strFileName);
			 FileWriter fw = new FileWriter(hl7v3XML);
			 fw.write(HL7V3);
			 fw.flush();
			 fw.close();
			 
			 //call the method callToStudyLookupService
			 Study study =callStudyLookupService(hl7v3XML);
			 
			 //call to change the xml attribute values
			 String changedHL7V3 = changeXMLAttvalues(HL7V3,study);
			 
			//Test code
			Document document;
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			ByteArrayInputStream stream = new ByteArrayInputStream(changedHL7V3
					.getBytes());
			DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.parse(stream);
			Element root = document.getDocumentElement();

			MessageElement messageElement = new MessageElement(root);

			messagePayload.set_any(new MessageElement[] { messageElement });
			requestMessage.getRequest().setBusinessMessagePayload(
					messagePayload);

			CaXchangeResponseServiceReference crsr = client
					.processRequestAsynchronously(requestMessage);

			CaXchangeResponseServiceClient responseService = new CaXchangeResponseServiceClient(
					crsr.getEndpointReference());
			boolean gotResponse = false;
			boolean hl7v3move =false;
			GetResponseResponse getResponse = null;

			int responseCount = 0;
			ResponseMessage responseMessage = null;
			while (!gotResponse) {
				try {
					responseMessage = responseService.getResponse();

					if (responseMessage.getResponse().getResponseStatus()
							.toString().equalsIgnoreCase("Success")) {
						logger
								.info("Response:Success -Moving File "
										+ fileList[i].getName() + " To "
										+ processedDir);
						filemoved = fileList[i].renameTo(new File(processedDir,
								fileList[i].getName()));
					    hl7v3move = hl7v3XML.renameTo(new File(processedDir,
								hl7v3XML.getName())); 
						gotResponse = true;
					} else if (responseMessage.getResponse()
							.getResponseStatus().toString().equalsIgnoreCase(
									"Failure")) {
						logger
								.info("Response: Failure -Moving File "
										+ fileList[i].getName() + " To "
										+ processedDir);
						filemoved = fileList[i].renameTo(new File(errorDir,
								fileList[i].getName()));
						 hl7v3move = hl7v3XML.renameTo(new File(errorDir,
									hl7v3XML.getName()));
						gotResponse = true;
					} else {
						logger
								.info("Error -Moving File "
										+ fileList[i].getName() + " To "
										+ processedDir);
						filemoved = fileList[i].renameTo(new File(errorDir,
								fileList[i].getName()));
						 hl7v3move = hl7v3XML.renameTo(new File(errorDir,
									hl7v3XML.getName()));

					}
					if (!filemoved) {
						logger.info("Error Moving File ");
					}
				} catch (Exception e) {
					logger.info("No response from caxchange", e);
					responseCount++;
					if (responseCount > 50) {
						logger.error("Never got a response from caxchange hub");
						throw new Exception(
								"Error sending Lab Data to Grid Service");
					}
					Thread.sleep(1000);
				}
			}

			if (responseMessage != null) {
				logger.info("caXchange response was "
						+ responseMessage.getResponse().getResponseStatus()
								.toString());
			}
		} catch (MalformedURIException e) {
			logger.error("MalformedURIException" + e.getLocalizedMessage());
		} catch (RemoteException e) {

			logger.error("RemoteException" + e.getLocalizedMessage());
		} catch (ParserConfigurationException e) {

			logger.error("ParserConfigurationException"
					+ e.getLocalizedMessage());
		} catch (SAXException e) {

			logger.error("SAXException" + e.getLocalizedMessage());
		} catch (IOException e) {

			logger.error("IOException" + e.getLocalizedMessage());
		} catch (InterruptedException e) {

			logger.error("InterruptedException" + e.getLocalizedMessage());
		} catch (Exception e) {

			logger.error("Exception" + e.getLocalizedMessage());
		}

	}

		/**
		 * Loads the properties from the properties file
		 * @return
		 */
		private static boolean loadProperties() {
		boolean isSuccess = false;
		try {
			FileInputStream fis = new FileInputStream(new File(
					programPropertiesFile));

			Properties props = new Properties();
			//Read in the stored properties
			props.load(fis);
			location = props.getProperty("Location");
			preProcessorPropertiesFile_str = props
					.getProperty("preProcessorPropertiesFile");
			userName = props.getProperty("userName");
			userPasswd = props.getProperty("userPasswd");
			rawFilesFolder = props.getProperty("rawFilesFolder");
			rawFilesBackupFolder = props.getProperty("rawFilesBackupFolder");
			inProcessFolder = props.getProperty("inProcessFolder");
			processedFolder = props.getProperty("processedFolder");
			errorFolder = props.getProperty("errorFolder");
			mapFileName = props.getProperty("mapFileName");
			initialDelay_str = props.getProperty("initialDelayInSeconds");
			pollingInterval_str = props.getProperty("pollingDelayInSeconds");
			hubURL = props.getProperty("HubURL");
			logger.info("preProcessorPropertiesFile : "
					+ preProcessorPropertiesFile_str);
			logger.info("Location : " + location);
			//logger.info("WebServiceURL : " + WebServiceURL);
			logger.info("rawFilesFolder : " + rawFilesFolder);
			logger.info("rawFilesBackupFolder : " + rawFilesBackupFolder);
			logger.info("inProcessFolder : " + inProcessFolder);
			logger.info("ProcessedFolder : " + processedFolder);
			logger.info("ErrorFolder : " + errorFolder);
			logger.info("initialDelay_str : " + initialDelay_str);
			logger.info("pollingInterval_str : " + pollingInterval_str);
			logger.info("hubURL : " + hubURL);
			initialDelay_long = new Long(initialDelay_str);
			pollingInterval_long = new Long(pollingInterval_str);
			logger.info("initialDelay_long : " + initialDelay_long);
			logger.info("pollingInterval_long : " + pollingInterval_long);

			isSuccess = true;
		} catch (Exception e) {
			logger.error("Exception processing Cancer Center Properties File");
			isSuccess = false;
		}
		return isSuccess;
	}
	
	/**
	 * Calls the StudyLookup Service to lookup study information for a 
	 * participant in the HL7V3 message.
	 * @param HL7V3
	 */
	private Study callStudyLookupService(File HL7V3)
	{
		Registration registration =new Registration();
		Study study =null;
		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document document = builder.parse(HL7V3);
			
			XPath xpath = XPathFactory.newInstance().newXPath();
			String expression1 = "//enrolledSubject/id/@root";
			String expression2 = "//enrolledSubject/id/@extension";
			
				String root =(String) xpath.evaluate(expression1, document, XPathConstants.STRING);
				String extension = (String)xpath.evaluate(expression2, document, XPathConstants.STRING);
				System.out.println("Root"+root);
				System.out.println("Extension"+extension);
				//create the registration object
				ParticipantType participant = new ParticipantType();
				OrganizationAssignedIdentifierType identifier = new OrganizationAssignedIdentifierType();
				identifier.setGridId(root);
				identifier.setValue(extension);
				OrganizationAssignedIdentifierType[] identifiers ={identifier};
				participant.setIdentifier(identifiers);
				registration.setParticipant(participant);
				//Call the StudyLookupService
				// Create the client
				StudyLookupServiceClient client = new StudyLookupServiceClient("http://localhost:8080/wsrf/services/cagrid/StudyLookupService");
				// Call the service
				study = client.getStudy(registration);
		
		}catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return study;
	}
	
	/**
	 * Replaces the values of the attributes in the xml with the values received 
	 * from Grid Service call. 
	 * @param xml
	 * @return xlm8 modified xml as String
	 */
	private String changeXMLAttvalues(String xml,Study study)
	{
		String xml1 = xml.replace("PROTOCOL ID ASSIGN AUTH", "NCI");
		String xml2 = xml1.replace("PROTOCOL ID ROOT", study.getIdentifier(0).getGridId());
		String xml3 = xml2.replace("PROTOCOL ID EXT", study.getIdentifier(0).getValue());
		String xml4 = xml3.replace("PROTOCOL TITLE", study.getLongTitleText());
		String xml5 = xml4.replace("PI ID ASSIGN AUTH", "PlaceHolder");
		String xml6 = xml5.replace("PI ID ROOT", "2.16.840.1.113883.19");
		String xml7 = xml6.replace("PI ID EXT", "PlaceHolder");
		String xml8 = xml7.replace("PI NAME", "PlaceHolder");
		return xml8;
	}
} // End of Class

