/**
 * ResponseMessage.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package gov.nih.nci.caxchange;

public class ResponseMessage  implements java.io.Serializable {
    private gov.nih.nci.caxchange.ResponseMetadata responseMetadata;
    private gov.nih.nci.caxchange.Response response;

    public ResponseMessage() {
    }

    public ResponseMessage(
           gov.nih.nci.caxchange.Response response,
           gov.nih.nci.caxchange.ResponseMetadata responseMetadata) {
           this.responseMetadata = responseMetadata;
           this.response = response;
    }


    /**
     * Gets the responseMetadata value for this ResponseMessage.
     * 
     * @return responseMetadata
     */
    public gov.nih.nci.caxchange.ResponseMetadata getResponseMetadata() {
        return responseMetadata;
    }


    /**
     * Sets the responseMetadata value for this ResponseMessage.
     * 
     * @param responseMetadata
     */
    public void setResponseMetadata(gov.nih.nci.caxchange.ResponseMetadata responseMetadata) {
        this.responseMetadata = responseMetadata;
    }


    /**
     * Gets the response value for this ResponseMessage.
     * 
     * @return response
     */
    public gov.nih.nci.caxchange.Response getResponse() {
        return response;
    }


    /**
     * Sets the response value for this ResponseMessage.
     * 
     * @param response
     */
    public void setResponse(gov.nih.nci.caxchange.Response response) {
        this.response = response;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ResponseMessage)) return false;
        ResponseMessage other = (ResponseMessage) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.responseMetadata==null && other.getResponseMetadata()==null) || 
             (this.responseMetadata!=null &&
              this.responseMetadata.equals(other.getResponseMetadata()))) &&
            ((this.response==null && other.getResponse()==null) || 
             (this.response!=null &&
              this.response.equals(other.getResponse())));
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
        if (getResponseMetadata() != null) {
            _hashCode += getResponseMetadata().hashCode();
        }
        if (getResponse() != null) {
            _hashCode += getResponse().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ResponseMessage.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://caXchange.nci.nih.gov/messaging", "responseMessage"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("responseMetadata");
        elemField.setXmlName(new javax.xml.namespace.QName("http://caXchange.nci.nih.gov/messaging", "responseMetadata"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://caXchange.nci.nih.gov/messaging", "responseMetadata"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("response");
        elemField.setXmlName(new javax.xml.namespace.QName("http://caXchange.nci.nih.gov/messaging", "response"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://caXchange.nci.nih.gov/messaging", "response"));
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
