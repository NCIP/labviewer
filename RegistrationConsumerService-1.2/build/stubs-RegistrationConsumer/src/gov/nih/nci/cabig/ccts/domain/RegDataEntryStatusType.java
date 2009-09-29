/**
 * RegDataEntryStatusType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package gov.nih.nci.cabig.ccts.domain;

public class RegDataEntryStatusType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected RegDataEntryStatusType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _INCOMPLETE = "INCOMPLETE";
    public static final java.lang.String _COMPLETE = "COMPLETE";
    public static final RegDataEntryStatusType INCOMPLETE = new RegDataEntryStatusType(_INCOMPLETE);
    public static final RegDataEntryStatusType COMPLETE = new RegDataEntryStatusType(_COMPLETE);
    public java.lang.String getValue() { return _value_;}
    public static RegDataEntryStatusType fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        RegDataEntryStatusType enumeration = (RegDataEntryStatusType)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static RegDataEntryStatusType fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(RegDataEntryStatusType.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "RegDataEntryStatusType"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
