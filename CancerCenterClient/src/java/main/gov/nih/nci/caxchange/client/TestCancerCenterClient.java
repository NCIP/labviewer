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
import gov.nih.nci.caxchange.client.exceptions.FolderDoesNotExistException;
import gov.nih.nci.caxchange.client.exceptions.FolderNotADirectoryException;
import gov.nih.nci.caxchange.client.exceptions.PropertiesFileException;
import gov.nih.nci.caxchange.client.preprocess.CSVP;

import java.io.File;
import java.io.FileInputStream;
import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.apache.log4j.Logger;

public class TestCancerCenterClient {

	private final ScheduledExecutorService scheduler = Executors
			.newScheduledThreadPool(1);

	// Property Files

	private static String preProcessorPropertiesFile_str;
	private static String programPropertiesFile;

	// To be set in a property file
	private static String location;
	private static String userName;
	private static String userPasswd;

	private static String inProcessFolder;
	private static String processedFolder;
	private static String errorFolder;
	private static String rawFilesFolder;
	private static String rawFilesBackupFolder;

	private static String initialDelay_str;
	private static String pollingInterval_str;
	private static long initialDelay_long;
	private static long pollingInterval_long;

	private static File processedDir;
	private static File inProcessDir;
	private static File errorDir;

	private static File rawFilesDir;
	private static File rawFilesBackupDirectory;
	private static String hubURL;
	private static String hl7v2Dir;
	private static String hl7v2mapFileName;

	// Map File used for mapping CSV file to HL7V3 format by caAdaptor.
	private static String mapFileName;

	// Logging File

	private static Logger logger = Logger
			.getLogger("gov.nih.nci.caxchange.client.CancerCenterClient"); 

	/*public static void main(String[] args) {

		String configFilePath = "D:/Development/CancerCenterClient/src/java/main/properties/DefaultProperties.properties";// System.getProperty("cancer.center.client.properties");
		if (configFilePath == null) {
			throw new RuntimeException(
					"Cancer Center Client property is not set as an argument on the java command line.");
		}

		File f = new File(configFilePath);
		if (f == null) {
			throw new RuntimeException(
					"Cancer Center Client Properties File is not available.");
		} else {
			programPropertiesFile = configFilePath;
			f = null;
		}

		logger.debug("Staring Cancer Center Client -- Main Method");
		TestCancerCenterClient client = new TestCancerCenterClient();
		boolean success = client.checkSetup();
		if (success) {
			client.process();
		} else {
			logger
					.fatal("Fatal: Required Resources not found to run Client Application");
			logger.fatal("Aborting Client");
			logger.fatal("Please contact System Admin");
		}

	}
	 */
	/**
	 * Entry point for the TestCancerClientUI. 
	 * @param file
	 */
	public void test(File file) {

		String configFilePath = file.getAbsolutePath();
		if (configFilePath == null) {
			throw new RuntimeException(
					Messages.getString("TestCancerCenterClient.1")); 
		}
		File f = new File(configFilePath);
		if (f == null) {
			throw new RuntimeException(
					Messages.getString("TestCancerCenterClient.2")); 
		} else {
			programPropertiesFile = configFilePath;
			f = null;
		}
		logger.debug(Messages.getString("TestCancerCenterClient.129")); 
		TestCancerCenterClient client = new TestCancerCenterClient();
		boolean success = client.checkSetup();
		if (success) {
			client.process();
		} else {
			logger.fatal(Messages.getString("TestCancerCenterClient.0")); 
			logger.fatal(Messages.getString("TestCancerCenterClient.5")); 
			logger.fatal(Messages.getString("TestCancerCenterClient.6")); 
		}

	}

