
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>

<!-- laf box 1st half -->
<p> </p>
<div class="box">
	<div class="pane" align=center>
	    <!-- header -->
	    <div class="header"><div class="background-L"><div class="background-R">
	      <h2>Please log in</h2>
	    </div></div></div>
	    <!-- end header -->
	    <!-- inner border -->
	    <div class="border-T"><div class="border-L"><div class="border-R"><div class="border-B"><div class="border-TL"><div class="border-TR"><div class="border-BL"><div class="border-BR">
	        <div class="interior"> <!-- interior -->
	            <div class="content">
<!-- laf box 1st half -->
			<table summary="" cellpadding="0" cellspacing="0" border="0"
				class="contentPage" width="60%" height="80%" >
					<html:form action="/Login">
					<tr>
						<table cellpadding="2" cellspacing="0" border="0" >
							<tr>
								<td colspan="2"><html:errors /></td>
							</tr>
							<tr> <td height=18> </td></tr>  <!-- space on top -->
							<tr>
								<td class="sidebarLogin" align="right"><label for="loginId">LOGIN
								ID</label></td>
								<td class="formFieldLogin"><html:text style="formField"
									size="14" property="loginId" value="" /></td>
							</tr>
							<tr>
								<td class="sidebarLogin" align="right"><label
									for="password">PASSWORD</label></td>
								<td class="formFieldLogin"><html:password style="formField"
									size="14" property="password" value="" /></td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td><html:submit style="actionButton" value="Login" /></td>
							</tr>
						</table>
					</tr>
				</html:form> 
			</table>
<!-- laf box 2nd half -->
	        </div>  <!-- content -->
        </div><!-- interior -->
    </div></div></div></div></div></div></div></div>
    <!-- end inner border -->
	</div>
</div>
<!-- laf box 2nd half -->

