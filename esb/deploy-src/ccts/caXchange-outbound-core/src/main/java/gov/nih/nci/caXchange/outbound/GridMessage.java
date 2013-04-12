/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
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
