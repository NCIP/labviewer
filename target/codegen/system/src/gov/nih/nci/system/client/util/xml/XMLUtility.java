package gov.nih.nci.system.client.util.xml;

import java.io.File;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

import org.apache.log4j.Logger;

/**
 * Class used to convert domain objects to and from XML.
 */
public class XMLUtility {

	private static Logger log = Logger.getLogger(XMLUtility.class.getName());

	private Marshaller marshaller;

	private Unmarshaller unmarshaller;

	public XMLUtility(Marshaller marshaller,
			Unmarshaller unmarshaller) {
		
		this.marshaller = marshaller;
		this.unmarshaller = unmarshaller;
	}

	/**
	 * 
	 * Serializes an object into xml
	 * 
	 * @param beanObject
	 *            The caCORE object to serialize into xml
	 */
	public String toXML(Object beanObject) throws XMLUtilityException {
		StringWriter strWriter = new StringWriter();
		toXML(beanObject, strWriter);
		return strWriter.toString();
	}

	public void toXML(Object beanObject, Writer stream)
			throws XMLUtilityException {
		
		try {
			beanObject = CastorDomainObjectFieldHandler.convertObject(beanObject,true);
		} catch(Exception e){
			log.error("Exception caught trying to convert from proxy to domain object: ", e);
			throw new XMLUtilityException("Exception caught trying to convert from proxy to domain object: ", e);
		}
		marshaller.toXML(beanObject, stream);
	}

	/**
	 * Instantiates an object from an XML File that contains the serialized output
	 * of that object.
	 * 
	 * @param xmlFile
	 * @return
	 * @throws XMLUtilityException
	 */
	public Object fromXML(File xmlFile) throws XMLUtilityException {
		Object beanObject = null;
		beanObject = unmarshaller.fromXML(xmlFile);
		return beanObject;
	}

	/**
	 * Instantiates an object from xml input that contains the serialized output
	 * of that object.
	 * 
	 * @param input
	 *            Reader type
	 * @return Instantiated object
	 * @throws XMLUtilityException
	 */
	public Object fromXML(Reader input) throws XMLUtilityException {
		Object beanObject;
		beanObject = unmarshaller.fromXML(input);
		return beanObject;
	}
}
