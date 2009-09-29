package gov.nih.nci.codegen.transformer;

import gov.nih.nci.cadsr.domain.PermissibleValue;
import gov.nih.nci.cadsr.umlproject.domain.Project;
import gov.nih.nci.cadsr.umlproject.domain.UMLAttributeMetadata;
import gov.nih.nci.codegen.Artifact;
import gov.nih.nci.codegen.ArtifactHandler;
import gov.nih.nci.codegen.GenerationException;
import gov.nih.nci.codegen.GeneratorError;
import gov.nih.nci.codegen.GeneratorErrors;
import gov.nih.nci.codegen.Transformer;
import gov.nih.nci.codegen.util.TransformerUtils;
import gov.nih.nci.ncicb.xmiinout.domain.UMLModel;
import gov.nih.nci.system.applicationservice.ApplicationService;
import gov.nih.nci.system.client.ApplicationServiceProvider;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

/**
 * @author Daniel Dumitru
 *
 */
public abstract class UMLValidatorJETTransformer implements Transformer
{
	private static Logger log = Logger.getLogger(UMLValidatorJETTransformer.class);
	
	private ArtifactHandler artifactHandler;
	
	private Map<String, Object> configurationParams;
	
	private boolean enabled = true;
	
	private String namespacePrefix;
	
	private String serviceURL;
	
	private String name = UMLModelJETTransformer.class.getName();
	
	protected TransformerUtils transformerUtils;
	
	private Map<String,String> caDSREnumMap = new HashMap<String,String>();
	
	private static final String PROJECT_SHORT_NAME = "projectShortName";
	private static final String PROJECT_VERSION = "version";

	public void setTransformerUtils(TransformerUtils transformerUtils) {
		this.transformerUtils = transformerUtils;
	}
	
	/**
	 * @param artifactHandler the artifactHandler to set
	 */
	public void setArtifactHandler(ArtifactHandler artifactHandler)
	{
		this.artifactHandler = artifactHandler;
	}
	
	public GeneratorErrors execute(UMLModel model)
	{
		GeneratorErrors errors = new GeneratorErrors();
		try 
		{
			initMapUsingGMENamespace();
			Artifact artifact = executeTemplate(model, configurationParams);
			artifactHandler.handleArtifact(artifact);
		} 
		catch (GenerationException e) 
		{
			errors.addError(new GeneratorError(getName() + ": Error while generating artifact for the model",e));
		}
		return errors;
	}

	public GeneratorErrors validate(UMLModel model)
	{
		return null;
	}
	
	protected abstract Artifact executeTemplate(UMLModel model, Map<String, Object> configurationParams) throws GenerationException;

