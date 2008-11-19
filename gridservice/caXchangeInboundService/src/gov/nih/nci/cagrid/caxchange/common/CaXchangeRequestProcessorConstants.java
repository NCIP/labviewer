package gov.nih.nci.cagrid.caxchange.common;

import javax.xml.namespace.QName;


public interface CaXchangeRequestProcessorConstants {
	public static final String SERVICE_NS = "http://caxchange.cagrid.nci.nih.gov/CaXchangeRequestProcessor";
	public static final QName RESOURCE_KEY = new QName(SERVICE_NS, "CaXchangeRequestProcessorKey");
	public static final QName RESOURCE_PROPERTY_SET = new QName(SERVICE_NS, "CaXchangeRequestProcessorResourceProperties");

	//Service level metadata (exposed as resouce properties)
	public static final QName SERVICEMETADATA = new QName("gme://caGrid.caBIG/1.0/gov.nih.nci.cagrid.metadata", "ServiceMetadata");
	
}