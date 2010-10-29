<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<s:form name="Result">
   <s:actionerror/>
    <display:table class="data"  requestURI="studyProtocollist.action" sort="list" pagesize="100" id="row"  name="results"  export="false">
        <display:setProperty name="pagination.pagenumber.param" value="results.pageNumber" />
        <display:setProperty name="paging.banner.item_name" value="Studies" />               
        <display:setProperty name="paging.banner.items_name" value="Studies" />         
        <display:column titleKey="studyProtocol.nciIdentifier" maxLength= "30" property="nciIdentifier" sortable="true" headerClass="sortable"/>
        <display:column titleKey="studyProtocol.shortTitle" maxLength= "70" property="shortTitle" sortable="true" headerClass="sortable"/>
        <display:column titleKey="studyProtocol.phaseCode" property="phaseCode" sortable="true" headerClass="sortable"/>
        <display:column titleKey="studyProtocol.statusCode" property="statusCode" sortable="true" headerClass="sortable"/>
        <display:column titleKey="studyProtocol.healthcareSite"  >
            <s:a href="#" cssClass="btn" onclick="showOrganization(%{#attr.row.id})"><span class="btn_img"><span class="organization">Healthcare</span></span></s:a>
        </display:column>
        <display:column titleKey="studyProtocol.participant"  >
             <s:a href="#" cssClass="btn" onclick="showParticipant(%{#attr.row.id})"><span class="btn_img"><span class="person">Participants</span></span></s:a>
        </display:column>

    </display:table>
</s:form>
