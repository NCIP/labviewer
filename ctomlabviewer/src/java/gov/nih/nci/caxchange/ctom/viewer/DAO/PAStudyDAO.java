/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */
package gov.nih.nci.caxchange.ctom.viewer.DAO;

import gov.nih.nci.cagrid.caxchange.client.CaXchangeRequestProcessorClient;
import gov.nih.nci.caxchange.Message;
import gov.nih.nci.caxchange.Response;
import gov.nih.nci.caxchange.ResponseMessage;
import gov.nih.nci.caxchange.Statuses;
import gov.nih.nci.coppa.common.LimitOffset;
import gov.nih.nci.coppa.services.pa.StudyProtocol;
import gov.nih.nci.ctom.ctlab.domain.Protocol;
import gov.nih.nci.ctom.ctlab.domain.ProtocolStatus;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.xml.namespace.QName;

import org.apache.axis.message.MessageElement;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.iso._21090.II;
import org.iso._21090.ST;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * @author Lisa Kelley
 */
public class PAStudyDAO extends HibernateDaoSupport
{
	private static final Logger log = Logger.getLogger(PAStudyDAO.class);

	// The maxmium number of search results to be returned for a remote service method.
	private static final int MAX_SEARCH_RESULTS = 500;
//	private static final String STUDY_PROTOCOL_IDENTIFIER_NAME = "NCI study protocol entity identifier";

