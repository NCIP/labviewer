/**
 * Copyright Notice. Copyright 2008 ScenPro, Inc ("caBIG(TM)
 * Participant").caXchange was created with NCI funding and is part of the
 * caBIG(TM) initiative. The software subject to this notice and license includes
 * both human readable source code form and machine readable, binary, object
 * code form (the "caBIG(TM) Software"). This caBIG(TM) Software License (the
 * "License") is between caBIG(TM) Participant and You. "You (or "Your") shall mean
 * a person or an entity, and all other entities that control, are controlled
 * by, or are under common control with the entity. "Control" for purposes of
 * this definition means (i) the direct or indirect power to cause the direction
 * or management of such entity, whether by contract or otherwise, or (ii)
 * ownership of fifty percent (50%) or more of the outstanding shares, or (iii)
 * beneficial ownership of such entity. License. Provided that You agree to the
 * conditions described below, caBIG(TM) Participant grants You a non-exclusive,
 * worldwide, perpetual, fully-paid-up, no-charge, irrevocable, transferable and
 * royalty-free right and license in its rights in the caBIG(TM) Software,
 * including any copyright or patent rights therein, to (i) use, install,
 * disclose, access, operate, execute, reproduce, copy, modify, translate,
 * market, publicly display, publicly perform, and prepare derivative works of
 * the caBIG(TM) Software in any manner and for any purpose, and to have or permit
 * others to do so; (ii) make, have made, use, practice, sell, and offer for
 * sale, import, and/or otherwise dispose of caBIG(TM) Software (or portions
 * thereof); (iii) distribute and have distributed to and by third parties the
 * caBIG(TM) Software and any modifications and derivative works thereof; and (iv)
 * sublicense the foregoing rights set out in (i), (ii) and (iii) to third
 * parties, including the right to license such rights to further third parties.
 * For sake of clarity, and not by way of limitation, caBIG(TM) Participant shall
 * have no right of accounting or right of payment from You or Your sublicensees
 * for the rights granted under this License. This License is granted at no
 * charge to You. Your downloading, copying, modifying, displaying, distributing
 * or use of caBIG(TM) Software constitutes acceptance of all of the terms and
 * conditions of this Agreement. If you do not agree to such terms and
 * conditions, you have no right to download, copy, modify, display, distribute
 * or use the caBIG(TM) Software. 1. Your redistributions of the source code for
 * the caBIG(TM) Software must retain the above copyright notice, this list of
 * conditions and the disclaimer and limitation of liability of Article 6 below.
 * Your redistributions in object code form must reproduce the above copyright
 * notice, this list of conditions and the disclaimer of Article 6 in the
 * documentation and/or other materials provided with the distribution, if any.
 * 2. Your end-user documentation included with the redistribution, if any, must
 * include the following acknowledgment: "This product includes software
 * developed by ScenPro, Inc." If You do not include such end-user
 * documentation, You shall include this acknowledgment in the caBIG(TM) Software
 * itself, wherever such third-party acknowledgments normally appear. 3. You may
 * not use the names "ScenPro, Inc", "The National Cancer Institute", "NCI",
 * "Cancer Bioinformatics Grid" or "caBIG(TM)" to endorse or promote products
 * derived from this caBIG(TM) Software. This License does not authorize You to use
 * any trademarks, service marks, trade names, logos or product names of either
 * caBIG(TM) Participant, NCI or caBIG(TM), except as required to comply with the
 * terms of this License. 4. For sake of clarity, and not by way of limitation,
 * You may incorporate this caBIG(TM) Software into Your proprietary programs and
 * into any third party proprietary programs. However, if You incorporate the
 * caBIG(TM) Software into third party proprietary programs, You agree that You are
 * solely responsible for obtaining any permission from such third parties
 * required to incorporate the caBIG(TM) Software into such third party proprietary
 * programs and for informing Your sublicensees, including without limitation
 * Your end-users, of their obligation to secure any required permissions from
 * such third parties before incorporating the caBIG(TM) Software into such third
 * party proprietary software programs. In the event that You fail to obtain
 * such permissions, You agree to indemnify caBIG(TM) Participant for any claims
 * against caBIG(TM) Participant by such third parties, except to the extent
 * prohibited by law, resulting from Your failure to obtain such permissions. 5.
 * For sake of clarity, and not by way of limitation, You may add Your own
 * copyright statement to Your modifications and to the derivative works, and
 * You may provide additional or different license terms and conditions in Your
 * sublicenses of modifications of the caBIG(TM) Software, or any derivative works
 * of the caBIG(TM) Software as a whole, provided Your use, reproduction, and
 * distribution of the Work otherwise complies with the conditions stated in
 * this License. 6. THIS caBIG(TM) SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED
 * OR IMPLIED WARRANTIES (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE)
 * ARE DISCLAIMED. IN NO EVENT SHALL THE ScenPro, Inc OR ITS AFFILIATES BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS caBIG(TM) SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
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
