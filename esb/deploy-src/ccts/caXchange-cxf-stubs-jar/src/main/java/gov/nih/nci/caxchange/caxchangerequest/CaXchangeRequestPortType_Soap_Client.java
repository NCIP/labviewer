
package gov.nih.nci.caxchange.caxchangerequest;

/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import java.util.concurrent.Future;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.AsyncHandler;
import javax.xml.ws.Response;

/**
 * This class was generated by Apache CXF 2.2
 * Mon Apr 20 15:03:48 EDT 2009
 * Generated source version: 2.2
 * 
 */

public final class CaXchangeRequestPortType_Soap_Client {

    private static final QName SERVICE_NAME = new QName("http://caXchange.nci.nih.gov/caxchangerequest", "CaXchangeRequestService");

    private CaXchangeRequestPortType_Soap_Client() {
    }

    public static void main(String args[]) throws Exception {
        URL wsdlURL = CaXchangeRequestService.WSDL_LOCATION;
        if (args.length > 0) { 
            File wsdlFile = new File(args[0]);
            try {
                if (wsdlFile.exists()) {
                    wsdlURL = wsdlFile.toURI().toURL();
                } else {
                    wsdlURL = new URL(args[0]);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
      
        CaXchangeRequestService ss = new CaXchangeRequestService(wsdlURL, SERVICE_NAME);
        CaXchangeRequestPortType port = ss.getSoap();  
        
        {
        System.out.println("Invoking processRequestAsync...");
        gov.nih.nci.caxchange.messaging.Message _processRequestAsync_parameters = null;
        Response<gov.nih.nci.caxchange.messaging.ResponseMessage> _processRequestAsync__return = port.processRequestAsync(_processRequestAsync_parameters);


        }
        {
        System.out.println("Invoking processRequestAsync...");
        gov.nih.nci.caxchange.messaging.Message _processRequestAsync_parameters = null;
        AsyncHandler<gov.nih.nci.caxchange.messaging.ResponseMessage> _processRequestAsync_asyncHandler = null;
        Future<?> _processRequestAsync__return = port.processRequestAsync(_processRequestAsync_parameters, _processRequestAsync_asyncHandler);


        }
        {
        System.out.println("Invoking processRequest...");
        gov.nih.nci.caxchange.messaging.Message _processRequest_parameters = null;
        gov.nih.nci.caxchange.messaging.ResponseMessage _processRequest__return = port.processRequest(_processRequest_parameters);
        System.out.println("processRequest.result=" + _processRequest__return);


        }

        System.exit(0);
    }

}
