<link href="<%=request.getContextPath()%>/styles/style.css" rel="stylesheet" type="text/css" media="all"/>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<s:form name="hcsResult">
    <s:actionerror/>
    <display:table class="data"  sort="list" pagesize="100" id="row"  name="results"  export="true">
        <display:column titleKey="hcs.nciIdentifier"    maxLength= "200" property="nciInstituteCode" sortable="true" headerClass="sortable"/>
        <display:column titleKey="hcs.name"             maxLength= "200" property="name" sortable="true" headerClass="sortable"/>
        <display:column titleKey="hcs.streetAddress"    maxLength= "200" property="streetAddress" sortable="true" headerClass="sortable"/>
        <display:column titleKey="hcs.state"            property="stateCode" sortable="true" headerClass="sortable"/>
        <display:column titleKey="hcs.city"             property="city" sortable="true" headerClass="sortable"/>
        <display:column titleKey="hcs.zip"              property="postalCode" sortable="true" headerClass="sortable"/>
        <display:column titleKey="hcs.ccountry"         property="countryCode" sortable="true" headerClass="sortable"/>
        <display:column titleKey="hcs.updatedDate"      property="ctomUpdateDate" />
    </display:table>
</s:form>
