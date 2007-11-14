/**
 * Message.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package gov.nih.nci.caxchange;

public class Message  implements java.io.Serializable {
    private gov.nih.nci.caxchange.Metadata metadata;
    private gov.nih.nci.caxchange.Request request;

    public Message() {
    }

    public Message(
           gov.nih.nci.caxchange.Metadata metadata,
           gov.nih.nci.caxchange.Request request) {
           this.metadata = metadata;
           this.request = request;
    }


    /**
     * Gets the metadata value for this Message.
     * 
     * @return metadata
     */
    public gov.nih.nci.caxchange.Metadata getMetadata() {
        return metadata;
    }


    /**
     * Sets the metadata value for this Message.
     * 
     * @param metadata
     */
    public void setMetadata(gov.nih.nci.caxchange.Metadata metadata) {
        this.metadata = metadata;
    }


    /**
     * Gets the request value for this Message.
     * 
     * @return request
     */
    public gov.nih.nci.caxchange.Request getRequest() {
        return request;
    }


    /**
     * Sets the request value for this Message.
     * 
     * @param request
     */
    public void setRequest(gov.nih.nci.caxchange.Request request) {
        this.request = request;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Message)) return false;
        Message other = (Message) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.metadata==null && other.getMetadata()==null) || 
             (this.metadata!=null &&
              this.metadata.equals(other.getMetadata()))) &&
            ((this.request==null && other.getRequest()==null) || 
             (this.request!=null &&
              this.request.equals(other.getRequest())));
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
        if (getMetadata() != null) {
            _hashCode += getMetadata().hashCode();
        }
        if (getRequest() != null) {
            _hashCode += getRequest().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Message.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://caXchange.nci.nih.gov/messaging", "message"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("metadata");
        elemField.setXmlName(new javax.xml.namespace.QName("http://caXchange.nci.nih.gov/messaging", "metadata"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://caXchange.nci.nih.gov/messaging", "metadata"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("request");
        elemField.setXmlName(new javax.xml.namespace.QName("http://caXchange.nci.nih.gov/messaging", "request"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://caXchange.nci.nih.gov/messaging", "request"));
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
