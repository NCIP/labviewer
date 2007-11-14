package gov.nih.nci.cagrid.caxchange.context.service.globus.resource;

import javax.xml.namespace.QName;


public interface ResourceConstants {
	public static final String SERVICE_NS = "http://caxchange.cagrid.nci.nih.gov/CaXchangeRequestProcessor/Context";
	public static final QName RESOURCE_KEY = new QName(SERVICE_NS, "CaXchangeResponseServiceKey");
	public static final QName RESOURCE_PROPERTY_SET = new QName(SERVICE_NS, "CaXchangeResponseServiceResourceProperties");

	//Service level metadata (exposed as resouce properties)
	public static final QName CURRENTTIME_Value_RP = new QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceLifetime-1.2-draft-01.xsd", "CurrentTime");
	public static final QName TERMINATIONTIME_Value_RP = new QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceLifetime-1.2-draft-01.xsd", "TerminationTime");
	public static final QName CAXCHANGERESPONSEMESSAGE_Value_RP = new QName("http://caXchange.nci.nih.gov/messaging", "caXchangeResponseMessage");
	
}
