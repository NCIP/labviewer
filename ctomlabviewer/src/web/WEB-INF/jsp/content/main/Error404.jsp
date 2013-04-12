<%--
Copyright ScenPro, Inc and SemanticBits, LLC

Distributed under the OSI-approved BSD 3-Clause License.
See http://ncip.github.com/labviewer/LICENSE.txt for details.
--%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%> 
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%> 
<%@ taglib uri="http://jakarta.apache.org/struts/tags-template" prefix="template"%> 

<tiles:insert page="/WEB-INF/jsp/layout/BaseLayout.jsp" flush="true"> 
<tiles:put name="OrgHeader" value="/WEB-INF/jsp/header/OrgHeader.jsp" />
<tiles:put name="AppMenu" value="/WEB-INF/jsp/menu/MainMenu.jsp"/>
<tiles:put name="AppAdminMenu" value="/WEB-INF/jsp/menu/SuperAdminMenu.jsp"/>		
<tiles:put name="Title"	value="404 Error Page"/>
<tiles:put name="Content" value="/WEB-INF/jsp/content/main/Error.jsp"/>
</tiles:insert> 
