package gov.nih.nci.caxchange.client;

import gov.nih.nci.caxchange.client.exceptions.FolderDoesNotExistException;
import gov.nih.nci.caxchange.client.exceptions.FolderNotADirectoryException;
import gov.nih.nci.caxchange.client.exceptions.PropertiesFileException;
import gov.nih.nci.caxchange.client.preprocess.CSVP;

import java.io.File;
import java.io.FileInputStream;
import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.ScheduledExecutorService;

import org.apache.log4j.Logger;

public class CancerCenterClient {

	// Property Files
	private String preProcessorPropertiesFile_str;
	private String programPropertiesFile;

	// To be set in a property file
	private String location;
	private String userName;
	private String userPasswd;

	private String inProcessFolder;
	private String processedFolder;
	private String errorFolder;
	private String rawFilesFolder;
	private String rawFilesBackupFolder;

	private String initialDelay_str;
	private String pollingInterval_str;
	private long initialDelay_long;
	private long pollingInterval_long;

	private File processedDir;
	private File inProcessDir;
	private File errorDir;

	private File rawFilesDir;
	private File rawFilesBackupDirectory;
	private String hubURL;
	private String studyLookupServiceURL;
	private String hl7v2Dir;
	private String hl7v2mapFileName;

	// Map File used for mapping CSV file to HL7V3 format by caAdaptor.
	private String mapFileName;

	// Logging File

	private static Logger logger = Logger
			.getLogger("gov.nih.nci.caxchange.client.CancerCenterClient");

	private static CancerCenterClient cancerCenterClient;

	/**
	 * Implementing the Singleton: private constructor
	 */
	private CancerCenterClient() {

	}

	/**
	 * getInstance creates a instance of CancerCenterClient if it is null;
	 * else returns the previously created instance.
	 * @return cancerCenterClient
	 */
	public static CancerCenterClient getInstance() {
		if (cancerCenterClient == null)
			cancerCenterClient = new CancerCenterClient();

		return cancerCenterClient;
	}

