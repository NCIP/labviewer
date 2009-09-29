package gov.nih.nci.system.client.util.xml;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import org.apache.log4j.Logger;
import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.mapping.MappingException;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.ValidationException;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

public class caCOREMarshaller implements gov.nih.nci.system.client.util.xml.Marshaller {
	
	private static Logger log= Logger.getLogger(caCOREMarshaller.class.getName());

	private Marshaller marshaller;
	private Mapping mapping;
	private String mappingFileName;

	/* Validation is turned off by default to improve performance */
	private boolean validation;

	/**
	 * Creates an caCOREMarshaller instance
	 */
	public caCOREMarshaller(String mappingFileName, boolean validation) {
		this.mappingFileName = mappingFileName;
		this.validation = validation;
	}

	/**
	 *
	 * @return default mapping file being used for xml serialziation/deserialization
	 */
	public Mapping getMapping() throws XMLUtilityException {
		/* if no mapping file explicity specified then load the default */
		if(mapping == null){
			//log.debug("mappingFileName: " + mappingFileName);
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
				
				InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(mappingFileName);
				
				// Uncomment the following if you wish to see the contents of the mapping file
//				ByteArrayOutputStream baos = new ByteArrayOutputStream();
//				int n, result = 0;
//				byte[] b;
//				while (true) {
//					n = is.available(); // available bytes without blocking
//					if ( n > 0 ) {
//						b = new byte[n];
//						result = is.read( b );
//						if ( result == -1 ) { break; }
//						baos.write( b, 0, n );
//					} else { break; }
//				} // end while
//				
//				baos.writeTo(System.out);
				
				org.xml.sax.InputSource mappIS = new org.xml.sax.InputSource(is);
				//log.debug("mappIS: " + mappIS);
				Mapping localMapping = new Mapping();
				localMapping.setEntityResolver(resolver);
				localMapping.loadMapping(mappIS);
				return localMapping;
			} catch (Exception e) {
				System.out.println("Error reading default xml mapping file " + e.getMessage());
				log.error("Error reading default xml mapping file ", e); 
				throw new XMLUtilityException("Error reading default xml mapping file " + e.getMessage(), e);
			}
		}
		return mapping;
	}

	public String toXML(Object beanObject) throws XMLUtilityException {
		StringWriter strWriter = new StringWriter();
		this.toXML(beanObject, strWriter);
		return strWriter.toString();
	}

	public void toXML(Object beanObject, java.io.Writer stream) throws XMLUtilityException{

		org.exolab.castor.xml.Marshaller marshaller = null;
		try {

			marshaller = new org.exolab.castor.xml.Marshaller(stream);
		} catch (IOException e) {
			System.out.println("Output stream invalid: " + e.getMessage());
			log.error("Output stream invalid: ", e);
			throw new XMLUtilityException("Output stream invalid "  + e.getMessage(), e);
		}
		try{
			Mapping mapping = this.getMapping();
			//log.debug("mapping: " + mapping);
		
			marshaller.setMapping(mapping);
		}catch (MappingException e) {
			System.out.println("The mapping file is invalid: " + e.getMessage());
			log.error("The mapping file is invalid: ", e);
			throw new XMLUtilityException("The mapping file is invalid "  + e.getMessage(), e);
		}

		try {
			/** Disabled to improve performance **/
			marshaller.setMarshalAsDocument(true);
			marshaller.setDebug(true);
			marshaller.setSuppressNamespaces(false);
			marshaller.setValidation(this.validation);
			marshaller.marshal(beanObject);
		} catch (MarshalException e) {
			System.out.println("Error in marshalling object: " + e.getMessage());
			log.error("Error in marshalling object: ", e);
			throw new XMLUtilityException(e.getMessage(),e);
		} catch (ValidationException e) {
			System.out.println("Error in xml validation of marshalled object: " + e.getMessage());
			log.error("Error in xml validation of marshalled object: ", e);
			throw new XMLUtilityException(e.getMessage(), e);
		}
	}

	public Object getBaseMarshaller(){
		return (Object)this.getMarshaller();
	}

	public Marshaller getMarshaller(){
		return marshaller;
	}
}
