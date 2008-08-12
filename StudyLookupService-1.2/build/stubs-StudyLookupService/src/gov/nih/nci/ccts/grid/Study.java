/**
 * Study.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package gov.nih.nci.ccts.grid;

public class Study  implements java.io.Serializable {
    private java.lang.String blindedIndicator;
    private java.lang.String multiInstitutionIndicator;
    private java.lang.String randomizedIndicator;
    private java.lang.String shortTitleText;
    private java.lang.String longTitleText;
    private java.lang.String descriptionText;
    private java.lang.String precisText;
    private java.lang.String phaseCode;
    private gov.nih.nci.ccts.grid.StudyDataEntryStatusType dataEntryStatus;
    private gov.nih.nci.ccts.grid.CoordinatingCenterStudyStatusType coordinatingCenterStudyStatus;
    private java.lang.String type;
    private java.lang.String primaryIdentifier;
    private java.math.BigInteger targetAccrualNumber;
    private gov.nih.nci.ccts.grid.IdentifierType[] identifier;
    private gov.nih.nci.ccts.grid.EpochType[] epoch;
    private gov.nih.nci.ccts.grid.StudyOrganizationType[] studyOrganization;
    private java.lang.String gridId;  // attribute
    private java.math.BigInteger version;  // attribute

    public Study() {
    }

    public Study(
           java.lang.String blindedIndicator,
           gov.nih.nci.ccts.grid.CoordinatingCenterStudyStatusType coordinatingCenterStudyStatus,
           gov.nih.nci.ccts.grid.StudyDataEntryStatusType dataEntryStatus,
           java.lang.String descriptionText,
           gov.nih.nci.ccts.grid.EpochType[] epoch,
           java.lang.String gridId,
           gov.nih.nci.ccts.grid.IdentifierType[] identifier,
           java.lang.String longTitleText,
           java.lang.String multiInstitutionIndicator,
           java.lang.String phaseCode,
           java.lang.String precisText,
           java.lang.String primaryIdentifier,
           java.lang.String randomizedIndicator,
           java.lang.String shortTitleText,
           gov.nih.nci.ccts.grid.StudyOrganizationType[] studyOrganization,
           java.math.BigInteger targetAccrualNumber,
           java.lang.String type,
           java.math.BigInteger version) {
           this.blindedIndicator = blindedIndicator;
           this.multiInstitutionIndicator = multiInstitutionIndicator;
           this.randomizedIndicator = randomizedIndicator;
           this.shortTitleText = shortTitleText;
           this.longTitleText = longTitleText;
           this.descriptionText = descriptionText;
           this.precisText = precisText;
           this.phaseCode = phaseCode;
           this.dataEntryStatus = dataEntryStatus;
           this.coordinatingCenterStudyStatus = coordinatingCenterStudyStatus;
           this.type = type;
           this.primaryIdentifier = primaryIdentifier;
           this.targetAccrualNumber = targetAccrualNumber;
           this.identifier = identifier;
           this.epoch = epoch;
           this.studyOrganization = studyOrganization;
           this.gridId = gridId;
           this.version = version;
    }


    /**
     * Gets the blindedIndicator value for this Study.
     * 
     * @return blindedIndicator
     */
    public java.lang.String getBlindedIndicator() {
        return blindedIndicator;
    }


    /**
     * Sets the blindedIndicator value for this Study.
     * 
     * @param blindedIndicator
     */
    public void setBlindedIndicator(java.lang.String blindedIndicator) {
        this.blindedIndicator = blindedIndicator;
    }


    /**
     * Gets the multiInstitutionIndicator value for this Study.
     * 
     * @return multiInstitutionIndicator
     */
    public java.lang.String getMultiInstitutionIndicator() {
        return multiInstitutionIndicator;
    }


    /**
     * Sets the multiInstitutionIndicator value for this Study.
     * 
     * @param multiInstitutionIndicator
     */
    public void setMultiInstitutionIndicator(java.lang.String multiInstitutionIndicator) {
        this.multiInstitutionIndicator = multiInstitutionIndicator;
    }


    /**
     * Gets the randomizedIndicator value for this Study.
     * 
     * @return randomizedIndicator
     */
    public java.lang.String getRandomizedIndicator() {
        return randomizedIndicator;
    }


    /**
     * Sets the randomizedIndicator value for this Study.
     * 
     * @param randomizedIndicator
     */
    public void setRandomizedIndicator(java.lang.String randomizedIndicator) {
        this.randomizedIndicator = randomizedIndicator;
    }


    /**
     * Gets the shortTitleText value for this Study.
     * 
     * @return shortTitleText
     */
    public java.lang.String getShortTitleText() {
        return shortTitleText;
    }


    /**
     * Sets the shortTitleText value for this Study.
     * 
     * @param shortTitleText
     */
    public void setShortTitleText(java.lang.String shortTitleText) {
        this.shortTitleText = shortTitleText;
    }


    /**
     * Gets the longTitleText value for this Study.
     * 
     * @return longTitleText
     */
    public java.lang.String getLongTitleText() {
        return longTitleText;
    }


    /**
     * Sets the longTitleText value for this Study.
     * 
     * @param longTitleText
     */
    public void setLongTitleText(java.lang.String longTitleText) {
        this.longTitleText = longTitleText;
    }


    /**
     * Gets the descriptionText value for this Study.
     * 
     * @return descriptionText
     */
    public java.lang.String getDescriptionText() {
        return descriptionText;
    }


    /**
     * Sets the descriptionText value for this Study.
     * 
     * @param descriptionText
     */
    public void setDescriptionText(java.lang.String descriptionText) {
        this.descriptionText = descriptionText;
    }


    /**
     * Gets the precisText value for this Study.
     * 
     * @return precisText
     */
    public java.lang.String getPrecisText() {
        return precisText;
    }


    /**
     * Sets the precisText value for this Study.
     * 
     * @param precisText
     */
    public void setPrecisText(java.lang.String precisText) {
        this.precisText = precisText;
    }


    /**
     * Gets the phaseCode value for this Study.
     * 
     * @return phaseCode
     */
    public java.lang.String getPhaseCode() {
        return phaseCode;
    }


    /**
     * Sets the phaseCode value for this Study.
     * 
     * @param phaseCode
     */
    public void setPhaseCode(java.lang.String phaseCode) {
        this.phaseCode = phaseCode;
    }


    /**
     * Gets the dataEntryStatus value for this Study.
     * 
     * @return dataEntryStatus
     */
    public gov.nih.nci.ccts.grid.StudyDataEntryStatusType getDataEntryStatus() {
        return dataEntryStatus;
    }


    /**
     * Sets the dataEntryStatus value for this Study.
     * 
     * @param dataEntryStatus
     */
    public void setDataEntryStatus(gov.nih.nci.ccts.grid.StudyDataEntryStatusType dataEntryStatus) {
        this.dataEntryStatus = dataEntryStatus;
    }


    /**
     * Gets the coordinatingCenterStudyStatus value for this Study.
     * 
     * @return coordinatingCenterStudyStatus
     */
    public gov.nih.nci.ccts.grid.CoordinatingCenterStudyStatusType getCoordinatingCenterStudyStatus() {
        return coordinatingCenterStudyStatus;
    }


    /**
     * Sets the coordinatingCenterStudyStatus value for this Study.
     * 
     * @param coordinatingCenterStudyStatus
     */
    public void setCoordinatingCenterStudyStatus(gov.nih.nci.ccts.grid.CoordinatingCenterStudyStatusType coordinatingCenterStudyStatus) {
        this.coordinatingCenterStudyStatus = coordinatingCenterStudyStatus;
    }


    /**
     * Gets the type value for this Study.
     * 
     * @return type
     */
    public java.lang.String getType() {
        return type;
    }


    /**
     * Sets the type value for this Study.
     * 
     * @param type
     */
    public void setType(java.lang.String type) {
        this.type = type;
    }


    /**
     * Gets the primaryIdentifier value for this Study.
     * 
     * @return primaryIdentifier
     */
    public java.lang.String getPrimaryIdentifier() {
        return primaryIdentifier;
    }


    /**
     * Sets the primaryIdentifier value for this Study.
     * 
     * @param primaryIdentifier
     */
    public void setPrimaryIdentifier(java.lang.String primaryIdentifier) {
        this.primaryIdentifier = primaryIdentifier;
    }


    /**
     * Gets the targetAccrualNumber value for this Study.
     * 
     * @return targetAccrualNumber
     */
    public java.math.BigInteger getTargetAccrualNumber() {
        return targetAccrualNumber;
    }


    /**
     * Sets the targetAccrualNumber value for this Study.
     * 
     * @param targetAccrualNumber
     */
    public void setTargetAccrualNumber(java.math.BigInteger targetAccrualNumber) {
        this.targetAccrualNumber = targetAccrualNumber;
    }


    /**
     * Gets the identifier value for this Study.
     * 
     * @return identifier
     */
    public gov.nih.nci.ccts.grid.IdentifierType[] getIdentifier() {
        return identifier;
    }


    /**
     * Sets the identifier value for this Study.
     * 
     * @param identifier
     */
    public void setIdentifier(gov.nih.nci.ccts.grid.IdentifierType[] identifier) {
        this.identifier = identifier;
    }

    public gov.nih.nci.ccts.grid.IdentifierType getIdentifier(int i) {
        return this.identifier[i];
    }

    public void setIdentifier(int i, gov.nih.nci.ccts.grid.IdentifierType _value) {
        this.identifier[i] = _value;
    }


    /**
     * Gets the epoch value for this Study.
     * 
     * @return epoch
     */
    public gov.nih.nci.ccts.grid.EpochType[] getEpoch() {
        return epoch;
    }


    /**
     * Sets the epoch value for this Study.
     * 
     * @param epoch
     */
    public void setEpoch(gov.nih.nci.ccts.grid.EpochType[] epoch) {
        this.epoch = epoch;
    }

    public gov.nih.nci.ccts.grid.EpochType getEpoch(int i) {
        return this.epoch[i];
    }

    public void setEpoch(int i, gov.nih.nci.ccts.grid.EpochType _value) {
        this.epoch[i] = _value;
    }


    /**
     * Gets the studyOrganization value for this Study.
     * 
     * @return studyOrganization
     */
    public gov.nih.nci.ccts.grid.StudyOrganizationType[] getStudyOrganization() {
        return studyOrganization;
    }


    /**
     * Sets the studyOrganization value for this Study.
     * 
     * @param studyOrganization
     */
    public void setStudyOrganization(gov.nih.nci.ccts.grid.StudyOrganizationType[] studyOrganization) {
        this.studyOrganization = studyOrganization;
    }

    public gov.nih.nci.ccts.grid.StudyOrganizationType getStudyOrganization(int i) {
        return this.studyOrganization[i];
    }

    public void setStudyOrganization(int i, gov.nih.nci.ccts.grid.StudyOrganizationType _value) {
        this.studyOrganization[i] = _value;
    }


    /**
     * Gets the gridId value for this Study.
     * 
     * @return gridId
     */
    public java.lang.String getGridId() {
        return gridId;
    }


    /**
     * Sets the gridId value for this Study.
     * 
     * @param gridId
     */
    public void setGridId(java.lang.String gridId) {
        this.gridId = gridId;
    }


    /**
     * Gets the version value for this Study.
     * 
     * @return version
     */
    public java.math.BigInteger getVersion() {
        return version;
    }


    /**
     * Sets the version value for this Study.
     * 
     * @param version
     */
    public void setVersion(java.math.BigInteger version) {
        this.version = version;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Study)) return false;
        Study other = (Study) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.blindedIndicator==null && other.getBlindedIndicator()==null) || 
             (this.blindedIndicator!=null &&
              this.blindedIndicator.equals(other.getBlindedIndicator()))) &&
            ((this.multiInstitutionIndicator==null && other.getMultiInstitutionIndicator()==null) || 
             (this.multiInstitutionIndicator!=null &&
              this.multiInstitutionIndicator.equals(other.getMultiInstitutionIndicator()))) &&
            ((this.randomizedIndicator==null && other.getRandomizedIndicator()==null) || 
             (this.randomizedIndicator!=null &&
              this.randomizedIndicator.equals(other.getRandomizedIndicator()))) &&
            ((this.shortTitleText==null && other.getShortTitleText()==null) || 
             (this.shortTitleText!=null &&
              this.shortTitleText.equals(other.getShortTitleText()))) &&
            ((this.longTitleText==null && other.getLongTitleText()==null) || 
             (this.longTitleText!=null &&
              this.longTitleText.equals(other.getLongTitleText()))) &&
            ((this.descriptionText==null && other.getDescriptionText()==null) || 
             (this.descriptionText!=null &&
              this.descriptionText.equals(other.getDescriptionText()))) &&
            ((this.precisText==null && other.getPrecisText()==null) || 
             (this.precisText!=null &&
              this.precisText.equals(other.getPrecisText()))) &&
            ((this.phaseCode==null && other.getPhaseCode()==null) || 
             (this.phaseCode!=null &&
              this.phaseCode.equals(other.getPhaseCode()))) &&
            ((this.dataEntryStatus==null && other.getDataEntryStatus()==null) || 
             (this.dataEntryStatus!=null &&
              this.dataEntryStatus.equals(other.getDataEntryStatus()))) &&
            ((this.coordinatingCenterStudyStatus==null && other.getCoordinatingCenterStudyStatus()==null) || 
             (this.coordinatingCenterStudyStatus!=null &&
              this.coordinatingCenterStudyStatus.equals(other.getCoordinatingCenterStudyStatus()))) &&
            ((this.type==null && other.getType()==null) || 
             (this.type!=null &&
              this.type.equals(other.getType()))) &&
            ((this.primaryIdentifier==null && other.getPrimaryIdentifier()==null) || 
             (this.primaryIdentifier!=null &&
              this.primaryIdentifier.equals(other.getPrimaryIdentifier()))) &&
            ((this.targetAccrualNumber==null && other.getTargetAccrualNumber()==null) || 
             (this.targetAccrualNumber!=null &&
              this.targetAccrualNumber.equals(other.getTargetAccrualNumber()))) &&
            ((this.identifier==null && other.getIdentifier()==null) || 
             (this.identifier!=null &&
              java.util.Arrays.equals(this.identifier, other.getIdentifier()))) &&
            ((this.epoch==null && other.getEpoch()==null) || 
             (this.epoch!=null &&
              java.util.Arrays.equals(this.epoch, other.getEpoch()))) &&
            ((this.studyOrganization==null && other.getStudyOrganization()==null) || 
             (this.studyOrganization!=null &&
              java.util.Arrays.equals(this.studyOrganization, other.getStudyOrganization()))) &&
            ((this.gridId==null && other.getGridId()==null) || 
             (this.gridId!=null &&
              this.gridId.equals(other.getGridId()))) &&
            ((this.version==null && other.getVersion()==null) || 
             (this.version!=null &&
              this.version.equals(other.getVersion())));
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
        if (getBlindedIndicator() != null) {
            _hashCode += getBlindedIndicator().hashCode();
        }
        if (getMultiInstitutionIndicator() != null) {
            _hashCode += getMultiInstitutionIndicator().hashCode();
        }
        if (getRandomizedIndicator() != null) {
            _hashCode += getRandomizedIndicator().hashCode();
        }
        if (getShortTitleText() != null) {
            _hashCode += getShortTitleText().hashCode();
        }
        if (getLongTitleText() != null) {
            _hashCode += getLongTitleText().hashCode();
        }
        if (getDescriptionText() != null) {
            _hashCode += getDescriptionText().hashCode();
        }
        if (getPrecisText() != null) {
            _hashCode += getPrecisText().hashCode();
        }
        if (getPhaseCode() != null) {
            _hashCode += getPhaseCode().hashCode();
        }
        if (getDataEntryStatus() != null) {
            _hashCode += getDataEntryStatus().hashCode();
        }
        if (getCoordinatingCenterStudyStatus() != null) {
            _hashCode += getCoordinatingCenterStudyStatus().hashCode();
        }
        if (getType() != null) {
            _hashCode += getType().hashCode();
        }
        if (getPrimaryIdentifier() != null) {
            _hashCode += getPrimaryIdentifier().hashCode();
        }
        if (getTargetAccrualNumber() != null) {
            _hashCode += getTargetAccrualNumber().hashCode();
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
        if (getEpoch() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getEpoch());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getEpoch(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getStudyOrganization() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getStudyOrganization());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getStudyOrganization(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getGridId() != null) {
            _hashCode += getGridId().hashCode();
        }
        if (getVersion() != null) {
            _hashCode += getVersion().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Study.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "Study"));
        org.apache.axis.description.AttributeDesc attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("gridId");
        attrField.setXmlName(new javax.xml.namespace.QName("", "gridId"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("version");
        attrField.setXmlName(new javax.xml.namespace.QName("", "version"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        typeDesc.addFieldDesc(attrField);
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("blindedIndicator");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "blindedIndicator"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("multiInstitutionIndicator");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "multiInstitutionIndicator"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("randomizedIndicator");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "randomizedIndicator"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("shortTitleText");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "shortTitleText"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("longTitleText");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "longTitleText"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descriptionText");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "descriptionText"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("precisText");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "precisText"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("phaseCode");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "phaseCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataEntryStatus");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "dataEntryStatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "StudyDataEntryStatusType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("coordinatingCenterStudyStatus");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "coordinatingCenterStudyStatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "CoordinatingCenterStudyStatusType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("type");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "type"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("primaryIdentifier");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "primaryIdentifier"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("targetAccrualNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "targetAccrualNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("identifier");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "identifier"));
        elemField.setXmlType(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "IdentifierType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("epoch");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "epoch"));
        elemField.setXmlType(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "EpochType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("studyOrganization");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "studyOrganization"));
        elemField.setXmlType(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "StudyOrganizationType"));
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
