/**
 * StudySiteType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package gov.nih.nci.cabig.ccts.domain;

public class StudySiteType  extends gov.nih.nci.cabig.ccts.domain.StudyOrganizationType  implements java.io.Serializable {
    private java.util.Date irbApprovalDate;
    private java.lang.String roleCode;
    private java.util.Date startDate;
    private gov.nih.nci.cabig.ccts.domain.SiteStudyStatusType siteStudyStatus;
    private java.util.Date endDate;

    public StudySiteType() {
    }

    public StudySiteType(
           java.util.Date endDate,
           java.util.Date irbApprovalDate,
           java.lang.String roleCode,
           gov.nih.nci.cabig.ccts.domain.SiteStudyStatusType siteStudyStatus,
           java.util.Date startDate) {
           this.irbApprovalDate = irbApprovalDate;
           this.roleCode = roleCode;
           this.startDate = startDate;
           this.siteStudyStatus = siteStudyStatus;
           this.endDate = endDate;
    }


    /**
     * Gets the irbApprovalDate value for this StudySiteType.
     * 
     * @return irbApprovalDate
     */
    public java.util.Date getIrbApprovalDate() {
        return irbApprovalDate;
    }


    /**
     * Sets the irbApprovalDate value for this StudySiteType.
     * 
     * @param irbApprovalDate
     */
    public void setIrbApprovalDate(java.util.Date irbApprovalDate) {
        this.irbApprovalDate = irbApprovalDate;
    }


    /**
     * Gets the roleCode value for this StudySiteType.
     * 
     * @return roleCode
     */
    public java.lang.String getRoleCode() {
        return roleCode;
    }


    /**
     * Sets the roleCode value for this StudySiteType.
     * 
     * @param roleCode
     */
    public void setRoleCode(java.lang.String roleCode) {
        this.roleCode = roleCode;
    }


    /**
     * Gets the startDate value for this StudySiteType.
     * 
     * @return startDate
     */
    public java.util.Date getStartDate() {
        return startDate;
    }


    /**
     * Sets the startDate value for this StudySiteType.
     * 
     * @param startDate
     */
    public void setStartDate(java.util.Date startDate) {
        this.startDate = startDate;
    }


    /**
     * Gets the siteStudyStatus value for this StudySiteType.
     * 
     * @return siteStudyStatus
     */
    public gov.nih.nci.cabig.ccts.domain.SiteStudyStatusType getSiteStudyStatus() {
        return siteStudyStatus;
    }


    /**
     * Sets the siteStudyStatus value for this StudySiteType.
     * 
     * @param siteStudyStatus
     */
    public void setSiteStudyStatus(gov.nih.nci.cabig.ccts.domain.SiteStudyStatusType siteStudyStatus) {
        this.siteStudyStatus = siteStudyStatus;
    }


    /**
     * Gets the endDate value for this StudySiteType.
     * 
     * @return endDate
     */
    public java.util.Date getEndDate() {
        return endDate;
    }


    /**
     * Sets the endDate value for this StudySiteType.
     * 
     * @param endDate
     */
    public void setEndDate(java.util.Date endDate) {
        this.endDate = endDate;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof StudySiteType)) return false;
        StudySiteType other = (StudySiteType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.irbApprovalDate==null && other.getIrbApprovalDate()==null) || 
             (this.irbApprovalDate!=null &&
              this.irbApprovalDate.equals(other.getIrbApprovalDate()))) &&
            ((this.roleCode==null && other.getRoleCode()==null) || 
             (this.roleCode!=null &&
              this.roleCode.equals(other.getRoleCode()))) &&
            ((this.startDate==null && other.getStartDate()==null) || 
             (this.startDate!=null &&
              this.startDate.equals(other.getStartDate()))) &&
            ((this.siteStudyStatus==null && other.getSiteStudyStatus()==null) || 
             (this.siteStudyStatus!=null &&
              this.siteStudyStatus.equals(other.getSiteStudyStatus()))) &&
            ((this.endDate==null && other.getEndDate()==null) || 
             (this.endDate!=null &&
              this.endDate.equals(other.getEndDate())));
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
        if (getIrbApprovalDate() != null) {
            _hashCode += getIrbApprovalDate().hashCode();
        }
        if (getRoleCode() != null) {
            _hashCode += getRoleCode().hashCode();
        }
        if (getStartDate() != null) {
            _hashCode += getStartDate().hashCode();
        }
        if (getSiteStudyStatus() != null) {
            _hashCode += getSiteStudyStatus().hashCode();
        }
        if (getEndDate() != null) {
            _hashCode += getEndDate().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(StudySiteType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "StudySiteType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("irbApprovalDate");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "irbApprovalDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("roleCode");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "roleCode"));
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
        elemField.setFieldName("siteStudyStatus");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "siteStudyStatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "SiteStudyStatusType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("endDate");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "endDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"));
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
