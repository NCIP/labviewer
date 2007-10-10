
package gov.nih.nci.caxchange.ws.routingandworkflow;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MessagePayload complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MessagePayload">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="FormatDefinition" type="{http://www.w3.org/2001/XMLSchema}anyURI" minOccurs="0"/>
 *         &lt;element name="PayloadType" type="{http://caxchange.nci.nih.gov/ws/routingandworkflow}PayloadTypes"/>
 *         &lt;choice>
 *           &lt;element name="Text" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *           &lt;element name="Object" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
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
@XmlType(name = "MessagePayload", propOrder = {
    "formatDefinition",
    "payloadType",
    "text",
    "object"
})
public class MessagePayload {

    @XmlElement(name = "FormatDefinition")
    protected String formatDefinition;
    @XmlElement(name = "PayloadType", required = true)
    protected PayloadTypes payloadType;
    @XmlElement(name = "Text")
    protected String text;
    @XmlElement(name = "Object")
    protected byte[] object;

    /**
     * Gets the value of the formatDefinition property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFormatDefinition() {
        return formatDefinition;
    }

    /**
     * Sets the value of the formatDefinition property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFormatDefinition(String value) {
        this.formatDefinition = value;
    }

    /**
     * Gets the value of the payloadType property.
     * 
     * @return
     *     possible object is
     *     {@link PayloadTypes }
     *     
     */
    public PayloadTypes getPayloadType() {
        return payloadType;
    }

    /**
     * Sets the value of the payloadType property.
     * 
     * @param value
     *     allowed object is
     *     {@link PayloadTypes }
     *     
     */
    public void setPayloadType(PayloadTypes value) {
        this.payloadType = value;
    }

    /**
     * Gets the value of the text property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the value of the text property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setText(String value) {
        this.text = value;
    }

    /**
     * Gets the value of the object property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getObject() {
        return object;
    }

    /**
     * Sets the value of the object property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setObject(byte[] value) {
        this.object = ((byte[]) value);
    }

}
