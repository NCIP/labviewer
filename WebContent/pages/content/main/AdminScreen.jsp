<%@ page isELIgnored="false"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"
	prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"
	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic"
	prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles"
	prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-template"
	prefix="template"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested"
	prefix="nested"%>
<%@ page import="gov.nih.nci.caxchange.ctom.viewer.forms.AdministrationForm"%>

<html:form action="/SaveAdminConfg.do" method="post">
<!-- laf box 1st half -->
<div style="margin-top: 0; padding-top: 0;">
 <!-- laf box 1st half -->
  <div class="box">
	<div  align="center">
	    <!-- header -->
	    <div class="header"><div class="background-L"><div class="background-R">
	      <h2>Administration</h2>
	    </div></div></div>
	    <!-- end header -->
	    <!-- inner border -->
	    <div class="border-T"><div class="border-L"><div class="border-R"><div class="border-B"><div class="border-TL"><div class="border-TR"><div class="border-BL"><div class="border-BR">
	        <div class="interior"> <!-- interior -->
	            <div class="content">
     <!-- laf box 1st half -->      
 	<table summary="" cellpadding="0" cellspacing="0" border="0" width="95%" height="100%" >
 	<tr>
			<td valign="top">
				<table cellpadding="0" cellspacing="0" border="0" width="100%" height="100%" class="contentBegins">
					<tr>
						<td>
																<center>
										<table cellpadding="0" cellspacing="0" border="0">
												<tr>
													<td>
													<font size="2" face="arial">caXchange URL</font>
													</td>
													<td>
														<html:text style="width: 4in" property="caxUrl"/>
													</td>
												</tr>
												<tr>
													<td>
													<font size="2" face="arial">caAERS Base URL</font>
													</td>
													<td>
														<html:text style="width: 4in" property="caaersUrl"/>
													</td>
												</tr>
												<tr>
													<tr>
													<td>
													<font size="2" face="arial">C3D Base URL</font>
													</td>
													<td>
														<html:text style="width: 4in" property="c3dUrl"/>
													</td>
												</tr>
												<tr>
													<tr>
													<td>
													<font size="2" face="arial">caTissue Base URL</font>
													</td>
													<td>
														<html:text style="width: 4in" property="tissueUrl"/>
													</td>
												</tr>
												<tr>
												<td align="center">
														<!-- action buttons begins -->
														<html:reset style="actionButton">Reset</html:reset>
														<html:submit style="actionButton">Save</html:submit>
													</td>
													</tr>
											</table>
											</center>
  </td></tr></table>
  </td></tr></table>
 

<!-- laf box 2nd half -->
	        </div>  <!-- content -->
        </div><!-- interior -->
    </div></div></div></div></div></div></div></div>
    <!-- end inner border -->
	</div>
</div>
<!-- laf box 2nd half -->    
</div>
</html:form>
