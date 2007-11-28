package gov.nih.nci.ccts.grid.service.globus;

import gov.nih.nci.ccts.grid.service.StudyConsumerImpl;

import java.rmi.RemoteException;

/** 
 * DO NOT EDIT:  This class is autogenerated!
 *
 * This class implements each method in the portType of the service.  Each method call represented
 * in the port type will be then mapped into the unwrapped implementation which the user provides
 * in the StudyConsumerImpl class.  This class handles the boxing and unboxing of each method call
 * so that it can be correclty mapped in the unboxed interface that the developer has designed and 
 * has implemented.  Authorization callbacks are automatically made for each method based
 * on each methods authorization requirements.
 * 
 * @created by Introduce Toolkit version 1.1
 * 
 */
public class StudyConsumerProviderImpl{
	
	StudyConsumerImpl impl;
	
	public StudyConsumerProviderImpl() throws RemoteException {
		impl = new StudyConsumerImpl();
	}
	

    public gov.nih.nci.ccts.grid.studyconsumer.stubs.CreateStudyResponse createStudy(gov.nih.nci.ccts.grid.studyconsumer.stubs.CreateStudyRequest params) throws RemoteException, gov.nih.nci.ccts.grid.studyconsumer.stubs.types.InvalidStudyException, gov.nih.nci.ccts.grid.studyconsumer.stubs.types.StudyCreationException {
    gov.nih.nci.ccts.grid.studyconsumer.stubs.CreateStudyResponse boxedResult = new gov.nih.nci.ccts.grid.studyconsumer.stubs.CreateStudyResponse();
    impl.createStudy(params.getStudy().getStudy());
    return boxedResult;
  }

    public gov.nih.nci.ccts.grid.studyconsumer.stubs.CommitResponse commit(gov.nih.nci.ccts.grid.studyconsumer.stubs.CommitRequest params) throws RemoteException, gov.nih.nci.ccts.grid.studyconsumer.stubs.types.InvalidStudyException {
    gov.nih.nci.ccts.grid.studyconsumer.stubs.CommitResponse boxedResult = new gov.nih.nci.ccts.grid.studyconsumer.stubs.CommitResponse();
    impl.commit(params.getStudy().getStudy());
    return boxedResult;
  }

    public gov.nih.nci.ccts.grid.studyconsumer.stubs.RollbackResponse rollback(gov.nih.nci.ccts.grid.studyconsumer.stubs.RollbackRequest params) throws RemoteException, gov.nih.nci.ccts.grid.studyconsumer.stubs.types.InvalidStudyException {
    gov.nih.nci.ccts.grid.studyconsumer.stubs.RollbackResponse boxedResult = new gov.nih.nci.ccts.grid.studyconsumer.stubs.RollbackResponse();
    impl.rollback(params.getStudy().getStudy());
    return boxedResult;
  }

}
