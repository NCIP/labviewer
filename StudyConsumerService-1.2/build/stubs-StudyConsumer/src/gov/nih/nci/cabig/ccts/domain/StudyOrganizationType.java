/**
 * StudyOrganizationType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package gov.nih.nci.cabig.ccts.domain;

public abstract class StudyOrganizationType  implements java.io.Serializable {
    private gov.nih.nci.cabig.ccts.domain.HealthcareSiteType[] healthcareSite;
    private gov.nih.nci.cabig.ccts.domain.StudyInvestigatorType[] studyInvestigator;
    private java.lang.String gridId;  // attribute

    public StudyOrganizationType() {
    }

    public StudyOrganizationType(
           java.lang.String gridId,
           gov.nih.nci.cabig.ccts.domain.HealthcareSiteType[] healthcareSite,
           gov.nih.nci.cabig.ccts.domain.StudyInvestigatorType[] studyInvestigator) {
           this.healthcareSite = healthcareSite;
           this.studyInvestigator = studyInvestigator;
           this.gridId = gridId;
    }


    /**
     * Gets the healthcareSite value for this StudyOrganizationType.
     * 
     * @return healthcareSite
     */
    public gov.nih.nci.cabig.ccts.domain.HealthcareSiteType[] getHealthcareSite() {
        return healthcareSite;
    }


    /**
     * Sets the healthcareSite value for this StudyOrganizationType.
     * 
     * @param healthcareSite
     */
    public void setHealthcareSite(gov.nih.nci.cabig.ccts.domain.HealthcareSiteType[] healthcareSite) {
        this.healthcareSite = healthcareSite;
    }

    public gov.nih.nci.cabig.ccts.domain.HealthcareSiteType getHealthcareSite(int i) {
        return this.healthcareSite[i];
    }

    public void setHealthcareSite(int i, gov.nih.nci.cabig.ccts.domain.HealthcareSiteType _value) {
        this.healthcareSite[i] = _value;
    }


    /**
     * Gets the studyInvestigator value for this StudyOrganizationType.
     * 
     * @return studyInvestigator
     */
    public gov.nih.nci.cabig.ccts.domain.StudyInvestigatorType[] getStudyInvestigator() {
        return studyInvestigator;
    }


    /**
     * Sets the studyInvestigator value for this StudyOrganizationType.
     * 
     * @param studyInvestigator
     */
    public void setStudyInvestigator(gov.nih.nci.cabig.ccts.domain.StudyInvestigatorType[] studyInvestigator) {
        this.studyInvestigator = studyInvestigator;
    }

    public gov.nih.nci.cabig.ccts.domain.StudyInvestigatorType getStudyInvestigator(int i) {
        return this.studyInvestigator[i];
    }

    public void setStudyInvestigator(int i, gov.nih.nci.cabig.ccts.domain.StudyInvestigatorType _value) {
        this.studyInvestigator[i] = _value;
    }


    /**
     * Gets the gridId value for this StudyOrganizationType.
     * 
     * @return gridId
     */
    public java.lang.String getGridId() {
        return gridId;
    }


    /**
     * Sets the gridId value for this StudyOrganizationType.
     * 
     * @param gridId
     */
    public void setGridId(java.lang.String gridId) {
        this.gridId = gridId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof StudyOrganizationType)) return false;
        StudyOrganizationType other = (StudyOrganizationType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.healthcareSite==null && other.getHealthcareSite()==null) || 
             (this.healthcareSite!=null &&
              java.util.Arrays.equals(this.healthcareSite, other.getHealthcareSite()))) &&
            ((this.studyInvestigator==null && other.getStudyInvestigator()==null) || 
             (this.studyInvestigator!=null &&
              java.util.Arrays.equals(this.studyInvestigator, other.getStudyInvestigator()))) &&
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
        if (getHealthcareSite() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getHealthcareSite());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getHealthcareSite(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getStudyInvestigator() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getStudyInvestigator());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getStudyInvestigator(), i);
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
        new org.apache.axis.description.TypeDesc(StudyOrganizationType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "StudyOrganizationType"));
        org.apache.axis.description.AttributeDesc attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("gridId");
        attrField.setXmlName(new javax.xml.namespace.QName("", "gridId"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(attrField);
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("healthcareSite");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "healthcareSite"));
        elemField.setXmlType(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "HealthcareSiteType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("studyInvestigator");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "studyInvestigator"));
        elemField.setXmlType(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "StudyInvestigatorType"));
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
