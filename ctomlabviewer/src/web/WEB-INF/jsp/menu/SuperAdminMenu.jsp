<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-template" prefix="template"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>

<%@ page import="gov.nih.nci.caxchange.ctom.viewer.constants.*"%>

<script>
  <!--
    	function set(id)
    	{
    		document.MenuForm.tableId.value=id;
    		document.MenuForm.submit();
    	}
   // -->
</script>

<html:form styleId="MenuForm" action='<%="/MenuSelection"%>'>
	<%
	String tableId;
	try
	{
		tableId = (String)session.getAttribute(DisplayConstants.CURRENT_TABLE_ID);
		if (tableId.equalsIgnoreCase(DisplayConstants.BLANK))
		{
			tableId = DisplayConstants.ADMIN_HOME_ID;
			session.setAttribute(DisplayConstants.CURRENT_TABLE_ID,DisplayConstants.ADMIN_HOME_ID);
		}
	}
	catch (Exception e)
	{
		tableId = DisplayConstants.ADMIN_HOME_ID;
		session.setAttribute(DisplayConstants.CURRENT_TABLE_ID,DisplayConstants.ADMIN_HOME_ID);
	}
	%>
	<html:hidden property="tableId" value="error" />
	<td class="mainMenu" height="20">
	<table summary="" cellpadding="0" cellspacing="0" border="0" height="20">
		<logic:present name="<%=DisplayConstants.LOGIN_OBJECT%>">
			<tr>
			<!-- link 1 begins -->
			<%if (tableId.equalsIgnoreCase(DisplayConstants.ADMIN_HOME_ID)){%>
			<td height="20" class="mainMenuItemOver" onmouseover="changeMenuStyle(this,'mainMenuItemOver'),showCursor()" onmouseout="changeMenuStyle(this,'mainMenuItemOver'),hideCursor()" onclick="javascript: set('<%=DisplayConstants.ADMIN_HOME_ID%>')"><a class="mainMenuLink" href="javascript: set('<%=DisplayConstants.ADMIN_HOME_ID%>')">HOME</a></td>
			<%}if (!tableId.equalsIgnoreCase(DisplayConstants.ADMIN_HOME_ID)){%>
			<td height="20" class="mainMenuItem" onmouseover="changeMenuStyle(this,'mainMenuItemOver'),showCursor()" onmouseout="changeMenuStyle(this,'mainMenuItem'),hideCursor()" onclick="javascript: set('<%=DisplayConstants.ADMIN_HOME_ID%>')"><a class="mainMenuLink" href="javascript: set('<%=DisplayConstants.ADMIN_HOME_ID%>')">HOME</a></td>
			<%}%>
			<!-- link 1 ends -->
			<td><img src="images/mainMenuSeparator.gif" width="1" height="16" alt="" /></td>			
			<!-- link 2 begins -->
			<%if (tableId.equalsIgnoreCase(DisplayConstants.APPLICATION_ID)){%>
			<td height="20" class="mainMenuItemOver" onmouseover="changeMenuStyle(this,'mainMenuItemOver'),showCursor()" onmouseout="changeMenuStyle(this,'mainMenuItemOver'),hideCursor()" onclick="javascript: set('<%=DisplayConstants.APPLICATION_ID%>')"><a class="mainMenuLink" href="javascript: set('<%=DisplayConstants.APPLICATION_ID%>')">APPLICATION</a></td>
			<%}if (!tableId.equalsIgnoreCase(DisplayConstants.APPLICATION_ID)){%>
			<td height="20" class="mainMenuItem" onmouseover="changeMenuStyle(this,'mainMenuItemOver'),showCursor()" onmouseout="changeMenuStyle(this,'mainMenuItem'),hideCursor()" onclick="javascript: set('<%=DisplayConstants.APPLICATION_ID%>')"><a class="mainMenuLink" href="javascript: set('<%=DisplayConstants.APPLICATION_ID%>')">APPLICATION</a></td>
			<%}%>
			<!-- link 2 ends -->
			<td><img src="images/mainMenuSeparator.gif" width="1" height="16" alt="" /></td>
			<!-- link 3 begins -->
			<%if (tableId.equalsIgnoreCase(DisplayConstants.USER_ID)){%>
			<td height="20" class="mainMenuItemOver" onmouseover="changeMenuStyle(this,'mainMenuItemOver'),showCursor()" onmouseout="changeMenuStyle(this,'mainMenuItemOver'),hideCursor()" onclick="javascript: set('<%=DisplayConstants.USER_ID%>')"><a class="mainMenuLink" href="javascript: set('<%=DisplayConstants.USER_ID%>')">USER</a></td>
			<%}if (!tableId.equalsIgnoreCase(DisplayConstants.USER_ID)){%>
			<td height="20" class="mainMenuItem" onmouseover="changeMenuStyle(this,'mainMenuItemOver'),showCursor()" onmouseout="changeMenuStyle(this,'mainMenuItem'),hideCursor()" onclick="javascript: set('<%=DisplayConstants.USER_ID%>')"><a class="mainMenuLink" href="javascript: set('<%=DisplayConstants.USER_ID%>')">USER</a></td>			
			<%}%>
			<!-- link 3 ends -->
			<td><img src="images/mainMenuSeparator.gif" width="1" height="16" alt="" /></td>
			
			<!-- link priv 3.0.1 begins -->
				<%if (tableId.equalsIgnoreCase(DisplayConstants.PRIVILEGE_ID)){%>
				<td height="16" class="mainMenuItemOver"
					onmouseover="changeMenuStyle(this,'mainMenuItemOver'),showCursor()"
					onmouseout="changeMenuStyle(this,'mainMenuItemOver'),hideCursor()"
					onclick="javascript: set('<%=DisplayConstants.PRIVILEGE_ID%>')"><a
					class="mainMenuLink"
					href="javascript: set('<%=DisplayConstants.PRIVILEGE_ID%>')">PRIVILEGE</a>
				<%}if (!tableId.equalsIgnoreCase(DisplayConstants.PRIVILEGE_ID)){%>
				<td height="16" class="mainMenuItem"
					onmouseover="changeMenuStyle(this,'mainMenuItemOver'),showCursor()"
					onmouseout="changeMenuStyle(this,'mainMenuItem'),hideCursor()"
					onclick="javascript: set('<%=DisplayConstants.PRIVILEGE_ID%>')"><a
					class="mainMenuLink"
					href="javascript: set('<%=DisplayConstants.PRIVILEGE_ID%>')">PRIVILEGE</a>
				<%}%>
				<!-- link priv 3.0.1 ends -->
			
			<!-- link 3 ends -->
			<td><img src="images/mainMenuSeparator.gif" width="1" height="16" alt="" /></td>
			
			
			
			<!-- link 4 begins -->			
			<td height="20" class="mainMenuItem" onmouseover="changeMenuStyle(this,'mainMenuItemOver'),showCursor()" onmouseout="changeMenuStyle(this,'mainMenuItem'),hideCursor()" onclick="javascript: set('<%=DisplayConstants.LOGOUT_ID%>')"><a class="mainMenuLink" href="javascript: set('<%=DisplayConstants.LOGOUT_ID%>')">LOG OUT</a></td>
			<!-- link 4 ends -->			
			<td><img src="images/mainMenuSeparator.gif" width="1" height="16" alt="" /></td>
			</tr>
		</logic:present>
	</table>
	</td>
</html:form>
