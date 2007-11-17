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
public interface GridInvocationStrategy {

    /**
     * Performs operations on the grid service.
     * 
     * @param message
     * @return
     * @throws GridInvocationException
     */
    public GridInvocationResult invokeGridService(DeliveryChannel channel,
	    MessageExchange exchange, GridMessage message)
	    throws GridInvocationException;

}