	/**
	 * SearchObjects retrieves the user entered search criteria and returns the
	 * study search results
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param errors
	 * @param messages
	 * @return searchResult
	 * @throws Exception
	 */
	public List<Protocol> getPAStudies(String studyID, List<String> studyTitleTerms, HttpSession session) throws Exception
	{
		List<Protocol> paStudies = new ArrayList<Protocol>();

		// create message elements for caXchange message
		List<MessageElement> messageElements = createMessageElements(studyID, studyTitleTerms);

		// create caXchange message
		Message requestMessage = COPPAServiceHelper.createMessage(session, "STUDY_PROTOCOL", "search", messageElements);

		CaXchangeRequestProcessorClient client = COPPAServiceHelper.getCaXchangeClient(session);

		boolean responseReceived = false;
		int attempts = 0;

		while (!responseReceived)
		{
			ResponseMessage responseMessage = null;
			try
			{
				responseMessage = client.processRequestSynchronously(requestMessage);
				responseReceived = true;
			}
			catch (Exception e)
			{
				attempts++;
				log.info("No response from caXchange(attempt #" + attempts + ")", e);
				if (attempts > 25)
				{
					log.error("Never got a response from caXchange");
					throw new Exception("No response from caXchange");
				}

				Thread.sleep(1000); // sleep 1 second
			}

			if (responseReceived)
			{
			    if (responseMessage.getResponse().getResponseStatus().equals(Statuses.SUCCESS))
				{
			    	List<StudyProtocol> studyProtocols = getPAStudyProtocols(responseMessage.getResponse());
					paStudies = createPAStudies(studyProtocols, session);
				}
			}
		} // end of while loop

		return paStudies;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private List<MessageElement> createMessageElements(String studyID, List<String> studyTitleTerms) throws Exception
	{
		List<MessageElement> messageElements = new ArrayList<MessageElement>();

		StudyProtocol studyProtocol = new StudyProtocol();

		if (StringUtils.isNotBlank(studyID))
		{
		    II assignedIdentifier = new II();
		    assignedIdentifier.setExtension("%" + studyID + "%");
		    studyProtocol.setAssignedIdentifier(assignedIdentifier);
		}

		// for now, the list of search terms contains only one search term
    	// the search method in the PA study protocol service can only handle one search term
    	// this may be enhanced to accommodate multiple search terms in the future
		for (String studyTitleTerm : studyTitleTerms)
		{
		    ST title = new ST();
		    title.setValue(studyTitleTerm);
		    studyProtocol.setOfficialTitle(title);
		}

		LimitOffset limit = new LimitOffset();
		limit.setLimit(MAX_SEARCH_RESULTS);
		limit.setOffset(0);

		// create message elements for the objects
		QName qName = new QName("http://pa.services.coppa.nci.nih.gov", "StudyProtocol");
		// create stream containing the WSDD configuration
		InputStream wsdd = getClass().getResourceAsStream("/gov/nih/nci/coppa/services/pa/client/client-config.wsdd");
		messageElements.add(COPPAServiceHelper.createMessageElement(studyProtocol, qName, wsdd));

		qName = new QName("http://common.coppa.nci.nih.gov", "LimitOffset");
		// this statement must be executed again
		wsdd = getClass().getResourceAsStream("/gov/nih/nci/coppa/services/pa/client/client-config.wsdd");
		messageElements.add(COPPAServiceHelper.createMessageElement(limit, qName, wsdd));

		return messageElements;
	}

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private List<StudyProtocol> getPAStudyProtocols(Response response) throws Exception
	{
		List<StudyProtocol> studyProtocols = new ArrayList<StudyProtocol>();

		String resourceName = "/gov/nih/nci/coppa/services/pa/client/client-config.wsdd";
		COPPAServiceHelper helper = new COPPAServiceHelper(); // lisa - try COPPAServiceHelper.getInstance()
    	List<Object> coppaObjects = helper.getCOPPAObjects(response, resourceName, StudyProtocol.class);

    	for (Object coppaObject : coppaObjects)
		{
    		studyProtocols.add((StudyProtocol) coppaObject);
		}

		return studyProtocols;
	}

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private List<Protocol> createPAStudies(List<StudyProtocol> studyProtocols, HttpSession session)
	{
		List<Protocol> paStudies = new ArrayList<Protocol>();

		for (StudyProtocol studyProtocol : studyProtocols)
		{
			Protocol paStudy = new Protocol();
			String studyProtocolRoot = studyProtocol.getAssignedIdentifier().getRoot();
			String studyProtocolExtension = studyProtocol.getAssignedIdentifier().getExtension();
			paStudy.setNciIdentifier(studyProtocolRoot + "." + studyProtocolExtension);
			paStudy.setLongTxtTitle(studyProtocol.getOfficialTitle().getValue());
			// long title is not available in Study class in ctomlabapi-beans.jar - need to add to ORM
			//paStudy.setShortTxtTitle(studyProtocol.getPublicTitle().getValue());
			paStudy.setShortTxtTitle(studyProtocol.getOfficialTitle().getValue());
			paStudy.setPhaseCode(studyProtocol.getPhaseCode().getCode());
			paStudy.setSponsorCode(getPASponsorCodes(studyProtocolRoot, studyProtocolExtension, session));
            // paStudy.setIdAssigningAuth(); // this is not available in PA StudyProtocol object

			ProtocolStatus status = new ProtocolStatus();
			status.setStatus_code(studyProtocol.getStatusCode().getCode());
			status.setCtom_insert_date(new Date());
			paStudy.setStatus(status);

			paStudies.add(paStudy);
		}

		return paStudies;
	}

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private String getPASponsorCodes(String studyProtocolRoot, String studyProtocolExtension, HttpSession session)
	{
		String sponsorCodes = null;

//		// create message element for caXchange message
//		Id studyProtocolId = new Id(); // gov.nih.nci.coppa.services.pa.Id;
//		studyProtocolId.setIdentifierName(STUDY_PROTOCOL_IDENTIFIER_NAME); // lisa - is this necessary?
//		studyProtocolId.setRoot(studyProtocolRoot);
//		studyProtocolId.setExtension(studyProtocolExtension);
//
//        List<MessageElement> messageElements = new ArrayList<MessageElement>();
//        Message requestMessage;
//		try
//		{
//			QName qName = new QName("http://pa.services.coppa.nci.nih.gov", "Id");
//			messageElements.add(createMessageElement(studyProtocolId, qName));
//
//			// create caXchange message
//			requestMessage = createMessage(session, "STUDY_RESOURCING", "getStudyResourceByStudyProtocol", messageElements);
//		}
//		catch (Exception e)
//		{
//			log.error("Exception occured while creating message elements", e);
//		}
//
//		boolean responseReceived = false;
//		int attempts = 0;
//
//		while (!responseReceived)
//		{
//			try
//			{
//				ResponseMessage responseMessage = getCaXchangeClient(session).processRequestSynchronously(requestMessage);
//				responseReceived = true;
//				if (responseMessage.getResponse().getResponseStatus().equals(Statuses.SUCCESS))
//				{
//					List<StudyResourcing> studyResourcings = new ArrayList<StudyResourcing>();
//
//					try
//					{
//						Transformer transformer = TransformerFactory.newInstance().newTransformer();
//						MessageElement messageElement = responseMessage.getResponse().getTargetResponse()[0].getTargetBusinessMessage().get_any()[0];
//						List<MessageElement> childElements = messageElement.getChildren();
//
//						if (childElements != null)
//						{
//							for (MessageElement childElement : childElements)
//							{
//								Element element = childElement.getAsDOM();
//								StringWriter writer = new StringWriter();
//								// transform XML element to a result
//								transformer.transform(new DOMSource(element), new StreamResult(writer));
//								// create reader for the XML
//								StringReader reader = new StringReader(writer.toString());
//								// create stream containing the WSDD configuration
//								InputStream wsdd =
//									getClass().getResourceAsStream("/gov/nih/nci/coppa/services/pa/client/client-config.wsdd");
//								// deserialize XML into StudyResourcing object
//								StudyResourcing studyResourcing = (StudyResourcing) Utils.deserializeObject(reader, StudyResourcing.class, wsdd);
//								studyResourcings.add(studyResourcing);
//							}
//						}
//					}
//					catch (Exception e)
//					{
//						log.error(e.getLocalizedMessage());
//					}
//
//					for (StudyResourcing studyResourcing : studyResourcings)
//					{
//						String sponsorCode = studyResourcing.getNihInstitutionCode().getCode();
//						// or studyResourcing.getNciDivisionProgramCode() depending on which one is populated
//
//						if (sponsorCode != null)
//						{
//							if (sponsorCodes == null)
//							{
//								sponsorCodes = "";
//							}
//
//						    sponsorCodes += sponsorCode;
//						}
//					}
//				}
//			}
//			catch (Exception e)
//			{
//				attempts++;
//				log.info("No response from caxchange(attempt #" + attempts + ")", e);
//				if (attempts > 25)
//				{
//					log.error("Never got a response from caXchange hub");
//					throw new Exception("No response from hub");
//				}
//
//				Thread.sleep(1000); // sleep 1 second
//			}
//		} // end of while loop

		return sponsorCodes;
	}

}
