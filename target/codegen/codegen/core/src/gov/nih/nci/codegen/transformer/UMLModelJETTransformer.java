package gov.nih.nci.codegen.transformer;

import gov.nih.nci.codegen.Artifact;
import gov.nih.nci.codegen.ArtifactHandler;
import gov.nih.nci.codegen.GenerationException;
import gov.nih.nci.codegen.GeneratorError;
import gov.nih.nci.codegen.GeneratorErrors;
import gov.nih.nci.codegen.Transformer;
import gov.nih.nci.codegen.util.TransformerUtils;
import gov.nih.nci.ncicb.xmiinout.domain.UMLModel;

import java.util.Map;

import org.apache.log4j.Logger;

/**
 * @author Satish Patel
 *
 */
public abstract class UMLModelJETTransformer implements Transformer
{
	private static Logger log = Logger.getLogger(UMLModelJETTransformer.class);
	
	private ArtifactHandler artifactHandler;
	
	private Map<String, Object> configurationParams;
	
	private boolean enabled = true;
	
	private String name = UMLModelJETTransformer.class.getName();
	
	protected TransformerUtils transformerUtils;

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
}