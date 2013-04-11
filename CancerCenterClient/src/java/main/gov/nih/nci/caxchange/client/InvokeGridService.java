/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */

package gov.nih.nci.caxchange.client;


import gov.nih.nci.cagrid.caxchange.client.CaXchangeRequestProcessorClient;
import gov.nih.nci.cagrid.caxchange.context.client.CaXchangeResponseServiceClient;
import gov.nih.nci.cagrid.caxchange.context.stubs.types.CaXchangeResponseServiceReference;
import gov.nih.nci.caxchange.Credentials;
import gov.nih.nci.caxchange.Message;
import gov.nih.nci.caxchange.MessagePayload;
import gov.nih.nci.caxchange.MessageTypes;
import gov.nih.nci.caxchange.Metadata;
import gov.nih.nci.caxchange.Request;
import gov.nih.nci.caxchange.ResponseMessage;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.axis.message.MessageElement;
import org.apache.axis.types.URI;
import org.apache.log4j.Logger;
import org.globus.gsi.GlobusCredential;
import org.jasig.cas.authentication.Authentication;
import org.jasig.cas.authentication.principal.UsernamePasswordCredentials;
import org.springframework.core.io.ClassPathResource;
import org.w3c.dom.Document;



/**
 * @author asharma
 *
 */
public class InvokeGridService {
    
    CancerCenterClient cancerCenterClient;
    GlobusCredential userCredentials;
    static Logger logger = Logger.getLogger("client");
    static final String CONFIG_FILE = "/project.properties";

    public InvokeGridService(CancerCenterClient client) {
        cancerCenterClient = client;
        client=null;
        userCredentials=null;
    }

    public final void invokeGridService(Document payload, File hl7v3XML) throws Exception {
        try {
            boolean gotResponse = false;
            boolean hl7v3move=false;
            int responseCount = 0;
            Credentials creds = new Credentials();
            String username = cancerCenterClient.getUserName();
            String password = cancerCenterClient.getUserPasswd();        
            creds.setUserName(username);
            creds.setPassword(password);
            // get the cred expr\
            String delegatedReference = null;
            org.cagrid.gaards.websso.authentication.CaGridAuthenticationManager authManager = new org.cagrid.gaards.websso.authentication.CaGridAuthenticationManager();
            UsernamePasswordCredentials credentials = new UsernamePasswordCredentials();
            credentials.setUsername(username.trim());
            credentials.setPassword(password.trim());
            Authentication auth = authManager.authenticate(credentials);
            delegatedReference =  authManager.getSerializedDelegationEpr();
            creds.setDelegatedCredentialReference(delegatedReference);
            userCredentials = authManager.getCredentials();

            ResponseMessage responseMessage = null;
            Message requestMessage = new Message();
            Metadata metadata = new Metadata();
            metadata.setExternalIdentifier("CTODS");
            metadata.setCredentials(creds);
            metadata.setServiceType(MessageTypes._CT_LAB_DATA);
            requestMessage.setMetadata(metadata);
            Request caxchangeRequest = new Request();
            requestMessage.setRequest(caxchangeRequest);
            
            MessagePayload messagePayload = new MessagePayload();
            URI uri = new URI();
            uri.setPath("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain");
            messagePayload.setXmlSchemaDefinition(uri);
            MessageElement messageElement = new MessageElement(payload.getDocumentElement());
            messagePayload.set_any(new MessageElement[] { messageElement });
            requestMessage.getRequest().setBusinessMessagePayload(messagePayload);

            CaXchangeRequestProcessorClient client =
                new CaXchangeRequestProcessorClient(cancerCenterClient.getHubURL(), userCredentials);
            CaXchangeResponseServiceReference crsr = client.processRequestAsynchronously(requestMessage);
            CaXchangeResponseServiceClient responseService = new CaXchangeResponseServiceClient(crsr.getEndpointReference(), userCredentials);
            
            while (!gotResponse) {
                try {
                    responseMessage = responseService.getResponse();
    
                    if (responseMessage.getResponse().getResponseStatus().toString().equalsIgnoreCase("Success")) {
                        InvokeGridService.logger.info("Response:Success - Moving File " + hl7v3XML.getName()
                                + Messages.getString("CancerCenterClient.65") + cancerCenterClient.getProcessedDir());
                        hl7v3move = hl7v3XML.renameTo(new File(cancerCenterClient.getProcessedDir(),hl7v3XML.getName()));
                        gotResponse = true;
                    } else if (responseMessage.getResponse().getResponseStatus().toString().equalsIgnoreCase("Failure")) {
                        logger.info("Response Failure: - Moving File " + hl7v3XML.getName() + " To " + cancerCenterClient.getProcessedDir());
                        hl7v3move = hl7v3XML.renameTo(new File(cancerCenterClient.getErrorDir(), hl7v3XML.getName()));
                        gotResponse = true;
                    } else {
                        logger.info("Error in Response:- Moving File " + hl7v3XML.getName() + " To " + cancerCenterClient.getProcessedDir());
                        hl7v3move = hl7v3XML.renameTo(new File(cancerCenterClient.getErrorDir(), hl7v3XML.getName()));
                    }
                } catch (Exception e) {
                    logger.info(Messages.getString("CancerCenterClient.71"));
                    responseCount++;
                    if (responseCount > 25) {
                        InvokeGridService.logger.error(Messages.getString("CancerCenterClient.72"));
                        throw new Exception(Messages.getString("CancerCenterClient.73"));
                    }
                    Thread.sleep(1000);
                }
            }
    
            if (responseMessage != null) {
                logger.info(Messages.getString("CancerCenterClient.74")
                        + responseMessage.getResponse().getResponseStatus().toString());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public InputStream getResourceInputStream(String fileName) throws Exception {
        ClassPathResource cpr = new ClassPathResource(fileName);
        if (!cpr.exists()) {
            throw new Exception(fileName+" does not exist.");
        }
        try {
          InputStream inputStream = cpr.getInputStream();
          return inputStream;
        }catch(IOException e) {
            throw new Exception("Error loading file "+fileName);
        }
    }
    

}
