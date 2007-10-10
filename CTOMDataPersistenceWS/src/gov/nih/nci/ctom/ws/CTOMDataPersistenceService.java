
package gov.nih.nci.ctom.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import gov.nih.nci.ctom.ws.datapersistence.Acknowledgement;
import gov.nih.nci.ctom.ws.datapersistence.PersistDataRequest;

@WebService(name = "CTOMDataPersistenceService", targetNamespace = "http://ctom.nci.nih.gov/ws")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface CTOMDataPersistenceService {


    @WebMethod(operationName = "persistData", action = "http://ctom.nci.nih.gov/ws/persistData")
    @WebResult(name = "AcknowledgementResponse", targetNamespace = "http://ctom.nci.nih.gov/ws")
    public Acknowledgement persistData(
        @WebParam(name = "CTOMPersistDataRequest", targetNamespace = "http://ctom.nci.nih.gov/ws")
        PersistDataRequest CTOMPersistDataRequest)
        throws ErrorMessage
    ;

}
