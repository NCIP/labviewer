<%@ page isELIgnored="false"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"
	prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"
	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic"
	prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles"
	prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-template"
	prefix="template"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested"
	prefix="nested"%>
<%@ page import="gov.nih.nci.caxchange.ctom.viewer.constants.*"%>
<%@ page import="gov.nih.nci.caxchange.ctom.viewer.viewobjects.*"%>
<%@ page import="gov.nih.nci.caxchange.ctom.viewer.forms.*"%>

<html:form action="studySearch.do?operation=doStudySearch" method="post">
<!-- laf box 1st half -->
<div style="margin-top: 0; padding-top: 0;">
 <!-- laf box 1st half -->
  <div class="box">
	<div  align="center">
	    <!-- header -->
	    <div class="header"><div class="background-L"><div class="background-R">
	      <h2>Study Search</h2>
	    </div></div></div>
	    <!-- end header -->
	    <!-- inner border -->
	    <div class="border-T"><div class="border-L"><div class="border-R"><div class="border-B"><div class="border-TL"><div class="border-TR"><div class="border-BL"><div class="border-BR">
	        <div class="interior"> <!-- interior -->
	            <div class="content">
     <!-- laf box 1st half -->      
 	<table summary="" cellpadding="0" cellspacing="0" border="0" width="95%" height="100%" >
 	<tr>
			<td valign="top">
				<table cellpadding="0" cellspacing="0" border="0" width="100%" height="100%" class="contentBegins">
					<tr>
						<td>
																<center>
										<table cellpadding="0" cellspacing="0" border="0">
												<tr>
													<td>
														<html:text style="width: 4in" property="studyPhrase"/>
													</td>
													<td>
														<!-- action buttons begins -->
														<html:submit style="actionButton">Search</html:submit>
													</td>
												</tr>
												<tr>
												<td>
												<font size="2" face="arial">
												<br>	
												* Please enter one or more search terms above and select the "Search" button.<br>
												** Search will perform search in Study Title and Identifier. 
												</font>
												</td></tr>
											</table>
											</center>
  </td></tr></table>
  </td></tr></table>
 

<!-- laf box 2nd half -->
	        </div>  <!-- content -->
        </div><!-- interior -->
    </div></div></div></div></div></div></div></div>
    <!-- end inner border -->
	</div>
</div>
<!-- laf box 2nd half -->    
</div>
<div style="height: 1em; white-space: nowrap;"></div>
<div>
  <logic:present name="StudySearchForm" property="studiesList">
  
   <!-- laf box 1st half -->
  <div class="box">
	<div  align="center">
	    <!-- header -->
	    <div class="header"><div class="background-L"><div class="background-R">
	      <h2>Search Results:<bean:write name="StudySearchForm" property="studyPhrase"/> [ <bean:write name="StudySearchForm" property="listSize"/>  Record(s) found]</h2>
	    </div></div></div>
	    <!-- end header -->
	    <!-- inner border -->
	    <div class="border-T"><div class="border-L"><div class="border-R"><div class="border-B"><div class="border-TL"><div class="border-TR"><div class="border-BL"><div class="border-BR">
	        <div class="interior"> <!-- interior -->
	            <div class="content">
     <!-- laf box 1st half -->      
 	<table summary="" cellpadding="0" cellspacing="0" border="0" width="95%" height="100%" >
 	<tr>
			<td valign="top">
				<table cellpadding="0" cellspacing="0" border="0" width="100%" height="100%" class="contentBegins" >
					<tr>
						<td>
						<table summary="Enter summary of data here" cellpadding="3" cellspacing="0" border="0" id="myScrollTable" class="dataTable sortable" width="100%" height="100%">
      				<thead class="scrollbar">
      					 <tr>
        	<th class="dataTableHeader" scope="col" align="center" width="15%"> ID</th>
            <th class="dataTableHeader" scope="col" align="center" width="15%">Short Title</th>
            <th class="dataTableHeader" scope="col" align="center" width="15%">Sponsor code</th>
            <th class="dataTableHeader" scope="col" align="center" width="15%">Phase code</th>
            <th class="dataTableHeader" scope="col" align="center" width="15%">Status</th>
            </tr>
     </thead>
     <tbody>   
     <nested:iterate  name="StudySearchForm" id="studiesList" property="studiesList" type="gov.nih.nci.caxchange.ctom.viewer.viewobjects.StudySearchResult" indexId="iterateId">
            <tr> 
                       <td class="dataCellText" width="15%">&nbsp;
                        <a href="#" onClick="loadParticipant(${iterateId})"><nested:write name="studiesList" property="studyId"/></a>
                        </td>
                      <td class="dataCellText" width="15%">&nbsp;
                            <nested:write name="studiesList" property="shortTitle"/>
                        </td>
                       <td class="dataCellText" width="15%">&nbsp;
                            <nested:write name="studiesList" property="sponsorCode"/>
                        </td>
                       <td class="dataCellText" width="15%">&nbsp;
                            <nested:write name="studiesList" property="phaseCode"/>
                        </td>
                        <td class="dataCellText" width="15%">&nbsp;
                            <nested:write name="studiesList" property="status"/>
                        </td>
                        <nested:hidden name="studiesList" property="index" indexed="true"/>         
                    </tr>  
         </nested:iterate>
       </tbody>
    </table>
  </td></tr></table>
  </td></tr></table>
 

<!-- laf box 2nd half -->
	        </div>  <!-- content -->
        </div><!-- interior -->
    </div></div></div></div></div></div></div></div>
    <!-- end inner border -->
	</div>
</div>
<!-- laf box 2nd half -->    
  </logic:present>
</div>
</html:form>
<head>
<script type="text/javascript">
var t = new ScrollableTable(document.getElementById('myScrollTable'), 72);
//handles load action
function loadParticipant(indexValue){
 
    var input2="studiesList[" + indexValue + "].index";
     var rate2 = document.forms["StudySearchForm"].elements[input2];
     rate2.value= "T";
     document.StudySearchForm.action="studySearch.do?operation=loadParticipant";
 	document.StudySearchForm.submit();
}
</script>
</head>
