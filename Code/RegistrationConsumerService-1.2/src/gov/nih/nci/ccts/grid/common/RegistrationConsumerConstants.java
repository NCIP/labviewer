package gov.nih.nci.ccts.grid.common;

import javax.xml.namespace.QName;


public interface RegistrationConsumerConstants {
	public static final String SERVICE_NS = "http://grid.ccts.nci.nih.gov/RegistrationConsumer";
	public static final QName RESOURCE_KEY = new QName(SERVICE_NS, "RegistrationConsumerKey");
	public static final QName RESOURCE_PROPERTY_SET = new QName(SERVICE_NS, "RegistrationConsumerResourceProperties");

	//Service level metadata (exposed as resouce properties)
	
}
