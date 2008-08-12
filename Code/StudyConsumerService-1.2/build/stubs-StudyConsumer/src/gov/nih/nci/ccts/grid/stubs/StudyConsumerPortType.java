/**
 * StudyConsumerPortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package gov.nih.nci.ccts.grid.stubs;

public interface StudyConsumerPortType extends java.rmi.Remote {
    public gov.nih.nci.ccts.grid.stubs.CreateStudyResponse createStudy(gov.nih.nci.ccts.grid.stubs.CreateStudyRequest parameters) throws java.rmi.RemoteException, gov.nih.nci.ccts.grid.stubs.types.StudyCreationException, gov.nih.nci.ccts.grid.stubs.types.InvalidStudyException;
    public gov.nih.nci.ccts.grid.stubs.CommitResponse commit(gov.nih.nci.ccts.grid.stubs.CommitRequest parameters) throws java.rmi.RemoteException, gov.nih.nci.ccts.grid.stubs.types.InvalidStudyException;
    public gov.nih.nci.ccts.grid.stubs.RollbackResponse rollback(gov.nih.nci.ccts.grid.stubs.RollbackRequest parameters) throws java.rmi.RemoteException, gov.nih.nci.ccts.grid.stubs.types.InvalidStudyException;
    public org.oasis.wsrf.properties.GetMultipleResourcePropertiesResponse getMultipleResourceProperties(org.oasis.wsrf.properties.GetMultipleResourceProperties_Element getMultipleResourcePropertiesRequest) throws java.rmi.RemoteException, org.oasis.wsrf.properties.InvalidResourcePropertyQNameFaultType, org.oasis.wsrf.properties.ResourceUnknownFaultType;
    public org.oasis.wsrf.properties.GetResourcePropertyResponse getResourceProperty(javax.xml.namespace.QName getResourcePropertyRequest) throws java.rmi.RemoteException, org.oasis.wsrf.properties.InvalidResourcePropertyQNameFaultType, org.oasis.wsrf.properties.ResourceUnknownFaultType;
    public org.oasis.wsrf.properties.QueryResourcePropertiesResponse queryResourceProperties(org.oasis.wsrf.properties.QueryResourceProperties_Element queryResourcePropertiesRequest) throws java.rmi.RemoteException, org.oasis.wsrf.properties.InvalidResourcePropertyQNameFaultType, org.oasis.wsrf.properties.InvalidQueryExpressionFaultType, org.oasis.wsrf.properties.QueryEvaluationErrorFaultType, org.oasis.wsrf.properties.ResourceUnknownFaultType, org.oasis.wsrf.properties.UnknownQueryExpressionDialectFaultType;
    public gov.nih.nci.cagrid.introduce.security.stubs.GetServiceSecurityMetadataResponse getServiceSecurityMetadata(gov.nih.nci.cagrid.introduce.security.stubs.GetServiceSecurityMetadataRequest parameters) throws java.rmi.RemoteException;
}
