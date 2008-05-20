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
import edu.knu.medinfo.hl7.v2tree.HL7MessageTreeException;
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
	private static Logger logger = Logger
			.getLogger("gov.nih.nci.caxchange.client.HL7V2ToHL7V3Tranformation"); 

	private final ScheduledExecutorService scheduler = Executors
			.newScheduledThreadPool(1);

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
					logger.debug(Messages.getString("CancerCenterClient.128")); 
					if (!cancerCenterClient.checkPreProcessedFolder()) {
						logger.error(Messages.getString("CancerCenterClient.20")); 
						throw new Exception(
								Messages.getString("CancerCenterClient.21")); 
					} else {
						File inProcessDir = new File(cancerCenterClient.getHl7v2Dir());
						// Delete Zero Byte Files Generated by Preprocessor in
						// case of exceptions.
						File[] inProcessfileList = inProcessDir.listFiles();
						for (int i = 0; i < inProcessfileList.length; i++) {
							if (inProcessfileList[i].length() == 0)
								inProcessfileList[i].delete();
						}
						File[] fileList = inProcessDir.listFiles();
						for (int i = 0; i < fileList.length; i++) {
							String csvFileName = getFileName(fileList[i].getName().toString());
							// invokes the caAdapter API to convert a HL7V2 file
							// to HL7V3.
							boolean transformed=invokecaAdapterAPIV2toV3(fileList[i].getAbsolutePath(),csvFileName,cancerCenterClient.getScsFileName());
							if(transformed)
							{
								System.out.println("Transformed");
								boolean success = new File(csvFileName).renameTo(new File(
										cancerCenterClient.getInProcessFolder(),
										fileNameLocationDateTimeStamp));
								//System.out.println("success" +success);
								boolean Tsuccess = fileList[i].renameTo(new File(
										cancerCenterClient.getRawFilesBackupDirectory(),
										fileNameLocationDateTimeStamp));
								//System.out.println("Tsuccess" +Tsuccess);
								if (!success) {
									logger.error(fileList[i].toString() + "Renamed to : "
											+ csvFileName +"InProcess folder");
									} else {
									logger.debug("File Moved to In Process folder");
									new File(csvFileName).delete();
								}
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
			 // Constructor
	        ConvertFromV2ToCSV con = new ConvertFromV2ToCSV(metaDir
	                                                        , hl7v2file           
	                                                        , msgtype
	                                                        , version
	                                                        , csvFile
	                                                        , scsFile
	                                                        , false                      // this value must be false
	                                                      );
	        flag=con.isSuccessful();  
			if (!flag) {
				List<String> errList = con.getErrorMessages();
				for(String messages: errList){
				  logger.error(messages);
				}
			}
		} catch (Exception e) {
			logger.error(Messages.getString("CancerCenterClient.51"), e 
					.fillInStackTrace());
		}
		return flag;
	}
   
	/**
	 * @param fileName
	 * @return
	 */
	private String getFileName(String fileName)
	{
		// Get Current Date and Time for Stamping the file
		Date dt = new Date();
		long currentTime = dt.getTime();
		StringBuffer sbuf = new StringBuffer();
		StringBuffer outbuf = new StringBuffer();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");

		sbuf = sdf.format(dt, outbuf, new FieldPosition(0));
		
		String[] strFile = fileName.split("\\."); 
	    fileNameLocationDateTimeStamp =  cancerCenterClient.getLocation() + "_" + sbuf + "_" + "V2TOV3"
		+ "_" + strFile[0] +".csv";
		String outFile = cancerCenterClient.getRawFilesBackupDirectory()+"\\"+ fileNameLocationDateTimeStamp;
		return outFile;
	}
}
