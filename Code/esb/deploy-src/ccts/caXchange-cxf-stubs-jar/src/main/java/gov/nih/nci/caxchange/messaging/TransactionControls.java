
package gov.nih.nci.caxchange.messaging;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for transactionControls.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="transactionControls">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="PROCESS"/>
 *     &lt;enumeration value="COMMIT"/>
 *     &lt;enumeration value="ROLLBACK"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "transactionControls")
@XmlEnum
public enum TransactionControls {

    PROCESS,
    COMMIT,
    ROLLBACK;

    public String value() {
        return name();
    }

    public static TransactionControls fromValue(String v) {
        return valueOf(v);
    }

}
