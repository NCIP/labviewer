
package gov.nih.nci.ctom.ws.datapersistence;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the gov.nih.nci.ctom.ws.datapersistence package. 
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

    private final static QName _Acknowledgement_QNAME = new QName("http://ctom.nci.nih.gov/ws/datapersistence", "Acknowledgement");
    private final static QName _ErrorDetails_QNAME = new QName("http://ctom.nci.nih.gov/ws/datapersistence", "ErrorDetails");
    private final static QName _CTOMPersistDataRequest_QNAME = new QName("http://ctom.nci.nih.gov/ws/datapersistence", "CTOMPersistDataRequest");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: gov.nih.nci.ctom.ws.datapersistence
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Acknowledgement }
     * 
     */
    public Acknowledgement createAcknowledgement() {
        return new Acknowledgement();
    }

    /**
     * Create an instance of {@link ErrorDetails }
     * 
     */
    public ErrorDetails createErrorDetails() {
        return new ErrorDetails();
    }

    /**
     * Create an instance of {@link PersistDataRequest }
     * 
     */
    public PersistDataRequest createPersistDataRequest() {
        return new PersistDataRequest();
    }

    /**
     * Create an instance of {@link Message }
     * 
     */
    public Message createMessage() {
        return new Message();
    }

    /**
     * Create an instance of {@link Credentials }
     * 
     */
    public Credentials createCredentials() {
        return new Credentials();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Acknowledgement }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ctom.nci.nih.gov/ws/datapersistence", name = "Acknowledgement")
    public JAXBElement<Acknowledgement> createAcknowledgement(Acknowledgement value) {
        return new JAXBElement<Acknowledgement>(_Acknowledgement_QNAME, Acknowledgement.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ErrorDetails }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ctom.nci.nih.gov/ws/datapersistence", name = "ErrorDetails")
    public JAXBElement<ErrorDetails> createErrorDetails(ErrorDetails value) {
        return new JAXBElement<ErrorDetails>(_ErrorDetails_QNAME, ErrorDetails.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PersistDataRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ctom.nci.nih.gov/ws/datapersistence", name = "CTOMPersistDataRequest")
    public JAXBElement<PersistDataRequest> createCTOMPersistDataRequest(PersistDataRequest value) {
        return new JAXBElement<PersistDataRequest>(_CTOMPersistDataRequest_QNAME, PersistDataRequest.class, null, value);
    }

}
