<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div id="header">
   <div id="logo"><a href="#"><img src="<%=request.getContextPath()%>/images/logo_dcim.gif" width="442" height="41" alt="LabViewer" /></a></div>
    <!--User Details-->
    <c:choose>
        <c:when test="${pageContext.request.remoteUser != null}">
        <div id="userarea">Welcome, <%=request.getRemoteUser()%> |  <a id="signOutBtn" href='<c:url value="/signOut.action"/>' class="btn_logout">Log Out</a></div>
        </c:when>
    </c:choose>
</div>
