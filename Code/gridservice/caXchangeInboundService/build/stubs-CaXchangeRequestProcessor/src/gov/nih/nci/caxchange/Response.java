/**
 * Response.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package gov.nih.nci.caxchange;

public class Response  implements java.io.Serializable {
    private gov.nih.nci.caxchange.Statuses responseStatus;
    private gov.nih.nci.caxchange.ErrorDetails caXchangeError;
    private gov.nih.nci.caxchange.TargetResponseMessage[] targetResponse;

    public Response() {
    }

    public Response(
           gov.nih.nci.caxchange.ErrorDetails caXchangeError,
           gov.nih.nci.caxchange.Statuses responseStatus,
           gov.nih.nci.caxchange.TargetResponseMessage[] targetResponse) {
           this.responseStatus = responseStatus;
           this.caXchangeError = caXchangeError;
           this.targetResponse = targetResponse;
    }


    /**
     * Gets the responseStatus value for this Response.
     * 
     * @return responseStatus
     */
    public gov.nih.nci.caxchange.Statuses getResponseStatus() {
        return responseStatus;
    }


    /**
     * Sets the responseStatus value for this Response.
     * 
     * @param responseStatus
     */
    public void setResponseStatus(gov.nih.nci.caxchange.Statuses responseStatus) {
        this.responseStatus = responseStatus;
    }


    /**
     * Gets the caXchangeError value for this Response.
     * 
     * @return caXchangeError
     */
    public gov.nih.nci.caxchange.ErrorDetails getCaXchangeError() {
        return caXchangeError;
    }


    /**
     * Sets the caXchangeError value for this Response.
     * 
     * @param caXchangeError
     */
    public void setCaXchangeError(gov.nih.nci.caxchange.ErrorDetails caXchangeError) {
        this.caXchangeError = caXchangeError;
    }


    /**
     * Gets the targetResponse value for this Response.
     * 
     * @return targetResponse
     */
    public gov.nih.nci.caxchange.TargetResponseMessage[] getTargetResponse() {
        return targetResponse;
    }


    /**
     * Sets the targetResponse value for this Response.
     * 
     * @param targetResponse
     */
    public void setTargetResponse(gov.nih.nci.caxchange.TargetResponseMessage[] targetResponse) {
        this.targetResponse = targetResponse;
    }

    public gov.nih.nci.caxchange.TargetResponseMessage getTargetResponse(int i) {
        return this.targetResponse[i];
    }

    public void setTargetResponse(int i, gov.nih.nci.caxchange.TargetResponseMessage _value) {
        this.targetResponse[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Response)) return false;
        Response other = (Response) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.responseStatus==null && other.getResponseStatus()==null) || 
             (this.responseStatus!=null &&
              this.responseStatus.equals(other.getResponseStatus()))) &&
            ((this.caXchangeError==null && other.getCaXchangeError()==null) || 
             (this.caXchangeError!=null &&
              this.caXchangeError.equals(other.getCaXchangeError()))) &&
            ((this.targetResponse==null && other.getTargetResponse()==null) || 
             (this.targetResponse!=null &&
              java.util.Arrays.equals(this.targetResponse, other.getTargetResponse())));
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
        if (getResponseStatus() != null) {
            _hashCode += getResponseStatus().hashCode();
        }
        if (getCaXchangeError() != null) {
            _hashCode += getCaXchangeError().hashCode();
        }
        if (getTargetResponse() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getTargetResponse());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getTargetResponse(), i);
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
        new org.apache.axis.description.TypeDesc(Response.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://caXchange.nci.nih.gov/messaging", "response"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("responseStatus");
        elemField.setXmlName(new javax.xml.namespace.QName("http://caXchange.nci.nih.gov/messaging", "responseStatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://caXchange.nci.nih.gov/messaging", "statuses"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("caXchangeError");
        elemField.setXmlName(new javax.xml.namespace.QName("http://caXchange.nci.nih.gov/messaging", "caXchangeError"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://caXchange.nci.nih.gov/messaging", "errorDetails"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("targetResponse");
        elemField.setXmlName(new javax.xml.namespace.QName("http://caXchange.nci.nih.gov/messaging", "targetResponse"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://caXchange.nci.nih.gov/messaging", "targetResponseMessage"));
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
