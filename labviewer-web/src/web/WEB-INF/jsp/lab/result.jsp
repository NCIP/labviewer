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
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="gov.nih.nci.lv.dto.LabSearchDto"%>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title><fmt:message key="lab.search.title"/></title>
    <s:head/>
    <script type="text/javascript">
        function resetValues() {
            document.getElementById("labTestCode").value="ALL";
            document.getElementById("range").value="ALL";
        }        
    
        function submitSearchAction() {
            document.labForm.action = "lablist.action";
            document.labForm.submit();
        }

        function submitCAERSAction() {
            document.getElementById("submitWaitMsg").style.display='block'; 
            document.labForm.action = "labcaers.action";
            document.labForm.submit();
        }
        function submitC3DAction() {
            document.getElementById("submitWaitMsg").style.display='block'; 
            document.labForm.action = "labc3d.action";
            document.labForm.submit();
        }

        function sendLabsClick(rowId){
            var str = document.labForm.ids.value;
            var pos = str.indexOf(rowId);
            if (pos >= 0) { 
                var str3 = rowId+",";
                var str2 = str.replace(str3,'');
                document.labForm.ids.value=str2;
            } else {
                document.labForm.ids.value += rowId;
                document.labForm.ids.value += ",";
            }
        }

        </script>
</head>
<body>
  <jsp:scriptlet>
        request.setAttribute("dyndecorator", new org.displaytag.decorator.TableDecorator()
        {
            public String addRowClass()
            {
                return ((LabSearchDto)getCurrentRowObject()).getIsWithinRange() ? "" : "error_msg";
            }
        });
  </jsp:scriptlet>

<!-- main content begins-->

    <h1><fmt:message key="lab.search.header"/></h1>
    <c:set var="topic" scope="request" value="labs"/>
    <jsp:include page="/WEB-INF/jsp/protocol/summary.jsp"/>
    <jsp:include page="/WEB-INF/jsp/participant/summary.jsp"/>
    <h3><fmt:message key="search.criteria"/></h3>
    <s:form name="labForm">
        <s:hidden  name="labSearhDto.studyParticipantId"  id="studyParticipantId" />
        <s:hidden  name="labSearhDto.studyProtocolId" id = "studyProtocolId"/>
        <s:hidden  name="labSearhDto.labIds" id = "ids"/>
        <table class="form" >
            <tr>
                <td class="label"><fmt:message key="lab.labTest"/></label> </td>
                <td>
                    <s:select  id = "labTestCode"   name="labSearhDto.labTestCode"
                        list="labResults"  
                        listKey="labTestCode" listValue="labTestCode" headerKey="ALL" headerValue="All" cssStyle="width:206px" />
                </td>
                <td class="label"><fmt:message key="lab.numericResult"/></label> </td>
                <td>
                    <s:select  id="range"   name="labSearhDto.range"
                        list="#{'INRANGE':'In Range','OUTOFRANGE':'Out of Range'}" headerKey="ALL" headerValue="All" cssStyle="width:106px" />
                </td>
                
            </tr>
        </table>
        <div class="actionsrow">
            <del class="btnwrapper">
                <ul class="btnrow">
                    <li><li>
                            <s:a href="#" cssClass="btn" onclick="submitSearchAction()"><span class="btn_img"><span class="search">Search</span></span></s:a>
                            <s:a href="#" cssClass="btn" onclick="resetValues();return false"><span class="btn_img"><span class="cancel">Reset</span></span></s:a>
                        </li>
                </ul>
            </del>
        </div>
        <jsp:include page="/WEB-INF/tags/failureMessage.jsp"/>
        <jsp:include page="/WEB-INF/tags/successMessage.jsp"/>
        <div id="submitWaitMsg" align="center" style="DISPLAY:none">
            <font color="green" size="4">
            <p style="border-color:black;border-style:solid;">Please wait while the system processes your request...<br/>Do not click the [Back] button on your browser</p></font><br/><br/>
            <img id="progress2" src="./images/loading.gif"/>Loading...
        </div> 
        <display:table class="data"  sort="list" pagesize="1000" id="row"  name="sessionScope.labResults"  requestURI="" decorator="dyndecorator" export="true">
            <display:setProperty name="export.excel.filename" value="LabResults.xls" />
            <display:setProperty name="export.csv.filename" value="LabResults.csv" />
            <display:setProperty name="export.xml.filename" value="LabResults.xml" />
            <display:setProperty name="export.excel.include_header" value="true" />
            <display:setProperty name="export.csv.include_header" value="true" />
            <display:setProperty name="export.xml.include_header" value="true" />
            <display:column titleKey="lab.sendLabs" >
                      <s:checkbox name="sendLabs" onclick="sendLabsClick(%{#attr.row.id})"/>
            </display:column>
            <display:column titleKey="lab.actualStartDate"  property="actualStartDate" format="{0,date,yyyy-MM-dd} {0,time,hh:mm:ss a}" sortable="true" headerClass="sortable"/>
            <display:column titleKey="lab.labTest"  property="labTestCode" sortable="true" headerClass="sortable"/>
            <display:column titleKey="lab.testResult"  property="textResult" sortable="true" headerClass="sortable"/>
            <display:column titleKey="lab.uom"  property="uom" sortable="true" headerClass="sortable"/>
            <display:column titleKey="lab.numericResult"  property="numericResult" sortable="true" headerClass="sortable"/> 
            <display:column titleKey="lab.lowerLimit"  property="referenceLowRange" sortable="true" headerClass="sortable"/> 
            <display:column titleKey="lab.upperLimit"  property="referenceHighRange" sortable="true" headerClass="sortable"/> 
            <display:column titleKey="lab.aeSentDate"  property="aeSentDate" sortable="true" format="{0,date,MM-dd-yyyy}" headerClass="sortable"/> 
            <display:column titleKey="lab.c3dSentDate"  property="cdmsSentDate" sortable="true" format="{0,date,MM-dd-yyyy}"  headerClass="sortable"/> 
        </display:table>

         <c:choose>
             <c:when test="${sessionScope.websso_enabled}">
		        <div class="line"></div>
		        <div class="actionsrow">
		            <del class="btnwrapper">
		                <ul class="btnrow">
		                    <li><li>
		                            <s:a href="#" cssClass="btn" onclick="submitCAERSAction()"><span class="btn_img"><span class="save">Submit Labs to caAERS</span></span></s:a>
		                            <s:a href="#" cssClass="btn" onclick="submitC3DAction()"><span class="btn_img"><span class="save">Submit Labs to C3D</span></span></s:a>
		                        </li>
		                </ul>
		            </del>
		        </div>
           </c:when>        
       </c:choose>
   </s:form>

 </div>
 <div class="line"></div>
 
</body>
</html>
