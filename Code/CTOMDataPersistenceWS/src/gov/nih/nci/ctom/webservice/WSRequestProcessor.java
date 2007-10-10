package gov.nih.nci.ctom.webservice;

import org.apache.log4j.Logger;


import gov.nih.nci.ctom.ws.ErrorMessage;
import gov.nih.nci.ctom.ws.datapersistence.ErrorDetails;

/**
 * @version 1.0
 * @created 07-Jul-2005 8:35:59 AM
 */
public class WSRequestProcessor {
    final static Logger log = Logger.getLogger(WSRequestProcessor.class
            .getName());


    /**
     * @param request
     *  
     */
    public static java.lang.Object processWS(String requestType,
            java.lang.Object request) throws ErrorMessage {

        WSRequestHandlerFactory factory = new WSRequestHandlerFactory();
        WSRequestHandler wsHandler;
        try {
            wsHandler = factory.getWSRequestHandler(requestType);
        } catch (Exception e) {
            log.fatal(e);
            throw WSErrorHandler.createWsErrors(e);
        }
        return wsHandler.handleWSRequest(request);
    }

}