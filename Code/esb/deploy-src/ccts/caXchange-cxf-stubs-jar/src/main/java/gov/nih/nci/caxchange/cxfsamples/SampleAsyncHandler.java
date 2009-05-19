package gov.nih.nci.caxchange.cxfsamples;

import gov.nih.nci.caxchange.messaging.ResponseMessage;

import javax.xml.ws.AsyncHandler;
import javax.xml.ws.Response;

/**
 * This is a handler class for handling Asynchronous responses
 * 
 * @author Ekagra Software Technologies
 * 
 */
public class SampleAsyncHandler implements AsyncHandler<ResponseMessage> {

	private ResponseMessage reply;

	/**
	 * Gets called when the response arrives during 
	 * asynchronous processing.
	 */
	public void handleResponse(Response<ResponseMessage> response) {
		try {
			reply = response.get();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public gov.nih.nci.caxchange.messaging.Response getResponse() {
		return reply.getResponse();
	}

}
