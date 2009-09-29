package gov.nih.nci.system.client.util.xml;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;

import org.apache.log4j.Logger;
import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.mapping.MappingException;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.ValidationException;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

public class caCOREUnmarshaller implements gov.nih.nci.system.client.util.xml.Unmarshaller {

	private static Logger log= Logger.getLogger(caCOREUnmarshaller.class.getName());
	
	private Unmarshaller unmarshaller;
	private Mapping mapping;
	private String mappingFileName;
	
	/**
	 * Creates and XMLUtility instance
	 */
	public caCOREUnmarshaller(String mappingFileName, boolean validation) {
		this.mappingFileName = mappingFileName;
	}

	/**
	 *
	 * @return default mapping file being used for xml serialziation/deserialization
	 */
	public Mapping getMapping() throws XMLUtilityException {
		/* if no mapping file explicity specified then load the default */
		//log.debug("mapping is null? " + (mapping==null));
		if(mapping == null){
			//log.debug("mapping is null; will try to load it");
			try {
				EntityResolver resolver = new EntityResolver() {
					public InputSource resolveEntity(String publicId, String systemId) {
						if ( publicId.equals( "-//EXOLAB/Castor Object Mapping DTD Version 1.0//EN" ) ) {
							InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("mapping.dtd");
							return new InputSource( in );
						}
						return null;
					}
				};
				org.xml.sax.InputSource mappIS = new org.xml.sax.InputSource(Thread.currentThread().getContextClassLoader().getResourceAsStream(mappingFileName));
				Mapping localMapping = new Mapping();
				localMapping.setEntityResolver(resolver);
				localMapping.loadMapping(mappIS);
				return localMapping;
			} catch (Exception e) {
				System.out.println("Error reading default xml mapping file: " + e.getMessage());				
				log.error("Error reading default xml mapping file: ", e); 
				throw new XMLUtilityException("Error reading default xml mapping file ", e);
			}
		}
		return mapping;
	}
	
	public synchronized Object fromXML(java.io.Reader input)throws XMLUtilityException{
		Object beanObject;

		org.exolab.castor.xml.Unmarshaller unmarshaller = null;
		try {
			//log.debug("Creating unmarshaller");
			unmarshaller = new org.exolab.castor.xml.Unmarshaller(this.getMapping());
		} catch (MappingException e) {
			System.out.println("XML mapping file is invalid: " + e.getMessage());
			log.error("XML mapping file is invalid: ", e);
			throw new XMLUtilityException("XML mapping file invalid: ", e);
		} catch (Exception e){
			System.out.println("General Exception caught trying to create unmarshaller: " + e.getMessage());		
			log.error("General Exception caught trying to create unmarshaller: ", e);
			throw new XMLUtilityException("General Exception caught trying to create unmarshaller: ", e);
		}

		try {
			log.debug("About to unmarshal from input ");
			beanObject = unmarshaller.unmarshal(input);
		} catch (MarshalException e) {
			System.out.println("Error marshalling input: " + e.getMessage());			
			log.error("Error marshalling input: ", e);
			throw new XMLUtilityException("Error unmarshalling xml input: " + e.getMessage(),e);
		} catch (ValidationException e) {
			System.out.println("Error in xml validation when unmarshalling xml input: " + e.getMessage());			
			log.error("Error in xml validation when unmarshalling xml input: ", e);
			throw new XMLUtilityException("Error in xml validation when unmarshalling xml input: ", e);
		}
		return beanObject;
	}

	public Object fromXML(java.io.File xmlFile)throws XMLUtilityException {
		Object beanObject = null;
		try {
			//log.debug("Reading from file: " + xmlFile.getName());
			FileReader fRead = new FileReader(xmlFile);
			beanObject = fromXML(fRead);
		} catch (FileNotFoundException e) {
			System.out.println("XML input file invalid: " + e.getMessage());
			log.error("XML input file invalid: ", e);
			throw new XMLUtilityException("XML input file invalid: ", e);
		}
		return beanObject;
	}
	public Object getBaseUnmarshaller(){
		return this.getUnmarshaller();
	}

	public Unmarshaller getUnmarshaller() {
		return this.unmarshaller;
	}

}
