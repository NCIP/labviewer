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
package org.apache.servicemix.eip.patterns;

import java.util.Date;
import java.util.concurrent.TimeoutException;

import javax.jbi.messaging.ExchangeStatus;
import javax.jbi.messaging.InOnly;
import javax.jbi.messaging.InOut;
import javax.jbi.messaging.MessageExchange;
import javax.jbi.messaging.MessagingException;
import javax.jbi.messaging.NormalizedMessage;
import javax.jbi.messaging.RobustInOnly;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.servicemix.common.util.MessageUtil;
import org.apache.servicemix.eip.EIPEndpoint;
import org.apache.servicemix.eip.support.ExchangeTarget;
import org.apache.servicemix.expression.Expression;
import org.apache.servicemix.expression.PropertyExpression;
import org.apache.servicemix.timers.Timer;
import org.apache.servicemix.timers.TimerListener;

/**
 *
 * @author gnodet
 * @org.apache.xbean.XBean element="async-bridge"
 */
public class AsyncBridge extends EIPEndpoint {

    public static final String CORRID = "org.apache.servicemix.eip.asyncbridge.corrid";

    private static final Log LOG = LogFactory.getLog(AsyncBridge.class);

    private Expression requestCorrId = new Expression() {
        public Object evaluate(MessageExchange exchange, NormalizedMessage message) throws MessagingException {
            return exchange.getExchangeId();
        }
    };
    private String responseCorrIdProperty = CORRID;
    private Expression responseCorrId = new Expression() {
        public Object evaluate(MessageExchange exchange, NormalizedMessage message) throws MessagingException {
            return exchange.getExchangeId();
        }
    };
    private long timeout;
    private ExchangeTarget target;
    private boolean useRobustInOnly;

    /**
     * @return the timeout
     */
    public long getTimeout() {
        return timeout;
    }

    /**
     * @param timeout the timeout to set
     */
    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    /**
     * @return the target
     */
    public ExchangeTarget getTarget() {
        return target;
    }

    /**
     * @param target the target to set
     */
    public void setTarget(ExchangeTarget target) {
        this.target = target;
    }

    /**
     * @return the requestCorrId
     */
    public Expression getRequestCorrId() {
        return requestCorrId;
    }

    /**
     * @param requestCorrId the requestCorrId to set
     */
    public void setRequestCorrId(Expression requestCorrId) {
        this.requestCorrId = requestCorrId;
    }

    /**
     * @return the responseCorrIdProperty
     */
    public String getResponseCorrIdProperty() {
        return responseCorrIdProperty;
    }

    /**
     * @param responseCorrIdProperty the responseCorrIdProperty to set
     */
    public void setResponseCorrIdProperty(String responseCorrIdProperty) {
        this.responseCorrIdProperty = responseCorrIdProperty;
    }

    /**
     * @return the responseCorrId
     */
    public Expression getResponseCorrId() {
        return responseCorrId;
    }

    /**
     * @param responseCorrId the responseCorrId to set
     */
    public void setResponseCorrId(Expression responseCorrId) {
        this.responseCorrId = responseCorrId;
    }

    /**
     * @return the useRobustInOnly
     */
    public boolean isUseRobustInOnly() {
        return useRobustInOnly;
    }

    /**
     * @param useRobustInOnly the useRobustInOnly to set
     */
    public void setUseRobustInOnly(boolean useRobustInOnly) {
        this.useRobustInOnly = useRobustInOnly;
    }

    /*
     * (non-Javadoc)
     * @see org.apache.servicemix.eip.EIPEndpoint#start()
     */
    public void start() throws Exception {
        super.start();
        if (responseCorrId == null) {
            responseCorrId = new PropertyExpression(responseCorrIdProperty);
        }
    }

    /* (non-Javadoc)
     * @see org.apache.servicemix.eip.EIPEndpoint#processSync(javax.jbi.messaging.MessageExchange)
     */
    protected void processSync(MessageExchange exchange) throws Exception {
        throw new IllegalStateException();
    }

    /* (non-Javadoc)
     * @see org.apache.servicemix.eip.EIPEndpoint#processAsync(javax.jbi.messaging.MessageExchange)
     */
    protected void processAsync(MessageExchange exchange) throws Exception {
        throw new IllegalStateException();
    }

