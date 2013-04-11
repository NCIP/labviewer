<%--
Copyright ScenPro, Inc and SemanticBits, LLC

Distributed under the OSI-approved BSD 3-Clause License.
See http://ncip.github.com/labviewer/LICENSE.txt for details.
--%>
<!-- copy from CTOM MainMenu.jsp -->
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"	prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-template"	prefix="template"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>
<%@ page import="gov.nih.nci.caxchange.ctom.viewer.constants.*"%>
<%@ page import="gov.nih.nci.caxchange.ctom.viewer.forms.*"%>
<%@ page import="java.util.Set"%>
<%@ page import="gov.nih.nci.cabig.ctms.suite.authorization.SuiteRole"%>

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

<html:form styleId="menuForm" action="<%=\"/MenuSelection\"%>">
	<%
	String tableId;
	Set userRoles = (Set) session.getAttribute(DisplayConstants.USER_ROLES);
	try
	{
		tableId = (String)session.getAttribute(DisplayConstants.CURRENT_TABLE_ID);
		if (tableId.equalsIgnoreCase(DisplayConstants.BLANK))
		{
			tableId = DisplayConstants.HOME_ID;
			session.setAttribute(DisplayConstants.CURRENT_TABLE_ID,DisplayConstants.HOME_ID);
		}
		if(session.getAttribute("testEnabled")==null)
		   session.setAttribute("testEnabled","true");
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
<div>
	<!-- Render logo, header background and logo text 
    <div class="background-R" style="background: #55E2F7;">
        <img src="images/CTOM.gif" alt="UPT Home" id="logo" width="129" height="40">
        <img src="images/ctom_txt_1.gif" alt="ctom txt" id="tagline" width="268" height="22">
    </div>-->
    <div style="background: #4FD5EE;">
    <img src="images/nci.JPG" alt="cTODS LabViewer" style="height: 44px; width: 1024px;">
    </div>
    <!-- Add log out link -->
    
    <div id="login-action" style="font-size: .8em; margin-top: 0; margin-bottom: 0; padding-top:0; padding-bottom: 0;">
       <% if(session.getAttribute("version")!= null){ %>
         	<font color=blue>CTODS LabViewer V<%=session.getAttribute("version")%></font> ||
         <%} %>
       <a href="https://cabig-kc.nci.nih.gov/CTMS/KC/index.php/CaBIG_Lab_Viewer_User_Guide%2C_v2.2_DRAFT" target="_blank" style="color: blue; font-size: .8em;">Help</a> ||
       <logic:present name="<%=DisplayConstants.LOGIN_OBJECT%>">
       <% if(session.getAttribute("CAGRID_SSO_GRID_IDENTITY")!=null && session.getAttribute("webssoEnabled")!= null){ %>
       <% if(session.getAttribute("webssoEnabled").equals("TRUE") || session.getAttribute("webssoEnabled").equals("true")|| (session.getAttribute("HOT_LINK") == "true")){ %>
         <a href="javascript: set('<%=DisplayConstants.LOGOUT_ID%>')" style="font-size: .8em; color: blue;">Log out</a>
       <%}}else{ %>
   		 <a href="javascript: set('<%=DisplayConstants.LOGOUT_ID%>')" style="font-size: .8em; color: blue;">Log out</a>    
       <%} %>
       </logic:present>
     </div>

	<!-- if log in, enable menu and login id -->
	<logic:present name="<%=DisplayConstants.LOGIN_OBJECT%>">
	    <!-- display login id using smaller font below log out link -->
	    <div id="login-id" style="color: blue; margin-top: -6px; padding-top: 0;">
			<bean:define name="<%=DisplayConstants.LOGIN_OBJECT%>" id="loginObject" type="LoginForm" />
	        Login ID: <bean:write name="loginObject" property="loginId" />
	    </div>
		<!-- add menu items, 2 only here -->
		<div style="border-bottom: 1px solid black; height: 17px;">
	    <ul id="sections" class="tabs" style="position: relative; top: 0; left: 0; text-align: left;">
	    	<%if (tableId.equalsIgnoreCase(DisplayConstants.HOME_ID)){%>
		    	<li class="selected"><div><a href="javascript: set('<%=DisplayConstants.HOME_ID%>')">Home</a></div></li>
			<%} else {%>
	    		<li class=""><div><a href="javascript: set('<%=DisplayConstants.HOME_ID%>')">Home</a></div></li>
			<%}%>
			
			<%if (userRoles.contains(SuiteRole.LAB_DATA_USER)) {%>
			    <%if (tableId.equalsIgnoreCase(DisplayConstants.STUDYSEARCH_ID)){%>
 	    		    <li class="selected"><div><a href="javascript: set('<%=DisplayConstants.STUDYSEARCH_ID%>')">Study</a></div></li>
 			    <%} else {%>
 		    	    <li class=""><div><a href="javascript: set('<%=DisplayConstants.STUDYSEARCH_ID%>')">Study</a></div></li>
 		    	<%}%>
 			
 	    	    <%if (tableId.equalsIgnoreCase(DisplayConstants.PARTICIPANTSEARCH_ID)){%>
 	    		    <li class="selected"><div><a href="javascript: set('<%=DisplayConstants.PARTICIPANTSEARCH_ID%>')">Participant</a></div></li>
 			    <%} else {%>
 		    	    <li class=""><div><a href="javascript: set('<%=DisplayConstants.PARTICIPANTSEARCH_ID%>')">Participant</a></div></li>
 			    <%}%>
	
	    	    <%if (tableId.equalsIgnoreCase(DisplayConstants.LABACTIVITES_ID)){%>
	    		    <li class="selected"><div><a href="javascript: set('<%=DisplayConstants.LABACTIVITES_ID%>')">Labs</a></div></li>
			    <%} else {%>
		    	    <li class=""><div><a href="javascript: set('<%=DisplayConstants.LABACTIVITES_ID%>')">Labs</a></div></li>
			    <%}%>
			<%}%>
			
			<%if( session.getAttribute("testEnabled").equals("true")){ %>
		<%if (tableId.equalsIgnoreCase(DisplayConstants.TEST_ID)){%>
	    		<li class="selected"><div><a href="javascript: set('<%=DisplayConstants.TEST_ID%>')">Test</a></div></li>
			<%} else {%>
		    	<li class=""><div><a href="javascript: set('<%=DisplayConstants.TEST_ID%>')">Test</a></div></li>
			<%}}%>
			
			<%if (userRoles.contains(SuiteRole.SYSTEM_ADMINISTRATOR) || userRoles.contains(SuiteRole.USER_ADMINISTRATOR)) {%>
			    <%if (tableId.equalsIgnoreCase(DisplayConstants.ADMIN_ID)){%>			        
	    		    <li class="selected">
	    		<%} else {%>
	    		    <li class="">
	    		<%}%>
	    		<div>
	    		<%if (userRoles.contains(SuiteRole.SYSTEM_ADMINISTRATOR)) {%>
	    		    <a href="javascript: set('<%=DisplayConstants.ADMIN_ID%>')">
	    		<%} else {%>
	    		    <a href="javascript: set('<%=DisplayConstants.USER_CONFG_ID%>')">
	    		<%}%>
	    		Administration</a></div></li>
			<%}%>
	    </ul>
	    </div>
	</logic:present>

	<!-- Add task bar, menu tips, under menu -->
    <div id="taskbar" style="clear: both; border-bottom: 1px solid black; ">
		<logic:present name="<%=DisplayConstants.LOGIN_OBJECT%>">
	    	<%if (tableId.equalsIgnoreCase(DisplayConstants.HOME_ID)){%>
	            Tasks: <font color="blue" size="2"> Greeting screen </font>
			<%}%>
			<%if (tableId.equalsIgnoreCase(DisplayConstants.STUDYSEARCH_ID)){%>
	            Tasks: <font color="blue" size="2"> Study Search </font>
			<%}%>
	    	<%if (tableId.equalsIgnoreCase(DisplayConstants.LABACTIVITES_ID)){%>
	            <% if(session.getAttribute("pageTitle")!= null){ %>
			    <font color="blue" size="2"><%=session.getAttribute("pageTitle")%></font>
			<%}else{%>Tasks: <font color="blue" size="2"> Search lab activities </font>
			<%}}%>
			<%if (tableId.equalsIgnoreCase(DisplayConstants.PARTICIPANTSEARCH_ID)){%>
			    <% if(session.getAttribute("pageTitle")!= null){ %>
			    <font color="blue" size="2"><%=session.getAttribute("pageTitle")%></font>
			<%}else{%>Tasks: <font color="blue" size="2">Participant Search</font>
			<%}}%>
			
			<%if (tableId.equalsIgnoreCase(DisplayConstants.ADMIN_ID) && 
			      userRoles.contains(SuiteRole.SYSTEM_ADMINISTRATOR) && 
			      userRoles.contains(SuiteRole.USER_ADMINISTRATOR)) {%>
	            <font color="blue" size="2" ><a href="javascript: set('<%=DisplayConstants.ADMIN_ID%>')" class="favBtn"> Admin Configuration</a> </font>:: <a href="javascript: set('<%=DisplayConstants.USER_CONFG_ID%>')" style="font-size: .9em; color: blue;" class="favBtn">User Configuration</a> 
			<%}%>
		</logic:present>
    </div>
    
</div>

</html:form>
<!-- combination of laf header.tag and CTOM MainMenu.jsp -->

