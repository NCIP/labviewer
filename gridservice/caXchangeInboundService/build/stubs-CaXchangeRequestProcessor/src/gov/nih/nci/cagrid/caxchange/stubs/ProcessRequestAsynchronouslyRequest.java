/**
 * ProcessRequestAsynchronouslyRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package gov.nih.nci.cagrid.caxchange.stubs;

public class ProcessRequestAsynchronouslyRequest  implements java.io.Serializable {
    private gov.nih.nci.cagrid.caxchange.stubs.ProcessRequestAsynchronouslyRequestCaXchangeRequestMessage caXchangeRequestMessage;

    public ProcessRequestAsynchronouslyRequest() {
    }

    public ProcessRequestAsynchronouslyRequest(
           gov.nih.nci.cagrid.caxchange.stubs.ProcessRequestAsynchronouslyRequestCaXchangeRequestMessage caXchangeRequestMessage) {
           this.caXchangeRequestMessage = caXchangeRequestMessage;
    }


    /**
     * Gets the caXchangeRequestMessage value for this ProcessRequestAsynchronouslyRequest.
     * 
     * @return caXchangeRequestMessage
     */
    public gov.nih.nci.cagrid.caxchange.stubs.ProcessRequestAsynchronouslyRequestCaXchangeRequestMessage getCaXchangeRequestMessage() {
        return caXchangeRequestMessage;
    }


    /**
     * Sets the caXchangeRequestMessage value for this ProcessRequestAsynchronouslyRequest.
     * 
     * @param caXchangeRequestMessage
     */
    public void setCaXchangeRequestMessage(gov.nih.nci.cagrid.caxchange.stubs.ProcessRequestAsynchronouslyRequestCaXchangeRequestMessage caXchangeRequestMessage) {
        this.caXchangeRequestMessage = caXchangeRequestMessage;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ProcessRequestAsynchronouslyRequest)) return false;
        ProcessRequestAsynchronouslyRequest other = (ProcessRequestAsynchronouslyRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.caXchangeRequestMessage==null && other.getCaXchangeRequestMessage()==null) || 
             (this.caXchangeRequestMessage!=null &&
              this.caXchangeRequestMessage.equals(other.getCaXchangeRequestMessage())));
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
        if (getCaXchangeRequestMessage() != null) {
            _hashCode += getCaXchangeRequestMessage().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ProcessRequestAsynchronouslyRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://caxchange.cagrid.nci.nih.gov/CaXchangeRequestProcessor", ">ProcessRequestAsynchronouslyRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("caXchangeRequestMessage");
        elemField.setXmlName(new javax.xml.namespace.QName("http://caxchange.cagrid.nci.nih.gov/CaXchangeRequestProcessor", "caXchangeRequestMessage"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://caxchange.cagrid.nci.nih.gov/CaXchangeRequestProcessor", ">>ProcessRequestAsynchronouslyRequest>caXchangeRequestMessage"));
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
