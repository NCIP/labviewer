/**
 * 
 */
package gov.nih.nci.caXchange.outbound.impl;

import javax.jbi.messaging.DeliveryChannel;
import javax.jbi.messaging.MessageExchange;

import org.apache.log4j.Category;
import org.apache.servicemix.jbi.jaxp.SourceTransformer;
import org.w3c.dom.Document;

import gov.nih.nci.caXchange.outbound.GridInvocationException;
import gov.nih.nci.caXchange.outbound.GridInvocationResult;
import gov.nih.nci.caXchange.outbound.GridInvocationStrategy;
import gov.nih.nci.caXchange.outbound.GridMessage;

/**
 * @author steve
 *
 */
public class GridInvocationStrategyImpl implements GridInvocationStrategy {
    
    private static final Category log =
	Category.getInstance(GridInvocationStrategyImpl.class);

    /* (non-Javadoc)
     * @see gov.nih.nci.caXchange.outbound.GridInvocationStrategy#invokeGridService(gov.nih.nci.caXchange.outbound.GridMessage)
     */
    public GridInvocationResult invokeGridService(DeliveryChannel channel, MessageExchange exchange, GridMessage message)
	    throws GridInvocationException {
	
	try {
	    Document document =
		new SourceTransformer().toDOMDocument(message.getPayload());
	    
	    return new GridInvocationResultImpl(document);
	} catch(Exception e) {
	    log.error("Invocation failed.", e);
	    throw new GridInvocationException("Invocation failed.", e);
	}
    }

}
