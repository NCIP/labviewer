<%--
Copyright ScenPro, Inc and SemanticBits, LLC

Distributed under the OSI-approved BSD 3-Clause License.
See http://ncip.github.com/labviewer/LICENSE.txt for details.
--%>
<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title><fmt:message key="studyParticipant.search.title"/></title>
    <s:head/>
    <script type="text/javascript">
        function resetValues() {
            document.getElementById("firstName").value="";
            document.getElementById("lastName").value="";
            document.getElementById("mrn").value="";
        }        
    
        function handleSearchAction() {
            document.studyParticipantFrom.action = "studyparticipantlist.action";
            document.studyParticipantFrom.submit();
        }
        function showLabs(partid) {
        	document.getElementById("studyParticipantId").value=partid;
            document.labForm.action = "lablist.action";
            document.labForm.submit();
        }
        </script>
</head>
<body>
<!-- main content begins-->

    <h1><fmt:message key="studyParticipant.search.header"/></h1>
    <c:set var="topic" scope="request" value="participant"/>
    <jsp:include page="/WEB-INF/jsp/protocol/summary.jsp"/>
    <h2>Search Criteria</h2>
    <s:form name="labForm">
        <s:hidden  name="labSearhDto.studyParticipantId"  id="studyParticipantId" />
        <s:hidden  name="labSearhDto.studyProtocolId" id = "studyProtocolId"/> 
    </s:form>
    <s:form name="studyParticipantFrom">
        <table class="form" >
            <tr>
                <td class="label"><fmt:message key="firstName"/></label> </td>
                <td>
                    <s:textfield name="spsDto.firstName"  id="firstName" style="width:200px"/>
                </td>
            </tr>
            <tr>
                <td class="label"><fmt:message key="lastName"/></label> </td>
                <td>
                    <s:textfield  name="spsDto.lastName"  id="lastName" style="width:200px"/>
                </td>
            </tr>
            <tr>
                <td class="label"><fmt:message key="mrn"/></label> </td>
                <td>
                    <s:textfield  name="spsDto.identifier"  id="mrn" style="width:200px"/>
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
        <display:table class="data"  sort="list" pagesize="100" id="row"  name="results"  requestURI="">
            <display:column titleKey="siteIdentifier"  property="healthcareSiteDto.nciInstituteCode" sortable="true" headerClass="sortable"/>
            <display:column titleKey="siteName" maxLength= "200" property="healthcareSiteDto.name" sortable="true" headerClass="sortable"/>
            <display:column titleKey="mrn" property="identifier" sortable="true" headerClass="sortable"/>
            <display:column titleKey="firstName"  property="firstName" sortable="true" headerClass="sortable"/>
            <display:column titleKey="lastName"  property="lastName" sortable="true" headerClass="sortable"/>
            <display:column >
                 <s:a href="#" cssClass="btn" onclick="showLabs(%{#attr.row.id})"><span class="btn_img"><span class="search">View Labs</span></span></s:a>
            </display:column>
            
        </display:table>

   </s:form>

 </div>
 <div class="line"></div>
 
</body>
</html>
