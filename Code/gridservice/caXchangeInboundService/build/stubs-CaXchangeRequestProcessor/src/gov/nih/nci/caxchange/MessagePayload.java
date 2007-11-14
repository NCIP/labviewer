/**
 * MessagePayload.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package gov.nih.nci.caxchange;

public class MessagePayload  implements java.io.Serializable, org.apache.axis.encoding.AnyContentType {
    private org.apache.axis.types.URI xmlSchemaDefinition;
    private org.apache.axis.message.MessageElement [] _any;

    public MessagePayload() {
    }

    public MessagePayload(
           org.apache.axis.message.MessageElement [] _any,
           org.apache.axis.types.URI xmlSchemaDefinition) {
           this.xmlSchemaDefinition = xmlSchemaDefinition;
           this._any = _any;
    }


    /**
     * Gets the xmlSchemaDefinition value for this MessagePayload.
     * 
     * @return xmlSchemaDefinition
     */
    public org.apache.axis.types.URI getXmlSchemaDefinition() {
        return xmlSchemaDefinition;
    }


    /**
     * Sets the xmlSchemaDefinition value for this MessagePayload.
     * 
     * @param xmlSchemaDefinition
     */
    public void setXmlSchemaDefinition(org.apache.axis.types.URI xmlSchemaDefinition) {
        this.xmlSchemaDefinition = xmlSchemaDefinition;
    }


    /**
     * Gets the _any value for this MessagePayload.
     * 
     * @return _any
     */
    public org.apache.axis.message.MessageElement [] get_any() {
        return _any;
    }


    /**
     * Sets the _any value for this MessagePayload.
     * 
     * @param _any
     */
    public void set_any(org.apache.axis.message.MessageElement [] _any) {
        this._any = _any;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MessagePayload)) return false;
        MessagePayload other = (MessagePayload) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.xmlSchemaDefinition==null && other.getXmlSchemaDefinition()==null) || 
             (this.xmlSchemaDefinition!=null &&
              this.xmlSchemaDefinition.equals(other.getXmlSchemaDefinition()))) &&
            ((this._any==null && other.get_any()==null) || 
             (this._any!=null &&
              java.util.Arrays.equals(this._any, other.get_any())));
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
        if (getXmlSchemaDefinition() != null) {
            _hashCode += getXmlSchemaDefinition().hashCode();
        }
        if (get_any() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(get_any());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(get_any(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(MessagePayload.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://caXchange.nci.nih.gov/messaging", "messagePayload"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("xmlSchemaDefinition");
        elemField.setXmlName(new javax.xml.namespace.QName("http://caXchange.nci.nih.gov/messaging", "xmlSchemaDefinition"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "anyURI"));
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
