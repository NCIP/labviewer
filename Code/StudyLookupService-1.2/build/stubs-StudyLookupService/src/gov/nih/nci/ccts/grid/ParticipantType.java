/**
 * ParticipantType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package gov.nih.nci.ccts.grid;

public class ParticipantType  implements java.io.Serializable {
    private java.lang.String firstName;
    private java.lang.String lastName;
    private gov.nih.nci.ccts.grid.AddressType address;
    private java.lang.String administrativeGenderCode;
    private java.util.Date birthDate;
    private java.lang.String ethnicGroupCode;
    private java.lang.String maritalStatusCode;
    private java.lang.String raceCode;
    private gov.nih.nci.ccts.grid.IdentifierType[] identifier;
    private java.lang.String gridId;  // attribute

    public ParticipantType() {
    }

    public ParticipantType(
           gov.nih.nci.ccts.grid.AddressType address,
           java.lang.String administrativeGenderCode,
           java.util.Date birthDate,
           java.lang.String ethnicGroupCode,
           java.lang.String firstName,
           java.lang.String gridId,
           gov.nih.nci.ccts.grid.IdentifierType[] identifier,
           java.lang.String lastName,
           java.lang.String maritalStatusCode,
           java.lang.String raceCode) {
           this.firstName = firstName;
           this.lastName = lastName;
           this.address = address;
           this.administrativeGenderCode = administrativeGenderCode;
           this.birthDate = birthDate;
           this.ethnicGroupCode = ethnicGroupCode;
           this.maritalStatusCode = maritalStatusCode;
           this.raceCode = raceCode;
           this.identifier = identifier;
           this.gridId = gridId;
    }


    /**
     * Gets the firstName value for this ParticipantType.
     * 
     * @return firstName
     */
    public java.lang.String getFirstName() {
        return firstName;
    }


    /**
     * Sets the firstName value for this ParticipantType.
     * 
     * @param firstName
     */
    public void setFirstName(java.lang.String firstName) {
        this.firstName = firstName;
    }


    /**
     * Gets the lastName value for this ParticipantType.
     * 
     * @return lastName
     */
    public java.lang.String getLastName() {
        return lastName;
    }


    /**
     * Sets the lastName value for this ParticipantType.
     * 
     * @param lastName
     */
    public void setLastName(java.lang.String lastName) {
        this.lastName = lastName;
    }


    /**
     * Gets the address value for this ParticipantType.
     * 
     * @return address
     */
    public gov.nih.nci.ccts.grid.AddressType getAddress() {
        return address;
    }


    /**
     * Sets the address value for this ParticipantType.
     * 
     * @param address
     */
    public void setAddress(gov.nih.nci.ccts.grid.AddressType address) {
        this.address = address;
    }


    /**
     * Gets the administrativeGenderCode value for this ParticipantType.
     * 
     * @return administrativeGenderCode
     */
    public java.lang.String getAdministrativeGenderCode() {
        return administrativeGenderCode;
    }


    /**
     * Sets the administrativeGenderCode value for this ParticipantType.
     * 
     * @param administrativeGenderCode
     */
    public void setAdministrativeGenderCode(java.lang.String administrativeGenderCode) {
        this.administrativeGenderCode = administrativeGenderCode;
    }


    /**
     * Gets the birthDate value for this ParticipantType.
     * 
     * @return birthDate
     */
    public java.util.Date getBirthDate() {
        return birthDate;
    }


    /**
     * Sets the birthDate value for this ParticipantType.
     * 
     * @param birthDate
     */
    public void setBirthDate(java.util.Date birthDate) {
        this.birthDate = birthDate;
    }


    /**
     * Gets the ethnicGroupCode value for this ParticipantType.
     * 
     * @return ethnicGroupCode
     */
    public java.lang.String getEthnicGroupCode() {
        return ethnicGroupCode;
    }


    /**
     * Sets the ethnicGroupCode value for this ParticipantType.
     * 
     * @param ethnicGroupCode
     */
    public void setEthnicGroupCode(java.lang.String ethnicGroupCode) {
        this.ethnicGroupCode = ethnicGroupCode;
    }


    /**
     * Gets the maritalStatusCode value for this ParticipantType.
     * 
     * @return maritalStatusCode
     */
    public java.lang.String getMaritalStatusCode() {
        return maritalStatusCode;
    }


    /**
     * Sets the maritalStatusCode value for this ParticipantType.
     * 
     * @param maritalStatusCode
     */
    public void setMaritalStatusCode(java.lang.String maritalStatusCode) {
        this.maritalStatusCode = maritalStatusCode;
    }


    /**
     * Gets the raceCode value for this ParticipantType.
     * 
     * @return raceCode
     */
    public java.lang.String getRaceCode() {
        return raceCode;
    }


    /**
     * Sets the raceCode value for this ParticipantType.
     * 
     * @param raceCode
     */
    public void setRaceCode(java.lang.String raceCode) {
        this.raceCode = raceCode;
    }


    /**
     * Gets the identifier value for this ParticipantType.
     * 
     * @return identifier
     */
    public gov.nih.nci.ccts.grid.IdentifierType[] getIdentifier() {
        return identifier;
    }


    /**
     * Sets the identifier value for this ParticipantType.
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
     * Gets the gridId value for this ParticipantType.
     * 
     * @return gridId
     */
    public java.lang.String getGridId() {
        return gridId;
    }


    /**
     * Sets the gridId value for this ParticipantType.
     * 
     * @param gridId
     */
    public void setGridId(java.lang.String gridId) {
        this.gridId = gridId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ParticipantType)) return false;
        ParticipantType other = (ParticipantType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.firstName==null && other.getFirstName()==null) || 
             (this.firstName!=null &&
              this.firstName.equals(other.getFirstName()))) &&
            ((this.lastName==null && other.getLastName()==null) || 
             (this.lastName!=null &&
              this.lastName.equals(other.getLastName()))) &&
            ((this.address==null && other.getAddress()==null) || 
             (this.address!=null &&
              this.address.equals(other.getAddress()))) &&
            ((this.administrativeGenderCode==null && other.getAdministrativeGenderCode()==null) || 
             (this.administrativeGenderCode!=null &&
              this.administrativeGenderCode.equals(other.getAdministrativeGenderCode()))) &&
            ((this.birthDate==null && other.getBirthDate()==null) || 
             (this.birthDate!=null &&
              this.birthDate.equals(other.getBirthDate()))) &&
            ((this.ethnicGroupCode==null && other.getEthnicGroupCode()==null) || 
             (this.ethnicGroupCode!=null &&
              this.ethnicGroupCode.equals(other.getEthnicGroupCode()))) &&
            ((this.maritalStatusCode==null && other.getMaritalStatusCode()==null) || 
             (this.maritalStatusCode!=null &&
              this.maritalStatusCode.equals(other.getMaritalStatusCode()))) &&
            ((this.raceCode==null && other.getRaceCode()==null) || 
             (this.raceCode!=null &&
              this.raceCode.equals(other.getRaceCode()))) &&
            ((this.identifier==null && other.getIdentifier()==null) || 
             (this.identifier!=null &&
              java.util.Arrays.equals(this.identifier, other.getIdentifier()))) &&
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
        if (getFirstName() != null) {
            _hashCode += getFirstName().hashCode();
        }
        if (getLastName() != null) {
            _hashCode += getLastName().hashCode();
        }
        if (getAddress() != null) {
            _hashCode += getAddress().hashCode();
        }
        if (getAdministrativeGenderCode() != null) {
            _hashCode += getAdministrativeGenderCode().hashCode();
        }
        if (getBirthDate() != null) {
            _hashCode += getBirthDate().hashCode();
        }
        if (getEthnicGroupCode() != null) {
            _hashCode += getEthnicGroupCode().hashCode();
        }
        if (getMaritalStatusCode() != null) {
            _hashCode += getMaritalStatusCode().hashCode();
        }
        if (getRaceCode() != null) {
            _hashCode += getRaceCode().hashCode();
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
        if (getGridId() != null) {
            _hashCode += getGridId().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ParticipantType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "ParticipantType"));
        org.apache.axis.description.AttributeDesc attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("gridId");
        attrField.setXmlName(new javax.xml.namespace.QName("", "gridId"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(attrField);
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("firstName");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "firstName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lastName");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "lastName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("address");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "address"));
        elemField.setXmlType(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "AddressType"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("administrativeGenderCode");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "administrativeGenderCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("birthDate");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "birthDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ethnicGroupCode");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "ethnicGroupCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("maritalStatusCode");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "maritalStatusCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("raceCode");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "raceCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("identifier");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "identifier"));
        elemField.setXmlType(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "IdentifierType"));
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
