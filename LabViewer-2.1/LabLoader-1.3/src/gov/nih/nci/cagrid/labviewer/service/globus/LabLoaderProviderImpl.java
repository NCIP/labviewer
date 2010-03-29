package gov.nih.nci.cagrid.labviewer.service.globus;

import gov.nih.nci.cagrid.labviewer.service.LabLoaderImpl;

import java.rmi.RemoteException;

/** 
 * DO NOT EDIT:  This class is autogenerated!
 *
 * This class implements each method in the portType of the service.  Each method call represented
 * in the port type will be then mapped into the unwrapped implementation which the user provides
 * in the LabLoaderImpl class.  This class handles the boxing and unboxing of each method call
 * so that it can be correclty mapped in the unboxed interface that the developer has designed and 
 * has implemented.  Authorization callbacks are automatically made for each method based
 * on each methods authorization requirements.
 * 
 * @created by Introduce Toolkit version 1.1
 * 
 */
public class LabLoaderProviderImpl{
	
	LabLoaderImpl impl;
	
	public LabLoaderProviderImpl() throws RemoteException {
		impl = new LabLoaderImpl();
	}
	

    public gov.nih.nci.cagrid.labviewer.stubs.LoadLabResponse loadLab(gov.nih.nci.cagrid.labviewer.stubs.LoadLabRequest params) throws RemoteException {
    gov.nih.nci.cagrid.labviewer.stubs.LoadLabResponse boxedResult = new gov.nih.nci.cagrid.labviewer.stubs.LoadLabResponse();
    impl.loadLab(params.getString());
    return boxedResult;
  }

    public gov.nih.nci.cagrid.labviewer.stubs.RollbackResponse rollback(gov.nih.nci.cagrid.labviewer.stubs.RollbackRequest params) throws RemoteException {
    gov.nih.nci.cagrid.labviewer.stubs.RollbackResponse boxedResult = new gov.nih.nci.cagrid.labviewer.stubs.RollbackResponse();
    impl.rollback(params.getString());
    return boxedResult;
  }

}
