package gov.nih.nci.caxchange.common;

import javax.xml.namespace.QName;


public interface CaXchangeConsumerServiceConstants {
	public static final String SERVICE_NS = "http://caxchange.nci.nih.gov/CaXchangeConsumerService";
	public static final QName RESOURCE_KEY = new QName(SERVICE_NS, "CaXchangeConsumerServiceKey");
	public static final QName RESOURCE_PROPERTY_SET = new QName(SERVICE_NS, "CaXchangeConsumerServiceResourceProperties");

	//Service level metadata (exposed as resouce properties)
	public static final QName SERVICEMETADATA = new QName("gme://caGrid.caBIG/1.0/gov.nih.nci.cagrid.metadata", "ServiceMetadata");
	
}
