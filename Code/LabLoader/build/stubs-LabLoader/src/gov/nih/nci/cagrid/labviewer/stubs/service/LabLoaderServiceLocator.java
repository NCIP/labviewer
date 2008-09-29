/**
 * LabLoaderServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package gov.nih.nci.cagrid.labviewer.stubs.service;

public class LabLoaderServiceLocator extends org.apache.axis.client.Service implements gov.nih.nci.cagrid.labviewer.stubs.service.LabLoaderService {

    public LabLoaderServiceLocator() {
    }


    public LabLoaderServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public LabLoaderServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for LabLoaderPortTypePort
    private java.lang.String LabLoaderPortTypePort_address = "http://localhost:8080/wsrf/services/";

    public java.lang.String getLabLoaderPortTypePortAddress() {
        return LabLoaderPortTypePort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String LabLoaderPortTypePortWSDDServiceName = "LabLoaderPortTypePort";

    public java.lang.String getLabLoaderPortTypePortWSDDServiceName() {
        return LabLoaderPortTypePortWSDDServiceName;
    }

    public void setLabLoaderPortTypePortWSDDServiceName(java.lang.String name) {
        LabLoaderPortTypePortWSDDServiceName = name;
    }

    public gov.nih.nci.cagrid.labviewer.stubs.LabLoaderPortType getLabLoaderPortTypePort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(LabLoaderPortTypePort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getLabLoaderPortTypePort(endpoint);
    }

    public gov.nih.nci.cagrid.labviewer.stubs.LabLoaderPortType getLabLoaderPortTypePort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            gov.nih.nci.cagrid.labviewer.stubs.bindings.LabLoaderPortTypeSOAPBindingStub _stub = new gov.nih.nci.cagrid.labviewer.stubs.bindings.LabLoaderPortTypeSOAPBindingStub(portAddress, this);
            _stub.setPortName(getLabLoaderPortTypePortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setLabLoaderPortTypePortEndpointAddress(java.lang.String address) {
        LabLoaderPortTypePort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (gov.nih.nci.cagrid.labviewer.stubs.LabLoaderPortType.class.isAssignableFrom(serviceEndpointInterface)) {
                gov.nih.nci.cagrid.labviewer.stubs.bindings.LabLoaderPortTypeSOAPBindingStub _stub = new gov.nih.nci.cagrid.labviewer.stubs.bindings.LabLoaderPortTypeSOAPBindingStub(new java.net.URL(LabLoaderPortTypePort_address), this);
                _stub.setPortName(getLabLoaderPortTypePortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("LabLoaderPortTypePort".equals(inputPortName)) {
            return getLabLoaderPortTypePort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain.ctlab/service", "LabLoaderService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain.ctlab/service", "LabLoaderPortTypePort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        if ("LabLoaderPortTypePort".equals(portName)) {
            setLabLoaderPortTypePortEndpointAddress(address);
        }
        else { // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
