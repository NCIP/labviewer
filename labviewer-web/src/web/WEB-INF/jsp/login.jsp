<link href="<%=request.getContextPath()%>/styles/style.css" rel="stylesheet" type="text/css" media="all"/>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<jsp:include page="/WEB-INF/jsp/common/taglibs.jsp"/>
<page:applyDecorator name="main">
<head>
    <c:set var="topic" scope="request" value="login"/>
    <script type="text/javascript">
        function loginAction() {
            document.loginForm.action = "loginsubmit.action";
            document.loginForm.submit();
        }
    </script>    
    
</head>
<body>
<h1>Log In</h1>
<div class="box">
    <s:form name="loginForm">
    <jsp:include page="/WEB-INF/tags/failureMessage.jsp"/>
    
    <table style="margin:0 auto">
        <tr><td class="space" colspan="2">&nbsp;</td></tr>
        <tr>
            <td class="label" scope="row">
                <label for="username">Username:</label>
            </td>
            <td class="value">
                <input name="username" maxlength="100" size="25" type="text">
            </td>
        </tr>
        <tr>
            <td class="label" scope="row">
                <label for="password">Password:</label>
            </td>
            <td class="value"><input name="password" maxlength="100" size="25" type="password"></td>
        </tr>
    </table>
    </s:form>
    <div class="actionsrow">
        <del class="btnwrapper">
            <ul class="btnrow">
                <li>
                    <s:a href="#" cssClass="btn" onclick="loginAction()"><span class="btn_img"><span class="save">Login</span></span></s:a>
                </li>
            </ul>
        </del>
    </div>
</form>
</div>
</body>
</page>