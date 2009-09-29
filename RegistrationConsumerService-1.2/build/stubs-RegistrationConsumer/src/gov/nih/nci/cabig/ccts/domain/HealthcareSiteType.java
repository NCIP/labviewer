/**
 * HealthcareSiteType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package gov.nih.nci.cabig.ccts.domain;

public class HealthcareSiteType  implements java.io.Serializable {
    private java.lang.String name;
    private java.lang.String descriptionText;
    private gov.nih.nci.cabig.ccts.domain.AddressType address;
    private java.lang.String nciInstituteCode;
    private java.lang.String gridId;  // attribute

    public HealthcareSiteType() {
    }

    public HealthcareSiteType(
           gov.nih.nci.cabig.ccts.domain.AddressType address,
           java.lang.String descriptionText,
           java.lang.String gridId,
           java.lang.String name,
           java.lang.String nciInstituteCode) {
           this.name = name;
           this.descriptionText = descriptionText;
           this.address = address;
           this.nciInstituteCode = nciInstituteCode;
           this.gridId = gridId;
    }


    /**
     * Gets the name value for this HealthcareSiteType.
     * 
     * @return name
     */
    public java.lang.String getName() {
        return name;
    }


    /**
     * Sets the name value for this HealthcareSiteType.
     * 
     * @param name
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }


    /**
     * Gets the descriptionText value for this HealthcareSiteType.
     * 
     * @return descriptionText
     */
    public java.lang.String getDescriptionText() {
        return descriptionText;
    }


    /**
     * Sets the descriptionText value for this HealthcareSiteType.
     * 
     * @param descriptionText
     */
    public void setDescriptionText(java.lang.String descriptionText) {
        this.descriptionText = descriptionText;
    }


    /**
     * Gets the address value for this HealthcareSiteType.
     * 
     * @return address
     */
    public gov.nih.nci.cabig.ccts.domain.AddressType getAddress() {
        return address;
    }


    /**
     * Sets the address value for this HealthcareSiteType.
     * 
     * @param address
     */
    public void setAddress(gov.nih.nci.cabig.ccts.domain.AddressType address) {
        this.address = address;
    }


    /**
     * Gets the nciInstituteCode value for this HealthcareSiteType.
     * 
     * @return nciInstituteCode
     */
    public java.lang.String getNciInstituteCode() {
        return nciInstituteCode;
    }


    /**
     * Sets the nciInstituteCode value for this HealthcareSiteType.
     * 
     * @param nciInstituteCode
     */
    public void setNciInstituteCode(java.lang.String nciInstituteCode) {
        this.nciInstituteCode = nciInstituteCode;
    }


    /**
     * Gets the gridId value for this HealthcareSiteType.
     * 
     * @return gridId
     */
    public java.lang.String getGridId() {
        return gridId;
    }


    /**
     * Sets the gridId value for this HealthcareSiteType.
     * 
     * @param gridId
     */
    public void setGridId(java.lang.String gridId) {
        this.gridId = gridId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof HealthcareSiteType)) return false;
        HealthcareSiteType other = (HealthcareSiteType) obj;
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
            ((this.address==null && other.getAddress()==null) || 
             (this.address!=null &&
              this.address.equals(other.getAddress()))) &&
            ((this.nciInstituteCode==null && other.getNciInstituteCode()==null) || 
             (this.nciInstituteCode!=null &&
              this.nciInstituteCode.equals(other.getNciInstituteCode()))) &&
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
        if (getAddress() != null) {
            _hashCode += getAddress().hashCode();
        }
        if (getNciInstituteCode() != null) {
            _hashCode += getNciInstituteCode().hashCode();
        }
        if (getGridId() != null) {
            _hashCode += getGridId().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(HealthcareSiteType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "HealthcareSiteType"));
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
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("address");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "address"));
        elemField.setXmlType(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "AddressType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nciInstituteCode");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "nciInstituteCode"));
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
