/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */
package org.cagrid.gaards.websso.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.cagrid.gaards.websso.exception.AuthenticationConfigurationException;
import org.jdom.Document;
import org.jdom.input.DOMBuilder;
import org.springframework.core.io.ClassPathResource;
import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;


public class FileHelper
{
	public FileHelper()
	{
	} 


	public InputStream getFileAsStream(String fileName) throws AuthenticationConfigurationException
	{
		ClassPathResource cpr = new ClassPathResource(fileName);
		if (!cpr.exists()) {
			throw new AuthenticationConfigurationException ("Unable to load " + fileName + " file");
		}
		try { 
		  return cpr.getInputStream();
		}catch(IOException ie) {
			throw new AuthenticationConfigurationException ("Unable to load " + fileName + " file");
		}
	}
	
	public URL getFileAsURL(String fileName) throws AuthenticationConfigurationException
	{
		ClassPathResource cpr = new ClassPathResource(fileName);
		if (!cpr.exists()) {
			throw new AuthenticationConfigurationException ("Unable to load " + fileName + " file");
		}
		try { 
		  return cpr.getURL();
		}catch(IOException ie) {
			throw new AuthenticationConfigurationException ("Unable to load " + fileName + " file");
		}
	}
	

	public File getFile(String fileName) throws AuthenticationConfigurationException
	{
		URL url = getFileAsURL(fileName);
		URI uri;
		try
		{
			uri = new URI (url.toString());
		}
		catch (URISyntaxException e)
		{
			throw new AuthenticationConfigurationException ("Error obtaining the URI for the " + fileName + " file");
		}
		return new File(uri);
	}
	
	public Document validateXMLwithSchema(String propertiesFileName, String schemaFileName) throws AuthenticationConfigurationException
	{
		org.w3c.dom.Document document = null;
		InputStream schemaFileInputStream = getFileAsStream(schemaFileName);
		URL propertiesFileURL = getFileAsURL(propertiesFileName);
		
    	DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
    	documentBuilderFactory.setNamespaceAware(true);
    	documentBuilderFactory.setValidating(false);
    	documentBuilderFactory.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaLanguage","http://www.w3.org/2001/XMLSchema");
    	documentBuilderFactory.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaSource", schemaFileInputStream);
    	DocumentBuilder documentBuilder = null;
		try 
		{
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
		} 
		catch (ParserConfigurationException e) {
			throw new AuthenticationConfigurationException("Error in parsing the " + propertiesFileName + " file");
		}
    	try 
    	{
			document = (org.w3c.dom.Document) documentBuilder.parse(propertiesFileURL.getPath());
		} 
    	catch (SAXException e) 
    	{
			throw new AuthenticationConfigurationException("Error in parsing the " + propertiesFileName + " file");
        } 
    	catch(DOMException de) 
    	{
			throw new AuthenticationConfigurationException("Error in parsing the " + propertiesFileName + " file");
        }
		catch (IOException e) 
		{
			throw new AuthenticationConfigurationException("Error in reading the " + propertiesFileName + " file");
		}
		DOMBuilder builder = new DOMBuilder();
		org.jdom.Document jdomDocument = builder.build(document);

		return jdomDocument;
	}

}
