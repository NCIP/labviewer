package gov.nih.nci.ccts.grid.service.globus;

import gov.nih.nci.ccts.grid.service.RegistrationConsumerImpl;

import java.rmi.RemoteException;

/** 
 * DO NOT EDIT:  This class is autogenerated!
 *
 * This class implements each method in the portType of the service.  Each method call represented
 * in the port type will be then mapped into the unwrapped implementation which the user provides
 * in the RegistrationConsumerImpl class.  This class handles the boxing and unboxing of each method call
 * so that it can be correclty mapped in the unboxed interface that the developer has designed and 
 * has implemented.  Authorization callbacks are automatically made for each method based
 * on each methods authorization requirements.
 * 
 * @created by Introduce Toolkit version 1.1
 * 
 */
public class RegistrationConsumerProviderImpl{
	
	RegistrationConsumerImpl impl;
	
	public RegistrationConsumerProviderImpl() throws RemoteException {
		impl = new RegistrationConsumerImpl();
	}
	

    public gov.nih.nci.ccts.grid.stubs.RollbackResponse rollback(gov.nih.nci.ccts.grid.stubs.RollbackRequest params) throws RemoteException, gov.nih.nci.ccts.grid.stubs.types.InvalidRegistrationException {
    gov.nih.nci.ccts.grid.stubs.RollbackResponse boxedResult = new gov.nih.nci.ccts.grid.stubs.RollbackResponse();
    impl.rollback(params.getRegistration().getRegistration());
    return boxedResult;
  }

    public gov.nih.nci.ccts.grid.stubs.CommitResponse commit(gov.nih.nci.ccts.grid.stubs.CommitRequest params) throws RemoteException, gov.nih.nci.ccts.grid.stubs.types.InvalidRegistrationException {
    gov.nih.nci.ccts.grid.stubs.CommitResponse boxedResult = new gov.nih.nci.ccts.grid.stubs.CommitResponse();
    impl.commit(params.getRegistration().getRegistration());
    return boxedResult;
  }

    public gov.nih.nci.ccts.grid.stubs.RegisterResponse register(gov.nih.nci.ccts.grid.stubs.RegisterRequest params) throws RemoteException, gov.nih.nci.ccts.grid.stubs.types.InvalidRegistrationException, gov.nih.nci.ccts.grid.stubs.types.RegistrationConsumptionException {
    gov.nih.nci.ccts.grid.stubs.RegisterResponse boxedResult = new gov.nih.nci.ccts.grid.stubs.RegisterResponse();
    boxedResult.setRegistration(impl.register(params.getRegistration().getRegistration()));
    return boxedResult;
  }

}
