
package gov.nih.nci.caxchange.ws.routingandworkflow;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RoutingAndWorkflowRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RoutingAndWorkflowRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ExternalIdentifier" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Credentials" type="{http://caxchange.nci.nih.gov/ws/routingandworkflow}Credentials"/>
 *         &lt;element name="ProcessingInstructions" type="{http://caxchange.nci.nih.gov/ws/routingandworkflow}ProcessingInstructions"/>
 *         &lt;element name="Payload" type="{http://caxchange.nci.nih.gov/ws/routingandworkflow}MessagePayload"/>
 *         &lt;element name="Properties" type="{http://caxchange.nci.nih.gov/ws/routingandworkflow}MessageProperties"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RoutingAndWorkflowRequest", propOrder = {
    "externalIdentifier",
    "credentials",
    "processingInstructions",
    "payload",
    "properties"
})
public class RoutingAndWorkflowRequest {

    @XmlElement(name = "ExternalIdentifier", required = true)
    protected String externalIdentifier;
    @XmlElement(name = "Credentials", required = true)
    protected Credentials credentials;
    @XmlElement(name = "ProcessingInstructions", required = true)
    protected ProcessingInstructions processingInstructions;
    @XmlElement(name = "Payload", required = true)
    protected MessagePayload payload;
    @XmlElement(name = "Properties", required = true)
    protected MessageProperties properties;

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

    /**
     * Gets the value of the processingInstructions property.
     * 
     * @return
     *     possible object is
     *     {@link ProcessingInstructions }
     *     
     */
    public ProcessingInstructions getProcessingInstructions() {
        return processingInstructions;
    }

    /**
     * Sets the value of the processingInstructions property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProcessingInstructions }
     *     
     */
    public void setProcessingInstructions(ProcessingInstructions value) {
        this.processingInstructions = value;
    }

    /**
     * Gets the value of the payload property.
     * 
     * @return
     *     possible object is
     *     {@link MessagePayload }
     *     
     */
    public MessagePayload getPayload() {
        return payload;
    }

    /**
     * Sets the value of the payload property.
     * 
     * @param value
     *     allowed object is
     *     {@link MessagePayload }
     *     
     */
    public void setPayload(MessagePayload value) {
        this.payload = value;
    }

    /**
     * Gets the value of the properties property.
     * 
     * @return
     *     possible object is
     *     {@link MessageProperties }
     *     
     */
    public MessageProperties getProperties() {
        return properties;
    }

    /**
     * Sets the value of the properties property.
     * 
     * @param value
     *     allowed object is
     *     {@link MessageProperties }
     *     
     */
    public void setProperties(MessageProperties value) {
        this.properties = value;
    }

}
