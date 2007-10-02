package gov.nih.nci.caxchange.ctom.viewer.applicationservice;

import gov.nih.nci.caxchange.common.services.constants.AuditConstants;
import gov.nih.nci.caxchange.ctom.viewer.util.AdverseEventJMSService;
import gov.nih.nci.caxchange.ctom.viewer.util.AdverseEventMapper;
import gov.nih.nci.caxchange.ctom.viewer.util.LoadLabActivitiesMapper;
import gov.nih.nci.caxchange.ctom.viewer.util.ObjectFactory;
import gov.nih.nci.caxchange.ctom.viewer.viewobjects.LabActivityResult;
import gov.nih.nci.caxchange.ws.RoutingAndWorkFlowServiceClient;
import gov.nih.nci.caxchange.ws.RoutingAndWorkflowService;
import gov.nih.nci.caxchange.ws.routingandworkflow.Acknowledgement;
import gov.nih.nci.caxchange.ws.routingandworkflow.AcknowledgementStatuses;
import gov.nih.nci.caxchange.ws.routingandworkflow.Credentials;
import gov.nih.nci.caxchange.ws.routingandworkflow.ErrorMessage_Exception;
import gov.nih.nci.caxchange.ws.routingandworkflow.MessagePayload;
import gov.nih.nci.caxchange.ws.routingandworkflow.MessageProperties;
import gov.nih.nci.caxchange.ws.routingandworkflow.PayloadTypes;
import gov.nih.nci.caxchange.ws.routingandworkflow.ProcessingInstructions;
import gov.nih.nci.caxchange.ws.routingandworkflow.Property;
import gov.nih.nci.caxchange.ws.routingandworkflow.RoutingAndWorkflowRequest;
import gov.nih.nci.ctom.messaging.adverseevent.AdverseEvent;
import gov.nih.nci.ctom.messaging.loadlabactivities.LoadLabActivities;
import gov.nih.nci.labhub.domain.SubjectAssignment;


import java.io.BufferedWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Properties;

import org.codehaus.xfire.client.Client;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.ValidationException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

public class EventsManager {

	private String loadLabActiviteSystemURL;
	
	
	public void sendLabActivities(List<LabActivityResult> saList, String userId) throws Exception, ValidationException {
		
		
		LoadLabActivitiesMapper mapper = new LoadLabActivitiesMapper();
		LoadLabActivities labActivities = mapper.mapLabActivities(saList);
		// Marshal the object
		StringWriter strw =  new StringWriter();	
		BufferedWriter writer = new BufferedWriter(strw);
		Marshaller.marshal(labActivities, writer);
		
		
		
		System.out.println(strw.toString());
		
		sendMessage(strw.toString(), "http://ctom.nci.nih.gov/labactivities", "TESTUSER");
		
	}
	
	public void sendAdverseEvent(LabActivityResult sa, String userId) throws Exception, ValidationException {
		
		AdverseEventMapper mapper = new AdverseEventMapper();
		AdverseEvent ad = mapper.mapAdverseEvent(sa);
		
		// Marshal the object
		StringWriter strw =  new StringWriter();	
		BufferedWriter writer = new BufferedWriter(strw);
		Marshaller.marshal(ad, writer);
		System.out.println("XML Being Sent" + strw.toString());

		ClassPathResource res = new ClassPathResource("applicationContext.xml"); 
		
		
		gov.nih.nci.caxchange.ctom.viewer.util.AdverseEventJMSService serv = (AdverseEventJMSService) ObjectFactory.getObject("AdverseEventJMSService");
		serv.getEsbBroadcastService().initialize();
		serv.getEsbBroadcastService().broadcast(strw.toString());
		
		//sendMessage(strw.toString(), "http://ctom.nci.nih.gov/adverseEvent", "TESTUSER");

		//TODO Send the LabActivites XML to the Hub
	}
	
