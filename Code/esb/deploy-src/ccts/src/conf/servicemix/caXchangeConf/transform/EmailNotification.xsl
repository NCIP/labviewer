<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/" xmlns:cax="http://caXchange.nci.nih.gov/messaging">
<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
  <xsl:template match="cax:serviceType">
       <xsl:copy-of select="text()" />
  </xsl:template>
  <xsl:template match="cax:caXchangeIdentifier">
       <xsl:copy-of select="text()" />
  </xsl:template>  
  <xsl:template match="cax:caXchangeRequestMessage">
     <ns1:mailBody xmlns:ns1="http://caXchange.nci.nih.gov/messaging">
       An error occurred processing request.SERVICETYPE: <xsl:apply-templates select="//cax:serviceType" /> CAXCHANGE_IDENTIFIER: <xsl:apply-templates select="//cax:caXchangeIdentifier" />
     </ns1:mailBody>
  </xsl:template>  
</xsl:stylesheet>
