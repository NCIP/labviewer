/**
 * TargetResponseMessage.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package gov.nih.nci.caxchange;

public class TargetResponseMessage  implements java.io.Serializable {
    private java.lang.String targetServiceIdentifier;
    private java.lang.String targetServiceOperation;
    private gov.nih.nci.caxchange.MessageStatuses targetMessageStatus;
    private gov.nih.nci.caxchange.ErrorDetails targetError;
    private gov.nih.nci.caxchange.MessagePayload targetBusinessMessage;

    public TargetResponseMessage() {
    }

    public TargetResponseMessage(
           gov.nih.nci.caxchange.MessagePayload targetBusinessMessage,
           gov.nih.nci.caxchange.ErrorDetails targetError,
           gov.nih.nci.caxchange.MessageStatuses targetMessageStatus,
           java.lang.String targetServiceIdentifier,
           java.lang.String targetServiceOperation) {
           this.targetServiceIdentifier = targetServiceIdentifier;
           this.targetServiceOperation = targetServiceOperation;
           this.targetMessageStatus = targetMessageStatus;
           this.targetError = targetError;
           this.targetBusinessMessage = targetBusinessMessage;
    }


    /**
     * Gets the targetServiceIdentifier value for this TargetResponseMessage.
     * 
     * @return targetServiceIdentifier
     */
    public java.lang.String getTargetServiceIdentifier() {
        return targetServiceIdentifier;
    }


    /**
     * Sets the targetServiceIdentifier value for this TargetResponseMessage.
     * 
     * @param targetServiceIdentifier
     */
    public void setTargetServiceIdentifier(java.lang.String targetServiceIdentifier) {
        this.targetServiceIdentifier = targetServiceIdentifier;
    }


    /**
     * Gets the targetServiceOperation value for this TargetResponseMessage.
     * 
     * @return targetServiceOperation
     */
    public java.lang.String getTargetServiceOperation() {
        return targetServiceOperation;
    }


    /**
     * Sets the targetServiceOperation value for this TargetResponseMessage.
     * 
     * @param targetServiceOperation
     */
    public void setTargetServiceOperation(java.lang.String targetServiceOperation) {
        this.targetServiceOperation = targetServiceOperation;
    }


    /**
     * Gets the targetMessageStatus value for this TargetResponseMessage.
     * 
     * @return targetMessageStatus
     */
    public gov.nih.nci.caxchange.MessageStatuses getTargetMessageStatus() {
        return targetMessageStatus;
    }


    /**
     * Sets the targetMessageStatus value for this TargetResponseMessage.
     * 
     * @param targetMessageStatus
     */
    public void setTargetMessageStatus(gov.nih.nci.caxchange.MessageStatuses targetMessageStatus) {
        this.targetMessageStatus = targetMessageStatus;
    }


    /**
     * Gets the targetError value for this TargetResponseMessage.
     * 
     * @return targetError
     */
    public gov.nih.nci.caxchange.ErrorDetails getTargetError() {
        return targetError;
    }


    /**
     * Sets the targetError value for this TargetResponseMessage.
     * 
     * @param targetError
     */
    public void setTargetError(gov.nih.nci.caxchange.ErrorDetails targetError) {
        this.targetError = targetError;
    }


    /**
     * Gets the targetBusinessMessage value for this TargetResponseMessage.
     * 
     * @return targetBusinessMessage
     */
    public gov.nih.nci.caxchange.MessagePayload getTargetBusinessMessage() {
        return targetBusinessMessage;
    }


    /**
     * Sets the targetBusinessMessage value for this TargetResponseMessage.
     * 
     * @param targetBusinessMessage
     */
    public void setTargetBusinessMessage(gov.nih.nci.caxchange.MessagePayload targetBusinessMessage) {
        this.targetBusinessMessage = targetBusinessMessage;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TargetResponseMessage)) return false;
        TargetResponseMessage other = (TargetResponseMessage) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.targetServiceIdentifier==null && other.getTargetServiceIdentifier()==null) || 
             (this.targetServiceIdentifier!=null &&
              this.targetServiceIdentifier.equals(other.getTargetServiceIdentifier()))) &&
            ((this.targetServiceOperation==null && other.getTargetServiceOperation()==null) || 
             (this.targetServiceOperation!=null &&
              this.targetServiceOperation.equals(other.getTargetServiceOperation()))) &&
            ((this.targetMessageStatus==null && other.getTargetMessageStatus()==null) || 
             (this.targetMessageStatus!=null &&
              this.targetMessageStatus.equals(other.getTargetMessageStatus()))) &&
            ((this.targetError==null && other.getTargetError()==null) || 
             (this.targetError!=null &&
              this.targetError.equals(other.getTargetError()))) &&
            ((this.targetBusinessMessage==null && other.getTargetBusinessMessage()==null) || 
             (this.targetBusinessMessage!=null &&
              this.targetBusinessMessage.equals(other.getTargetBusinessMessage())));
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
        if (getTargetServiceIdentifier() != null) {
            _hashCode += getTargetServiceIdentifier().hashCode();
        }
        if (getTargetServiceOperation() != null) {
            _hashCode += getTargetServiceOperation().hashCode();
        }
        if (getTargetMessageStatus() != null) {
            _hashCode += getTargetMessageStatus().hashCode();
        }
        if (getTargetError() != null) {
            _hashCode += getTargetError().hashCode();
        }
        if (getTargetBusinessMessage() != null) {
            _hashCode += getTargetBusinessMessage().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TargetResponseMessage.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://caXchange.nci.nih.gov/messaging", "targetResponseMessage"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("targetServiceIdentifier");
        elemField.setXmlName(new javax.xml.namespace.QName("http://caXchange.nci.nih.gov/messaging", "targetServiceIdentifier"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("targetServiceOperation");
        elemField.setXmlName(new javax.xml.namespace.QName("http://caXchange.nci.nih.gov/messaging", "targetServiceOperation"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("targetMessageStatus");
        elemField.setXmlName(new javax.xml.namespace.QName("http://caXchange.nci.nih.gov/messaging", "targetMessageStatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://caXchange.nci.nih.gov/messaging", "messageStatuses"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("targetError");
        elemField.setXmlName(new javax.xml.namespace.QName("http://caXchange.nci.nih.gov/messaging", "targetError"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://caXchange.nci.nih.gov/messaging", "errorDetails"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("targetBusinessMessage");
        elemField.setXmlName(new javax.xml.namespace.QName("http://caXchange.nci.nih.gov/messaging", "targetBusinessMessage"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://caXchange.nci.nih.gov/messaging", "messagePayload"));
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
