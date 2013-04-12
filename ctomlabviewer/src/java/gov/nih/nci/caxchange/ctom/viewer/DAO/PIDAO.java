/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */
package gov.nih.nci.caxchange.ctom.viewer.DAO;

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
import gov.nih.nci.caxchange.TargetResponseMessage;
import gov.nih.nci.caxchange.ctom.viewer.beans.PrincipalInvestigator;
import gov.nih.nci.caxchange.ctom.viewer.constants.DisplayConstants;
import gov.nih.nci.caxchange.ctom.viewer.forms.LoginForm;
import gov.nih.nci.caxchange.ctom.viewer.forms.PIForm;
import gov.nih.nci.caxchange.ctom.viewer.util.CommonUtil;
import gov.nih.nci.coppa.po.Id;
import gov.nih.nci.coppa.po.Person;
import gov.nih.nci.ctom.ctlab.domain.Investigator;
import gov.nih.nci.ctom.ctlab.domain.Protocol;
import gov.nih.nci.ctom.ctlab.handler.InvestigatorHandler;
import gov.nih.nci.ctom.ctlab.persistence.CTLabDAO;
import gov.nih.nci.logging.api.user.UserInfoHelper;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.axis.message.MessageElement;
import org.apache.axis.types.URI;
import org.apache.log4j.Logger;
import org.globus.gsi.GlobusCredential;
import org.iso._21090.ADXP;
import org.iso._21090.AddressPartType;
import org.iso._21090.ENXP;
import org.iso._21090.EntityNamePartType;
import org.iso._21090.TEL;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author asharma
 */
public class PIDAO
{

	private static final Logger log = Logger.getLogger(PIDAO.class);

	/**
	 * @param request
	 * @param baseDBForm
	 * @throws Exception
	 */
	public void retrievePI(HttpServletRequest request, PIForm baseDBForm) throws Exception
	{

		invokeService(request, baseDBForm);
	}

	/**
	 * @param request
	 * @param baseDBForm
	 * @throws Exception
	 */
	private void invokeService(HttpServletRequest request, PIForm baseDBForm) throws Exception
	{

		CommonUtil util = new CommonUtil();
		List<PrincipalInvestigator> piList = new ArrayList<PrincipalInvestigator>();
		
		// initialize the protocol
		Protocol protocol = new Protocol();
		
		
		// set the protocol id
		Integer protcolId = (Integer) request.getSession().getAttribute("ID");
		protocol.setId(protcolId.longValue());
		

		// 2 step process:
		// 1. using the protocolId obtain the CTEP id for the PI from CTODS DB.
		// 2. using the CTEPId - retrieve the PI by invoking the COPPA service.

		// 1. using the protocol id obtain the CTEP id for the HCS from CTODS
		// DB.

		List<String> ctepIdList = retrieveCTEPIdforPI(protcolId.longValue());

		for (String ctepIdentifier : ctepIdList)
		{
			// 2.Create a CTEP ii for the PI.

			Id iiCTEPId = new Id();
			iiCTEPId.setExtension(ctepIdentifier);
				
			//get the serialized version of the CTEP ID
			Document payload = serializedId(iiCTEPId);
			
			if (payload == null)
			{
				continue;
			}

			// using this ii for CTEP - invoke the COPPA service.

			// create caXchange RequestMessage
			Message requestMessage = createMessage(request, payload);

			// retrieve the globus credentials
			GlobusCredential gridCreds =
					(GlobusCredential) request.getSession().getAttribute(
							"CAGRID_SSO_GRID_CREDENTIAL");
			if (gridCreds != null)
				log.info("The credential : " + gridCreds.getIdentity());

			// create the request
			String url = (String) request.getSession().getAttribute("caXchangeURL");
			
			CaXchangeRequestProcessorClient client =
					new CaXchangeRequestProcessorClient(url, gridCreds);

			// Utils.serializeObject(requestMessage, lab, writer);
			ResponseMessage responseMessage = null;
			boolean gotResponse = false;
			int responseCount = 0;

			while (!gotResponse)
			{
				try
				{
					responseMessage = client.processRequestSynchronously(requestMessage);
					gotResponse = true;
					PrincipalInvestigator pi = new PrincipalInvestigator();
					System.out.println(responseMessage.getResponse().getResponseStatus());

					// COPPA service invocation successful
					if (responseMessage.getResponse().getResponseStatus().equals(Statuses.SUCCESS))
					{

						Response resp = responseMessage.getResponse();
						Person person = getTargetResponse(resp);
						// get the investigator name
						Investigator investigator = convertToInvestigator(person);
						investigator.setNciId(ctepIdentifier);
						pi.setName(investigator.getFirstName() + " "
								+ investigator.getMiddleNAle() + " " + investigator.getLastName());

						// get the investigator address
						String postalAddress =
								investigator.getStreetAddr() + " " + investigator.getCity() + " "
										+ investigator.getState() + " " + investigator.getZipCode()
										+ " " + investigator.getCountryCode();

						pi.setAddress(postalAddress);
						pi.setUpdatedDate(new Date().toString());
						pi.setCoppaUpdate("Y");
						pi.setEmail(investigator.getTelecomAddr());
						pi.setPhone(investigator.getPhone());

						log.info("name" + pi.getName());
						log.info("address" + postalAddress);
						//add it to the baseform list
						
						 
						// persist the data retrieved from COPPA to CTODS
						persistPIDetails(investigator, protocol);
					}
					// COPPA service invocation failed
					else
					{
						// retrieve the PI details based on the CTEP Identifier
						retrievePIDetailsForUI(ctepIdentifier, pi);
					}
					piList.add(pi);
				}
				catch (Exception e)
				{
					log.info("No response from caxchange", e);
					responseCount++;
					if (responseCount > 25)
					{
						log.error("Never got a response from caxchange hub");
						throw new Exception("No response from hub");
					}
					Thread.sleep(1000);
				}
			}// end of while
			baseDBForm.setPiList(piList);
		}// end of for
	}

	
	/**
	 * Serialized id.
	 * 
	 * @param ctepIi
	 *            the ctep ii
	 * @param idQname
	 *            the id qname
	 * @param writer
	 *            the writer
	 */
	private Document serializedId(Id ctepIi)
	{

		Document payload = null;
		try
		{
			DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			// QName for the Id
			QName idQname = new QName("http://po.coppa.nci.nih.gov", "Id");
			StringWriter writer = new StringWriter();
			// Serialize using the wsdd
			InputStream wsddIs =
					getClass().getResourceAsStream(
							"/gov/nih/nci/coppa/services/client/client-config.wsdd");

			Utils.serializeObject(ctepIi, idQname, writer, wsddIs);

			// convert to Input stream
			byte[] barray = writer.toString().getBytes();
			InputStream is = new ByteArrayInputStream(barray);

			// create the payload
			payload = db.parse(is);

		}
		catch (Exception e)
		{
			log.error("Exception occured while serializing ii", e);
		}
		return payload;
	}

