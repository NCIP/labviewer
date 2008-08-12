/**
 * HealthcareSiteInvestigatorType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package gov.nih.nci.ccts.grid;

public class HealthcareSiteInvestigatorType  implements java.io.Serializable {
    private gov.nih.nci.ccts.grid.InvestigatorType[] investigator;

    public HealthcareSiteInvestigatorType() {
    }

    public HealthcareSiteInvestigatorType(
           gov.nih.nci.ccts.grid.InvestigatorType[] investigator) {
           this.investigator = investigator;
    }


    /**
     * Gets the investigator value for this HealthcareSiteInvestigatorType.
     * 
     * @return investigator
     */
    public gov.nih.nci.ccts.grid.InvestigatorType[] getInvestigator() {
        return investigator;
    }


    /**
     * Sets the investigator value for this HealthcareSiteInvestigatorType.
     * 
     * @param investigator
     */
    public void setInvestigator(gov.nih.nci.ccts.grid.InvestigatorType[] investigator) {
        this.investigator = investigator;
    }

    public gov.nih.nci.ccts.grid.InvestigatorType getInvestigator(int i) {
        return this.investigator[i];
    }

    public void setInvestigator(int i, gov.nih.nci.ccts.grid.InvestigatorType _value) {
        this.investigator[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof HealthcareSiteInvestigatorType)) return false;
        HealthcareSiteInvestigatorType other = (HealthcareSiteInvestigatorType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.investigator==null && other.getInvestigator()==null) || 
             (this.investigator!=null &&
              java.util.Arrays.equals(this.investigator, other.getInvestigator())));
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
        if (getInvestigator() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getInvestigator());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getInvestigator(), i);
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
        new org.apache.axis.description.TypeDesc(HealthcareSiteInvestigatorType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "HealthcareSiteInvestigatorType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("investigator");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "investigator"));
        elemField.setXmlType(new javax.xml.namespace.QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain", "InvestigatorType"));
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
