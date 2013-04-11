<%--
Copyright ScenPro, Inc and SemanticBits, LLC

Distributed under the OSI-approved BSD 3-Clause License.
See http://ncip.github.com/labviewer/LICENSE.txt for details.
--%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%> 

<tiles:insert page="/WEB-INF/jsp/layout/BaseLayout.jsp" flush="true"> 
<tiles:put name="OrgHeader" value="/WEB-INF/jsp/header/OrgHeader.jsp" />		
<tiles:put name="Title"	value="500 Error Page"/>
<tiles:put name="Content" value="/WEB-INF/jsp/content/main/Error500Content.jsp"/>
</tiles:insert>
