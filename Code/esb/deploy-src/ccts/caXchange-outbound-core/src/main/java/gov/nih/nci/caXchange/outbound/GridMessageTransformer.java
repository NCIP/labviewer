/**
 * 
 */
package gov.nih.nci.caXchange.outbound;

import javax.jbi.messaging.MessageExchange;
import javax.jbi.messaging.NormalizedMessage;

/**
 * This interface provides methods for conversion between normalized message and a grid message
 * 
 * @author steve
 */
public interface GridMessageTransformer {

    public GridMessage transformInToGrid(MessageExchange exchange)
	    throws TransformationException;

    public NormalizedMessage transformGridToOut(GridInvocationResult result,
	    MessageExchange exchange) throws TransformationException;

    public NormalizedMessage transofrmGridToFault(GridInvocationResult result,
	    MessageExchange exchange) throws TransformationException;

}
