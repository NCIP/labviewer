/**
 * EpochType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package gov.nih.nci.ccts.grid;

public abstract class EpochType  implements java.io.Serializable {
    private java.lang.String name;
    private java.lang.String descriptionText;
    private java.lang.String gridId;  // attribute

    public EpochType() {
    }

    public EpochType(
           java.lang.String descriptionText,
           java.lang.String gridId,
           java.lang.String name) {
           this.name = name;
           this.descriptionText = descriptionText;
           this.gridId = gridId;
    }


    /**
     * Gets the name value for this EpochType.
     * 
     * @return name
     */
    public java.lang.String getName() {
        return name;
    }


    /**
     * Sets the name value for this EpochType.
     * 
     * @param name
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }


    /**
     * Gets the descriptionText value for this EpochType.
     * 
     * @return descriptionText
     */
    public java.lang.String getDescriptionText() {
        return descriptionText;
    }


    /**
     * Sets the descriptionText value for this EpochType.
     * 
     * @param descriptionText
     */
    public void setDescriptionText(java.lang.String descriptionText) {
        this.descriptionText = descriptionText;
    }


    /**
     * Gets the gridId value for this EpochType.
     * 
     * @return gridId
     */
    public java.lang.String getGridId() {
        return gridId;
    }


    /**
     * Sets the gridId value for this EpochType.
     * 
     * @param gridId
     */
    public void setGridId(java.lang.String gridId) {
        this.gridId = gridId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof EpochType)) return false;
        EpochType other = (EpochType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.name==null && other.getName()==null) || 
             (this.name!=null &&
              this.name.equals(other.getName()))) &&
            ((this.descriptionText==null && other.getDescriptionText()==null) || 
             (this.descriptionText!=null &&
              this.descriptionText.equals(other.getDescriptionText()))) &&
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
        if (getName() != null) {
            _hashCode += getName().hashCode();
        }
        if (getDescriptionText() != null) {
            _hashCode += getDescriptionText().hashCode();
        }
        if (getGridId() != null) {
            _hashCode += getGridId().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(EpochType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "EpochType"));
        org.apache.axis.description.AttributeDesc attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("gridId");
        attrField.setXmlName(new javax.xml.namespace.QName("", "gridId"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(attrField);
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("name");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "name"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descriptionText");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "descriptionText"));
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
