<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>   
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <title><fmt:message key="labviewer.home.title"/></title>
        <s:head />
    </head>
    <body onload="setFocusToFirstControl();">
     <h1><fmt:message key="labviewer.home.title" /></h1>
     <c:set var="topic" scope="request" value=""/> 
    
        <div class="homepage" style="width:600px">
            <div class="homebanner"><img src="<%=request.getContextPath()%>/images/banner_labviewer.jpg" width="599" height="140" alt="" /></div>
            <h1>LabViewer Home</h1>
            <p class="padme2">
            </p>
            <ul class="padme10">
				caBIG Lab Viewer is a web based application that allows users to view laboratory data stored in the CTODS Lab Domain database. Users may browse for laboratory activity by Patient Id, Study Id, Lab Start Date, and Lab End Date            
			</ul>                    
        </div>
    </body>
</html>