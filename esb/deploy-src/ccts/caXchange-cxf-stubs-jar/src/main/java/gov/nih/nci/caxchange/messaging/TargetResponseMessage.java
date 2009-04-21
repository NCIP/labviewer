
package gov.nih.nci.caxchange.messaging;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for targetResponseMessage complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="targetResponseMessage">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="targetServiceIdentifier" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="targetServiceOperation" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="targetMessageStatus" type="{http://caXchange.nci.nih.gov/messaging}messageStatuses"/>
 *         &lt;choice>
 *           &lt;element name="targetError" type="{http://caXchange.nci.nih.gov/messaging}errorDetails"/>
 *           &lt;element name="targetBusinessMessage" type="{http://caXchange.nci.nih.gov/messaging}messagePayload"/>
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
@XmlType(name = "targetResponseMessage", propOrder = {
    "targetServiceIdentifier",
    "targetServiceOperation",
    "targetMessageStatus",
    "targetError",
    "targetBusinessMessage"
})
public class TargetResponseMessage {

    @XmlElement(required = true)
    protected String targetServiceIdentifier;
    @XmlElement(required = true)
    protected String targetServiceOperation;
    @XmlElement(required = true)
    protected MessageStatuses targetMessageStatus;
    protected ErrorDetails targetError;
    protected MessagePayload targetBusinessMessage;

    /**
     * Gets the value of the targetServiceIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTargetServiceIdentifier() {
        return targetServiceIdentifier;
    }

    /**
     * Sets the value of the targetServiceIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTargetServiceIdentifier(String value) {
        this.targetServiceIdentifier = value;
    }

    /**
     * Gets the value of the targetServiceOperation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTargetServiceOperation() {
        return targetServiceOperation;
    }

    /**
     * Sets the value of the targetServiceOperation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTargetServiceOperation(String value) {
        this.targetServiceOperation = value;
    }

    /**
     * Gets the value of the targetMessageStatus property.
     * 
     * @return
     *     possible object is
     *     {@link MessageStatuses }
     *     
     */
    public MessageStatuses getTargetMessageStatus() {
        return targetMessageStatus;
    }

    /**
     * Sets the value of the targetMessageStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link MessageStatuses }
     *     
     */
    public void setTargetMessageStatus(MessageStatuses value) {
        this.targetMessageStatus = value;
    }

    /**
     * Gets the value of the targetError property.
     * 
     * @return
     *     possible object is
     *     {@link ErrorDetails }
     *     
     */
    public ErrorDetails getTargetError() {
        return targetError;
    }

    /**
     * Sets the value of the targetError property.
     * 
     * @param value
     *     allowed object is
     *     {@link ErrorDetails }
     *     
     */
    public void setTargetError(ErrorDetails value) {
        this.targetError = value;
    }

    /**
     * Gets the value of the targetBusinessMessage property.
     * 
     * @return
     *     possible object is
     *     {@link MessagePayload }
     *     
     */
    public MessagePayload getTargetBusinessMessage() {
        return targetBusinessMessage;
    }

    /**
     * Sets the value of the targetBusinessMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link MessagePayload }
     *     
     */
    public void setTargetBusinessMessage(MessagePayload value) {
        this.targetBusinessMessage = value;
    }

}
