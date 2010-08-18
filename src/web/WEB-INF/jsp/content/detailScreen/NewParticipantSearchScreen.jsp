<%@ page isELIgnored="false"%>

<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>

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



<html:form action="participantSearch.do?operation=doParticipantSearch" method="post">									
<!-- laf box 1st half -->
<div style="margin-top: 0; padding-top: 0;">
   <!-- laf box 1st half -->
  <div class="box">
			<div align="center">
				<!-- header -->
				<div class="header">
					<div class="background-L">
						<div class="background-R">
							<h2>
								Participant Search
							</h2>
						</div>
					</div>
				</div>
				<!-- end header -->
				<!-- inner border -->
				<div class="border-T">
					<div class="border-L">
						<div class="border-R">
							<div class="border-B">
								<div class="border-TL">
									<div class="border-TR">
										<div class="border-BL">
											<div class="border-BR">
												<div class="interior">
													<!-- interior -->
													<div class="content">
														<!-- laf box 1st half -->
														<table summary="" cellpadding="0" cellspacing="0"
															border="0" width="95%" height="100%">
															<tr>
																<td valign="top">
																	<table cellpadding="0" cellspacing="0" border="0"
																		width="100%" height="100%" class="contentBegins">
																		<tr>
																			<td>
																				<html:errors />
																			</td>
																		</tr>
																		<tr>
																			<td>
																				<center>
																					<table cellpadding="0" cellspacing="0" border="0">
																						<tr>
																							<td>
																								<html:text style="width: 4in"
																									property="participantPhrase" />
																							</td>
																							<td>
																								<!-- action buttons begins -->
																								<html:submit style="actionButton">Search</html:submit>
																							</td>
																						</tr>
																						<tr>
																							<td>
																								<font size="2" face="arial"> <br> *
																									Please enter one or more search terms above and
																									select the "Search" button.<br> ** Search
																									will perform search in First name, Last name
																									and Identifier. </font>
																							</td>
																						</tr>
																					</table>
																				</center>
																			</td>
																		</tr>
																	</table>
																</td>
															</tr>
														</table>
														<!-- laf box 2nd half -->
													</div>
													<!-- content -->
												</div>
												<!-- interior -->
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<!-- end inner border -->
			</div>
		</div>
<!-- laf box 2nd half -->    
 </div>
<div style="height: 1em; white-space: nowrap;"></div>
<div>
 <logic:present name="ParticipantSearchForm" property="participantsList">
  <!-- laf box 1st half -->
  <div class="box">
	<div align=center>
	    <!-- header -->
	    <div class="header"><div class="background-L"><div class="background-R">
	      <h2>Search Results: <bean:write name="ParticipantSearchForm" property="participantPhrase"/>
	    </div></div></div>
	    <!-- end header -->
	    <!-- inner border -->
	    <div class="border-T"><div class="border-L"><div class="border-R"><div class="border-B"><div class="border-TL"><div class="border-TR"><div class="border-BL"><div class="border-BR">
	        <div class="interior"> <!-- interior -->
	            <div class="content">
     <!-- laf box 1st half --> 
     <table summary="" cellpadding="0" cellspacing="0"
																border="0" width="95%" height="100%">
																<tr>
																	<td valign="top">
																		<table cellpadding="0" cellspacing="0" border="0"
																			width="100%" height="100%" class="contentBegins">
																			<tr>
																				<td>
     <display:table name="${sessionScope.ParticipantSearchForm.participantsList}" sort="list" cellspacing="0" cellpadding="3"
			               id="participantTable" export="true" 
			               size="totalParticipants" 
			               defaultsort="1"
                        requestURI=""
                        decorator="gov.nih.nci.caxchange.ctom.viewer.util.ParticipantSearchDecorator"                        
	    style="border: 1px solid black; margin-top: 1em; margin-bottom: 1em; width: 100%;" class="dataTable">    
           <display:setProperty name="paging.banner.placement" value="bottom" />    
           <display:setProperty name="paging.banner.item_name" value="Participants" />               
           <display:setProperty name="paging.banner.items_name" value="Participants" />    
           <display:setProperty name="css.th.sorted" value="dataTableHeader" />
           <display:setProperty name="css.th.even" value="dataRowLight" />
           <display:setProperty name="css.th.odd" value="dataRowDark" />
           <display:setProperty name="export.excel.filename" value="Participant.xls"/>    
           <display:setProperty name="export.csv.filename" value="Participant.csv"/>               
           <display:setProperty name="export.xml.filename" value="Participant.xml"/>                          
           <display:setProperty name="export.excel.include_header" value="true"/>                                     
           <display:setProperty name="export.csv.include_header" value="true"/>                                     
           <display:setProperty name="export.xml.include_header" value="true"/>   
           <display:column class="dataCellText" sortable="true" sortProperty="patientId" title="Participant ID (MRN)" maxLength="90" media="html" >
               <a href="#" onclick="loadLabs(${participantTable_rowNum})"> ${participantTable.patientId}</a>
           </display:column>
           <display:column class="dataCellText" title="Participant ID (MRN)" media="csv excel xml" >
               ${participantTable.patientId}
           </display:column>
           <display:column property="firstName"  class="dataCellText" sortable="true" title="First Name" maxLength="200"/>
           <display:column property="lastName" class="dataCellText" sortable="true" title="Last Name" />
          </display:table>	                       
	      <input type="hidden" name="index" value=""/>
																				</td>
																			</tr>
																		</table>
																	</td>
																</tr>
															</table>



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
//var t = new ScrollableTable(document.getElementById('myScrollTable'), 82);
//handles load action
function loadLabs(indexValue){
 
    document.ParticipantSearchForm.index.value=indexValue;
     document.ParticipantSearchForm.action="participantSearch.do?operation=loadLabs";
 	document.ParticipantSearchForm.submit();
}
</script>
</head>
