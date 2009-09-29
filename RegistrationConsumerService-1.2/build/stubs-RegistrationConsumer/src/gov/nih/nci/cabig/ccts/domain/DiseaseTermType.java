/**
 * DiseaseTermType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package gov.nih.nci.cabig.ccts.domain;

public class DiseaseTermType  implements java.io.Serializable {
    private java.lang.String[] term;
    private java.lang.String[] ctepTerm;
    private java.lang.String[] medraCode;
    private java.lang.String gridId;  // attribute

    public DiseaseTermType() {
    }

    public DiseaseTermType(
           java.lang.String[] ctepTerm,
           java.lang.String gridId,
           java.lang.String[] medraCode,
           java.lang.String[] term) {
           this.term = term;
           this.ctepTerm = ctepTerm;
           this.medraCode = medraCode;
           this.gridId = gridId;
    }


    /**
     * Gets the term value for this DiseaseTermType.
     * 
     * @return term
     */
    public java.lang.String[] getTerm() {
        return term;
    }


    /**
     * Sets the term value for this DiseaseTermType.
     * 
     * @param term
     */
    public void setTerm(java.lang.String[] term) {
        this.term = term;
    }

    public java.lang.String getTerm(int i) {
        return this.term[i];
    }

    public void setTerm(int i, java.lang.String _value) {
        this.term[i] = _value;
    }


    /**
     * Gets the ctepTerm value for this DiseaseTermType.
     * 
     * @return ctepTerm
     */
    public java.lang.String[] getCtepTerm() {
        return ctepTerm;
    }


    /**
     * Sets the ctepTerm value for this DiseaseTermType.
     * 
     * @param ctepTerm
     */
    public void setCtepTerm(java.lang.String[] ctepTerm) {
        this.ctepTerm = ctepTerm;
    }

    public java.lang.String getCtepTerm(int i) {
        return this.ctepTerm[i];
    }

    public void setCtepTerm(int i, java.lang.String _value) {
        this.ctepTerm[i] = _value;
    }


    /**
     * Gets the medraCode value for this DiseaseTermType.
     * 
     * @return medraCode
     */
    public java.lang.String[] getMedraCode() {
        return medraCode;
    }


    /**
     * Sets the medraCode value for this DiseaseTermType.
     * 
     * @param medraCode
     */
    public void setMedraCode(java.lang.String[] medraCode) {
        this.medraCode = medraCode;
    }

    public java.lang.String getMedraCode(int i) {
        return this.medraCode[i];
    }

    public void setMedraCode(int i, java.lang.String _value) {
        this.medraCode[i] = _value;
    }


    /**
     * Gets the gridId value for this DiseaseTermType.
     * 
     * @return gridId
     */
    public java.lang.String getGridId() {
        return gridId;
    }


    /**
     * Sets the gridId value for this DiseaseTermType.
     * 
     * @param gridId
     */
    public void setGridId(java.lang.String gridId) {
        this.gridId = gridId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DiseaseTermType)) return false;
        DiseaseTermType other = (DiseaseTermType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.term==null && other.getTerm()==null) || 
             (this.term!=null &&
              java.util.Arrays.equals(this.term, other.getTerm()))) &&
            ((this.ctepTerm==null && other.getCtepTerm()==null) || 
             (this.ctepTerm!=null &&
              java.util.Arrays.equals(this.ctepTerm, other.getCtepTerm()))) &&
            ((this.medraCode==null && other.getMedraCode()==null) || 
             (this.medraCode!=null &&
              java.util.Arrays.equals(this.medraCode, other.getMedraCode()))) &&
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
        if (getTerm() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getTerm());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getTerm(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getCtepTerm() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getCtepTerm());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getCtepTerm(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getMedraCode() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getMedraCode());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getMedraCode(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getGridId() != null) {
            _hashCode += getGridId().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DiseaseTermType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "DiseaseTermType"));
        org.apache.axis.description.AttributeDesc attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("gridId");
        attrField.setXmlName(new javax.xml.namespace.QName("", "gridId"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(attrField);
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("term");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "term"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ctepTerm");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "ctepTerm"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("medraCode");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "medraCode"));
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
