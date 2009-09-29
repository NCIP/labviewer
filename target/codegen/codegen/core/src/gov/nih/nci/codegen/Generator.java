package gov.nih.nci.codegen;

import gov.nih.nci.codegen.util.ObjectFactory;
import gov.nih.nci.ncicb.xmiinout.domain.UMLModel;

import java.util.List;

import org.apache.log4j.Logger;

/**
 * Generates the code from the {@link #model}. 
 * <code>Generator</code> class iterates over available {@link gov.nih.nci.codegen.Validator} 
 * in the {@link #validators} and reports errors if any discovered. Subsequent to the 
 * validation, <code>Generator</code> validates all the  {@link gov.nih.nci.codegen.Transformer}
 * from the {@link #transformers}</code> list in a sequence. After validating the 
 * {@link #transformers} list, it iterates over all the {@link gov.nih.nci.codegen.Transformer}
 * in the {@link #transformers} list to transform the {@link #model} into desired 
 * artifacts.
 * 
 *  <p><code>Generator</code> can exit from execution only for following reasons
 *  <ul>
 *  <li>Errors discovered at the end of the execution of all the {@link gov.nih.nci.codegen.Validator} contained in the {@link #validators} list 
 *  <li>Errors discovered at the end of the execution of validation method of all the 
 *  {@link gov.nih.nci.codegen.Transformer} contained in the {@link #transformers} list 
 *  <li>Errors discovered at the end of the execution of execute method of all the 
 *  {@link gov.nih.nci.codegen.Transformer} contained in the {@link #transformers} list 
 *  </ul>
 *  </p>
 * 
 * @author Satish Patel
 * @see gov.nih.nci.ncicb.xmiinout.domain.UMLModel
 * @see gov.nih.nci.codegen.Validator
 * @see gov.nih.nci.codegen.Transformer
 */

public class Generator
{
	private static Logger log = Logger.getLogger(Generator.class);

	/**
	 * UMLModel from which the code is to be generated
	 */
	private UMLModel model;
	
	/**
	 * List of {@link gov.nih.nci.codegen.Validator} which can validate the {@link #model}
	 */
	private List<Validator> validators;
	
	/**
	 * List of {@link gov.nih.nci.codegen.Transformer} which can transform {@link #model} in to code
	 */
	private List<Transformer> transformers;
	
	/**
	 * Contains errors discovered during the code generation process
	 */
	private GeneratorErrors errors = new GeneratorErrors();


	/**
	 * Entry point into the <code>Generator</code> after all the configuration is completed 
	 * by the calling class. The sequence of execution is as follows
	 * 
	 * <ol>
	 * <li>Execute all the {@link gov.nih.nci.codegen.Validator#validate(UMLModel)} instances contained in the 
	 * {@link #validators} list.
	 * <li>Report error (if any) discovered by the previous step
	 * <li>Execute {@link gov.nih.nci.codegen.Transformer#validate(UMLModel))} method of the 
	 * {@link gov.nih.nci.codegen.Transformer} contained in the {@link #transformers} list.
	 * <li>Report error (if any) discovered by the previous step
	 * <li>Execute {@link gov.nih.nci.codegen.Transformer#execute(UMLModel))} method of the 
	 * {@link gov.nih.nci.codegen.Transformer} contained in the {@link #transformers} list.
	 * <li>Report error (if any) discovered by the previous step
	 * </ol>
	 */
	public void execute()
	{
		for(Validator validator:validators)
			if(validator.isEnabled())
			{
				log.info("Executing " + validator.getName());
				errors.addErrors(validator.validate(model));
			}
			else
			{
				log.info("Skipped execution of " + validator.getName());
			}

		reportErrors(errors);

		for(Transformer transformer:transformers)
			if(transformer.isEnabled())
			{
				log.info("Executing validator for " + transformer.getName());
				errors.addErrors(transformer.validate(model));
			}
			else
			{
				log.info("Skipped validation of " + transformer.getName());
			}
			
		
		reportErrors(errors);
		
		for(Transformer transformer:transformers)
			if(transformer.isEnabled())
			{
				log.info("Executing " + transformer.getName());
				errors.addErrors(transformer.execute(model));
			}
			else
			{
				log.info("Skipped execution of " + transformer.getName());
			}
				

		reportErrors(errors);
		
		log.debug("Code generation complete");
	}
	
	/**
	 * Reports string representation of all the {@link GeneratorError} contained in the 
	 * <code>errors</code> to the logger. If any error was reported then it performs 
	 * a forced exit from the system.
	 * 
	 * @param errors
	 */
	private void reportErrors(GeneratorErrors errors)
	{
		if(!errors.isEmpty())
		{
			StringBuilder buffer = new StringBuilder();
			for(GeneratorError error:errors.getErrors())
				buffer.append("\n"+error.toString());
			log.error(buffer.toString());
			System.exit(-1);
		}
	}

	

	/**
	 * Default constructor
	 * 
	 * @param model
	 * @param validators
	 * @param transformers
	 */
	public Generator(UMLModel model, List<Validator> validators, List<Transformer> transformers)
	{
		this.model = model;
		this.validators = validators;
		this.transformers = transformers;
	}
	
	
	/**
	 * Executes the <code>Generator</code>
	 * 
	 * @param args First parameter for the command line argument should be the name of the file which contains the configuration for the codegen. If there are no command line parameters specified then the file name defaults to CodegenConfig.xml
	 * @throws Exception
	 */
	public static void main(String args[]) throws Exception
	{
		String fileName = (args!=null && args.length >0) ? args[0] : "CodegenConfig.xml";
		log.info("Initializing codegen configuration from "+fileName+"\n");

		ObjectFactory.initialize(fileName);
		Generator generator = (Generator)ObjectFactory.getObject("Generator");
		log.info("Configuration read from the file\n");
		log.info("Executing codegen\n");
		generator.execute();
		log.info("Codegen execution complete\n");		
	}	
}