<%--
Copyright ScenPro, Inc and SemanticBits, LLC

Distributed under the OSI-approved BSD 3-Clause License.
See http://ncip.github.com/labviewer/LICENSE.txt for details.
--%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<!doctype html public "-//w3c//dtd html 4.0 transitional//en">

<html>

<head>
   	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
   	<link rel="stylesheet" href="styles/styleSheet.css" type="text/css" />
   	<link rel="stylesheet" href="laf/src/main/resources/gov/nih/nci/cabig/ctms/lookandfeel/assets/css/common.css" type="text/css" />
   	<link rel="stylesheet" href="laf/src/main/resources/gov/nih/nci/cabig/ctms/lookandfeel/assets/css/tabbedflow.css" type="text/css" />
	<script language="JavaScript" src="scripts/script.js" ></script>
<!--	<script language="JavaScript" src="scripts/sorttable.js"></script>-->

	<!-- Page Title begins -->
	<title><tiles:insertAttribute name="Title"/></title>
	<!-- Page Title ends -->
</head>
<body>
<table summary="" cellpadding="0" cellspacing="0" border="0" width="100%">

	<!-- NCI hdr begins -->
  <tr>
    <td><tiles:insertAttribute name="OrgHeader"/>
    </td>
  </tr>
  <!-- NCI hdr ends -->
  <tr>
    <td align="center" valign="top">
      <table summary="" cellpadding="0" cellspacing="0" border="0" width="90%">
    	            
			  <!-- main content begins -->
              <tr>
                <td valign="top" align="center">
                  <!-- target of anchor to skip menus --><a name="content" /></a>
                  <tiles:insertAttribute name="Content"/>
                </td>
              </tr>
			  <!-- main content ends -->
            </table>
          </td>
        </tr>
      </table>
<br>  <!-- 3 br to fix bottom border and caBIG footer overlay -->
<br>
<br>
</body>
</html>
