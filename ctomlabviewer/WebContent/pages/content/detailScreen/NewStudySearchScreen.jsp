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
	
<html:form action="studySearch.do?operation=doStudySearch" method="post">
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
																					<table cellpadding="0" cellspacing="0" border="0">
																						<tr>
																							<td>
																								<html:text style="width: 4in"
																									property="studyPhrase" />
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
																									will perform search in Study Title and
																									Identifier. </font>
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
		<logic:present name="StudySearchForm" property="studiesList">
		<!-- laf box 1st half -->
			<div class="box">
				<div align="center">
					<!-- header -->
					<div class="header">
						<div class="background-L">
							<div class="background-R">
								<h2>
									Search Results:
									<bean:write name="StudySearchForm" property="studyPhrase" />
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
																				
			 <display:table name="${sessionScope.StudySearchForm.studiesList}" sort="list" cellspacing="0" cellpadding="3"
			               pagesize="25" id="studyTable" export="true" 
			               size="totalStudies" 
			               defaultsort="10" defaultorder="descending"
                        requestURI=""
                        decorator="gov.nih.nci.caxchange.ctom.viewer.util.StudySearchDecorator"                        
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
           <display:column  class="dataCellText" sortable="true" title="ID" maxLength="90" >
           <a href="#" onclick="loadParticipant(${studyTable_rowNum})">${studyTable.studyId}</a>
           </display:column>
           <display:column property="shortTitle"  class="dataCellText" sortable="true" title="Short Title" maxLength="200"/>
           <display:column property="sponsorCode" class="dataCellText" sortable="true" title="Sponsor code" />        
           <display:column property="phaseCode"  class="dataCellText" sortable="true" title="Phase code"  />
           <display:column property="status"  class="dataCellText" sortable="true" title="Status"  />
           <display:column class="dataCellText" sortable="true" title="HealthCare Site"  >
		<a href="#"	onclick="showHealthCareSite(${studyTable_rowNum})">Details</a>
           </display:column>
           <display:column class="dataCellText" sortable="true" title="Principal Investigator"  >
           <a href="#" onclick="showPI(${studyTable_rowNum})">Details</a>
           </display:column>
                                   
	</display:table>	     
 	<input type="hidden" name="index" value=""/>
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
		</logic:present>
	</div>
</html:form>
<head>
	<script type="text/javascript">
var t = new ScrollableTable(document.getElementById('myScrollTable'), 82);
//handles load action
function loadParticipant(indexValue){
    document.StudySearchForm.index.value=indexValue;
    document.StudySearchForm.action="studySearch.do?operation=loadParticipant";
    document.StudySearchForm.submit();
}
//handles show HealthCareSite action
function showHealthCareSite(indexValue){
     document.StudySearchForm.index.value=indexValue;
     window.open('','HealthCareSite','width=1125,height=600');
	 document.StudySearchForm.target = "HealthCareSite";
	 document.StudySearchForm.action="studySearch.do?operation=showHealthCareSite";
     document.StudySearchForm.submit();
 	 
     
 	
}

//handles show PI action
function showPI(indexValue){
     document.StudySearchForm.index.value=indexValue;
     window.open('','PI Details','width=1125,height=600');
     document.StudySearchForm.target = "PI Details";
     document.StudySearchForm.action="studySearch.do?operation=showPI";
 	document.StudySearchForm.submit();
}
</script>
</head>
