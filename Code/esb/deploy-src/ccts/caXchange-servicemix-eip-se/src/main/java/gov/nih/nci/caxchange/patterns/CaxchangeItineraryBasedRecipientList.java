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
package gov.nih.nci.caxchange.patterns;

import java.io.IOException;

import javax.jbi.messaging.ExchangeStatus;
import javax.jbi.messaging.InOnly;
import javax.jbi.messaging.MessageExchange;
import javax.jbi.messaging.NormalizedMessage;
import javax.jbi.messaging.RobustInOnly;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;

import gov.nih.nci.caXchange.CaxchangeConstants;
import gov.nih.nci.caXchange.messaging.CaXchangeRequestMessageDocument;

import org.apache.servicemix.eip.patterns.StaticRecipientList;
import org.apache.servicemix.eip.support.ExchangeTarget;
import org.apache.servicemix.jbi.util.MessageUtil;
import org.apache.xmlbeans.XmlException;

    /**This is class handles static Recipient List that 
     * adds the count of targetSites as a property
     *@author hmarwaha
     * @org.apache.xbean.XBean element="caxchange-itinerary-based-recipient-list"
     *                  description="A static Recipient List that adds the count of targetSites as a property"
     */
public class CaxchangeItineraryBasedRecipientList  extends StaticRecipientList {
    
    private ExchangeTarget recipient;
    
    /**
     * Default constructor
     */
    public CaxchangeItineraryBasedRecipientList() {
    }
    
    /**
     * This methods counts the caxchange message recipient and the message correlation id
     * @param exchange
     * @return
     * @throws Exception
     */
    public void process(MessageExchange exchange) throws Exception {
        NormalizedMessage in = exchange.getMessage("in");
         
        String[] targetSites = getTargetSites(in);
            
        int length = 0;
         
        if (targetSites != null) {
            length = targetSites.length;
        }
        in.setProperty(CaxchangeEIPConstants.CAXCHANGE_RECIPIENT_COUNT, length + "");
        in.setProperty(CaxchangeEIPConstants.ORIGINAL_EXCHANGE_CORRELATIONID, exchange.getProperty("org.apache.servicemix.correlationId"));
        super.process(exchange);
    }
    
    /** This methods checks synchronously the message exchange patterns if they are supported and sends the response
     * back to the aggregator
     * @param exchange
     * @return
     * @throws Exception
     * @see org.apache.servicemix.eip.EIPEndpoint#processSync(javax.jbi.messaging.MessageExchange)
     */
    protected void processSync(MessageExchange exchange) throws Exception {
        if (!(exchange instanceof InOnly
            || exchange instanceof RobustInOnly)) {
            fail(exchange, new UnsupportedOperationException("Use an InOnly or RobustInOnly MEP"));
            return;
        }
        String[] targetURLs = getTargetSites(exchange.getMessage("in"));
        NormalizedMessage in;
        for (int i = 0; i < targetURLs.length; i++) {
            in = MessageUtil.copyIn(exchange);
            in.setProperty(CaxchangeConstants.TARGET_ID, targetURLs[i]);
            
            MessageExchange me = getExchangeFactory().createExchange(exchange.getPattern());
            
            recipient.configureTarget(me, getContext());
            MessageUtil.transferToIn(in, me);
            sendSync(me);
        }
        done(exchange);
    }

    /** This methods checks asynchronously the message exchange patterns if they are supported and sends the response
     * back to the aggregator
     * @param exchange
     * @return
     * @throws Exception
     * @see org.apache.servicemix.eip.EIPEndpoint#processAsync(javax.jbi.messaging.MessageExchange)
     */
    protected void processAsync(MessageExchange exchange) throws Exception {
        if (exchange.getStatus() == ExchangeStatus.DONE) {
            return;
        }  else if (exchange.getStatus() == ExchangeStatus.ERROR) {
            return;
        }  else if (!(exchange instanceof InOnly
                || exchange instanceof RobustInOnly)) {
            fail(exchange, new UnsupportedOperationException("Use an InOnly or RobustInOnly MEP"));
        } else if (exchange.getFault() != null) {
            done(exchange);
        } else {
            String[] targetURLs = getTargetSites(exchange.getMessage("in"));
            NormalizedMessage in;
            for (int i = 0; i < targetURLs.length; i++) {
                in = MessageUtil.copyIn(exchange);
                in.setProperty(CaxchangeConstants.TARGET_ID, targetURLs[i]);
                MessageExchange me = getExchangeFactory().createExchange(exchange.getPattern());
                recipient.configureTarget(me, getContext());
                MessageUtil.transferToIn(in, me);
                send(me);
            }
            done(exchange);
        }
    }

    /**
     * This methods get the target sites for the normalized message
     * @param in
     * @return null
     * @throws XmlException
     * @throws IOException
     */
    private String[] getTargetSites(NormalizedMessage in) throws XmlException, IOException {
        CaXchangeRequestMessageDocument requestDocument = null;
        if (in.getContent() instanceof DOMSource) {
            DOMSource ds = (DOMSource)in.getContent();
            logger.debug("ds.node is " + ds.getNode().getNamespaceURI() + " " + ds.getNode().getNodeName());
            requestDocument = CaXchangeRequestMessageDocument.Factory.parse(((DOMSource)in.getContent()).getNode());
        } else if (in.getContent() instanceof StreamSource) {
            StreamSource ss = (StreamSource)in.getContent();
            requestDocument = CaXchangeRequestMessageDocument.Factory.parse(ss.getInputStream());
        }
        if (requestDocument != null) {
            return requestDocument.getCaXchangeRequestMessage().getMetadata().getTargetSiteArray();
        }
        return null;
    }
    
    /**
     * Gets the recipient of the messages
     * @param
     * @return recipient
     * @throws
     */
    public ExchangeTarget getRecipient() {
        return recipient;
    }
    
    /**
     * Sets the recipient of the message
     * @param recipient
     * @return
     * @throws
     */
    public void setRecipient(ExchangeTarget recipient) {
        this.recipient = recipient;
    }
}
