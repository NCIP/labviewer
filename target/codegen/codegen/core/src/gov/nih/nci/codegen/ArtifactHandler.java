package gov.nih.nci.codegen;


/**
 * Endpoint for the artifact is handled by the ArtifactHandler. Implementation of this interface will be responsible for
 * managing the artifact (e.g. persisting on the file system)
 *  
 * @author Satish Patel
 *
 */
public interface ArtifactHandler{

	public void handleArtifact(Artifact artifact) throws GenerationException;
	
}