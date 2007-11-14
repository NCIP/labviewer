/**
 * CaXchangeResponseServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package gov.nih.nci.cagrid.caxchange.context.stubs.service;

public class CaXchangeResponseServiceLocator extends org.apache.axis.client.Service implements gov.nih.nci.cagrid.caxchange.context.stubs.service.CaXchangeResponseService {

    public CaXchangeResponseServiceLocator() {
    }


    public CaXchangeResponseServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public CaXchangeResponseServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for CaXchangeResponseServicePortTypePort
    private java.lang.String CaXchangeResponseServicePortTypePort_address = "http://localhost:8080/wsrf/services/";

    public java.lang.String getCaXchangeResponseServicePortTypePortAddress() {
        return CaXchangeResponseServicePortTypePort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String CaXchangeResponseServicePortTypePortWSDDServiceName = "CaXchangeResponseServicePortTypePort";

    public java.lang.String getCaXchangeResponseServicePortTypePortWSDDServiceName() {
        return CaXchangeResponseServicePortTypePortWSDDServiceName;
    }

    public void setCaXchangeResponseServicePortTypePortWSDDServiceName(java.lang.String name) {
        CaXchangeResponseServicePortTypePortWSDDServiceName = name;
    }

    public gov.nih.nci.cagrid.caxchange.context.stubs.CaXchangeResponseServicePortType getCaXchangeResponseServicePortTypePort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(CaXchangeResponseServicePortTypePort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getCaXchangeResponseServicePortTypePort(endpoint);
    }

    public gov.nih.nci.cagrid.caxchange.context.stubs.CaXchangeResponseServicePortType getCaXchangeResponseServicePortTypePort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            gov.nih.nci.cagrid.caxchange.context.stubs.bindings.CaXchangeResponseServicePortTypeSOAPBindingStub _stub = new gov.nih.nci.cagrid.caxchange.context.stubs.bindings.CaXchangeResponseServicePortTypeSOAPBindingStub(portAddress, this);
            _stub.setPortName(getCaXchangeResponseServicePortTypePortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setCaXchangeResponseServicePortTypePortEndpointAddress(java.lang.String address) {
        CaXchangeResponseServicePortTypePort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (gov.nih.nci.cagrid.caxchange.context.stubs.CaXchangeResponseServicePortType.class.isAssignableFrom(serviceEndpointInterface)) {
                gov.nih.nci.cagrid.caxchange.context.stubs.bindings.CaXchangeResponseServicePortTypeSOAPBindingStub _stub = new gov.nih.nci.cagrid.caxchange.context.stubs.bindings.CaXchangeResponseServicePortTypeSOAPBindingStub(new java.net.URL(CaXchangeResponseServicePortTypePort_address), this);
                _stub.setPortName(getCaXchangeResponseServicePortTypePortWSDDServiceName());
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
        if ("CaXchangeResponseServicePortTypePort".equals(inputPortName)) {
            return getCaXchangeResponseServicePortTypePort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://caxchange.cagrid.nci.nih.gov/CaXchangeRequestProcessor/Context/service", "CaXchangeResponseService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://caxchange.cagrid.nci.nih.gov/CaXchangeRequestProcessor/Context/service", "CaXchangeResponseServicePortTypePort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        if ("CaXchangeResponseServicePortTypePort".equals(portName)) {
            setCaXchangeResponseServicePortTypePortEndpointAddress(address);
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
