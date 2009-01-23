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

<html:form action="/UserConfig.do" method="post">
	<!-- laf box 1st half -->
		<div class="box">
			<div align=center>
				<!-- header -->
				<div class="header">
					<div class="background-L">
						<div class="background-R">
							<h2>
								User Information
							</h2>
						</div>
					</div>
				</div>
				<!-- end header -->
				<!-- inner border -->
				<div class="border-T">
					<div class="border-L">
						<div class="border-R">
							<div class="border-B">
								<div class="border-TL">
									<div class="border-TR">
										<div class="border-BL">
											<div class="border-BR">
												<div class="interior">
													<!-- interior -->
													<div class="content">
														<!-- laf box 1st half -->
														<table summary="" cellpadding="0" cellspacing="0"
															border="0" width="95%" height="100%">
															<tr>
																<td valign="top">
																	<table cellpadding="0" cellspacing="0" border="0"
																		width="100%" class="contentBegins">
																		<tr>
																			<td>
																			<div> Please enter all data marked (<font color="red">*</font>)</div>
																				<table summary="Enter summary of data here"
																					cellpadding="2" cellspacing="0" border="0"
																					id="myScrollTable" class="dataTable"
																					width="75%">
																					<tbody>
																						<tr>
																						   <td>
																						   <font color="red">*</font> Login Name:
																						   </td>
																					    	<td>
																					       	<html:text style="width: 2in" name="UserConfigForm" property="userBean.loginName"/>
																							</td>
																							</tr>
																							<tr>
																						   <td>
																						  <font color="red">*</font>  Password:
																						   </td>
																					    	<td>
																					       	<html:password style="width: 2in" name="UserConfigForm" property="userBean.password"/>
																							</td>
																							</tr>
																							<tr>
																						   <td>
																						  <font color="red">*</font>  First Name:
																						   </td>
																					    	<td>
																					       	<html:text style="width: 2in" name="UserConfigForm" property="userBean.firstName"/>
																							</td>
																							</tr>
																							<tr>
																						   <td>
																						 <font color="red">*</font>   Last Name:
																						   </td>
																					    	<td>
																					       	<html:text style="width: 2in" name="UserConfigForm" property="userBean.lastName"/>
																							</td>
																							</tr>
																							<tr>
																						   <td>
																						    Organization:
																						   </td>
																					    	<td>
																					       	<html:text style="width: 2in" name="UserConfigForm" property="userBean.organization"/>
																							</td>
																							</tr>
																							<tr>
																						   <td>
																						    Department:
																						   </td>
																					    	<td>
																					       	<html:text style="width: 2in" name="UserConfigForm" property="userBean.department"/>
																							</td>
																							</tr>
																							<tr>
																						   <td>
																						    Title:
																						   </td>
																					    	<td>
																					       	<html:text style="width: 2in" name="UserConfigForm" property="userBean.title"/>
																							</td>
																							</tr>
																							<tr>
																						   <td>
																				<font color="red">*</font> Phone Number:
																						   </td>
																					    	<td>
																					       	<html:text style="width: 2in" name="UserConfigForm" property="userBean.phoneNumber"/>
																							</td>
																							</tr>
																							<tr>
																						   <td>
																					<font color="red">*</font> Email Id:
																						   </td>
																					    	<td>
																					       	<html:text style="width: 2in" name="UserConfigForm" property="userBean.emailId"/>
																							</td>
																							</tr>
																							
																					</tbody>
																				</table>
																			</td>
																		</tr>
																		<tr>
																		<td>
																		<div align="center">
																		<input name="saveUser" type="button" class="button" id="save"
																			value="Save User" onclick="handleSaveUser()" />
							
																		</div>
																		</td>
																		</tr>
																		
																	</table>
																</td>
															</tr>
														</table>


														<!-- laf box 2nd half -->
													</div>
													<!-- content -->
												</div>
												<!-- interior -->
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<!-- end inner border -->
			</div>
		</div>
		<!-- laf box 2nd half -->
	</html:form>
<head>
<script type="text/javascript">
var t = new ScrollableTable(document.getElementById('myScrollTable'), 250);

//handles Save User action
function handleSaveUser(){
    document.UserConfigForm.action="UserConfig.do?operation=addUser";
    document.UserConfigForm.submit();
}
</script>
</head>
