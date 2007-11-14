/**
 * Request.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package gov.nih.nci.caxchange;

public class Request  implements java.io.Serializable {
    private gov.nih.nci.caxchange.MessagePayload businessMessagePayload;

    public Request() {
    }

    public Request(
           gov.nih.nci.caxchange.MessagePayload businessMessagePayload) {
           this.businessMessagePayload = businessMessagePayload;
    }


    /**
     * Gets the businessMessagePayload value for this Request.
     * 
     * @return businessMessagePayload
     */
    public gov.nih.nci.caxchange.MessagePayload getBusinessMessagePayload() {
        return businessMessagePayload;
    }


    /**
     * Sets the businessMessagePayload value for this Request.
     * 
     * @param businessMessagePayload
     */
    public void setBusinessMessagePayload(gov.nih.nci.caxchange.MessagePayload businessMessagePayload) {
        this.businessMessagePayload = businessMessagePayload;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Request)) return false;
        Request other = (Request) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.businessMessagePayload==null && other.getBusinessMessagePayload()==null) || 
             (this.businessMessagePayload!=null &&
              this.businessMessagePayload.equals(other.getBusinessMessagePayload())));
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
        if (getBusinessMessagePayload() != null) {
            _hashCode += getBusinessMessagePayload().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Request.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://caXchange.nci.nih.gov/messaging", "request"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("businessMessagePayload");
        elemField.setXmlName(new javax.xml.namespace.QName("http://caXchange.nci.nih.gov/messaging", "businessMessagePayload"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://caXchange.nci.nih.gov/messaging", "messagePayload"));
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
