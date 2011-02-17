<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<br>
<h3> Study Details</h3>
<table class="form" >
  <tr>
    <td class="label"> <fmt:message key="studyProtocol.nciIdentifier"/>:</td>
    <td > <c:out value="${studySearchDto.nciIdentifier }"/></td>
   </tr>                
  <tr>
    <td class="label"> <fmt:message key="studyProtocol.title"/>:</td>
    <td align="left"> <c:out value="${studySearchDto.shortTitle }"/></td>
   </tr>                
  <tr>
    <td class="label"> <fmt:message key="studyProtocol.phaseCode"/>:</td>
    <td align="left"> <c:out value="${studySearchDto.phaseCode }"/></td>
   </tr>                
  <tr>
    <td class="label"> <fmt:message key="studyProtocol.statusCode"/>:</td>
    <td align="left"> <c:out value="${studySearchDto.statusCode }"/></td>
   </tr>                

</table>

<br>
<br>