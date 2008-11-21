package gov.nih.nci.ccts.grid.studyconsumer.common;

import javax.xml.namespace.QName;


public interface StudyConsumerConstants {
	public static final String SERVICE_NS = "http://studyconsumer.grid.ccts.nci.nih.gov/StudyConsumer";
	public static final QName RESOURCE_KEY = new QName(SERVICE_NS, "StudyConsumerKey");
	public static final QName RESOURCE_PROPERTY_SET = new QName(SERVICE_NS, "StudyConsumerResourceProperties");

	//Service level metadata (exposed as resouce properties)
	
}
