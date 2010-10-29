<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<jsp:include page="/WEB-INF/jsp/common/taglibs.jsp"/>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <title><fmt:message key="labviewer.home.title"/></title>
        <s:head />
    </head>
    <body onload="setFocusToFirstControl();">
     <h1><fmt:message key="labviewer.home.title" /></h1>
        <div class="homepage" style="width:600px">
            <div class="homebanner"><img src="<%=request.getContextPath()%>/images/banner_labviewer.jpg" width="599" height="140" alt="" /></div>
            <h1><fmt:message key="labviewer.home.title2" /></h1>
            <p class="padme8">
            <b> Search for Laboratory Results by: </b>
            </p>
            <ul class="intro">
                    <li> Study Title </li>
                    <li> Study Identifier </li>
                    <li> Participant First and Last Name </li>
                    <li> Participant Identifier </li>
                    <li> Lab Start or End Date </li>
                    <li> Add a Study, Participant or Lab </li>
                    <li> View Healthcare Site and Principal Investigator details for a Study </li>
                    <li> Filter Lab Results by Participant, Lab Test Name, Result (In/Out of Range), or Date Range </li>
                    <li> Send Lab data to other CCTS applications </li>
                    <li> Export Lab Results as .CSV, .XLS or .XML       </li>                   
            </ul>                    
        </div>
    </body>
</html>