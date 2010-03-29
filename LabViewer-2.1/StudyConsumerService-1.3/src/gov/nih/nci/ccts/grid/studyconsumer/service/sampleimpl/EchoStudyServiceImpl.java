package gov.nih.nci.ccts.grid.studyconsumer.service.sampleimpl;

import java.rmi.RemoteException;

import javax.xml.namespace.QName;

import org.oasis.wsrf.properties.GetMultipleResourcePropertiesResponse;
import org.oasis.wsrf.properties.GetMultipleResourceProperties_Element;
import org.oasis.wsrf.properties.GetResourcePropertyResponse;
import org.oasis.wsrf.properties.QueryResourcePropertiesResponse;
import org.oasis.wsrf.properties.QueryResourceProperties_Element;

import gov.nih.nci.cabig.ccts.domain.Study;
import gov.nih.nci.ccts.grid.studyconsumer.common.StudyConsumerI;
import gov.nih.nci.ccts.grid.studyconsumer.stubs.types.InvalidStudyException;
import gov.nih.nci.ccts.grid.studyconsumer.stubs.types.StudyCreationException;


public class EchoStudyServiceImpl implements StudyConsumerI {

    public void commit(Study study) throws RemoteException, InvalidStudyException {
        System.out.println("Recieved commit Message..");
        
    }

    public void createStudy(Study study) throws RemoteException, InvalidStudyException,
                    StudyCreationException {
        System.out.println("Recieved create study Message..");
    }

    public GetMultipleResourcePropertiesResponse getMultipleResourceProperties(
                    GetMultipleResourceProperties_Element params) throws RemoteException {
        // TODO Auto-generated method stub
        return null;
    }

    public GetResourcePropertyResponse getResourceProperty(QName params) throws RemoteException {
        // TODO Auto-generated method stub
        return null;
    }

    public QueryResourcePropertiesResponse queryResourceProperties(
                    QueryResourceProperties_Element params) throws RemoteException {
        // TODO Auto-generated method stub
        return null;
    }

    public void rollback(Study study) throws RemoteException, InvalidStudyException {
        System.out.println("Recieved rollback Message..");
    }

}
