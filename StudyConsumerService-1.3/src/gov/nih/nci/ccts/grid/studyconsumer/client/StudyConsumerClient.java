/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */
package gov.nih.nci.ccts.grid.studyconsumer.client;

import java.io.InputStream;
import java.rmi.RemoteException;

import javax.xml.namespace.QName;

import org.apache.axis.EngineConfiguration;
import org.apache.axis.client.AxisClient;
import org.apache.axis.client.Stub;
import org.apache.axis.configuration.FileProvider;
import org.apache.axis.message.addressing.EndpointReferenceType;
import org.apache.axis.types.URI.MalformedURIException;

import org.oasis.wsrf.properties.GetResourcePropertyResponse;

import org.globus.gsi.GlobusCredential;

import gov.nih.nci.ccts.grid.studyconsumer.stubs.StudyConsumerPortType;
import gov.nih.nci.ccts.grid.studyconsumer.stubs.service.StudyConsumerServiceAddressingLocator;
import gov.nih.nci.ccts.grid.studyconsumer.common.StudyConsumerI;
import gov.nih.nci.cabig.ccts.domain.Study;
import gov.nih.nci.cagrid.introduce.security.client.ServiceSecurityClient;

/**
 * This class is autogenerated, DO NOT EDIT GENERATED GRID SERVICE ACCESS METHODS.
 *
 * This client is generated automatically by Introduce to provide a clean unwrapped API to the
 * service.
 *
 * On construction the class instance will contact the remote service and retrieve it's security
 * metadata description which it will use to configure the Stub specifically for each method call.
 * 
 * @created by Introduce Toolkit version 1.3
 */
public class StudyConsumerClient extends StudyConsumerClientBase implements StudyConsumerI {	

	public StudyConsumerClient(String url) throws MalformedURIException, RemoteException {
		this(url,null);	
	}

	public StudyConsumerClient(String url, GlobusCredential proxy) throws MalformedURIException, RemoteException {
	   	super(url,proxy);
	}
	
	public StudyConsumerClient(EndpointReferenceType epr) throws MalformedURIException, RemoteException {
	   	this(epr,null);
	}
	
	public StudyConsumerClient(EndpointReferenceType epr, GlobusCredential proxy) throws MalformedURIException, RemoteException {
	   	super(epr,proxy);
	}

	public static void usage(){
		System.out.println(StudyConsumerClient.class.getName() + " -url <service url>");
	}
	
	public static void main(String [] args){
	    System.out.println("Running the Grid Service Client");
		try{
		if(!(args.length < 2)){
			if(args[0].equals("-url")){
			  StudyConsumerClient client = new StudyConsumerClient(args[1]);
			  client.createStudy(new Study());
			} else {
				usage();
				System.exit(1);
			}
		} else {
			usage();
			System.exit(1);
		}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

  public void createStudy(gov.nih.nci.cabig.ccts.domain.Study study) throws RemoteException, gov.nih.nci.ccts.grid.studyconsumer.stubs.types.InvalidStudyException, gov.nih.nci.ccts.grid.studyconsumer.stubs.types.StudyCreationException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"createStudy");
    gov.nih.nci.ccts.grid.studyconsumer.stubs.CreateStudyRequest params = new gov.nih.nci.ccts.grid.studyconsumer.stubs.CreateStudyRequest();
    gov.nih.nci.ccts.grid.studyconsumer.stubs.CreateStudyRequestStudy studyContainer = new gov.nih.nci.ccts.grid.studyconsumer.stubs.CreateStudyRequestStudy();
    studyContainer.setStudy(study);
    params.setStudy(studyContainer);
    gov.nih.nci.ccts.grid.studyconsumer.stubs.CreateStudyResponse boxedResult = portType.createStudy(params);
    }
  }

  public void commit(gov.nih.nci.cabig.ccts.domain.Study study) throws RemoteException, gov.nih.nci.ccts.grid.studyconsumer.stubs.types.InvalidStudyException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"commit");
    gov.nih.nci.ccts.grid.studyconsumer.stubs.CommitRequest params = new gov.nih.nci.ccts.grid.studyconsumer.stubs.CommitRequest();
    gov.nih.nci.ccts.grid.studyconsumer.stubs.CommitRequestStudy studyContainer = new gov.nih.nci.ccts.grid.studyconsumer.stubs.CommitRequestStudy();
    studyContainer.setStudy(study);
    params.setStudy(studyContainer);
    gov.nih.nci.ccts.grid.studyconsumer.stubs.CommitResponse boxedResult = portType.commit(params);
    }
  }

  public void rollback(gov.nih.nci.cabig.ccts.domain.Study study) throws RemoteException, gov.nih.nci.ccts.grid.studyconsumer.stubs.types.InvalidStudyException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"rollback");
    gov.nih.nci.ccts.grid.studyconsumer.stubs.RollbackRequest params = new gov.nih.nci.ccts.grid.studyconsumer.stubs.RollbackRequest();
    gov.nih.nci.ccts.grid.studyconsumer.stubs.RollbackRequestStudy studyContainer = new gov.nih.nci.ccts.grid.studyconsumer.stubs.RollbackRequestStudy();
    studyContainer.setStudy(study);
    params.setStudy(studyContainer);
    gov.nih.nci.ccts.grid.studyconsumer.stubs.RollbackResponse boxedResult = portType.rollback(params);
    }
  }

  public org.oasis.wsrf.properties.GetMultipleResourcePropertiesResponse getMultipleResourceProperties(org.oasis.wsrf.properties.GetMultipleResourceProperties_Element params) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"getMultipleResourceProperties");
    return portType.getMultipleResourceProperties(params);
    }
  }

  public org.oasis.wsrf.properties.GetResourcePropertyResponse getResourceProperty(javax.xml.namespace.QName params) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"getResourceProperty");
    return portType.getResourceProperty(params);
    }
  }

  public org.oasis.wsrf.properties.QueryResourcePropertiesResponse queryResourceProperties(org.oasis.wsrf.properties.QueryResourceProperties_Element params) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"queryResourceProperties");
    return portType.queryResourceProperties(params);
    }
  }

}
