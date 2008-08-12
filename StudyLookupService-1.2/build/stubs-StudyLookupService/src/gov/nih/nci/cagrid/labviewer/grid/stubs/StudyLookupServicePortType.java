/**
 * StudyLookupServicePortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package gov.nih.nci.cagrid.labviewer.grid.stubs;

public interface StudyLookupServicePortType extends java.rmi.Remote {
    public gov.nih.nci.cagrid.labviewer.grid.stubs.GetStudyResponse getStudy(gov.nih.nci.cagrid.labviewer.grid.stubs.GetStudyRequest parameters) throws java.rmi.RemoteException;
    public gov.nih.nci.cagrid.introduce.security.stubs.GetServiceSecurityMetadataResponse getServiceSecurityMetadata(gov.nih.nci.cagrid.introduce.security.stubs.GetServiceSecurityMetadataRequest parameters) throws java.rmi.RemoteException;
}
