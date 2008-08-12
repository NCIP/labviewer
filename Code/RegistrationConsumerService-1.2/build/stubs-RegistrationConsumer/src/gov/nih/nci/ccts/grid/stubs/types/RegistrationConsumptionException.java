/**
 * RegistrationConsumptionException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package gov.nih.nci.ccts.grid.stubs.types;

public class RegistrationConsumptionException  extends org.oasis.wsrf.faults.BaseFaultType  implements java.io.Serializable {

    public RegistrationConsumptionException() {
    }

    public RegistrationConsumptionException(
           java.util.Calendar timestamp,
           org.apache.axis.message.addressing.EndpointReferenceType originator,
           org.oasis.wsrf.faults.BaseFaultTypeErrorCode errorCode,
           org.oasis.wsrf.faults.BaseFaultTypeDescription[] description,
           org.oasis.wsrf.faults.BaseFaultType[] faultCause) {
        super(
            timestamp,
            originator,
            errorCode,
            description,
            faultCause);
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RegistrationConsumptionException)) return false;
        RegistrationConsumptionException other = (RegistrationConsumptionException) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj);
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = super.hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RegistrationConsumptionException.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://grid.ccts.nci.nih.gov/RegistrationConsumer/types", "RegistrationConsumptionException"));
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


    /**
     * Writes the exception data to the faultDetails
     */
    public void writeDetails(javax.xml.namespace.QName qname, org.apache.axis.encoding.SerializationContext context) throws java.io.IOException {
        context.serialize(qname, null, this);
    }
}
