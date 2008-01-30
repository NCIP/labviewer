package gov.nih.nci.caxchange.client;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;

import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.concurrent.Executors; 
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;


import org.apache.log4j.Logger;

import gov.nih.nci.caxchange.client.exceptions.*;
import gov.nih.nci.caxchange.ws.RoutingAndWorkFlowServiceClient;
import gov.nih.nci.caxchange.ws.RoutingAndWorkflowService;
import gov.nih.nci.caxchange.ws.routingandworkflow.*;
import gov.nih.nci.caxchange.client.GetBytesfromFile;
import gov.nih.nci.caxchange.common.services.constants.TransformationConstants;

import org.codehaus.xfire.client.Client;

import gov.nih.nci.caxchange.client.preprocess.CSVP;


public class CancerCenterClientOLd {
	
	private final ScheduledExecutorService scheduler = 
	       Executors.newScheduledThreadPool(1);
	
	// Property Files
	
	private static String preProcessorPropertiesFile_str;
	private static String programPropertiesFile = "d:/Development/CancerCenterClient/resource/CancerCenterClient.properties";

	// To be set in a property file
	
	private static String location;
	
	private static String WebServiceURL;
	private static String WorkFlowId;
	private static String EndPointListSeparatorChar = ",";
	private static String EndPointsCommaDelimitedString;
	
	private static String userName;
	private static String userPasswd;
	
	private static String inProcessFolder;
	private static String processedFolder;
	private static String errorFolder;
	private static String rawFilesFolder;
	private static String rawFilesBackupFolder;
	
	private static String initialDelay_str;
	private static String pollingInterval_str;
	//private static String pollingTimeUnit_str;
	
	private static long initialDelay_long;
	private static long  pollingInterval_long;
	//private static TimeUnit pollingTimeUnit;

	
	private static File processedDir;
	private static File inProcessDir;
	private static File errorDir;
	
	private static File rawFilesDir;
	private static File rawFilesBackupDirectory;
	
	
	// Map File used for mapping CSV file to HL7V3 format by caAdaptor.
	private static String mapFileName;
	
	// Logging File
	
	private static Logger logger = Logger.getLogger("gov.nih.nci.caxchange.client.CancerCenterClient");
	
	
	public static void main(String[] args) {
		
		String configFilePath = "d:/Development/CancerCenterClient/resource/CancerCenterClient.properties";//System.getProperty("cancer.center.client.properties");
		if(configFilePath==null){
			throw new RuntimeException("Cancer Center Client property is not set as an argument on the java command line.");
		}
		
		File f = new File(configFilePath);
		if(f==null){
			throw new RuntimeException("Cancer Center Client Properties File is not available.");
		}else{
			programPropertiesFile = configFilePath;
			f=null;
		}

		
		logger.debug("Staring Cancer Center Client -- Main Method");
		CancerCenterClientOLd client = new CancerCenterClientOLd();
		boolean success = client.checkSetup();
    	if (success) {    		
   		      client.process();
        } 
    	else
    	{
    		logger.fatal("Fatal: Required Resources not found to run Client Application");
    		logger.fatal("Aborting Client");
    		logger.fatal("Please contact System Admin");
    	}
			
   }
	
