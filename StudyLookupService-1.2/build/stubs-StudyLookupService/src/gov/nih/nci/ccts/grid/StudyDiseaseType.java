/**
 * StudyDiseaseType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package gov.nih.nci.ccts.grid;

public class StudyDiseaseType  implements java.io.Serializable {
    private boolean[] leadDisease;
    private gov.nih.nci.ccts.grid.DiseaseTermType[] diseaseTerm;
    private java.lang.String gridId;  // attribute

    public StudyDiseaseType() {
    }

    public StudyDiseaseType(
           gov.nih.nci.ccts.grid.DiseaseTermType[] diseaseTerm,
           java.lang.String gridId,
           boolean[] leadDisease) {
           this.leadDisease = leadDisease;
           this.diseaseTerm = diseaseTerm;
           this.gridId = gridId;
    }


    /**
     * Gets the leadDisease value for this StudyDiseaseType.
     * 
     * @return leadDisease
     */
    public boolean[] getLeadDisease() {
        return leadDisease;
    }


    /**
     * Sets the leadDisease value for this StudyDiseaseType.
     * 
     * @param leadDisease
     */
    public void setLeadDisease(boolean[] leadDisease) {
        this.leadDisease = leadDisease;
    }

    public boolean getLeadDisease(int i) {
        return this.leadDisease[i];
    }

    public void setLeadDisease(int i, boolean _value) {
        this.leadDisease[i] = _value;
    }


    /**
     * Gets the diseaseTerm value for this StudyDiseaseType.
     * 
     * @return diseaseTerm
     */
    public gov.nih.nci.ccts.grid.DiseaseTermType[] getDiseaseTerm() {
        return diseaseTerm;
    }


    /**
     * Sets the diseaseTerm value for this StudyDiseaseType.
     * 
     * @param diseaseTerm
     */
    public void setDiseaseTerm(gov.nih.nci.ccts.grid.DiseaseTermType[] diseaseTerm) {
        this.diseaseTerm = diseaseTerm;
    }

    public gov.nih.nci.ccts.grid.DiseaseTermType getDiseaseTerm(int i) {
        return this.diseaseTerm[i];
    }

    public void setDiseaseTerm(int i, gov.nih.nci.ccts.grid.DiseaseTermType _value) {
        this.diseaseTerm[i] = _value;
    }


    /**
     * Gets the gridId value for this StudyDiseaseType.
     * 
     * @return gridId
     */
    public java.lang.String getGridId() {
        return gridId;
    }


    /**
     * Sets the gridId value for this StudyDiseaseType.
     * 
     * @param gridId
     */
    public void setGridId(java.lang.String gridId) {
        this.gridId = gridId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof StudyDiseaseType)) return false;
        StudyDiseaseType other = (StudyDiseaseType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.leadDisease==null && other.getLeadDisease()==null) || 
             (this.leadDisease!=null &&
              java.util.Arrays.equals(this.leadDisease, other.getLeadDisease()))) &&
            ((this.diseaseTerm==null && other.getDiseaseTerm()==null) || 
             (this.diseaseTerm!=null &&
              java.util.Arrays.equals(this.diseaseTerm, other.getDiseaseTerm()))) &&
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
        if (getLeadDisease() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getLeadDisease());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getLeadDisease(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getDiseaseTerm() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDiseaseTerm());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDiseaseTerm(), i);
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
        new org.apache.axis.description.TypeDesc(StudyDiseaseType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "StudyDiseaseType"));
        org.apache.axis.description.AttributeDesc attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("gridId");
        attrField.setXmlName(new javax.xml.namespace.QName("", "gridId"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(attrField);
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("leadDisease");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "leadDisease"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("diseaseTerm");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "diseaseTerm"));
        elemField.setXmlType(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "DiseaseTermType"));
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
