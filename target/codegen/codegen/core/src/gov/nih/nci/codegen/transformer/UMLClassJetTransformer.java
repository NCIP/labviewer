package gov.nih.nci.codegen.transformer;

import gov.nih.nci.codegen.Artifact;
import gov.nih.nci.codegen.ArtifactHandler;
import gov.nih.nci.codegen.GenerationException;
import gov.nih.nci.codegen.GeneratorError;
import gov.nih.nci.codegen.GeneratorErrors;
import gov.nih.nci.codegen.Transformer;
import gov.nih.nci.codegen.util.TransformerUtils;
import gov.nih.nci.ncicb.xmiinout.domain.UMLClass;
import gov.nih.nci.ncicb.xmiinout.domain.UMLModel;

import java.util.Collection;

import org.apache.log4j.Logger;

/**
 * @author Satish Patel
 *
 */
public abstract class UMLClassJetTransformer implements Transformer
{
	
	private static Logger log = Logger.getLogger(UMLClassJetTransformer.class);
	
	private ArtifactHandler artifactHandler;

	private boolean enabled = true;
	
	private String name = UMLClassJetTransformer.class.getName();
	
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
		Collection<UMLClass> classes = null;

		try {
			classes = getAllClasses(model);
		} catch(GenerationException ge){
			errors.addError(new GeneratorError(getName() + ": Error while getting classes to process: ", ge));
		}
		
		for(UMLClass klass:classes)
			try
		{
				Artifact artifact = executeTemplate(model, klass);
				artifactHandler.handleArtifact(artifact);
		}
		catch(GenerationException ge)
		{
			errors.addError(new GeneratorError(getName() + ": Error while generating artifact for the class "+klass.getName()+"\n\t",ge));
		}
		return errors;
	}
	
	public abstract Artifact executeTemplate(UMLModel model, UMLClass klass) throws GenerationException;

	public GeneratorErrors validate(UMLModel model)
	{
		return null;
	}
	
	protected Collection<UMLClass> getAllClasses(UMLModel model) throws GenerationException
	{
		return transformerUtils.getAllClasses(model);
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