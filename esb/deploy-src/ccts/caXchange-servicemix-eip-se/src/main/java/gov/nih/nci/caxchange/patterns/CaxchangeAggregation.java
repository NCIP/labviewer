package gov.nih.nci.caxchange.patterns;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.jbi.messaging.NormalizedMessage;
/**
 * This class contains the setters and getters methods for 
 * aggregating responses form all target Grid Services
 * @author hmarwaha
 *
 */
public class CaxchangeAggregation {
    protected int count=0;
    protected List messages;
    protected String correlationId;
    //This is the set of service identifiers from which exchanges are expected.
    protected Set exchangesToReceive = null;    
    public Set getExchangesToReceive() {
		return exchangesToReceive;
	}

	public void setExchangesToReceive(Set exchangesToReceive) {
		this.exchangesToReceive = exchangesToReceive;
	}

	/**
     * Constructor for the class 
     * @param correlationId
     */
    public CaxchangeAggregation(String correlationId) {
       this.correlationId = correlationId;
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
        if ((messages!=null)&&(count==messages.size())) {
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
}
