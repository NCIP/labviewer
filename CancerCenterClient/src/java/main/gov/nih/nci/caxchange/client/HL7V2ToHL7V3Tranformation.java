/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */

package gov.nih.nci.caxchange.client;

import static java.util.concurrent.TimeUnit.SECONDS;
import gov.nih.nci.caadapter.ui.mapping.V2V3.ConvertFromV2ToCSV;

import java.io.File;
import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import org.apache.log4j.Logger;

/**
 * @author asharma
 *
 */
public class HL7V2ToHL7V3Tranformation {

	// Logging File
	private static Logger logger = Logger.getLogger("client"); 

	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

	private CancerCenterClient cancerCenterClient;
	private String fileNameLocationDateTimeStamp;
	
	public HL7V2ToHL7V3Tranformation(CancerCenterClient client){
		cancerCenterClient = client;
	}
	
	/**
	 * Starts a thread to poll the directory for HL7V2 file.
	 * It invokes the caAdapter API to convert a HL7V2 file to .CSV file.
	 * Invokes the caAdapter API to convert a generated .CSV to HL7V3.
	 * Invokes the grid service to persist the generated HL7V3 message.
	 */
	public void process(ArrayList<ScheduledExecutorService>threadList) {
	   threadList.add(scheduler);
		final Runnable fileCheck = new Runnable() {

			public void run() {
				try {
					logger.debug(Messages.getString("Polling for HL7V2 files")); 
					File inProcessDir = new File(cancerCenterClient.getHl7v2Dir());
					File[] inProcessfileList = inProcessDir.listFiles();
					for (int i = 0; i < inProcessfileList.length; i++) {
						if (inProcessfileList[i].length() == 0)
							inProcessfileList[i].delete();
					}
					File[] fileList = inProcessDir.listFiles();
					for (int i = 0; i < fileList.length; i++) {
						String csvFileName = getFileName(fileList[i].getName().toString());
						boolean transformed=invokecaAdapterAPIV2toV3(fileList[i].getAbsolutePath(),csvFileName,cancerCenterClient.getScsFileName());
						if(transformed)
						{
							logger.info("V2 to V3 Transformation complete");
							boolean success = new File(csvFileName).renameTo(new File(
									cancerCenterClient.getInProcessFolder(),fileNameLocationDateTimeStamp));
							boolean Tsuccess = fileList[i].renameTo(new File(
									cancerCenterClient.getRawFilesBackupDirectory(),fileNameLocationDateTimeStamp));
							logger.info("Rename to Tsuccess" +Tsuccess);
							if (!success) {
								logger.error(fileList[i].toString() + "Renamed to : "+ csvFileName +"InProcess folder");
							} else {
								logger.debug("File Moved to In Process folder");
								new File(csvFileName).delete();
							}
						}
					}
				} catch (Exception e) {
					logger.fatal(Messages.getString("CancerCenterClient.22")); 
				}
			}
		};
		final ScheduledFuture<?> fileCheckHandle = scheduler
				.scheduleAtFixedRate(fileCheck,cancerCenterClient.getInitialDelay_long(),
						cancerCenterClient.getPollingInterval_long(), SECONDS);
	}

	
	/**
	 * Invokes the caAdapter API to convert the HL7V2 message file to HL7V3
	 * message. This is a 2 step process 
	 * 1.Convert HL7V2 to .CSV file 
	 * 2.Convert .CSV file to HL7V3 message - 
	 * 	call the method callTocaAdapterAPI() to perform the transformation.
	 * @param hl7FileName
	 * @param csvFileName
	 * @param scsFileName
	 * @throws Exception
	 */
	public boolean invokecaAdapterAPIV2toV3(String hl7FileName,String csvFileName,String scsFileName) throws Exception {
        boolean flag =false;
		try {
			String hl7v2file =  hl7FileName.replace('\\', '\\');// input v2 message file
			String metaDir = "./v2Meta";// v2 meta data directory
			String csvFile = csvFileName.replace('/', '\\');             // output csv file
			String scsFile = scsFileName.replace('/', '\\');              // scs file for validation
			String msgtype="ORU^R01";                  // message type
			String version = cancerCenterClient.getVersion();                      // target version
	        ConvertFromV2ToCSV con = new ConvertFromV2ToCSV(metaDir , hl7v2file , msgtype , version
	                                                        , csvFile , scsFile , false );
	        flag=con.isSuccessful();  
			if (!flag) {
				List<String> errList = con.getErrorMessages();
				for(String messages: errList){
				  logger.error(messages);
				}
			}
		} catch (Exception e) {
			logger.error(Messages.getString("CancerCenterClient.51"), e.fillInStackTrace());
		}
		return flag;
	}
   
	/**
	 * Returns the intermediate CSV file.
	 * @param fileName
	 * @return outFile intermediate CSV file 
	 */
	private String getFileName(String fileName)
	{
		// Get Current Date and Time for Stamping the file
		Date dt = new Date();
		StringBuffer sbuf = new StringBuffer();
		StringBuffer outbuf = new StringBuffer();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
		sbuf = sdf.format(dt, outbuf, new FieldPosition(0));
		String[] strFile = fileName.split("\\."); 
	    fileNameLocationDateTimeStamp =  cancerCenterClient.getLocation() + "_" + sbuf + "_" + "V2TOV3" + "_" + strFile[0] +".csv";
		String outFile = cancerCenterClient.getRawFilesBackupDirectory()+"\\"+ fileNameLocationDateTimeStamp;
		return outFile;
	}
}
