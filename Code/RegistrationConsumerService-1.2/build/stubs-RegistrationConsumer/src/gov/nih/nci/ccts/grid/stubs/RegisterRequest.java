/**
 * RegisterRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package gov.nih.nci.ccts.grid.stubs;

public class RegisterRequest  implements java.io.Serializable {
    private gov.nih.nci.ccts.grid.stubs.RegisterRequestRegistration registration;

    public RegisterRequest() {
    }

    public RegisterRequest(
           gov.nih.nci.ccts.grid.stubs.RegisterRequestRegistration registration) {
           this.registration = registration;
    }


    /**
     * Gets the registration value for this RegisterRequest.
     * 
     * @return registration
     */
    public gov.nih.nci.ccts.grid.stubs.RegisterRequestRegistration getRegistration() {
        return registration;
    }


    /**
     * Sets the registration value for this RegisterRequest.
     * 
     * @param registration
     */
    public void setRegistration(gov.nih.nci.ccts.grid.stubs.RegisterRequestRegistration registration) {
        this.registration = registration;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RegisterRequest)) return false;
        RegisterRequest other = (RegisterRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.registration==null && other.getRegistration()==null) || 
             (this.registration!=null &&
              this.registration.equals(other.getRegistration())));
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
        if (getRegistration() != null) {
            _hashCode += getRegistration().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RegisterRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://grid.ccts.nci.nih.gov/RegistrationConsumer", ">RegisterRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("registration");
        elemField.setXmlName(new javax.xml.namespace.QName("http://grid.ccts.nci.nih.gov/RegistrationConsumer", "registration"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://grid.ccts.nci.nih.gov/RegistrationConsumer", ">>RegisterRequest>registration"));
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
