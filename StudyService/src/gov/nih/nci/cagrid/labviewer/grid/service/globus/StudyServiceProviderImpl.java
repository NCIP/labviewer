/*
 * Copyright ScenPro, Inc and SemanticBits, LLC
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/labviewer/LICENSE.txt for details.
 */
package gov.nih.nci.cagrid.labviewer.grid.service.globus;

import gov.nih.nci.cagrid.labviewer.grid.service.StudyServiceImpl;

import java.rmi.RemoteException;

/** 
 * DO NOT EDIT:  This class is autogenerated!
 *
 * This class implements each method in the portType of the service.  Each method call represented
 * in the port type will be then mapped into the unwrapped implementation which the user provides
 * in the StudyServiceImpl class.  This class handles the boxing and unboxing of each method call
 * so that it can be correclty mapped in the unboxed interface that the developer has designed and 
 * has implemented.  Authorization callbacks are automatically made for each method based
 * on each methods authorization requirements.
 * 
 * @created by Introduce Toolkit version 1.3
 * 
 */
public class StudyServiceProviderImpl{
	
	StudyServiceImpl impl;
	
	public StudyServiceProviderImpl() throws RemoteException {
		impl = new StudyServiceImpl();
	}
	

    public gov.nih.nci.cagrid.labviewer.grid.stubs.GetStudyResponse getStudy(gov.nih.nci.cagrid.labviewer.grid.stubs.GetStudyRequest params) throws RemoteException {
    gov.nih.nci.cagrid.labviewer.grid.stubs.GetStudyResponse boxedResult = new gov.nih.nci.cagrid.labviewer.grid.stubs.GetStudyResponse();
    boxedResult.setStudy(impl.getStudy(params.getRegistration().getRegistration()));
    return boxedResult;
  }

}
