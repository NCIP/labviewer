/**
 * 
 */
package gov.nih.nci.caXchange.outbound;

import java.util.List;

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
    
    public List<Element> getPayloads();

    
    public Element getSchemaDefinition();
    
    public String getExternalIdentifier();
    
    public String getCaxchangeIdentifier();
    
    public String getOperationName();
    
    public String getServiceType();
    

}
