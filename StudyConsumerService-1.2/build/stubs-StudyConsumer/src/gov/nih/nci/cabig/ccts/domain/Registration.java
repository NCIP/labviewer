/**
 * Registration.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package gov.nih.nci.cabig.ccts.domain;

public class Registration  implements java.io.Serializable {
    private gov.nih.nci.cabig.ccts.domain.StudySiteType studySite;
    private gov.nih.nci.cabig.ccts.domain.StudyRefType studyRef;
    private gov.nih.nci.cabig.ccts.domain.ParticipantType participant;
    private java.util.Date startDate;
    private java.util.Date informedConsentFormSignedDate;
    private java.lang.String informedConsentVersion;
    private gov.nih.nci.cabig.ccts.domain.IdentifierType[] identifier;
    private gov.nih.nci.cabig.ccts.domain.RegDataEntryStatusType regDataEntryStatus;
    private gov.nih.nci.cabig.ccts.domain.RegWorkflowStatusType regWorkflowStatus;
    private gov.nih.nci.cabig.ccts.domain.ScheduledEpochType scheduledEpoch;
    private java.lang.String stratumGroup;
    private java.lang.String offStudyReasonText;
    private java.lang.String offStudyDate;
    private java.lang.String gridId;  // attribute

    public Registration() {
    }

    public Registration(
           java.lang.String gridId,
           gov.nih.nci.cabig.ccts.domain.IdentifierType[] identifier,
           java.util.Date informedConsentFormSignedDate,
           java.lang.String informedConsentVersion,
           java.lang.String offStudyDate,
           java.lang.String offStudyReasonText,
           gov.nih.nci.cabig.ccts.domain.ParticipantType participant,
           gov.nih.nci.cabig.ccts.domain.RegDataEntryStatusType regDataEntryStatus,
           gov.nih.nci.cabig.ccts.domain.RegWorkflowStatusType regWorkflowStatus,
           gov.nih.nci.cabig.ccts.domain.ScheduledEpochType scheduledEpoch,
           java.util.Date startDate,
           java.lang.String stratumGroup,
           gov.nih.nci.cabig.ccts.domain.StudyRefType studyRef,
           gov.nih.nci.cabig.ccts.domain.StudySiteType studySite) {
           this.studySite = studySite;
           this.studyRef = studyRef;
           this.participant = participant;
           this.startDate = startDate;
           this.informedConsentFormSignedDate = informedConsentFormSignedDate;
           this.informedConsentVersion = informedConsentVersion;
           this.identifier = identifier;
           this.regDataEntryStatus = regDataEntryStatus;
           this.regWorkflowStatus = regWorkflowStatus;
           this.scheduledEpoch = scheduledEpoch;
           this.stratumGroup = stratumGroup;
           this.offStudyReasonText = offStudyReasonText;
           this.offStudyDate = offStudyDate;
           this.gridId = gridId;
    }


    /**
     * Gets the studySite value for this Registration.
     * 
     * @return studySite
     */
    public gov.nih.nci.cabig.ccts.domain.StudySiteType getStudySite() {
        return studySite;
    }


    /**
     * Sets the studySite value for this Registration.
     * 
     * @param studySite
     */
    public void setStudySite(gov.nih.nci.cabig.ccts.domain.StudySiteType studySite) {
        this.studySite = studySite;
    }


    /**
     * Gets the studyRef value for this Registration.
     * 
     * @return studyRef
     */
    public gov.nih.nci.cabig.ccts.domain.StudyRefType getStudyRef() {
        return studyRef;
    }


    /**
     * Sets the studyRef value for this Registration.
     * 
     * @param studyRef
     */
    public void setStudyRef(gov.nih.nci.cabig.ccts.domain.StudyRefType studyRef) {
        this.studyRef = studyRef;
    }


    /**
     * Gets the participant value for this Registration.
     * 
     * @return participant
     */
    public gov.nih.nci.cabig.ccts.domain.ParticipantType getParticipant() {
        return participant;
    }


    /**
     * Sets the participant value for this Registration.
     * 
     * @param participant
     */
    public void setParticipant(gov.nih.nci.cabig.ccts.domain.ParticipantType participant) {
        this.participant = participant;
    }


    /**
     * Gets the startDate value for this Registration.
     * 
     * @return startDate
     */
    public java.util.Date getStartDate() {
        return startDate;
    }


    /**
     * Sets the startDate value for this Registration.
     * 
     * @param startDate
     */
    public void setStartDate(java.util.Date startDate) {
        this.startDate = startDate;
    }


    /**
     * Gets the informedConsentFormSignedDate value for this Registration.
     * 
     * @return informedConsentFormSignedDate
     */
    public java.util.Date getInformedConsentFormSignedDate() {
        return informedConsentFormSignedDate;
    }


    /**
     * Sets the informedConsentFormSignedDate value for this Registration.
     * 
     * @param informedConsentFormSignedDate
     */
    public void setInformedConsentFormSignedDate(java.util.Date informedConsentFormSignedDate) {
        this.informedConsentFormSignedDate = informedConsentFormSignedDate;
    }


    /**
     * Gets the informedConsentVersion value for this Registration.
     * 
     * @return informedConsentVersion
     */
    public java.lang.String getInformedConsentVersion() {
        return informedConsentVersion;
    }


    /**
     * Sets the informedConsentVersion value for this Registration.
     * 
     * @param informedConsentVersion
     */
    public void setInformedConsentVersion(java.lang.String informedConsentVersion) {
        this.informedConsentVersion = informedConsentVersion;
    }


    /**
     * Gets the identifier value for this Registration.
     * 
     * @return identifier
     */
    public gov.nih.nci.cabig.ccts.domain.IdentifierType[] getIdentifier() {
        return identifier;
    }


    /**
     * Sets the identifier value for this Registration.
     * 
     * @param identifier
     */
    public void setIdentifier(gov.nih.nci.cabig.ccts.domain.IdentifierType[] identifier) {
        this.identifier = identifier;
    }

    public gov.nih.nci.cabig.ccts.domain.IdentifierType getIdentifier(int i) {
        return this.identifier[i];
    }

    public void setIdentifier(int i, gov.nih.nci.cabig.ccts.domain.IdentifierType _value) {
        this.identifier[i] = _value;
    }


    /**
     * Gets the regDataEntryStatus value for this Registration.
     * 
     * @return regDataEntryStatus
     */
    public gov.nih.nci.cabig.ccts.domain.RegDataEntryStatusType getRegDataEntryStatus() {
        return regDataEntryStatus;
    }


    /**
     * Sets the regDataEntryStatus value for this Registration.
     * 
     * @param regDataEntryStatus
     */
    public void setRegDataEntryStatus(gov.nih.nci.cabig.ccts.domain.RegDataEntryStatusType regDataEntryStatus) {
        this.regDataEntryStatus = regDataEntryStatus;
    }


    /**
     * Gets the regWorkflowStatus value for this Registration.
     * 
     * @return regWorkflowStatus
     */
    public gov.nih.nci.cabig.ccts.domain.RegWorkflowStatusType getRegWorkflowStatus() {
        return regWorkflowStatus;
    }


    /**
     * Sets the regWorkflowStatus value for this Registration.
     * 
     * @param regWorkflowStatus
     */
    public void setRegWorkflowStatus(gov.nih.nci.cabig.ccts.domain.RegWorkflowStatusType regWorkflowStatus) {
        this.regWorkflowStatus = regWorkflowStatus;
    }


    /**
     * Gets the scheduledEpoch value for this Registration.
     * 
     * @return scheduledEpoch
     */
    public gov.nih.nci.cabig.ccts.domain.ScheduledEpochType getScheduledEpoch() {
        return scheduledEpoch;
    }


    /**
     * Sets the scheduledEpoch value for this Registration.
     * 
     * @param scheduledEpoch
     */
    public void setScheduledEpoch(gov.nih.nci.cabig.ccts.domain.ScheduledEpochType scheduledEpoch) {
        this.scheduledEpoch = scheduledEpoch;
    }


    /**
     * Gets the stratumGroup value for this Registration.
     * 
     * @return stratumGroup
     */
    public java.lang.String getStratumGroup() {
        return stratumGroup;
    }


    /**
     * Sets the stratumGroup value for this Registration.
     * 
     * @param stratumGroup
     */
    public void setStratumGroup(java.lang.String stratumGroup) {
        this.stratumGroup = stratumGroup;
    }


    /**
     * Gets the offStudyReasonText value for this Registration.
     * 
     * @return offStudyReasonText
     */
    public java.lang.String getOffStudyReasonText() {
        return offStudyReasonText;
    }


    /**
     * Sets the offStudyReasonText value for this Registration.
     * 
     * @param offStudyReasonText
     */
    public void setOffStudyReasonText(java.lang.String offStudyReasonText) {
        this.offStudyReasonText = offStudyReasonText;
    }


    /**
     * Gets the offStudyDate value for this Registration.
     * 
     * @return offStudyDate
     */
    public java.lang.String getOffStudyDate() {
        return offStudyDate;
    }


    /**
     * Sets the offStudyDate value for this Registration.
     * 
     * @param offStudyDate
     */
    public void setOffStudyDate(java.lang.String offStudyDate) {
        this.offStudyDate = offStudyDate;
    }


    /**
     * Gets the gridId value for this Registration.
     * 
     * @return gridId
     */
    public java.lang.String getGridId() {
        return gridId;
    }


    /**
     * Sets the gridId value for this Registration.
     * 
     * @param gridId
     */
    public void setGridId(java.lang.String gridId) {
        this.gridId = gridId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Registration)) return false;
        Registration other = (Registration) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.studySite==null && other.getStudySite()==null) || 
             (this.studySite!=null &&
              this.studySite.equals(other.getStudySite()))) &&
            ((this.studyRef==null && other.getStudyRef()==null) || 
             (this.studyRef!=null &&
              this.studyRef.equals(other.getStudyRef()))) &&
            ((this.participant==null && other.getParticipant()==null) || 
             (this.participant!=null &&
              this.participant.equals(other.getParticipant()))) &&
            ((this.startDate==null && other.getStartDate()==null) || 
             (this.startDate!=null &&
              this.startDate.equals(other.getStartDate()))) &&
            ((this.informedConsentFormSignedDate==null && other.getInformedConsentFormSignedDate()==null) || 
             (this.informedConsentFormSignedDate!=null &&
              this.informedConsentFormSignedDate.equals(other.getInformedConsentFormSignedDate()))) &&
            ((this.informedConsentVersion==null && other.getInformedConsentVersion()==null) || 
             (this.informedConsentVersion!=null &&
              this.informedConsentVersion.equals(other.getInformedConsentVersion()))) &&
            ((this.identifier==null && other.getIdentifier()==null) || 
             (this.identifier!=null &&
              java.util.Arrays.equals(this.identifier, other.getIdentifier()))) &&
            ((this.regDataEntryStatus==null && other.getRegDataEntryStatus()==null) || 
             (this.regDataEntryStatus!=null &&
              this.regDataEntryStatus.equals(other.getRegDataEntryStatus()))) &&
            ((this.regWorkflowStatus==null && other.getRegWorkflowStatus()==null) || 
             (this.regWorkflowStatus!=null &&
              this.regWorkflowStatus.equals(other.getRegWorkflowStatus()))) &&
            ((this.scheduledEpoch==null && other.getScheduledEpoch()==null) || 
             (this.scheduledEpoch!=null &&
              this.scheduledEpoch.equals(other.getScheduledEpoch()))) &&
            ((this.stratumGroup==null && other.getStratumGroup()==null) || 
             (this.stratumGroup!=null &&
              this.stratumGroup.equals(other.getStratumGroup()))) &&
            ((this.offStudyReasonText==null && other.getOffStudyReasonText()==null) || 
             (this.offStudyReasonText!=null &&
              this.offStudyReasonText.equals(other.getOffStudyReasonText()))) &&
            ((this.offStudyDate==null && other.getOffStudyDate()==null) || 
             (this.offStudyDate!=null &&
              this.offStudyDate.equals(other.getOffStudyDate()))) &&
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
        if (getStudySite() != null) {
            _hashCode += getStudySite().hashCode();
        }
        if (getStudyRef() != null) {
            _hashCode += getStudyRef().hashCode();
        }
        if (getParticipant() != null) {
            _hashCode += getParticipant().hashCode();
        }
        if (getStartDate() != null) {
            _hashCode += getStartDate().hashCode();
        }
        if (getInformedConsentFormSignedDate() != null) {
            _hashCode += getInformedConsentFormSignedDate().hashCode();
        }
        if (getInformedConsentVersion() != null) {
            _hashCode += getInformedConsentVersion().hashCode();
        }
        if (getIdentifier() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getIdentifier());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getIdentifier(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getRegDataEntryStatus() != null) {
            _hashCode += getRegDataEntryStatus().hashCode();
        }
        if (getRegWorkflowStatus() != null) {
            _hashCode += getRegWorkflowStatus().hashCode();
        }
        if (getScheduledEpoch() != null) {
            _hashCode += getScheduledEpoch().hashCode();
        }
        if (getStratumGroup() != null) {
            _hashCode += getStratumGroup().hashCode();
        }
        if (getOffStudyReasonText() != null) {
            _hashCode += getOffStudyReasonText().hashCode();
        }
        if (getOffStudyDate() != null) {
            _hashCode += getOffStudyDate().hashCode();
        }
        if (getGridId() != null) {
            _hashCode += getGridId().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Registration.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "Registration"));
        org.apache.axis.description.AttributeDesc attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("gridId");
        attrField.setXmlName(new javax.xml.namespace.QName("", "gridId"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(attrField);
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("studySite");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "studySite"));
        elemField.setXmlType(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "StudySiteType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("studyRef");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "studyRef"));
        elemField.setXmlType(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "StudyRefType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("participant");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "participant"));
        elemField.setXmlType(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "ParticipantType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("startDate");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "startDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("informedConsentFormSignedDate");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "informedConsentFormSignedDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("informedConsentVersion");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "informedConsentVersion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("identifier");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "identifier"));
        elemField.setXmlType(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "IdentifierType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("regDataEntryStatus");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "regDataEntryStatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "RegDataEntryStatusType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("regWorkflowStatus");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "regWorkflowStatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "RegWorkflowStatusType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("scheduledEpoch");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "scheduledEpoch"));
        elemField.setXmlType(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "ScheduledEpochType"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("stratumGroup");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "stratumGroup"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("offStudyReasonText");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "offStudyReasonText"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("offStudyDate");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "offStudyDate"));
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
