/**
 * StudyConsumerServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package gov.nih.nci.ccts.grid.stubs.service;

public class StudyConsumerServiceLocator extends org.apache.axis.client.Service implements gov.nih.nci.ccts.grid.stubs.service.StudyConsumerService {

    public StudyConsumerServiceLocator() {
    }


    public StudyConsumerServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public StudyConsumerServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for StudyConsumerPortTypePort
    private java.lang.String StudyConsumerPortTypePort_address = "http://localhost:8080/wsrf/services/";

    public java.lang.String getStudyConsumerPortTypePortAddress() {
        return StudyConsumerPortTypePort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String StudyConsumerPortTypePortWSDDServiceName = "StudyConsumerPortTypePort";

    public java.lang.String getStudyConsumerPortTypePortWSDDServiceName() {
        return StudyConsumerPortTypePortWSDDServiceName;
    }

    public void setStudyConsumerPortTypePortWSDDServiceName(java.lang.String name) {
        StudyConsumerPortTypePortWSDDServiceName = name;
    }

    public gov.nih.nci.ccts.grid.stubs.StudyConsumerPortType getStudyConsumerPortTypePort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(StudyConsumerPortTypePort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getStudyConsumerPortTypePort(endpoint);
    }

    public gov.nih.nci.ccts.grid.stubs.StudyConsumerPortType getStudyConsumerPortTypePort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            gov.nih.nci.ccts.grid.stubs.bindings.StudyConsumerPortTypeSOAPBindingStub _stub = new gov.nih.nci.ccts.grid.stubs.bindings.StudyConsumerPortTypeSOAPBindingStub(portAddress, this);
            _stub.setPortName(getStudyConsumerPortTypePortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setStudyConsumerPortTypePortEndpointAddress(java.lang.String address) {
        StudyConsumerPortTypePort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (gov.nih.nci.ccts.grid.stubs.StudyConsumerPortType.class.isAssignableFrom(serviceEndpointInterface)) {
                gov.nih.nci.ccts.grid.stubs.bindings.StudyConsumerPortTypeSOAPBindingStub _stub = new gov.nih.nci.ccts.grid.stubs.bindings.StudyConsumerPortTypeSOAPBindingStub(new java.net.URL(StudyConsumerPortTypePort_address), this);
                _stub.setPortName(getStudyConsumerPortTypePortWSDDServiceName());
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
        if ("StudyConsumerPortTypePort".equals(inputPortName)) {
            return getStudyConsumerPortTypePort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://grid.ccts.nci.nih.gov/StudyConsumer/service", "StudyConsumerService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://grid.ccts.nci.nih.gov/StudyConsumer/service", "StudyConsumerPortTypePort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        if ("StudyConsumerPortTypePort".equals(portName)) {
            setStudyConsumerPortTypePortEndpointAddress(address);
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
