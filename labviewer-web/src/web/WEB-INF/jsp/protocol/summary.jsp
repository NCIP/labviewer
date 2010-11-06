<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<h2> Study Details</h2>
<table class="form" >
  <tr>
    <td class="label"> <fmt:message key="studyProtocol.nciIdentifier"/>:</td>
    <td > <c:out value="${studySearchDto.nciIdentifier }"/></td>
    <td class="label"> <fmt:message key="studyProtocol.title"/>:</td>
    <td align="left"> <c:out value="${studySearchDto.shortTitle }"/></td>
   </tr>                
  <tr>
    <td class="label"> <fmt:message key="studyProtocol.phaseCode"/>:</td>
    <td align="left"> <c:out value="${studySearchDto.phaseCode }"/></td>
    <td class="label"> <fmt:message key="studyProtocol.statusCode"/>:</td>
    <td align="left"> <c:out value="${studySearchDto.statusCode }"/></td>
   </tr>                

</table>
<div class="line"></div>
