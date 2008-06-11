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

import gov.nih.nci.cagrid.caxchange.client.CaXchangeRequestProcessorClient;
import gov.nih.nci.cagrid.caxchange.context.client.CaXchangeResponseServiceClient;
import gov.nih.nci.cagrid.caxchange.context.stubs.GetResponseResponse;
import gov.nih.nci.cagrid.caxchange.context.stubs.types.CaXchangeResponseServiceReference;
import gov.nih.nci.caxchange.Message;
import gov.nih.nci.caxchange.MessagePayload;
import gov.nih.nci.caxchange.MessageTypes;
import gov.nih.nci.caxchange.Metadata;
import gov.nih.nci.caxchange.Request;
import gov.nih.nci.caxchange.ResponseMessage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.Properties;

import org.apache.axis.message.MessageElement;
import org.apache.axis.types.URI;
import org.apache.axis.types.URI.MalformedURIException;
import org.apache.log4j.Logger;
import org.globus.gsi.GlobusCredential;
import org.globus.gsi.GlobusCredentialException;

/**
 * @author asharma
 *
 */
public class InvokeGridService {
	
	CancerCenterClient cancerCenterClient;

	// Logging File
	static Logger logger = Logger
			.getLogger("client");
	static final String CONFIG_FILE = "/project.properties";

	public InvokeGridService(CancerCenterClient client) {
		cancerCenterClient = client;
	}

	/**
	 * Invokes the Grid Service HUB to load the HL7V3 message.
	 * 
	 * @param fileList
	 * @param i
	 * @param HL7V3
	 * @throws Exception
	 */
	public final void invokeGridService(MessageElement messageElement, File hl7v3XML) throws Exception {
		try {
			boolean gotResponse = false;
			boolean hl7v3move=false;
			GetResponseResponse getResponse = null;
			Properties props = new Properties();
			InputStream istream = getClass().getResourceAsStream(InvokeGridService.CONFIG_FILE);
			props.load(istream);
			String proxyFile = (String)props.getProperty("proxyFile");
			GlobusCredential gb =new GlobusCredential(proxyFile);
			CaXchangeRequestProcessorClient client = new CaXchangeRequestProcessorClient(
					cancerCenterClient.getHubURL(),gb);
			// creates the caXchange Message
			Message requestMessage = new Message();
			MessagePayload messagePayload = this.createMessage(requestMessage);
	
				messagePayload.set_any(new MessageElement[] { messageElement });
				requestMessage.getRequest().setBusinessMessagePayload(
						messagePayload);
	            CaXchangeResponseServiceReference crsr = client
						.processRequestAsynchronously(requestMessage);
	
				CaXchangeResponseServiceClient responseService = new CaXchangeResponseServiceClient(
						crsr.getEndpointReference());
	
				int responseCount = 0;
				ResponseMessage responseMessage = null;
				while (!gotResponse) {
					try {
						responseMessage = responseService.getResponse();
	
						if (responseMessage.getResponse().getResponseStatus()
								.toString().equalsIgnoreCase("Success")) {
	
							InvokeGridService.logger.info("Response:Success -Moving File "
									+ hl7v3XML.getName()
									+ Messages
											.getString("CancerCenterClient.65")
									+ cancerCenterClient.getProcessedDir());
							hl7v3move = hl7v3XML.renameTo(new File(
									cancerCenterClient.getProcessedDir(),
									hl7v3XML.getName()));
							gotResponse = true;
						} else if (responseMessage.getResponse()
								.getResponseStatus().toString()
								.equalsIgnoreCase("Failure")) {
	
							InvokeGridService.logger.info("Response Failure: -Moving File "
									+ hl7v3XML.getName() + " To "
									+ cancerCenterClient.getProcessedDir());
							hl7v3move = hl7v3XML.renameTo(new File(
									cancerCenterClient.getErrorDir(), hl7v3XML
											.getName()));
							gotResponse = true;
						} else {
							InvokeGridService.logger.info("Error Moving File "
									+ hl7v3XML.getName() + " To "
									+ cancerCenterClient.getProcessedDir());
							hl7v3move = hl7v3XML.renameTo(new File(
									cancerCenterClient.getErrorDir(), hl7v3XML
											.getName()));
	
						}
	
					} catch (Exception e) {
						InvokeGridService.logger.info(
								Messages.getString("CancerCenterClient.71"), e);
						responseCount++;
						if (responseCount > 1) {
							InvokeGridService.logger.error(Messages
									.getString("CancerCenterClient.72"));
							throw new Exception(Messages
									.getString("CancerCenterClient.73"));
						}
						Thread.sleep(1000);
					}
				}
	
				if (responseMessage != null) {
					InvokeGridService.logger.info(Messages.getString("CancerCenterClient.74")
							+ responseMessage.getResponse().getResponseStatus()
									.toString());
				}
			if (!hl7v3move) {
				InvokeGridService.logger.info("Error Moving File"+ hl7v3XML.getName());
			}
		} catch (MalformedURIException e) {
			InvokeGridService.logger.error("MalformedURIException" + e.getLocalizedMessage());
		} catch (RemoteException e) {
				InvokeGridService.logger.error("RemoteException" + e);
		} catch (IOException e) {
				InvokeGridService.logger.error("IOException" + e.getLocalizedMessage());
		} catch (InterruptedException e) {
	
			InvokeGridService.logger.error("InterruptedException" + e.getLocalizedMessage());
		} catch (GlobusCredentialException e1) {
			InvokeGridService.logger.error("GlobusCredentialException" + e1.getLocalizedMessage());
		}catch (Exception e) {
			InvokeGridService.logger.error("Exception" + e.getLocalizedMessage());
		}
	
	}

	/**
	 * Create the caXchange message
	 * 
	 * @param requestMessage
	 * @return messagePayload
	 */
	private MessagePayload createMessage(Message requestMessage) {
		// Create the caXchange message
		Metadata metadata = new Metadata();
		metadata.setExternalIdentifier("CTODS");
		metadata.setMessageType(MessageTypes.CT_LAB_DATA);
		
		//  Credentials creds = new Credentials();// Optional Credentials - for
		// testing purposes comment out the creds.
		// creds.setUserName(userName);
		// creds.setPassword(userPasswd);
		 
	
		requestMessage.setMetadata(metadata);
		Request caxchangeRequest = new Request();
		requestMessage.setRequest(caxchangeRequest);
		MessagePayload messagePayload = new MessagePayload();
		URI uri = new URI();
		try {
			uri.setPath("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain");
		} catch (MalformedURIException e) {
			InvokeGridService.logger.error("MalformedURIException" + e);
		}
		messagePayload.setXmlSchemaDefinition(uri);
	
		return messagePayload;
	}

}
