/**
 * CreateStudyRequestStudy.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package gov.nih.nci.ccts.grid.stubs;

public class CreateStudyRequestStudy  implements java.io.Serializable {
    private gov.nih.nci.ccts.grid.Study study;

    public CreateStudyRequestStudy() {
    }

    public CreateStudyRequestStudy(
           gov.nih.nci.ccts.grid.Study study) {
           this.study = study;
    }


    /**
     * Gets the study value for this CreateStudyRequestStudy.
     * 
     * @return study
     */
    public gov.nih.nci.ccts.grid.Study getStudy() {
        return study;
    }


    /**
     * Sets the study value for this CreateStudyRequestStudy.
     * 
     * @param study
     */
    public void setStudy(gov.nih.nci.ccts.grid.Study study) {
        this.study = study;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CreateStudyRequestStudy)) return false;
        CreateStudyRequestStudy other = (CreateStudyRequestStudy) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.study==null && other.getStudy()==null) || 
             (this.study!=null &&
              this.study.equals(other.getStudy())));
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
        if (getStudy() != null) {
            _hashCode += getStudy().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CreateStudyRequestStudy.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://grid.ccts.nci.nih.gov/StudyConsumer", ">>CreateStudyRequest>study"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("study");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "study"));
        elemField.setXmlType(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "Study"));
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