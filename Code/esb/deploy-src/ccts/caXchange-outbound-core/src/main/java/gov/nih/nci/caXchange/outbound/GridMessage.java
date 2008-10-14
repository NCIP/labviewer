/**
 * 
 */
package gov.nih.nci.caXchange.outbound;

import org.w3c.dom.Element;

/**
 * This interface defines the message to be sent 
 * from ESB to the grid service.
 * 
 * @author steve
 */
public interface GridMessage {
    
    public Element getMetaData();
    
    public Element getPayload();
    
    public Element getSchemaDefinition();
    

}
