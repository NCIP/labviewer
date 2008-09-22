package gov.nih.nci.caxchange.patterns;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import java.util.List;

import javax.jbi.messaging.MessageExchange;
import javax.jbi.messaging.NormalizedMessage;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.servicemix.eip.support.AbstractAggregator;
import org.apache.servicemix.jbi.jaxp.SourceTransformer;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 * This class contains getters and setters to calculate timeout using correlation id and aggregation
 * and builds the aggregated response
 * @org.apache.xbean.XBean element="caxchangeAggregator"
 * @author hmarwaha
 */
public class CaxchangeAggregator extends AbstractAggregator {
    static final String EXCHANGE_CORRELATIONID="org.apache.servicemix.correlationId";
    protected  long timeOut=5*60*1000;

    static Logger logger=LogManager.getLogger(CaxchangeAggregator.class);
    
    /**
     * Default constructor
     */
    public CaxchangeAggregator() {
    }
    
    /**
     * This method gets the correlation id
     * @param exchange
     * @param message
     * @return correlationId
     * @throws
     */
    protected String getCorrelationID(MessageExchange exchange, 
                                      NormalizedMessage message) {
       String correlationId=(String)exchange.getProperty("org.apache.servicemix.correlationId");
       String count = (String)message.getProperty("caxchange.targetservices.count");
       logger.debug("Got the count "+count);
        return correlationId;
    }
    
    /**
     * This methods creates the aggregation 
     * @param correlationID
     * @return aggregation
     * @throws
     */
    protected Object createAggregation(String correlationID) {
        CaxchangeAggregation aggregation = new CaxchangeAggregation(correlationID);
        
        return aggregation;
    }
    
    /**
     * This method gets the timeout time 
     * @param aggregate
     * @return date
     * @throws 
     */
    protected Date getTimeout(Object aggregate) {
        long currentTime = System.currentTimeMillis();
        
        return new Date(currentTime+timeOut);
    }
    /**
     * Initializes the aggregator to anticipate responses from which target services.
     * 
     * @param targetServiceIdentifiers
     * @return
     */
    protected Set initializeExchangesToReceive(String targetServiceIdentifiers){
    	Set exchangesToReceive = null;
    	if (targetServiceIdentifiers == null) {
    		exchangesToReceive =  new HashSet(0);
    		return exchangesToReceive;
    	}
    	StringTokenizer st = new StringTokenizer(targetServiceIdentifiers,",");
    	exchangesToReceive = new HashSet(st.countTokens()); 
    	while(st.hasMoreTokens()) {
    		exchangesToReceive.add(st.nextToken());
    	}
    	return exchangesToReceive;
    }
    /**
     * Gets the target service identifier from the message. This is defined by
     * the xpath expression :  /targetResponse/targetServiceIdentifier
     * @param message
     * @return
     */
    protected String getTargetServiceIdentifier(NormalizedMessage message) {
       try {	
    	Source content = message.getContent();
    	SourceTransformer sourceTransformer = new SourceTransformer();
    	Node node = sourceTransformer.toDOMNode(content);
    	XPath xpath = XPathFactory.newInstance().newXPath();
        XPathExpression expression = xpath.compile("/targetResponse/targetServiceIdentifier");
        String targetServiceIdentifier = (String)expression.evaluate(node,XPathConstants.STRING);
    	return targetServiceIdentifier;
       }
       catch(TransformerException te) {
    	   te.printStackTrace();
    	   logger.error("Error getting targetServiceIdentifier from target response.",te);
    	   return null;
       }
       catch(XPathExpressionException xe) {
    	   xe.printStackTrace();
    	   logger.error("Error getting targetServiceIdentifier from target response.",xe);
    	   return null;
       }
       catch(ParserConfigurationException pce){
    	   logger.error("Error getting targetServiceIdentifier from target response.", pce);
    	   return null;
       }
       catch(IOException ie){
    	   logger.error("Error getting targetServiceIdentifier from target response.", ie);
    	   return null;
       }
       catch(SAXException se){
    	   logger.error("Error getting targetServiceIdentifier from target response.", se);
    	   return null;
       }
    }
    
    /**
     * This method add the message to the aggregator and return if the aggregation is complete or not
     * 
     * @param aggregate
     * @param message
     * @param exchange
     * @return true or false
     * @throws
     */
    protected boolean addMessage(Object aggregate, NormalizedMessage message, 
                                 MessageExchange exchange) {
        CaxchangeAggregation caxchangeAggregate = (CaxchangeAggregation)aggregate;
        logger.debug("Adding message");
        caxchangeAggregate.addMessage(message);
        String count= (String)message.getProperty(CaxchangeEIPConstants.CAXCHANGE_RECIPIENT_COUNT);
        String correlationId = (String)exchange.getProperty("org.apache.servicemix.correlationId");
        if (correlationId!=null) {
           caxchangeAggregate.setCorrelationId(correlationId);
        }
        if (count!=null) {
            caxchangeAggregate.setCount(new Integer(count).intValue());
        }
        Set exchangesToReceive = caxchangeAggregate.getExchangesToReceive();
        if (exchangesToReceive == null) {
        	String targetServiceIdentifiers = (String)message.getProperty(CaxchangeEIPConstants.CAXCHANGE_RECIPIENTS);
        	exchangesToReceive = initializeExchangesToReceive(targetServiceIdentifiers);
        	caxchangeAggregate.setExchangesToReceive(exchangesToReceive);
        }
        String targetServiceIdentifier = getTargetServiceIdentifier(message);
        //Remove the targetServiceIdentifier from the set of service identifiers from which exchanges are expected.
        exchangesToReceive.remove(targetServiceIdentifier);
        return caxchangeAggregate.isAggregationComplete();
    }
    
    /**
     * This methods checks if some services timeout during the building of 
     * aggregated response and build aggregate response
     * @param aggregate
     * @param message
     * @param exchange
     * @param timeout
     * @return
     * @throws Exception
     */
    protected void buildAggregate(Object aggregate, NormalizedMessage message, 
                                  MessageExchange exchange, boolean timeout) throws Exception{
        logger.debug("Building aggregate");
        CaxchangeAggregation caxchangeAggregate = (CaxchangeAggregation)aggregate;
        List messages= caxchangeAggregate.getMessages();
        logger.error("*********"+messages.size()+"  "+timeout+"  "+caxchangeAggregate.getExchangesToReceive() );
        Document document = AggregatedResponseBuilder.buildAggregatedDocument(messages, timeout, caxchangeAggregate.getExchangesToReceive());        
        message.setContent(new DOMSource(document));
        message.setProperty(CaxchangeEIPConstants.ORIGINAL_EXCHANGE_CORRELATIONID, caxchangeAggregate.getCorrelationId());
       return;
    }

    /**
     * Set the time out duration
     * @param timeOut
     * @return
     * @throws
     */ 
    public void setTimeOut(long timeOut) {
        this.timeOut = timeOut;
    }
    
    /**
     * Gets the time out duration
     * @param 
     * @return timeOut
     * @throws
     */ 
    public long getTimeOut() {
        return timeOut;
    }
}