	public void setConfigurationParams(Map<String, Object> configurationParams) {
		this.configurationParams = configurationParams;
	}	
	
	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
	}
	
	public Boolean isEnabled() {
		return enabled;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getNamespacePrefix() {
		return namespacePrefix;
	}

	public void setNamespacePrefix(String namespacePrefix) {
		this.namespacePrefix = namespacePrefix;
	}
	
	public String getServiceURL() {
		return serviceURL;
	}

	public void setServiceURL(String serviceURL) {
		this.serviceURL = serviceURL;
	}
	
	protected void initMapUsingGMENamespace() throws GenerationException {
		try
		{

			//Application Service retrieval for secured system
			//ApplicationService appService = ApplicationServiceProvider.getApplicationService("userId","password");
			ApplicationService appService;
			if (serviceURL == null || serviceURL.length() == 0){
				log.warn("Service URL is unexpectedly null. Retrieving Application service from ApplicationServiceProvider instead.");
				appService = ApplicationServiceProvider.getApplicationService();
			} else {
				log.debug("Retrieving Application service from URL: " + serviceURL);
				appService = ApplicationServiceProvider.getApplicationServiceFromUrl(serviceURL);
			}
			
			Map<String,String>criteriaProps = getCriteriaPropertiesFromNamespace();
			
			String projectShortName = criteriaProps.get(PROJECT_SHORT_NAME);
			String version = criteriaProps.get(PROJECT_VERSION);
			
			if (projectShortName == null || !(projectShortName.length()>0) || version == null || !(version.length()>0))
				throw new GenerationException("Error parsing NAMESPACE_PREFIX property set in deploy.properties file. Parsed Classification Scheme (UML Project shortname) is: [" + projectShortName +"]; parsed Classification Scheme Version is: [" + version + "].");
			
			Project proj = new Project();
			proj.setShortName(projectShortName);
			proj.setVersion(version);
			log.debug("Searching for " + Project.class.getName() + " using the following search criteria:");
			log.debug("\tProject short name: " + proj.getShortName());
			log.debug("\tProject version " + proj.getVersion());

			Collection<Object> results = appService.search("gov.nih.nci.cadsr.umlproject.domain.Project", proj);
			Collection<UMLAttributeMetadata> attrResults;
			Collection<Object> permValueResults;
			
			String path = 
				"gov.nih.nci.cadsr.domain.PermissibleValue," +
				"gov.nih.nci.cadsr.domain.ValueDomainPermissibleValue," +
				"gov.nih.nci.cadsr.domain.EnumeratedValueDomain," +
				"gov.nih.nci.cadsr.domain.ValueDomain," +
				"gov.nih.nci.cadsr.domain.DataElement," + 
				"gov.nih.nci.cadsr.umlproject.domain.UMLAttributeMetadata";
			
			for(Object obj : results)
			{
				Project project = (Project)obj;
				
				log.debug("Printing gov.nih.nci.cadsr.umlproject.domain.Project");
				log.debug("\tId: "+project.getId());
				log.debug("\tVersion: "+project.getVersion());
				log.debug("\tShortName: "+project.getShortName());
				log.debug("\tLongName: "+project.getLongName());
				log.debug("\tGmeNamespace: "+project.getGmeNamespace());
				
				//Get associated attributes
				attrResults = project.getUMLAttributeMetadataCollection();
				//String umlClassName = null;
				for(Object attr : attrResults)
				{
					log.debug("*******\n ");
					UMLAttributeMetadata attribute = (UMLAttributeMetadata)attr;
					
					//umlClassName = attribute.getUMLClassMetadata().getFullyQualifiedName();
					
					log.debug("Printing gov.nih.nci.cadsr.umlproject.domain.UMLAttributeMetadata");
					log.debug("\tId: "+attribute.getId());
					log.debug("\tName: "+attribute.getName());
					log.debug("\tFullyQualifiedName: "+attribute.getFullyQualifiedName());
					//log.debug("\tUML Class FullyQualifiedName: "+umlClassName);
					
					//Perform NestedCriteria query for PermissibleValue
					log.debug("=======\n ");
					UMLAttributeMetadata attrCriteria = new UMLAttributeMetadata();
					attrCriteria.setId(attribute.getId());
					permValueResults = appService.search(path, attrCriteria);
					log.debug("permValueResults size(): " + permValueResults.size());
					
					if (permValueResults.size() > 0){
						//Build enumerated value string, which delimits values using a "|"
						log.debug("-------\n ");
						
						StringBuilder sb = new StringBuilder();
						Iterator<Object> iter = permValueResults.iterator();
						PermissibleValue value = (PermissibleValue)iter.next();
						
						log.debug("Printing gov.nih.nci.cadsr.domain.PermissibleValue");
						log.debug("\tId: "+value.getId());
						log.debug("\tValue: "+value.getValue());

						sb.append(value.getValue());
						
						while (iter.hasNext()){
							value = (PermissibleValue)iter.next();
							
							log.debug("Printing gov.nih.nci.cadsr.domain.PermissibleValue");
							log.debug("\tId: "+value.getId());
							log.debug("\tValue: "+value.getValue());
							
							sb.append("|").append(value.getValue());
						}
						
						//String key = umlClassName + ":" + attribute.getName();
						String key = attribute.getFullyQualifiedName();
						log.debug("Adding caDSREnumMap entry for attribute " + key + ".  Enum value string is: " + sb.toString());
						
						if (caDSREnumMap.containsKey(key)){
							throw new GenerationException("Duplicate caDSR UMLAttribute found: " + key);
						}
						caDSREnumMap.put(key, sb.toString());
					}
				}
			}
			
		}catch(Exception e)
		{
			log.error("Error retrieving caDSR Permissible Value Enumerations: " + e.getMessage(),e);
			throw new GenerationException("Error retrieving caDSR Permissible Value Enumerations: " + e.getMessage(),e);
		}
	}
		
	protected String getCaDSREnumPattern(String fqcnAttributeName){
		return caDSREnumMap.get(fqcnAttributeName);
	}
	
	private Map<String,String> getCriteriaPropertiesFromNamespace() throws GenerationException {
		
		//Check that the namespacePrefix property has been set
		if (namespacePrefix == null){
			log.error("NAMESPACE_PREFIX property has not been set in deploy.properties.  Use format:  gme://<<Classification Scheme (UML Project ShortName)>>.<<Context>>/<<Classification Scheme Version>>/<<<Classification Scheme Item> (Optional)>>");
			throw new GenerationException("NAMESPACE_PREFIX property has not been set in deploy.properties.  Use format:  gme://<<Classification Scheme (UML Project ShortName)>>.<<Context>>/<<Classification Scheme Version>>/<<<Classification Scheme Item> (Optional)>>");				
		}

		//Check that the NAMESPACE_PREFIX property format is correct
		StringTokenizer tokens = new StringTokenizer(namespacePrefix, "/");
		log.debug("Namespace prefix ('/') tokenizer count: " + tokens.countTokens());
		
		if (!tokens.hasMoreTokens() || tokens.countTokens() < 3){
			log.error("Format of NAMESPACE_PREFIX property in deploy.properties is invalid.  Use format:  gme://<<Classification Scheme (UML Project ShortName)>>.<<Context>>/<<Classification Scheme Version>>/<<<Classification Scheme Item> (Optional)>>");
			throw new GenerationException("Format of NAMESPACE_PREFIX property in deploy.properties is invalid.  Use format:  gme://<<Classification Scheme (UML Project ShortName)>>.<<Context>>/<<Classification Scheme Version>>/<<<Classification Scheme Item> (Optional)>>");				
		}
		
		String gmePrefix = tokens.nextToken().trim();
		log.debug("gmePrefix: " + gmePrefix);
		
		String classificationSchemeAndContext = tokens.nextToken().trim();
		log.debug("classificationSchemeAndContext: " + classificationSchemeAndContext);
		
		String version = tokens.nextToken().trim();
		log.debug("version: " + version);
		
		String projectShortName = classificationSchemeAndContext.substring(0, classificationSchemeAndContext.indexOf('.'));
		log.debug("projectShortName: " + projectShortName);
		
		Map<String,String> criteriaProps = new HashMap<String,String>();
		criteriaProps.put(PROJECT_SHORT_NAME, projectShortName);
		criteriaProps.put(PROJECT_VERSION, version);
		
		return criteriaProps;
		
	}
}