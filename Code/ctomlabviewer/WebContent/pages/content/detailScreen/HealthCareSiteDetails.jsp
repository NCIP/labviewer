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
<%@ page import="gov.nih.nci.caxchange.ctom.viewer.forms.HealthCareSiteForm"%>

<html:form action="loadHealthCareSite.do">
<logic:present name="HealthCareSiteForm" property="hcsList">

		<!-- laf box 1st half -->
		<div class="box">
			<div align=center>
				<!-- header -->
				<div class="header">
					<div class="background-L">
						<div class="background-R">
							<h2>
								HealthCareSite(s) details
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
																		width="100%" class="contentBegins">
																		<tr>
																			<td>
																				<table summary="Enter summary of data here"
																					cellpadding="3" cellspacing="0" border="0"
																					id="myScrollTable" class="sortable dataTable"
																					width="100%">
																					<thead class="scrollbar">
																						<tr>
																							<th class="dataTableHeader" scope="col"
																								align="center" width="15%">
																								Name
																							</th>
																							<th class="dataTableHeader" scope="col"
																								align="center" width="15%">
																								Address
																							</th>
																							<th class="dataTableHeader" scope="col"
																								align="center" width="15%">
																								Telephone
																							</th>
																							<th class="dataTableHeader" scope="col"
																								align="center" width="25%">
																								Email
																							</th>
																							<th class="dataTableHeader" scope="col"
																								align="center" width="25%">
																								Updated Date
																							</th>
																						</tr>
																					</thead>
																					<tbody>
																						<nested:iterate name="HealthCareSiteForm"
																							id="hcsList" property="hcsList"
																							type="gov.nih.nci.caxchange.ctom.viewer.beans.HCSite"
																							indexId="iterateId">
																							<tr>
																								<td class="dataCellText" width="15%">&nbsp;
																									<nested:write name="hcsList"
																										property="name" />
																								</td>
																								<td class="dataCellText" width="15%">&nbsp;
																									<nested:write name="hcsList"
																										property="address" />
																								</td>
																								<td class="dataCellText" width="15piList">&nbsp;
																										<nested:write name="hcsList"
																										property="phone" />
																								</td>
																								<td class="dataCellText" width="15%">&nbsp;
																									<nested:write name="hcsList"
																										property="email" />
																								</td>
																								<td class="dataCellText" width="25%">&nbsp;
																									<nested:write name="hcsList"
																										property="updatedDate" />
																									<logic:equal name="hcsList" property="coppaUpdate" value="Y">
																									 <img src="images/COPPA.jpg" alt="Data obtained from COPPA" height="30" width="40">
																									</logic:equal>	
																								</td>
																							</tr>
																						</nested:iterate>
																					</tbody>
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
	</logic:present>
</html:form>
