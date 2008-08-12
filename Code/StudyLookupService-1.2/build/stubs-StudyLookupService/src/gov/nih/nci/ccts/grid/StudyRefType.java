/**
 * StudyRefType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package gov.nih.nci.ccts.grid;

public class StudyRefType  implements java.io.Serializable {
    private java.lang.String shortTitleText;
    private java.lang.String longTitleText;
    private gov.nih.nci.ccts.grid.IdentifierType[] identifier;
    private java.lang.String gridId;  // attribute

    public StudyRefType() {
    }

    public StudyRefType(
           java.lang.String gridId,
           gov.nih.nci.ccts.grid.IdentifierType[] identifier,
           java.lang.String longTitleText,
           java.lang.String shortTitleText) {
           this.shortTitleText = shortTitleText;
           this.longTitleText = longTitleText;
           this.identifier = identifier;
           this.gridId = gridId;
    }


    /**
     * Gets the shortTitleText value for this StudyRefType.
     * 
     * @return shortTitleText
     */
    public java.lang.String getShortTitleText() {
        return shortTitleText;
    }


    /**
     * Sets the shortTitleText value for this StudyRefType.
     * 
     * @param shortTitleText
     */
    public void setShortTitleText(java.lang.String shortTitleText) {
        this.shortTitleText = shortTitleText;
    }


    /**
     * Gets the longTitleText value for this StudyRefType.
     * 
     * @return longTitleText
     */
    public java.lang.String getLongTitleText() {
        return longTitleText;
    }


    /**
     * Sets the longTitleText value for this StudyRefType.
     * 
     * @param longTitleText
     */
    public void setLongTitleText(java.lang.String longTitleText) {
        this.longTitleText = longTitleText;
    }


    /**
     * Gets the identifier value for this StudyRefType.
     * 
     * @return identifier
     */
    public gov.nih.nci.ccts.grid.IdentifierType[] getIdentifier() {
        return identifier;
    }


    /**
     * Sets the identifier value for this StudyRefType.
     * 
     * @param identifier
     */
    public void setIdentifier(gov.nih.nci.ccts.grid.IdentifierType[] identifier) {
        this.identifier = identifier;
    }

    public gov.nih.nci.ccts.grid.IdentifierType getIdentifier(int i) {
        return this.identifier[i];
    }

    public void setIdentifier(int i, gov.nih.nci.ccts.grid.IdentifierType _value) {
        this.identifier[i] = _value;
    }


    /**
     * Gets the gridId value for this StudyRefType.
     * 
     * @return gridId
     */
    public java.lang.String getGridId() {
        return gridId;
    }


    /**
     * Sets the gridId value for this StudyRefType.
     * 
     * @param gridId
     */
    public void setGridId(java.lang.String gridId) {
        this.gridId = gridId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof StudyRefType)) return false;
        StudyRefType other = (StudyRefType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.shortTitleText==null && other.getShortTitleText()==null) || 
             (this.shortTitleText!=null &&
              this.shortTitleText.equals(other.getShortTitleText()))) &&
            ((this.longTitleText==null && other.getLongTitleText()==null) || 
             (this.longTitleText!=null &&
              this.longTitleText.equals(other.getLongTitleText()))) &&
            ((this.identifier==null && other.getIdentifier()==null) || 
             (this.identifier!=null &&
              java.util.Arrays.equals(this.identifier, other.getIdentifier()))) &&
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
        if (getShortTitleText() != null) {
            _hashCode += getShortTitleText().hashCode();
        }
        if (getLongTitleText() != null) {
            _hashCode += getLongTitleText().hashCode();
        }
        if (getIdentifier() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getIdentifier());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getIdentifier(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getGridId() != null) {
            _hashCode += getGridId().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(StudyRefType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "StudyRefType"));
        org.apache.axis.description.AttributeDesc attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("gridId");
        attrField.setXmlName(new javax.xml.namespace.QName("", "gridId"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(attrField);
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("shortTitleText");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "shortTitleText"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("longTitleText");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "longTitleText"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("identifier");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "identifier"));
        elemField.setXmlType(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "IdentifierType"));
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
