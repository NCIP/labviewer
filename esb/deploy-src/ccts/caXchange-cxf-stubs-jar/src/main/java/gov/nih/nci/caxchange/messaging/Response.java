
package gov.nih.nci.caxchange.messaging;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for response complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="response">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="responseStatus" type="{http://caXchange.nci.nih.gov/messaging}statuses"/>
 *         &lt;choice>
 *           &lt;element name="caXchangeError" type="{http://caXchange.nci.nih.gov/messaging}errorDetails"/>
 *           &lt;element name="targetResponse" type="{http://caXchange.nci.nih.gov/messaging}targetResponseMessage" maxOccurs="unbounded"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "response", propOrder = {
    "responseStatus",
    "caXchangeError",
    "targetResponse"
})
public class Response {

    @XmlElement(required = true)
    protected Statuses responseStatus;
    protected ErrorDetails caXchangeError;
    protected List<TargetResponseMessage> targetResponse;

    /**
     * Gets the value of the responseStatus property.
     * 
     * @return
     *     possible object is
     *     {@link Statuses }
     *     
     */
    public Statuses getResponseStatus() {
        return responseStatus;
    }

    /**
     * Sets the value of the responseStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link Statuses }
     *     
     */
    public void setResponseStatus(Statuses value) {
        this.responseStatus = value;
    }

    /**
     * Gets the value of the caXchangeError property.
     * 
     * @return
     *     possible object is
     *     {@link ErrorDetails }
     *     
     */
    public ErrorDetails getCaXchangeError() {
        return caXchangeError;
    }

    /**
     * Sets the value of the caXchangeError property.
     * 
     * @param value
     *     allowed object is
     *     {@link ErrorDetails }
     *     
     */
    public void setCaXchangeError(ErrorDetails value) {
        this.caXchangeError = value;
    }

    /**
     * Gets the value of the targetResponse property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the targetResponse property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTargetResponse().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TargetResponseMessage }
     * 
     * 
     */
    public List<TargetResponseMessage> getTargetResponse() {
        if (targetResponse == null) {
            targetResponse = new ArrayList<TargetResponseMessage>();
        }
        return this.targetResponse;
    }

}
