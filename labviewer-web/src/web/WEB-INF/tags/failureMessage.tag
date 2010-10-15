<%@ tag display-name="failureMessage"  description="Displays the failure messages"  body-content="empty" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%-- Success Messages --%>

<c:if test="${requestScope.failureMessage  != null}">
<div class="error_msg">
	<strong>Error Message:</strong> <c:out value="${requestScope.failureMessage }"/>.
</div>
<c:remove var="failureMessage" scope="request"/>
</c:if>
