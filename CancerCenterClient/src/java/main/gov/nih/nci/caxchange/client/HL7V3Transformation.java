package gov.nih.nci.caxchange.client;
/**
 * Copyright Notice.  Copyright 2008  Scenpro, Inc (“caBIG™ Participant”).caXchange
 * was created with NCI funding and is part of the caBIG™ initiative. 
 * The software subject to this notice and license includes both human readable source code form and 
 * machine readable, binary, object code form (the “caBIG™ Software”).
 * This caBIG™ Software License (the “License”) is between caBIG™ Participant and You.  
 * “You (or “Your”) shall mean a person or an entity, and all other entities that control, 
 * are controlled by, or are under common control with the entity.  “Control” for purposes of this 
 * definition means (i) the direct or indirect power to cause the direction or management of such entity, 
 * whether by contract or otherwise, or (ii) ownership of fifty percent (50%) or more of the outstanding shares, 
 * or (iii) beneficial ownership of such entity.  
 * License.  Provided that You agree to the conditions described below, caBIG™ Participant grants 
 * You a non-exclusive, worldwide, perpetual, fully-paid-up, no-charge, irrevocable, transferable and 
 * royalty-free right and license in its rights in the caBIG™ Software, including any copyright or patent rights therein, to 
 * (i) use, install, disclose, access, operate, execute, reproduce, copy, modify, translate, market, publicly display, 
 * publicly perform, and prepare derivative works of the caBIG™ Software in any manner and for any purpose, and to have 
 * or permit others to do so; (ii) make, have made, use, practice, sell, and offer for sale, import, and/or otherwise 
 * dispose of caBIG™ Software (or portions thereof); (iii) distribute and have distributed to and by third parties the 
 * caBIG™ Software and any modifications and derivative works thereof; and (iv) sublicense the foregoing rights 
 * set out in (i), (ii) and (iii) to third parties, including the right to license such rights to further third parties.  
 * For sake of clarity, and not by way of limitation, caBIG™ Participant shall have no right of accounting or right of payment
 *  from You or Your sublicensees for the rights granted under this License.  This License is granted at no charge to You.  
 *  Your downloading, copying, modifying, displaying, distributing or use of caBIG™ Software constitutes acceptance of all 
 *  of the terms and conditions of this Agreement.  If you do not agree to such terms and conditions, you have no right to 
 *  download, copy, modify, display, distribute or use the caBIG™ Software.  
 * 1.	Your redistributions of the source code for the caBIG™ Software must retain the above copyright notice, 
 * 		this list of conditions and the disclaimer and limitation of liability of Article 6 below.  
 * 		Your redistributions in object code form must reproduce the above copyright notice, this list of conditions and 
 * 		the disclaimer of Article 6 in the documentation and/or other materials provided with the distribution, if any.
 * 2.	Your end-user documentation included with the redistribution, if any, must include the following acknowledgment: 
 * 		“This product includes software developed by Scenpro, Inc.”  
 * 		If You do not include such end-user documentation, You shall include this acknowledgment in the caBIG™ Software itself, 
 * 		wherever such third-party acknowledgments normally appear.
 * 3.	You may not use the names  “Scenpro, Inc”, 
 * 		“The National Cancer Institute”, “NCI”, “Cancer Bioinformatics Grid” or “caBIG™” to endorse or promote products 
 * 		derived from this caBIG™ Software.  This License does not authorize You to use any trademarks, service marks, trade names,
 * 		logos or product names of either caBIG™ Participant, NCI or caBIG™, except as required to comply with the terms of this 
 * 		License.
 * 4.	For sake of clarity, and not by way of limitation, You may incorporate this caBIG™ Software into Your proprietary 
 * 		programs and into any third party proprietary programs.  However, if You incorporate the caBIG™ Software into third party 
 * 		proprietary programs, You agree that You are solely responsible for obtaining any permission from such third parties 
 * 		required to incorporate the caBIG™ Software into such third party proprietary programs and for informing Your sublicensees, 
 * 		including without limitation Your end-users, of their obligation to secure any required permissions from such third parties 
 * 		before incorporating the caBIG™ Software into such third party proprietary software programs.  In the event that You fail to 
 * 		obtain such permissions, You agree to indemnify caBIG™ Participant for any claims against caBIG™ Participant by such third 
 * 		parties, except to the extent prohibited by law, resulting from Your failure to obtain such permissions.
 * 5.	For sake of clarity, and not by way of limitation, You may add Your own copyright statement to Your modifications and 
 * 		to the derivative works, and You may provide additional or different license terms and conditions in Your sublicenses of 
 * 		modifications of the caBIG™ Software, or any derivative works of the caBIG™ Software as a whole, provided Your use, reproduction, 
 * 		and distribution of the Work otherwise complies with the conditions stated in this License.
 * 6.	THIS caBIG™ SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED WARRANTIES (INCLUDING, BUT NOT LIMITED TO, 
 * 		THE IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED.  
 * 		IN NO EVENT SHALL THE Scenpro, Inc OR ITS AFFILIATES 
 * 		BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * 		PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON 
 * 		ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY 
 * 		OUT OF THE USE OF THIS caBIG™ SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * 
 */
