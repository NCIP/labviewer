package gov.nih.nci.caxchange.servicemix.bean.validation;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.xml.XMLConstants;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

public class FileSystemSchemaFactory implements CaxchangeSchemaFactory {

	private String schemaDirectoryLocation;
	private static SchemaFactory schemaFactory = null;
	private static Map<String,Schema> schemaCache = null;
	private Logger logger = LogManager.getLogger(FileSystemSchemaFactory.class);
	
	public Schema getSchema(String namespaceName) throws SchemaFactoryException {
		
		Schema schema = null;
		try {
		   schema = schemaCache.get(namespaceName);
		   File schemaFile = null;
		   if (schema == null) {
		      String filename = getFileName(namespaceName);
		      schemaFile = new File(schemaDirectoryLocation+ File.separator +filename);
		      if (!schemaFile.canRead()){
		    	 throw new SchemaFactoryException("Cannot read the schema file for namespace:"+namespaceName+" file name:"+filename);	
		      }else {
		    	  //Parse and cache the schema
		    	  schema = schemaFactory.newSchema(schemaFile);
		    	  schemaCache.put(namespaceName, schema);
		      }
		   
		   }
		} catch (SAXException e2) {
			throw new SchemaFactoryException("Error parsing schema for namespace "+namespaceName, e2);
		}
		
		return schema;	
	}
	
	public String getFileName(String namespace) throws SchemaFactoryException {
		StringBuffer buffer = new StringBuffer();
		boolean pathSerparator = false;
		for(char c:namespace.toCharArray()){
			if (Character.isLetterOrDigit(c)){
				buffer.append(c);
				pathSerparator = false;
			}else if (c=='.'){
				buffer.append(c);
				pathSerparator = false;
			}else if (!pathSerparator) {
				buffer.append('_');
				pathSerparator = true;
			}
		}
		buffer.append(".xsd");
		return buffer.toString();
	}

	public void init() {
		//verify the directory exists
		File schemaDirectory = new File(schemaDirectoryLocation);
		logger.info("schema directory is "+schemaDirectoryLocation);
		if (!schemaDirectory.exists()) {
			logger.info("schema directory does not exist. "+schemaDirectoryLocation);
		   throw new IllegalStateException("Schema files cache location does not exist."+schemaDirectoryLocation);
		}
		//create the schema cache
		if (schemaCache == null) {
		   schemaCache = new HashMap<String, Schema>();
		}
		
		if (schemaFactory == null) {
			 schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		}

	}

	public String getSchemaDirectoryLocation() {
		return schemaDirectoryLocation;
	}

	public void setSchemaDirectoryLocation(String schemaDirectoryLocation) {
		this.schemaDirectoryLocation = schemaDirectoryLocation;
	}

}
