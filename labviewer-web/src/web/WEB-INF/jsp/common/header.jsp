<%--
Copyright ScenPro, Inc and SemanticBits, LLC

Distributed under the OSI-approved BSD 3-Clause License.
See http://ncip.github.com/labviewer/LICENSE.txt for details.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div id="header">
   <div id="logo"><a href="#"><img src="<%=request.getContextPath()%>/images/logo_labviewer.gif" width="442" height="41" alt="LabViewer" /></a></div>
    <!--User Details-->
    <c:choose>
        <c:when test="${sessionScope.userName != null}">
            <c:choose>
                <c:when test="${sessionScope.websso_enabled}">
                    <div id="userarea">Welcome, <%=(String) session.getAttribute("userName")%> |  <a id="signOutBtn" href='<c:url value="/loginwebsso.action"/>' class="btn_logout">Log Out</a></div>
                </c:when>        
                <c:otherwise>
                    <div id="userarea">Welcome, <%=(String) session.getAttribute("userName")%> |  <a id="signOutBtn" href='<c:url value="loginlogout.action"/>' class="btn_logout">Log Out</a></div>
                </c:otherwise>
            </c:choose>
        </c:when>
    </c:choose>
</div>
