<%--
Copyright ScenPro, Inc and SemanticBits, LLC

Distributed under the OSI-approved BSD 3-Clause License.
See http://ncip.github.com/labviewer/LICENSE.txt for details.
--%>
<%@ page isELIgnored="false"%>

<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>

<%@ page import="gov.nih.nci.caxchange.ctom.viewer.constants.*"%>
	
<s:form id="StudyForm" action="searchStudy.action" method="post">
    <s:hidden name="tableId" value="searchStudy"/>
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
								Study Search
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
																				<center>
<!--																				    <s:if test="hasActionErrors()"><div><s:actionerror /></div></s:if>-->
																					<table cellpadding="0" cellspacing="3" border="0">
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
																							<td colspan="2">																							
																								<font size="2" face="arial">Enter a Study ID and/or a term from the Study Title.<br>Searches are case sensitive.  The wildcard character is "%".</font>
																							</td>
																						</tr>
																						<tr>
												                                            <td colspan="2" align="center">
														                                        <s:submit type="button" cssClass="actionButton">Search</s:submit>
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
        <s:if test="studySearchResults != null">
		    <!-- laf box 1st half -->
			<div class="box">
				<div align="center">
					<!-- header -->
					<div class="header">
						<div class="background-L">
							<div class="background-R">
								<h2>
									Search Results:
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
			 <display:table name="studySearchResults" sort="list" cellspacing="0" cellpadding="3"
			               id="studyTable" export="true" requestURI="" 
			               defaultsort="1"
	    style="border: 1px solid black; margin-top: 1em; margin-bottom: 1em; width: 100%;" class="dataTable">    
           <display:setProperty name="paging.banner.placement" value="bottom" />    
           <display:setProperty name="paging.banner.item_name" value="Studies" />               
           <display:setProperty name="paging.banner.items_name" value="Studies" />    
           <display:setProperty name="css.th.sorted" value="dataTableHeader" />
           <display:setProperty name="css.th.even" value="dataRowLight" />
           <display:setProperty name="css.th.odd" value="dataRowDark" />
           <display:setProperty name="export.excel.filename" value="Studies.xls"/>    
           <display:setProperty name="export.csv.filename" value="Studies.csv"/>               
           <display:setProperty name="export.xml.filename" value="Studies.xml"/>                          
           <display:setProperty name="export.excel.include_header" value="true"/>                                     
           <display:setProperty name="export.csv.include_header" value="true"/>                                     
           <display:setProperty name="export.xml.include_header" value="true"/>
           <display:column  class="dataCellText" sortable="true" sortProperty="studyId" title="Study ID" media="html" >
               <s:if test="#attr.studyTable.id == null">
                   ${studyTable.studyId}
               </s:if>
               <s:else>
                   <a href="#" onclick="displayParticipants('<%=DisplayConstants.PARTICIPANTSEARCH_ID%>', '${studyTable.studyId}', '${studyTable.longTitle}')">${studyTable.studyId}</a>
               </s:else>
           </display:column>
           <display:column property="studyId" class="dataCellText" title="Study ID" media="csv excel xml" />
           <display:column property="longTitle"  class="dataCellText" sortable="true" title="Official Title" maxLength="200"/>
           <display:column property="sponsorCode" class="dataCellText" sortable="true" title="Sponsor Code" />        
           <display:column property="phaseCode"  class="dataCellText" sortable="true" title="Phase Code"  />
           <display:column property="status"  class="dataCellText" sortable="true" title="Status"  />
           <display:column class="dataCellText" sortable="true" title="HealthCare Site" media="html" >
               <s:if test="#attr.studyTable.id == null">
                   NA
               </s:if>
               <s:else>
                   <a href="#"	onclick="displayHealthCareSite(${studyTable.id})">View Details</a>
               </s:else>
           </display:column>
           <display:column class="dataCellText" sortable="true" title="Principal Investigator" media="html" >
               <s:if test="#attr.studyTable.id == null">
                   NA
               </s:if>
               <s:else>
                   <a href="#" onclick="displayPrincipalInvestigator(${studyTable.id})">View Details</a>
               </s:else>
           </display:column>                                   
	</display:table>
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
        </s:if>
	</div>
</s:form>

<s:form id="ParticipantForm" action="displayParticipant.action" method="post">
    <s:hidden name="tableId"/>
    <s:hidden name="studyId"/>
    <s:hidden name="studyTitle"/>
</s:form>

<s:form id="HealthCareSiteForm" action="HealthCareSite.action" method="post">
    <s:hidden name="protocolId"/>
</s:form>

<s:form id="PrincipalInvestigatorForm" action="PrincipalInvestigator.action" method="post">
    <s:hidden name="protocolId"/>
</s:form>

<script type="text/javascript">

    function displayParticipants(tableId, studyId, studyTitle)
    {
        document.ParticipantForm.tableId.value = tableId;
        document.ParticipantForm.studyId.value = studyId;
        document.ParticipantForm.studyTitle.value = studyTitle;
        //document.ParticipantForm.target = '_self';
        document.ParticipantForm.submit();
    }

    function displayHealthCareSite(protocolId)
    {
        document.HealthCareSiteForm.protocolId.value = protocolId;
        var windowName = "HealthCareSite";
        window.open('', windowName, 'width=1125,height=600');
	    document.HealthCareSiteForm.target = windowName;
        document.HealthCareSiteForm.submit();
    }

    function displayPrincipalInvestigator(protocolId)
    {
        document.PrincipalInvestigatorForm.protocolId.value = protocolId;
        var windowName = "PrincipalInvestigator";
        window.open('', windowName, 'width=1125,height=600');
        document.PrincipalInvestigatorForm.target = windowName;
 	    document.PrincipalInvestigatorForm.submit();
    }
</script>
