<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/" xmlns:cax="http://caXchange.nci.nih.gov/messaging">
<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
<xsl:template match="@* | node()">
    <xsl:copy>
      <xsl:apply-templates select="@* | node()"/>
    </xsl:copy>
  </xsl:template>
  
  <xsl:template match="soap:Envelope">
    <xsl:apply-templates/>
  </xsl:template>
  
  <xsl:template match="soap:Body">
      <xsl:apply-templates/>
  </xsl:template>
  
  
</xsl:stylesheet>