	/**
	 * @param username
	 * @param password
	 * @param payload
	 * @return
	 */
	private Message createMessage(HttpServletRequest request, Document payload) throws Exception
	{

		Message requestMessage = new Message();
		try
		{
			HttpSession session = request.getSession();

			String username =
					((LoginForm) session.getAttribute(DisplayConstants.LOGIN_OBJECT)).getLoginId();
			String password =
					((LoginForm) session.getAttribute(DisplayConstants.LOGIN_OBJECT)).getPassword();
			UserInfoHelper.setUserInfo(username, session.getId());

			// Create the caxchange message
			Metadata metadata = new Metadata();
			metadata.setExternalIdentifier("CTODS");
			metadata.setServiceType("PERSON_BUSINESS_SERVICE");
			metadata.setOperationName("getPersonByCTEPId");

			// set the credentials
			Credentials creds = new Credentials();
			creds.setUserName(username);
			creds.setPassword(password);
			String credentialEpr =
					(String) request.getSession().getAttribute("CAGRID_SSO_DELEGATION_SERVICE_EPR");
			log.info("The credential EPR: " + credentialEpr);
			if (credentialEpr != null)
				creds.setDelegatedCredentialReference(credentialEpr);
			metadata.setCredentials(creds);
			requestMessage.setMetadata(metadata);

			// create the message payload
			MessagePayload messagePayload = new MessagePayload();
			URI uri = new URI();
			uri.setPath("http://coppa.nci.nih.gov");
			messagePayload.setXmlSchemaDefinition(uri);
			// MessageElement messageElement = new MessageElement(person, ii);
			MessageElement messageElement = new MessageElement(payload.getDocumentElement());
			messagePayload.set_any(new MessageElement[]
			{ messageElement });
			Request caxchangeRequest = new Request();
			requestMessage.setRequest(caxchangeRequest);
			requestMessage.getRequest().setBusinessMessagePayload(messagePayload);
		}
		catch (Exception e)
		{
			log.error("Exception occured while creating the caXchange message", e);
		}

		return requestMessage;
	}

	/**
	 * @param resp
	 * @return
	 */
	private Person getTargetResponse(Response resp)
	{

		Person person = new Person();
		try
		{
			javax.xml.transform.Transformer stringTransformer =
					TransformerFactory.newInstance().newTransformer();
			for (TargetResponseMessage msg : resp.getTargetResponse())
			{
				MessageElement[] messagePay = msg.getTargetBusinessMessage().get_any();
				for (MessageElement mEle : messagePay)
				{
					if (mEle != null)
					{
						Element el = mEle.getAsDOM();
						StringWriter sw = new StringWriter();
						stringTransformer.transform(new DOMSource(el), new StreamResult(sw));
						InputStream wsddIs =
								getClass().getResourceAsStream(
										"/gov/nih/nci/coppa/services/client/client-config.wsdd");

						person =
								(gov.nih.nci.coppa.po.Person) Utils.deserializeObject(
										new StringReader(sw.toString()),
										gov.nih.nci.coppa.po.Person.class, wsddIs);
					}
				}

			}
		}
		catch (Exception e)
		{
			log.error(e.getLocalizedMessage());
		}

		return person;

	}

