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
package org.apache.servicemix.eip.support;

import java.util.Date;

import javax.jbi.messaging.MessageExchange;
import javax.jbi.messaging.MessageExchange.Role;
import javax.jbi.messaging.MessagingException;
import javax.jbi.messaging.NormalizedMessage;

import junit.framework.TestCase;

import org.apache.servicemix.id.IdGenerator;
import org.apache.servicemix.jbi.jaxp.StringSource;
import org.apache.servicemix.locks.impl.SimpleLockManager;
import org.apache.servicemix.soap.interceptors.jbi.JbiConstants;
import org.apache.servicemix.store.Store;
import org.apache.servicemix.store.StoreFactory;
import org.apache.servicemix.store.memory.MemoryStore;
import org.apache.servicemix.store.memory.MemoryStoreFactory;
import org.apache.servicemix.tck.mock.MockExchangeFactory;
import org.apache.servicemix.tck.mock.MockMessageExchange;
import org.apache.servicemix.tck.mock.MockNormalizedMessage;
import org.apache.servicemix.timers.impl.TimerManagerImpl;

public class BaseAbstractAggregatorTest extends TestCase {
    
    private static final String CORRELATION_ID = "my-unique-correlation-id";
    
    private AbstractAggregator aggregator;
    private MockExchangeFactory factory;
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        aggregator = new MockAggregator();
        aggregator.setTimerManager(new TimerManagerImpl());
        aggregator.getTimerManager().start();
        aggregator.setLockManager(new SimpleLockManager());
        aggregator.setStore(new MemoryStore(new IdGenerator()));
        aggregator.start();
        factory = new MockExchangeFactory();
    }
    
    public void testRescheduleTimeout() throws Exception {
        //configure aggregator to reschedule timeouts
        aggregator.setRescheduleTimeouts(true);
        
        // send a few exchanges
        aggregator.process(createExchange());
        aggregator.process(createExchange());
        aggregator.process(createExchange());
        
        // assert that the aggregator has actually rescheduled twice 
        assertEquals(3, ((MockAggregator) aggregator).scheduleCount);
    }
    
    public void testNoRescheduleTimeout() throws Exception {
        //configure aggregator to not reschedule timeouts
        aggregator.setRescheduleTimeouts(false);
        
        // send a few exchanges
        aggregator.process(createExchange());
        aggregator.process(createExchange());
        aggregator.process(createExchange());
        
        // assert that the aggregator hasn't rescheduled 
        assertEquals(1, ((MockAggregator) aggregator).scheduleCount);
    }
    

    private MessageExchange createExchange() throws MessagingException {
        MockMessageExchange me = (MockMessageExchange)factory.createExchange(JbiConstants.IN_ONLY);
        me.setRole(Role.PROVIDER);
        NormalizedMessage message = new MockNormalizedMessage();
        message.setContent(new StringSource("<test/>"));
        me.setMessage(message, "in");
        return me;
    }
    
    private class MockAggregator extends AbstractAggregator {

        private int scheduleCount;
        private Store mockClosedAggregates;
        private StoreFactory mockClosedAggregatesStoreFactory;

        @Override
        protected boolean addMessage(Object aggregate, NormalizedMessage message, MessageExchange exchange)
            throws Exception {
            // return false for test --> aggregation is not finished
            return false;
        }

        @Override
        protected void buildAggregate(Object aggregate, NormalizedMessage message, MessageExchange exchange,
                                      boolean timeout) throws Exception {
            // TODO Auto-generated method stub
            
        }

        @Override
        protected Object createAggregation(String correlationID) throws Exception {
            return new Object();
        }

        @Override
        protected String getCorrelationID(MessageExchange exchange, NormalizedMessage message)
            throws Exception {
            return CORRELATION_ID;
        }

        @Override
        protected Date getTimeout(Object aggregate) {
            scheduleCount++;
            
            //always time-out by 500ms
            return new Date(System.currentTimeMillis() + 500);
        }
        
        @Override
        protected void send(MessageExchange me) throws MessagingException {
            // graciously do nothing
        }

        @Override
        public void start() throws Exception {
            if (mockClosedAggregatesStoreFactory == null) {
                mockClosedAggregatesStoreFactory = new MemoryStoreFactory();
            }
            mockClosedAggregates = mockClosedAggregatesStoreFactory.open("mock-closed-aggregates");
        }
        
        protected boolean isAggregationClosed(String correlationId) throws Exception {
            // TODO: implement this using a persistent / cached behavior
            Object data = mockClosedAggregates.load(correlationId);
            if (data != null) {
                mockClosedAggregates.store(correlationId, data);
            }
            return data != null;
        }
    }

}
