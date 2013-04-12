<%--
Copyright ScenPro, Inc and SemanticBits, LLC

Distributed under the OSI-approved BSD 3-Clause License.
See http://ncip.github.com/labviewer/LICENSE.txt for details.
--%>
<!-- copy from CTOM MainMenu.jsp -->
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="gov.nih.nci.caxchange.ctom.viewer.constants.*"%>

<script>
    	function set(tableId)
    	{
    		document.tabForm.tableId.value = tableId;
    		document.tabForm.action = tableId + ".action";
            document.tabForm.submit();
    	}
</script>

<!-- end copy from CTOM MainMenu.jsp -->
<!-- combination of laf header.tag and CTOM MainMenu.jsp -->
<s:form id="tabForm">
<s:hidden name="tableId"/>
<div>
    <div style="background: #4FD5EE;">
    <img src="images/nci.JPG" alt="cTODS LabViewer" style="height: 44px; width: 1024px;">
    </div>
    <!-- Add log out link -->
    
    <div id="login-action" style="font-size: .8em; margin-top: 0; margin-bottom: 0; padding-top:0; padding-bottom: 0;">
       <s:if test="#session.version != null">
         	<font color=blue>CTODS LabViewer V<s:property value="#session.version"/></font> ||
       </s:if>
       <a href="helpdocs/CTODS Lab Viewer End User Guide.doc" style="color: blue; font-size: .8em;">Help</a> ||
       <%-- <%=DisplayConstants.LOGOUT_OBJECT%> doesn't work in the following test expression,
            therefore the text 'LOGIN_OBJECT' needs to match the 'value' of DisplayConstants.LOGIN_OBJECT,
            this applies throughout this jsp --%> <!-- lisa - ask about this -->
       <s:if test="#session.LOGIN_OBJECT != null"> 
           <s:if test="isWebssoCasLogout">
               <a href="javascript: set('<%=DisplayConstants.LOGOUT_ID%>')" style="font-size: .8em; color: blue;">Log out</a>
           </s:if>
           <s:else>
   		       <a href="javascript: set('<%=DisplayConstants.LOGOUT_ID%>')" style="font-size: .8em; color: blue;">Log out</a>    
           </s:else>
       </s:if>
       <s:else>
           <a href="javascript: set('<%=DisplayConstants.HOME_ID%>')" style="font-size: .8em; color: blue;">Log in</a>    
       </s:else>
     </div>

	<!-- if log in, enable menu and login id -->
	<s:if test="#session.LOGIN_OBJECT != null">
	    <!-- display login id using smaller font below log out link -->
	    <div id="login-id" style="color: blue; margin-top: -6px; padding-top: 0;">
	        Login ID: <s:property value="#session.LOGIN_OBJECT.loginId" />
	    </div>
		<!-- add menu items, 2 only here -->
		<div style="border-bottom: 1px solid black; height: 17px;">
	    <ul id="sections" class="tabs" style="position: relative; top: 0; left: 0; text-align: left;">
	        <s:set name="HOME_ID" value="@gov.nih.nci.caxchange.ctom.viewer.constants.DisplayConstants@HOME_ID" />
	        <s:if test="tableId == #HOME_ID">
		    	<li class="selected"><div><a href="javascript: set('<%=DisplayConstants.HOME_ID%>')">Home</a></div></li>
			</s:if>
            <s:else>
	    		<li class=""><div><a href="javascript: set('<%=DisplayConstants.HOME_ID%>')">Home</a></div></li>
			</s:else>
			
			<s:set name="STUDYSEARCH_ID" value="@gov.nih.nci.caxchange.ctom.viewer.constants.DisplayConstants@STUDYSEARCH_ID" />
			<s:if test="tableId == #STUDYSEARCH_ID">
 	    		<li class="selected"><div><a href="javascript: set('<%=DisplayConstants.STUDYSEARCH_ID%>')">Study</a></div></li>
 			</s:if>
            <s:else>
 		    	<li class=""><div><a href="javascript: set('<%=DisplayConstants.STUDYSEARCH_ID%>')">Study</a></div></li>
 			</s:else>
 			
 	    	<s:set name="PARTICIPANTSEARCH_ID" value="@gov.nih.nci.caxchange.ctom.viewer.constants.DisplayConstants@PARTICIPANTSEARCH_ID" />
			<s:if test="tableId == #PARTICIPANTSEARCH_ID">
 	    		<li class="selected"><div><a href="javascript: set('<%=DisplayConstants.PARTICIPANTSEARCH_ID%>')">Participant</a></div></li>
 			</s:if>
            <s:else>
 		    	<li class=""><div><a href="javascript: set('<%=DisplayConstants.PARTICIPANTSEARCH_ID%>')">Participant</a></div></li>
 			</s:else>
	
	    	<s:set name="LABACTIVITES_ID" value="@gov.nih.nci.caxchange.ctom.viewer.constants.DisplayConstants@LABACTIVITES_ID" />
			<s:if test="tableId == #LABACTIVITES_ID">
	    		<li class="selected"><div><a href="javascript: set('<%=DisplayConstants.LABACTIVITES_ID%>')">Labs</a></div></li>
			</s:if>
            <s:else>
		    	<li class=""><div><a href="javascript: set('<%=DisplayConstants.LABACTIVITES_ID%>')">Labs</a></div></li>
			</s:else>
			
			<s:if test='#session.testEnabled == "true"'>
		        <s:set name="TEST_ID" value="@gov.nih.nci.caxchange.ctom.viewer.constants.DisplayConstants@TEST_ID" />
			    <s:if test="tableId == #TEST_ID">
	    		    <li class="selected"><div><a href="javascript: set('<%=DisplayConstants.TEST_ID%>')">Test</a></div></li>
			    </s:if>
                <s:else>
		    	    <li class=""><div><a href="javascript: set('<%=DisplayConstants.TEST_ID%>')">Test</a></div></li>
			    </s:else>
			</s:if>
			
			<s:set name="ADMIN_ID" value="@gov.nih.nci.caxchange.ctom.viewer.constants.DisplayConstants@ADMIN_ID" />
			<s:if test="tableId == #ADMIN_ID">
	    		<li class="selected"><div><a href="javascript: set('<%=DisplayConstants.ADMIN_ID%>')">Administration</a></div></li>
			</s:if>
            <s:else>
		    	<li class=""><div><a href="javascript: set('<%=DisplayConstants.ADMIN_ID%>')">Administration</a></div></li>
			</s:else>
	    </ul>
	    </div>
	</s:if>

	<!-- Add task bar, menu tips, under menu -->
    <div id="taskbar" style="clear: both; border-bottom: 1px solid black; ">
		<s:if test="#session.LOGIN_OBJECT != null">	    	
	    	<s:if test="tableId == #HOME_ID">
	            Tasks: <font color="blue" size="2"> Greeting screen </font>
			</s:if>
			
			<s:elseif test="tableId == #STUDYSEARCH_ID">
	            Tasks: <font color="blue" size="2"> Study Search </font>
			</s:elseif>
			
			<s:elseif test="tableId == #PARTICIPANTSEARCH_ID">
			    <s:if test="#session.pageTitle != null">
			        <font color="blue" size="2"><s:property value="#session.pageTitle"/></font>
			    </s:if>
                <s:else>
			        Tasks: <font color="blue" size="2">Participant Search</font>
			    </s:else>
			</s:elseif>

	    	<s:elseif test="tableId == #LABACTIVITES_ID">
	            <s:if test="#session.pageTitle != null">
			        <font color="blue" size="2"><s:property value="#session.pageTitle"/></font>
			    </s:if>
                <s:else>
			        Tasks: <font color="blue" size="2"> Search lab activities </font>
			    </s:else>
			</s:elseif>
			
			<s:elseif test="tableId == #ADMIN_ID">
	            <font color="blue" size="2" ><a href="javascript: set('<%=DisplayConstants.ADMIN_ID%>')" class="favBtn"> Admin Configuration</a></font>::<a href="javascript: set('<%=DisplayConstants.USER_CONFG_ID%>')" style="font-size: .9em; color: blue;" class="favBtn">User Configuration</a> 
			</s:elseif>
		</s:if>
    </div>
    
</div>
</s:form> 
<!-- combination of laf header.tag and CTOM MainMenu.jsp -->

