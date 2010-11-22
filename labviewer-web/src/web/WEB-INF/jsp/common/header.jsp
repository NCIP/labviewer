<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div id="header">
   <div id="logo"><a href="#"><img src="<%=request.getContextPath()%>/images/logo_labviewer.gif" width="442" height="41" alt="LabViewer" /></a></div>
    <!--User Details-->
    <c:choose>
        <c:when test="${sessionScope.userName != null}">
            <div id="userarea">Welcome, <%=(String) session.getAttribute("userName")%> |  <a id="signOutBtn" href='<c:url value="/logout"/>' class="btn_logout">Log Out</a></div>
        </c:when>
    </c:choose>
</div>
