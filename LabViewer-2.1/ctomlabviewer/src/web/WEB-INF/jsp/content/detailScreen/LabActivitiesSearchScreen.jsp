<br><%@ page isELIgnored="false"%>
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
<%@ page import="gov.nih.nci.caxchange.ctom.viewer.constants.*"%>
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
							Filter Search Criteria
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
															<td valign="top" align="center">
																<table cellpadding="0" cellspacing="0" border="0"
																	width="100%" height="100%" class="contentBegins"  align="center">
																	<tr>
																		<td>
																			<html:errors />
																		</td>
																	</tr>
																	<tr>
																		<td valign="top">
																			<table cellpadding="0" cellspacing="0" border="0"
																				width="90%" height="90%">
																				<tr align="center">
																					<td align="center">
																						<table summary="" cellpadding="0" cellspacing="0"
																							border="0" width="90%" height="100%" align="center">
																							<tr>
																								<td align="center">
																									<html:form action="search.do" method="post">
																										<table cellpadding="1" cellspacing="1"
																											border="0" width="100%" height="100%"  align="center">
																											<tr>
																												
																												<td width="6%">
																													<font size="2" face="verdana"> <b>
																															Site </b> </font>
																												</td>
																												<td width="15%">
																												<font size="2" face="verdana"> <b>
																												  Lab Test </b>
																												</td>
																												<td width="15%">
																													<font size="2" face="verdana"> <b>
																															Numeric Result </b> </font>
																												</td>
																												<td width="15%">
																													<font size="2" face="verdana"> <b>
																															Date Range </b> </font>
																												</td>

																											</tr>
																											<tr>
																											<tr>
																												<td width="6%">
																												<html:select property="selectedSite">
																												<html:options name="LabActivitiesForm"
																												property="siteFilter" />
																																																									</html:select>
																												</td>
																												
																												<td width="15%">
																													<html:select property="selectedLabTest">
																														<html:options name="LabActivitiesForm"
																															property="labTestFilter" />
																													</html:select>
																												</td>
																												
																												<td width="15%">
																													<html:select
																														property="selectedNumericResult">
																														<html:options name="LabActivitiesForm"
																															property="numericResultFilter" />
																													</html:select>
																												</td>
																												<td width="15%">
																													<html:select property="selectedDateRange"
																														onchange="handleCustomDates(this)">
																														<html:options name="LabActivitiesForm"
																															property="dateRangeFilter" />
																													</html:select>
																																																									</td>
																												<td valign="top">
																													<div id="addTable" style="display: none">
																														<table>
																															<tr>
																																<td width="5%">
																																	<font size="2" face="verdana"><b>Begin
																																			Date</b>
																																	</font><font size="1" face="verdana"><i>(MM/DD/YYYY)</i></font> 
																																</td>
																																<td width="5%">
																																	<font size="2" face="verdana"><b>End
																																			Date</b> </font><font size="1" face="verdana"><i>(MM/DD/YYYY)</i></font>
																																</td>
																															</tr>
																															<tr>
																																<td width="5%">
																																	<html:text name="LabActivitiesForm" property="beginDate" 
																																		 />
																																</td>
																																<td width="5%">
																																	<html:text name="LabActivitiesForm" property="endDate"
																																		 />
																																</td>
																															</tr>
																														</table>
																													</div>
																												</td>
																											</tr>
																											<tr>
																												<td colspan="4" align="center"
																													class="actionSection">
																													<!-- action buttons begins -->
																													<table cellpadding="4" cellspacing="0"
																														border="0">
																														<tr>
																															<td>
																																<html:button style="actionButton" property="reset" onclick="resetCriteria();">Reset</html:button>
																															</td>
																															<td>
																																<html:submit style="actionButton">Filter</html:submit>
																															</td>
																														</tr>
																													</table>
																												</td>
																											</tr>

																										</table>

																									</html:form>
																								</td>
																							</tr>
																						</table>
																					</td>
																				</tr>
																			</table>
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

<!-- end inner border -->
</div>
</div>
<br>
<br>
<div>
	<logic:present name="<%=DisplayConstants.SEARCH_RESULT%>">
		<jsp:include
			page="/WEB-INF/jsp/content/searchResult/NewLabActivitiesSearchResult.jsp" />
	</logic:present>
</div>
<!-- laf box 2nd half -->
<head>
	<script type="text/javascript">
	
	function resetCriteria()
	{
	    document.LabActivitiesForm.selectedSite.selectedIndex = 0;
	    document.LabActivitiesForm.selectedLabTest.selectedIndex = 0;
	    document.LabActivitiesForm.selectedNumericResult.selectedIndex = 0;
	    document.LabActivitiesForm.selectedDateRange.selectedIndex = 0;
    }

function handleCustomDates(element){
	var selectedDateFilter=element.value;
	if (selectedDateFilter==undefined)
	{
	selectedDateFilter = "${LabActivitiesForm.selectedDateRange}";
	
	}
	if(selectedDateFilter=="Custom dates/times"){
	
     		document.getElementById("addTable").style.display = "block";
     		   		     		
    	}else {
     		document.getElementById("addTable").style.display = "none";
    	}
    	
    
  }
  //on load to handle custom dates
  addLoadListener(handleCustomDates);
  </script>
</head>
