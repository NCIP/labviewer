/**
 * StratificationCriterionType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package gov.nih.nci.ccts.grid;

public class StratificationCriterionType  implements java.io.Serializable {
    private java.lang.Integer questionNumber;
    private java.lang.String questionText;
    private gov.nih.nci.ccts.grid.StratificationCriterionPermissibleAnswerType[] permissibleAnswer;

    public StratificationCriterionType() {
    }

    public StratificationCriterionType(
           gov.nih.nci.ccts.grid.StratificationCriterionPermissibleAnswerType[] permissibleAnswer,
           java.lang.Integer questionNumber,
           java.lang.String questionText) {
           this.questionNumber = questionNumber;
           this.questionText = questionText;
           this.permissibleAnswer = permissibleAnswer;
    }


    /**
     * Gets the questionNumber value for this StratificationCriterionType.
     * 
     * @return questionNumber
     */
    public java.lang.Integer getQuestionNumber() {
        return questionNumber;
    }


    /**
     * Sets the questionNumber value for this StratificationCriterionType.
     * 
     * @param questionNumber
     */
    public void setQuestionNumber(java.lang.Integer questionNumber) {
        this.questionNumber = questionNumber;
    }


    /**
     * Gets the questionText value for this StratificationCriterionType.
     * 
     * @return questionText
     */
    public java.lang.String getQuestionText() {
        return questionText;
    }


    /**
     * Sets the questionText value for this StratificationCriterionType.
     * 
     * @param questionText
     */
    public void setQuestionText(java.lang.String questionText) {
        this.questionText = questionText;
    }


    /**
     * Gets the permissibleAnswer value for this StratificationCriterionType.
     * 
     * @return permissibleAnswer
     */
    public gov.nih.nci.ccts.grid.StratificationCriterionPermissibleAnswerType[] getPermissibleAnswer() {
        return permissibleAnswer;
    }


    /**
     * Sets the permissibleAnswer value for this StratificationCriterionType.
     * 
     * @param permissibleAnswer
     */
    public void setPermissibleAnswer(gov.nih.nci.ccts.grid.StratificationCriterionPermissibleAnswerType[] permissibleAnswer) {
        this.permissibleAnswer = permissibleAnswer;
    }

    public gov.nih.nci.ccts.grid.StratificationCriterionPermissibleAnswerType getPermissibleAnswer(int i) {
        return this.permissibleAnswer[i];
    }

    public void setPermissibleAnswer(int i, gov.nih.nci.ccts.grid.StratificationCriterionPermissibleAnswerType _value) {
        this.permissibleAnswer[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof StratificationCriterionType)) return false;
        StratificationCriterionType other = (StratificationCriterionType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.questionNumber==null && other.getQuestionNumber()==null) || 
             (this.questionNumber!=null &&
              this.questionNumber.equals(other.getQuestionNumber()))) &&
            ((this.questionText==null && other.getQuestionText()==null) || 
             (this.questionText!=null &&
              this.questionText.equals(other.getQuestionText()))) &&
            ((this.permissibleAnswer==null && other.getPermissibleAnswer()==null) || 
             (this.permissibleAnswer!=null &&
              java.util.Arrays.equals(this.permissibleAnswer, other.getPermissibleAnswer())));
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
        if (getQuestionNumber() != null) {
            _hashCode += getQuestionNumber().hashCode();
        }
        if (getQuestionText() != null) {
            _hashCode += getQuestionText().hashCode();
        }
        if (getPermissibleAnswer() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getPermissibleAnswer());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getPermissibleAnswer(), i);
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
        new org.apache.axis.description.TypeDesc(StratificationCriterionType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "StratificationCriterionType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("questionNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "questionNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("questionText");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "questionText"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("permissibleAnswer");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "permissibleAnswer"));
        elemField.setXmlType(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "StratificationCriterionPermissibleAnswerType"));
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
