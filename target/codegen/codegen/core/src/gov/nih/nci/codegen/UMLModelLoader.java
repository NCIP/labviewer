package gov.nih.nci.codegen;

import java.io.IOException;

import org.apache.log4j.Logger;

import gov.nih.nci.codegen.util.DebugUtils;
import gov.nih.nci.ncicb.xmiinout.domain.UMLModel;
import gov.nih.nci.ncicb.xmiinout.handler.HandlerEnum;
import gov.nih.nci.ncicb.xmiinout.handler.XmiException;
import gov.nih.nci.ncicb.xmiinout.handler.XmiHandlerFactory;
import gov.nih.nci.ncicb.xmiinout.handler.XmiInOutHandler;

/**
 * This class reads the XMI file and loads the UML model.
 * The constructor should be called only once in the system. 
 * 
 * @author Satish Patel
 *
 */
public class UMLModelLoader
{
	private static Logger log = Logger.getLogger(UMLModelLoader.class);
			
	private UMLModel model;
	
	private boolean printModel;
	
	public UMLModelLoader(String fileName, String fileType) throws GenerationException
	{
		if(model!=null) return;
		loadModel(fileName, fileType);
		
		if(printModel)
			DebugUtils.printModel(model);			
	}
	
	public UMLModel getUMLModel()
	{
		return model;
	}

	private void loadModel(String xmiFileName, String fileType) throws GenerationException
	{
		
		log.debug("Reading XMI File");
		
		XmiInOutHandler xmiHandler;
		if("EA".equalsIgnoreCase(fileType))
			xmiHandler = XmiHandlerFactory.getXmiHandler(HandlerEnum.EADefault);
		else if("ARGO".equalsIgnoreCase(fileType))
			xmiHandler = XmiHandlerFactory.getXmiHandler(HandlerEnum.ArgoUMLDefault);
		else
			throw new GenerationException("Can not instantiate UML Model Loader for specified file type: "+fileType);
			
		try
		{
			xmiHandler.load(xmiFileName);
			log.debug("XMI Loaded in Memory");
			
			model = xmiHandler.getModel();			
		} 
		catch (XmiException e)
		{
			log.error("XMI Error reading XMI file: Malformed XMI file: ", e);
			throw new GenerationException("XMI Error reading XMI file: Malformed XMI file: ", e);
		} 
		catch (IOException e)
		{
			log.error("IO Error reading XMI file: ",e);
			throw new GenerationException("IO Error reading XMI file: ", e);
		}
		catch (Exception e){
			log.error("Unknown error: ",e);
			throw new GenerationException("Unknown error: ", e);			
		}

		log.debug("UML Model retrieved");
	}
	
}