
package gov.nih.nci.ctom.ws;

import javax.xml.namespace.QName;
import gov.nih.nci.ctom.ws.datapersistence.ErrorDetails;
import org.codehaus.xfire.fault.FaultInfoException;

public class ErrorMessage
    extends FaultInfoException
{

    private ErrorDetails faultInfo;

    public ErrorMessage(String message, ErrorDetails faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    public ErrorMessage(String message, Throwable t, ErrorDetails faultInfo) {
        super(message, t);
        this.faultInfo = faultInfo;
    }

    public ErrorDetails getFaultInfo() {
        return faultInfo;
    }

    public static QName getFaultName() {
        return new QName("http://ctom.nci.nih.gov/ws/datapersistence", "ErrorDetails");
    }

}
