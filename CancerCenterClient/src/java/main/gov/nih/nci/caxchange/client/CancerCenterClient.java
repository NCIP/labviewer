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

/**
 * @author asharma
 *
 */
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
	private String scsFileName;
	private String version;

	// Map File used for mapping CSV file to HL7V3 format by caAdaptor.
	private String mapFileName;

	// Logging File

	private static Logger logger = Logger
			.getLogger("gov.nih.nci.caxchange.client.CancerCenterClient");

	private static CancerCenterClient cancerCenterClient=null;

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
	public static synchronized CancerCenterClient getInstance() {
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
			this.programPropertiesFile = configFilePath;
			f = null;
		}
		logger.debug(Messages.getString("this.129"));
		boolean success = this.checkSetup();
		if (success) {
			this.process(threadList);
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

			this.rawFilesDir=new File( rawFilesFolder);
			this.rawFilesBackupDirectory=new File( rawFilesBackupFolder);

			this.inProcessDir=new File( inProcessFolder);
			this.processedDir=new File( processedFolder);
			this.errorDir=new File( errorFolder);

			File preProcessorPropertiesFile_file = new File(
					this.preProcessorPropertiesFile_str);

			if (! preProcessorPropertiesFile_file.exists()) {
				throw new PropertiesFileException(Messages
						.getString("CancerCenterClient.8"));
			}
			if (! this.rawFilesDir.exists()) {
				throw new FolderDoesNotExistException(Messages
						.getString("CancerCenterClient.9"));
			}
			if (! this.rawFilesDir.isDirectory()) {
				throw new FolderNotADirectoryException(Messages
						.getString("CancerCenterClient.10"));
			}
			if (! this.rawFilesBackupDirectory.exists()) {
				throw new FolderDoesNotExistException(Messages
						.getString("CancerCenterClient.11"));
			}
			if (!this.rawFilesBackupDirectory.isDirectory()) {
				throw new FolderNotADirectoryException(Messages
						.getString("CancerCenterClient.12"));
			}
			if (! this.inProcessDir.exists()) {
				throw new FolderDoesNotExistException(Messages
						.getString("CancerCenterClient.13"));
			}
			if (! this.inProcessDir.isDirectory()) {
				throw new FolderNotADirectoryException(Messages
						.getString("CancerCenterClient.14"));
			}
			if (! this.processedDir.exists()) {
				throw new FolderDoesNotExistException(Messages
						.getString("CancerCenterClient.15"));
			}
			if (! this.processedDir.isDirectory()) {
				throw new FolderNotADirectoryException(Messages
						.getString("CancerCenterClient.16"));
			}
			if (! this.errorDir.exists()) {
				throw new FolderDoesNotExistException(Messages
						.getString("CancerCenterClient.17"));
			}
			if (! this.errorDir.isDirectory()) {
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
	 * @param threadList
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
	public synchronized boolean checkPreProcessedFolder() {
		logger.debug(Messages.getString("CancerCenterClient.23"));
		boolean retResult = true;
		try {
			File[] fileList =  rawFilesDir.listFiles();
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

				String fileNameLocationDateTimeStamp = this.location + "_" + sbuf
						+ "_" + fileName;
				String outFile = this.inProcessFolder
						+ fileNameLocationDateTimeStamp;
				try {
					logger.debug(Messages
							.getString("CancerCenterClient.29"));
					CSVP fileOut = new CSVP(fileList[i].toString(), outFile,
							this.preProcessorPropertiesFile_str);

					boolean success = fileList[i].renameTo(new File(
							this.rawFilesBackupDirectory,
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
					logger.info("Erro Dir : " +  this.errorDir);
					logger.info("Error folder : " + this.errorFolder);
					logger.info("fileNameLocationDateTimeStamp : "
							+ fileNameLocationDateTimeStamp);
					logger.info("File : " + fileList[i]);
					logger.info("*****");
					boolean movesuccess = fileList[i].renameTo(new File(
							this.errorDir, fileNameLocationDateTimeStamp));
					if (!movesuccess) {
						logger.error(fileList[i].toString() + "Renamed to : "
								+  this.errorDir + fileNameLocationDateTimeStamp
								+ " Was not moved to the error folder");
						logger.info("*****");
					} else {
						logger.error(fileList[i].toString() + "Renamed to : "
								+  this.errorDir + fileNameLocationDateTimeStamp
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
					this.programPropertiesFile));

			Properties props = new Properties();
			// Read in the stored properties
			props.load(fis);
			this.location = props.getProperty("Location");
			this.preProcessorPropertiesFile_str= props
					.getProperty("preProcessorPropertiesFile");
			this.userName = props.getProperty("userName");
			this.userPasswd = props.getProperty("userPasswd");
			this.rawFilesFolder=props.getProperty("rawFilesFolder");
			this.rawFilesBackupFolder=props.getProperty("rawFilesBackupFolder");
			this.inProcessFolder=props.getProperty("inProcessFolder");
			this.processedFolder=props.getProperty("processedFolder");
			this.errorFolder=props.getProperty("errorFolder");
			this.mapFileName=props.getProperty("mapFileName");
			this.hl7v2Dir=props.getProperty("HL7V2Dir");
			this.hl7v2mapFileName=props.getProperty("hl7v2mapFileName");
			this.scsFileName=props.getProperty("scsFileName");
			this.initialDelay_str=props.getProperty("initialDelayInSeconds");
			this.pollingInterval_str=props.getProperty("pollingDelayInSeconds");
			this.hubURL=props.getProperty("HubURL");
			this.studyLookupServiceURL=props.getProperty("StudyLookUpServiceURL");
			this.version=props.getProperty("V2Version");
			logger.info("preProcessorPropertiesFile : "
					+ this.preProcessorPropertiesFile_str);
			logger.info("Location : " + this.location);
			logger.info("rawFilesFolder : " + this.rawFilesFolder);
			logger.info("rawFilesBackupFolder : " + this.rawFilesBackupFolder);
			logger.info("inProcessFolder : " + this.inProcessFolder);
			logger.info("ProcessedFolder : " + this.processedFolder);
			logger.info("ErrorFolder : " + this.errorFolder);
			logger.info("initialDelay_str : " + this.initialDelay_str);
			logger.info("pollingInterval_str : " + this.pollingInterval_str);
			logger.info("hubURL : " + this.hubURL);
			logger.info("hl7v2Dir : " + this.hl7v2Dir);
			logger.info("hl7v2mapFileName : " + this.hl7v2mapFileName);
			logger.info("scsFileName : " + this.scsFileName);
			this.initialDelay_long = new Long(this.initialDelay_str);
			this.pollingInterval_long = new Long(this.pollingInterval_str);
			logger.info("initialDelay_long : " + this.initialDelay_long);
			logger.info("pollingInterval_long : " + this.pollingInterval_long);
			logger.info("V2Version : " + this.version);

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
		return this.preProcessorPropertiesFile_str;
	}

	/**
	 * @return the programPropertiesFile
	 */
	public String getProgramPropertiesFile() {
		return this.programPropertiesFile;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return this.location;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return this.userName;
	}

	/**
	 * @return the userPasswd
	 */
	public String getUserPasswd() {
		return this.userPasswd;
	}

	/**
	 * @return the inProcessFolder
	 */
	public String getInProcessFolder() {
		return this.inProcessFolder;
	}

	/**
	 * @return the processedFolder
	 */
	public String getProcessedFolder() {
		return this.processedFolder;
	}

	/**
	 * @return the errorFolder
	 */
	public String getErrorFolder() {
		return this.errorFolder;
	}

	/**
	 * @return the rawFilesFolder
	 */
	public String getRawFilesFolder() {
		return this.rawFilesFolder;
	}

	/**
	 * @return the rawFilesBackupFolder
	 */
	public String getRawFilesBackupFolder() {
		return this.rawFilesBackupFolder;
	}

	/**
	 * @return the initialDelay_str
	 */
	public String getInitialDelay_str() {
		return this.initialDelay_str;
	}

	/**
	 * @return the pollingInterval_str
	 */
	public String getPollingInterval_str() {
		return this.pollingInterval_str;
	}

	/**
	 * @return the initialDelay_long
	 */
	public long getInitialDelay_long() {
		return this.initialDelay_long;
	}

	/**
	 * @return the pollingInterval_long
	 */
	public long getPollingInterval_long() {
		return this.pollingInterval_long;
	}

	/**
	 * @return the processedDir
	 */
	public File getProcessedDir() {
		return this.processedDir;
	}

	/**
	 * @return the inProcessDir
	 */
	public File getInProcessDir() {
		return this.inProcessDir;
	}

	/**
	 * @return the errorDir
	 */
	public File getErrorDir() {
		return this.errorDir;
	}

	/**
	 * @return the rawFilesDir
	 */
	public File getRawFilesDir() {
		return this.rawFilesDir;
	}

	/**
	 * @return the rawFilesBackupDirectory
	 */
	public File getRawFilesBackupDirectory() {
		return this.rawFilesBackupDirectory;
	}

	/**
	 * @return the hubURL
	 */
	public String getHubURL() {
		return this.hubURL;
	}

	/**
	 * @return the mapFileName
	 */
	public String getMapFileName() {
		return this.mapFileName;
	}

	/**
	 * @return the hl7v2Dir
	 */
	public String getHl7v2Dir() {
		return this.hl7v2Dir;
	}

	/**
	 * @return the hl7v2mapFileName
	 */
	public String getHl7v2mapFileName() {
		return this.hl7v2mapFileName;
	}

	/**
	 * @param preProcessorPropertiesFile_str the preProcessorPropertiesFile_str to set
	 */
	public void setPreProcessorPropertiesFile_str(
			String preProcessorPropertiesFile_string) {
		this.preProcessorPropertiesFile_str = preProcessorPropertiesFile_string;
	}

	/**
	 * @param programPropertiesFile the programPropertiesFile to set
	 */
	public void setProgramPropertiesFile(String programPropertiesfile) {
		this.programPropertiesFile = programPropertiesfile;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(String loc) {
		this.location = loc;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String user) {
		this.userName = user;
	}

	/**
	 * @param userPasswd the userPasswd to set
	 */
	public void setUserPasswd(String userPassword) {
		this.userPasswd = userPassword;
	}

	/**
	 * @param inProcessFolder the inProcessFolder to set
	 */
	public void setInProcessFolder(String inProcessFoldr) {
		this.inProcessFolder = inProcessFoldr;
	}

	/**
	 * @param processedFolder the processedFolder to set
	 */
	public void setProcessedFolder(String processedFoldr) {
		this.processedFolder = processedFoldr;
	}

	/**
	 * @param errorFolder the errorFolder to set
	 */
	public void setErrorFolder(String errorFoldr) {
		this.errorFolder = errorFoldr;
	}

	/**
	 * @param rawFilesFolder the rawFilesFolder to set
	 */
	public void setRawFilesFolder(String rawFilesFoldr) {
		this.rawFilesFolder = rawFilesFoldr;
	}

	/**
	 * @param rawFilesBackupFolder the rawFilesBackupFolder to set
	 */
	public void setRawFilesBackupFolder(String rawFilesBackupFoldr) {
		this.rawFilesBackupFolder = rawFilesBackupFoldr;
	}

	/**
	 * @param initialDelay_str the initialDelay_str to set
	 */
	public void setInitialDelay_str(String initialDelay_string) {
		this.initialDelay_str = initialDelay_string;
	}

	/**
	 * @param pollingInterval_str the pollingInterval_str to set
	 */
	public void setPollingInterval_str(String pollingInterval_string) {
		this.pollingInterval_str = pollingInterval_string;
	}

	/**
	 * @param initialDelay_long the initialDelay_long to set
	 */
	public void setInitialDelay_long(long initialDelay_lg) {
		this.initialDelay_long = initialDelay_lg;
	}

	/**
	 * @param pollingInterval_long the pollingInterval_long to set
	 */
	public void setPollingInterval_long(long pollingInterval_lg) {
		this.pollingInterval_long = pollingInterval_lg;
	}

	/**
	 * @param processedDir the processedDir to set
	 */
	public void setProcessedDir(File processedDirectory) {
		this.processedDir = processedDirectory;
	}

	/**
	 * @param inProcessDir the inProcessDir to set
	 */
	public void setInProcessDir(File inProcessDirectory) {
		this.inProcessDir = inProcessDirectory;
	}

	/**
	 * @param errorDir the errorDir to set
	 */
	public void setErrorDir(File errorDirectory) {
		this.errorDir = errorDirectory;
	}

	/**
	 * @param rawFilesDir the rawFilesDir to set
	 */
	public void setRawFilesDir(File rawFilesDirectory) {
		this.rawFilesDir = rawFilesDirectory;
	}

	/**
	 * @param rawFilesBackupDir the rawFilesBackupDirectory to set
	 */
	public void setRawFilesBackupDirectory(File rawFilesBackupDir) {
		this.rawFilesBackupDirectory = rawFilesBackupDir;
	}

	/**
	 * @param huburl the hubURL to set
	 */
	public void setHubURL(String huburl) {
		this.hubURL = huburl;
	}

	/**
	 * @param hl7v2Directory the hl7v2Dir to set
	 */
	public void setHl7v2Dir(String hl7v2Directory) {
		this.hl7v2Dir = hl7v2Directory;
	}

	/**
	 * @param hl7v2mapFile the hl7v2mapFileName to set
	 */
	public void setHl7v2mapFileName(String hl7v2mapFile) {
		this.hl7v2mapFileName = hl7v2mapFile;
	}

	/**
	 * @param mapFile the mapFileName to set
	 */
	public void setMapFileName(String mapFile) {
		this.mapFileName = mapFile;
	}

	/**
	 * @return the studyLookupServiceURL
	 */
	public String getStudyLookupServiceURL() {
		return this.studyLookupServiceURL;
	}

	
	/**
	 * @param studyLookupServiceurl
	 */
	public void setStudyLookupServiceURL(String studyLookupServiceurl) {
		this.studyLookupServiceURL = studyLookupServiceurl;
	}
	/**
	 * @return the version
	 */
	public String getVersion() {
		return this.version;
	}

	/**
	 * @param ver the version to set
	 */
	public void setVersion(String ver) {
		this.version= ver;
	}
	/**
	 * @return the scsFileName
	 */
	public String getScsFileName() {
		return this.scsFileName;
	}

	/**
	 * @param scsFile the scsFileName to set
	 */
	public void setScsFileName(String scsFile) {
		this.scsFileName= scsFile;
	}
} // End of Class

