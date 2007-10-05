
package gov.nih.nci.ctom.ws.datapersistence;

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
 *     &lt;enumeration value="MessagePersisted"/>
 *     &lt;enumeration value="ApplicationError"/>
 *     &lt;enumeration value="AuthorizationFailure"/>
 *     &lt;enumeration value="AuthenticationFailure"/>
 *     &lt;enumeration value="UnSupportedMessageFormatError"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlEnum
public enum AcknowledgementStatuses {

    @XmlEnumValue("ApplicationError")
    APPLICATION_ERROR("ApplicationError"),
    @XmlEnumValue("AuthenticationFailure")
    AUTHENTICATION_FAILURE("AuthenticationFailure"),
    @XmlEnumValue("AuthorizationFailure")
    AUTHORIZATION_FAILURE("AuthorizationFailure"),
    @XmlEnumValue("MessagePersisted")
    MESSAGE_PERSISTED("MessagePersisted"),
    @XmlEnumValue("UnSupportedMessageFormatError")
    UN_SUPPORTED_MESSAGE_FORMAT_ERROR("UnSupportedMessageFormatError");
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
