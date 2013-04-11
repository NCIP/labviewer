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
import gov.nih.nci.caxchange.ctom.viewer.beans.HCSite;
import gov.nih.nci.caxchange.ctom.viewer.constants.DisplayConstants;
import gov.nih.nci.caxchange.ctom.viewer.forms.HealthCareSiteForm;
import gov.nih.nci.caxchange.ctom.viewer.forms.LoginForm;
import gov.nih.nci.caxchange.ctom.viewer.util.CommonUtil;
import gov.nih.nci.coppa.po.Id;
import gov.nih.nci.coppa.po.Organization;
import gov.nih.nci.ctom.ctlab.domain.HealthCareSite;
import gov.nih.nci.ctom.ctlab.domain.Protocol;
import gov.nih.nci.ctom.ctlab.handler.HealthCareSiteHandler;
import gov.nih.nci.ctom.ctlab.persistence.CTLabDAO;
import gov.nih.nci.logging.api.user.UserInfoHelper;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
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
import org.iso._21090.DSETTEL;
import org.iso._21090.ENXP;
import org.iso._21090.TEL;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author asharma
 */
public class HealthCareSiteDAO
{

	private static final Logger log = Logger.getLogger(HealthCareSiteDAO.class);

	/**
	 * @param request
	 * @param baseDBForm
	 * @throws Exception
	 */
	public void retrieveHealthCareSite(HttpServletRequest request, HealthCareSiteForm baseDBForm)
			throws Exception
	{

		invokeService(request, baseDBForm);
	}

	/**
	 * @param request
	 * @param baseDBForm
	 * @throws Exception
	 */
	private void invokeService(HttpServletRequest request, HealthCareSiteForm baseDBForm)
			throws Exception
	{

		CommonUtil util = new CommonUtil();
		List<HCSite> hcsList = new ArrayList<HCSite>();

		// initialize the protocol
		Protocol protocol = new Protocol();

		// set the protocol id
		Integer protcolId = (Integer) request.getSession().getAttribute("ID");
		protocol.setId(protcolId.longValue());

		// 2 step process:
		// 1. using the protocolId obtain the CTEP id for the HCS from CTODS DB.
		// 2. using the CTEPId - retrieve the HCS by invoking the COPPA service.

		// 1. using the protocol id obtain the CTEP id for the HCS from CTODS
		// DB.
		List<String> ctepIdList = retrieveCTEPIdforHCS(protcolId.longValue());

		for (String ctepIdentifier : ctepIdList)
		{
			// 2.Create a CTEP ii for the HCS.

			Id iiCTEPId = new Id();
			iiCTEPId.setExtension(ctepIdentifier);

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

			ResponseMessage responseMessage = null;
			boolean gotResponse = false;
			int responseCount = 0;

			while (!gotResponse)
			{
				try
				{
					responseMessage = client.processRequestSynchronously(requestMessage);
					gotResponse = true;
					HCSite hcSite = new HCSite();
					System.out.println(responseMessage.getResponse().getResponseStatus());
					if (responseMessage.getResponse().getResponseStatus().equals(Statuses.SUCCESS))
					{
						Response resp = responseMessage.getResponse();
						Organization organization = getTargetResponse(resp);

						// get the organization
						HealthCareSite hcs = convertToSite(organization);
						hcs.setNciInstituteCd(ctepIdentifier);
						hcSite.setName(hcs.getName());

						// get the address
						String postalAddress = "";
						postalAddress =
								hcs.getStreetAddr() + "  " + hcs.getCity() + " "
										+ hcs.getPostalCode() + " " + hcs.getStateCode() + " "
										+ hcs.getCountryCode();
						hcSite.setAddress(postalAddress);
						hcSite.setEmail(hcs.getTelecomAddr());
						hcSite.setUpdatedDate(new Date().toString());
						hcSite.setCoppaUpdate("Y");
						log.info("name" + hcs.getName());
						log.info("address" + postalAddress);

						// persist Healthcaresite details
						persistHCSDetails(hcs, protocol);
					}
					// COPPA service invocation failed
					else
					{
						// retrieve the HCS details based on the CTEP Identifier
						retrieveHCSDetailsForUI(ctepIdentifier, hcSite);
					}
					hcsList.add(hcSite);
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
			baseDBForm.setHcsList(hcsList);
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
			// retrieve session data
			HttpSession session = request.getSession();
			String username =
					((LoginForm) session.getAttribute(DisplayConstants.LOGIN_OBJECT)).getLoginId();
			String password =
					((LoginForm) session.getAttribute(DisplayConstants.LOGIN_OBJECT)).getPassword();
			UserInfoHelper.setUserInfo(username, session.getId());

			Metadata metadata = new Metadata();
			metadata.setExternalIdentifier("CTODS");
			metadata.setServiceType("ORGANIZATION_BUSINESS_SERVICE");
			metadata.setOperationName("getOrganizationByCTEPId");

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
	private Organization getTargetResponse(Response resp)
	{

		Organization organization = new Organization();
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

						organization =
								(gov.nih.nci.coppa.po.Organization) Utils.deserializeObject(
										new StringReader(sw.toString()),
										gov.nih.nci.coppa.po.Organization.class, wsddIs);
					}
				}

			}
			
			serializeOrg(organization);
		}
		catch (Exception e)
		{
			log.error(e.getLocalizedMessage());
		}

		return organization;

	}

