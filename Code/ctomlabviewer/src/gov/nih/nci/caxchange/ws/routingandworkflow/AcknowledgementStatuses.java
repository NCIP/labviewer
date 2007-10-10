
package gov.nih.nci.caxchange.ws.routingandworkflow;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;


/**
 * <p>Java class for AcknowledgementStatuses.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="AcknowledgementStatuses">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="MessageReceived"/>
 *     &lt;enumeration value="ErrorOccurred"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlEnum
public enum AcknowledgementStatuses {

    @XmlEnumValue("ErrorOccurred")
    ERROR_OCCURRED("ErrorOccurred"),
    @XmlEnumValue("MessageReceived")
    MESSAGE_RECEIVED("MessageReceived");
    private final String value;

    AcknowledgementStatuses(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static AcknowledgementStatuses fromValue(String v) {
        for (AcknowledgementStatuses c: AcknowledgementStatuses.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v.toString());
    }

}
