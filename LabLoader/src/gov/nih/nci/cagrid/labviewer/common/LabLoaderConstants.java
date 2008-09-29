package gov.nih.nci.cagrid.labviewer.common;

import javax.xml.namespace.QName;


public interface LabLoaderConstants {
	public static final String SERVICE_NS = "gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain.ctlab";
	public static final QName RESOURCE_KEY = new QName(SERVICE_NS, "LabLoaderKey");
	public static final QName RESOURCE_PROPERTY_SET = new QName(SERVICE_NS, "LabLoaderResourceProperties");

	//Service level metadata (exposed as resouce properties)
	
}
