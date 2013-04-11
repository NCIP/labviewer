/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */

package gov.nih.nci.caxchange.client;

import static java.util.concurrent.TimeUnit.SECONDS;
import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.axis.message.MessageElement;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;

/**
 * @author asharma
 *
 */
public class LoadV3Labs {
	private static Logger logger = Logger.getLogger("client"); 
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	private CancerCenterClient cancerCenterClient;
	public LoadV3Labs(CancerCenterClient client){
	    cancerCenterClient = client;
	}
	
	/**
	 * Starts a thread to poll the directory for HL7V3 files.
	 * Invokes the grid service to persist the HL7V3 message.
	 */
	public void process(ArrayList<ScheduledExecutorService>threadList) {
	   threadList.add(scheduler);
		final Runnable fileCheck = new Runnable() {
			public void run() {
				try {
					logger.debug("Polling for HL7V3 Labs"); 
				    MessageElement messageElement=null;
					File inProcessDir = new File(cancerCenterClient.getHl7v3Dir());
					File[] inProcessfileList = inProcessDir.listFiles();
					for (int i = 0; i < inProcessfileList.length; i++) {
						if (inProcessfileList[i].length() == 0)
							inProcessfileList[i].delete();
					}
					File[] fileList = inProcessDir.listFiles();
					for (int i = 0; i < fileList.length; i++) {
						File hl7v3XML = fileList[i];
						DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
						InvokeGridService invokeGridService = new InvokeGridService(cancerCenterClient);	
					    invokeGridService.invokeGridService(builder.parse(hl7v3XML),hl7v3XML);
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
}
