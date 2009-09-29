package gov.nih.nci.system.client.util.xml;

/**
 * The object used for deserializing an xml rempresentation of an object back to a java bean.
 */
public interface Unmarshaller {
	
	/**
	 * Deserializes xml to a java bean.
	 *
	 * @param  reader the object to be deserialized.
	 */
   Object fromXML(java.io.Reader reader) throws XMLUtilityException;
   /**
	 * Deserializes xml to a java bean
	 *
	 * @param  file the object to be deserialzed.
	 */
   Object fromXML(java.io.File file) throws XMLUtilityException;
	/**
	 * Returns the base unmarshaller
	 *
	 * @return the base unmarshaller object
	 */
   Object getBaseUnmarshaller();
}