   public boolean checkSetup()
   {
	   boolean success=false;
	   try
	   {
		   //Load Properties File
		   if(!loadProperties()) {
			   throw new PropertiesFileException("Exception processing CancerCenterClient.properties file");
		   }
	

		   rawFilesDir=new File(rawFilesFolder);
		    rawFilesBackupDirectory = new File(rawFilesBackupFolder);
		    
			inProcessDir    = new File(inProcessFolder);
		    processedDir = new File(processedFolder);
			errorDir     = new File(errorFolder);
			
			File preProcessorPropertiesFile_file = new File(preProcessorPropertiesFile_str);
			
		   if (!preProcessorPropertiesFile_file.exists()){
			   throw new PropertiesFileException("preProcessorPropertiesFile as defined in CancerCenterClient.properties Does not Exist");   
		   }	
   	       if (!rawFilesDir.exists()) {    	    	 
   	    	 throw new FolderDoesNotExistException("rawFilesFolder as defined in CancerCenterClient.properties  Does Not Exist");           
   	       }
   	       if (!rawFilesDir.isDirectory()) {
   	    	 throw new FolderNotADirectoryException("rawFilesFolder as defined in CancerCenterClient.properties  is not a Folder. It must be a folder");
   	       }
   	       if (!rawFilesBackupDirectory.exists()) {    	    	 
     	    	 throw new FolderDoesNotExistException("rawFilesBackupFolder as defined in CancerCenterClient.properties  Does Not Exist");           
     	   }
     	   if (!rawFilesBackupDirectory.isDirectory()) {
     	    	 throw new FolderNotADirectoryException("rawFilesBackupFolder as defined in CancerCenterClient.properties  is not a Folder. It must be a folder");
     	   }
   	       if (!inProcessDir.exists()) {    	    	 
   	    	 throw new FolderDoesNotExistException("inProcessFolder as defined in CancerCenterClient.properties  Does Not Exist");           
   	       }
   	       if (!inProcessDir.isDirectory()) {
   	    	 throw new FolderNotADirectoryException("inProcessFolder as defined in CancerCenterClient.properties  is not a Folder. It must be a folder");
   	       }
		   if (!processedDir.exists()) {    	    	 
		    	 throw new FolderDoesNotExistException("processedFolder as defined in CancerCenterClient.properties  Does Not Exist");           
		   }
		   if (!processedDir.isDirectory()) {
		    	 throw new FolderNotADirectoryException("processedFolder as defined in CancerCenterClient.properties  is not a Folder. It must be a folder");
		   }
		   if (!errorDir.exists()) {    	    	 
		    	 throw new FolderDoesNotExistException("errorFolder as defined in CancerCenterClient.properties  Does Not Exist");           
		   }
		   if (!errorDir.isDirectory()) {
		    	 throw new FolderNotADirectoryException("errorFolder as defined in CancerCenterClient.properties is not a Folder. It must be a folder");
		   }
		   success=true;
	   }
   	   catch (FolderDoesNotExistException e) {
   		   logger.error(e.getErrorMessage());
		   success=false;
	   }
	   catch (FolderNotADirectoryException e) {
		   logger.error(e.getErrorMessage());
		   success=false;
   	   }
	   catch(PropertiesFileException e){
		   logger.fatal(e.getErrorMessage());
		   success=false;
	   }
	   return success;
   }
   
