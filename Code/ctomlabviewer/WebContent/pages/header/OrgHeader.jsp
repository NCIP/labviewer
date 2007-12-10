<!-- copy from CTOM MainMenu.jsp -->
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"	prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-template"	prefix="template"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ page import="gov.nih.nci.caxchange.ctom.viewer.constants.*"%>
<%@ page import="gov.nih.nci.caxchange.ctom.viewer.forms.*"%>

<script>
  <!--
    	function set(tableId)
    	{
    		document.MenuForm.tableId.value=tableId;
    		document.MenuForm.submit();
    	}
    	
    	  
function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_findObj(n, d) { //v4.0
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && document.getElementById) x=document.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}
    	
    	
   // -->
</script>

<html:form styleId="menuForm" action="<%="/MenuSelection"%>">
	<%
	String tableId;
	try
	{
		tableId = (String)session.getAttribute(DisplayConstants.CURRENT_TABLE_ID);
		if (tableId.equalsIgnoreCase(DisplayConstants.BLANK))
		{
			tableId = DisplayConstants.HOME_ID;
			session.setAttribute(DisplayConstants.CURRENT_TABLE_ID,DisplayConstants.HOME_ID);
		}
	}
	catch (Exception e)
	{
		tableId = DisplayConstants.HOME_ID;
		session.setAttribute(DisplayConstants.CURRENT_TABLE_ID,DisplayConstants.HOME_ID);
	}
	
	%>
	<html:hidden property="tableId" value="error" />

<!-- end copy from CTOM MainMenu.jsp -->
<!-- combination of laf header.tag and CTOM MainMenu.jsp -->
<div id="header">
	<!-- Render logo, header background and logo text -->
    <div class="background-R">
        <img src="images/CTOM.gif" alt="UPT Home" id="logo" width="129" height="40">
        <img src="images/ctom_txt_1.gif" alt="ctom txt" id="tagline" width="268" height="22">
    </div>
    <!-- Add log out link -->
    <div id="login-action">
        <a href="javascript: set('<%=DisplayConstants.LOGOUT_ID%>')">Log out</a>
    </div>

	<!-- if log in, enable menu and login id -->
	<logic:present name="<%=DisplayConstants.LOGIN_OBJECT%>">
	    <!-- display login id using smaller font below log out link -->
	    <div id="login-id">
			<bean:define name="<%=DisplayConstants.LOGIN_OBJECT%>" id="loginObject" type="LoginForm" />
	        <font color=white>Login ID: <bean:write name="loginObject" property="loginId" /></font>
	    </div>
		<!-- add menu items, 2 only here -->
	    <ul id="sections" class="tabs" align=center>
	    	<%if (tableId.equalsIgnoreCase(DisplayConstants.HOME_ID)){%>
		    	<li class="selected"><div><a href="javascript: set('<%=DisplayConstants.HOME_ID%>')">Home</a></div></li>
			<%} else {%>
	    		<li class=""><div><a href="javascript: set('<%=DisplayConstants.HOME_ID%>')">Home</a></div></li>
			<%}%>
	    	<%if (tableId.equalsIgnoreCase(DisplayConstants.LABACTIVITES_ID)){%>
	    		<li class="selected"><div><a href="javascript: set('<%=DisplayConstants.LABACTIVITES_ID%>')">Lab Activities</a></div></li>
			<%} else {%>
		    	<li class=""><div><a href="javascript: set('<%=DisplayConstants.LABACTIVITES_ID%>')">Lab Activities</a></div></li>
			<%}%>
	    </ul>
	</logic:present>

	<!-- Add task bar, menu tips, under menu -->
    <div id="taskbar">
		<logic:present name="<%=DisplayConstants.LOGIN_OBJECT%>">
	    	<%if (tableId.equalsIgnoreCase(DisplayConstants.HOME_ID)){%>
	            Tasks: <font color=blue> Greeting screen </font>
			<%}%>
	    	<%if (tableId.equalsIgnoreCase(DisplayConstants.LABACTIVITES_ID)){%>
	            Tasks: <font color=blue> Search lab activities </font>
			<%}%>
		</logic:present>
    </div>
    
</div>

</html:form>
<!-- combination of laf header.tag and CTOM MainMenu.jsp -->

