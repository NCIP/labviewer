/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */
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

	*//**
	 * @param fileName
	 *//*
	private void setUpToInvokeGrid(String fileName) {
		// invokes the grid service to persist the HL7V3
		// message.
		int counter = 1;
		boolean hl7v3move = false;
		MessageElement messageElement=null;
		QName lab = new QName( "gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "LoadLabRequest");
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
					messageElement = new MessageElement(lab,root);
 			}
				if(messageElement!=null){
					InvokeGridService invokeGridService = new InvokeGridService(cancerCenterClient);	
					invokeGridService.invokeGridService(messageElement,lab,hl7v3XML);
				}
				counter++;
		 }
		}catch (Exception e) {
			logger.fatal(Messages.getString("CancerCenterClient.22"));
		}

	}

	*//**
	 * Invokes the caAdapter API to convert the .csv file to HL7V3 message.
	 * 
	 * @param filePath
	 * @param mapFile
	 * @return hl7MessageXml
	 * @throws Exception
	 *//*
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

	*//**
	 * Creates a file for the HL7V3 message.
	 * 
	 * @param fileName
	 * @param HL7V3
	 * @return hl7v3XML
	 *//*
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

	*//**
	 * Accepts an inputStream and returns the org.w3c.dom.Document
	 * 
	 * @param stream
	 * @return document
	 *//*
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

	*//**
	 * Invokes the StudyLookup Service to lookup study information for a
	 * participant in the HL7V3 message.
	 * 
	 * @param HL7V3
	 * @return study
	 *//*
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
			OrganizationAssignedIdentifierType identifier = 
				new OrganizationAssignedIdentifierType();
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

	*//**
	 * Replaces the values of the attributes in the XML with the values in the
	 * Study received from Grid Service call.
	 * 
	 * @param xml
	 * @param study
	 * @return xlm8 modified XML as String
	 *//*
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
*/
