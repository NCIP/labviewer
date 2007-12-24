Note: Copy schema related file from HL7 CVS directory

:ssh:echen@cvs.hl7.nscee.edu:/home/hl7/cvs/v3-editions/input/domains/rp/spl-edition.zip


In order to work with Introduce, I modify the following:

//1. datatypes-base.xsd

//1.1 add xmlns:tns="http://hl7v3datatype.cabig.nci.nih.gov" and targetNamespace="http://hl7v3datatype.cabig.nci.nih.gov"
//at xs:schema element

//1.2 add <xs:element name="BL" type="tns:BL"/>
