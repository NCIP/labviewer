<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="lv" %>

<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title><decorator:title default="LV"/></title>
    <jsp:include page="/WEB-INF/jsp/common/includecss.jsp"/>
<script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/subModal.js"/>"></script>
<script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/subModalcommon.js"/>"></script>
<script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/calendarpopup.js"/>"></script>
<script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/tooltip.js"/>"></script>
<script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/showhide.js"/>"></script>
<script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/popup.js"/>"></script>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/Help.js'/>"></script>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/wwhapi.js'/>"></script>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/wwhbaseurl.js'/>"></script>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/webtoolkit.scrollabletable.js'/>"></script>
    <decorator:head/>
</head>
<style type="text/css">
   BODY { width:1221px }
</style>
<a href="#content" id="navskip">Skip to Page Content</a>
<div id="wrapper">
    
    <jsp:include page="/WEB-INF/jsp/common/nciheader.jsp"/>
    <jsp:include page="/WEB-INF/jsp/common/header.jsp"/>
    <div id="main">
       <div id="contentwrapper">
           <div id="content">
                <a href="${sessionScope.helpLink}" target="_blank" class="helpbutton">Help</a>
               <decorator:body/>
           </div>
           <div class="clear"></div>
       </div>
       <div id="leftnav">
           <ul class="menu">
                <jsp:include page="/WEB-INF/jsp/common/menu.jsp"/>
               <jsp:include page="/WEB-INF/jsp/common/quicklinks.jsp"/>
           </ul>
       </div>
    </div>
    <div class="clear"><br /></div>
    <jsp:include page="/WEB-INF/jsp/common/footer.jsp"/>
</div>
</body>
</html>
