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
import gov.nih.nci.caXchange.CaxchangeErrors;
import gov.nih.nci.caxchange.jdbc.CaxchangeMessage;
import gov.nih.nci.caxchange.persistence.CaxchangeMessageDAO;
import gov.nih.nci.caxchange.persistence.DAOFactory;
import org.apache.servicemix.MessageExchangeListener;
import javax.annotation.Resource;
import javax.jbi.messaging.DeliveryChannel;
import javax.jbi.messaging.ExchangeStatus;
import javax.jbi.messaging.Fault;
import javax.jbi.messaging.MessageExchange;
import javax.jbi.messaging.MessagingException;
import javax.jbi.messaging.NormalizedMessage;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.servicemix.jbi.util.MessageUtil;
import gov.nih.nci.caxchange.servicemix.bean.util.*;
/**
 * This class stores original message in the database to use it later 
 * for generating response and or sending roll back
 *  
 * @author hmarwaha
 *
 */
public class StoreOriginalMessageServiceBean implements MessageExchangeListener {

    @Resource
    private DeliveryChannel channel;


    public static Logger logger= LogManager.getLogger(InvokeDelegationServiceBean.class);

	/**
	 * When the POJO Implements the MessageExchangeListener interface of servicemix-bean component
	 * all the exchange will be dispatched to the onMessageExchange() method
	 * @param exchange
	 * @return
	 * @throws MessagingException
	 */	
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
        MessageUtil.transfer(in, out);
        try {
            logger.debug("Storing original message");
            storeOriginalMessage(exchange);
        }catch(Exception e) {
        	e.printStackTrace();
            logger.error("Error occurred storing original message.", e);
            Fault fault = exchange.createFault();
            MessageUtil.transfer(in, fault);
            fault.setProperty(CaxchangeConstants.ERROR_CODE, CaxchangeErrors.ERROR_STORING_MESSAGE);
            fault.setProperty(CaxchangeConstants.ERROR_MESSAGE, "Error occurred storing original message.");
            exchange.setFault(fault);
            channel.send(exchange);
            return;
        }
        exchange.setMessage(out, "out");
        channel.send(exchange);
    }



   /**
     * This method store the original message in the database to add with the exchange
     * correlationID as the message id.
     * @param exchange
     * @return
     * @throws Exception
     */
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
