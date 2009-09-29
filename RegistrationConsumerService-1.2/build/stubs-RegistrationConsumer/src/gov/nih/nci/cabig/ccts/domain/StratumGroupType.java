/**
 * StratumGroupType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package gov.nih.nci.cabig.ccts.domain;

public class StratumGroupType  implements java.io.Serializable {
    private java.lang.String gridId;

    public StratumGroupType() {
    }

    public StratumGroupType(
           java.lang.String gridId) {
           this.gridId = gridId;
    }


    /**
     * Gets the gridId value for this StratumGroupType.
     * 
     * @return gridId
     */
    public java.lang.String getGridId() {
        return gridId;
    }


    /**
     * Sets the gridId value for this StratumGroupType.
     * 
     * @param gridId
     */
    public void setGridId(java.lang.String gridId) {
        this.gridId = gridId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof StratumGroupType)) return false;
        StratumGroupType other = (StratumGroupType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.gridId==null && other.getGridId()==null) || 
             (this.gridId!=null &&
              this.gridId.equals(other.getGridId())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getGridId() != null) {
            _hashCode += getGridId().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(StratumGroupType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "StratumGroupType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("gridId");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "gridId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
