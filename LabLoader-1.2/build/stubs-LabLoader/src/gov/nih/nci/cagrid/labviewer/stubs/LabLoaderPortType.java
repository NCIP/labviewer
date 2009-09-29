/**
 * LabLoaderPortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package gov.nih.nci.cagrid.labviewer.stubs;

public interface LabLoaderPortType extends java.rmi.Remote {
    public gov.nih.nci.cagrid.labviewer.stubs.LoadLabResponse loadLab(gov.nih.nci.cagrid.labviewer.stubs.LoadLabRequest parameters) throws java.rmi.RemoteException;
    public gov.nih.nci.cagrid.labviewer.stubs.RollbackResponse rollback(gov.nih.nci.cagrid.labviewer.stubs.RollbackRequest parameters) throws java.rmi.RemoteException;
    public gov.nih.nci.cagrid.introduce.security.stubs.GetServiceSecurityMetadataResponse getServiceSecurityMetadata(gov.nih.nci.cagrid.introduce.security.stubs.GetServiceSecurityMetadataRequest parameters) throws java.rmi.RemoteException;
}
