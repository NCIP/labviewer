
package gov.nih.nci.ctom.ws.datapersistence;

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
 *         &lt;element name="Status" type="{http://ctom.nci.nih.gov/ws/datapersistence}AcknowledgementStatuses"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "description"
})
public class Acknowledgement {

    @XmlElement(name = "Status", required = true)
    protected AcknowledgementStatuses status;
    @XmlElement(name = "Description", required = true)
    protected String description;

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
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

}
