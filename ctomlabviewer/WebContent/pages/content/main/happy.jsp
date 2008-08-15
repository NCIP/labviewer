<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%> 
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%> 
<%@ taglib uri="http://jakarta.apache.org/struts/tags-template" prefix="template"%> 

<tiles:insert page="/pages/layout/BaseLayout.jsp" flush="true"> 
<tiles:put name="AppMenu" value="/pages/menu/MainMenu.jsp"/>
<tiles:put name="AppAdminMenu" value="/pages/menu/SuperAdminMenu.jsp"/>		
<tiles:put name="Title"	value="cTODS LabViewer Diagnostic happy page "/>
<tiles:put name="Content" value="/pages/content/main/happy-conf.jsp"/>
</tiles:insert> 


