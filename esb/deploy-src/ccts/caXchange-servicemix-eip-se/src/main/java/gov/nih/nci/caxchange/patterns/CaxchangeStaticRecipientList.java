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

import java.util.Iterator;
import java.util.List;

import javax.jbi.messaging.MessageExchange;

import javax.jbi.messaging.NormalizedMessage;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.servicemix.eip.patterns.StaticRecipientList;

/**
 * This class contains static Recipient List that adds the count of recipients as a property
 * @author hmarwaha
 * @org.apache.xbean.XBean element="caxchange-static-recipient-list"
 *                  description="A static Recipient List that adds the count of recipients as a property"
 */
public class CaxchangeStaticRecipientList extends StaticRecipientList {

    static final Logger LOGGER = LogManager.getLogger(CaxchangeStaticRecipientList.class);

    private List targetServiceIdentifiers;

    /**
     * Default constructor
     */
    public CaxchangeStaticRecipientList() {
    }

    /**
     *
     * @return list of target service identifiers this recipient list is routing to.
     */
    public List getTargetServiceIdentifiers() {
        return targetServiceIdentifiers;
    }
    /**
     *
     * @param targetServiceIdentifiers
     */
    public void setTargetServiceIdentifiers(List targetServiceIdentifiers) {
        this.targetServiceIdentifiers = targetServiceIdentifiers;
    }

    /**
     * This method  sets the message properties for the EIP aggregator component.
     * The properties set are recipient count, correlation id, and target service identifiers.
     *
     * @param exchange
     * @return
     * @throws Exception
     */
    public void process(MessageExchange exchange) throws Exception {
        NormalizedMessage in = exchange.getMessage("in");
        int length = getRecipients().length;
        in.setProperty(CaxchangeEIPConstants.CAXCHANGE_RECIPIENT_COUNT, length + "");
        in.setProperty(CaxchangeEIPConstants.ORIGINAL_EXCHANGE_CORRELATIONID, exchange.getProperty("org.apache.servicemix.correlationId"));
        in.setProperty(CaxchangeEIPConstants.CAXCHANGE_RECIPIENTS, getTargetServiceIdentifiersAsString());
        super.process(exchange);
    }
    /**
     * Creates a comma delimited string of the target service identifiers. This can
     * be set as a property of the message.
     *
     * @return a comma delimited string of the target service identifiers.
     */
    public String getTargetServiceIdentifiersAsString() {
        if (targetServiceIdentifiers == null) {
            return null;
        }
        StringBuffer identifiers = new StringBuffer(30);
        Iterator identifierIterator = targetServiceIdentifiers.iterator();
        while (identifierIterator.hasNext()) {
            Object o = identifierIterator.next();
            String serviceIdentifier = (String)o;
            identifiers.append(serviceIdentifier + ",");
        }
        if (identifiers.length() > 0) {
            identifiers.deleteCharAt(identifiers.length() - 1);
        }
        return identifiers.toString();

    }
}
