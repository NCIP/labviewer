<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>

<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

<%@ page import="gov.nih.nci.caxchange.ctom.viewer.constants.*"%>
<%@ page import="gov.nih.nci.caxchange.ctom.viewer.forms.*"%>

<table width="100%" height="50" border="0" cellspacing="0" cellpadding="0" class="subhdrBG">
							<tr>
							
							<td height="50" width="400" align="left"><html:link forward="Home"><html:img src="images/appLogo.gif" alt="Lab Viewer Logo" hspace="10" border="0"/></html:link></td>		
								
							<!-- end home links -->	
								
								<logic:present name="<%=DisplayConstants.LOGIN_OBJECT%>">
								
								<bean:define name="<%=DisplayConstants.LOGIN_OBJECT%>" id="loginObject" type="LoginForm" />
								
								
								
								<td width="200" align="right">
									<table>
									<!--
									<tr><td class="appMenu" width="200" align="right">&nbsp;</td><td class="appMenu" width="50" align="center">&nbsp;</td></tr>
									-->
									<tr><td class="appMenu" width="60%" align="right">Login ID :</td><td class="appMenu2" width="40%" align="left"><bean:write name="loginObject" property="loginId" /></td></tr>
									</table>
								</td>
								</logic:present>
							</tr>
</table>
