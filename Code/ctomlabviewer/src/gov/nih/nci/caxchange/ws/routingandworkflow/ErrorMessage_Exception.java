
package gov.nih.nci.caxchange.ws.routingandworkflow;

import javax.xml.namespace.QName;
import gov.nih.nci.caxchange.ws.routingandworkflow.ErrorDetails;
import org.codehaus.xfire.fault.FaultInfoException;

public class ErrorMessage_Exception
    extends FaultInfoException
{

    private ErrorDetails faultInfo;

    public ErrorMessage_Exception(String message, ErrorDetails faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    public ErrorMessage_Exception(String message, Throwable t, ErrorDetails faultInfo) {
        super(message, t);
        this.faultInfo = faultInfo;
    }

    public ErrorDetails getFaultInfo() {
        return faultInfo;
    }

    public static QName getFaultName() {
        return new QName("http://caxchange.nci.nih.gov/ws/routingandworkflow", "ErrorDetails");
    }

}
