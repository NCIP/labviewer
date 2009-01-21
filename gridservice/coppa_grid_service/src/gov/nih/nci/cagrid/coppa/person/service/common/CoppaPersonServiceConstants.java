package gov.nih.nci.cagrid.coppa.person.service.common;

import javax.xml.namespace.QName;


public interface CoppaPersonServiceConstants {
	public static final String SERVICE_NS = "http://person.coppa.cagrid.nci.nih.gov/CoppaPersonService";
	public static final QName RESOURCE_KEY = new QName(SERVICE_NS, "CoppaPersonServiceKey");
	public static final QName RESOURCE_PROPERTY_SET = new QName(SERVICE_NS, "CoppaPersonServiceResourceProperties");

	//Service level metadata (exposed as resouce properties)
	public static final QName SERVICEMETADATA = new QName("gme://caGrid.caBIG/1.0/gov.nih.nci.cagrid.metadata", "ServiceMetadata");
	
}
