<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="lv" %>
<s:form name="Result">
    <display:table class="data"  requestURI="" sort="list" pagesize="100" id="row"  name="results"  export="false">
        <display:setProperty name="pagination.pagenumber.param" value="results.pageNumber" />
        <display:setProperty name="paging.banner.item_name" value="Studies" />               
        <display:setProperty name="paging.banner.items_name" value="Studies" />         
        <display:column titleKey="studyProtocol.nciIdentifier" maxLength= "40" >
            <s:a href="#" onclick="showProtocol(%{#attr.row.id})"> <c:out value="${row.nciIdentifier}"/></s:a>
        </display:column>
        <display:column titleKey="studyProtocol.shortTitle" maxLength= "70" property="shortTitle" sortable="true" headerClass="sortable"/>
        <display:column titleKey="studyProtocol.phaseCode" property="phaseCode" sortable="true" headerClass="sortable"/>
        <display:column titleKey="studyProtocol.statusCode" property="statusCode" sortable="true" headerClass="sortable"/>
        <display:column titleKey="studyProtocol.healthcareSite" >
            <s:a href="#" cssClass="btn" onclick="showOrganization(%{#attr.row.id})"><span class="btn_img"><span class="organization">View </span></span></s:a>
        </display:column>
        <display:column titleKey="studyProtocol.investigator" >
            <s:a href="#" cssClass="btn" onclick="showInvestigator(%{#attr.row.id})"><span class="btn_img"><span class="person">View </span></span></s:a>
        </display:column>
        <display:column titleKey="studyProtocol.participant"  >
             <s:a href="#" cssClass="btn" onclick="showParticipant(%{#attr.row.id})"><span class="btn_img"><span class="person">Participant</span></span></s:a>
        </display:column>
    </display:table>
</s:form>
