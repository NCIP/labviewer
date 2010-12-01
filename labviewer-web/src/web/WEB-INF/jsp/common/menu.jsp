<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<li class="stdnav">
<div>LabViewer</div>
<ul>
    <c:if test="${sessionScope.allowAccess == 'yes'}">
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
        <c:if test="${sessionScope.studyPartSearchDto != null}">
            <c:choose>
               <c:when test="${requestScope.topic =='labs'}">
                    <li><a href="lablist.action" class="selected">Labs</a></li>
               </c:when>
               <c:otherwise>
                    <li><a href="lablist.action" >Labs</a></li>
               </c:otherwise>
            </c:choose>
        </c:if>
    </c:if>
    <li><a href="${sessionScope.helpLink}" target="_blank">Help</a></li>
    <c:if test= "${sessionScope.adminAccess =='yes'}">
        <li><div>Admin</div>
        <c:choose>
           <c:when test="${requestScope.topic =='property'}">
                <li><a href="propertylist.action" class="selected">General Settings</a></li>
           </c:when>
           <c:otherwise>
                <li><a href="propertylist.action" >General Settings</a></li>
           </c:otherwise>
        </c:choose>
    </c:if>

</ul>
</li>



