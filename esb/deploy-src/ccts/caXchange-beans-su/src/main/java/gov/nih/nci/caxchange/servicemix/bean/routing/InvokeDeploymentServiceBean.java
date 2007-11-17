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
        try {
            invokeDelegationService(exchange);
        }catch (Exception e) {
            logger.error("Error calling delegation service.",e);
            sendErrorResponse(exchange,"ERROR_DELEGATION_SERVICE","Error calling delegation service.");
            exchange.setStatus(ExchangeStatus.DONE);
            channel.send(exchange);
            return;
        }
        try {
           logger.debug("Storing original message");
            storeOriginalMessage(exchange);
        }catch(Exception e) {
            logger.error("Error occurred storing original message.", e);
            sendErrorResponse(exchange,"ERROR_STORING_MESSAGE","Error occurred storing original message.");
            exchange.setStatus(ExchangeStatus.DONE);
            channel.send(exchange);
            return;
        }
        logger.debug("Forward exchange to the next service.");
        try {
           forwardRequest(exchange);
        }catch(Exception e) {
            logger.error("Error occurred forwarding request for routing..", e);
            sendErrorResponse(exchange,"ERROR_FORWARDING_MESSAGE","Error occurred forwarding request for routing..");            
        }
        exchange.setStatus(ExchangeStatus.DONE);
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


    public void sendErrorResponse(MessageExchange exchange, String errCode, String errorMessage) throws MessagingException {
      try {
      System.out.println("Sending an error response...");
        RobustInOnly robustInOnly = channel.createExchangeFactory().createRobustInOnlyExchange();
        NormalizedMessage newin = robustInOnly.createMessage();
        NormalizedMessage in = exchange.getMessage("in");
        newin.setContent(in.getContent());
        robustInOnly.setMessage(newin, "in");
        robustInOnly.setService(CaxchangeConstants.GENERATE_RESPONSE_SERVICE);
        newin.setProperty(CaxchangeConstants.ERROR_CODE, errCode );
        newin.setProperty(CaxchangeConstants.ERROR_MESSAGE, errorMessage );
        MessageUtil.transfer(in,newin);
        channel.sendSync(robustInOnly,1000);        
      }catch(Exception e) {
          logger.error("Error occurred sending error response. Original message --"+errorMessage, e);
          throw new MessagingException("Error occurred sending error response. Original message --"+errorMessage, e);
      }
    }

    public void forwardRequest(MessageExchange exchange) throws MessagingException {
      try {
        logger.debug("Forwarding request...");
        RobustInOnly robustInOnly = channel.createExchangeFactory().createRobustInOnlyExchange();
        NormalizedMessage newin = robustInOnly.createMessage();
        NormalizedMessage in = exchange.getMessage("in");
        newin.setContent(in.getContent());
        robustInOnly.setMessage(newin, "in");
        robustInOnly.setService(CaxchangeConstants.MESSAGE_TYPE_ROUTER);
        MessageUtil.transfer(in,newin);
        channel.send(robustInOnly);
      }catch(Exception e) {
          logger.error("Error occurred sending error response. ", e);
          throw new MessagingException("Error occurred sending error response. ", e);
      }
    }


    public void storeOriginalMessage(MessageExchange exchange) throws Exception {
        NormalizedMessage in = exchange.getMessage("in");
        Source source = in.getContent();
        StringWriter sw = new StringWriter();
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        StreamResult stringResult= new StreamResult(sw);
        transformer.transform(source, stringResult);
        XPathUtil util = new XPathUtil();
        util.setIn(in);
        util.initialize();
        String correlationId = (String)exchange.getProperty(CaxchangeConstants.EXCHANGE_CORRELATIONID);
        logger.debug("Correlation id is:"+correlationId);
        String message = sw.getBuffer().toString();
        CaxchangeMessage caxchangeMessage = new CaxchangeMessage();
        caxchangeMessage.setMessageId(correlationId);
        caxchangeMessage.setMessage(message);
        CaxchangeMessageDAO caxchangeMessageDAO = DAOFactory.getCaxchangeMessageDAO();
        caxchangeMessageDAO.storeMessage(caxchangeMessage);
    }

}
