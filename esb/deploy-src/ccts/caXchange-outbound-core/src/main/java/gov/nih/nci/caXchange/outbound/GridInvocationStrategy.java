/**
 * 
 */
package gov.nih.nci.caXchange.outbound;

import javax.jbi.messaging.DeliveryChannel;
import javax.jbi.messaging.MessageExchange;

/**
 * Provides grid service invocation functionality for the service unit.
 * 
 * @author steve
 */
public abstract class GridInvocationStrategy {

	protected String serviceUrl;
	protected boolean isItineraryBased;
    /**
     * Performs operations on the grid service.
     * 
     * @param message
     * @return
     * @throws GridInvocationException
     */
    public abstract GridInvocationResult invokeGridService(DeliveryChannel channel,
	    MessageExchange exchange, GridMessage message)
	    throws GridInvocationException;
    
	public String getServiceUrl() {
		return serviceUrl;
	}

	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}

	public boolean isItineraryBased() {
		return isItineraryBased;
	}

	public void setItineraryBased(boolean isItineraryBased) {
		this.isItineraryBased = isItineraryBased;
	}

}
