
package gov.nih.nci.caxchange.messaging;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the gov.nih.nci.caxchange.messaging package. 
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

    private final static QName _MetadataOperationName_QNAME = new QName("http://caXchange.nci.nih.gov/messaging", "operationName");
    private final static QName _CaXchangeRoutingMetadata_QNAME = new QName("http://caXchange.nci.nih.gov/messaging", "caXchangeRoutingMetadata");
    private final static QName _CaXchangeRequestMessage_QNAME = new QName("http://caXchange.nci.nih.gov/messaging", "caXchangeRequestMessage");
    private final static QName _CaXchangeResponseMessage_QNAME = new QName("http://caXchange.nci.nih.gov/messaging", "caXchangeResponseMessage");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: gov.nih.nci.caxchange.messaging
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Credentials }
     * 
     */
    public Credentials createCredentials() {
        return new Credentials();
    }

    /**
     * Create an instance of {@link ErrorDetails }
     * 
     */
    public ErrorDetails createErrorDetails() {
        return new ErrorDetails();
    }

    /**
     * Create an instance of {@link Metadata }
     * 
     */
    public Metadata createMetadata() {
        return new Metadata();
    }

    /**
     * Create an instance of {@link ResponseMetadata }
     * 
     */
    public ResponseMetadata createResponseMetadata() {
        return new ResponseMetadata();
    }

    /**
     * Create an instance of {@link TargetResponseMessage }
     * 
     */
    public TargetResponseMessage createTargetResponseMessage() {
        return new TargetResponseMessage();
    }

    /**
     * Create an instance of {@link MessagePayload }
     * 
     */
    public MessagePayload createMessagePayload() {
        return new MessagePayload();
    }

    /**
     * Create an instance of {@link RoutingMetadata }
     * 
     */
    public RoutingMetadata createRoutingMetadata() {
        return new RoutingMetadata();
    }

    /**
     * Create an instance of {@link Request }
     * 
     */
    public Request createRequest() {
        return new Request();
    }

    /**
     * Create an instance of {@link Message }
     * 
     */
    public Message createMessage() {
        return new Message();
    }

    /**
     * Create an instance of {@link Response }
     * 
     */
    public Response createResponse() {
        return new Response();
    }

    /**
     * Create an instance of {@link MtMetadata }
     * 
     */
    public MtMetadata createMtMetadata() {
        return new MtMetadata();
    }

    /**
     * Create an instance of {@link ResponseMessage }
     * 
     */
    public ResponseMessage createResponseMessage() {
        return new ResponseMessage();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://caXchange.nci.nih.gov/messaging", name = "operationName", scope = Metadata.class)
    public JAXBElement<String> createMetadataOperationName(String value) {
        return new JAXBElement<String>(_MetadataOperationName_QNAME, String.class, Metadata.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RoutingMetadata }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://caXchange.nci.nih.gov/messaging", name = "caXchangeRoutingMetadata")
    public JAXBElement<RoutingMetadata> createCaXchangeRoutingMetadata(RoutingMetadata value) {
        return new JAXBElement<RoutingMetadata>(_CaXchangeRoutingMetadata_QNAME, RoutingMetadata.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Message }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://caXchange.nci.nih.gov/messaging", name = "caXchangeRequestMessage")
    public JAXBElement<Message> createCaXchangeRequestMessage(Message value) {
        return new JAXBElement<Message>(_CaXchangeRequestMessage_QNAME, Message.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResponseMessage }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://caXchange.nci.nih.gov/messaging", name = "caXchangeResponseMessage")
    public JAXBElement<ResponseMessage> createCaXchangeResponseMessage(ResponseMessage value) {
        return new JAXBElement<ResponseMessage>(_CaXchangeResponseMessage_QNAME, ResponseMessage.class, null, value);
    }

}
