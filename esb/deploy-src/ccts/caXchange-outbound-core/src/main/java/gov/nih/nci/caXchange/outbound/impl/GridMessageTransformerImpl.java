/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */
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
 * This class contains the methods for mapping between 
 * the grid message and the normalized message
 * @author steve
 *
 */
public class GridMessageTransformerImpl implements GridMessageTransformer {
    
    private static final Category log =
	Category.getInstance(GridMessageTransformerImpl.class);

   /**
    * This methods converts the grid message to the normalized message 
    * to send it to the esb
    * @param result
    * @param exchange
    * @return out
    * @throws TransformationException
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
	/**
	 * This method converts the normalized message to the grid message
	 * @param exchange
	 * @return 
	 * @throws TransformationException
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

   /**
    * This method returns fault when converting from the grid message to normalized message
    * @param result
    * @param exchange
    * @return
    * @throws TransformationException
    */
    public NormalizedMessage transofrmGridToFault(GridInvocationResult result,
	    MessageExchange exchange) throws TransformationException {
	// TODO Auto-generated method stub
	return null;
    }

}
