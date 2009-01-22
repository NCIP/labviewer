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
package org.apache.servicemix.eip;

import javax.resource.spi.ConnectionManager;
import javax.resource.spi.ManagedConnectionFactory;
import javax.sql.DataSource;
import javax.sql.XADataSource;

import org.apache.activemq.broker.BrokerService;
import org.apache.derby.jdbc.EmbeddedXADataSource;
import org.apache.servicemix.client.DefaultServiceMixClient;
import org.apache.servicemix.jbi.container.JBIContainer;
import org.apache.servicemix.jbi.nmr.flow.Flow;
import org.apache.servicemix.jbi.nmr.flow.jca.JCAFlow;
import org.apache.servicemix.jbi.nmr.flow.seda.SedaFlow;
import org.apache.servicemix.store.Store;
import org.apache.servicemix.store.jdbc.JdbcStoreFactory;
import org.apache.servicemix.tck.ExchangeCompletedListener;
import org.jencks.GeronimoPlatformTransactionManager;
import org.jencks.factory.ConnectionManagerFactoryBean;
import org.tranql.connector.AllExceptionsAreFatalSorter;
import org.tranql.connector.jdbc.AbstractXADataSourceMCF;

public abstract class AbstractEIPTransactionalTest extends AbstractEIPTest {

    protected BrokerService broker;
    protected GeronimoPlatformTransactionManager tm;
    protected DataSource dataSource;
    protected Store store;
    
    protected void setUp() throws Exception {
        // Create an AMQ broker
        broker = new BrokerService();
        broker.setUseJmx(false);
        broker.setPersistent(false);
        String jmsURL = broker.addConnector("tcp://localhost:0").getUri().toString();
        broker.start();
        
        tm = new GeronimoPlatformTransactionManager();
        
        // Create an embedded database for testing tx results when commit / rollback
        ConnectionManagerFactoryBean factory = new ConnectionManagerFactoryBean();
        factory.setTransactionManager(tm);
        factory.setTransaction("xa");
        factory.afterPropertiesSet();
        ConnectionManager cm = (ConnectionManager) factory.getObject();
        ManagedConnectionFactory mcf = new DerbyDataSourceMCF("target/testdb");
        dataSource = (DataSource) mcf.createConnectionFactory(cm);

        JdbcStoreFactory storeFactory = new JdbcStoreFactory();
        storeFactory.setDataSource(dataSource);
        storeFactory.setTransactional(true);
        store = storeFactory.open("store");
        
        jbi = new JBIContainer();
        jbi.setFlows(new Flow[] {new SedaFlow(), new JCAFlow(jmsURL) });
        jbi.setEmbedded(true);
        jbi.setUseMBeanServer(false);
        jbi.setCreateMBeanServer(false);
        jbi.setTransactionManager(tm);
        jbi.setAutoEnlistInTransaction(true);
        listener = new ExchangeCompletedListener(2000);
        jbi.addListener(listener);
        jbi.init();
        jbi.start();

        client = new DefaultServiceMixClient(jbi);
    }
    
    protected void tearDown() throws Exception {
        try {
            listener.assertExchangeCompleted();
        } finally {
            jbi.shutDown();
            broker.stop();
        }
    }

    protected void configurePattern(EIPEndpoint endpoint) {
        endpoint.setStore(store);
    }
    
    public static class DerbyDataSourceMCF extends AbstractXADataSourceMCF {
        private static final long serialVersionUID = 7971682207810098396L;
        protected DerbyDataSourceMCF(String dbName) {
            super(createXADS(dbName), new AllExceptionsAreFatalSorter());
        }
        public String getPassword() {
            return null;
        }
        public String getUserName() {
            return null;
        }
        protected static XADataSource createXADS(String dbName) {
            EmbeddedXADataSource xads = new EmbeddedXADataSource();
            xads.setDatabaseName(dbName);
            xads.setCreateDatabase("create");
            return xads;
        }
    }
    
}