    /* (non-Javadoc)
     * @see org.apache.servicemix.common.ExchangeProcessor#process(javax.jbi.messaging.MessageExchange)
     */
    public void process(MessageExchange exchange) throws Exception {
        // Three exchanges are involved: the first InOut will be called t0,
        // the InOnly send will be called t1 and the InOnly received will be called t2

        if (exchange.getRole() == MessageExchange.Role.PROVIDER) {
            // Step1: receive t0 as the first message
            if (exchange instanceof InOut && exchange.getStatus() == ExchangeStatus.ACTIVE) {
                MessageExchange t0 = exchange;
                MessageExchange t1;
                final String correlationId = (String) requestCorrId.evaluate(t0, t0.getMessage("in"));
                if (correlationId == null || correlationId.length() == 0) {
                    throw new IllegalArgumentException("Could not retrieve correlation id for incoming exchange");
                }
                LOG.info("Storing correlation id:"+correlationId);
                store.store(correlationId + ".t0", t0);
                t1 = useRobustInOnly ? getExchangeFactory().createRobustInOnlyExchange()
                                     : getExchangeFactory().createInOnlyExchange();
                target.configureTarget(t1, getContext());
                MessageUtil.transferInToIn(t0, t1);
                t1.setProperty(responseCorrIdProperty, correlationId);
                t1.getMessage("in").setProperty(responseCorrIdProperty, correlationId);
                send(t1);
            // Receive the done / error from t0
            } else if (exchange instanceof InOut && exchange.getStatus() != ExchangeStatus.ACTIVE) {
                MessageExchange t0 = exchange;
                MessageExchange t1;
                MessageExchange t2;
                String correlationIdFromProperty = (String)t0.getMessage("in").getProperty(responseCorrIdProperty);
                //String correlationIdFromProperty = (String)t0.getProperty(responseCorrIdProperty);
                final String correlationId = (correlationIdFromProperty==null)?(String) requestCorrId.evaluate(t0, t0.getMessage("in")):correlationIdFromProperty;
                LOG.info("Retrieving correlation id:"+correlationId);
                t1 = (MessageExchange) store.load(correlationId + ".t1");
                t2 = (MessageExchange) store.load(correlationId + ".t2");
                if (t1 != null) {
                    done(t1);
                }
                if (t2 != null) {
                    done(t2);
                }
            // Receive the response from t2
            } else if ((exchange instanceof InOnly || exchange instanceof RobustInOnly) && exchange.getStatus() == ExchangeStatus.ACTIVE) {
                MessageExchange t0;
                MessageExchange t2 = exchange;
                String correlationIdFromProperty = (String)t2.getMessage("in").getProperty(responseCorrIdProperty);
                LOG.info("CORRID from property:"+correlationIdFromProperty+":  "+(String)t2.getMessage("in").getProperty(responseCorrIdProperty));
                final String correlationId = (correlationIdFromProperty==null)?(String) responseCorrId.evaluate(t2, t2.getMessage("in")):correlationIdFromProperty;
                 LOG.info("Retrieving correlation id:"+correlationId);
                if (correlationId == null || correlationId.length() == 0) {
                    throw new IllegalArgumentException("Could not retrieve correlation id for incoming exchange");
                }
                t0 = (MessageExchange) store.load(correlationId + ".t0");
                store.store(correlationId + ".t2", t2);
                // The request is found and has not timed out
                if (t0 != null) {
                    MessageUtil.transferInToOut(t2, t0);
                    send(t0);
                }
            } else {
                throw new IllegalStateException();
            }
        // Handle an exchange as a CONSUMER
        } else {
            // Step 2: receive t1 response
            // If this is an error or a fault, transfer it from t1 to t0 and send,
            // else, start a timeout to wait for t2
            MessageExchange t1 = exchange;
            // an error
            final String correlationId = (String) t1.getProperty(responseCorrIdProperty);
            if (t1.getStatus() == ExchangeStatus.ERROR) {
                MessageExchange t0 = (MessageExchange) store.load(correlationId + ".t0");
                // t1 response may come after t0, so in case this happens, we need to discard t1
                if (t0 != null) {
                    fail(t0, t1.getError());
                }
            // a fault ?
            } else if (exchange.getStatus() == ExchangeStatus.ACTIVE) {
                MessageExchange t0 = (MessageExchange) store.load(correlationId + ".t0");
                // t1 response may come after t0, so in case this happens, we need to discard t1
                if (t0 != null) {
                    store.store(correlationId + ".t1", t1);
                    MessageUtil.transferFaultToFault(t1, t0);
                    send(t0);
                }
            // request sent successfully, start the timeout
            } else {
                Date exchangeTimeout = getTimeout(t1);
                if (exchangeTimeout != null) {
                    getTimerManager().schedule(new TimerListener() {
                        public void timerExpired(Timer timer) {
                            AsyncBridge.this.onTimeout(correlationId);
                        }
                    }, exchangeTimeout);
                }
            }
        }
    }

    protected void onTimeout(String correlationId) {
        try {
            MessageExchange t0 = (MessageExchange) store.load(correlationId + ".t0");
            if (t0 != null) {
                fail(t0, new TimeoutException());
            }
        } catch (Exception e) {
            LOG.debug("Exception caught when handling timeout", e);
        }
    }

    protected Date getTimeout(MessageExchange exchange) {
        if (timeout > 0) {
            return new Date(System.currentTimeMillis() + timeout);
        }
        return null;
    }

}
