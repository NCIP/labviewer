/**
 * StudyLookupServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package gov.nih.nci.cagrid.labviewer.grid.stubs.service;

public class StudyLookupServiceLocator extends org.apache.axis.client.Service implements gov.nih.nci.cagrid.labviewer.grid.stubs.service.StudyLookupService {

    public StudyLookupServiceLocator() {
    }


    public StudyLookupServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public StudyLookupServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for StudyLookupServicePortTypePort
    private java.lang.String StudyLookupServicePortTypePort_address = "http://localhost:8080/wsrf/services/";

    public java.lang.String getStudyLookupServicePortTypePortAddress() {
        return StudyLookupServicePortTypePort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String StudyLookupServicePortTypePortWSDDServiceName = "StudyLookupServicePortTypePort";

    public java.lang.String getStudyLookupServicePortTypePortWSDDServiceName() {
        return StudyLookupServicePortTypePortWSDDServiceName;
    }

    public void setStudyLookupServicePortTypePortWSDDServiceName(java.lang.String name) {
        StudyLookupServicePortTypePortWSDDServiceName = name;
    }

    public gov.nih.nci.cagrid.labviewer.grid.stubs.StudyLookupServicePortType getStudyLookupServicePortTypePort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(StudyLookupServicePortTypePort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getStudyLookupServicePortTypePort(endpoint);
    }

    public gov.nih.nci.cagrid.labviewer.grid.stubs.StudyLookupServicePortType getStudyLookupServicePortTypePort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            gov.nih.nci.cagrid.labviewer.grid.stubs.bindings.StudyLookupServicePortTypeSOAPBindingStub _stub = new gov.nih.nci.cagrid.labviewer.grid.stubs.bindings.StudyLookupServicePortTypeSOAPBindingStub(portAddress, this);
            _stub.setPortName(getStudyLookupServicePortTypePortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setStudyLookupServicePortTypePortEndpointAddress(java.lang.String address) {
        StudyLookupServicePortTypePort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (gov.nih.nci.cagrid.labviewer.grid.stubs.StudyLookupServicePortType.class.isAssignableFrom(serviceEndpointInterface)) {
                gov.nih.nci.cagrid.labviewer.grid.stubs.bindings.StudyLookupServicePortTypeSOAPBindingStub _stub = new gov.nih.nci.cagrid.labviewer.grid.stubs.bindings.StudyLookupServicePortTypeSOAPBindingStub(new java.net.URL(StudyLookupServicePortTypePort_address), this);
                _stub.setPortName(getStudyLookupServicePortTypePortWSDDServiceName());
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
        if ("StudyLookupServicePortTypePort".equals(inputPortName)) {
            return getStudyLookupServicePortTypePort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://grid.labviewer.cagrid.nci.nih.gov/StudyLookupService/service", "StudyLookupService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://grid.labviewer.cagrid.nci.nih.gov/StudyLookupService/service", "StudyLookupServicePortTypePort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        if ("StudyLookupServicePortTypePort".equals(portName)) {
            setStudyLookupServicePortTypePortEndpointAddress(address);
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