import static java.util.concurrent.TimeUnit.SECONDS;
import gov.nih.nci.caadapter.common.validation.ValidatorResults;
import gov.nih.nci.caadapter.hl7.transformation.TransformationService;
import gov.nih.nci.caadapter.hl7.transformation.data.XMLElement;
import gov.nih.nci.cagrid.labviewer.grid.client.StudyLookupServiceClient;
import gov.nih.nci.ccts.grid.OrganizationAssignedIdentifierType;
import gov.nih.nci.ccts.grid.ParticipantType;
import gov.nih.nci.ccts.grid.Registration;
import gov.nih.nci.ccts.grid.Study;

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
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.axis.message.MessageElement;
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

	CancerCenterClient cancerCenterClient;

	private final ScheduledExecutorService scheduler = Executors
			.newScheduledThreadPool(1);
	
	private List<String> HL7V3Msgs = Collections
			.synchronizedList(new ArrayList<String>());

	// Logging File
	static Logger logger = Logger
			.getLogger("client");

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
								if(fileList[i].getName().indexOf("V2TOV3") != -1){
								fileMoved = fileList[i].renameTo(new File(
										cancerCenterClient.getRawFilesBackupDirectory(),
										fileName));
								logger.debug("File Moved " +fileMoved+":" +fileName );
								}else {
									fileMoved=true;
								}
								//invoke the grid service setup
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
						.getInitialDelay_long(), cancerCenterClient
						.getPollingInterval_long(), SECONDS);
	}

	/**
	 * @param fileName
	 */
	private void setUpToInvokeGrid(String fileName) {
		// invokes the grid service to persist the HL7V3
		// message.
		int counter = 1;
		boolean hl7v3move = false;
		MessageElement messageElement=null;
		try {
			for (String HL7V3 : HL7V3Msgs) {
					
				// Create HL7V3 File
				File hl7v3XML = createHL7V3File(fileName, HL7V3, counter);

				// call the method callToStudyLookupService
				Study study = invokeStudyLookupService(new ByteArrayInputStream(
						HL7V3.getBytes()));
				if (study == null) {
					logger.error(Messages.getString("CancerCenterClient.61"));
					if (hl7v3XML != null) {
						hl7v3move = hl7v3XML.renameTo(new File(cancerCenterClient
								.getErrorDir(), hl7v3XML.getName()));
					}
					else{
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
					messageElement = new MessageElement(root);
 			}
				if(messageElement!=null){
					InvokeGridService invokeGridService = new InvokeGridService(cancerCenterClient);	
					invokeGridService.invokeGridService(messageElement,hl7v3XML);
				}
				counter++;
		 }
		}catch (Exception e) {
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
				logger.debug(Messages.getString("CancerCenterClient.46")
						+ hl7MessageXml);
			}
		} catch (Exception e) {
			logger.error(Messages.getString("CancerCenterClient.47"), e
					.fillInStackTrace());
		}
		return hl7MessageXml;
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
			logger.debug("Root" + root);
			logger.debug("Extension" + extension);
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
