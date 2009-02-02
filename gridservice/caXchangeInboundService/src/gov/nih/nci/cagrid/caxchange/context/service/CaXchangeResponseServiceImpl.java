package gov.nih.nci.cagrid.caxchange.context.service;

import gov.nih.nci.cagrid.caxchange.context.service.globus.resource.CaXchangeResponseServiceResource;
import gov.nih.nci.cagrid.caxchange.context.stubs.types.CaXchangeFault;
import gov.nih.nci.cagrid.caxchange.context.stubs.types.CaXchangeResponseNotReadyFault;
import gov.nih.nci.caxchange.ResponseMessage;

import java.rmi.RemoteException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * TODO:I am the service side implementation class.  IMPLEMENT AND DOCUMENT ME
 *
 * @created by Introduce Toolkit version 1.1
 *
 */
public class CaXchangeResponseServiceImpl extends CaXchangeResponseServiceImplBase {
	   static Logger logger = LogManager.getLogger(CaXchangeResponseServiceImpl.class);

	public CaXchangeResponseServiceImpl() throws RemoteException {
		super();
	}

  public gov.nih.nci.caxchange.ResponseMessage getResponse() throws RemoteException, gov.nih.nci.cagrid.caxchange.context.stubs.types.CaXchangeFault, gov.nih.nci.cagrid.caxchange.context.stubs.types.CaXchangeResponseNotReadyFault {
      try {
          gov.nih.nci.cagrid.caxchange.context.service.globus.resource.CaXchangeResponseServiceResourceHome brh =getResourceHome();
          CaXchangeResponseServiceResource currentResource = brh.getAddressedResource();
          if (currentResource.getCaXchangeResponseMessage() == null) {
        	  CaXchangeResponseNotReadyFault caXchangeResponseNotReadyFault = new CaXchangeResponseNotReadyFault();
        	  caXchangeResponseNotReadyFault.setFaultString("CaXchange response not ready.");
        	  throw caXchangeResponseNotReadyFault;
          }
          ResponseMessage responseMessage = currentResource.getCaXchangeResponseMessage();
          return responseMessage;
      }
      catch (CaXchangeResponseNotReadyFault cf) {
    	  throw cf;
      }
      catch (Exception e) {
          logger.error("Error in getting response.", e);
          CaXchangeFault caXchangeFault = new CaXchangeFault();
          caXchangeFault.setFaultString("Error in getting response."+e.getMessage());
          caXchangeFault.setStackTrace(e.getStackTrace());
          throw caXchangeFault;
      }
  }

}

