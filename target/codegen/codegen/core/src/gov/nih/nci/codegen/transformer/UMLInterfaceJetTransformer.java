package gov.nih.nci.codegen.transformer;

import gov.nih.nci.codegen.Artifact;
import gov.nih.nci.codegen.ArtifactHandler;
import gov.nih.nci.codegen.GenerationException;
import gov.nih.nci.codegen.GeneratorError;
import gov.nih.nci.codegen.GeneratorErrors;
import gov.nih.nci.codegen.Transformer;
import gov.nih.nci.codegen.artifact.BaseArtifact;
import gov.nih.nci.codegen.util.TransformerUtils;
import gov.nih.nci.ncicb.xmiinout.domain.UMLInterface;
import gov.nih.nci.ncicb.xmiinout.domain.UMLModel;

import java.util.Collection;

import org.apache.log4j.Logger;

/**
 * @author Satish Patel
 *
 */
public abstract class UMLInterfaceJetTransformer implements Transformer
{
	
	private static Logger log = Logger.getLogger(UMLInterfaceJetTransformer.class);
	
	private ArtifactHandler artifactHandler;

	private boolean enabled = true;
	
	private String name = UMLInterfaceJetTransformer.class.getName();	
	
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
		Collection<UMLInterface> interfaces = null;

		try {
			interfaces = getAllInterfaces(model);
		} catch(GenerationException ge){
			errors.addError(new GeneratorError(getName() + ": Error while getting interfaces to process: ", ge));
		}
		
		for(UMLInterface interfaze:interfaces)
			try
		{
				Artifact artifact = executeTemplate(model, interfaze);
				artifactHandler.handleArtifact(artifact);
		}
		catch(GenerationException ge)
		{
			errors.addError(new GeneratorError(getName() + ": Error while generating artifact for the interface "+interfaze.getName()+"\n\t",ge));
		}
		
		
		return errors;
	}
	
	public abstract Artifact executeTemplate(UMLModel model, UMLInterface interfaze) throws GenerationException;

	public GeneratorErrors validate(UMLModel model)
	{
		return null;
	}
	
	protected Collection<UMLInterface> getAllInterfaces(UMLModel model) throws GenerationException
	{
		return transformerUtils.getAllInterfaces(model);
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