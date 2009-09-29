package gov.nih.nci.codegen.artifact;

import gov.nih.nci.codegen.Artifact;
import gov.nih.nci.codegen.util.TransformerUtils;
import gov.nih.nci.ncicb.xmiinout.domain.UMLClass;
import gov.nih.nci.ncicb.xmiinout.domain.UMLInterface;
import gov.nih.nci.ncicb.xmiinout.domain.UMLPackage;

/**
 * 
 * @author Satish Patel
 */
public class BaseArtifact implements Artifact
{
	private String content;
	private String sourceName;
	protected TransformerUtils transformerUtils;
	

	public BaseArtifact(TransformerUtils transformerUtils) {
		this.transformerUtils = transformerUtils;
	}
	/* (non-Javadoc)
	 * @see gov.nih.nci.codegen.Artifact#getContent()
	 */
	public String getContent()
	{
		return content;
	}
	/**
	 * @param content the content to set
	 */
	public void setContent(String content)
	{
		this.content = content;
	}

	/* (non-Javadoc)
	 * @see gov.nih.nci.codegen.Artifact#getSourceName()
	 */
	public String getSourceName() {
		return sourceName;
	}
	
	/**
	 * Creates the source name for the artifact from the <code>klass</code>
	 * 
	 * @param klass
	 */
	public void createSourceName(UMLClass klass) {
		sourceName = transformerUtils.getFQCN(klass).replace('.','/');
	}	
	
	/**
	 * Creates the source name for the artifact from the <code>interface</code>
	 * 
	 * @param interfaze
	 */
	public void createSourceName(UMLInterface interfaze) {
		sourceName = transformerUtils.getFQCN(interfaze).replace('.','/');
	}	
	
	
	/**
	 * Creates the source name for the artifact from the <code>pkg</code>
	 *
	 * @param pkg
	 */
	public void createSourceName(String sourceName) {
		this.sourceName = sourceName;
	}		
		
}