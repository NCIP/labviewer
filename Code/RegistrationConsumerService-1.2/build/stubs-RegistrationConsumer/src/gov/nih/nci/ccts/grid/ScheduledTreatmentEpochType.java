/**
 * ScheduledTreatmentEpochType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package gov.nih.nci.ccts.grid;

public class ScheduledTreatmentEpochType  extends gov.nih.nci.ccts.grid.ScheduledEpochType  implements java.io.Serializable {
    private gov.nih.nci.ccts.grid.ScheduledArmType scheduledArm;
    private boolean eligibilityIndicator;

    public ScheduledTreatmentEpochType() {
    }

    public ScheduledTreatmentEpochType(
           boolean eligibilityIndicator,
           gov.nih.nci.ccts.grid.ScheduledArmType scheduledArm) {
           this.scheduledArm = scheduledArm;
           this.eligibilityIndicator = eligibilityIndicator;
    }


    /**
     * Gets the scheduledArm value for this ScheduledTreatmentEpochType.
     * 
     * @return scheduledArm
     */
    public gov.nih.nci.ccts.grid.ScheduledArmType getScheduledArm() {
        return scheduledArm;
    }


    /**
     * Sets the scheduledArm value for this ScheduledTreatmentEpochType.
     * 
     * @param scheduledArm
     */
    public void setScheduledArm(gov.nih.nci.ccts.grid.ScheduledArmType scheduledArm) {
        this.scheduledArm = scheduledArm;
    }


    /**
     * Gets the eligibilityIndicator value for this ScheduledTreatmentEpochType.
     * 
     * @return eligibilityIndicator
     */
    public boolean isEligibilityIndicator() {
        return eligibilityIndicator;
    }


    /**
     * Sets the eligibilityIndicator value for this ScheduledTreatmentEpochType.
     * 
     * @param eligibilityIndicator
     */
    public void setEligibilityIndicator(boolean eligibilityIndicator) {
        this.eligibilityIndicator = eligibilityIndicator;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ScheduledTreatmentEpochType)) return false;
        ScheduledTreatmentEpochType other = (ScheduledTreatmentEpochType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.scheduledArm==null && other.getScheduledArm()==null) || 
             (this.scheduledArm!=null &&
              this.scheduledArm.equals(other.getScheduledArm()))) &&
            this.eligibilityIndicator == other.isEligibilityIndicator();
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
        if (getScheduledArm() != null) {
            _hashCode += getScheduledArm().hashCode();
        }
        _hashCode += (isEligibilityIndicator() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ScheduledTreatmentEpochType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "ScheduledTreatmentEpochType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("scheduledArm");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "scheduledArm"));
        elemField.setXmlType(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "ScheduledArmType"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("eligibilityIndicator");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "eligibilityIndicator"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
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
