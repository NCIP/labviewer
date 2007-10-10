
package gov.nih.nci.caxchange.ws;

import java.net.MalformedURLException;
import java.util.Collection;
import java.util.HashMap;
import javax.xml.namespace.QName;
import org.codehaus.xfire.XFireRuntimeException;
import org.codehaus.xfire.aegis.AegisBindingProvider;
import org.codehaus.xfire.annotations.AnnotationServiceFactory;
import org.codehaus.xfire.annotations.jsr181.Jsr181WebAnnotations;
import org.codehaus.xfire.client.XFireProxyFactory;
import org.codehaus.xfire.jaxb2.JaxbTypeRegistry;
import org.codehaus.xfire.service.Endpoint;
import org.codehaus.xfire.service.Service;
import org.codehaus.xfire.soap.AbstractSoapBinding;
import org.codehaus.xfire.transport.TransportManager;

public class RoutingAndWorkFlowServiceClient {

    private static XFireProxyFactory proxyFactory = new XFireProxyFactory();
    private HashMap endpoints = new HashMap();
    private Service service0;

    public RoutingAndWorkFlowServiceClient() {
        create0();
        Endpoint RoutingAndWorkflowServiceLocalEndpointEP = service0 .addEndpoint(new QName("http://caxchange.nci.nih.gov/ws", "RoutingAndWorkflowServiceLocalEndpoint"), new QName("http://caxchange.nci.nih.gov/ws", "RoutingAndWorkflowServiceLocalBinding"), "xfire.local://RoutingAndWorkFlowService");
        endpoints.put(new QName("http://caxchange.nci.nih.gov/ws", "RoutingAndWorkflowServiceLocalEndpoint"), RoutingAndWorkflowServiceLocalEndpointEP);
        Endpoint RoutingAndWorkflowServiceSoapPortEP = service0 .addEndpoint(new QName("http://caxchange.nci.nih.gov/ws", "RoutingAndWorkflowServiceSoapPort"), new QName("http://caxchange.nci.nih.gov/ws", "NewBinding"), "http://localhost:8080/caxchange/routingandworkflow");
        endpoints.put(new QName("http://caxchange.nci.nih.gov/ws", "RoutingAndWorkflowServiceSoapPort"), RoutingAndWorkflowServiceSoapPortEP);
    }

    public Object getEndpoint(Endpoint endpoint) {
        try {
            return proxyFactory.create((endpoint).getBinding(), (endpoint).getUrl());
        } catch (MalformedURLException e) {
            throw new XFireRuntimeException("Invalid URL", e);
        }
    }

    public Object getEndpoint(QName name) {
        Endpoint endpoint = ((Endpoint) endpoints.get((name)));
        if ((endpoint) == null) {
            throw new IllegalStateException("No such endpoint!");
        }
        return getEndpoint((endpoint));
    }

    public Collection getEndpoints() {
        return endpoints.values();
    }

    private void create0() {
        TransportManager tm = (org.codehaus.xfire.XFireFactory.newInstance().getXFire().getTransportManager());
        AnnotationServiceFactory asf = new AnnotationServiceFactory(new Jsr181WebAnnotations(), tm, new AegisBindingProvider(new JaxbTypeRegistry()));
        asf.setBindingCreationEnabled(true);
        service0 = asf.create((gov.nih.nci.caxchange.ws.RoutingAndWorkFlowServiceImpl.class));
        {
            AbstractSoapBinding soapBinding = asf.createSoap11Binding(service0, new QName("http://caxchange.nci.nih.gov/ws", "RoutingAndWorkflowServiceLocalBinding"), "urn:xfire:transport:local");
        }
        {
            AbstractSoapBinding soapBinding = asf.createSoap11Binding(service0, new QName("http://caxchange.nci.nih.gov/ws", "NewBinding"), "http://schemas.xmlsoap.org/soap/http");
        }
    }

    public RoutingAndWorkflowService getRoutingAndWorkflowServiceLocalEndpoint() {
        return ((RoutingAndWorkflowService)(this).getEndpoint(new QName("http://caxchange.nci.nih.gov/ws", "RoutingAndWorkflowServiceLocalEndpoint")));
    }

    public RoutingAndWorkflowService getRoutingAndWorkflowServiceLocalEndpoint(String url) {
        RoutingAndWorkflowService var = getRoutingAndWorkflowServiceLocalEndpoint();
        org.codehaus.xfire.client.Client.getInstance(var).setUrl(url);
        return var;
    }

    public RoutingAndWorkflowService getRoutingAndWorkflowServiceSoapPort() {
        return ((RoutingAndWorkflowService)(this).getEndpoint(new QName("http://caxchange.nci.nih.gov/ws", "RoutingAndWorkflowServiceSoapPort")));
    }

    public RoutingAndWorkflowService getRoutingAndWorkflowServiceSoapPort(String url) {
        RoutingAndWorkflowService var = getRoutingAndWorkflowServiceSoapPort();
        org.codehaus.xfire.client.Client.getInstance(var).setUrl(url);
        return var;
    }

}
