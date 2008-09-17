/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package gov.nih.nci.caxchange.servicemix.bean.routing;

import gov.nih.nci.caXchange.CaxchangeConstants;
import gov.nih.nci.caXchange.outbound.GridInvocationException;
import gov.nih.nci.cagrid.common.Utils;
import gov.nih.nci.caxchange.security.CaXchangePrincipal;
import gov.nih.nci.caxchange.servicemix.bean.util.XPathUtil;
import java.io.StringReader;
import java.security.Principal;
import javax.annotation.Resource;
import javax.jbi.messaging.DeliveryChannel;
import javax.jbi.messaging.ExchangeStatus;
import javax.jbi.messaging.Fault;
import javax.jbi.messaging.MessageExchange;
import javax.jbi.messaging.MessagingException;
import javax.jbi.messaging.NormalizedMessage;
import javax.security.auth.Subject;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.servicemix.MessageExchangeListener;
import org.apache.servicemix.jbi.util.MessageUtil;
import org.cagrid.gaards.cds.client.CredentialDelegationServiceClient;
import org.cagrid.gaards.cds.client.DelegatedCredentialUserClient;
import org.cagrid.gaards.cds.delegated.stubs.types.DelegatedCredentialReference;
import org.cagrid.gaards.cds.stubs.types.CDSInternalFault;
import org.cagrid.gaards.cds.stubs.types.DelegationFault;
import org.cagrid.gaards.cds.stubs.types.PermissionDeniedFault;
import org.globus.gsi.GlobusCredential;
import org.globus.gsi.GlobusCredentialException;
import org.globus.wsrf.encoding.DeserializationException;
/**
 * It includes two components: a pipeline, which invokes a bean component that calls 
 * delegation service for authorization and authentication
 *  
 * @author hmarwaha
 *
 */
public class InvokeDelegationServiceBean implements MessageExchangeListener {

	@Resource
	private DeliveryChannel channel;

	public static final String USERNAME_XPATH = "/caxchange:caXchangeRequestMessage/metaData/credentials/userName";
	public static final String GROUPNAME_XPATH = "/caxchange:caXchangeRequestMessage/metaData/credentials/groupName";
	public static final String GRIDIDENTIFIER_XPATH = "/caxchange:caXchangeRequestMessage/metaData/credentials/gridIdentifier";

	public static final String USERNAME = "userName";
	public static final String GROUPNAME = "groupName";
	public static final String GRIDIDENTIFIER = "gridIdentifier";
	
	private String certificateFilePath, keyFilePath;

	private static GlobusCredential hostCredential = null;
	
	public static Logger logger = LogManager
	.getLogger(InvokeDelegationServiceBean.class);
	
	/**
     * Default constructor
     */
	public InvokeDelegationServiceBean() {

	}
	/**
	 * This method creates the Host credentials from the certificate and the key file provides in 
	 * caxchange.property file
	 * @param 
	 * @return
	 * @throws GridInvocationException
	 */	
	public void init() throws GridInvocationException{
		try{
			hostCredential = new GlobusCredential(certificateFilePath, keyFilePath);
		}catch (GlobusCredentialException e){
			throw new GridInvocationException(
					"Unable to create Host Credentials from the Certificate and Key File", e);
		}
	}
	
	/**
	 * When the POJO Implements the MessageExchangeListener interface of servicemix-bean component
	 * all the exchange will be dispatched to the onMessageExchange() method
	 * @param exchange
	 * @return
	 * @throws MessagingException
	 */	
	public void onMessageExchange(MessageExchange exchange)
	throws MessagingException {
		if (exchange.getStatus().equals(ExchangeStatus.DONE)) {
			return;
		}
		if (exchange.getStatus().equals(ExchangeStatus.ERROR)) {
			return;
		}
		
		NormalizedMessage in = exchange.getMessage("in");
		NormalizedMessage out = exchange.createMessage();
		
		
		try {
			invokeDelegationService(exchange);
		}
		catch (CDSInternalFault e){
			Fault fault = getFault("401","Internal error invoking delegation service."+e.getMessage(), exchange);
			exchange.setFault(fault);
			channel.send(exchange);
			return;			
		}catch (DelegationFault e){
			Fault fault = getFault("402","Delegation fault invoking delegation service."+e.getMessage(), exchange);
			exchange.setFault(fault);
			channel.send(exchange);
			return;	
		}catch (PermissionDeniedFault e){
			Fault fault = getFault("403","Permission denied fault invoking delegation service."+e.getMessage(), exchange);
			exchange.setFault(fault);
			channel.send(exchange);
			return;	
		}
		catch (Throwable e) {
			Fault fault = getFault("400","Error invoking delegation service."+e.getMessage(), exchange);
			exchange.setFault(fault);
			channel.send(exchange);
			return;
		}
		MessageUtil.transfer(in, out);
		exchange.setMessage(out, "out");
		channel.send(exchange);
	}

