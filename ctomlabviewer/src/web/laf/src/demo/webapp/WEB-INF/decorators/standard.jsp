<%@ page import="gov.nih.nci.cabig.ctms.web.chrome.Section" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Arrays" %>
<%--
    An example decorator for standard (non-tabbed-flow) pages.
--%>
<%@taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="laf" uri="http://gforge.nci.nih.gov/projects/ctmscommons/taglibs/laf" %>

<!DOCTYPE html
    PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<title>CTMS Commons LaF Demo || <decorator:title/></title>
<laf:stylesheets/>
<laf:scripts/>
<link href="<c:url value="/css/ctms-commons-overrides.css"/>" rel="stylesheet" />
<decorator:head/>
</head>
<body>
<%-- In a real application, this block would probably be defined in another tagfile
    and shared among decorators --%>
<laf:header>
    <jsp:attribute name="logoText">ctms LaF</jsp:attribute>
    <jsp:attribute name="logoImageUrl"><c:url value="/images/chrome/logo.png"/></jsp:attribute>
    <jsp:attribute name="tagline">CTMS Commons LaF Demo</jsp:attribute>
    <jsp:attribute name="taglineImageUrl"><c:url value="/images/chrome/tagline.png"/></jsp:attribute>
    <jsp:attribute name="logoutUrl"><c:url value="/logout"/></jsp:attribute>
    <jsp:attribute name="renderSection">
        <laf:sectionTab section="${section}" currentSection="${currentSection}"/>
    </jsp:attribute>
    <jsp:attribute name="renderTask">
        <laf:taskLink task="${task}"/>
    </jsp:attribute>
</laf:header>

<c:set var="__decorator_title"><decorator:title/></c:set>
<laf:body title="${__decorator_title}">
    <laf:flashMessage/>
    <decorator:body/>
</laf:body>

<laf:footer/>
<%-- in a real application, you'd probably want to make this dependent on a config option --%>
<c:if test="${true}">
    <laf:debugInfo/>
</c:if>
</body>
</html>
