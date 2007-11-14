/**
 * MessageTypes.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package gov.nih.nci.caxchange;

public class MessageTypes implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected MessageTypes(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _STUDY_CREATION = "STUDY_CREATION";
    public static final java.lang.String _REGISTER_SUBJECT = "REGISTER_SUBJECT";
    public static final java.lang.String _CT_LAB_DATA = "CT_LAB_DATA";
    public static final java.lang.String _LOAD_LAB_TO_CDMS = "LOAD_LAB_TO_CDMS";
    public static final java.lang.String _LAB_BASED_AE = "LAB_BASED_AE";
    public static final java.lang.String _SCHEDULE_MODIFICATION = "SCHEDULE_MODIFICATION";
    public static final MessageTypes STUDY_CREATION = new MessageTypes(_STUDY_CREATION);
    public static final MessageTypes REGISTER_SUBJECT = new MessageTypes(_REGISTER_SUBJECT);
    public static final MessageTypes CT_LAB_DATA = new MessageTypes(_CT_LAB_DATA);
    public static final MessageTypes LOAD_LAB_TO_CDMS = new MessageTypes(_LOAD_LAB_TO_CDMS);
    public static final MessageTypes LAB_BASED_AE = new MessageTypes(_LAB_BASED_AE);
    public static final MessageTypes SCHEDULE_MODIFICATION = new MessageTypes(_SCHEDULE_MODIFICATION);
    public java.lang.String getValue() { return _value_;}
    public static MessageTypes fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        MessageTypes enumeration = (MessageTypes)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static MessageTypes fromString(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumSerializer(
            _javaType, _xmlType);
    }
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumDeserializer(
            _javaType, _xmlType);
    }
    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(MessageTypes.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://caXchange.nci.nih.gov/messaging", "messageTypes"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
