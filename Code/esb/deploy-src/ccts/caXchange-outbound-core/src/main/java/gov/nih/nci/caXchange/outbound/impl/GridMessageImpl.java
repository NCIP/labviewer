/**
 * 
 */
package gov.nih.nci.caXchange.outbound.impl;

import org.apache.log4j.Category;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import gov.nih.nci.caXchange.outbound.GridMessage;
import gov.nih.nci.caXchange.outbound.GridSU;

/**
 * This class contains the methods related to getting meta data, payload and
 * schema definition related to caxchange message
 * @author steve
 * 
 */
public class GridMessageImpl implements GridMessage {

	private static final Category log = Category
			.getInstance(GridMessageImpl.class);

	private Document message;
	/**
	 * Constructor for the class
	 * @param message
	 */
	public GridMessageImpl(Document message) {

		this.message = message;
	}
	/**
	 * This methods gets the meta data form the caxchange message
	 * @param
	 * @return null
	 * @throws
	 */
	public Element getMetaData() {
		NodeList elements = message.getDocumentElement()
				.getElementsByTagNameNS(GridSU.NS, GridSU.META_DATA_ELEMENT);

		if (elements.getLength() == 1) {
			return (Element) elements.item(0);
		} else {
			log.warn("Grid message does not contain meta data element.");
			return null;
		}
	}
	
	/**
	 * This method gets the payload from the caxchange message
	 * @param
	 * @return node or null
	 * @throws
	 */
	public Element getPayload() {
		NodeList nodes = getBusinessPayload().getChildNodes();
		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);
			if (node instanceof Element) {
				if (GridSU.SCHEMA_DEFINITION_ELEMENT
						.equals(node.getLocalName())) {
					continue;
				}
				return (Element) node;
			}
		}
		log.warn("Grid message does not contain payload element.");
		return null;
	}

	/**
	 * This method gets the schema definition from the caxchange message
	 * @param
	 * @return null
	 * @throws
	 */
	public Element getSchemaDefinition() {
		NodeList nodes = getBusinessPayload().getElementsByTagNameNS(
				GridSU.NS, GridSU.SCHEMA_DEFINITION_ELEMENT);

		if (nodes.getLength() == 1) {
			return (Element) nodes.item(0);
		} else {
			log.warn("Grid message does not contain schema definition element.");
			return null;
		}
	}
	
	/**
	 * This method gets the business payload form the caxchange message
	 * @param
	 * @return null
	 * @throws
	 */
	protected Element getBusinessPayload() {
		NodeList nodes = getRequest().getElementsByTagNameNS(GridSU.NS, GridSU.REQUEST_PAYLOAD_ELEMENT);

		if (nodes.getLength() == 1) {
			return (Element) nodes.item(0);
		} else {
			log.warn("Grid message does not contain request element.");
			return null;
		}
	}
	/**
	 * This method gets the caxchange request message
	 * @param
	 * @return null
	 * @throws
	 */
	protected Element getRequest() {
		NodeList nodes = message.getElementsByTagNameNS(GridSU.NS, GridSU.REQUEST_ELEMENT);
		if (nodes.getLength() == 1) {
			return (Element) nodes.item(0);
		} else {
			log.warn("Grid message does not contain request element.");
			return null;
		}
	}
	
    public String getExternalIdentifier(){
    	NodeList nodes = getMetaData().getElementsByTagNameNS(GridSU.NS, GridSU.EXTERNAL_IDENTIFIER);
    	if (nodes.getLength() == 1) {
    		return ((Element)nodes.item(0)).getTextContent();
    	}
    	return "";
    }
    
    public String getCaxchangeIdentifier(){
    	NodeList nodes = getMetaData().getElementsByTagNameNS(GridSU.NS, GridSU.CAXCHANGE_IDENTIFIER);
    	if (nodes.getLength() == 1) {
    		return ((Element)nodes.item(0)).getTextContent();
    	}
    	return "";
    }
    
    public String getOperationName() {
    	NodeList nodes = getMetaData().getElementsByTagNameNS(GridSU.NS, GridSU.OPERATION_NAME_ELEMENT);
    	if (nodes.getLength() == 1) {
    		return ((Element)nodes.item(0)).getTextContent();
    	}
    	return "";
    }

    
    public String getServiceType() {
    	NodeList nodes = getMetaData().getElementsByTagNameNS(GridSU.NS, GridSU.SERVICE_TYPE_ELEMENT);
    	if (nodes.getLength() == 1) {
    		return ((Element)nodes.item(0)).getTextContent();
    	}
    	return "";
    }    
}
