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

import gov.nih.nci.cabig.ctms.suite.authorization.SuiteRole;
import gov.nih.nci.cabig.ctms.suite.authorization.SuiteRoleMembership;
import gov.nih.nci.cagrid.caxchange.client.CaXchangeRequestProcessorClient;
import gov.nih.nci.cagrid.common.Utils;
import gov.nih.nci.caxchange.Credentials;
import gov.nih.nci.caxchange.Message;
import gov.nih.nci.caxchange.MessagePayload;
import gov.nih.nci.caxchange.Metadata;
import gov.nih.nci.caxchange.Request;
import gov.nih.nci.caxchange.Response;
import gov.nih.nci.caxchange.ResponseMessage;
import gov.nih.nci.caxchange.Statuses;
import gov.nih.nci.caxchange.ctom.viewer.beans.util.HibernateUtil;
import gov.nih.nci.caxchange.ctom.viewer.constants.DisplayConstants;
import gov.nih.nci.caxchange.ctom.viewer.forms.LoginForm;
import gov.nih.nci.caxchange.ctom.viewer.util.CommonUtil;
import gov.nih.nci.caxchange.ctom.viewer.util.LabViewerAuthorizationHelper;
import gov.nih.nci.caxchange.ctom.viewer.viewobjects.SearchResult;
import gov.nih.nci.caxchange.ctom.viewer.viewobjects.StudySearchResult;
import gov.nih.nci.coppa.common.LimitOffset;
import gov.nih.nci.coppa.services.pa.StudyProtocol;
import gov.nih.nci.ctom.ctlab.domain.Protocol;
import gov.nih.nci.ctom.ctlab.domain.ProtocolStatus;
import gov.nih.nci.ctom.ctlab.handler.ProtocolHandler;
import gov.nih.nci.labhub.domain.Study;
import gov.nih.nci.system.client.ApplicationServiceProvider;
import gov.nih.nci.system.query.cql.CQLAssociation;
import gov.nih.nci.system.query.cql.CQLAttribute;
import gov.nih.nci.system.query.cql.CQLGroup;
import gov.nih.nci.system.query.cql.CQLLogicalOperator;
import gov.nih.nci.system.query.cql.CQLObject;
import gov.nih.nci.system.query.cql.CQLPredicate;
import gov.nih.nci.system.query.cql.CQLQuery;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.axis.message.MessageElement;
import org.apache.axis.types.URI;
import org.apache.axis.types.URI.MalformedURIException;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.globus.gsi.GlobusCredential;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.iso._21090.II;
import org.iso._21090.ST;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author Lisa Kelley
 */
public class StudySearchDAO extends HibernateDaoSupport
{
	private static final Logger log = Logger.getLogger(StudySearchDAO.class);
	
	// The maxmium number of search results to be returned for a remote service method.
	private static final int MAX_SEARCH_RESULTS = 500;
	public static final String STUDY_PROTOCOL_IDENTIFIER_NAME = "NCI study protocol entity identifier";

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
	public SearchResult getStudySearchResults(String studyID, String studyTitle, HttpSession session) throws Exception
	{
		List<StudySearchResult> studySearchResults = null;
		log.debug("Study search called with search term(s): " + studyID + studyTitle);

		List<String> searchTerms = getSearchTerms(studyTitle);
		if (StringUtils.isEmpty(studyID.trim()) && searchTerms.isEmpty())
		{
			studyID = "%";
		}
		
		List<StudySearchResult> ctodsSearchResults = getCTODSSearchResults(studyID, searchTerms, session);
		
		// create message elements for caXchange message
		List<MessageElement> messageElements = createMessageElements(studyID, searchTerms);

		// create caXchange message
		Message requestMessage = createMessage(session, "STUDY_PROTOCOL", "search", messageElements);
		
		CaXchangeRequestProcessorClient client = getCaXchangeClient(session);
		
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
				log.info("No response from caxchange(attempt #" + attempts + ")", e);
				if (attempts > 25)
				{
					log.error("Never got a response from caXchange");
					throw new Exception("No response from hub");
				}
				
				Thread.sleep(1000); // sleep 1 second
			}
			
