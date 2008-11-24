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
<%@ page import="gov.nih.nci.caxchange.ctom.viewer.forms.*"%>

<html:form action="participantSearch.do?operation=doParticipantSearch" method="post">									
<!-- laf box 1st half -->
<div style="margin-top: 0; padding-top: 0;">
  <logic:present name="StudySearchForm" property="studiesList">
  
   <!-- laf box 1st half -->
  <div class="box">
	<div  align="center">
	    <!-- header -->
	    <div class="header"><div class="background-L"><div class="background-R">
	      <h2>Participant Search</h2>
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
														<html:text style="width: 4in" property="participantPhrase"/>
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
												** Search will perform search in Fisrt name, Last name and Identifier. 
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
  </logic:present>
</div>
<div style="height: 1em; white-space: nowrap;"></div>
<div>
  <logic:present name="ParticipantSearchForm" property="participantsList">
  
   <!-- laf box 1st half -->
  <div class="box">
	<div align=center>
	    <!-- header -->
	    <div class="header"><div class="background-L"><div class="background-R">
	      <h2>Search Results:<bean:write name="ParticipantSearchForm" property="participantPhrase"/> [ <bean:write name="ParticipantSearchForm" property="listSize"/>  Record(s) found]</h2>
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
				<table cellpadding="0" cellspacing="0" border="0" width="100%" class="contentBegins">
					<tr>
						<td>
						<table summary="Enter summary of data here" cellpadding="3" cellspacing="0" border="0" id="myScrollTable" class="sortable dataTable" width="100%">
      				<thead class="scrollbar">
      					 <tr>
        	<th class="dataTableHeader" scope="col" align="center" width="15%"> ID[MRN]</th>
            <th class="dataTableHeader" scope="col" align="center" width="15%">First Name</th>
            <th class="dataTableHeader" scope="col" align="center" width="15%">Last Name</th>
            <th class="dataTableHeader" scope="col" align="center" width="15%">Study Id</th>
           <!--  <th class="dataTableHeader" scope="col" align="center" width="15%">View Participant Details</th> --> 
     </tr>
     </thead>
     <tbody>   
     <nested:iterate  name="ParticipantSearchForm" id="participantsList" property="participantsList" type="gov.nih.nci.caxchange.ctom.viewer.viewobjects.ParticipantSearchResult" indexId="iterateId">
            <tr> 
                       <td class="dataCellText" width="15%">
                        <a href="#" onClick="loadLabs(${iterateId})"><nested:write name="participantsList" property="patientId" /></a>
                        </td>
                      <td class="dataCellText" width="15%">
                            <nested:write name="participantsList" property="firstName"/>
                        </td>
                       <td class="dataCellText" width="15%">
                            <nested:write name="participantsList" property="lastName"/>
                        </td>
                       <td class="dataCellText" width="15%">
                            <nested:write name="participantsList" property="studyId"/>
                        </td>
                        <nested:hidden name="participantsList" property="index" indexed="true"/>
                       <!--  <td class="dataCellText" width="15%">
                            <a href="">Details</a>
		                  </td>-->
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
function loadLabs(indexValue){
 
    var input2="participantsList[" + indexValue + "].index";
     var rate2 = document.forms["ParticipantSearchForm"].elements[input2];
     rate2.value= "T";
     document.ParticipantSearchForm.action="participantSearch.do?operation=loadLabs";
 	document.ParticipantSearchForm.submit();
}
</script>
</head>
