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
import gov.nih.nci.caxchange.jdbc.CaxchangeMessage;

import gov.nih.nci.caxchange.persistence.CaxchangeMessageDAO;

import gov.nih.nci.caxchange.persistence.DAOFactory;

import java.security.Principal;

import java.util.HashMap;
import java.util.Map;

import java.util.Set;

import org.apache.servicemix.MessageExchangeListener;

import javax.annotation.Resource;
import javax.jbi.messaging.DeliveryChannel;
import javax.jbi.messaging.ExchangeStatus;
import javax.jbi.messaging.Fault;
import javax.jbi.messaging.MessageExchange;
import javax.jbi.messaging.MessagingException;

import javax.jbi.messaging.NormalizedMessage;

import javax.security.auth.Subject;

import javax.security.auth.login.LoginContext;

import javax.xml.transform.Source;

import javax.xml.transform.Transformer;

import javax.xml.transform.TransformerFactory;

import javax.xml.transform.stream.StreamResult;

import gov.nih.nci.caxchange.security.CaXchangePrincipal;
import gov.nih.nci.caxchange.security.DelegationServiceCallbackHandler;

import gov.nih.nci.caxchange.security.DelegationServiceLoginConfiguration;


import java.io.StringWriter;

import javax.jbi.messaging.InOnly;

import javax.jbi.messaging.RobustInOnly;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.servicemix.expression.JAXPNodeSetXPathExpression;
import org.apache.servicemix.jbi.jaxp.SourceTransformer;

import org.apache.servicemix.jbi.jaxp.StringSource;

import org.apache.servicemix.jbi.util.MessageUtil;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import gov.nih.nci.caxchange.servicemix.bean.util.*;

public class InvokeDeploymentServiceBean implements MessageExchangeListener {

    @Resource
    private DeliveryChannel channel;

    public static final String USERNAME_XPATH="/caxchange:caXchangeRequestMessage/metaData/credentials/userName";
    public static final String GROUPNAME_XPATH="/caxchange:caXchangeRequestMessage/metaData/credentials/groupName";
    public static final String GRIDIDENTIFIER_XPATH="/caxchange:caXchangeRequestMessage/metaData/credentials/gridIdentifier";

    public static final String USERNAME="userName";
    public static final String GROUPNAME="groupName";
    public static final String GRIDIDENTIFIER="gridIdentifier";

    public static Logger logger= LogManager.getLogger(InvokeDeploymentServiceBean.class);


    public void onMessageExchange(MessageExchange exchange) throws MessagingException {
        if (exchange.getStatus().equals(ExchangeStatus.DONE)) {
            return;
        }
        if (exchange.getStatus().equals(ExchangeStatus.ERROR)) {
            return;
        }
        logger.debug("Received exchange: " + exchange);
        NormalizedMessage in = exchange.getMessage("in");
        NormalizedMessage out= exchange.createMessage();
        try {
            invokeDelegationService(exchange);
        }catch (Exception e) {
            logger.error("Error calling delegation service.",e);
            Fault fault = exchange.createFault();
            MessageUtil.transfer(in, fault);
            fault.setProperty(CaxchangeConstants.ERROR_CODE, "DELEGATION_SERVICE_ERROR");
            fault.setProperty(CaxchangeConstants.ERROR_MESSAGE, "Error occurred calling  delegation service.");
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
     *
     * @param exchange
     * @throws Exception
     */
    public void invokeDelegationService(MessageExchange exchange) throws Exception {
       try {
         NormalizedMessage in = exchange.getMessage("in");
         Map<String,String> credentials = getCredentials(exchange);
         Subject subject = new Subject();
          DelegationServiceCallbackHandler callbackHandler = new DelegationServiceCallbackHandler();
          callbackHandler.setUserName(credentials.get(USERNAME));
         LoginContext loginContext = new LoginContext("DelegationService", subject, new DelegationServiceCallbackHandler(), new DelegationServiceLoginConfiguration());
         //loginContext.login();
         subject = loginContext.getSubject();
         CaXchangePrincipal principal = new CaXchangePrincipal();
         principal.setName(credentials.get(USERNAME));
         Set principals = subject.getPrincipals();
         principals.add((Principal)principal);
         in.setSecuritySubject(subject);
         return;
       }catch(Exception e) {
           logger.error("Error authenticating", e);
           throw e;
       }


    }
    /**
     * Gets the credentials from the message.
     *
     * @param exchange
     * @return
     * @throws Exception
     */
    public  Map<String, String> getCredentials(MessageExchange exchange) throws Exception {
        try {
            NormalizedMessage in = exchange.getMessage("in");
            Map<String, String> credentials = new HashMap();
            if (in != null) {
               XPathUtil util = new XPathUtil();
               util.setIn(in);
               util.initialize();
               String userName = util.getUserName();
               credentials.put(USERNAME,userName);
            }
            if (credentials.get(USERNAME)== null) {
                throw new Exception("Username not found in the credentials.");
            }
            return credentials;
        }
        catch(Exception e) {
            logger.error("Error getting credentials from the message.");
            throw e;
        }
    }


}
