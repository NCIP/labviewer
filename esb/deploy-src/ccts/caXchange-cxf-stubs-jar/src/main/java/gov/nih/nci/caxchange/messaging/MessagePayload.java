
package gov.nih.nci.caxchange.messaging;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import org.w3c.dom.Element;


/**
 * <p>Java class for messagePayload complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="messagePayload">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="xmlSchemaDefinition" type="{http://www.w3.org/2001/XMLSchema}anyURI"/>
 *         &lt;any/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "messagePayload", propOrder = {
    "xmlSchemaDefinition",
    "any"
})
public class MessagePayload {

    @XmlElement(required = true)
    @XmlSchemaType(name = "anyURI")
    protected String xmlSchemaDefinition;
    @XmlAnyElement(lax = true)
    protected Object any;

    /**
     * Gets the value of the xmlSchemaDefinition property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXmlSchemaDefinition() {
        return xmlSchemaDefinition;
    }

    /**
     * Sets the value of the xmlSchemaDefinition property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXmlSchemaDefinition(String value) {
        this.xmlSchemaDefinition = value;
    }

    /**
     * Gets the value of the any property.
     * 
     * @return
     *     possible object is
     *     {@link Element }
     *     {@link Object }
     *     
     */
    public Object getAny() {
        return any;
    }

    /**
     * Sets the value of the any property.
     * 
     * @param value
     *     allowed object is
     *     {@link Element }
     *     {@link Object }
     *     
     */
    public void setAny(Object value) {
        this.any = value;
    }

}
