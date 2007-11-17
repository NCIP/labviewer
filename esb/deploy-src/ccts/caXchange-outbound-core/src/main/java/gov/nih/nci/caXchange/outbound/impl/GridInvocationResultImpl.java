/**
 * 
 */
package gov.nih.nci.caXchange.outbound.impl;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import gov.nih.nci.caXchange.outbound.GridInvocationResult;

/**
 * @author steve
 *
 */
public class GridInvocationResultImpl implements GridInvocationResult {
    
    private Document message;
    
    public GridInvocationResultImpl(Document message) {
	this.message = message;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.caXchange.outbound.GridInvocationResult#isFault()
     */
    public boolean isFault() {
	return false;
    }
    
    public Node getResult() {
	return message.getDocumentElement();
    }

}
