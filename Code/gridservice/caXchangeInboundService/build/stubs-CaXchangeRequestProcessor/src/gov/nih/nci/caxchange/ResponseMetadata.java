/**
 * ResponseMetadata.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package gov.nih.nci.caxchange;

public class ResponseMetadata  implements java.io.Serializable {
    private java.lang.String externalIdentifier;
    /** Populated by caXchange components, any value provided by the client
 * will be overridden by the caXchange components */
    private java.lang.String caXchangeIdentifier;

    public ResponseMetadata() {
    }

    public ResponseMetadata(
           java.lang.String caXchangeIdentifier,
           java.lang.String externalIdentifier) {
           this.externalIdentifier = externalIdentifier;
           this.caXchangeIdentifier = caXchangeIdentifier;
    }


    /**
     * Gets the externalIdentifier value for this ResponseMetadata.
     * 
     * @return externalIdentifier
     */
    public java.lang.String getExternalIdentifier() {
        return externalIdentifier;
    }


    /**
     * Sets the externalIdentifier value for this ResponseMetadata.
     * 
     * @param externalIdentifier
     */
    public void setExternalIdentifier(java.lang.String externalIdentifier) {
        this.externalIdentifier = externalIdentifier;
    }


    /**
     * Gets the caXchangeIdentifier value for this ResponseMetadata.
     * 
     * @return caXchangeIdentifier Populated by caXchange components, any value provided by the client
 * will be overridden by the caXchange components
     */
    public java.lang.String getCaXchangeIdentifier() {
        return caXchangeIdentifier;
    }


    /**
     * Sets the caXchangeIdentifier value for this ResponseMetadata.
     * 
     * @param caXchangeIdentifier Populated by caXchange components, any value provided by the client
 * will be overridden by the caXchange components
     */
    public void setCaXchangeIdentifier(java.lang.String caXchangeIdentifier) {
        this.caXchangeIdentifier = caXchangeIdentifier;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ResponseMetadata)) return false;
        ResponseMetadata other = (ResponseMetadata) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.externalIdentifier==null && other.getExternalIdentifier()==null) || 
             (this.externalIdentifier!=null &&
              this.externalIdentifier.equals(other.getExternalIdentifier()))) &&
            ((this.caXchangeIdentifier==null && other.getCaXchangeIdentifier()==null) || 
             (this.caXchangeIdentifier!=null &&
              this.caXchangeIdentifier.equals(other.getCaXchangeIdentifier())));
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
        if (getExternalIdentifier() != null) {
            _hashCode += getExternalIdentifier().hashCode();
        }
        if (getCaXchangeIdentifier() != null) {
            _hashCode += getCaXchangeIdentifier().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ResponseMetadata.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://caXchange.nci.nih.gov/messaging", "responseMetadata"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("externalIdentifier");
        elemField.setXmlName(new javax.xml.namespace.QName("http://caXchange.nci.nih.gov/messaging", "externalIdentifier"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("caXchangeIdentifier");
        elemField.setXmlName(new javax.xml.namespace.QName("http://caXchange.nci.nih.gov/messaging", "caXchangeIdentifier"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
