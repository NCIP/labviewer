
package gov.nih.nci.caxchange.ws.routingandworkflow;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the gov.nih.nci.caxchange.ws.routingandworkflow package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _RoutingAndWorkflowRequest_QNAME = new QName("http://caxchange.nci.nih.gov/ws/routingandworkflow", "RoutingAndWorkflowRequest");
    private final static QName _ErrorDetails_QNAME = new QName("http://caxchange.nci.nih.gov/ws/routingandworkflow", "ErrorDetails");
    private final static QName _Acknowledgement_QNAME = new QName("http://caxchange.nci.nih.gov/ws/routingandworkflow", "Acknowledgement");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: gov.nih.nci.caxchange.ws.routingandworkflow
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link MessageProperties }
     * 
     */
    public MessageProperties createMessageProperties() {
        return new MessageProperties();
    }

    /**
     * Create an instance of {@link Property }
     * 
     */
    public Property createProperty() {
        return new Property();
    }

    /**
     * Create an instance of {@link Credentials }
     * 
     */
    public Credentials createCredentials() {
        return new Credentials();
    }

    /**
     * Create an instance of {@link RoutingAndWorkflowRequest }
     * 
     */
    public RoutingAndWorkflowRequest createRoutingAndWorkflowRequest() {
        return new RoutingAndWorkflowRequest();
    }

    /**
     * Create an instance of {@link Workflow }
     * 
     */
    public Workflow createWorkflow() {
        return new Workflow();
    }

    /**
     * Create an instance of {@link MessagePayload }
     * 
     */
    public MessagePayload createMessagePayload() {
        return new MessagePayload();
    }

    /**
     * Create an instance of {@link PayloadTypes }
     * 
     */
    public PayloadTypes createPayloadTypes() {
        return new PayloadTypes();
    }

    /**
     * Create an instance of {@link Acknowledgement }
     * 
     */
    public Acknowledgement createAcknowledgement() {
        return new Acknowledgement();
    }

    /**
     * Create an instance of {@link ProcessingInstructions }
     * 
     */
    public ProcessingInstructions createProcessingInstructions() {
        return new ProcessingInstructions();
    }

    /**
     * Create an instance of {@link ErrorDetails }
     * 
     */
    public ErrorDetails createErrorDetails() {
        return new ErrorDetails();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RoutingAndWorkflowRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://caxchange.nci.nih.gov/ws/routingandworkflow", name = "RoutingAndWorkflowRequest")
    public JAXBElement<RoutingAndWorkflowRequest> createRoutingAndWorkflowRequest(RoutingAndWorkflowRequest value) {
        return new JAXBElement<RoutingAndWorkflowRequest>(_RoutingAndWorkflowRequest_QNAME, RoutingAndWorkflowRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ErrorDetails }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://caxchange.nci.nih.gov/ws/routingandworkflow", name = "ErrorDetails")
    public JAXBElement<ErrorDetails> createErrorDetails(ErrorDetails value) {
        return new JAXBElement<ErrorDetails>(_ErrorDetails_QNAME, ErrorDetails.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Acknowledgement }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://caxchange.nci.nih.gov/ws/routingandworkflow", name = "Acknowledgement")
    public JAXBElement<Acknowledgement> createAcknowledgement(Acknowledgement value) {
        return new JAXBElement<Acknowledgement>(_Acknowledgement_QNAME, Acknowledgement.class, null, value);
    }

}
