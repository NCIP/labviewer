package gov.nih.nci.cagrid.labviewer.grid.service.globus.resource;

import javax.xml.namespace.QName;


public interface ResourceConstants {
	public static final String SERVICE_NS = "http://grid.labviewer.cagrid.nci.nih.gov/StudyLookupService";
	public static final QName RESOURCE_KEY = new QName(SERVICE_NS, "StudyLookupServiceKey");
	public static final QName RESOURCE_PROPERTY_SET = new QName(SERVICE_NS, "StudyLookupServiceResourceProperties");

	//Service level metadata (exposed as resouce properties)
	public static final QName SERVICEMETADATA_Value_RP = new QName("gme://caGrid.caBIG/1.0/gov.nih.nci.cagrid.metadata", "ServiceMetadata");
	
}
