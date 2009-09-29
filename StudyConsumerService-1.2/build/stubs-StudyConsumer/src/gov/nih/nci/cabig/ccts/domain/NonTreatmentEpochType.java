/**
 * NonTreatmentEpochType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package gov.nih.nci.cabig.ccts.domain;

public class NonTreatmentEpochType  extends gov.nih.nci.cabig.ccts.domain.EpochType  implements java.io.Serializable {
    private java.lang.Integer accrualCeiling;
    private java.lang.String accrualIndicator;
    private java.lang.String reservationIndicator;
    private java.lang.String enrollmentIndicator;

    public NonTreatmentEpochType() {
    }

    public NonTreatmentEpochType(
           java.lang.Integer accrualCeiling,
           java.lang.String accrualIndicator,
           java.lang.String enrollmentIndicator,
           java.lang.String reservationIndicator) {
           this.accrualCeiling = accrualCeiling;
           this.accrualIndicator = accrualIndicator;
           this.reservationIndicator = reservationIndicator;
           this.enrollmentIndicator = enrollmentIndicator;
    }


    /**
     * Gets the accrualCeiling value for this NonTreatmentEpochType.
     * 
     * @return accrualCeiling
     */
    public java.lang.Integer getAccrualCeiling() {
        return accrualCeiling;
    }


    /**
     * Sets the accrualCeiling value for this NonTreatmentEpochType.
     * 
     * @param accrualCeiling
     */
    public void setAccrualCeiling(java.lang.Integer accrualCeiling) {
        this.accrualCeiling = accrualCeiling;
    }


    /**
     * Gets the accrualIndicator value for this NonTreatmentEpochType.
     * 
     * @return accrualIndicator
     */
    public java.lang.String getAccrualIndicator() {
        return accrualIndicator;
    }


    /**
     * Sets the accrualIndicator value for this NonTreatmentEpochType.
     * 
     * @param accrualIndicator
     */
    public void setAccrualIndicator(java.lang.String accrualIndicator) {
        this.accrualIndicator = accrualIndicator;
    }


    /**
     * Gets the reservationIndicator value for this NonTreatmentEpochType.
     * 
     * @return reservationIndicator
     */
    public java.lang.String getReservationIndicator() {
        return reservationIndicator;
    }


    /**
     * Sets the reservationIndicator value for this NonTreatmentEpochType.
     * 
     * @param reservationIndicator
     */
    public void setReservationIndicator(java.lang.String reservationIndicator) {
        this.reservationIndicator = reservationIndicator;
    }


    /**
     * Gets the enrollmentIndicator value for this NonTreatmentEpochType.
     * 
     * @return enrollmentIndicator
     */
    public java.lang.String getEnrollmentIndicator() {
        return enrollmentIndicator;
    }


    /**
     * Sets the enrollmentIndicator value for this NonTreatmentEpochType.
     * 
     * @param enrollmentIndicator
     */
    public void setEnrollmentIndicator(java.lang.String enrollmentIndicator) {
        this.enrollmentIndicator = enrollmentIndicator;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof NonTreatmentEpochType)) return false;
        NonTreatmentEpochType other = (NonTreatmentEpochType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.accrualCeiling==null && other.getAccrualCeiling()==null) || 
             (this.accrualCeiling!=null &&
              this.accrualCeiling.equals(other.getAccrualCeiling()))) &&
            ((this.accrualIndicator==null && other.getAccrualIndicator()==null) || 
             (this.accrualIndicator!=null &&
              this.accrualIndicator.equals(other.getAccrualIndicator()))) &&
            ((this.reservationIndicator==null && other.getReservationIndicator()==null) || 
             (this.reservationIndicator!=null &&
              this.reservationIndicator.equals(other.getReservationIndicator()))) &&
            ((this.enrollmentIndicator==null && other.getEnrollmentIndicator()==null) || 
             (this.enrollmentIndicator!=null &&
              this.enrollmentIndicator.equals(other.getEnrollmentIndicator())));
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
        if (getAccrualCeiling() != null) {
            _hashCode += getAccrualCeiling().hashCode();
        }
        if (getAccrualIndicator() != null) {
            _hashCode += getAccrualIndicator().hashCode();
        }
        if (getReservationIndicator() != null) {
            _hashCode += getReservationIndicator().hashCode();
        }
        if (getEnrollmentIndicator() != null) {
            _hashCode += getEnrollmentIndicator().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(NonTreatmentEpochType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "NonTreatmentEpochType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accrualCeiling");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "accrualCeiling"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accrualIndicator");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "accrualIndicator"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reservationIndicator");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "reservationIndicator"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("enrollmentIndicator");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "enrollmentIndicator"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
