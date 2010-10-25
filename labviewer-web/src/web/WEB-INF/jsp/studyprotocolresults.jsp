<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<s:form name="Result">
    <s:actionerror/>
    <display:table class="data"  sort="list" pagesize="100" id="row"  name="results"  export="true">
        <display:column titleKey="studyProtocol.nciIdentifier" maxLength= "200" property="nciIdentifier" sortable="true" headerClass="sortable"/>
        <display:column titleKey="studyProtocol.shortTitle" maxLength= "200" property="shortTitle" sortable="true" headerClass="sortable"/>
        <display:column titleKey="studyProtocol.organization"  >
            <a href="#" onclick="showOrganization('${row.id}');">View Organization</a>
        </display:column>
    </display:table>
</s:form>
