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
import gov.nih.nci.caxchange.ctom.viewer.constants.DisplayConstants;
import gov.nih.nci.caxchange.ctom.viewer.forms.LoginForm;
import gov.nih.nci.coppa.po.Organization;
import gov.nih.nci.coppa.po.Person;
import gov.nih.nci.coppa.services.pa.StudyProtocol;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
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
import org.apache.log4j.Logger;
import org.globus.gsi.GlobusCredential;
import org.w3c.dom.Document;

/**
 * @author Lisa Kelley
 */
public class COPPAServiceHelper
{
	private static final Logger log = Logger.getLogger(COPPAServiceHelper.class);
	
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
	public static MessageElement createMessageElement(Object object, QName qName, InputStream wsdd) throws Exception
	{
		MessageElement messageElement = null;	
		// create writer to place XML into
		StringWriter writer = new StringWriter();
		
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
			log.error("createMessageElement: Exception occurred: ", e);
			throw e;
		}
		
		return messageElement;
	}
	
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static Message createMessage(HttpSession session, String serviceType, String operationName, List<MessageElement> messageElements) throws MalformedURIException
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
			log.error("createMessage: MalformedURIException occurred: ", e);
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
	public static CaXchangeRequestProcessorClient getCaXchangeClient(HttpSession session) throws Exception
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
			log.error("getCaXchangeClient: Exception occurred: ", e);
			throw e;
		} 
		
		return client;
	}
	
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public Object getCOPPAObject(Response response, String resourceName, Class javaClass) throws Exception
	{
		Object coppaObject = null;
		
		List<Object> coppaObjects = getCOPPAObjects(response, resourceName, javaClass);		
		if (!coppaObjects.isEmpty())
		{
			coppaObject = coppaObjects.get(0);
		}
		
		return coppaObject;
	}
	
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public List<Object> getCOPPAObjects(Response response, String resourceName, Class javaClass) throws Exception
	{
		List<Object> coppaObjects = new ArrayList<Object>();
		
		MessageElement messageElement = response.getTargetResponse()[0].getTargetBusinessMessage().get_any()[0];
		if( messageElement.getName().equals("Array"))
		{
			List<MessageElement> childElements = messageElement.getChildren();
			
			if (childElements != null)
			{
				for (MessageElement childElement : childElements)
				{					    
					Object coppaObject = getCOPPAObject(childElement, resourceName, javaClass);					
					coppaObjects.add(coppaObject);
				}
			}
		}
		else
		{
			if (messageElement != null)
			{
				Object coppaObject = getCOPPAObject(messageElement, resourceName, javaClass);
				coppaObjects.add(coppaObject);
			}
		}
		
		return coppaObjects;
	}
	
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private Object getCOPPAObject(MessageElement element, String resourceName, Class javaClass) throws Exception
	{
		Object coppaObject = null;
		
		try
		{
		    StringWriter writer = new StringWriter();
			// transform XML element to a result
		    Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.transform(new DOMSource(element.getAsDOM()), new StreamResult(writer));
			// create reader for the XML
			StringReader reader = new StringReader(writer.toString());
			// create stream containing the WSDD configuration
			InputStream wsdd = getClass().getResourceAsStream(resourceName);					
			
			// deserialize XML into appropriate object
			if (javaClass == Organization.class)
			{
				coppaObject = (Organization) Utils.deserializeObject(reader, Organization.class, wsdd);
			}			
			else if (javaClass == Person.class)
			{
				coppaObject = (Person) Utils.deserializeObject(reader, Person.class, wsdd);
			}
			else if (javaClass == StudyProtocol.class)
			{
				coppaObject = (StudyProtocol) Utils.deserializeObject(reader, StudyProtocol.class, wsdd);	
			}
		}
		catch (Exception e)
		{
			log.error("getCOPPAObject: Exception occurred: ", e);
			throw e;
		}
		
		return coppaObject;
	}

}
