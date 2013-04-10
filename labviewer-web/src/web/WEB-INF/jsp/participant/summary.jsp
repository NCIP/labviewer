<%--
Copyright ScenPro, Inc and SemanticBits, LLC

Distributed under the OSI-approved BSD 3-Clause License.
See http://ncip.github.com/labviewer/LICENSE.txt for details.
--%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="gov.nih.nci.lv.dto.StudyParticipantSearchDto" %> 
<h3> <fmt:message key="studyParticipant.details"/> </h3>
<table class="form" >
  <tr>
    <td class="label"> <fmt:message key="mrn"/>:</td>
    <td align="left"> <c:out value="${studyPartSearchDto.identifier }"/></td>
    <td class="label"> <fmt:message key="firstName"/>:</td>
    <td align="left"> <c:out value="${studyPartSearchDto.firstName }"/></td>
    <td class="label"> <fmt:message key="lastName"/>:</td>
    <td align="left"> <c:out value="${studyPartSearchDto.lastName }"/></td>
   </tr>                
    <%
    String caAERSurl = (String) session.getAttribute("caaers_url");
    StudyParticipantSearchDto spDto = (StudyParticipantSearchDto) session.getAttribute("studyPartSearchDto");
    String gridId =  spDto.getGridId();
    String url = caAERSurl + gridId;
    %>
    
    <c:if test= "${sessionScope.caaersAccess =='yes'}">
    <tr>
        <td colspan="6" align="center">
        <c:out value="${url}"/>
        <a href="<%=url%>" target="_blank">View this patient in caAERS</a>
        </td>
    </tr>
    </c:if>
    
</table>
<div class="line"></div>
