/**
 * Metadata.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package gov.nih.nci.caxchange;

public class Metadata  implements java.io.Serializable {
    private gov.nih.nci.caxchange.Operations operation;
    private gov.nih.nci.caxchange.MessageTypes messageType;
    private java.lang.String externalIdentifier;
    /** Populated by caXchange components, any value provided by the client
 * will be overridden by the caXchange components */
    private java.lang.String caXchangeIdentifier;
    private gov.nih.nci.caxchange.Credentials credentials;
    private java.lang.String[] targetSite;

    public Metadata() {
    }

    public Metadata(
           java.lang.String caXchangeIdentifier,
           gov.nih.nci.caxchange.Credentials credentials,
           java.lang.String externalIdentifier,
           gov.nih.nci.caxchange.MessageTypes messageType,
           gov.nih.nci.caxchange.Operations operation,
           java.lang.String[] targetSite) {
           this.operation = operation;
           this.messageType = messageType;
           this.externalIdentifier = externalIdentifier;
           this.caXchangeIdentifier = caXchangeIdentifier;
           this.credentials = credentials;
           this.targetSite = targetSite;
    }


    /**
     * Gets the operation value for this Metadata.
     * 
     * @return operation
     */
    public gov.nih.nci.caxchange.Operations getOperation() {
        return operation;
    }


    /**
     * Sets the operation value for this Metadata.
     * 
     * @param operation
     */
    public void setOperation(gov.nih.nci.caxchange.Operations operation) {
        this.operation = operation;
    }


    /**
     * Gets the messageType value for this Metadata.
     * 
     * @return messageType
     */
    public gov.nih.nci.caxchange.MessageTypes getMessageType() {
        return messageType;
    }


    /**
     * Sets the messageType value for this Metadata.
     * 
     * @param messageType
     */
    public void setMessageType(gov.nih.nci.caxchange.MessageTypes messageType) {
        this.messageType = messageType;
    }


    /**
     * Gets the externalIdentifier value for this Metadata.
     * 
     * @return externalIdentifier
     */
    public java.lang.String getExternalIdentifier() {
        return externalIdentifier;
    }


    /**
     * Sets the externalIdentifier value for this Metadata.
     * 
     * @param externalIdentifier
     */
    public void setExternalIdentifier(java.lang.String externalIdentifier) {
        this.externalIdentifier = externalIdentifier;
    }


    /**
     * Gets the caXchangeIdentifier value for this Metadata.
     * 
     * @return caXchangeIdentifier Populated by caXchange components, any value provided by the client
 * will be overridden by the caXchange components
     */
    public java.lang.String getCaXchangeIdentifier() {
        return caXchangeIdentifier;
    }


    /**
     * Sets the caXchangeIdentifier value for this Metadata.
     * 
     * @param caXchangeIdentifier Populated by caXchange components, any value provided by the client
 * will be overridden by the caXchange components
     */
    public void setCaXchangeIdentifier(java.lang.String caXchangeIdentifier) {
        this.caXchangeIdentifier = caXchangeIdentifier;
    }


    /**
     * Gets the credentials value for this Metadata.
     * 
     * @return credentials
     */
    public gov.nih.nci.caxchange.Credentials getCredentials() {
        return credentials;
    }


    /**
     * Sets the credentials value for this Metadata.
     * 
     * @param credentials
     */
    public void setCredentials(gov.nih.nci.caxchange.Credentials credentials) {
        this.credentials = credentials;
    }


    /**
     * Gets the targetSite value for this Metadata.
     * 
     * @return targetSite
     */
    public java.lang.String[] getTargetSite() {
        return targetSite;
    }


    /**
     * Sets the targetSite value for this Metadata.
     * 
     * @param targetSite
     */
    public void setTargetSite(java.lang.String[] targetSite) {
        this.targetSite = targetSite;
    }

    public java.lang.String getTargetSite(int i) {
        return this.targetSite[i];
    }

    public void setTargetSite(int i, java.lang.String _value) {
        this.targetSite[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Metadata)) return false;
        Metadata other = (Metadata) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.operation==null && other.getOperation()==null) || 
             (this.operation!=null &&
              this.operation.equals(other.getOperation()))) &&
            ((this.messageType==null && other.getMessageType()==null) || 
             (this.messageType!=null &&
              this.messageType.equals(other.getMessageType()))) &&
            ((this.externalIdentifier==null && other.getExternalIdentifier()==null) || 
             (this.externalIdentifier!=null &&
              this.externalIdentifier.equals(other.getExternalIdentifier()))) &&
            ((this.caXchangeIdentifier==null && other.getCaXchangeIdentifier()==null) || 
             (this.caXchangeIdentifier!=null &&
              this.caXchangeIdentifier.equals(other.getCaXchangeIdentifier()))) &&
            ((this.credentials==null && other.getCredentials()==null) || 
             (this.credentials!=null &&
              this.credentials.equals(other.getCredentials()))) &&
            ((this.targetSite==null && other.getTargetSite()==null) || 
             (this.targetSite!=null &&
              java.util.Arrays.equals(this.targetSite, other.getTargetSite())));
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
        if (getOperation() != null) {
            _hashCode += getOperation().hashCode();
        }
        if (getMessageType() != null) {
            _hashCode += getMessageType().hashCode();
        }
        if (getExternalIdentifier() != null) {
            _hashCode += getExternalIdentifier().hashCode();
        }
        if (getCaXchangeIdentifier() != null) {
            _hashCode += getCaXchangeIdentifier().hashCode();
        }
        if (getCredentials() != null) {
            _hashCode += getCredentials().hashCode();
        }
        if (getTargetSite() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getTargetSite());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getTargetSite(), i);
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
        new org.apache.axis.description.TypeDesc(Metadata.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://caXchange.nci.nih.gov/messaging", "metadata"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("operation");
        elemField.setXmlName(new javax.xml.namespace.QName("http://caXchange.nci.nih.gov/messaging", "operation"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://caXchange.nci.nih.gov/messaging", "operations"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("messageType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://caXchange.nci.nih.gov/messaging", "messageType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://caXchange.nci.nih.gov/messaging", "messageTypes"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("externalIdentifier");
        elemField.setXmlName(new javax.xml.namespace.QName("http://caXchange.nci.nih.gov/messaging", "externalIdentifier"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("caXchangeIdentifier");
        elemField.setXmlName(new javax.xml.namespace.QName("http://caXchange.nci.nih.gov/messaging", "caXchangeIdentifier"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("credentials");
        elemField.setXmlName(new javax.xml.namespace.QName("http://caXchange.nci.nih.gov/messaging", "credentials"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://caXchange.nci.nih.gov/messaging", "credentials"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("targetSite");
        elemField.setXmlName(new javax.xml.namespace.QName("http://caXchange.nci.nih.gov/messaging", "targetSite"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
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
