<%--
Copyright ScenPro, Inc and SemanticBits, LLC

Distributed under the OSI-approved BSD 3-Clause License.
See http://ncip.github.com/labviewer/LICENSE.txt for details.
--%>
<link href="<%=request.getContextPath()%>/styles/style.css" rel="stylesheet" type="text/css" media="all"/>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/struts-tags" prefix="s" %>


<br>
<h2> Study Details</h2>
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
 
<div class="actionsrow">
     <del class="btnwrapper">
        <ul class="btnrow">
           <li>         
               <s:a href="#" cssClass="btn" onclick="window.top.hidePopWin();"><span class="btn_img"><span class="cancel">Close</span></span></s:a>
               </li>
           </ul>   
      </del>
</div>

