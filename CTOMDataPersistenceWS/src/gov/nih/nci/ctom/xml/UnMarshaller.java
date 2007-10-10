package gov.nih.nci.ctom.xml;

public interface UnMarshaller {

	
	
	/**
	 * Parses an xml string to an Object
	 * 
	 * @param xml
	 * @return
	 * @throws Exception 
	 */
	public Object parseXmlToObject(String xml) throws Exception;
}
