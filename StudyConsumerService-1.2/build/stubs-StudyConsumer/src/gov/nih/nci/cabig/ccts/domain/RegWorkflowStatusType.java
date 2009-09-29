/**
 * RegWorkflowStatusType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package gov.nih.nci.cabig.ccts.domain;

public class RegWorkflowStatusType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected RegWorkflowStatusType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _UNREGISTERED = "UNREGISTERED";
    public static final java.lang.String _PENDING = "PENDING";
    public static final java.lang.String _DISAPPROVED = "DISAPPROVED";
    public static final java.lang.String _RESERVED = "RESERVED";
    public static final java.lang.String _REGISTERED = "REGISTERED";
    public static final java.lang.String _OFF_STUDY = "OFF_STUDY";
    public static final java.lang.String _INVALID = "INVALID";
    public static final RegWorkflowStatusType UNREGISTERED = new RegWorkflowStatusType(_UNREGISTERED);
    public static final RegWorkflowStatusType PENDING = new RegWorkflowStatusType(_PENDING);
    public static final RegWorkflowStatusType DISAPPROVED = new RegWorkflowStatusType(_DISAPPROVED);
    public static final RegWorkflowStatusType RESERVED = new RegWorkflowStatusType(_RESERVED);
    public static final RegWorkflowStatusType REGISTERED = new RegWorkflowStatusType(_REGISTERED);
    public static final RegWorkflowStatusType OFF_STUDY = new RegWorkflowStatusType(_OFF_STUDY);
    public static final RegWorkflowStatusType INVALID = new RegWorkflowStatusType(_INVALID);
    public java.lang.String getValue() { return _value_;}
    public static RegWorkflowStatusType fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        RegWorkflowStatusType enumeration = (RegWorkflowStatusType)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static RegWorkflowStatusType fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(RegWorkflowStatusType.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "RegWorkflowStatusType"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
