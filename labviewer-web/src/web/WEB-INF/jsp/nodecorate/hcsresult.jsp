<link href="<%=request.getContextPath()%>/styles/style.css" rel="stylesheet" type="text/css" media="all"/>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<jsp:include page="/WEB-INF/jsp/protocol/header.jsp"/>


<s:form name="hcsResult">
	<h2> Healthcare Sites</h2></td>
    <s:actionerror/>
    <display:table class="data"  sort="list" pagesize="100" id="row"  name="results"  requestURI="ajaxHealthcareSiteview.action" >
        <display:setProperty name="paging.banner.onepage" value="Healtcare Sites" />
        <display:column titleKey="hcs.nciIdentifier"    maxLength= "200" property="nciInstituteCode" sortable="true" headerClass="sortable"/>
        <display:column titleKey="hcs.name"             maxLength= "200" property="name" sortable="true" headerClass="sortable"/>
        <display:column titleKey="hcs.streetAddress"    maxLength= "200" property="streetAddress" sortable="true" headerClass="sortable"/>
        <display:column titleKey="hcs.city"             property="city" sortable="true" headerClass="sortable"/>
        <display:column titleKey="hcs.zip"              property="postalCode" sortable="true" headerClass="sortable"/>
        <display:column titleKey="hcs.country"         property="countryCode" sortable="true" headerClass="sortable"/>
        <display:column titleKey="hcs.state"            property="stateCode" sortable="true" headerClass="sortable"/>
        <display:column titleKey="hcs.updatedDate"      property="ctomUpdateDate" />
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