    public void process() {
   	
        final Runnable fileCheck = new Runnable() {

                public void run() { 
                	try{
                	     logger.debug("Poll the File");
                	    if(!checkPreProcessedFolder())
                	      { 
                	    	logger.error("Error Preprocessing Files.. Aborting"); 
                	    	throw new Exception("Error Preprocessing Files.. Aborting");
                	    }
                	    else 
                	    {
                	    	RoutingAndWorkFlowServiceClient();
                	    }
                	}
                	catch(Exception e)
                	{
                		logger.fatal("Fatal Error in Client.. Aborting");
                	}
                }
        	
         };
        final ScheduledFuture<?> fileCheckHandle = 
            scheduler.scheduleAtFixedRate(fileCheck, initialDelay_long, pollingInterval_long, SECONDS);
    } 
    
    
    public boolean checkPreProcessedFolder()
    {    	
    	logger.debug("In Check Pre Processed Folder");
    	boolean retResult = true;
    	try{
    	    File[] fileList =  rawFilesDir.listFiles();
    	    // Get Current Date and Time for Stamping the file
    	    Date dt = new Date();
    	    long currentTime = dt.getTime();
    	    StringBuffer sbuf = new StringBuffer();
    	    StringBuffer outbuf = new StringBuffer();
    	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss"); 
    	    
    	    sbuf = sdf.format(dt, outbuf, new FieldPosition(0));
    	    
    	    logger.debug("Current Date Time : " + sbuf);
    	    
    	    for (int i=0; i<fileList.length; i++)
    	    {
    	    	//logger.debug("Pre Processing File : " + fileList[i].toString() );
    	    	String fileName = fileList[i].getName().toString();
    	    	//logger.info("File Name : " + fileName );
    	    	
    	    	String fileNameLocationDateTimeStamp =  location + "_" + sbuf +"_"+ fileName;
    	    	String outFile = inProcessFolder + fileNameLocationDateTimeStamp;
    	    	//logger.info("Out File : " + outFile);

    	    		//logger.info("Valid File Name : " + fileName);
    	    	   try{ 
    	    		   logger.debug("Starting CSV Parser");
    	    		   CSVP fileOut = new CSVP(fileList[i].toString(), outFile, preProcessorPropertiesFile_str);
    	    		   
    	    		   //logger.info("Backup Dir : " + rawFilesBackupDirectory);
    	    		   //logger.info("Backup folder : " + rawFilesBackupFolder);
    	    		   //logger.info("fileNameLocationDateTimeStamp : " + fileNameLocationDateTimeStamp);
    	    		   //logger.info("*****");
    	    		   
 		    	    	boolean success = fileList[i].renameTo(new File(rawFilesBackupDirectory, fileNameLocationDateTimeStamp));
    	    		    		    
 		    	    	//logger.info("File Moved to Backup folder");
 		    	    	//logger.info("***********");
 		    	    	if (!success) {    		
 		    	    		logger.error(fileList[i].toString() + "Renamed to : " + fileNameLocationDateTimeStamp + " Was not moved to the backup folder" );
 		    	    		retResult=false;
 		    	        }
 		    	    	else {
 		    	    		logger.debug("File Moved to Backup folder");
 		    	    		retResult = true;
 		    	    	}
 		    	      
    	    	   }
    	    	   catch(Exception e)
    	    	   {
    	    		   retResult=false;
    	    		     logger.error("Error PreProcessing File " + fileList[i].toString());
    	    		     logger.error(e.getMessage());
    	    		     logger.info("Erro Dir : " + errorDir);
    	    		     logger.info("Error folder : " + errorFolder);
    	    		     logger.info("fileNameLocationDateTimeStamp : " + fileNameLocationDateTimeStamp);
    	    		     logger.info("File : " + fileList[i]);
    	    		     logger.info("*****");
    	    	         boolean movesuccess = fileList[i].renameTo(new File(errorDir, fileNameLocationDateTimeStamp));
    	    		   //logger.info("****");
    	    		   
    	    		   if (!movesuccess) {    		
		    	    		logger.error(fileList[i].toString() + "Renamed to : " + errorDir+fileNameLocationDateTimeStamp + " Was not moved to the error folder" );
		    	    		 logger.info("*****");
		    	        }
    	    		   else{
    	    			     logger.error(fileList[i].toString() + "Renamed to : " +errorDir+fileNameLocationDateTimeStamp + " Was moved to the error folder" );
    	    		   }
   
    	    	   } // End of Catch


    	    } // End of For
    	    
    	}
    	catch (Exception e)
    	{
 		   logger.fatal("Error Accesing Files in file System");
    	   retResult=false; 
    	}
    	return retResult;

    }	   
   
