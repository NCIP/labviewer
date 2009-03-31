<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:po="http://po.coppa.nci.nih.gov" xmlns:cax="http://caXchange.nci.nih.gov/messaging" xmlns:ISO="uri:iso.org:21090">
<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
<xsl:template match="po:playerIdentifier" >
<Id xmlns="http://po.coppa.nci.nih.gov" xmlns:ISO="uri:iso.org:21090" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"> 
<xsl:attribute name="root" >
<xsl:apply-templates select="@root" />
</xsl:attribute>
<xsl:attribute name="extension" >
<xsl:apply-templates select="@extension" />
</xsl:attribute> 
</Id> 
</xsl:template> 
<xsl:template match="/">
<xsl:if test="count(//po:playerIdentifier)=1">
<ns1:caXchangeRequestMessage xmlns:ns1="http://caXchange.nci.nih.gov/messaging">
<ns1:metadata>
<ns1:transactionControl xsi:nil="true" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"/>
<ns1:serviceType>ORGANIZATION</ns1:serviceType>
<ns1:operationName>getById</ns1:operationName>
<ns1:externalIdentifier/>
<ns1:caXchangeIdentifier/>
<ns1:credentials>
<ns1:userName/>
<ns1:gridIdentifier/>
<ns1:password/>
<ns1:delegatedCredentialReference/>
</ns1:credentials>
</ns1:metadata>
<ns1:request>
<ns1:businessMessagePayload>
<ns1:xmlSchemaDefinition>http://po.coppa.nci.nih.gov</ns1:xmlSchemaDefinition>
<xsl:apply-templates select="//po:playerIdentifier" />
</ns1:businessMessagePayload>
</ns1:request>
</ns1:caXchangeRequestMessage>
</xsl:if>
<xsl:if test="count(/targetResponse[targetMessageStatus='ERROR']) = 1">
<targetResponse>
    <targetServiceIdentifier>ORGANIZATION_BUSINESS_SERVICE</targetServiceIdentifier>
    <targetServiceOperation>getOrganizationByCTEPId</targetServiceOperation>
    <targetMessageStatus>ERROR</targetMessageStatus>
    <xsl:copy-of select="//targetError" />
</targetResponse>
</xsl:if>
<xsl:if test="(count(//po:playerIdentifier)!=1)and(count(/targetResponse[targetMessageStatus='ERROR'])!=1)">
<targetResponse>
    <targetServiceIdentifier>ORGANIZATION_BUSINESS_SERVICE</targetServiceIdentifier>
    <targetServiceOperation>getOrganizationByCTEPId</targetServiceOperation>
    <targetMessageStatus>ERROR</targetMessageStatus>
    <targetError>
        <errorCode>001</errorCode>
        <errorDescription>CTEP Identifier not found.</errorDescription>
    </targetError>
</targetResponse>
</xsl:if>
</xsl:template>
</xsl:stylesheet>