			if (responseReceived)
			{
			    if (responseMessage.getResponse().getResponseStatus().equals(Statuses.SUCCESS))
				{
					List<Protocol> paStudies = getPAStudies(responseMessage.getResponse(), session);
					
					studySearchResults = determineStudySearchResults(paStudies, ctodsSearchResults);
				}
				else // PA service invocation failed
				{
				    studySearchResults = ctodsSearchResults;
				}
			}
		} // end of while loop
		
		SearchResult searchResult = new SearchResult();
		searchResult.setSearchResultObjects(studySearchResults);
		session.setAttribute("SEARCH_RESULT_STUDY", searchResult); // lisa - why is this added to session?

		return searchResult;
	}
	
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private List<String> getSearchTerms(String studyTitle)
	{	
		List<String> searchTerms = new ArrayList<String>();
		String[] searchTermArray = studyTitle.split(" ");
	    List<String> searchTermList = Arrays.asList(searchTermArray);
	    
	    for (Iterator<String> it = searchTermList.iterator(); it.hasNext(); )
	    {
	    	String searchTerm = (String) it.next();	    	
	        if (!searchTerm.equals("")) // this handles situation when user enters multiple spaces between search terms
	        {
	        	searchTerms.add(searchTerm);
	        	// for now, break after one search term has been added to the list
	        	// the search method in the PA study protocol service can only handle one search term
	        	// this may be enhanced to accommodate multiple search terms in the future
	        	break;
	        }
	    }
	    
	    return searchTerms;
	}
	
	/**
	 * getCTODSSearchResults queries the database with the user entered search criteria.
	 * returns the study search results
	 * 
	 * @param studyPhrase
	 * @return
	 * @throws Exception
	 */	
	private List<StudySearchResult> getCTODSSearchResults(String studyID, List<String> searchTerms, HttpSession session) throws Exception
	{
		CQLQuery query = new CQLQuery();
		CQLObject target = new CQLObject();
		target.setName("gov.nih.nci.labhub.domain.Study"); // Study correlates to protocol table
		CQLGroup group = new CQLGroup();
		
		for (String searchTerm : searchTerms)
		{
			// long title is not available in Study class in ctomlabapi-beans.jar - need to add to ORM
			//group.addAttribute(new CQLAttribute("name", CQLPredicate.LIKE, "%" + searchTerm + "%")); // name correlates to long_title_text
			group.addAttribute(new CQLAttribute("shortTitle", CQLPredicate.LIKE, "%" + searchTerm + "%"));
		}
		    
		if (StringUtils.isNotEmpty(studyID))
		{
		    CQLAssociation association = new CQLAssociation();
		    association.setName("gov.nih.nci.labhub.domain.II"); 
		    association.setTargetRoleName("studyIdentifier"); // studyIdentifier correlates to identifier table
		    association.setAttribute(new CQLAttribute("extension", CQLPredicate.LIKE, "%" + studyID + "%"));
		    group.addAssociation(association);
		}
		
		CommonUtil util = new CommonUtil();		
		String username = util.checkUserLogin(session);
		LabViewerAuthorizationHelper authHelper = new LabViewerAuthorizationHelper();
		SuiteRoleMembership roleMembership = authHelper.getUserRoleMembership(username, SuiteRole.LAB_DATA_USER);
		
		// if the user has permission to access specific studies (not all studies), then filter based upon these specific studies
		if (!roleMembership.isAllStudies())
		{
			CQLGroup innerGroup = new CQLGroup();
			for (String studyId : roleMembership.getStudyIdentifiers())
			{
				CQLAssociation association = new CQLAssociation();
			    association.setName("gov.nih.nci.labhub.domain.II"); 
			    association.setTargetRoleName("studyIdentifier"); // studyIdentifier correlates to identifier table
			    association.setAttribute(new CQLAttribute("extension", CQLPredicate.EQUAL_TO, studyId));
			    innerGroup.addAssociation(association);
			}
			
			innerGroup.setLogicOperator(CQLLogicalOperator.OR);
			group.addGroup(innerGroup);
	    }
		
		// if the user has permission to access specific sites (not all sites), then filter based upon these specific sites
		if (!roleMembership.isAllSites())
		{
			CQLGroup innerGroup = new CQLGroup();
			for (String siteId : roleMembership.getSiteIdentifiers())
			{
				CQLAssociation innerAssociation = new CQLAssociation();
				innerAssociation.setName("gov.nih.nci.labhub.domain.HealthCareSite"); 
				innerAssociation.setTargetRoleName("healthCareSite");
				innerAssociation.setAttribute(new CQLAttribute("nci_institute_code", CQLPredicate.EQUAL_TO, siteId));
			    
				CQLAssociation association = new CQLAssociation();
			    association.setName("gov.nih.nci.labhub.domain.StudySite"); 
			    association.setTargetRoleName("studySiteCollection"); // studySiteCollection correlates to study_site table
			    association.setAssociation(innerAssociation);
			    innerGroup.addAssociation(association);
			}
			
			innerGroup.setLogicOperator(CQLLogicalOperator.OR);
			group.addGroup(innerGroup);
	    }
		
		group.setLogicOperator(CQLLogicalOperator.AND);		
		target.setGroup(group);
		query.setTarget(target);
		
		List studies = null;
		try
		{
		    studies = ApplicationServiceProvider.getApplicationService().query(query);
		}
		catch (Exception e)
		{
			log.error("Exception occurred while retrieving Study Search Results: ", e);
			throw e;
		}
		
		log.debug("Study search query result size = " + studies.size());
		return convertCTODSStudies(studies, session);
	}
	
	/**
	 * printRecord creates the view object that will properly display the
	 * results
	 * 
	 * @param resultList
	 * @param request
	 * @return
	 * @throws ParseException
	 */
	private List<StudySearchResult> convertCTODSStudies(List<Study> studies, HttpSession session) throws HibernateException
	{
		List<StudySearchResult> ctodsSearchResults = new ArrayList<StudySearchResult>();
		
		if (studies.size() <= 0)
		{
			return ctodsSearchResults;
		}
		
		for (Study study : studies)
		{
	        StudySearchResult ctodsStudy = new StudySearchResult();
	        ctodsStudy.setId(study.getId());
	        
	        gov.nih.nci.labhub.domain.II identifier = study.getStudyIdentifier().iterator().next();
			ctodsStudy.setStudyId(identifier.getExtension() == null ? "" : identifier.getExtension());
			
			// long title is not available in Study class in ctomlabapi-beans.jar - need to add to ORM
			ctodsStudy.setLongTitle(study.getShortTitle() == null || study.getShortTitle().equals("null") ? "&nbsp;" : study.getShortTitle()); // lisa - figure out how the string "null" is getting stored in the database! and why have to use "&nbsp;" instead of "" 
			
			String sponsorCode = "";
			if (study.getSponsorCode() != null && !study.getSponsorCode().equals("null")) // lisa - figure out how the string "null" is getting stored in the database!
			{
				sponsorCode = study.getSponsorCode();
			}
			ctodsStudy.setSponsorCode(sponsorCode);
			
			ctodsStudy.setPhaseCode(study.getPhaseCode() == null ? "" : study.getPhaseCode());
				
			String gridId = identifier.getRoot();				
			ctodsStudy.setGridId(gridId == null ? "" : gridId);
			
			// TO DO: set the details
			// link: https://cbvapp-d1017.nci.nih.gov:28443/c3pr/pages/study/viewStudy?studyId=14
			ctodsStudy.setDetails(""); // lisa - anu - what is this?
				
			ctodsSearchResults.add(ctodsStudy);
		}
			
		return setStatus(ctodsSearchResults);
	}
	
	private static final String QUERY = "FROM ProtocolStatus ps " +
                                       "WHERE ps.protocol_id = ? " +
                                         "AND ps.ctom_insert_date = (SELECT MAX(ctom_insert_date) FROM ProtocolStatus WHERE protocol_id = ps.protocol_id)";
	
	/*
	 * Checks and sets the Protocol/Study Status
	 * 
	 * @param studySearchResultList
	 */
	private List<StudySearchResult> setStatus(List<StudySearchResult> ctodsSearchResults) throws HibernateException
	{		
		try
		{
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			
			for (StudySearchResult ctodsSearchResult : ctodsSearchResults)
			{
				Query query = session.createQuery(QUERY);
				query.setInteger(0, ctodsSearchResult.getId().intValue());
				List<gov.nih.nci.caxchange.ctom.viewer.beans.ProtocolStatus> statusCodes = query.list();
				
				if (!statusCodes.isEmpty())
				{
					ctodsSearchResult.setStatus(statusCodes.get(0).getStatus_code());
				}
			}
			
			session.getTransaction().commit();
		}
		catch (HibernateException e)
		{
			log.error("HibernateException occurred while retrieving Study Status: ", e);
			throw e;
		}
		
		return ctodsSearchResults;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private List<MessageElement> createMessageElements(String studyID, List<String> searchTerms) throws Exception
	{
		List<MessageElement> messageElements = new ArrayList<MessageElement>();
		
		StudyProtocol studyProtocol = new StudyProtocol();
		
		if (StringUtils.isNotEmpty(studyID))
		{
		    II assignedIdentifier = new II();
		    assignedIdentifier.setExtension("%" + studyID + "%");
		    studyProtocol.setAssignedIdentifier(assignedIdentifier);
		}
		
		// for now, the list of search terms contains only one search term
    	// the search method in the PA study protocol service can only handle one search term
    	// this may be enhanced to accommodate multiple search terms in the future
		for (String searchTerm : searchTerms)
		{
		    ST title = new ST();
		    title.setValue(searchTerm);
		    studyProtocol.setOfficialTitle(title);
		}
		
		LimitOffset limit = new LimitOffset();
		limit.setLimit(MAX_SEARCH_RESULTS);
		limit.setOffset(0);
		
		// create message elements for the objects
		QName qName = new QName("http://pa.services.coppa.nci.nih.gov", "StudyProtocol");
		messageElements.add(createMessageElement(studyProtocol, qName));
			
		qName = new QName("http://common.coppa.nci.nih.gov", "LimitOffset");
		messageElements.add(createMessageElement(limit, qName));
		
		return messageElements;
	}
	
	/**
	 * Create message element for an object
	 * 
	 * @param object
	 *            The object to be serialized
	 * @param qName
	 *            The QName of the object
	 * @param wsdd
	 *            A stream containing the WSDD configuration
	 * @throws Exception
	 */
	private MessageElement createMessageElement(Object object, QName qName) throws Exception
	{
		MessageElement messageElement = null;	
		// create writer to place XML into
		StringWriter writer = new StringWriter();
		// create stream containing the WSDD configuration
		InputStream wsdd = getClass().getResourceAsStream("/gov/nih/nci/coppa/services/pa/client/client-config.wsdd");
		
		try
		{
			// serialize the object to XML
			Utils.serializeObject(object, qName, writer, wsdd);
			
			// convert to an input stream
			byte[] byteArray = writer.toString().getBytes();
			InputStream inputStream = new ByteArrayInputStream(byteArray);
			
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			// setNamespaceAware(true) prevents the addition of empty namespace tags, which causes SAXParseException
			documentBuilderFactory.setNamespaceAware(true);
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			// parse the content of the input stream as an XML document
			Document document = documentBuilder.parse(inputStream);
			// create message element with document element
			messageElement = new MessageElement(document.getDocumentElement());
		}
		catch (Exception e)
		{
			log.error("Exception occured while creating message element: ", e);
			throw e;
		}
		
		return messageElement;
	}
	
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private Message createMessage(HttpSession session, String serviceType, String operationName, List<MessageElement> messageElements) throws MalformedURIException
	{
		Message message = new Message();
		
		String username = ((LoginForm) session.getAttribute(DisplayConstants.LOGIN_OBJECT)).getLoginId();
		String password = ((LoginForm) session.getAttribute(DisplayConstants.LOGIN_OBJECT)).getPassword();
		String credentialEpr = (String) session.getAttribute("CAGRID_SSO_DELEGATION_SERVICE_EPR");
		log.info("credential EPR = " + credentialEpr);
			
		Credentials credentials = new Credentials();
		credentials.setUserName(username);
		credentials.setPassword(password);
		if (credentialEpr != null)
		{
			credentials.setDelegatedCredentialReference(credentialEpr);
		}

		Metadata metadata = new Metadata();
		metadata.setExternalIdentifier("CTODS");
		metadata.setServiceType(serviceType);
		metadata.setOperationName(operationName);
		metadata.setCredentials(credentials);

		MessagePayload messagePayload = new MessagePayload();
			
		URI uri = new URI();
		try
		{			
			uri.setPath("http://coppa.nci.nih.gov");
		}
		catch (MalformedURIException e)
		{
			log.error("MalformedURIException occured while creating the caXchange message: ", e);
			throw e;
		}
			
		MessageElement[] messageElementArray = new MessageElement[messageElements.size()];
		for (int i = 0; i < messageElements.size(); i++)
		{
			messageElementArray[i] = messageElements.get(i);
		}
			
		messagePayload.setXmlSchemaDefinition(uri);
		messagePayload.set_any(messageElementArray);
			
		message.setMetadata(metadata);
		message.setRequest(new Request());
		message.getRequest().setBusinessMessagePayload(messagePayload);

		return message;
	}
	
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private CaXchangeRequestProcessorClient getCaXchangeClient(HttpSession session) throws Exception
	{
		String caXchangeURL = (String) session.getAttribute("caXchangeURL");
		GlobusCredential gridCredentials = (GlobusCredential) session.getAttribute("CAGRID_SSO_GRID_CREDENTIAL");
		log.info("grid credentials = " + gridCredentials.getIdentity());
		CaXchangeRequestProcessorClient client = null;
		try
		{
			client = new CaXchangeRequestProcessorClient(caXchangeURL, gridCredentials);
		}
		catch (Exception e)
		{
			log.error("Exception occured while creating the caXchange client: ", e);
			throw e;
		} 
		
		return client;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private List<Protocol> getPAStudies(Response response, HttpSession session) throws Exception
	{
		List<StudyProtocol> studyProtocols = new ArrayList<StudyProtocol>();
		
		try
		{
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			MessageElement messageElement = response.getTargetResponse()[0].getTargetBusinessMessage().get_any()[0];
			List<MessageElement> childElements = messageElement.getChildren();
			
			if (childElements != null)
			{
				for (MessageElement childElement : childElements)
				{
					Element element = childElement.getAsDOM();
					StringWriter writer = new StringWriter();
					// transform XML element to a result
					transformer.transform(new DOMSource(element), new StreamResult(writer));
					// create reader for the XML
					StringReader reader = new StringReader(writer.toString());
					// create stream containing the WSDD configuration
					InputStream wsdd = 
						getClass().getResourceAsStream("/gov/nih/nci/coppa/services/pa/client/client-config.wsdd");
					// deserialize XML into StudyProtocol object
					StudyProtocol studyProtocol = (StudyProtocol) Utils.deserializeObject(reader, StudyProtocol.class, wsdd);
					studyProtocols.add(studyProtocol);
				}
			}
		}
		catch (Exception e)
		{
			log.error("Exception occured while getting PA study protocols: ", e);
			throw e;
		}
		
		return convertPAStudies(studyProtocols, session);
	}
	
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private List<Protocol> convertPAStudies(List<StudyProtocol> studyProtocols, HttpSession session)
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
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private List<StudySearchResult> determineStudySearchResults(List<Protocol> paStudies, List<StudySearchResult> ctodsSearchResults)
	{
		List<StudySearchResult> studySearchResults = new ArrayList<StudySearchResult>();
					
		if (paStudies.isEmpty())
		{					
		    studySearchResults = ctodsSearchResults;
		}
		else
		{
			if (ctodsSearchResults.isEmpty())
			{	
// users decided they just wanted CTODS search results displayed - need to clean this up				
//				for (Protocol paStudy : paStudies)
//		        {
//					StudySearchResult paSearchResult = createSearchResult(paStudy);
//					studySearchResults.add(paSearchResult);
//		        }
			}
			else
			{
		        for (Protocol paStudy : paStudies)
		        {
		        	StudySearchResult paSearchResult = createSearchResult(paStudy);
		        	StudySearchResult removeObject = null;
		        	
			        for (StudySearchResult ctodsSearchResult : ctodsSearchResults)
			        {
			        	// for now, just compare the study ID's (do not include the grid ID's)
			        	// the search method in the PA study protocol service always returns the root as null
			        	// this will be fixed in 3.2
			        	int index = paStudy.getNciIdentifier().indexOf(".");
			        	if (paStudy.getNciIdentifier().substring(index + 1).equals(ctodsSearchResult.getStudyId()))
			        	//if (paStudy.getNciIdentifier().equals(ctodsSearchResult.getGridId() + "." + ctodsSearchResult.getStudyId()))
			            {						        								        	
			        		// if the study status from PA is the same as the current study status stored in the CTODS database,
			        		// leave the current status as is (i.e. do not insert a new row in the protocol_status table)
			        		// to accomplish this, set the study status from PA to null in the Protocol object
			        		// and a new row will not get inserted in the protocol_status table
			        		String paStudyStatus = paStudy.getStatus().getStatus_code();
			        		if (paStudyStatus == null || paStudyStatus.equalsIgnoreCase(ctodsSearchResult.getStatus()))
			        		{
			        			paStudy.setStatus(null);
			        		}
			        		
			        		paStudy.setId(ctodsSearchResult.getId().longValue());
			        		updateCTODSStudy(paStudy);
			        		
			        		paSearchResult.setId(ctodsSearchResult.getId());
			        		
			            	removeObject = ctodsSearchResult;
			    	        break; // since match has been found
			            }
			        }
			        						        
// users decided they just wanted CTODS search results displayed - need to clean this up
//			        studySearchResults.add(paSearchResult);
//			        if (removeObject != null)
//			        {
//			            ctodsSearchResults.remove(removeObject);
//			        }
			    }
		        
		        // add any remaining CTODS search results
		        studySearchResults.addAll(ctodsSearchResults);
			}												
		}
		
		return studySearchResults;
	}
	
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private StudySearchResult createSearchResult(Protocol paStudy)
	{		
		StudySearchResult paSearchResult = new StudySearchResult();
		
		int index = paStudy.getNciIdentifier().indexOf(".");
		paSearchResult.setStudyId(paStudy.getNciIdentifier().substring(index + 1));
		paSearchResult.setLongTitle(paStudy.getLongTxtTitle() == null ? "" : paStudy.getLongTxtTitle());
		paSearchResult.setSponsorCode(paStudy.getSponsorCode() == null ? "" : paStudy.getSponsorCode());
		paSearchResult.setPhaseCode(paStudy.getPhaseCode() == null ? "" : paStudy.getPhaseCode());
		paSearchResult.setStatus(paStudy.getStatus().getStatus_code() == null ? "" : paStudy.getStatus().getStatus_code());
		paSearchResult.setGridId(paStudy.getNciIdentifier().substring(0, index));
		
		// TO DO: set the details
		// link: https://cbvapp-d1017.nci.nih.gov:28443/c3pr/pages/study/viewStudy?studyId=14
		paSearchResult.setDetails(""); // lisa - anu - what is this?
			
		return paSearchResult;
	}
	
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private void updateCTODSStudy(Protocol paStudy)
	{	
		ProtocolHandler protocolDAO = new ProtocolHandler();
		try
		{
			protocolDAO.update(protocolDAO.getConnection(), paStudy);
		}
		catch (Exception e)
		{
			log.error("Exception occured while updating CTODS study: ", e);

		}
	}

}
