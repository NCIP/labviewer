package gov.nih.nci.codegen.handler;

import gov.nih.nci.codegen.Artifact;
import gov.nih.nci.codegen.ArtifactHandler;
import gov.nih.nci.codegen.GenerationException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.log4j.Logger;

/**
 * @author Satish Patel
 *
 */
public class FileHandler implements ArtifactHandler
{
	private static Logger log = Logger.getLogger(FileHandler.class);
	
	private String outputDir;
	private String fileName;
	private String prefix;
	private String suffix;
	private Boolean useArtifactSource;
	/**
	 * @return the fileName
	 */
	public String getFileName()
	{
		return fileName;
	}
	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}
	/**
	 * @return the outputDir
	 */
	public String getOutputDir()
	{
		return outputDir;
	}
	/**
	 * @param outputDir the outputDir to set
	 */
	public void setOutputDir(String outputDir)
	{
		this.outputDir = outputDir;
	}
	/**
	 * @return the prefix
	 */
	public String getPrefix()
	{
		return prefix;
	}
	/**
	 * @param prefix the prefix to set
	 */
	public void setPrefix(String prefix)
	{
		this.prefix = prefix;
	}
	/**
	 * @return the suffix
	 */
	public String getSuffix()
	{
		return suffix;
	}
	/**
	 * @param suffix the suffix to set
	 */
	public void setSuffix(String suffix)
	{
		this.suffix = suffix;
	}
	/**
	 * @return the useArtifactSource
	 */
	public Boolean getUseArtifactSource()
	{
		return useArtifactSource;
	}
	/**
	 * @param useArtifactSource the useArtifactSource to set
	 */
	public void setUseArtifactSource(Boolean useArtifactSource)
	{
		this.useArtifactSource = useArtifactSource;
	}
	/* (non-Javadoc)
	 * @see gov.nih.nci.codegen.ArtifactHandler#handleArtifact(gov.nih.nci.codegen.Artifact)
	 */
	public void handleArtifact(Artifact artifact) throws GenerationException
	{
		String fName = prepareFileName(artifact);
		try
		{
			log.debug("Writing an artifact to a file "+fName+" in "+outputDir+" directory");
			File f = new File(outputDir,fName);
			File p = f.getParentFile();
			if (!p.exists())
				p.mkdirs();
			
			f.createNewFile();
			FileWriter out = new FileWriter(f);
			out.write(artifact.getContent());
			out.close();
			log.debug("Wrote an artifact to a file "+fName+" in "+outputDir+" directory");
		}
		catch (IOException e) 
		{
			throw new GenerationException("Error writing to file : "+fName,e);
		}
	}
	
	/**
	 * Prepares the name of the file in which the artifact is to be stored
	 * @param artifact
	 * @return
	 */
	private String prepareFileName(Artifact artifact)
	{
		String fName = null;

		if (useArtifactSource == true) {
			fName = artifact.getSourceName();
		} else {
			fName = fileName;
		}

		String name = (prefix == null ? "" : prefix) + fName + (suffix == null ? "" : suffix);
		
		return name;
	}
}