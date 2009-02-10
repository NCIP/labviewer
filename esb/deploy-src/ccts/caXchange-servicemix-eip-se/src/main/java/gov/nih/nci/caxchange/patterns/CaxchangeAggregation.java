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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.jbi.messaging.NormalizedMessage;
/**
 * This class contains the setters and getters methods for 
 * aggregating responses form all target Grid Services
 * @author hmarwaha
 *
 */
public class CaxchangeAggregation {
    protected int count = 0 + 0;
    protected List messages;
    protected String correlationId;
    //This is the set of service identifiers from which exchanges are expected.
    protected Set exchangesToReceive; 
    protected Map<String,Object> properties = new HashMap<String,Object>();
    
    /**
     * Constructor for the class 
     * @param correlationId
     */
    public CaxchangeAggregation(String correlationId) {
        this.correlationId = correlationId;
    }
    
    public Set getExchangesToReceive() {
        return exchangesToReceive;
    }

    public void setExchangesToReceive(Set exchangesToReceive) {
        this.exchangesToReceive = exchangesToReceive;
    }
   
    /**
     * Sets the correlation Id
     * @param correlationId
     * @return
     * @throws
     */
    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }
    
    /**
     * Gets the correlation Id
     * @param
     * @return correlationId
     * @throws
     */
    public String getCorrelationId() {
        return correlationId;
    }
    
    /**
     * This method adds message in the ArrayList
     * @param message
     * @return
     * @throws
     */
    public void addMessage(NormalizedMessage message) {
        if (messages == null) {
            messages = new ArrayList();
        }
        messages.add(message);
    }
    
    /**
     * Gets the message from the List
     * @param
     * @return messages
     * @throws
     */
    public List getMessages() {
        return messages;
    }
    
    /**
     * This method checks if all the messages are received to the 
     * aggregrator to send to listener aggregrator
     * @param
     * @return true or false
     * @throws
     */
    public boolean isAggregationComplete() {
        if ((messages != null) && (count == messages.size())) {
            return true;
        }
        return false;
    }
    
    /**
     * This method set the count
     * @param count
     * @return
     * @throws
     */
    public void setCount(int count) {
        this.count = count;
    }
    
    /**
     * This method gets the count
     * @param
     * @return count
     * @throws
     */
    public int getCount() {
        return count;
    }
    
    public void addProperty(String name, Object value) {
    	properties.put(name, value);
    }

	public Map<String,Object> getProperties() {
		return properties;
	}

	public void setProperties(Map<String,Object> properties) {
		this.properties = properties;
	}
    
    
}
