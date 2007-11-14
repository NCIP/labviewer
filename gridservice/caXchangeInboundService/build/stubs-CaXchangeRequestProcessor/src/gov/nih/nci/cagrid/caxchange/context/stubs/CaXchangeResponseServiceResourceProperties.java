/**
 * CaXchangeResponseServiceResourceProperties.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package gov.nih.nci.cagrid.caxchange.context.stubs;

public class CaXchangeResponseServiceResourceProperties  implements java.io.Serializable {
    private java.util.Calendar currentTime;
    private java.util.Calendar terminationTime;
    private gov.nih.nci.caxchange.ResponseMessage caXchangeResponseMessage;

    public CaXchangeResponseServiceResourceProperties() {
    }

    public CaXchangeResponseServiceResourceProperties(
           gov.nih.nci.caxchange.ResponseMessage caXchangeResponseMessage,
           java.util.Calendar currentTime,
           java.util.Calendar terminationTime) {
           this.currentTime = currentTime;
           this.terminationTime = terminationTime;
           this.caXchangeResponseMessage = caXchangeResponseMessage;
    }


    /**
     * Gets the currentTime value for this CaXchangeResponseServiceResourceProperties.
     * 
     * @return currentTime
     */
    public java.util.Calendar getCurrentTime() {
        return currentTime;
    }


    /**
     * Sets the currentTime value for this CaXchangeResponseServiceResourceProperties.
     * 
     * @param currentTime
     */
    public void setCurrentTime(java.util.Calendar currentTime) {
        this.currentTime = currentTime;
    }


    /**
     * Gets the terminationTime value for this CaXchangeResponseServiceResourceProperties.
     * 
     * @return terminationTime
     */
    public java.util.Calendar getTerminationTime() {
        return terminationTime;
    }


    /**
     * Sets the terminationTime value for this CaXchangeResponseServiceResourceProperties.
     * 
     * @param terminationTime
     */
    public void setTerminationTime(java.util.Calendar terminationTime) {
        this.terminationTime = terminationTime;
    }


    /**
     * Gets the caXchangeResponseMessage value for this CaXchangeResponseServiceResourceProperties.
     * 
     * @return caXchangeResponseMessage
     */
    public gov.nih.nci.caxchange.ResponseMessage getCaXchangeResponseMessage() {
        return caXchangeResponseMessage;
    }


    /**
     * Sets the caXchangeResponseMessage value for this CaXchangeResponseServiceResourceProperties.
     * 
     * @param caXchangeResponseMessage
     */
    public void setCaXchangeResponseMessage(gov.nih.nci.caxchange.ResponseMessage caXchangeResponseMessage) {
        this.caXchangeResponseMessage = caXchangeResponseMessage;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CaXchangeResponseServiceResourceProperties)) return false;
        CaXchangeResponseServiceResourceProperties other = (CaXchangeResponseServiceResourceProperties) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.currentTime==null && other.getCurrentTime()==null) || 
             (this.currentTime!=null &&
              this.currentTime.equals(other.getCurrentTime()))) &&
            ((this.terminationTime==null && other.getTerminationTime()==null) || 
             (this.terminationTime!=null &&
              this.terminationTime.equals(other.getTerminationTime()))) &&
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
        if (getCurrentTime() != null) {
            _hashCode += getCurrentTime().hashCode();
        }
        if (getTerminationTime() != null) {
            _hashCode += getTerminationTime().hashCode();
        }
        if (getCaXchangeResponseMessage() != null) {
            _hashCode += getCaXchangeResponseMessage().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CaXchangeResponseServiceResourceProperties.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://caxchange.cagrid.nci.nih.gov/CaXchangeRequestProcessor/Context", ">CaXchangeResponseServiceResourceProperties"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("currentTime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceLifetime-1.2-draft-01.xsd", "CurrentTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("terminationTime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceLifetime-1.2-draft-01.xsd", "TerminationTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
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