	/**
	 * Checks the setup of all the directories.
	 * @return success
	 */
	public boolean checkSetup() {
		boolean success = false;
		try {
			// Load Properties File
			if (!loadProperties()) {
				throw new PropertiesFileException(
						Messages.getString("TestCancerCenterClient.7")); 
			}

			rawFilesDir = new File(rawFilesFolder);
			rawFilesBackupDirectory = new File(rawFilesBackupFolder);

			inProcessDir = new File(inProcessFolder);
			processedDir = new File(processedFolder);
			errorDir = new File(errorFolder);

			File preProcessorPropertiesFile_file = new File(
					preProcessorPropertiesFile_str);

			if (!preProcessorPropertiesFile_file.exists()) {
				throw new PropertiesFileException(
						Messages.getString("TestCancerCenterClient.8")); 
			}
			if (!rawFilesDir.exists()) {
				throw new FolderDoesNotExistException(
						Messages.getString("TestCancerCenterClient.9")); 
			}
			if (!rawFilesDir.isDirectory()) {
				throw new FolderNotADirectoryException(
						Messages.getString("TestCancerCenterClient.10")); 
			}
			if (!rawFilesBackupDirectory.exists()) {
				throw new FolderDoesNotExistException(
						Messages.getString("TestCancerCenterClient.11")); 
			}
			if (!rawFilesBackupDirectory.isDirectory()) {
				throw new FolderNotADirectoryException(
						Messages.getString("TestCancerCenterClient.12"));
			}
			if (!inProcessDir.exists()) {
				throw new FolderDoesNotExistException(
						Messages.getString("TestCancerCenterClient.13")); 
			}
			if (!inProcessDir.isDirectory()) {
				throw new FolderNotADirectoryException(
						Messages.getString("TestCancerCenterClient.14")); 
			}
			if (!processedDir.exists()) {
				throw new FolderDoesNotExistException(
						Messages.getString("TestCancerCenterClient.15")); 
			}
			if (!processedDir.isDirectory()) {
				throw new FolderNotADirectoryException(
						Messages.getString("TestCancerCenterClient.16")); 
			}
			if (!errorDir.exists()) {
				throw new FolderDoesNotExistException(
						Messages.getString("TestCancerCenterClient.17")); 
			}
			if (!errorDir.isDirectory()) {
				throw new FolderNotADirectoryException(
						Messages.getString("TestCancerCenterClient.18")); 
			}
			success = true;
		} catch (FolderDoesNotExistException e) {
			logger.error(e.getErrorMessage());
			success = false;
		} catch (FolderNotADirectoryException e) {
			logger.error(e.getErrorMessage());
			success = false;
		} catch (PropertiesFileException e) {
			logger.fatal(e.getErrorMessage());
			success = false;
		}
		return success;
	}

	/**
	 * Polls the directory for files to be processed.When it encounters a file:
	 * 1.It invokes the caAdapter API to convert a .csv file to HL7V3. 2.Then
	 * invokes the grid service to persist the HL7V3 message.
	 */
	public void process() {

		/* HL7V3Transformation v3Transformation = new HL7V3Transformation(this);
		 v3Transformation.process();
		
		 HL7V2ToHL7V3Tranformation v2Transformation = new HL7V2ToHL7V3Tranformation(this);
		 v2Transformation.process();*/
		
		/*final Runnable fileCheck = new Runnable() {

			public void run() {
				try {
					logger.debug(Messages.getString("TestCancerCenterClient.128")); 
					if (!checkPreProcessedFolder()) {
						logger.error(Messages.getString("TestCancerCenterClient.20")); 
						throw new Exception(
								Messages.getString("TestCancerCenterClient.21")); 
					} else {
						File inProcessDir = new File(inProcessFolder);
						// Delete Zero Byte Files Generated by Preprocessor in
						// case of exceptions.
						File[] inProcessfileList = inProcessDir.listFiles();
						for (int i = 0; i < inProcessfileList.length; i++) {
							if (inProcessfileList[i].length() == 0)
								inProcessfileList[i].delete();
						}
						File[] fileList = inProcessDir.listFiles();
						for (int i = 0; i < fileList.length; i++) {
							// invokes the caAdapter API to convert a .csv file
							// to HL7V3.
							String HL7V3 = invokecaAdapterAPI(fileList[i]
									.getAbsolutePath());
							// invokes the grid service to persist the HL7V3
							// message.
							invokeGridService(fileList, i, HL7V3);
						}
					}
				} catch (Exception e) {
					logger.fatal(Messages.getString("TestCancerCenterClient.22")); 
				}
			}

		};
		final ScheduledFuture<?> fileCheckHandle = scheduler
				.scheduleAtFixedRate(fileCheck, initialDelay_long,
						pollingInterval_long, SECONDS);
*/	}

