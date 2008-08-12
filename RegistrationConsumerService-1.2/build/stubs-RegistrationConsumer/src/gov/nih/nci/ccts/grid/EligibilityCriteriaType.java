/**
 * EligibilityCriteriaType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package gov.nih.nci.ccts.grid;

public class EligibilityCriteriaType  implements java.io.Serializable {
    private java.lang.Boolean notApplicableIndicator;
    private java.lang.String questionNumber;
    private java.lang.String questionText;
    private java.lang.String gridId;  // attribute

    public EligibilityCriteriaType() {
    }

    public EligibilityCriteriaType(
           java.lang.String gridId,
           java.lang.Boolean notApplicableIndicator,
           java.lang.String questionNumber,
           java.lang.String questionText) {
           this.notApplicableIndicator = notApplicableIndicator;
           this.questionNumber = questionNumber;
           this.questionText = questionText;
           this.gridId = gridId;
    }


    /**
     * Gets the notApplicableIndicator value for this EligibilityCriteriaType.
     * 
     * @return notApplicableIndicator
     */
    public java.lang.Boolean getNotApplicableIndicator() {
        return notApplicableIndicator;
    }


    /**
     * Sets the notApplicableIndicator value for this EligibilityCriteriaType.
     * 
     * @param notApplicableIndicator
     */
    public void setNotApplicableIndicator(java.lang.Boolean notApplicableIndicator) {
        this.notApplicableIndicator = notApplicableIndicator;
    }


    /**
     * Gets the questionNumber value for this EligibilityCriteriaType.
     * 
     * @return questionNumber
     */
    public java.lang.String getQuestionNumber() {
        return questionNumber;
    }


    /**
     * Sets the questionNumber value for this EligibilityCriteriaType.
     * 
     * @param questionNumber
     */
    public void setQuestionNumber(java.lang.String questionNumber) {
        this.questionNumber = questionNumber;
    }


    /**
     * Gets the questionText value for this EligibilityCriteriaType.
     * 
     * @return questionText
     */
    public java.lang.String getQuestionText() {
        return questionText;
    }


    /**
     * Sets the questionText value for this EligibilityCriteriaType.
     * 
     * @param questionText
     */
    public void setQuestionText(java.lang.String questionText) {
        this.questionText = questionText;
    }


    /**
     * Gets the gridId value for this EligibilityCriteriaType.
     * 
     * @return gridId
     */
    public java.lang.String getGridId() {
        return gridId;
    }


    /**
     * Sets the gridId value for this EligibilityCriteriaType.
     * 
     * @param gridId
     */
    public void setGridId(java.lang.String gridId) {
        this.gridId = gridId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof EligibilityCriteriaType)) return false;
        EligibilityCriteriaType other = (EligibilityCriteriaType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.notApplicableIndicator==null && other.getNotApplicableIndicator()==null) || 
             (this.notApplicableIndicator!=null &&
              this.notApplicableIndicator.equals(other.getNotApplicableIndicator()))) &&
            ((this.questionNumber==null && other.getQuestionNumber()==null) || 
             (this.questionNumber!=null &&
              this.questionNumber.equals(other.getQuestionNumber()))) &&
            ((this.questionText==null && other.getQuestionText()==null) || 
             (this.questionText!=null &&
              this.questionText.equals(other.getQuestionText()))) &&
            ((this.gridId==null && other.getGridId()==null) || 
             (this.gridId!=null &&
              this.gridId.equals(other.getGridId())));
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
        if (getNotApplicableIndicator() != null) {
            _hashCode += getNotApplicableIndicator().hashCode();
        }
        if (getQuestionNumber() != null) {
            _hashCode += getQuestionNumber().hashCode();
        }
        if (getQuestionText() != null) {
            _hashCode += getQuestionText().hashCode();
        }
        if (getGridId() != null) {
            _hashCode += getGridId().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(EligibilityCriteriaType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "EligibilityCriteriaType"));
        org.apache.axis.description.AttributeDesc attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("gridId");
        attrField.setXmlName(new javax.xml.namespace.QName("", "gridId"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(attrField);
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("notApplicableIndicator");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "notApplicableIndicator"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("questionNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "questionNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("questionText");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "questionText"));
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
