<%@ page isELIgnored="false"%>

<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>

<%@ page import="gov.nih.nci.caxchange.ctom.viewer.constants.*"%>

<s:form id="ParticipantForm" action="searchParticipant.action" method="post">
    <s:hidden name="tableId" value="searchParticipant"/>
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
<!--																				<s:if test="hasActionErrors()"><div><s:actionerror /></div></s:if>-->
																			</td>
																		</tr>
																		<tr>
																			<td>
																				<center>
																					<table cellpadding="0" cellspacing="0" border="0">
																					    <tr>
													                                        <td>
													                                            <font size="2" face="arial">Participant:</font>
													                                        </td>
													                                        <td>
                                                                                                <s:textfield name="searchTerm" cssStyle="width: 2in"/>
                                                                                            </td>
												                                        </tr>
												                                        <tr>
																							<td colspan="2">																							
																								<font size="2" face="arial">Enter one or more search terms. First name, last name and identifier will be searched.</font>
																							</td>
																						</tr>
																					    <tr>
													                                        <td>
													                                            <font size="2" face="arial">Study ID:</font>
													                                        </td>
													                                        <td>
                                                                                                <s:textfield name="studyId" cssStyle="width: 2in"/>
                                                                                            </td>
												                                        </tr>
												                                        <tr>
													                                        <td>
													                                            <font size="2" face="arial">Study Title:</font>
													                                        </td>
													                                        <td>
														                                        <s:textfield name="studyTitle" cssStyle="width: 2in"/>
													                                        </td>
												                                        </tr>																						
																						<tr>
												                                            <td colspan="2" align="center">
														                                        <s:submit type="button" cssClass="actionButton">Search</s:submit>
													                                        </td>
													                                    </tr>
<!--																						<tr>-->
<!--																							<td>-->
<!--																								<html:text style="width: 4in"-->
<!--																									property="participantPhrase" />-->
<!--																							</td>-->
<!--																							<td>--> 
<!--																								<html:submit style="actionButton">Search</html:submit>-->
<!--																							</td>-->
<!--																						</tr>-->
<!--																						<tr>-->
<!--																							<td>-->
<!--																								<font size="2" face="arial"> <br> *-->
<!--																									Please enter one or more search terms above and-->
<!--																									select the "Search" button.<br> ** Search-->
<!--																									will perform search in Fisrt name, Last name-->
<!--																									and Identifier. </font>-->
<!--																							</td>-->
<!--																						</tr>-->
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
 <s:if test="searchResults != null">
  <!-- laf box 1st half -->
  <div class="box">
	<div align=center>
	    <!-- header -->
	    <div class="header"><div class="background-L"><div class="background-R">
	      <h2>Search Results:
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
     <display:table name="searchResults" sort="list" cellspacing="0" cellpadding="3"
			               pagesize="25" id="participantTable" export="true"
			               defaultsort="1"
                        requestURI=""                       
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
           <display:column class="dataCellText" sortable="true" title=" Participant ID (MRN)">
               <a href="#" onclick="displayLabs('<%=DisplayConstants.LABACTIVITES_ID%>', ${participantTable_rowNum})">${participantTable.patientId}</a>
           </display:column>
           <display:column property="firstName"  class="dataCellText" sortable="true" title="First Name"/>
           <display:column property="lastName" class="dataCellText" sortable="true" title="Last Name" />        
           <display:column property="studyId"  class="dataCellText" sortable="true" title="Study ID"  />
          </display:table>
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
  </s:if>
</div>
</s:form>

<script type="text/javascript">
function loadLabs(indexValue){
 
    document.ParticipantSearchForm.index.value=indexValue;
     document.ParticipantSearchForm.action="participantSearch.do?operation=loadLabs";
 	document.ParticipantSearchForm.submit();
}
</script>
