/**
 * 
 */
package gov.nih.nci.caXchange.outbound;

import org.w3c.dom.Node;

/**
 * This interface defines the result of a grid service invocation.
 * 
 * @author steve
 */
public interface GridInvocationResult {
    
    public boolean isFault();
    
    public Node getResult();

}
