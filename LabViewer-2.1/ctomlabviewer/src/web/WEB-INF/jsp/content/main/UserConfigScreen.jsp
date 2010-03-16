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
	<logic:present name="UserConfigForm" property="usersList">

		<!-- laf box 1st half -->
		<div class="box">
			<div align=center>
				<!-- header -->
				<div class="header">
					<div class="background-L">
						<div class="background-R">
							<h2>
								Users
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
																				<table summary="Enter summary of data here"
																					cellpadding="3" cellspacing="0" border="0"
																					id="myScrollTable" class="sortable dataTable"
																					width="100%">
																					<thead class="scrollbar">
																						<tr>
																							<th class="dataTableHeader" scope="col"
																								align="center" width="15%">
																								Login Name
																							</th>
																							<th class="dataTableHeader" scope="col"
																								align="center" width="15%">
																								First Name
																							</th>
																							<th class="dataTableHeader" scope="col"
																								align="center" width="15%">
																								Last Name
																							</th>
																							<th class="dataTableHeader" scope="col"
																								align="center" width="25%">
																								Actions
																							</th>

																						</tr>
																					</thead>
																					<tbody>
																						<nested:iterate name="UserConfigForm"
																							id="usersList" property="usersList"
																							type="gov.nih.nci.caxchange.ctom.viewer.beans.Users"
																							indexId="iterateId">
																							<tr>
																								<td class="dataCellText" width="15%">
																									<nested:write name="usersList"
																										property="loginName" />
																								</td>
																								<td class="dataCellText" width="15%">
																									<nested:write name="usersList"
																										property="firstName" />
																								</td>
																								<td class="dataCellText" width="15%">
																									<nested:write name="usersList"
																										property="lastName" />
																								</td>
																								<td class="dataCellText" width="15%">
																									<nested:hidden name="usersList" property="id" indexed="true" />
																									<nested:hidden name="usersList" property="selectedId" indexed="true" />
																									<nested:hidden name="usersList" property="modifyFlag" indexed="true" />
																									<nested:hidden name="usersList" property="deleteFlag" indexed="true" />
																									<a href="#" onclick="handleModify(${iterateId})">Modify</a>&nbsp;&nbsp;&nbsp;
																									<a  href="#" onclick="handleDelete(${iterateId})">Delete</a>
																								</td>
																							</tr>
																						</nested:iterate>
																					</tbody>
																				</table>
																			</td>
																		</tr>
																		<tr>
																		<td>
																		<div align="right">
																		<input name="add" type="button" class="button" id="add"
																			value="Add User" onclick="handleAddUser()" />
							
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
	</logic:present>
</html:form>
<head>
<script type="text/javascript">
//var t = new ScrollableTable(document.getElementById('myScrollTable'), 150);

//Function handles Edit
function handleModify(iterateId)
{
    var input1="usersList[" + iterateId + "].modifyFlag";
    document.forms["UserConfigForm"].elements[input1].value="T";
    document.UserConfigForm.action="UserConfig.do?operation=loadModifyUser";
    document.UserConfigForm.submit();
}

//Function handles Delete
function handleDelete(iterateId)
{ 
	var input1="usersList[" + iterateId + "].deleteFlag";
    document.forms["UserConfigForm"].elements[input1].value="T";
    document.UserConfigForm.action="UserConfig.do?operation=deleteUsers";
    document.UserConfigForm.submit();
}

//handles Add User action
function handleAddUser()
{
     document.UserConfigForm.action="UserConfig.do?operation=loadAddUser";
    document.UserConfigForm.submit();
}
</script>
</head>
