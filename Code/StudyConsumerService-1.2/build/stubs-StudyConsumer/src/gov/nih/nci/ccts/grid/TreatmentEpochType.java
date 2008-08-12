/**
 * TreatmentEpochType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package gov.nih.nci.ccts.grid;

public class TreatmentEpochType  extends gov.nih.nci.ccts.grid.EpochType  implements java.io.Serializable {
    private gov.nih.nci.ccts.grid.ArmType[] arm;
    private gov.nih.nci.ccts.grid.EligibilityCriteriaType[] eligibilityCriteria;
    private gov.nih.nci.ccts.grid.StratificationCriterionType[] stratificationCriteria;

    public TreatmentEpochType() {
    }

    public TreatmentEpochType(
           gov.nih.nci.ccts.grid.ArmType[] arm,
           gov.nih.nci.ccts.grid.EligibilityCriteriaType[] eligibilityCriteria,
           gov.nih.nci.ccts.grid.StratificationCriterionType[] stratificationCriteria) {
           this.arm = arm;
           this.eligibilityCriteria = eligibilityCriteria;
           this.stratificationCriteria = stratificationCriteria;
    }


    /**
     * Gets the arm value for this TreatmentEpochType.
     * 
     * @return arm
     */
    public gov.nih.nci.ccts.grid.ArmType[] getArm() {
        return arm;
    }


    /**
     * Sets the arm value for this TreatmentEpochType.
     * 
     * @param arm
     */
    public void setArm(gov.nih.nci.ccts.grid.ArmType[] arm) {
        this.arm = arm;
    }

    public gov.nih.nci.ccts.grid.ArmType getArm(int i) {
        return this.arm[i];
    }

    public void setArm(int i, gov.nih.nci.ccts.grid.ArmType _value) {
        this.arm[i] = _value;
    }


    /**
     * Gets the eligibilityCriteria value for this TreatmentEpochType.
     * 
     * @return eligibilityCriteria
     */
    public gov.nih.nci.ccts.grid.EligibilityCriteriaType[] getEligibilityCriteria() {
        return eligibilityCriteria;
    }


    /**
     * Sets the eligibilityCriteria value for this TreatmentEpochType.
     * 
     * @param eligibilityCriteria
     */
    public void setEligibilityCriteria(gov.nih.nci.ccts.grid.EligibilityCriteriaType[] eligibilityCriteria) {
        this.eligibilityCriteria = eligibilityCriteria;
    }

    public gov.nih.nci.ccts.grid.EligibilityCriteriaType getEligibilityCriteria(int i) {
        return this.eligibilityCriteria[i];
    }

    public void setEligibilityCriteria(int i, gov.nih.nci.ccts.grid.EligibilityCriteriaType _value) {
        this.eligibilityCriteria[i] = _value;
    }


    /**
     * Gets the stratificationCriteria value for this TreatmentEpochType.
     * 
     * @return stratificationCriteria
     */
    public gov.nih.nci.ccts.grid.StratificationCriterionType[] getStratificationCriteria() {
        return stratificationCriteria;
    }


    /**
     * Sets the stratificationCriteria value for this TreatmentEpochType.
     * 
     * @param stratificationCriteria
     */
    public void setStratificationCriteria(gov.nih.nci.ccts.grid.StratificationCriterionType[] stratificationCriteria) {
        this.stratificationCriteria = stratificationCriteria;
    }

    public gov.nih.nci.ccts.grid.StratificationCriterionType getStratificationCriteria(int i) {
        return this.stratificationCriteria[i];
    }

    public void setStratificationCriteria(int i, gov.nih.nci.ccts.grid.StratificationCriterionType _value) {
        this.stratificationCriteria[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TreatmentEpochType)) return false;
        TreatmentEpochType other = (TreatmentEpochType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.arm==null && other.getArm()==null) || 
             (this.arm!=null &&
              java.util.Arrays.equals(this.arm, other.getArm()))) &&
            ((this.eligibilityCriteria==null && other.getEligibilityCriteria()==null) || 
             (this.eligibilityCriteria!=null &&
              java.util.Arrays.equals(this.eligibilityCriteria, other.getEligibilityCriteria()))) &&
            ((this.stratificationCriteria==null && other.getStratificationCriteria()==null) || 
             (this.stratificationCriteria!=null &&
              java.util.Arrays.equals(this.stratificationCriteria, other.getStratificationCriteria())));
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
        if (getArm() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getArm());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getArm(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getEligibilityCriteria() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getEligibilityCriteria());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getEligibilityCriteria(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getStratificationCriteria() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getStratificationCriteria());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getStratificationCriteria(), i);
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
        new org.apache.axis.description.TypeDesc(TreatmentEpochType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "TreatmentEpochType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("arm");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "arm"));
        elemField.setXmlType(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "ArmType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("eligibilityCriteria");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "eligibilityCriteria"));
        elemField.setXmlType(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "EligibilityCriteriaType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("stratificationCriteria");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "stratificationCriteria"));
        elemField.setXmlType(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "StratificationCriterionType"));
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
