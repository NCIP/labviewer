/**
 * Copyright Notice. Copyright 2008 ScenPro, Inc (“caBIG™
 * Participant”).caXchange was created with NCI funding and is part of the
 * caBIG™ initiative. The software subject to this notice and license includes
 * both human readable source code form and machine readable, binary, object
 * code form (the “caBIG™ Software”). This caBIG™ Software License (the
 * “License”) is between caBIG™ Participant and You. “You (or “Your”) shall mean
 * a person or an entity, and all other entities that control, are controlled
 * by, or are under common control with the entity. “Control” for purposes of
 * this definition means (i) the direct or indirect power to cause the direction
 * or management of such entity, whether by contract or otherwise, or (ii)
 * ownership of fifty percent (50%) or more of the outstanding shares, or (iii)
 * beneficial ownership of such entity. License. Provided that You agree to the
 * conditions described below, caBIG™ Participant grants You a non-exclusive,
 * worldwide, perpetual, fully-paid-up, no-charge, irrevocable, transferable and
 * royalty-free right and license in its rights in the caBIG™ Software,
 * including any copyright or patent rights therein, to (i) use, install,
 * disclose, access, operate, execute, reproduce, copy, modify, translate,
 * market, publicly display, publicly perform, and prepare derivative works of
 * the caBIG™ Software in any manner and for any purpose, and to have or permit
 * others to do so; (ii) make, have made, use, practice, sell, and offer for
 * sale, import, and/or otherwise dispose of caBIG™ Software (or portions
 * thereof); (iii) distribute and have distributed to and by third parties the
 * caBIG™ Software and any modifications and derivative works thereof; and (iv)
 * sublicense the foregoing rights set out in (i), (ii) and (iii) to third
 * parties, including the right to license such rights to further third parties.
 * For sake of clarity, and not by way of limitation, caBIG™ Participant shall
 * have no right of accounting or right of payment from You or Your sublicensees
 * for the rights granted under this License. This License is granted at no
 * charge to You. Your downloading, copying, modifying, displaying, distributing
 * or use of caBIG™ Software constitutes acceptance of all of the terms and
 * conditions of this Agreement. If you do not agree to such terms and
 * conditions, you have no right to download, copy, modify, display, distribute
 * or use the caBIG™ Software. 1. Your redistributions of the source code for
 * the caBIG™ Software must retain the above copyright notice, this list of
 * conditions and the disclaimer and limitation of liability of Article 6 below.
 * Your redistributions in object code form must reproduce the above copyright
 * notice, this list of conditions and the disclaimer of Article 6 in the
 * documentation and/or other materials provided with the distribution, if any.
 * 2. Your end-user documentation included with the redistribution, if any, must
 * include the following acknowledgment: “This product includes software
 * developed by ScenPro, Inc.” If You do not include such end-user
 * documentation, You shall include this acknowledgment in the caBIG™ Software
 * itself, wherever such third-party acknowledgments normally appear. 3. You may
 * not use the names “ScenPro, Inc”, “The National Cancer Institute”, “NCI”,
 * “Cancer Bioinformatics Grid” or “caBIG™” to endorse or promote products
 * derived from this caBIG™ Software. This License does not authorize You to use
 * any trademarks, service marks, trade names, logos or product names of either
 * caBIG™ Participant, NCI or caBIG™, except as required to comply with the
 * terms of this License. 4. For sake of clarity, and not by way of limitation,
 * You may incorporate this caBIG™ Software into Your proprietary programs and
 * into any third party proprietary programs. However, if You incorporate the
 * caBIG™ Software into third party proprietary programs, You agree that You are
 * solely responsible for obtaining any permission from such third parties
 * required to incorporate the caBIG™ Software into such third party proprietary
 * programs and for informing Your sublicensees, including without limitation
 * Your end-users, of their obligation to secure any required permissions from
 * such third parties before incorporating the caBIG™ Software into such third
 * party proprietary software programs. In the event that You fail to obtain
 * such permissions, You agree to indemnify caBIG™ Participant for any claims
 * against caBIG™ Participant by such third parties, except to the extent
 * prohibited by law, resulting from Your failure to obtain such permissions. 5.
 * For sake of clarity, and not by way of limitation, You may add Your own
 * copyright statement to Your modifications and to the derivative works, and
 * You may provide additional or different license terms and conditions in Your
 * sublicenses of modifications of the caBIG™ Software, or any derivative works
 * of the caBIG™ Software as a whole, provided Your use, reproduction, and
 * distribution of the Work otherwise complies with the conditions stated in
 * this License. 6. THIS caBIG™ SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED
 * OR IMPLIED WARRANTIES (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE)
 * ARE DISCLAIMED. IN NO EVENT SHALL THE ScenPro, Inc OR ITS AFFILIATES BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS caBIG™ SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
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
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
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
								hcs.getStreetAddr() + ", " + hcs.getCity() + ", "
										+ hcs.getPostalCode() + ", " + hcs.getStateCode() + ", "
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
	public static HealthCareSite convertToSite(Organization poOrg)
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
				hcs.setStreetAddr(part.getValue());
			}
			else if (AddressPartType.ADL == part.getType())
			{
				hcs.setStreetAddr(hcs.getStreetAddr() + part.getValue());
			}
			else if (AddressPartType.CTY == part.getType())
			{
				hcs.setCity(part.getValue());
			}
			else if (AddressPartType.STA == part.getType())
			{
				hcs.setStateCode(part.getValue());
			}
			else if (AddressPartType.ZIP == part.getType())
			{
				hcs.setPostalCode(part.getValue());
			}
			else if (AddressPartType.CNT == part.getType())
			{
				hcs.setCountryCode(part.getCode());
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
}