	/**
	 * Converts the raw .csv file to caAdapter compatible .csv file and moves it
	 * to the InProcess folder
	 * 
	 * @return retResult
	 */
	public boolean checkPreProcessedFolder() {
		logger.debug(Messages.getString("TestCancerCenterClient.23")); 
		boolean retResult = true;
		try {
			File[] fileList = rawFilesDir.listFiles();
			// Get Current Date and Time for Stamping the file
			Date dt = new Date();
			long currentTime = dt.getTime();
			StringBuffer sbuf = new StringBuffer();
			StringBuffer outbuf = new StringBuffer();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss"); 

			sbuf = sdf.format(dt, outbuf, new FieldPosition(0));

			logger.debug("Current Date Time : " + sbuf); 

			for (int i = 0; i < fileList.length; i++) {
				String fileName = fileList[i].getName().toString();
				logger.info("File Name : " + fileName); 

				String fileNameLocationDateTimeStamp = location + "_" + sbuf 
						+ "_" + fileName; 
				String outFile = inProcessFolder
						+ fileNameLocationDateTimeStamp;
				try {
					logger.debug(Messages.getString("TestCancerCenterClient.29")); 
					CSVP fileOut = new CSVP(fileList[i].toString(), outFile,
							preProcessorPropertiesFile_str);

					boolean success = fileList[i].renameTo(new File(
							rawFilesBackupDirectory,
							fileNameLocationDateTimeStamp));
					if (!success) {
						logger.error(fileList[i].toString() + "Renamed to : " 
								+ fileNameLocationDateTimeStamp
								+ " Was not moved to the backup folder"); 
						retResult = false;
					} else {
						logger.debug("File Moved to Backup folder"); 
						retResult = true;
					}

				} catch (Exception e) {
					retResult = false;
					logger.error(Messages.getString("TestCancerCenterClient.33") 
							+ fileList[i].toString());
					logger.error(e.getMessage());
					logger.info("Erro Dir : " + errorDir); 
					logger.info("Error folder : " + errorFolder); 
					logger.info("fileNameLocationDateTimeStamp : " 
							+ fileNameLocationDateTimeStamp);
					logger.info("File : " + fileList[i]); 
					logger.info("*****"); 
					boolean movesuccess = fileList[i].renameTo(new File(
							errorDir, fileNameLocationDateTimeStamp));
					if (!movesuccess) {
						logger.error(fileList[i].toString() + "Renamed to : " 
								+ errorDir + fileNameLocationDateTimeStamp
								+ " Was not moved to the error folder"); 
						logger.info("*****"); 
					} else {
						logger.error(fileList[i].toString() + "Renamed to : " 
								+ errorDir + fileNameLocationDateTimeStamp
								+ " Was moved to the error folder"); 
					}

				} // End of Catch

			} // End of For

		} catch (Exception e) {
			logger.fatal("Error Accesing Files in file System", e); 
			retResult = false;
		}
		return retResult;

	}

	
	/**
	 * Loads the properties from the properties file
	 * @return isSuccess
	 */
	private static boolean loadProperties() {
		boolean isSuccess = false;
		try {
			FileInputStream fis = new FileInputStream(new File(
					programPropertiesFile));

			Properties props = new Properties();
			// Read in the stored properties
			props.load(fis);
			location = props.getProperty("Location"); 
			preProcessorPropertiesFile_str = props
					.getProperty("preProcessorPropertiesFile"); 
			userName = props.getProperty("userName"); 
			userPasswd = props.getProperty("userPasswd"); 
			rawFilesFolder = props.getProperty("rawFilesFolder"); 
			rawFilesBackupFolder = props.getProperty("rawFilesBackupFolder"); 
			inProcessFolder = props.getProperty("inProcessFolder"); 
			processedFolder = props.getProperty("processedFolder"); 
			errorFolder = props.getProperty("errorFolder"); 
			mapFileName = props.getProperty("mapFileName"); 
			hl7v2Dir=props.getProperty("HL7V2Dir");
			hl7v2mapFileName=props.getProperty("hl7v2mapFileName");
			initialDelay_str = props.getProperty("initialDelayInSeconds"); 
			pollingInterval_str = props.getProperty("pollingDelayInSeconds"); 
			hubURL = props.getProperty("HubURL"); 
			logger.info("preProcessorPropertiesFile : " 
					+ preProcessorPropertiesFile_str);
			logger.info("Location : " + location); 
			logger.info("rawFilesFolder : " + rawFilesFolder); 
			logger.info("rawFilesBackupFolder : " + rawFilesBackupFolder); 
			logger.info("inProcessFolder : " + inProcessFolder); 
			logger.info("ProcessedFolder : " + processedFolder); 
			logger.info("ErrorFolder : " + errorFolder); 
			logger.info("initialDelay_str : " + initialDelay_str); 
			logger.info("pollingInterval_str : " + pollingInterval_str); 
			logger.info("hubURL : " + hubURL); 
			logger.info("hl7v2Dir : " + hl7v2Dir);
			logger.info("hl7v2mapFileName : " + hl7v2mapFileName);
			initialDelay_long = new Long(initialDelay_str);
			pollingInterval_long = new Long(pollingInterval_str);
			logger.info("initialDelay_long : " + initialDelay_long); 
			logger.info("pollingInterval_long : " + pollingInterval_long); 

			isSuccess = true;
		} catch (Exception e) {
			logger.error(Messages.getString("TestCancerCenterClient.127")); 
			isSuccess = false;
		}
		return isSuccess;
	}

