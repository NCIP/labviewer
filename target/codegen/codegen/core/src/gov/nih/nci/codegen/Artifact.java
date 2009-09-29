package gov.nih.nci.codegen;


/**
 * Provides a way to access the generated output
 * 
 * @author Satish Patel
 *
 */
public interface Artifact
{
	/**
	 * Returns the generated content
	 * 
	 * @return the content
	 */
	public String getContent();
	
	/**
	 * Returns the name of the component from which the artifact was generated
	 * 
	 * @return
	 */
	public String getSourceName();

	
}