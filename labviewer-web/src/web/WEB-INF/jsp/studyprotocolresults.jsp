<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<s:form name="Result">
    <s:actionerror/>
    <display:table class="data"  sort="list" pagesize="100" id="row"  name="results"  export="true">
        <display:column titleKey="studyProtocol.nciIdentifier" maxLength= "200" property="nciIdentifier" sortable="true" headerClass="sortable"/>
        <display:column titleKey="studyProtocol.shortTitle" maxLength= "200" property="shortTitle" sortable="true" headerClass="sortable"/>
        <display:column titleKey="studyProtocol.phaseCode" property="phaseCode" sortable="true" headerClass="sortable"/>
        <display:column titleKey="studyProtocol.healthcareSite"  >
	         <s:a href="#" onclick="showOrganization(%{#attr.row.id})">
	             <img src="<%=request.getContextPath()%>/images/ico_organization.gif"
	                 alt="Healtcare Site" width="16" height="16" />
	         </s:a>
        </display:column>
        <display:column titleKey="studyProtocol.investigator"  >
                    <s:a href="#" onclick="showInvestigator(%{#attr.row.id})">Investigator
                        <img src="<%=request.getContextPath()%>/images/ico_person.gif"
                            alt="Investigator" width="16" height="16" />
                    </s:a>
        </display:column>
        <display:column titleKey="studyProtocol.participant"  >
                    <s:a href="#" onclick="showParticipant(%{#attr.row.id})">Participant
                        <img src="<%=request.getContextPath()%>/images/ico_person.gif"
                            alt="Investigator" width="16" height="16" />
                    </s:a>
        </display:column>

    </display:table>
</s:form>
