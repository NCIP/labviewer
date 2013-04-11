/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */
package gov.nih.nci.cagrid.labviewer.xml;

public interface UnMarshaller
{	
	/**
	 * Parses an xml string to an Object
	 * 
	 * @param xml
	 * @return
	 * @throws Exception 
	 */
	public Object parseXmlToObject(String xml) throws Exception;
}
