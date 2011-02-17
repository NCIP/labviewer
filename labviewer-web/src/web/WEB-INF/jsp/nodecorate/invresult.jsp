<link href="<%=request.getContextPath()%>/styles/style.css" rel="stylesheet" type="text/css" media="all"/>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<jsp:include page="/WEB-INF/jsp/protocol/header.jsp"/>


<s:form name="invResult">
    <h2> Investigator </h2></td>
    <s:actionerror/>
    <display:table class="data"  sort="list" pagesize="100" id="row"  name="results"  requestURI="ajaxInvestigatorview.action" >
        <display:setProperty name="paging.banner.onepage" value="Investigator" />
        <display:column titleKey="firstName"    maxLength= "100" property="firstName" sortable="true" headerClass="sortable"/>
        <display:column titleKey="lastName"    property="lastName" sortable="true" headerClass="sortable"/>
        <display:column titleKey="zip"              property="postalCode" sortable="true" headerClass="sortable"/>
        <display:column titleKey="country"         property="countryCode" sortable="true" headerClass="sortable"/>
    </display:table>
    
    <div class="actionsrow">
         <del class="btnwrapper">
            <ul class="btnrow">
               <li>         
                   <s:a href="#" cssClass="btn" onclick="window.top.hidePopWin();"><span class="btn_img"><span class="cancel">Close</span></span></s:a>
                   </li>
               </ul>   
          </del>
    </div>
    
</s:form>
