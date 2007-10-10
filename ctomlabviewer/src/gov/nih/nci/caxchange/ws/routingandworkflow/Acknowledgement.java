
package gov.nih.nci.caxchange.ws.routingandworkflow;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Acknowledgement complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Acknowledgement">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Status" type="{http://caxchange.nci.nih.gov/ws/routingandworkflow}AcknowledgementStatuses"/>
 *         &lt;element name="Error" type="{http://caxchange.nci.nih.gov/ws/routingandworkflow}ErrorDetails" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Acknowledgement", propOrder = {
    "status",
    "error"
})
public class Acknowledgement {

    @XmlElement(name = "Status", required = true)
    protected AcknowledgementStatuses status;
    @XmlElement(name = "Error")
    protected ErrorDetails error;

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link AcknowledgementStatuses }
     *     
     */
    public AcknowledgementStatuses getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link AcknowledgementStatuses }
     *     
     */
    public void setStatus(AcknowledgementStatuses value) {
        this.status = value;
    }

    /**
     * Gets the value of the error property.
     * 
     * @return
     *     possible object is
     *     {@link ErrorDetails }
     *     
     */
    public ErrorDetails getError() {
        return error;
    }

    /**
     * Sets the value of the error property.
     * 
     * @param value
     *     allowed object is
     *     {@link ErrorDetails }
     *     
     */
    public void setError(ErrorDetails value) {
        this.error = value;
    }

}
