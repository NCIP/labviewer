<%--
Copyright ScenPro, Inc and SemanticBits, LLC

Distributed under the OSI-approved BSD 3-Clause License.
See http://ncip.github.com/labviewer/LICENSE.txt for details.
--%>
<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<jsp:include page="/WEB-INF/jsp/common/includejs.jsp"/>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">

<head>
    <title><fmt:message key="studyProtocol.search.title"/></title>
    <s:head/>

    <script type="text/javascript">
        function handleSearchAction() {
            document.studySearchFrom.action = "studyProtocollist.action";
            document.studySearchFrom.submit();
        }
        function showOrganization(pid) {
            showPopup('ajaxHealthcareSiteview.action?studyProtocolId='+pid, '', 'Healthcare Site');
        }         
        function showInvestigator(pid) {
            showPopup('ajaxInvestigatorview.action?studyProtocolId='+pid, '', 'Investigator');
        }         

        function showProtocol(pid) {
            showPopup('ajaxStudyProtocolview.action?studyProtocolId='+pid, '', 'Study Detail');
        }         
        function showParticipant(pid) {
            document.getElementById("studyProtocolId").value=pid;
            document.participantForm.action = "studyparticipantlist.action";
            document.participantForm.submit();
        }
        function resetValues() {
            document.getElementById("shortTitle").value="";
            document.getElementById("nciIdentifier").value="";
        }        
        </script>
</head>
<body>

<!-- main content begins-->
    <c:set var="topic" scope="request" value="study_search"/>
    <h1><fmt:message key="studyProtocol.search.header"/></h1>
    <s:form name="participantForm">
        <s:hidden  name="studyProtocolId"  id="studyProtocolId"/>
        <input type="hidden"  name="userName"   value ="<%=(String) session.getAttribute("userName")%>"/>
    </s:form>
    <s:form name="studySearchFrom">
        <input type="hidden"  name="ssDto.userName"   value ="<%=(String) session.getAttribute("userName")%>"/>
        <jsp:include page="/WEB-INF/tags/failureMessage.jsp"/>
        <table class="form" >
            <tr>
                <td class="label"><fmt:message key="studyProtocol.title"/></label> </td>
                <td>
                    <s:textfield name="ssDto.shortTitle"  id="shortTitle" style="width:300px"/>
                </td>
            </tr>
            <tr>
                <td class="label"><fmt:message key="studyProtocol.identifier"/></label> </td>
                <td>
                    <s:textfield  name="ssDto.nciIdentifier"  id="nciIdentifier" style="width:200px"/>
                </td>
            </tr>

        </table>
        <div class="actionsrow">
            <del class="btnwrapper">
                <ul class="btnrow">
                    <li><li>
                            <s:a href="#" cssClass="btn" onclick="handleSearchAction()"><span class="btn_img"><span class="search">Search</span></span></s:a>
                            <s:a href="#" cssClass="btn" onclick="resetValues();return false"><span class="btn_img"><span class="cancel">Reset</span></span></s:a>
                            
                        </li>
                </ul>
            </del>

        </div>
        <c:choose>
        <c:when test="${results != null}">
            <h2>Search Results</h2>
            <jsp:include page="/WEB-INF/jsp/protocol/result.jsp"/>
        </c:when>
        </c:choose>
   </s:form>

 </div>
 <div class="line"></div>
 
</body>
</html>
