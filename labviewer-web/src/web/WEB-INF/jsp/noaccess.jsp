<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <title><fmt:message key="labviewer.home.title"/></title>
        <s:head />
    </head>
     <h1><fmt:message key="labviewer.home.title" /></h1>
     
        <div class="homepage" style="width:600px">
            <div class="homebanner"><img src="<%=request.getContextPath()%>/images/banner_labviewer.jpg" width="599" height="140" alt="" /></div>
            <h1><fmt:message key="labviewer.home.noaccess" /></h1>
            <jsp:include page="/WEB-INF/tags/failureMessage.jsp"/>
            <p class="padme8">
            <b> User do not have access to LabViewer: </b>
            </p>
            <ul class="intro">
                    <li> Please contact System Administration to assign Lad Data User or Administration Role to the User  <b> <%=(String) session.getAttribute("userName")%> </b></li>
            </ul>                    
        </div>
    </body>
</html>