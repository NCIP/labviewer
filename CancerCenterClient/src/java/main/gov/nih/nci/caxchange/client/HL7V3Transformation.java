package gov.nih.nci.caxchange.client;

import static java.util.concurrent.TimeUnit.SECONDS;
import gov.nih.nci.caadapter.common.validation.ValidatorResults;
import gov.nih.nci.caadapter.hl7.transformation.TransformationService;
import gov.nih.nci.caadapter.hl7.transformation.data.XMLElement;
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
import gov.nih.nci.ccts.grid.OrganizationAssignedIdentifierType;
import gov.nih.nci.ccts.grid.ParticipantType;
import gov.nih.nci.ccts.grid.Registration;
import gov.nih.nci.ccts.grid.Study;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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

	private CancerCenterClient cancerCenterClient;

	private final ScheduledExecutorService scheduler = Executors
			.newScheduledThreadPool(1);

	private List<String> HL7V3Msgs = Collections
			.synchronizedList(new ArrayList<String>());

	// Logging File
	private static Logger logger = Logger
			.getLogger("gov.nih.nci.caxchange.client.HL7V3Transformation");

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
					logger.debug(Messages.getString("CancerCenterClient.128"));
					if (!cancerCenterClient.checkPreProcessedFolder()) {
						logger.error(Messages
								.getString("CancerCenterClient.20"));
						throw new Exception(Messages
								.getString("CancerCenterClient.21"));
					} else {
						File inProcessDir = new File(cancerCenterClient
								.getInProcessFolder());
						// Delete Zero Byte Files Generated by Preprocessor in
						// case of exceptions.
						File[] inProcessfileList = inProcessDir.listFiles();
						for (int i = 0; i < inProcessfileList.length; i++) {
							if (inProcessfileList[i].length() == 0)
								inProcessfileList[i].delete();
						}
						File[] fileList = inProcessDir.listFiles();
						boolean fileMoved = false;
						for (int i = 0; i < fileList.length; i++) {

							if (fileList[i].getName().indexOf("V2TOV3") == -1) {
								// invokes the caAdapter API to convert a .csv
								// file
								// to HL7V3.
								HL7V3Msgs = invokecaAdapterAPI(fileList[i]
										.getAbsolutePath(), cancerCenterClient
										.getMapFileName());
							} else {
								// invokes the caAdapter API to convert a V2
								// generated.csv file
								// to HL7V3.
								HL7V3Msgs = invokecaAdapterAPI(fileList[i]
										.getAbsolutePath(), cancerCenterClient
										.getHl7v2mapFileName().replace('/',
												'\\'));
							}
							if (HL7V3Msgs.isEmpty()) {
								fileMoved = fileList[i].renameTo(new File(
										cancerCenterClient.getErrorDir(),
										fileList[i].getName()));
							} else {
								String fileName = fileList[i].getName();
								fileMoved = fileList[i].renameTo(new File(
										cancerCenterClient.getRawFilesBackupDirectory(),
										fileList[i].getName()));
								if(!fileMoved)
									 logger.error("Error moving the .CSV file to backup folder");
								//invoke the grid service setup
								setUpToInvokeGrid(fileName);
							}
							
						}
						
					}
				} catch (Exception e) {
					logger.fatal(Messages.getString("CancerCenterClient.22"));
				}
			}

		};
		final ScheduledFuture<?> fileCheckHandle = scheduler
				.scheduleAtFixedRate(fileCheck, cancerCenterClient
						.getInitialDelay_long(), cancerCenterClient
						.getPollingInterval_long(), SECONDS);
	}

	/**
	 * @param fileName
	 */
	private void setUpToInvokeGrid(String fileName) {
		// invokes the grid service to persist the HL7V3
		// message.
		try {
			for (String HL7V3 : HL7V3Msgs) {
					invokeGridService(fileName, HL7V3, HL7V3Msgs.indexOf(HL7V3));
			}
		} catch (Exception e) {
			logger.fatal(Messages.getString("CancerCenterClient.22"));
		}

	}

	/**
	 * Invokes the caAdapter API to convert the .csv file to HL7V3 message.
	 * 
	 * @param filePath
	 * @param mapFile
	 * @return hl7MessageXml
	 * @throws Exception
	 */
	public ArrayList<String> invokecaAdapterAPI(String filePath, String mapFile)
			throws Exception {
		// Transformation Service
		TransformationService transformationService = new TransformationService(
				mapFile, filePath);
		List<XMLElement> xmlElements;
		ArrayList<String> hl7MessageXml = new ArrayList<String>();
		try {
			logger.info(Messages.getString("CancerCenterClient.45"));
			xmlElements = transformationService.process();
			if (xmlElements == null) {
				// if failed in processing the source data
				// file,it returns error messages
				ValidatorResults rs = transformationService
						.getValidatorResults();
				String errorMsg = rs.getAllMessages().toString();
			} else {
				// return a list of generated messages

				for (XMLElement rootElement : xmlElements) {
					hl7MessageXml.add(rootElement.toXML().toString());
				}
				logger.info(Messages.getString("CancerCenterClient.46")
						+ hl7MessageXml);
			}
		} catch (Exception e) {
			logger.error(Messages.getString("CancerCenterClient.47"), e
					.fillInStackTrace());
		}
		return hl7MessageXml;
	}

	/**
	 * Create the caXchange message
	 * 
	 * @param requestMessage
	 * @return messagePayload
	 */
	private MessagePayload createMessage(Message requestMessage) {
		// Create the caXchange message
		Metadata metadata = new Metadata();
		metadata.setExternalIdentifier("CTODS");
		metadata.setMessageType(MessageTypes.CT_LAB_DATA);

		// Credentials creds = new Credentials();// Optional Credentials - for
		// testing purposes comment out the creds.
		// creds.setUserName(userName);
		// creds.setPassword(userPasswd);
		// metadata.setCredentials(creds);

		requestMessage.setMetadata(metadata);
		Request caxchangeRequest = new Request();
		requestMessage.setRequest(caxchangeRequest);
		MessagePayload messagePayload = new MessagePayload();
		URI uri = new URI();
		try {
			uri.setPath("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain");
		} catch (MalformedURIException e) {
			logger.error("MalformedURIException" + e);
		}
		messagePayload.setXmlSchemaDefinition(uri);

		return messagePayload;
	}

	/**
	 * Creates a file for the HL7V3 message.
	 * 
	 * @param fileName
	 * @param HL7V3
	 * @return hl7v3XML
	 */
	private File createHL7V3File(String fileName, String HL7V3, int index) {
		// Create the HL7V3 file
		File hl7v3XML = null;
		try {
			String[] strFile = fileName.split("\\.");
			String strFileName = strFile[0] + "-" + index + "-hl7v3.xml";
			hl7v3XML = new File(cancerCenterClient.getRawFilesBackupFolder()
					+ strFileName);
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
	 * Accepts an inputStream and returns the org.w3c.dom.Document
	 * 
	 * @param stream
	 * @return document
	 */
	private Document getDocument(ByteArrayInputStream stream) {
		Document document = null;
		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			document = builder.parse(stream);
		} catch (ParserConfigurationException e) {

			logger.error("ParserConfigurationException"
					+ e.getLocalizedMessage());
		} catch (SAXException e) {

			logger.error("SAXException" + e.getLocalizedMessage());
		} catch (IOException e) {

			logger.error("IOException" + e.getLocalizedMessage());
		}
		return document;
	}

	/**
	 * Invokes the Grid Service HUB to load the HL7V3 message.
	 * 
	 * @param fileList
	 * @param i
	 * @param HL7V3
	 * @throws Exception
	 */
	public final void invokeGridService(String fileName, String HL7V3,
			 int index) throws Exception {
		try {
			boolean gotResponse = false;
			boolean hl7v3move = false;
			GetResponseResponse getResponse = null;

			CaXchangeRequestProcessorClient client = new CaXchangeRequestProcessorClient(
					cancerCenterClient.getHubURL());
			// creates the caXchange Message
			Message requestMessage = new Message();
			MessagePayload messagePayload = createMessage(requestMessage);

			// Create HL7V3 File
			File hl7v3XML = createHL7V3File(fileName, HL7V3, index);

			// call the method callToStudyLookupService
			Study study = invokeStudyLookupService(new ByteArrayInputStream(
					HL7V3.getBytes()));
			if (study == null) {
				logger.error(Messages.getString("CancerCenterClient.61"));
				if (hl7v3XML != null) {
					hl7v3move = hl7v3XML.renameTo(new File(cancerCenterClient
							.getErrorDir(), hl7v3XML.getName()));
				} else {
					logger.error(Messages.getString("CancerCenterClient.62"));
				}
			} else {
				// call to change the xml attribute values
				String changedHL7V3 = changeXMLAttvalues(HL7V3, study);
				FileWriter changedfw = new FileWriter(hl7v3XML, false);
				changedfw.write(changedHL7V3);
				changedfw.flush();
				changedfw.close();

				// gets the Document
				ByteArrayInputStream stream = new ByteArrayInputStream(
						changedHL7V3.getBytes());
				Document document = getDocument(stream);
				Element root = document.getDocumentElement();
				MessageElement messageElement = new MessageElement(root);
				messagePayload.set_any(new MessageElement[] { messageElement });
				requestMessage.getRequest().setBusinessMessagePayload(
						messagePayload);

				CaXchangeResponseServiceReference crsr = client
						.processRequestAsynchronously(requestMessage);

				CaXchangeResponseServiceClient responseService = new CaXchangeResponseServiceClient(
						crsr.getEndpointReference());

				int responseCount = 0;
				ResponseMessage responseMessage = null;
				while (!gotResponse) {
					try {
						responseMessage = responseService.getResponse();

						if (responseMessage.getResponse().getResponseStatus()
								.toString().equalsIgnoreCase("Success")) {

							logger.info("Response:Success -Moving File "
									+ hl7v3XML.getName()
									+ Messages
											.getString("CancerCenterClient.65")
									+ cancerCenterClient.getProcessedDir());
							hl7v3move = hl7v3XML.renameTo(new File(
									cancerCenterClient.getProcessedDir(),
									hl7v3XML.getName()));
							gotResponse = true;
						} else if (responseMessage.getResponse()
								.getResponseStatus().toString()
								.equalsIgnoreCase("Failure")) {

							logger.info("Response: Failure -Moving File "
									+ hl7v3XML.getName() + " To "
									+ cancerCenterClient.getProcessedDir());
							hl7v3move = hl7v3XML.renameTo(new File(
									cancerCenterClient.getErrorDir(), hl7v3XML
											.getName()));
							gotResponse = true;
						} else {
							logger.info("Error -Moving File "
									+ hl7v3XML.getName() + " To "
									+ cancerCenterClient.getProcessedDir());
							hl7v3move = hl7v3XML.renameTo(new File(
									cancerCenterClient.getErrorDir(), hl7v3XML
											.getName()));

						}

					} catch (Exception e) {
						logger.info(
								Messages.getString("CancerCenterClient.71"), e);
						responseCount++;
						if (responseCount > 50) {
							logger.error(Messages
									.getString("CancerCenterClient.72"));
							throw new Exception(Messages
									.getString("CancerCenterClient.73"));
						}
						Thread.sleep(1000);
					}
				}

				if (responseMessage != null) {
					logger.info(Messages.getString("CancerCenterClient.74")
							+ responseMessage.getResponse().getResponseStatus()
									.toString());
				}
			}// end of else
			if (!hl7v3move) {
				logger.info("Error Moving File ");
			}
		} catch (MalformedURIException e) {
			logger.error("MalformedURIException" + e.getLocalizedMessage());
		} catch (RemoteException e) {

			logger.error("RemoteException" + e.getLocalizedMessage());
		} catch (IOException e) {

			logger.error("IOException" + e.getLocalizedMessage());
		} catch (InterruptedException e) {

			logger.error("InterruptedException" + e.getLocalizedMessage());
		} catch (Exception e) {

			logger.error("Exception" + e.getLocalizedMessage());
		}

	}

	/**
	 * Invokes the StudyLookup Service to lookup study information for a
	 * participant in the HL7V3 message.
	 * 
	 * @param HL7V3
	 * @return study
	 */
	private Study invokeStudyLookupService(ByteArrayInputStream HL7V3) {
		Registration registration = new Registration();
		Study study = null;
		try {
			// gets the Document
			Document document = getDocument(HL7V3);

			XPath xpath = XPathFactory.newInstance().newXPath();
			String expression1 = "//enrolledSubject/id/@root";
			String expression2 = "//enrolledSubject/id/@extension";

			String root = (String) xpath.evaluate(expression1, document,
					XPathConstants.STRING);
			String extension = (String) xpath.evaluate(expression2, document,
					XPathConstants.STRING);
			System.out.println("Root" + root);
			System.out.println("Extension" + extension);
			// create the registration object
			ParticipantType participant = new ParticipantType();
			OrganizationAssignedIdentifierType identifier = new OrganizationAssignedIdentifierType();
			participant.setGridId(root);
			identifier.setValue(extension);
			OrganizationAssignedIdentifierType[] identifiers = { identifier };
			participant.setIdentifier(identifiers);
			registration.setParticipant(participant);
			// Call the StudyLookupService
			// Create the client
			// -"http://localhost:8080/wsrf/services/cagrid/StudyLookupService"
			StudyLookupServiceClient client = new StudyLookupServiceClient(
					cancerCenterClient.getStudyLookupServiceURL());
			// Call the service
			study = client.getStudy(registration);

		} catch (XPathExpressionException e) {
			logger.error("XPathExpressionException" + e);
		} catch (IOException e) {
			logger.error("IOException" + e);
		}
		return study;
	}

	/**
	 * Replaces the values of the attributes in the XML with the values in the
	 * Study received from Grid Service call.
	 * 
	 * @param xml
	 * @param study
	 * @return xlm8 modified XML as String
	 */
	private String changeXMLAttvalues(String xml, Study study) {
		String xml1 = xml.replace("PROTOCOL ID ASSIGN AUTH", "NCI");
		String xml2 = xml1.replace("PROTOCOL ID ROOT", study.getIdentifier(0)
				.getGridId());
		String xml3 = xml2.replace("PROTOCOL ID EXT", study.getIdentifier(0)
				.getValue());
		String xml4 = xml3.replace("PROTOCOL TITLE", study.getLongTitleText());
		String xml5 = xml4.replace("PI ID ASSIGN AUTH", "PlaceHolder");
		String xml6 = xml5.replace("PI ID ROOT", "2.16.840.1.113883.19");
		String xml7 = xml6.replace("PI ID EXT", "PlaceHolder");
		String xml8 = xml7.replace("PI NAME", "PlaceHolder");
		return xml8;
	}

}