	/**
	 * @return the scheduler
	 */
	public ScheduledExecutorService getScheduler() {
		return scheduler;
	}

	/**
	 * @return the preProcessorPropertiesFile_str
	 */
	public static String getPreProcessorPropertiesFile_str() {
		return preProcessorPropertiesFile_str;
	}

	/**
	 * @return the programPropertiesFile
	 */
	public static String getProgramPropertiesFile() {
		return programPropertiesFile;
	}

	/**
	 * @return the location
	 */
	public static String getLocation() {
		return location;
	}

	/**
	 * @return the userName
	 */
	public static String getUserName() {
		return userName;
	}

	/**
	 * @return the userPasswd
	 */
	public static String getUserPasswd() {
		return userPasswd;
	}

	/**
	 * @return the inProcessFolder
	 */
	public static String getInProcessFolder() {
		return inProcessFolder;
	}

	/**
	 * @return the processedFolder
	 */
	public static String getProcessedFolder() {
		return processedFolder;
	}

	/**
	 * @return the errorFolder
	 */
	public static String getErrorFolder() {
		return errorFolder;
	}

	/**
	 * @return the rawFilesFolder
	 */
	public static String getRawFilesFolder() {
		return rawFilesFolder;
	}

	/**
	 * @return the rawFilesBackupFolder
	 */
	public static String getRawFilesBackupFolder() {
		return rawFilesBackupFolder;
	}

	/**
	 * @return the initialDelay_str
	 */
	public static String getInitialDelay_str() {
		return initialDelay_str;
	}

	/**
	 * @return the pollingInterval_str
	 */
	public static String getPollingInterval_str() {
		return pollingInterval_str;
	}

	/**
	 * @return the initialDelay_long
	 */
	public static long getInitialDelay_long() {
		return initialDelay_long;
	}

	/**
	 * @return the pollingInterval_long
	 */
	public static long getPollingInterval_long() {
		return pollingInterval_long;
	}

	/**
	 * @return the processedDir
	 */
	public static File getProcessedDir() {
		return processedDir;
	}

	/**
	 * @return the inProcessDir
	 */
	public static File getInProcessDir() {
		return inProcessDir;
	}

	/**
	 * @return the errorDir
	 */
	public static File getErrorDir() {
		return errorDir;
	}

	/**
	 * @return the rawFilesDir
	 */
	public static File getRawFilesDir() {
		return rawFilesDir;
	}

	/**
	 * @return the rawFilesBackupDirectory
	 */
	public static File getRawFilesBackupDirectory() {
		return rawFilesBackupDirectory;
	}

	/**
	 * @return the hubURL
	 */
	public static String getHubURL() {
		return hubURL;
	}

	/**
	 * @return the mapFileName
	 */
	public static String getMapFileName() {
		return mapFileName;
	}

	/**
	 * @return the logger
	 */
	public static Logger getLogger() {
		return logger;
	}

	/**
	 * @return the hl7v2Dir
	 */
	public static String getHl7v2Dir() {
		return hl7v2Dir;
	}

	/**
	 * @return the hl7v2mapFileName
	 */
	public static String getHl7v2mapFileName() {
		return hl7v2mapFileName;
	}

} // End of Class

