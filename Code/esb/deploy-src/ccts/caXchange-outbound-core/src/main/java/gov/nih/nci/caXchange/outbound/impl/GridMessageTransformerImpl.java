package gov.nih.nci.caXchange.outbound.impl;

import javax.jbi.messaging.MessageExchange;
import javax.jbi.messaging.MessagingException;
import javax.jbi.messaging.NormalizedMessage;
import javax.xml.transform.dom.DOMSource;

import org.apache.log4j.Category;
import org.apache.servicemix.jbi.jaxp.SourceTransformer;
import org.w3c.dom.Document;

import gov.nih.nci.caXchange.outbound.GridInvocationResult;
import gov.nih.nci.caXchange.outbound.GridMessage;
import gov.nih.nci.caXchange.outbound.GridMessageTransformer;
import gov.nih.nci.caXchange.outbound.TransformationException;

/**
 * 
 */

/**
 * @author steve
 *
 */
public class GridMessageTransformerImpl implements GridMessageTransformer {
    
    private static final Category log =
	Category.getInstance(GridMessageTransformerImpl.class);

    /* (non-Javadoc)
     * @see gov.nih.nci.caXchange.outbound.MessageTransformer#transformGridToOut(gov.nih.nci.caXchange.outbound.GridInvocationResult, javax.jbi.messaging.MessageExchange)
     */
    public NormalizedMessage transformGridToOut(GridInvocationResult result,
	    MessageExchange exchange) throws TransformationException {
	
	try {
	    NormalizedMessage out = exchange.createMessage();
	    out.setContent(new DOMSource(result.getResult()));
	    exchange.setMessage(out, "out");
	    return out;
	} catch(MessagingException me) {
	    log.error("Failed to transfer result to normalized message.", me);
	    throw new TransformationException("Failed to transfer result to " +
	    		"normalized message.", me);
	}
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.caXchange.outbound.MessageTransformer#transformInToGrid(javax.jbi.messaging.MessageExchange)
     */
    public GridMessage transformInToGrid(MessageExchange exchange)
	    throws TransformationException {
	
	try {
	    Document document =
		new SourceTransformer().toDOMDocument(exchange.getMessage("in"));
	
	    return new GridMessageImpl(document);
	} catch(Exception e) {
	    log.error("Failed to transform in message.", e);
	    throw new TransformationException("Failed to transform in" +
	    		" message.", e);
	}
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.caXchange.outbound.MessageTransformer#transofrmGridToFault(gov.nih.nci.caXchange.outbound.GridInvocationResult, javax.jbi.messaging.MessageExchange)
     */
    public NormalizedMessage transofrmGridToFault(GridInvocationResult result,
	    MessageExchange exchange) throws TransformationException {
	// TODO Auto-generated method stub
	return null;
    }

}
