package gov.nih.nci.caxchange.servicemix.bean.validation;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.xml.XMLConstants;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.projectmobius.client.gme.ImportInfo;
import org.projectmobius.common.GridServiceResolver;
import org.projectmobius.common.MobiusException;
import org.projectmobius.common.Namespace;
import org.projectmobius.gme.XMLDataModelService;
import org.projectmobius.gme.client.GlobusGMEXMLDataModelServiceFactory;
import org.xml.sax.SAXException;

/**
 * Gets the schemas from the Globel Model Exchange (GME).
 * 
 * @author marwahah
 *
 */
public class GMESchemaFactory implements CaxchangeSchemaFactory{
	//The url to the GME from which schemas are retrieved
	private String GMEGridServiceLocation;
	//The directory location where the schemas are cached.
	private String cacheDirectoryLocation;
	private static SchemaFactory schemaFactory = null;
	private static Map<String,Schema> schemaCache = null;
	private Logger logger = LogManager.getLogger(GMESchemaFactory.class);
	/**
	 * Gets the schema for the given namespace from GME.
	 * @param namespaceName
	 * @return
	 * @throws SchemaFactoryException
	 */
	public Schema getSchema(String namespaceName) throws SchemaFactoryException {
		
		Schema schema = null;
		try {
		   schema = schemaCache.get(namespaceName);
		   File schemaFile = null;
		   if (GMEGridServiceLocation == null) {
			   throw new SchemaFactoryException("GME url needs to be specified for validating payloads.");
		   }
		   if (schema == null) {
			  //Get the schema from GME or the file cache and parse it.
		      GridServiceResolver.getInstance().setDefaultFactory(new GlobusGMEXMLDataModelServiceFactory());  
  		      Namespace namespace = new Namespace(namespaceName);
		      ImportInfo importInfo= new ImportInfo(namespace);
		      String filename = importInfo.getFileName();
		      XMLDataModelService handle = 
					(XMLDataModelService) GridServiceResolver.getInstance().getGridService(GMEGridServiceLocation); 		
		      schemaFile = new File(cacheDirectoryLocation+ File.separator +filename);
		      if (!schemaFile.canRead()){
		    	 //Schema file is not in the local cache. 
				 handle.cacheSchema(namespace, new File(cacheDirectoryLocation));	
			  }
		      if (!schemaFile.canRead()) {
		    	  throw new SchemaFactoryException("Error getting schema for namespace :"+namespaceName);
		      }else {
		    	  //Parse and cache the schema
		    	  schema = schemaFactory.newSchema(schemaFile);
		    	  schemaCache.put(namespaceName, schema);
		      }
		   
		   }
		} catch (MobiusException e1) {
			throw new SchemaFactoryException("Error while getting schema "+namespaceName, e1);
		} catch (SAXException e2) {
			throw new SchemaFactoryException("Error parsing schema for namespace "+namespaceName, e2);
		}
		
		return schema;		
	}
	
	public void init() {
		//verify the directory exists
		File cacheDirectory = new File(cacheDirectoryLocation);
		logger.info("Cache directory is "+cacheDirectoryLocation);
		if (!cacheDirectory.exists()) {
			logger.info("Cache directory does not exist. "+cacheDirectoryLocation);
				if (!cacheDirectory.mkdir()) {
				   logger.info("Error occurred creating Cache directory. "+cacheDirectoryLocation);
				   throw new IllegalStateException("Schema files cache location does not exist."+cacheDirectoryLocation);
				 }
				
		}
		//create the schema cache
		if (schemaCache == null) {
		   schemaCache = new HashMap<String, Schema>();
		}
		
		if (schemaFactory == null) {
			 schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		}

	}
	
	public String getGMEGridServiceLocation() {
		return GMEGridServiceLocation;
	}
	public void setGMEGridServiceLocation(String url) {
		GMEGridServiceLocation = url;
	}
	public String getCacheDirectoryLocation() {
		return cacheDirectoryLocation;
	}
	public void setCacheDirectoryLocation(String cacheDirectoryLocation) {
		this.cacheDirectoryLocation = cacheDirectoryLocation;
	}

}
