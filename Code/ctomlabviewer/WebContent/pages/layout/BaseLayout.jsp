<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-template"	prefix="template"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>

<!doctype html public "-//w3c//dtd html 4.0 transitional//en">

<%@ page import="gov.nih.nci.caxchange.ctom.viewer.constants.*"%>

<html:html>

<head>
   	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
   	<link rel="stylesheet" href="styles/styleSheet.css" type="text/css" />
   	<link rel="stylesheet" href="laf/src/main/resources/gov/nih/nci/cabig/ctms/lookandfeel/assets/css/common.css" type="text/css" />
   	<link rel="stylesheet" href="laf/src/main/resources/gov/nih/nci/cabig/ctms/lookandfeel/assets/css/tabbedflow.css" type="text/css" />
	<script language="JavaScript" src="scripts/script.js"></script>
	<script language="JavaScript" src="scripts/sorttable.js"></script>
	<script type="text/javascript" src="scripts/webtoolkit.scrollabletable.js"></script>
	<!-- Page Title begins -->
	<title><tiles:get name="Title"/></title>
	<!-- Page Title ends -->
	</head>
<body>
<table summary="" cellpadding="0" cellspacing="0" border="0" width="100%" height="90%">

	<!-- NCI hdr begins -->
  <tr>
    <td><tiles:get name="OrgHeader"/>
    </td>
  </tr>
  <!-- NCI hdr ends -->
  <tr>
    <td height="100%" align="center" valign="top">
      <table summary="" cellpadding="0" cellspacing="0" border="0" height="100%" width="90%">
    	            
			  <!-- main content begins -->
              <tr>
                <td valign="top" align=center>
                  <!-- target of anchor to skip menus --><a name="content" /></a>
                  <tiles:get name="Content"/>
                </td>
              </tr>
			  <!-- main content ends -->
            </table>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
<br>  <!-- 3 br to fix bottom border and caBIG footer overlay -->
<br>
<br>
</body>
</html:html>
