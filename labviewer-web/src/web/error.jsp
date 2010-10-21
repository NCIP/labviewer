<%@ page language="java" isErrorPage="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
<head>
    <title><fmt:message key="errorPage.title"/></title>
</head>

<body id="error">
    <div id="page">
        <div id="content" class="clearfix">
            <div id="main">
                <s:actionerror/>
                <h1><fmt:message key="errorPage.heading"/></h1>
                <c:if test="${initParam['showErrorStackTraces'] == 'true'}">
                    <% if (exception != null) { %>
                        <pre><% exception.printStackTrace(new java.io.PrintWriter(out)); %></pre>
                    <% } else if (request.getAttribute("javax.servlet.error.exception") != null) { %>
                        <pre><% ((Exception)request.getAttribute("javax.servlet.error.exception"))
                                               .printStackTrace(new java.io.PrintWriter(out)); %></pre>
                    <% } else { %>
                        <pre><s:property value="%{exceptionStack}"/></pre>
                    <% } %>
                </c:if>
            </div>
        </div>
    </div>
</body>
</html>
