
package gov.nih.nci.caxchange.messaging;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for messageStatuses.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="messageStatuses">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="RESPONSE"/>
 *     &lt;enumeration value="ERROR"/>
 *     &lt;enumeration value="FAULT"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "messageStatuses")
@XmlEnum
public enum MessageStatuses {

    RESPONSE,
    ERROR,
    FAULT;

    public String value() {
        return name();
    }

    public static MessageStatuses fromValue(String v) {
        return valueOf(v);
    }

}
