
package gov.nih.nci.caxchange.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import gov.nih.nci.caxchange.ws.routingandworkflow.Acknowledgement;
import gov.nih.nci.caxchange.ws.routingandworkflow.ErrorMessage_Exception;

@WebService(name = "RoutingAndWorkflowService", targetNamespace = "http://caxchange.nci.nih.gov/ws")
public interface RoutingAndWorkflowService {


    @WebMethod(operationName = "executeRoutingOrWorkflow", action = "http://caxchange.nci.nih.gov/ws/executeRoutingOrWorkflow")
    @WebResult(name = "AcknowledgementResponse", targetNamespace = "http://caxchange.nci.nih.gov/ws")
    public Acknowledgement executeRoutingOrWorkflow(
        @WebParam(name = "RoutingAndWorkflowRequest", targetNamespace = "http://caxchange.nci.nih.gov/ws")
        gov.nih.nci.caxchange.ws.routingandworkflow.RoutingAndWorkflowRequest RoutingAndWorkflowRequest)
        throws ErrorMessage_Exception
    ;

}
