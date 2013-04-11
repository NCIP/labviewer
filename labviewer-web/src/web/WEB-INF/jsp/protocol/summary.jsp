<%--
Copyright ScenPro, Inc and SemanticBits, LLC

Distributed under the OSI-approved BSD 3-Clause License.
See http://ncip.github.com/labviewer/LICENSE.txt for details.
--%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<h3> <fmt:message key="studyProtocol.details"/></h3>
<table class="form" >
  <tr>
    <td class="label"> <fmt:message key="studyProtocol.nciIdentifier"/>:</td>
    <td > <c:out value="${studySearchDto.nciIdentifier }"/></td>
    <td class="label"> <fmt:message key="studyProtocol.title"/>:</td>
    <td align="left"> <c:out value="${studySearchDto.shortTitle }"/></td>
   </tr>                
</table>
<div class="line"></div>
