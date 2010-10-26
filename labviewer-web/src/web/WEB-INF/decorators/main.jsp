<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title><decorator:title default="LV"/></title>
    <%@ include file="/WEB-INF/jsp/common/includecss.jsp" %>
    <%@ include file="/WEB-INF/jsp/common/includejs.jsp" %>
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

