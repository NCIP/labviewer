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
