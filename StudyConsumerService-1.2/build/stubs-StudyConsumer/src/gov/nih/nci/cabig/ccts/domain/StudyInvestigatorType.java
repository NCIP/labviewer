/**
 * StudyInvestigatorType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package gov.nih.nci.cabig.ccts.domain;

public class StudyInvestigatorType  implements java.io.Serializable {
    private java.lang.String roleCode;
    private java.lang.String statusCode;
    private java.util.Date startDate;
    private java.util.Date endDate;
    private gov.nih.nci.cabig.ccts.domain.HealthcareSiteInvestigatorType healthcareSiteInvestigator;

    public StudyInvestigatorType() {
    }

    public StudyInvestigatorType(
           java.util.Date endDate,
           gov.nih.nci.cabig.ccts.domain.HealthcareSiteInvestigatorType healthcareSiteInvestigator,
           java.lang.String roleCode,
           java.util.Date startDate,
           java.lang.String statusCode) {
           this.roleCode = roleCode;
           this.statusCode = statusCode;
           this.startDate = startDate;
           this.endDate = endDate;
           this.healthcareSiteInvestigator = healthcareSiteInvestigator;
    }


    /**
     * Gets the roleCode value for this StudyInvestigatorType.
     * 
     * @return roleCode
     */
    public java.lang.String getRoleCode() {
        return roleCode;
    }


    /**
     * Sets the roleCode value for this StudyInvestigatorType.
     * 
     * @param roleCode
     */
    public void setRoleCode(java.lang.String roleCode) {
        this.roleCode = roleCode;
    }


    /**
     * Gets the statusCode value for this StudyInvestigatorType.
     * 
     * @return statusCode
     */
    public java.lang.String getStatusCode() {
        return statusCode;
    }


    /**
     * Sets the statusCode value for this StudyInvestigatorType.
     * 
     * @param statusCode
     */
    public void setStatusCode(java.lang.String statusCode) {
        this.statusCode = statusCode;
    }


    /**
     * Gets the startDate value for this StudyInvestigatorType.
     * 
     * @return startDate
     */
    public java.util.Date getStartDate() {
        return startDate;
    }


    /**
     * Sets the startDate value for this StudyInvestigatorType.
     * 
     * @param startDate
     */
    public void setStartDate(java.util.Date startDate) {
        this.startDate = startDate;
    }


    /**
     * Gets the endDate value for this StudyInvestigatorType.
     * 
     * @return endDate
     */
    public java.util.Date getEndDate() {
        return endDate;
    }


    /**
     * Sets the endDate value for this StudyInvestigatorType.
     * 
     * @param endDate
     */
    public void setEndDate(java.util.Date endDate) {
        this.endDate = endDate;
    }


    /**
     * Gets the healthcareSiteInvestigator value for this StudyInvestigatorType.
     * 
     * @return healthcareSiteInvestigator
     */
    public gov.nih.nci.cabig.ccts.domain.HealthcareSiteInvestigatorType getHealthcareSiteInvestigator() {
        return healthcareSiteInvestigator;
    }


    /**
     * Sets the healthcareSiteInvestigator value for this StudyInvestigatorType.
     * 
     * @param healthcareSiteInvestigator
     */
    public void setHealthcareSiteInvestigator(gov.nih.nci.cabig.ccts.domain.HealthcareSiteInvestigatorType healthcareSiteInvestigator) {
        this.healthcareSiteInvestigator = healthcareSiteInvestigator;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof StudyInvestigatorType)) return false;
        StudyInvestigatorType other = (StudyInvestigatorType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.roleCode==null && other.getRoleCode()==null) || 
             (this.roleCode!=null &&
              this.roleCode.equals(other.getRoleCode()))) &&
            ((this.statusCode==null && other.getStatusCode()==null) || 
             (this.statusCode!=null &&
              this.statusCode.equals(other.getStatusCode()))) &&
            ((this.startDate==null && other.getStartDate()==null) || 
             (this.startDate!=null &&
              this.startDate.equals(other.getStartDate()))) &&
            ((this.endDate==null && other.getEndDate()==null) || 
             (this.endDate!=null &&
              this.endDate.equals(other.getEndDate()))) &&
            ((this.healthcareSiteInvestigator==null && other.getHealthcareSiteInvestigator()==null) || 
             (this.healthcareSiteInvestigator!=null &&
              this.healthcareSiteInvestigator.equals(other.getHealthcareSiteInvestigator())));
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
        if (getRoleCode() != null) {
            _hashCode += getRoleCode().hashCode();
        }
        if (getStatusCode() != null) {
            _hashCode += getStatusCode().hashCode();
        }
        if (getStartDate() != null) {
            _hashCode += getStartDate().hashCode();
        }
        if (getEndDate() != null) {
            _hashCode += getEndDate().hashCode();
        }
        if (getHealthcareSiteInvestigator() != null) {
            _hashCode += getHealthcareSiteInvestigator().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(StudyInvestigatorType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "StudyInvestigatorType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("roleCode");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "roleCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("statusCode");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "statusCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("startDate");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "startDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("endDate");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "endDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("healthcareSiteInvestigator");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "healthcareSiteInvestigator"));
        elemField.setXmlType(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "HealthcareSiteInvestigatorType"));
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
