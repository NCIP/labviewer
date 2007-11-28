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
 * @author steve
 * 
 */
public class GridMessageImpl implements GridMessage {

	private static final Category log = Category
			.getInstance(GridMessageImpl.class);

	private Document message;

	public GridMessageImpl(Document message) {

		this.message = message;
	}

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

	public Element getSchemaDefinition() {
		NodeList nodes = getBusinessPayload().getElementsByTagNameNS(
				GridSU.NS, GridSU.SCHEMA_DEFINITION_ELEMENT);

		if (nodes.getLength() == 1) {
			return (Element) nodes.item(0);
		} else {
			log
					.warn("Grid message does not contain schema definition element.");
			return null;
		}
	}

	protected Element getBusinessPayload() {
		NodeList nodes = getRequest().getElementsByTagNameNS(GridSU.NS, GridSU.REQUEST_PAYLOAD_ELEMENT);

		if (nodes.getLength() == 1) {
			return (Element) nodes.item(0);
		} else {
			log.warn("Grid message does not contain request element.");
			return null;
		}
	}

	protected Element getRequest() {
		NodeList nodes = message.getElementsByTagNameNS(GridSU.NS, GridSU.REQUEST_ELEMENT);
		if (nodes.getLength() == 1) {
			return (Element) nodes.item(0);
		} else {
			log.warn("Grid message does not contain request element.");
			return null;
		}
	}

}
