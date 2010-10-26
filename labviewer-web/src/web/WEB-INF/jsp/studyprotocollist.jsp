<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
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
            showPopup('ajaxHealthcareSiteview.action?studyProtocolId='+pid, '', 'Organization');
        }         
        </script>
</head>
<body>
<!-- main content begins-->

    <h1><fmt:message key="studyProtocol.search.header"/></h1>
    <s:form name="studySearchFrom">
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
                    <s:textfield  name="ssDto.nciIdentifier"  id="studyIdentifier" style="width:200px"/>
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
        <s:if test="results != null">
        <h2>Search Results</h2>
        <jsp:include page="/WEB-INF/jsp/studyprotocolresults.jsp"/>
         </s:if>

   </s:form>

 </div>
 <div class="line"></div>
 
</body>
</html>
