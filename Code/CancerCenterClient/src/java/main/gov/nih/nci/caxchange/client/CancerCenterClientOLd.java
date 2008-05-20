package gov.nih.nci.caxchange.client;
/**
 * Copyright Notice.  Copyright 2008  Scenpro, Inc (“caBIG™ Participant”).caXchange
 * was created with NCI funding and is part of the caBIG™ initiative. 
 * The software subject to this notice and license includes both human readable source code form and 
 * machine readable, binary, object code form (the “caBIG™ Software”).
 * This caBIG™ Software License (the “License”) is between caBIG™ Participant and You.  
 * “You (or “Your”) shall mean a person or an entity, and all other entities that control, 
 * are controlled by, or are under common control with the entity.  “Control” for purposes of this 
 * definition means (i) the direct or indirect power to cause the direction or management of such entity, 
 * whether by contract or otherwise, or (ii) ownership of fifty percent (50%) or more of the outstanding shares, 
 * or (iii) beneficial ownership of such entity.  
 * License.  Provided that You agree to the conditions described below, caBIG™ Participant grants 
 * You a non-exclusive, worldwide, perpetual, fully-paid-up, no-charge, irrevocable, transferable and 
 * royalty-free right and license in its rights in the caBIG™ Software, including any copyright or patent rights therein, to 
 * (i) use, install, disclose, access, operate, execute, reproduce, copy, modify, translate, market, publicly display, 
 * publicly perform, and prepare derivative works of the caBIG™ Software in any manner and for any purpose, and to have 
 * or permit others to do so; (ii) make, have made, use, practice, sell, and offer for sale, import, and/or otherwise 
 * dispose of caBIG™ Software (or portions thereof); (iii) distribute and have distributed to and by third parties the 
 * caBIG™ Software and any modifications and derivative works thereof; and (iv) sublicense the foregoing rights 
 * set out in (i), (ii) and (iii) to third parties, including the right to license such rights to further third parties.  
 * For sake of clarity, and not by way of limitation, caBIG™ Participant shall have no right of accounting or right of payment
 *  from You or Your sublicensees for the rights granted under this License.  This License is granted at no charge to You.  
 *  Your downloading, copying, modifying, displaying, distributing or use of caBIG™ Software constitutes acceptance of all 
 *  of the terms and conditions of this Agreement.  If you do not agree to such terms and conditions, you have no right to 
 *  download, copy, modify, display, distribute or use the caBIG™ Software.  
 * 1.	Your redistributions of the source code for the caBIG™ Software must retain the above copyright notice, 
 * 		this list of conditions and the disclaimer and limitation of liability of Article 6 below.  
 * 		Your redistributions in object code form must reproduce the above copyright notice, this list of conditions and 
 * 		the disclaimer of Article 6 in the documentation and/or other materials provided with the distribution, if any.
 * 2.	Your end-user documentation included with the redistribution, if any, must include the following acknowledgment: 
 * 		“This product includes software developed by Scenpro, Inc.”  
 * 		If You do not include such end-user documentation, You shall include this acknowledgment in the caBIG™ Software itself, 
 * 		wherever such third-party acknowledgments normally appear.
 * 3.	You may not use the names  “Scenpro, Inc”, 
 * 		“The National Cancer Institute”, “NCI”, “Cancer Bioinformatics Grid” or “caBIG™” to endorse or promote products 
 * 		derived from this caBIG™ Software.  This License does not authorize You to use any trademarks, service marks, trade names,
 * 		logos or product names of either caBIG™ Participant, NCI or caBIG™, except as required to comply with the terms of this 
 * 		License.
 * 4.	For sake of clarity, and not by way of limitation, You may incorporate this caBIG™ Software into Your proprietary 
 * 		programs and into any third party proprietary programs.  However, if You incorporate the caBIG™ Software into third party 
 * 		proprietary programs, You agree that You are solely responsible for obtaining any permission from such third parties 
 * 		required to incorporate the caBIG™ Software into such third party proprietary programs and for informing Your sublicensees, 
 * 		including without limitation Your end-users, of their obligation to secure any required permissions from such third parties 
 * 		before incorporating the caBIG™ Software into such third party proprietary software programs.  In the event that You fail to 
 * 		obtain such permissions, You agree to indemnify caBIG™ Participant for any claims against caBIG™ Participant by such third 
 * 		parties, except to the extent prohibited by law, resulting from Your failure to obtain such permissions.
 * 5.	For sake of clarity, and not by way of limitation, You may add Your own copyright statement to Your modifications and 
 * 		to the derivative works, and You may provide additional or different license terms and conditions in Your sublicenses of 
 * 		modifications of the caBIG™ Software, or any derivative works of the caBIG™ Software as a whole, provided Your use, reproduction, 
 * 		and distribution of the Work otherwise complies with the conditions stated in this License.
 * 6.	THIS caBIG™ SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED WARRANTIES (INCLUDING, BUT NOT LIMITED TO, 
 * 		THE IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED.  
 * 		IN NO EVENT SHALL THE Scenpro, Inc OR ITS AFFILIATES 
 * 		BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * 		PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON 
 * 		ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY 
 * 		OUT OF THE USE OF THIS caBIG™ SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * 
 */
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

