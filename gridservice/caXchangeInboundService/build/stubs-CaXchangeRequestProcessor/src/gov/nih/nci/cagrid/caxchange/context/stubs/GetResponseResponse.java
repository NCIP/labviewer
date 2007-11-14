/**
 * GetResponseResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package gov.nih.nci.cagrid.caxchange.context.stubs;

public class GetResponseResponse  implements java.io.Serializable {
    private gov.nih.nci.caxchange.ResponseMessage caXchangeResponseMessage;

    public GetResponseResponse() {
    }

    public GetResponseResponse(
           gov.nih.nci.caxchange.ResponseMessage caXchangeResponseMessage) {
           this.caXchangeResponseMessage = caXchangeResponseMessage;
    }


    /**
     * Gets the caXchangeResponseMessage value for this GetResponseResponse.
     * 
     * @return caXchangeResponseMessage
     */
    public gov.nih.nci.caxchange.ResponseMessage getCaXchangeResponseMessage() {
        return caXchangeResponseMessage;
    }


    /**
     * Sets the caXchangeResponseMessage value for this GetResponseResponse.
     * 
     * @param caXchangeResponseMessage
     */
    public void setCaXchangeResponseMessage(gov.nih.nci.caxchange.ResponseMessage caXchangeResponseMessage) {
        this.caXchangeResponseMessage = caXchangeResponseMessage;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetResponseResponse)) return false;
        GetResponseResponse other = (GetResponseResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.caXchangeResponseMessage==null && other.getCaXchangeResponseMessage()==null) || 
             (this.caXchangeResponseMessage!=null &&
              this.caXchangeResponseMessage.equals(other.getCaXchangeResponseMessage())));
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
        if (getCaXchangeResponseMessage() != null) {
            _hashCode += getCaXchangeResponseMessage().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetResponseResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://caxchange.cagrid.nci.nih.gov/CaXchangeRequestProcessor/Context", ">GetResponseResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("caXchangeResponseMessage");
        elemField.setXmlName(new javax.xml.namespace.QName("http://caXchange.nci.nih.gov/messaging", "caXchangeResponseMessage"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://caXchange.nci.nih.gov/messaging", "responseMessage"));
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