	public final void RoutingAndWorkFlowServiceClient() {
		try{
		logger.debug("In RoutingAndWorkFlowServiceClient");
		RoutingAndWorkFlowServiceClient svc = new RoutingAndWorkFlowServiceClient();
		RoutingAndWorkflowService service = svc.getRoutingAndWorkflowServiceSoapPort(WebServiceURL);
		Client client = Client.getInstance(service);
		client.setProperty("mtom-enabled", "true");
		logger.debug("Added MTOM Property");
		RoutingAndWorkflowRequest req = new RoutingAndWorkflowRequest();
		ProcessingInstructions procInst = new ProcessingInstructions();
		MessageProperties mprops      = new MessageProperties();
		Properties props = new Properties();
		Property property = new Property();
		property.setKey(TransformationConstants.MAP_FILE);
		property.setValue(mapFileName);
		List<Property> p = mprops.getProperty();
		p.add(property);
		Workflow wflow = new Workflow();
		wflow.setIdentifier(WorkFlowId);
		procInst.setWorkflow(wflow);
		// EndPoints	
		List<String> endpoints = procInst.getEndpoints();
		// Tokenize
		StringTokenizer st = new StringTokenizer(EndPointsCommaDelimitedString,EndPointListSeparatorChar,false);
	     while (st.hasMoreTokens()) {
	    	 String ep = st.nextToken();
	    	 logger.debug("EndPoint" + ep);
	         endpoints.add(ep);
	     }
		logger.debug("Added Workflow and End Points");
		Credentials cred = new Credentials();
		cred.setUserName(userName);
		cred.setPassword(userPasswd);
		req.setCredentials(cred);
		req.setProcessingInstructions(procInst);
		req.setProperties(mprops);
		
		MessagePayload pld = new MessagePayload(); 
		PayloadTypes pldType = new PayloadTypes();
		File inProcessDir    = new File(inProcessFolder);
		// Delete Zero Byte Files Generated by Preprocessor in case of exceptions.
		File[] inProcessfileList =  inProcessDir.listFiles();
		for (int i=0; i<inProcessfileList.length; i++)
		{
			if ( inProcessfileList[i].length() == 0 )
				inProcessfileList[i].delete(); 
		}
		File[] fileList =  inProcessDir.listFiles();
		GetBytesfromFile gbf = new GetBytesfromFile();
		for (int i=0; i<fileList.length; i++)
	    {
			try{
	    	     logger.info("Processing File : " + fileList[i].toString() );
	    	     byte[] fileBytes = gbf.GetBytesFromFile(fileList[i]);
	    	     pld.setPayloadType(pldType);
	    	     pld.setObject(fileBytes);
     			 req.setPayload(pld);
     			 req.setExternalIdentifier(fileList[i].getName().toString());
     			 logger.info("External Identifier : " + fileList[i].getName().toString() );
     			 
     			 
	    			
	    			try {
	    				logger.info("Invoking WebService");
	    				Acknowledgement ack = service.executeRoutingOrWorkflow(req);
	    				AcknowledgementStatuses status = ack.getStatus();
	    				logger.info("Status Received from Webservice : " + status);
	    			    boolean filemoved=false;
	    			    
	    			   
	    			    
	    			    logger.debug("status.value().trim() : " + status.value().trim());
	    				if (status.value().trim().equalsIgnoreCase(AcknowledgementStatuses.ERROR_OCCURRED.value().trim()))
	    				{
	    					// Error Occured in WebService
	    					logger.error("WebService Call Returned Error");
	    			    	gov.nih.nci.caxchange.ws.routingandworkflow.ErrorDetails ed = new gov.nih.nci.caxchange.ws.routingandworkflow.ErrorDetails();
	    			    	ed = ack.getError();
	    			    	logger.error(" Error Code : " + ed.getErrorCode());
	    			    	logger.error("Error Description : " + ed.getErrorDescription());
	    			    	logger.error("Moving File " + fileList[i].toString() +  " to Error Folder");
	    			    	filemoved = fileList[i].renameTo(new File(errorDir, fileList[i].getName()));
	    				}
	    				else if (status.value().trim().equalsIgnoreCase(AcknowledgementStatuses.MESSAGE_RECEIVED.value().trim()))
	    			    {
	    			      // File processed OK.. Move it to Processed Folder	
	    			    	logger.info("WebService Call Successful");
	    			    	logger.info("Moving File " + fileList[i].getName() + " To " + processedDir);
	    			    	filemoved = fileList[i].renameTo(new File(processedDir, fileList[i].getName()));
	    			    }
	    			    else
	    			    {
	    			    	// WS Call was not OK.. Move it to Error Folder
	    			    	logger.error("WebService Call Returned Error");
	    			    	gov.nih.nci.caxchange.ws.routingandworkflow.ErrorDetails ed = new gov.nih.nci.caxchange.ws.routingandworkflow.ErrorDetails();
	    			    	ed = ack.getError();
	    			    	logger.error(" Error Code : " + ed.getErrorCode());
	    			    	logger.error("Error Description : " + ed.getErrorDescription());
	    			    	logger.error("Moving File " + fileList[i].toString() +  " to Error Folder");
	    			    	filemoved = fileList[i].renameTo(new File(errorDir, fileList[i].getName()));
	    			    }	    			    
		    	    	if (!filemoved) {    		
		    	    		logger.error(fileList[i].toString() + " Was not moved" );
		    	    		//System Alert
		    	        }
		    	    	
	    			} catch (ErrorMessage_Exception e) {
	    				// TODO Auto-generated catch block
	    				e.printStackTrace();
	    			    logger.error("Exception Caught : " + e.getMessage());
	    				//assertTrue(false);
	    			    //Message Exception
	    			}
	    			catch (Exception e) {
	    				e.printStackTrace();
	    			    logger.error("Exception Caught : " + e.getMessage());
	    				//assertTrue(false);
	    			    // Failed - Error Handling
	    			}

			} // End of main Try
			catch (IOException e)
			{
				logger.fatal("IO Exception reading/writing files");
				// File I/O Exception - Raise Admin Alert
			}
	    }	// End of For Loop
		}
		catch (Exception e)
		{
			logger.fatal("Exception Caught in RoutingAndWorkFlowServiceClient");
			e.printStackTrace();
		}
	} // End of testRoutingAndWorkFlowServiceClient
	
