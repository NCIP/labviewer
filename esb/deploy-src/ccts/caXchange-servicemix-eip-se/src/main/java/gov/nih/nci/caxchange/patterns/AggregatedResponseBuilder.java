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



import java.io.StringReader;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.jbi.messaging.NormalizedMessage;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import gov.nih.nci.caXchange.CaxchangeErrors;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.servicemix.jbi.jaxp.SourceTransformer;




/**
 * This class builds the aggregated response after getting responses from all
 * the target grid services
 * 
 * @author hmarwaha
 * 
 */
public class AggregatedResponseBuilder {
    protected static DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
    protected static final Logger LOGGER = LogManager.getLogger(AggregatedResponseBuilder.class);
    protected static String timeOutResponse = "  <targetResponse>\n"
            + "                <targetServiceIdentifier>@targetServiceIdentifier@</targetServiceIdentifier>\n"
            + "                <targetServiceOperation></targetServiceOperation>\n"
            + "                <targetMessageStatus>ERROR</targetMessageStatus>\n"
            + "                   <targetError>  "
            + "                        <errorCode>"
            + CaxchangeErrors.TIMEOUT
            + "</errorCode>  "
            + "                        <errorDescription>A timeout occurred waiting for response.</errorDescription> "
            + "                    </targetError> "
            + "            </targetResponse>";

    protected static Document timeOutDocument;

    String faultResponse = "            <businessResponse>\n"
            + "                <targetServiceIdentifier>targetServiceIdentifier0</targetServiceIdentifier>\n"
            + "                <targetServiceOperation>targetServiceOperation0</targetServiceOperation>\n"
            + "                <messageStatus>RESPONSE</messageStatus>\n"
            + "                <messagePayload>\n"
            + "                    <xmlSchemaDefinition>http://www.oxygenxml.com/</xmlSchemaDefinition>\n"
            + "                </messagePayload>\n"
            + "            </businessResponse>";

    /**
     * Default constructor
     */
    public AggregatedResponseBuilder() {
    }

    /**
     * This methods scans the list of messages form all the target grid services
     * and builds the response document to send it to listener aggregator
     * 
     * @param messages
     * @param timeOut
     * @return document
     * @throws Exception
     */
    static Document buildAggregatedDocument(List messages, boolean timeOut,
            Set exchangesToReceive) throws Exception {
        DocumentBuilder documentBuilder = documentBuilderFactory
                .newDocumentBuilder();
        Document document = documentBuilder.newDocument();
        Element aggregateResponse = document.createElement("aggregateResponse");
        document.appendChild(aggregateResponse);
        SourceTransformer st = new SourceTransformer();
        if (messages != null) {
            Iterator iterator = messages.iterator();
            while (iterator.hasNext()) {
                NormalizedMessage message = (NormalizedMessage) iterator.next();
                Source content = message.getContent();
                Document doc = st.toDOMDocument(content);
                Node importedNode = document.importNode(doc
                        .getDocumentElement(), true);
                aggregateResponse.appendChild(importedNode);
            }
        }
        // Create the timeout response for the services which have not yet
        // responded.
        if (timeOut && (exchangesToReceive != null)) {
            Iterator exchangesToReceiveIterator = exchangesToReceive
                .iterator();
            while (exchangesToReceiveIterator.hasNext()) {
                String exchangeIdentifier = (String) exchangesToReceiveIterator
                        .next();
                LOGGER.debug("******" + exchangeIdentifier);
                String serviceTimeOutResponse = timeOutResponse.replace(
                        "@targetServiceIdentifier@", exchangeIdentifier);
                LOGGER.debug("******" + serviceTimeOutResponse);
                timeOutDocument = documentBuilder.parse(new InputSource(
                        new StringReader(serviceTimeOutResponse)));
                Node importedNode = document.importNode(timeOutDocument
                        .getDocumentElement(), true);
                aggregateResponse.appendChild(importedNode);
            }
        }
        return document;
    }
}
