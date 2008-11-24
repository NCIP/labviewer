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
<%@ page import="gov.nih.nci.caxchange.ctom.viewer.constants.*"%>
<!-- laf box 1st half -->
<div class="box">
	<div class="pane" align=center>
		<!-- header -->
		<div class="header">
			<div class="background-L">
				<div class="background-R">
					<h2>
						Filter The Lab Activity Search Criteria
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

												<div>
													<table summary="" cellpadding="0" cellspacing="0"
														border="0" width="100%" height="50%">
														<tr>
															<td align="center">
																<font size="2" face="verdana"> Filter Search
																	Criteria </font>
															</td>
														</tr>
														<tr>
															<td>
																<html:errors />
															</td>
														</tr>
														<tr>
															<td valign="top">
																<table cellpadding="0" cellspacing="0" border="0"
																	width="100%" height="90%">
																	<tr>
																		<td>
																			<table summary="" cellpadding="0" cellspacing="0"
																				border="0" width="100%" height="100%">
																				<tr>
																					<td>
																						<html:form action="search.do" method="post">
																							<table cellpadding="5" cellspacing="5" border="0"
																								width="100%" height="100%">
																								<tr>
																									<td>
																										<font size="2" face="verdana"> <b>
																												Lab Test </b>
																									</td>
																									<td>
																										<font size="2" face="verdana"> <b>
																												Site </b> </font>
																									</td>
																									<td>
																										<font size="2" face="verdana"> <b>
																												Numeric Result </b> </font>
																									</td>
																									<td>
																										<font size="2" face="verdana"> <b>
																												Date Range </b> </font>
																									</td>

																								</tr>
																								<tr>
																								<tr>
																									<td>
																										<html:select property="selectedLabTest">
																											<html:options name="LabActivitiesForm"
																												property="labTestFilter" />
																										</html:select>
																									</td>
																									<td>
																										<html:select property="selectedSite">
																											<html:options name="LabActivitiesForm"
																												property="siteFilter" />
																										</html:select>
																									</td>
																									<td>
																										<html:select property="selectedNumericResult">
																											<html:options name="LabActivitiesForm"
																												property="numericResultFilter" />
																										</html:select>
																									</td>
																									<td>
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
																													<td>
																														<font size="2" face="verdana"><b>Begin
																																Date<b>
																														</font>
																													</td>
																													<td>
																														<font size="2" face="verdana"><b>End
																																Date</b>
																														</font>
																													</td>
																												</tr>
																												<tr>
																													<td>
																														<html:text property="beginDate"
																															value="(MM/DD/YYYY)" />
																													</td>
																													<td>
																														<html:text property="endDate"
																															value="(MM/DD/YYYY)" />
																													</td>
																												</tr>
																											</table>
																										</div>
																									</td>
																								</tr>
																								<tr>
																									<td colspan="5" align="center"
																										class="actionSection">
																										<!-- action buttons begins -->
																										<table cellpadding="4" cellspacing="0"
																											border="0">
																											<tr>
																												<td>
																													<html:reset style="actionButton">Reset</html:reset>
																												</td>
																												<td>
																													<html:submit style="actionButton">Search</html:submit>
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
												</div>
											</div>
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
	</div>
	
	<!-- end inner border -->
</div>
</div>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>


<div >
<logic:present name="<%=DisplayConstants.SEARCH_RESULT%>">
<jsp:include page="/pages/content/searchResult/LabActivitiesSearchResult.jsp"/> 
</logic:present>
</div>
<!-- laf box 2nd half -->
<head>
<script type="text/javascript">

	function handleCustomDates(element){
	
    if(element.value=="Custom dates/times"){
     document.getElementById("addTable").style.display = "block";
    }else {
     document.getElementById("addTable").style.display = "none";
    }
    
  }
  </script>    
</head>
