
package gov.nih.nci.caxchange.ws.routingandworkflow;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;


/**
 * <p>Java class for KnownTypes.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="KnownTypes">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Lombardi_Cancer_Center_CSV"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlEnum
public enum KnownTypes {

    @XmlEnumValue("Lombardi_Cancer_Center_CSV")
    LOMBARDI_CANCER_CENTER_CSV("Lombardi_Cancer_Center_CSV");
    private final String value;

    KnownTypes(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static KnownTypes fromValue(String v) {
        for (KnownTypes c: KnownTypes.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v.toString());
    }

}
