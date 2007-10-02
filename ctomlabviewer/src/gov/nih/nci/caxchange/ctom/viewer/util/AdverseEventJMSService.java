package gov.nih.nci.caxchange.ctom.viewer.util;

public class AdverseEventJMSService {
			 
	
	edu.duke.cabig.c3pr.esb.impl.MessageBroadcastServiceImpl esbBroadcastService;

	public edu.duke.cabig.c3pr.esb.impl.MessageBroadcastServiceImpl getEsbBroadcastService() {
		return esbBroadcastService;
	}

	public void setEsbBroadcastService(
			edu.duke.cabig.c3pr.esb.impl.MessageBroadcastServiceImpl esbBroadcastService) {
		this.esbBroadcastService = esbBroadcastService;
	}
}