	/**
	 * Entry point for the TestCancerClientUI. 
	 * @param file
	 */
	public void test(File file,ArrayList<ScheduledExecutorService>threadList) {

		String configFilePath = file.getAbsolutePath();
		if (configFilePath == null) {
			throw new RuntimeException(Messages
					.getString("CancerCenterClient.1"));
		}
		File f = new File(configFilePath);
		if (f == null) {
			throw new RuntimeException(Messages
					.getString("CancerCenterClient.2"));
		} else {
			getInstance().programPropertiesFile = configFilePath;
			f = null;
		}
		logger.debug(Messages.getString("CancerCenterClient.129"));
		CancerCenterClient client = new CancerCenterClient();
		boolean success = client.checkSetup();
		if (success) {
			client.process(threadList);
		} else {
			logger.fatal(Messages.getString("CancerCenterClient.0"));
			logger.fatal(Messages.getString("CancerCenterClient.5"));
			logger.fatal(Messages.getString("CancerCenterClient.6"));
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
				throw new PropertiesFileException(Messages
						.getString("CancerCenterClient.7"));
			}

			getInstance().setRawFilesDir(new File( getInstance().rawFilesFolder));
			getInstance().setRawFilesBackupDirectory(new File( getInstance().rawFilesBackupFolder));

			getInstance().setInProcessDir(new File( getInstance().inProcessFolder));
			getInstance().setProcessedDir(new File( getInstance().processedFolder));
			getInstance().setErrorDir(new File( getInstance().errorFolder));

			File preProcessorPropertiesFile_file = new File(
					 getInstance().preProcessorPropertiesFile_str);

			if (! preProcessorPropertiesFile_file.exists()) {
				throw new PropertiesFileException(Messages
						.getString("CancerCenterClient.8"));
			}
			if (! getInstance().rawFilesDir.exists()) {
				throw new FolderDoesNotExistException(Messages
						.getString("CancerCenterClient.9"));
			}
			if (! getInstance().rawFilesDir.isDirectory()) {
				throw new FolderNotADirectoryException(Messages
						.getString("CancerCenterClient.10"));
			}
			if (! getInstance().rawFilesBackupDirectory.exists()) {
				throw new FolderDoesNotExistException(Messages
						.getString("CancerCenterClient.11"));
			}
			if (! getInstance().rawFilesBackupDirectory.isDirectory()) {
				throw new FolderNotADirectoryException(Messages
						.getString("CancerCenterClient.12"));
			}
			if (! getInstance().inProcessDir.exists()) {
				throw new FolderDoesNotExistException(Messages
						.getString("CancerCenterClient.13"));
			}
			if (! getInstance().inProcessDir.isDirectory()) {
				throw new FolderNotADirectoryException(Messages
						.getString("CancerCenterClient.14"));
			}
			if (! getInstance().processedDir.exists()) {
				throw new FolderDoesNotExistException(Messages
						.getString("CancerCenterClient.15"));
			}
			if (! getInstance().processedDir.isDirectory()) {
				throw new FolderNotADirectoryException(Messages
						.getString("CancerCenterClient.16"));
			}
			if (! getInstance().errorDir.exists()) {
				throw new FolderDoesNotExistException(Messages
						.getString("CancerCenterClient.17"));
			}
			if (! getInstance().errorDir.isDirectory()) {
				throw new FolderNotADirectoryException(Messages
						.getString("CancerCenterClient.18"));
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
	public void process(ArrayList<ScheduledExecutorService>threadList) {

		 HL7V3Transformation v3Transformation = new HL7V3Transformation(getInstance());
		 v3Transformation.process(threadList);
		
		 HL7V2ToHL7V3Tranformation v2Transformation = new HL7V2ToHL7V3Tranformation(getInstance());
		 v2Transformation.process(threadList);

	}

	/**
	 * Converts the raw .CSV file to caAdapter compatible .CSV file and moves it
	 * to the InProcess folder
	 * 
	 * @return retResult
	 */
	public boolean checkPreProcessedFolder() {
		logger.debug(Messages.getString("CancerCenterClient.23"));
		boolean retResult = true;
		try {
			File[] fileList =  getInstance().rawFilesDir.listFiles();
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

				String fileNameLocationDateTimeStamp =  getInstance().location + "_" + sbuf
						+ "_" + fileName;
				String outFile = getInstance().inProcessFolder
						+ fileNameLocationDateTimeStamp;
				try {
					logger.debug(Messages
							.getString("CancerCenterClient.29"));
					CSVP fileOut = new CSVP(fileList[i].toString(), outFile,
							 getInstance().preProcessorPropertiesFile_str);

					boolean success = fileList[i].renameTo(new File(
							 getInstance().rawFilesBackupDirectory,
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
					logger.error(Messages
							.getString("CancerCenterClient.33")
							+ fileList[i].toString());
					logger.error(e.getMessage());
					logger.info("Erro Dir : " +  getInstance().errorDir);
					logger.info("Error folder : " +  getInstance().errorFolder);
					logger.info("fileNameLocationDateTimeStamp : "
							+ fileNameLocationDateTimeStamp);
					logger.info("File : " + fileList[i]);
					logger.info("*****");
					boolean movesuccess = fileList[i].renameTo(new File(
							 getInstance().errorDir, fileNameLocationDateTimeStamp));
					if (!movesuccess) {
						logger.error(fileList[i].toString() + "Renamed to : "
								+  getInstance().errorDir + fileNameLocationDateTimeStamp
								+ " Was not moved to the error folder");
						logger.info("*****");
					} else {
						logger.error(fileList[i].toString() + "Renamed to : "
								+  getInstance().errorDir + fileNameLocationDateTimeStamp
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
	private boolean loadProperties() {
		boolean isSuccess = false;
		try {
			FileInputStream fis = new FileInputStream(new File(
					getInstance().programPropertiesFile));

			Properties props = new Properties();
			// Read in the stored properties
			props.load(fis);
			getInstance().setLocation(props.getProperty("Location"));
			getInstance().setPreProcessorPropertiesFile_str( props
					.getProperty("preProcessorPropertiesFile"));
			getInstance().setUserName(props.getProperty("userName"));
			getInstance().setUserPasswd(props.getProperty("userPasswd"));
			getInstance().setRawFilesFolder(props.getProperty("rawFilesFolder"));
			getInstance().setRawFilesBackupFolder(props.getProperty("rawFilesBackupFolder"));
			getInstance().setInProcessFolder(props.getProperty("inProcessFolder"));
			getInstance().setProcessedFolder(props.getProperty("processedFolder"));
			getInstance().setErrorFolder(props.getProperty("errorFolder"));
			getInstance().setMapFileName(props.getProperty("mapFileName"));
			getInstance().setHl7v2Dir(props.getProperty("HL7V2Dir"));
			getInstance().setHl7v2mapFileName(props.getProperty("hl7v2mapFileName"));
			getInstance().setInitialDelay_str(props.getProperty("initialDelayInSeconds"));
			getInstance().setPollingInterval_str(props.getProperty("pollingDelayInSeconds"));
			getInstance().setHubURL(props.getProperty("HubURL"));
			getInstance().setStudyLookupServiceURL(props.getProperty("StudyLookUpServiceURL"));
			logger.info("preProcessorPropertiesFile : "
					+ getInstance().preProcessorPropertiesFile_str);
			logger.info("Location : " + getInstance().location);
			logger.info("rawFilesFolder : " + getInstance().rawFilesFolder);
			logger.info("rawFilesBackupFolder : " + getInstance().rawFilesBackupFolder);
			logger.info("inProcessFolder : " + getInstance().inProcessFolder);
			logger.info("ProcessedFolder : " + getInstance().processedFolder);
			logger.info("ErrorFolder : " + getInstance().errorFolder);
			logger.info("initialDelay_str : " + getInstance().initialDelay_str);
			logger.info("pollingInterval_str : " + getInstance().pollingInterval_str);
			logger.info("hubURL : " + getInstance().hubURL);
			logger.info("hl7v2Dir : " + getInstance().hl7v2Dir);
			logger.info("hl7v2mapFileName : " + getInstance().hl7v2mapFileName);
			getInstance().initialDelay_long = new Long(getInstance().initialDelay_str);
			getInstance().pollingInterval_long = new Long(getInstance().pollingInterval_str);
			logger.info("initialDelay_long : " + getInstance().initialDelay_long);
			logger.info("pollingInterval_long : " +getInstance(). pollingInterval_long);

			isSuccess = true;
		} catch (Exception e) {
			logger.error(Messages.getString("CancerCenterClient.127"));
			isSuccess = false;
		}
		return isSuccess;
	}

	/**
	 * @return the preProcessorPropertiesFile_str
	 */
	public String getPreProcessorPropertiesFile_str() {
		return getInstance().preProcessorPropertiesFile_str;
	}

	/**
	 * @return the programPropertiesFile
	 */
	public String getProgramPropertiesFile() {
		return getInstance().programPropertiesFile;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return getInstance().location;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return getInstance().userName;
	}

	/**
	 * @return the userPasswd
	 */
	public String getUserPasswd() {
		return getInstance().userPasswd;
	}

	/**
	 * @return the inProcessFolder
	 */
	public String getInProcessFolder() {
		return getInstance().inProcessFolder;
	}

	/**
	 * @return the processedFolder
	 */
	public String getProcessedFolder() {
		return getInstance().processedFolder;
	}

	/**
	 * @return the errorFolder
	 */
	public String getErrorFolder() {
		return getInstance().errorFolder;
	}

	/**
	 * @return the rawFilesFolder
	 */
	public String getRawFilesFolder() {
		return getInstance().rawFilesFolder;
	}

	/**
	 * @return the rawFilesBackupFolder
	 */
	public String getRawFilesBackupFolder() {
		return getInstance().rawFilesBackupFolder;
	}

	/**
	 * @return the initialDelay_str
	 */
	public String getInitialDelay_str() {
		return getInstance().initialDelay_str;
	}

	/**
	 * @return the pollingInterval_str
	 */
	public String getPollingInterval_str() {
		return getInstance().pollingInterval_str;
	}

	/**
	 * @return the initialDelay_long
	 */
	public long getInitialDelay_long() {
		return getInstance().initialDelay_long;
	}

	/**
	 * @return the pollingInterval_long
	 */
	public long getPollingInterval_long() {
		return getInstance().pollingInterval_long;
	}

	/**
	 * @return the processedDir
	 */
	public File getProcessedDir() {
		return getInstance().processedDir;
	}

	/**
	 * @return the inProcessDir
	 */
	public File getInProcessDir() {
		return getInstance().inProcessDir;
	}

	/**
	 * @return the errorDir
	 */
	public File getErrorDir() {
		return getInstance().errorDir;
	}

	/**
	 * @return the rawFilesDir
	 */
	public File getRawFilesDir() {
		return getInstance().rawFilesDir;
	}

	/**
	 * @return the rawFilesBackupDirectory
	 */
	public File getRawFilesBackupDirectory() {
		return getInstance().rawFilesBackupDirectory;
	}

	/**
	 * @return the hubURL
	 */
	public String getHubURL() {
		return getInstance().hubURL;
	}

	/**
	 * @return the mapFileName
	 */
	public String getMapFileName() {
		return getInstance().mapFileName;
	}

	/**
	 * @return the hl7v2Dir
	 */
	public String getHl7v2Dir() {
		return getInstance().hl7v2Dir;
	}

	/**
	 * @return the hl7v2mapFileName
	 */
	public String getHl7v2mapFileName() {
		return getInstance().hl7v2mapFileName;
	}

	/**
	 * @param preProcessorPropertiesFile_str the preProcessorPropertiesFile_str to set
	 */
	public void setPreProcessorPropertiesFile_str(
			String preProcessorPropertiesFile_str) {
		this.preProcessorPropertiesFile_str = preProcessorPropertiesFile_str;
	}

	/**
	 * @param programPropertiesFile the programPropertiesFile to set
	 */
	public void setProgramPropertiesFile(String programPropertiesFile) {
		this.programPropertiesFile = programPropertiesFile;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @param userPasswd the userPasswd to set
	 */
	public void setUserPasswd(String userPasswd) {
		this.userPasswd = userPasswd;
	}

	/**
	 * @param inProcessFolder the inProcessFolder to set
	 */
	public void setInProcessFolder(String inProcessFolder) {
		this.inProcessFolder = inProcessFolder;
	}

	/**
	 * @param processedFolder the processedFolder to set
	 */
	public void setProcessedFolder(String processedFolder) {
		this.processedFolder = processedFolder;
	}

	/**
	 * @param errorFolder the errorFolder to set
	 */
	public void setErrorFolder(String errorFolder) {
		this.errorFolder = errorFolder;
	}

	/**
	 * @param rawFilesFolder the rawFilesFolder to set
	 */
	public void setRawFilesFolder(String rawFilesFolder) {
		this.rawFilesFolder = rawFilesFolder;
	}

	/**
	 * @param rawFilesBackupFolder the rawFilesBackupFolder to set
	 */
	public void setRawFilesBackupFolder(String rawFilesBackupFolder) {
		this.rawFilesBackupFolder = rawFilesBackupFolder;
	}

	/**
	 * @param initialDelay_str the initialDelay_str to set
	 */
	public void setInitialDelay_str(String initialDelay_str) {
		this.initialDelay_str = initialDelay_str;
	}

	/**
	 * @param pollingInterval_str the pollingInterval_str to set
	 */
	public void setPollingInterval_str(String pollingInterval_str) {
		this.pollingInterval_str = pollingInterval_str;
	}

	/**
	 * @param initialDelay_long the initialDelay_long to set
	 */
	public void setInitialDelay_long(long initialDelay_long) {
		this.initialDelay_long = initialDelay_long;
	}

	/**
	 * @param pollingInterval_long the pollingInterval_long to set
	 */
	public void setPollingInterval_long(long pollingInterval_long) {
		this.pollingInterval_long = pollingInterval_long;
	}

	/**
	 * @param processedDir the processedDir to set
	 */
	public void setProcessedDir(File processedDir) {
		this.processedDir = processedDir;
	}

	/**
	 * @param inProcessDir the inProcessDir to set
	 */
	public void setInProcessDir(File inProcessDir) {
		this.inProcessDir = inProcessDir;
	}

	/**
	 * @param errorDir the errorDir to set
	 */
	public void setErrorDir(File errorDir) {
		this.errorDir = errorDir;
	}

	/**
	 * @param rawFilesDir the rawFilesDir to set
	 */
	public void setRawFilesDir(File rawFilesDir) {
		this.rawFilesDir = rawFilesDir;
	}

	/**
	 * @param rawFilesBackupDirectory the rawFilesBackupDirectory to set
	 */
	public void setRawFilesBackupDirectory(File rawFilesBackupDirectory) {
		this.rawFilesBackupDirectory = rawFilesBackupDirectory;
	}

	/**
	 * @param hubURL the hubURL to set
	 */
	public void setHubURL(String hubURL) {
		this.hubURL = hubURL;
	}

	/**
	 * @param hl7v2Dir the hl7v2Dir to set
	 */
	public void setHl7v2Dir(String hl7v2Dir) {
		this.hl7v2Dir = hl7v2Dir;
	}

	/**
	 * @param hl7v2mapFileName the hl7v2mapFileName to set
	 */
	public void setHl7v2mapFileName(String hl7v2mapFileName) {
		this.hl7v2mapFileName = hl7v2mapFileName;
	}

	/**
	 * @param mapFileName the mapFileName to set
	 */
	public void setMapFileName(String mapFileName) {
		this.mapFileName = mapFileName;
	}

	/**
	 * @return the studyLookupServiceURL
	 */
	public String getStudyLookupServiceURL() {
		return getInstance().studyLookupServiceURL;
	}

	/**
	 * @param studyLookupServiceURL the studyLookupServiceURL to set
	 */
	public void setStudyLookupServiceURL(String studyLookupServiceURL) {
		this.studyLookupServiceURL = studyLookupServiceURL;
	}

} // End of Class

