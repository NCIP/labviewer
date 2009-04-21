
package gov.nih.nci.caxchange.messaging;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for metadata complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="metadata">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="transactionControl" type="{http://caXchange.nci.nih.gov/messaging}transactionControls"/>
 *         &lt;element name="serviceType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="operationName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="externalIdentifier" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="caXchangeIdentifier" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="credentials" type="{http://caXchange.nci.nih.gov/messaging}credentials" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "metadata", propOrder = {
    "transactionControl",
    "serviceType",
    "operationName",
    "externalIdentifier",
    "caXchangeIdentifier",
    "credentials"
})
public class Metadata {

    @XmlElement(required = true)
    protected TransactionControls transactionControl;
    @XmlElement(required = true)
    protected String serviceType;
    @XmlElementRef(name = "operationName", namespace = "http://caXchange.nci.nih.gov/messaging", type = JAXBElement.class)
    protected JAXBElement<String> operationName;
    @XmlElement(required = true)
    protected String externalIdentifier;
    protected String caXchangeIdentifier;
    protected Credentials credentials;

    /**
     * Gets the value of the transactionControl property.
     * 
     * @return
     *     possible object is
     *     {@link TransactionControls }
     *     
     */
    public TransactionControls getTransactionControl() {
        return transactionControl;
    }

    /**
     * Sets the value of the transactionControl property.
     * 
     * @param value
     *     allowed object is
     *     {@link TransactionControls }
     *     
     */
    public void setTransactionControl(TransactionControls value) {
        this.transactionControl = value;
    }

    /**
     * Gets the value of the serviceType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServiceType() {
        return serviceType;
    }

    /**
     * Sets the value of the serviceType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServiceType(String value) {
        this.serviceType = value;
    }

    /**
     * Gets the value of the operationName property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getOperationName() {
        return operationName;
    }

    /**
     * Sets the value of the operationName property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setOperationName(JAXBElement<String> value) {
        this.operationName = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the externalIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExternalIdentifier() {
        return externalIdentifier;
    }

    /**
     * Sets the value of the externalIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExternalIdentifier(String value) {
        this.externalIdentifier = value;
    }

    /**
     * Gets the value of the caXchangeIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCaXchangeIdentifier() {
        return caXchangeIdentifier;
    }

    /**
     * Sets the value of the caXchangeIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCaXchangeIdentifier(String value) {
        this.caXchangeIdentifier = value;
    }

    /**
     * Gets the value of the credentials property.
     * 
     * @return
     *     possible object is
     *     {@link Credentials }
     *     
     */
    public Credentials getCredentials() {
        return credentials;
    }

    /**
     * Sets the value of the credentials property.
     * 
     * @param value
     *     allowed object is
     *     {@link Credentials }
     *     
     */
    public void setCredentials(Credentials value) {
        this.credentials = value;
    }

}
