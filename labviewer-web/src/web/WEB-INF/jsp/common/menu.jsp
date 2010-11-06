<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<li class="stdnav">
<div>LabViewer</div>
<ul>
    <c:choose>
       <c:when test="${requestScope.topic =='study_search'}">
            <li><a href="studyProtocolexecute.action" class="selected">Study Search</a></li>
       </c:when>
       <c:otherwise>
            <li><a href="studyProtocolexecute.action" >Study Search</a></li>
       </c:otherwise>
    </c:choose>
    <c:if test="${sessionScope.studySearchDto != null}">
    <c:choose>
       <c:when test="${requestScope.topic =='participant'}">
            <li><a href="studyparticipantlist.action" class="selected">Participant</a></li>
       </c:when>
       <c:otherwise>
            <li><a href="studyparticipantlist.action" >Participant</a></li>
       </c:otherwise>
    </c:choose>
    
    </c:if>
    <li><a href="${sessionScope.helpLink}" target="_blank">Help</a></li>
</ul>
</li>



