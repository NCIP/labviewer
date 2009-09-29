package gov.nih.nci.codegen;

import gov.nih.nci.ncicb.xmiinout.domain.UMLModel;

/**
 * Component which transforms the model into artifact(s)
 * 
 * @author Satish Patel
 */
public interface Transformer
{
	
	/**
	 * Perform validation of the model.
	 * @param model
	 * @return
	 */
	public GeneratorErrors validate(UMLModel model);
	
	/**
	 * Executes the transformer and generate the artifact(s) from the model
	 * @param model
	 * @return
	 */
	public GeneratorErrors execute(UMLModel model);
	
	/**
	 * Determines if the generator should execute the Transformer or not
	 * @return
	 */
	public Boolean isEnabled();
	
	/**
	 * Return the user configurable (via CodegenConfig.xml) name of the Transformer
	 * @return
	 */
	public String getName();
	
}