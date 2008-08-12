/**
 * InvestigatorType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package gov.nih.nci.ccts.grid;

public class InvestigatorType  implements java.io.Serializable {
    private java.lang.String nciIdentifier;
    private java.lang.String fullName;

    public InvestigatorType() {
    }

    public InvestigatorType(
           java.lang.String fullName,
           java.lang.String nciIdentifier) {
           this.nciIdentifier = nciIdentifier;
           this.fullName = fullName;
    }


    /**
     * Gets the nciIdentifier value for this InvestigatorType.
     * 
     * @return nciIdentifier
     */
    public java.lang.String getNciIdentifier() {
        return nciIdentifier;
    }


    /**
     * Sets the nciIdentifier value for this InvestigatorType.
     * 
     * @param nciIdentifier
     */
    public void setNciIdentifier(java.lang.String nciIdentifier) {
        this.nciIdentifier = nciIdentifier;
    }


    /**
     * Gets the fullName value for this InvestigatorType.
     * 
     * @return fullName
     */
    public java.lang.String getFullName() {
        return fullName;
    }


    /**
     * Sets the fullName value for this InvestigatorType.
     * 
     * @param fullName
     */
    public void setFullName(java.lang.String fullName) {
        this.fullName = fullName;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof InvestigatorType)) return false;
        InvestigatorType other = (InvestigatorType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.nciIdentifier==null && other.getNciIdentifier()==null) || 
             (this.nciIdentifier!=null &&
              this.nciIdentifier.equals(other.getNciIdentifier()))) &&
            ((this.fullName==null && other.getFullName()==null) || 
             (this.fullName!=null &&
              this.fullName.equals(other.getFullName())));
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
        if (getNciIdentifier() != null) {
            _hashCode += getNciIdentifier().hashCode();
        }
        if (getFullName() != null) {
            _hashCode += getFullName().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(InvestigatorType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "InvestigatorType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nciIdentifier");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "nciIdentifier"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fullName");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "fullName"));
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
