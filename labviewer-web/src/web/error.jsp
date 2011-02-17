<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
<head>
    <title><fmt:message key="errorPage.title"/></title>
</head>
     <h1>Oops, You have found an Error , Please login in your defect here <a href="https://tracker.nci.nih.gov/browse/CTMSLV" target="_blank">LabViewer Jira</a></h1>
    <c:if test="${sessionScope.failureMessage  != null}">
        <div class="error_msg">
            <strong>Error Message : </strong> 
            <c:out value="${sessionScope.failureMessage }"/>
            <br>
            <c:if test="${sessionScope.failureDetailedMessage  != null}">
                <strong> Detailed Error Message : </strong>
                <c:out value="${sessionScope.failureDetailedMessage }"/>.
                <c:remove var="failureDetailedMessage" scope="session"/>
            </c:if>
        </div>
        <c:remove var="failureMessage" scope="session"/>
    </c:if>
         

</html>
