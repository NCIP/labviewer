
package gov.nih.nci.caxchange.messaging;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for messageTypes.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="messageTypes">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="STUDY_CREATION"/>
 *     &lt;enumeration value="REGISTER_SUBJECT"/>
 *     &lt;enumeration value="CT_LAB_DATA"/>
 *     &lt;enumeration value="LOAD_LAB_TO_CDMS"/>
 *     &lt;enumeration value="LAB_BASED_AE"/>
 *     &lt;enumeration value="SCHEDULE_MODIFICATION"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "messageTypes")
@XmlEnum
public enum MessageTypes {

    STUDY_CREATION,
    REGISTER_SUBJECT,
    CT_LAB_DATA,
    LOAD_LAB_TO_CDMS,
    LAB_BASED_AE,
    SCHEDULE_MODIFICATION;

    public String value() {
        return name();
    }

    public static MessageTypes fromValue(String v) {
        return valueOf(v);
    }

}
