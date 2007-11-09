<%--
    An example decorator for tabbed workflow pages.

    Controllers which use this page must include two special entries in
    their referenceData:
        - flow: a gov.nih.nci.cabig.caaers.web.Flow instance describing the flow
        - tab: a gov.nih.nci.cabig.caaers.web.Tab instance for the current page
--%>
<%@taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="laf" uri="http://gforge.nci.nih.gov/projects/ctmscommons/taglibs/laf" %>

<!DOCTYPE html
    PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<title>CTMS Commons LaF Demo || ${flow.name} || ${tab.longTitle}</title>
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


<div class="tabpane">
    <laf:workflowTabs tab="${tab}" flow="${flow}"/>

    <laf:body title="${flow.name} || ${tab.longTitle}">
        <c:set var="hasSummary" value="${not empty summary}"/>
        <c:if test="${hasSummary}">
            <div id="summary-pane" class="pane">
                <laf:box title="Summary">
                    <c:forEach items="${summary}" var="summaryEntry">
                    <div class="row">
                        <div class="label">${summaryEntry.key}</div>
                        <div class="value">${empty summaryEntry.value ? '<em class="none">None</em>' : summaryEntry.value}</div>
                    </div>
                    </c:forEach>
                </laf:box>
            </div>
        </c:if>

        <div id="main${hasSummary ? '' : '-no-summary'}-pane" class="pane">
            <decorator:body/>
        </div>
    </laf:body>
</div>

<laf:footer/>
<%-- in a real application, you'd probably want to make this dependent on a config option --%>
<c:if test="${true}">
    <laf:debugInfo/>
</c:if>
</body>
</html>