	private static boolean loadProperties() {
		boolean isSuccess=false;
    try {
    	 FileInputStream fis = new FileInputStream(new File(programPropertiesFile));

    	 
       	    Properties props = new Properties();
            //Read in the stored properties
            props.load(fis);
            location = props.getProperty("Location");
            preProcessorPropertiesFile_str = props.getProperty("preProcessorPropertiesFile");
            WebServiceURL = props.getProperty("WebServiceURL");
            WorkFlowId= props.getProperty("WorkFlowId");
            EndPointsCommaDelimitedString = props.getProperty("EndPointsCommaDelimitedString");
            userName = props.getProperty("userName");
            userPasswd = props.getProperty("userPasswd");
            rawFilesFolder = props.getProperty("rawFilesFolder");
            rawFilesBackupFolder = props.getProperty("rawFilesBackupFolder");
            inProcessFolder = props.getProperty("inProcessFolder");
            processedFolder = props.getProperty("processedFolder");
            errorFolder = props.getProperty("errorFolder");
            mapFileName = props.getProperty("mapFileName");
            initialDelay_str = props.getProperty("initialDelayInSeconds");
            pollingInterval_str = props.getProperty("pollingDelayInSeconds");
            logger.info("preProcessorPropertiesFile : " + preProcessorPropertiesFile_str);
            logger.info("Location : " + location);
            logger.info("WebServiceURL : " + WebServiceURL);
            logger.info("rawFilesFolder : " + rawFilesFolder);
            logger.info("rawFilesBackupFolder : " + rawFilesBackupFolder);
            logger.info("inProcessFolder : " + inProcessFolder);
            logger.info("ProcessedFolder : " + processedFolder);
            logger.info("ErrorFolder : " + errorFolder);
            logger.info("initialDelay_str : " + initialDelay_str);
            logger.info("pollingInterval_str : " + pollingInterval_str);
            logger.info("WorkFlowId : " + WorkFlowId);
            logger.info("EndPointsCommaDelimitedString : " + EndPointsCommaDelimitedString);
            
            initialDelay_long = new Long(initialDelay_str);
            pollingInterval_long = new Long (pollingInterval_str);
            
            logger.info("initialDelay_long : " + initialDelay_long);
            logger.info("pollingInterval_long : " + pollingInterval_long);
            
            isSuccess=true;
        } catch (Exception e) {
           logger.error("Exception processing Cancer Center Properties File");
           isSuccess=false;
        }  
            return isSuccess;
    }
} // End of Class

