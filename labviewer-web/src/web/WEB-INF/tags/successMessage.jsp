<%--
Copyright ScenPro, Inc and SemanticBits, LLC

Distributed under the OSI-approved BSD 3-Clause License.
See http://ncip.github.com/labviewer/LICENSE.txt for details.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:if test="${requestScope.successMessage  != null}">
<div class="confirm_msg">
	<strong>Success Message:</strong> <c:out value="${requestScope.successMessage}"/>.
</div>
<c:remove var="successMessage" scope="request"/>
</c:if>
