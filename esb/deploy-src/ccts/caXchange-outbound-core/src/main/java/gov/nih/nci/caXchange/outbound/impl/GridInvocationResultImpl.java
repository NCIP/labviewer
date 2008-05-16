/**
 * 
 */
package gov.nih.nci.caXchange.outbound.impl;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import gov.nih.nci.caXchange.outbound.GridInvocationResult;

/**
 * This class contains methods for the grid invocation result
 * @author steve
 *
 */
public class GridInvocationResultImpl implements GridInvocationResult {
    
    private Document message;
    /**
     * Constructor for the class 
     * @param message
     */
    public GridInvocationResultImpl(Document message) {
	this.message = message;
    }

   /**
    * This method checks if there is fault in the message
    * @param
    * @return false
    * @throws
    */
    public boolean isFault() {
	return false;
    }
    /**
     * This method gets the result form child node
     * @param
     * @rerurn gets result form the child node of document
     * @throws 
     */
    public Node getResult() {
	return message.getDocumentElement();
    }

}