	/**
	 * @param poPerson
	 * @return
	 */
	private HealthCareSite convertToSite(Organization poOrg)
	{

		HealthCareSite hcs = new HealthCareSite();

		// get the name
		List<ENXP> list = poOrg.getName().getPart();
		Iterator iteName = list.iterator();
		while (iteName.hasNext())
		{
			ENXP part = (ENXP) iteName.next();
			hcs.setName(part.getValue());
		}
		// get the address
		List<ADXP> address = poOrg.getPostalAddress().getPart();
		String addressStr = "";
		Iterator iteAddr = address.iterator();
		while (iteAddr.hasNext())
		{
			ADXP part = (ADXP) iteAddr.next();
			if (AddressPartType.AL == part.getType())
			{
				hcs.setStreetAddr(part.getValue()!=null?part.getValue():"");
			}
			else if (AddressPartType.ADL == part.getType())
			{
				hcs.setStreetAddr(hcs.getStreetAddr() + part.getValue()!=null?part.getValue():"");
			}
			else if (AddressPartType.CTY == part.getType())
			{
				hcs.setCity(part.getValue()!=null?part.getValue():"");
			}
			else if (AddressPartType.STA == part.getType())
			{
				hcs.setStateCode(part.getValue()!=null?part.getValue():"");
			}
			else if (AddressPartType.ZIP == part.getType())
			{
				hcs.setPostalCode(part.getValue()!=null?part.getValue():"");
			}
			else if (AddressPartType.CNT == part.getType())
			{
				hcs.setCountryCode(part.getCode()!=null?part.getCode():"");
			}
		}
		// get the Telecom Address
		DSETTEL telList = poOrg.getTelecomAddress();
		List<TEL> set = telList.getItem();
		Iterator iter = set.iterator();
		while (iter.hasNext())
		{
			TEL obj = (TEL) iter.next();
			hcs.setTelecomAddr(obj.getValue().toString());
		}
		return hcs;
	}

	/**
	 * @param investigator
	 */
	private void persistHCSDetails(HealthCareSite healthCareSite, Protocol protocol)
	{

		try
		{
			protocol.setHealthCareSite(healthCareSite);
			HealthCareSiteHandler dao = new HealthCareSiteHandler();
			dao.update(dao.getConnection(), protocol);
		}
		catch (Exception se)
		{
			log.error("Error persisting HealthCareSite details", se);

		}
	}

	/**
	 * If the call to COPPA service fails; Retrieve HealthCareSite details from
	 * the DB to display on the UI.
	 * 
	 * @param ctepId
	 * @param baseDBForm
	 */
	private void retrieveHCSDetailsForUI(String ctepId, HCSite hcsite)
	{

		try
		{

			CTLabDAO dao = new CTLabDAO();
			HealthCareSite hcs = dao.retrieveHealCareSite(dao.getConnection(), ctepId);
			hcsite.setAddress(hcs.getStreetAddr() != null ? hcs.getStreetAddr() : "" + " "
					+ hcs.getCity() != null ? hcs.getCity()
					: "" + " " + hcs.getStateCode() != null ? hcs.getStateCode() : "" + " "
							+ hcs.getPostalCode() != null ? hcs.getPostalCode() : "" + " "
							+ hcs.getCountryCode() != null ? hcs.getCountryCode() : "");
			hcsite.setName(hcs.getName() != null ? hcs.getName() : "");
			hcsite.setEmail(hcs.getTelecomAddr());
			hcsite.setUpdatedDate(hcs.getCtomUpdateDt() != null ? hcs.getCtomUpdateDt().toString()
					: "");
		}
		catch (Exception se)
		{
			log.error("Error retrieving HealthCareSite details", se);

		}
	}

	/**
	 * @param protocolId
	 * @return
	 */
	private List<String> retrieveCTEPIdforHCS(Long protocolId)
	{

		List<String> ctepIdList = null;
		try
		{
			CTLabDAO dao = new CTLabDAO();
			ctepIdList = dao.retrieveCTEPIDForHCS(dao.getConnection(), protocolId);

		}
		catch (Exception se)
		{
			log.error("Error retrieving CTEP IDs for HCS", se);

		}
		return ctepIdList;
	}
	
	private void serializeOrg(Organization org)
	{

		try
		{
			// QName for the Id
			QName idQname = new QName("http://po.coppa.nci.nih.gov", "Organization");
			PrintWriter writer = new PrintWriter("Org.xml");
			// Serialize using the wsdd
			InputStream wsddIs =
					getClass().getResourceAsStream(
							"/gov/nih/nci/coppa/services/client/client-config.wsdd");

			Utils.serializeObject(org, idQname, writer, wsddIs);

		}
		catch (Exception e)
		{
			log.error("Exception occured while serializing ii", e);
		}
		
	}
}
