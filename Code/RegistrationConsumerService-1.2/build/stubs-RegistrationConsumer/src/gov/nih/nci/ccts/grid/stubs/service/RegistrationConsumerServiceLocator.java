/**
 * RegistrationConsumerServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package gov.nih.nci.ccts.grid.stubs.service;

public class RegistrationConsumerServiceLocator extends org.apache.axis.client.Service implements gov.nih.nci.ccts.grid.stubs.service.RegistrationConsumerService {

    public RegistrationConsumerServiceLocator() {
    }


    public RegistrationConsumerServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public RegistrationConsumerServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for RegistrationConsumerPortTypePort
    private java.lang.String RegistrationConsumerPortTypePort_address = "http://localhost:8080/wsrf/services/";

    public java.lang.String getRegistrationConsumerPortTypePortAddress() {
        return RegistrationConsumerPortTypePort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String RegistrationConsumerPortTypePortWSDDServiceName = "RegistrationConsumerPortTypePort";

    public java.lang.String getRegistrationConsumerPortTypePortWSDDServiceName() {
        return RegistrationConsumerPortTypePortWSDDServiceName;
    }

    public void setRegistrationConsumerPortTypePortWSDDServiceName(java.lang.String name) {
        RegistrationConsumerPortTypePortWSDDServiceName = name;
    }

    public gov.nih.nci.ccts.grid.stubs.RegistrationConsumerPortType getRegistrationConsumerPortTypePort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(RegistrationConsumerPortTypePort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getRegistrationConsumerPortTypePort(endpoint);
    }

    public gov.nih.nci.ccts.grid.stubs.RegistrationConsumerPortType getRegistrationConsumerPortTypePort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            gov.nih.nci.ccts.grid.stubs.bindings.RegistrationConsumerPortTypeSOAPBindingStub _stub = new gov.nih.nci.ccts.grid.stubs.bindings.RegistrationConsumerPortTypeSOAPBindingStub(portAddress, this);
            _stub.setPortName(getRegistrationConsumerPortTypePortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setRegistrationConsumerPortTypePortEndpointAddress(java.lang.String address) {
        RegistrationConsumerPortTypePort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (gov.nih.nci.ccts.grid.stubs.RegistrationConsumerPortType.class.isAssignableFrom(serviceEndpointInterface)) {
                gov.nih.nci.ccts.grid.stubs.bindings.RegistrationConsumerPortTypeSOAPBindingStub _stub = new gov.nih.nci.ccts.grid.stubs.bindings.RegistrationConsumerPortTypeSOAPBindingStub(new java.net.URL(RegistrationConsumerPortTypePort_address), this);
                _stub.setPortName(getRegistrationConsumerPortTypePortWSDDServiceName());
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
        if ("RegistrationConsumerPortTypePort".equals(inputPortName)) {
            return getRegistrationConsumerPortTypePort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://grid.ccts.nci.nih.gov/RegistrationConsumer/service", "RegistrationConsumerService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://grid.ccts.nci.nih.gov/RegistrationConsumer/service", "RegistrationConsumerPortTypePort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        if ("RegistrationConsumerPortTypePort".equals(portName)) {
            setRegistrationConsumerPortTypePortEndpointAddress(address);
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
