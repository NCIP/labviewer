
package gov.nih.nci.caxchange.messaging;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for mtMetadata complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="mtMetadata">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="messageType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="payloadUri" type="{http://www.w3.org/2001/XMLSchema}anyURI"/>
 *         &lt;element name="reliableTransactions" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "mtMetadata", propOrder = {
    "messageType",
    "payloadUri",
    "reliableTransactions"
})
public class MtMetadata {

    @XmlElement(required = true)
    protected String messageType;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "anyURI")
    protected String payloadUri;
    protected boolean reliableTransactions;

    /**
     * Gets the value of the messageType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessageType() {
        return messageType;
    }

    /**
     * Sets the value of the messageType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessageType(String value) {
        this.messageType = value;
    }

    /**
     * Gets the value of the payloadUri property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPayloadUri() {
        return payloadUri;
    }

    /**
     * Sets the value of the payloadUri property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPayloadUri(String value) {
        this.payloadUri = value;
    }

    /**
     * Gets the value of the reliableTransactions property.
     * 
     */
    public boolean isReliableTransactions() {
        return reliableTransactions;
    }

    /**
     * Sets the value of the reliableTransactions property.
     * 
     */
    public void setReliableTransactions(boolean value) {
        this.reliableTransactions = value;
    }

}
