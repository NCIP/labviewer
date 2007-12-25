refernce: Intorduce 1.1 Tutorial http://www.cagrid.org/mwiki/index.php?title=Introduce:1.1:Tutorial:Phase1

1. Create HL7 data type service
1) Open Introduce, create or modify service called: HL7DataType
2) At Types Tab, import hl7v3datatype.xsd file, which contains II and CD data type
3) At Operations Tab, add two operations: 
	CD echoCD(CD cD)
	II echoII(II iI)
4) Save the HL7 data type service

2.Deploy HL7 data type service to GDID

1) Copy the following libraries to  C:\Projects\caxchange\Code\hl7v3datatypeService\lib directory:

xbean.jar
seriaMif.jar
jsig.jar
hl7datatype.jar

3. Implement data type service
At HL7DataTypeImpl.java, implement CD echoCD(CD cD) and II echoII(II iI) method

4. Deploy HL7 data type service to GLOBUS directory 
