/**
 * StratificationCriterionPermissibleAnswerType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package gov.nih.nci.cabig.ccts.domain;

public class StratificationCriterionPermissibleAnswerType  implements java.io.Serializable {
    private java.lang.String permissibleAnswer;

    public StratificationCriterionPermissibleAnswerType() {
    }

    public StratificationCriterionPermissibleAnswerType(
           java.lang.String permissibleAnswer) {
           this.permissibleAnswer = permissibleAnswer;
    }


    /**
     * Gets the permissibleAnswer value for this StratificationCriterionPermissibleAnswerType.
     * 
     * @return permissibleAnswer
     */
    public java.lang.String getPermissibleAnswer() {
        return permissibleAnswer;
    }


    /**
     * Sets the permissibleAnswer value for this StratificationCriterionPermissibleAnswerType.
     * 
     * @param permissibleAnswer
     */
    public void setPermissibleAnswer(java.lang.String permissibleAnswer) {
        this.permissibleAnswer = permissibleAnswer;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof StratificationCriterionPermissibleAnswerType)) return false;
        StratificationCriterionPermissibleAnswerType other = (StratificationCriterionPermissibleAnswerType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.permissibleAnswer==null && other.getPermissibleAnswer()==null) || 
             (this.permissibleAnswer!=null &&
              this.permissibleAnswer.equals(other.getPermissibleAnswer())));
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
        if (getPermissibleAnswer() != null) {
            _hashCode += getPermissibleAnswer().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(StratificationCriterionPermissibleAnswerType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "StratificationCriterionPermissibleAnswerType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("permissibleAnswer");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "permissibleAnswer"));
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