	/**
	 * invokes the delegation service and sets the subject in the message.
	 * @param exchange
	 * @return
	 * @throws Exception
	 */
	public void invokeDelegationService(MessageExchange exchange)
	throws Exception {
		try {
			NormalizedMessage in = exchange.getMessage("in");
	
			XPathUtil util = new XPathUtil();
			util.setIn(in);
			util.initialize();
			
			//TODO: this is a temporary arrangement for Lab Loader
			if("CT_LAB_DATA".equals(util.getMessageType()))
				return;
			
			
			String delegationEPR = util.getDelegatedCredentialReference();
			
			DelegatedCredentialReference delegatedCredentialReference = null;
			try{
				logger.error("****"+delegationEPR);
				delegatedCredentialReference =  
					(DelegatedCredentialReference)
					                     Utils.deserializeObject(new StringReader(delegationEPR), DelegatedCredentialReference.class, DelegatedCredentialUserClient.class.getResourceAsStream("client-config.wsdd"));

			}catch (DeserializationException e){
				throw new GridInvocationException(
						"Unable to deserialize the Delegation Reference", e);
			}

			DelegatedCredentialUserClient delegatedCredentialUserClient = null;

			try{
				logger.error("**** delegatedCredentialReference.getEndpointReference"+delegatedCredentialReference.getEndpointReference());
				logger.error("**** delegatedCredentialReference.getEndpointReference.getAddress"+delegatedCredentialReference.getEndpointReference().getAddress());
				delegatedCredentialUserClient = new DelegatedCredentialUserClient(
						delegatedCredentialReference, hostCredential);
			}catch (Exception e){
				throw new GridInvocationException(
						"Unable to Initialize the Delegation Lookup Client", e);
			}

			GlobusCredential userCredential;

			try{
				userCredential = delegatedCredentialUserClient
				.getDelegatedCredential();
			}catch (CDSInternalFault e){
				logger.error("Internal error getting delegated credentials.",e); 
				throw e;
			}catch (DelegationFault e){
				logger.error("Delegation fault occurred getting delegated credentials.", e);
				throw e;
			}catch (PermissionDeniedFault e){
				logger.error("Permission denied fault getting delegated credentials.", e);
				throw e;
			}

			Subject subject = new Subject();

			CaXchangePrincipal principal = new CaXchangePrincipal();
			principal.setName(userCredential.getIdentity());
			subject.getPrincipals().add((Principal) principal);

			subject.getPrivateCredentials().add(userCredential);
			in.setSecuritySubject(subject);
			
			logger.debug("usercredential="+userCredential.toString());
		
		} catch (Exception e) {
			logger.error("Error authenticating", e);
			throw e;
		}

	}
	public Fault getFault(String errorCode, String errorMessage, MessageExchange exchange) throws MessagingException {
		NormalizedMessage in = exchange.getMessage("in");
		Fault fault = exchange.createFault();
		MessageUtil.transfer(in, fault);
		fault.setProperty(CaxchangeConstants.ERROR_CODE,
		errorCode);
		fault.setProperty(CaxchangeConstants.ERROR_MESSAGE,
		errorMessage);
        return fault;
	}
	
   /*No references*/
	public String getCertificateFilePath() {
		return certificateFilePath;
	}
	/*No references*/
	public void setCertificateFilePath(String certificateFilePath) {
		this.certificateFilePath = certificateFilePath;
	}
	/*No references*/
	public String getKeyFilePath() {
		return keyFilePath;
	}
	/*No references*/
	public void setKeyFilePath(String keyFilePath) {
		this.keyFilePath = keyFilePath;
	}

}