	protected void sendMessage(String xml, String xmlURI, String userId) throws Exception{
	
		
		
		
		
		
		RoutingAndWorkFlowServiceClient svc = new RoutingAndWorkFlowServiceClient();
//		RoutingAndWorkflowService service = svc.getRoutingAndWorkflowServiceSoapPort("http://cbiovdev5032.nci.nih.gov:8192/routingandworkflow/");
		RoutingAndWorkflowService service = svc.getRoutingAndWorkflowServiceSoapPort(getLoadLabActiviteSystemURL());
		Client client = Client.getInstance(service);
		client.setProperty("mtom-enabled", "true");
		
		
		//Creating request
		RoutingAndWorkflowRequest req = new RoutingAndWorkflowRequest();
		ProcessingInstructions procInst = new ProcessingInstructions();
		
		//Setting Properties
		MessageProperties mprops      = new MessageProperties();
		
		Properties props = new Properties();
		Property property = new Property();
		mprops.getProperty().add(property);
	
		
		mprops.getProperty().add(property);			
		

		property = new Property();
		property.setKey(AuditConstants.ORGANIZATION_NAME);
		property.setValue("CAXCHANGE");
		mprops.getProperty().add(property);
		
		
		Credentials cred = new Credentials();
		cred.setUserName(userId);
		/*cred.setUserName("modik");
		cred.setPassword("f");
		property.setKey(SecurityConstants.IDENTITY_PROVIDER_LOCATION);
		property.setValue("https://cagrid-auth-qa.nci.nih.gov:8443/wsrf/services/cagrid/AuthenticationService");*/
		
		
		MessagePayload pl = new MessagePayload();
		//pl.setText("<hello>This is a hello world message</hello>");
		
		//pl.setText("<?xml version=\"1.0\" encoding=\"UTF-8\"?><loadLabActivities xmlns=\"http://ctom.nci.nih.gov/labactivities\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://ctom.nci.nih.gov/labactivities C:\\eclipse\\workspace\\CTOMAPIS\\LabActivities.xsd\"> <laStudySubjectAssignment> <saStudy> <identifier>String</identifier> <name>String</name> <assigningAuthority>String</assigningAuthority> </saStudy> <studySubjectIdentifier>String</studySubjectIdentifier> <saActivity> <identifier>String</identifier> <startDateTime>2001-12-17T09:30:47.0Z</startDateTime> <endDateTime>2001-12-17T09:30:47.0Z</endDateTime> <activityLabTest> <code>String</code> <ltabResult> <textResult>String</textResult> <numericResult>3.14159E0</numericResult> <unitOfMeasureCode>String</unitOfMeasureCode> <referenceRangeComments>String</referenceRangeComments> <referenceRangeHigh>3.141593E0</referenceRangeHigh> <referenceRangeLow>3.141593E0</referenceRangeLow> <testPerformedDate>1967-08-13</testPerformedDate> </ltabResult> </activityLabTest> </saActivity> </laStudySubjectAssignment> </loadLabActivities>");
		
		pl.setText(xml);
		
		
		
		ProcessingInstructions pi = new ProcessingInstructions();
	

		req.setProcessingInstructions(pi);
		req.setPayload(pl);
		req.setExternalIdentifier(String.valueOf(new java.util.Date()));
		req.setProperties(mprops);
		req.setCredentials(cred);
		
		Acknowledgement ack = null;;
		try {
			ack = service.executeRoutingOrWorkflow(req);
		} catch (ErrorMessage_Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		AcknowledgementStatuses status = ack.getStatus();
		System.out.println("Status Received from Webservice : " + status);
		if (status.value().trim().equalsIgnoreCase(AcknowledgementStatuses.MESSAGE_RECEIVED.value().trim()))
			return;
		else
			throw new Exception("Error Occurred While Sending Message, Operation not Completed");
		
		
		
	}

	public String getLoadLabActiviteSystemURL() {
		return loadLabActiviteSystemURL;
	}

	public void setLoadLabActiviteSystemURL(String loadLabActiviteSystemURL) {
		this.loadLabActiviteSystemURL = loadLabActiviteSystemURL;
	}
	
	
	
}
