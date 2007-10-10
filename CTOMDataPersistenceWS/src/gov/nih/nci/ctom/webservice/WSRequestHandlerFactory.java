/*
 * WSRequestHandlerFactory.java
 * Created on Jul 7, 2005
 */
package gov.nih.nci.ctom.webservice;

import java.util.Hashtable;

import org.apache.log4j.Logger;


/**
 * Factory to return the implementation of the approriate WSRequestHandler
 * 
 * @author griffinch
 */
public class WSRequestHandlerFactory {

    final static Logger log = Logger.getLogger(WSRequestHandlerFactory.class
            .getName());

    static java.util.Map requestHandlerMap = new Hashtable();
    static {
    	requestHandlerMap.put(WsRequestConstants.PersistDataRequest,
    			PersistDataWSHandler.class);
        }
    
    public WSRequestHandler getWSRequestHandler(Object key) throws Exception {

        
        WSRequestHandler handler = (WSRequestHandler) ((Class) requestHandlerMap
                .get(key)).newInstance();

        if (handler == null) {
            String errorMessage = "The class " + key.getClass().getName()
                    + " could not be associated with a valid WSRequestHandler";
            log.fatal(errorMessage);
            throw new Exception(errorMessage);
        }

        return handler;

    }

}