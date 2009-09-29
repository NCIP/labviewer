/**
 * OrganizationAssignedIdentifierType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package gov.nih.nci.cabig.ccts.domain;

public class OrganizationAssignedIdentifierType  extends gov.nih.nci.cabig.ccts.domain.IdentifierType  implements java.io.Serializable {
    private gov.nih.nci.cabig.ccts.domain.HealthcareSiteType healthcareSite;

    public OrganizationAssignedIdentifierType() {
    }

    public OrganizationAssignedIdentifierType(
           gov.nih.nci.cabig.ccts.domain.HealthcareSiteType healthcareSite) {
           this.healthcareSite = healthcareSite;
    }


    /**
     * Gets the healthcareSite value for this OrganizationAssignedIdentifierType.
     * 
     * @return healthcareSite
     */
    public gov.nih.nci.cabig.ccts.domain.HealthcareSiteType getHealthcareSite() {
        return healthcareSite;
    }


    /**
     * Sets the healthcareSite value for this OrganizationAssignedIdentifierType.
     * 
     * @param healthcareSite
     */
    public void setHealthcareSite(gov.nih.nci.cabig.ccts.domain.HealthcareSiteType healthcareSite) {
        this.healthcareSite = healthcareSite;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof OrganizationAssignedIdentifierType)) return false;
        OrganizationAssignedIdentifierType other = (OrganizationAssignedIdentifierType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.healthcareSite==null && other.getHealthcareSite()==null) || 
             (this.healthcareSite!=null &&
              this.healthcareSite.equals(other.getHealthcareSite())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = super.hashCode();
        if (getHealthcareSite() != null) {
            _hashCode += getHealthcareSite().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(OrganizationAssignedIdentifierType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "OrganizationAssignedIdentifierType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("healthcareSite");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "healthcareSite"));
        elemField.setXmlType(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "HealthcareSiteType"));
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
