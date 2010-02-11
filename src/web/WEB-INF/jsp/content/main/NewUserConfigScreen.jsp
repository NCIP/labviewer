<%@ page isELIgnored="false"%>

<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>

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
		<display:table name="${sessionScope.UserConfigForm.usersList}" sort="list" cellspacing="0" cellpadding="3"
			               pagesize="25" id="userTable" export="true" 
			               size="totalUsers" 
			               defaultsort="1" defaultorder="ascending"
                        requestURI=""
                        decorator="gov.nih.nci.caxchange.ctom.viewer.util.UserDecorator"                        
	    style="border: 1px solid black; margin-top: 1em; margin-bottom: 1em; width: 100%;" class="dataTable">    
           <display:setProperty name="paging.banner.placement" value="bottom" />    
           <display:setProperty name="paging.banner.item_name" value="Users" />               
           <display:setProperty name="paging.banner.items_name" value="Users" />    
           <display:setProperty name="css.th.sorted" value="dataTableHeader" />
           <display:setProperty name="css.th.even" value="dataRowLight" />
           <display:setProperty name="css.th.odd" value="dataRowDark" />
           <display:setProperty name="export.excel.filename" value="Users.xls"/>    
           <display:setProperty name="export.csv.filename" value="Users.csv"/>               
           <display:setProperty name="export.xml.filename" value="Users.xml"/>                          
           <display:setProperty name="export.excel.include_header" value="true"/>                                     
           <display:setProperty name="export.csv.include_header" value="true"/>                                     
           <display:setProperty name="export.xml.include_header" value="true"/>            
           <display:column property="loginName"  class="dataCellText" sortable="true" title="Login Name" maxLength="200"/>
           <display:column property="firstName" class="dataCellText" sortable="true" title="First Name" />        
           <display:column property="lastName"  class="dataCellText" sortable="true" title="Last Name"  />
           <display:column  class="dataCellText" sortable="true" title="Actions" >
           <a href="#" onclick="handleModify(${userTable_rowNum})">Modify</a>&nbsp;&nbsp;&nbsp;
		   <a  href="#" onclick="handleDelete(${userTable_rowNum})">Delete</a>
		   </display:column>
         <display:column  property="id" class="hidden"   headerClass="hidden" media="html" />
		<display:column  property="selectedId" class="hidden"  headerClass="hidden" media="html" />
		<display:column  property="modifyFlag" class="hidden"   headerClass="hidden" media="html" />
		<display:column  property="deleteFlag" class="hidden"  headerClass="hidden" media="html" />	                                     
	</display:table>	     
 	<input type="hidden" name="selectedIndex" value=""/>
						
																		
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
    document.UserConfigForm.selectedIndex.value=iterateId;
    document.UserConfigForm.action="UserConfig.do?operation=loadModifyUser";
    document.UserConfigForm.submit();
}

//Function handles Delete
function handleDelete(iterateId)
{ 
	document.UserConfigForm.selectedIndex.value=iterateId;
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
