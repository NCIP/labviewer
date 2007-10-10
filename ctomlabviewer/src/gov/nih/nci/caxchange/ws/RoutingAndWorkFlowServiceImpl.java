
package gov.nih.nci.caxchange.ws;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import gov.nih.nci.caxchange.ws.routingandworkflow.Acknowledgement;
import gov.nih.nci.caxchange.ws.routingandworkflow.ErrorMessage_Exception;

@WebService(serviceName = "RoutingAndWorkFlowService", targetNamespace = "http://caxchange.nci.nih.gov/ws", wsdlLocation = "file:/C:/EWS/JSR-181ServiceUnitNov20/src/main/resources/service.wsdl", endpointInterface = "gov.nih.nci.caxchange.ws.RoutingAndWorkflowService")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.BARE)
public class RoutingAndWorkFlowServiceImpl
    implements RoutingAndWorkflowService
{


    public Acknowledgement executeRoutingOrWorkflow(gov.nih.nci.caxchange.ws.routingandworkflow.RoutingAndWorkflowRequest RoutingAndWorkflowRequest)
        throws ErrorMessage_Exception
    {
        throw new UnsupportedOperationException();
    }

}
