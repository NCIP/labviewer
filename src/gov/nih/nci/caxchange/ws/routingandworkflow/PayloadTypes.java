
package gov.nih.nci.caxchange.ws.routingandworkflow;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PayloadTypes complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PayloadTypes">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="GenericType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="KnownType" type="{http://caxchange.nci.nih.gov/ws/routingandworkflow}KnownTypes"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PayloadTypes", propOrder = {
    "genericType",
    "knownType"
})
public class PayloadTypes {

    @XmlElement(name = "GenericType")
    protected String genericType;
    @XmlElement(name = "KnownType")
    protected KnownTypes knownType;

    /**
     * Gets the value of the genericType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGenericType() {
        return genericType;
    }

    /**
     * Sets the value of the genericType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGenericType(String value) {
        this.genericType = value;
    }

    /**
     * Gets the value of the knownType property.
     * 
     * @return
     *     possible object is
     *     {@link KnownTypes }
     *     
     */
    public KnownTypes getKnownType() {
        return knownType;
    }

    /**
     * Sets the value of the knownType property.
     * 
     * @param value
     *     allowed object is
     *     {@link KnownTypes }
     *     
     */
    public void setKnownType(KnownTypes value) {
        this.knownType = value;
    }

}
