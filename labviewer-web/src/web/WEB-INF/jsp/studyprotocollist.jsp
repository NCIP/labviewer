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
            document.getElementById("studySearchWaitMsg").style.display='block'; 
            document.studySearchFrom.style.display='none'; 
            setTimeout('document.images["progress"].src = "../images/loading.gif"', 400);
            document.studySearchFrom.action = "studyProtocollist.action";
            document.studySearchFrom.submit();
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
                    <input type="text" name="studyTitle"  id="studyTitle" style="width:300px"/>
                </td>
            </tr>
            <tr>
                <td class="label"><fmt:message key="studyProtocol.identifier"/></label> </td>
                <td>
                    <input type="text" name="studyIdentifier"  id="studyTitle" style="width:200px"/>
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


   </s:form>

 </div>
 <div class="line"></div>

    <div id="studySearchWaitMsg" align="center" style="DISPLAY:none">
        <font color="green" size="4">
        <p style="border-color:black;border-style:solid;">Please wait while the system processes your request...</p></font><br/><br/>
        <img id="progress2" src="../images/loading.gif"/>Loading...
    </div> 
</body>
</html>
