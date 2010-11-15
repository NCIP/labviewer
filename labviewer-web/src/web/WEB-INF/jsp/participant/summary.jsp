<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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

</table>
<div class="line"></div>