	/**
	 * @param poPerson
	 * @return
	 */
	private Investigator convertToInvestigator(Person poPerson)
	{

		Investigator pi = new Investigator();

		// get the name
		List<ENXP> list = poPerson.getName().getPart();
		Iterator iteName = list.iterator();
		while (iteName.hasNext())
		{
			ENXP part = (ENXP) iteName.next();
			if (EntityNamePartType.FAM == part.getType())
			{
				pi.setLastName(part.getValue());
			}
			else if (EntityNamePartType.GIV == part.getType())
			{
				if (pi.getFirstName() == null)
				{
					pi.setFirstName(part.getValue());
				}
				else
				{
					pi.setMiddleNAle(part.getValue());
				}
			}
		}
		// get the address
		List<ADXP> address = poPerson.getPostalAddress().getPart();
		Iterator iteAddr = address.iterator();
		while (iteAddr.hasNext())
		{
			ADXP part = (ADXP) iteAddr.next();
			if (AddressPartType.AL == part.getType())
			{
				pi.setStreetAddr(part.getValue()!=null?part.getValue():"");
			}
			else if (AddressPartType.ADL == part.getType())
			{
				pi.setStreetAddr(pi.getStreetAddr() + " " + part.getValue()!=null?part.getValue():"");
			}
			else if (AddressPartType.CTY == part.getType())
			{
				pi.setCity(part.getValue()!=null?part.getValue():"");
			}
			else if (AddressPartType.STA == part.getType())
			{
				pi.setState(part.getValue()!=null?part.getValue():"");
			}
			else if (AddressPartType.ZIP == part.getType())
			{
				pi.setZipCode(part.getValue()!=null?part.getValue():"");
			}
			else if (AddressPartType.CNT == part.getType())
			{
				pi.setCountryCode(part.getCode()!=null?part.getCode():"");
			}
		}
		List<TEL> telephone = poPerson.getTelecomAddress().getItem();
		pi.setTelecomAddr(telephone.get(0).getValue());
		pi.setPhone(telephone.get(1).getValue());
		return pi;
	}

	/**
	 * Persist the PI details retrieved from COPPA Service into CTODS DB
	 * 
	 * @param investigator
	 * @param protocol
	 */
	private void persistPIDetails(Investigator investigator, Protocol protocol)
	{

		try
		{
			protocol.setInvestigator(investigator);
			InvestigatorHandler dao = new InvestigatorHandler();
			dao.update(dao.getConnection(), protocol);

		}
		catch (Exception se)
		{
			log.error("Error persisting PI details", se);

		}
	}

	/**
	 * If the call to COPPA service fails; Retrieve PI details from the DB to
	 * display on the UI.
	 * 
	 * @param ctepId
	 * @param baseDBForm
	 */
	private void retrievePIDetailsForUI(String ctepId, PrincipalInvestigator pi)
	{
		try
		{

			CTLabDAO dao = new CTLabDAO();
			Investigator investigator =
					dao.retrieveInvestigator(dao.getConnection(), ctepId);
			
			String address = investigator.getStreetAddr()!=null?investigator.getStreetAddr():"";
			String city =  investigator.getCity()!=null?investigator.getCity():"" ;
			String state = investigator.getState()!=null?investigator.getState():"";
			String zipCode =investigator.getZipCode()!=null?investigator.getZipCode():"";
			String country = investigator.getCountryCode()!=null?investigator.getCountryCode():"";
			pi.setAddress(address + " " + city + " " + state + " " + zipCode + " " + country);
			String fname = investigator.getFirstName()!=null?investigator.getFirstName():"";
			String mname = investigator.getMiddleNAle()!=null?investigator.getMiddleNAle():"";
			String lname = investigator.getLastName()!=null?investigator.getLastName():"";
			pi.setName(fname + " " + mname + " "+ lname);
			pi.setEmail(investigator.getTelecomAddr());
			pi.setPhone(investigator.getPhone());
			pi.setUpdatedDate(investigator.getCtomUpdateDt()!=null?investigator.getCtomUpdateDt().toString():"");
		}
		catch (Exception se)
		{
			log.error("Error retrieving PI details", se);

		}
	}

	/**
	 * @param protocolId
	 * @return
	 */
	private List<String> retrieveCTEPIdforPI(Long protocolId)
	{

		List<String> ctepIdList = null;
		try
		{
			CTLabDAO dao = new CTLabDAO();
			ctepIdList = dao.retrieveCTEPIDForPI(dao.getConnection(), protocolId);

		}
		catch (Exception se)
		{
			log.error("Error retrieving CTEP IDs for PI", se);

		}
		return ctepIdList;
	}
	
	}
